import java.util.ArrayList;
import java.util.Random;


public class Sort {
	
	/**
	 * Implement insertion sort.
	 * 
	 * It should be:
	 *  inplace
	 *  stable
	 *  
	 * Have a worst case running time of:
	 *  O(n^2)
	 *  
	 * And a best case running time of:
	 *  O(n)
	 * 
	 * @param arr
	 */
	public static <T extends Comparable<T>> void insertionsort(T[] arr) {
		// TODO Auto-generated method stub
		for (int i = 1; i<arr.length; i++){
			T temp = arr[i];
			int j;
			for (j = i-1; j>=0 && temp.compareTo(arr[j]) < 0; j-- ){
				arr[j+1] = arr[j];
			}
			arr[j+1] = temp;
		}
	}
	
	/**
	 * Implement quick sort. 
	 * 
	 * Use the provided random object to select your pivots.
	 * For example if you need a pivot between a (inclusive)
	 * and b (exclusive) where b > a, use the following code:
	 * 
	 * int pivotIndex = r.nextInt(b - a) + a;
	 * 
	 * It should be:
	 *  inplace
	 *  
	 * Have a worst case running time of:
	 *  O(n^2)
	 *  
	 * And a best case running time of:
	 *  O(n log n)
	 * 
	 * @param arr
	 */
	public static <T extends Comparable<T>> void quicksort(T[] arr, Random r) {
		// TODO Auto-generated method stub

		quicksorthelper(arr, 0, arr.length-1, r);
		
	}
	
    private static <T> void quicksorthelper(T[] arr, int left, int right, Random r){
         if (left < right)
         {
              int pivot = randomizedPartition(arr, left, right, r);
              quicksorthelper(arr, left, pivot, r);
              quicksorthelper(arr, pivot + 1, right, r);
         }
    }
    private static <T> int randomizedPartition(T[] arr, int left, int right, Random r)
    {
         int swapIndex = left + r.nextInt(right - left) + 1;
         swap(arr, left, swapIndex);
         return partition(arr, left, right);
    }

    private static <T> int partition(T[] arr, int left, int right)
    {
         T pivot = arr[left];
         int i = left - 1;
         int j = right + 1;
         while (true)
         {
              do
                   j--;
              while (((Comparable)arr[j]).compareTo(pivot)>0);

              do
                   i++;
              while (((Comparable)arr[i]).compareTo(pivot)<0);

              if (i < j)
                   swap(arr, i, j);
              else
                   return j;
         }
    }

    private static <T> void swap(T[] arr, int i, int j)
    {
         T temp = arr[i];
         arr[i] = arr[j];
         arr[j] = temp;
    }
	
	
	
	/**
	 * Implement merge sort.
	 * 
	 * It should be:
	 *  stable
	 *  
	 * Have a worst case running time of:
	 *  O(n log n)
	 *  
	 * And a best case running time of:
	 *  O(n log n)
	 *  
	 * @param arr
	 * @return
	 */
	public static <T extends Comparable<T>> T[] mergesort(T[] arr) {
		// TODO Auto-generated method stub
		if (arr.length > 1){
			int half = arr.length/2;
			T[] left = (T[])new Comparable[half];
			T[] right =(T[])new Comparable[arr.length-half];;
			System.arraycopy(arr, 0, left, 0, half);
			System.arraycopy(arr, half, right, 0, arr.length-half);
			T[] newLeft = mergesort(left);
			T[] newRight = mergesort(right);
			T[] sorted = merge(arr, newLeft, newRight);
			return sorted;
		}
		else return arr;
		
	}
	
	private static <T> T[] merge(T[] arr, T[] left, T[] right){
		int total = left.length + right.length;
		int i=0, leftIndex=0, rightIndex=0;
		while(i<total){
			if (leftIndex<left.length && rightIndex< right.length){
				if(((Comparable)left[leftIndex]).compareTo(right[rightIndex]) <= 0){
					arr[i] = left[leftIndex];
					i++;
					leftIndex++;
				}
				else {
					arr[i] = right[rightIndex];
					i++;
					rightIndex++;
				}
			}
			else {
				if (leftIndex >= left.length) {
                    while (rightIndex < right.length) {
                        arr[i] = right[rightIndex];
                        i++;
                        rightIndex++;
                    }
                }
                if (rightIndex >= right.length) {
                    while (leftIndex < left.length) {
                        arr[i] = left[leftIndex];
                        leftIndex++;
                        i++;
                    }
                }
			}
		}
		return arr;
		
	}
	
	/**
	 * Implement radix sort
	 * 
	 * Hint: You can use Integer.toString to get a string
	 * of the digits. Don't forget to account for negative
	 * integers, they will have a '-' at the front of the
	 * string.
	 * 
	 * It should be:
	 *  stable
	 *  
	 * Have a worst case running time of:
	 *  O(kn)
	 *  
	 * And a best case running time of:
	 *  O(kn)
	 * 
	 * @param arr
	 * @return
	 */
	public static int[] radixsort(int[] arr) {
		// TODO Auto-generated method stub
		
		int offset = findOffset(arr);
		for (int i = 0; i < arr.length; i++){
			arr[i] = arr[i] - offset;
		}
		int maxDigits = findMaxDigits(arr);
		
		
        int tenponent = 1;
        for(int i =0; i < maxDigits; i++){
            ArrayList<Integer>[] buckets = new ArrayList[10];
            for(int j=0; j < 10; j++){
                buckets[j] = new ArrayList();
            }
            for(int k =0; k < arr.length; k++){
                int number = (arr[k]/tenponent)%10;
                buckets[number].add(arr[k]);
            }
            tenponent *= 10;
            int index =0;         
            
            for(int l=0; l < 10; l++){
                for(int num: buckets[l]){
                    arr[index] = num;
                    index++;
                }
            }
        }
		for (int i = 0; i < arr.length; i++){
			arr[i] = arr[i] + offset;
		}
		return arr;
	}
	
	//finds the smallest negative number.  All #s will be added/subtracted from the array to bypass negative # sorting situations
	private static int findOffset(int arr[]){
		int j = 0;
		for (int i = 0; i < arr.length; i++){
			if (arr[i]<j){
				j = arr[i];
			}
		}
		return j;
	}
	
	//finds the largest number of digits.
	private static int findMaxDigits(int arr[]){
		String largest ="";
		int length = largest.length();
		for (int i = 0; i < arr.length; i++){
			if (length < Integer.toString(arr[i]).length()){
				largest = Integer.toString(arr[i]);
				length = largest.length();
			}
		}
		return length;
	}


}