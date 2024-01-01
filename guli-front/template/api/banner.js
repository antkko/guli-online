import request from '@/utils/request'

export default {

  /**
   * 获取首页banner
   * @returns banner列表
   */
  getListBanner() {
    return request({
      url: '/educms/bannerfront/getAllBanner',
      method: 'get'
    })
  }
}