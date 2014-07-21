

import java.util.LinkedList;
/****
 * 
 * @author Cristina
 * 
 *
 */


public class Node {
	String key;
	String value; 
	LinkedList<Node> children;
	
	/***
	 * constructor pentru nod fara parametri
	 * cheia este initializata cu sirul vid
	 * copiii sunt initializati cu o lista de noduri
	 */
	
	public Node() {
	        key = "";
	        children = new LinkedList<Node>();
	    }
	
	 /**
	  * 
	  * @return cheia unui nod
	  */
	
	String getKey(){
		return this.key;
	}
	
	/***
	 * 
	 * @return - valoarea unui nod
	 */
	
	String getValue(){
		return this.value;
	}
	
	/***
	 *  
	 * @return - lista de copii ai unui nod
	 */
	
	public LinkedList<Node> getChildren(){
		return this.children;
	}
	
	
	/**
	 * 
	 * @param key - cheia carea urmeaza sa fie inserata
	 */
	
	void setKey(String key){
		this.key = key;
	}
	
	/**
	 * 
	 * @param value - valoarea care urmeaza sa fie inserata
	 */
	
	void setValue(String value){
		this.value = value;
	}
	
	/***
	 *nodul n dat ca parametru devine copilul instantei curente
	 *
	 * @param n - un nod
	 */
	void setChild(Node n){
		this.children.add(n);
	}
	
	/***
	 * lista de copii l devine lista copiilor instantei curente
	 * 
	 * @param l -lista de copii
	 */
	
	void setChildren(LinkedList<Node> l){
		this.children = l;
	}
	
	/***
	 * functia gaseste pozitia pana la care un string si 
	 * cheia instantei curente sunt identice
	 * 
	 * @param string - un sir de caractere
	 * @return noOfMatches - pozitia pana la care se potrivesc doua chei
	 */
	
	
	int matches (String string) {
		int noOfMatches = 0;
		
		char[] a = this.key.toCharArray();
		char[] b = string.toCharArray();
		
		for (int i = 0; i < Math.min(this.key.length(), string.length()); i++) {
			if (a[i] != b[i])
				break;
			
			noOfMatches++;
		}
		return noOfMatches;
	}
}



