<template>
    <div>
        <editor-tools></editor-tools>
        <div :class="$style.markdownEditor">
            <textarea ref="el"></textarea>
        </div>
        <div :class="$style.statusBar">
            <span>Markdown</span>
            <span :class="$style.value">7186</span> <span>bytes</span>
            <span :class="$style.value">1088</span> <span>words</span>
            <span :class="$style.value">145</span> <span>lines</span>
            <span>Ln</span><span :class="$style.value">21</span>, Col <span :class="$style.value">0</span>
        </div>
    </div>

</template>

<script>
import editor from "./editor";
/**
 * 迟早我会完全的吧这个编辑器抄过来！！！！！！！
 */
import EditorTools from "./editor-tools";

export default {
    name: "markdown-editor",
    components: {EditorTools},
    data() {
        return {
        }
    },
    provide(){
        // pageHeaderDesc () {
        //     return tipMsg[this.tip]
        // }

        return {
        }
    },
    methods: {
    },
    mounted () {
        editor.initialize(this.$refs.el)
    }
}
</script>

<style module lang="scss">

.contentWarp {

}
.markdownEditor {
    flex: 1;
    height: 0;
    overflow: auto;
    background-color: white;
    textarea {
        opacity: 0;
    }
    :global {
        .CodeMirror {
            /*height: calc(100% - 20px);*/
            height: 100%;
            border: none;
        }
        .CodeMirror-fullscreen {
            top: 40px;
            z-index: 9999!important;
        }
    }


}
.statusBar {
    height: 20px;
    color: #595959;
    /*margin: 0 10px;*/
    padding: 0 10px;
    font-size: 12px;
    line-height: 20px;
    background-color: $--color-info-light;
    .value {
        font-weight: 600;
        margin-left: 5px;
    }
}

</style>
