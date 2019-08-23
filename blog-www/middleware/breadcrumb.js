
export default ({ route, store }) => {
    // Take the last value (latest route child)
    // 必须是后台管理
    if (route.path.startsWith('/6tw9sQs')) {
        const breadcrumbs = route.meta.filter(item => item.title && item.breadcrumb !== false)
        store.commit('sidebar/SET_BREADCRUMBS', breadcrumbs)
    }
    // const theme = route.meta.reduce((theme, meta) => meta.theme || theme, 'light')

}
