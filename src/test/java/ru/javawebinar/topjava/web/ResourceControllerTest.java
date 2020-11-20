package ru.javawebinar.topjava.web;

import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Panfilov Dmitriy
 * 20.11.2020
 */
public class ResourceControllerTest extends AbstractControllerTest{
   @Test
    void testResources() throws Exception{
        perform(get("/resources/css/style.css"))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/css;charset=UTF-8"));

    }
}
