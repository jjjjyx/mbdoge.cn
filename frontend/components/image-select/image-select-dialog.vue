<template>
    <el-dialog
        :title="title"
        :visible.sync="dialogVisible"
        center
        @close="cancel">
        <!-- 上传图片，图片列表，网络搜索 -->
        <div class="mb-2">
            <el-button @click="handlerClickUploadImage" class="mr-2">上传图片</el-button>
            <el-button @click="handlerClickRemoteUrl" class="mr-2">远程资源</el-button>
        </div>
        <el-tabs v-model="activeName">
            <el-tab-pane label="最近上传" name="last">
                <!-- 这里显示最近上传的图片，以及通过open 参数传入的图片 -->
            </el-tab-pane>
            <el-tab-pane label="我的图片库" name="myMedia">
            </el-tab-pane>
            <el-tab-pane label="网络搜索" name="network">
                网络资源集合： <br>
                <ul>
                    <li>
                        知乎
                        <el-link href="https://www.zhihu.com/question/314166850/answer/611720661" target="_blank">有哪些有格调的免费素材网站？</el-link>
                    </li>
                </ul>
            </el-tab-pane>
        </el-tabs>

        <span slot="footer" class="dialog-footer">
            <el-button @click="cancel">取 消</el-button>
            <el-button type="primary" @click="confirmSelect">确 定</el-button>
        </span>
    </el-dialog>
</template>

<script>
export default {
    name: 'image-select-dialog',
    data () {
        return {
            activeName: 'last',
            dialogVisible: false,
            resolve: null,
            reject: null,
            multiple: false,
            selected: [],
            images: [],
            title: '选择图片'
        }
    },
    methods: {
        handlerClickUploadImage () {
            console.log('handlerClickUploadImage')

        },
        handlerClickRemoteUrl () {
            console.log('handlerClickRemoteUrl')
        },
        handlerClickSelectAll () {
            console.log(this, 'this.multiple = ', this.multiple)
            if (this.multiple) {
                this.selected = []
                this.selected.push(...this.images)
            }
        },
        handlerClickUnSelectAll () {
            if (this.multiple) {
                this.selected = []
            } else {
                this.selected = null
            }
            // this.selected.push(...this.images)
        },
        open ({ multiple = false, images = [], selected = [] } = {}) {
            this.multiple = multiple
            this.images = images
            this.dialogVisible = true

            if (this.multiple) {
                this.selected = selected.filter((file) => {
                    return this.images.findIndex((i) => i.uid === file.uid) !== -1
                })
            } else {
                this.selected = null
            }
            return new Promise((resolve, reject) => {
                this.resolve = resolve
                this.reject = reject
            })
        },
        cancel () {
            console.log('image cancel')
            this.dialogVisible = false
            this.reject(new Error('Not selected'))
        },
        confirmSelect () {
            this.dialogVisible = false
            this.resolve(_.cloneDeep(this.selected))
        },
        handleClose () {
            this.reject(new Error('Not selected'))
        },
        checkActive (file) {
            // console.log('checkActive = ', file, this.selected.findIndex((i) => i.uid === file.uid))
            if (this.multiple) {
                return this.selected.findIndex((i) => i.uid === file.uid) !== -1
            } else {
                return this.selected === file
            }
        },
        handlerClickItem (file) {
            if (this.multiple) {
                let ret = this.selected.findIndex((i) => i.uid === file.uid)
                if (ret >= 0) {
                    this.selected.splice(ret, 1)
                } else {
                    this.selected.push(file)
                }
            } else {
                this.selected = file
            }
        }
    }
}
</script>

<style module lang="scss">
.main {
    max-height: 500px;
    overflow-y: auto;
    overflow-x: hidden;
}
.item {
    :global(.el-image) {
        width: 100%;
        height: 120px;
    }
    :global {
        .el-card {
            overflow: visible;
        }
        .el-progress-bar__outer {
            background-color: transparent;
        }
        .el-progress-bar__outer, .el-progress-bar__inner {
            border-radius: 0;
        }

        .el-icon-success {
            position: absolute;
            top: .5rem;
            left: .5rem;
            font-size: 1rem;
            z-index: 2;
        }
    }
    &.active {
        :global {
            .el-icon-success {
                color: $--color-success;
            }
        }
    }
    .img {
        position: relative;
        font-size: 0;
    }

    .fn {
        font-size: 1rem;
        padding: 8px;
        text-align: center;
    }
}
</style>
