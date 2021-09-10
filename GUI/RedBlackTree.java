package GUI;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


class GUI extends JFrame{
	
	public GUI()
	{
		RedBlackTree tree = new RedBlackTree();
		
		this.setTitle("RBT");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(1600,800);
		/*ImageIcon image = new ImageIcon("tree.png");
		this.setIconImage(image.getImage());*/
		
		JScrollPane scroll = new JScrollPane();
		scroll.setPreferredSize(new Dimension(1000, 500));
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(scroll, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		JLabel label1 = new JLabel("Red Black Tree..");
		label1.setForeground(Color.WHITE);
		panel.add(label1);
		
		tree.setSize(1600, 700);
		tree.setBackground(Color.GRAY);
		
		JTextField insertT = new JTextField(15);
		panel.add(insertT);
		JButton insertB = new JButton("Insert");
		insertB.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if (insertT.getText().equals(""))
					return;
				Integer x =Integer.valueOf(insertT.getText());
				tree.insert(x);
				tree.startDrawing(tree);
				repaint();
				
			}
		});
		panel.add(insertB);
		
		JTextField deleteT = new JTextField(15);
		panel.add(deleteT);
		JButton deleteB = new JButton("Delete");
		deleteB.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if (deleteB.getText().equals(""))
					return;
				Integer x =Integer.valueOf(deleteT.getText());
				tree.deleteNode(x);	
				tree.startDrawing(tree);
				repaint();
				
			}
		});
		panel.add(deleteB);
		
		JLabel label2 = new JLabel("OR");
		label2.setForeground(Color.WHITE);
		panel.add(label2);
		JButton clearB = new JButton("Clear");
		clearB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tree.clearTree();
				tree.startDrawing(tree);
				repaint();
			}
		});
		panel.add(clearB);
		new BorderLayout();
		this.add(panel,BorderLayout.NORTH);	
		this.add(tree);
		this.setVisible(true);
	}
}

class Node {
	
	public int data; 
	public Node parent; 
	public Node left; 
	public Node right; 
	public int color;
}


class myShape 
{
	int xStart; int yStart; int xEnd; int yEnd; int textStart; int textEnd;  String text=""; int colorr;
	int xleftStart; int yleftStart; int xleftEnd; int yleftEnd;
    int xrightStart; int yrightStart; int xrightEnd; int yrightEnd;
    
    public void drawNode(int xStart, int yStart, int xEnd, int yEnd, String text, int textStart, int textEnd,int colorr,
            int xleftStart, int yleftStart, int xleftEnd, int yleftEnd,
            int xrightStart, int yrightStart, int xrightEnd, int yrightEnd)
				{
						this.xStart = xStart;
						this.yStart = yStart;
						this.xEnd = xEnd;
						this.yEnd = yEnd;
						this.text = text;
						this.textStart = textStart;
						this.textEnd = textEnd;
						this.colorr = colorr;
						this.xleftStart = xleftStart;
						this.yleftStart = yleftStart;
						this.xleftEnd = xleftEnd;
						this.yleftEnd = yleftEnd;
						this.xrightStart = xrightStart;
						this.yrightStart = yrightStart;
						this.xrightEnd = xrightEnd;
						this.yrightEnd = yrightEnd;
				}
}

public class RedBlackTree extends JPanel{
	private Node root;
	private Node TNULL;
	public static ArrayList <myShape> llist = new ArrayList<myShape> ();
	
	public void clearTree()
	{
		root.left = TNULL;
		root.right = TNULL;
		root = TNULL;	
	}
	
	private Node searchTreeHelper(Node node, int key) {
		if (node == TNULL || key == node.data) {
			return node;
		}

		if (key < node.data) {
			return searchTreeHelper(node.left, key);
		} 
		return searchTreeHelper(node.right, key);
	}

	private void fixDelete(Node x) {
		Node s;
		while (x != root && x.color == 0) {
			if (x == x.parent.left) {
				s = x.parent.right;
				if (s.color == 1) {
					s.color = 0;
					x.parent.color = 1;
					leftRotate(x.parent);
					s = x.parent.right;
				}

				if (s.left.color == 0 && s.right.color == 0) {
					s.color = 1;
					x = x.parent;
				} else {
					if (s.right.color == 0) {
						s.left.color = 0;
						s.color = 1;
						rightRotate(s);
						s = x.parent.right;
					} 

					s.color = x.parent.color;
					x.parent.color = 0;
					s.right.color = 0;
					leftRotate(x.parent);
					x = root;
				}
			} else {
				s = x.parent.left;
				if (s.color == 1) {
					s.color = 0;
					x.parent.color = 1;
					rightRotate(x.parent);
					s = x.parent.left;
				}

				if (s.right.color == 0 && s.right.color == 0) {
					s.color = 1;
					x = x.parent;
				} else {
					if (s.left.color == 0) {
						s.right.color = 0;
						s.color = 1;
						leftRotate(s);
						s = x.parent.left;
					} 

					s.color = x.parent.color;
					x.parent.color = 0;
					s.left.color = 0;
					rightRotate(x.parent);
					x = root;
				}
			} 
		}
		x.color = 0;
	}

	private void rbTransplant(Node u, Node v){
		if (u.parent == null) {
			root = v;
		} else if (u == u.parent.left){
			u.parent.left = v;
		} else {
			u.parent.right = v;
		}
		v.parent = u.parent;
	}

	private void deleteNodeHelper(Node node, int key) {
		Node z = TNULL;
		Node x, y;
		while (node != TNULL){
			if (node.data == key) {
				z = node;
			}

			if (node.data <= key) {
				node = node.right;
			} else {
				node = node.left;
			}
		}

		if (z == TNULL) {
			System.out.println("Couldn't find key in the tree");
			return;
		} 

		y = z;
		int yOriginalColor = y.color;
		if (z.left == TNULL) {
			x = z.right;
			rbTransplant(z, z.right);
		} else if (z.right == TNULL) {
			x = z.left;
			rbTransplant(z, z.left);
		} else {
			y = minimum(z.right);
			yOriginalColor = y.color;
			x = y.right;
			if (y.parent == z) {
				x.parent = y;
			} else {
				rbTransplant(y, y.right);
				y.right = z.right;
				y.right.parent = y;
			}

			rbTransplant(z, y);
			y.left = z.left;
			y.left.parent = y;
			y.color = z.color;
		}
		if (yOriginalColor == 0){
			fixDelete(x);
		}
	}
	
	private void fixInsert(Node k){
		Node u;
		while (k.parent.color == 1) {
			if (k.parent == k.parent.parent.right) {
				u = k.parent.parent.left; 
				if (u.color == 1) {
					u.color = 0;
					k.parent.color = 0;
					k.parent.parent.color = 1;
					k = k.parent.parent;
				} else {
					if (k == k.parent.left) {
						k = k.parent;
						rightRotate(k);
					}
					k.parent.color = 0;
					k.parent.parent.color = 1;
					leftRotate(k.parent.parent);
				}
			} else {
				u = k.parent.parent.right; 

				if (u.color == 1) {
					u.color = 0;
					k.parent.color = 0;
					k.parent.parent.color = 1;
					k = k.parent.parent;	
				} else {
					if (k == k.parent.right) {
						k = k.parent;
						leftRotate(k);
					}
					k.parent.color = 0;
					k.parent.parent.color = 1;
					rightRotate(k.parent.parent);
				}
			}
			if (k == root) {
				break;
			}
		}
		root.color = 0;
	}

	public RedBlackTree() {
		TNULL = new Node();
		TNULL.color = 0;
		TNULL.left = null;
		TNULL.right = null;
		root = TNULL;
	}

	public Node searchTree(int k) {
		return searchTreeHelper(this.root, k);
	}

	public Node minimum(Node node) {
		while (node.left != TNULL) {
			node = node.left;
		}
		return node;
	}

	public Node maximum(Node node) {
		while (node.right != TNULL) {
			node = node.right;
		}
		return node;
	}

	public Node successor(Node x) {
		if (x.right != TNULL) {
			return minimum(x.right);
		}

		Node y = x.parent;
		while (y != TNULL && x == y.right) {
			x = y;
			y = y.parent;
		}
		return y;
	}

	public Node predecessor(Node x) {
	
		if (x.left != TNULL) {
			return maximum(x.left);
		}

		Node y = x.parent;
		while (y != TNULL && x == y.left) {
			x = y;
			y = y.parent;
		}

		return y;
	}

	public void leftRotate(Node x) {
		Node y = x.right;
		x.right = y.left;
		if (y.left != TNULL) {
			y.left.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == null) {
			this.root = y;
		} else if (x == x.parent.left) {
			x.parent.left = y;
		} else {
			x.parent.right = y;
		}
		y.left = x;
		x.parent = y;
	}

	public void rightRotate(Node x) {
		Node y = x.left;
		x.left = y.right;
		if (y.right != TNULL) {
			y.right.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == null) {
			this.root = y;
		} else if (x == x.parent.right) {
			x.parent.right = y;
		} else {
			x.parent.left = y;
		}
		y.right = x;
		x.parent = y;
	}
	
	public void insert(int key) {
		Node node = new Node();
		node.parent = null;
		node.data = key;
		node.left = TNULL;
		node.right = TNULL;
		node.color = 1; 

		Node y = null;
		Node x = this.root;

		while (x != TNULL) {
			y = x;
			if (node.data < x.data) {
				x = x.left;
			} else {
				x = x.right;
			}
		}

		node.parent = y;
		if (y == null) {
			root = node;
		} else if (node.data < y.data) {
			y.left = node;
		} else {
			y.right = node;
		}

		if (node.parent == null){
			node.color = 0;
			return;
		}

		if (node.parent.parent == null) {
			return;
		}

		fixInsert(node);
	}

	public Node getRoot(){
		return this.root;
	}

	public void deleteNode(int data) {
		deleteNodeHelper(this.root, data);
	}
	
	public void drawTree(Node tree,int numofLevels, int numofNode )
	{	
		int startWidth = (int) (1300 / Math.pow(2, numofLevels+1));
		int Height = (numofLevels+1)*20;	
		if(tree != TNULL)
		{
			String str = Integer.toString(tree.data);
			myShape obj = new myShape();
			obj.drawNode((startWidth-20+(startWidth*2*numofNode)) , Height , 50 , 50 
				    , str , (startWidth+ (startWidth*2*numofNode)) , (Height+30)
					, tree.color
					, (startWidth-20+((startWidth)*2*numofNode)) , (Height+25) , (startWidth/2 + startWidth*2*numofNode) , (Height*2)
					, (startWidth+30+(startWidth*2*numofNode)) , (Height+25) , (startWidth/2 + (startWidth*((numofNode*2)+1))) , (Height*2));
			llist.add(obj);
			drawTree(tree.left, numofLevels+1, 2*numofNode);
			drawTree(tree.right, numofLevels+1, 2*numofNode +1);
		}
		
	}
	
	public void startDrawing(RedBlackTree tree)
	{
		if(tree.getRoot() != TNULL)
		{
			drawTree(tree.getRoot(),0,0);
		}
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		for (int i=0; i<llist.size(); i++)
		{
			if(llist.get(i).colorr == 0)
				g2D.setColor(Color.BLACK);
			if(llist.get(i).colorr == 1)
				g2D.setColor(Color.RED);
			g2D.fillOval(llist.get(i).xStart, llist.get(i).yStart, llist.get(i).xEnd, llist.get(i).yEnd);
			g2D.setColor(Color.white);
			g2D.drawString(llist.get(i).text,llist.get(i).textStart,llist.get(i).textEnd);
			g2D.setStroke(new BasicStroke(3));
			g2D.setColor(Color.black);
			g2D.drawLine(llist.get(i).xleftStart, llist.get(i).yleftStart,llist.get(i).xleftEnd, llist.get(i).yleftEnd);
			g2D.setColor(Color.black);
			g2D.drawLine(llist.get(i).xrightStart, llist.get(i).yrightStart, llist.get(i).xrightEnd, llist.get(i).yrightEnd);
		}
		llist.clear();
	}
	
	public static void main(String [] args){
    	 new GUI();
	}
	
	
}