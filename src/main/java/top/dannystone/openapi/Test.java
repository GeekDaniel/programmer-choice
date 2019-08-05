package top.dannystone.openapi;

import com.alibaba.fastjson.JSONObject;
import com.piaoniu.open.api.PNApi;
import com.piaoniu.open.api.domain.activity.request.QueryStockAndPriceRequest;
import com.piaoniu.open.api.domain.activity.response.QueryStockAndPriceResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2019/6/17 5:50 PM
 */
public class Test {
    public static void main(String[] args){
        PNApi pnApi = new PNApi("http://openapi.piaoniu.com", "10020", "i3vtal55qwert");
//        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
//        createOrderRequest.setOrderId("pntest2");
//        createOrderRequest.setTicketCategoryId(1544805);
//        createOrderRequest.setAmount(new BigDecimal("59"));
//        createOrderRequest.setCount(1);
//        createOrderRequest.setDeliverType(1);
//        createOrderRequest.setPostage(new BigDecimal("10"));
//        JSONObject idCard=new JSONObject();
//        idCard.put("name", "daniel");
//        idCard.put("idCard", "320911199109170029");
//        JSONArray idCards = new JSONArray();
//        idCards.add(idCard);
//        createOrderRequest.setIdCards(idCards.toJSONString());
//        createOrderRequest.setReceiverName("asdf");
//        createOrderRequest.setPhone("10000000000");
//        createOrderRequest.setAddress("asdfasdfasdf");
//        createOrderRequest.setDistrict("asdfasdfasdfasd");
//        CreateOrderResponse execute = pnApi.execute(createOrderRequest);
//        System.out.println(JSONObject.toJSONString(execute));


        QueryStockAndPriceRequest queryStockAndPriceRequest = new QueryStockAndPriceRequest();
        List<Integer> ids = new ArrayList();
        ids.add(2144855);
        queryStockAndPriceRequest.setIds(ids);
        QueryStockAndPriceResponse response = pnApi.execute(queryStockAndPriceRequest);
        System.out.println(JSONObject.toJSONString(response));


//
//        PayOrderRequest payOrderRequest = new PayOrderRequest();
//        payOrderRequest.setOrderId("eeeeeeee");
//        payOrderRequest.setAmount(new BigDecimal(10));
//        PayOrderResponse payOrderResponse = pnApi.execute(payOrderRequest);
//        System.out.println(JSONObject.toJSONString(payOrderResponse));


//        QueryActivityDetailRequest queryActivityDetailRequest = new QueryActivityDetailRequest();
//        queryActivityDetailRequest.setActivityId(87159);
//        QueryActivityDetailResponse execute = pnApi.execute(queryActivityDetailRequest);
//        System.out.println(JSONObject.toJSONString(execute));



    }
}
