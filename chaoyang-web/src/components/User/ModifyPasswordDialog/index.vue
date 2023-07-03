<template>
  <el-dialog title="修改密码" :visible.sync="visible" width="500px" @open="open()" @close="close()">
    <el-form ref="ModifyPasswordDialogForm" :model="user" :rules="rules" size="medium" label-width="78px">
      <el-form-item label="密码" prop="password">
        <el-input v-model="user.password" placeholder="请输入密码" show-password style="width: 320px"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="modifyPassword()">保存</el-button>
        <el-button @click="cancel()">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
import {modifyPassword} from "@/api/user"

export default {
  name: "index",
  props: {
    userId: {
      type: Number
    }
  },
  data() {
    return {
      visible: false,
      user: {
        password: null
      },
      rules: {
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 20, message: '密码长度为6-20个字符', trigger: 'blur' }
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
      this.user.password = null
      this.$refs.ModifyPasswordDialogForm.resetFields();
    },
    modifyPassword: function () {
      this.$refs.ModifyPasswordDialogForm.validate(valid => {
        if (valid) {
          const data = {
            id: this.userId,
            password: this.user.password
          }
          modifyPassword(data).then(result => {
            const {code, message} = result
            if (code === 200) {
              this.$message({
                message: message,
                type: 'success'
              })
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
