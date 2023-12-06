package com.itzc.rear_end;

import com.itzc.rear_end.until.MD5Utils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MD5UtilsTest {

    @Test
     void test(){
        String code = MD5Utils.code("123");
        System.out.println(code);
    }
}
