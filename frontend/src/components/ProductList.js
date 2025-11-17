import React, { useEffect, useState } from 'react';
import axios from 'axios';

function ProductList({ addToCart }) {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8080/products')
      .then(response => {
        setProducts(response.data);
      })
      .catch(error => {
        console.error('Error fetching products:', error);
      });
  }, []);

  return (
    <div>
      <h2>Products</h2>
      {products.length === 0 && <p>No products available</p>}
      <ul>
        {products.map(prod => (
        <div key={prod.id}>
            <h3>{prod.name}</h3>
            <p>{prod.description}</p>
            <p>Price: €{prod.price}</p>
            {/* Add the button here */}
            <button onClick={() => addToCart(prod)}>Add to Cart</button>
        </div>
        ))}
      </ul>
    </div>
  );
}

export default ProductList;
