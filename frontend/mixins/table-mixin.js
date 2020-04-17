import { fetchDataMixin } from './fetch-data-mixin'

import {cloneDeep, isEqual} from "@/tools/lodash";

const transformSort = {
    ascending: 'asc',
    descending: 'desc'
}

export const convertOriginData = function (data) {
    return {
        tableData: data.content,
        tableParams: {
            currPage: data.number, // 页码 0 开始
            pageSize: data.size, //
            total: data.totalElements,
            order: '',
            defaultOrder: {}
        }
    }
}

export const tableMixin = {
    mixins: [fetchDataMixin],
    data () {
        return {
            tableData: [],
            tableFilter: {},
            tableParams: {
                currPage: 0, // 页码 0 开始
                pageSize: 10, //
                total: 0,
                order: '',
                defaultOrder: {}
            },
            deleteLoading: false,

            editLoading: false,
            editTarget: null,
            editTargetOrigin: null,
            editVisible: false,
            editTargetIsModify: false // 被编辑的对象是否修改
        }
    },
    watch: {
        editTarget: {
            handler: function (val) {
                this.editTargetIsModify = !isEqual(val, this.editTargetOrigin)
            },
            deep: true
        }
    },
    methods: {
        handlerEditTarget(target) {
            this.editTargetOrigin = target
            this.editTarget = cloneDeep(target);
            this.editVisible = true
        },
        handlerCancelEdit () {
            this.editVisible = false
            this.editVisibleLoading = false
        },
        // 设置当前表格的数据
        beforeDataItem(item) {
        },
        /**
         * 适用spring boot 的 pageable
         * @param data
         */
        setTableData(data) {
            data.content.forEach(this.beforeDataItem)
            this.tableData = data.content
            this.tableParams.currPage = data.number
            this.tableParams.pageSize = data.size
            this.tableParams.total = data.totalElements
        },
        selectAll () {
            this.tableData.forEach((row) => {
                this.$refs.table.toggleRowSelection(row, true)
            })
        },
        reverseSelectAll () {
            this.$refs.table.toggleAllSelection()
        },
        handleSortUser ({ prop, order }) {
            if (order) {
                let sort = `${prop}.${transformSort[order] || 'asc'}`
                this.fetchDataByPageAndOrder(this.tableParams.currPage, sort)
            }
        },
        handleFilter () {
            this.tableParams.currPage = 1
            return this.fetchData()
        },
        handleChangePage (page) {
            this.fetchDataByPageAndOrder(page)
        },
        fetchDataByPageAndOrder (page = 1, order = '') {
            this.tableParams.currPage = page
            if (order) {
                this.tableParams.order = order
            }
            return this.fetchData()
        }
    },
    mounted () {

        // setTimeout(() => {
        //     this.$store.commit('app/SET_RELOAD_DATA_LOADING', false)
        // },200)
    }
}
