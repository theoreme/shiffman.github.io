import processing.core.*; import java.applet.*; import java.awt.*; import java.awt.image.*; import java.awt.event.*; import java.io.*; import java.net.*; import java.text.*; import java.util.*; import java.util.zip.*; public class Pendulum extends PApplet {// Pendulum
// Daniel Shiffman <http://www.shiffman.net>

// A simple pendulum simulation
// Given a pendulum with an angle theta (0 being the pendulum at rest) and a radius r
// we can use sine to calculate the angular component of the gravitational force.

// Gravity Force = Mass * Gravitational Constant;
// Pendulum Force = Gravity Force * sine(theta)
// Angular Acceleration = Pendulum Force / Mass = Gravitational Constant * sine(theta);

// Note this is an ideal world scenario with no tension in the 
// pendulum arm, a more realistic formula might be:
// Angular Acceleration = (G / R) * sine(theta)

// For a more substantial explanation, visit:
// http://www.myphysicslab.com/pendulum1.html 

// Created 5 May 2005

Pendul p;

public void setup() {
  size(200,200);
  framerate(30);
  smooth();
  colorMode(RGB,255,255,255,100);
  
  // Make a new Pendulum with an origin location and armlength
  p = new Pendul(new Vector3D(width/2,height/2),75.0f);

}

public void draw() {

  background(100);
  p.go();
}

public void mousePressed() {
  p.clicked(mouseX,mouseY);
}

public void mouseReleased() {
  p.stopDragging();
}


// Pendulum
// Daniel Shiffman <http://www.shiffman.net>

// A Simple Pendulum Class
// Includes functionality for user can click and drag the pendulum

// Created 2 May 2005

class Pendul  {

  Vector3D loc;      // Location of pendulum ball
  Vector3D origin;   // Location of arm origin
  float r;           // Length of arm
  float theta;       // Pendulum arm angle
  float theta_vel;   // Angle velocity
  float theta_acc;   // Angle acceleration

  float ballr;       // Ball radius
  float damping;     // Arbitary damping amount

  boolean dragging = false;

  // This constructor could be improved to allow a greater variety of pendulums
  Pendul(Vector3D origin_, float r_) {
    // Fill all variables
    origin = origin_.copy();
    r = r_;
    theta = 0.0f;
    
    //calculate the location of the ball using polar to cartesian conversion
    float x = r * sin(theta);
    float y = r * cos(theta);
    loc = new Vector3D(origin.x + x, origin.y + y);
    theta_vel = 0.0f;
    theta_acc = 0.0f;
    damping = 0.995f;   // Arbitrary damping
    ballr = 16.0f;      // Arbitrary ball radius
  }

  public void go() {
    update();
    drag();    //for user interaction
    render();
  }

  // Function to update location
  public void update() {
    // As long as we aren't dragging the pendulum, let it swing!
    if (!dragging) {
      float G = 0.4f;                              // Arbitrary universal gravitational constant
      theta_acc = (-1 * G / r) * sin(theta);      // Calculate acceleration (see: http://www.myphysicslab.com/pendulum1.html)
      theta_vel += theta_acc;                     // Increment velocity
      theta_vel *= damping;                       // Arbitrary damping
      theta += theta_vel;                         // Increment theta
    }
    loc.setXY(r*sin(theta),r*cos(theta));         // Polar to cartesian conversion
    loc.add(origin);                              // Make sure the location is relative to the pendulum's origin
  }

  public void render() {
    stroke(255,50);
    // Draw the arm
    line(origin.x,origin.y,loc.x,loc.y);
    ellipseMode(CENTER);
    fill(200);
    if (dragging) fill(255);
    noStroke();
    // Draw the ball
    ellipse(loc.x,loc.y,ballr,ballr);
  }

  // The methods below are for mouse interaction
  
  // This checks to see if we clicked on the pendulum ball
  public void clicked(int mx, int my) {
    float d = dist(mx,my,loc.x,loc.y);
    if (d < ballr) {
      dragging = true;
    }
  }

  // This tells us we are not longer clicking on the ball
  public void stopDragging() {
    dragging = false;
  }

  public void drag() {
    // If we are draging the ball, we calculate the angle between the 
    // pendulum origin and mouse location
    // we assign that angle to the pendulum
    if (dragging) {
      Vector3D diff = Vector3D.sub(origin,new Vector3D(mouseX,mouseY));   // Difference between 2 points
      theta = atan2(-1*diff.y,diff.x) - radians(90);                      // Angle relative to vertical axis
    }
  }

}

static public void main(String args[]) {   PApplet.main(new String[] { "Pendulum" });}}