import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Classes {

   public static ArrayList < String > classes = new ArrayList < > ();
   public static ArrayList < Integer > LecNumber = new ArrayList < > ();
   public static int ClassNumber = 1;

   public Classes() {

      while (true) {
         String className = JOptionPane.showInputDialog(null, "Enter a Class You want to Enroll in. Enter 'Stop' When you are finish with all Classes");
         if (className.toLowerCase().equals("stop")) break;
         int LecPosition = Integer.parseInt((JOptionPane.showInputDialog(null, "Enter a Class You want to Enroll in. Enter 'Stop' When you are finish with all Classes")));

         classes.add(className);
         LecNumber.add(LecPosition);
         ClassNumber++;
      }

   }

}