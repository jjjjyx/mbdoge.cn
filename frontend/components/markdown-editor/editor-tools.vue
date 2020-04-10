<template>
    <ul :class="$style.toolsWarp">
        <!--<el-button @click="aa">test</el-button>-->
        <!--不需要的按钮就不显示了 这里工具栏全部使用自定义-->
        <li v-once v-for="it in tools" :class="{[$style[it.float]]: it.float}" @click="handlerClickTool(it)">
            <el-tooltip effect="dark" :content="it.tip" placement="bottom-start" :open-delay="500">
                <a>
                    <font-icon :type="it.icon"></font-icon>
                </a>
            </el-tooltip>
        </li>
    </ul>
</template>

<script>

const TOOLS_HANDLER = {
    insetTable (editor) {
        console.log(this, editor)

    },
    openSelectImageDialog() {
        // 支持粘贴上传
        // 支持本地媒体图片库搜索
        // 支持输入远程图片地址，转换为本地图片库添加
        // 需要一个全局上传图片功能，以及全局选择图片接口
        this.$openImageSelectModal()
        console.log(this.$openImageSelectModal())
    }
}

export default {
	name: "editor-tools",
    data () {
        return {
            tools: [
                {icon: 'icon-chexiao', name: 'undo', tip: '撤销'},
                {icon: 'icon-chexiao2', name: 'redo', tip: '还原'},
                {icon: 'icon-tupian1', name: 'openSelectImageDialog', tip: '插入图片'},
                {icon: 'icon-biaoge', name: 'insetTable', tip: '插入表格'},
                {icon: 'el-icon-time', name: 'history', tip: '历史版本'},
                {icon: 'el-icon-paperclip', name: 'adjunct', tip: '添加附件'},
                {icon: 'icon-shujuguanlisvg40', name: 'insetCode', tip: '插入代码'},

                {icon: 'el-icon-help', name: 'help', tip: 'Markdown 语法帮助'},


                {float: 'right', icon: 'icon-zhuanzhu', name: 'toggleFullScreen', tip: '专注模式'},
                {float: 'right', icon: 'el-icon-view', name: 'preview', tip: '预览效果'},
                {float: 'right', icon: 'icon-baocun', name: 'save', tip: '保存'},
            ]
        }
    },
    // inject: ['editor'],
    methods: {
        handlerClickTool(it) {
            // console.log(11, this.md)
            // todo 实现工具栏中按钮功能
            const {name} = it
            let fn = TOOLS_HANDLER[name]
            if (typeof fn === 'function') {
                fn.call(this, this.$parent.editor)
            }
        },
    }
}
</script>

<style module lang="scss">
.toolsWarp {
    flex: 0 0 auto;
}
.toolsWarp {
    height: 40px;
    background-color: $--color-info-light;
    margin: 0;
    padding: 0;

    li {
        display: inline-block;
        cursor: pointer;
        text-align: center;
        font-size: 1rem;
    }

    li a {
        color: #595959;
        padding: 11px 14px;
        display: inline-block;
        line-height: 17px;
        font-size: 16px;
        /*transition: all .s linear;*/
    }

    li a:hover {
        color: #f2f2f2 !important;
        background-color: #595959;
    }

    li.right {
        float: right;
    }
}
</style>
