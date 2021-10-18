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

    public boolean isPalindrome(String word) {
        OffByOne offByOne = new OffByOne();
        return isPalindrome(word, offByOne);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        int pointerS = 0;
        int pointerE = word.length() - 1;
        while (pointerS < pointerE) {
            if (!cc.equalChars(word.charAt(pointerS), word.charAt(pointerE))) {
                return false;
            }
            pointerS += 1;
            pointerE -= 1;
        }
        return true;
    }
}
