import SvgIcon from './svg-icon'

const prefixCls = 'j-icon'

export default {
    name: 'font-icon',
    props: {
        type: {
            type: String,
            default: ''
        },
        size: [Number, String],
        color: String,
        custom: {
            type: String,
            default: ''
        }
    },
    components: {
        SvgIcon
    },
    computed: {
        classes () {
            return [
                `${prefixCls}`,
                {
                    [this.type]: this.type !== '',
                    [this.custom]: this.custom !== ''
                }
            ]
        },
        styles () {
            let style = {}
            if (this.size) {
                style['font-size'] = `${this.size}px`
            }
            if (this.color) {
                style.color = this.color
            }
            return style
        }
    },
    render (h) {
        let { type, size, color, custom, $listeners } = this
        let i
        if (type.startsWith('icon-')) {
            i = h('svg-icon', {
                props: {
                    type, size, color, custom
                },
                on: $listeners
            })
        } else {
            i = h('i', {
                class: this.classes,
                style: this.styles,
                on: $listeners
            })
        }
        return i
    }
}
