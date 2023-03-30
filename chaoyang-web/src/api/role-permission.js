import request from "@/utils/request";

export function findPage(params) {
  return request({
    url: '/role-permission/page',
    method: 'get',
    params
  })
}

export function create(data) {
  return request({
    url: '/role-permission',
    method: 'post',
    data
  })
}

export function remove(params) {
  return request({
    url: '/role-permission',
    method: 'delete',
    params
  })
}
