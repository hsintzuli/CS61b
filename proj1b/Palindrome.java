public class Palindrome {

    /** Given a String word, return a Deque where the characters
     * appear in the same order as in the String. */
    public Deque<Character> wordToDeque(String word) {
        if (word == null) {
            return null;
        }
        Deque<Character> ansDeque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i += 1) {
            char thisChar = word.charAt(i);
            ansDeque.addLast(thisChar);
        }
        return ansDeque;
    }

    /** return true if the given word is a palindrome, and false otherwise. */
    public boolean isPalindrome(String word) {
        Deque<Character> wordDeque = wordToDeque(word);
        return isDequePalindrome(wordDeque);
    }
    /** return true if the wordDeque is a palindrome, and false otherwise. */
    private boolean isDequePalindrome(Deque<Character> wordDeque){
        if (wordDeque.size() <= 1) {
            return true;
        }

        if  (wordDeque.removeFirst() != wordDeque.removeLast()) {
            return false;
        } else {
            return isDequePalindrome(wordDeque);
        }
    }


    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> wordDeque = wordToDeque(word);
        return isDequePalindrome(wordDeque, cc);
    }
    private boolean isDequePalindrome(Deque<Character> wordDeque, CharacterComparator cc){
        if (wordDeque.size() <= 1) {
            return true;
        }

        if  (!cc.equalChars(wordDeque.removeFirst(), wordDeque.removeLast())) {
            return false;
        } else {
            return isDequePalindrome(wordDeque, cc);
        }
    }
}
