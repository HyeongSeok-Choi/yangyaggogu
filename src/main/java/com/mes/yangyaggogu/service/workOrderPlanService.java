package com.mes.yangyaggogu.service;

import com.mes.yangyaggogu.constant.obtainorder_state;
import com.mes.yangyaggogu.constant.productionPlan_state;
import com.mes.yangyaggogu.constant.workOrderPlan_state;
import com.mes.yangyaggogu.entity.obtainorder_detail;
import com.mes.yangyaggogu.entity.obtainorder_number;
import com.mes.yangyaggogu.entity.productPlan;
import com.mes.yangyaggogu.entity.workOrderPlan;
import com.mes.yangyaggogu.repository.obtainorder_detailRepository;
import com.mes.yangyaggogu.repository.obtainorder_numberRepository;
import com.mes.yangyaggogu.repository.productPlanRepository;
import com.mes.yangyaggogu.repository.workOrderPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class workOrderPlanService {

    private final obtainorder_detailRepository obtain_order_DetailRepository;
    private final obtainorder_numberRepository obtain_order_numberRepository;
    private final workOrderPlanRepository workOrderPlanRepository;
    private final productPlanRepository productPlanRepository;

    public List<workOrderPlan> getAll() {
        return workOrderPlanRepository.findAll();
    }


    public void testWorkOrderPlanData (){

        //수주번호의 생성 -> 가상으로 만들어 봅세다.
        obtainorder_number obtainorderNumber = new obtainorder_number();
        //2024-06-15에 첫번째 수주번호가 생성 후 저장
        obtainorderNumber.setOrder_Number("20240615-001");
        obtain_order_numberRepository.save(obtainorderNumber);

        //----------------------------------------------------------

        //수주번호를 기반으로한 수주 디테일 작성
        obtainorder_detail obtainorder_detail = new obtainorder_detail();
        //어떤 수주번호를 기반으로 하는지 저장, 그냥 수주번호 저장한다 생각
        obtainorder_detail.setOrderNumber(obtainorderNumber);
        obtainorder_detail.setOrder_Date(LocalDateTime.now());
        obtainorder_detail.setOrder_Amount(500L);
        obtainorder_detail.setWriter("최형석");
        obtainorder_detail.setProductName("양배추즙");
        obtainorder_detail.setCompany_name("삼성");
        obtainorder_detail.setState(obtainorder_state.ready);
        obtainorder_detail.setDelivery_Date(LocalDateTime.now().plusWeeks(1));

        obtain_order_DetailRepository.save(obtainorder_detail);
        //----------------------------------------------------------

        //수주정보를 기반으로 한 생산계획 작성
        productPlan productplan = new productPlan();
        productplan.setMaterials_Name(obtainorder_detail.getProductName());
        productplan.setOrder_Number(obtainorderNumber);
        productplan.setProductionPlanCode(LocalDateTime.now()+"prP-001");
        productplan.setState(productionPlan_state.ready);
        productplan.setP_startDate(LocalDateTime.now());
        productplan.setNow_Output(obtainorder_detail.getOrder_Amount());
        productplan.setNow_Output(0L);
        productPlanRepository.save(productplan);


        //----------------------------------------------------------

        //생산계획이 작성된다면 그에 따른 작업 지시들이 작성됨

        //1.전처리
        workOrderPlan workOrderPlanBefore = new workOrderPlan();
        workOrderPlanBefore.setProductPlanCode(productplan);
        workOrderPlanBefore.setProcessName("전처리");
        workOrderPlanBefore.setState(workOrderPlan_state.ready);
        workOrderPlanBefore.setP_startDate(productplan.getP_startDate());
        workOrderPlanBefore.setObtainorder_number(obtainorderNumber);
        workOrderPlanBefore.setMaterials_Name(productplan.getMaterials_Name());
        workOrderPlanBefore.setProduction_Plan_Id("A1");
        workOrderPlanRepository.save(workOrderPlanBefore);

        //2.칭량
        workOrderPlan workOrderPlanCount = new workOrderPlan();
        workOrderPlanCount.setProductPlanCode(productplan);
        workOrderPlanCount.setProcessName("칭량");
        workOrderPlanCount.setState(workOrderPlan_state.ready);
        workOrderPlanCount.setP_startDate(productplan.getP_startDate());
        workOrderPlanCount.setObtainorder_number(obtainorderNumber);
        workOrderPlanCount.setMaterials_Name(productplan.getMaterials_Name());
        workOrderPlanCount.setProduction_Plan_Id("A2");
        workOrderPlanRepository.save(workOrderPlanCount);

        //3.착즙
        workOrderPlan workOrderPlanJuice = new workOrderPlan();
        workOrderPlanJuice.setProductPlanCode(productplan);
        workOrderPlanJuice.setProcessName("착즙");
        workOrderPlanJuice.setState(workOrderPlan_state.ready);
        workOrderPlanJuice.setP_startDate(productplan.getP_startDate());
        workOrderPlanJuice.setObtainorder_number(obtainorderNumber);
        workOrderPlanJuice.setMaterials_Name(productplan.getMaterials_Name());
        workOrderPlanJuice.setProduction_Plan_Id("A3");
        workOrderPlanRepository.save(workOrderPlanJuice);

        //4.여과
        workOrderPlan workOrderPlanPercolation = new workOrderPlan();
        workOrderPlanPercolation.setProductPlanCode(productplan);
        workOrderPlanPercolation.setProcessName("여과");
        workOrderPlanPercolation.setState(workOrderPlan_state.ready);
        workOrderPlanPercolation.setP_startDate(productplan.getP_startDate());
        workOrderPlanPercolation.setObtainorder_number(obtainorderNumber);
        workOrderPlanPercolation.setMaterials_Name(productplan.getMaterials_Name());
        workOrderPlanPercolation.setProduction_Plan_Id("A4");
        workOrderPlanRepository.save(workOrderPlanPercolation);

        //5.살균
        workOrderPlan workOrderPlanSterilization = new workOrderPlan();
        workOrderPlanSterilization.setProductPlanCode(productplan);
        workOrderPlanSterilization.setProcessName("살균");
        workOrderPlanSterilization.setState(workOrderPlan_state.ready);
        workOrderPlanSterilization.setP_startDate(productplan.getP_startDate());
        workOrderPlanSterilization.setObtainorder_number(obtainorderNumber);
        workOrderPlanSterilization.setMaterials_Name(productplan.getMaterials_Name());
        workOrderPlanSterilization.setProduction_Plan_Id("A5");
        workOrderPlanRepository.save(workOrderPlanSterilization);

        //6.충진
        workOrderPlan workOrderPlanFilling = new workOrderPlan();
        workOrderPlanFilling.setProductPlanCode(productplan);
        workOrderPlanFilling.setProcessName("충진");
        workOrderPlanFilling.setState(workOrderPlan_state.ready);
        workOrderPlanFilling.setP_startDate(productplan.getP_startDate());
        workOrderPlanFilling.setObtainorder_number(obtainorderNumber);
        workOrderPlanFilling.setMaterials_Name(productplan.getMaterials_Name());
        workOrderPlanFilling.setProduction_Plan_Id("A6");
        workOrderPlanRepository.save(workOrderPlanFilling);

        //7.검사
        workOrderPlan workOrderPlanInspection = new workOrderPlan();
        workOrderPlanInspection.setProductPlanCode(productplan);
        workOrderPlanInspection.setProcessName("검사");
        workOrderPlanInspection.setState(workOrderPlan_state.ready);
        workOrderPlanInspection.setP_startDate(productplan.getP_startDate());
        workOrderPlanInspection.setObtainorder_number(obtainorderNumber);
        workOrderPlanInspection.setMaterials_Name(productplan.getMaterials_Name());
        workOrderPlanInspection.setProduction_Plan_Id("A7");
        workOrderPlanRepository.save(workOrderPlanInspection);

        //8.포장
        workOrderPlan workOrderPlanPakaging = new workOrderPlan();
        workOrderPlanPakaging.setProductPlanCode(productplan);
        workOrderPlanPakaging.setProcessName("포장");
        workOrderPlanPakaging.setState(workOrderPlan_state.ready);
        workOrderPlanPakaging.setP_startDate(productplan.getP_startDate());
        workOrderPlanPakaging.setObtainorder_number(obtainorderNumber);
        workOrderPlanPakaging.setMaterials_Name(productplan.getMaterials_Name());
        workOrderPlanPakaging.setProduction_Plan_Id("A8");
        workOrderPlanRepository.save(workOrderPlanPakaging);
    }


}
