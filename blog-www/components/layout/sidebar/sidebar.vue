<template>
    <div >
        <logo></logo>
        {{$style.menuText}}
        <el-scrollbar :wrap-class="$style.scrollbarWrapper">
            <!--:default-active="activeMenu"-->
            <!---->
            <el-menu
                :collapse="collapsed"
                :background-color="$style.menuBg"
                :text-color="$style.menuText"
                :active-text-color="$style.menuActiveText"
                :unique-opened="false"
                :collapse-transition="false"
                mode="vertical"
            >
                <el-submenu index="1">
                    <template slot="title">
                        <i class="el-icon-location"></i>
                        <span slot="title">导航一</span>
                    </template>
                    <el-menu-item-group>
                        <span slot="title">分组一</span>
                        <el-menu-item index="1-1">选项1</el-menu-item>
                        <el-menu-item index="1-2">选项2</el-menu-item>
                    </el-menu-item-group>
                    <el-menu-item-group title="分组2">
                        <el-menu-item index="1-3">选项3</el-menu-item>
                    </el-menu-item-group>
                    <el-submenu index="1-4">
                        <span slot="title">选项4</span>
                        <el-menu-item index="1-4-1">选项1</el-menu-item>
                    </el-submenu>
                </el-submenu>
                <el-menu-item index="2">
                    <i class="el-icon-menu"></i>
                    <span slot="title">导航二</span>
                </el-menu-item>
                <el-menu-item index="3" disabled>
                    <i class="el-icon-document"></i>
                    <span slot="title">导航三</span>
                </el-menu-item>
                <el-menu-item index="4">
                    <i class="el-icon-setting"></i>
                    <span slot="title">导航四</span>
                </el-menu-item>
                <!--<sidebar-item v-for="route in routes" :key="route.path" :item="route" :base-path="route.path" />-->
            </el-menu>
        </el-scrollbar>
    </div>
</template>

<script>
import { mapState } from 'vuex'
import Logo from './logo'
export default {
    name: 'sidebar',
    // ...mapGetters('sidebar', [
    //     'sidebar'
    // ]),
    data () {
        return {
            routes: [
                {path: 'a'},
                {path: 'b'}
            ]
        }
    },
    components: { Logo },
    computed: {
        ...mapState('sidebar', ['collapsed']),
        // routes() {
        //     return this.$router.options.routes
        // },
        // activeMenu() {
        //     const route = this.$route
        //     const { meta, path } = route
        //     // if set path, the sidebar will highlight the path you set
        //     if (meta.activeMenu) {
        //         return meta.activeMenu
        //     }
        //     return path
        // },
        // variables() {
        //     return variables
        // },
        // isCollapse() {
        //     return !this.sidebar.opened
        // }
    },
    mounted () {
        console.log(this.$router.options.routes)
    }
}
</script>

<style module lang="scss">
.scrollbarWrapper {
    overflow-x: hidden!important;
}
:export {
    menuText: $menuText;
    menuActiveText: $menuActiveText;
    subMenuActiveText: $subMenuActiveText;
    menuBg: $menuBg;
    menuHover: $menuHover;
    subMenuBg: $subMenuBg;
    subMenuHover: $subMenuHover;
    sideBarWidth: $sideBarWidth;
}
</style>
