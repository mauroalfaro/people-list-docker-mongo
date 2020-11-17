import com.alfarosoft.peoplelist.PeopleListApp;
import com.alfarosoft.peoplelist.model.Customer;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.alfarosoft.peoplelist.service.CustomerService;
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
public class CustomerControllerTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void whenPostingACustomerShouldReturnAccepted() throws Exception{
        File file = ResourceUtils.getFile("classpath:customerExample.json");
        String body = FileUtils.readFileToString(file);

        Customer customer = new Customer();
        customer.setId("123");

        when(customerService.addCustomer(customer)).thenReturn(customer);

        MvcResult mvcResult = mvc.perform(
                MockMvcRequestBuilders.post("/services/customers/add").contentType(MediaType.APPLICATION_JSON).content(body)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isAccepted())
                .andReturn();

        assertEquals(HttpStatus.ACCEPTED.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void whenUpdatingACustomerShouldReturnOK() throws Exception{
        File file = ResourceUtils.getFile("classpath:customerExample.json");
        String body = FileUtils.readFileToString(file);

        Customer customer = new Customer();
        customer.setId("123");

        when(customerService.updateCustomer(customer.getId(), customer)).thenReturn(customer);

        MvcResult mvcResult = mvc.perform(
                MockMvcRequestBuilders.put("/services/customers/update/123").contentType(MediaType.APPLICATION_JSON).content(body)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void whenLookupCustomerShouldReturnOK() throws Exception{
        File file = ResourceUtils.getFile("classpath:customerExample.json");
        String body = FileUtils.readFileToString(file);

        Customer customer = new Customer();
        customer.setId("123");

        when(customerService.getCustomer(customer.getId())).thenReturn(customer);

        MvcResult mvcResult = mvc.perform(
                MockMvcRequestBuilders.get("/services/customers/123").contentType(MediaType.APPLICATION_JSON).content(body)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void whenSearchingCustomersShouldReturnOK() throws Exception{
        File file = ResourceUtils.getFile("classpath:customerSearchExample.json");
        String body = FileUtils.readFileToString(file);

        Customer customer = new Customer();
        customer.setId("123");
        List<Customer> customersResult = new ArrayList<>();
        customersResult.add(customer);

        when(customerService.getCustomers()).thenReturn(customersResult);

        MvcResult mvcResult = mvc.perform(
                MockMvcRequestBuilders.get("/services/customers/").contentType(MediaType.APPLICATION_JSON).content(body)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }



}
