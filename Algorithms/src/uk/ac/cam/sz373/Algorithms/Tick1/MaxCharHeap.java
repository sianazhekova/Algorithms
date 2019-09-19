package uk.ac.cam.sz373.Algorithms.Tick1;
//import uk.ac.cam.rkh23.Algorithms.Tick1.MaxCharHeapInterface;
//import uk.ac.cam.rkh23.Algorithms.Tick1.EmptyHeapException;

public class MaxCharHeap implements MaxCharHeapInterface {
	
	private int maxHeapSize;
	private char[] myHeap;
	private int countLength;
	
	public MaxCharHeap(String s) {
		
			s = s.toLowerCase();
			maxHeapSize = 2*(s.toCharArray()).length;
			myHeap= new char[maxHeapSize];
			countLength = (s.toCharArray()).length;
			for(int i=0; i<(s.toCharArray()).length; i++){
				myHeap[i] = s.toCharArray()[i];
			}
			for(int i= countLength -1; i>=0; i--) {
				heapify(i);
			}
		}
	
	public char[] getHeap() {
		return myHeap;
	}
	

	public void insert(char i) {
		i = Character.toLowerCase(i);
		countLength++;
		if(checkFull()) {
			char[] newHeap = new char[maxHeapSize];
			for(int j=0; j<maxHeapSize; j++) {
				newHeap[j] = myHeap[j];
			}
			maxHeapSize = 2*maxHeapSize;
			myHeap = new char[maxHeapSize];
			for(int j=0; j<newHeap.length; j++) {
				myHeap[j] = newHeap[j];
			}
		}
		myHeap[countLength -1] = i;
		for(int j=countLength; j>=0; j--) {
			heapify(j);
		}
	}

	public void heapify(int i) {
		int left = 2*i+1;
		int right = 2*i+2;
		int maxOne = i;
		if(left > countLength-1 && right > countLength-1) {
			return;
		} else if(left < countLength && right >= countLength) {
			if(myHeap[left] > myHeap[maxOne]) {
				swap(left, maxOne);
				return;
			}
		} else if(left <= countLength-1 && right <= countLength-1) {
			if(myHeap[left] > myHeap[maxOne]) {
				maxOne = left;
			}
			if(myHeap[right] > myHeap[maxOne]) {
				maxOne = right;
			}
			if(maxOne == i) {
				return;
			}
			if(maxOne != i) {
				swap(i, maxOne);
				heapify(maxOne);
			}
		}		
	}
	
	public char getMax() throws EmptyHeapException {
		if(countLength==0) {
			throw new EmptyHeapException();
		}
		maxHeapSize--;
		char[] newHeap = new char[maxHeapSize];
		char max = myHeap[0];
		for(int i=1; i<=maxHeapSize; i++) {
			newHeap[i-1] = myHeap[i];
		}
		countLength--;
		myHeap = newHeap;
		for(int i=countLength -1; i>=0; i--) {
			heapify(i);
		}
		return max;
	}

	@Override
	public int getLength() {
		//Return the length of the heap
		return countLength;
	}
	
	private void swap(int i, int j) {
		char temp = myHeap[i];
		myHeap[i] = myHeap[j];
		myHeap[j] = temp;
	}
	
	public boolean checkFull () {
		if (countLength == maxHeapSize) {
			return true;
			}
		else {
			return false;
			}
	}
	public static void main(String[] args) throws EmptyHeapException {
		MaxCharHeap my = new MaxCharHeap("AbCdEfG");
		for (int i = 0; i< my.getLength(); i++) {System.out.print(my.getHeap()[i]);}
		System.out.println("");
		my.insert('F');
		my.insert('d');
		my.insert('G');
		
		for (int i = 0; i< my.getLength(); i++) {System.out.print(my.getHeap()[i]);}
		System.out.println(" ");
		System.out.println(my.getLength());
		MaxCharHeap m = new MaxCharHeap("");
		System.out.println(my.getMax());
		System.out.println(my.getLength());
		//my = new MaxCharHeap("Awb");
		for (int i = 0; i<9; i++) {System.out.println(my.getMax()+" remaining countlength: "+ my.getLength());}
		my.insert('h');
		my.insert('g');
		my.insert('f');
		MaxCharHeap my1 = new MaxCharHeap("hgf");
		System.out.print(my1.getMax());
	}

}
