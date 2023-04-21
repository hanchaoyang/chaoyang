<template>
  <div style="background-color: #55a2fa; height: 100%; width: 100%; padding: 160px">
    <el-form ref="LoginForm" :model="form" size="medium" style="border-radius: 10px;box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);background-color: #FFFFFF; width: 440px; margin: auto auto; padding: 40px 40px 20px; position: relative">
      <el-form-item>
        <div>登录</div>
      </el-form-item>
      <el-form-item>
        <el-input v-model="form.phone" placeholder="手机号"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="form.password" type="password" placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item>
        <el-col :span="16" style="padding-right: 15px">
          <el-input v-model="form.captcha" placeholder="验证码"></el-input>
        </el-col>
        <el-col :span="8">
          <el-image :src="captchaUrl" fit="contain" @click="captcha()"></el-image>
        </el-col>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="login()" style="width: 100%">登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

export default {
  name: 'index',
  data() {
    return {
      nonce: null,
      captchaUrl: null,
      form: {
        phone: null,
        password: null,
        captcha: null
      }
    }
  },
  mounted() {
    this.captcha()
  },
  methods: {
    captcha: function() {
      this.nonce = new Date().getTime()
      this.captchaUrl = "http://localhost:18080/captcha?nonce=" + this.nonce
    },
    login: function() {
      const form = {
        nonce: this.nonce,
        phone: this.form.phone,
        password: this.form.password,
        captcha: this.form.captcha
      }

    }
  }
}
</script>

<style scoped>

</style>
