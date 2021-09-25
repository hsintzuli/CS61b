public class HelloNumber{
	public static void main(String[] args){
		int x = 0;
		int p = 1;
		while(x<=45){
			System.out.print(x + " ");
			x = x + p;
			p = p + 1;
		}	
	}
}