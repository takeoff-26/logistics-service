package takeoff.logisticsservice.msa.delivery.delivery.domain.entity;

import static takeoff.logisticsservice.msa.delivery.delivery.domain.entity.DeliveryStatus.Status.CANCEL;
import static takeoff.logisticsservice.msa.delivery.delivery.domain.entity.DeliveryStatus.Status.COMPLETED;
import static takeoff.logisticsservice.msa.delivery.delivery.domain.entity.DeliveryStatus.Status.DELIVERING;
import static takeoff.logisticsservice.msa.delivery.delivery.domain.entity.DeliveryStatus.Status.ORDERED;
import static takeoff.logisticsservice.msa.delivery.delivery.domain.entity.DeliveryStatus.Status.PENDING;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.util.Objects;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import takeoff.logistics_service.msa.common.domain.BaseEntity;

@Entity
@Table(name = "p_delivery")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Delivery extends BaseEntity {

  @EmbeddedId
  private DeliveryId id;

  @Column(name = "order_id", nullable = false)
  private UUID orderId;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private DeliveryStatus status;

  @Column(name = "customerId", nullable = false)
  private Long customerId;

  @Column(name = "deliveryManager_Id", nullable = false)
  private Long deliveryManagerId;

  @Column(name = "from_hub_id", nullable = false)
  private UUID fromHubId;

  @Column(name = "to_hub_id", nullable = false)
  private UUID toHubId;

  @Builder
  public Delivery(UUID id, UUID orderId, Long customerId,
      DeliveryStatus status, UUID fromHubId,
      UUID toHubId) {
    this.id = DeliveryId.from(id);
    this.orderId = orderId;
    this.customerId = customerId;
    this.status = status;
    this.fromHubId = fromHubId;
    this.toHubId = toHubId;
  }

  public Delivery(UUID id, UUID orderId, Long customerId,
       Long deliveryManagerId,
       UUID fromHubId,
      UUID toHubId) {
    this.id = DeliveryId.from(id);
    this.orderId = orderId;
    this.customerId = customerId;
    this.deliveryManagerId = deliveryManagerId;
    this.status = DeliveryStatus.ORDERED;
    this.fromHubId = fromHubId;
    this.toHubId = toHubId;
  }

  public void modifyStatus(String status) {
    switch (status) {
      case ORDERED -> this.status = DeliveryStatus.ORDERED;
      case DELIVERING -> this.status = DeliveryStatus.DELIVERING;
      case COMPLETED -> this.status = DeliveryStatus.COMPLETED;
      case PENDING -> this.status = DeliveryStatus.PENDING;
      case CANCEL -> this.status = DeliveryStatus.CANCEL;
      default -> throw new IllegalArgumentException("Invalid status: " + status);
    }
  }
  public void modifyDeliveryCompanyManager(Long deliveryManagerId) {
    this.deliveryManagerId = deliveryManagerId;
  }

  public void modifyDeliveryToHubAndFromHub(UUID toHubId, UUID fromHubId) {
    this.toHubId = toHubId;
    this.fromHubId = fromHubId;
  }

  public UUID getIdLiteral() {
    return id.getId();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Delivery delivery)) {
      return false;
    }
    return Objects.equals(id, delivery.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
