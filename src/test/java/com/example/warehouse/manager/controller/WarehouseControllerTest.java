package com.example.warehouse.manager.controller;

import com.example.warehouse.manager.service.WarehouseInventoryService;
import com.example.warehouse.manager.service.WarehouseProductService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(WarehouseController.class)
class WarehouseControllerTest {

    @MockBean
    WarehouseInventoryService inventoryService;
    @MockBean
    WarehouseProductService productService;
    @Autowired
    private MockMvc mvc;
    private String inventoryJsonString = "{\n" +
            "  \"inventory\": [\n" +
            "    {\n" +
            "      \"art_id\": \"1\",\n" +
            "      \"name\": \"leg\",\n" +
            "      \"stock\": \"12\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"art_id\": \"2\",\n" +
            "      \"name\": \"screw\",\n" +
            "      \"stock\": \"17\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"art_id\": \"3\",\n" +
            "      \"name\": \"seat\",\n" +
            "      \"stock\": \"2\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"art_id\": \"4\",\n" +
            "      \"name\": \"table top\",\n" +
            "      \"stock\": \"1\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    private String productJsonString = "{\n" +
            "  \"products\": [\n" +
            "    {\n" +
            "      \"name\": \"Dining Chair\",\n" +
            "      \"contain_articles\": [\n" +
            "        {\n" +
            "          \"art_id\": \"1\",\n" +
            "          \"amount_of\": \"4\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"art_id\": \"2\",\n" +
            "          \"amount_of\": \"8\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"art_id\": \"3\",\n" +
            "          \"amount_of\": \"1\"\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}\n";

    @Test
    void test_load_inventory() throws Exception {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "inventory.json",
                MediaType.APPLICATION_JSON_VALUE,
                inventoryJsonString.getBytes()
        );
        mvc.perform(multipart("/api/inventory").file(file))
                .andExpect(status().isOk());
    }

    @Test
    void test_find_all_inventory() throws Exception {
        given(inventoryService.getAllInventory()).willReturn(inventoryJsonString);

        mvc.perform(get("/api/inventory")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(inventoryService, VerificationModeFactory.times(1)).getAllInventory();
    }

    @Test
    void get_inventory_for_id() throws Exception {
        given(inventoryService.getInventoryById(anyLong())).willReturn(inventoryJsonString);

        mvc.perform(get("/api/inventory/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.inventory.[0].art_id").value("1"));

        verify(inventoryService, VerificationModeFactory.times(1)).getInventoryById(anyLong());
    }

    @Test
    void loadProduct() throws Exception {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "inventory.json",
                MediaType.APPLICATION_JSON_VALUE,
                productJsonString.getBytes()
        );
        mvc.perform(multipart("/api/product").file(file))
                .andExpect(status().isOk());
    }

    @Test
    void test_find_all_products() throws Exception {
        given(productService.getAllProducts()).willReturn(productJsonString);

        mvc.perform(get("/api/product")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.products").hasJsonPath());

        verify(productService, VerificationModeFactory.times(1)).getAllProducts();
    }

    @Test
    void test_update_Products() throws Exception {
        given(productService.updateProducts(any())).willReturn(productJsonString);

        mvc.perform(MockMvcRequestBuilders
                .put("/api/product")
                .content(productJsonString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(productService, VerificationModeFactory.times(1)).updateProducts(any());
    }
}