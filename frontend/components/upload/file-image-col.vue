<template>
    <el-image :class="$style.img" :src="info.src">
        <div slot="error" :class="$style.imageSlot">
            <i class="el-icon-picture-outline"></i>
        </div>
    </el-image>
</template>

<script>
export default {
	name: 'file-image-col',
    props: {
	    info: Object
    }
}
</script>

<style module lang="scss">
.img {
    width: 60px;
    height: 60px;
}
.imageSlot {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    width: 100%;
    height: 100%;
    font-size: 1rem;
}
</style>
