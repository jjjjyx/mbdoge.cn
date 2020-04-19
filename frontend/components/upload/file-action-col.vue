<template>
    <div>
        <!--    上传中 取消-->
        <!--    上传完成 复制，清除-->
        <!--    上传失败 清除-->
        <el-tooltip content="复制">
            <el-link @click="handlerCopy" :class="$style.btn" v-if="showCopy" :underline="false" ><i class="el-icon-document-copy"></i></el-link>
        </el-tooltip>
        <el-tooltip content="取消">
            <el-link @click="handlerCancel" :class="$style.btn" :underline="false" type="info"><i class="el-icon-close"></i></el-link>
        </el-tooltip>
        <el-tooltip content="清除">
            <el-link @click="handlerDelete" :class="$style.btn" v-if="showDelete" :underline="false" type="info"><i class="el-icon-delete"></i></el-link>
        </el-tooltip>
    </div>
</template>

<script>
import {UploadStatus} from '@/components/upload/constant'
import {copy} from '@/tools/copy'

export default {
	name: 'file-action-col',
    props: {
	    info: Object
    },
    computed: {
        showCopy () {
            return this.info.status === UploadStatus.FULFIL
        },
	    showDelete () {
            return this.info.status !== UploadStatus.UPLOADING
                // || this.info.status === UploadStatus.CANCEL
                // || this.info.status === UploadStatus.FULFIL
        }
    },
    methods: {
        handlerCopy () {
            // console.log()
            const {url, name} = this.info.resp
            copy(`![${name}](${url})`)
        },
        handlerCancel () {
            this.info.source.cancel('取消上传')
        },
        handlerDelete () {
            this.$emit('clear-file', this.info)
        }
    }
}
</script>

<style module lang="scss">
.btn {
    margin-left: .25rem;
}
</style>
