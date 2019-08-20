<template>
    <div :class="wrapperClasses" class="app-wrapper">
        <!--<div v-if="device==='mobile'&&sidebar.opened" class="drawer-bg" @click="handleClickOutside" />-->
        <!--<sidebar class="sidebar-container" />-->
        <sidebar :class="$style.sidebarContainer"></sidebar>
        <div :class="$style.mainContainer">
            <el-button @click="toggleSidebarMini">aa</el-button>
            <!--<div :class="{'fixed-header':fixedHeader}">-->
                <!--<navbar />-->
            <!--</div>-->
            <!--<app-main />-->
        </div>
    </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import Sidebar from '@/components/layout/sidebar/sidebar'
export default {
    components: { Sidebar },
    layout: 'admin',
    name: 'admin',
    scrollToTop: true,
    fetch ({ store, redirect, route }) {
        if (!store.getters['user/isLogin']) {
            return redirect({ name: '6tw9sQs-login'})
        }
    },
    computed: {
        ...mapState('sidebar', ['collapsed']),
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
}
</style>
