<template>
<div id="app">
    <nuxt />
</div>
</template>

<script>
export default {
    name: 'admin'
}
</script>

<style lang="scss">
@import "../assets/sass/reboot.scss";
#app, html {
    height: 100%;
}

/* breadcrumb transition */
.breadcrumb-enter-active,
.breadcrumb-leave-active {
    transition: all .5s;
}

.breadcrumb-enter,
.breadcrumb-leave-active {
    opacity: 0;
    transform: translateX(20px);
}

.breadcrumb-move {
    transition: all .5s;
}

.breadcrumb-leave-active {
    position: absolute;
}
</style>
