import request from "@/utils/request";

export default {
  //生成订单
  createOrder(courseId) {
    return request({
      url: "/eduorder/order/createOrder/" + courseId,
      method: "post",
    });
  },
  //根据订单号查询订单信息
  getOrderInfoByNo(orderNo) {
    return request({
      url: `/eduorder/order/getOrderInfo/${orderNo}`,
      method: "get",
    });
  },
  //根据订单号，生产二维码
  createWxQRcode(orderNo) {
    return request({
      url: `/eduorder/pay-log/createNative/${orderNo}`,
      method: "get",
    });
  },
  //根据订单号，查询订单支付状态
  getPayStatus(orderNo) {
    return request({
      url: `/eduorder/pay-log//queryPayStatus/${orderNo}`,
      method: "get",
    });
  },
};
