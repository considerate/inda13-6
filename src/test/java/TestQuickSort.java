import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Before;
import org.junit.After;

import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Random;

@RunWith(JUnit4.class)
public class TestQuickSort {
	private Quicksort sorter;
	private Random random = new Random();

	@Before
	public void setUp() {
		sorter = new Quicksort();
	}

	@After
	public void tearDown() {
		sorter = null;
	}

	@Test
	public void testSort() {
		int[] array = {0, 5, 6, 8, 7, 1, 11, 100, 2}; 
		sorter.sort(array);
		int[] expected = {0, 1, 2, 5, 6, 7, 8, 11, 100};
		System.out.println(Arrays.toString(array));
		assertEquals(true, Arrays.equals(expected, array));
	}

	@Test
	public void testSort2() {
		int[] array = {0, 5, 6, 8, 7, 1, 11, 100, 2}; 
		sorter.sort(array);
		System.out.println(Arrays.toString(array));
		assertEquals(true, isSorted(array));
	}
	
	@Test
	public void testSortRandom() {
		for(int count = 100; count < 10000; count += random.nextInt(1000)) {
			int[] array = new int[count];
			
			for(int i = 0; i < count; i++) {
				array[i] = random.nextInt();
			}
			sorter.sort(array);
			boolean sorted = isSorted(array);
			if(!sorted) {
				System.out.println(Arrays.toString(array));
			}
			assertEquals(true, sorted);
		}
	}
	
	//@Test
	public void testSortPermutations() {
		for(int count = 100; count < 10000; count += random.nextInt(1000)) {
			int[] array = new int[count];
			
			for(int i = 0; i < count; i++) {
				array[i] = random.nextInt();
			}
			for(int j = 0; j < count; j++) {
				for(int i = 0; i < count; i++) {
					if(i != j) {
						sorter.swap(array,i, j);
					}
					sorter.sort(array);
					assertEquals(true, isSorted(array));			
				}
			}
		}
	}

	private boolean isSorted(int[] array) {
		boolean sorted = true;
		for(int i = 0; i < array.length-1; i++) {
			sorted = sorted && array[i] <= array[i+1];
			if(!sorted) {
				System.out.println(array[i] +" >" +array[i+1]);
				return false;
			}
		}
		return sorted;
	}

}