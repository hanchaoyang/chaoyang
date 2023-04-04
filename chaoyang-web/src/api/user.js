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

export function modifyStatus(data) {
  return request({
    url: '/user/status',
    method: 'patch',
    data
  })
}

export function modifyPassword(data) {
  return request({
    url: '/user/password',
    method: 'patch',
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
