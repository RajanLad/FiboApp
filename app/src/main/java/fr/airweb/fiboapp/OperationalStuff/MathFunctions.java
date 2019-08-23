package fr.airweb.fiboapp.OperationalStuff;

import java.util.ArrayList;

public class MathFunctions
{
        public static String fibos(int n)
        {
            // Fill Fibonacci numbers in f[] using
            int a1,a2,an;

                StringBuilder eachIter=new StringBuilder();
                a1=0;
                a2=1;
                eachIter.append(a2+" ");

                for(int j=1;j<n;j++)
                {
                    an=a1+a2;

                    eachIter.append(an+" ");
                    a1=a2;
                    a2=an;

                }

            return eachIter.toString();
        }

    }


