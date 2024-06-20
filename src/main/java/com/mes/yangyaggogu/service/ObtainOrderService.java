package com.mes.yangyaggogu.service;

import com.mes.yangyaggogu.constant.obtainorder_state;
import com.mes.yangyaggogu.constant.productionPlan_state;
import com.mes.yangyaggogu.dto.AddOrderDto;
import com.mes.yangyaggogu.dto.OrderDtlDto;
import com.mes.yangyaggogu.entity.obtainorder_detail;
import com.mes.yangyaggogu.entity.obtainorder_number;
import com.mes.yangyaggogu.entity.productPlan;
import com.mes.yangyaggogu.repository.obtainorder_detailRepository;
import com.mes.yangyaggogu.repository.obtainorder_numberRepository;
import com.mes.yangyaggogu.repository.productPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ObtainOrderService {

    public final obtainorder_detailRepository obtainorderDetailRepository;
    public final obtainorder_numberRepository obtainOrderNumberRepository;
    public final productPlanRepository productPlanRepository;

    //수주 현황 조회, 수주 상세 조회
    public List<obtainorder_detail> getObtainOrderDtl() {
        return obtainorderDetailRepository.findAll();
    }


    //아이디로 수주정보 얻기
    public obtainorder_detail getObtainOrderDtlById(Long id) {
        return obtainorderDetailRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found :"));
    }


    //저장
    public obtainorder_detail save(obtainorder_detail obtainorder_detail) {

        return obtainorderDetailRepository.save(obtainorder_detail);
    }


    //등록
    @Transactional
    public boolean saveList(List<AddOrderDto> addOrderDtoList){


        //수주 번호의 생성
        obtainorder_number addOrderNumber = new obtainorder_number();

        int count = getObtainOrderNumber(addOrderDtoList.get(0).getOrder_Date());


        addOrderNumber.setOrder_Number(LocalDate.now().toString()+"-"+count);

        obtainOrderNumberRepository.save(addOrderNumber);


        //수주 디테일 저장
        for (AddOrderDto addOrderDto : addOrderDtoList) {

            obtainorder_detail obtainorder_detail= addOrderDto.toEntity();

            obtainorder_detail.setOrderNumber(addOrderNumber);
            obtainorder_detail.setState(obtainorder_state.ready);

            obtainorderDetailRepository.save(obtainorder_detail);
        }
        return true;
    }

    //수주번호 인덱스 계산
    public int getObtainOrderNumber(LocalDate date){

        int addNumber = 0;

        //날짜가 있는지 확인
       boolean checkLocalDate = obtainorderDetailRepository.existsByOrderDate(date);

       //날짜가 존재한다면
       if(checkLocalDate){

          obtainorder_detail findObtain = obtainorderDetailRepository.findTopByOrderByIdDesc();

          obtainorder_number obtainorderNumber = findObtain.getOrderNumber();

           System.out.println(obtainorderNumber.getOrder_Number());

          String[] num  = obtainorderNumber.getOrder_Number().split("-");

           addNumber = Integer.parseInt(num[3]);

          return addNumber+1;

       }else{
           addNumber = 1;

       }

       return addNumber;

    }

    //수주 상세 조회
    public OrderDtlDto getOrderDetailById(Long id){

        obtainorder_detail findObtain=
        obtainorderDetailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found :" + id));

        OrderDtlDto findedDto = new OrderDtlDto(findObtain);

        return findedDto;
    }

    //3라인이 넘는지 안넘는지 체크
    public boolean checkPossibleDay(LocalDate localDate,String comeProductName,Long targetOutput){


        LocalDate startDate = localDate.minusDays(3);
        LocalDate endDate = localDate;

        List<productPlan> productPlanList = productPlanRepository.getBestPost(startDate,endDate);

        int countJuice=0;
        int countJelly=0;


        if(comeProductName.equals("양배추즙")||comeProductName.equals("흑마늘즙")){
            countJuice = countJuice+1;
        }else if(comeProductName.equals("석류젤리")||comeProductName.equals("매실젤리")){
            countJelly=countJelly+1;
        }

        for(productPlan productPlan : productPlanList){

            if(productPlan.getMaterials_Name().equals("양배추즙")||productPlan.getMaterials_Name().equals("흑마늘즙")){
                countJuice = countJuice+1;
            }else if(productPlan.getMaterials_Name().equals("석류젤리")||productPlan.getMaterials_Name().equals("매실젤리")){
                countJelly=countJelly+1;
            }

        }

        if(countJuice >= 3 || countJelly >= 2){

            return false;

        }

        return true;
    }

    //계획이 합쳐질 수 있을지 파악
    public boolean checkPossibleAddPlan(LocalDate StartDate,String comeProductName,Long targetOutput){

          List<productPlan> productPlanList = productPlanRepository.getEqualStartDatePlan(StartDate,comeProductName);

          for(productPlan productPlan : productPlanList) {

              if (productPlan.getMaterials_Name().equals(comeProductName)) {

                  if (comeProductName.equals("양배추즙") || comeProductName.equals("흑마늘즙")) {

                      Long capacity = productPlan.getTarget_Output() + targetOutput;

                      if (capacity <= 250L) {

                          return true;

                      } else return false;

                  } else if (comeProductName.equals("매실젤리") || comeProductName.equals("석류젤리")) {

                      Long capacity = productPlan.getTarget_Output() + targetOutput;

                      if (capacity <= 160) {

                          return true;

                      } else return false;


                  }
              }
          }
        return true;
    }

    //계획이 합쳐질 수 있을지 파악한 걸 합치는 작업
    public productPlan JoinProductPlan(LocalDate StartDate,String comeProductName,Long targetOutput,obtainorder_number obtainorderNumber){

        List<productPlan> productPlanList = productPlanRepository.getEqualStartDatePlan(StartDate,comeProductName);

        for(productPlan productPlan : productPlanList){
            if (productPlan.getMaterials_Name().equals(comeProductName)) {

                if (comeProductName.equals("양배추즙") || comeProductName.equals("흑마늘즙")) {

                    Long capacity = productPlan.getTarget_Output() + targetOutput;

                    if (capacity <= 250L) {

                        productPlan existedPlan = productPlan;

                        productPlan newJoinProducePlan = new productPlan();

                        if (comeProductName.equals("양배추즙")) {
                            newJoinProducePlan.setProductionPlanCode(obtainorderNumber.getOrder_Number() + "CB"+","+existedPlan.getProductionPlanCode());
                        } else if (comeProductName.equals("흑마늘즙")) {
                            newJoinProducePlan.setProductionPlanCode(obtainorderNumber.getOrder_Number() + "BG"+","+existedPlan.getProductionPlanCode());
                        }

                        obtainorder_number fakeOrderNum = new obtainorder_number();
                        fakeOrderNum.setOrder_Number("복수 수주번호");

                        obtainOrderNumberRepository.save(fakeOrderNum);
                        newJoinProducePlan.setOrder_Number(fakeOrderNum);
                        newJoinProducePlan.setMaterials_Name(comeProductName);
                        newJoinProducePlan.setState(productionPlan_state.ready);
                        newJoinProducePlan.setTarget_Output(capacity);

                        newJoinProducePlan.setPstartDate(StartDate);
                        newJoinProducePlan.setPendDate(existedPlan.getPendDate());

                        productPlanRepository.delete(existedPlan);

                        return productPlanRepository.save(newJoinProducePlan);

                    } else return null;

                } else if (comeProductName.equals("매실젤리") || comeProductName.equals("석류젤리")) {

                    Long capacity = productPlan.getTarget_Output() + targetOutput;

                    productPlan existedPlan = productPlan;

                    productPlan newJoinProducePlan = new productPlan();

                    if (capacity <= 160L) {
                        if (comeProductName.equals("매실젤리")) {
                            newJoinProducePlan.setProductionPlanCode(obtainorderNumber.getOrder_Number() + "MS" + "," + existedPlan.getProductionPlanCode());
                        } else if (comeProductName.equals("석류젤리")) {
                            newJoinProducePlan.setProductionPlanCode(obtainorderNumber.getOrder_Number() + "SS" + "," + existedPlan.getProductionPlanCode());
                        }

                        obtainorder_number fakeOrderNum = new obtainorder_number();
                        fakeOrderNum.setOrder_Number("복수 수주번호");

                        obtainOrderNumberRepository.save(fakeOrderNum);
                        newJoinProducePlan.setOrder_Number(fakeOrderNum);
                        newJoinProducePlan.setMaterials_Name(comeProductName);
                        newJoinProducePlan.setState(productionPlan_state.ready);
                        newJoinProducePlan.setTarget_Output(capacity);

                        newJoinProducePlan.setPstartDate(StartDate);
                        newJoinProducePlan.setPendDate(existedPlan.getPendDate());

                        productPlanRepository.delete(existedPlan);

                        return productPlanRepository.save(newJoinProducePlan);


                    }

                }

            }
        }
        return null;
    }

}
