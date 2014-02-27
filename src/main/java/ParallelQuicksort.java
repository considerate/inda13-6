import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.Arrays;

public class ParallelQuicksort  {
	private Quicksort sorter = new Quicksort();

	public void sort(int[] array) {
		sort(array, Quicksort.PivotAlgorithms.MEDIAN9, new ForkJoinPool());
	}

	public void sort(int[] array, int pivotAlgorithm, ForkJoinPool pool) {
		if(sorter.handleBasicCases(array)) {
			return;
		}
		pool.invoke(new SortAction(array, 0, array.length - 1, pivotAlgorithm));
	}

	private class SortAction extends RecursiveAction {
		private static final int PARALLEL_THRESHOLD = 1000;
		private int[] array;
        private int first;
        private int last;
        private int pivotAlgorithm;

        private SortAction(int[] array, int first, int last, int pivotAlgorithm) {
            this.array = array;
            this.first = first;
            this.last = last;
            this.pivotAlgorithm = pivotAlgorithm;
        }

        @Override
        protected void compute() {
            if (this.first >= this.last) {
            	return;
            }
            int[] array = this.array;
            int first = this.first;
            int last = this.last;
            int[] range = new int[2];
            sorter.partition(array, first, last, range);
            int low = range[0];
            int high = range[1];
            if (last - first < PARALLEL_THRESHOLD) {
                sorter.sortRange(array, first, low - 1);
                sorter.sortRange(array, high + 1, last);
            } else {
            	//Recursively and parallely sort the two top and bottom ranges.
                invokeAll(new SortAction(array, first, low - 1, pivotAlgorithm),
                          new SortAction(array, high + 1, last, pivotAlgorithm));
            }
        }
	}
}