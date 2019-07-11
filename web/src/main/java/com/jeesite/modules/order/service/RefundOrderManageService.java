package com.jeesite.modules.order.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.commom.utils.ApiUtils;
import com.jeesite.modules.order.entity.AddOrderVo;
import com.jeesite.modules.order.entity.OrderDetailsVo;
import com.jeesite.modules.order.entity.OrderListVo;
import com.jeesite.modules.order.entity.RefundOrderListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;


@Service
@Transactional(readOnly = true)
@Slf4j
public class RefundOrderManageService {

    @Value("${api.host}")
    private String apiHost;

   public Page<RefundOrderListVo> loadRefundOrderManageList(Page<RefundOrderListVo> page){
        //构建入参对象
        JSONObject object = new JSONObject();
        object.put("pageSize",page.getPageSize());
        object.put("pageNumber",page.getPageNo());
        object.put("refundKid",page.getOtherData().get("refundKid"));
        object.put("refundStatu",page.getOtherData().get("refundStatu"));

        // String requestUrl = apiHost + "/order/tourist/queryRefundManageByPage";
        String requestUrl = "http://192.168.31.201:7120/order/refund/queryRefundManageByPage";
        String result = ApiUtils.post(requestUrl, object);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if(resultObject != null){
                if("200".equals(resultObject.getString("code"))){
                    List<RefundOrderListVo> refundListVos = (List<RefundOrderListVo>)resultObject.getJSONObject("data").get("entities");
                    page.setList(refundListVos);
                    page.setCount(resultObject.getJSONObject("data").getLong("count"));
                }
            }
        }
        return page;
    }



  /* public OrderDetailsVo queryOrderDetails (OrderDetailsVo orderDetailsVo){
       // String requestUrl = apiHost + "/order/tourist/queryOrderManageDetails";
       String requestUrl = "http://192.168.31.201:7120/order/tourist/queryOrderManageDetails";
       String result = ApiUtils.post(requestUrl, orderDetailsVo);
       if(!StringUtils.isEmpty(result)){
           JSONObject resultObject = JSON.parseObject(result);
           if(resultObject != null){
               if("200".equals(resultObject.getString("code"))){
                   OrderDetailsVo data = resultObject.getObject("data", OrderDetailsVo.class);
                   return data;
               }
           }
       }
       return null;
   }*/

}
