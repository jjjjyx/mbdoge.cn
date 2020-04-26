<template>
    <div :class="$style.imageGridWarp" @mousedown="handlerImageGridMousedown">
        <div :class="$style.imageGrid" v-infinite-scroll="$parent.fetchData" ref="grid">
            <div :class="$style.imageItem" v-for="(item, index) in imageData" :key="index" >
                <el-image :src="imageDomain + item.key + imgStyle" :alt="item.key" :previewSrcList="previewSrcList"></el-image>
                <font-icon :class="$style.check" type="el-icon-success" size="20"></font-icon>
            </div>
        </div>
    </div>
</template>

<script>
import config from '@/config'
import {off, on} from '@/tools/dom'
function getDistance(x1, x2, y1, y2) {
    const x = x1 - x2
    const y = y1 - y2
    return Math.sqrt(x * x + y * y)
}

export default {
	name: 'images-grid',
    data () {
	    return {
            imgStyle: '?imageView2/1/w/180/h/180/q/75|watermark/2/text/ampqanl4/font/Y291cmllciBuZXc=/fontsize/240/fill/I0ZERkRGRA==/dissolve/84/gravity/SouthWest/dx/10/dy/10|imageslim',
            imageDomain: config.imageDomain
        }
    },
    computed: {
        previewSrcList () {
            return this.imageData.map((item) => {
                return config.imageDomain + item.key
            }).filter(Boolean)
        }
    },
    props: {
        imageData: Array
    },
    methods: {
        handlerImageGridMousedown (e) {
            // 框选开始
            e.preventDefault()
            e.stopPropagation()
            const { clientX, clientY } = e
            const $div = document.createElement("div")
            const $grid = this.$refs.grid
            const gridBound = $grid.getBoundingClientRect()
            const $images = document.querySelectorAll('.' + this.$style.imageItem)
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

            $div.className = this.$style.frameDiv;
            $div.style.cssText = `left: ${posx}px; top: ${posy}px`

            const computedSelected = () => {
                const selectedEls = [];
                const {offsetLeft, offsetTop, offsetWidth, offsetHeight} = $div
                // const
                // const px =
                $images.forEach((item) => {
                    item.classList.remove(this.$style.active)
                })
                // 选起来有点慢 可能是频繁的读取 offsetWidth 属性
                const selected = Array.prototype.slice.call($images).filter((item) => {
                    // const sl = item.offsetWidth + item.offsetLeft
                    // const st = item.offsetHeight + item.offsetTop
                    const box = item['@box']
                    return box.w > offsetLeft && box.h > offsetTop && box.l < offsetLeft + offsetWidth && box.t < offsetTop + offsetHeight
                })
                selected.forEach((item) => {
                    item.classList.add(this.$style.active)
                })
                return selected
            }

            const handlerMousemove = (ev) => {
                // 判断下拖动距离 太短不开启拖动
                // Math.pow(ev.clientX - clientX, 2) + Math.pow(ev.clientY - clientY, 2)
                if (!start) {
                    const distance = getDistance(ev.clientX, clientX, ev.clientY, clientY)
                    // 拖动距离大于35 才开始
                    if (distance > 35) {
                        start = true
                        $grid.appendChild($div);
                    } else {
                        return
                    }
                }

                ev.preventDefault()
                ev.stopPropagation()

                const x = ev.clientX - gridBound.x + $grid.scrollLeft
                const y = ev.clientY - gridBound.y + $grid.scrollTop
                const h = $grid.clientHeight
                // const w = $grid.clientWidth

                // 向下拖拽
                if (y >= h && $grid.scrollTop <= h) {
                    $grid.scrollTop += y - h;
                }
                // 向上拖拽
                if (ev.clientY <= gridBound.y && $grid.scrollTop > 0) {
                    $grid.scrollTop = Math.abs(ev.clientY - gridBound.y);
                }


                const left = Math.min(x, posx)
                const top = Math.min(y, posy)
                const width = Math.abs(x - posx)
                const height = Math.abs(y - posy)

                $div.style.cssText = `left: ${left}px; top: ${top}px;width:${width}px;height: ${height}px`

                const selectedEls = computedSelected()
                console.log(selectedEls)
            }
            const handlerMouseup = (ev) => {
                ev.preventDefault()
                ev.stopPropagation()
                const selectedEls = computedSelected()
                $div.parentNode.removeChild($div);
                off(document.body, 'mousemove', handlerMousemove)
                off(document.body, 'mouseup', handlerMouseup)
            }

            on(document.body, 'mousemove', handlerMousemove)
            on(document.body, 'mouseup', handlerMouseup)

        }
    }
}
</script>

<style module lang='scss'>
.imageGridWarp {
    flex: 1;
    position: relative;
}

.imageGrid {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    overflow-y: scroll;
    user-select: none;
}
.imageItem {
    position: relative;
    float: left;
    width: 160px;
    height: 160px;
    margin: .5rem;
    overflow: hidden;
    transition: background-color .15s ease-in;
    padding: 10px;
    border: 1px solid transparent;
    &:hover, &.active {
        background-color: rgba($primary, .2);
        //transform: scale(1.05);
    }
    &:hover .check, &.active .check {
        display: block;
        opacity: .4;
    }
    .imgConn {
        height: 150px;
        display: flex;
        justify-content: center;
        align-items: center;
        overflow: hidden;

        img {
            //width: 100%;
            height: 100%;
            box-shadow: 0 0 6px 0 rgba(0, 0, 0, 0.2);
            user-select: none;
        }
    }

    .check {
        position: absolute;
        top: 5px;
        left: 5px;
        height: 21px;
        width: 21px;
        display: none;
        cursor: pointer;
    }
}

.frameDiv {
    border: 1px solid $--color-primary;
    background: rgba($--color-primary, .1);
    position: absolute;
    width: 0;
    height: 0;
    user-select: none;
    z-index: 666;
    /*opacity: 0.1;*/
}
</style>
