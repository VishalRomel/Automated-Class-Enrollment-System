import javax.swing.JOptionPane;

public class AccountInfo {

   public static String username = "";
   public static String password = "";

   public static String Email = "gamergalsilva123@gmail.com";
   public static String Emalpass = ""; //Key (APP PASSWORD) is Not Given to access this gamil.

   public AccountInfo() {
      username = JOptionPane.showInputDialog("Enter Cuny Email: ");
      password = JOptionPane.showInputDialog("Enter Cuny password: ");
   }

}