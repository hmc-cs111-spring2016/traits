import acme.Store
import logging._

object Program extends App {
   val CAPACITY = 100
   
   object logger extends ColoredConsoleLogger with Lowercasing with Timestamping 
   
   val store = new Store(CAPACITY, logger)
   
   // do some things with the store
   def orderHalf = store.order(CAPACITY / 2)   
   try {
     orderHalf
     orderHalf
     orderHalf
   } catch {
     case e: java.lang.IllegalArgumentException => {}
   }
     
}