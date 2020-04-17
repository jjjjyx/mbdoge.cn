<template>
    <div :class="$style.main">
        <div :class="$globalStyle.pageHeader">
            <div :class="$style.pageIcon">
                <font-icon type="icon-mail"></font-icon>
            </div>
            <div :class="$style.content">
                <div :class="$globalStyle.title">
                    {{$t('admin.post.new.title')}}
                </div>
                <div :class="$globalStyle.content">
                    {{pageHeaderDesc}}
                </div>
            </div>
        </div>

        <el-row :gutter="16" type="flex" style="flex: 1">
            <el-col :span="18">
                <div :class="[$globalStyle.card, $style.editorContent]">
                    <div :class="$style.titleWarp">
                        <div :class="$style.titleInput">
                            <input v-model="articleTitle" type="text" :placeholder="$t('admin.post.new.editor.title')">
                        </div>
                    </div>
                    <client-only>
                        <editor ref="editor" :class="[$style.editorWarp]" height="100%" :on-save="save"></editor>
                    </client-only>
                </div>

            </el-col>
            <el-col :span="6">
                <el-card shadow="never" :class="[$globalStyle.card]">
                    222
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>

import { dateFormat } from "~/tools/time";

export default {
    layout: 'admin',
    head() {
        return {
            link: [
                {
                    rel: 'stylesheet', href: "//cdnjs.cloudflare.com/ajax/libs/github-markdown-css/4.0.0/github-markdown.min.css"
                }
            ]
        }
    },
    async asyncData(ctx) {
        const tipMsg = [
            '今日状态不错，写下感受吧',
            '是不是有新的思路需要保存下来？',
            '今天很努力，肯定又学习到新的姿势，快快记录下来！',
            '来都来了，不写点什么吗？'
        ]
        const pageHeaderDesc = tipMsg[~~(Math.random() * tipMsg.length)]
        const descRanges = ['有感', '心得', '草稿']


        const timeLabel = (
            (timeRanges) => {
                const hour = new Date().getHours()
                if(hour < 6) {return '凌晨'}
                else if (hour < 9){return '早上'}
                else if (hour < 12){return '上午'}
                else if (hour < 14){return '中午'}
                else if (hour < 17){return '下午'}
                else if (hour < 19){return '傍晚'}
                else if (hour < 22){return '晚上'}
                else {return '夜里'}
            }
        ) (['深夜', '早晨', '深夜', '上午', '中午', '下午', '傍晚', '晚上'])
        // YY-MM-dd [深夜|下午|上午] [有感|心得|记|草稿]
        const articleTitle = `${dateFormat()} ${timeLabel}${descRanges[~~(Math.random() * descRanges.length)]}`
        return {pageHeaderDesc, articleTitle}
    },
    name: 'new-post',
    data() {
        return {

        }
    },
    components: {
        editor: () => {
            return process.client ? import('~/components/markdown-editor/markdown-editor').then((m) => m.default) : Promise.resolve({ render: (h) => h('editor') })
        }
    },
    computed: {
        // pageHeaderDesc () {
        //     return tipMsg[this.tip]
        // }
    },
    methods: {
        save (value) {
            // console.log(value)
            // 保存文章，

        }
    },
    mounted() {

    },
    async created() {

    }
}
</script>

<style module lang="scss">
.main {
    height: 100%;
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
.editorContent {
    height: 100%;
    margin-bottom: 0;
    display: flex;
    flex-direction: column;
    overflow: auto;
}
.titleWarp {
    flex: 0 0 auto;
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
.editorWarp {
    height: 100%;
    margin-bottom: 0;
    display: flex;
    flex-direction: column;

    /*.contentWarp {*/
    /*    flex: 1;*/
    /*    height: 0;*/
    /*    overflow: auto;*/
    /*}*/
    :global(.te-preview) {
        background-color: #fffef9;
    }

}
</style>
