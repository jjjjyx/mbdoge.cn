
import Vue from 'vue'
import VueI18n from 'vue-i18n'

import elementEnLocale from 'element-ui/lib/locale/lang/en' // element-ui lang
import elementZhCNLocale from 'element-ui/lib/locale/lang/zh-CN'// element-ui lang

import enLocale from '~/locales/en-US'
import zhLocale from '~/locales/zh-CN'


Vue.use(VueI18n)

export default ({ app, store }) => {
    app.i18n = new VueI18n({
        locale: store.state.locale,
        fallbackLocale: 'en',
        messages: {
            "en": {
                ...enLocale,
                ...elementEnLocale
            },
            "zh-CN": {
                ...zhLocale,
                ...elementZhCNLocale
            }
        }
    })
}
