import Vue from 'vue'
import Router from 'vue-router'
import Index from '~/views'
// import AdminIndex from '~/views/6tw9sQs/admin'

Vue.use(Router)

const callback = m => m.default || m

export const routes = [
    {
        path: '/',
        name: 'index',
        component: Index
    },
    {
        name: 'dashboard',
        path: '/6tw9sQs',
        // /* webpackChunkName: "test" */
        component: () => import('~/views/admin/dashboard/dashboard').then(callback)
    },
    {
        name: 'post-manage',
        path: '/6tw9sQs/posts',
        // /* webpackChunkName: "test" */
        component: () => import('~/views/admin/posts/post').then(callback)
    },
    {
        name: 'category-manage',
        path: '/6tw9sQs/category',
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

export function createRouter () {
    return new Router({
        mode: 'history',
        routes
    })
}
