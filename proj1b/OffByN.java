public class OffByN implements CharacterComparator{
    private int N;

    public OffByN(int N){
        this.N = N;
    }

    @Override
    public  boolean equalChars(char x, char y){
        int numberX = x;
        int numberY = y;
        if (Math.abs(numberX - numberY) == N) {
            return true;
        } else {
            return false;
        }
    }
}
