<template>
    <div>
        category
        <el-button @click="handlerClick">test</el-button>
    </div>
</template>

<script>
export default {
    layout: 'admin',
    name: 'category',
    meta: {
        title: 'Category',
        icon: 'el-icon-collection-tag'
    },
    methods: {
        handlerClick () {
            this.$axios.$post('xxxx')
        }
    }
}
</script>

<style scoped>

</style>
