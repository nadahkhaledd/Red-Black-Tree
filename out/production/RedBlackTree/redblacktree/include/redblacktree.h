#ifndef REDBLACKTREE_H
#define REDBLACKTREE_H
#include <iostream>
using namespace std;

enum Colour {b, r, db};
struct Node {
Node* parent;
Node* left;
Node* right;
int value;
Colour colour;
};
typedef Node *node;

class redblacktree
{
    public:
        redblacktree();
        void insertrb(int);
        void deleterb(int);
        void printrb();
        void clearrb();
        virtual ~redblacktree();

    private:
        node root;
        node nill;

        void rotateleft(node);
        void rotateright(node);
        void insertcases(node);
        node mini(node);
        void replacenode(node , node);
        void print(node ,string, int);
        node Delete(node,int);
        void DeleteCases(node);
        void clearfirst(node);
};

#endif // REDBLACKTREE_H
