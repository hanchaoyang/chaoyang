<template>
  <div ref="LineChartRef" style="width: 100%; height: 100%"></div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: 'index',
  mounted() {
    this.createLineChart()
  },
  methods: {
    createLineChart: function() {
      echarts.init(this.$refs.LineChartRef).setOption({
        // title: {
        //   text: '浏览量'
        // },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            axis: 'auto',
            snap: false,
            label: {
              backgroundColor: '#6A7985'
            }
          }
        },
        color: [
          '#27E1C1',
          '#7149C6'
        ],
        legend: {
          data: ['浏览', '克隆']
        },
        grid: {
          top: '20%',
          bottom: '8%',
          left: '3%',
          right: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: ['05/11', '05/12', '05/13', '05/14', '05/15', '05/16', '05/17', '05/18', '05/19', '05/20', '05/21', '05/22']
        },
        yAxis: [
          {
            name: '浏览',
            type: 'value',
            position: 'left',
            min: 0,
            max: 40
          },
          {
            name: '克隆',
            type: 'value',
            position: 'right',
            min: 0,
            max: 20
          }
        ],
        series: [
          {
            name: '浏览',
            type: 'line',
            smooth: true,
            areaStyle: {
              opacity: 0.3
            },
            emphasis: {
              focus: 'series'
            },
            data: [24, 15, 12, 18, 28, 35, 22, 26, 15, 12, 19, 23]
          },
          {
            name: '克隆',
            type: 'line',
            smooth: true,
            areaStyle: {
              opacity: 0.4
            },
            emphasis: {
              focus: 'series'
            },
            data: [4, 5, 2, 8, 6, 5, 2, 6, 5, 2, 9, 3]
          }
        ]
      })
    }
  }
}
</script>

<style scoped>

</style>
