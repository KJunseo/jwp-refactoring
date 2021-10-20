package kitchenpos.application;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import kitchenpos.dao.ProductDao;
import kitchenpos.domain.Product;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@DisplayName("ProductService 단위 테스트")
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("상품을 등록할 수 있다.")
    void create() {
        // given
        Product 강정치킨 = new Product("강정치킨", 17000);
        Product expected = new Product(1L, "강정치킨", 17000);
        given(productDao.save(강정치킨)).willReturn(expected);

        // when
        Product actual = productService.create(강정치킨);

        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("상품 가격은 null이 아니어야한다.")
    void createWrongPriceNull() {
        // given
        Product 강정치킨 = new Product("강정치킨");

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> productService.create(강정치킨));
        assertEquals("상품의 가격은 비어있을 수 없고 0 이상이어야 합니다.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {-100, -1})
    @DisplayName("상품 가격이 음수일 수 없다.")
    void createWrongPriceUnderZero(int price) {
        // given
        Product 강정치킨 = new Product("강정치킨", price);

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> productService.create(강정치킨));
        assertEquals("상품의 가격은 비어있을 수 없고 0 이상이어야 합니다.", exception.getMessage());
    }

    @Test
    @DisplayName("전체 상품을 조회할 수 있다.")
    void list() {
        // given
        Product 강정치킨 = new Product(1L, "강정치킨", 17000);
        Product 구운치킨 = new Product(2L, "구운치킨", 14000);
        List<Product> expected = Arrays.asList(강정치킨, 구운치킨);
        given(productDao.findAll()).willReturn(expected);

        // when
        List<Product> actual = productService.list();

        // then
        assertEquals(expected, actual);
    }
}
