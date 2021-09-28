public class Exercise1_2_3 {
    public static void main(String[] args) {
       int sum=0;
        for (int i = 0; i < args.length; i++) {
            try {
                int n = Integer.parseInt(args[i]);
                sum += n;
            } catch (NumberFormatException e) {
                System.out.printf("%s is not a valid number.\n", args[i]);
            }
        }
        System.out.println("The sum is: " + sum);
    }
}