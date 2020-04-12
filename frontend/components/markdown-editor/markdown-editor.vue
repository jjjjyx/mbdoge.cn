<template>
    <div>
<!--        @click="handlerClickBtn"-->
        <ul :class="$style.toolsWarp" >
            <!--插入图片，插入表格，插入代码，语法帮助-->
            <!--专注模式，预览效果，保存 -->
            <tools-btn icon="icon-tupian1" name="image" v-popover:imagePopover></tools-btn>
            <tools-btn icon="icon-biaoge" name="table" v-popover:tablePopover></tools-btn>
            <tools-btn icon="icon-shujuguanlisvg40" name="code" ></tools-btn>
            <tools-btn icon="el-icon-help" name="help"></tools-btn>


            <tools-btn :class="$style.right" icon="icon-baocun" name="save"></tools-btn>
            <tools-btn :class="$style.right" icon="el-icon-view" name="preview" @click="handlerTogglePreview"></tools-btn>
            <tools-btn :class="$style.right" icon="icon-zhuanzhu" name="full-screen"></tools-btn>
        </ul>

        <div :class="$style.editorBody">
            <textarea ref="el" placeholder=""></textarea>
            <div class="markdown-body" :class="$style.preview" v-show="preview" ref="preview"></div>
        </div>
        <div :class="$style.statusBar">
            <span>
                <span>Markdown</span>
<!--                <span :class="$style.value">{{statusData.byteSize}}</span> <span>bytes</span>-->
                <span :class="$style.value">{{statusData.charSize}}</span> <span>length</span>
                <span :class="$style.value">{{statusData.lineCount}}</span> <span>lines</span>
                <span>Ln</span><span :class="$style.value">{{statusData.line}}</span>, Col <span :class="$style.value">{{statusData.ch}}</span>
            </span>
            <span :class="$style.right" style="float: right">已保存 (16:05)</span>
        </div>
        <el-popover ref="tablePopover" placement="bottom-start" :popper-class="$style.toolsPopoverPanel">
            <tools-inset-table></tools-inset-table>
        </el-popover>
        <el-popover ref="imagePopover" placement="bottom-start" :popper-class="$style.toolsPopoverPanel">
            22
        </el-popover>
    </div>

</template>

<script>

// import EditorTools from "./editor-tools";
import editor from "./editor";
import style from './markdown.scss?module'
import ToolsBtn from "./tools/tools-btn";
import ToolsInsetTable from "./tools/tools-insetTable";

/*
  必定是 强耦合的
  要考除成单独的组件，难度比较大

  尽量不耦合吧，不依赖 element 不依赖当前项目的一些组件
  目前想到的会依赖的地方 ：
  1.按钮的icon
  2.按的弹出
  3.图片上传
  4.一些 css 定义
 */

export default {
    name: "markdown-editor",
    data() {
        return {
            preview: false,
            statusData: {
                mode: 'Markdown',
                lineCount: 0,
                byteSize: 0,
                charSize: 0,
                line: 0,
                ch: 0
            }
        }
    },
    computed: {
        $style () {
            return style
        }
    },
    components: {ToolsInsetTable, ToolsBtn},
    provide(){

        return {
        }
    },
    methods: {
        handlerTogglePreview () {
            this.$refs.preview.innerHTML = editor.markdown()
            this.preview = !this.preview
        },
        handlerClickBtn (e) {
            const { target } = e
            if (target.nodeName !== "A") {
                return
            }
            const command = target.getAttribute("data-command")
            if (!command) {
                return;
            }
            editor.invoke(command)
        }
    },
    mounted () {
        editor.initialize(this.$refs.el)
        editor.on('cursorActivity', (instance) => {
            // line start: 0
            // Pos {line: 6, ch: 4, sticky: "before"}
            const cursor = instance.getCursor();
            this.statusData.line = cursor.line + 1
            this.statusData.ch = cursor.ch + 1
        })

        editor.on('update', (instance) => {
            this.statusData.lineCount = instance.lineCount()
            this.statusData.charSize = instance.getValue().length
            // this.statusData.byteSize = editor.getBytesSize()
        })
    }
}
</script>

