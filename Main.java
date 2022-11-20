import java.awt.*;
import javax.swing.JFrame;
import java.util.*;

public class Main extends Canvas{
  public static void main(String[] args){
    xys.get(0).add(x1);
    xys.get(1).add(y1);
    for (int i = 0; i <= sides; i++){//adds each side of shape to an array
      segments.add((double)50);
    }
    for (int i = 0; i < sides; i++){//adds angles for shape to an array
      if (i == 0 | i == (sides-1)){
        angles.add((double)-1*(180-(360/sides)));//Switch the -1* to the other angles.add to invert the fractal
      }else{
        angles.add((double)(360/sides));//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
      }
    }
    for (int i = 0; i < sides; i++){//repeats for each side of shape to make snowflake
      fractal(4);//controls the amount of fractalization, 4 usually works the best, otherwise there'd be more pixels than you can render
      angle += 360/sides;
    }
    JFrame frame = new JFrame("Fractal");
    Canvas canvas = new Main();
    canvas.setSize(1020, 1020);
    canvas.setBackground(Color.white);
    frame.add(canvas);
    frame.pack();
    frame.setVisible(true);
    frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
  }
  static double sides = 5;//uses this many sided shape for the base (3 - triangle, 4 - square, ect...)
  //fractal lists
  static ArrayList<Double> segments = new ArrayList<Double>();
  static ArrayList<Double> angles = new ArrayList<Double>();
  //list of finished fractal's coordinates
  static ArrayList<ArrayList<Double>> xys = new ArrayList<ArrayList<Double>>(Arrays.asList(new ArrayList<Double>(), new ArrayList<Double>()));
  //variables for fractal
  static double angle = 90;//change to adjust the starting angle
  static double x1 = 0;
  static double y1 = 0;
  static int quality = 1;//skips this many steps in xys when drawing to save time (really only used to make it look janky for fun)
  public void paint(Graphics g){
    double height = Collections.max(xys.get(1)) - Collections.min(xys.get(1));
    double zoom = 1;
    if (height > 900){
      zoom = 900 / height;
      for (int x = 0; x < 2; x++){//uses zoom to shrink the coordinates to be 900 pixels tall if needed
        for (int y = 0; y < xys.get(x).size(); y++){ 
          xys.get(x).set(y, xys.get(x).get(y) * zoom);
        }
      }
    }
    double width = Collections.max(xys.get(0)) - Collections.min(xys.get(0));
    height = Collections.max(xys.get(1)) - Collections.min(xys.get(1));
    ArrayList<Double> offset = new ArrayList<Double>();
    offset.add(Collections.max(xys.get(0)) - width);
    offset.add(Collections.max(xys.get(1)) - height);
    for (int x = 0; x < 2; x++){//centers the fractal
      for (int y = 0; y < xys.get(x).size(); y++){ 
        xys.get(x).set(y, xys.get(x).get(y) - offset.get(x));
      }
    }
    g.setColor(new Color(0, 0, 0, 69));//adjusting the alpha will let you see detail in complex shapes with alot of overlap
    for (int i = 0; i < xys.get(0).size() - 1; i += (1 * quality)){//draws the finished fractal
      g.drawLine((int)Math.round(xys.get(0).get(i)), (int)Math.round(xys.get(1).get(i)), (int)Math.round(xys.get(0).get(i + (1 * quality))), (int)Math.round(xys.get(1).get(i + (1 * quality))));
    }
  }
  public static void fractal(int iter){//creates each fractaled side of the shape
    for (int i = 0; i < segments.size(); i++){
      if (iter == 0){//when iteration equals zero this and if i < angles.size() sequence back and forth up the whole stack
        getXY(segments.get(i));
        xys.get(0).add(x1);
        xys.get(1).add(y1);
        if (i < angles.size()){
          angle += angles.get(i);
        }
      }else{
        fractal(iter - 1);//repeats to stack whats below above what's in if iter = 0
        if (i < angles.size()){//this gets skipped until iter = 0
          angle += angles.get(i);
        }
      }
    }
  }
  public static void getXY(double segment){//finds leg and opposite from angle and hypotenuse
    x1 += segment * Math.sin(Math.PI * angle / 180);//converts degrees to radians and then moves the distance of segment in that direction
    y1 -= segment * Math.cos(Math.PI * angle / 180);
  }
}
