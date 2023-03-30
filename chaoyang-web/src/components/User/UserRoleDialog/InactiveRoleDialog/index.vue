<template>
  <el-dialog title="添加" :visible.sync="visible" width="50%" append-to-body @open="open()" @close="close()">
    <el-table :data="permissions" size="medium" empty-text="暂无数据" border stripe style="margin-top: 20px">
      <el-table-column type="index" width="100" label="#"></el-table-column>
      <el-table-column prop="permissionName" label="名称" min-width="100"></el-table-column>
      <el-table-column prop="permissionCode" label="标识" min-width="100"></el-table-column>
      <el-table-column fixed="right" label="操作" width="120">
        <template slot-scope="scope">
          <el-button type="success" size="medium" @click="create(scope.row)">添加</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination layout="prev, pager, next" :current-page.sync="page.current" :page-size="page.size" :total="page.total" background style="margin-top: 20px;" @current-change="currentChange()"></el-pagination>
<!--    <div slot="footer">-->
<!--      <el-button size="medium" @click="cancel()">取消</el-button>-->
<!--      <el-button type="primary" size="medium" @click="done()">确定</el-button>-->
<!--    </div>-->
  </el-dialog>
</template>

<script>
import {findInactivePermissionPage} from "@/api/permission"
import {create} from "@/api/role-permission"

export default {
  name: "index",
  props: {
    roleId: {
      type: Number
    }
  },
  data() {
    return {
      visible: false,
      permissions: [],
      page: {
        current: 1,
        size: 4,
        total: 0,
        pages: 0
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
      this.permissions = []
      this.page.current = 1
      this.page.size = 4
      this.page.total = 0
      this.page.pages = 0
      this.$emit("close")
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
      findInactivePermissionPage(params).then(result => {
        const {data} = result
        const {current, size, total, pages, records: permissions} = data
        this.page.current = current
        this.page.size = size
        this.page.total = total
        this.page.pages = pages
        this.permissions = permissions
      })
    },
    create: function (permission) {
      const data = {
        roleId: this.roleId,
        permissionId: permission.permissionId
      }
      create(data).then(result => {
        const {code, message} = result
        if (code === 200) {
          this.$message({
            message: message,
            type: 'success'
          })
          if (this.permissions.length === 1 && this.page.current > 1) {
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
