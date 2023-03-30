<template>
  <div>
    <el-dialog title="角色" :visible.sync="visible" width="50%" @open="open()" @close="close()">
      <div>
        <el-button type="success" size="medium" @click="openInactiveRoleDialog()">添加</el-button>
      </div>
      <el-table :data="userRoles" size="medium" empty-text="暂无数据" border stripe style="margin-top: 20px">
        <el-table-column type="index" width="100" label="#"></el-table-column>
        <el-table-column prop="roleName" label="角色名称" min-width="100"></el-table-column>
        <el-table-column prop="roleCode" label="角色标识" min-width="100"></el-table-column>
        <el-table-column fixed="right" label="操作" width="120">
          <template slot-scope="scope">
            <el-button type="danger" size="medium" @click="remove(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination layout="prev, pager, next" :current-page.sync="page.current" :page-size="page.size" :total="page.total" background style="margin-top: 20px;" @current-change="currentChange()"></el-pagination>
<!--      <div slot="footer">-->
<!--        <el-button size="medium" @click="cancel()">取消</el-button>-->
<!--        <el-button type="primary" size="medium" @click="done()">确定</el-button>-->
<!--      </div>-->
    </el-dialog>
    <inactive-role-dialog ref="InactiveRoleDialog" :user-id="dialog.inactiveRole.userId" @close="getPage()"></inactive-role-dialog>
  </div>
</template>

<script>
import InactiveRoleDialog from "@/components/User/UserRoleDialog/InactiveRoleDialog"
import {findPage, remove} from "@/api/user-role"

export default {
  name: "index",
  components: {
    InactiveRoleDialog
  },
  props: {
    userId: {
      type: Number
    }
  },
  data() {
    return {
      visible: false,
      userRoles: [],
      page: {
        current: 1,
        size: 4,
        total: 0,
        pages: 0
      },
      dialog: {
        inactiveRole: {
          userId: null
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
      this.userRoles = []
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
        userId: this.userId,
        current: this.page.current,
        size: this.page.size
      }
      findPage(params).then(result => {
        const {data} = result
        const {current, size, total, pages, records: userRoles} = data
        this.page.current = current
        this.page.size = size
        this.page.total = total
        this.page.pages = pages
        this.userRoles = userRoles
      })
    },
    openInactiveRoleDialog: function () {
      this.dialog.inactiveRole.userId = this.userId
      this.$refs.InactiveRoleDialog.setVisible(true)
    },
    remove: function (userRole) {
      const params = {
        userRoleId: userRole.userRoleId
      }
      remove(params).then(result => {
        const {code, message} = result
        if (code === 200) {
          this.$message({
            message: message,
            type: 'success'
          })
          if (this.userRoles.length === 1 && this.page.current > 1) {
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
