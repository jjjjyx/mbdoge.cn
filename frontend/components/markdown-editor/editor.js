import Vue from 'vue'
import 'SimpleMDE/dist/simplemde.min.css'


class Editor {

    constructor() {
        this.ready = false
        this.$md = null;
    }

    async initialize (el) {
        if (this.ready) return
        const SimpleMDE = await import(/* webpackChunkName: "SimpleMDE" */'SimpleMDE').then((m) => m.default || m)

        const configs = {
            element: el,
            status: false,
            toolbar: [],
            autoDownloadFontAwesome: false,
            spellChecker: false // 禁用拼写检查
        }

        this.$md = new SimpleMDE(configs)

        // fix:
        this.$md.toolbarElements = {
            fullscreen: {
                className: ''
            }
        }

        this.ready = true
    }

    undo(editor) {
        this.$md.undo()
    }

    redo(editor) {
        this.$md.redo()
    }

    drawTable([cell, row]) {
        // console.log(this, editor)
        // 弹出选框
        console.log([cell, row])
        this.$md.drawTable()
    }

    preview () {
        this.$md.togglePreview()
    }

    openSelectImageDialog() {
        // 支持粘贴上传
        // 支持本地媒体图片库搜索
        // 支持输入远程图片地址，转换为本地图片库添加
        // 需要一个全局上传图片功能，以及全局选择图片接口
        Vue.prototype.$openImageSelectModal()
        // this.$openImageSelectModal()
        // console.log(this.$openImageSelectModal())
    }
}

const editor = new Editor();

export default editor;
