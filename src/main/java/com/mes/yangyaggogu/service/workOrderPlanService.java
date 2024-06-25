package com.mes.yangyaggogu.service;

import com.mes.yangyaggogu.constant.*;
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
    private final ingredientStockRepository ingredientStockRepository;
    private final ObtainOrderService obtainOrderService;


    //모든 작업지시 출력
    public List<workOrderPlan> getAll() {
        return workOrderPlanRepository.findAll();
    }


    public void saveWorkOrderPlan(workOrderPlan workOrder) {
        workOrderPlanRepository.save(workOrder);
        if ("포장".equals(workOrder.getProcessName()) && workOrderPlan_state.completed.equals(workOrder.getState())) {
            LocalDate expDate = LocalDate.from(workOrder.getP_endDate().plusMonths(6));
            finishedstock finished = finishedstock.builder()
                    .orderNumber(workOrder.getObtainorder_number())
                    .amount((long) workOrder.getTarget_Output())
                    .exp(expDate)
                    .materials_Name(workOrder.getMaterials_Name())
                    .state(finishedstock_state.in)
                    .build();

            finishedstockRepository.save(finished);
        }
    }


    //작업 시작
    public String start_Work(Long id,String producer){
        
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
                find_WorkPlan.setP_startDate(LocalDateTime.now());

                workOrderPlanRepository.save(find_WorkPlan);
                return "작업이 시작됩니다.";


            }else if(find_WorkPlan2.getState() != workOrderPlan_state.completed){
                return "선행 공정의 작업이 필요합니다.";
            }

            //첫 공정
        }else if(answer==1){

            //첫 공정 시 생산계획 번호와 같은 원자재 조회

            ingredientStock findIngredientStock = ingredientStockRepository.findByProductionPlanCode(find_WorkPlan.getProductPlanCode());

            //해당되는 원자재 재고가 검색되지 않았다는 것은 아직 발주되지 않았다는 뜻
            if(findIngredientStock == null){
                return "원자재가 입고되지 않았습니다.";
            }


            //해당되는 원자재 재고가 출고되어야만 공정이 시작할 수 있음
            if(findIngredientStock.getState() != rowStock_state.out){

                return "원자재가 출고되지 않았습니다.";
            }else{
                //첫 공정이면서 원자재 입 출고가 다 이루어 졌다면 작업을 시작

                find_WorkPlan.setProducer(producer);
                find_WorkPlan.setState(workOrderPlan_state.proceeding);
                find_WorkPlan.setP_startDate(LocalDateTime.now());
                workOrderPlanRepository.save(find_WorkPlan);

                return "작업이 시작됩니다.";
            }

        }
        return "오류발생";
    }

    //작업 종료
    public workOrderPlan stop_Work(Long id){

        workOrderPlan find_WorkPlan= workOrderPlanRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no such data"));

        find_WorkPlan.setState(workOrderPlan_state.completed);
        find_WorkPlan.setP_endDate(LocalDateTime.now());
        find_WorkPlan.setNow_Output(find_WorkPlan.getTarget_Output());

        if(find_WorkPlan.getProcessName().equals("포장")){

            String [] OrderNumbers = find_WorkPlan.getProductPlanCode().getProductionPlanCode().split(",");

            if(OrderNumbers.length > 1 ){

                for(String orderNum :OrderNumbers){


                    String ProductCode = orderNum.substring(orderNum.length() - 2);
                    String ProductName="" ;

                    switch (ProductCode){
                        case "CB":
                            ProductName="양배추즙";
                            break;
                        case "BG":
                            ProductName="흑마늘즙";
                            break;

                        case "MS":
                            ProductName="매실젤리";
                            break;

                        case "SS":
                            ProductName="석류젤리";
                            break;
                        default:
                            ProductName="알수없음";
                            break;
                    }


                      String realOrderNum = orderNum.substring(0,orderNum.length() - 2);

                      obtainorder_number obtainorderNumber= obtain_order_numberRepository.findById(realOrderNum).orElseThrow();

                      obtainorder_detail finded_obtainorder_detail = obtain_order_DetailRepository.findByProductNameAndOrderNumber(ProductName,obtainorderNumber);

                    LocalDate expDate = LocalDate.from(find_WorkPlan.getP_endDate().plusMonths(6));

                    finishedstock finished = finishedstock.builder()
                            .orderNumber(finded_obtainorder_detail.getOrderNumber())
                            .amount(finded_obtainorder_detail.getOrder_Amount())
                            .exp(expDate)
                            .materials_Name(finded_obtainorder_detail.getProductName())
                            .state(finishedstock_state.in)
                            .build();

                    finishedstockRepository.save(finished);

                }

            }else {

                LocalDate expDate = LocalDate.from(find_WorkPlan.getP_endDate().plusMonths(6));

                finishedstock finished = finishedstock.builder()
                        .orderNumber(find_WorkPlan.getObtainorder_number())
                        .amount((long) find_WorkPlan.getNow_Output())
                        //지금 생산량 받아오는중 . 나중에 수정필요할수도 있음
                        .exp(expDate)
                        .materials_Name(find_WorkPlan.getMaterials_Name())
                        .state(finishedstock_state.in)
                        .build();

                finishedstockRepository.save(finished);
            }


        }

        workOrderPlanRepository.save(find_WorkPlan);

        return find_WorkPlan;
    }


    //생산계획 합치고 작업지시 생성
    public void MakeWorkOrderPlanDataJoined (productPlan productplan) {


            if (productplan.getMaterialsName().equals("양배추즙") || productplan.getMaterialsName().equals("흑마늘즙")) {

                //생산계획이 작성된다면 그에 따른 작업 지시들이 작성
                Long calculatorOutput =  (1000 * productplan.getTarget_Output())/250;

                //1.전처리
                workOrderPlan workOrderPlanBefore = new workOrderPlan();
                workOrderPlanBefore.setProductPlanCode(productplan);
                workOrderPlanBefore.setProcessName("전처리");
                workOrderPlanBefore.setState(workOrderPlan_state.ready);
                workOrderPlanBefore.setObtainorder_number(productplan.getOrderNumber());
                workOrderPlanBefore.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanBefore.setProcessCode("A1");
                workOrderPlanBefore.setTarget_Output(calculatorOutput);
                workOrderPlanRepository.save(workOrderPlanBefore);

                //2.칭량
                workOrderPlan workOrderPlanCount = new workOrderPlan();
                workOrderPlanCount.setProductPlanCode(productplan);
                workOrderPlanCount.setProcessName("칭량");
                workOrderPlanCount.setState(workOrderPlan_state.ready);
                workOrderPlanCount.setObtainorder_number(productplan.getOrderNumber());
                workOrderPlanCount.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanCount.setProcessCode("A2");
                workOrderPlanCount.setTarget_Output(Math.round(calculatorOutput * 0.75*10)/10.0);
                workOrderPlanRepository.save(workOrderPlanCount);

                //3.착즙
                workOrderPlan workOrderPlanJuice = new workOrderPlan();
                workOrderPlanJuice.setProductPlanCode(productplan);
                workOrderPlanJuice.setProcessName("착즙");
                workOrderPlanJuice.setState(workOrderPlan_state.ready);
                workOrderPlanJuice.setObtainorder_number(productplan.getOrderNumber());
                workOrderPlanJuice.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanJuice.setProcessCode("A3");
                workOrderPlanJuice.setTarget_Output(Math.round(calculatorOutput * 0.75*10)/10.0);
                workOrderPlanRepository.save(workOrderPlanJuice);

                //4.여과
                workOrderPlan workOrderPlanPercolation = new workOrderPlan();
                workOrderPlanPercolation.setProductPlanCode(productplan);
                workOrderPlanPercolation.setProcessName("여과");
                workOrderPlanPercolation.setState(workOrderPlan_state.ready);
                workOrderPlanPercolation.setObtainorder_number(productplan.getOrderNumber());
                workOrderPlanPercolation.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanPercolation.setProcessCode("A4");
                workOrderPlanPercolation.setTarget_Output(Math.round((calculatorOutput * 0.75*0.2)* 10)/10.0);
                workOrderPlanRepository.save(workOrderPlanPercolation);

                //5.살균
                workOrderPlan workOrderPlanSterilization = new workOrderPlan();
                workOrderPlanSterilization.setProductPlanCode(productplan);
                workOrderPlanSterilization.setProcessName("살균");
                workOrderPlanSterilization.setState(workOrderPlan_state.ready);
                workOrderPlanSterilization.setObtainorder_number(productplan.getOrderNumber());
                workOrderPlanSterilization.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanSterilization.setProcessCode("A5");
                workOrderPlanSterilization.setTarget_Output( Math.round((((calculatorOutput * 0.75) * 0.2) / 2)* 10)/10.0);
                workOrderPlanRepository.save(workOrderPlanSterilization);

                //6.충진
                workOrderPlan workOrderPlanFilling = new workOrderPlan();
                workOrderPlanFilling.setProductPlanCode(productplan);
                workOrderPlanFilling.setProcessName("충진");
                workOrderPlanFilling.setState(workOrderPlan_state.ready);
                workOrderPlanFilling.setObtainorder_number(productplan.getOrderNumber());
                workOrderPlanFilling.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanFilling.setProcessCode("A6");
                workOrderPlanFilling.setTarget_Output( Math.round(((((calculatorOutput * 0.75) * 0.2) / 2)/10)* 10)/10.0);
                workOrderPlanRepository.save(workOrderPlanFilling);

                //7.검사
                workOrderPlan workOrderPlanInspection = new workOrderPlan();
                workOrderPlanInspection.setProductPlanCode(productplan);
                workOrderPlanInspection.setProcessName("검사");
                workOrderPlanInspection.setState(workOrderPlan_state.ready);
                workOrderPlanInspection.setObtainorder_number(productplan.getOrderNumber());
                workOrderPlanInspection.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanInspection.setProcessCode("A7");
                workOrderPlanInspection.setTarget_Output( Math.round(((((calculatorOutput * 0.75) * 0.2) / 2)/10)* 10)/10.0);
                workOrderPlanRepository.save(workOrderPlanInspection);

                //8.포장
                workOrderPlan workOrderPlanPakaging = new workOrderPlan();
                workOrderPlanPakaging.setProductPlanCode(productplan);
                workOrderPlanPakaging.setProcessName("포장");
                workOrderPlanPakaging.setState(workOrderPlan_state.ready);
                workOrderPlanPakaging.setObtainorder_number(productplan.getOrderNumber());
                workOrderPlanPakaging.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanPakaging.setProcessCode("A8");
                workOrderPlanPakaging.setTarget_Output( productplan.getTarget_Output());
                workOrderPlanRepository.save(workOrderPlanPakaging);

            }else if(productplan.getMaterialsName().equals("매실젤리") || productplan.getMaterialsName().equals("석류젤리")){


                double calculatorOutput =  (0.375 * productplan.getTarget_Output());

                //1.칭량
                workOrderPlan workOrderPlanCount = new workOrderPlan();
                workOrderPlanCount.setProductPlanCode(productplan);
                workOrderPlanCount.setProcessName("칭량");
                workOrderPlanCount.setState(workOrderPlan_state.ready);
                workOrderPlanCount.setObtainorder_number(productplan.getOrderNumber());
                workOrderPlanCount.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanCount.setProcessCode("B1");
                workOrderPlanCount.setTarget_Output( (productplan.getTarget_Output()*25)*5);
                workOrderPlanRepository.save(workOrderPlanCount);

                //2.혼합
                workOrderPlan workOrderPlanJuice = new workOrderPlan();
                workOrderPlanJuice.setProductPlanCode(productplan);
                workOrderPlanJuice.setProcessName("혼합");
                workOrderPlanJuice.setState(workOrderPlan_state.ready);
                workOrderPlanJuice.setObtainorder_number(productplan.getOrderNumber());
                workOrderPlanJuice.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanJuice.setProcessCode("B2");
                workOrderPlanJuice.setTarget_Output((productplan.getTarget_Output()*25)*15);
                workOrderPlanRepository.save(workOrderPlanJuice);

                //3.살균
                workOrderPlan workOrderPlanPercolation = new workOrderPlan();
                workOrderPlanPercolation.setProductPlanCode(productplan);
                workOrderPlanPercolation.setProcessName("살균");
                workOrderPlanPercolation.setState(workOrderPlan_state.ready);
                workOrderPlanPercolation.setObtainorder_number(productplan.getOrderNumber());
                workOrderPlanPercolation.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanPercolation.setProcessCode("B3");
                workOrderPlanPercolation.setTarget_Output((productplan.getTarget_Output()*25)*15);
                workOrderPlanRepository.save(workOrderPlanPercolation);

                //4.충진
                workOrderPlan workOrderPlanSterilization = new workOrderPlan();
                workOrderPlanSterilization.setProductPlanCode(productplan);
                workOrderPlanSterilization.setProcessName("충진");
                workOrderPlanSterilization.setState(workOrderPlan_state.ready);
                workOrderPlanSterilization.setObtainorder_number(productplan.getOrderNumber());
                workOrderPlanSterilization.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanSterilization.setProcessCode("B4");
                workOrderPlanSterilization.setTarget_Output(productplan.getTarget_Output()*25);
                workOrderPlanRepository.save(workOrderPlanSterilization);

                //5.냉각
                workOrderPlan workOrderPlanFilling = new workOrderPlan();
                workOrderPlanFilling.setProductPlanCode(productplan);
                workOrderPlanFilling.setProcessName("냉각");
                workOrderPlanFilling.setState(workOrderPlan_state.ready);
                workOrderPlanFilling.setObtainorder_number(productplan.getOrderNumber());
                workOrderPlanFilling.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanFilling.setProcessCode("B5");
                workOrderPlanFilling.setTarget_Output(productplan.getTarget_Output()*25);
                workOrderPlanRepository.save(workOrderPlanFilling);

                //6.검사
                workOrderPlan workOrderPlanInspection = new workOrderPlan();
                workOrderPlanInspection.setProductPlanCode(productplan);
                workOrderPlanInspection.setProcessName("검사");
                workOrderPlanInspection.setState(workOrderPlan_state.ready);
                workOrderPlanInspection.setObtainorder_number(productplan.getOrderNumber());
                workOrderPlanInspection.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanInspection.setProcessCode("B6");
                workOrderPlanInspection.setTarget_Output(productplan.getTarget_Output()*25);
                workOrderPlanRepository.save(workOrderPlanInspection);

                //7.포장
                workOrderPlan workOrderPlanPakaging = new workOrderPlan();
                workOrderPlanPakaging.setProductPlanCode(productplan);
                workOrderPlanPakaging.setProcessName("포장");
                workOrderPlanPakaging.setState(workOrderPlan_state.ready);
                workOrderPlanPakaging.setObtainorder_number(productplan.getOrderNumber());
                workOrderPlanPakaging.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanPakaging.setProcessCode("B7");
                workOrderPlanPakaging.setTarget_Output(productplan.getTarget_Output());
                workOrderPlanRepository.save(workOrderPlanPakaging);


            }


        }

    //생산계획 작성
    public void MakeWorkOrderPlanData (List<obtainorder_detail> Lists) {


        for (obtainorder_detail obtainorder_detail : Lists) {

            //수주정보를 기반으로 한 생산계획 작성
            productPlan productplan = new productPlan();
            productplan.setMaterialsName(obtainorder_detail.getProductName());
            productplan.setOrderNumber(obtainorder_detail.getOrderNumber());
            if (productplan.getMaterialsName().equals("양배추즙")) {
                productplan.setProductionPlanCode(obtainorder_detail.getOrderNumber().getOrderNumber() + "CB");
            } else if (productplan.getMaterialsName().equals("흑마늘즙")) {
                productplan.setProductionPlanCode(obtainorder_detail.getOrderNumber().getOrderNumber() + "BG");
            } else if (productplan.getMaterialsName().equals("매실젤리")) {
                productplan.setProductionPlanCode(obtainorder_detail.getOrderNumber().getOrderNumber() + "MS");
            } else if (productplan.getMaterialsName().equals("석류젤리")) {
                productplan.setProductionPlanCode(obtainorder_detail.getOrderNumber().getOrderNumber() + "SS");
            }
            productplan.setState(productionPlan_state.beforeOrder);
            productplan.setPstartDate(obtainOrderService.returnStartday(obtainorder_detail));
            productplan.setPendDate(obtainorder_detail.getDelivery_Date());
            productplan.setTarget_Output(obtainorder_detail.getOrder_Amount());
            productPlanRepository.save(productplan);


            //----------------------------------------------------------

            if (productplan.getMaterialsName().equals("양배추즙") || productplan.getMaterialsName().equals("흑마늘즙")) {

                //생산계획이 작성된다면 그에 따른 작업 지시들이 작성
                Long calculatorOutput =  (1000 * productplan.getTarget_Output())/250;

                //1.전처리
                workOrderPlan workOrderPlanBefore = new workOrderPlan();
                workOrderPlanBefore.setProductPlanCode(productplan);
                workOrderPlanBefore.setProcessName("전처리");
                workOrderPlanBefore.setState(workOrderPlan_state.ready);
                workOrderPlanBefore.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanBefore.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanBefore.setProcessCode("A1");
                workOrderPlanBefore.setTarget_Output(calculatorOutput);
                workOrderPlanRepository.save(workOrderPlanBefore);

                //2.칭량
                workOrderPlan workOrderPlanCount = new workOrderPlan();
                workOrderPlanCount.setProductPlanCode(productplan);
                workOrderPlanCount.setProcessName("칭량");
                workOrderPlanCount.setState(workOrderPlan_state.ready);
                workOrderPlanCount.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanCount.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanCount.setProcessCode("A2");
                workOrderPlanCount.setTarget_Output( Math.round(calculatorOutput * 0.75*10)/10.0);
                workOrderPlanRepository.save(workOrderPlanCount);

                //3.착즙
                workOrderPlan workOrderPlanJuice = new workOrderPlan();
                workOrderPlanJuice.setProductPlanCode(productplan);
                workOrderPlanJuice.setProcessName("착즙");
                workOrderPlanJuice.setState(workOrderPlan_state.ready);
                workOrderPlanJuice.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanJuice.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanJuice.setProcessCode("A3");
                workOrderPlanJuice.setTarget_Output(Math.round(calculatorOutput * 0.75*10)/10.0);
                workOrderPlanRepository.save(workOrderPlanJuice);

                //4.여과
                workOrderPlan workOrderPlanPercolation = new workOrderPlan();
                workOrderPlanPercolation.setProductPlanCode(productplan);
                workOrderPlanPercolation.setProcessName("여과");
                workOrderPlanPercolation.setState(workOrderPlan_state.ready);
                workOrderPlanPercolation.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanPercolation.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanPercolation.setProcessCode("A4");
                workOrderPlanPercolation.setTarget_Output(Math.round((calculatorOutput * 0.75*0.2)* 10)/10.0);
                workOrderPlanRepository.save(workOrderPlanPercolation);

                //5.살균
                workOrderPlan workOrderPlanSterilization = new workOrderPlan();
                workOrderPlanSterilization.setProductPlanCode(productplan);
                workOrderPlanSterilization.setProcessName("살균");
                workOrderPlanSterilization.setState(workOrderPlan_state.ready);
                workOrderPlanSterilization.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanSterilization.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanSterilization.setProcessCode("A5");
                workOrderPlanSterilization.setTarget_Output(Math.round((((calculatorOutput * 0.75) * 0.2) / 2)* 10)/10.0);
                workOrderPlanRepository.save(workOrderPlanSterilization);

                //6.충진
                workOrderPlan workOrderPlanFilling = new workOrderPlan();
                workOrderPlanFilling.setProductPlanCode(productplan);
                workOrderPlanFilling.setProcessName("충진");
                workOrderPlanFilling.setState(workOrderPlan_state.ready);
                workOrderPlanFilling.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanFilling.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanFilling.setProcessCode("A6");
                workOrderPlanFilling.setTarget_Output(Math.round((((((calculatorOutput * 0.75) * 0.2) / 2)/10))*1000* 10)/10.0);
                workOrderPlanRepository.save(workOrderPlanFilling);

                //7.검사
                workOrderPlan workOrderPlanInspection = new workOrderPlan();
                workOrderPlanInspection.setProductPlanCode(productplan);
                workOrderPlanInspection.setProcessName("검사");
                workOrderPlanInspection.setState(workOrderPlan_state.ready);
                workOrderPlanInspection.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanInspection.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanInspection.setProcessCode("A7");
                workOrderPlanInspection.setTarget_Output(Math.round((((((calculatorOutput * 0.75) * 0.2) / 2)/10))*1000* 10)/10.0);
                workOrderPlanRepository.save(workOrderPlanInspection);

                //8.포장
                workOrderPlan workOrderPlanPakaging = new workOrderPlan();
                workOrderPlanPakaging.setProductPlanCode(productplan);
                workOrderPlanPakaging.setProcessName("포장");
                workOrderPlanPakaging.setState(workOrderPlan_state.ready);
                workOrderPlanPakaging.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanPakaging.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanPakaging.setProcessCode("A8");
                workOrderPlanPakaging.setTarget_Output(productplan.getTarget_Output());
                workOrderPlanRepository.save(workOrderPlanPakaging);

            }else if(productplan.getMaterialsName().equals("매실젤리") || productplan.getMaterialsName().equals("석류젤리")){


                double calculatorOutput =  (0.375 * productplan.getTarget_Output());

                //1.칭량
                workOrderPlan workOrderPlanCount = new workOrderPlan();
                workOrderPlanCount.setProductPlanCode(productplan);
                workOrderPlanCount.setProcessName("칭량");
                workOrderPlanCount.setState(workOrderPlan_state.ready);
                workOrderPlanCount.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanCount.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanCount.setProcessCode("B1");
                workOrderPlanCount.setTarget_Output((productplan.getTarget_Output()*25)*5);
                workOrderPlanRepository.save(workOrderPlanCount);

                //2.혼합
                workOrderPlan workOrderPlanJuice = new workOrderPlan();
                workOrderPlanJuice.setProductPlanCode(productplan);
                workOrderPlanJuice.setProcessName("혼합");
                workOrderPlanJuice.setState(workOrderPlan_state.ready);
                workOrderPlanJuice.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanJuice.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanJuice.setProcessCode("B2");
                workOrderPlanJuice.setTarget_Output((productplan.getTarget_Output()*25)*15);
                workOrderPlanRepository.save(workOrderPlanJuice);

                //3.살균
                workOrderPlan workOrderPlanPercolation = new workOrderPlan();
                workOrderPlanPercolation.setProductPlanCode(productplan);
                workOrderPlanPercolation.setProcessName("살균");
                workOrderPlanPercolation.setState(workOrderPlan_state.ready);
                workOrderPlanPercolation.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanPercolation.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanPercolation.setProcessCode("B3");
                workOrderPlanPercolation.setTarget_Output((productplan.getTarget_Output()*25)*15);
                workOrderPlanRepository.save(workOrderPlanPercolation);

                //4.충진
                workOrderPlan workOrderPlanSterilization = new workOrderPlan();
                workOrderPlanSterilization.setProductPlanCode(productplan);
                workOrderPlanSterilization.setProcessName("충진");
                workOrderPlanSterilization.setState(workOrderPlan_state.ready);
                workOrderPlanSterilization.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanSterilization.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanSterilization.setProcessCode("B4");
                workOrderPlanSterilization.setTarget_Output(productplan.getTarget_Output()*25);
                workOrderPlanRepository.save(workOrderPlanSterilization);

                //5.냉각
                workOrderPlan workOrderPlanFilling = new workOrderPlan();
                workOrderPlanFilling.setProductPlanCode(productplan);
                workOrderPlanFilling.setProcessName("냉각");
                workOrderPlanFilling.setState(workOrderPlan_state.ready);
                workOrderPlanFilling.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanFilling.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanFilling.setProcessCode("B5");
                workOrderPlanFilling.setTarget_Output(productplan.getTarget_Output()*25);
                workOrderPlanRepository.save(workOrderPlanFilling);

                //6.검사
                workOrderPlan workOrderPlanInspection = new workOrderPlan();
                workOrderPlanInspection.setProductPlanCode(productplan);
                workOrderPlanInspection.setProcessName("검사");
                workOrderPlanInspection.setState(workOrderPlan_state.ready);
                workOrderPlanInspection.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanInspection.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanInspection.setProcessCode("B6");
                workOrderPlanInspection.setTarget_Output(productplan.getTarget_Output()*25);
                workOrderPlanRepository.save(workOrderPlanInspection);

                //7.포장
                workOrderPlan workOrderPlanPakaging = new workOrderPlan();
                workOrderPlanPakaging.setProductPlanCode(productplan);
                workOrderPlanPakaging.setProcessName("포장");
                workOrderPlanPakaging.setState(workOrderPlan_state.ready);
                workOrderPlanPakaging.setObtainorder_number(obtainorder_detail.getOrderNumber());
                workOrderPlanPakaging.setMaterials_Name(productplan.getMaterialsName());
                workOrderPlanPakaging.setProcessCode("B7");
                workOrderPlanPakaging.setTarget_Output(productplan.getTarget_Output());
                workOrderPlanRepository.save(workOrderPlanPakaging);


            }

        }

    }


    }
