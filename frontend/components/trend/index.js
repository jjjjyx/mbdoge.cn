import Numeral from 'numeral'
import TrendStyle from './trend.module.scss'

const Trend = {
    name: 'trend',
    functional: true,
    props: {
        value: {
            type: [Number, String],
            default: 0
        },
        trend: {
            type: Boolean,
            default: true
        },
        renderTextColor: {
            type: Boolean,
            default: false
        },
        prefix: String,
        suffix: {
            type: String,
            default: '%'
        },
        format: {
            type: String,
            default: '0.00'
        }
    },
    render (h, ctx) {
        const { value, trend, renderTextColor, prefix, suffix, format } = ctx.props
        const trendClass = trend ? 'el-icon-caret-top' : 'el-icon-caret-bottom'
        const color = trend ? TrendStyle.up : TrendStyle.down
        const textColor = renderTextColor ? color : {}
        const str = Numeral(value).format(format)

        const $el = <span class={ textColor }>{str}{suffix}</span>

        let $trend = <i class={[trendClass, color, TrendStyle.trend]}/>
        return [prefix, $el, $trend]
    }
}

export default function install (Vue) {
    Vue.component('trend', Trend)
}
