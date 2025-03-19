//package takeoff.logistics_service.msa.product.stock.domain.repository;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//import java.util.UUID;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.atomic.AtomicReference;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.support.TransactionTemplate;
//import takeoff.logistics_service.msa.product.product.application.service.ProductService;
//import takeoff.logistics_service.msa.product.product.infrastructure.client.FeignStockClient;
//import takeoff.logistics_service.msa.product.stock.domain.command.CreateStock;
//import takeoff.logistics_service.msa.product.stock.domain.command.CreateStockId;
//import takeoff.logistics_service.msa.product.stock.domain.entity.Stock;
//import takeoff.logistics_service.msa.product.stock.domain.entity.StockId;
//import takeoff.logistics_service.msa.product.stock.domain.repository.StockRepositoryH2TimeOutTest.TestConfig;
//
//@DataJpaTest
//@Import(TestConfig.class)  // 필요한 설정을 추가
//@ActiveProfiles("test")
//class StockRepositoryH2TimeOutTest {
//
//	@Autowired
//	private StockRepository stockRepository;
//
//	@Autowired
//	private TestEntityManager entityManager;
//
//	@Autowired
//	private PlatformTransactionManager transactionManager;
//
//	private UUID productId1;
//	private UUID hubId1;
//
//	@BeforeEach
//	void setUp() {
//		productId1 = UUID.randomUUID();
//		hubId1 = UUID.randomUUID();
//		StockId stockId = StockId.create(new CreateStockId(productId1, hubId1));
//		Stock stock = Stock.create(new CreateStock(stockId, 100));
//
//		entityManager.persist(stock);
//		entityManager.flush();
//	}
//
//	@Test
//	void testFindByIdWithLockWithTwoThreads() throws Exception {
//		StockId stockId = StockId.create(new CreateStockId(productId1, hubId1));
//		CountDownLatch latch1 = new CountDownLatch(1);
//		AtomicReference<Exception> exceptionHolder = new AtomicReference<>();
//
//		Thread thread1 = new Thread(() -> {
//			try {
//				TransactionTemplate tx = new TransactionTemplate(transactionManager);
//				tx.execute(status -> {
//					stockRepository.findByIdWithLock(stockId)
//						.orElseThrow(() -> new RuntimeException("Stock not found in thread1"));
//					latch1.countDown();
//					try {
//						Thread.sleep(3000);
//					} catch (InterruptedException e) {
//						Thread.currentThread().interrupt();
//					}
//					return null;
//				});
//			} catch (Exception e) {
//				exceptionHolder.set(e);
//			}
//		});
//
//		Thread thread2 = new Thread(() -> {
//			try {
//				latch1.await();
//				TransactionTemplate tx = new TransactionTemplate(transactionManager);
//				tx.execute(status -> {
//					try {
//						Stock stock = stockRepository.findByIdWithLock(stockId)
//							.orElseThrow(() -> new RuntimeException("Stock not found in thread2"));
//					} catch (Exception e) {
//						exceptionHolder.set(e);
//					}
//					return null;
//				});
//			} catch (Exception e) {
//				exceptionHolder.set(e);
//			}
//		});
//
//		thread1.start();
//		thread2.start();
//		thread1.join();
//		thread2.join();
//
//		Exception exception = exceptionHolder.get();
//		assertThat(exception).isNotNull();
//
//		boolean isLockTimeoutException = false;
//		Throwable cause = exception;
//		while (cause != null) {
//			if (cause.getMessage() != null &&
//				(cause.getMessage().contains("timeout") ||
//					cause.getMessage().contains("lock"))) {
//				isLockTimeoutException = true;
//				break;
//			}
//			cause = cause.getCause();
//		}
//
//		assertThat(isLockTimeoutException).isTrue();
//	}
//
//	@TestConfiguration
//	static class TestConfig {
//
//		@Bean
//		public FeignStockClient feignStockClient() {
//			return Mockito.mock(FeignStockClient.class);
//		}
//
//		@Bean
//		public ProductService productService() {
//			return Mockito.mock(ProductService.class);
//		}
//	}
//}