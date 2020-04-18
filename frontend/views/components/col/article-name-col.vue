<template>
    <div :class="$style.col">
        <el-tooltip placement="top" :content="title">
            <div :class="$style.title">
                <el-tag size="mini" effect="plain" type="danger" v-if="isFixedTop">荐</el-tag>
                <el-tag size="mini" effect="plain" type="success" v-if="isReproduce">转</el-tag>
                {{titleText}}
            </div>
        </el-tooltip>
        <p :class="$style.contentPreview">文章内容的预览，这个功能展示还没有，以后会有...</p>
    </div>
</template>

<script>
export default {
	name: "article-name-col",
    props: {
	    title: String,
        type: String,
        shortChain: String,
        attribute: {
	        type: Object,
            default () {
	            return {}
            }
        }
    },
    computed: {
	    // 置顶
	    isFixedTop () {
	        return this.attribute ? this.attribute.fixedTop : false
        },
        isReproduce () {
            return this.type === 'REPRODUCE'
        },
        titleText () {
	        // 显示 20字，超过 去掉中间
            if (this.title.length < 20) return this.title
            const prefix = this.title.substr(0, 15)
            const suffix = this.title.substr(this.title.length - 4)
            return `${prefix}...${suffix}`
        }
    }
}
</script>

<style module lang="scss">
.col {
}
.title {
    color: black;
    font-size: .9rem;
    /*display: flex;*/
    line-height:1.6;
    :global(.el-tag) {
        margin-right: .25rem;
    }
}
.contentPreview {
    line-height: 1;
    margin: .25rem 0 0;
}
</style>
