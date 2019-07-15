package com.jeesite.modules.order.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.commom.utils.ApiUtils;
import com.jeesite.modules.order.entity.*;
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

   //退款单列表
   public Page<RefundOrderListVo> loadRefundOrderManageList(Page<RefundOrderListVo> page){
        //构建入参对象
        JSONObject object = new JSONObject();
        object.put("pageSize",page.getPageSize());
        object.put("pageNumber",page.getPageNo());
        object.put("refundKid",page.getOtherData().get("refundKid"));
        object.put("refundStatu",page.getOtherData().get("refundStatu"));

        // String requestUrl = apiHost + "/order/refund/queryRefundManageByPage";
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

    //退款单详情
   public RefundOrderDetailsVo queryRefundOrderDetails (RefundOrderDetailsVo refundOrderDetailsVo){
       // String requestUrl = apiHost + "/order/refund/queryRefundManageDetails";
       String requestUrl = "http://192.168.31.201:7120/order/refund/queryRefundManageDetails";
       String result = ApiUtils.post(requestUrl, refundOrderDetailsVo);
       if(!StringUtils.isEmpty(result)){
           JSONObject resultObject = JSON.parseObject(result);
           if(resultObject != null){
               if("200".equals(resultObject.getString("code"))){
                   RefundOrderDetailsVo data = resultObject.getObject("data", RefundOrderDetailsVo.class);
                   return data;
               }
           }
       }
       return null;
   }

   //后台审核同意退款
   public void backstageAgreeRefund (RefundOrderListVo refundOrderListVo){
       // String requestUrl = apiHost + "/order/refund/backstageAgreeRefund";
       String requestUrl = "http://192.168.31.201:7120/order/refund/backstageAgreeRefund";
       String result = ApiUtils.post(requestUrl, refundOrderListVo);
       if(!StringUtils.isEmpty(result)){
           JSONObject resultObject = JSON.parseObject(result);
           if(!"200".equals(resultObject.getString("code"))){
               log.info("后台同意退款失败");
           }
       }

   }

    // 后台审核不同意退款
    public void backstageRefuseRefund (ExamineReasonVo examineReasonVo){
        // String requestUrl = apiHost + "/order/refund/backstageRefuseRefund";
        String requestUrl = "http://192.168.31.201:7120/order/refund/backstageRefuseRefund";
        String result = ApiUtils.post(requestUrl, examineReasonVo);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if(!"200".equals(resultObject.getString("code"))){
                log.info("后台拒绝退款失败");
            }
        }

    }

}
