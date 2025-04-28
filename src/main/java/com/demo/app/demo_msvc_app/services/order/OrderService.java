package com.demo.app.demo_msvc_app.services.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.demo.app.demo_msvc_app.cart.CartServiceIMPL;
import com.demo.app.demo_msvc_app.dto.OrderDto;
import com.demo.app.demo_msvc_app.entities.Cart;
import com.demo.app.demo_msvc_app.entities.Order;
import com.demo.app.demo_msvc_app.entities.OrderItem;
import com.demo.app.demo_msvc_app.entities.Product;
import com.demo.app.demo_msvc_app.enums.OrderStatus;
import com.demo.app.demo_msvc_app.exceptions.ElementsNotFoundException;
import com.demo.app.demo_msvc_app.repositories.OrderRepository;
import com.demo.app.demo_msvc_app.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceIMPL {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartServiceIMPL cartService;
    private final ModelMapper modelMapper;
    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List <OrderItem> orderItemsList = createOrderItems(order, cart);

        order.setOrderItems(new HashSet<>(orderItemsList));
        order.setTotalOrderAmount(calculateTotalAmount(orderItemsList));
        Order savedOrder = orderRepository.save(order);
        //una vez se haga la order y se guarde el servicio va a limpiar el carro automaticamente
        cartService.clearCart(cart.getId());

        return savedOrder;
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ElementsNotFoundException("Order not found"));
        return convertToDto(order);
    }


    
    private Order createOrder(Cart cart){
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }


    
    
    private List <OrderItem> createOrderItems(Order order, Cart cart){
        return cart.getCartItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            //con esto vamos a ver cuanto ordena exactamente el usuario al hacer su pedido
            product.setInventory(product.getInventory() - cartItem.getQuantity()); 
            productRepository.save(product);
            return new OrderItem(cartItem.getQuantity(), cartItem.getPricePerUnit(), order, product  );
        }).toList();
    }
    
    
    
    
    private BigDecimal calculateTotalAmount(List <OrderItem> orderItems){
        return orderItems.stream().map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    @Override
    public List <OrderDto> getUserOrders(Long userId){
        return (List<OrderDto>) orderRepository.findById(userId)
        .orElseThrow(() -> new ElementsNotFoundException("Order not found"));
    }

    public OrderDto convertToDto(Order order){
        return modelMapper.map(order, OrderDto.class);
    }

}
