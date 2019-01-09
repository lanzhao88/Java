
/* http://train.usaco.org/usacogate  */
/* Use the slash-star style comments or the system won't see your
   identification information */
/*
ID: lanzhao1
LANG: JAVA
TASK: wormhole
SECTION: 1.4
*/

import java.io.*;
import java.util.*;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/* I have 4/5 sets, please see attached.
Solutions I've saved:
Set 3 Answer:  EEDDC ACBEC ECBAA
Set 5 Answer:  ECEDD DBCDC DCABA
*/

/* TASK: skidesign
*/
class skidesign {

    public static void main(String[] args) throws IOException {
        BufferedReader f = new BufferedReader(new FileReader("skidesign.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("skidesign.out")));

        // Read Input
        // N:
        int n = Integer.parseInt(f.readLine());
        // Lock Combos
        int[] heightArr = new int[n];
        for (int i = 0; i < n; i++)
            heightArr[i] = Integer.parseInt(f.readLine());
        // NO NEED for sorting here - O(NlogN). Just find min and max heights - O(N)
        Arrays.sort(heightArr);

        // Calculate total costs based on range of final heights
        int totalCost;
        int minCost = 10000000;
        for (int lowH = heightArr[0]; lowH <= heightArr[n-1]; lowH++) {
            totalCost = 0;
            for (int hill=0; hill<n; hill++) {
                int ldiff = lowH-heightArr[hill];
                int rdiff = heightArr[hill]-lowH-17;
                if (ldiff>0) totalCost += ldiff*ldiff;
                else if (rdiff > 0) totalCost += rdiff*rdiff;
            }
            minCost = Math.min(minCost, totalCost);
        }
        out.println(minCost);
        out.close();
    }
}

/*
TASK:wormhole
NOTE: RECURSIVE !!! REVIEW THIS
*/

class Coordinate {
    int x;
    int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
class wormhole {
    static int n;
    static int[] partner;
    static Coordinate[] wormholes;
    static int[] first_right;
    static int nstuck;

    public static boolean loop_exists() {
        int pos;
        int start, count;
        for (start = 1; start<=n; start++) {
            pos = start;
            for (count = 1; count<=n; count++)
                pos = first_right[partner[pos]];
            if (pos != 0) return(true);
        }
        return(false);
    }

    public static int parings() {
        int total = 0;
        int i;

        for (i=1; i<=n; i++) {
            if (partner[i] == 0)  // Not paired yet
                break;
        }

            // Every one parired?
            if (i > n) {
                // Debug
                //for (int j = 1; j<=n; j++) System.out.print(j + " " + partner[j] + "; " );
                //System.out.println();
                if (loop_exists()) return 1;
                else return(0);
            }
            for (int j=i+1; j<=n; j++) {
                if (partner[j]==0) {
                    partner[i] = j;
                    partner[j] = i;
                    total += parings();

                    partner[i] = 0;
                    partner[j] = 0;
                }
            }

        return(total);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader f = new BufferedReader(new FileReader("wormhole.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("wormhole.out")));

        // Read Input
        // N: number of wormholes
        n = Integer.parseInt(f.readLine());
        // Lock Combos
        partner = new int[n+1];
        first_right = new int[n+1];
        wormholes = new Coordinate[n+1];

        for (int i = 1; i <= n; i++) {
            StringTokenizer st = new StringTokenizer(f.readLine());
            wormholes[i] = new Coordinate(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        // Find index of first hole on its right for each wormhole
        for (int i = 1; i<=n; i++)
            for (int j= 1;j<=n; j++) {
                if (wormholes[i].y == wormholes[j].y && wormholes[j].x > wormholes[i].x) {
                    int d = wormholes[j].x - wormholes[i].x;
                    if (first_right[i] == 0 || d < (wormholes[first_right[i]].x-wormholes[i].x))
                        first_right[i] = j;
                }
            }

        // Recursively check all paring combinations
        nstuck += parings();

        out.println(nstuck);
        out.close();
    }
}

/* TASK: combo
   NOTE: This problem could be solved with brute force. i.e. Check every possible keys
         - O(N^3), but easy implementation
         My implementation is O(1) complexity. However it takes significantly longer to code and debug
 */
class LockCombo {
    int n1, n2, n3;

    public LockCombo(int in1, int in2, int in3) {
        this.n1 = in1;
        this.n2 = in2;
        this.n3 = in3;
    }
}

class ValidKeys {
    // ArrayList<int[]> ranges = new ArrayList<>(3);
    public ArrayList<SortedSet<Integer>> ranges;
    public ValidKeys() {
        this.ranges = new ArrayList<>(3);
    }
    public ValidKeys(SortedSet<Integer> r0, SortedSet<Integer> r1, SortedSet<Integer> r2) {
        this.ranges = new ArrayList<>(3);
        this.ranges.add(r0);
        this.ranges.add(r1);
        this.ranges.add(r2);
    }

    public void add(SortedSet<Integer> r) {
        this.ranges.add(r);
    }
}

class combo {

    public static void main(String[] args) throws IOException {
        BufferedReader f = new BufferedReader(new FileReader("combo.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("combo.out")));

        // Read Input
        // N: numbers on the lock, [1, 100]
        int n = Integer.parseInt(f.readLine());
        // Lock Combos
        int[] fCombo = new int[3];
        int[] sCombo = new int[3];
        StringTokenizer st = new StringTokenizer(f.readLine());
        for (int i = 0; i <= 2; i++)
            fCombo[i] = (Integer.parseInt(st.nextToken()));
        StringTokenizer st1 = new StringTokenizer(f.readLine());
        for (int i = 0; i <= 2; i++)
            sCombo[i] = (Integer.parseInt(st1.nextToken()));

        // Establish valid key ranges
        ValidKeys fValidKeys = new ValidKeys();
        ValidKeys sValidKeys = new ValidKeys();

        for (int i = 0; i <= 2; i++) {    // Loop thru all three keys
            // USE SortedSet, Unique Elements
            SortedSet<Integer> keyRange = new TreeSet<>();
            for (int j = fCombo[i] - 2; j <= fCombo[i] + 2; j++) {
                int key = (j < 1) ? (j + n) : (j > n) ? (j - n) : j;
                if (key<1 || key>n) continue;
                keyRange.add(key);
            }
            fValidKeys.add(keyRange);
        }

        for (int i = 0; i <= 2; i++) {    // Loop thru all three keys
            // USE SortedSet, Unique Elements
            SortedSet<Integer> keyRange = new TreeSet<>();
            for (int j = sCombo[i] - 2; j <= sCombo[i] + 2; j++) {
                int key = (j < 1) ? (j + n) : (j > n) ? (j - n) : j;
                if (key<1 || key>n) continue;
                keyRange.add(key);
            }
            sValidKeys.add(keyRange);
        }

        // Check for overlapping Keys
        int[] nOverlapKeys = new int[3];
        for (int i = 0; i <= 2; i++) {
            SortedSet fSet = fValidKeys.ranges.get(i);
            SortedSet sSet = sValidKeys.ranges.get(i);

            for ( Object fKey : fSet)
                for ( Object sKey : sSet) {
                    if (fKey == sKey) nOverlapKeys[i]++;
               }
        }

        int fValidKeyNum = fValidKeys.ranges.get(0).size() * fValidKeys.ranges.get(1).size()
                * fValidKeys.ranges.get(2).size();
        int sValidKeyNum = sValidKeys.ranges.get(0).size() * sValidKeys.ranges.get(1).size()
                * sValidKeys.ranges.get(2).size();
        int overlapKeyNum = nOverlapKeys[0] * nOverlapKeys[1] * nOverlapKeys[2];
        int nValidKeys = fValidKeyNum + sValidKeyNum - overlapKeyNum;

        out.println(nValidKeys);
        out.close();
    }
}

/* NOT WORKING
class combo {
    public static int[] getRange(int min, int max int anchor, int distance, int[] range) {
        range.add(anchor);

        (i0 < 1) ? (i0 + n) : (i0 > n) ? (i0 + n) : i0;
        return Math.max()
    }
    public static void main(String[] args) throws IOException {
        BufferedReader f = new BufferedReader(new FileReader("combo.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("combo.out")));

        // Read Input
        // N: numbers on the lock, [1, 100]
        int n = Integer.parseInt(f.readLine());
        // Lock Combos
        int[] fCombo = new int[3];
        int[] sCombo = new int[3];
        StringTokenizer st = new StringTokenizer(f.readLine());
        for (int i = 0; i <= 2; i++)
            fCombo[i] = (Integer.parseInt(st.nextToken()));
        StringTokenizer st1 = new StringTokenizer(f.readLine());
        for (int i = 0; i <= 2; i++)
            sCombo[i] = (Integer.parseInt(st1.nextToken()));

        //
        Set<LockCombo> farmerSet = new HashSet<>();
        Set<LockCombo> masterSet  = new HashSet<>();

        for (int i0 = fCombo[0]-2; i0 <= fCombo[0]+2; i0++ ) {
            int c0 = (i0 < 1) ? (i0 + n) : (i0 > n) ? (i0 + n) : i0;
            if (c0<1 || c0>n) continue;
            for (int i1 = fCombo[1] - 2; i1 <= fCombo[1] + 2; i1++) {
                int c1 = (i1 < 1) ? (i1 + n) : (i1 > n) ? (i1 + n) : i1;
                if (c1<1 || c1>n) continue;
                for (int i2 = fCombo[2] - 2; i2 <= fCombo[2] + 2; i2++) {
                    int c2 = (i2 < 1) ? (i2 + n) : (i2 > n) ? (i2 + n) : i2;
                    if (c2<1 || c2>n) continue;
                    farmerSet.add(new LockCombo(c0, c1, c2));
                }
            }
        }
        int nOverlap = 0;
        for (int i0 = sCombo[0]-2; i0 <= sCombo[0]+2; i0++ ) {
            int c0 = (i0 < 1) ? (i0 + n) : (i0 > n) ? (i0 + n) : i0;
            if (c0<1 || c0>n) continue;
            for (int i1 = sCombo[1] - 2; i1 <= sCombo[1] + 2; i1++) {
                int c1 = (i1 < 1) ? (i1 + n) : (i1 > n) ? (i1 + n) : i1;
                if (c1<1 || c1>n) continue;
                for (int i2 = sCombo[2] - 2; i2 <= sCombo[2] + 2; i2++) {
                    int c2 = (i2 < 1) ? (i2 + n) : (i2 > n) ? (i2 + n) : i2;
                    if (c2<1 || c2>n) continue;

                    //if (i0 >= fCombo[0] - 2 && i0 <= fCombo[0] + 2 &&
                    //        i1 >= fCombo[1] - 2 && i1 <= fCombo[1] + 2 &&
                    //        i2 >= fCombo[2] - 2 && i2 <= fCombo[2] + 2)
                    LockCombo stmp = new LockCombo(c0,c1,c2);
                    if (farmerSet.contains(stmp))
                        nOverlap++;

                    masterSet.add(stmp);
                }
            }
        }
        int nValidCombo = farmerSet.size()+masterSet.size()-nOverlap;

        out.println(nValidCombo);
        out.close();
    }
}
*/

/* TASK: crypt1
 */
class crypt1 {

    static boolean isValid(int n, int[] digits) {
        boolean valid = true;
        int d;
        while(n > 0) {
            d = n%10;
            if ((Arrays.binarySearch(digits, d)) < 0 ) {
                valid = false;
                break;
            }
            n /= 10;
        }

        return valid;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader f = new BufferedReader(new FileReader("crypt1.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("crypt1.out")));

        // Read Input
        // Total number of digits to be used
        int n = Integer.parseInt(f.readLine());
        // Digits used to solve the cryptarithm
        int[] digits = new int[n];
        StringTokenizer st = new StringTokenizer(f.readLine());
        for (int i=0; i<n; i++)
            digits[i] = Integer.parseInt(st.nextToken());
        Arrays.sort(digits);

        int nSolutions = 0;
        int mult1, mult2, product;
        int mult1_1, mult1_2, mult2_1;
        int prod1, prod2;
        int prodDigit;
        boolean valid = false;
        for (int i1 = 0; i1<n; i1++) {
            mult1_1 = digits[i1]*100;

            for (int i2=0; i2<n; i2++) {
                mult1_2 = mult1_1+digits[i2]*10;

                for (int i3=0; i3<n; i3++) {
                    mult1 = mult1_2 + digits[i3];

                    for (int i4=0;i4<n; i4++) {
                        mult2_1 = digits[i4] * 10;
                        prod1 = digits[i4]*mult1;
                        if (prod1 >= 1000) break;
                        if (!isValid(prod1, digits)) continue;

                        for(int i5=0; i5<n;i5++) {
                            mult2 = mult2_1+digits[i5];
                            prod2 = digits[i5]*mult1;
                            if ( prod2 >= 1000) break;
                            if (!isValid(prod2, digits)) continue;

                            product = prod1*10 + prod2;
                            int product_dbg = product;
                            if (product >= 10000) break;
                           else if (valid = isValid(product, digits) == true) {
                                nSolutions++;
                            }
                        }
                    }
                 }
            }
        }

        out.println(nSolutions);
        out.close();
    }
}

/* TASK: barn1
 */
class StallOccupancy {
    int nOccupiedStalls;  // number of consecutive stalls occupied
    int nVacantStalls;    // number of consecutive stalls NOT occupied;

    public StallOccupancy(int occupied, int vacant) {
        this.nOccupiedStalls = occupied;
        this.nVacantStalls = vacant;
    }
}

class Sortbyulen implements Comparator<StallOccupancy> {
    public int compare(StallOccupancy a, StallOccupancy b) {
        // For StallOccupancy Sorting in Ascending Order
        return a.nVacantStalls-b.nVacantStalls;
    }
}

class barn1 {

    public static void main(String[] args) throws IOException {
        BufferedReader f = new BufferedReader(new FileReader("barn1.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("barn1.out")));

        // Read Input
        StringTokenizer st = new StringTokenizer(f.readLine());
        int m = Integer.parseInt(st.nextToken());  // Total number of Boards (max) [1, 50]
        int s = Integer.parseInt(st.nextToken());  // Total number of stalls [1, 200]
        int c = Integer.parseInt(st.nextToken());  // Number of occupied stalls

        int[] occupiedArr = new int[c];
        for (int i=0; i<c; i++)
            occupiedArr[i] = Integer.parseInt(f.readLine());

        // Process consecutive occupied stalls and vacant stalls
        Arrays.sort(occupiedArr);

        int nBoards = 0;
        int nStallsBlocked = 0;
        int nOccupied = 1;
        int nVacant = 0;
        int curOccupied = occupiedArr[0];
        ArrayList<StallOccupancy> so = new ArrayList<>();
        for (int i=1; i<c; i++) {
            int nextOccupied = occupiedArr[i];
            nVacant = nextOccupied - curOccupied-1;
            if (nVacant == 0) {
                nOccupied++;
            } else {
                so.add(new StallOccupancy(nOccupied, nVacant));
                nStallsBlocked += nOccupied;
                nOccupied = 1;
                nBoards++;
            }
            curOccupied = nextOccupied;
        }
        // Update for the last occupied stall batch
        so.add(new StallOccupancy(nOccupied, 200));
        nStallsBlocked += nOccupied;
        nOccupied = 1;
        nBoards++;

        Collections.sort(so, new Sortbyulen());
        int idx = 0;
        // If number_of_boards > m, combine two boards with least vacant gap in-between
        while (nBoards > m) {
            nBoards -= 1;
            nStallsBlocked += so.get(idx).nVacantStalls;
            idx++;
        }

        out.println(nStallsBlocked);
        out.close();
    }

}

/* TASK: milk
 */
class MilkSupply {
    int uprice;   // Price Per Unit
    int umilk;    // Units Available from this farmer

    public MilkSupply(int uprice, int umilk) {
        this.uprice = uprice;
        this.umilk = umilk;
    }
}

class Sortbyuprice implements Comparator<MilkSupply> {
    // Used for sorting milk supply in ascending order of price per unit
    public int compare(MilkSupply a, MilkSupply b) { return a.uprice-b.uprice; }
}

class milk {

    public static void main(String[] args) throws IOException {
        BufferedReader f = new BufferedReader(new FileReader("milk.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("milk.out")));

        // Read Input
        StringTokenizer st = new StringTokenizer(f.readLine());
        int nmilk    = Integer.parseInt(st.nextToken());  // [0, 2,000,000]
        int nfarmers = Integer.parseInt(st.nextToken());  // [0, 5000]

        ArrayList<MilkSupply> msupply = new ArrayList<>();    // [0, 1000]
                                                            // [0, 2,000,000]
        for (int i = 0; i<nfarmers; i++) {
            StringTokenizer st1 = new StringTokenizer(f.readLine());
            int uprice = Integer.parseInt(st1.nextToken());
            int umilk  = Integer.parseInt(st1.nextToken());

            msupply.add(new MilkSupply(uprice, umilk));
        }

        // Calculate minimum cost - Greedy algorithm works
        Collections.sort(msupply, new Sortbyuprice());
        int idx = 0;
        int totalprice = 0;
        while (nmilk > 0) {
            int nunit = msupply.get(idx).umilk;
            if (nmilk > nunit) {
                nmilk -= nunit;
                totalprice += nunit*msupply.get(idx).uprice;
            } else {
                totalprice += nmilk * msupply.get(idx).uprice;
                break;
            }
            idx++;
        }

        out.println(totalprice);
        out.close();
    }
}
