import com.alfarosoft.peoplelist.PeopleListApp;
import com.alfarosoft.peoplelist.model.Store;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.alfarosoft.peoplelist.service.StoreService;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = PeopleListApp.class)
@AutoConfigureMockMvc
public class StoreControllerTest {
    @MockBean
    private StoreService storeService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void whenPostingAStoreShouldReturnAccepted() throws Exception{
        File file = ResourceUtils.getFile("classpath:storeExample.json");
        String body = FileUtils.readFileToString(file);

        Store store = new Store();
        store.setId("123");

        when(storeService.addStore(store)).thenReturn(store);

        MvcResult mvcResult = mvc.perform(
                MockMvcRequestBuilders.post("/services/stores/add").contentType(MediaType.APPLICATION_JSON).content(body)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isAccepted())
                .andReturn();

        assertEquals(HttpStatus.ACCEPTED.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void whenUpdatingAStoreShouldReturnOK() throws Exception{
        File file = ResourceUtils.getFile("classpath:storeExample.json");
        String body = FileUtils.readFileToString(file);

        Store store = new Store();
        store.setId("123");

        when(storeService.updateStore(store.getId(), store)).thenReturn(store);

        MvcResult mvcResult = mvc.perform(
                MockMvcRequestBuilders.put("/services/stores/update/123").contentType(MediaType.APPLICATION_JSON).content(body)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void whenLookupStoreShouldReturnOK() throws Exception{
        File file = ResourceUtils.getFile("classpath:storeExample.json");
        String body = FileUtils.readFileToString(file);

        Store store = new Store();
        store.setId("123");

        when(storeService.getStore(store.getId())).thenReturn(store);

        MvcResult mvcResult = mvc.perform(
                MockMvcRequestBuilders.get("/services/stores/123").contentType(MediaType.APPLICATION_JSON).content(body)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void whenSearchingStoresShouldReturnOK() throws Exception{
        File file = ResourceUtils.getFile("classpath:employeeSearchExample.json");
        String body = FileUtils.readFileToString(file);

        Store store = new Store();
        store.setId("123");

        List<Store> storesResult = new ArrayList<>();
        storesResult.add(store);

        when(storeService.getStores()).thenReturn(storesResult);

        MvcResult mvcResult = mvc.perform(
                MockMvcRequestBuilders.get("/services/stores/").contentType(MediaType.APPLICATION_JSON).content(body)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }

}
