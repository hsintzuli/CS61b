import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FlikTest {
    @Test
    public void testIsSameNumber() {
        int a1 = 1;
        int b1 = 1;
        assertTrue(Flik.isSameNumber(a1, b1));
    }

    @Test
    public void testNumber128(){
        int a = 128;
        int b = 128;
        assertTrue(Flik.isSameNumber(a,b));
    }

    @Test
    public void testNumber128Exception(){
        int a = 128;
        int b = 128;
        assertFalse(Flik.isSameNumber(a,b));
    }

    public static void testSameNumberInLoop(){
        int i = 100;
        int j = 100;
        while(Flik.isSameNumber(i,j)){
            System.out.print("i = " + i + " j = " + j + "\n");
            if(i == 130){
                break;
            }
            i++;
            j++;
        }
        System.out.println("BreakOut! i = " + i + " ,j = " + j);
    }

    public static void main(String[] args){
        testSameNumberInLoop();
    }
}
