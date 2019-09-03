import SvgIcon from './svg-icon'
import FontIcon from './font-icon'

export default function install (Vue) {
    Vue.component('svg-icon', SvgIcon)
    Vue.component('font-icon', FontIcon)
}
