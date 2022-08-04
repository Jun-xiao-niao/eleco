package randomnick.eleco.component;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class KeyUtil {
    //生成唯一的主键 格式:时间+随机数

    public static synchronized String getUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900) + 100;
        return  System.currentTimeMillis() + String.valueOf(number);
    }
}

