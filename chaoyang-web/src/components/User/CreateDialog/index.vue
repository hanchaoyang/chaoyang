<template>
  <el-dialog title="添加" :visible.sync="visible" width="500px" @open="open()" @close="close()">
    <el-form ref="CreateDialogForm" :model="user" :rules="rules" size="medium" label-width="78px">
      <el-form-item label="昵称" prop="nickname">
        <el-input v-model="user.nickname" placeholder="请输入昵称" style="width: 320px"></el-input>
      </el-form-item>
      <el-form-item label="账号" prop="account">
        <el-input v-model="user.account" placeholder="请输入账号" style="width: 320px"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="user.password" placeholder="请输入密码" show-password style="width: 320px"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="user.status">
          <el-radio :label="1">启用</el-radio>
          <el-radio :label="0">禁用</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="create()">保存</el-button>
        <el-button @click="cancel()">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
import {create} from "@/api/user"

export default {
  name: "index",
  data() {
    return {
      visible: false,
      user: {
        nickname: null,
        account: null,
        password: null,
        status: 1
      },
      rules: {
        nickname: [
          { required: true, message: '请输入昵称', trigger: 'blur' },
          { min: 1, max: 20, message: '昵称长度为1-20个字符', trigger: 'blur' }
        ],
        account: [
          { required: true, message: '请输入账号', trigger: 'blur' },
          { min: 4, max: 40, message: '账号长度为4-40个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 20, message: '密码长度为6-20个字符', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '请选择状态', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    setVisible: function (target) {
      this.visible = target
    },
    open: function () {

    },
    close: function () {
      this.user.nickname = null
      this.user.account = null
      this.user.password = null
      this.user.status = 1
      this.$refs.CreateDialogForm.resetFields();
    },
    create: function () {
      this.$refs.CreateDialogForm.validate(valid => {
        if (valid) {
          const data = {
            nickname: this.user.nickname,
            account: this.user.account,
            password: this.user.password,
            status: this.user.status
          }
          create(data).then(result => {
            const {code, message} = result
            if (code === 200) {
              this.$message({
                message: message,
                type: 'success'
              })
              this.$emit("close")
              this.setVisible(false)
            }
          })
        }
      })
    },
    cancel: function () {
      this.setVisible(false)
    }
  }
}
</script>

<style scoped>

</style>
