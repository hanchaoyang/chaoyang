import request from '@/utils/request'

// TODO 改这里

export function login(data) {
  return request({
    url: '/login',
    method: 'post',
    data
  })
}

export function getLoginInfo() {
  return request({
    url: '/login-info',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}
