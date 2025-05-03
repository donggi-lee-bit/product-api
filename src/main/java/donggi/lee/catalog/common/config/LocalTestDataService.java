package donggi.lee.catalog.common.config;

import donggi.lee.catalog.product.application.OptionValueService;
import donggi.lee.catalog.product.application.ProductOptionService;
import donggi.lee.catalog.product.application.ProductService;
import donggi.lee.catalog.product.domain.OptionType;
import donggi.lee.catalog.product.domain.Product;
import donggi.lee.catalog.user.domain.User;
import donggi.lee.catalog.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("local")
@RequiredArgsConstructor
class LocalTestDataService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProductService productService;
    private final ProductOptionService productOptionService;
    private final OptionValueService optionValueService;

    /**
     * 로컬 환경에서 테스트용 데이터 삽입
     */
    public void init() {
        createAdminUser();
        seedSampleProducts();
    }

    private void createAdminUser() {
        final var email = "admin@donggi.lee";
        final var rawPassword = "admin1234";

        if (!userRepository.existsByEmail(email)) {
            final var admin = new User(email, passwordEncoder.encode(rawPassword));
            userRepository.save(admin);
        }
    }

    private void seedSampleProducts() {
        List<SampleProduct> samples = List.of(
            new SampleProduct("티셔츠", "편안한 티셔츠", 20_000L, 2_500L),
            new SampleProduct("청바지", "슬림핏 청바지", 45_000L, 3_000L),
            new SampleProduct("자켓", "캐주얼 자켓", 75_000L, 5_000L),
            new SampleProduct("스웨터", "따뜻한 스웨터", 50_000L, 2_500L),
            new SampleProduct("코트", "겨울 코트", 120_000L, 7_000L)
        );

        samples.forEach(this::createProductWithOptions);
    }

    private void createProductWithOptions(SampleProduct sample) {
        // 상품 생성
        final Product product = productService.create(
            sample.name(),
            sample.description(),
            sample.price(),
            sample.shippingFee()
        );

        // 사이즈 옵션
        var sizeOption = productOptionService.create(
            product.getId(),
            "사이즈",
            OptionType.SELECT,
            0L
        );
        sample.sizes().forEach(size -> optionValueService.create(sizeOption.getId(), size));

        // 색상 옵션
        var colorOption = productOptionService.create(
            product.getId(),
            "색상",
            OptionType.SELECT,
            0L
        );
        sample.colors().forEach(color -> optionValueService.create(colorOption.getId(), color));
    }

    /**
     * 로컬 테스트용 상품 정보
     */
    private record SampleProduct(
        String name,
        String description,
        long price,
        long shippingFee
    ) {
        List<String> sizes() {
            return List.of("S", "M", "L");
        }

        List<String> colors() {
            return List.of("Red", "Blue", "Black");
        }
    }
}
