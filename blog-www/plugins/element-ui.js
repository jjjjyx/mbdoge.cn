import Vue from 'vue'
import {
    Button,
    Dialog,
    Tag,
    Popover,
    Tooltip,
    Badge,
    Pagination,
    Radio,
    RadioGroup,
    CheckboxGroup,
    Checkbox,
    Form,
    FormItem,
    Input,
    Backtop,
    Loading,
    MessageBox,
    Message,
    Menu,
    MenuItem,
    Submenu,
    MenuItemGroup,
    Scrollbar
} from 'element-ui'
import CollapseTransition from 'element-ui/lib/transitions/collapse-transition'

import locale from 'element-ui/lib/locale'
import lang from 'element-ui/lib/locale/lang/zh-CN'

function install (Vue) {

    locale.use(lang)

    Vue.component(Button.name, Button)
    Vue.component(Dialog.name, Dialog)
    Vue.component(Tag.name, Tag)
    Vue.component(Popover.name, Popover)
    Vue.component(Tooltip.name, Tooltip)
    Vue.component(Badge.name, Badge)
    Vue.component(Pagination.name, Pagination)
    Vue.component(Radio.name, Radio)
    Vue.component(RadioGroup.name, RadioGroup)
    Vue.component(CheckboxGroup.name, CheckboxGroup)
    Vue.component(Checkbox.name, Checkbox)
    Vue.component(Form.name, Form)
    Vue.component(FormItem.name, FormItem)
    Vue.component(Input.name, Input)
    Vue.component(Backtop.name, Backtop)
    Vue.component(Menu.name, Menu)
    Vue.component(Submenu.name, Submenu)
    Vue.component(MenuItem.name, MenuItem)
    Vue.component(MenuItemGroup.name, MenuItemGroup)
    Vue.component(Scrollbar.name, Scrollbar)
    Vue.component(CollapseTransition.name, CollapseTransition)

    Vue.use(Loading.directive)

    Vue.prototype.$loading = Loading.service
    Vue.prototype.$message = Message
    Vue.prototype.$msgbox = MessageBox
    Vue.prototype.$alert = MessageBox.alert
    Vue.prototype.$confirm = MessageBox.confirm
    Vue.prototype.$prompt = MessageBox.prompt


}

Vue.use(install)
