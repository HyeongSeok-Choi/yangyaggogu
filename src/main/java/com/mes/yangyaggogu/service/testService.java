package com.mes.yangyaggogu.service;


import com.mes.yangyaggogu.constant.obtainorder_state;
import com.mes.yangyaggogu.constant.productionPlan_state;
import com.mes.yangyaggogu.constant.workOrderPlan_state;
import com.mes.yangyaggogu.entity.obtainorder_detail;
import com.mes.yangyaggogu.entity.obtainorder_number;
import com.mes.yangyaggogu.entity.productPlan;
import com.mes.yangyaggogu.entity.workOrderPlan;
import com.mes.yangyaggogu.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class testService {

    private final obtainorder_detailRepository obtain_order_DetailRepository;
    private final obtainorder_numberRepository obtain_order_numberRepository;
    private final com.mes.yangyaggogu.repository.workOrderPlanRepository workOrderPlanRepository;
    private final com.mes.yangyaggogu.repository.productPlanRepository productPlanRepository;
    private final com.mes.yangyaggogu.repository.finishedstockRepository finishedstockRepository;

    //더미 데이터
    public void MakeWorkOrderPlanData (List<obtainorder_detail> Lists) {


        for (obtainorder_detail obtainorder_detail : Lists) {

            //수주정보를 기반으로 한 생산계획 작성
            productPlan productplan = new productPlan();
            productplan.setMaterials_Name(obtainorder_detail.getProductName());
            productplan.setOrder_Number(obtainorder_detail.getOrderNumber());
            productplan.setProductionPlanCode(LocalDateTime.now() + "prP-001");
            productplan.setState(productionPlan_state.ready);
            productplan.setPstartDate(LocalDate.now());
            productplan.setPendDate(LocalDate.now());
            productplan.setTarget_Output(obtainorder_detail.getOrder_Amount());
            productPlanRepository.save(productplan);


            //----------------------------------------------------------

            //생산계획이 작성된다면 그에 따른 작업 지시들이 작성됨

            //1.전처리
            workOrderPlan workOrderPlanBefore = new workOrderPlan();
            workOrderPlanBefore.setProductPlanCode(productplan);
            workOrderPlanBefore.setProcessName("전처리");
            workOrderPlanBefore.setState(workOrderPlan_state.ready);
            workOrderPlanBefore.setObtainorder_number(obtainorder_detail.getOrderNumber());
            workOrderPlanBefore.setMaterials_Name(productplan.getMaterials_Name());
            workOrderPlanBefore.setProcessCode("A1");
            workOrderPlanBefore.setTarget_Output(productplan.getTarget_Output());
            workOrderPlanRepository.save(workOrderPlanBefore);

            //2.칭량
            workOrderPlan workOrderPlanCount = new workOrderPlan();
            workOrderPlanCount.setProductPlanCode(productplan);
            workOrderPlanCount.setProcessName("칭량");
            workOrderPlanCount.setState(workOrderPlan_state.ready);
            workOrderPlanCount.setObtainorder_number(obtainorder_detail.getOrderNumber());
            workOrderPlanCount.setMaterials_Name(productplan.getMaterials_Name());
            workOrderPlanCount.setProcessCode("A2");
            workOrderPlanCount.setTarget_Output((long) (productplan.getTarget_Output() * 0.75));
            workOrderPlanRepository.save(workOrderPlanCount);

            //3.착즙
            workOrderPlan workOrderPlanJuice = new workOrderPlan();
            workOrderPlanJuice.setProductPlanCode(productplan);
            workOrderPlanJuice.setProcessName("착즙");
            workOrderPlanJuice.setState(workOrderPlan_state.ready);
            workOrderPlanJuice.setObtainorder_number(obtainorder_detail.getOrderNumber());
            workOrderPlanJuice.setMaterials_Name(productplan.getMaterials_Name());
            workOrderPlanJuice.setProcessCode("A3");
            workOrderPlanJuice.setTarget_Output((long) (productplan.getTarget_Output() * 0.75));
            workOrderPlanRepository.save(workOrderPlanJuice);

            //4.여과
            workOrderPlan workOrderPlanPercolation = new workOrderPlan();
            workOrderPlanPercolation.setProductPlanCode(productplan);
            workOrderPlanPercolation.setProcessName("여과");
            workOrderPlanPercolation.setState(workOrderPlan_state.ready);
            workOrderPlanPercolation.setObtainorder_number(obtainorder_detail.getOrderNumber());
            workOrderPlanPercolation.setMaterials_Name(productplan.getMaterials_Name());
            workOrderPlanPercolation.setProcessCode("A4");
            workOrderPlanPercolation.setTarget_Output((long) ((long) (productplan.getTarget_Output() * 0.75) * 0.2));
            workOrderPlanRepository.save(workOrderPlanPercolation);

            //5.살균
            workOrderPlan workOrderPlanSterilization = new workOrderPlan();
            workOrderPlanSterilization.setProductPlanCode(productplan);
            workOrderPlanSterilization.setProcessName("살균");
            workOrderPlanSterilization.setState(workOrderPlan_state.ready);
            workOrderPlanSterilization.setObtainorder_number(obtainorder_detail.getOrderNumber());
            workOrderPlanSterilization.setMaterials_Name(productplan.getMaterials_Name());
            workOrderPlanSterilization.setProcessCode("A5");
            workOrderPlanSterilization.setTarget_Output((long) ((long) (productplan.getTarget_Output() * 0.75) * 0.2) / 2);
            workOrderPlanRepository.save(workOrderPlanSterilization);

            //6.충진
            workOrderPlan workOrderPlanFilling = new workOrderPlan();
            workOrderPlanFilling.setProductPlanCode(productplan);
            workOrderPlanFilling.setProcessName("충진");
            workOrderPlanFilling.setState(workOrderPlan_state.ready);
            workOrderPlanFilling.setObtainorder_number(obtainorder_detail.getOrderNumber());
            workOrderPlanFilling.setMaterials_Name(productplan.getMaterials_Name());
            workOrderPlanFilling.setProcessCode("A6");
            workOrderPlanFilling.setTarget_Output((long) ((long) (productplan.getTarget_Output() * 0.75) * 0.2) / 2);
            workOrderPlanRepository.save(workOrderPlanFilling);

            //7.검사
            workOrderPlan workOrderPlanInspection = new workOrderPlan();
            workOrderPlanInspection.setProductPlanCode(productplan);
            workOrderPlanInspection.setProcessName("검사");
            workOrderPlanInspection.setState(workOrderPlan_state.ready);
            workOrderPlanInspection.setObtainorder_number(obtainorder_detail.getOrderNumber());
            workOrderPlanInspection.setMaterials_Name(productplan.getMaterials_Name());
            workOrderPlanInspection.setProcessCode("A7");
            workOrderPlanInspection.setTarget_Output((long) ((long) (productplan.getTarget_Output() * 0.75) * 0.2) / 2);
            workOrderPlanRepository.save(workOrderPlanInspection);

            //8.포장
            workOrderPlan workOrderPlanPakaging = new workOrderPlan();
            workOrderPlanPakaging.setProductPlanCode(productplan);
            workOrderPlanPakaging.setProcessName("포장");
            workOrderPlanPakaging.setState(workOrderPlan_state.ready);
            workOrderPlanPakaging.setObtainorder_number(obtainorder_detail.getOrderNumber());
            workOrderPlanPakaging.setMaterials_Name(productplan.getMaterials_Name());
            workOrderPlanPakaging.setProcessCode("A8");
            workOrderPlanPakaging.setTarget_Output((long) ((long) (productplan.getTarget_Output() * 0.75) * 0.2) / 2);
            workOrderPlanRepository.save(workOrderPlanPakaging);
        }



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
        obtainorder_detail.setOrderDate(LocalDate.now());
        obtainorder_detail.setOrder_Amount(500L);
        obtainorder_detail.setWriter("최형석");
        obtainorder_detail.setProductName("매실스틱");
        obtainorder_detail.setCompany_name("삼성");
        obtainorder_detail.setState(obtainorder_state.ready);
        obtainorder_detail.setDelivery_Date(LocalDate.now());

        obtain_order_DetailRepository.save(obtainorder_detail);
        //----------------------------------------------------------

        //수주정보를 기반으로 한 생산계획 작성
        productPlan productplan = new productPlan();
        productplan.setMaterials_Name(obtainorder_detail.getProductName());
        productplan.setOrder_Number(obtainorderNumber);
        productplan.setProductionPlanCode(LocalDateTime.now() + "prP-001");
        productplan.setState(productionPlan_state.ready);
        productplan.setNow_Output(obtainorder_detail.getOrder_Amount());
        productplan.setNow_Output(0L);
        productPlanRepository.save(productplan);


        //----------------------------------------------------------

        //생산계획이 작성된다면 그에 따른 작업 지시들이 작성됨

        //1.칭량
        workOrderPlan workOrderPlanCount = new workOrderPlan();
        workOrderPlanCount.setProductPlanCode(productplan);
        workOrderPlanCount.setProcessName("칭량");
        workOrderPlanCount.setState(workOrderPlan_state.ready);
        workOrderPlanCount.setObtainorder_number(obtainorderNumber);
        workOrderPlanCount.setMaterials_Name(productplan.getMaterials_Name());
        workOrderPlanCount.setProcessCode("B1");
        workOrderPlanCount.setTarget_Output(500L);
        workOrderPlanRepository.save(workOrderPlanCount);

        //2.혼합
        workOrderPlan workOrderPlanJuice = new workOrderPlan();
        workOrderPlanJuice.setProductPlanCode(productplan);
        workOrderPlanJuice.setProcessName("혼합");
        workOrderPlanJuice.setState(workOrderPlan_state.ready);
        workOrderPlanJuice.setObtainorder_number(obtainorderNumber);
        workOrderPlanJuice.setMaterials_Name(productplan.getMaterials_Name());
        workOrderPlanJuice.setProcessCode("B2");
        workOrderPlanJuice.setTarget_Output(500L);
        workOrderPlanRepository.save(workOrderPlanJuice);

        //3.살균
        workOrderPlan workOrderPlanPercolation = new workOrderPlan();
        workOrderPlanPercolation.setProductPlanCode(productplan);
        workOrderPlanPercolation.setProcessName("살균");
        workOrderPlanPercolation.setState(workOrderPlan_state.ready);
        workOrderPlanPercolation.setObtainorder_number(obtainorderNumber);
        workOrderPlanPercolation.setMaterials_Name(productplan.getMaterials_Name());
        workOrderPlanPercolation.setProcessCode("B3");
        workOrderPlanPercolation.setTarget_Output(500L);
        workOrderPlanRepository.save(workOrderPlanPercolation);

        //4.살균
        workOrderPlan workOrderPlanSterilization = new workOrderPlan();
        workOrderPlanSterilization.setProductPlanCode(productplan);
        workOrderPlanSterilization.setProcessName("충진");
        workOrderPlanSterilization.setState(workOrderPlan_state.ready);
        workOrderPlanSterilization.setObtainorder_number(obtainorderNumber);
        workOrderPlanSterilization.setMaterials_Name(productplan.getMaterials_Name());
        workOrderPlanSterilization.setProcessCode("B4");
        workOrderPlanSterilization.setTarget_Output(500L);
        workOrderPlanRepository.save(workOrderPlanSterilization);

        //5.냉각
        workOrderPlan workOrderPlanFilling = new workOrderPlan();
        workOrderPlanFilling.setProductPlanCode(productplan);
        workOrderPlanFilling.setProcessName("냉각");
        workOrderPlanFilling.setState(workOrderPlan_state.ready);
        workOrderPlanFilling.setObtainorder_number(obtainorderNumber);
        workOrderPlanFilling.setMaterials_Name(productplan.getMaterials_Name());
        workOrderPlanFilling.setProcessCode("B5");
        workOrderPlanFilling.setTarget_Output(500L);
        workOrderPlanRepository.save(workOrderPlanFilling);

        //6.검사
        workOrderPlan workOrderPlanInspection = new workOrderPlan();
        workOrderPlanInspection.setProductPlanCode(productplan);
        workOrderPlanInspection.setProcessName("검사");
        workOrderPlanInspection.setState(workOrderPlan_state.ready);

        workOrderPlanInspection.setObtainorder_number(obtainorderNumber);
        workOrderPlanInspection.setMaterials_Name(productplan.getMaterials_Name());
        workOrderPlanInspection.setProcessCode("B6");
        workOrderPlanInspection.setTarget_Output(500L);
        workOrderPlanRepository.save(workOrderPlanInspection);

        //7.포장
        workOrderPlan workOrderPlanPakaging = new workOrderPlan();
        workOrderPlanPakaging.setProductPlanCode(productplan);
        workOrderPlanPakaging.setProcessName("포장");
        workOrderPlanPakaging.setState(workOrderPlan_state.ready);
        workOrderPlanPakaging.setObtainorder_number(obtainorderNumber);
        workOrderPlanPakaging.setMaterials_Name(productplan.getMaterials_Name());
        workOrderPlanPakaging.setProcessCode("B7");
        workOrderPlanPakaging.setTarget_Output(500L);
        workOrderPlanRepository.save(workOrderPlanPakaging);


    }


}
