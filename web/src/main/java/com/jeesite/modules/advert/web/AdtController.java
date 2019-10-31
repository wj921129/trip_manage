package com.jeesite.modules.advert.web;


import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.advert.entity.AdtInVo;
import com.jeesite.modules.advert.entity.AdtOutVo;
import com.jeesite.modules.advert.service.AdtService;
import com.jeesite.modules.commom.utils.ApiPage;
import com.jeesite.modules.line.entity.SearchLineInVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.jeesite.common.web.http.ServletUtils.renderResult;

@Controller
@RequestMapping(value = "${adminPath}/adt")
@Slf4j
public class AdtController {

    @Autowired
    private AdtService adtService;

    @RequestMapping(value = "list")
    public String list(AdtInVo adtInVo, Model model) {

        model.addAttribute("adt", adtInVo);
        return "modules/adt/adtList";
    }

    private void page(HttpServletRequest request, AdtInVo adtInVo){
        String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");
        if(!StringUtils.isEmpty(pageSize)){
            adtInVo.setPageSize(Integer.parseInt(pageSize));
        }
        if(!StringUtils.isEmpty(pageNo)){
            adtInVo.setPageNumber(Integer.parseInt(pageNo));
        }
    }

    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<AdtOutVo> listData(AdtInVo adtInVo, Model model, HttpServletRequest request, HttpServletResponse response) {

        page(request, adtInVo);

        ApiPage<AdtOutVo> adtOutVoApiPage = adtService.getAdts(adtInVo);

        Page<AdtOutVo> page = new Page();
        page.setList(adtOutVoApiPage.getEntities());
        page.setCount(adtOutVoApiPage.getCount());
        page.setPageNo(adtInVo.getPageNumber());
        page.setPageSize(adtInVo.getPageSize());

        return page;
    }

    /**
     * 编辑广告
     * @return
     */
    @RequestMapping(value = "editAdt")
    @ResponseBody
    public String editAdt(String kid, Integer adtStatus) {
        log.info("编辑广告,kid :" + kid + "状态,adtStatus :" + adtStatus);
        if (StringUtils.isEmpty(kid)){

            return renderResult(Global.TRUE, "操作失败,kid不能为空！");
        }

        if (StringUtils.isEmpty(adtStatus)){
            return renderResult(Global.FALSE, "操作失败,操作状态不能为空！");
        }

        String message = adtService.editAdt(kid, adtStatus);
        log.info("路线上下架出参 : " + message);
        return renderResult(Global.TRUE, message);
    }
}
