import java.lang.Math;

public class Planet{
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static final double G = 6.67e-11;
    
    public Planet(double xP, double yP, double xV, double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }
    
    public Planet(Planet p){
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }
    
    public double calcDistance(Planet p2){
        double distance;
        distance = Math.sqrt( Math.pow(xxPos - p2.xxPos, 2) + Math.pow(yyPos - p2.yyPos, 2));
        
        return distance;
    }
    
    public double calcForceExertedBy(Planet p2){
        double distance, force;
        distance = calcDistance(p2);
        force = G*mass*p2.mass / Math.pow(distance, 2);
        
        return force;
    }
    
    public double calcForceExertedByX(Planet p2){
        double distance, force, dx, forcebyX;
        distance = calcDistance(p2);
        dx = p2.xxPos - xxPos;
        force = calcForceExertedBy(p2);
        
        forcebyX = force * dx / distance;
        return forcebyX;
    }
    public double calcForceExertedByY(Planet p2){
        double distance, force, dy, forcebyY;
        distance = calcDistance(p2);
        dy = p2.yyPos - yyPos;
        force = calcForceExertedBy(p2);
        
        forcebyY = force * dy / distance;
        return forcebyY;
    }
    
    public double calcNetForceExertedByX(Planet[] planets){
        double netforcebyX = 0;
        for(Planet p:planets){
            if(this.equals(p) == false){
                netforcebyX += calcForceExertedByX(p);
            }
        }
        return netforcebyX;
    }
    public double calcNetForceExertedByY(Planet[] planets){
        double netforcebyY = 0;
        for(Planet p:planets){
            if(this.equals(p) == false){
                netforcebyY += calcForceExertedByY(p);
            }
        }
        return netforcebyY;
    }
    public void update(double dt, double Xforce, double Yforce){
        double ax,ay;
        ax = Xforce / mass;
        ay = Yforce / mass;
        
        xxVel = xxVel + dt*ax;
        yyVel = yyVel + dt*ay;
        
        xxPos = xxPos + xxVel*dt;
        yyPos = yyPos + yyVel*dt;
    }
    
    public void draw(){
        String img = "images/" + imgFileName;
        StdDraw.picture(xxPos, yyPos, img);
    }
}