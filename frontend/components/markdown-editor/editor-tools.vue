<template>
    <ul :class="$style.toolsWarp">
<!--        @click="handlerClickTool(it)"-->
        <li v-once v-for="it in tools" :class="{[$style[it.float]]: it.float}" >
            <tools-btn :icon="it.icon" :name="it.name" :custom="it.custom" :tip="it.tip"></tools-btn>
        </li>

    </ul>
</template>

<script>
import editor from "./editor.js";
import ToolsInsetTable from "./tools/tools-insetTable";

export default {
    name: "editor-tools",
    data() {
        return {
            tools: [
                {icon: 'icon-chexiao', name: 'undo', tip: '撤销'},
                {icon: 'icon-chexiao2', name: 'redo', tip: '还原'},
                {icon: 'icon-tupian1', name: 'openSelectImageDialog', tip: '插入图片'},
                {icon: 'icon-biaoge', name: 'insetTable', tip: '插入表格', custom: true},
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
    components: {
        ToolsInsetTable,
        toolsBtn: {
            functional: true,
            props: {
                icon: String,
                name: String,
                tip: String,
                custom: Boolean
            },
            render(h, ctx) {
                const {icon, name, custom, tip} = ctx.props
                const param = {
                    on: {},
                    directives: [],
                    ref: `tools-${name}`
                }
                let $popover
                if (custom) {
                    $popover = h('el-popover', {
                        ref: `${name}popover`,
                        props: {
                            placement: 'bottom-start',
                            width: 200
                        }
                    }, [h(`tools-${name}`)])

                    param.directives.push({
                        name: 'popover',
                        arg: `${name}popover`
                    })
                    // console.log('name = ', name, $popover)
                } else {
                    let fn = editor[name]
                    if (typeof fn === 'function') {
                        param['on']['click'] = fn.bind(editor)
                    }
                }
                const $icon = h('font-icon', {props: {type: icon}})
                const $a = h('a', param, [$icon])

                const $tip = h('el-tooltip', {
                    props: {
                        effect: 'dark',
                        content: tip,
                        placement: 'bottom-start',
                        'open-delay': 500
                    }
                }, [$a])
                return [$popover, $tip]
            }
        }
    },
    methods: {
        handler () {
            // console.log(this.editor)
        },
        handlerClickTool(it) {
            // todo 实现工具栏中按钮功能
            const {name} = it
            let fn = TOOLS_HANDLER[name]
            if (typeof fn === 'function') {
                if (!this.$parent.ready) {
                    return
                }
                fn.call(this, this.$parent.$md)
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
