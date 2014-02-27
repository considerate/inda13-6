import com.google.caliper.BeforeExperiment;
import com.google.caliper.Benchmark;
import com.google.caliper.api.Macrobenchmark;
import com.google.caliper.Param;

import com.google.caliper.runner.CaliperMain;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;


/**
 * Measures sorting on different distributions of integers.
 */
public final class BenchmarkQuicksort {

  @Param({"100000"}) private int length;

  /*@Param({"0","1","2","3"})*/ private int algorithm = 3;

  @Param private Distribution distribution;

  private int[] values;
  private int[] copy;
  private Quicksort sorter;
  private ParallelQuicksort parallelSorter;
  private ForkJoinPool pool;

  @BeforeExperiment void setUp() throws Exception {
    values = distribution.create(length);
    copy = new int[length];
    sorter = new Quicksort();
    parallelSorter = new ParallelQuicksort();
    pool = new ForkJoinPool();
  }

  //@Benchmark 
  void sortJavaArraysSort(int reps) {
    for (int i = 0; i < reps; i++) {
      System.arraycopy(values, 0, copy, 0, values.length);
      Arrays.sort(copy);
    }
  }

  @Benchmark 
  void quicksort(int reps) {
    int algorithm = this.algorithm;
    for (int i = 0; i < reps; i++) {
      System.arraycopy(values, 0, copy, 0, values.length);
      sorter.sort(copy, algorithm);
    }
  }

  @Macrobenchmark 
  void parallel_quicksort() {
    int algorithm = this.algorithm;
    System.arraycopy(values, 0, copy, 0, values.length);
    parallelSorter.sort(copy, algorithm, pool);
  }

  public enum Distribution {
    SAWTOOTH {
      @Override
      int[] create(int length) {
        int[] result = new int[length];
        for (int i = 0; i < length; i += 5) {
          result[i] = 0;
          result[i + 1] = 1;
          result[i + 2] = 2;
          result[i + 3] = 3;
          result[i + 4] = 4;
        }
        return result;
      }
    },
    INCREASING {
      @Override
      int[] create(int length) {
        int[] result = new int[length];
        for (int i = 0; i < length; i++) {
          result[i] = i;
        }
        return result;
      }
    },
    DECREASING {
      @Override
      int[] create(int length) {
        int[] result = new int[length];
        for (int i = 0; i < length; i++) {
          result[i] = length - i;
        }
        return result;
      }
    },
    RANDOM {
      @Override
      int[] create(int length) {
        Random random = new Random();
        int[] result = new int[length];
        for (int i = 0; i < length; i++) {
          result[i] = random.nextInt();
        }
        return result;
      }
    },
    SAME {
      @Override
      int[] create(int length) {
        Random random = new Random();
        int[] result = new int[length];
        int value = random.nextInt();
        for (int i = 0; i < length; i++) {
          result[i] = value;
        }
        return result;
      }
    };

    abstract int[] create(int length);
  }

  public static void main(String[] args) throws Exception {
    CaliperMain.main(BenchmarkQuicksort.class, args);
  }
}