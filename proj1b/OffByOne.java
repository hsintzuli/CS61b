public class OffByOne implements CharacterComparator{

    @Override
    public  boolean equalChars(char x, char y){
        int numberX = x;
        int numberY = y;
        if (Math.abs(numberX - numberY) == 1) {
            return true;
        } else {
            return false;
        }
    }

}
