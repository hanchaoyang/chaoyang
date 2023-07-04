<template>
  <el-dialog title="编辑" :visible.sync="visible" width="500px" @open="open()" @close="close()">
    <el-form ref="CreateDialogForm" :model="permission" :rules="rules" size="medium" label-width="78px">
      <el-form-item label="名称" prop="name">
        <el-input v-model="permission.name" placeholder="请输入名称" style="width: 320px"></el-input>
      </el-form-item>
      <el-form-item label="标识" prop="code">
        <el-input v-model="permission.code" placeholder="请输入标识" style="width: 320px"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="modify()">保存</el-button>
        <el-button @click="cancel()">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
import {find, modify} from "@/api/permission"

export default {
  name: "index",
  props: {
    permissionId: {
      type: Number
    }
  },
  data() {
    return {
      visible: false,
      permission: {
        id: null,
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
      this.init()
    },
    close: function () {
      this.permission.id = null
      this.permission.name = null
      this.permission.code = null
      this.$refs.CreateDialogForm.resetFields();
    },
    init: function () {
      const params = {
        id: this.permissionId
      }
      find(params).then(result => {
        const {data} = result
        const {id, name, code} = data
        this.permission.id = id
        this.permission.name = name
        this.permission.code = code
      })
    },
    modify: function () {
      this.$refs.CreateDialogForm.validate(valid => {
        if (valid) {
          const data = {
            id: this.permission.id,
            name: this.permission.name,
            code: this.permission.code
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
