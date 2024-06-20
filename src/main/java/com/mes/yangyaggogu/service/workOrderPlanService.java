package com.mes.yangyaggogu.service;

import com.mes.yangyaggogu.constant.finishedstock_state;
import com.mes.yangyaggogu.constant.obtainorder_state;
import com.mes.yangyaggogu.constant.productionPlan_state;
import com.mes.yangyaggogu.constant.workOrderPlan_state;
import com.mes.yangyaggogu.entity.*;
import com.mes.yangyaggogu.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class workOrderPlanService {

    private final obtainorder_detailRepository obtain_order_DetailRepository;
    private final obtainorder_numberRepository obtain_order_numberRepository;
    private final workOrderPlanRepository workOrderPlanRepository;
    private final productPlanRepository productPlanRepository;
    private final com.mes.yangyaggogu.repository.finishedstockRepository finishedstockRepository;

    public List<workOrderPlan> getAll() {
        return workOrderPlanRepository.findAll();
    }


    public void saveWorkOrderPlan(workOrderPlan workOrder) {
        workOrderPlanRepository.save(workOrder);
        if ("포장".equals(workOrder.getProcessName()) && workOrderPlan_state.completed.equals(workOrder.getState())) {
            LocalDateTime expDate = LocalDateTime.from(workOrder.getP_endDate().plusMonths(6));
            finishedstock finished = finishedstock.builder()
                    .orderNumber(workOrder.getObtainorder_number())
                    .amount(workOrder.getTarget_Output())
                    .exp(expDate)
                    .materials_Name(workOrder.getMaterials_Name())
                    .state(finishedstock_state.in)
                    .build();

            finishedstockRepository.save(finished);
        }
    }



    //작업 시작
    public boolean start_Work(Long id,String producer){
        
       workOrderPlan find_WorkPlan= workOrderPlanRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no such data"));


       //공정코드에서 숫자만 추출
        String[] strArr = find_WorkPlan.getProcessCode().replaceAll("[^0-9]" , "").split("");

        int answer = 0;

        for(String s : strArr) {
            answer += Integer.parseInt(s);
        }

        //A2,B2 공정부터 조회
        if(answer>1){

            workOrderPlan find_WorkPlan2 = new workOrderPlan();

            if(find_WorkPlan.getMaterials_Name().equals("양배추즙")||find_WorkPlan.getMaterials_Name().equals("흑마늘즙")) {

                 find_WorkPlan2 = workOrderPlanRepository.findByProductPlanCodeAndProcessCode(find_WorkPlan.getProductPlanCode(), "A" + (answer - 1));

            } else if (find_WorkPlan.getMaterials_Name().equals("매실젤리")||find_WorkPlan.getMaterials_Name().equals("석류젤리")) {

                find_WorkPlan2 = workOrderPlanRepository.findByProductPlanCodeAndProcessCode(find_WorkPlan.getProductPlanCode(), "B" + (answer - 1));
            }
            if(find_WorkPlan2.getState() == workOrderPlan_state.completed){

                find_WorkPlan.setProducer(producer);
                find_WorkPlan.setState(workOrderPlan_state.proceeding);

                workOrderPlanRepository.save(find_WorkPlan);
                return true;


            }else if(find_WorkPlan2.getState() != workOrderPlan_state.completed){
                return false;
            }
        }else if(answer==1){
            find_WorkPlan.setProducer(producer);
            find_WorkPlan.setState(workOrderPlan_state.proceeding);

            workOrderPlanRepository.save(find_WorkPlan);

        }
        return true;
    }

    //작업 종료
    public workOrderPlan stop_Work(Long id){

        workOrderPlan find_WorkPlan= workOrderPlanRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no such data"));


        find_WorkPlan.setState(workOrderPlan_state.completed);
        find_WorkPlan.setP_endDate(LocalDateTime.now());

        if(find_WorkPlan.getProcessName().equals("포장")){
            LocalDateTime expDate = find_WorkPlan.getP_endDate().plusMonths(6);

            finishedstock finished = finishedstock.builder()
                    .orderNumber(find_WorkPlan.getObtainorder_number())
                    .amount(find_WorkPlan.getNow_Output())
                    .exp(expDate)
                    .materials_Name(find_WorkPlan.getMaterials_Name())
                    .state(finishedstock_state.in)
                    .build();

            finishedstockRepository.save(finished);



        }

        workOrderPlanRepository.save(find_WorkPlan);

        return find_WorkPlan;
    }


    //생산계획 합치고 작업지시 생성
    public void MakeWorkOrderPlanDataJoined (productPlan productplan) {


            if (productplan.getMaterials_Name().equals("양배추즙") || productplan.getMaterials_Name().equals("흑마늘즙")) {

                //생산계획이 작성된다면 그에 따른 작업 지시들이 작성
                Long calculatorOutput =  (1000 * productplan.getTarget_Output())/250;

                //1.전처리
                workOrderPlan workOrderPlanBefore = new workOrderPlan();
                workOrderPlanBefore.setProductPlanCode(productplan);
                workOrderPlanBefore.setProcessName("전처리");
                workOrderPlanBefore.setState(workOrderPlan_state.ready);
                workOrderPlanBefore.setObtainorder_number(productplan.getOrder_Number());
                workOrderPlanBefore.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanBefore.setProcessCode("A1");
                workOrderPlanBefore.setTarget_Output(calculatorOutput);
                workOrderPlanRepository.save(workOrderPlanBefore);

                //2.칭량
                workOrderPlan workOrderPlanCount = new workOrderPlan();
                workOrderPlanCount.setProductPlanCode(productplan);
                workOrderPlanCount.setProcessName("칭량");
                workOrderPlanCount.setState(workOrderPlan_state.ready);
                workOrderPlanCount.setObtainorder_number(productplan.getOrder_Number());
                workOrderPlanCount.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanCount.setProcessCode("A2");
                workOrderPlanCount.setTarget_Output((long) (calculatorOutput * 0.75));
                workOrderPlanRepository.save(workOrderPlanCount);

                //3.착즙
                workOrderPlan workOrderPlanJuice = new workOrderPlan();
                workOrderPlanJuice.setProductPlanCode(productplan);
                workOrderPlanJuice.setProcessName("착즙");
                workOrderPlanJuice.setState(workOrderPlan_state.ready);
                workOrderPlanJuice.setObtainorder_number(productplan.getOrder_Number());
                workOrderPlanJuice.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanJuice.setProcessCode("A3");
                workOrderPlanJuice.setTarget_Output((long) (calculatorOutput * 0.75));
                workOrderPlanRepository.save(workOrderPlanJuice);

                //4.여과
                workOrderPlan workOrderPlanPercolation = new workOrderPlan();
                workOrderPlanPercolation.setProductPlanCode(productplan);
                workOrderPlanPercolation.setProcessName("여과");
                workOrderPlanPercolation.setState(workOrderPlan_state.ready);
                workOrderPlanPercolation.setObtainorder_number(productplan.getOrder_Number());
                workOrderPlanPercolation.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanPercolation.setProcessCode("A4");
                workOrderPlanPercolation.setTarget_Output((long) ((long) (calculatorOutput * 0.75) * 0.2));
                workOrderPlanRepository.save(workOrderPlanPercolation);

                //5.살균
                workOrderPlan workOrderPlanSterilization = new workOrderPlan();
                workOrderPlanSterilization.setProductPlanCode(productplan);
                workOrderPlanSterilization.setProcessName("살균");
                workOrderPlanSterilization.setState(workOrderPlan_state.ready);
                workOrderPlanSterilization.setObtainorder_number(productplan.getOrder_Number());
                workOrderPlanSterilization.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanSterilization.setProcessCode("A5");
                workOrderPlanSterilization.setTarget_Output((long) ((long) (calculatorOutput * 0.75) * 0.2) / 2);
                workOrderPlanRepository.save(workOrderPlanSterilization);

                //6.충진
                workOrderPlan workOrderPlanFilling = new workOrderPlan();
                workOrderPlanFilling.setProductPlanCode(productplan);
                workOrderPlanFilling.setProcessName("충진");
                workOrderPlanFilling.setState(workOrderPlan_state.ready);
                workOrderPlanFilling.setObtainorder_number(productplan.getOrder_Number());
                workOrderPlanFilling.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanFilling.setProcessCode("A6");
                workOrderPlanFilling.setTarget_Output((long) ((long) (calculatorOutput * 0.75) * 0.2) / 2);
                workOrderPlanRepository.save(workOrderPlanFilling);

                //7.검사
                workOrderPlan workOrderPlanInspection = new workOrderPlan();
                workOrderPlanInspection.setProductPlanCode(productplan);
                workOrderPlanInspection.setProcessName("검사");
                workOrderPlanInspection.setState(workOrderPlan_state.ready);
                workOrderPlanInspection.setObtainorder_number(productplan.getOrder_Number());
                workOrderPlanInspection.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanInspection.setProcessCode("A7");
                workOrderPlanInspection.setTarget_Output((long) ((long) (calculatorOutput * 0.75) * 0.2) / 2);
                workOrderPlanRepository.save(workOrderPlanInspection);

                //8.포장
                workOrderPlan workOrderPlanPakaging = new workOrderPlan();
                workOrderPlanPakaging.setProductPlanCode(productplan);
                workOrderPlanPakaging.setProcessName("포장");
                workOrderPlanPakaging.setState(workOrderPlan_state.ready);
                workOrderPlanPakaging.setObtainorder_number(productplan.getOrder_Number());
                workOrderPlanPakaging.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanPakaging.setProcessCode("A8");
                workOrderPlanPakaging.setTarget_Output((long) ((long) (calculatorOutput * 0.75) * 0.2) / 2);
                workOrderPlanRepository.save(workOrderPlanPakaging);

            }else if(productplan.getMaterials_Name().equals("매실젤리") || productplan.getMaterials_Name().equals("석류젤리")){


                double calculatorOutput =  (0.375 * productplan.getTarget_Output());

                //1.칭량
                workOrderPlan workOrderPlanCount = new workOrderPlan();
                workOrderPlanCount.setProductPlanCode(productplan);
                workOrderPlanCount.setProcessName("칭량");
                workOrderPlanCount.setState(workOrderPlan_state.ready);
                workOrderPlanCount.setObtainorder_number(productplan.getOrder_Number());
                workOrderPlanCount.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanCount.setProcessCode("B1");
                workOrderPlanCount.setTarget_Output((long) calculatorOutput);
                workOrderPlanRepository.save(workOrderPlanCount);

                //2.혼합
                workOrderPlan workOrderPlanJuice = new workOrderPlan();
                workOrderPlanJuice.setProductPlanCode(productplan);
                workOrderPlanJuice.setProcessName("혼합");
                workOrderPlanJuice.setState(workOrderPlan_state.ready);
                workOrderPlanJuice.setObtainorder_number(productplan.getOrder_Number());
                workOrderPlanJuice.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanJuice.setProcessCode("B2");
                workOrderPlanJuice.setTarget_Output((long) calculatorOutput);
                workOrderPlanRepository.save(workOrderPlanJuice);

                //3.살균
                workOrderPlan workOrderPlanPercolation = new workOrderPlan();
                workOrderPlanPercolation.setProductPlanCode(productplan);
                workOrderPlanPercolation.setProcessName("살균");
                workOrderPlanPercolation.setState(workOrderPlan_state.ready);
                workOrderPlanPercolation.setObtainorder_number(productplan.getOrder_Number());
                workOrderPlanPercolation.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanPercolation.setProcessCode("B3");
                workOrderPlanPercolation.setTarget_Output((long) calculatorOutput);
                workOrderPlanRepository.save(workOrderPlanPercolation);

                //4.살균
                workOrderPlan workOrderPlanSterilization = new workOrderPlan();
                workOrderPlanSterilization.setProductPlanCode(productplan);
                workOrderPlanSterilization.setProcessName("충진");
                workOrderPlanSterilization.setState(workOrderPlan_state.ready);
                workOrderPlanSterilization.setObtainorder_number(productplan.getOrder_Number());
                workOrderPlanSterilization.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanSterilization.setProcessCode("B4");
                workOrderPlanSterilization.setTarget_Output((long) calculatorOutput);
                workOrderPlanRepository.save(workOrderPlanSterilization);

                //5.냉각
                workOrderPlan workOrderPlanFilling = new workOrderPlan();
                workOrderPlanFilling.setProductPlanCode(productplan);
                workOrderPlanFilling.setProcessName("냉각");
                workOrderPlanFilling.setState(workOrderPlan_state.ready);
                workOrderPlanFilling.setObtainorder_number(productplan.getOrder_Number());
                workOrderPlanFilling.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanFilling.setProcessCode("B5");
                workOrderPlanFilling.setTarget_Output((long) calculatorOutput);
                workOrderPlanRepository.save(workOrderPlanFilling);

                //6.검사
                workOrderPlan workOrderPlanInspection = new workOrderPlan();
                workOrderPlanInspection.setProductPlanCode(productplan);
                workOrderPlanInspection.setProcessName("검사");
                workOrderPlanInspection.setState(workOrderPlan_state.ready);
                workOrderPlanInspection.setObtainorder_number(productplan.getOrder_Number());
                workOrderPlanInspection.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanInspection.setProcessCode("B6");
                workOrderPlanInspection.setTarget_Output((long) calculatorOutput);
                workOrderPlanRepository.save(workOrderPlanInspection);

                //7.포장
                workOrderPlan workOrderPlanPakaging = new workOrderPlan();
                workOrderPlanPakaging.setProductPlanCode(productplan);
                workOrderPlanPakaging.setProcessName("포장");
                workOrderPlanPakaging.setState(workOrderPlan_state.ready);
                workOrderPlanPakaging.setObtainorder_number(productplan.getOrder_Number());
                workOrderPlanPakaging.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanPakaging.setProcessCode("B7");
                workOrderPlanPakaging.setTarget_Output((long) calculatorOutput);
                workOrderPlanRepository.save(workOrderPlanPakaging);


            }


        }

    //생산계획 작성
    public void MakeWorkOrderPlanData (List<obtainorder_detail> Lists) {


        for (obtainorder_detail obtainorder_detail : Lists) {

            //수주정보를 기반으로 한 생산계획 작성
            productPlan productplan = new productPlan();
            productplan.setMaterials_Name(obtainorder_detail.getProductName());
            productplan.setOrder_Number(obtainorder_detail.getOrderNumber());
            if (productplan.getMaterials_Name().equals("양배추즙")) {
                productplan.setProductionPlanCode(obtainorder_detail.getOrderNumber().getOrder_Number() + "CB");
            } else if (productplan.getMaterials_Name().equals("흑마늘즙")) {
                productplan.setProductionPlanCode(obtainorder_detail.getOrderNumber().getOrder_Number() + "BG");
            } else if (productplan.getMaterials_Name().equals("매실젤리")) {
                productplan.setProductionPlanCode(obtainorder_detail.getOrderNumber().getOrder_Number() + "MS");
            } else if (productplan.getMaterials_Name().equals("석류젤리")) {
                productplan.setProductionPlanCode(obtainorder_detail.getOrderNumber().getOrder_Number() + "SS");
            }
            productplan.setState(productionPlan_state.ready);
            productplan.setPstartDate(obtainorder_detail.getDelivery_Date().minusDays(3));
            productplan.setPendDate(obtainorder_detail.getDelivery_Date());
            productplan.setTarget_Output(obtainorder_detail.getOrder_Amount());
            productPlanRepository.save(productplan);


            //----------------------------------------------------------

            if (productplan.getMaterials_Name().equals("양배추즙") || productplan.getMaterials_Name().equals("흑마늘즙")) {

                //생산계획이 작성된다면 그에 따른 작업 지시들이 작성
                Long calculatorOutput =  (1000 * productplan.getTarget_Output())/250;

                //1.전처리
                workOrderPlan workOrderPlanBefore = new workOrderPlan();
                workOrderPlanBefore.setProductPlanCode(productplan);
                workOrderPlanBefore.setProcessName("전처리");
                workOrderPlanBefore.setState(workOrderPlan_state.ready);
                workOrderPlanBefore.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanBefore.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanBefore.setProcessCode("A1");
                workOrderPlanBefore.setTarget_Output(calculatorOutput);
                workOrderPlanRepository.save(workOrderPlanBefore);

                //2.칭량
                workOrderPlan workOrderPlanCount = new workOrderPlan();
                workOrderPlanCount.setProductPlanCode(productplan);
                workOrderPlanCount.setProcessName("칭량");
                workOrderPlanCount.setState(workOrderPlan_state.ready);
                workOrderPlanCount.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanCount.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanCount.setProcessCode("A2");
                workOrderPlanCount.setTarget_Output((long) (calculatorOutput * 0.75));
                workOrderPlanRepository.save(workOrderPlanCount);

                //3.착즙
                workOrderPlan workOrderPlanJuice = new workOrderPlan();
                workOrderPlanJuice.setProductPlanCode(productplan);
                workOrderPlanJuice.setProcessName("착즙");
                workOrderPlanJuice.setState(workOrderPlan_state.ready);
                workOrderPlanJuice.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanJuice.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanJuice.setProcessCode("A3");
                workOrderPlanJuice.setTarget_Output((long) (calculatorOutput * 0.75));
                workOrderPlanRepository.save(workOrderPlanJuice);

                //4.여과
                workOrderPlan workOrderPlanPercolation = new workOrderPlan();
                workOrderPlanPercolation.setProductPlanCode(productplan);
                workOrderPlanPercolation.setProcessName("여과");
                workOrderPlanPercolation.setState(workOrderPlan_state.ready);
                workOrderPlanPercolation.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanPercolation.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanPercolation.setProcessCode("A4");
                workOrderPlanPercolation.setTarget_Output((long) ((long) (calculatorOutput * 0.75) * 0.2));
                workOrderPlanRepository.save(workOrderPlanPercolation);

                //5.살균
                workOrderPlan workOrderPlanSterilization = new workOrderPlan();
                workOrderPlanSterilization.setProductPlanCode(productplan);
                workOrderPlanSterilization.setProcessName("살균");
                workOrderPlanSterilization.setState(workOrderPlan_state.ready);
                workOrderPlanSterilization.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanSterilization.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanSterilization.setProcessCode("A5");
                workOrderPlanSterilization.setTarget_Output((long) ((long) (calculatorOutput * 0.75) * 0.2) / 2);
                workOrderPlanRepository.save(workOrderPlanSterilization);

                //6.충진
                workOrderPlan workOrderPlanFilling = new workOrderPlan();
                workOrderPlanFilling.setProductPlanCode(productplan);
                workOrderPlanFilling.setProcessName("충진");
                workOrderPlanFilling.setState(workOrderPlan_state.ready);
                workOrderPlanFilling.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanFilling.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanFilling.setProcessCode("A6");
                workOrderPlanFilling.setTarget_Output((long) ((long) (calculatorOutput * 0.75) * 0.2) / 2);
                workOrderPlanRepository.save(workOrderPlanFilling);

                //7.검사
                workOrderPlan workOrderPlanInspection = new workOrderPlan();
                workOrderPlanInspection.setProductPlanCode(productplan);
                workOrderPlanInspection.setProcessName("검사");
                workOrderPlanInspection.setState(workOrderPlan_state.ready);
                workOrderPlanInspection.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanInspection.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanInspection.setProcessCode("A7");
                workOrderPlanInspection.setTarget_Output((long) ((long) (calculatorOutput * 0.75) * 0.2) / 2);
                workOrderPlanRepository.save(workOrderPlanInspection);

                //8.포장
                workOrderPlan workOrderPlanPakaging = new workOrderPlan();
                workOrderPlanPakaging.setProductPlanCode(productplan);
                workOrderPlanPakaging.setProcessName("포장");
                workOrderPlanPakaging.setState(workOrderPlan_state.ready);
                workOrderPlanPakaging.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanPakaging.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanPakaging.setProcessCode("A8");
                workOrderPlanPakaging.setTarget_Output((long) ((long) (calculatorOutput * 0.75) * 0.2) / 2);
                workOrderPlanRepository.save(workOrderPlanPakaging);

            }else if(productplan.getMaterials_Name().equals("매실젤리") || productplan.getMaterials_Name().equals("석류젤리")){


                double calculatorOutput =  (0.375 * productplan.getTarget_Output());

                //1.칭량
                workOrderPlan workOrderPlanCount = new workOrderPlan();
                workOrderPlanCount.setProductPlanCode(productplan);
                workOrderPlanCount.setProcessName("칭량");
                workOrderPlanCount.setState(workOrderPlan_state.ready);
                workOrderPlanCount.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanCount.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanCount.setProcessCode("B1");
                workOrderPlanCount.setTarget_Output((long) calculatorOutput);
                workOrderPlanRepository.save(workOrderPlanCount);

                //2.혼합
                workOrderPlan workOrderPlanJuice = new workOrderPlan();
                workOrderPlanJuice.setProductPlanCode(productplan);
                workOrderPlanJuice.setProcessName("혼합");
                workOrderPlanJuice.setState(workOrderPlan_state.ready);
                workOrderPlanJuice.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanJuice.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanJuice.setProcessCode("B2");
                workOrderPlanJuice.setTarget_Output((long) calculatorOutput);
                workOrderPlanRepository.save(workOrderPlanJuice);

                //3.살균
                workOrderPlan workOrderPlanPercolation = new workOrderPlan();
                workOrderPlanPercolation.setProductPlanCode(productplan);
                workOrderPlanPercolation.setProcessName("살균");
                workOrderPlanPercolation.setState(workOrderPlan_state.ready);
                workOrderPlanPercolation.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanPercolation.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanPercolation.setProcessCode("B3");
                workOrderPlanPercolation.setTarget_Output((long) calculatorOutput);
                workOrderPlanRepository.save(workOrderPlanPercolation);

                //4.살균
                workOrderPlan workOrderPlanSterilization = new workOrderPlan();
                workOrderPlanSterilization.setProductPlanCode(productplan);
                workOrderPlanSterilization.setProcessName("충진");
                workOrderPlanSterilization.setState(workOrderPlan_state.ready);
                workOrderPlanSterilization.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanSterilization.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanSterilization.setProcessCode("B4");
                workOrderPlanSterilization.setTarget_Output((long) calculatorOutput);
                workOrderPlanRepository.save(workOrderPlanSterilization);

                //5.냉각
                workOrderPlan workOrderPlanFilling = new workOrderPlan();
                workOrderPlanFilling.setProductPlanCode(productplan);
                workOrderPlanFilling.setProcessName("냉각");
                workOrderPlanFilling.setState(workOrderPlan_state.ready);
                workOrderPlanFilling.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanFilling.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanFilling.setProcessCode("B5");
                workOrderPlanFilling.setTarget_Output((long) calculatorOutput);
                workOrderPlanRepository.save(workOrderPlanFilling);

                //6.검사
                workOrderPlan workOrderPlanInspection = new workOrderPlan();
                workOrderPlanInspection.setProductPlanCode(productplan);
                workOrderPlanInspection.setProcessName("검사");
                workOrderPlanInspection.setState(workOrderPlan_state.ready);
                workOrderPlanInspection.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanInspection.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanInspection.setProcessCode("B6");
                workOrderPlanInspection.setTarget_Output((long) calculatorOutput);
                workOrderPlanRepository.save(workOrderPlanInspection);

                //7.포장
                workOrderPlan workOrderPlanPakaging = new workOrderPlan();
                workOrderPlanPakaging.setProductPlanCode(productplan);
                workOrderPlanPakaging.setProcessName("포장");
                workOrderPlanPakaging.setState(workOrderPlan_state.ready);
                workOrderPlanPakaging.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanPakaging.setMaterials_Name(productplan.getMaterials_Name());
                workOrderPlanPakaging.setProcessCode("B7");
                workOrderPlanPakaging.setTarget_Output((long) calculatorOutput);
                workOrderPlanRepository.save(workOrderPlanPakaging);


            }

        }

    }


    }
