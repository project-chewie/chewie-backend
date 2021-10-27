package com.chewie.api;

import com.chewie.AbstractTest;
import com.chewie.domain.Saldo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SaldoControllerTests extends AbstractTest {

    @BeforeEach
    public void init(){
        super.setUp();
    }

    @Test
    @DisplayName("find all saldo")
    public void findAllSaldo()  {
        try {
            String uri = "/saldos";
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

            int status = mvcResult.getResponse().getStatus();
            assertEquals(200, status);
            String content = mvcResult.getResponse().getContentAsString();
            List<Saldo> saldos = super.mapFromJsonToList(content, Saldo.class);
            assertNotNull(saldos);
            assertEquals(1, saldos.size());
            Saldo saldo=saldos.get(0);
            assertNotNull(saldo);
            assertEquals(1, (long) saldo.getId());
            assertNotNull(saldo.getValue());

        } catch (Exception e) {
            fail(new StringBuilder("Broken mock caller!!!").append(e.getMessage()).toString());
        }
    }

    @Test
    @DisplayName("find default saldo for admin ")
    public void findSaldoById()  {
        try {
            String uri = "/saldo/{id}";
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri,"1")
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

            int status = mvcResult.getResponse().getStatus();
            assertEquals(200, status);
            String content = mvcResult.getResponse().getContentAsString();
            Saldo saldo = super.mapFromJson(content, Saldo.class);
            assertNotNull(saldo);
            assertEquals(1, (long) saldo.getId());
            assertEquals(0, (long) saldo.getValue());
        } catch (Exception e) {
            fail(new StringBuilder("Broken mock caller!!!").append(e.getMessage()).toString());
        }
    }

    @Test
    @DisplayName("search not existent saldo")
    public void explodeWhenNoSaldo()  {
        try {

            String uri = "/saldo/{id}";
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri,"1000")
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

            int status = mvcResult.getResponse().getStatus();
            assertEquals(405, status);

        } catch (Exception e) {
            fail(new StringBuilder("Broken mock caller!!!").append(e.getMessage()).toString());
        }
    }
}