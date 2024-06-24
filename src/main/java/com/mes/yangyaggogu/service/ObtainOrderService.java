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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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

        //인덱스가 계산되고 적절한 번호 출력
        int count = getObtainOrderNumber(addOrderDtoList.get(0).getOrder_Date());

        //같이 저장되니까 같은 수주번호를 가진 채 수주 디테일 저장
        for (AddOrderDto addOrderDto : addOrderDtoList) {

            if(addOrderDto.getProductName().equals("양배추즙")||addOrderDto.getProductName().equals("흑마늘즙")){

                Long Amount = addOrderDto.getOrder_Amount();

                if(Amount <= 250){
                    obtainorder_number addOrderNumber = new obtainorder_number();
                    addOrderNumber.setOrderNumber(LocalDate.now().toString()+"-"+count+"-"+addOrderDto.getProductName());
                    obtainOrderNumberRepository.save(addOrderNumber);

                    obtainorder_detail obtainorder_detail= addOrderDto.toEntity();

                    obtainorder_detail.setOrderNumber(addOrderNumber);
                    obtainorder_detail.setState(obtainorder_state.ready);

                    obtainorderDetailRepository.save(obtainorder_detail);

                } else if (Amount > 250 && Amount <= 500) {
                    for (int i = 1; i < 3; i++) {
                        obtainorder_number addOrderNumber = new obtainorder_number();
                        addOrderNumber.setOrderNumber(LocalDate.now().toString()+"-"+count+"-"+addOrderDto.getProductName()+i);
                        obtainOrderNumberRepository.save(addOrderNumber);

                        obtainorder_detail obtainorder_detail= addOrderDto.toEntity();

                        obtainorder_detail.setOrderNumber(addOrderNumber);
                        obtainorder_detail.setState(obtainorder_state.ready);
                        if(i >1){
                            obtainorder_detail.setOrder_Amount(Amount%250);
                        }else{
                            obtainorder_detail.setOrder_Amount(250L);
                        }

                        obtainorderDetailRepository.save(obtainorder_detail);

                    }

                } else if (Amount >500 && Amount <=750) {
                    for (int i = 1; i < 4; i++) {
                        obtainorder_number addOrderNumber = new obtainorder_number();
                        addOrderNumber.setOrderNumber(LocalDate.now().toString()+"-"+count+"-"+addOrderDto.getProductName()+i);
                        obtainOrderNumberRepository.save(addOrderNumber);

                        obtainorder_detail obtainorder_detail= addOrderDto.toEntity();

                        obtainorder_detail.setOrderNumber(addOrderNumber);
                        obtainorder_detail.setState(obtainorder_state.ready);

                        if(i >2){
                            obtainorder_detail.setOrder_Amount(Amount%500);
                        }else{
                            obtainorder_detail.setOrder_Amount(250L);
                        }
                        obtainorderDetailRepository.save(obtainorder_detail);
                    }

                } else if (Amount >750 && Amount <=1000) {
                    for (int i = 1; i < 5; i++) {
                        obtainorder_number addOrderNumber = new obtainorder_number();
                        addOrderNumber.setOrderNumber(LocalDate.now().toString()+"-"+count+"-"+addOrderDto.getProductName()+i);
                        obtainOrderNumberRepository.save(addOrderNumber);

                        obtainorder_detail obtainorder_detail= addOrderDto.toEntity();
                        obtainorder_detail.setOrderNumber(addOrderNumber);
                        obtainorder_detail.setState(obtainorder_state.ready);

                        if(i >3){
                            obtainorder_detail.setOrder_Amount(Amount%750);
                        }else{
                            obtainorder_detail.setOrder_Amount(250L);
                        }
                        obtainorderDetailRepository.save(obtainorder_detail);

                    }

                }
                
            }else{
                //젤리
                Long Amount = addOrderDto.getOrder_Amount();

                if(Amount <= 160){
                    obtainorder_number addOrderNumber = new obtainorder_number();
                    addOrderNumber.setOrderNumber(LocalDate.now().toString()+"-"+count+"-"+addOrderDto.getProductName());
                    obtainOrderNumberRepository.save(addOrderNumber);

                    obtainorder_detail obtainorder_detail= addOrderDto.toEntity();

                    obtainorder_detail.setOrderNumber(addOrderNumber);
                    obtainorder_detail.setState(obtainorder_state.ready);

                    obtainorderDetailRepository.save(obtainorder_detail);

                } else if (Amount > 160 && Amount <= 320) {
                    for (int i = 1; i < 3; i++) {
                        obtainorder_number addOrderNumber = new obtainorder_number();
                        addOrderNumber.setOrderNumber(LocalDate.now().toString()+"-"+count+"-"+addOrderDto.getProductName()+i);
                        obtainOrderNumberRepository.save(addOrderNumber);

                        obtainorder_detail obtainorder_detail= addOrderDto.toEntity();

                        obtainorder_detail.setOrderNumber(addOrderNumber);
                        obtainorder_detail.setState(obtainorder_state.ready);
                        if(i >1){
                            obtainorder_detail.setOrder_Amount(Amount%160);
                        }else{
                            obtainorder_detail.setOrder_Amount(160L);
                        }

                        obtainorderDetailRepository.save(obtainorder_detail);

                    }

                } else if (Amount >320 && Amount <=480) {
                    for (int i = 1; i < 4; i++) {
                        obtainorder_number addOrderNumber = new obtainorder_number();
                        addOrderNumber.setOrderNumber(LocalDate.now().toString()+"-"+count+"-"+addOrderDto.getProductName()+i);
                        obtainOrderNumberRepository.save(addOrderNumber);

                        obtainorder_detail obtainorder_detail= addOrderDto.toEntity();

                        obtainorder_detail.setOrderNumber(addOrderNumber);
                        obtainorder_detail.setState(obtainorder_state.ready);

                        if(i >2){
                            obtainorder_detail.setOrder_Amount(Amount%320);
                        }else{
                            obtainorder_detail.setOrder_Amount(160L);
                        }
                        obtainorderDetailRepository.save(obtainorder_detail);
                    }

                } else if (Amount >480 && Amount <=640) {
                    for (int i = 1; i < 5; i++) {
                        obtainorder_number addOrderNumber = new obtainorder_number();
                        addOrderNumber.setOrderNumber(LocalDate.now().toString()+"-"+count+"-"+addOrderDto.getProductName()+i);
                        obtainOrderNumberRepository.save(addOrderNumber);

                        obtainorder_detail obtainorder_detail= addOrderDto.toEntity();
                        obtainorder_detail.setOrderNumber(addOrderNumber);
                        obtainorder_detail.setState(obtainorder_state.ready);

                        if(i >3){
                            obtainorder_detail.setOrder_Amount(Amount%480);
                        }else{
                            obtainorder_detail.setOrder_Amount(160L);
                        }
                        obtainorderDetailRepository.save(obtainorder_detail);

                    }

                }else if (Amount >640 && Amount <=800) {
                    for (int i = 1; i < 6; i++) {
                        obtainorder_number addOrderNumber = new obtainorder_number();
                        addOrderNumber.setOrderNumber(LocalDate.now().toString()+"-"+count+"-"+addOrderDto.getProductName()+i);
                        obtainOrderNumberRepository.save(addOrderNumber);

                        obtainorder_detail obtainorder_detail= addOrderDto.toEntity();
                        obtainorder_detail.setOrderNumber(addOrderNumber);
                        obtainorder_detail.setState(obtainorder_state.ready);

                        if(i >4){
                            obtainorder_detail.setOrder_Amount(Amount%640);
                        }else{
                            obtainorder_detail.setOrder_Amount(160L);
                        }
                        obtainorderDetailRepository.save(obtainorder_detail);

                    }

                }else if (Amount >800 && Amount <=960) {
                    for (int i = 1; i < 7; i++) {
                        obtainorder_number addOrderNumber = new obtainorder_number();
                        addOrderNumber.setOrderNumber(LocalDate.now().toString()+"-"+count+"-"+addOrderDto.getProductName()+i);
                        obtainOrderNumberRepository.save(addOrderNumber);

                        obtainorder_detail obtainorder_detail= addOrderDto.toEntity();
                        obtainorder_detail.setOrderNumber(addOrderNumber);
                        obtainorder_detail.setState(obtainorder_state.ready);

                        if(i >5){
                            obtainorder_detail.setOrder_Amount(Amount%800);
                        }else{
                            obtainorder_detail.setOrder_Amount(160L);
                        }
                        obtainorderDetailRepository.save(obtainorder_detail);

                    }

                }else if (Amount >960 && Amount <=1000) {
                    for (int i = 1; i < 8; i++) {
                        obtainorder_number addOrderNumber = new obtainorder_number();
                        addOrderNumber.setOrderNumber(LocalDate.now().toString()+"-"+count+"-"+addOrderDto.getProductName()+i);
                        obtainOrderNumberRepository.save(addOrderNumber);

                        obtainorder_detail obtainorder_detail= addOrderDto.toEntity();
                        obtainorder_detail.setOrderNumber(addOrderNumber);
                        obtainorder_detail.setState(obtainorder_state.ready);

                        if(i >6){
                            obtainorder_detail.setOrder_Amount(Amount%960);
                        }else{
                            obtainorder_detail.setOrder_Amount(160L);
                        }
                        obtainorderDetailRepository.save(obtainorder_detail);

                    }

                }
                
            }


//        //인덱스가 계산된 수주번호 완성 !!
//        addOrderNumber.setOrderNumber(LocalDate.now().toString()+"-"+count);
//
//        obtainOrderNumberRepository.save(addOrderNumber);


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

           System.out.println(obtainorderNumber.getOrderNumber());

          String[] num  = obtainorderNumber.getOrderNumber().split("-");

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
    public boolean checkPossibleDay(obtainorder_detail obtainorderDetail){


        //시작일과 끝일을 계산
        LocalDate startDate = obtainorderDetail.getDelivery_Date().minusDays(3);
        LocalDate endDate = obtainorderDetail.getDelivery_Date();

        List<productPlan> productPlanList = productPlanRepository.getBestPost(startDate,endDate);

        int countJuice=0;
        int countJelly=0;


        if(obtainorderDetail.getProductName().equals("양배추즙")||obtainorderDetail.getProductName().equals("흑마늘즙")){
            countJuice = countJuice+1;
        }else if(obtainorderDetail.getProductName().equals("석류젤리")||obtainorderDetail.getProductName().equals("매실젤리")){
            countJelly=countJelly+1;
        }

        for(productPlan productPlan : productPlanList){

            if(productPlan.getMaterialsName().equals("양배추즙")||productPlan.getMaterialsName().equals("흑마늘즙")){
                countJuice = countJuice+1;
            }else if(productPlan.getMaterialsName().equals("석류젤리")||productPlan.getMaterialsName().equals("매실젤리")){
                countJelly=countJelly+1;
            }

        }

        if(countJuice >= 3 || countJelly >= 2){

            return false;

        }

        return true;
    }

    //계획이 합쳐질 수 있을지 파악
    public boolean checkPossibleAddPlan(obtainorder_detail obtainorderDetail){

         LocalDate StartDate = returnStartday(obtainorderDetail);

        //들어온 확정예정건의 시작일과 이름이 일치하는 계획을 불러옴
          List<productPlan> productPlanList = productPlanRepository.getEqualStartDatePlan(StartDate,obtainorderDetail.getProductName());


          //계획들을 반복해서 검사
          for(productPlan productPlan : productPlanList) {

              //비교 하려는 제품이름이 같은지 파악
              if (productPlan.getMaterialsName().equals(obtainorderDetail.getProductName())) {

                  //즙일 때
                  if (obtainorderDetail.getProductName().equals("양배추즙") || obtainorderDetail.getProductName().equals("흑마늘즙")) {

                      //두 캡파를 더함
                      Long capacity = productPlan.getTarget_Output() + obtainorderDetail.getOrder_Amount();

                      System.out.println(productPlan.getTarget_Output()+"몇이고");
                      if (productPlan.getTarget_Output() == 250L) {
                          continue;
                      }

                      //더한 캡파가 250박스 이하면 합칠 수 있으니 true
                      System.out.println(capacity+"캡파몇인데");
                      if (capacity <= 250L) {
                          return true;

                          //아니라면 false
                      } else return false;

                      //젤리일때
                  } else if (obtainorderDetail.getProductName().equals("매실젤리") || obtainorderDetail.getProductName().equals("석류젤리")) {

                      //두 캡파를 더함
                      Long capacity = productPlan.getTarget_Output() + obtainorderDetail.getOrder_Amount();

                      //더한 캡파가 160박스 이하면 합칠 수 있으니 true
                      if (capacity <= 160) {

                          return true;

                          //아니라면 false
                      } else return false;


                  }
              }
          }
          //이런 경우는 없겠지 ?
        return true;
    }

    //계획이 합쳐질 수 있을지 파악한 걸 합치는 작업
    public productPlan JoinProductPlan(LocalDate StartDate,obtainorder_detail comeObtain) {

        List<productPlan> productPlanList = productPlanRepository.getEqualStartDatePlan(StartDate, comeObtain.getProductName());

        for (productPlan productPlan : productPlanList) {
            if (productPlan.getMaterialsName().equals(comeObtain.getProductName())) {

                if (comeObtain.getProductName().equals("양배추즙") || comeObtain.getProductName().equals("흑마늘즙")) {

                    Long capacity = productPlan.getTarget_Output() + comeObtain.getOrder_Amount();

                    if (productPlan.getTarget_Output() == 250L) {

                        continue;
                    }

                    if (capacity <= 250L) {

                        productPlan existedPlan = productPlan;

                        productPlan newJoinProducePlan = new productPlan();

                        if (comeObtain.getProductName().equals("양배추즙")) {
                            newJoinProducePlan.setProductionPlanCode(comeObtain.getOrderNumber().getOrderNumber() + "CB" + "," + existedPlan.getProductionPlanCode());
                        } else if (comeObtain.getProductName().equals("흑마늘즙")) {
                            newJoinProducePlan.setProductionPlanCode(comeObtain.getOrderNumber().getOrderNumber() + "BG" + "," + existedPlan.getProductionPlanCode());
                        }

                        obtainorder_number fakeOrderNum = new obtainorder_number();
                        fakeOrderNum.setOrderNumber("복수 수주번호");

                        obtainOrderNumberRepository.save(fakeOrderNum);
                        newJoinProducePlan.setOrderNumber(fakeOrderNum);
                        newJoinProducePlan.setMaterialsName(comeObtain.getProductName());
                        newJoinProducePlan.setState(productionPlan_state.beforeOrder);
                        newJoinProducePlan.setTarget_Output(capacity);

                        newJoinProducePlan.setPstartDate(StartDate);
                        newJoinProducePlan.setPendDate(existedPlan.getPendDate());

                        productPlanRepository.delete(existedPlan);

                        return productPlanRepository.save(newJoinProducePlan);

                    } else return null;

                } else if (comeObtain.getProductName().equals("매실젤리") || comeObtain.getProductName().equals("석류젤리")) {

                    Long capacity = productPlan.getTarget_Output() + comeObtain.getOrder_Amount();

                    productPlan existedPlan = productPlan;

                    productPlan newJoinProducePlan = new productPlan();

                    if (capacity <= 160L) {
                        if (comeObtain.getProductName().equals("매실젤리")) {
                            newJoinProducePlan.setProductionPlanCode(comeObtain.getOrderNumber().getOrderNumber() + "MS" + "," + existedPlan.getProductionPlanCode());
                        } else if (comeObtain.getProductName().equals("석류젤리")) {
                            newJoinProducePlan.setProductionPlanCode(comeObtain.getOrderNumber().getOrderNumber() + "SS" + "," + existedPlan.getProductionPlanCode());
                        }

                        obtainorder_number fakeOrderNum = new obtainorder_number();
                        fakeOrderNum.setOrderNumber("복수 수주번호");

                        obtainOrderNumberRepository.save(fakeOrderNum);
                        newJoinProducePlan.setOrderNumber(fakeOrderNum);
                        newJoinProducePlan.setMaterialsName(comeObtain.getProductName());
                        newJoinProducePlan.setState(productionPlan_state.beforeOrder);
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
   //팝업창 데이터 수정
    public boolean updateOrder(OrderDtlDto orderDtlDto){
        try {
            Optional<obtainorder_detail> optionalObtainorderDetail = obtainorderDetailRepository.findById(orderDtlDto.getId());
            if (optionalObtainorderDetail.isPresent()) {
                obtainorder_detail obtainorderDetail = optionalObtainorderDetail.get();
                obtainorderDetail.setOrder_Amount(orderDtlDto.getOrder_Amount());
                obtainorderDetail.setDelivery_Date(orderDtlDto.getDelivery_Date());
                obtainorderDetailRepository.save(obtainorderDetail);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //팝업창 데이터 삭제
    @Transactional
    public boolean deleteOrder(Long id) {
        try {
            obtainorder_detail obtainorderDetail = obtainorderDetailRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
            obtainorderDetailRepository.delete(obtainorderDetail);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<obtainorder_detail> findByOrderNumber(obtainorder_number orderNumber) {
        return obtainorderDetailRepository.findByOrderNumber(orderNumber);
        //shipmentApiController에서 사용, 수주 상세 테이블에서 거래처 이름 받아오는데 씀
    }

    public LocalDate returnStartday(obtainorder_detail obtainorderDetail) {

        LocalDate StartDate = obtainorderDetail.getDelivery_Date().minusDays(3);
        LocalDate endDate = obtainorderDetail.getDelivery_Date();

        List<LocalDate> betweenAllDays = StartDate.datesUntil(endDate)
                .collect(Collectors.toList());

        for (LocalDate date : betweenAllDays) {
            if (date.getDayOfWeek().getValue() == 6||date.getDayOfWeek().getValue() == 7) {
                StartDate= StartDate.minusDays(2);
                break;
            }

        }


        return StartDate;
    }


    //엑셀 업로드 후 DB 저장
    /*public String upload(MultipartFile file){
        ArrayList<AddOrderDto> addOrderDtoList = new ArrayList<AddOrderDto>();
        String fileExtsn =
    }*/
    public List<AddOrderDto> ExcelFileUpload(MultipartFile file){
        List<AddOrderDto> addOrderDtoList = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())){
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet){
                //첫 번째 행은 헤더이므로 건너 뛴다
                if (row.getRowNum() == 0){
                    continue;
                }
                AddOrderDto addOrderDto = new AddOrderDto();
                addOrderDto.setCompany_name(row.getCell(0).getStringCellValue());
                addOrderDto.setProductName(row.getCell(1).getStringCellValue());
                addOrderDto.setOrder_Date(row.getCell(2).getLocalDateTimeCellValue().toLocalDate());
                addOrderDto.setOrder_Amount((long) row.getCell(3).getNumericCellValue());
                addOrderDto.setDelivery_Date(row.getCell(4).getLocalDateTimeCellValue().toLocalDate());
                addOrderDto.setWriter(row.getCell(5).getStringCellValue());

                addOrderDtoList.add(addOrderDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addOrderDtoList;
    }
}
