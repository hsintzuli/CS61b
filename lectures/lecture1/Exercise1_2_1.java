public class Exercise1_2_1{
    public static void main(String[] args) {
        Dog d = new Dog(15);
        Dog d2 = new Dog(100);
        Dog biggerdog1 = Dog.maxDog(d, d2);
        Dog biggerdog2 = d.maxDog(d2);
        biggerdog1.makeNoise();
        biggerdog2.makeNoise();
    }

}