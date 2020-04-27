<template>
    <li @click="handleClick" :class="[$style.item, {[$style.disabled]: disabled}]">
        <slot>
            <span :class="$style.label" v-text="label"></span>
            <kbd v-if="hotKey" v-text="hotKey"></kbd>
        </slot>
    </li>
</template>

<script>
export default {
	name: 'contextmenu-item',
    props: {
        name: {
            type: String
        },
        label: {
            type: String,
            default: ''
        },
        disabled: {
            type: Boolean,
            default: false
        },
        hotKey: {
            type: String,
            default: ''
        }
    },
    methods: {
        handleClick () {
            if (this.disabled) return;
            const $parent = this.$parent
            if ($parent && $parent.$options.name === 'contextmenu') {
                $parent.$emit('on-hover-click');
            }
            $parent.$emit('on-click-item', this.name);
        }
    }
}
</script>

<style module lang='scss'>
.item {
    line-height: 2.4;
    padding: 0 1rem;
    margin: 0;
    font-size: 12px;
    color: #606266;
    cursor: pointer;
    outline: none;
    display: flex;
    align-items: center;
    justify-content: space-between;
    user-select: none;
    &:hover {
        background-color: #ecf5ff;
        color: #66b1ff;
    }

    kbd {
        display: inline-block;
        padding: 3px 5px;
        font: 11px SFMono-Regular, Consolas, Liberation Mono, Menlo, monospace;
        line-height: 10px;
        color: #444d56;
        vertical-align: middle;
        background-color: #fafbfc;
        border: 1px solid #d1d5da;
        border-radius: 3px;
        box-shadow: inset 0 -1px 0 #d1d5da
    }
    .label {
        margin-right: 1rem;
    }
    &.disabled:hover {
        color: inherit;
        background-color: white;
    }
    &.disabled {
        opacity: .6;
        cursor: no-drop;
    }
}

</style>
