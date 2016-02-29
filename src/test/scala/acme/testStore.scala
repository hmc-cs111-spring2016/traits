package acme

import org.scalatest.{FunSpec, Matchers}

class StoreSpec extends FunSpec with Matchers {
  
  final val CAPACITY = 100
  val store = new Store(CAPACITY)

  describe("A store") {

    // create a store with CAPACITY widgets
    describe(s"when created with $CAPACITY widgets") {
      it(s"should have $CAPACITY available widgets") {
        store.widgetsInStock should be (CAPACITY)
      }
    }

    // order half of the widgets
    describe(s"when ordering the 1st half of the widgets") {
      it(s"should have $CAPACITY / 2 available widgets") {
        store.order(CAPACITY / 2)
        store.widgetsInStock should be (CAPACITY / 2)
      }
    }

    // order (the other) half of the widgets
    describe(s"when ordering the 2nd half of the widgets") {
      it(s"should have no available widgets") {
        store.order(CAPACITY / 2)
        store.widgetsInStock should be (0)
      }
    }

    // order (the third) half of the widgets
    describe(s"when ordering the 3rd half of the widgets") {
      it("should throw an exception") {
        a [java.lang.IllegalArgumentException] should be thrownBy {
          store.order(CAPACITY / 2)
        }        
      }
      it(s"should have no available widgets") {
        store.widgetsInStock should be (0)
      }
    }

  }
}
