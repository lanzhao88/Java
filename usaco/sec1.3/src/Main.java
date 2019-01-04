
/* Use the slash-star style comments or the system won't see your
   identification information */
/*
ID: lanzhao1
LANG: JAVA
TASK: dualpal
*/

import java.io.*;
import java.util.*;
import java.util.Comparator;

/* TASK: dualpal
*/
class dualpal {
    static boolean isPalindrome(String p){
        boolean ispalin = true;
        int len = p.length();
        for (int i=0; i<(len+1)/2; i++) {
            if (p.charAt(i) != p.charAt(len-i-1)) {
                ispalin = false;
                break;
            }
        }
        return (ispalin);
    }

    static String convert2base(int base, int dividend) {
        int quotient = dividend;
        int residue=0;
        // Convert aquare to String - based in 'base'
        StringBuilder str = new StringBuilder("");
        while (quotient > 0) {
            residue = quotient%base;
            str.append(residue);
            quotient = quotient/base;
        }

        return(str.toString());
    }

    public static void main(String[] args) throws IOException {
        BufferedReader f = new BufferedReader(new FileReader("dualpal.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("dualpal.out")));

        StringTokenizer st = new StringTokenizer(f.readLine());
        int n = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());

        int ndualpal = 0;
        for (int num = s+1; ; num++) {
            int npalin = 0;
            for (int base = 2; base<=10; base++) {
                String numinbase = convert2base(base, num);
                if (isPalindrome(numinbase)) {
                    npalin++;
                    if (npalin == 2) {
                        ndualpal++;
                        out.println(num);
                        break;
                    }
                }
            }
            if (ndualpal == n) break;
        }

        out.close();
    }
}



/* TASK: palsquare
*/

class palsquare {

    static boolean isPalindrome(String p){
        boolean ispalin = true;
        int len = p.length();
        for (int i=0; i<(len+1)/2; i++) {
            if (p.charAt(i) != p.charAt(len-i-1)) {
                ispalin = false;
                break;
            }
        }
        return (ispalin);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader f = new BufferedReader(new FileReader("palsquare.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("palsquare.out")));

        int base = Integer.parseInt(f.readLine());

        for (int i = 1; i<=300; i++) {
            int quotient = i*i;
            int residue=0;
            // Convert aquare to String - based in 'base'
            StringBuilder numsq = new StringBuilder("");
            while (quotient > 0) {
                residue = quotient%base;
                if (residue<10) numsq.append(residue);
                else numsq.append((char)(residue-10+'A'));
                quotient = quotient/base;
            }

            String sqstr = numsq.toString();
            if (isPalindrome(sqstr)) {
                StringBuilder num = new StringBuilder();
                quotient = i;
                while (quotient > 0) {
                    residue = quotient%base;
                    if (residue<10) num.append(residue);
                    else num.append((char)(residue-10+'A'));
                    quotient = quotient/base;
                }

                out.println(num.reverse().toString() + " " + sqstr);
            }
        }

        out.close();
    }
}



/* TASK: namenum
*/
class namenum {
    public static void main(String[] args) throws IOException {
        BufferedReader f = new BufferedReader(new FileReader("namenum.in"));
        BufferedReader d = new BufferedReader(new FileReader("dict.txt"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("namenum.out")));

        // Read in the Dictionary
        ArrayList<String> dict = new ArrayList<>();
        String name;
        while ( (name= d.readLine()) != null ) {
            dict.add(name);
        }
        // Read input cow number
        String serialNum = new String(f.readLine());
        int len = serialNum.length();

        String num2minchar = new String("ADGJMPTW");
        String num2maxchar = new String("CFILOSVY");
        String minName = new String(serialNum);
        String maxName = new String(serialNum);
        char[] minNameChars = minName.toCharArray();
        char[] maxNameChars = maxName.toCharArray();
        for (int i = 0; i < len; i++) {
            // This works except for >'P'
            //minNameChars[i] =
            //        (char)((serialNum.charAt(i)-'2')*3+'A');
            //maxNameChars[i] = (char)(minNameChars[i]+2);

            int numidx = Integer.parseInt(String.valueOf(serialNum.charAt(i)));
            minNameChars[i] = num2minchar.charAt(numidx-2);
            maxNameChars[i] = num2maxchar.charAt(numidx-2);
        }
        //minName = String.valueOf(minNameChars);
        //maxName = String.valueOf(maxNameChars);

        // Search for valid names
        int nmatch = 0;
        for (String dictItem : dict) {
            if (dictItem.length() == len) {
                boolean match = true;
                for (int i=0; i<len; i++) {
                    if (dictItem.charAt(i) < minNameChars[i] || dictItem.charAt(i) > maxNameChars[i]) {
                        match = false;
                        break;
                    }
                }

                if (match) {
                    out.println(dictItem);
                    nmatch++;
                }
            }
        }
        if (nmatch == 0) out.println("NONE");

        out.close();
    }
}


/* TASK transform
*/
class transform {
    static int nSize;
    static List<String> otile = new ArrayList<>();
    static List<String> ntile = new ArrayList<>();

    public static boolean isTransNum(int transNum) {
        // Pattern #1
        if (transNum == 1) {// Rotate Clockwise 90 Degrees
            for (int i=0; i<nSize; i++)
                for (int j=0; j<nSize; j++) {
                    if ((otile.get(i)).charAt(j) != (ntile.get(j)).charAt(nSize-1-i)) {
                        return(false);
                    }
                }
            return(true);

        } else if (transNum == 2) { // Rotate Clockwise 180 Degrees
            for (int i=0; i<nSize; i++)
                for (int j=0; j<nSize; j++) {
                    if ((otile.get(i)).charAt(j) != (ntile.get(nSize-1-i)).charAt(nSize-1-j)) {
                         return(false);
                    }
                }
            return(true);

        } else if (transNum == 3) { // Rotate Clockwise 270 Degrees
            for (int i=0; i<nSize; i++)
                for (int j=0; j<nSize; j++) {
                    if ((otile.get(i)).charAt(j) != (ntile.get(nSize-1-j)).charAt(i)){
                        return(false);
                    }
                }
            return(true);

        } else if (transNum == 4) {  // Reflection on middle vertical line
            for (int i=0; i<nSize; i++)
                for (int j=0; j<nSize; j++) {
                    if ((otile.get(i)).charAt(j) != (ntile.get(i)).charAt(nSize-1-j)) {
                        return(false);
                    }
                }
            return(true);

        } else if (transNum == 5) {  // Reflection on middle vertical line
            boolean match = true;
            comb1:
            for (int i=0; i<nSize; i++) {
                for (int j=0; j<nSize; j++) {
                    if ((otile.get(i)).charAt(j) != (ntile.get(nSize-1-j)).charAt(nSize-1-i)) {
                            match = false;
                            break comb1;
                    }
                }
            }
            if (match) return(true);

            match = true;
            comb2:
            for (int i=0; i<nSize; i++) {
                for (int j=0; j<nSize; j++) {
                    if ((otile.get(i)).charAt(j) != (ntile.get(nSize-1-i)).charAt(j)) {
                        match = false;
                        break comb2;
                    }
                }
            }
            if (match) return(true);

            match = true;
            comb3:
            for (int i=0; i<nSize; i++) {
                for (int j=0; j<nSize; j++) {
                    if ((otile.get(i)).charAt(j) != (ntile.get(j)).charAt(i)) {
                        match = false;
                        break comb3;
                    }
                }
            }
            if (match) return(true);
            else return(false);

        } else if (transNum == 6) {
            for (int i=0; i<nSize; i++)
                for (int j=0; j<nSize; j++) {
                    if ((otile.get(i)).charAt(j) != (ntile.get(i)).charAt(j)) {
                        return(false);
                    }
                }
            return(true);

        } else {
            return(true);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader f  = new BufferedReader(new FileReader(("transform.in")));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("transform.out")));

        // Read in old and new square patterns
        nSize = Integer.parseInt(f.readLine());
        for (int i = 0; i <nSize; i++ )
            otile.add(f.readLine());
        for (int i = 0; i<nSize; i++)
            ntile.add(f.readLine());

        for (int n = 1; n <= 7; n++) {
             if (isTransNum(n)) {
                 out.println(n);
                 out.close();
                 break;
             }
        }
    }
}

/* MILK2
*/
class MilkTime {
    int start;
    int end;

    public MilkTime(int start, int end) {
        this.start = start;
        this.end = end;
    }
}

// Comparator Interface - sort the elements based on data members
class Sortbystart implements Comparator<MilkTime> {
    // Used for sorting in ascending order in MilkTime.start
    public int compare(MilkTime a, MilkTime b) {
        return a.start-b.start;
    }
}

class milk2 {
    public static void main(String [] args) throws IOException {
        // Use BufferedReader rather than RandomAccessFile; It's much faster
        BufferedReader f = new BufferedReader(new FileReader("milk2.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("milk2.out")));

        // Read input data
        int nfarmers = Integer.parseInt(f.readLine());
        ArrayList<MilkTime> mtime = new ArrayList<>();
        for (int i =0; i< nfarmers; i++) {
            StringTokenizer st = new StringTokenizer(f.readLine());
            int t1 = Integer.parseInt(st.nextToken());
            int t2 = Integer.parseInt(st.nextToken());
            mtime.add(new MilkTime(t1, t2));
        }

        // Sort by start time
        Collections.sort(mtime, new Sortbystart());

        // Find longest milking time, and longest no-milking time
        int combStart = mtime.get(0).start;
        int combEnd = mtime.get(0).end;
        int maxIdle = 0;
        int maxMilking = combEnd - combStart;

        for (int i = 1; i < nfarmers; i++) {
            if (mtime.get(i).start > combEnd) {
                combStart = mtime.get(i).start;
                maxIdle = Math.max(maxIdle, combStart-combEnd);
                combEnd = mtime.get(i).end;
            } else if (mtime.get(i).end > combEnd) {
                combEnd = mtime.get(i).end;
                maxMilking = Math.max(maxMilking, combEnd - combStart);
            }
        }

        out.println(maxMilking + " " + maxIdle);
        out.close();
    }

}
