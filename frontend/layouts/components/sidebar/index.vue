<template>
    <div :class="$style.hasLogo">
        <logo :collapse="isCollapse"/>
        <el-scrollbar :wrap-class="$style.scrollbarWrapper">
            <el-menu
                :class="$style.menu"
                :collapse="sidebar.collapsed"
                :background-color="$style.menuBg"
                :text-color="$style.menuText"
                :active-text-color="$style.menuActiveText"
                :unique-opened="false"
                :collapse-transition="false"
                mode="vertical"
                router
            >
                <el-menu-item index="dashboard" :route="{ name : 'dashboard' }">
                    <!--<i class="el-icon-collection"></i>-->
                    <font-icon type="icon-dashboard"></font-icon>
                    <span slot="title">{{$t('route.dashboard')}}</span>
                </el-menu-item>
                <el-menu-item index="post" :route="{ name : 'post-manage' }">
                    <i class="el-icon-collection"></i>
                    <span slot="title">{{$t('route.post')}}</span>
                </el-menu-item>
                <el-menu-item index="category" :route="{ name : 'category-manage' }">
                    <i class="el-icon-collection-tag"></i>
                    <span slot="title">{{$t('route.category')}}</span>
                </el-menu-item>
                <el-menu-item index="2" :route="{ name : 'media-manage' }">
                    <i class="el-icon-picture-outline"></i>
                    <span slot="title">{{$t('route.media')}}</span>
                </el-menu-item>

                <el-menu-item index="2" :route="{ name : 'test-vue' }">
                    <i class="el-icon-picture-outline"></i>
                    <span slot="title">{{$t('route.test')}}</span>
                </el-menu-item>

                <!--<el-submenu index="1">-->
                <!--<template slot="title">-->
                <!--<i class="el-icon-location"></i>-->
                <!--<span slot="title">导航一</span>-->
                <!--</template>-->
                <!--<el-menu-item-group>-->
                <!--<span slot="title">分组一</span>-->
                <!--<el-menu-item index="1-1">选项1</el-menu-item>-->
                <!--<el-menu-item index="1-2">选项2</el-menu-item>-->
                <!--</el-menu-item-group>-->
                <!--<el-menu-item-group title="分组2">-->
                <!--<el-menu-item index="1-3">选项3</el-menu-item>-->
                <!--</el-menu-item-group>-->
                <!--<el-submenu index="1-4">-->
                <!--<span slot="title">选项4</span>-->
                <!--<el-menu-item index="1-4-1">选项1</el-menu-item>-->
                <!--</el-submenu>-->
                <!--</el-submenu>-->

                <!--<el-menu-item index="3" disabled>-->
                <!--<i class="el-icon-document"></i>-->
                <!--<span slot="title">导航三</span>-->
                <!--</el-menu-item>-->
                <!--<el-menu-item index="4">-->
                <!--<i class="el-icon-setting"></i>-->
                <!--<span slot="title">导航四</span>-->
                <!--</el-menu-item>-->
                <!--<sidebar-item v-for="route in routes" :key="route.path" :item="route" :base-path="route.path" />-->
            </el-menu>
        </el-scrollbar>
    </div>
</template>

<script>
import { mapState } from 'vuex'
import Logo from './sidebar-logo'
import SidebarItem from './sidebar-item'
// import variables from '@/assets/sass/_var.scss'

export default {
    components: { SidebarItem, Logo },
    computed: {
        ...mapState('app', ['sidebar']),
        isCollapse () {
            return this.sidebar.collapsed
        }
    }
}
</script>
<style module lang="scss">
.hasLogo {
    :global(.el-scrollbar) {
        height: calc(100% - 50px) !important;
    }
}
.scrollbarWrapper {
    overflow-x: hidden !important;
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
