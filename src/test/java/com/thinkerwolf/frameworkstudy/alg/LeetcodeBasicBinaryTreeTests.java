package com.thinkerwolf.frameworkstudy.alg;

import com.thinkerwolf.frameworkstudy.alogrithm.TreeNode;
import com.thinkerwolf.frameworkstudy.alogrithm.TreeNodeCodec;
import com.thinkerwolf.frameworkstudy.common.Util;
import org.junit.Test;

import java.util.*;

/**
 * Leetcode二叉树练习
 *
 * @author wukai
 * @date 2020/4/23 10:34
 */
public class LeetcodeBasicBinaryTreeTests {

    @Test
    public void testPreOrder() {
        TreeNode root = TreeNodeCodec.deserialize("10,8,12,2,9");
        List<Object> list = new ArrayList<>();
        preOrderRecursive(root, list);
        System.out.println("Pre Recursive " + list);
        list.clear();
        preOrderIteration(root, list);
        System.out.println("Pre Iteration " + list);
    }


    @Test
    public void testInOrderTest() {
        TreeNode root = TreeNodeCodec.deserialize("10,8,12,2,9");
        List<Object> list = new ArrayList<>();
        inOrderRecursive(root, list);
        System.out.println("In Recursive " + list);
        list.clear();
        inOrderIteration(root, list);
        System.out.println("In Iteration " + list);
    }

    @Test
    public void testPostOrder() {
        TreeNode root = TreeNodeCodec.deserialize("10,8,12,2,9");
        List<Object> list = new ArrayList<>();
        postOrderRecursive(root, list);
        System.out.println("Post Recursive " + list);
        list.clear();
        postOrderIteration(root, list);
        System.out.println("Post Iteration " + list);
    }

    /**
     * 前序遍历 根-左-右
     */
    public void preOrderRecursive(TreeNode root, List<Object> list) {
        if (root == null) return;
        list.add(root.val);
        preOrderRecursive(root.left, list);
        preOrderRecursive(root.right, list);
    }

    /**
     * 中序遍历 左中右
     */
    public void inOrderRecursive(TreeNode root, List<Object> list) {
        if (root == null) return;
        inOrderRecursive(root.left, list);
        list.add(root.val);
        inOrderRecursive(root.right, list);
    }

    /**
     * 后序遍历 左右中
     */
    public void postOrderRecursive(TreeNode root, List<Object> list) {
        if (root == null) return;
        postOrderRecursive(root.left, list);
        postOrderRecursive(root.right, list);
        list.add(root.val);
    }

    public void preOrderIteration(TreeNode root, List<Object> list) {
        if (root == null) return;
        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode t = stack.pop();
            list.add(t.val);
            if (t.right != null) {
                stack.push(t.right);
            }
            if (t.left != null) {
                stack.push(t.left);
            }
        }
    }

    public void inOrderIteration(TreeNode root, List<Object> list) {
        if (root == null) return;
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode ct = root;
        while (ct != null || !stack.isEmpty()) {
            while (ct != null) {
                stack.push(ct);
                ct = ct.left;
            }
            TreeNode t = stack.pop();
            list.add(t.val);
            if (t.right != null) {
                ct = t.right;
            }
        }
    }

    public void postOrderIteration(TreeNode root, List<Object> list) {
        if (root == null) return;
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode curr = root;
        TreeNode last = null;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.peek();
            if (curr.right == null || curr.right == last) {
                list.add(curr.val);
                stack.pop();
                last = curr;
                curr = null;
            } else {
                curr = curr.right;
            }
        }
    }

    /**
     * 验证二叉搜索树
     *
     * @param root
     * @return
     */
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        // 左中右，中序遍历
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode last = null;
        TreeNode ct = root;
        while (ct != null || !stack.isEmpty()) {
            while (ct != null) {
                stack.push(ct);
                ct = ct.left;
            }
            TreeNode t = stack.pop();
            if (last != null) {
                if (!Util.less(last.val, t.val)) {
                    return false;
                }
            }
            last = t;
            if (t.right != null) {
                ct = t.right;
            }
        }
        return true;
    }

    /**
     * 对称二叉树 .不可使用遍历法，层序遍历？？？
     *
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isMirror(root.left, root.right);
    }

    private boolean isMirror(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return true;
        else if (t1 == null || t2 == null) return false;

        return t1.val == t2.val && isMirror(t1.left, t2.right) && isMirror(t1.right, t2.left);
    }

}
