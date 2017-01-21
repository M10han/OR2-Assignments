class q2
{

//storing random variables in an array
public static double x[] = new double[1000];

public static void main(String[] args) 
{

	//Initializing the array with the random variables
	Initialization();

	//arg 1 is observed value and arg 2 is interval 
	chiSquare(1000,10);
}

//Chi-Square test(alpha = 0.05, n = 10, N = 1000, Ei = N/n = 100)
public static void Initialization()
{
	//Initialization
	x[0] = 0.46289452;
	x[1] = 0.74395023;

	int i = 2;
	while(i <= 999)
	{
		x[i] = x[i-1] + x[i-2];
		if (x[i] >= 1) 
		{
			x[i] = x[i] - 1.0;
		}
		i++;
	}
}

public static void chiSquare(int N, int n )
{
	double sum = 0.0;
	//Ei
	double Ei = N/n;
	for (double i = 0; i< n ; i++ ) 
	{
		double count = 0;
		for (int j = 0 ; j<N ; j++) 
		{
				if(x[j] >= i*0.1 && x[j]<(i+1)*0.1)
				{
					count++;
				}
		}
			System.out.println("Oi in the interval " + (i+1) + " = " + count);	
			sum += Math.pow((count - Ei),2);
	}

	System.out.println("Calculated Chi = " + sum/Ei);
	System.out.println("Chi-Square for alpha = 0.05 is 16.9");

	//Criticl value is (0.05, 9) is 16.9
	if (sum/Ei < 16.9) 
	{
		System.out.println("The hypothesis is not rejected.");
	}
	else
	{
		System.out.println("The hypothesis is rejected.");
	}

}

}
