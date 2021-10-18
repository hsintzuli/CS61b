public class Maximizer {
    public static Comparable max(Comparable[] items){
        if (items.length == 0 ) {
            return null;
        }
        int maxindex = 0;
        for (int i = 1; i < items.length; i+=1) {
            int cmp = items[maxindex].compareTo(items[i]);
            if (cmp < 0) {
                maxindex = i;
            }
        }
        return items[maxindex];
    }
}
