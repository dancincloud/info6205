package edu.neu.coe.info6205.rbtree;

import java.util.Random;

/**
 * implement left leaning red-black tree
 *
 * @author Joseph Yuanhao Li
 * @date 4/15/21 23:55
 */
public class RedBlackTree<Key extends Comparable<Key>,  Value> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    public class RBTNode {
        private Key key;
        private Value val;
        private boolean color; // red or black

        private RBTNode left, right;

        public RBTNode(Key key, Value value, boolean color){
            this.key = key;
            this.val = value;
            this.color = color;
        }

        /* API for Visualization */
//        public void draw(Graphics g, int x, int y, int radius){
//            g.setColor(new Color(color == RED ? 0xff0000 : 0x000000));
//            g.drawOval(x, y, radius, radius);
//            g.drawString(key.toString(), x + 2, y + radius / 3 * 2);
//        }
    }

    private RBTNode root; // root of black-red tree

    public boolean isEmpty(){
        return root == null;
    }

    public boolean contains(Key key){
        return get(key) != null;
    }

    private boolean isRed(RBTNode node){
        return node != null && node.color == RED;
    }

    public int height(){
        if(root == null) return 0;
        return height(root);
    }

    private int height(RBTNode node){
        if(node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    public Value get(Key key){
        RBTNode curt = root;
        while (curt != null){
            int cmp = key.compareTo(curt.key);
            if(cmp < 0) curt = curt.left;
            else if(cmp > 0) curt = curt.right;
            else return curt.val;
        }

        return null;
    }

    public void put(Key key, Value value){
        root = put(key, value, root);
        root.color = BLACK;
    }

    private RBTNode put(Key key, Value value, RBTNode node){
        if(node == null) return new RBTNode(key,value, RED);


        int cmp = key.compareTo(node.key);
        if(cmp < 0) node.left = put(key, value, node.left);
        else if(cmp > 0) node.right = put(key, value, node.right);
        else node.val = value;

        if(isRed(node.right)) node = rotateLeft(node);

        if(isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);

        if(isRed(node.left) && isRed(node.right)) flipColors(node);

        return node;
    }

    private RBTNode rotateLeft(RBTNode node) {
        if(node.right == null){
            throw new IllegalArgumentException("Rotate left error: can't find new parent node");
        }

        RBTNode parent = node.right;
        node.right = parent.left;
        parent.left = node;

        parent.color = node.color;
        node.color = RED;

        return parent;
    }


    private RBTNode rotateRight(RBTNode node){
        if(node.left == null){
            throw new IllegalArgumentException("Rotate left error: can't find new parent node");
        }

        RBTNode parent = node.left;
        node.left = parent.right;
        parent.right = node;

        parent.color = node.color;
        node.color = RED;
        return parent;
    }

    private RBTNode flipColors(RBTNode node){
        node.color = !node.color;
        if(node.left != null) node.left.color = !node.left.color;
        if(node.right != null) node.right.color = !node.right.color;
        return node;
    }

    public void deleteMax(){
        root = deleteMax(root);
        root.color = BLACK;
    }

    private RBTNode deleteMax(RBTNode node){
        if (isRed(node.left)) node = rotateRight(node);

        if(node.right == null) return null;

        if(!isRed(node.right) && isRed(node.right.left)) node = moveRedRight(node);

        node.left = deleteMax(node.left);

        return fixUp(node);
    }

    private RBTNode fixUp(RBTNode node){
        if(isRed(node.right)) node = rotateLeft(node);
        if(isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
        if(isRed(node.left) && isRed(node.right)) flipColors(node);

        return node;
    }

    private RBTNode moveRedRight(RBTNode node){
        flipColors(node);
        if(isRed(node.left.left)){
            node = rotateRight(node);
            flipColors(node);
        }

        return node;
    }

    private RBTNode moveRedLeft(RBTNode node){
        flipColors(node);
        if(isRed(node.right.left)){
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
            flipColors(node);
        }

        return node;
    }

    public void deleteMin(){
        root = deleteMin(root);
        root.color = BLACK;
    }

    private RBTNode deleteMin(RBTNode node){
        if(node.left == null) return null;

        if(!isRed(node.left) && !isRed(node.left.left)){
            node = moveRedLeft(node);
        }

        node.left = deleteMin(node.left);

        return fixUp(node);
    }

    public void delete(Key key){
        if(key == null) throw new IllegalArgumentException("delete: key can't be null");

        if(!isRed(root.left) && !isRed(root.right)) root.color = RED;

        root = delete(root, key);
        if(root != null) root.color = BLACK;
    }

    private RBTNode delete(RBTNode node, Key key){
        int cmp = key.compareTo(node.key);
        if(cmp < 0){
            if(!isRed(node.left) && !isRed(node.left.left)) node = moveRedLeft(node);
            node.left = delete(node.left, key);
        }else{
            if(isRed(node.left)) node = rotateRight(node);
            if(cmp == 0 && node.right == null) return null;
            if(!isRed(node.right) && !isRed(node.right.left)) node = moveRedRight(node);
            if(cmp == 0){
                RBTNode minNode = min(node.right);
                node.key = minNode.key;
                node.val = minNode.val;

                node.right = deleteMin(node.right);
            }else{
                node.right = delete(node.right, key);
            }
        }

        return fixUp(node);
    }


    public Key min() {
        if (isEmpty()) throw new IllegalArgumentException("min: Tree is empty");
        return min(root).key;
    }

    private RBTNode min(RBTNode x) {
        if (x.left == null) return x;
        else return min(x.left);
    }

    public Key max() {
        if (isEmpty()) throw new IllegalArgumentException("max: Tree is empty");
        return max(root).key;
    }

    private RBTNode max(RBTNode x) {
        if (x.right == null) return x;
        else return max(x.right);
    }

    /* API for Visualization */
//    public void drawTree(Graphics g){
//        Font font = new Font("TimesRoman", Font.PLAIN, 10);
//        g.setFont(font);
//
//        Queue<RBTNode> q = new LinkedList<>();
//        root.index = 1;
//        q.offer(root);
//
//        int height = height();
//        System.out.printf("height = %d\n", height);
//
//        int x, y = 0, radius = 20;
//        int level = 0;
//        while (!q.isEmpty()){
//            int sz = q.size();
//
//            int count = (int) Math.pow(2, level);
//            x = 700 - count / 2 * 30;
//
//            if(count % 2 == 0) x += 15;
//
//            for(int i = 0; i < sz; i++){
//                RBTNode node = q.poll();
//
//                node.draw(g, x + (node.index - count) * 30, y, radius);
//
//
//                if(node.left != null){
//                    node.left.index = node.index * 2;
//                    q.offer(node.left);
//                }
//
//                if(node.right != null){
//                    node.right.index = node.index * 2 + 1;
//                    q.offer(node.right);
//                }
//            }
//
//            y += 50;
//            level++;
//        }
//    }

    /* Methods used for test */
    public boolean isBalanced() {
        int black = 0;
        RBTNode node = root;
        while (node != null) {
            if (!isRed(node)) black++;
            node = node.left;
        }
        return isBalanced(root, black);
    }

    private boolean isBalanced(RBTNode node, int black) {
        if (node == null) return black == 0;
        if (!isRed(node)) black--;
        return isBalanced(node.left, black) && isBalanced(node.right, black);
    }


    public boolean isBST() {
        return isBST(root, null, null);
    }

    private boolean isBST(RBTNode x, Key min, Key max) {
        if (x == null) return true;
        if (min != null && x.key.compareTo(min) <= 0) return false;
        if (max != null && x.key.compareTo(max) >= 0) return false;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }


    public boolean is23() { return is23(root); }
    private boolean is23(RBTNode x) {
        if (x == null) return true;
        if (isRed(x.right)) return false;
        if (x != root && isRed(x) && isRed(x.left))
            return false;
        return is23(x.left) && is23(x.right);
    }

    public boolean checkRBTree(){
        return isBalanced() && isBST() && is23();
    }

    public static void main(String[] args){
        RedBlackTree<Integer, Integer> rbTree = new RedBlackTree();

        Random random = new Random();
//        for(int i = 0; i < 20; i++){
//            rbTree.put(random.nextInt(255), i);
//        }
        for(int i = 0; i < 10; i++){
            rbTree.put(i, i);
        }

        System.out.println(rbTree.height());


//        RBTPanel p = new RBTPanel(rbTree);
//        Thread panelThread = new Thread(p);
//        JFrame frame = new JFrame();
//        frame.add(p);
//        frame.setSize(  1400, 1000);
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//        frame.setTitle("SEIR-Model Simulation");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        panelThread.start();//开启画布线程，即世界线程，接着看代码的下一站可以转MyPanel.java
    }
}
