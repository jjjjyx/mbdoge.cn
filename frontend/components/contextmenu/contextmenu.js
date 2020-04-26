import {off, on} from '@/tools/dom'

const ctx = '@@contextmenu'

const _preventContextMenuEvent = function (e) {
    e.stopPropagation()
    e.preventDefault()
    // 显示菜单
    // const {id} = el[ctx]
}
const _handleBodyMousedown = function (e) {
    console.log('隐藏菜单')
    // ContextMenuManage.contextMenuInstances.forEach(i => i.hide())
}

const instances = []


class ContextMenu {
    $el
    menu
    constructor(el, menu) {
        this.$el = el
        this.menu = menu
        // 移动menu 到 body 中
        this.onContextmenu = (e) => {
            this.menu.show(e)
            // e.stopPropagation()
            // e.preventDefault()
            // let { clientX, clientY } = e
            // this.$menu.style.cssText = `top: ${clientY}px;left:${clientX}px;display: block;`
        }
        this.onMousedown = (event) => {
            const {target} = event;
            if (this.menu.$el !== target && !this.menu.$el.contains(target)) {
                this.menu.hide()
            }
        }

        on(this.$el, 'contextmenu', this.onContextmenu)
        // 点击元素外关闭
        on(document.body, 'mousedown', this.onMousedown)
    }

    destroy () {
        // 销毁一般伴随着指令所在的组件也没了，menu 也会消失，但是menu 被主动的插入到了body中所以不会主动清除
        if (this.menu.$el.parentNode === document.body) {
            document.body.removeChild(this.menu.$el)
        }
        // 解除事件绑定
        off(this.$el, 'contextmenu', this.onContextmenu)
        off(document.body, 'mousedown', this.onMousedown)
    }
}

export default {
    bind (el, binding, vnode) {
        console.log(el)
        console.log(vnode)
        console.log(binding)

        const _ref = binding.expression ? binding.value : binding.arg;
        const menu = vnode.context.$refs[_ref];
        // 创建对象
        if (menu) {
            const contextMenu = new ContextMenu(el, menu)
            // ContextMenuManage.addInstance(contextMenu)
            instances.push({el, contextMenu})

        } else {
            log.warn("尚未找到菜单元素")
        }

        // on(el, 'contextmenu', _preventContextMenuEvent)
        // on(document.body, 'mousedown', _handleClick)
        // el[ctx] = {
        //     offEvent: () => {
        //         off(el, 'contextmenu', _preventContextMenuEvent)
        //         off(document.body, 'mousedown', _handleClick)
        //     }
        // }

    },
    unbind (el) {
        instances.filter((item) => item.el === el).forEach((instance) => {
            instance.contextMenu.destroy()
        })
    }

}
