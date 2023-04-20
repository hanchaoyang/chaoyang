<template>
  <div style="padding: 20px">
    <div>
      <el-button type="success" size="medium" @click="openCreateDialog()">添加</el-button>
      <el-divider direction="vertical"></el-divider>
      <el-input v-model="condition.userNickname" placeholder="用户昵称" size="medium" :clearable="true" style="width: 200px"></el-input>
      <el-input v-model="condition.userPhone" placeholder="用户手机号" size="medium" :clearable="true" style="width: 200px;margin-left: 10px"></el-input>
      <el-select v-model="condition.userStatus" placeholder="用户状态" size="medium" :clearable="true" style="width: 200px;margin-left: 10px">
        <el-option label="禁用" :value="0"></el-option>
        <el-option label="启用" :value="1"></el-option>
      </el-select>
      <el-button type="primary" size="medium" style="margin-left: 10px" @click="search()">查询</el-button>
      <el-button size="medium" style="margin-left: 10px" @click="reset()">重置</el-button>
    </div>
    <el-table :data="users" size="medium" empty-text="暂无数据" border stripe style="margin-top: 20px">
      <el-table-column type="index" width="100" label="#"></el-table-column>
      <el-table-column prop="userNickname" label="用户昵称" min-width="100"></el-table-column>
      <el-table-column prop="userPhone" label="用户手机号" min-width="100"></el-table-column>
<!--      <el-table-column prop="userStatus" label="用户状态" min-width="100">-->
<!--        <template slot-scope="scope">-->
<!--          <el-tag :type="userStatusTagType(scope.row)">{{scope.row.userStatusName}}</el-tag>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column prop="userStatus" label="用户状态" min-width="100">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.userStatus" active-text="启用" inactive-text="禁用" :active-value="1" :inactive-value="0" active-color="#13CE66" inactive-color="#FF4949" @change="userStatus => modifyStatus(scope.row.userId, userStatus)"></el-switch>
        </template>
      </el-table-column>
<!--      <el-table-column prop="userStatusName" label="用户状态" min-width="100"></el-table-column>-->
      <el-table-column fixed="right" label="操作" width="400">
        <template slot-scope="scope">
          <el-button type="primary" size="medium" @click="openModifyDialog(scope.row)">编辑</el-button>
<!--          <el-button v-if="scope.row.userStatus === 0" type="success" size="medium" @click="enable(scope.row)">启用</el-button>-->
<!--          <el-button v-else-if="scope.row.userStatus === 1" type="warning" size="medium" @click="disable(scope.row)">禁用</el-button>-->
          <el-button type="success" size="medium" @click="openUserRoleDialog(scope.row)">角色</el-button>
          <el-button type="primary" size="medium" @click="openModifyPasswordDialog(scope.row)">修改密码</el-button>
          <el-button type="danger" size="medium" @click="remove(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination layout="prev, pager, next" :current-page.sync="page.current" :page-size="page.size" :total="page.total" background style="margin-top: 20px;" @current-change="currentChange()"></el-pagination>
    <create-dialog ref="CreateDialog" @close="getPage()"></create-dialog>
    <modify-dialog ref="ModifyDialog" :user-id="dialog.modify.userId" @close="getPage()"></modify-dialog>
    <modify-password-dialog ref="ModifyPasswordDialog" :user-id="dialog.modifyPassword.userId"></modify-password-dialog>
    <user-role-dialog ref="UserRoleDialog" :user-id="dialog.userRole.userId"></user-role-dialog>
  </div>
</template>

<script>
import CreateDialog from '@/components/User/CreateDialog'
import ModifyDialog from '@/components/User/ModifyDialog'
import ModifyPasswordDialog from '@/components/User/ModifyPasswordDialog'
import UserRoleDialog from '@/components/User/UserRoleDialog'
import { findPage, modifyStatus, remove } from '@/api/user'

export default {
  name: "index",
  components: {
    CreateDialog,
    ModifyDialog,
    ModifyPasswordDialog,
    UserRoleDialog
  },
  data() {
    return {
      condition: {
        userNickname: null,
        userPhone: null,
        userStatus: null
      },
      users: [],
      page: {
        current: 1,
        size: 8,
        total: 0,
        pages: 0
      },
      dialog: {
        userRole: {
          userId: null
        },
        modify: {
          userId: null
        },
        modifyPassword: {
          userId: null
        }
      }
    }
  },
  mounted() {
    this.getPage();
  },
  methods: {
    search: function () {
      this.getPage()
    },
    reset: function () {
      this.condition.userNickname = null
      this.condition.userPhone = null
      this.condition.userStatus = null
      this.getPage()
    },
    currentChange: function () {
      this.getPage()
    },
    getPage: function () {
      const params = {
        userNickname: this.condition.userNickname,
        userPhone: this.condition.userPhone,
        userStatus: this.condition.userStatus,
        current: this.page.current,
        size: this.page.size
      }
      findPage(params).then(result => {
        const {data} = result
        const {current, size, total, pages, records: users} = data
        this.page.current = current
        this.page.size = size
        this.page.total = total
        this.page.pages = pages
        this.users = users
      })
    },
    userStatusTagType: function (user) {
      switch (user.userStatus) {
        case 0:
          return "warning"
        case 1:
          return "success"
        default:
      }
    },
    openCreateDialog: function () {
      this.$refs.CreateDialog.setVisible(true)
    },
    openModifyDialog: function (user) {
      this.dialog.modify.userId = user.userId
      this.$refs.ModifyDialog.setVisible(true)
    },
    openModifyPasswordDialog: function(user) {
      this.dialog.modifyPassword.userId = user.userId
      this.$refs.ModifyPasswordDialog.setVisible(true)
    },
    openUserRoleDialog: function (user) {
      this.dialog.userRole.userId = user.userId
      this.$refs.UserRoleDialog.setVisible(true);
    },
    modifyStatus: function(userId, userStatus) {
      const data = {
        userId: userId,
        userStatus:userStatus
      }
      modifyStatus(data).then(result => {
        const {code, message} = result
        if (code === 200) {
          this.$message({
            message: message,
            type: 'success'
          })
          this.getPage()
        }
      }).catch(() => {
        this.getPage()
      })
    },
    remove: function (user) {
      this.$confirm('确认删除？', '提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const params = {
          userId: user.userId
        }
        remove(params).then(result => {
          const {code, message} = result
          if (code === 200) {
            this.$message({
              message: message,
              type: 'success'
            })
            if (this.users.length === 1 && this.page.current > 1) {
              this.page.current -= 1
            }
            this.getPage()
          }
        })
      })
    }
  }
}
</script>

<style scoped>

</style>
