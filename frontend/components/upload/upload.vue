<template>
    <div :class="$style.uploadMain" v-show="visible">
        222
    </div>
</template>

<script>
export default {
    name: 'upload',
    data () {
        return {
            visible: false
        }
    }
}
</script>

<style module lang="scss">
.uploadMain {
    width: 633px;
    top: auto;
    bottom: -1px;
    left: auto;
    right: 35px;
    display: block;
    visibility: visible;
    z-index: 1080; // 比poptip 低一级
    position: absolute;
    overflow: hidden;
    box-shadow: 0 0 10px #ccc;
    border-top-left-radius: 7px;
    border-top-right-radius: 7px;
    border: 1px solid #e2e2e2;
}
</style>
