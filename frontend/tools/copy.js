import {Message} from 'element-ui'

let copyrelayDom

export function copy(str) {
    if (!copyrelayDom) {
        copyrelayDom = document.createElement('textarea')
        copyrelayDom.style.cssText = 'position: fixed;left: -999px;top:0;opacity: 0'
        document.body.appendChild(copyrelayDom)
    }

    copyrelayDom.value = str
    copyrelayDom.focus()
    copyrelayDom.select()
    try {
        if (document.execCommand('copy', false, null)) {
            Message.success('复制成功')
        } else {
            Message.error('复制失败')
        }
    } catch (err) {
        Message.error('复制失败')
    }
}
