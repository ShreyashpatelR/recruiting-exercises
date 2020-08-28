import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import org.json.JSONObject;

public class InventoryAllocator {

	// store orders data
	private JSONObject orders;
	private JSONObject ordersCopy;
	// store warehouse data
	private ArrayList<JSONObject> warehouses;

	public InventoryAllocator() {
		warehouses = new ArrayList<>();
	}

	public void setOrders(JSONObject orders) {
		this.orders = orders;
	}

	public void setWarehouses(ArrayList<JSONObject> warehouses) {
		this.warehouses = warehouses;
	}

	// it will return shipment of every orders
	ArrayList<JSONObject> calculateShipments() {
		if(!orders.isEmpty()) {
			this.ordersCopy = new JSONObject(orders, JSONObject.getNames(orders));
		}
		ArrayList<JSONObject> shipments = new ArrayList<>();
		int checkSingleShipment;
		Iterator<String> keys;
		if (!orders.isEmpty() && !warehouses.isEmpty()) {
			for (JSONObject warehouse : warehouses) {
				try {
					JSONObject inventoryObject = warehouse.getJSONObject("inventory");
					keys = inventoryObject.keys();
					JSONObject shipmentInventory = new JSONObject();
					checkSingleShipment = 0;
					while (keys.hasNext()) {
						String key = keys.next();
						if (inventoryObject.getInt(key) > 0) {
							if (orders.has(key)) {
								int units = orders.getInt(key);
								int remainUnit = 0;
								if (units >= inventoryObject.getInt(key)) {
									remainUnit = units - inventoryObject.getInt(key);
								} else {
									remainUnit = 0;
								}
								if ((units - remainUnit) > 0) {
									shipmentInventory.put(key, units - remainUnit);
								}
								orders.remove(key);
								if (remainUnit > 0) {
									orders.put(key, remainUnit);
								}
							}
						}
						
						if(inventoryObject.getInt(key) > 0) {
							if (ordersCopy.has(key)) {
								if(inventoryObject.getInt(key)>=ordersCopy.getInt(key) ) {
									checkSingleShipment++;
								}
							}
						}
					}
					if(checkSingleShipment==ordersCopy.length()) {
						JSONObject shipmentName = new JSONObject();
						keys = ordersCopy.keys();
						shipmentInventory = new JSONObject();
						while (keys.hasNext()) {
							String key = keys.next();
							shipmentInventory.put(key, ordersCopy.get(key));
						}
						if(!shipmentInventory.isEmpty()) {
							shipments = new ArrayList<>();
							shipmentName.put(warehouse.getString("name"), shipmentInventory);
							shipments.add(shipmentName);
							orders  = new JSONObject();
							break;
						}
					}
					else if (!shipmentInventory.isEmpty()) {
						JSONObject shipmentName = new JSONObject();
						shipmentName.put(warehouse.getString("name"), shipmentInventory);
						shipments.add(shipmentName);
					}
				} catch (Exception e) {
					System.out.println("\n Invalid input\n Please check you have enter 'name' and 'inventory'\n");
					System.out.println(
							" Example input:- [ { name: owd, inventory: { apple: 5, orange: 10 } }, { name: dm, inventory: { banana: 5, orange: 10 } } ] \n");
				}
			}
			if (!orders.isEmpty()) {
				shipments = new ArrayList<>();
			}
		}
		Collections.sort(shipments, new sortShipments());
		return shipments;
	}

}

class sortShipments implements Comparator<JSONObject> {

	@Override
	public int compare(JSONObject o1, JSONObject o2) {
		return o1.keys().next().compareTo(o2.keys().next());
	}

}