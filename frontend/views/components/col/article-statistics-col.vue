<template>
    <div :class="$style.col">
    <!--    查看 点赞 评论-->
        <div :class="$style.item">
            <font-icon :class="$style.icon" type="el-icon-view"></font-icon> <span>{{eyeCount}}</span>
        </div>
        <div :class="$style.item">
            <font-icon :class="$style.icon" type="el-icon-chat-dot-square"></font-icon> <span>{{commentCount}}</span>
        </div>
        <div :class="$style.item">
            <font-icon :class="$style.icon" type="icon-heart"></font-icon> <span>{{likeCount}}</span>
        </div>
    </div>
</template>

<script>
export default {
    name: "article-statistics-col",
    props: {
        eyeCount: Number,
        likeCount: Number,
        commentCount: Number
    }
}
</script>

<style module lang="scss">

.col {
    display: flex;
    align-items: center;
    .item {
        margin-right: .5rem;
        line-height: 1;
    }
    .icon {
        /*margin-right: 0rem;*/
        font-size: 18px;
    }
}
</style>
