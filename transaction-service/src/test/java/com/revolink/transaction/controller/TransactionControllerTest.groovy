package com.revolink.transaction.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.revolink.transaction.dto.TransactionRequest
import com.revolink.transaction.service.interf.TransactionService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc

import static org.mockito.Mockito.doNothing
import static org.mockito.Mockito.verify
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService transactionService;

    @Test
    void shouldAcceptTransactionPostRequestAndCallService() throws Exception {
        TransactionRequest request = new TransactionRequest(
                UUID.randomUUID(),
                UUID.randomUUID(),
                100.0,
                "EUR"
        );

        doNothing().when(transactionService).processTransaction(request);

        mockMvc.perform(post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(transactionService).processTransaction(request);
    }
}