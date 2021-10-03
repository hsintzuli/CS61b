public class IntList{
    public int first;
    public IntList rest;
    public IntList(int f, IntList r){
        first = f;
        rest = r;
    }
    
    /**Return the size of this IntList. */
    public int size(){
        if(rest == null){
            return 1;
        }
        return 1 + this.rest.size();
    }
    
    public int get(int i){
        if(i == 0){
            return first;
        }
        return rest.get(i-1);
    }
    
    /**Exercise2.2.1*/
    public void addFirst(int x){
        this.rest = new IntList(this.first, this.rest);
        this.first = x;
    }
    public int getFirst(){
        return first;
    }
    
    public static void main(String[] args){
        IntList L = new IntList(15, null);
        L = new IntList(10, L);
        L = new IntList(5, L);
        L.addFirst(6);
        
        System.out.println(L.get(2));
        System.out.println(L.getFirst());
    }
}