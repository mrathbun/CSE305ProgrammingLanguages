//Mitchell Rathbun

/* a) Since r is passed in by value, the r variable that is operated on in 
 * the normalize function is a copy of the one in main. This means that no 
 * changes are actually made to the Rational r in main. By changing this to a 
 * reference, the values of r will be updated permanently. */

#include <stdio.h> 

typedef struct {
   int numr;
   int denr;
} RATIONAL;

int main() {
   RATIONAL r;
   int normalize(RATIONAL* r) {
      int gcd(int x, int y) {
      while (x != y)
         if (x > y)
            x = x-y;
         else y=y-x;
      return x;
      }
      int g = gcd(r->numr, r->denr);
      r->numr = r->numr/g;
      r->denr = r->denr/g;
   }
   r.numr = 77;
   r.denr = 88;
   normalize(&r);
   printf("%d/%d\n", r.numr, r.denr);
}

