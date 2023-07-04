<template>
  <div>
    <el-dialog title="权限" :visible.sync="visible" width="800px" @open="open()" @close="close()">
      <div>
        <el-button type="success" size="medium" @click="openInactivePermissionDialog()">添加</el-button>
      </div>
      <el-table :data="rolePermissions" size="medium" border stripe style="margin-top: 20px">
        <el-table-column type="index" width="100" label="#"></el-table-column>
        <el-table-column prop="permissionName" label="名称" min-width="100"></el-table-column>
        <el-table-column prop="permissionCode" label="标识" min-width="100"></el-table-column>
        <el-table-column fixed="right" label="操作" width="120">
          <template slot-scope="scope">
            <el-button type="danger" size="medium" @click="remove(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination layout="prev, pager, next" :current-page.sync="page.current" :page-size="page.size" :total="page.total" background style="margin-top: 20px;" @current-change="currentChange()"></el-pagination>
    </el-dialog>
    <inactive-permission-dialog ref="InactivePermissionDialog" :role-id="dialog.inactivePermission.roleId" @close="getPage()"></inactive-permission-dialog>
  </div>
</template>

<script>
import InactivePermissionDialog from "@/components/Role/RolePermissionDialog/InactivePermissionDialog"
import {findPage, remove} from "@/api/role-permission"

export default {
  name: "index",
  components: {
    InactivePermissionDialog
  },
  props: {
    roleId: {
      type: Number
    }
  },
  data() {
    return {
      visible: false,
      rolePermissions: [],
      page: {
        current: 1,
        size: 4,
        total: 0,
        pages: 0
      },
      dialog: {
        inactivePermission: {
          roleId: null
        }
      }
    }
  },
  methods: {
    setVisible: function (target) {
      this.visible = target
    },
    open: function () {
      this.getPage()
    },
    close: function () {
      this.rolePermissions = []
      this.page.current = 1
      this.page.size = 4
      this.page.total = 0
      this.page.pages = 0
    },
    currentChange: function () {
      this.getPage()
    },
    getPage: function () {
      const params = {
        roleId: this.roleId,
        current: this.page.current,
        size: this.page.size
      }
      findPage(params).then(result => {
        const {data} = result
        const {current, size, total, pages, records: rolePermissions} = data
        this.page.current = current
        this.page.size = size
        this.page.total = total
        this.page.pages = pages
        this.rolePermissions = rolePermissions
      })
    },
    openInactivePermissionDialog: function () {
      this.dialog.inactivePermission.roleId = this.roleId
      this.$refs.InactivePermissionDialog.setVisible(true)
    },
    remove: function (rolePermission) {
      const params = {
        roleId: rolePermission.roleId,
        permissionId: rolePermission.permissionId
      }
      remove(params).then(result => {
        const {code, message} = result
        if (code === 200) {
          this.$message({
            message: message,
            type: 'success'
          })
          if (this.rolePermissions.length === 1 && this.page.current > 1) {
            this.page.current -= 1
          }
          this.getPage()
        }
      })
    },
    cancel: function () {
      this.setVisible(false)
    },
    done: function () {
      this.setVisible(false)
    }
  }
}
</script>

<style scoped>

</style>
