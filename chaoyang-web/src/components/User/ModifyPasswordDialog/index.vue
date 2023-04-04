<template>
  <el-dialog title="修改密码" :visible.sync="visible" width="40%" @open="open()" @close="close()">
    <el-form ref="ModifyPasswordDialogForm" :model="user" :rules="rules" size="medium" label-width="100px">
      <el-form-item label="用户密码" prop="userPassword">
        <el-input v-model="user.userPassword" show-password style="width: 320px"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input v-model="user.confirmPassword" show-password style="width: 320px"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="modifyPassword()">保存</el-button>
        <el-button @click="cancel()">取消</el-button>
      </el-form-item>
    </el-form>
<!--    <div slot="footer">-->
<!--      <el-button size="medium" @click="cancel()">取消</el-button>-->
<!--      <el-button type="primary" size="medium" @click="done()">确定</el-button>-->
<!--    </div>-->
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
        userPassword: null,
        confirmPassword: null
      },
      rules: {
        userPassword: [
          { required: true, message: '请输入用户密码', trigger: 'blur' },
          { min: 6, max: 20, message: '用户密码长度为6-20个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请输入确认密码', trigger: 'blur' },
          { min: 6, max: 20, message: '确认密码长度为6-20个字符', trigger: 'blur' }
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
      this.user.userPassword = null
      this.user.confirmPassword = null
      this.$refs.ModifyPasswordDialogForm.resetFields();
    },
    modifyPassword: function () {
      this.$refs.ModifyPasswordDialogForm.validate(valid => {
        if (valid) {
          if (this.user.userPassword !== this.user.confirmPassword) {
            this.$message({
              message: "两次密码输入不一致",
              type: 'error'
            })
            return
          }
          const data = {
            userId: this.userId,
            userPassword: this.user.userPassword
          }
          modifyPassword(data).then(result => {
            const {code, message} = result
            if (code === 200) {
              this.$message({
                message: message,
                type: 'success'
              })
              // this.$emit("close")
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
