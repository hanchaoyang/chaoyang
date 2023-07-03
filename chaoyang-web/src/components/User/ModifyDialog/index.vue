<template>
  <el-dialog title="编辑" :visible.sync="visible" width="500px" @open="open()" @close="close()">
    <el-form ref="ModifyDialogForm" :model="user" :rules="rules" size="medium" label-width="78px">
      <el-form-item label="昵称" prop="nickname">
        <el-input v-model="user.nickname" placeholder="请输入昵称" style="width: 320px"></el-input>
      </el-form-item>
      <el-form-item label="账号" prop="account">
        <el-input v-model="user.account" placeholder="请输入账号" style="width: 320px"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="modify()">保存</el-button>
        <el-button @click="cancel()">取消</el-button>
      </el-form-item>
    </el-form>
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
        id: null,
        nickname: null,
        account: null
      },
      rules: {
        nickname: [
          { required: true, message: '请输入昵称', trigger: 'blur' },
          { min: 1, max: 20, message: '昵称长度为1-20个字符', trigger: 'blur' }
        ],
        account: [
          { required: true, message: '请输入账号', trigger: 'blur' },
          { min: 4, max: 40, message: '账号长度为4-40个字符', trigger: 'blur' }
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
      this.user.id = null
      this.user.nickname = null
      this.user.account = null
      this.$refs.ModifyDialogForm.resetFields();
    },
    init: function () {
      const params = {
        id: this.userId
      }
      find(params).then(result => {
        const {data} = result
        const {id, nickname, account} = data
        this.user.id = id
        this.user.nickname = nickname
        this.user.account = account
      })
    },
    modify: function () {
      this.$refs.ModifyDialogForm.validate(valid => {
        if (valid) {
          const data = {
            id: this.user.id,
            nickname: this.user.nickname,
            account: this.user.account
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
