package fr.luzi.utilities.jtable;

/**
* @language JAVA
* @author Randy LUZI
* @mail randy.luzi@gmail.com
* @license MIT
*/

public class ElementsDrug {

	private int id;
	private String name;
	private String categorie;
	private Double price;
	private String date;
	private String quantity;

	/**
	 * 
	 * @param id_
	 * @param name_
	 * @param categorie_
	 * @param price_
	 * @param date_
	 * @param quantity_
	 */

	public ElementsDrug(int id_, String name_, String categorie_, Double price_, String date_, String quantity_) {

		this.id = id_;
		this.name = name_;
		this.categorie = categorie_;
		this.price = price_;
		this.date = date_;
		this.quantity = quantity_;

	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCategorie() {
		return categorie;
	}

	public Double getPrice() {
		return price;
	}

	public String getDate() {
		return date;
	}

	public String getQuantity() {
		return quantity;
	}

}
