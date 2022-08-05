package randomnick.eleco;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("randomnick.eleco.mapper")
@SpringBootApplication
public class ElecoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElecoApplication.class, args);
    }

}
