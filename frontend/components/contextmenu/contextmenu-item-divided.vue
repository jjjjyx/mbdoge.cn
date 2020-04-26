<template>
    <li :class="$style.divided"></li>
</template>

<script>
export default {
	name: 'contextmenu-item-divided',
}
</script>

<style module lang='scss'>

.divided {
    margin: 5px 0;
    height: 1px;
    background-color: #e8eaec;
    /*border-top: 1px solid #e8eaec;*/

}
</style>
