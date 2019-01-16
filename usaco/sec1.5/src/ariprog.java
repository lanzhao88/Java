
/* http://train.usaco.org/usacogate  */
/* Use the slash-star style comments or the system won't see your
   identification information */
/*
ID: lanzhao1
LANG: JAVA
TASK: milk3
SECTION: 1.5
*/

import java.io.*;
import java.util.*;

/* TASK: milk3
   NOTES: Use BFS; Data Structures:
          queue:        Queue<>
          Ordered set : TreeSet<>
          Array:        int[][][]
          Iterator    : .iterator(), .hasNext(), .next()
 */

//
class Node {
    int[] liters;

    Node(int a, int b, int c) {
       liters = new int[3];
       liters[0] = a;
       liters[1] = b;
       liters[2] = c;
    }

    Node (Node nd) {
        liters = new int[3];
        this.liters[0] = nd.liters[0];
        this.liters[1] = nd.liters[1];
        this.liters[2] = nd.liters[2];
    }
}

class BucketFiller {
    Node capacity;      // Capacity of each bucket
    Queue<Node> nodeQ;  // Nodes in the BFS tree, with # of liters in each bucket (a, b, c)
    // nodeMark[a][b][c]=1: Node with (a, b, c) has been traversed
    int [][][] nodeMark;
    TreeSet<Integer> litersBucketC;

    BucketFiller(int A, int B, int C) {
        capacity = new Node(A, B, C);
        nodeMark = new int[A+1][B+1][C+1];
        nodeQ    = new LinkedList<>();
        litersBucketC = new TreeSet<>();
    }

    void setNodeMark(int a, int b, int c) {
        nodeMark[a][b][c] = 1;
    }

    void setNodeMark(Node nd) {
        nodeMark[nd.liters[0]][nd.liters[1]][nd.liters[2]] = 1;
    }

    boolean isNodeTraversed(Node nd) {
        if (nodeMark[nd.liters[0]][nd.liters[1]][nd.liters[2]] == 1) return true;
        else return false;
    }

    void fill(Node nd) {
        // Try pour milk from bucket i to bucket j
        for (int i = 0; i<=2; i++) {
            if (nd.liters[i] == 0) continue;
            for (int j = 0; j <= 2; j++) {
                if (i == j || nd.liters[j]==capacity.liters[j]) continue;
                int exchange = Math.min(nd.liters[i], capacity.liters[j]-nd.liters[j]);

                Node newNode = new Node(nd);
                newNode.liters[i] -= exchange;
                newNode.liters[j] += exchange;

                if (!isNodeTraversed(newNode)) {
                    nodeQ.add(newNode);
                    setNodeMark(newNode);
                    // Add amount of milk in C to the result, if bucket A is empty
                    if (newNode.liters[0] == 0) {
                        litersBucketC.add(newNode.liters[2]);
                    }
                }
            }
        }
    }

    // BFS
    boolean fillBFS() {
        while (!nodeQ.isEmpty()) {
            Node nd = nodeQ.remove();
            fill(nd);
        }
        return true;
    }

    void printResult(BufferedWriter out) throws IOException {
        Iterator<Integer> it = litersBucketC.iterator();
        while (it.hasNext()) {
            out.write(Integer.toString(it.next()));
            if (it.hasNext())
                out.write(" ");
        }
        out.newLine();
    }
}

class milk3 {
    public static void main(String[] args) throws IOException {
        BufferedReader f = new BufferedReader(new FileReader("milk3.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("milk3.out"));

        StringTokenizer st = new StringTokenizer(f.readLine());
        int A = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());

        BucketFiller bf = new BucketFiller(A, B, C);

        bf.nodeQ.add(new Node(0, 0, C));
        bf.setNodeMark(0, 0, C);
        bf.litersBucketC.add(C);
        bf.fillBFS();

        bf.printResult(out);
        out.close();
    }
}

/* TASK: ariprog
 */
class Bisquare {
    int N;   // Sequence Length
    int M;   // Max value of p, q
    int[] isBisq;

    Bisquare(int n, int m) {
        this.N = n;
        this.M = m;
        isBisq = new int[2*(m+1)*(m+1)];
    }
    // Mark all bi-square numbers
    void initBisq() {
        for (int i = 0; i<=M; i++)
            for (int j=0; j<=M; j++)
                isBisq[i*i+j*j] = 1;
    }

    // a, a+b, a+2b, a+3b,...,a+nb equal to some p^2+q^2
    boolean isBisqSeq(int seqlenminus1, int start, int step){
        // Recursive, base case
        int currentVal = start;
        for (int idx=0; idx<seqlenminus1; idx++) {
            if (isBisq[currentVal] == 0)
                return(false);
            currentVal += step;
        }
        return(true);
    }
}

class ariprog {
    public static void main(String[] args) throws IOException {
        int N;    // Length of sequence
        int M;    //

        BufferedReader f = new BufferedReader(new FileReader("ariprog.in"));
        BufferedWriter out;
        out = new BufferedWriter(new BufferedWriter(new FileWriter("ariprog.out")));

        // Read Input
        N = Integer.parseInt(f.readLine());
        M = Integer.parseInt(f.readLine());

        Bisquare bisq = new Bisquare(N, M);
        int isBisqCnt = 0;
        bisq.initBisq();
        for (int step = 1; step<=(M*M*2)/(N-1); step++) {
            for (int start = 0; start<=M*M*2; start++) {
                if ( (start + (N-1)*step) > (M*M*2) ) break;
                if ( bisq.isBisqSeq(N, start, step) ) {
                    out.write(start + " " + step + "\n");
                    isBisqCnt++;
                };
            }
        }

        if (isBisqCnt == 0)
            out.write("NONE\n");

        out.close();
    }
}