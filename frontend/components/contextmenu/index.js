import ContextMenu from './contextmenu.vue'
import ContextMenuItem from './contextmenu-item'
import ContextmenuItemDivided from './contextmenu-item-divided'
import contextmenu from './contextmenu.js'

export default function install(Vue, opts = {}) {

    Vue.directive('contextmenu', contextmenu)
    Vue.component(ContextMenu.name, ContextMenu)
    Vue.component(ContextMenuItem.name, ContextMenuItem)
    Vue.component(ContextmenuItemDivided.name, ContextmenuItemDivided)
}
