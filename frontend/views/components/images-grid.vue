<template>
    <div :class="$mediaStyle.imageGridWarp" @mousedown="handlerImageGridMousedown">
        <div :class="$mediaStyle.imageGrid" v-infinite-scroll="fetchData" ref="grid">
            <div @click="handlerClick" :class="$mediaStyle.imageItem" v-for="(item, index) in imageData" :key="index"
                 :data-key="item.key" :data-index="index" role="imageItem">
                <!--:previewSrcList="previewSrcList"-->
                <el-image :src="imageDomain + item.key + imgStyle" :alt="item.key"></el-image>
                <font-icon :class="$mediaStyle.check" type="el-icon-success" size="20"></font-icon>
            </div>
        </div>
    </div>
</template>

<script>
import config from '@/config'
import {off, on} from '@/tools/dom'
import {$mediaStyle} from '@/tools/style'

function getDistance(x1, x2, y1, y2) {
    const x = x1 - x2
    const y = y1 - y2
    return Math.sqrt(x * x + y * y)
}

export default {
    name: 'images-grid',
    data() {
        return {
            imgStyle: '?imageView2/1/w/180/h/180/q/75|watermark/2/text/ampqanl4/font/Y291cmllciBuZXc=/fontsize/240/fill/I0ZERkRGRA==/dissolve/84/gravity/SouthWest/dx/10/dy/10|imageslim',
            imageDomain: config.imageDomain
        }
    },
    computed: {
        $mediaStyle
        // previewSrcList () {
        //     return this.imageData.map((item) => {
        //         return config.imageDomain + item.key
        //     }).filter(Boolean)
        // }
    },
    props: {
        imageData: Array
    },
    methods: {
        fetchData () {
            this.$parent.$parent.fetchData()
        },
        getEventTarget(e) {
            return e.path.find((t) => t.matches && t.matches('.' + this.$mediaStyle.imageItem))
        },
        handlerClick(e) {
            // const $images = document.querySelectorAll('.' + this.$mediaStyle.imageItem)
            // $images.forEach((item) => {
            //     item.classList.remove(this.$mediaStyle.active)
            // })
            // const target = this.getEventTarget(e)
            // target.classList.add(this.$mediaStyle.active)
        },
        handlerImageGridMousedown(e) {
            // 如果是右键点击，放弃选中，选中当前右击的对象
            // 1 左键， 4 中建 2 右键
            // 左键点击， 右键 可以是 单选， 取消其他选中
            // 左键拖动可以是框选
            // 中建是减选
            // console.log(e.buttons)

            if (e.buttons === 2) {
                e.preventDefault()
                // 如果左击在目标上
                const target = this.getEventTarget(e)
                if (target) {
                    //选中目标
                    target.classList.add(this.$mediaStyle.active)
                }
                return
            } else if (e.buttons === 4) {
                const target = this.getEventTarget(e)
                if (target) {
                    target.classList.remove(this.$mediaStyle.active)
                }
                return
            } else if (e.buttons === 1) {

            }
            // 框选开始
            const {clientX, clientY} = e
            const $div = document.createElement('div')
            const $grid = this.$refs.grid
            const gridBound = $grid.getBoundingClientRect()
            const $images = document.querySelectorAll('.' + this.$mediaStyle.imageItem)
            let start = false
            // 元素的 offsetWidth 等属性 在初始化的时候设置一次
            // 以后读取可能会快一些
            $images.forEach(item => {
                item['@box'] = {
                    l: item.offsetLeft,
                    t: item.offsetTop,
                    w: item.offsetWidth + item.offsetLeft,
                    h: item.offsetHeight + item.offsetTop
                }
            })

            const posx = clientX - gridBound.x + $grid.scrollLeft
            const posy = clientY - gridBound.y + $grid.scrollTop

            $div.className = this.$mediaStyle.frameDiv
            $div.style.cssText = `left: ${posx}px; top: ${posy}px`
            $grid.appendChild($div)
            const computedSelected = () => {
                const selectedEls = []
                const {offsetLeft, offsetTop, offsetWidth, offsetHeight} = $div
                // const
                // const px =
                $images.forEach((item) => {
                    item.classList.remove(this.$mediaStyle.active)
                })
                // 选起来有点慢 可能是频繁的读取 offsetWidth 属性， 通过在初始时 缓存一下元素的位置信息，加速计算过程，减少元素重绘
                const selected = Array.prototype.slice.call($images).filter((item) => {
                    // const sl = item.offsetWidth + item.offsetLeft
                    // const st = item.offsetHeight + item.offsetTop
                    const box = item['@box']
                    return box.w > offsetLeft && box.h > offsetTop && box.l < offsetLeft + offsetWidth && box.t < offsetTop + offsetHeight
                })
                selected.forEach((item) => {
                    item.classList.add(this.$mediaStyle.active)
                })
                return selected
            }

            const handlerMousemove = (ev) => {
                // 判断下拖动距离 太短不开启拖动
                // Math.pow(ev.clientX - clientX, 2) + Math.pow(ev.clientY - clientY, 2)
                // if (!start) {
                //     const distance = getDistance(ev.clientX, clientX, ev.clientY, clientY)
                //     // 拖动距离大于35 才开始
                //     if (distance > 35) {
                //         start = true
                //     } else {
                //         return
                //     }
                // }

                const x = ev.clientX - gridBound.x + $grid.scrollLeft
                const y = ev.clientY - gridBound.y + $grid.scrollTop
                const h = $grid.clientHeight
                // const w = $grid.clientWidth

                // 向下拖拽
                if (y >= h && $grid.scrollTop <= h) {
                    $grid.scrollTop += y - h
                }
                // 向上拖拽
                if (ev.clientY <= gridBound.y && $grid.scrollTop > 0) {
                    $grid.scrollTop = Math.abs(ev.clientY - gridBound.y)
                }


                const left = Math.min(x, posx)
                const top = Math.min(y, posy)
                const width = Math.abs(x - posx)
                const height = Math.abs(y - posy)

                $div.style.cssText = `left: ${left}px; top: ${top}px;width:${width}px;height: ${height}px`

                computedSelected()

            }
            const handlerMouseup = (ev) => {

                // if (start) {
                // }
                computedSelected()
                // ev.preventDefault()
                // ev.stopPropagation()
                $div.parentNode.removeChild($div)
                off(document.body, 'mousemove', handlerMousemove)
                off(document.body, 'mouseup', handlerMouseup)
            }

            on(document.body, 'mousemove', handlerMousemove)
            on(document.body, 'mouseup', handlerMouseup)

        }
    }
}
</script>
