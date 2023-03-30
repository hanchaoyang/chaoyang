<template>
  <el-dialog title="添加" :visible.sync="visible" width="40%" @open="open()" @close="close()">
    <el-form ref="CreateDialogForm" :model="role" :rules="rules" size="medium" label-width="100px">
      <el-form-item label="角色名称" prop="roleName">
        <el-input v-model="role.roleName" style="width: 320px"></el-input>
      </el-form-item>
      <el-form-item label="角色标识" prop="roleCode">
        <el-input v-model="role.roleCode" style="width: 320px"></el-input>
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
import {create} from "@/api/role"

export default {
  name: "index",
  data() {
    return {
      visible: false,
      role: {
        roleName: null,
        roleCode: null
      },
      rules: {
        roleName: [
          { required: true, message: '请输入角色名称', trigger: 'blur' },
          { min: 1, max: 20, message: '角色名称长度为1-20个字符', trigger: 'blur' }
        ],
        roleCode: [
          { required: true, message: '请输入角色标识', trigger: 'blur' },
          { min: 1, max: 40, message: '角色标识长度为1-40个字符', trigger: 'blur' }
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
      this.role.roleName = null
      this.role.roleCode = null
      this.$refs.CreateDialogForm.resetFields();
    },
    create: function () {
      this.$refs.CreateDialogForm.validate(valid => {
        if (valid) {
          const data = {
            roleName: this.role.roleName,
            roleCode: this.role.roleCode
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
