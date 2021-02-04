package me.yourpixel.helpcosmetics;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class MathUtils {
	
	public double cos(double angle) {
		return Math.cos(angle);
	}
	
	public double sin(double angle) {
		return Math.sin(angle);
	}
	
	public double tan(double angle) {
		return Math.tan(angle);
	}
	
	public Vector rotateFunction(Vector v,Location loc) {
		double yawR = loc.getYaw()/180.0*Math.PI;
		
		double pitchR = loc.getPitch()/180.0*Math.PI;
		
		v=rotateAboutX(v,pitchR);
		v=rotateAboutY(v,-yawR);
		return v;
	}
	
	public Vector rotateAboutX(Vector vect, double a) {
		double Y = cos(a)*vect.getY( ) - sin(a)*vect.getZ();
		double Z = sin(a)*vect.getY() + cos(a)*vect.getZ();
		return vect.setY(Y).setZ(Z);
	}
	
	public Vector rotateAboutY(Vector vect,double a) {
		double X = cos(a)*vect.getX( ) + sin(a)*vect.getZ();
		double Z = -sin(a)*vect.getX() + cos(a)*vect.getZ();
		return vect.setX(X).setZ(Z);
	}
	
	public Vector rotateAboutZ(Vector vect, double a) {
		double X = cos(a)*vect.getX() - sin(a)*vect.getY();
		double Y = sin(a)*vect.getX() + cos(a)*vect.getY();
		return vect.setX(X).setY(Y);
	}
	
	public Vector getVectorForPoints(Location l1, Location l2) {
		double g = -0.08;
		double d = l2.distance(l1);
		double t = d;
		double vX = (1.0+0.07*t)*(l2.getX()-l1.getBlockX())/t;
		double vY = (1.0+0.03*t)*(l2.getY()-l1.getY())/t-0.5*g*t;
		double vZ = (1.0+0.07*t) * (l2.getZ()-l1.getZ())/t;
		return new Vector(vX, vY, vZ);
	}

}
