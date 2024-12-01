import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDB {
    
    public static List<String> loadCart(String filePath, List<String> cartItems) throws IOException {
        
        // Locate and read file
        File f = new File(filePath);                    // Initialize File object f in filePath
        FileReader fr = new FileReader(f);              // Open FileReader to read f
        BufferedReader br = new BufferedReader(fr);     // Open BufferedReader to read fr
        String line = "";                               // Empty string to store read line
        while ((line = br.readLine()) != null) {        // While there is still things to read
            cartItems.add(line);                        // Add whatever is read to cartItems
        }
        br.close();                                     // Close BufferedReader
        fr.close();                                     // Close FileReader
        
        return cartItems;                               // Return cartItems array
    }


    public static void saveCart(String filePath, List<String> cartItems) throws IOException {

        // Locate and write file
        File f = new File(filePath);
        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);
        for (int i = 0; i < cartItems.size(); i++) {
            bw.write(cartItems.get(i) + "\n");
        }
        bw.flush();
        fw.flush();
        bw.close();
        fw.close();
    }


    public static void viewUsers(String cartdb) {
        File directory = new File(cartdb);
        String[] listOfUsers = directory.list();

        if (listOfUsers == null) {
            System.out.println("There are no users registered");
        } else {
            System.out.println("The following users are registered");
            for (int i = 0; i < listOfUsers.length; i++) {
                String user = listOfUsers[i].substring(0, listOfUsers[i].length() - 3);
                System.out.println((i+1) + ". " + user);
            }
        }
    }
    
    
}
