
public class SortProject {
	static final int size = 100000;
	static int[] randomList;
	static int maxLevel;
	
	public static void main(String[] args) throws InterruptedException {
		int[] list;
		long runTime;
		
		randomList = fillArray(size);
		
		// Run selection sort
		list = copy(randomList);
		runTime = System.currentTimeMillis();
		selectionSort(list);
		runTime = System.currentTimeMillis()-runTime;
		System.out.println("Selection Sort Run time: "+runTime);
		
		// Run threaded selection sort
		list = copy(randomList);
		runTime = System.currentTimeMillis();
		halfSelectionSort(list);
		runTime = System.currentTimeMillis()-runTime;
		System.out.println("Half Selection Sort Run time: "+runTime);
		
		// Run merge sort
		list = copy(randomList);
		runTime = System.currentTimeMillis();
		mergeSort(list, 1);
		runTime = System.currentTimeMillis()-runTime;
		System.out.println("Merge Sort Max level: " + maxLevel);
		System.out.println("Merge Sort Run time: "+runTime);
		
		// Run bubble sort
		list = copy(randomList);
		runTime = System.currentTimeMillis();
		bubbleSort(list);
		runTime = System.currentTimeMillis()-runTime;
		System.out.println("Bubble Sort Run time: "+runTime);
		
		// Run insertion sort
		list = copy(randomList);
		runTime = System.currentTimeMillis();
		list = insertionSort(list);
		runTime = System.currentTimeMillis()-runTime;
		System.out.println("Insertion Sort Run time: "+runTime);
	}
	
	public static int[] fillArray(int size) {
		int[] list = new int[size];
		int i;
		for (i=0; i<size; i++)
			list[i] = (int)(Math.random()*10000)+1;
		return list;
	}
	
	public static int[] copy(int[] list) {
		int i;
		int[] list2 = new int[list.length];
		for (i=0; i<list.length; i++)
			list2[i] = list[i];
		return list2;
	}
	
	public static void printList(int[] list, int n) {
		int i;
		if (n<=0) n = list.length;
		if (n>list.length) n=list.length;
		for (i=0; i<n; i++)
			System.out.println(""+i+": "+list[i]);
	}

	public static void printList(int[] list) {
		printList(list, 0);
	}
	
	public static void selectionSort(int [] nums) {		
		for (int i = 0; i < nums.length-1; i++) {
			int min = i;
			
			for (int j = i+1; j < nums.length; j++) {				
				if (nums[j] < nums[min])
					min = j;
			}
			
			int temp = nums[i];
			nums[i] = nums[min];
			nums[min] = temp;
			
		}
	}
	
	public static void halfSelectionSort(int[] list) throws InterruptedException {
		int[] lista = new int[list.length/2];
		int[] listb = new int[list.length - lista.length];
		int a,b,c;
		for (a = 0; a < lista.length; a++)
			lista[a] = list[a];
		for (b = 0; b < listb.length; b++)
			listb[b] = list[lista.length+b];
		
		SelfSortThread thread1 = new SelfSortThread(lista);
		SelfSortThread thread2 = new SelfSortThread(listb);
		
		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();
		
		merge(lista, listb, list);
	}
	
	public static void bubbleSort(int [] nums) {
		int passes = 0;
		int i, t;

		boolean sorted = false;

		while (!sorted) {
		      sorted = true;
		      passes++;
		      
		      for (i=0; i<nums.length-passes; i++) {
		            if (nums[i] > nums[i+1])  {
		                  t = nums[i];
		                  nums[i] = nums[i+1];
		                  nums[i+1] = t;

		                  sorted = false;
		             }
		      }
		}
		
		System.out.println("Bubble Sort Passes: " + passes);
	}
	
	public static int[] insertionSort(int [] nums) {
		int [] orderedList = new int[nums.length];
		int size = 0, j;
		
		for (int i = 0; i < nums.length; i++)
		{
			int value = nums[i];
			for (j = size; j > 0; j--)
				if (value < orderedList[j-1]) {
					orderedList[j] = orderedList[j-1];
				} else break;
			
			orderedList[j] = value;
			size++;
		}
		
		return orderedList;
	}
	
	public static void merge(int[] lista, int[] listb, int[] list) {
		int a, b, c;
		a=b=c=0;
		
		while (a < lista.length && b < listb.length) {
			if (lista[a] <= listb[b])
				list[c++] = lista[a++];
			else
				list[c++] = listb[b++];
		}
		
		while (a < lista.length) {
			list[c++] = lista[a++];
		}
		while (b < listb.length) {
			list[c++] = listb[b++];
		}
		
	}
	
	public static void mergeSort(int[] list, int level) {
		if (level > maxLevel) maxLevel = level;
		if (list.length <= 1) return;
		
		int[] lista = new int[list.length / 2];
		int[] listb = new int[list.length - lista.length];
		
		int a,b;
		
		for (a=0; a<lista.length; a++)
			lista[a] = list[a];
		for (b=0; a<list.length; a++)
			listb[b++] = list[a];
		
		mergeSort(lista, level+1);
		mergeSort(listb, level+1);
		merge(lista,listb,list);
	}

}


