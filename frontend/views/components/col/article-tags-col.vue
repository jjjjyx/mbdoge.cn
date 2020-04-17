<template>
    <div>
        <el-tag closable class="mr-1" size="mini" v-for="(tag, index) in tags" :key="index" @close="handleClose(index, tag)">{{tag}}</el-tag>
    </div>
</template>

<script>
export default {
	name: "article-tags-col",
    props: {
	    id: [String, Number],
        tags: Array
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

<style scoped>

</style>
