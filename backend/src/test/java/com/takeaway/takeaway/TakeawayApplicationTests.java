package com.takeaway.takeaway;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TakeawayApplicationTests {

    @Test
    void contextLoads() {
        Assertions.assertThat(1 == 1).isSameAs(true);
    }

}
