package com.demo.app.demo_msvc_app.services.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.demo.app.demo_msvc_app.cart.CartServiceIMPL;
import com.demo.app.demo_msvc_app.dto.OrderDto;
import com.demo.app.demo_msvc_app.entities.Cart;
import com.demo.app.demo_msvc_app.entities.Order;
import com.demo.app.demo_msvc_app.entities.OrderItem;
import com.demo.app.demo_msvc_app.entities.Product;
import com.demo.app.demo_msvc_app.entities.User;
import com.demo.app.demo_msvc_app.enums.OrderStatus;
import com.demo.app.demo_msvc_app.exceptions.ElementsNotFoundException;
import com.demo.app.demo_msvc_app.repositories.OrderRepository;
import com.demo.app.demo_msvc_app.repositories.ProductRepository;
import com.demo.app.demo_msvc_app.repositories.UserRepository;
import com.demo.app.demo_msvc_app.request.OrderRequestDTO;
import com.demo.app.demo_msvc_app.response.OrderResponseDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceIMPL {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartServiceIMPL cartService;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    @Override
    public Order placeOrder(Long userId) {
        //buscamos el carro y nos aseguramos de que no esté vacio
        Cart cart = cartService.getCartByUserId(userId);
        if (cart.getCartItems().isEmpty()) {
            throw new IllegalStateException( "cannot create an order with an empty cart.");
        }

        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());

        //hacemos la transformacion de cartItems a OrderItems

        Set <OrderItem> orderItems = (Set<OrderItem>) cart.getCartItems().stream()
        .map(cartItem ->{
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPricePerUnit());
            orderItem.setOrder(order);
            return orderItem;
        })
        .collect(Collectors.toSet());

        //asignamos items y calculamos el valor total de la orden
        order.setOrderItems(orderItems);
        order.setTotalOrderAmount(
            orderItems.stream()
            .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add)
        );


        //ya por ultimo quedaria <limpiar> el carro y guardar la orden
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());

        return savedOrder;
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ElementsNotFoundException("Order not found"));
        return convertToDto(order);
    }


    @Transactional
    private OrderResponseDTO createOrder(OrderRequestDTO request){
        // buscamos el usuario
         User user = userRepository.findById(request.getUserId())
        .orElseThrow( () -> new ElementsNotFoundException("User not found"));
        //creamos la orden
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus(OrderStatus.PENDING);

        //ahora agregamos los items a la orden
        for(OrderRequestDTO.OrderItemRequestDTO itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
            .orElseThrow( () -> new ElementsNotFoundException("Product not found"));


            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItem.setOrder(order);

            order.getOrderItems().add(orderItem);
        }
          
        //calculamos el total
        order.setTotalOrderAmount(
            order.getOrderItems().stream().map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        //guardamos la orden
        Order savedOrder = orderRepository.save(order);

        return new OrderResponseDTO("ok", savedOrder);
           
        

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


    @Transactional
    @Override
    public Order cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
        .orElseThrow(()-> new ElementsNotFoundException("Order not found with Id: " + orderId));


        //para saber si la podemos eliminar o no tenemos que saber el status de la orden:
        if (order.getOrderStatus() == OrderStatus.SHIPPED) {
            throw new IllegalStateException("Cannot cancel a shipped order!");
        }

        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        //tambien tendriamos que reintegrar esos productos que fueron ordenados en ese momento al stock nuevamente
        reintegrateStock(order);

        return order;
    }
    private void reintegrateStock(Order order){
            order.getOrderItems().forEach(item ->{
                Product product = item.getProduct();
                product.setInventory(product.getInventory() + item.getQuantity());
                productRepository.save(product);
            });
    }




}
