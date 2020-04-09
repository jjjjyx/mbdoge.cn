<template>
<div id="app" :class="wrapperClasses">
    <div v-if="device === 'mobile' && !sidebar.collapsed" :class="$style.drawerBg" @click="handleClickOutside"></div>
    <sidebar :class="$style.sidebarContainer"/>
    <div :class="[$style.mainContainer, {[$style.hasTagsView]: showTagsView, [$style.hasFooter]: showFooter}]">
        <div :class="{[$style.fixedHeader]: fixedHeader}">
            <navbar/>
            <tags-view v-if="showTagsView"></tags-view>
        </div>
        <div :class="$style.fun"></div>
        <!--<section :class="$style.appMain" >-->
            <!--<transition name="fade-transform" mode="out-in">-->
                <!--<keep-alive :include="cachedViews">-->
        <nuxt :nuxt-child-key="$route.path" :class="$style.appMain" :style="mainStyles"/>
                    <!--<nuxt-child :key="$route.fullPath"></nuxt-child>-->
                <!--</keep-alive>-->
            <!--</transition>-->
        <!--</section>-->
        <footer :class="$style.footer" v-if="showFooter">
            <div :class="$style.copyright">{{copyright}}</div> {{aa}}
        </footer>
    </div>
</div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import Sidebar from './components/sidebar'
import Navbar from './components/navbar'
import TagsView from './components/tags-view'
import ResizeMixin from './components/mixin/resize-handler'
import conf from '~/config'

export default {
    name: 'admin',
    middleware: ['auth'],
    components: {
        TagsView,
        Navbar,
        Sidebar
    },
    // layout 中不支持 asyncData  fetch
    // async asyncData () {
    //     return { aa: 'bbb'}
    // },
    // fetch ({ store, redirect, route }) {
    //     console.log('store.getters=', store.getters['user/isLogin'])
    //     if (!store.getters['user/isLogin']) {
    //         return redirect({ name: 'login'})
    //     }
    // },
    mixins: [ResizeMixin],
    data () {
        return {
            copyright: conf.copyright,
            aa: ''
        }
    },
    computed: {
        ...mapState('app', ['sidebar', 'device', 'fixedHeader', 'showTagsView', 'cachedViews', 'showFooter']),
        wrapperClasses () {
            return [this.$style.appWrapper, {
                [this.$style.collapsed]: this.sidebar.collapsed,
                [this.$style.withoutAnimation]: this.sidebar.withoutAnimation,
                [this.$style.mobile]: this.device === 'mobile'
            }]
        },
        mainStyles () {
            let height = parseInt(this.$style.navHeight)
            if (this.showTagsView) {
                height += parseInt(this.$style.tagViewHeight)
            }
            if (this.showFooter) {
                height += parseInt(this.$style.footerHeight)
            }
            return {
                'min-height': `calc(100vh - ${height}px)`
            }
        }
    },
    methods: {
        ...mapActions('app', ['closeSideBar']),
        handleClickOutside () {
            this.closeSideBar({ withoutAnimation: false })
        }
    }
}
</script>
<style>
.el-menu-item i {
    color: inherit;
}
</style>
<style lang="scss">
#app, html {
    height: 100%;
}
@import "../assets/sass/reboot.scss";
@import "../assets/sass/element-ui.scss";
</style>
<style module lang="scss">
@import "../assets/sass/mixin";

$navHeight: 50px;
$tagViewHeight: 34px;
$footerHeight: 64px;

:export {
    navHeight: $navHeight;
    tagViewHeight: $tagViewHeight;
    footerHeight: $footerHeight;
}

.appWrapper {
    @include clearfix;
    position: relative;
    height: 100%;
    width: 100%;
    &.mobile.openSidebar {
        position: fixed;
        top: 0;
    }
}
.mainContainer {
    min-height: 100%;
    transition: margin-left .28s;
    margin-left: $sideBarWidth;
    position: relative;
    background-color: #f5f7f9;
    .appMain {
        /*min-height: calc(100vh - 50px);*/
        padding: 16px;
        width: 100%;
        position: relative;
        overflow: hidden;
        display: flex;
        flex-direction: column;
    }
}

.sidebarContainer {
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

    a {
        display: inline-block;
        width: 100%;
        overflow: hidden;
    }

    :global {
        // reset element-ui css
        .horizontal-collapse-transition {
            transition: 0s width ease-in-out, 0s padding-left ease-in-out, 0s padding-right ease-in-out;
        }

        .el-scrollbar__bar.is-vertical {
            right: 0px;
        }

        .el-scrollbar {
            height: 100%;
        }
        .el-menu {
            border: none;
            height: 100%;
            width: 100% !important;
        }
        .el-menu-item .svg-icon, .el-menu-item [class^=el-icon-] {
            margin-right: 16px;
            font-size: 1rem;
            width: 1rem;
            /*width: auto;*/
        }
        .is-active > .el-submenu__title {
            color: $subMenuActiveText !important;
        }
        .el-submenu .el-menu-item {
            min-width: $sideBarWidth !important;
            background-color: $subMenuBg !important;

            &:hover {
                background-color: $subMenuHover !important;
            }
        }
    }
}

.drawerBg {
    background: #000;
    opacity: 0.3;
    width: 100%;
    top: 0;
    height: 100%;
    position: absolute;
    z-index: 999;
}

.fixedHeader {
    position: fixed;
    top: 0;
    right: 0;
    z-index: 9;
    width: calc(100% - #{$sideBarWidth});
    transition: width 0.28s;
}
.fixedHeader + .fun {
    margin-top: 50px;
}
// fix css style bug in open el-dialog
:global(.el-popup-parent--hidden) {
    .fixedHeader {
        padding-right: 15px;
    }
}
.collapsed {
    .sidebarContainer {
        width: 54px !important;
        :global {
            .el-menu--collapse .el-submenu__title > span {
                height: 0;
                width: 0;
                overflow: hidden;
                visibility: hidden;
                display: inline-block;
            }
            .el-tooltip {
                padding: 0 !important;
                text-align: center;
                .svg-icon {
                    margin-left: 20px;
                }
            }
        }
    }
    .mainContainer {
        margin-left: 54px;
    }

    .fixedHeader {
        width: calc(100% - 54px)
    }
}
// mobile responsive

.mobile {
    .fixedHeader {
        width: 100%;
    }
    .sidebarContainer {
        transition-duration: 0.3s;
        transition: transform .28s;
    }
    &.collapsed .sidebarContainer{
        width: $sideBarWidth !important;
        pointer-events: none;
        transform: translate3d(-$sideBarWidth, 0, 0);
    }
    .mainContainer {
        margin-left: 0;
    }
}
.withoutAnimation {
    .mainContainer, .sidebarContainer {
        transition: none !important;
    }
}

.hasTagsView {
    $height: $tagViewHeight + $navHeight;
    .fixedHeader + .fun {
        margin-top: $height
    }
}
.hasFooter {

}

.footer {
    padding: 24px 16px;
    text-align: center;

    .copyright {
        color: #808695;
        font-size: 14px;
    }
}
</style>
