package takeoff.logistics_service.msa.order.presentation.external;


import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import takeoff.logistics_service.msa.order.application.service.OrderService;
import takeoff.logistics_service.msa.order.domain.entity.Order;
import takeoff.logistics_service.msa.order.domain.entity.OrderId;
import takeoff.logistics_service.msa.order.domain.entity.OrderItem;
import takeoff.logistics_service.msa.order.presentation.dto.request.PatchOrderItemRequest;
import takeoff.logistics_service.msa.order.presentation.dto.request.PatchOrderRequest;
import takeoff.logistics_service.msa.order.presentation.dto.request.PostOrderItemRequest;
import takeoff.logistics_service.msa.order.presentation.dto.request.PostOrderRequest;
import takeoff.logistics_service.msa.order.presentation.dto.response.PatchOrderResponse;
import takeoff.logistics_service.msa.order.presentation.dto.response.PostOrderResponse;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {
    "eureka.client.register-with-eureka=false",
    "eureka.client.fetch-registry=false"})
class OrderControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private OrderService orderService;

  private final String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
      + ".eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6M"
      + "TUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30";

  @Test
  @DisplayName("[Order][POST] 주문 생성 - 정상 호출")
  void 주문_정보를_입력하여_주문_생성() throws Exception {
    // given
    UUID product1Id = createRandomUUID("product1");
    UUID product2Id = createRandomUUID("product2");

    List<PostOrderItemRequest> orderItemsRequest = List.of(
        new PostOrderItemRequest(
            product1Id,
            1
        ),
        new PostOrderItemRequest(
            product2Id,
            2
        )
    );

    UUID supplier1 = createRandomUUID("supplier1");
    UUID company1 = createRandomUUID("company1");
    long customerId = 1L;
    String address = "대전 서구 둔산로 100";
    String requestNotes = "요청 사항 입니다.";

    PostOrderRequest request = new PostOrderRequest(
        supplier1,
        company1,
        customerId,
        orderItemsRequest,
        address,
        requestNotes
    );

    UUID order1 = createRandomUUID("order1");

    // 도메인 객체 모킹
    Order mockOrder = mock(Order.class);
    OrderItem mockOrderItem1 = mock(OrderItem.class);
    OrderItem mockOrderItem2 = mock(OrderItem.class);
    OrderId orderId = mock(OrderId.class);

    // 모의 객체 동작 설정
    when(orderId.getOrderId()).thenReturn(order1);
    when(mockOrder.getId()).thenReturn(orderId);
    when(mockOrder.getSupplierId()).thenReturn(supplier1);
    when(mockOrder.getCustomerId()).thenReturn(customerId);
    when(mockOrder.getAddress()).thenReturn(address);
    when(mockOrder.getRequestNotes()).thenReturn(requestNotes);

    when(mockOrderItem1.getProductId()).thenReturn(product1Id);
    when(mockOrderItem1.getQuantity()).thenReturn(1);
    when(mockOrderItem2.getProductId()).thenReturn(product2Id);
    when(mockOrderItem2.getQuantity()).thenReturn(2);

    List<OrderItem> orderItems = List.of(mockOrderItem1, mockOrderItem2);
    when(mockOrder.getOrderItems()).thenReturn(orderItems);

    // 서비스 응답 설정
    PostOrderResponse response = PostOrderResponse.from(mockOrder);
    given(orderService.saveOrder(any(PostOrderRequest.class))).willReturn(response);

    // when && then
    mvc.perform(post("/api/v1/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-User-Id", "1")
            .header("X-User-Role", "MASTER_ADMIN")
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.orderId").value(order1.toString()))
        .andExpect(jsonPath("$.supplierId").value(supplier1.toString()))
        .andExpect(jsonPath("$.customerId").value(customerId))
        .andExpect(jsonPath("$.address").value(address))
        .andExpect(jsonPath("$.requestNotes").value(requestNotes))
        .andExpect(jsonPath("$.orderItems", hasSize(2)))
        .andExpect(jsonPath("$.orderItems[0].productId").value(product1Id.toString()))
        .andExpect(jsonPath("$.orderItems[0].quantity").value(1))
        .andExpect(jsonPath("$.orderItems[1].productId").value(product2Id.toString()))
        .andExpect(jsonPath("$.orderItems[1].quantity").value(2))
        .andDo(
            document("주문 생성",
                preprocessRequest(Preprocessors.prettyPrint()),
                preprocessResponse(Preprocessors.prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("Order-External")
                    .summary("주문 생성")
                    .description("주문을 생성하는 외부 API 입니다.")
                    .build())
            )
        );
  }

  @Test
  @DisplayName("[Order][Patch] 주문 수정 - 정상 호출")
  void 관리자나_배송담당자는_주문_상태를_수정한다() throws Exception {
    // given
    UUID product1Id = createRandomUUID("product1");
    UUID product2Id = createRandomUUID("product2");

    List<PatchOrderItemRequest> orderItemsRequest = List.of(
        new PatchOrderItemRequest(product1Id, 3),
        new PatchOrderItemRequest(product2Id, 4)
    );

    PatchOrderRequest request = new PatchOrderRequest(orderItemsRequest);
    UUID order1 = createRandomUUID("order1");

    Order mockOrder = mock(Order.class);
    OrderItem mockOrderItem1 = mock(OrderItem.class);
    OrderItem mockOrderItem2 = mock(OrderItem.class);

    when(mockOrderItem1.getProductId()).thenReturn(product1Id);
    when(mockOrderItem1.getQuantity()).thenReturn(3);
    when(mockOrderItem2.getProductId()).thenReturn(product2Id);
    when(mockOrderItem2.getQuantity()).thenReturn(4);

    List<OrderItem> orderItems = List.of(mockOrderItem1, mockOrderItem2);
    when(mockOrder.getOrderItems()).thenReturn(orderItems);

    PatchOrderResponse response = PatchOrderResponse.from(mockOrder);
    given(orderService.updateOrder(any(PatchOrderRequest.class), any(UUID.class)))
        .willReturn(response);

    // when && then

    mvc.perform(patch("/api/v1/orders/{orderId}", order1)
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-User-Id", "1")
            .header("X-User-Role", "MASTER_ADMIN")
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.orderItems[0].quantity").value(3))
        .andExpect(jsonPath("$.orderItems[1].quantity").value(4))
        .andDo(
            document("주문 수정",
                preprocessRequest(Preprocessors.prettyPrint()),
                preprocessResponse(Preprocessors.prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("Order-External")
                    .summary("주문 수정")
                    .description("주문을 수정하는 외부 API 입니다.")
                    .build())
            ));
  }

  @Test
  @DisplayName("[Order][Delete] 주문 삭제 = 정상 호출")
  void 관리지가_주문을_삭제할_수_있다() {
    // given

    // when

    // then
  }

  @Test
  @DisplayName("[Order][GET] 주문 조회 - 정상 호출")
  void 주문을_검색할_수_있다() {
    // given

    // when

    // then
  }

  private UUID createRandomUUID(String seed) {
    return UUID.nameUUIDFromBytes(seed.getBytes());
  }
}
