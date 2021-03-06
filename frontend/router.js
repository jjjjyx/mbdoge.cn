import Vue from 'vue'
import Router from 'vue-router'
import Index from '~/views'
// import AdminIndex from '~/views/6tw9sQs/admin'

Vue.use(Router)

const callback = m => m.default || m

const routes = [
    {
        path: '/',
        name: 'index',
        component: Index
    },
    {
        name: 'dashboard',
        path: '/6tw9sQs',
        // /* webpackChunkName: "test" */
        meta: {
            title: 'dashboard',
            icon: '',
            affix: true
        },
        component: () => import('~/views/admin/dashboard/dashboard').then(callback)
    },
    {
        name: 'post-manage',
        path: '/6tw9sQs/posts',
        meta: {
            title: 'post',
            icon: ''
        },
        component: () => import('~/views/admin/posts/post').then(callback)
    },
    {
        name: 'new-post',
        path: '/6tw9sQs/post/new',
        meta: {
            title: 'new-post',
            icon: ''
        },
        component: () => import('~/views/admin/posts/new').then(callback)
    },
    {
        name: 'category-manage',
        path: '/6tw9sQs/category',
        meta: {
            title: 'category',
            icon: ''
        },
        component: () => import('~/views/admin/category/category').then(callback)
    },
    {
        name: 'media-manage',
        path: '/6tw9sQs/media',
        meta: {
            title: 'media',
            icon: ''
        },
        component: () => import('~/views/admin/media/media').then(callback)
    },
    {
        name: 'test-vue',
        path: '/6tw9sQs/test',
        meta: {
            title: 'test',
            icon: ''
        },
        component: () => import('~/views/admin/test').then(callback)
    },
    {
        name: 'login',
        path: '/6tw9sQs/login',
        // /* webpackChunkName: "test" */
        component: () => import('~/views/admin/user/login').then(callback)
    },
]

export const constantRoutes = routes
    .filter((item) => item.meta && item.path.startsWith('/6tw9sQs'))
    .map(({ component, ...route }) => route)

export function createRouter () {
    return new Router({
        mode: 'history',
        routes
    })
}
