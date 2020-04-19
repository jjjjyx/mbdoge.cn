<template>
    <div v-show="visible" :class="$style.conn"
         @drop.prevent="handlerDrop"
         @dragover.prevent="handlerDragover"
         @dragleave.prevent="handlerDragleave">
        <div :class="$style.text">
            <p>上传文件</p>
            <p :class="$style.desc">仅支持：{{accept}}</p>
        </div>
    </div>
</template>

<script>
import {on, off} from '@/tools/dom'
import {accept} from './constant'
// 仅允许图片
//
const allowAccept = accept.split(',')

function filterDataTransfer(entries) {
    return new Promise((resolve, reject) => {
        const files = []
        for (let i = 0; i < entries.length; i++) {
            let entry = typeof entries[i].webkitGetAsEntry === 'function' ? entries[i].webkitGetAsEntry() : entries[i]
            console.log(entry)
            if (entry.isFile) {
                entry.file(function (file) {
                    console.log(file, entry, this)
                })
            } else {
                // 不支持了，太多问题了，就怕搞个很多文件，单层目录就好
                // entry.createReader().readEntries(eachEntry)
            }

        }
        resolve(files)
    })
}

export default {
    name: 'upload-dragger',
    data() {
        return {
            dragover: false,
            visible: false,
            accept
        }
    },
    methods: {
        handlerDragenter(e) {
            // console.log(e, e.dataTransfer.items)
            let items = e.dataTransfer.items
            // 没有包含文件则退出
            let fileFlag = false
            for (let i = 0; i < items.length; i++) {
                if (items[i].kind === 'file') {
                    fileFlag = true
                    break
                }
            }
            if (!fileFlag) return

            e.preventDefault()
            // 拖动的必须是文件 显示拖动的效果框
            this.visible = true
        },
        async handlerDrop(e) {

            // 过滤文件

            // const items = await filterDataTransfer(e.dataTransfer.items)
            const items = [].slice.call(e.dataTransfer.files)
                .filter(item => {
                    if (item.size === 0) {
                        return false
                    }
                    const {type} = item
                    const baseType = type.replace(/\/.*$/, '')

                    return allowAccept.some((acceptedType) => {
                        if (/\/\*$/.test(acceptedType)) {
                            return baseType === acceptedType.replace(/\/\*$/, '')
                        }
                        if (/^[^\/]+\/[^\/]+$/.test(acceptedType)) {
                            return type === acceptedType
                        }
                        return false
                    })
                })
            this.$emit('drop-file', items)
            this.visible = false
            this.dragover = false
        },
        handlerDragleave(e) {
            this.visible = false
            this.dragover = false
        },
        handlerDragover(e) {
            this.dragover = true
        }
    },
    destroyed() {
        off(document.body, 'dragenter', this.handlerDragenter)
        if (this.$el.parentNode === document.body) {
            document.body.removeChild(this.$el)
        }
    },
    mounted() {
        // 注册到body 中
        document.body.appendChild(this.$el)
        on(document.body, 'dragenter', this.handlerDragenter)
    }

}
</script>

<style module lang="scss">
.conn {
    position: fixed;
    background: rgba(255, 255, 255, 0.6);
    border: 3px dashed rgb(204, 204, 204);
    z-index: 1000000;
    overflow: hidden;
    bottom: 0;
    top: 0;
    left: 0;
    right: 0;
}

.text {
    color: rgb(153, 153, 153);
    font-size: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    height: 100%;
}
.desc {
    font-size: 1rem;
}
</style>
