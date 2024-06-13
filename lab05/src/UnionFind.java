import java.util.Arrays;

public class UnionFind {
    // TODO: Instance variables
    private  int[] _item;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        // TODO: YOUR CODE HERE
        _item = new int[N];
        Arrays.fill(_item, - 1);
    }

    public UnionFind(int[] src) {
        _item = new int[src.length];
        System.arraycopy(src, 0, _item, 0, src.length);
    }


    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        // TODO: YOUR CODE HERE

        int parent = v;
        for (; parent >=  0; parent = _item[parent]) ;
        return -parent;

    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        // TODO: YOUR CODE HERE
        return _item[v];
    }


    private int root(int v) {
        int root = v;
        for (; _item[root] >=0; root = _item[root]) ;
        return root;
    }
    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO: YOUR CODE HERE
        return root(v1) == root(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) throws IllegalArgumentException{
        if (v < 0 || v > _item.length) {
            throw  new IllegalArgumentException("Invalid index");
        }
        // TODO: YOUR CODE HERE
        return root(v);
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        // TODO: YOUR CODE HERE
        if (root(v1) == root(v2)) {
            return;
        }
        int sizeV1 = sizeOf(v1), sizeV2 = sizeOf(v2);
        if (sizeV1 <= sizeV2) {
            _item[root(v1)] = root(v2);
            _item[root(v2)] -= sizeV1;
        } else {
            _item[root(v2)] = root(v1);
            _item[root(v1)] -= sizeV2;
        }
    }

    public int pathCompressionFind(int v) {
        int p = v;
        while (parent(p) != root(v)) {
            int tmp = parent(p);
            _item[p] = root(v);
            p = tmp;
        }
        return parent(p);
    }

    public static void main(String[] args) {
        int[] a =  new int[]{1,-6,1,2,3,4};
        UnionFind s = new UnionFind(a);

        s.pathCompressionFind(5);
        System.out.println(s.find(5));

    }

}
