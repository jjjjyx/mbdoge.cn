<template>
    <div :class="$style.dashboardContainer">
        <div :class="$globalStyle.pageHeader">
            <div :class="$globalStyle.title">
                工作台
            </div>
            <div :class="[$globalStyle.content, $style.pageHeader]">
                <el-avatar :size="60" icon="el-icon-user-solid" :class="$style.avatar"></el-avatar>
                <div>
                    <h2 :class="$style.title">早安，xxx，开始您一天的工作吧！<i class="el-icon-lollipop text-danger"></i></h2>
                    <p :class="$style.desc"><i class="el-icon-heavy-rain"></i> 今日阴转小雨，22℃ - 32℃，出门记得带伞哦。</p>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    layout: 'admin',
    name: 'dashboard'
}
</script>

<style module lang="scss">
.dashboardContainer {
    /*padding: 24px;*/
}
.pageHeader {
    display: flex;
    align-items: center;
    .avatar {
        flex: 0 0 auto;
        margin-right: 1rem;
    }
    .title {
        font-size: 20px;
        font-weight: 700;
        margin-bottom: 12px;
        /*margin-top: 0;*/
    }
    .desc {
        color: #808695;
    }
}
.avatar {
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 30px;
}
</style>
