<template>
    <transition name="transition-drop" >
        <ul v-show="visible" :style="menuStyles" :class="$style.menu" @contextmenu.prevent>
            <slot></slot>
        </ul>
    </transition>
</template>

<script>
export default {
	name: 'contextmenu',
    data () {
	    return {
            visible: false,
            x: 0,
            y: 0,
            originEvent: null
        }
    },
    computed: {
        menuStyles () {
            return {
                top: this.y + 'px',
                left: this.x + 'px'
            }
        }
    },
    methods: {
	    show(e) {
            this.$emit('before-open', e)
            e.preventDefault()
            // e.stopPropagation()
            let { clientX, clientY } = e
            this.x = clientX
            this.y = clientY
            this.originEvent = e
            this.visible = true
        },
	    hide() {
	        this.visible = false
        }

    },
    mounted () {
        this.$on('on-click-item', (key) => {
            // if (this.stopPropagation) return;
            // const $parent = this.hasParent();
            // if ($parent) $parent.$emit('on-click', key);
            this.$emit('on-click', key, this.originEvent);
        });

        this.$on('on-hover-click', () => {
            this.$nextTick(() => {
                // if (this.trigger === 'custom') return false;
                this.visible = false;
            });
            // const $parent = this.hasParent();
            // if ($parent) $parent.$emit('on-haschild-click');
        });
	}
}
</script>
<style>
.transition-drop-appear, .transition-drop-enter-active, .transition-drop-leave-active {
    transform-origin: center top 0;
    animation-duration: .1s;
    animation-fill-mode: both;
    animation-play-state: paused
}

.transition-drop-appear, .transition-drop-enter-active {
    animation-name: ivuTransitionDropIn;
    animation-play-state: running
}

.transition-drop-leave-active {
    animation-name: transitionDropOut;
    animation-play-state: running
}

.transition-drop-appear, .transition-drop-enter-active {
    opacity: 0
}

.transition-drop-appear, .transition-drop-enter-active, .transition-drop-leave-active {
    animation-timing-function: ease-in-out
}

@keyframes transitionDropOut {
    0% {
        opacity: 1;
        transform: scaleY(1)
    }

    to {
        opacity: 0;
        transform: scaleY(.8)
    }
}
</style>
<style module lang='scss'>
.menu {
    position: fixed;
    z-index: 3000;
    min-width: 200px;
    /*width: 100px;*/
    /*height: 200px;*/
    padding: 5px 0;
    margin: 5px 0;
    background-color: #fff;
    border: 1px solid #ebeef5;
    border-radius: 0;
    box-shadow: 0 2px 12px 0 rgba(0,0,0, .1);

    li {
        list-style: none;
    }
}
</style>
