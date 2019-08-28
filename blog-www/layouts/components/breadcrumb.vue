<template>
    <el-breadcrumb :class="$style.breadcrumb" separator="/">
        <transition-group name="breadcrumb">
            <el-breadcrumb-item v-for="(item,index) in levelList" :key="item.path">
                <span v-if="item.redirect ==='noRedirect' || index === levelList.length - 1" :class="$style.noRedirect">{{ item.meta.title }}</span>
                <a v-else @click.prevent="handleLink(item)">{{ item.meta.title }}</a>
            </el-breadcrumb-item>
        </transition-group>
    </el-breadcrumb>
</template>

<script>

export default {
    data () {
        return {
            levelList: null
        }
    },
    watch: {
        $route () {
            this.getBreadcrumb()
        }
    },
    created () {
        this.getBreadcrumb()
    },
    methods: {
        getBreadcrumb () {
            // only show routes with meta.title
            let matched = this.$route.matched.filter(item => item.meta && item.meta.title)

            if (this.$route.name !== 'dashboard') {
                matched.unshift({ path: '/6tw9sQs', name: 'dashboard', meta: { title: 'dashboard' } })
            }
            this.levelList = matched.filter(item => item.meta && item.meta.title && item.meta.breadcrumb !== false)
        },
        isDashboard (route) {
            const name = route && route.name
            if (!name) {
                return false
            }
            return name.trim().toLocaleLowerCase() === 'Dashboard'.toLocaleLowerCase()
        },
        handleLink (item) {
            const { redirect, path, name } = item
            if (redirect) {
                this.$router.push(redirect)
                return
            }

            this.$router.push({ name })
        }
    }
}
</script>
<style lang="scss" module>
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
