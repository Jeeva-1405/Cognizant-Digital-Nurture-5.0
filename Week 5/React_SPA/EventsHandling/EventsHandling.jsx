import React, { useState } from "react";
import { createRoot } from "react-dom/client";

function ProductCatalog() {
  const [cart, setCart] = useState([]);
  const [message, setMessage] = useState("");

  const products = [
    { id: "P101", name: "Wireless Mouse", price: 799 },
    { id: "P102", name: "Mechanical Keyboard", price: 3499 },
    { id: "P103", name: "27-inch Monitor", price: 15999 }
  ];

  const handleAddToCart = (product, event) => {
    event.preventDefault();
    setCart((prevCart) => [...prevCart, product]);
    setMessage(product.name + " added to cart");
  };

  const handleClearCart = () => {
    setCart([]);
    setMessage("Cart cleared");
  };

  const handleMouseEnter = (productName) => {
    console.log("Hovering over: " + productName);
  };

  return (
    <div className="product-catalog">
      <h2>Product Catalog</h2>
      {products.map((product) => (
        <div
          key={product.id}
          className="product-row"
          onMouseEnter={() => handleMouseEnter(product.name)}
        >
          <span>{product.name} - Rs.{product.price}</span>
          <button onClick={(event) => handleAddToCart(product, event)}>
            Add to Cart
          </button>
        </div>
      ))}

      <button onClick={handleClearCart}>Clear Cart</button>

      <p>{message}</p>
      <p>Items in cart: {cart.length}</p>
    </div>
  );
}

const root = createRoot(document.getElementById("root"));
root.render(<ProductCatalog />);

export default ProductCatalog;
