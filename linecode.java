import java.util.*;
public class linecode{
    static void unipolar(int[]data){
       System.out.println("Unipolar Encoding: ");
       for(int bit:data){
        if(bit==1)
        System.out.print(" |-| ");
       
       else
       System.out.print(" |_|  ");
       }
        System.out.println();
       
    }
    public static void main (String []args){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the number of bits: ");
        int n=sc.nextInt();
        int[] data = new int[n];
        System.out.println("Enter the bits: ");
        for(int i=0;i<n;i++)
         data[i]=sc.nextInt();
        unipolar(data);
        sc.close();
        }
}