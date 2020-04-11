<template>
    <div :class="$style.panel">
        <div :class="$style.title" v-text="title"></div>
        <div :class="$style.grid">
            <table :class="$style.table" @click="handlerClick" @mousemove="handlerMousemove" @mouseleave="handlerMouseover" ref="table">
                <tbody>
                    <tr v-for="i in gridSize[0]" v-once>
                        <td v-for="j in gridSize[1]">
                            <div></div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>
import editor from "../editor";
export default {
	name: "tools-insetTable",
    data () {
	    return {
	        gridSize: [10, 10],
            // 0： 列  1 行
            last: [-1, -1],
        }
    },
    computed: {
	    title () {
	        if (this.last[0] === this.last[1] && this.last[1] === -1) {
	            return this.$tc('editor.tools.insertTable.title', 1)
            }
	        return this.$tc('editor.tools.insertTable.title',2, {count: this.last.join('x')})
        }
    },
    methods: {
        handlerClick () {
            this.$parent.doToggle()
            editor.drawTable(this.last)
        },
        handlerMouseover () {
            this.last = [-1, -1]
            this.clearTable()
        },
        handlerMousemove(e) {
            // 去抖
            let {target} = e
            if (target.nodeName === 'DIV') {
                target = target.parentNode
            }
            // 确定当前的 td 是几行几列
            const cellIndex = target.cellIndex
            const rowIndex = target.parentNode.rowIndex


            // const diffCell = cellIndex - this.last[0]
            // const diffRow = rowIndex - this.last[1]
            this.renderTable(cellIndex + 1, rowIndex + 1)
        },
        renderTable(diffCell, diffRow) {
            // console.log(diffCell, diffRow, this.last)
            this.clearTable()
            const table = this.$refs.table

            const active = this.$style.tdActive
            for (let j = 0; j < diffRow; j++) {
                const row = table.rows[j]
                for (let i = 0; i < diffCell; i++) {
                    const td = row.cells[i]
                    td.classList.add(active)
                }
            }
            this.last = [diffCell, diffRow]
        },
        clearTable() {
            const table = this.$refs.table
            const active = this.$style.tdActive
            for (let j = 0; j < this.gridSize[0]; j++) {
                const row = table.rows[j]
                for (let i = 0; i < this.gridSize[1]; i++) {
                    const td = row.cells[i]
                    td.classList.remove(active)
                }
            }

        }
    }
}
</script>

<style module lang="scss">
.panel {
    width: 195px;
    padding: 1px;
}
.title {
    padding: .5rem .25rem;
    background-color: $--color-info-lighter;
    color: $--color-text-regular;
    font-weight: bold;
}
.grid {
    padding: .125rem;
}
.table {
    /*width: 100%;*/
    margin: 0 auto;
    border-collapse: collapse;
    border-spacing: 0;
}
.table td {
    width: 1rem;
    height: 1rem;

}

.table td.tdActive > div {
    border: 1px solid $--color-primary;
}
.table td > div {
    width: 1rem;
    height: 1rem;
    border: 1px solid $--color-black;
}


</style>
