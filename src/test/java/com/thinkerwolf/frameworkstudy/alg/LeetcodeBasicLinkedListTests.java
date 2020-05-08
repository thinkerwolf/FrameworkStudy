package com.thinkerwolf.frameworkstudy.alg;

import org.junit.Test;

import java.util.*;

/**
 * 初级链表练习
 *
 * @author wukai
 * @date 2020/5/5 10:47
 */
public class LeetcodeBasicLinkedListTests {
    private static void printListNode(ListNode head) {
        StringBuilder sb = new StringBuilder("[");
        while (head != null) {
            sb.append(' ');
            sb.append(head.val);
            sb.append(',');
            head = head.next;
        }
        sb.append("]");
        System.out.println(sb.toString());
    }

    private static ListNode generateListNode(String s) {
        if (s == null) {
            throw new NullPointerException();
        }
        s = s.replaceAll("\\[", "");
        s = s.replaceAll("]", "");
        int pos = 0;
        ListNode head = new ListNode(0);
        ListNode pre = null;
        for (String v : s.split(",")) {
            int val = Integer.parseInt(v.trim());
            if (pos == 0) {
                head.val = val;
                pre = head;
            } else {
                pre.next = new ListNode(val);
                pre = pre.next;
            }
            pos++;
        }
        return head;
    }

    private static ListNode getTail(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        for (; ; ) {
            if (head.next == null) {
                return head;
            }
            head = head.next;
        }
    }

    public static ListNode getRandomNode(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        List<ListNode> list = new ArrayList<>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }
        int size = list.size();
        list.remove(size - 1);
        int idx = new Random().nextInt(size - 1);
        return list.get(idx);
    }

    @Test
    public void testBasic() {
        ListNode head = generateListNode("[1,2,3,4,5]");
        printListNode(head);
    }

    @Test
    public void testReverseList() {
        ListNode head = generateListNode("[1,2,3,4,5]");
        printListNode(reverseList2(head));
    }

    @Test
    public void testRemoveNthFromEnd() {
        ListNode head = generateListNode("[1,2,3,4,5]");
        printListNode(removeNthFromEnd(head, 2));
        head = generateListNode("[1,2,3,4,5]");
        printListNode(removeNthFromEnd2(head, 2));
    }

    @Test
    public void testMergeTwoLists() {
        ListNode l1 = generateListNode("[1, 4, 6]");
        ListNode l2 = generateListNode("[1, 2, 3]");
        printListNode(mergeTwoLists2(l1, l2));

        l1 = generateListNode("[4, 5, 6]");
        l2 = generateListNode("[1, 2, 3]");
        printListNode(mergeTwoLists2(l1, l2));

        l1 = generateListNode("[1, 4, 5]");
        l2 = generateListNode("[1, 2, 3, 6]");
        printListNode(mergeTwoLists2(l1, l2));
    }

    @Test
    public void testIsPalindrome() {
        ListNode head = generateListNode("[1,2,2,1]");
        System.out.println(isPalindrome(head));
        head = generateListNode("[1,2,1]");
        System.out.println(isPalindrome(head));
        head = generateListNode("[1,2,3,4]");
        System.out.println(isPalindrome(head));
    }

    @Test
    public void testHasCycle() {
        ListNode head = generateListNode("[1,2,3,5,6,9,10,11]");
        System.out.println(hasCycle(head));
        ListNode tail = getTail(head);
        ListNode r = getRandomNode(head);
        tail.next = r;
        System.out.println(hasCycle(head));
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (n == 1) {
            ListNode pre = null;
            ListNode node = head;
            for (; ; ) {
                if (node.next == null) {
                    break;
                }
                pre = node;
                node = node.next;
            }
            if (pre == null) {
                return null;
            }
            pre.next = node.next;
            node.next = null;
            return head;
        }
        int p = 0;
        for (ListNode node = head; node != null; node = node.next) {
            p++;
        }
        if (n == p) {
            ListNode node = head.next;
            head.next = null;
            return node;
        } else {
            ListNode pre = null;
            ListNode node = head;
            int i = 0;
            for (; ; ) {
                i++;
                if (i + n == p + 1) {
                    break;
                }
                pre = node;
                node = node.next;
            }
            pre.next = node.next;
            node.next = null;
            return head;
        }
    }

    public ListNode removeNthFromEnd2(ListNode head, int n) {
        Map<Integer, ListNode> map = new HashMap<>();
        int p = 0;
        for (ListNode node = head; node != null; node = node.next) {
            p++;
            map.put(p, node);
        }
        ListNode pre = map.get(p - n);
        if (pre == null) {
            ListNode node = head.next;
            head.next = null;
            return node;
        } else {
            ListNode node = map.get(p - n + 1);
            pre.next = node.next;
            node.next = null;
            return head;
        }
    }

    public ListNode reverseList(ListNode head) {
        ListNode[] nodes = new ListNode[2];
        nodes[0] = head;
        reverseListHelper(nodes);
        return nodes[1];
    }

    private void reverseListHelper(ListNode[] nodes) {
        ListNode node = nodes[0];
        if (node == null) {
            return;
        }
        if (node.next == null) {
            nodes[1] = node;
            return;
        }
        nodes[0] = nodes[0].next;
        reverseListHelper(nodes);
        nodes[0].next = node;
        node.next = null;
        nodes[0] = node;
    }

    public ListNode reverseList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode pre = null;
        ListNode node = head;
        for (; ; ) {
            ListNode tmp = node.next;
            node.next = pre;
            if (tmp == null) {
                return node;
            }
            pre = node;
            node = tmp;
        }
    }

    /**
     * 合并两个升序链表
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        }
        ArrayList<Integer> list = new ArrayList<>();
        ListNode t1 = l1;
        for (; t1 != null; ) {
            list.add(t1.val);
            if (t1.next == null) {
                break;
            }
            t1 = t1.next;
        }
        t1.next = l2;
        ListNode t2 = l2;
        for (; t2 != null; ) {
            list.add(t2.val);
            if (t2.next == null) {
                break;
            }
            t2 = t2.next;
        }
        Collections.sort(list);
        ListNode node = l1;
        for (Integer v : list) {
            node.val = v;
            node = node.next;
        }
        return l1;
    }

    public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        }
        ListNode pre = new ListNode(-1);
        ListNode head = pre;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                pre.next = l1;
                pre = l1;
                l1 = l1.next;
            } else {
                pre.next = l2;
                pre = l2;
                l2 = l2.next;
            }
        }
        if (l1 == null) {
            pre.next = l2;
        } else {
            pre.next = l1;
        }
        return head.next;
    }

    /**
     * 回文链表
     * 要求：O(N)时间复杂度，和O(1)空间复杂度
     * 提示：快慢指针
     *
     * @param head
     * @return
     */
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        // 1.快慢指针找到左半部分结尾，骚的一批
        ListNode fast = head;
        ListNode slow = head;
        for (; fast.next != null && fast.next.next != null; ) {
            fast = fast.next.next;
            slow = slow.next;
        }
        // 2.反转右半部分
        ListNode right = reverseList2(slow.next);
        // 3.对比左半部分和右半部分
        ListNode left = head;
        for (; right != null && right != slow; ) {
            if (left.val != right.val) {
                return false;
            }
            left = left.next;
            right = right.next;
        }
        return true;
    }

    /**
     * 回环链表
     * 要求：O(1)空间复杂度
     *
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            if (slow == fast.next.next) {
                return true;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return false;
    }

    static class ListNode {
        public int val;
        public ListNode next;

        ListNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            return val + "";
        }
    }

}
