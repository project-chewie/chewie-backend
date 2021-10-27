package com.chewie.api;

import com.chewie.AbstractTest;
import com.chewie.domain.Booking;
import com.chewie.domain.User;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersControllerTests extends AbstractTest {

    @BeforeEach
    public void init(){
        super.setUp();
    }

    @Test
    @DisplayName("find all users")
    public void findAllUsers()  {
        try {
            String uri = "/users";
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

            int status = mvcResult.getResponse().getStatus();
            assertEquals(200, status);
            String content = mvcResult.getResponse().getContentAsString();
            List<User> users = super.mapFromJsonToList(content, User.class);
            assertNotNull(users);
            assertTrue(1== users.size());
            User user=users.get(0);
            assertNotNull(user);
            assertTrue("Admin".equals(user.getName()));
            assertTrue("ChangeMeUncrypted".equals(user.getPassword()));

        } catch (Exception e) {
            fail(new StringBuilder("Broken mock caller!!!").append(e.getMessage()).toString());
        }
    }

    @Test
    @DisplayName("find default admin user")
    public void findUserById()  {
        try {
            String uri = "/user/{id}";
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri,"1")
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

            int status = mvcResult.getResponse().getStatus();
            assertEquals(200, status);
            String content = mvcResult.getResponse().getContentAsString();
            User user = super.mapFromJson(content, User.class);
            assertNotNull(user);
            assertTrue("Admin".equals(user.getName()));
            assertTrue("ChangeMeUncrypted".equals(user.getPassword()));
        } catch (Exception e) {
            fail(new StringBuilder("Broken mock caller!!!").append(e.getMessage()).toString());
        }
    }

    @Test
    @DisplayName("search not existent user")
    public void explodeWhenNoUser()  {
        try {

            String uri = "/users/{id}";
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri,"1000")
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

            int status = mvcResult.getResponse().getStatus();
            assertEquals(404, status);

        } catch (Exception e) {
            fail(new StringBuilder("Broken mock caller!!!").append(e.getMessage()).toString());
        }
    }
}