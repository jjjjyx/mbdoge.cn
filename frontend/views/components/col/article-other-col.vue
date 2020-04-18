<template>
    <div :class="$style.col">
        <p>
            <span>评论：</span> <el-tag size="mini" effect="plain" :type="commentStatusType">{{commentStatusText}}</el-tag>
            <!--<span v-text="commentStatusText"></span>-->
        </p>
        <p>
            <!-- 上 3张-->
            <span>封面：</span> <span class="font-italic" v-text="coverText"></span>
        </p>
    </div>
</template>

<script>
import {ArticleCommentStatus, ArticleCoverPosition} from "@/tools/enum";

export default {
	name: "article-other-col",
    props: {
        commentStatus: String,
        attribute: {
            type: Object,
            default: () => ({})
        }
    },
    computed: {
        commentStatusText () {
            const status = ArticleCommentStatus[this.commentStatus]
            return status ? status.label : ''
        },
        commentStatusType () {
            const status = ArticleCommentStatus[this.commentStatus]
            return status ? status.color : ''
        },
        coverText () {
            const { covers = null, coverPosition = 1 } = this.attribute || {}
            if (!covers) {
                return '暂无封面'
            }
            const position = ArticleCoverPosition[coverPosition] || ArticleCoverPosition["1"]
            return `${position.label} ${covers.length}张`

        }
    }
}
</script>

<style module lang="scss">
.col {
    p {
        margin: .25rem 0;
        line-height: 1;
    }
    :global(.el-tag) {
        margin-right: 0;
    }
}
</style>
