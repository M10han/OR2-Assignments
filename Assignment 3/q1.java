import java.util.Arrays;
import java.util.Collections;
public class q1 
{
    
    public static void main(String args[])
    {
        
        double Ri[] = new double[10];
        double Ci[] = new double[1000];
        double Ni[] = new double[]{0.00,0.10,0.20,0.30,0.40,0.50,0.60,0.70,0.80,0.90,1.00};
        double Ei[] = new double[]{100,100,100,100,100,100,100,100,100,100};
        double Oi[] = new double[10];
        double OEi[] = new double[10];
        double OEi_s[] = new double[10];
        double Chi[] = new double[10];
        double Dplus[] = new double[10];
        double Dminus[] = new double[10];
        double Total = 0.00;
        double Dmax = 0.00;
        double Dalpha = 0.410;
        double Xsquare = 16.9;
        
        //Kolmogorv-Smirnov
        for (int i=0; i<10; i++)
        {
            Ri[i]=Math.random();
        }
        //Sort array Ri
        Arrays.sort(Ri);
        
        for (int j=0; j<10; j++)
        {
            Dplus[j] = Ni[j+1] - Ri[j];
            Dminus[j] = Ri[j] - Ni[j];
        }
        //Sort Dplus and Dminus
        Arrays.sort(Dplus);
        Arrays.sort(Dminus);    
        Dmax = Dplus[9] > Dminus[9] ? Dplus[9] : Dminus[9];


        System.out.println("Kolmogorv-Smirnov");
        if (Dmax < Dalpha)
            {
                System.out.println("Ho is not rejected");
                System.out.println("For alpha = 0.05 , D(alpha) = 0.410 , N = "+Dmax);
            }
        else
            {
                System.out.println("Ho is rejected");
                System.out.println("For alpha = 0.05 , D(alpha) = 0.410 , N = "+Dmax);
            
            }

        //Chi-Square Test
        for (int k=0; k<10; k++)
        {
            for (int l=0; l<1000; l++)
            {
                Ci[l]=Math.random();
                if(Ci[l]> Ni[k] && Ci[l]<= Ni[k+1]) 
                {
                   Oi[k] = Oi[k]+1;
                }
            }
        }
        
        for (int m=0; m<10; m++)
        {
            OEi[m] = Oi[m] - Ei[m];
            OEi_s[m] = OEi[m]*OEi[m];
            Chi[m] = OEi_s[m]/Ei[m];
            Total = Total + Chi[m];
        }
        System.out.println("Chi-Square");
        if (Total < Xsquare)
            {
                System.out.println("Ho is not rejected");
                System.out.println("For alpha = 0.05, Xsquare = 18.3, X = "+Total);
            }
        else
            {
                System.out.println("Ho is rejected");
                System.out.println("For alpha = 0.05, Xsquare = 18.3, X = "+Total +" is greater than actual value"); 
            }
    }
}