
import contextmenu from './contextmenu'

export default function install(Vue, opts = {}) {
    Vue.directive('contextmenu', contextmenu)
}
