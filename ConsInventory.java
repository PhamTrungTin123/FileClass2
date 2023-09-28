package Example1;

public class ConsInventory implements Inventory {
	private Toy first;
	private Inventory rest;
	/**
	 * This is constructor of ConsInventory
	 *  Toy t1 = new Toy("ball", 25.00, 5);
	 *  Inventory empty = new MTInventory();
	 *  ConsInventory c1 = new ConsInventory(t1, empty);
	 * @param first: dau tien
	 * @param rest: nghi 
	 */
	public ConsInventory(Toy first, Inventory rest) {
		super();
		this.first = first;
		this.rest = rest;
	}
	/**
	 * Returns a string representation of the object
	 *   Toy t1 = new Toy("ball", 25.00, 5);
	 *  Inventory empty = new MTInventory();
	 *  ConsInventory c1 = new ConsInventory(t1, empty);
	 *  name:doll	  price:17.95	  available:5
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.first.toString() + "\n" + this.rest.toString();
	}
	

}
