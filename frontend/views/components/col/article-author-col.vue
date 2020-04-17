<template>
    <div :class="$style.col">
        <el-avatar size="small" :src="user.avatar" class="mr-1"></el-avatar>
        <span>{{user.nickname}}</span>
    </div>
</template>

<script>
export default {
	name: "article-author-col",
    props: {
	    user: Object
    }
}
</script>

<style module lang="scss">
.col {
    display: flex;
    align-items: center;
}
</style>
