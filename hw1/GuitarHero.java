import synthesizer.GuitarString;

/** A client that uses the synthesizer package to replicate a plucked guitar string sound */

public class GuitarHero {

    public static void main(String[] args) {
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        synthesizer.GuitarString stringArray[] = new synthesizer.GuitarString[keyboard.length()];
        for (int i = 0; i < keyboard.length(); i++) {
            double CONCERT = 440 * Math.pow(2, (i-24) / 12);
            stringArray[i] = new synthesizer.GuitarString(CONCERT);
        }

        int keyNum = 0;
        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                keyNum = keyboard.indexOf(key);
                if (keyNum < 0) {
                    continue;
                }
                stringArray[keyNum].pluck();
            }
            /* compute the superposition of samples */
            double sample = 0;
            for (synthesizer.GuitarString s : stringArray) {
                sample += s.sample();
            }
            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (synthesizer.GuitarString s : stringArray) {
                s.tic();
            }

        }
    }
}
