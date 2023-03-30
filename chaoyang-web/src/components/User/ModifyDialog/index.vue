<template>
  <el-dialog title="编辑" :visible.sync="visible" width="40%" @open="open()" @close="close()">
    <el-form ref="CreateDialogForm" :model="user" :rules="rules" size="medium" label-width="100px">
      <el-form-item label="用户名称" prop="userNickname">
        <el-input v-model="user.userNickname" style="width: 320px"></el-input>
      </el-form-item>
      <el-form-item label="用户手机号" prop="userPhone">
        <el-input v-model="user.userPhone" style="width: 320px"></el-input>
      </el-form-item>
      <el-form-item label="用户密码" prop="userPassword">
        <el-input v-model="user.userPassword" show-password style="width: 320px"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input v-model="user.confirmPassword" show-password style="width: 320px"></el-input>
      </el-form-item>
      <el-form-item label="用户状态" prop="userStatus">
        <el-radio-group v-model="user.userStatus">
          <el-radio :label="0">禁用</el-radio>
          <el-radio :label="1">启用</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="modify()">保存</el-button>
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
import {find, modify} from "@/api/user"

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
        userId: null,
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
          { min: 11, max: 11, message: '用户手机号长度为11个字符', trigger: 'blur' }
        ],
        userPassword: [
          { required: true, message: '请输入用户密码', trigger: 'blur' },
          { min: 6, max: 20, message: '用户密码长度为6-20个字符', trigger: 'blur' }
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
      this.init()
    },
    close: function () {
      this.user.userId = null
      this.user.userNickname = null
      this.user.userPhone = null
      this.user.userPassword = null
      this.user.confirmPassword = null
      this.user.userStatus = null
      this.$refs.CreateDialogForm.resetFields();
    },
    init: function () {
      const params = {
        userId: this.userId
      }
      find(params).then(result => {
        const {data} = result
        const {userId, userNickname, userPhone, userStatus} = data
        this.user.userId = userId
        this.user.userNickname = userNickname
        this.user.userPhone = userPhone
        this.user.userStatus = userStatus
        // alert(JSON.stringify(this.user))
      })
    },
    modify: function () {
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
            userId: this.user.userId,
            userNickname: this.user.userNickname,
            userPhone: this.user.userPhone,
            userPassword: this.user.userPassword,
            userStatus: this.user.userStatus
          }
          modify(data).then(result => {
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
