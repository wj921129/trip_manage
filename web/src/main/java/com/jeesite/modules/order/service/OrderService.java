package com.jeesite.modules.order.service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.commom.utils.ApiUtils;
import com.jeesite.modules.order.entity.OrderListVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.List;


@Service
@Transactional(readOnly = true)
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

        // String requestUrl = apiHost + "/order/tourist/queryOrderManageList";
        String requestUrl = "http://192.168.31.201:7120/order/tourist/queryOrderManageList";
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

}
