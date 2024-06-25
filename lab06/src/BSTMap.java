import org.apache.logging.log4j.util.PropertySource;

import java.util.*;
import java.util.stream.BaseStream;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    // 树形结构
     class Node {
        public K _key;
        public V _value;
        public Node _left;
        public Node _right;
        Node(K key, V value) {
            _key = key;
            _value = value;
            _left = null;
            _right = null;
        }

//        @Override
//        public String toString() {
//            String res = "|"
//        }
    }


    //指向树根
    private Node _root;

    //树形结构中键值,也就是节点的数量
    private int _size;

    //二叉搜索树类，其实节点（Node类）也是一颗树，但是我们可以用一个模型来将真实的树结构与用户分离，我们的操作还是对BST中的_root进行操作，他就是我们的树。
    public BSTMap() {
        _root = null;
        _size = 0;

    }
    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */

    //把（K，V）联系起来，如果K在树中，就修改K键中V的值，size不变；如果不在树中，则将（K，V）插入树中，size+1，
    //如果一个辅助方法，putRec（）；他是用递归实现的，类中的方法不好直接使用递归。
    //可以把putRec（）返回一个节点，即也可以看作返回一颗新树，这棵树与之前的树相比可能有两种变化：
    //          1.原树中键为key的结点的值被改变；
    //          2.在原树中添加了（K，V）节点。
    //所以我们将_root = putRec(_root, new Node(key, value));就将putRec（）对原树的修改保存在类了 _root中
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("key can`t be null");
        }
        _size += containsKey(key) ? 0 : 1;
        _root = putRec(_root, new Node(key, value));
    }

    /**
     * put的辅助函数，递归插入（K，V），树中的每一个节点都是一颗树，这很重要！也就是说，每一个节点都是根（root），null是一棵空树。
     * 首先查找根，如果跟节点的键等于K，则，修改根节点的键值
     * 如果根不空，那么分别到根节点左右子树上找，在基本情况时，说明树中没有键值为key的节点（树），则新建一个节点（树），并返回节点（树）
     * */
    private Node putRec(Node root, Node node) {
        if (root == null) {
            return node;
        }
        if (root._key.compareTo(node._key) == 0) {
            root._value = node._value;
        } else if (root._key.compareTo(node._key) > 0) {
            root._left = putRec(root._left, node);//    这里赋值语句是因为，树形结构的父节点是无法找到的，所以我们把他。。。
        } else {                                  // 也可以这样看，根节点的键值不等，所以我们要到其左右子树中去插入或修改，因为putRec（）返回一颗被修改的树
                                                  // 将被修改的保存在原根节点原位置
            root._right = putRec(root._right, node);
        }
        return root;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        Node n = find(_root, key);
        return n == null ? null : n._value;
    }

    //find函数，找出树中节点键值为key的节点，存在返回节点，不存在返回null。
    // 根不为空比较根的key值，等则赋值给result，返回，
    // 不等： 两种情况：大于，去左子树上找，find（左子树）--返回左子树上的key值节点，没有返回null，将返回值赋值给result，返回
    //                小于，去右子树上找，find（右子树）--返回右子树的key值结点，没有返回null，将返回值赋值给result，返回
    private Node find(Node root, K key) {
        if (root == null) {
            return null;
        }
        Node result;
        if (root._key.compareTo(key) > 0) {
             result= find(root._left, key);
        } else if (root._key.compareTo(key) < 0) {
             result = find(root._right, key);
        } else {
            result =  root;
        }
        return result;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        return find(_root, key) != null;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return _size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        _root = null;
        _size = 0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
//        Set<K> result = new HashSet<>();
//        for (K item : this) {
//            result.add(item);
//        }
//        return result;
        return keySetRec(_root);
    }

    private Set<K> keySetRec(Node root) {
        if (root == null) {
            return new HashSet<>();
        }
        Set<K> result = new HashSet<>();
        Set<K> r = new HashSet<>();
        Set<K> left = new HashSet<>();
        Set<K> right = new HashSet<>();
        left = keySetRec(root._left);
        r.add(root._key);
        right = keySetRec(root._right);
        result.addAll(left);
        result.addAll(r);
        result.addAll(right);
        return result;
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */

    //BST中remove一个节点可以分为三种情况：
    //       该节点没有子节点（子树）：删除方法是将该节点的父节点的指向该节点的指针赋值为null；
    //       有一个子节点（子树）：删除方法是，将该节点的父节点的指向该节点的指针 指向该节点的 子节点。
    //       有两个子节点（子树）。找出该节点左子树上的最大节点或右子树上的最小节点，将其与要删除的节点互换值，（不用互换，直接替换掉删除删除节点也行，但是返回节点时会出现问题）
    //                     此时，要删除的节点被换到（节点中的数据，除指针数据），左边最大/右边最小节点的位置，则变成删除这些节点的问题，他们保证只有一个或零个孩子。用上面的方法即可。
    @Override
    public V remove(K key) {
        Node removeNode = find(_root, key);
        if (removeNode == null) {
            return null;
        }
        switch (NumChild(removeNode)) {
            case 0:
                _root = remove0_1Child(_root, removeNode, null);
                break;
            case 1:
                _root = remove0_1Child(_root, removeNode,
                        removeNode._left == null ? removeNode._right : removeNode._left);
                break;
            case 2:
                Node maxLeft = findLeftMax(removeNode._left);//GPT修改：_root._left -> removeNode._left  !!!从被删除的根节点的左子树中找最大而不是从_root节点的左子树中找最大。
                swap(maxLeft, removeNode);
                removeNode = maxLeft;
                if (NumChild(removeNode) == 0) {
                    _root = remove0_1Child(_root, removeNode, null);//
                } else {
                    _root = remove0_1Child(_root, removeNode, removeNode._left == null ? removeNode._right : removeNode._left);
                }
        }
        _size--;
        return removeNode._value;
    }
    //return 一颗树中最大的节点，
    private Node findLeftMax(Node root) {
        if (root == null) {
            return null;
        }
        if (root._right == null) {
            return root;
        }
        return findLeftMax(root._right);
    }
    //swap函数，为什么创立一个新节点，_root会变为null    !!!因为在node类的构造函数中，错误的将_root赋值了。bug...
                                                        //  树形结构
                                                        // class Node {
                                                        //    public K _key;
                                                        //    public V _value;
                                                        //    public Node _left;
                                                        //    public Node _right;
                                                        //    Node(K key, V value) {
                                                        //        _key = key;
                                                        //        _value = value;
                                                        //        _left = null;
                                                        //        _root = null;!!!
                                                        //    }
private void swap(Node a,Node b) {
       Node nd = new Node(a._key, a._value);
        a._value = b._value;
        a._key = b._key;
        b._key = nd._key;
        b._value = nd._value;
    }

    //删除0/1个子节点（树）的函数，返回被删除的树。
    private Node remove0_1Child(Node root,Node desNode,Node context) {
        if (root == null) {
            return null;
        }
        if(root==desNode) root = context;       //发现根节点（只有一个孩子，或是叶子节点）删除的节点，把context赋值给root，无孩子context=null，有一个孩子：context = 指向孩子的指针
        else {
            root._left = remove0_1Child(root._left, desNode, context);  //不等，去左子树中删除，删除的子树赋值给root._left
            root._right = remove0_1Child(root._right, desNode, context);//不等，去右子树中删除，删除的子树赋值给root._left
        }
        return root;    //返回被修改的树
    }

    //返回要删除节点的孩子数量
    private int NumChild(Node keyNode) {
        int result = 0;
        if (keyNode._right != null) result++;
        if (keyNode._left != null) result++;
        return result;
    }

    public void printInOrder() {
        InorderRec(_root);
    }

    private void InorderRec(Node root) {
        if (root != null) {
            InorderRec(root._left);
            System.out.print(root._key.toString() + root._value + "->");
            InorderRec(root._right);
        }
    }
    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        return new TreeIterator<>();
    }

    class TreeIterator<K> implements Iterator<K> {
        private final Deque<Node> _deque;
        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        TreeIterator() {
            _deque = new LinkedList<>();
            _deque.addLast(_root);
        }
        @Override
        public boolean hasNext() {
            return !_deque.isEmpty();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public K next() {
            Node first = _deque.removeFirst();
            if (first == null) {
                return null;
            }
            if (first._left != null) {
                _deque.addLast(first._left);
            }
            if (first._right != null) {
                _deque.addLast(first._right);
            }
            return (K) first._key;
        }
    }

    public static void main(String[] args) {

    }
}
