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
                            <input type="text" :placeholder="$t('admin.post.new.editor.title')">
                        </div>
                    </div>
                    <markdown-editor :class="[$style.editorWarp]"></markdown-editor>
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
// import SimpleMDE from 'SimpleMDE'

import MarkdownEditor from "~/components/markdown-editor/markdown-editor";

export default {
    components: {MarkdownEditor},
    layout: 'admin',
    name: 'new-post',
    async asyncData(ctx) {
        const tipMsg = [
            '今日状态不错，写下感受吧',
            '是不是有新的思路需要保存下来？',
            '今天很努力，肯定又学习到新的姿势，快快记录下来！',
            '来都来了，不写点什么吗？'
        ]
        const pageHeaderDesc = tipMsg[~~(Math.random() * tipMsg.length)]


        return {pageHeaderDesc}
    },
    data() {
        return {
            // tip: ~~(Math.random() * tipMsg.length),
        }
    },
    computed: {
        // pageHeaderDesc () {
        //     return tipMsg[this.tip]
        // }
    },
    methods: {},
    created() {
        // console.log(SimpleMDE)
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

}
</style>
