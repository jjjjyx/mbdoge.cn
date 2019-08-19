<template>
    <div>
        后台管理
    </div>
</template>

<script>
export default {
    layout: 'admin',
    name: 'admin',
    scrollToTop: true,
    fetch ({ store, redirect, route }) {
        if (!store.getters['user/isLogin']) {
            return redirect('./login?redirect=' + route.path)
        }
    }
}
</script>

<style scoped>

</style>
