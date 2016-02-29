package acme

import logging._

class Store(initialStock: Int, logger: Logger = new NoOpLogger{} ) {
  private var inStock = 0
  widgetsInStock = initialStock
  
  import logger.{info, warning, error, log}
  
  info(s"created a store with ${widgetsInStock} widgets")

  // properties (getters and setters)
  def widgetsInStock = inStock
  def widgetsInStock_=(newValue: Int) = {
    require(newValue >= 0, {error("Out of stock!"); "Out of stock!"})
    inStock = newValue
  }

  def order(numWidgets: Int) =  {
    info(s"Order arrived for ${numWidgets} widgets...")
    widgetsInStock -= numWidgets
    warning(s"widgets in stock: $widgetsInStock")
  }
}
