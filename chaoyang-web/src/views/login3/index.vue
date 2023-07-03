<template>
  <div class="container">
    <el-form ref="form" :model="form" :rules="rules" class="form">
      <div class="title">后台管理系统</div>
      <el-form-item prop="phone">
        <el-input type="text" v-model="form.account" prefix-icon="el-icon-user" placeholder="账号"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password" v-model="form.password" prefix-icon="el-icon-key" show-password placeholder="密码"></el-input>
      </el-form-item>
      <el-row :gutter="20">
        <el-col :span="15">
          <el-form-item prop="captcha">
            <el-input type="text" v-model="form.captcha" prefix-icon="el-icon-lock" placeholder="验证码" @keyup.enter.native="login()"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="9">
          <el-image :src.sync="captchaUrl" fit="contain" @click="captcha()"></el-image>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button type="primary" :loading="loading" class="button" @click="login()" @keyup.enter.native="login()">登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'index',
  data() {
    return {
      captchaUrl: null,
      form: {
        nonce: null,
        account: 'admin',
        password: '123456',
        captcha: null
      },
      rules: {
        account: [
          { required: true, message: '请输入账号', trigger: 'blur' },
          { min: 1, max: 20, message: '账号长度为1-32个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 1, max: 20, message: '密码长度为1-32个字符', trigger: 'blur' }
        ],
        captcha: [
          { required: true, message: '请输入验证码', trigger: 'blur' },
          { min: 1, max: 20, message: '验证码长度为1-4个字符', trigger: 'blur' }
        ]
      },
      loading: false,
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  mounted() {
    this.captcha()
  },
  beforeDestroy() {
    this.$refs.form.resetFields()
  },
  methods: {
    captcha: function() {
      this.form.nonce = new Date().getTime()
      this.captchaUrl = process.env.VUE_APP_BASE_API + '/captcha?nonce=' + this.form.nonce
    },
    login: function() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('user/login', this.form).then(() => {
            this.$router.push({ path: this.redirect || '/' })
            this.loading = false
          }).catch(() => {
            this.loading = false
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.container {
  width: 100%;
  min-height: 100%;
  background-color: #111827;
}

.form {
  position: relative;
  width: 450px;
  max-width: 100%;
  padding: 160px 35px 0;
  margin: 0 auto;
  overflow: hidden;
}

.title {
  color: #DDDDDD;
  font-size: 24px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 32px;
}

/deep/ .el-input__inner {
  background-color: #1D2432 !important;
  border: 1px solid #343A47;
  color: #FFFFFF;
}

.button {
  width: 100%;
}
</style>
