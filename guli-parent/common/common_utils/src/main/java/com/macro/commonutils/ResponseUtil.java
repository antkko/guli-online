package com.macro.commonutils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author macro
 * @description
 * @date 2024/1/18 22:47
 * @github https://github.com/bugstackss
 */
public class ResponseUtil {

    public static void out(final HttpServletResponse response, final R r) {
        final ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            mapper.writeValue(response.getWriter(), r);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
