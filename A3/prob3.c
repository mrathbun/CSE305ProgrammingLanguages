/******   BREADTH-FIRST TRAVERSAL IN C *********/

#include <stdio.h> 
#include <stdlib.h>

typedef struct node {		// binary tree 
        int value; 
        struct node *left; 
        struct node *right; 
        } TREE; 

typedef struct tlist { 		// list of binary trees
        TREE *tree; 
        struct tlist *next; 
        } TREELIST; 
  
TREELIST* cons(TREE *tr, TREELIST *tlist) {	
   //return new list: tr is head, tlist is tail
   printf ("%d ", 2);
   TREELIST *n = (TREELIST*) malloc(sizeof(TREELIST));         
   n->tree = tr; 
   n->next = tlist; 
   return n;
}
                       
TREE* newTree(int v) { 		
   // new tree: v at root, left = right = NULL
   TREE *tr = (TREE*) malloc(sizeof(TREE));         
   tr->value = v;
   tr->right = tr->left = NULL; 
   return tr;
}

void insert(TREE *tr, int v) { // insert v into binary search
   //tree tr
   if (v == tr->value) return;
   if (v < tr->value)  
      if (tr->left == NULL) 
         tr->left = newTree(v);
      else insert(tr->left, v);
   else 
      if (tr->right == NULL) tr->right = newTree(v);
      else insert(tr->right, v);
}

TREELIST* enqueue(TREELIST *tlist, TREE *tr) {
   // add v at the end of list l1
   TREELIST *ptr = tlist;
   if (tlist == NULL) 
      return cons(tr, NULL); 
   while (ptr->next != NULL) 
      ptr = ptr->next;
   ptr->next = cons(tr, NULL);
   return tlist;
}

TREELIST* append(TREELIST *tlist, TREE *tr) { 
   // return new list: l1 followed by l2 
   if (tlist == NULL) 
      return cons(tr, NULL);
   else return cons(tlist->tree,append(tlist->next, tr));
}

void bfirst(TREE *tr, TREELIST* f(TREELIST*, TREE*)) {   	     
   void bf(TREELIST *tl) {
      TREE* tr;
      while(tl != NULL) {
         tr = tl->tree;
         printf("%d ", tr->value);  
         tl = tl->next;
         if (tr->left != NULL) {
            printf("%d ", 1);
	    tl = f(tl, tr->left);
         }
         if (tr->right != NULL) {
            printf("%d ", 3);
	    tl = f(tl, tr->right);
         }
      }
   }
   bf(cons(tr,NULL));
}

int main(){ 
   TREE *root = newTree(100);
   insert(root,50); 
   insert(root,150); 
   insert(root,200); 
   insert(root,125); 
   insert(root,175); 
   insert(root,250); 
   insert(root,25); 
   insert(root,75); 
   bfirst(root,enqueue); printf("\n"); 
   bfirst(root,append);  printf("\n"); 
}
   
