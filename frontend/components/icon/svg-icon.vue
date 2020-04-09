<template>
    <svg class="svg-icon" aria-hidden="true" :style="styles" v-on="$listeners">
        <use v-bind:xlink:href="iconHref" :fill="fill"></use>
    </svg>
</template>

<script>
// import
export default {
    name: 'svg-icon',
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
    computed: {
        iconHref () {
            return `#${this.type}`
        },
        fill () {
            if (this.color) {
                return this.color
            }
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
    }
}
</script>
<style >
.svg-icon {
    width: 1em;
    height: 1em;
    vertical-align: -0.15em;
    fill: currentColor;
    overflow: hidden;
}

.svg-external-icon {
    background-color: currentColor;
    mask-size: cover !important;
    display: inline-block;
}
</style>
