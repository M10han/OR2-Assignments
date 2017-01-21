#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(void){
    double arr[5],rand_num;
    /*Given in question*/
    int lamda = 1;
    int i = 0;
    /*Setting seed*/
    int seed = 12345;
    srand(seed);
    printf("Seed is %d \n",seed);
    printf("Rand Max is %d \n",RAND_MAX);
    for(i=0;i<5;i++)
        {
            rand_num = ((float)rand()/(double)(RAND_MAX));
            arr[i]= (double)(-1/lamda)*log(1-rand_num);
            rand_num = 0;
            printf("   %f \n",arr[i]);
        }
}