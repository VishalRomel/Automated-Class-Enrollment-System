import java.util.Random;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class EnrollmentBot {

   public static void main(String[] args) {

      int Resfreshes = 0;

      ChromeDriver driver = new ChromeDriver();

      AccountInfo info = new AccountInfo();

      try {
         driver.get("https://sb.cunyfirst.cuny.edu/criteria.jsp");

         Thread.sleep(2000);

         WebElement SignInButton = driver.findElement(By.xpath("//*[@id=\"bodyContent\"]/div[3]/div/div/button"));

         SignInButton.click();

         Thread.sleep(2000);

         WebElement usernameField = driver.findElement(By.name("usernameH"));
         WebElement passwordField = driver.findElement(By.name("password"));

         usernameField.sendKeys(AccountInfo.username);
         passwordField.sendKeys(AccountInfo.password);

         WebElement loginButton = driver.findElement(By.name("submit"));
         loginButton.click();

         Thread.sleep(2000);

         driver.manage().window().minimize();

         Thread.sleep(1000);
         Thread.sleep(5000);

         WebElement SelectCourse = driver.findElement(By.xpath("//*[@id=\"bodyContent\"]/div[2]/div[2]/div[1]/div/ul/li[1]/a"));
         SelectCourse.click();

         Thread.sleep(2000);

         SearchForClasses(driver);

         try {
            while (true) {
               try {

                  int ClassNum = Integer.parseInt(JOptionPane.showInputDialog("How much Classes are you Currently Enrolled in?")); //This needs manual changing
                  Classes classname = new Classes();

                  WebElement Dropdown;

                  for (int i = 0; i < Classes.classes.size(); i++) {
                     String DropDownArrow = "//*[@id=\"tipExpandX_" + ClassNum + "\"]";
                     Dropdown = driver.findElement(By.xpath(DropDownArrow));
                     Dropdown.click();
                     Thread.sleep(2000);
                     ClassNum++;
                  }

                  Thread.sleep(2000);

                  WebElement Closed;
                  int ClassNumber = ClassNum + 4;

                  for (int i = 0; i < Classes.classes.size(); i++) {
                     String LecPath = "//*[@id=\"requirements\"]/div[" + ClassNumber + "]/div[3]/div[2]/div/div[8]/span[2]/span[" + Classes.LecNumber.get(i) + "]/label/span[1]";
                     Closed = driver.findElement(By.xpath(LecPath));
                     System.out.println(Resfreshes + " : " + Closed.getText());

                     Thread.sleep(1000);

                     StringTokenizer st = new StringTokenizer(Closed.getText(), " ");

                     String isClosed = "";
                     boolean isOpen = false;

                     while (st.hasMoreTokens()) {

                        isClosed = (st.nextToken());
                        if (isClosed.equals("(Closed)")) {
                           isOpen = false;
                           //System.out.print("      :" + isClosed);
                           break;
                        } else isOpen = true;

                     }

                     if (isOpen == true) {

                        int temp = ClassNumber + 1;
                        for (int j = i + 1; j < Classes.classes.size(); j++) {

                           String SelectClassOff = "//*[@id=\"requirements\"]/div[" + temp + "]/div[3]/div[1]/label/span[3]";
                           WebElement CheckMarkClassOFF = driver.findElement(By.xpath(SelectClassOff));
                           CheckMarkClassOFF.click();
                           temp++;
                           Thread.sleep(1000);

                        }
                        String Selectnone = "//*[@id=\"requirements\"]/div[" + ClassNumber + "]/div[3]/div[2]/div/div[8]/div[2]/button[2]/span";
                        WebElement SelectNone = driver.findElement(By.xpath(Selectnone));
                        SelectNone.click();

                        String SelectLec = "//*[@id=\"requirements\"]/div[" + ClassNumber + "]/div[3]/div[2]/div/div[8]/span[2]/span[" + Classes.LecNumber.get(i) + "]/label/span[4]";
                        WebElement SelectLecture = driver.findElement(By.xpath(SelectLec));
                        SelectLecture.click();

                        Thread.sleep(5000);

                        WebElement GenerateButton = driver.findElement(By.xpath("//*[@id=\"do_search\"]"));
                        GenerateButton.click();

                        Thread.sleep(3000);

                        WebElement ScheduleButton = driver.findElement(By.xpath("//*[@id=\"crnListButtons\"]/div/button"));
                        ScheduleButton.click();

                        Thread.sleep(4000);

                        WebElement EnrollButton = driver.findElement(By.xpath("//*[@id=\"legend_checkout\"]/input[3]"));
                        EnrollButton.click();

                        Thread.sleep(10000);

                        WebElement GetStatus = driver.findElement(By.xpath("//*[@id=\"legend_box\"]/div[6]/div/div/div[4]")); //This needs manual changing

                        System.out.println(GetStatus.getText());

                        SendEmail Open3 = new SendEmail();
                        Open3.MessageToBeSent(GetStatus.getText());
                        Open3.sendMail("vishalcharran123@gmail.com");

                        if (GetStatus.getText().contains("Failed") == true) i = Classes.classes.size();
                        else System.exit(0);

                     } else {

                        String SelectClassOff = "//*[@id=\"requirements\"]/div[" + ClassNumber + "]/div[3]/div[1]/label/span[3]";
                        WebElement CheckMarkClassOFF = driver.findElement(By.xpath(SelectClassOff));
                        CheckMarkClassOFF.click();

                     }
                     isOpen = false;
                     ClassNumber++;
                  }

                  Thread.sleep(2000);

               } catch (Exception e) {
                  SendEmail Open4 = new SendEmail();
                  System.out.print("");
                  Open4.MessageToBeSent("Something went wrong");
                  Open4.sendMail("vishalcharran123@gmail.com");
                  driver.quit();
                  main(null);
                  //   System.exit(0);

               }
               Resfreshes++;
               driver.navigate().refresh();
               Random random = new Random();
               int randomSleepTime = random.nextInt(14000) + 45000;
               System.out.println("Sleeping for " + randomSleepTime + " milliseconds");

               Thread.sleep(randomSleepTime);

            }
         } catch (Exception e) {
            e.printStackTrace();
         }

      } catch (Exception e) {
         e.printStackTrace();
      }

   }

   private static void SearchForClasses(ChromeDriver driver) throws Exception {
      try {
         for (int i = 0; i < Classes.classes.size(); i++) {

            WebElement SearchClass = driver.findElement(By.xpath("//*[@id=\"code_number\"]"));
            SearchClass.sendKeys(Classes.classes.get(i));

            Thread.sleep(2000);

            WebElement ClickClass = driver.findElement(By.xpath("//*[@id=\"results_focus_0\"]"));
            ClickClass.click();

            Thread.sleep(3000);
         }
      } catch (Exception e) {

         SendEmail Open5 = new SendEmail();
         Open5.MessageToBeSent("Something went wrong wih searching for the class");
         Open5.sendMail("vishalcharran123@gmail.com");

      }
   }

}