<template>
    <div>
        <el-tag effect="dark" closable class="mr-1" size="mini" v-for="(tag, index) in filterTags" :key="index" @close="handleClose(index, tag)">{{tag}}</el-tag>
        <span v-if="tags.length > 5">...</span>
    </div>
</template>

<script>
export default {
	name: "article-tags-col",
    props: {
	    id: [String, Number],
        tags: Array
    },
    computed: {
	    filterTags () {
	        // 只显示3个
            return this.tags.slice(0, 5)
        }
    },
    methods: {
        handleClose (index, tag) {
            // this.tags.slice(index, 1)
            this.tags.splice(index, 1)
            try {
                this.$axios.$patch(`/article/${this.id}/tags`, {tags: this.tags})
                this.$message.success(`删除标签 【${tag}】`)
            } catch (e) {
                this.tags.splice(index, 0, tag)
            } finally {

            }
        }
    }
}
</script>
