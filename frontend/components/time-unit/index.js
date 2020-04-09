
const units = Object.freeze({
    'year': { color: 'danger', label: '年' },
    'month': { color: 'success', label: '月' },
    'week': { color: '', label: '周' },
    'day': { color: 'warning', label: '日' }
})
const ALLOW_VALUE = Object.keys(units)

const TimeUnit = {
    name: 'time-unit',
    functional: true,
    props: {
        value: {
            type: String,
            validator: function (val) {
                return ALLOW_VALUE.indexOf(val) !== -1
            }
        },
        size: {
            type: String,
            default: 'mini'
        }
    },
    render (h, ctx) {
        const { value, size } = ctx.props
        const type = units[value].color
        return <el-tag size={size} type={type}>{units[value].label}</el-tag>
    }
}

export default function install (Vue) {
    Vue.component('time-unit', TimeUnit)
}
