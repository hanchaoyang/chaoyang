<template>
  <div ref="PieChartRef" style="width: 100%; height: 100%"></div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: 'index',
  mounted() {
    this.createPieChart()
  },
  methods: {
    createPieChart: function() {
      echarts.init(this.$refs.PieChartRef).setOption({
        // title: {
        //   text: '开发语言'
        // },
        color: [
          '#27E1C1',
          '#936de8',
          '#f56565',
          '#5eaef8',
          '#FFE569'
        ],
        legend: {
          orient: 'vertical',
          x: 'right',
          data: ['Java', 'Vue', 'JavaScript', 'SCSS', '其他']
        },
        grid: {
          top: '20%',
          bottom: '8%',
          left: '3%',
          right: '3%',
          containLabel: true
        },
        series: [
          {
            type: 'pie',
            radius: ['40%', '75%'],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: 'center'
            },
            labelLine: {
              show: false
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '20',
                fontWeight: 'bold'
              }
            },
            itemStyle: {
              borderRadius: 10,
              borderColor: '#FFFFFF',
              borderWidth: 2
            },
            data: [
              {
                name: 'Java',
                value: 40.5
              },
              {
                name: 'Vue',
                value: 28.6
              },
              {
                name: 'JavaScript',
                value: 17.5
              },
              {
                name: 'SCSS',
                value: 5.6
              },
              {
                name: '其他',
                value: 7.8
              }
            ]
          }
        ]
      })
    }
  }
}
</script>

<style scoped>

</style>
