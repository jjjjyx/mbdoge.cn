<template>
    <div>
        <!--<div >-->
        <!--    -->
        <!--</div>-->
        <contextmenu ref="menu" :class="$style.menu" @on-click="abc">
            <contextmenu-item name="xx" label="菜单一xxxxxxxxx" hot-key="ctrl-shift-alt-s"></contextmenu-item>
            <contextmenu-item name="xx" label="菜单一" hot-key="ctrl-shift-s"></contextmenu-item>
            <contextmenu-item name="xxx" label="菜单二" hot-key="ctrl-s"></contextmenu-item>
            <contextmenu-item-divided></contextmenu-item-divided>
            <contextmenu-item name="xxx" label="菜单三" hot-key="alt-s"></contextmenu-item>
        </contextmenu>

        <div :class="$style.conn" v-contextmenu:menu>
        </div>

    </div>
</template>

<script>
export default {
    layout: 'admin',
	name: 'test',
    data () {
        return {
            // abc: 'xx'
        }
    },
    methods: {
        abc (key, originEvent) {
            console.log('222', key, originEvent)
        }
    }
}
</script>

<style module lang='scss'>
.conn {
    width: 500px;
    height: 500px;
    border: 1px solid #f00;
}
.menu {

}
</style>
