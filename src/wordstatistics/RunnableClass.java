package wordstatistics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RunnableClass implements Runnable {    // USE INTERFACE TO ABLE ADD EXTEND

    private ArrayList<String> filesPath = new ArrayList<String>();
    private static final int sleepTime = 500;   // half second
    //Syncro_uptodate Start = new Syncro_uptodate();
    
    // --set all files's path 
    public void setfiles(ArrayList<String> S) {
        filesPath = S;
    }
    
    // --this function is synchronized, so one thread only enter
    public synchronized int geFilesSize() {
        return filesPath.size();
    }
    
    // --this function is synchronized, so one thread only enter
    public synchronized String getFilePath() {
        String temp = filesPath.get(0);
        filesPath.remove(0);
        return temp;
    }
    
    // --after the thread declare and start, will be enter this run function
    @Override
    public void run() {
        while (geFilesSize() != 0) {    // loop that continues as long as there are more files in filesPath array
            int countIs = 0;
            int countAre = 0;
            int countYou = 0;
            String longest = "";
            String Shortest = "";
            String filePath = getFilePath();
            
            try {
                // --File Initialization to read the content of the file.
                File myFile = new File(filePath);
                Scanner myReader = new Scanner(myFile);
                // --Reading File Line by Line:
                while (myReader.hasNextLine()) {    // loop that continues as long as there are more lines in the file.
                    String currentLineData = myReader.nextLine();   // reads the current line from the file and stores it in the data 
                    String[] myData = currentLineData.split(" ");   // myData => array of words in currentLineData
                    // --start calculate #is,#are,#you,longest,Shortest
                    for (int i = 0; i < myData.length; i++) {
                        // count #is #are #you
                        if (myData[i].equals("is")) {
                            countIs++;
                        } else if (myData[i].equals("you")) {
                            countYou++;
                        } else if (myData[i].equals("are")) {
                            countAre++;
                        }
                        
                        // --search on longest and Shortest word
                        if (Shortest.length() == 0 && myData[i].length()>1) {   // Initialize the shortest variable with the first element in the myData that has more than one character
                            Shortest = myData[i];
                        }
                        if (myData[i].length() < Shortest.length() && myData[i].length()>1) {
                            Shortest = myData[i];
                        }
                        if (myData[i].length() > longest.length()) {
                            longest = myData[i];
                        }
                    }
                    
                    // --Convert the file path into parts to get the name of this file by access the last item of array
                    // --filePath = C:\Users\future\Documents\Semester1_Year3\OS2\project\My-Word-Statistics-Project\testing directory\file1_level0.txt
                    // --pathIntoParts = [C:, Users, future, Documents, Semester1_Year3, OS2, project, My-Word-Statistics-Project, testing directory, file1_level0.txt]
                    String[] pathIntoParts = filePath.split("\\\\");
                    
                    // --store full_path,longest,Shortest,countAre,countYou,countIs of current line of file in storeUpdate
                    ArrayList<String> storeUpdate = new ArrayList<String>();
                    storeUpdate.add(pathIntoParts[pathIntoParts.length - 1]);
                    storeUpdate.add(Integer.toString(myData.length));
                    storeUpdate.add(Integer.toString(countIs));
                    storeUpdate.add(Integer.toString(countAre));
                    storeUpdate.add(Integer.toString(countYou));
                    storeUpdate.add(longest);
                    storeUpdate.add(Shortest);
                    
                    // --reassign this variables by 0 because the current line in file is ended and start work in next line in same file
                    countIs = 0;
                    countAre = 0;
                    countYou = 0;
                    
                    // --send this data of current line in file to gui to show in screen
                    // --this function in synchronized class, so one thread only enter
                    GuiAndUpdate.showUpdateData(storeUpdate);
                    
                    // --thread sleeping or waiting then continue work
                    // --use to have a time between each upadate in the table
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(RunnableClass.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                // --close file because all lines in file it has been read
                myReader.close();
                
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        }
    }
    /*
    assume that direcotry have TWO files:
        then must be TWO thread enter while that inside run function
        thread 1 work on file 1 and thread 2 work on file 2 and vice versa
        the number of each thread will be call  
        each thread will call "GuiAndUpdate.showUpdateData(storeUpdate);" a number of times equal to the number of lines in the file

        just declare and start four threads 
        the thread 1,2,3,4 enter run() then call geFilesSize()
        geFilesSize() is synchronized function, so one thread from four threads enter geFilesSize()
        assume thread 1 enter geFilesSize(), then return 2 and enter while, then call getFilePath() => file size become 1 
        thread 2 enter geFilesSize(), then return 1 and enter while, then call getFilePath() => file size become 0
        when thread 3 and 4 enter geFilesSize(), then return 0 and cannot enter while
        so thread 1 and 2 They will continue work on file 1 and 2 and thread 3 and 4 not continue work  
    */

}
