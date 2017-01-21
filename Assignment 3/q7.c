#include <stdio.h>
#include <stdlib.h>
#include <math.h> 

#define RANDOM_MAX ((long int)RAND_MAX+1)

int main()
{
    int N = 1000, lamda = 1, seed = 55555;
    int intervals[11];
    double randnums[N], exp[N];
    srand(seed);

    for(int i=0; i<1000; i++)
    {
        randnums[i] = rand()/(double)RANDOM_MAX;
        exp[i]=(-1/lamda)*(log(1-randnums[i]));
    }    

    for(int i=0; i<11 ; i++)
    {
        int total = 0;
        for(int j=0 ; j<N; j++)
        {
            if(exp[j] >= i*0.5 && exp[j]<(i+1)*0.5)
            {
                total++;
            }
            intervals[i] = total; 
        }
        printf("\nOi value in [%f - %f) : %d",(i*0.5),((i+1)*0.5),total);
    }

    int total = 0;
    for(int i=0 ; i<11; i++)
    {
        total += intervals[i];
    }
    intervals[10] += N-total;
    for(int i=0 ; i<11; i++)
    {
        printf("\nRelative frequency in :%d is %f",i,(double)intervals[i]/N);
    }
    
    printf("\n");
}