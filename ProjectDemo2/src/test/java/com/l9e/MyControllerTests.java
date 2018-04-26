package com.l9e;

import com.l9e.transaction.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author meizs
 * @create 2018-04-13 15:11
 **/
/*@RunWith(SpringRunner.class)
@WebMvcTest(RefundTicketController.class)*/
public class MyControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountService userVehicleService;

   // @Test
    public void testExample() throws Exception {
        given(this.userVehicleService.addAccout(null)).willReturn(0);

        this.mvc.perform(get("/refund/refundTicket.do")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("Honda Civic"));
    }

}