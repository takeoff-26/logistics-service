package takeoff.logistics_service.msa.order.model.entity;

import java.util.UUID;

public record ModifyQuantityCommand(
    UUID productId,
    int quantity
) {

  public static ModifyQuantityCommand from(UUID productId, int quantity) {
    return new ModifyQuantityCommand(productId, quantity);
  }
}
