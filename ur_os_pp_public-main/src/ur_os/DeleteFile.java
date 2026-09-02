/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os;

import java.io.File;

/**
 *
 * @author prestamour
 */
public class DeleteFile {
    public static void DeleteFile(String filePath) {
        File obj = new File(filePath);
        
        if (obj.exists()){
            if(obj.delete()){
                System.out.println("The file: " + obj.getName() + " was removed");
            }else{
                System.out.println("The file: " + obj.getName() + " could not be removed");
            }
        }else{
            System.out.println("Error: The file was not found.");
        }
    }
}
