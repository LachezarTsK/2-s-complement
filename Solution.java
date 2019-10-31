import java.util.Scanner;

public class Solution {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int numberOfTestCases = scanner.nextInt();

    /**
    * Integer.MIX_VALUE <= min <= max <= Integer.MAX_VALUE
    */
    for (int i = 0; i < numberOfTestCases; i++) {
      int min = scanner.nextInt();
      int max = scanner.nextInt();
      long all_ones_in_range = calculate_all_ones_in_range(min, max);
      System.out.println(all_ones_in_range);
    }
    scanner.close();
  }

  private static long calculate_all_ones_in_range(int min, int max) {
    long result = 0;

    if (min < 0 && max < -1) {
      long ones_from_Min_to_Zero = onesInColumn_negativeIntegers(min);
      long ones_from_BelowMax_to_Zero = onesInColumn_negativeIntegers(max + 1);
      result = ones_from_Min_to_Zero - ones_from_BelowMax_to_Zero;
    } else if (min < 0 && (max == -1 || max == 0)) {
      long ones_from_Min_to_Zero = onesInColumn_negativeIntegers(min);
      result = ones_from_Min_to_Zero;
    } else if (min < 0 && max > 0) {
      long ones_from_Min_to_Zero = onesInColumn_negativeIntegers(min);
      long ones_from_Max_to_Zero = onesInColumn_positiveIntegers(max);
      result = ones_from_Min_to_Zero + ones_from_Max_to_Zero;
    } else if ((min == 0 || min == 1) && max > 0) {
      long ones_from_Max_to_Zero = onesInColumn_positiveIntegers(max);
      result = ones_from_Max_to_Zero;
    } else if (min > 1 && max > 0) {
      long ones_from_BelowMin_to_Zero = onesInColumn_positiveIntegers(min - 1);
      long ones_from_Max_to_Zero = onesInColumn_positiveIntegers(max);
      result = ones_from_Max_to_Zero - ones_from_BelowMin_to_Zero;
    }

    return result;
  }
  
   /** 
   * A SERIES OF POSITIVE CONSEQUTIVE INTEGERS FROM "1" TO "N".
   *
   * Finds the total occurence of "1s" in the binary representation of 
   * postive consequtive integers from "1" to "n".
   *
   * 1. Put on top of each other the binary representation of a series 
   *    of postive consequtive integers from "1" to "n". 
   *
   * 2. Add one row for the "0", thus having rows = n+1.
   *
   * 3. Counting from "0"-row, each column will contain itertating groups 
   *    of only "0s" and of only "1s". In case of positive integers, the first group is always 
   *    the group that contains only "0s".
   *
   * 3.1 If rows%(2xMath.pow(2, indexColumn))=0,
   *     i.e. rows%(Math.pow(2, indexColumn+1))=0, then each group of only "0s" 
   *     and that of only "1s" will contain Math.pow(2, indexColumn) members.
   *  
   * 3.2 If rows%(Math.pow(2, indexColumn+1))!=0, then in order to add
   *     the remainder of the last group of "1s", if any, it has to be taken into account that
   *     in each set (i.e. one group of only "0s" and one group of only "1s"), 
   *     the group of "0s" always preceeds that of "1s".  
   *
   * 4. Thus, iterating through the row of the binary representation of "n", 
   *    applying described procedure, will return the total occurence of "1s" 
   *    in the series of postive consequtive integers from "1" to "n". 
   *     
   * Example with 8-bit integers:
   *      0 =>  0000 0000
   *      1 =>  0000 0001
   *      2 =>  0000 0010
   *      3 =>  0000 0011
   *      4 =>  0000 0100
   *      5 =>  0000 0101
   *      6 =>  0000 0110
   *      7 =>  0000 0111
   *      8 =>  0000 1000
   *      9 =>  0000 1001
   *     10 =>  0000 1010
   * 
   * See file "Representing groups of 0s and 1s with colors" for better 
   * visualization of the described correlations.
   */
  private static long onesInColumn_positiveIntegers(int input) {

    long rows = (long) input + 1;
    int index_leadColumn = Integer.toBinaryString(input).length() - 1;
    long total_ones = 0;

    for (int i = index_leadColumn; i >= 0; i--) {
      long ones_in_sets = ((long) Math.pow(2, i)) * (rows / (long) (Math.pow(2, i + 1)));
      long ones_in_remainder = (rows % (long) (Math.pow(2, i + 1))) - (long) (Math.pow(2, i));

      if (ones_in_remainder < 0) {
        ones_in_remainder = 0;
      }

      total_ones += ones_in_sets + ones_in_remainder;
    }

    return total_ones;
  }

 /** 
   * A SERIES OF NEGATIVE CONSEQUTIVE INTEGERS FROM "-1" TO "-N".
   * 
   * Finds the total occurence of "1s" in the binary representation of 
   * negative consequtive integers from "-1" to "-n".
   *   
   * The procedure is similar to that for a series of positive consequtive integers.
   * However, the following has to be considered:
   *
   * 1. rows = Math.abs(-n), i.e without the addition of "1" as for positive integers.
   *   
   * 2. Counting from "1"-row, again, each column will contain iterating groups 
   *    of only "1s" and of only "0s" but the first group is always the group 
   *    that contains only "1s". This has to be taken into account when calculating
   *    the remainder of "1s", if rows%(Math.pow(2, indexColumn+1))!=0. 
   *    Otherwise, the number of members per group is as described for a series of  
   *    positive integers.
   *
   * 3. Since all the columns that preceed the column of the leading "1" in "-n" consist of 
   *    only "1s", they all have to be added to the result obtained as per the described 
   *    procedure.
   *
   * Example with 8-bit integers:
   *     -1 =>  1111 1111
   *     -2 =>  1111 1110
   *     -3 =>  1111 1101
   *     -4 =>  1111 1100
   *     -5 =>  1111 1011
   *     -6 =>  1111 1010
   *     -7 =>  1111 1001
   *     -8 =>  1111 1000
   *     -9 =>  1111 0111
   *    -10 =>  1111 0110
   *   
   * See file "Representing groups of 0s and 1s with colors" for better 
   * visualization of the described correlations.
   */
  private static long onesInColumn_negativeIntegers(int input) {

    long rows = Math.abs((long) input);
    int index_leadColumn = Integer.toBinaryString(input).length() - 1;
    long total_ones = 0;

    for (int i = index_leadColumn; i >= 0; i--) {
      long ones_in_sets = ((long) Math.pow(2, i)) * (rows / (long) (Math.pow(2, i + 1)));
      long ones_in_remainder = rows % (long) (Math.pow(2, i + 1));

      if (ones_in_remainder > ((long) (Math.pow(2, i)))) {
        ones_in_remainder = (long) (Math.pow(2, i));
      }

      total_ones += ones_in_sets + ones_in_remainder;
    }

    total_ones += rows * (32 - (index_leadColumn + 1));

    return total_ones;
  }
}
