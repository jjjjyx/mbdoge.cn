<template>
    <div>
        <div :class="$style.conn" v-contextmenu:menu>
            2222222
        </div>

        <div ref="menu">
            asd
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
        abc () {

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
</style>
