import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import org.json.JSONObject;

public class MainFile {

	
	public static void main(String[] args) {
		InventoryAllocator inventoryAllocator = new InventoryAllocator();
		InputStringCheck inputStringCheck = new InputStringCheck();
		splitWarehouse splitWarehouse = new splitWarehouse();
		Scanner sc = new Scanner(System.in);
		String response = null;
		JSONObject orders = null;
		ArrayList<JSONObject> warehouses = null;
		String responseOrderWarehouses = null;

		do {
			System.out.println("-------------------------------- Welcome--------------------------------");
			System.out.println("\nEnter 1 or 2 \n\n1 Want to run unit test \n2 Enter data manually \n ");
			response = sc.nextLine().trim();
			if (response.equals("1")) {
				//it will get package name of this file class
				Package pack = inputStringCheck.getClass().getPackage();
				String packName = pack.getName();
				if(!packName.isEmpty()) {
					packName = packName + ".";
				}
				org.junit.runner.JUnitCore.main(packName+"JUnitTest");
			} else if (response.equals("2")) {
				orders = null;
				do {
					System.out.println(
							"\nEnter your orders, \n Example input:-  { apple: 5, banana: 5, orange: 5 } \n ");
					responseOrderWarehouses = sc.nextLine().trim();
					orders = inputStringCheck.checkString(responseOrderWarehouses);
					responseOrderWarehouses = null;
				} while (orders == null);
				warehouses = null;
				do {
					System.out.println(
							"\nEnter your warehouses, \n Example input:- [ { name: owd, inventory: { apple: 5, orange: 10 } }, { name: dm, inventory: { banana: 5, orange: 10 } } ] \n");
					responseOrderWarehouses = sc.nextLine().trim();
					warehouses = splitWarehouse.warehouseSplit(responseOrderWarehouses, inputStringCheck);
					responseOrderWarehouses = null;
				} while (warehouses==null);
				inventoryAllocator.setOrders(orders);
				inventoryAllocator.setWarehouses(warehouses);
				System.out.println("\nOutput:- \n\n  " + inventoryAllocator.calculateShipments().toString().replaceAll("\"", ""));
			}
			System.out.println("\nWant to test Again? \nEnter Y or N\n Y - Yes\n N - No \n");
			response = sc.nextLine().trim();
		} while (response.equals("Y"));
		sc.close();
		System.out.println("--------------------------------End--------------------------------");
	}

}

// warehouses input data split into JSON format
class splitWarehouse {

	ArrayList<JSONObject> warehouseSplit(String str, InputStringCheck inputStringCheck) {
		ArrayList<JSONObject> wareHouseSplit = new ArrayList<>();
		Stack<String> stack = new Stack<>();
		int start = 0, i = 0;
		JSONObject temp = null;
		if (str.charAt(0) == '[' && str.charAt(str.length() - 1) == ']') {
			str = str.substring(1, str.length() - 1);
			while (i < str.length()) {
				if (str.charAt(i) == ',' && stack.empty()) {
					temp = inputStringCheck.checkString(str.substring(start, i));
					if (temp != null) {
						wareHouseSplit.add(temp);
						start = i + 1;
					}
				} else if (str.charAt(i) == '{') {
					stack.push("{");
				} else if (str.charAt(i) == '}') {
					stack.pop();
				}
				i++;
			}
			if (start < str.length() && stack.empty()) {
				temp = inputStringCheck.checkString(str.substring(start, i));
				if (temp != null) {
					wareHouseSplit.add(temp);
					start = i + 1;
				}
			}
		} else {
			System.out.println("\n Invalid input !!! ");
		}
		return wareHouseSplit;
	}

}

// It will convert input string into JSON format
class InputStringCheck {

	JSONObject checkString(String str) {
		try {
			return new JSONObject(str);
		} catch (Exception e) {
			System.out.println("\n Invalid input !!! ");
			return null;
		}
	}

}