<template>
  <el-dialog title="添加" :visible.sync="visible" width="40%" @open="open()" @close="close()">
    <el-form ref="CreateDialogForm" :model="user" :rules="rules" size="medium" label-width="100px">
      <el-form-item label="用户名称" prop="userName">
        <el-input v-model="user.userName" style="width: 320px"></el-input>
      </el-form-item>
      <el-form-item label="用户标识" prop="userCode">
        <el-input v-model="user.userCode" style="width: 320px"></el-input>
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
        userName: null,
        userCode: null
      },
      rules: {
        userName: [
          { required: true, message: '请输入用户名称', trigger: 'blur' },
          { min: 1, max: 20, message: '用户名称长度为1-20个字符', trigger: 'blur' }
        ],
        userCode: [
          { required: true, message: '请输入用户标识', trigger: 'blur' },
          { min: 1, max: 40, message: '用户标识长度为1-40个字符', trigger: 'blur' }
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
      this.user.userName = null
      this.user.userCode = null
      this.$refs.CreateDialogForm.resetFields();
    },
    create: function () {
      this.$refs.CreateDialogForm.validate(valid => {
        if (valid) {
          const data = {
            userName: this.user.userName,
            userCode: this.user.userCode
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
