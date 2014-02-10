//memoized fibonacci program
# include <iostream>
using namespace std;

//instead of using a global variable, better to make the memoized arraay
//static so it can be modified only in the function
int memoized_fib(int memo[], int n){
   static int memo[20];
   memo[0]=0;
   memo[1]=1;
   for(int i = 2; i<=20; i++){
      memo[i] = -1;
   }
   if (memo[n]>-1) return memo[n];
   else memo[n] = memoized_fib(memo,n-1)+memoized_fib(memo,n-2);
      //return memo[n];    //don't need this line
}

int main(){
   //to print out fib numbers:
   for(int i=1; i<=20; i++)
      cout<<"fib("<<i<<") = "<<memoized_fib(memo,i)<<endl;
   return 0;
}
