<template>
  <el-dialog title="添加" :visible.sync="visible" width="40%" @open="open()" @close="close()">
    <el-form ref="CreateDialogForm" :model="user" :rules="rules" size="medium" label-width="100px">
      <el-form-item label="用户昵称" prop="userNickname">
        <el-input v-model="user.userNickname" style="width: 320px"></el-input>
      </el-form-item>
      <el-form-item label="用户手机号" prop="userPhone">
        <el-input v-model="user.userPhone" style="width: 320px"></el-input>
      </el-form-item>
      <el-form-item label="用户密码" prop="userPassword">
        <el-input v-model="user.userPassword" show-password style="width: 320px"></el-input>
      </el-form-item>
      <el-form-item label="用户密码" prop="confirmPassword">
        <el-input v-model="user.confirmPassword" show-password style="width: 320px"></el-input>
      </el-form-item>
      <el-form-item label="用户状态" prop="userStatus">
        <el-radio-group v-model="user.userStatus">
          <el-radio label="0">禁用</el-radio>
          <el-radio label="1">启用</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="create()">保存</el-button>
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
import {create} from "@/api/user"

export default {
  name: "index",
  data() {
    return {
      visible: false,
      user: {
        userNickname: null,
        userPhone: null,
        userPassword: null,
        confirmPassword: null,
        userStatus: null
      },
      rules: {
        userNickname: [
          { required: true, message: '请输入用户昵称', trigger: 'blur' },
          { min: 1, max: 20, message: '用户昵称长度为1-20个字符', trigger: 'blur' }
        ],
        userPhone: [
          { required: true, message: '请输入用户手机号', trigger: 'blur' },
          { min: 11, max: 11, message: '用户昵称长度为11个字符', trigger: 'blur' }
        ],
        userPassword: [
          { required: true, message: '请输入用户密码', trigger: 'blur' },
          { min: 6, max: 20, message: '用户昵称长度为6-20个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请输入确认密码', trigger: 'blur' },
          { min: 6, max: 20, message: '确认密码长度为6-20个字符', trigger: 'blur' }
        ],
        userStatus: [
          { required: true, message: '请选择用户状态', trigger: 'blur' }
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
      this.user.userNickname = null
      this.user.userPhone = null
      this.user.userPassword = null
      this.user.confirmPassword = null
      this.user.userStatus = null
      this.$refs.CreateDialogForm.resetFields();
    },
    create: function () {
      this.$refs.CreateDialogForm.validate(valid => {
        if (valid) {
          if (this.user.userPassword !== this.user.confirmPassword) {
            this.$message({
              message: "两次密码输入不一致",
              type: 'error'
            })
            return
          }
          const data = {
            userNickname: this.user.userNickname,
            userPhone: this.user.userPhone,
            userPassword: this.user.userPassword,
            userStatus: this.user.userStatus
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
