//  Assignment18_coan.cpp
//  with memoized array
//  Created by Angelica Contero on 5/1/12.

#include <iostream>
using namespace std;
const int rows = 5;
const int cols = 6;
int weight[rows][cols]={
    {3,4,1,2,8,6},
    {6,1,8,2,7,4},
    {5,9,3,9,9,5},
    {8,4,1,3,2,6},
    {3,7,2,8,6,4}};

int cost(int i, int j){ //i is the row, j is the col
    int static  memo_cost[rows][cols] = {0};
    int up, left, down;
    //base case;
    if (j==0)
        return weight[i][j];
    
    //if memoized value for cost exists, return that value
    if(memo_cost[i][j]!=0) return memo_cost[i][j];
    
    //recursive call
    else{
        left = cost(i,j-1);
        up = cost((i+rows-1)%5,j-1);
        down = cost((i+1)%5,j-1);
    }
    //find value of the shortest path through cell (i,j)
    int min = up;
    if(left<min) min = left;
    if(down<min) min = down;
    return memo_cost[i][j] = min + weight[i][j];
}//end of cost function

int main(){
    int ex[rows];
    for(int i = 0; i<rows; i++){
        ex[i] = weight[i][5];
    }
    //get the shortest path out of each entry on the rightmost column
    for(int i = 0; i<rows; i++)
        ex[i] = cost(i,cols-1); //finds cost of current entry of ex array
    //now find the smallest of the entries in the last column
    int min = 0;
    for(int i = 0; i<rows; i++)
        if(ex[i]<ex[min]) min = i;
    cout<<"The shortest path is of cost "<<ex[min]<<endl;
    
    return 0;
}

