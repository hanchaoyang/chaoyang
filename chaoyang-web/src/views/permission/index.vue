<template>
  <div style="padding: 20px">
    <div>
      <el-button type="success" size="medium" @click="openCreateDialog()">添加</el-button>
      <el-divider direction="vertical"></el-divider>
      <el-input v-model="condition.name" placeholder="名称" size="medium" :clearable="true" style="width: 200px"></el-input>
      <el-input v-model="condition.code" placeholder="标识" size="medium" :clearable="true" style="width: 200px;margin-left: 10px"></el-input>
      <el-button type="primary" size="medium" style="margin-left: 10px" @click="search()">查询</el-button>
      <el-button size="medium" style="margin-left: 10px" @click="reset()">重置</el-button>
    </div>
    <el-table :data="permissions" size="medium" border stripe style="margin-top: 20px">
      <el-table-column type="index" width="100" label="#"></el-table-column>
      <el-table-column prop="name" label="名称" min-width="100"></el-table-column>
      <el-table-column prop="code" label="标识" min-width="100"></el-table-column>
      <el-table-column fixed="right" label="操作" width="220">
        <template slot-scope="scope">
          <el-button type="primary" size="medium" @click="openModifyDialog(scope.row)">编辑</el-button>
          <el-button type="danger" size="medium" @click="remove(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination layout="prev, pager, next" :current-page.sync="page.current" :page-size="page.size" :total="page.total" background style="margin-top: 20px;" @current-change="currentChange()"></el-pagination>
    <modify-dialog ref="ModifyDialog"  :permission-id="dialog.modify.permissionId" @close="getPage()"></modify-dialog>
    <create-dialog ref="CreateDialog" @close="getPage()"></create-dialog>
  </div>
</template>

<script>
import CreateDialog from '@/components/Permission/CreateDialog'
import ModifyDialog from '@/components/Permission/ModifyDialog'
import {findPage,remove} from '@/api/permission'

export default {
  name: "index",
  components: {
    CreateDialog,
    ModifyDialog
  },
  data() {
    return {
      condition: {
        name: null,
        code: null
      },
      permissions: [],
      page: {
        current: 1,
        size: 8,
        total: 0,
        pages: 0
      },
      dialog: {
        modify: {
          permissionId: null
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
      this.condition.name = null
      this.condition.code = null
      this.getPage()
    },
    currentChange: function () {
      this.getPage()
    },
    getPage: function () {
      const params = {
        name: this.condition.name,
        code: this.condition.code,
        current: this.page.current,
        size: this.page.size
      }
      findPage(params).then(result => {
        const {data} = result
        const {current, size, total, pages, records: permissions} = data
        this.page.current = current
        this.page.size = size
        this.page.total = total
        this.page.pages = pages
        this.permissions = permissions
      })
    },
    openCreateDialog: function () {
      this.$refs.CreateDialog.setVisible(true)
    },
    openModifyDialog: function (permission) {
      this.dialog.modify.permissionId = permission.id
      this.$refs.ModifyDialog.setVisible(true)
    },
    remove: function (permission) {
      this.$confirm('确认删除？', '提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const params = {
          id: permission.id
        }
        remove(params).then(result => {
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
      })
    }
  }
}
</script>

<style scoped>

</style>
