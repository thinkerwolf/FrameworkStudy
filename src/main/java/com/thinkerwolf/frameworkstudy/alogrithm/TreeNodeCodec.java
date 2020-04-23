package com.thinkerwolf.frameworkstudy.alogrithm;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class TreeNodeCodec {
    /**
     * @param input
     * @return
     */
    public static TreeNode deserialize(String input) {
        input = input.trim();
        if (input.length() == 0) {
            return null;
        }
        String[] parts = input.split(",");
        String item = parts[0];
        TreeNode root = new TreeNode(Integer.parseInt(item));
        Deque<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int index = 1;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (index == parts.length) {
                break;
            }
            item = parts[index++];
            item = item.trim();
            if (!item.equals("null")) {
                int leftNumber = Integer.parseInt(item);
                node.left = new TreeNode(leftNumber);
                queue.add(node.left);
            }

            if (index == parts.length) {
                break;
            }
            item = parts[index++];
            item = item.trim();
            if (!item.equals("null")) {
                int rightNumber = Integer.parseInt(item);
                node.right = new TreeNode(rightNumber);
                queue.add(node.right);
            }
        }
        return root;
    }

    // Encodes a tree to a single string.
    public static String serialize(TreeNode root) {
        if (root == null) {
            return "{}";
        }
        ArrayList<TreeNode> queue = new ArrayList<TreeNode>();
        queue.add(root);
        for (int i = 0; i < queue.size(); i++) {
            TreeNode node = queue.get(i);
            if (node == null) {
                continue;
            }
            // 无需理会叶子结点是不是空，把当前非空结点的叶子结点添加到了队列中
            queue.add(node.left);
            queue.add(node.right);
        }

        // 去掉队列结尾的空结点
        while (queue.get(queue.size() - 1) == null) {
            queue.remove(queue.size() - 1);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append(queue.get(0).val);
        // 在for循环前已经将初值加入sb中,所以 i 初值应设为1
        for (int i = 1; i < queue.size(); i++) {
            if (queue.get(i) == null) {
                sb.append(",null");
            } else {
                sb.append(",");
                sb.append(queue.get(i).val);
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
