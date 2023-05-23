<template>
  <div class="dashboard-container">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="never">
<!--          <el-card shadow="never" style="border: none;background-image: linear-gradient(to bottom right, #ffc3c3, #f8d4d4)">-->
          <div style="font-size: 20px;color: #AAAAAA">成交量</div>
<!--          <div style="font-size: 40px;line-height: 1;display: block;margin: 30px auto 20px;text-align: center">485</div>-->
          <div style="font-size: 40px;line-height: 1;display: block;margin: 30px auto 20px;text-align: center;font-weight: bold">485</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="never">
<!--          <el-card shadow="never" style="border: none;background-image: linear-gradient(to bottom right, #ffecbb, #fff2d2)">-->
          <div style="font-size: 20px;color: #AAAAAA">营业额</div>
<!--          <div style="font-size: 40px;line-height: 1;display: block;margin: 30px auto 20px;text-align: center">￥7482.10</div>-->
          <div style="font-size: 40px;line-height: 1;display: block;margin: 30px auto 20px;text-align: center;font-weight: bold">￥7482.10</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="never">
<!--          <el-card shadow="never" style="border: none;background-image: linear-gradient(to bottom right, #a1d3ff, #d1e9df)">-->
          <div style="font-size: 20px;color: #AAAAAA">浏览量</div>
<!--          <div style="font-size: 40px;line-height: 1;display: block;margin: 30px auto 20px;text-align: center">2485</div>-->
          <div style="font-size: 40px;line-height: 1;display: block;margin: 30px auto 20px;text-align: center;font-weight: bold">2485</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="never">
<!--          <el-card shadow="never" style="border: none;background-image: linear-gradient(to bottom right, #b4ffba, #d3ffd7)">-->
          <div style="font-size: 20px;color: #AAAAAA">收藏量</div>
<!--          <div style="font-size: 40px;line-height: 1;display: block;margin: 30px auto 20px;text-align: center">1896</div>-->
          <div style="font-size: 40px;line-height: 1;display: block;margin: 30px auto 20px;text-align: center;font-weight: bold">1896</div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="16">
        <el-card shadow="never">
          <line-chart style="height: 320px"></line-chart>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="never">
          <pie-chart style="height: 320px"></pie-chart>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import LineChart from '@/components/Chart/LineChart'
import PieChart from '@/components/Chart/PieChart'

export default {
  name: 'Dashboard',
  components: {
    LineChart,
    PieChart
  },
  computed: {
    ...mapGetters([
      'name'
    ])
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }

  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
