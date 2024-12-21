package io.pismo.transaction;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
class TransactionApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void applicationMain() {

        TransactionApplication.main(new String[]{});
    }

}
