<template>
    <el-breadcrumb :class="$style.breadcrumb" separator="/">
        <transition-group name="breadcrumb">
            <el-breadcrumb-item v-for="(item,index) in levelList" :key="item.name">
                <span v-if="item.redirect ==='noRedirect' || index === levelList.length - 1" :class="$style.noRedirect">{{ generateTitle(item.meta) }}</span>
                <a v-else @click.prevent="handleLink(item)">{{ generateTitle(item.meta) }}</a>
            </el-breadcrumb-item>
        </transition-group>
    </el-breadcrumb>
</template>

<script>
import { generateTitle } from '@/tools/i18n'

export default {
    name: 'breadcrumb',
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
        generateTitle,
        getBreadcrumb () {
            // only show routes with meta.title
            let matched = this.$route.matched.filter(item => item.meta && item.meta.title)

            if (this.$route.name !== 'dashboard') {
                matched.unshift({ path: '/6tw9sQs', name: 'dashboard', meta: { title: 'dashboard' } })
            }
            this.levelList = matched.filter(item => item.meta && item.meta.title && item.meta.breadcrumb !== false)
                .map((item) => {
                    let meta = { ...item.meta }
                    if (meta.title && typeof meta.title === 'function') {
                        meta.__titleIsFunction__ = true
                        meta.title = meta.title(this.$route)
                    }

                    return {
                        icon: (item.meta && item.meta.icon) || '',
                        name: item.name,
                        meta: meta
                    }
                })
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
    user-select: none;

    .noRedirect {
        color: #97a8be;
        cursor: text;
    }
}
</style>
