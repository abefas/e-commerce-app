
function Cart({ cartItems, removeFromCart, updateQuantity, onCheckout }) {
  const totalPrice = cartItems.reduce(
    (total, item) => total + item.price * item.quantity,
    0
  );

    const handleCheckout = () => {
        onCheckout(cartItems);
    };

  return (
    <div>
      <h2>Shopping Cart</h2>
      {cartItems.length === 0 && <p>The cart is empty.</p>}
      <ul>
        {cartItems.map(item => (
          <li key={item.id}>
            {item.name} - €{item.price} × 
            <input
              type="number"
              value={item.quantity}
              min="1"
              onChange={e => updateQuantity(item.id, Number(e.target.value))}
              style={{ width: '50px', marginLeft: '5px', marginRight: '5px' }}
            />
            = €{(item.price * item.quantity).toFixed(2)}
            <button onClick={() => removeFromCart(item.id)} style={{ marginLeft: '10px' }}>
              Remove
            </button>
          </li>
        ))}
      </ul>
      {cartItems.length > 0 && (
        <h3>Total: €{totalPrice.toFixed(2)}</h3>
      )}
      <button onClick={handleCheckout} disabled={cartItems.length === 0}>
        Checkout
      </button>

    </div>
  );
}

export default Cart;
