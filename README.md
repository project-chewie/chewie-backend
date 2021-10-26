# chewie-backend
Backend for Chewie

Chewie is a didactical project to get in touch with technologies that won't be possible to learn in production without pain. 
It's about learning some new technologies but also in being "infected" from some good habits, like TDD to name one.

#What

The design of the app is based on fully segregated layers that are agnostic regarding their consumers. 
That means each Class on each Layer is only responsible for its own contract and ignores how its output will be processed.
Each Layer is also responsible for a single concern:

- Entity Mapping Layer (package com.chewie.domain ) maps the domain entities to a persistent Data Layer (RDBMS)
- Data Access Layer (package com.chewie.repositories ) allows querying the persistent layer and retrieve the domain entities
- Service Layer  (package com.chewie.services) enforces semantics and rules to consume and combine the domain entities
- REST API Layer (package com.chewie.controllers) allows the usage of the service layer through a REST API

The layering may appear overengineered and in fact it will be so mostly. That's intended for pure didactical reasons. 
The project is small enough to allow avoiding having a clear separation between Data Access Layer and Service Layer, and 
the REST API will very likely mostly retrieve and insert data in the RDBMS. Nevertheless, we may want to make the services
later rather more complicated as we thought at the beginning. 

#How

The only way to use the services exposed by the application is, as mentioned above, the REST API. Combine this information 
with the wish of learning TDD, and the facilities provided by that beautiful piece of technology that SpringBoot is, and you'll get 
to the starting point.

## Writing the tests first

The entry point of the development should be the package com.chewie.api in the src/test/java directory. This package contains
some integration tests that are run by Spring as if a real consumer was putting up the API on a running server and consuming its
endpoints. The tests contained there are doing the same a human does by running REST calls through the http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
URL. 

The very first step would be than **write a test in this package** describing the endpoint you are planning to expose.
Of course, the tests are meant to fail before providing the implementation: there is no controller to handle the endpoint.

The implementation should go through all layers needed to make the test pass. It's always the case of writing a controller exposing
the endpoints first, and going down to the service layer first. If some service providing the needed information is already available, 
this service will be reused. If not, the new implementation should also be developed in a test driven way. That means the wished service
contract must be written in the code and the easiest possible implementation of it has to be provided, so that the REST API test passes.
The method followed should be the same while going down in the several software layers as needed.

## Example: List all Users

Starting point is a test in the com.chewie.api.UsersControllerTest:

```
@Test
@DisplayName("find all users")
public void findAllUsers()  {
try {
String uri = "/users";
MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

            int status = mvcResult.getResponse().getStatus();
            assertEquals(200, status);
            String content = mvcResult.getResponse().getContentAsString();
            List<User> users = super.mapFromJsonToList(content, User.class);
            assertNotNull(users);

        } catch (Exception e) {
            fail(new StringBuilder("Broken mock caller!!!").append(e.getMessage()).toString());
        }
    }
```

Here we are asserting that:

- An Endpoint is exposed with URL */users*
- Calling the URL with Http Method GET a list of Users as JSON Object should be returned
- Such list is not empty

A service to retrieve the values from the persistence layer needs to be written. But wait, first... no Entity of type User 
even exists in the Domain. Ok, the first todos are then 

- mapping the user object to a database
- retrieving all of them in one go

As we go we benefit of JPA and of the ways one can do such things with Spring (folks now really... have a glance on the code for that).

But crap... how to integrate the data layer? We need a database for that! Right. Ok, let's grab MySQL and ... **NOOOOOOO!!!**
We do not want to have the pain in the ass of integrating a mammoth database from the very beginning, therefore we do things in memory.
There are many SQL Databases that can run in memory, without any installation. 
Maybe you are now wondering what happens with all those DDL things you may need to do when the database is running, if the database runs just in memory.
What happens with tables, indexes, keys and all those goodies one makes with a RDBMS once the database is not in memory anymore?

Right, they are gone. Forever. Basta, Kaput, Finito. Right, you have to create them again, and again and again to run your tests each time.
Well not you but

##Liquibase (or how to grow your datalayer incrementally)

Each time you need to change something in your database structure or in the mini dataset need to implement, you will use [Liquibase](https://www.liquibase.org/) (yes, exactly how they explain in the documentation there).
Liquibase makes sure to have everything you need on the database you are running on (of course, if you tell it exactly what are you aiming at).
That means it can completely do whatever you need to do for your tests, integrating the changes in the in memory db from scratch every single time you run the tests.
Spoiler: the effort you are doing at this stage can be of course reused with few or no effort when you'll pick a "real" database like MySQL, Postgre or Oracle.
For further information have a glance at the documentation of Liquibase and at the db.changelog-master.yaml. Then google if you wanna know more.
If you really, really need, than ask ;) (boys and girls I am spoonfeeding you more than I maybe should with all this information...)

