package ngrams;


public class RedBlackTree {
    public static class Node {
        public TimeSeries _ts;
        public String _word_key;
        public boolean _isBlack;
        public Node _left;
        public Node _right;

        public Node() {
            this(null, null, null, null, false);
        }

        public Node(String word, TimeSeries ts) {
            this(word, ts, null, null, false);
        }

        public Node(String word, TimeSeries ts, Node left, Node right, boolean isBlack) {
            _word_key = word;
            _ts = ts;
            _left = left;
            _right = right;
            _isBlack = isBlack;
        }
    }

    private Node _root;
    private int _size;

    /**
     * 构造函数，无参构造函数，返回一个红黑树模型
     * */
    public RedBlackTree() {
        _root = null;
        _size = 0;
    }

    /**
     * 往树中插入一个新的元素，如果这个元素存在，则plus到这个元素中的_ts中去
     * */
    public void insert(String word, TimeSeries ts) {
        _size += containKeys(word) ? 0 : 1;
        _root = insertRec(_root, new Node(word, ts));
        _root._isBlack = true;
    }

    private Node insertRec(Node root, Node node) {
        if (root == null) {
            return node;
        }
        if (node._word_key.compareTo(root._word_key) > 0) root._right = insertRec(root._right, node);
        if (node._word_key.compareTo(root._word_key) < 0) root._left = insertRec(root._left, node);
        if (node._word_key.compareTo(root._word_key) == 0) root._ts = root._ts.plus(node._ts);
        if (isRed(root._right) && !isRed(root._left)) {
            root = leftRotating(root);
        }
        if (isRed(root._left) && (isRed(root._left._left) || isRed(root._left._right))) {
            root = rightRotating(root);
        }
        if (isRed(root._left) && isRed(root._right)) {
            root = flapColor(root);
        }
        return root;
    }

    private boolean isRed(Node node) {
        return node != null && !node._isBlack;
    }

    private void swapColor(Node nd1, Node nd2) {
        boolean tmp = nd1._isBlack;
        nd1._isBlack = nd2._isBlack;
        nd2._isBlack = tmp;
    }

    private Node leftRotating(Node node) {
        Node right = node._right;
        if (!node._isBlack && !right._isBlack) {
            node._right = right._left;
            right._left = node;
        } else {
            node._right = right._left;
            right._left = node;
            swapColor(node, right);
        }
        return right;
    }

    private Node rightRotating(Node node) {
        Node left = node._left;
        node._left = left._right;
        left._right = node;
        swapColor(left, node);
        return left;
    }

    private Node flapColor(Node node) {
        node._isBlack = !node._isBlack;
        node._left._isBlack = !node._left._isBlack;
        node._right._isBlack = !node._right._isBlack;
        return node;
    }

    /**
     * 树中是否存在键为key节点
     * */
    public boolean containKeys(String key) {
        return find(_root, key) != null;
    }

    private Node find(Node root, String key) {
        if (root == null) {
            return null;
        }
        Node result = null;
        if (key.compareTo(root._word_key) == 0) result = root;
        if (key.compareTo(root._word_key) > 0) result = find(root._right, key);
        if (key.compareTo(root._word_key) < 0) result = find(root._left, key);
        return result;
    }
    /**
     * 返回键为key的value
     * */
    public TimeSeries get(String key) {
        Node result = find(_root, key);
        return result == null ? null : result._ts;
    }
    /**
     *
     * */
    public void put(String key, TimeSeries ts) {
 /**
  *  if (!containKeys(key)) {
  *  insert(key, ts);
  *
   } else {
            find(_root, key)._ts = find(_root, key)._ts.plus(ts);
        }*/
        insert(key, ts);
    }


    private Node putRec(Node root, String key, TimeSeries ts) {
        /**从树上掉下来         * */
        if (root == null) {
            return new Node(key, ts);
        }
        if (key.compareTo(root._word_key) > 0) root._right = putRec(root._right, key, ts);
        if (key.compareTo(root._word_key) < 0) root._left = putRec(root._left, key, ts);
        if (key.compareTo(root._word_key) == 0) root._ts = ts;
        return root;
    }
    /**
     * 删除方法有问题，会破坏树结构，破坏B树的不变量，
     * 与插入类似，边删除边修改，利用旋转来改变的树结构。
     * */
    public TimeSeries remove(String key) {
        Node result = find(_root, key);
        if (result == null) {
            throw new IllegalArgumentException("No exist key equal" + key);
        }
        switch (countChildNum(result)) {
            case 0:
                remove0_1Child(_root, result, null);
                break;
            case 1:
                remove0_1Child(_root, result, result._left == null ? result._right : result._left);
                break;
            case 2:
                Node leftLarge = findMax(_root._left);
                result._ts = leftLarge._ts;
                result._word_key = leftLarge._word_key;
                result._isBlack = true;
                if (countChildNum(leftLarge) == 0) {
                    remove0_1Child(_root, leftLarge, null);
                } else {
                    remove0_1Child(_root, leftLarge, leftLarge._left == null ? leftLarge._right : leftLarge._left);
                }
                break;
        }
        _size--;
        return result._ts;
    }

    private void swap(Node nd1, Node nd2) {
        nd1._ts = nd2._ts;
        nd1._word_key = nd2._word_key;
        nd1._isBlack = true;
    }
    private int countChildNum(Node node) {
        int result = 0;
        if (node._right != null) result++;
        if (node._left != null) result++;
        return result;
    }

    /**
     * 怎找一个树节点的父节点？
     * 树的遍历
     * */
    private Node remove0_1Child(Node root, Node node, Node context) {
        if (root == null) return null;
        if (root._left!=null&&root._left._word_key.compareTo(node._word_key) == 0 ) {
            root._left = context;
            return root;
        }
        if (root._right!=null&&root._right._word_key.compareTo(node._word_key) == 0) {
            root._right = context;
            return root;
        }
        if (node._word_key.compareTo(root._word_key)>0) node._right = remove0_1Child(root._right, node, context);
        if (node._word_key.compareTo(root._word_key)<0) node._left = remove0_1Child(root._left, node, context);
        return root;
    }

    private Node findMax(Node root) {
        if (root._right == null) {
            return root;
        }
        return findMax(root._right);
    }
    public int size() {
        return _size;
    }
}
