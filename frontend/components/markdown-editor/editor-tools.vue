<template>
    <ul :class="$style.toolsWarp">
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
                const self = ctx.parent
                const param = {
                    on: {},
                    domProps: {
                        href: 'javascript:void(0);'
                    },
                    directives: [],
                    ref: `tools-${name}`
                }
                let $popover
                if (custom) {
                    $popover = h('el-popover', {
                        ref: `${name}popover`,
                        props: {
                            placement: 'bottom-start',
                            'visible-arrow': false,
                            'popper-class': ctx.parent.$style.toolsPanel
                            // width: 200
                        },
                        on: {
                            show: () => {
                                // 将按钮设置为按下
                                self.$refs[`tools-${name}`].classList.add(self.$style.toolsActive)
                            },
                            hide: () => {
                                self.$refs[`tools-${name}`].classList.remove(self.$style.toolsActive)
                            }
                        }
                    }, [h(`tools-${name}`)])

                    param.directives.push({
                        name: 'popover',
                        arg: `${name}popover`
                    })
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
                        'open-delay': 500,
                        'hide-after': 2000
                    }
                }, [$a])
                return [$popover, $tip]
            }
        }
    },
    methods: {
    }
}
</script>

<style module lang="scss">

</style>
