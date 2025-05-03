package donggi.lee.catalog.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * 로컬 프로파일에서만 실행되는 테스트용 데이터 초기화기.
 */
@Component
@Profile("local")
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final LocalTestDataService testDataService;

    @Override
    public void run(ApplicationArguments args) {
        testDataService.init();
    }
}
