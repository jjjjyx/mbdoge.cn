<template>
    <div class="container" v-if="loading">
        <div clsas="logo">
            <logo style="width: 50px;margin-bottom: 30px;"></logo>
        </div>
        <div class="la-ball-climbing-dot la-dark" style="margin-bottom: 10px">
            <div></div>
            <div></div>
            <div></div>
            <div></div>
        </div>
<!--        <span>loading...</span>-->
    </div>
</template>

<script>
import Logo from "@/components/logo";
export default {
	name: "loading",
    data: () => ({
        loading: false
    }),
    components: {Logo},
    methods: {
        start () {
            this.loading = true
        },
        finish () {
            this.loading = false
        }
    }
}
</script>

<style scoped>
.container {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: white;
    z-index: 1040;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

/*!
 * Load Awesome v1.1.0 (http://github.danielcardoso.net/load-awesome/)
 * Copyright 2015 Daniel Cardoso <@DanielCardoso>
 * Licensed under MIT
 */
.la-ball-climbing-dot,
.la-ball-climbing-dot > div {
    position: relative;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}
.la-ball-climbing-dot {
    display: block;
    font-size: 0;
    color: #fff;
}
.la-ball-climbing-dot.la-dark {
    color: #333;
}
.la-ball-climbing-dot > div {
    display: inline-block;
    float: none;
    background-color: currentColor;
    border: 0 solid currentColor;
}
.la-ball-climbing-dot {
    width: 42px;
    height: 32px;
}
.la-ball-climbing-dot > div:nth-child(1) {
    position: absolute;
    bottom: 32%;
    left: 18%;
    width: 14px;
    height: 14px;
    border-radius: 100%;
    transform-origin: center bottom;
    animation: ball-climbing-dot-jump .6s ease-in-out infinite;
}
.la-ball-climbing-dot > div:not(:nth-child(1)) {
    position: absolute;
    top: 0;
    right: 0;
    width: 14px;
    height: 2px;
    border-radius: 0;
    transform: translate(60%, 0);

    animation: ball-climbing-dot-steps 1.8s linear infinite;
}
.la-ball-climbing-dot > div:not(:nth-child(1)):nth-child(2) {
    animation-delay: 0ms;
}
.la-ball-climbing-dot > div:not(:nth-child(1)):nth-child(3) {
    animation-delay: -600ms;
}
.la-ball-climbing-dot > div:not(:nth-child(1)):nth-child(4) {
    animation-delay: -1200ms;
}
.la-ball-climbing-dot.la-sm {
    width: 20px;
    height: 16px;
}
.la-ball-climbing-dot.la-sm > div:nth-child(1) {
    width: 6px;
    height: 6px;
}
.la-ball-climbing-dot.la-sm > div:not(:nth-child(1)) {
    width: 6px;
    height: 1px;
}
.la-ball-climbing-dot.la-2x {
    width: 84px;
    height: 64px;
}
.la-ball-climbing-dot.la-2x > div:nth-child(1) {
    width: 28px;
    height: 28px;
}
.la-ball-climbing-dot.la-2x > div:not(:nth-child(1)) {
    width: 28px;
    height: 4px;
}
.la-ball-climbing-dot.la-3x {
    width: 126px;
    height: 96px;
}
.la-ball-climbing-dot.la-3x > div:nth-child(1) {
    width: 42px;
    height: 42px;
}
.la-ball-climbing-dot.la-3x > div:not(:nth-child(1)) {
    width: 42px;
    height: 6px;
}
/*
 * Animations
 */

@keyframes ball-climbing-dot-jump {
    0% {
        transform: scale(1, .7);
    }
    20% {
        transform: scale(.7, 1.2);
    }
    40% {
        transform: scale(1, 1);
    }
    50% {
        bottom: 125%;
    }
    46% {
        transform: scale(1, 1);
    }
    80% {
        transform: scale(.7, 1.2);
    }
    90% {
        transform: scale(.7, 1.2);
    }
    100% {
        transform: scale(1, .7);
    }
}

@keyframes ball-climbing-dot-steps {
    0% {
        top: 0;
        right: 0;
        opacity: 0;
    }
    50% {
        opacity: 1;
    }
    100% {
        top: 100%;
        right: 100%;
        opacity: 0;
    }
}
</style>
