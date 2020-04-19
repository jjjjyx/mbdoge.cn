<template>
    <el-collapse-transition>
        <div :class="$style.uploadMain" v-show="visible">
            <div :class="$style.header">
                <div :class="$style.title">{{title}}</div>
                <el-tooltip content="最大/最小化">
                    <font-icon :class="$style.btn" :type="miniIcon" @click="mini = !mini"></font-icon>
                </el-tooltip>
                <el-tooltip content="隐藏">
                    <font-icon :class="$style.btn" type="el-icon-close" @click="visible = false"></font-icon>
                </el-tooltip>
            </div>
            <el-collapse-transition>
                <div :class="$style.content" v-if="!mini">
                    <el-table :data="fileList" stripe>
                        <!--:show-header="false"-->
                        <!-- 文件信息，空间信息， 状态信息，操作-->
                        <el-table-column label="预览" prop="name" v-slot="{row}" width="80px">
                            <file-image-col :info="row"></file-image-col>
                        </el-table-column>
                        <el-table-column label="文件" prop="name" v-slot="{row}">
                            <file-name-col :info="row"></file-name-col>
                        </el-table-column>
                        <el-table-column label="进度" prop="status" v-slot="{row}" width="200">
                            <file-percentage-col :info="row"></file-percentage-col>
                        </el-table-column>
                        <el-table-column label="操作" prop="status" v-slot="{row}" width="120">
                            <file-action-col :info="row" @clear-file="handlerClearFile"></file-action-col>
                        </el-table-column>
                    </el-table>
                </div>
            </el-collapse-transition>

            <!--<div :class="$style.content"></div>-->
            <upload-dragger @drop-file="uploadFiles"></upload-dragger>
        </div>
    </el-collapse-transition>
</template>

<script>
import {isArray} from '~/tools/lodash'
import UploadDragger from './upload-dragger'
import FileNameCol from './file-name-col'
import {UploadStatus} from './constant'
import FilePercentageCol from '@/components/upload/file-percentage-col'
import FileImageCol from '@/components/upload/file-image-col'
import FileActionCol from '@/components/upload/file-action-col'
/**
 * 文件上传的状态
 */

export default {
    name: 'upload',
    components: {FileActionCol, FileImageCol, FilePercentageCol, FileNameCol, UploadDragger},
    data() {
        return {
            fileList: [
                // {
                //     space: 'xxx',
                //     name: 'xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx',
                //     percentage: 50,
                //     status: UploadStatus.FULFIL,
                //     msg: '错误信息',
                //     loaded: 545,
                //     total: 325642 // 请求大小
                // }
            ],
            visible: true,
            mini: true,
            action: '',
            tempIndex: 1
        }
    },
    computed: {
        miniIcon() {
            return this.mini ? 'el-icon-arrow-up' : 'el-icon-arrow-down'
        },
        title () {
            return '图片上传'
        }
    },
    methods: {
        handlerClearFile (row) {
            const index = this.fileList.indexOf(row)
            if (index>=0) {
                this.fileList.splice(index, 1)
            }
        },
        handleStart (rawFile, space) {
            rawFile.uid = Date.now() + this.tempIndex++;
            let index1 = rawFile.name.lastIndexOf('.')
            let index2 = rawFile.name.length
            let ext = rawFile.name.substring(index1 + 1, index2) // 后缀名
            const source = this.$axios.CancelToken.source()


            let file = {
                space,
                status: UploadStatus.READY,
                name: rawFile.name,
                size: rawFile.size,
                uid: rawFile.uid,
                raw: rawFile,
                src: URL.createObjectURL(rawFile),
                source,
                resp: {},
                percentage: 0,
                loaded: 0,
                total: 0 // 请求大小
            }
            this.fileList.push(file)
            return file
        },
        uploadFiles(files, opts = {}) {

            const postFiles = Array.prototype.slice.call(files);
            if (postFiles.length === 0) return

            return Promise.all(postFiles.map(rawFile => this.upload(rawFile, opts)))
        },
        async upload (file, opts) {
            const {space, token} = opts
            const fileInfo = this.handleStart(file, space)
            const formData = new FormData()
            formData.append("token", token)
            formData.append("file", file)
            formData.append("x:space", space)
            // 原始文件名
            formData.append("x:remark", file.name)

            try {
                fileInfo.status = UploadStatus.UPLOADING


                const ret = await this.$axios.$post(this.action, formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    },
                    cancelToken: fileInfo.source.token,
                    onUploadProgress (e) {
                        // 上传进度信息
                        fileInfo.total = e.total
                        fileInfo.loaded = e.loaded
                        // 进度 中的total 是整个请求的，会比当前的文件多一些
                        fileInfo.percentage = e.loaded / e.total * 100
                    }
                })
                fileInfo.resp = ret
                fileInfo.status = UploadStatus.FULFIL
            } catch (e) {
                if (this.$axios.isCancel(e)) {
                    fileInfo.status = UploadStatus.CANCEL
                } else {
                    fileInfo.status = UploadStatus.ERROR
                    fileInfo.msg = e.message
                }
                // 上传失败
            }

        }
    },
    mounted () {
    }
}
</script>

<style module lang="scss">
.uploadMain {
    width: 633px;
    top: auto;
    bottom: 0;
    left: auto;
    right: 35px;
    display: block;
    visibility: visible;
    z-index: 1080; // 比 poptip 低一级
    position: absolute;
    overflow: hidden;
    box-shadow: 0 0 10px #ccc;
    border-top-left-radius: 7px;
    border-top-right-radius: 7px;
    border: 1px solid #e2e2e2;
    background-color: white;
}

.header {
    display: flex;
    padding: .5rem 1rem;

    .btn {
        margin-left: .5rem;
        font-size: 1rem;
        color: $--color-text-primary;
        cursor: pointer;
    }

    .btn:hover {
        color: black;
    }

    .title {
        flex: 1;
    }
}

.content {

}
</style>
