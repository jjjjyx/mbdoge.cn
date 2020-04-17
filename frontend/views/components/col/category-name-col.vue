<template>
    <div :class="$style.col">
        <div :class="$style.icon">
            <el-popover trigger="hover" :popper-class="$style.popover" placement="bottom-start">
                <font-icon :type="icon" size="100"></font-icon>
                <font-icon slot="reference" :type="icon" size="25"></font-icon>
            </el-popover>
        </div>
        <div :class="$style.name">
            <a href="javascript:void(0);">{{name}}</a>
        </div>
    </div>
</template>

<script>
/**
 * 标签名称 col
 */
export default {
	name: "category-name-col",
    props: {
	    name: String,
        icon: String,
    }
}
</script>

<style module lang="scss">
.col {
    display: flex;
    align-items: center;
}
.icon {
    margin-right: .25rem;
}

.name {

}
.popover {
    width: 120px;
    min-width: 120px;
    padding: 10px;
}
</style>
