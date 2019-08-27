<template>
    <div class="login-container">
        <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">
            <div class="title-container">
                <h3 class="title">Login Form</h3>
            </div>
            <el-form-item prop="username">
                <span class="svg-container">
                  <i class="el-icon-user-solid"></i>
                </span>
                <el-input
                    ref="username"
                    v-model="loginForm.username"
                    placeholder="Username"
                    name="username"
                    type="text"
                    tabindex="1"
                    auto-complete="on"
                />
            </el-form-item>

            <el-form-item prop="password">
                <span class="svg-container">
                  <i class="el-icon-key"></i>
                </span>
                <el-input
                    :key="passwordType"
                    ref="password"
                    v-model="loginForm.password"
                    :type="passwordType"
                    placeholder="Password"
                    name="password"
                    tabindex="2"
                    auto-complete="on"
                    @keyup.enter.native="handleLogin"
                />
                <span class="show-pwd" @click="showPwd">
          <!--<svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"/>-->
                    <i :class="passwordType ? 'el-icon-mouse' : 'el-icon-view'"></i>
        </span>
            </el-form-item>

            <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;" @click.native.prevent="handleLogin">Login</el-button>

            <div class="tips">
                <span>尝试解密：</span>
                <span>ZTY4YWIxZTZhZDg5ZWZiYzhjZTg5OWJkZTc4NGI2ZTRiZGEwZTY4OWJlZTU4OGIwZTRiYTg2ZThiZjk5ZTRiOGFhZTlhMWI1ZTk5ZGEyZWZiYzhjZTRiZDg2ZTY5OGFmZThiZjk5ZTY5OGFmZTViMTllZTRiYThlZTY4ODkxZTRiOGFhZTRiYWJhZTc5YTg0ZWZiYzhjZTRiODhkZTY4ZjkwZTRiZTliZTZiM2E4ZTU4NjhjN2VlNTljYThlOGFmYjRlNGI4ODBlOTgxOGRlNmIyYTFlNjljODk=</span>
            </div>

        </el-form>
    </div>
</template>

<script>
import { mapActions } from 'vuex'
function validUsername () {

}

export default {
    layout: 'admin',
    name: 'login',
    fetch ({ store, redirect }) {
        if (store.getters['user/isLogin']) {
            return redirect('./admin')
        }
    },
    data () {
        return {
            loginForm: {
                username: '',
                password: ''
            },
            loginRules: {
                username: [
                    { required: true, message: '请输入账号', trigger: 'blur' },
                    { min: 5, max: 18, message: '长度在 5 到 18 个字符', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    { min: 5, max: 32, message: '长度在 5 到 32 个字符', trigger: 'blur' }
                ]
            },
            loading: false,
            passwordType: 'password',
            redirect: undefined
        }
    },
    watch: {
        $route: {
            handler: function (route) {
                this.redirect = route.query && route.query.redirect
            },
            immediate: true
        }
    },
    methods: {
        ...mapActions('user', [
            'login'
        ]),
        showPwd () {
            if (this.passwordType === 'password') {
                this.passwordType = ''
            } else {
                this.passwordType = 'password'
            }
            this.$nextTick(() => {
                this.$refs.password.focus()
            })
        },
        async handleLogin () {
            this.loading = true
            try {
                let valid = await this.$refs.loginForm.validate()
                if (valid) {
                    await this.login(this.loginForm)
                    this.$message({
                        message: '登录成功',
                        type: 'success'
                    })
                    if (this.redirect) {
                        this.$router.push({ path: this.redirect})
                    } else {
                        this.$router.push({ name: '6tw9sQs-admin'})
                    }
                }
            } catch (e) {
            } finally {
                this.loading = false
            }

        }
    }
}
</script>
<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg: #283443;
$light_gray: #fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
    .login-container .el-input input {
        color: $cursor;
    }
}

/* reset element-ui css */
.login-container {
    .el-input {
        display: inline-block;
        height: 47px;
        width: 85%;

        input {
            background: transparent;
            border: 0px;
            -webkit-appearance: none;
            border-radius: 0px;
            padding: 12px 5px 12px 15px;
            color: $light_gray;
            height: 47px;
            caret-color: $cursor;

            &:-webkit-autofill {
                box-shadow: 0 0 0px 1000px $bg inset !important;
                -webkit-text-fill-color: $cursor !important;
            }
        }
    }

    .el-form-item {
        border: 1px solid rgba(255, 255, 255, 0.1);
        background: rgba(0, 0, 0, 0.1);
        border-radius: 5px;
        color: #454545;
    }
}
</style>

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.login-container {
    min-height: 100vh;
    width: 100%;
    background-color: $bg;
    overflow: hidden;

    .login-form {
        position: relative;
        width: 520px;
        max-width: 100%;
        padding: 160px 35px 0;
        margin: 0 auto;
        overflow: hidden;
    }

    .tips {
        font-size: 14px;
        color: #fff;
        margin-bottom: 10px;

        span {
            &:first-of-type {
                margin-right: 16px;
            }
        }
    }

    .svg-container {
        padding: 6px 5px 6px 15px;
        color: $dark_gray;
        vertical-align: middle;
        width: 30px;
        display: inline-block;
    }

    .title-container {
        position: relative;

        .title {
            font-size: 26px;
            color: $light_gray;
            margin: 0px auto 40px auto;
            text-align: center;
            font-weight: bold;
        }
    }

    .show-pwd {
        position: absolute;
        right: 10px;
        top: 7px;
        font-size: 16px;
        color: $dark_gray;
        cursor: pointer;
        user-select: none;
    }
}
</style>
