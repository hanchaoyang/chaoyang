import request from '@/utils/request'

export function find(params) {
  return request({
    url: '/permission',
    method: 'get',
    params
  })
}

export function findPage(params) {
  return request({
    url: '/permission/page',
    method: 'get',
    params
  })
}

export function findInactivePermissionPage(params) {
  return request({
    url: '/permission/inactive/page',
    method: 'get',
    params
  })
}

export function create(data) {
  return request({
    url: '/permission',
    method: 'post',
    data
  })
}

export function modify(data) {
  return request({
    url: '/permission',
    method: 'put',
    data
  })
}

export function remove(params) {
  return request({
    url: '/permission',
    method: 'delete',
    params
  })
}
