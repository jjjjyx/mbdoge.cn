<template>
    <div :class="$style.main">
        <div :class="$globalStyle.pageHeader">
            <div :class="$style.pageIcon">
                <font-icon type="icon-mail"></font-icon>
            </div>
            <div :class="$style.content">
                <div :class="$globalStyle.title">
                    写文章
                </div>
                <div :class="$globalStyle.content">
                    {{pageHeaderDesc}}
                </div>
            </div>
        </div>

        <el-row :gutter="16" type="flex" style="flex: 1">
            <el-col :span="18" >
                <div class="el-card" :class="[$globalStyle.card, $style.writeWarp]">
                    <div :class="$style.titleWarp">
                        <div :class="$style.titleInput">
                            <input type="text">
                        </div>
                    </div>
                    <ul :class="$style.toolsWarp">
                        <!--<el-button @click="aa">test</el-button>-->
                        <!--不需要的按钮就不显示了 这里工具栏全部使用自定义-->
                        <li v-for="it in tools" :class="{[$style[it.float]]: it.float}" @click="handlerClickTool(it)">
                            <el-tooltip effect="dark" :content="it.tip" placement="bottom-start">
                                <a>
                                    <font-icon :type="it.icon"></font-icon>
                                </a>
                            </el-tooltip>
                        </li>
                    </ul>
                    <div :class="$style.contentWarp" ref="postContent">
                        <markdown-editor ref="md"></markdown-editor>
                    </div>
                </div>
            </el-col>
            <el-col :span="6">
                <el-card shadow="never" :class="[$globalStyle.card]">
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>
// import SimpleMDE from 'SimpleMDE'
import MarkdownEditor from '@/components/markdown-editor/index'
export default {
    components: { MarkdownEditor },
    layout: 'admin',
    name: 'write',
    async asyncData (ctx) {
        const tipMsg = [
            '今日状态不错，写下感受吧',
            '是不是有新的思路需要保存下来？',
            '今天很努力，肯定又学习到新的姿势，快快记录下来！',
            '来都来了，不写点什么吗？'
        ]
        const pageHeaderDesc = tipMsg[~~(Math.random() * tipMsg.length)]

        const tools = [
            { icon: 'icon-chexiao', name: 'undo', tip: '撤销' },
            { icon: 'icon-chexiao2', name: 'redo', tip: '还原' },
            { icon: 'icon-tupian1', name: 'insetImage', tip: '插入图片' },
            { icon: 'icon-biaoge', name: 'insetTable', tip: '插入表格' },
            { icon: 'el-icon-time', name: 'insetTable', tip: '历史版本' },
            { icon: 'el-icon-paperclip', name: 'insetTable', tip: '添加附件' },
            { icon: 'icon-shujuguanlisvg40', name: 'insetTable', tip: '插入代码' },

            { icon: 'el-icon-help', name: 'insetTable', tip: 'Markdown 语法帮助' },


            { float: 'right', icon: 'icon-zhuanzhu', name: 'toggleFullScreen', tip: '专注模式' },
            { float: 'right', icon: 'el-icon-view', name: 'insetTable', tip: '预览效果' },
            { float: 'right', icon: 'icon-baocun', name: 'insetTable', tip: '保存' },


        ]

        return { pageHeaderDesc, tools }
    },
    data () {
        return {
            // tip: ~~(Math.random() * tipMsg.length),
        }
    },
    computed: {
        // pageHeaderDesc () {
        //     return tipMsg[this.tip]
        // }
        md () {
            return this.$refs.md
        }
    },
    methods: {
        handlerClickTool (it) {
            // console.log(11, this.md)
            // let { name } = it
            // console.log(this.md.$md[name])
            // this.md.$md[name]()
        }
    },
    created () {
        // console.log(SimpleMDE)
    }
}
</script>

<style module lang="scss">
.main {
    height: 100%;
}
.writeWarp {
    height: 100%;
    margin-bottom: 0;
    display: flex;
    flex-direction: column;

    .contentWarp {
        flex: 1;
        height: 0;
        overflow: auto;
    }
    .titleWarp,
    .toolsWarp {
        flex: 0 0 auto;
    }
}
.titleWarp {
    display: flex;
    .titleInput {
        flex: 1;
        input {
            width: 100%;
            font-size: 1.4rem;
            padding: 16px 12px;
            border: none;
            outline: none;
            text-overflow: ellipsis;
            overflow: hidden;
        }

    }
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
.contentWarp {
}
.pageIcon {
    float: left;
    font-size: 60px;
    line-height: 60px;
    width: 60px;
    /*margin-right: 1rem;*/
}
.content {
    margin-left: 70px;
}
</style>
