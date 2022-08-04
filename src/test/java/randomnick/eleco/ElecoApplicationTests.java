package randomnick.eleco;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static randomnick.eleco.component.KeyUtil.getUniqueKey;

@SpringBootTest
    class ElecoApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(getUniqueKey());
    }

}
