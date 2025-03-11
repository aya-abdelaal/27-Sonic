package com.example.MiniProject1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.model.Cart;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.User;
import com.example.repository.CartRepository;
import com.example.repository.OrderRepository;
import com.example.repository.ProductRepository;
import com.example.repository.UserRepository;
import com.example.service.CartService;
import com.example.service.OrderService;
import com.example.service.ProductService;
import com.example.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
@ComponentScan(basePackages = "com.example.*")
@WebMvcTest
class NewProj1Tests {

    @Value("${spring.application.userDataPath}")
    private String userDataPath;

    @Value("${spring.application.productDataPath}")
    private String productDataPath;

    @Value("${spring.application.orderDataPath}")
    private String orderDataPath;

    @Value("${spring.application.cartDataPath}")
    private String cartDataPath;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    public void overRideAll() {
        try {
            objectMapper.writeValue(new File(userDataPath), new ArrayList<User>());
            objectMapper.writeValue(new File(productDataPath), new ArrayList<Product>());
            objectMapper.writeValue(new File(orderDataPath), new ArrayList<Order>());
            objectMapper.writeValue(new File(cartDataPath), new ArrayList<Cart>());
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to JSON file", e);
        }
    }

    public Object find(String typeString, Object toFind) {
        switch (typeString) {
            case "User":
                ArrayList<User> users = getUsers();

                for (User user : users) {
                    if (user.getId().equals(((User) toFind).getId())) {
                        return user;
                    }
                }
                break;
            case "Product":
                ArrayList<Product> products = getProducts();
                for (Product product : products) {
                    if (product.getId().equals(((Product) toFind).getId())) {
                        return product;
                    }
                }
                break;
            case "Order":
                ArrayList<Order> orders = getOrders();
                for (Order order : orders) {
                    if (order.getId().equals(((Order) toFind).getId())) {
                        return order;
                    }
                }
                break;
            case "Cart":
                ArrayList<Cart> carts = getCarts();
                for (Cart cart : carts) {
                    if (cart.getId().equals(((Cart) toFind).getId())) {
                        return cart;
                    }
                }
                break;
        }
        return null;
    }

    public Product addProduct(Product product) {
        try {
            File file = new File(productDataPath);
            ArrayList<Product> products;
            if (!file.exists()) {
                products = new ArrayList<>();
            } else {
                products = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, Product[].class)));
            }
            products.add(product);
            objectMapper.writeValue(file, products);
            return product;
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to JSON file", e);
        }
    }

    public ArrayList<Product> getProducts() {
        try {
            File file = new File(productDataPath);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return new ArrayList<Product>(Arrays.asList(objectMapper.readValue(file, Product[].class)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from JSON file", e);
        }
    }

    public User addUser(User user) {
        try {
            File file = new File(userDataPath);
            ArrayList<User> users;
            if (!file.exists()) {
                users = new ArrayList<>();
            } else {
                users = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, User[].class)));
            }
            users.add(user);
            objectMapper.writeValue(file, users);
            return user;
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to JSON file", e);
        }
    }

    public ArrayList<User> getUsers() {
        try {
            File file = new File(userDataPath);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return new ArrayList<User>(Arrays.asList(objectMapper.readValue(file, User[].class)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from JSON file", e);
        }
    }

    public Cart addCart(Cart cart) {
        try {
            File file = new File(cartDataPath);
            ArrayList<Cart> carts;
            if (!file.exists()) {
                carts = new ArrayList<>();
            } else {
                carts = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, Cart[].class)));
            }
            carts.add(cart);
            objectMapper.writeValue(file, carts);
            return cart;
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to JSON file", e);
        }
    }

    public ArrayList<Cart> getCarts() {
        try {
            File file = new File(cartDataPath);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return new ArrayList<Cart>(Arrays.asList(objectMapper.readValue(file, Cart[].class)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from JSON file", e);
        }
    }

    public Order addOrder(Order order) {
        try {
            File file = new File(orderDataPath);
            ArrayList<Order> orders;
            if (!file.exists()) {
                orders = new ArrayList<>();
            } else {
                orders = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, Order[].class)));
            }
            orders.add(order);
            objectMapper.writeValue(file, orders);
            return order;
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to JSON file", e);
        }
    }

    public ArrayList<Order> getOrders() {
        try {
            File file = new File(orderDataPath);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return new ArrayList<Order>(Arrays.asList(objectMapper.readValue(file, Order[].class)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from JSON file", e);
        }
    }


    @BeforeEach
    void setUp() {
        overRideAll();
    }

    // ------------------------ User Tests -------------------------
    @Test
    void testAddUserEndPoint() throws Exception {
        User testUserSeif = new User();
        testUserSeif.setId(UUID.randomUUID());
        testUserSeif.setName("seif");


        mockMvc.perform(MockMvcRequestBuilders.post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUserSeif)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        boolean found = false;

        for (User user : getUsers()) {
            if (user.getId().equals(testUserSeif.getId()) && user.getName().equals(testUserSeif.getName())) {
                found = true;
                break;
            }
        }
        assertTrue(found, "User should be added correctly");
    }

    @Test
    void testAddUserEndPoint2() throws Exception {
        User testUserMariam = new User();
        testUserMariam.setId(UUID.randomUUID());
        testUserMariam.setName("mariam");


        mockMvc.perform(MockMvcRequestBuilders.post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUserMariam)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        boolean found = false;

        for (User user : getUsers()) {
            if (user.getId().equals(testUserMariam.getId()) && user.getName().equals(testUserMariam.getName())) {
                found = true;
                break;
            }
        }
        assertTrue(found, "User should be added correctly");
    }

    @Test
    void testAddUserEndPoint3() throws Exception {
        User testUserAhmed = new User();
        testUserAhmed.setId(UUID.randomUUID());
        testUserAhmed.setName("ahmed");


        mockMvc.perform(MockMvcRequestBuilders.post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUserAhmed)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        boolean found = false;

        for (User user : getUsers()) {
            if (user.getId().equals(testUserAhmed.getId()) && user.getName().equals(testUserAhmed.getName())) {
                found = true;
                break;
            }
        }
        assertTrue(found, "User should be added correctly");
    }


    @Test
    void testGetUsersEndPoint() throws Exception {
        User testUserAhmed = new User();
        testUserAhmed.setId(UUID.randomUUID());
        testUserAhmed.setName("ahmed");
        addUser(testUserAhmed);


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        List<User> responseUsers = objectMapper.readValue(responseContent, new TypeReference<List<User>>() {
        });

        assertEquals(responseUsers.size(), getUsers().size(), "Users should be returned correctly From Endpoint");
    }


    @Test
    void testGetUsersEndPoint2() throws Exception {
        User testUserMariam = new User();
        testUserMariam.setId(UUID.randomUUID());
        testUserMariam.setName("mariam");
        addUser(testUserMariam);


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        List<User> responseUsers = objectMapper.readValue(responseContent, new TypeReference<List<User>>() {
        });

        assertEquals(responseUsers.size(), getUsers().size(), "Users should be returned correctly From Endpoint");
    }


    @Test
    void testGetUsersEndPoint3() throws Exception {

        User testUserSeif = new User();
        testUserSeif.setId(UUID.randomUUID());
        testUserSeif.setName("seif");
        addUser(testUserSeif);


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        List<User> responseUsers = objectMapper.readValue(responseContent, new TypeReference<List<User>>() {
        });

        assertEquals(responseUsers.size(), getUsers().size(), "Users should be returned correctly From Endpoint");
    }


    @Test
    void testGetUserByIdEndPoint1() throws Exception {
        User testUser1 = new User();
        testUser1.setId(UUID.randomUUID());
        testUser1.setName("Test User1");
        addUser(testUser1);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}", testUser1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(testUser1)));
    }

    @Test
    void testGetUserByIdEndPoint2() throws Exception {
        User testUser2 = new User();
        testUser2.setId(UUID.randomUUID());
        testUser2.setName("Test User2");
        addUser(testUser2);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}", testUser2.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(testUser2)));
    }

    @Test
    void testGetUserByIdEndPoint3() throws Exception {
        User testUser3 = new User();
        testUser3.setId(UUID.randomUUID());
        testUser3.setName("Test User3");
        addUser(testUser3);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}", testUser3.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(testUser3)));
    }


    @Test
    void testGetOrdersByUserIdEndPoint1() throws Exception {
        User testUser1 = new User();
        testUser1.setId(UUID.randomUUID());
        testUser1.setName("Test User1");

        List<Order> orders = List.of(
                new Order(UUID.randomUUID(), testUser1.getId(), 20.0,
                        List.of(new Product(UUID.randomUUID(), "Product A", 20.0)))
        );

        testUser1.setOrders(orders);
        addUser(testUser1);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}/orders", testUser1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(orders)));
    }

    @Test
    void testGetOrdersByUserIdEndPoint2() throws Exception {
        User testUser2 = new User();
        testUser2.setId(UUID.randomUUID());
        testUser2.setName("Test User2");

        List<Order> orders = List.of(
                new Order(UUID.randomUUID(), testUser2.getId(), 30.0,
                        List.of(new Product(UUID.randomUUID(), "Product B", 15.0),
                                new Product(UUID.randomUUID(), "Product C", 15.0)))
        );

        testUser2.setOrders(orders);
        addUser(testUser2);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}/orders", testUser2.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(orders)));
    }

    @Test
    void testGetOrdersByUserIdEndPoint3() throws Exception {
        User testUser3 = new User();
        testUser3.setId(UUID.randomUUID());
        testUser3.setName("Test User3");

        List<Order> orders = List.of(
                new Order(UUID.randomUUID(), testUser3.getId(), 50.0,
                        List.of(new Product(UUID.randomUUID(), "Product D", 25.0),
                                new Product(UUID.randomUUID(), "Product E", 25.0)))
        );

        testUser3.setOrders(orders);
        addUser(testUser3);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}/orders", testUser3.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(orders)));
    }


    @Test
    void testAddOrderToUserEndPoint1() throws Exception {
        User testUser1 = new User();
        testUser1.setId(UUID.randomUUID());
        testUser1.setName("Test User1");

        Cart cart = new Cart();
        cart.setId(UUID.randomUUID());
        cart.setUserId(testUser1.getId());

        Product productA = new Product(UUID.randomUUID(), "Product A", 15.0);
        cart.setProducts(List.of(productA));

        addCart(cart);
        addUser(testUser1);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/{userId}/checkout", testUser1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Order added successfully"));
    }

    @Test
    void testAddOrderToUserEndPoint2() throws Exception {
        User testUser2 = new User();
        testUser2.setId(UUID.randomUUID());
        testUser2.setName("Test User2");

        Cart cart = new Cart();
        cart.setId(UUID.randomUUID());
        cart.setUserId(testUser2.getId());

        Product productB = new Product(UUID.randomUUID(), "Product B", 20.0);
        Product productC = new Product(UUID.randomUUID(), "Product C", 25.0);
        cart.setProducts(List.of(productB, productC));

        addCart(cart);
        addUser(testUser2);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/{userId}/checkout", testUser2.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Order added successfully"));
    }

    @Test
    void testAddOrderToUserEndPoint3() throws Exception {
        User testUser3 = new User();
        testUser3.setId(UUID.randomUUID());
        testUser3.setName("Test User3");

        Cart cart = new Cart();
        cart.setId(UUID.randomUUID());
        cart.setUserId(testUser3.getId());

        Product productD = new Product(UUID.randomUUID(), "Product D", 30.0);
        Product productE = new Product(UUID.randomUUID(), "Product E", 40.0);
        Product productF = new Product(UUID.randomUUID(), "Product F", 50.0);
        cart.setProducts(List.of(productD, productE, productF));

        addCart(cart);
        addUser(testUser3);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/{userId}/checkout", testUser3.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Order added successfully"));
    }

    @Test
    void testRemoveOrderOfUserEndPoint1() throws Exception {
        User testUser1 = new User();
        testUser1.setId(UUID.randomUUID());
        testUser1.setName("Test User1");
        Product product = new Product(UUID.randomUUID(), "Test Product", 100.0);
        Order order = new Order(UUID.randomUUID(), testUser1.getId(), 100.0, List.of(product));
        testUser1.getOrders().add(order);
        addUser(testUser1);
        addOrder(order);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/{userId}/removeOrder", testUser1.getId())
                        .param("orderId", order.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Order removed successfully"));
    }

    @Test
    void testRemoveOrderOfUserEndPoint2() throws Exception {
        User testUser7 = new User();
        testUser7.setId(UUID.randomUUID());
        testUser7.setName("Test User7");
        addUser(testUser7);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/{userId}/removeOrder", testUser7.getId())
                        .param("orderId", UUID.randomUUID().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Order not found"));
    }

    @Test
    void testRemoveOrderOfUserEndPoint3() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/{userId}/removeOrder", UUID.randomUUID())
                        .param("orderId", UUID.randomUUID().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Order not found"));
    }

    @Test
    void testEmptyCartEndpoint1() throws Exception {
        User testUser2 = new User();
        testUser2.setId(UUID.randomUUID());
        testUser2.setName("Test User2");
        Product product = new Product(UUID.randomUUID(), "Test Product", 100.0);
        Cart cart = new Cart(UUID.randomUUID(), testUser2.getId(), new ArrayList<>(List.of(product)));
        addUser(testUser2);
        addCart(cart);

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/{userId}/emptyCart", testUser2.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Cart emptied successfully"));
    }

    @Test
    void testEmptyCartEndpoint2() throws Exception {
        User testUser8 = new User();
        testUser8.setId(UUID.randomUUID());
        testUser8.setName("Test User8");
        addUser(testUser8);
        Cart cart = new Cart(UUID.randomUUID(), testUser8.getId(), new ArrayList<>());
addCart(cart);
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/{userId}/emptyCart", testUser8.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Cart emptied successfully"));
    }

  @Test
    void testEmptyCartEndpoint3() throws Exception {
        User testUser2 = new User();
        testUser2.setId(UUID.randomUUID());
        testUser2.setName("Test User2");
        Product product = new Product(UUID.randomUUID(), "Test Product", 10.0);
        Cart cart = new Cart(UUID.randomUUID(), testUser2.getId(), new ArrayList<>(List.of(product)));
        addUser(testUser2);
        addCart(cart);

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/{userId}/emptyCart", testUser2.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Cart emptied successfully"));
    }

    @Test
    void testAddProductToCartEndPoint1() throws Exception {
        User testUser3 = new User();
        testUser3.setId(UUID.randomUUID());
        testUser3.setName("Test User3");
        Product testProduct = new Product(UUID.randomUUID(), "Test Product", 10.0);
        addUser(testUser3);
        addProduct(testProduct);

        mockMvc.perform(MockMvcRequestBuilders.put("/user/addProductToCart")
                        .param("userId", testUser3.getId().toString())
                        .param("productId", testProduct.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Product added to cart"));
    }

    @Test
    void testAddProductToCartEndPoint2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/user/addProductToCart")
                        .param("userId", UUID.randomUUID().toString())
                        .param("productId", UUID.randomUUID().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User not found"));
    }

    @Test
    void testAddProductToCartEndPoint3() throws Exception {
        User testUser9 = new User();
        testUser9.setId(UUID.randomUUID());
        testUser9.setName("Test User9");
        addUser(testUser9);

        mockMvc.perform(MockMvcRequestBuilders.put("/user/addProductToCart")
                        .param("userId", testUser9.getId().toString())
                        .param("productId", UUID.randomUUID().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Product not found"));
    }

    @Test
    void testDeleteProductFromCartEndPoint1() throws Exception {
        User testUser1 = new User();
        testUser1.setId(UUID.randomUUID());
        testUser1.setName("Test User1");

        Product testProduct = new Product(UUID.randomUUID(), "Test Product1", 10.0);
        addUser(testUser1);
        addProduct(testProduct);

        mockMvc.perform(MockMvcRequestBuilders.put("/user/deleteProductFromCart")
                        .param("userId", testUser1.getId().toString())
                        .param("productId", testProduct.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Cart is empty"));
    }

    @Test
    void testDeleteProductFromCartEndPoint2() throws Exception {
        User testUser2 = new User();
        testUser2.setId(UUID.randomUUID());
        testUser2.setName("Test User2");

        Product testProduct = new Product(UUID.randomUUID(), "Test Product2", 15.0);
        addUser(testUser2);
        addProduct(testProduct);

        mockMvc.perform(MockMvcRequestBuilders.put("/user/deleteProductFromCart")
                        .param("userId", testUser2.getId().toString())
                        .param("productId", testProduct.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Cart is empty"));
    }

    @Test
    void testDeleteProductFromCartEndPoint3() throws Exception {
        User testUser3 = new User();
        testUser3.setId(UUID.randomUUID());
        testUser3.setName("Test User3");

        Product testProduct = new Product(UUID.randomUUID(), "Test Product3", 20.0);
        addUser(testUser3);
        addProduct(testProduct);

        mockMvc.perform(MockMvcRequestBuilders.put("/user/deleteProductFromCart")
                        .param("userId", testUser3.getId().toString())
                        .param("productId", testProduct.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Cart is empty"));
    }


    @Test
    void testDeleteUserByIdEndPoint1() throws Exception {
        User testUser5 = new User();
        testUser5.setId(UUID.randomUUID());
        testUser5.setName("Test User5");
        addUser(testUser5);

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/{userId}", testUser5.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User deleted successfully"));
    }

    @Test
    void testDeleteUserByIdEndPoint2() throws Exception {
        User testUser6 = new User();
        testUser6.setId(UUID.randomUUID());
        testUser6.setName("Test User6");
        addUser(testUser6);

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/{userId}", UUID.randomUUID()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User not found"));
    }

    @Test
    void testDeleteUserByIdEndPoint3() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/{userId}", UUID.randomUUID()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User not found"));
    }








}