<template>
    <div>
        <div :class="$globalStyle.pageHeader">
            <div :class="$globalStyle.title">
                文章管理
            </div>
            <div :class="$globalStyle.content">

            </div>
        </div>
    </div>
</template>

<script>
export default {
    layout: 'admin',
    name: 'post',
    meta: {
        title: 'Post',
        icon: 'el-icon-collection'
    },
    mounted () {
    }
}
</script>

<style scoped>

</style>
