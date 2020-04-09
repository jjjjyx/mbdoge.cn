import ECharts from 'vue-echarts/components/ECharts'
import 'echarts/lib/chart/bar'
import 'echarts/lib/chart/line'
import 'echarts/lib/chart/pie'
import 'echarts/lib/component/legend'
import 'echarts/lib/component/tooltip'
import 'echarts/lib/component/title'
import 'echarts/lib/component/markPoint'
// import 'echarts/lib/component/markLine'
// import 'echarts/lib/component'
export default function (Vue, option) {
    Vue.component('v-chart', ECharts)
}
