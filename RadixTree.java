

import java.util.LinkedList;
import java.util.Queue;

public class RadixTree {
	Node root;
	
	public RadixTree() {
		root = new Node();
		root.key = "";
	}
	/**  Functia private "insert", adauga cuvintele din text in arbore, dupa caz.
	 * In primul caz (numarul de caractere care se potrivesc din cheia care urmeaza sa fie introdusa
	si cheia curenta este mai mic decat lungimea cheii care urmeaza sa fie introdusa
	si egal cu lungimea cheii nodului curent SAU nu exista caractere comune intre
	cheia curenta si cea care urmeaza sa fie introdusa) am procedat astfel: 
		 * daca noul cuvant care urmeaza sa fie introdus in arbore inca se
		 potriveste cu cheile altor noduri de mai jos, continuam
		 sa inseram cheia si valorea recursiv in arbore; 
		 * 
		 * altfel, adaug un nod nou(n); 
		 * cheia sa este subsirul din cheia care va fi introdusa;
		 * subsirul incepe de la pozitia de la care cheia curenta si cheia
		 * pe care urmeaza sa o introduc nu se mai potrivesc;
		 * 
		 * valoarea este valoarea data ca parametru;
		 * 
		 * n devine copilul nodului curent.
		 * 
		 * Al doilea caz(numarul de caractere care se potrivesc din cheia
	care urmeaza sa fie introdusa si cheia curenta este egal cu lungimea
	cheii care urmeaza sa fie introdusa si cu lungimea cheii nodului curent)
	presupune ca introducem acelasi cuvant de mai multe ori.
		 * Deci, trebuie schimbata doar valoarea nodului curent, 
		 * care devine valoarea nodului curent concatenata cu
		 * caracterul '*' si pozitia din cadrul parametrului 
		 * value al functiei insert.
		 * 
		 * In cazul in care in nodul respectiv nu exista valoare,
		 * adaug valoarea data ca parametru.
		 * 
		 *In al treilea caz (numarul de caractere care se potrivesc din cheia
	care urmeaza sa fie introdusa si cea curenta este mai mare ca 0 si mai mic decat lungimea
	cheii nodului curent) adaug 2 noduri noi(aux1 si aux2) pe care le voi
	lega de arbore astfel:
		  
		 * cheia lui aux1 va fi subsirul din cheia curenta
		 * care incepe de la pozitia de la care cheia curenta 
		 * si cheia pe care urmeaza sa o introduc(parametrul key)
		 * nu se mai potrivesc,
		 * 
		 * valoarea lui aux1 va prelua valoarea nodului curent,
		 * 
		 * copiii nodului curent devin copiii lui aux1;
		 * 
		 * 
		 * cheia nodului curent va fi subsirul comun al cheii curente si al
		 * cheii care urmeaza sa fie introdusa,
		 * 
		 * valoarea nodului curent e inexistenta, deci va fi setata la null,
		 * 
		 * aux1 devine copilul nodului curent.
		 * 
		 * 
		 * Daca numarul de caractere care se potrivesc din cheia
	care urmeaza sa fie introdusa si cea curenta e mai scurt decat cheia
	care urmeaza sa fie introdusa atunci inseram inca un nod nou(aux2).
		 * 
		 * Cheia lui aux2 va fi subsirul din cheia care va fi introdusa
		 * care incepe de la pozitia de la care cheia curenta si
		 * cheia pe care urmeaza sa o introduc nu se mai potrivesc,
		 * 
		 * valoarea lui aux2 este valoare parametrului functiei insert,
		 * 
		 * aux2 devine copilul nodului curent.
		 *
	 * @param current - nodul curent
	 * @param key - cheia care urmeaza sa fie introdusa
	 * @param value - valoarea care umeaza sa fie introdusa
	 */
	
	private void insert(Node current, String key, String value) {
		int noOfMatches = current.matches(key);
				
		if ((noOfMatches < key.length() &&
			noOfMatches == current.getKey().length())
			|| noOfMatches == 0) {
			int ok  = 0;
			
			String Key = key.substring(noOfMatches);
			
			for (int i = 0; i < current.getChildren().size(); i++) {
				Node aux = current.getChildren().get(i);
				
				if (aux.getKey().substring(0,1).equals(Key.substring(0,1))) {
					ok = 1;
					insert(aux, Key, value);
					break;
				}
			}

           
			if (ok == 0) {
				Node aux = new Node();
				
				aux.setKey(Key);
				aux.setValue(value);
				current.setChild(aux);
			}
		}
		
		else if (noOfMatches == key.length() &&
				noOfMatches == current.getKey().length()) {
			
			if (current.getValue() !=null)
				
				current.setValue(current.getValue() +
				value.substring(value.indexOf('*')));
			
			else
				
				current.setValue(value);
		}

		
		else if (noOfMatches > 0 && noOfMatches < current.getKey().length()) {
			Node aux1 = new Node();
			
			aux1.setKey(current.getKey().substring(noOfMatches));
			aux1.setValue(current.getValue());
			
			if(current.getChildren().size() != 0)
			aux1.setChildren(current.getChildren());
			

            
			
			current.setKey(key.substring(0, noOfMatches));
			current.setValue(null);
			current.setChildren(new LinkedList<Node>());
			current.setChild(aux1);
            
			if(noOfMatches < key.length()) {
				Node aux2 = new Node();
				
				
				aux2.setKey(key.substring(noOfMatches));
				aux2.setValue(value);
				current.setChild(aux2);
			}
			else 
				current.setValue(value);
		}      
	}
	
	/**	Functia incapsuleaza functia insert anterioara, care 
	 * este private.
	 * 
	 * @param  word - cuvantul din text concatenat cu pozitia sa
	*/
	
	void insert(String word){
		String key = word.substring(0, word.indexOf('*')) ;
		String value = word;
		this.insert( this.root, key, value);		
	}
	
	/**
	 * Functia intoarce nodul in care se termina prefixul cautat si
	returneaza "null" daca nu gaseste prefixul cautat.
	Exista doua cazuri principale de cautare: 
	 * Primul caz implica faptul ca lungimea prefixului este aceeasi
	cu lungimea cheii nodului curent. 
	
	In acest caz nodul in care se termina prefixul este cel curent.
	 * 
	 * Al doilea caz implica faptul ca lungimea prefixului este mai mare
	decat lungimea cheii nodului curent.
	 * Daca prefixul inca se mai potriveste cu cheile altor noduri mai jos,
	 * continuam sa cautam, coborand in arbore.
	 * 
	 * Altfel, returnez nodul in care s-a oprit cautarea 
	 * Tot aici, exista un subcaz: daca prefixul nu se potriveste
	 * in intregime cu cheia curenta, atunci nu se poate trece mai departe,
	 * deci prefixul cautat nu a fost gasit.
	 * 
	 * @param prefix - prefixul pe care il caut
	 * @param current - nodul in care ma aflu la un moment dat
	 * @return nodul in care se termina prefixul cautat
	 */
	Node search (Node current, String prefix) {
		Node found = null;
		
		int noOfMatches = current.matches(prefix);		
		
		if (noOfMatches == prefix.length() && 
			noOfMatches <= current.getKey().length()) {
	            found = current;
		}
		
		else if (noOfMatches < prefix.length() &&
				prefix.length() >= current.getKey().length()) {
			
			if(noOfMatches < current.getKey().length()){
				return found;
			}
			
			
			int ok = 0;
			
			String Prefix = prefix.substring(noOfMatches);
			
			for (int i = 0; i < current.getChildren().size(); i++) {	            	
	            	/*daca vreunul din copii incepe cu litera care
					se potriveste, continui sa caut acolo*/
	            	Node aux =  current.getChildren().get(i);
	            	
				if (aux.getKey().substring(0,1).equals(Prefix.substring(0,1))){
	                    found = search(aux, Prefix);
	                    ok = 1;
	                    break;
				}
				
				if (ok != 0){
					return found;
				}
			}
		}

		return found;	        
	}	 
	
	/**
	 * Functia construieste mai intai o lista cu valorile copiilor nodului
	in care se termina prefixul dat, apoi formeaza o lista cu pozitiile
	decupate din valorile listei anterior construita.
	 * 
	 * @param found - nodul in care se termina prefixul dat
	 * @return lista de pozitii ale cuvintelor care incep cu prefixul dat
	 */
	LinkedList<String> getPositions(Node found) {
		//folosesc BFS pentru a parcurge nodurile
		//fac o lista cu copiii nodului in care am gasit prefixul
		Queue<Node> queue = new LinkedList<Node>();
		
		LinkedList<String> l = new LinkedList<String>();
		
		//daca nu am gasit  prefixul, returnez o lista goala
		if (found == null)
			return l;
		
		/*daca nodul in care am gasit prefixul contine o valoare 
		atunci il adaug in lista*/
		if (found.getValue() != null)
			l.add(found.getValue());
		
		queue.addAll(found.getChildren());
		
		while (!queue.isEmpty()) {
			Node aux = queue.remove();
			
			if (aux.value != null) {
				l.add(aux.getValue());
			}
			
			queue.addAll(aux.getChildren());
		}
	    
		//decupez pozitiile cuvintelor pe diverse cazuri
		
		LinkedList<String> ll = new LinkedList<String>();
		
		for(int i = 0; i < l.size() ; i++){
			LinkedList<Integer> v = new LinkedList<Integer>();
			
			char[] s = l.get(i).toCharArray();
			
			/*identific pozitiile caracterului '*' din cadrul unei valori
			si le pastrez intr-o lista*/
			for(int j = 0; j < s.length; j++) {
				if (s[j] == '*')
					v.add(j);	        			
	        }
	        /*in cazul in care cuvantul apare o singura data in text
			extrag pozitia si o adaug in lista de pozitii pe care o voi returna
			la finalul functiei*/
	        if (v.size() == 1) {
	        	ll.add(l.get(i).substring(l.get(i).indexOf('*') + 1));
	        }
	        /*in cazul in care cuvantul apare de doua ori in text
			extrag pozitiile si le adaug in lista de pozitii
	        pe care o voi returna la finalul functiei*/
	        else if (v.size() == 2) {
	        	int pos1 = l.get(i).indexOf('*');
	        	int pos2 = l.get(i).lastIndexOf('*');
	        	
	        	ll.add(l.get(i).substring(pos1+1,pos2));
	        	ll.add(l.get(i).substring(l.get(i).lastIndexOf('*')+1));
	        }
	        
	        else{
	        	/*daca un cuvant apare de mai mult de 2 ori in text
	        	extrag pozitiile cu ajutorul listei pozitiilor caracterului '*'
	        	indexul apare intre 2 caractere '*' consecutive*/
	        	for(int k = 0; k < v.size()-1; k++){
	        		int p = l.get(i).indexOf('*', v.get(k)+1);
	        		
	        		ll.add(l.get(i).substring(v.get(k)+1,p));	        		
	         	}	        
	        	//caz separat pentru ultimul index
	        	ll.add(l.get(i).substring(l.get(i).lastIndexOf('*')+1));
	        }
		}
	        return ll;	        
	}
	
	/**
	 * Functia afiseaza pe aceeasi linie numarul de aparitii al unui cuvant,
	 * precum si pozitiile pe care acesta apare separate prin spatiu.
	 * 
	 * @param found - nodul in care se termina prefixul dat
	 * @param prefix - prefixul dat
	 */
	
	private void getIndexes(Node found, String prefix){
		Node n = this.search(this.root, prefix);
		//daca un prefix nu apare in text, afisez caracterul '0'
		if (this.getPositions(n).size() == 0) {
			System.out.println("0");
		}
		
		if (this.getPositions(n).size() != 0) {
			LinkedList<String> l = new LinkedList<String>();
			
			l = this.getPositions(n);
			
			System.out.print(l.size() + " ");
			
			for (int i = 0; i< l.size(); i++) {
				System.out.print(l.get(i) + " ");
			}
					
			System.out.println();					
		}
	}
	
	/**
	 * Functia incapsuleaza functia cu acelasi nume care este "private".
	 * @param prefix - prefixul cautat
	 */
		
	void getIndexes(String prefix){
		this.getIndexes(this.root, prefix);
	}
}


