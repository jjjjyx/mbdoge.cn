<template>
    <div>
        <!--    发布：发布于-->
        <!--    草稿：最后编辑-->
        <!--    待处理： - -->
        <el-tag :type="statusType" size="mini" v-text="statusText"></el-tag>
        <!--<span v-text="statusText"></span>-->
        <span :class="$style.desc" v-text="statusDesc"></span>
    </div>
</template>

<script>
import {ArticleStatus} from "@/tools/enum"
import {datetimeFormat} from "@/tools/time";

export default {
	name: "article-status-col",
    props: {
	    status: String,
        updatedAt: Number,
        postAt: Number
    },
    computed: {
        statusText () {
            return ArticleStatus[this.status].label
        },
        statusType () {
            return ArticleStatus[this.status].color
        },
        statusDesc () {
            const status = ArticleStatus[this.status]
            if (!status) return ''
            switch (status) {
                case ArticleStatus.DRAFT:
                    return '编辑于：' + datetimeFormat(this.updatedAt)
                case ArticleStatus.PUBLISH:
                    return '发布于：' + datetimeFormat(this.postAt)
            }
            return ''
        }
    },
    methods: {

    }
}
</script>

<style lang="scss" module>
.desc {
    display: block;
    font-style: italic;
    line-height: 1;
}
</style>
