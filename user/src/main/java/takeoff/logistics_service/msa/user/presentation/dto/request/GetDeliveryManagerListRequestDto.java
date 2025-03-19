package takeoff.logistics_service.msa.user.presentation.dto.request;

import lombok.Builder;
import org.springframework.data.domain.Pageable;
import takeoff.logistics_service.msa.user.domain.service.DeliveryManagerSearchCondition;
import takeoff.logistics_service.msa.user.domain.vo.DeliveryManagerType;

@Builder
public record GetDeliveryManagerListRequestDto(
        String hubId,
        DeliveryManagerType deliveryManagerType,
        Integer page,
        Integer size
) {
    public DeliveryManagerSearchCondition toCondition() {
        return new DeliveryManagerSearchCondition(hubId, deliveryManagerType);
    }

    public Pageable toPageable() {
        return Pageable.ofSize(size != null ? size : 10).withPage(page != null ? page : 0);
    }
}
