package randomnick.eleco.component;

import org.apache.commons.lang.StringUtils;

import java.util.UUID;

/**
 * uuid生成工具
 */
public  class UuidTool {

    public static String getUUID(){
        return StringUtils.replace(UUID.randomUUID().toString(), "-", "");
    }

}
