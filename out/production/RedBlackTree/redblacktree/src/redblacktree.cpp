#include "redblacktree.h"
#include <iostream>
#include <queue>
using namespace std;

redblacktree::redblacktree()
{
    nill = new Node;
    nill->left = nullptr;
    nill->right = nullptr;
    nill->colour = b;
    root = nill;

}

void redblacktree::rotateleft(node n)
{
    node child = n->right;
    n->right = child->left;

    if(child->left != nill)
        child->left->parent = n;

    if(n->parent == nullptr)
        this->root = child;
    else if(n->parent->left == n)
        n->parent->left = child;
    else
        n->parent->right = child;

    child->parent = n->parent;
    child->left = n;
    n->parent = child;
}

void redblacktree::rotateright(node n)
{
    node child = n->left;
    n->left = child->right;

    if(child->right != nill)
       child->right->parent= n;

    if(n->parent == nullptr)
        this->root = child;
    else if( n->parent->left == n)
        n->parent->left = child;
    else
         n->parent->right= child;

    child->parent = n->parent;
    child->right = n;
    n->parent = child;
}

void redblacktree::replacenode(node x, node y)
{
    if (x->parent == nullptr)
			root = y;

    y = (x == x->parent->left) ? x->parent->left : x->parent->right;
    y->parent = x->parent;
}

node redblacktree::mini(node n)
{
    while (n->left != nill) {
			n = n->left;
		}
		return n;
}

void redblacktree::insertcases(node n)
{
    node uncle;
		while(n->parent->colour == r) {
			if(n->parent == n->parent->parent->right) ///parent is a left child
            {
                uncle = n->parent->parent->right;

				if(uncle->colour == r)
                {
					uncle->colour = b;
					n->parent->colour = b;
					n->parent->parent->colour = r;
					n = n->parent->parent;
				}
				else
                {
					if(n == n->parent->right)
					{
						n = n->parent;
						rotateleft(n);
					}

					n->parent->colour = b;
					n->parent->parent->colour = r;
					rotateright(n->parent->parent);
				}
			}
			else ///parent is a right child
            {
				uncle = n->parent->parent->left;
				if(uncle->colour == r)
				{
					uncle->colour = b;
					n->parent->colour = b;
					n->parent->parent->colour = r;
					n = n->parent->parent;
				}
                else
                {
					if(n == n->parent->left)
					{
						n = n->parent;
						rotateright(n);
					}
					n->parent->colour = b;
					n->parent->parent->colour = r;
					rotateleft(n->parent->parent);
				}
			}
			if (n == root)
				break;
		}
		root->colour = b;
}

void redblacktree::insertrb(int v)
{
        node n = new Node;
		n->parent = nullptr;
		n->right = nill;
		n->left = nill;
		n->colour = r;
		n->value = v;

		node temp = this->root;
		node p = nullptr;


		while (temp != nill)
        {
			p = temp;
			if ((n->value) > (temp->value))
				temp = temp->right;
			 else
				temp = temp->left;
		}

		n->parent = p;
		if (n->parent == nullptr)
        {
            root = n;
            n->colour = b;
			return;
        }

        else if ((n->value) > (p->value))
			p->right = n;
		 else
			p->left = n;

		insertcases(n);
}

node redblacktree::Delete(node n,int v)
{
    if(n == nill) //node doesn't exist
        return n;

    if(n->right==nill||n->left==nill) //node has one child or no children
        return n;
    if(v > n->value) // go right
        return Delete(n->right,v);
    if(v < n->value) // go left
        return Delete(n->left,v);

    node minm =mini(n->right); // then we get the minimum node in the tree and delete its value
    n->value=minm->value;
    return Delete(n->right,minm->value);
}

void redblacktree::DeleteCases(node n) {
    if (n == nill) // node or value doesn't exist
        return;

    if (n == root) // delete the root so we make it a nill node
    {
        root = nill;
        return;
    }

    if (n->colour == r || n->right->colour == r || n->left->colour ==r) /// node and children red
    {
        node child;
        if (n->right != nill)
            child = n->right;
        else
            child = n->left;

        if (n == n->parent->right) // node is a right child
        {
            n->parent->right = child; // replace node with its child
            if (child != nill)
                child->parent = n->parent;
            child->colour = b;
            delete (n); ///node replacement prepared then node is deleted


        }
        else // node is a left child
        {
           n->parent->left = child; //replace the node with its child
           if (child != nill)
               child->parent = n->parent; //modify relations
           child->colour = b;
           delete (n); ///node replacement prepared then node is deleted
        }
    }
    else
    {
        node hold = n;
        node s = nill;
        hold->colour = db;
        while (hold != root && hold->colour == db)
        {
            if (n == n->parent->left) // node is a left child
            {
                s = n->parent->right;
                if (s->colour == r)
                {
                    s->colour = b;
                    n->parent->colour = r;
                    rotateleft(n->parent);
                }
                else
                {
                    if (s->right->colour == b && s->left->colour == b)
                    {
                        s->colour = r;
                        if(n->parent->colour == r)
                            n->parent->colour = b;
                        else
                            n->parent->colour = db;
                        hold = n->parent;
                    }
                    else
                    {
                        if (s->right->colour == b)
                        {
                            s->left->colour = b;
                            s->colour = r;
                            rotateright(s);
                            s = n->parent->right;
                        }
                        s->colour = n->parent->colour;
                        n->parent->colour = b;
                        s->right->colour =  b;
                        rotateleft(n->parent);
                        break;
                    }
                }
            }

            else  //node is a right child
            {
                s = n->parent->left;
                if (s->colour == r)
                {
                    s->colour = b;
                    n->parent->colour= r;
                    rotateright(n->parent);
                }
                else
                {
                    if (s->left->colour == b && s->right->colour == b)
                    {
                        s->colour = r;
                        if (n->parent->colour == r)
                            n->parent->colour = b;
                        else
                            n->parent->colour =  db;
                        hold = n->parent;
                    }
                    else
                    {
                        if (s->left->colour == b)
                        {
                            s->right->colour = b;
                            s->colour = r;
                            rotateleft(s);
                            s = n->parent->left;
                        }
                        s->colour = n->parent->colour;
                        n->parent->colour = b;
                        s->left->colour = b;
                        rotateright(n->parent);
                        break;
                    }
                }
            }
        }

        if (n == n->parent->right)
            n->parent->right = nill;
        else
            n->parent->left = nill;

        delete(n);
        root->colour = b;
    }
}

void redblacktree::deleterb(int v)
{
    node re=Delete(root,v);
    DeleteCases(re);
}

void redblacktree::print(node nn , string c , int level)
{
    queue<node> q;

    if (nn != nill)
    {
       q.push(nn);
        while(!q.empty())
        {
            int counter = q.size();

            while(counter > 0)
            {
                node n = q.front();
               if(n->colour == r)
                    cout << "(" << level << ")  '" <<  c << "' " << n->value << " (RED)";
                else if(n->colour == b)
                    cout << "(" << level << ")  '" <<  c << "' " <<n->value << " (BLACK)";
                q.pop();
                cout << "\n\n";
                if (n->left != NULL)
                    print(n->left , "<-", (level+1));
                if (n->right != NULL)
                    print(n->right, "->", (level+1));
                counter--;
            }

        }
    }


}

void redblacktree::printrb()
{
    if(root)
        print(this->root, "root", 1);
}

void redblacktree::clearfirst(node n)
{
    if(n == this->nill)
        return;

    clearfirst(n->left);
    clearfirst(n->right);

    if(n == this->root)
        return;
    if(n->left == this->nill && n->right == this->nill)
    {
        if(n->parent->left == n)
            n->parent->left = this->nill;
        else
            n->parent->right = this->nill;

        delete n;
    }
}

void redblacktree::clearrb()
{
    clearfirst(this->root);
    root = this->nill;

}

redblacktree::~redblacktree()
{
    //dtor
}
