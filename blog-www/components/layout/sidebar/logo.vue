<template>
    <div :class="[$style.logoContainer, {[$style.collapse]: collapsed}]" >
        <transition name="sidebarLogoFade">
            <router-link v-if="collapsed" key="collapse" :class="$style.sidebarLogoLink" :to="{ name: '6tw9sQs-admin' }">
                <img :src="logo" :class="$style.sidebarLogo">
                <h1 :class="$style.sidebarTitle">{{ title }} </h1>
            </router-link>
            <router-link v-else key="expand" :class="$style.sidebarLogoLink" :to="{ name: '6tw9sQs-admin' }">
                <img :src="logo" :class="$style.sidebarLogo">
                <h1 :class="$style.sidebarTitle">{{ title }} </h1>
            </router-link>
        </transition>
    </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
    name: 'logo',
    data() {
        return {
            title: 'mbdoge.cn',
            logo: 'https://image.cdn.mbdoge.cn/12f2aa30-3174-11e7-a772-3734af15197f.png'
        }
    },
    computed: {
        ...mapState('sidebar', ['collapsed']),
    }
}
</script>
<style scoped>
.sidebarLogoFade-enter-active {
    transition: opacity 1.5s;
}
.sidebarLogoFade-enter,
.sidebarLogoFade-leave-to {
    opacity: 0;
}
</style>
<style module lang="scss">
.logoContainer {
    position: relative;
    width: 100%;
    height: 50px;
    line-height: 50px;
    background: #2b2f3a;
    text-align: center;
    overflow: hidden;
}
.sidebarLogoLink {
    display: inline-block;
    overflow: hidden;
    height: 100%;
    width: 100%;
}
.sidebarLogo {
    width: 32px;
    height: 32px;
    vertical-align: middle;
    margin-right: 12px;
}
.sidebarTitle {
    display: inline-block;
    margin: 0;
    color: #fff;
    font-weight: 600;
    line-height: 50px;
    font-size: 14px;
    font-family: Avenir, Helvetica Neue, Arial, Helvetica, sans-serif;
    vertical-align: middle;
}
.collapse {
    .sidebarLogo {
        margin-right: 0;
    }
}
</style>
