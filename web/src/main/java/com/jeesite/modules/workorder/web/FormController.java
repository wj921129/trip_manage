package com.jeesite.modules.workorder.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.workorder.entity.*;
import com.jeesite.modules.workorder.service.FormService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "${adminPath}/form")
public class FormController  extends BaseController {

    @Autowired
    private FormService formService;

    @RequestMapping(value = {"list", ""})
    public String list(Form form, Model model) {
        model.addAttribute("form", form);
        return "modules/workorder/formList";
    }


    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<Form> listData(Form form, HttpServletRequest request, HttpServletResponse response) {
        String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");

        Page<Form> page = new Page<>();
        if(!StringUtils.isEmpty(pageSize)){
            page.setPageSize(Integer.parseInt(pageSize));
        }
        if(!StringUtils.isEmpty(pageNo)){
            page.setPageNo(Integer.parseInt(pageNo));
        }
        Map<String, Object> paramterMap = new HashMap<>();
        if(request.getParameter("status") != null){
            paramterMap.put("status", request.getParameter("status"));
        }
        page.setOtherData(paramterMap);

        Page<Form> formPage = formService.queryAllForm(page);

        return formPage;
    }


    @RequestMapping(value = "form")
    public String form(String kid, Model model, HttpServletRequest request) {
        if(StringUtils.isEmpty(kid)){
            return renderResult(Global.FALSE, "工单kid不能为空！");
        }

        FormByKidOutVo formByKidOutVo = formService.queryByKid(kid);
        FormByKidBaseInfoOutVo baseInfoOutVo = new FormByKidBaseInfoOutVo();
        try {
            BeanUtils.copyProperties(baseInfoOutVo, formByKidOutVo);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //得到当前登录用户名
        String username = (String) UserUtils.getSession().getAttribute("username");
        model.addAttribute("baseInfo", baseInfoOutVo);
        model.addAttribute("detail", formByKidOutVo.getFormDetailOutVos());
        model.addAttribute("username", username);
        return "modules/workorder/form";
    }


    @PostMapping(value = "addReply")
    @ResponseBody
    public JSONObject addReply(String workOrderKid, String replyDesc, Integer replyType, String replyUsername, String picStr) {
        JSONObject obj = new JSONObject();
        obj.put("msg", "error");

        List<String> imageUrls = new ArrayList<>();
        if(!StringUtils.isEmpty(picStr)){
            String[] split = picStr.split(",");
            if(split.length > 0){
                for (int i=0;i<split.length;i++){
                    imageUrls.add(split[i]);
                }
            }
        }

        FormReplyAddInVo formReplyAddInVo = new FormReplyAddInVo();
        formReplyAddInVo.setWorkOrderKid(workOrderKid);
        formReplyAddInVo.setReplyDesc(replyDesc);
        formReplyAddInVo.setReplyType(replyType);
        formReplyAddInVo.setReplyUsername(replyUsername);
        formReplyAddInVo.setImageUrls(imageUrls);

        String result = formService.addReply(formReplyAddInVo);
        if(!StringUtils.isEmpty(result)){
            if("200".equals(result)){
                obj.put("msg", "success");
            }else{
                obj.put("msg", "failed");
            }
        }
        return obj;
    }


    @PostMapping(value = "doneDeal")
    @ResponseBody
    public JSONObject doneDeal(String workOrderKid, Integer status) {
        JSONObject obj = new JSONObject();
        obj.put("msg", "error");

        FormUpdateInVo formUpdateInVo = new FormUpdateInVo();
        formUpdateInVo.setKid(workOrderKid);
        formUpdateInVo.setStatus(status);

        String result = formService.doneDeal(formUpdateInVo);
        if(!StringUtils.isEmpty(result)){
            if("200".equals(result)){
                obj.put("msg", "success");
            }else{
                obj.put("msg", "failed");
            }
        }
        return obj;
    }

}
