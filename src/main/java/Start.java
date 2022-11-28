public class Start {
    public static void main(String[] args) {

        String a = "Hello";
        String b= "Word";
        String c =a+" " +b;
        System.out.println(c);
        String [] all = c.split(" ");
        System.out.println(all[0]);
        System.out.println(all[1]);
    }
}
