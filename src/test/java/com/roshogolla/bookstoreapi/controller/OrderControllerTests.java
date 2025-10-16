package com.roshogolla.bookstoreapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roshogolla.bookstoreapi.dto.order.OrderCreateDTO;
import com.roshogolla.bookstoreapi.dto.order.OrderUpdateDTO;
import com.roshogolla.bookstoreapi.entity.Order;
import com.roshogolla.bookstoreapi.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderService orderService;

    @Test
    public void testCreateOrder() throws Exception {
        OrderCreateDTO orderCreateDTO = new OrderCreateDTO();
        orderCreateDTO.setDate(LocalDate.now());
        orderCreateDTO.setTotalAmount(2150F);
        orderCreateDTO.setBookIDs(List.of());

        Order order = Order.builder().id(1L).date(LocalDate.now()).totalAmount(2150F).build();

        when(orderService.addOrder(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderCreateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.totalAmount").value(2150F));
    }

    @Test
    public void testGetOrderById() throws Exception {
        Order order = Order.builder().id(1L).date(LocalDate.now()).totalAmount(2150F).build();

        when(orderService.getOrderById(1L)).thenReturn(Optional.of(order));

        mockMvc.perform(get("/order/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.totalAmount").value(2150F));
    }

    @Test
    public void testGetOrderByDate() throws Exception {
        Order order = Order.builder().id(1L).date(LocalDate.now()).totalAmount(2150F).build();

        when(orderService.searchOrderByDate(LocalDate.now())).thenReturn(List.of(order));

        mockMvc.perform(get("/order/search/by-date")
                        .param("date", LocalDate.now().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$[0].totalAmount").value(2150F));
    }

    @Test
    public void testGetAllOrder() throws Exception {
        Order order = Order.builder().id(1L).date(LocalDate.now()).totalAmount(2150F).build();

        when(orderService.getAllOrders()).thenReturn(List.of(order));

        mockMvc.perform(get("/order")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$[0].totalAmount").value(2150F));
    }

    @Test
    public void testUpdateOrder() throws Exception {
        Order order = Order.builder().id(1L).date(LocalDate.now()).totalAmount(2150F).build();

        OrderUpdateDTO orderUpdateDTO = new OrderUpdateDTO();
        orderUpdateDTO.setId(1L);
        orderUpdateDTO.setDate(LocalDate.now());
        orderUpdateDTO.setTotalAmount(1550F);
        orderUpdateDTO.setBookIDs(List.of());

        Order updatedOrder = Order.builder().id(1L).date(LocalDate.now()).totalAmount(1550F).build();

        when(orderService.getOrderById(1L)).thenReturn(Optional.of(order));
        when(orderService.updateOrder(eq(1L), any(Order.class))).thenReturn(updatedOrder);

        mockMvc.perform(put("/order/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.totalAmount").value(1550F));
    }

    @Test
    public void testDeleteOrder() throws Exception {
        doNothing().when(orderService).deleteOrder(1L);

        mockMvc.perform(delete("/order/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Order deleted successfully"));

        verify(orderService, times(1)).deleteOrder(1L);
    }
}



















