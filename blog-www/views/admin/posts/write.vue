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

                    </div>
                    <div :class="$style.toolsWarp"></div>
                    <div :class="$style.contentWarp"></div>
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

export default {
    layout: 'admin',
    name: 'write',
    async asyncData (ctx) {
        const tipMsg = [
            '今日状态不错，写下感受吧',
            '是不是有新的思路需要保存下来？',
            '今天很努力，肯定又学习到新的姿势，快快记录下来！',
            '来都来了，不写点什么吗？'
        ]
        let pageHeaderDesc = tipMsg[~~(Math.random() * tipMsg.length)]
        return { pageHeaderDesc }
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
    }
    .titleWarp,
    .toolsWarp {
        flex: 0 0 auto;
    }
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
