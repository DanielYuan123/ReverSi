package view;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestTest {
    public static Class<?> GameFrame;
    
    @Order(1)
    @Test
    void test1() throws Exception {
        String[] fieldsExpected = {"private java.lang.String Player.name"};
        String[] methodsExpected = {
                "public java.lang.String Player.getName()",
                "public void Player.setName(java.lang.String)"
        };
        
    }
    
}