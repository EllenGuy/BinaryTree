package com.eyes;

import java.util.Scanner;
import org.junit.Test;

public class BinaryTree2 {
	TreeNode root;
	boolean isLeft = true;
	//创建二叉�?
	public void createTree(int[] datas){
		for (int i : datas) insert(i);
	}

	public void insert(int data){
		if (root == null) {
			TreeNode node = new TreeNode();
			node.data = data;
			root = node;
		}else{
			insert(root, data);
		}
	}
	private void insert(TreeNode node, int data){
		//值重�?
		if (data == node.data) {
			System.out.println("值已存在，未插入");
		//大于，向右�?�归
		}else if(data > node.data){
			if (node.right == null) {
				TreeNode node1 = new TreeNode();
				node1.data = data;
				node.right = node1;
			}else insert(node.right, data);
		//小于，向左�?�归
		}else{
			if (node.left == null) {
				TreeNode node1 = new TreeNode();
				node1.data = data;
				node.left = node1;
			}else insert(node.left, data);
		}
	}
	public void update(int oldData, int newData){
		
	}

	public void delete(int data){
		TreeNode parentNode = findParentNodeByData(root, data);
		TreeNode node = null;
		if (parentNode == null) {
			System.out.println("删除时父节点为空");
			return;
		}
		if (isLeft)  node = parentNode.left;
		else node = parentNode.right;
		//叶子结点
		if (node.left == null && node.right == null) {
			if (isLeft) parentNode.left = null;
			else parentNode.right = null;
		//无左节点
		}else if (node.left == null) {
			if (isLeft) parentNode.left = node.right;
			else parentNode.right = node.right;
		//无右节点	
		}else if (node.right == null) {
			if (isLeft) parentNode.left = node.left;
			else parentNode.right = node.left;
		//既有左节点，又有右节�?
		}else{	
			//找到后继节点父节点，后继节点即可根据父节点的left找到
			TreeNode node2 = node.right.left;
			//后继节点就是其right节点
			if (node2 == null) {
				TreeNode node3 = node.right;
				node3.left = node.left;
				if (isLeft) parentNode.left = node3;
				else parentNode.right = node3;
			//后继节点不是right节点
			}else {
				//找到后继节点父节�?
				TreeNode afterNodeParent = findNextNodeParentByNode(node.right);
				//找到后继节点
				TreeNode afterNode = afterNodeParent.left;
				//�?始挪位置
				afterNodeParent.left = afterNode.right;
				afterNode.left = node.left;
				afterNode.right = node.right;
				if (isLeft) parentNode.left = afterNode;
				else parentNode.right = afterNode;
			}
		}		
	}
	
	//中序遍历
	public void midOrder(TreeNode node){
		if (node != null) {
			midOrder(node.left);
			System.out.println(node.data);
			midOrder(node.right);
		}
	}
	
	//找到父节�?
	public TreeNode findParentNodeByData(TreeNode node, int data){
		TreeNode node2 = node;
		TreeNode parentNode = null;
		while (node2 != null) {
			if (node2.data == data) break;
			else if (node2.data > data){
				parentNode = node2;
				node2 = node2.left;
				isLeft = true;
			}else {
				parentNode = node2;
				node2 = node2.right;
				isLeft = false;
			}
		}
		return parentNode;
	}
	
	//找到后继节点
	public TreeNode findNextNodeParentByNode(TreeNode node) {
		if (node == null) return null;	//只有第一次可能满�?
		else if(node.left == null) return null;	//只有第一次可能满�?
		else if (node.left.left == null) return node;
		else return findNextNodeParentByNode(node.left);
	}
	
	@Test
	public void test(){
		Scanner scan = new Scanner(System.in);
		while(scan.hasNext()){
			String[] strs = scan.nextLine().split(",");
			int[] ints = new int[strs.length];
			for (int i = 0; i < strs.length; i++) 
				ints[i] = Integer.parseInt(strs[i]);
			int num = scan.nextInt();
			createTree(ints);
			System.out.println("构建好的二叉树：");
			midOrder(root);
			delete(num);
			System.out.println("-----------");
			System.out.println("删除指定元素的二叉树�?");
			midOrder(root);
			root = null;
		}
	}
}

class TreeNode{
	TreeNode left;
	TreeNode right;
	int data;
}