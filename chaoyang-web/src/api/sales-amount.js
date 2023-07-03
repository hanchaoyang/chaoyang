import request from '@/utils/request'

export function findByDay() {
  return request({
    url: '/sales-amount/statistic/day',
    method: 'get'
  })
}
