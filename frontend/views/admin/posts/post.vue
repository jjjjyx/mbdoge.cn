<template>
    <div>
        <div :class="$globalStyle.pageHeader">
            <div :class="$globalStyle.title">
                {{$t('admin.post.title')}}
            </div>
            <div :class="$globalStyle.content">
            </div>
        </div>

        <!---->
        <el-card shadow="never" :class="$style.main">
            <div :class="$style.header">
                <div :class="$style.filterContainer">
                    <!-- 搜索标签 标题-->
                    <el-input  style="width: 200px;" @keyup.enter.native="handleFilter" :placeholder="$t('admin.post.input.search')"></el-input>
                    <!-- 按文章状态筛选-->
                    <!-- 按发布时间 -->
                    <!-- 按分类 -->
                    <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">{{$t('admin.post.button.search')}}</el-button>

                    <el-button class="filter-item" type="success" icon="el-icon-edit" @click="handleClickCreatePost">{{$t('admin.post.button.new')}}</el-button>
                </div>
                <div :class="$style.tableInfo">
                    共 {{params.total}} 篇文章
                </div>
                <el-button :loading="loading" icon="el-icon-refresh" size="small" @click="handlerClickRefresh" circle></el-button>
            </div>

            <!--<el-table></el-table>-->
            <el-pagination
                :class="$style.pagination"
                :page-size="params.pageSize"
                :pager-count="11"
                :current-page="params.currPage"
                layout="prev, pager, next"
                :total="params.total"
                @current-change="handleChangePage">
            </el-pagination>
        </el-card>
    </div>
</template>

<script>
export default {
    layout: 'admin',
    name: 'post',
    data () {
        return {
            loading: false,
            params: {
                currPage: 1,
                pageSize: 20,
                total: 0,
                order: '',
                defaultOrder: {}
            }
        }
    },
    methods: {
        handleFilter () {},
        handleChangePage () {},
        handlerClickRefresh () {},
        handleClickCreatePost () {
            this.$router.push({ name: 'new-post' })
        },
    },
    mounted () {
    }
}
</script>

<style module lang="scss">
.main {
}
.header {
    margin-bottom: 1rem;
    display: flex;
    //justify-content: space-between;
    align-items: center;

    .filterContainer {
        flex: 1;
    }
    .tableInfo {
        margin-right: .5rem;
    }
}

.pagination {
    margin-top: 1rem;
    text-align: center;
}
</style>
