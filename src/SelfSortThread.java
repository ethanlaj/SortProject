
public class SelfSortThread extends Thread{
	int[] list;
	public SelfSortThread(int[] data) {
		list = data;
	}
	
	public void run() {
		SortProject.selectionSort(list);
	}
}
