import numeralStyle from './numeral.module.scss'
import Numeral from 'numeral'
const numeral = {
    name: 'numeral',
    functional: true,
    props: {
        value: {
            type: [Number, String],
            default: 0
        },
        size: {
            type: [Number],
            default: 14
        },
        format: {
            type: String,
            default: '0,0.00'
        }
    },
    render (h, context) {
        const { value, size, format } = context.props
        const str = Numeral(value).format(format)
        return <span class={numeralStyle.num} style={ { fontSize: size + 'px' } }>{str}</span>
    }
}

export default function install (Vue) {
    Vue.component('numeral', numeral)
}
