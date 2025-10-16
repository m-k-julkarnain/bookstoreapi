package com.roshogolla.bookstoreapi.repository;

import com.roshogolla.bookstoreapi.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class OrderRepositoryTests {
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void testFindOrderById() {
        Order order = Order.builder().date(LocalDate.now()).totalAmount(2550F).build();
        orderRepository.save(order);

        Optional<Order> foundOrder = orderRepository.findById(order.getId());

        assertThat(foundOrder).isNotEmpty();
        assertThat(foundOrder.get().getDate()).isEqualTo(LocalDate.now());
        assertThat(foundOrder.get().getTotalAmount()).isEqualTo(2550F);
    }
}
