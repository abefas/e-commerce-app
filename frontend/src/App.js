import React, { useState } from 'react';
import ProductList from './components/ProductList';
import Cart from './components/Cart';
import axios from 'axios';

function App() {
  const [cartItems, setCartItems] = useState([]);

  // Add product to cart or increase quantity if it exists
  const addToCart = (product) => {
    setCartItems(prevItems => {
      const existingItem = prevItems.find(item => item.id === product.id);
      if (existingItem) {
        return prevItems.map(item =>
          item.id === product.id ? { ...item, quantity: item.quantity + 1 } : item
        );
      }
      return [...prevItems, { ...product, quantity: 1 }];
    });
  };

  // Remove product from cart
  const removeFromCart = (productId) => {
    setCartItems(prevItems => prevItems.filter(item => item.id !== productId));
  };

  // Update quantity of a product in the cart
  const updateQuantity = (productId, quantity) => {
    if (quantity <= 0) {
      removeFromCart(productId);
      return;
    }
    setCartItems(prevItems =>
      prevItems.map(item =>
        item.id === productId ? { ...item, quantity } : item
      )
    );
  };

  const handleCheckout = async (cartItems) => {
  // Prepare order payload
  const orderPayload = {
    user: { id: 1 }, // use a fixed user or add user management later
    orderDate: new Date().toISOString(),
    orderItems: cartItems.map(item => ({
      product: { id: item.id },
      quantity: item.quantity,
      priceAtPurchase: item.price
    }))
  };

  try {
    const response = await axios.post('http://localhost:8080/orders', orderPayload);
    alert('Order placed successfully! Order ID: ' + response.data.id);
    setCartItems([]); // Clear cart after success
  } catch (error) {
    console.error('Checkout failed:', error);
    alert('Failed to place order.');
  }
};
  return (
    <div className="App">
      <h1>My E-commerce Store</h1>
      <ProductList addToCart={addToCart} />
      <Cart
        cartItems={cartItems}
        removeFromCart={removeFromCart}
        updateQuantity={updateQuantity}
        onCheckout={handleCheckout}
      />
    </div>
  );

}

export default App;
