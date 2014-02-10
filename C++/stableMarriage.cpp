/*
Stable Marriage
Assignment#10 CS211
Angelica Contero
1D array with goto's
*/

# include <iostream>
using namespace std;

//funtion to check if current assignments are ok
bool ok(int q[], int mp[3][3], int wp[3][3], int c){
   for(int i=0; i<c; i++){
      if(q[i]==q[c]) return false;
   }
   //to check for instabilities in the match
   //instability means one partner prefers another and that other also prefers them over their current
   for(int i=0; i<c; i++){  //i = man indicated
      if(mp[c][q[i]]<mp[c][q[c]] && wp[q[i]][c]<wp[q[i]][i]) return false;
      if(mp[i][q[c]]<mp[i][q[i]] && wp[q[c]][i]<wp[q[c]][c]) return false;  
   }
   return true;
}

void backtrack(int &c){
   c--;
   if(c==-1){
      exit(1);
   }
}

//print 1D array solution with a count
void print(int q[], int size, int &count){
   	for(int i=0; i<size; i++){
           cout<<q[i]<<" ";
        }
        cout<<endl<<"Solution #"<<++count<<endl;
        cout<<endl;
}

int main(){
   int mp[3][3]={0,2,1,
                 0,2,1,
                 1,2,0};
   int wp[3][3]={2,1,0, 
                 0,1,2,
                 2,0,1};
   int n, c, count;
   cout<<"Please enter a size: ";
   cin>>n;
   int q[n];
   q[0]=0;
   count=0;
   c=1;   
   bool checkboard;
   // the boolean variable "from_backtrack" keeps track if we need
   // to reset the row to the top of the current column or not.
   bool from_backtrack=false;
   // The outer loop keeps looking for solutions
   // The program terminates from function backtrack
   // when we are forced to backtack into column -1
   while(true){
      while(c<n){
         // if we just returned from backtrack,use current value of q[c]
         // if not, get ready to start at the top of this column
         if(!from_backtrack){
            q[c]=0;
            checkboard = ok(q,mp,wp,c);
         }
         if(from_backtrack){
            q[c]++;
            checkboard = ok(q,mp,wp,c);
         }
         from_backtrack=false; // reset for the next time through
         // place woman assignment in this column or backtrack as required
         //if this position is ok, place the woman assignment here
         // and move on to the next column(or man),
         // otherwise keep looking in this column for a match
         while(q[c]<n){
            if(checkboard==true){
               c++;   
               q[c]=n+1; //just to get out of while loop
            }
	    if(checkboard==false){
               q[c]++;
               checkboard = ok(q,mp,wp,c);
            }
         }   
         //if the row = n, there is no valid match in this column
         // so backtrack and continue the loop in the previous column
         if(q[c]==n){
            backtrack(c);
            from_backtrack=true;	 
         } 
      }
      // one complete solution found, print it.
      if(c==n){
         print(q,n,count);
         //find the next place for the woman, going back as far as need be
         backtrack(c);
         from_backtrack=true;
      }      
   }
   return 0;
}

