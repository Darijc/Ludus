package bxt.unilectures.algorithmenunddatenstrukturen.fun.sorting;

/**
 * Quicksort
 * @author Bernhard Häussner
 */
public class QuickSort implements Sort {

	/**
	 * Merge sort is divide and conquer sorting by sorting two halves of 
	 * the unsorted list and then picking the smaller element from the 
	 * two. <p>Unfortunately it is usually not working in-place, so it is
	 * slightly awkwardly adapted here. 
	 */
	@Override
	public void sort(long[] a) {
		quickSort(a, 0, a.length-1);
	}

	/**
	 * The dividing in two halves is done here. 
	 * @param a Complete array to take parts of
	 * @param von Starting index
	 * @param bis Last index (not length)
	 */
	private void quickSort(long[] a, int von, int bis) {
		if(von<bis) {
			int mitte = partition(a,von,bis);
			quickSort(a, von, mitte-1);
			quickSort(a, mitte+1,bis);
		}
	}

	private int partition(long[] a, int von, int bis) {
		long pivot=a[bis];
		int i=von;
		for(int j=von;j<bis;j++) {
			if(a[j]<pivot) {
				long tmp=a[i];
				a[i]=a[j];
				a[j]=tmp;
				i++;
			}
		}
		long tmp=a[i];
		a[i]=a[bis];
		a[bis]=tmp;
		return i;
	}
		
}
