package ur_os;

import java.io.File;
import java.io.IOException;

public class CreateFile {
    
    private static File lastcreated = null;
    public static File createSchedulerFile(String schedulerName) {
        File file = new File(schedulerName + ".txt");

        try {
            if (file.createNewFile()) {
                System.out.println("Created File: " + file.getName());
            } else {
                System.out.println("The file is already created " + file.getName());
            }
        } catch (IOException e) {
            System.out.println("Error to create the file " + e.getMessage());
        }
        lastcreated = file;
        return file;
    }
    public static String returnFile(){
        
        return lastcreated.getAbsolutePath();
        
    }
}
