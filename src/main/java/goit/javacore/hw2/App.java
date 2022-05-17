package goit.javacore.hw2;

public class App 
{
    public static void main( String[] args )
    {
        CartService cartService = new CartService();

        Double result = cartService.calculateTotalPrice(" ABCDABA");

        System.out.println("result = " + result);
    }
}
