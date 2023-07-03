<template>
  <el-dialog title="添加角色" :visible.sync="visible" width="500px" @open="open()" @close="close()">
    <el-form ref="CreateDialogForm" :model="role" :rules="rules" size="medium" label-width="78px">
      <el-form-item label="名称" prop="name">
        <el-input v-model="role.name" placeholder="请输入名称" style="width: 320px"></el-input>
      </el-form-item>
      <el-form-item label="标识" prop="code">
        <el-input v-model="role.code" placeholder="请输入标识" style="width: 320px"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="create()">保存</el-button>
        <el-button @click="cancel()">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
import {create} from "@/api/role"

export default {
  name: "index",
  data() {
    return {
      visible: false,
      role: {
        name: null,
        code: null
      },
      rules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' },
          { min: 1, max: 20, message: '名称长度为1-20个字符', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入标识', trigger: 'blur' },
          { min: 1, max: 40, message: '标识长度为1-40个字符', trigger: 'blur' }
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
      this.role.name = null
      this.role.code = null
      this.$refs.CreateDialogForm.resetFields();
    },
    create: function () {
      this.$refs.CreateDialogForm.validate(valid => {
        if (valid) {
          const data = {
            name: this.role.name,
            code: this.role.code
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
