import request from '@/utils/request'

export function find(params) {
  return request({
    url: '/user',
    method: 'get',
    params
  })
}

export function findPage(params) {
  return request({
    url: '/user/page',
    method: 'get',
    params
  })
}

export function create(data) {
  return request({
    url: '/user',
    method: 'post',
    data
  })
}

export function modify(data) {
  return request({
    url: '/user',
    method: 'put',
    data
  })
}

export function remove(params) {
  return request({
    url: '/user',
    method: 'delete',
    params
  })
}
