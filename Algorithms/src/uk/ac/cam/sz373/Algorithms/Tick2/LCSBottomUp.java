package uk.ac.cam.sz373.Algorithms.Tick2;
//import uk.ac.cam.rkh23.Algorithms.Tick2.LCSFinder;
public class LCSBottomUp extends LCSFinder {

	public LCSBottomUp(String s1, String s2) {
		super(s1, s2);
	
	}

	//Compute the full table of LCS lengths and return the final LCS length. 
	//Note that this function should not compute the actual LCS. 
	//The table of length solutions should be stored in the mTable array.
	/**
	 * Computes the LCS length table (i.e. fills
	 * the member mTable)
	 * @return the length of the LCS solution
	 */
	@Override
	public int getLCSLength(){
        if (mString1.equals("") || mString2.equals("")) {
                return 0;
        }
        int s1 = mString1.length();
        int s2 = mString2.length();
        mTable = new int[s1][s2];
        for (int i = 0; i < s1; i++) {
            for (int j = 0; j < s2; j++) {
                if (mString1.charAt(i) == mString2.charAt(j)) {
                    if (i == 0 || j == 0) {
                    	
                        mTable[i][j] = 1;
                        
                    }
                    else mTable[i][j] = mTable[i - 1][j - 1] + 1;

                }
                else {
                    if (i != 0) {
                        mTable[i][j] = max(mTable[i][j], mTable[i - 1][j]);
                    }
                    if (j != 0) {
                        mTable[i][j] = max(mTable[i][j], mTable[i][j - 1]);
                    }
                }
            }
        }
        return mTable[s1 - 1][s2 - 1];
    }
	// Define a helper function
	int max(int a, int b) 
	  { 
	    return (a > b)? a : b; 
	  }
	
	/*Use the table of solutions in mTable to compute the actual LCS as a string.
	  If either input string is empty, 
	  your solution should return zero for getLCSLength() and "" for getLCSString(), 
	  and mTable should be null.  */
	/**
	 * Computes the LCS as a string by analysing 
	 * mTable
	 * @return The LCS string
	 */
	@Override
	public String getLCSString() {
		
		int m = mString1.length();
        int n = mString2.length();
		int[][] myTable = new int[m+1][n+1]; 
        for (int i=0; i<=m; i++) 
        { 
            for (int j=0; j<=n; j++) 
            { 
                if (i == 0 || j == 0) 
                    myTable[i][j] = 0; 
                else if (mString1.charAt(i-1) == mString2.charAt(j-1)) 
                    myTable[i][j] = myTable[i-1][j-1] + 1; 
                else
                    myTable[i][j] = Math.max(myTable[i-1][j], myTable[i][j-1]); 
            } 
        }
        int index = myTable[m][n];
        // Create an array of chars to store the largest common substring
        char[] strA = new char[index];

        int i = m, j = n; 
        while (i > 0 && j > 0) 
        {
            if (mString1.charAt(i-1) == mString2.charAt(j-1)) 
            {   //Place current char in the output char array 
                strA[index-1] = mString1.charAt(i-1);
                i--;  
                j--;  
                index--;      
            }
            else if (myTable[i-1][j] > myTable[i][j-1]) { 
                i--; 
            }
            else {
                j--; }
        } // Convert output char array to String to fit in required function return type 
		String output = new String(strA);
		return output;
	}
	public static void main(String[] args) {
		LCSBottomUp test = new LCSBottomUp("abbsaiu", "asayyyu");
		System.out.println(test.getLCSString());
	}
	
	

}
