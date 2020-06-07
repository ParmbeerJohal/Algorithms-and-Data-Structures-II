/**
 *
 * @author Rahnuma Islam Nishat
 * September 19, 2018
 * CSC 226 - Fall 2018
 */

/**
 *
 * Parm Johal
 * October 4, 2018
 * CSC 226 Programming Assignment 1
 * QuickSelect.java
 *
 */
import java.util.*;
import java.io.*;



/*
This class inputs a list of non-negative integers and outputs the kth value in the list.
The runtime is O(n).
*/
public class QuickSelect {

	//This method will pick the pivot using the median of medians approach.
	public static int PickPivot(int[] array) {

		//Creates an ArrayList
		ArrayList<int[]> subsequences = new ArrayList<int[]>(array.length / 7 + 1);

		/*
		The following loop iterates through (length - length mod 7) of the array and adds a subsequence
		to the ArrayList whenever the subsequence length becomes 7. Runs in O(n) time.
		*/
		int count = 0;
		while(count < array.length / 7 * 7) {
			int[] splitArr = new int[7];
			for(int j = 0; j < 7; j++) {
				splitArr[j] = array[count];
				count++;
			} //End for
			subsequences.add(splitArr);
		} //End while

		//This creates an array for the last (length mod 7) subsequence in the array
		int[] lastSS = new int[array.length % 7];
		for(int num = 0; num < lastSS.length; num++) {
			lastSS[num] = array[count];
			count++;
		} //End for
		subsequences.add(lastSS);

		//This sorts all of the subsequences
		for(int i = 0; i < subsequences.size(); i++) { // Sort each subsequence array(row) of the ArrayList.
			Arrays.sort(subsequences.get(i));
		} //End for

		int[] mmArr = new int[array.length / 7 + 1]; //Create a new array for the medians of each subsequence

		//Store the medians of each subsequence in array mmArr.
		for(int i = 0; i < mmArr.length; i++) {
			mmArr[i] = subsequences.get(i)[subsequences.get(i).length / 2];
		} //End for
		Arrays.sort(mmArr);


		int medPivot = mmArr[mmArr.length / 2];

		return medPivot;
	}


	//This method will partition the array into 2 with the pivot as the split.
	//one side less than the pivot and the other side greater than the pivot.
	public static ArrayList<int[]> Partition(int[] array, int pivot) {

		int left = 0;
		int right = array.length - 1;

		int leftMark = left;
		int rightMark = right;

		//This loop paritions the array around the median of medians pivot. Runs in O(n) time.
		while(true) {
			while(leftMark < right && array[leftMark] < pivot) {
				leftMark += 1;
			}
			while(rightMark > left && array[rightMark] > pivot) {
				rightMark -= 1;
			}

			if(leftMark >= rightMark) {
				break;
			} else {
				int temp = array[leftMark];
				array[leftMark] = array[rightMark];
				array[rightMark] = temp;
			} //End if

		} //End while

		int temp = array[left];
		array[left] = array[rightMark];
		array[rightMark] = temp;

		/*
		This swaps the pivot located at index 0 with the highest indexed value less than the pivot.
		Runs in O(n) time.
		*/
		int piv = array[0];
		for(int i = 1; i < array.length; i++) {
			if(array[i-1] > array[i]) {
				int temp2 = array[i-1];
				array[i-1] = array[i];
				array[i] = temp2;

			} else {break;} //End if
		} //End for

		/*
		This loop iterates the partitioned array to find the split in the numbers
		less than and greater than the pivot. Runs in O(n) time.
		*/
		int split = 0;
		for(int i = 0; i < array.length; i++) {
			if(array[i] == pivot) {
				split = i;
				break;
			}
		}

		/*
		This creates an ArrayList to store and return the less than
		and greater than array halves of the array.
		*/
		ArrayList<int[]> twoHalves = new ArrayList<int[]>();
		int[] less = new int[split];
		int[] greater = new int[array.length - split - 1];

		for(int i = 0; i < less.length; i++) {
			less[i] = array[i];
		} //End for
		for(int i = 0; i < greater.length; i++) {
			greater[i] = array[array.length - i - 1];
		} //End for

		twoHalves.add(less);
		twoHalves.add(greater);

		return twoHalves;
	}

    public static int QuickSelect(int[] A, int k) {

		//Checks if k is within the bounds of the array
		if(k > A.length || k < 1 || A.length == 0) {return -1;}

		//Checks if the length of the array is one and returns the only element in the array.
		if(A.length == 1) {return A[0];}

		int p = PickPivot(A);

		//For this step use an ArrayList in the partition method containing 2 arrays;
		//one array less than the pivot and the other array greater than the pivot
		ArrayList<int[]> partArrs = Partition(A, p);

		/*
		The following sequence of if statements checks whether the kth element is the pivot
		or either in the less than pivot or greater than pivot arrays.
		*/
		if(k <= partArrs.get(0).length) {
			return QuickSelect(partArrs.get(0), k); //If kth element is in L.
		} else if(k == partArrs.get(0).length + 1) {
			return p; //If kth element is the pivot.
		} else { //k is greater than Length(L) + 1
			return QuickSelect(partArrs.get(1), k - partArrs.get(0).length - 1);
		}
    }

    public static void main(String[] args) {


        Scanner s;
        int[] array;
        int k;
        if(args.length > 0) {
	    try{
		s = new Scanner(new File(args[0]));
		int n = s.nextInt();
		array = new int[n];
		for(int i = 0; i < n; i++){
		    array[i] = s.nextInt();
		}
	    } catch(java.io.FileNotFoundException e) {
		System.out.printf("Unable to open %s\n",args[0]);
		return;
	    }
	    System.out.printf("Reading input values from %s.\n", args[0]);
        }
	else {
	    s = new Scanner(System.in);
	    System.out.printf("Enter a list of non-negative integers. Enter a negative value to end the list.\n");
	    int temp = s.nextInt();
	    ArrayList<Integer> a = new ArrayList<>();
	    while(temp >= 0) {
		a.add(temp);
		temp=s.nextInt();
	    }
	    array = new int[a.size()];
	    for(int i = 0; i < a.size(); i++) {
		array[i]=a.get(i);
	    }

	    System.out.println("Enter k");
        }
        k = s.nextInt();
        System.out.println("The " + k + "th smallest number is the list is "
			   + QuickSelect(array,k));
    }
}
