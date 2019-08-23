<template>
    <div :class="$style.navbar">
        <div :class="$style.hamburgerContainer" @click="toggleSidebarMini">
            <i :class="[collapsed ? 'el-icon-s-unfold' : 'el-icon-s-fold', $style.hamburger]"></i>
        </div>
        <el-breadcrumb :class="$style.breadcrumb" separator="/">
            <transition-group name="breadcrumb">
                <el-breadcrumb-item v-for="(item,index) in breadcrumbs" :key="item.title">
                    <span v-if="item.redirect==='noRedirect' || index === breadcrumbs.length - 1" :class="$style.noRedirect">{{ item.title }}</span>
                    <a v-else @click.prevent="handleClickLink(item)">{{ item.title }}</a>
                </el-breadcrumb-item>
            </transition-group>
        </el-breadcrumb>
    </div>
</template>

<script>
import { mapState, mapActions} from 'vuex'

// 简单的定义下 已知路由，这个项目的菜单项比较固定，不会有增加，
export default {
    name: 'navbar',
    data() {
        return {
            // breadcrumbs: []
        }
    },
    computed: {
        ...mapState('sidebar', ['collapsed', 'breadcrumbs']),
    },
    methods: {
        ...mapActions('sidebar', ['toggleSidebarMini']),

        handleClickLink (item) {
            console.log(item)
        }
    },
    watch: {

    },
    created() {
    }
}
</script>

<style module lang="scss">
.navbar {
    height: 50px;
    overflow: hidden;
    position: relative;
    background: #fff;
    box-shadow: 0 1px 4px rgba(0,21,41,.08);
}
.hamburgerContainer {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    padding: 0 15px;
    -webkit-tap-highlight-color:transparent;

    &:hover {
        background: rgba(0, 0, 0, .025)
    }
}
.hamburger {
    display: inline-block;
    vertical-align: middle;
    font-size: 20px;
    font-weight: 100;
}
.breadcrumb {
    display: inline-block;
    font-size: 14px;
    line-height: 50px;
    margin-left: 8px;
    .noRedirect {
        color: #97a8be;
        cursor: text;
    }
}
</style>
