/**
 * 一棵树的操作有很多，在这个lab中只要求做insert操作，这是因为在BST中，我们差不多实现了基本方法
 * 而红黑树是一种BST，但是他的插入方法比较特别，所以只实现插入操作 ！！remove方法是不是与BST相同
 * 在左倾红黑树中，我们使用红色链接来表示B树中的3-节点，但是不好实现，于是这里采用红节点的方式
 * 来替代红链接。
 * LLRB中的三个不变量：
 *          1.如果一个节点有一个红色子节点，那么它一定位于左侧
 *          2.任何节点都不能有两个红色子节点。
 *          3.没有红色节点可以有红色的父节点（每个红色节点的父节点都是黑色的）
 * 在插入新节点时，我们时常会破坏着三个不变量，需要有对应的旋转操作来维护，
 * 维护采取的办法时是在每次插入时维护，而不是最后在插入之后维护整棵完整的树。
 * */

public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    /**
     * 红黑树节点类，其实也可以说成树类，在数据结构中，单一节点也可以说成
     * 是一个数据结构，例如，一个链表节点是一个有一元素的链表，没有节点，可以
     * 看作是一个空链表
     * 一个树节点可以看作（是）一颗有一个元素的树，没有节点是一棵空树
     * 一个图节点，可以看作是有一个节点的图，没有节点是一个空图。
     * 对于数据结构来说，重要的是他的组织方式
     * 这是树节点类，老师把他设置为static类型，泛型类
     * 里面有四个实例变量，T 类型的 item，isBlack，left，right。
     * 节点中的数据是T item，复杂的一点的情况是每个节点的数据是一个带键节点，
     * 插入树中时，按键排序。
     * 这其实就是一棵树了，我们可以RedBlackTree中的方法放在RBTreeNode中实现，
     * 那样做有一点麻烦，我们用选择在真实数据上加一层抽象，方便我们理解，我们定义RedBlackTree类
     * 类中实例变量是一个RBTreeNode<T> root（一棵树的引用），加一层抽象更加爽！！
     * 记住这个类只是一个抓手，帮助我们操作树模型。
     */
    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /**
         * Creates a RBTreeNode with item ITEM and color depending on ISBLACK
         * value.
         * @param isBlack
         * @param item
         */
   /**
    * this(isBlack, item, null, null)调用了BTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
    * RBTreeNode<T> right)函数，代码更简洁，减少了冗余，并提高了可维护性。
    */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /**
         * Creates a RBTreeNode with item ITEM, color depending on ISBLACK
         * value, left child LEFT, and right child RIGHT.
         * @param isBlack
         * @param item
         * @param left
         * @param right
         */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Creates an empty RedBlackTree.
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Flips the color of node and its children. Assume that NODE has both left
     * and right children
     * @param node
     */
    /**
     * 针对一个节点有两个红色子节点的情况
     * */
    void flipColors(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        node.isBlack = !node.isBlack;
        node.left.isBlack = !node.left.isBlack;
        node.right.isBlack = !node.right.isBlack;
    }
    private void swapNodeIsBlack(RBTreeNode<T> nd1, RBTreeNode<T> nd2) {
        boolean tmp = nd1.isBlack;
        nd1.isBlack = nd2.isBlack;
        nd2.isBlack = tmp;
    }
    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    /**
     *
     * 针对节点的右子树是红色节点，并且右子树有还有一个红色子节点（右节点和左节点其中一个是），
     * 破坏了这个不变量  没有红色节点可以有红色的父节点（每个红色节点的父节点都是黑色的）
     *
     * */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        RBTreeNode<T> left = node.left;
        swapNodeIsBlack(node,left);
        node.left = left.right;
        left.right = node;
        return left;
    }

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     *
     * @param node
     * @return
     */
    /**
     * 针对一个节点的右孩子是红节点的情况，左旋该节点，交换该节点与左子节点的颜色，swap函数实现
     * 在旋转中，还会改变树的结构，节点的右子树会变为右子树的左子树，节点（树）会成为右子树的左子树
     *              node.right = right.left;right.left = node;
     * 最后返回这棵被修改的树
     *
     * */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        RBTreeNode<T> right = node.right;
        if (!node.isBlack && !right.isBlack) {
            node.right = right.left;
            right.left = node;
        } else {
            swapNodeIsBlack(node,right);
            node.right = right.left;
            right.left = node;
        }
        return right;
    }

    /**
     * Helper method that returns whether the given node is red. Null nodes (children or leaf
     * nodes) are automatically considered black.
     * @param node
     * @return
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    /**
     * Inserts the item into the Red Black Tree. Colors the root of the tree black.
     * @param item
     */
    public void insert(T item) {
        root = insert(root, item);
        root.isBlack = true;
     }

    /**
     * Inserts the given node into this Red Black Tree. Comments have been provided to help break
     * down the problem. For each case, consider the scenario needed to perform those operations.
     * Make sure to also review the other methods in this class!
     * @param node
     * @param item
     * @return
     */
    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
        // TODO: Insert (return) new red leaf node.
        if (node == null) {
            return new RBTreeNode<>(false,item);
        }
        // TODO: Handle normal binary search tree insertion.
        if (item.compareTo(node.item) > 0) {
            node.right = insert(node.right, item);
        }else if(item.compareTo(node.item)<0) node.left = insert(node.left,item);
        // TODO: Rotate left operation
        //如果树的右子节点不是黑的（是红的），左旋根节点
        if (isRed(node.right)&&!isRed(node.left)) {
            node = rotateLeft(node);
        }
        // TODO: Rotate right operation
        if (isRed(node.left)&&isRed(node.left.left)) {
            node = rotateRight(node);
        }
        // TODO: Color flip
        if (isRed(node.left)&&isRed(node.right)) {
            flipColors(node);
        }
        return node; //fix this return statement
    }

}
