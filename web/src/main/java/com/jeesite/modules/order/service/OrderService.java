package com.jeesite.modules.order.service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.commom.utils.ApiUtils;
import com.jeesite.modules.order.entity.AddOrderVo;
import com.jeesite.modules.order.entity.OrderDetailsVo;
import com.jeesite.modules.order.entity.OrderListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.List;


@Service
@Transactional(readOnly = true)
@Slf4j
public class OrderService {

    @Value("${api.host}")
    private String apiHost;

   public Page<OrderListVo> loadOrderManageList(Page<OrderListVo> page){
        //构建入参对象
        JSONObject object = new JSONObject();
        object.put("pageSize",page.getPageSize());
        object.put("pageNumber",page.getPageNo());
        object.put("orderKid",page.getOtherData().get("orderKid"));
        object.put("orderStatu",page.getOtherData().get("orderStatu"));

        String requestUrl = apiHost + "/order/tourist/queryOrderManageList";
        String result = ApiUtils.post(requestUrl, object);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if(resultObject != null){
                if("200".equals(resultObject.getString("code"))){
                    List<OrderListVo> OrderListVos = (List<OrderListVo>)resultObject.getJSONObject("data").get("entities");
                    page.setList(OrderListVos);
                    page.setCount(resultObject.getJSONObject("data").getLong("count"));
                }
            }
        }
        return page;
    }

   public void createOrder(AddOrderVo addOrderVo) {
         String requestUrl = apiHost + "/order/tourist/createOrder";
     //  String requestUrl = "http://192.168.31.201:7120/order/tourist/createOrder";
       String result = ApiUtils.post(requestUrl, addOrderVo);
       if(!StringUtils.isEmpty(result)){
           JSONObject resultObject = JSON.parseObject(result);
           if(resultObject != null){
               if(!"200".equals(resultObject.getString("code"))){
                   log.info("创建订单失败");
               }
           }
       }
   }

   public OrderDetailsVo queryOrderDetails (OrderDetailsVo orderDetailsVo){
         String requestUrl = apiHost + "/order/tourist/queryOrderManageDetails";
     //  String requestUrl = "http://192.168.31.201:7120/order/tourist/queryOrderManageDetails";
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
   }

}
