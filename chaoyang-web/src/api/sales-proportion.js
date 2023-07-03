import request from '@/utils/request'

export function findByDay() {
  return request({
    url: '/sales-proportion/statistic/day',
    method: 'get'
  })
}
