import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import org.json.JSONObject;
import org.junit.Test;

public class JUnitTest {

	InventoryAllocator inventoryAllocator = new InventoryAllocator();

	// Test 1: simple test case
	/*
		Input :-
		{ apple: 1 }
		[{ name: owd, inventory: { apple: 1 } }]
		Output:-
		[{owd:{apple:1}}]
	*/
	@Test
	public void test_example_one() {
		JSONObject orders = new JSONObject("{ apple: 1 }");
		ArrayList<JSONObject> warehouses = new ArrayList<>();
		warehouses.add(new JSONObject("{ name: owd, inventory: { apple: 1 } }"));
		inventoryAllocator.setOrders(orders);
		inventoryAllocator.setWarehouses(warehouses);
		ArrayList<JSONObject> expecteds = new ArrayList<>();
		expecteds.add(new JSONObject("{ owd: { apple: 1 } }"));
		assertEquals(expecteds.toString(), inventoryAllocator.calculateShipments().toString());
	}

	// Test 2: check warehouse name are sorted
	/*
	 	Input:-
		{ apple: 10 }
		[{ name: owd, inventory: { apple: 5 } }, { name: dm, inventory: { apple: 5 }}]
		Output:-
		[{dm:{apple:5}}, {owd:{apple:5}}]
	*/
	@Test
	public void test_example_two() {
		JSONObject orders = new JSONObject("{ apple: 10 }");
		ArrayList<JSONObject> warehouses = new ArrayList<>();
		warehouses.add(new JSONObject("{ name: owd, inventory: { apple: 5 } }"));
		warehouses.add(new JSONObject("{ name: dm, inventory: { apple: 5 } }"));
		inventoryAllocator.setOrders(orders);
		inventoryAllocator.setWarehouses(warehouses);
		ArrayList<JSONObject> expecteds = new ArrayList<>();
		expecteds.add(new JSONObject("{ dm: { apple: 5 } }"));
		expecteds.add(new JSONObject("{ owd: { apple: 5 } }"));
		assertEquals(expecteds.toString(), inventoryAllocator.calculateShipments().toString());
	}

	// Test 3: 0 items
	/*
	  	Input: -
		{ apple: 1 }
		[{ name: owd, inventory: { apple: 0 } }]
		Output:-
		[]
	*/
	@Test
	public void test_example_three() {
		JSONObject orders = new JSONObject("{ apple: 1 }");
		ArrayList<JSONObject> warehouses = new ArrayList<>();
		warehouses.add(new JSONObject("{ name: owd, inventory: { apple: 0 } }"));
		inventoryAllocator.setOrders(orders);
		inventoryAllocator.setWarehouses(warehouses);
		ArrayList<JSONObject> expecteds = new ArrayList<>();
		assertEquals(expecteds.toString(), inventoryAllocator.calculateShipments().toString());
	}

	// Test 4: not enough items
	/*
	 	Input:-
		{ apple: 2 }
		[{ name: owd, inventory: { apple: 1 } }]
		Output:-
		[]
	*/
	@Test
	public void test_example_four() {
		JSONObject orders = new JSONObject("{ apple: 2 }");
		ArrayList<JSONObject> warehouses = new ArrayList<>();
		warehouses.add(new JSONObject("{ name: owd, inventory: { apple: 1 } }"));
		inventoryAllocator.setOrders(orders);
		inventoryAllocator.setWarehouses(warehouses);
		ArrayList<JSONObject> expecteds = new ArrayList<>();
		assertEquals(expecteds.toString(), inventoryAllocator.calculateShipments().toString());
	}

	// Test 5: split shipment across several warehouses 
	/*
	 	Input:-
		{ apple: 5, banana: 5, orange: 5 }
		[ { name: owd, inventory: { apple: 5, orange: 10 } }, { name: dm, inventory: { banana: 5, orange: 10 } } ]
		Output:-
		[{dm:{banana:5}}, {owd:{orange:5,apple:5}}]
	*/
	@Test
	public void test_example_five() {
		JSONObject orders = new JSONObject("{ apple: 5, banana: 5, orange: 5 }");
		ArrayList<JSONObject> warehouses = new ArrayList<>();
		warehouses.add(new JSONObject("{ name: owd, inventory: { apple: 5, orange: 10 } }"));
		warehouses.add(new JSONObject("{ name: dm, inventory: { banana: 5, orange: 10 } }"));
		inventoryAllocator.setOrders(orders);
		inventoryAllocator.setWarehouses(warehouses);
		ArrayList<JSONObject> expecteds = new ArrayList<>();
		expecteds.add(new JSONObject("{dm: {banana: 5}}"));
		expecteds.add(new JSONObject("{owd: {orange: 5 ,apple: 5}}"));
		assertEquals(expecteds.toString(), inventoryAllocator.calculateShipments().toString());
	}

	// Test 6: warehouses input empty
	/*
		Input:-
		{ apple: 5, banana: 5, orange: 5 }
		[]
		Output:-
		[]
	*/
	@Test
	public void test_example_six() {
		JSONObject orders = new JSONObject("{ apple: 5, banana: 5, orange: 5 }");
		ArrayList<JSONObject> warehouses = new ArrayList<>();
		inventoryAllocator.setOrders(orders);
		inventoryAllocator.setWarehouses(warehouses);
		ArrayList<JSONObject> expecteds = new ArrayList<>();
		assertEquals(expecteds.toString(), inventoryAllocator.calculateShipments().toString());
	}

	// Test 7: orders and warehouses input empty
	/*
	  	Input:-
		{}
		[]
		Output:-
		[]
	*/
	@Test
	public void test_example_seven() {
		JSONObject orders = new JSONObject("{ }");
		ArrayList<JSONObject> warehouses = new ArrayList<>();
		inventoryAllocator.setOrders(orders);
		inventoryAllocator.setWarehouses(warehouses);
		ArrayList<JSONObject> expecteds = new ArrayList<>();
		assertEquals(expecteds.toString(), inventoryAllocator.calculateShipments().toString());
	}

	// Test 8: orders input empty
	/*
	  	Input:-
		{}
		[ { name: owd, inventory: { apple: 5, orange: 10 } }, { name: dm, inventory: { banana: 5, orange: 10 } } ]
		Output:-
		[]
	*/
	@Test
	public void test_example_eight() {
		JSONObject orders = new JSONObject("{}");
		ArrayList<JSONObject> warehouses = new ArrayList<>();
		warehouses.add(new JSONObject("{ name: owd, inventory: { apple: 5, orange: 10 } }"));
		warehouses.add(new JSONObject("{ name: dm, inventory: { banana: 5, orange: 10 } }"));
		inventoryAllocator.setOrders(orders);
		inventoryAllocator.setWarehouses(warehouses);
		ArrayList<JSONObject> expecteds = new ArrayList<>();
		assertEquals(expecteds.toString(), inventoryAllocator.calculateShipments().toString());
	}

	// Test 9: warehouses inventory have not require items
	/*
	  	Input:-
		{ orange: 5 }
		[{ name: owd, inventory: { apple: 5} },{ name: dm, inventory: { banana: 5} }]
		Output:-
		[]
	*/
	@Test
	public void test_example_nine() {
		JSONObject orders = new JSONObject("{ orange: 5 }");
		ArrayList<JSONObject> warehouses = new ArrayList<>();
		warehouses.add(new JSONObject("{ name: owd, inventory: { apple: 5} }"));
		warehouses.add(new JSONObject("{ name: dm, inventory: { banana: 5} }"));
		inventoryAllocator.setOrders(orders);
		inventoryAllocator.setWarehouses(warehouses);
		ArrayList<JSONObject> expecteds = new ArrayList<>();
		assertEquals(expecteds.toString(), inventoryAllocator.calculateShipments().toString());
	}

	// Test 10: orders items value 0
	/*
	  	Input:-
		{ apple: 0, banana: 0, orange: 0 }
		[ { name: owd, inventory: { apple: 5, orange: 10 } }, { name: dm:, inventory: { banana: 5, orange: 10 } } ]
		Output:-
		[]
	*/
	@Test
	public void test_example_ten() {
		JSONObject orders = new JSONObject("{ apple: 0, banana: 0, orange: 0 }");
		ArrayList<JSONObject> warehouses = new ArrayList<>();
		warehouses.add(new JSONObject("{ name: owd, inventory: { apple: 5, orange: 10 } }"));
		warehouses.add(new JSONObject("{ name: dm, inventory: { banana: 5, orange: 10 } }"));
		inventoryAllocator.setOrders(orders);
		inventoryAllocator.setWarehouses(warehouses);
		ArrayList<JSONObject> expecteds = new ArrayList<>();
		assertEquals(expecteds.toString(), inventoryAllocator.calculateShipments().toString());
	}

	// Test 11: orders items value < 0
	/*
	  	Input:-
		{ apple: -2, banana: 0, orange: -8 }
		[ { name: owd, inventory: { apple: 5, orange: 10 } }, { name: dm, inventory: { banana: 5, orange: 10 } } ]
		Output:-
		[]
	*/
	@Test
	public void test_example_eleven() {
		JSONObject orders = new JSONObject("{ apple: -2, banana: 0, orange: -8 }");
		ArrayList<JSONObject> warehouses = new ArrayList<>();
		warehouses.add(new JSONObject("{ name: owd, inventory: { apple: 5, orange: 10 } }"));
		warehouses.add(new JSONObject("{ name: dm, inventory: { banana: 5, orange: 10 } }"));
		inventoryAllocator.setOrders(orders);
		inventoryAllocator.setWarehouses(warehouses);
		ArrayList<JSONObject> expecteds = new ArrayList<>();
		assertEquals(expecteds.toString(), inventoryAllocator.calculateShipments().toString());
	}

	// Test 12: warehouses inventory items value < 0
	/*
	  	Input:-
		{ apple: 2, banana: 0, orange: 8 }
		[ { name: owd, inventory: { apple: 5, orange: -10 } }, { name: dm, inventory: { banana: 5, orange: -10 } } ]
		Output:-
		[]
	*/
	@Test
	public void test_example_twelve() {
		JSONObject orders = new JSONObject("{ apple: 2, banana: 0, orange: 8 }");
		ArrayList<JSONObject> warehouses = new ArrayList<>();
		warehouses.add(new JSONObject("{ name: owd, inventory: { apple: 5, orange: -10 } }"));
		warehouses.add(new JSONObject("{ name: dm, inventory: { banana: 5, orange: -10 } }"));
		inventoryAllocator.setOrders(orders);
		inventoryAllocator.setWarehouses(warehouses);
		ArrayList<JSONObject> expecteds = new ArrayList<>();
		assertEquals(expecteds.toString(), inventoryAllocator.calculateShipments().toString());
	}

	// Test 13: orders item value 0 and warehouses inventory items value < 0
	/*
	  	Input:-
		{ apple: 2, banana: 0, orange: 8 }
		[ { name: owd, inventory: { apple: 5, orange: -10 } }, { name: dm, inventory: { banana: 5, orange: 10 } } ]
		Output:-
		[{dm:{orange:8}}, {owd:{apple:2}}]
	*/
	@Test
	public void test_example_thirteen() {
		JSONObject orders = new JSONObject("{ apple: 2, banana: 0, orange: 8 }");
		ArrayList<JSONObject> warehouses = new ArrayList<>();
		warehouses.add(new JSONObject("{ name: owd, inventory: { apple: 5, orange: -10 } }"));
		warehouses.add(new JSONObject("{ name: dm, inventory: { banana: 5, orange: 10 } }"));
		inventoryAllocator.setOrders(orders);
		inventoryAllocator.setWarehouses(warehouses);
		ArrayList<JSONObject> expecteds = new ArrayList<>();
		expecteds.add(new JSONObject("{dm: {orange: 8}}"));
		expecteds.add(new JSONObject("{owd: {apple: 2}}"));
		assertEquals(expecteds.toString(), inventoryAllocator.calculateShipments().toString());
	}
	
	// Test 14: single shipment prioritizes split shipment 
	/*
	  	Input:-
		{ apple: 10 }
		[{ name: owd, inventory: { apple: 5 } },{ name: dm, inventory: { apple: 5 } },{ name: ui, inventory: { apple: 13 } }]
		Output:-
		[{ui:{apple:10}}]
	*/
	@Test
	public void test_example_fourteen() {
		JSONObject orders = new JSONObject("{ apple: 10 }");
		ArrayList<JSONObject> warehouses = new ArrayList<>();
		warehouses.add(new JSONObject("{ name: owd, inventory: { apple: 5 } }"));
		warehouses.add(new JSONObject("{ name: dm, inventory: { apple: 5 } }"));
		warehouses.add(new JSONObject("{ name: ui, inventory: { apple: 13 } }"));
		inventoryAllocator.setOrders(orders);
		inventoryAllocator.setWarehouses(warehouses);
		ArrayList<JSONObject> expecteds = new ArrayList<>();
		expecteds.add(new JSONObject("{ui: {apple: 10}}"));
		assertEquals(expecteds.toString(), inventoryAllocator.calculateShipments().toString());
	}
}
