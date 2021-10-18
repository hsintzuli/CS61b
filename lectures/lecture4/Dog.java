import java.util.Comparator;

public class Dog implements Comparable<Dog>{
    public int size;
    public String name;

    public Dog(String name, int size){
        this.name = name;
        this.size = size;
    }

    public void bark(){
        System.out.println(name + " says: bark! ");
    }

    @Override
    public int compareTo(Dog g){
        return size - g.size;
    }

    private static class NameComparator implements Comparator<Dog>{
        public int compare(Dog a, Dog b){
            return a.name.compareTo(b.name);
        }
    }

    public static Comparator<Dog> getNameComparator() {
        return new NameComparator();
    }
}
