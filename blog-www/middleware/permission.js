const whiteList = ['/6tw9sQs/login']

export default ({ route, store, redirect }) => {
    // Take the last value (latest route child)
    // 必须是后台管理
    if (route.path.startsWith('/6tw9sQs')) {
        // 是否登录
        // 已登录
        if (store.getters['user/isLogin']) {
            if (route.path === '/6tw9sQs/login') {
                redirect({ name: 'dashboard' })
            }
        } else {
            if (whiteList.indexOf(route.path) === -1) {
                // /login?redirect=${route.path}
                redirect({ name: 'login', query: { redirect: route.path } })
            }
        }
        //     const breadcrumbs = route.meta.filter(item => item.title && item.breadcrumb !== false)
        //     store.commit('app/SET_BREADCRUMBS', breadcrumbs)
    }
    // const theme = route.meta.reduce((theme, meta) => meta.theme || theme, 'light')

}
