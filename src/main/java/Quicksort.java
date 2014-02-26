import java.util.Arrays;
import java.util.Random;

public class Quicksort {

	private Random random = new Random();
	private static final int MAX_STACK_DEPTH = 5000;
	private static final int FASTEST_MIN_RANGE = 7;
	public static final class PivotAlgorithms {
		public static final int FIRST = 0;
		public static final int RANDOM = 1;
		public static final int MEDIAN3 = 2;
		public static final int MEDIAN9 = 3;
	}

	private static class Range {
		public int first;
		public int last;
		public Range(int first, int last) {
			this.first = first;
			this.last = last;
		}
	}

	public void sort(int[] array) {
		sort(array, PivotAlgorithms.MEDIAN9, FASTEST_MIN_RANGE);
	}

	private byte isSorted(int[] array) {
		byte prev = 0;
		if(array.length < 2) {
			return 1;
		}
		if(array[0] < array[1]) {
			prev = 1;
		} else if(array[0] > array[1]) {
			prev = -1;
		} else {
			return 0;
		}
		for(int i = 2; i < array.length; i++) {
			if(prev == 1) {
				if(array[i-1] < array[i]) {
					continue;
				} else if(array[i-1] > array[i]) {
					return 0;
				} else {
					return 0;
				}
			} else {
				if(array[i-1] < array[i]) {
					return 0;
				} else if(array[i-1] > array[i]) {
					continue;
				} else {
					return 0;
				}			
			}
		}
		return prev;
	}

	private void reverse(int[] array) {
		int l = array.length-1;
		int n = l/2;
		for(int i = 0; i < n; i++) {
			swap(array, i, l-i);
		}
	}

	public void sort(int[] array, int pivotAlgorithm, int maxElements) {
		int first = 0;
		int last = array.length-1;
		byte sorted = isSorted(array);
		if(sorted != 0) {
			if(sorted == 1) {
				return;
			} else if(sorted == -1) {
				reverse(array);
				return;
			}
		}

		if(maxElements > 0) {
			switch(pivotAlgorithm) {
				case PivotAlgorithms.FIRST:
					qsortFirstIS(array,first,last, maxElements);
				break;
				case PivotAlgorithms.RANDOM:
					qsortRandomIS(array,first,last, maxElements);
				break;
				case PivotAlgorithms.MEDIAN3:
					qsortMedianIS(array,first,last, maxElements);
				break;
				case PivotAlgorithms.MEDIAN9:
					qsortMedian9IS(array,first,last, maxElements);
				break;
			}
		} else {
			switch(pivotAlgorithm) {
				case PivotAlgorithms.FIRST:
					qsortFirst(array,first,last);
				break;
				case PivotAlgorithms.RANDOM:
					qsortRandom(array,first,last);
				break;
				case PivotAlgorithms.MEDIAN3:
					qsortMedian(array,first,last);
				break;
				case PivotAlgorithms.MEDIAN9:
					qsortMedian9(array,first,last);
				break;
			}			
		}
	}

	void insertionsort(int[] array, int first, int last){
	    for (int i = first+1; i <= last; i++) {
		    for(int j = i ; j > 0 ; j--){
			    if(array[j] < array[j-1]){
			    	swap(array,j, j-1);
			    }
		    }
	    }
	}

	void qsortFirst(int[] array, int first, int last) {
		// Less than two elements
		if(first >= last) {
			return;
		}

	    // Choose a pivot element.
		int pivot = array[first]; 

		//Inline partitioning.
		int low = first;
		int high = last;
		int i = low;
		while(i <= high) {
			if(array[i] < pivot) {
				swap(array, i, low);
				i++;
				low++;
			} else if (array[i] > pivot) {
				swap(array, i, high);
				high--;
			} else {
				i++;
			}
		}

	    qsortFirst(array, first, low-1);
	    qsortFirst(array, high+1, last);		
	}

	void qsortRandom(int[] array, int first, int last) {
		// Less than two elements
		if(first >= last) {
			return;
		}

	    // Choose a pivot element.
		int index = random.nextInt(last-first); //get a random index for the pivot.
		int pivot = array[first+index]; 


		//Inline partitioning.
		int low = first;
		int high = last;
		int i = low;
		while(i <= high) {
			if(array[i] < pivot) {
				swap(array, i, low);
				i++;
				low++;
			} else if (array[i] > pivot) {
				swap(array, i, high);
				high--;
			} else {
				i++;
			}
		}
		
	    qsortRandom(array, first, low-1);
	    qsortRandom(array, high+1, last);		
	}

	void qsortMedian(int[] array, int first, int last) {
		// Less than two elements
		if(first >= last) {
			return;
		}

	    // Choose a pivot element.
		int mid = first+(last-first)/2;
		int a = array[first];
		int b = array[mid];
		int c = array[last];
		int median = Math.max(Math.min(a,b), Math.min(Math.max(a,b),c));
		int pivot = median; 


		//Inline partitioning.
		int low = first;
		int high = last;
		int i = low;
		while(i <= high) {
			if(array[i] < pivot) {
				swap(array, i, low);
				i++;
				low++;
			} else if (array[i] > pivot) {
				swap(array, i, high);
				high--;
			} else {
				i++;
			}
		}
		
	    qsortMedian(array, first, low-1);
	    qsortMedian(array, high+1, last);		
	}

	void qsortMedian9(int[] array, int first, int last) {
		// Less than two elements
		if(first >= last) {
			return;
		}
		if(last-first < 9) {
			qsortMedian(array, first, last);
			return;
		}

	    // Choose a pivot element.
	    int eighth = (last-first)/8;
	    int leftmidleft = first+eighth;
	    int leftmid = first+eighth*2;
	    int leftmidright = leftmid+eighth*3;
		int mid = first+eighth*4;
		int rightmidleft = first+eighth*5;
		int rightmid = first+eighth*6;
		int rightmidright = first+eighth*7;
		
		
		//first leftmidleft leftmid leftmidright mid rightmidleft rightmid rightmidright last;
		int a,b,c;
		a = array[first];
		b = array[leftmidleft];
		c = array[leftmid];
		int median1 = Math.max(Math.min(a,b), Math.min(Math.max(a,b),c));
		a = array[leftmidright];
		b = array[mid];
		c = array[rightmidleft];
		int median2 = Math.max(Math.min(a,b), Math.min(Math.max(a,b),c));
		a = array[rightmid];
		b = array[rightmidright];
		c = array[last];
		int median3 = Math.max(Math.min(a,b), Math.min(Math.max(a,b),c));	
		a = median1;
		b = median2;
		c = median3;
		int median = Math.max(Math.min(a,b), Math.min(Math.max(a,b),c));
		int pivot = median; 


		//Inline partitioning.
		int low = first;
		int high = last;
		int i = low;
		while(i <= high) {
			if(array[i] < pivot) {
				swap(array, i, low);
				i++;
				low++;
			} else if (array[i] > pivot) {
				swap(array, i, high);
				high--;
			} else {
				i++;
			}
		}
		
	    qsortMedian9(array, first, low-1);
	    qsortMedian9(array, high+1, last);		
	}

	void qsortFirstIS(int[] array, int first, int last, int maxElements) {
		// Less than two elements
		if(first >= last) {
			return;
		}

		if(last-first < maxElements) {
			insertionsort(array, first, last);
			return;
		}

	    // Choose a pivot element.
		int pivot = array[first]; 

		//Inline partitioning.
		int low = first;
		int high = last;
		int i = low;
		while(i <= high) {
			if(array[i] < pivot) {
				swap(array, i, low);
				i++;
				low++;
			} else if (array[i] > pivot) {
				swap(array, i, high);
				high--;
			} else {
				i++;
			}
		}
		
	    qsortFirstIS(array, first, low-1, maxElements);
	    qsortFirstIS(array, high+1, last, maxElements);		
	}

	void qsortRandomIS(int[] array, int first, int last, int maxElements) {
		// Less than two elements
		if(first >= last) {
			return;
		}

		if(last-first < maxElements) {
			insertionsort(array, first, last);
			return;
		}

	    // Choose a pivot element.
		int index = random.nextInt(last-first); //get a random index for the pivot.
		int pivot = array[first+index]; 


		//Inline partitioning.
		int low = first;
		int high = last;
		int i = low;
		while(i <= high) {
			if(array[i] < pivot) {
				swap(array, i, low);
				i++;
				low++;
			} else if (array[i] > pivot) {
				swap(array, i, high);
				high--;
			} else {
				i++;
			}
		}
		
	    qsortRandomIS(array, first, low-1, maxElements);
	    qsortRandomIS(array, high+1, last, maxElements);		
	}

	void qsortMedianIS(int[] array, int first, int last, int maxElements) {
		// Less than two elements
		if(first >= last) {
			return;
		}

		if(last-first < maxElements) {
			insertionsort(array, first, last);
			return;
		}

	    // Choose a pivot element.
		int mid = first+(last-first)/2;
		int a = array[first];
		int b = array[mid];
		int c = array[last];
		int median = Math.max(Math.min(a,b), Math.min(Math.max(a,b),c));
		int pivot = median; 


		//Inline partitioning.
		int low = first;
		int high = last;
		int i = low;
		while(i <= high) {
			if(array[i] < pivot) {
				swap(array, i, low);
				i++;
				low++;
			} else if (array[i] > pivot) {
				swap(array, i, high);
				high--;
			} else {
				i++;
			}
		}
		
	    qsortMedianIS(array, first, low-1, maxElements);
	    qsortMedianIS(array, high+1, last, maxElements);		
	}

	void qsortMedian9IS(int[] array, int first, int last, int maxElements) {
		// Less than two elements
		if(first >= last) {
			return;
		}
		if(last-first < maxElements) {
			insertionsort(array, first, last);
			return;
		}
		if(last-first < 9) {
			qsortMedian(array, first, last);
			return;
		}

	    // Choose a pivot element.
	    int eighth = (last-first)/8;
	    int leftmidleft = first+eighth;
	    int leftmid = first+eighth*2;
	    int leftmidright = leftmid+eighth*3;
		int mid = first+eighth*4;
		int rightmidleft = first+eighth*5;
		int rightmid = first+eighth*6;
		int rightmidright = first+eighth*7;
		
		
		//first leftmidleft leftmid leftmidright mid rightmidleft rightmid rightmidright last;
		int a,b,c;
		a = array[first];
		b = array[leftmidleft];
		c = array[leftmid];
		int median1 = Math.max(Math.min(a,b), Math.min(Math.max(a,b),c));
		a = array[leftmidright];
		b = array[mid];
		c = array[rightmidleft];
		int median2 = Math.max(Math.min(a,b), Math.min(Math.max(a,b),c));
		a = array[rightmid];
		b = array[rightmidright];
		c = array[last];
		int median3 = Math.max(Math.min(a,b), Math.min(Math.max(a,b),c));	
		a = median1;
		b = median2;
		c = median3;
		int median = Math.max(Math.min(a,b), Math.min(Math.max(a,b),c));
		int pivot = median; 


		//Inline partitioning.
		int low = first;
		int high = last;
		int i = low;
		while(i <= high) {
			if(array[i] < pivot) {
				swap(array, i, low);
				i++;
				low++;
			} else if (array[i] > pivot) {
				swap(array, i, high);
				high--;
			} else {
				i++;
			}
		}
		
	    qsortMedian9IS(array, first, low-1, maxElements);
	    qsortMedian9IS(array, high+1, last, maxElements);		
	}
	/*

	void qsortRandomIS(int[] array, int first, int last, int maxElements) {
		// Less than two elements
		if(first >= last) {
			return;
		}
		//Small array, fall back to faster for low n values insertion sort.
	    if (last-first < maxElements) {
	    	insertionsort(array, first, last);
	        return;
	    }

	    // Choose a pivot element.
	    int count = last-first;
		int index = random.nextInt(count); //get a random index for the pivot.
		int pivot = array[first+index]; 


		//Inline partitioning.
		int low = first;
		int high = last;
		int i = low;
		while(i <= high) {
			if(array[i] < pivot) {
				swap(array, i, low);
				i++;
				low++;
			} else if (array[i] > pivot) {
				swap(array, i, high);
				high--;
			} else {
				i++;
			}
		}
		
	    qsortRandomIS(array, first, low-1, maxElements);
	    qsortRandomIS(array, high+1, last, maxElements);
	}*/


	// Sorts the elements of the subvector v[first..last].
	void qsort(int[] array, int first, int last) {
		// Less than two elements
		if(first >= last) {
			return;
		}
		//Small array, fall back to faster for low n values insertion sort.
	    if (last-first < 20) {
	    	insertionsort(array, first, last);
	        return;
	    }

	    // Choose a pivot element.
	    int count = last-first;
		int index = random.nextInt(count); //get a random index for the pivot.
		int pivot = array[first+index]; 

		int[] range = new int[2]; 
		partition(array, first, last, pivot, range);
		int low = range[0];
		int high = range[1];
	    qsort(array, first, low-1);
	    qsort(array, high+1, last);
	}

	/**
	 * Reorders the elements of the subarray array[first..last] so that
	 * all elements in array[first..low-1] are less than pivot,
	 * all elements in array[low..high] are equal to pivot,
	 * all elements in array[high+1..last] are greater than pivot.
	 * 
	 * Precondition: first &lt; last.
	 */
	private void partition(int[] array, int first, int last, int pivot, int[] range) {
		int low = first;
		int high = last;
		int i = low;
		while(i <= high) {
			if(array[i] < pivot) {
				swap(array, i, low);
				i++;
				low++;
			} else if (array[i] > pivot) {
				swap(array, i, high);
				high--;
			} else {
				i++;
			}
		}
		range[0] = low;
		range[1] = high;
	}

	public void swap(int[] array, int a, int b) {
		int a0 = array[a];
		array[a] = array[b];
		array[b] = a0; 
	}
}