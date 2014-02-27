import com.google.caliper.BeforeExperiment;
import com.google.caliper.Benchmark;
import com.google.caliper.Param;

import com.google.caliper.runner.CaliperMain;

import java.util.Arrays;
import java.util.Random;


/**
 * Measures sorting on different distributions of integers.
 */
public final class FindMinRange {

  @Param({"10000"}) private int length;
  @Param({
    "0",
    "1",
    "2",
    "3",
    "4",
    "5",
    "6",
    "7",
    "8",
    "9",
    "10",
    "11",
    "12",
    "13",
    "14",
    "15",
    "16",
    "17",
    "18",
    "19",
    "20"
  }) private int minRange;
  private int algorithm = 1;

  private Distribution distribution = Distribution.RANDOM;

  private int[] values;
  private int[] copy;
  private Quicksort sorter;

  @BeforeExperiment void setUp() throws Exception {
    values = distribution.create(length);
    copy = new int[length];
    sorter = new Quicksort();
  }

  @Benchmark void qsortInsertion(int reps) {
    int minRange = this.minRange;
    int algorithm = this.algorithm;
    for (int i = 0; i < reps; i++) {
      System.arraycopy(values, 0, copy, 0, values.length);
      sorter.sort(copy, algorithm, minRange);
    }
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
    };

    abstract int[] create(int length);
  }

  public static void main(String[] args) throws Exception {
    CaliperMain.main(FindMinRange.class, args);
  }
}