<template>
  <el-dialog title="添加" :visible.sync="visible" width="40%" @open="open()" @close="close()">
    <el-form ref="CreateDialogForm" :model="permission" :rules="rules" size="medium" label-width="100px">
      <el-form-item label="权限名称" prop="permissionName">
        <el-input v-model="permission.permissionName" style="width: 320px"></el-input>
      </el-form-item>
      <el-form-item label="权限标识" prop="permissionCode">
        <el-input v-model="permission.permissionCode" style="width: 320px"></el-input>
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
import {create} from "@/api/permission"

export default {
  name: "index",
  data() {
    return {
      visible: false,
      permission: {
        permissionName: null,
        permissionCode: null
      },
      rules: {
        permissionName: [
          { required: true, message: '请输入权限名称', trigger: 'blur' },
          { min: 1, max: 20, message: '权限名称长度为1-20个字符', trigger: 'blur' }
        ],
        permissionCode: [
          { required: true, message: '请输入权限标识', trigger: 'blur' },
          { min: 1, max: 40, message: '权限标识长度为1-40个字符', trigger: 'blur' }
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
      this.permission.permissionName = null
      this.permission.permissionCode = null
      this.$refs.CreateDialogForm.resetFields();
    },
    create: function () {
      this.$refs.CreateDialogForm.validate(valid => {
        if (valid) {
          const data = {
            permissionName: this.permission.permissionName,
            permissionCode: this.permission.permissionCode
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
