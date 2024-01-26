import java.util.*;
import java.io.*;

class array_length_short
{
    String blank_a[];

    double[][] shorti(double a[][],int l1, int l2)
    {
        double new_a[][]=new double[l1][l2];
        for(int i=0;i<l1;i++)
        {
            for(int j=0;j<l2;j++)
                new_a[i][j]=a[i][j];
        }
        return new_a;
    }

    String[][] shorti(String a[][],int l1, int l2)
    {
        String new_a[][]=new String[l1][l2];
        for(int i=0;i<l1;i++)
        {
            for(int j=0;j<l2;j++)
                new_a[i][j]=a[i][j];
        }
        return new_a;
    }

    String[][] shorti(String a[][],int l1)
    {
        String new_a[][]=new String[l1][a[0].length];
        for(int i=0;i<l1;i++)
        {
            for(int j=0;j<a[0].length;j++)
                new_a[i][j]=a[i][j];
        }
        return new_a;
    }

    String[] shorti(String a[],int l)
    {
        String new_a[]=new String[l];
        for(int i=0;i<l;i++)
        {
            new_a[i]=a[i];
        }
        return new_a;
    }

    double[] shorti(double a[],int l)
    {
        double new_a[]=new double[l];
        for(int i=0;i<l;i++)
        {
            new_a[i]=a[i];
        }
        return new_a;
    }

    int[] shorti(int a[],int l)
    {
        int new_a[]=new int[l];
        for(int i=0;i<l;i++)
        {
            new_a[i]=a[i];
        }
        return new_a;
    }

    String[][] shorti_db(String a[][],int l,int l1)
    {
        String new_a[][]=new String[l][l1];
        for(int i=0;i<l;i++)
        {
            for(int j=0;j<l1;j++)
            {
                new_a[i][j]=a[i][j];
            }

        }
        return new_a;
    }

    void pnt(String a[][])
    {
        System.out.println();   
        for(int i=0;i<a.length;i++)
        {
            for(int j=0;j<a[i].length;j++)
                System.out.print(a[i][j]+" | ");   
            System.out.println();
        }
        System.out.println();   
    }

    void pnt(String a[], int i1)
    {
        System.out.println();   
        for(int i=0;i<a.length;i++)
        {
            if(i1==1)
                System.out.println(a[i]+" | ");   
            else
                System.out.print(a[i]+" | ");  
        }
        System.out.println();   
    }

    void pnt(String a[])
    {
        System.out.println();   
        for(int i=0;i<a.length;i++)
        {
            System.out.print(a[i]+" | ");   
        }
        System.out.println();   
    }

    void pnt(double a[])
    {
        System.out.println();   
        for(int i=0;i<a.length;i++)
        {
            System.out.print(a[i]+" | ");   
        }
        System.out.println();   
    }

    void pnt(double a[][])
    {
        System.out.println();   
        for (double[] row : a) {
            for (double value : row) {
                System.out.print(value + " | ");
            }
            System.out.println(); // Move to the next line after printing each row
        }
        System.out.println();   
    }

    void pnt(String a[][],int l1,int l2)
    {
        System.out.println();   
        for(int i=0;i<l1;i++)
        {
            for(int j=0;j<l2;j++)
            {
                System.out.print(a[i][j]+" | ");
            }            
            System.out.println();
        }
        System.out.println();
    }

    void pnt_inte(int a[])
    {
        System.out.println();   
        for(int i=0;i<a.length;i++)
        {
            System.out.print(a[i]+" | ");   
        }
        System.out.println();   
    }

    double[] copy_to_double(String a[])
    {
        double ar[]=new double[a.length]; 
        for(int i=0;i<a.length;i++)
        {
            ar[i]=Double.valueOf(a[i]);   
        }
        return ar;
    }
    
    double[][] copy_to_double(String a[][])
    {
        double ar[][]=new double[a.length][a[0].length]; 
        for(int i=0;i<a.length;i++)
        {
            for(int j=0;j<a[i].length;j++)
            ar[i][j]=Double.valueOf(a[i][j]);   
        }
        return ar;
    }
    
    String[] copy_to_str(double a[])
    {
        String ar[]=new String[a.length]; 
        for(int i=0;i<a.length;i++)
        {
            ar[i]=Double.toString(a[i]);   
        }
        return ar;
    }
    
    String[][] copy_to_str(double a[][])
    {
        String ar[][]=new String[a.length][a[0].length]; 
        for(int i=0;i<a.length;i++)
        {
            for(int j=0;j<a[i].length;j++)
            ar[i][j]=Double.toString(a[i][j]);   
        }
        return ar;
    }

    String[] addi(String a1[],String a2[])
    {
        String new_a[]=new String[a1.length+a2.length];
        for(int i=0;i<a1.length;i++)
        {
            new_a[i]=a1[i];
        }
        for(int i=0;i<a2.length;i++)
        {
            new_a[a1.length+i]=a2[i];
        }
        return new_a;
    }

    String[] clr(String a[])
    {
        return blank_a;
    }

    String[] clr_ini(String a[])
    {
        for(int i=0;i<a.length;i++)
        {
            a[i]="";
        }
        return a;        
    }

    String[] str_to_arr(String s)
    {
        int len=s.length();
        String new_a[]=new String[len];
        for(int i=0;i<len;i++)
        {
            new_a[i]=s.charAt(i)+"";
        }
        return new_a;
    }

    String[] str_to_arr_dl(String s)
    {
        int len=s.length();int k=0,dl=0;
        String sh1="";
        String new_a[]=new String[len];
        for(int i=0;i<len;i++)
        {
            if(s.charAt(i)=='(')
            {
                dl=1;continue;
            }
            if(s.charAt(i)==')')
            {
                dl=0;new_a[k++]=sh1;sh1="";continue;
            }
            if(dl==1)
            {
                sh1=sh1+s.charAt(i);
            }
            if(dl==0)
                new_a[k++]=s.charAt(i)+"";
        }
        String final_a[]=shorti(new_a,k);
        System.out.println("final_a in str_to_arr_dl is ");
        pnt(final_a);
        return final_a;
    }

    String[] str_to_arr_ch(String s, char ch)
    {
        int len=s.length();
        int arr_l=0;
        String new_a[]=new String[len];
        new_a=clr_ini(new_a);
        for(int i=0;i<len;i++)
        {
            if(s.charAt(i)==ch)
                arr_l++;
            else
                new_a[arr_l]=new_a[arr_l]+s.charAt(i);
        }
        String final_a[]=shorti(new_a,arr_l+1);
        return final_a;
    }

    String[] str_to_arr_sep_ch(String s,char ch1,char ch2)
    {
        int len=s.length();
        int arr_l=-1;int k=0;
        String new_a[]=new String[len];
        new_a=clr_ini(new_a);
        for(int i=0;i<len;i++)
        {
            if(s.charAt(i)==ch1)
            {k=1;arr_l++;continue;}
            if(s.charAt(i)==ch2)
            {k=0;continue;}
            if(k==0)
                arr_l++;
            new_a[arr_l]=new_a[arr_l]+s.charAt(i);
        }
        String final_a[]=shorti(new_a,arr_l+1);
        return final_a;
    }

    String[] str_to_arr_with_ch(String s, char ch)
    {
        int len=s.length();
        int arr_l=-1;int k=0;
        String new_a[]=new String[len];
        new_a=clr_ini(new_a);
        for(int i=0;i<len;i++)
        {
            if(s.charAt(i)!=ch)
            {
                if(k==0)
                {
                    arr_l++;
                }
            }
            else
            {
                if(k==0)
                {
                    k=1;
                    arr_l++;
                    continue;
                }
                if(k==1)
                {
                    k=0;
                    continue;
                }
            }
            new_a[arr_l]=new_a[arr_l]+s.charAt(i);
        }
        String final_a[]=shorti(new_a,arr_l+1);
        return final_a;
    }

    String arr_to_str_dl(String ar[])
    {
        int len=ar.length;int k=0,dl=0;
        String s="";
        for(int i=0;i<len;i++)
        {
            s=s+'('+ar[i]+')';
        }
        return s;
    }

    double[] add_arr_db(double[] array1, double[] array2) {
        if (array1.length != array2.length) {
            throw new IllegalArgumentException("Arrays must be of equal length");
        }

        double[] sumArray = new double[array1.length];

        for (int i = 0; i < array1.length; i++) {
            sumArray[i] = array1[i] + array2[i];
        }

        return sumArray;
    }

    double[] add_arr_db(double[][] array2D) {
        int rows = array2D.length;
        int cols = array2D[0].length;

        double[] columnSums = new double[cols];

        for (int j = 0; j < cols; j++) {
            double sum = 0.0;
            for (int i = 0; i < rows; i++) {
                sum += array2D[i][j];
            }
            columnSums[j] = sum;
        }

        return columnSums;
    }

}
