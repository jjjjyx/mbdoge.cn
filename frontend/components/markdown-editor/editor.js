import Vue from 'vue'

import { fromTextArea } from 'codemirror'
import "codemirror/lib/codemirror.css"
import "codemirror/addon/edit/continuelist"
import "codemirror/addon/display/fullscreen"
import "codemirror/addon/display/placeholder"
import "codemirror/addon/mode/overlay"
import "codemirror/addon/selection/mark-selection"
import "codemirror/mode/markdown/markdown"
import "codemirror/mode/gfm/gfm"


import marked from 'marked'

// Get reference 自定义 renderer
const renderer = new marked.Renderer();


const wordPattern = /[a-zA-Z0-9_\u0392-\u03c9\u0410-\u04F9]+|[\u4E00-\u9FFF\u3400-\u4dbf\uf900-\ufaff\u3040-\u309f\uac00-\ud7af]+/g;
function wordCount(data) {
    const m = data.match(wordPattern);
    let count = 0;
    if(m === null) return count;

    for(let i = 0; i < m.length; i++) {
        if(m[i].charCodeAt(0) >= 0x4E00) {
            count += m[i].length;
        } else {
            count += 1;
        }
    }
    return count;
}

function getBytesLength (data) {
    let l = 0;
    for (let i = 0; i < data.length; i++) {
        if (data.charCodeAt(i) < 299) {
            l++;
        } else {
            l += 2;
        }
    }
    return l;
}

class Handler {
    constructor() {
    }

    undo(editor) {
        // this.$md.undo()
    }

    redo(editor) {
        // this.$md.redo()
    }

    preview (editor) {
        // this.$md.togglePreview()
    }
}


class Editor {
    constructor() {
        this.ready = false
        this.codemirror = null;
        this.handler = new Handler()
    }

    initialize (el) {
        if (this.ready) return

        this.codemirror = fromTextArea(el, {
            mode: {
                name: 'gfm',
                highlightFormatting: true,
                // gitHubSpice: false,
            },
            theme: "paper",
            // lineNumbers: true,
            tabSize: 4,
            indentUnit: 4,
            indentWithTabs: true,
            autofocus: true,
            lineWrapping: true,
            allowDropFileTypes: ["text/plain"],
            placeholder: el.getAttribute("placeholder") || ""
        });

        this.ready = true
    }
    checkStatus () {
        if (!this.ready) {
            throw new Error("editor Not ready!")
        }
    }
    on (event, callback) {
        this.checkStatus()
        this.codemirror.on(event, callback)
    }

    invoke (command) {
        this.checkStatus()
        const handle = this.handler[command]
        if (typeof handle === 'function') {
            handle(this.codemirror)
        }
    }

    getBytesSize() {
        return getBytesLength(this.codemirror.getValue())
    }

    getValue () {
        this.checkStatus()
        return this.codemirror.getValue()
    }

    markdown () {
        const value = this.codemirror.getValue();
        return marked(value, { renderer })
    }
}

const editor = new Editor();

export default editor;
