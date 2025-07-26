function calculateDiscount(price, discountRate) {
  if (discountRate < 0 || discountRate > 1) {
    throw new Error("Invalid discount rate");
  }
  return price - (price * discountRate);
}
