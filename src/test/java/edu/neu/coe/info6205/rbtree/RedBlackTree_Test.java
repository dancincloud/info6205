package edu.neu.coe.info6205.rbtree;

import org.junit.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * a test of left leaning red-black tree
 *
 * @author Joseph Yuanhao Li
 * @date 4/15/21 23:57
 */
public class RedBlackTree_Test {
    @Test
    public void testBalance() {
        RedBlackTree<Integer, Integer> rbTree = new RedBlackTree();

        Random random = new Random();
        for(int i = 0; i < 255; i++){
            rbTree.put(random.nextInt(255), i);
        }

        assertTrue(rbTree.isBalanced());
    }

    @Test
    public void testBST() {
        RedBlackTree<Integer, Integer> rbTree = new RedBlackTree();

        Random random = new Random();
        for(int i = 0; i < 255; i++){
            rbTree.put(random.nextInt(255), i);
        }

        assertTrue(rbTree.isBST());
    }

    @Test
    public void test23() {
        RedBlackTree<Integer, Integer> rbTree = new RedBlackTree();

        Random random = new Random();
        for(int i = 0; i < 255; i++){
            rbTree.put(random.nextInt(255), i);
        }

        assertTrue(rbTree.is23());
    }

    @Test
    public void testContains(){
        RedBlackTree<Integer, Integer> rbTree = new RedBlackTree();
        Set<Integer> keys = new HashSet<>();

        Random random = new Random();
        for(int i = 0; i < 255; i++){
            int key = random.nextInt(1000);
            keys.add(key);
            rbTree.put(key, key * 31);
        }

        for(int i = 0; i < 255; i++){
            int key = random.nextInt(1000);
            assertTrue(rbTree.contains(key) == keys.contains(key));
        }
    }

    @Test
    public void testPut() {
        RedBlackTree<Integer, Integer> rbTree = new RedBlackTree();

        Random random = new Random();
        for(int i = 0; i < 255; i++){
            int key = random.nextInt(1000);
            rbTree.put(key, key * 31);
        }

        for(int i = 0; i < 255; i++){
            int key = random.nextInt(1000);
            if(rbTree.contains(key)){
                assertEquals(rbTree.get(key), key * 31, 0);
            }
        }

        assertTrue(rbTree.checkRBTree());
    }

    @Test
    public void testDelete() {
        RedBlackTree<Integer, Integer> rbTree = new RedBlackTree();

        Random random = new Random();
        for(int i = 0; i < 255; i++){
            rbTree.put(random.nextInt(1000), i * 31);
        }

        for(int i = 0; i < 10; i++){
            int key = random.nextInt();
            if(rbTree.contains(key)){
                rbTree.delete(key);

                assertTrue(!rbTree.contains(key));
            }
        }

        assertTrue(rbTree.checkRBTree());
    }
}
