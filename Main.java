import java.awt.*;
import javax.swing.JFrame;
import java.util.ArrayList;

public class Main extends Canvas{
  public static void main(String[] args){
    xys.add(x1);
    xys.add(y1);
    for (int i = 0; i <= sides; i++){//adds each side of shape to an array
      segments.add((double)50);
    }
    for (int i = 0; i < sides; i++){//adds angles for shape to an array
      if (i == 0 | i == (sides-1)){
        angles.add((double)(180-(360/sides)));//Switch the -1* to the other angles.add to invert the fractalvvv
      }else{
        angles.add((double)-1*(360/sides));//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
      }
    }
    for (int i = 0; i < sides; i++){//repeats for each side of shape to make snowflake
      fractal(8, 0.0006);//you need to adjust the second argument whenever you change the shape and the intensity becuase the size will change dramatically, not quite as much on shape though
      angle += 360/sides;
    }
    JFrame frame = new JFrame("Fractal");//sets up canvas vvv
    Canvas canvas = new Main();
    canvas.setSize(1820, 1020);
    canvas.setBackground(Color.white);
    frame.add(canvas);
    frame.pack();
    frame.setVisible(true);
    frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
  }
  static double sides = 5;//creates this many sided shape (3 - triangle, 4 - square, ect...)
  //fractal lists
  static ArrayList<Double> segments = new ArrayList<Double>();
  static ArrayList<Double> angles = new ArrayList<Double>();
  //list of finished fractal's xs and ys
  static ArrayList<Double> xys = new ArrayList<Double>();
  //variables for fractal
  static double angle = 90;//change to adjust the starting angle
  static double x1 = 400;//change to adjust starting x and y
  static double y1 = 10;
  static int quality = 1;//skips this many steps in xys when drawing to save time (turn to 1 when drawing below iter 3 or so)
  public void paint(Graphics g){//draws finished snowflake
    for (int i = 0; i < xys.size() - 3; i += (2 * quality)){
      g.drawLine((int)Math.round(xys.get(i)), (int)Math.round(xys.get(i + 1)), (int)Math.round(xys.get(i + 2)), (int)Math.round(xys.get(i + 3)));
    }
  }
  public static void fractal(int iter, double zoom){//creates fractaled side of the shape
    for (int i = 0; i < segments.size(); i++){
      if (iter == 0){//when iteration equals zero this and if i < angles.size() sequence back and forth up the whole stack
        getXY(segments.get(i) * zoom);
        xys.add(x1);
        xys.add(y1);
        if (i < angles.size()){
          angle += angles.get(i);
        }
      }else{
        fractal(iter - 1, zoom);//repeats to stack whats below above what's in if iter = 0
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
