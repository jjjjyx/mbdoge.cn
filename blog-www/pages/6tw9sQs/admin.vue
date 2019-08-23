<template>
    <div :class="wrapperClasses" class="app-wrapper">
        <!--<div v-if="device==='mobile'&&sidebar.opened" class="drawer-bg" @click="handleClickOutside" />-->
        <!--<sidebar class="sidebar-container" />-->
        <sidebar :class="$style.sidebarContainer"></sidebar>
        <div :class="$style.mainContainer">
            <!--<el-button @click="toggleSidebarMini">aa</el-button>-->
            <div :class="{[$style.fixedHeader]: fixedHeader}">
                <navbar></navbar>
            </div>
            <section :class="$style.main">
                <transition name="fade-transform" mode="out-in">
                    <nuxt-child></nuxt-child>
                </transition>
            </section>
        </div>
    </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import Sidebar from '~/components/layout/sidebar/sidebar'
import Navbar from '~/components/layout/navbar'
export default {
    components: { Navbar, Sidebar },
    layout: 'admin',
    name: 'admin',
    scrollToTop: true,
    head () {
        return {
            title: '后台管理系统',
        }
    },
    meta: {
        title: 'Dashboard',
        icon: 'dashboard'
    },
    fetch ({ store, redirect, route }) {
        if (!store.getters['user/isLogin']) {
            return redirect({ name: '6tw9sQs-login'})
        }
        if (route.name === '6tw9sQs-admin') {
            return redirect({ name: '6tw9sQs-admin-dashboard'})
        }
    },
    computed: {
        ...mapState('sidebar', ['collapsed', 'fixedHeader']),
        // sidebar() {
        //     return this.$store.state.app.sidebar
        // },
        // device() {
        //     return this.$store.state.app.device
        // },
        // fixedHeader() {
        //     return this.$store.state.settings.fixedHeader
        // },
        wrapperClasses() {
            return {
                [this.$style.collapsed]: this.collapsed,
                // [this.$style.openSidebar]: this.sidebar.opened,
                // [this.$style.withoutAnimation]: this.sidebar.withoutAnimation,
                // [this.$style.mobile]: this.device === 'mobile'
            }
        }
    },
    methods: {
        ...mapActions('sidebar', ['toggleSidebarMini'])
    },
}
</script>
<style>
.el-menu-item i {
    color: inherit;
}
</style>
<style module lang="scss">
.mainContainer {
    min-height: 100vh;
    transition: margin-left .28s;
    margin-left: $sideBarWidth;
    position: relative;
}

.sidebarContainer{
    transition: width 0.28s;
    width: $sideBarWidth !important;
    background-color: $menuBg;
    height: 100%;
    position: fixed;
    font-size: 0px;
    top: 0;
    bottom: 0;
    left: 0;
    z-index: 1001;
    overflow: hidden;
}

.collapsed {
    .sidebarContainer {
        width: 54px !important;
    }

    .mainContainer {
        margin-left: 54px;
    }
    :global(.el-tooltip) {
        padding: 0 !important;

        :global(i[class*='el-icon']) {
            margin-left: 14px;
        }
    }
}
.fixedHeader {
    position: fixed;
    top: 0;
    right: 0;
    z-index: 9;
    width: calc(100% - #{$sideBarWidth});
    transition: width 0.28s;
}
.main {
    min-height: calc(100vh - 50px);
    width: 100%;
    position: relative;
    overflow: hidden;
}
.fixedHeader + .main{
    padding-top: 50px;
}
</style>
