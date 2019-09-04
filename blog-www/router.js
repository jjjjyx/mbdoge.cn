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
        name: 'write-post',
        path: '/6tw9sQs/post/write',
        meta: {
            title: 'write-post',
            icon: ''
        },
        component: () => import('~/views/admin/posts/write').then(callback)
    },
    {
        name: 'category-manage',
        path: '/6tw9sQs/category',
        meta: {
            title: 'category',
            icon: ''
        },
        // /* webpackChunkName: "test" */
        component: () => import('~/views/admin/category/category').then(callback)
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
