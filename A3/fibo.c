#include <stdio.h>

int fibo() {
   static int first = 1;
   static int second = 0;

   int temp;

   temp = second;
   second = first + second;
   first = temp;

   return second;
}

int main() {
   int i = 0;
   for(i; i < 10; i++) {
      printf("%d\n", fibo());  
   } 
}
