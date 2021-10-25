package com.chewie.api;

import com.chewie.AbstractTest;
import com.chewie.domain.Booking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookingControllerTests extends AbstractTest {

    @BeforeEach
    public void init(){
        super.setUp();
    }

    @Test
    @DisplayName("book ingoing user")
    public void performInBooking()  {
        try {
            String uri = "/booking_in/{id}";
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri,"1")
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

            int status = mvcResult.getResponse().getStatus();
            assertEquals(200, status);
            String content = mvcResult.getResponse().getContentAsString();
            Booking booking = super.mapFromJson(content, Booking.class);
            assertNotNull(booking);
            assertTrue(booking.getType().getIncoming());
            assertTrue(booking.getBookedOn().before(new Date()));
        } catch (Exception e) {
            fail(new StringBuilder("Broken mock caller!!!").append(e.getMessage()).toString());
        }
    }
    @Test
    @DisplayName("book outgoing user")
    public void performOutBooking()  {
        String uri = "/booking_out/{id}";
        MvcResult mvcResult = null;
        try {
            mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri,"1")
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
            int status = mvcResult.getResponse().getStatus();
            assertEquals(200, status);
            String content = mvcResult.getResponse().getContentAsString();
            Booking booking = super.mapFromJson(content, Booking.class);
            assertNotNull(booking);
            assertFalse(booking.getType().getIncoming());
            assertTrue(booking.getBookedOn().before(new Date()));
        } catch (Exception e) {
            fail(new StringBuilder("Broken mock caller!!!").append(e.getMessage()).toString());
        }
    }
    @Test
    @DisplayName("booking not existent user")
    public void explodeWhenNoUser()  {
        try {

            String uri = "/booking_in/{id}";
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri,"1000")
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

            int status = mvcResult.getResponse().getStatus();
            assertEquals(404, status);

        } catch (Exception e) {
            fail(new StringBuilder("Broken mock caller!!!").append(e.getMessage()).toString());
        }
    }

    @Test
    @DisplayName("api http method mapping verification")
    public void apiContractIsNotViolated()  {
        String uriOut = "/booking_out/{id}";
        String uriIn = "/booking_in/{id}";
        MvcResult mvcResult = null;
        try {
            mvcResult = mvc.perform(MockMvcRequestBuilders.get(uriOut,"1")
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
            int statusGet = mvcResult.getResponse().getStatus();
            assertEquals(405, statusGet);
            mvcResult = mvc.perform(MockMvcRequestBuilders.put(uriOut,"1")
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
            int statusPut = mvcResult.getResponse().getStatus();
            assertEquals(405, statusPut);
            mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uriOut,"1")
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
            int statusDelete = mvcResult.getResponse().getStatus();
            assertEquals(405, statusDelete);
            mvcResult = mvc.perform(MockMvcRequestBuilders.get(uriIn,"1")
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
            int statusInGet = mvcResult.getResponse().getStatus();
            assertEquals(405, statusInGet);
            mvcResult = mvc.perform(MockMvcRequestBuilders.put(uriIn,"1")
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
            int statusInPut = mvcResult.getResponse().getStatus();
            assertEquals(405, statusInPut);
            mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uriIn,"1")
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
            int statusInDelete = mvcResult.getResponse().getStatus();
            assertEquals(405, statusInDelete);
        } catch (Exception e) {
            fail(new StringBuilder("Broken mock caller!!!").append(e.getMessage()).toString());
        }
    }
}
