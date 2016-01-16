//Mitchell Rathbun

#include <stdio.h>

int sigma(int *k, int low, int high, int expr()) {
   int sum = 0;
   for (*k=low; *k<=high; (*k)++) {
      sum = sum + expr();
   }
   return sum;
}

int main() {
   int i = 0, j = 0, k = 0;
   int thunk3() {
      return k;
   }
   int thunk2() {
      return (j) * sigma(&k, 0, 4, thunk3);
   }
   int thunk1() {
      return (i) * sigma(&j, 0, 4, thunk2); 
   }
 
   int answer = sigma(&i, 0, 4, thunk1);
   printf("%d\n", answer); 
}







