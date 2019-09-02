<template>
    <div :class="$style.navbar">
        <hamburger :is-active="!sidebar.collapsed" :class="$style.hamburgerContainer" @toggleClick="toggleSideBar"/>

        <breadcrumb :class="$style.breadcrumbContainer"/>

        <div :class="$style.rightMenu">
            <el-dropdown :class="$style.avatarContainer" trigger="click">
                <div :class="$style.avatarWrapper">
                    <img src="http://iph.href.lu/80x80?text=avatar" :class="$style.userAvatar">
                    <i class="el-icon-caret-bottom"></i>
                </div>
                <el-dropdown-menu slot="dropdown" :class="$style.userDropdown">
                    <router-link to="/">
                        <el-dropdown-item>
                            Home
                        </el-dropdown-item>
                    </router-link>
                    <!--<a target="_blank" href="https://github.com/PanJiaChen/vue-admin-template/">-->
                        <!--<el-dropdown-item>Github</el-dropdown-item>-->
                    <!--</a>-->
                    <!--<a target="_blank" href="https://panjiachen.github.io/vue-element-admin-site/#/">-->
                        <!--<el-dropdown-item>Docs</el-dropdown-item>-->
                    <!--</a>-->
                    <el-dropdown-item divided>
                        <span style="display:block;" @click="logout">Log Out</span>
                    </el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import Breadcrumb from './breadcrumb'
import Hamburger from './hamburger'

export default {
    components: {
        Breadcrumb,
        Hamburger
    },
    computed: {
        ...mapState('app', [
            'sidebar'
        ])
    },
    methods: {
        ...mapActions('app', ['toggleSideBar']),
        // ...mapActions('user', ['logout']),
        async logout () {
            await this.$store.dispatch('user/logout')
            location.replace('/')
            // this.$router.push(`/login?redirect=${this.$route.fullPath}`)
        }
    }
}
</script>

<style lang="scss" module>
.navbar {
    height: 50px;
    overflow: hidden;
    position: relative;
    background: #fff;
    box-shadow: 0 1px 4px rgba(0, 21, 41, .08);
}
.hamburgerContainer {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
        background: rgba(0, 0, 0, .025)
    }
}

.breadcrumbContainer {
    float: left;
}

.rightMenu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
        outline: none;
    }

    .right-menu-item {
        display: inline-block;
        padding: 0 8px;
        height: 100%;
        font-size: 18px;
        color: #5a5e66;
        vertical-align: text-bottom;

        &.hover-effect {
            cursor: pointer;
            transition: background .3s;

            &:hover {
                background: rgba(0, 0, 0, .025)
            }
        }
    }
}
.avatarContainer {
    margin-right: 30px;

    .avatarWrapper {
        margin-top: 5px;
        position: relative;

        .userAvatar {
            cursor: pointer;
            width: 40px;
            height: 40px;
            border-radius: 2px;
        }
        :global(.el-icon-caret-bottom) {
            cursor: pointer;
            position: absolute;
            right: -20px;
            top: 25px;
            font-size: 12px;
        }
    }
}
</style>
