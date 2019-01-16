package com.macaulay.olajide.smartfridgemanager.controller;

import com.macaulay.olajide.smartfridgemanager.SmartFridgeManagerApplication;
import com.macaulay.olajide.smartfridgemanager.jpa.model.Item;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartFridgeManagerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractControllerTest {
    @LocalServerPort
    protected int PORT;

    protected static long DAIRY_TYPE_ID = 1;
    protected static long FISH_TYPE_ID = 2;
    protected static long MEAT_TYPE_ID = 3;
    protected static long SODA_TYPE_ID = 4;
    protected static long VEGETABLE_TYPE_ID = 5;


    private static TestRestTemplate restTemplate = new TestRestTemplate();

    private static HttpHeaders headers = new HttpHeaders();

    public ResponseEntity addItem(Item item) {
        return restTemplate.exchange(
                createURL("/event/v1/item-added"),
                HttpMethod.POST, new HttpEntity<>(item, headers), String.class);
    }

    public ResponseEntity removeItem(String uuid) {
        return restTemplate.exchange(
                createURL("/event/v1/item-removed/" + uuid),
                HttpMethod.DELETE, new HttpEntity(null, headers), String.class);
    }

    public ResponseEntity<Object[]> getItems(Double fillFactor){
        return restTemplate.exchange(
                createURL("/query/v1/items?fillFactor="+ fillFactor),
                HttpMethod.GET, new HttpEntity(null, headers), Object[].class);
    }

    public ResponseEntity<Double> getTypeFillFactor(Long id){
        return restTemplate.exchange(
                createURL("/query/v1/types/"+id+"/fill-factor"),
                HttpMethod.GET, new HttpEntity(null, headers), Double.class);
    }

    public void forgetType(Long id){
        restTemplate.exchange(
                createURL("/query/v1/types/"+id+"/forget"),
                HttpMethod.PUT, new HttpEntity(null, headers), String.class);
    }

    private String createURL(String uri) {
        return "http://localhost:" + PORT + uri;
    }
}
