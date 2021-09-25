public class Exercise1_2_2{
    public static void main(String[] args) {
        Dog smallDog = new Dog(5);
        Dog mediumDog = new Dog(25);
        Dog hugeDog = new Dog(150);

/** Should modify the origin problem, otherwise it would raise Exception
origin: Dog[] manyDogs = new Dog[4];  
*/
        Dog[] manyDogs = new Dog[3]; 
        manyDogs[0] = smallDog;
        manyDogs[1] = hugeDog;
        manyDogs[2] = new Dog(130);

/** The follwing program would print:
bark. bark.
woof
woof
*/
        int i = 0;
        while(i < manyDogs.length){
            Dog.maxDog(manyDogs[i], mediumDog).makeNoise();
            i = i + 1;
        }
    }

}