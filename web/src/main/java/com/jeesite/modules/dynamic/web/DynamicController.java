package com.jeesite.modules.dynamic.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.commom.utils.ApiPage;
import com.jeesite.modules.dynamic.entity.Dynamic;
import com.jeesite.modules.dynamic.entity.DynamicInVo;
import com.jeesite.modules.dynamic.entity.DynamicOutVo;
import com.jeesite.modules.dynamic.service.DynamicService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.jeesite.common.web.http.ServletUtils.renderResult;

@Controller
@RequestMapping(value = "${adminPath}/dynamic")
public class DynamicController {

    @Autowired
    private DynamicService dynamicService;

    private void page(HttpServletRequest request, DynamicInVo dynamicInVo){
        String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");
        if(!StringUtils.isEmpty(pageSize)){
            dynamicInVo.setPageSize(Integer.parseInt(pageSize));
        }
        if(!StringUtils.isEmpty(pageNo)){
            dynamicInVo.setPageNumber(Integer.parseInt(pageNo));
        }
    }

    /**
     * 动态列表
     */
    @RequiresPermissions("dynamic:view")
    @RequestMapping(value = {"list", ""})
    public String list(DynamicInVo dynamicInVo, Model model) {

        model.addAttribute("dynamic", dynamicInVo);
        return "modules/dynamic/dynamicList";
    }

    /**
     * 根据状态查询路线结果
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("dynamic:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<DynamicOutVo> listData(DynamicInVo dynamicInVo, Model model, HttpServletRequest request, HttpServletResponse response) {

        page(request, dynamicInVo);

        ApiPage<DynamicOutVo> dynamicOutVoApiPage = dynamicService.queryParams(dynamicInVo);

        Page<DynamicOutVo> page = new Page();
        page.setList(dynamicOutVoApiPage.getEntities());
        page.setCount(dynamicOutVoApiPage.getCount());
        page.setPageNo(dynamicInVo.getPageNumber());
        int pageSize = dynamicInVo.getPageSize();
        page.setPageSize(pageSize);

        return page;
    }

    /**
     * 动态编辑
     * @param empUser
     * @return
     */
    @RequiresPermissions("dynamic:edit")
    @RequestMapping(value = "edit")
    @ResponseBody
    public String edit(DynamicInVo dynamicInVo) {

        if (StringUtils.isEmpty(dynamicInVo.getDynamicKid())){

            return renderResult(Global.TRUE, "操作失败,kid不能为空！");
        }

        String message = dynamicService.edit(dynamicInVo);
        return renderResult(Global.TRUE, message);
    }
}
