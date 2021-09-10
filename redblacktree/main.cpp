#include <iostream>
#include <redblacktree.h>

using namespace std;

int main()
{
    redblacktree r;
    r.insertrb(15);
	r.insertrb(10);
	r.insertrb(20);
	r.insertrb(8);
	r.insertrb(66);
	r.deleterb(66);
	r.insertrb(12);
	r.insertrb(16);
	r.insertrb(25);
	r.insertrb(30);
	r.insertrb(85);
	r.deleterb(16);
	r.printrb();
	cout << "--------------------------------------------\n tree cleared!" << endl;
	r.clearrb();
	r.printrb();
    return 0;
}
