package com.powernode.crm.workbench.web.controller;

import com.powernode.crm.workbench.domain.FunnelVo;
import com.powernode.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ChartController {
    @Autowired
    private TranService tranService;
    @RequestMapping("/workbench/chart/transaction/index.do")
    public String index(){
        return "workbench/chart/transaction/index";
    }
    @RequestMapping("/workbench/chart/transaction/queryCountOfTranGroupByStage.do")
    public @ResponseBody Object queryCountOfTranGroupByStage(){
        //调用service查询信息
        List<FunnelVo> funnelVoList = tranService.queryCountOfTranGroupByStage();
        return funnelVoList;
    }
}
