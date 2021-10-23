public class NBody{
    public static double readRadius(String filename){
        In in = new In(filename);
        int nums = in.readInt();
        double radius = in.readDouble();
        
        return radius;
    }
    
    public static Planet[] readPlanets(String filename){
        In in = new In(filename);
        int nums = in.readInt();
        Planet[] planets = new Planet[nums];
        in.readDouble();
        for(int i=0; i<nums; i++){
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String img = in.readString();
            planets[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, img);
            
            
        }
        return planets;
    }
    
    public static void main(String[] args){
        double T = Double.parseDouble(args[0]); 
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        String imageToDraw = "images/starfield.jpg";
        String audioFilename = "audio/2001.mid";
        
        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);
        int nums = planets.length;
        
        double time=0;
        StdDraw.enableDoubleBuffering();
        
        StdAudio.play(audioFilename);
        while(time < T){
            double[] xForces = new double[nums];
            double[] yForces = new double[nums];
            for(int i=0; i<nums; i++){
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for(int i=0; i<nums; i++){
                planets[i].update(dt, xForces[i], yForces[i]);
            }
            
            StdDraw.setScale(-radius, radius);
            StdDraw.clear();
            StdDraw.picture(0, 0, imageToDraw);
            for(Planet p:planets){
                p.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            time = time + dt;
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < nums; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
            planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
        }
    }
}