package wordstatistics;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class ReadFiles {

    private ArrayList<String> pathsDirs = new ArrayList<String>();   // use to store paths of directories
    private ArrayList<String> pathsFiles = new ArrayList<String>();  // use to store paths of files

    //---------------------get all directories---------------------
    private void getDirectories(String path, int level) {
        if (level == 2) {
            // --exit because we need level 1 and 2 only
            return;
        }
        File pathOfDir = new File(path);
        if (level == 0) {
            pathsDirs.add(path);
        }
        String[] contentOfPath = pathOfDir.list();  // list of content of directory
        for (int i = 0; i < contentOfPath.length; i++) {
            File newPath = new File(path + "\\" + contentOfPath[i]);
            if (newPath.isDirectory()) {    // check the newPath is directory
                pathsDirs.add(newPath.toString());
                getDirectories(newPath.toString(), level + 1);
            }
        }
    }
    /*
    #---- getDirectories() fun ----#
    use to get all Paths of all directories in main directory and level 1
    
    #---- call getPaths first time (My-Word-Statistics-Project\testing directory, 0) ----#
    pathOfDir     => My-Word-Statistics-Project\testing directory
    pathsDir      => [My-Word-Statistics-Project\testing directory]
    contentOfPath => [file1_level0.txt, file2_level0.txt, level 1]
    newPath       => My-Word-Statistics-Project\testing directory\contentOfPath[i]
    if newPath is directory
        pathsDir  => [My-Word-Statistics-Project\testing directory, My-Word-Statistics-Project\testing directory\level 1]
    
    #---- call getPaths recurion (My-Word-Statistics-Project\testing directory\level 1, 1) ----#
    pathOfDir     => My-Word-Statistics-Project\testing directory\level 1
    contentOfPath => [file1_level1.txt, file2_level1.txt, level 2]
    newPath       => My-Word-Statistics-Project\testing directory\level 1\contentOfPath[i]
    if newPath is directory
        pathsDir  => [My-Word-Statistics-Project\testing directory, My-Word-Statistics-Project\testing directory\level 1, My-Word-Statistics-Project\testing directory\level 1\level 2]
    
    #---- call getPaths recurion (My-Word-Statistics-Project\testing directory\level 1\level 2, 2) ----#
    return        => exit and getPaths() fun finally returns pathsDir
    pathsDir      => [My-Word-Statistics-Project\testing directory, My-Word-Statistics-Project\testing directory\level 1, My-Word-Statistics-Project\testing directory\level 1\level 2]
    */

    
    //-----------------------get all files-----------------------
    public ArrayList<String> getFiles(String path, boolean check) {
        if (check) {
            // --checkBox include, so level 1,2 also include
            getDirectories(path, 0);
        }
        else{   
            // --checkBox not include, so level 0 only its include 
            pathsDirs.add(path);
        }
        for (int i = 0; i < pathsDirs.size(); i++) {
            File pathOfDir = new File(pathsDirs.get(i));        
            String[] contentOfPath = pathOfDir.list();  // list of content of directory
            for (int i2 = 0; i2 < contentOfPath.length; i2++) {
                File newPath = new File(pathOfDir + "\\" + contentOfPath[i2]);  //pathOfDir.getAbsoluteFile() = pathOfDir.toString() = pathOfDir
                if (!newPath.isDirectory()) {   // check the newPath not directory
                    // --newPath is file not directory 
                    pathsFiles.add(newPath.toString());
                }
            }
        }
        return pathsFiles;
    }
    
    /* 
    #---- getDirectories() fun ----#
    use to get all Paths of all files in all directories that inside the pathsDirs array
    
    if check==1 => pathsDir = [My-Word-Statistics-Project\testing directory, My-Word-Statistics-Project\testing directory\level 1, My-Word-Statistics-Project\testing directory\level 1\level 2]
    else => pathsDir = [C:\Users\future\Documents\Semester1_Year3\OS2\project\My-Word-Statistics-Project\testing directory]
    loop on each item(path) in pathsDir:
        pathOfDir = pathsDir[i] => My-Word-Statistics-Project\testing directory
        contentOfPath = [file1_level0.txt, file2_level0.txt, level 1]
        loop on each item(file or directory) in contentOfPath:
            newPath = pathOfDir+\+contentOfPath[i2] => My-Word-Statistics-Project\testing directory\file1_level0.txt
            if newPath is file not directory 
                pathsFiles = [newPath]
                    
    if check==1
        return pathsFiles =[My-Word-Statistics-Project\testing directory\file1_level0.txt, 
                            My-Word-Statistics-Project\testing directory\file2_level0.txt,
                            My-Word-Statistics-Project\testing directory\level 1\file1_level1.txt,
                            My-Word-Statistics-Project\testing directory\level 1\file2_level1.txt,
                            My-Word-Statistics-Project\testing directory\level 1\level 2\file1_level2.txt
                            My-Word-Statistics-Project\testing directory\level 1\level 2\file2_level2.txt,
                            My-Word-Statistics-Project\testing directory\level 1\level 2\file3_level2.txt,
                            My-Word-Statistics-Project\testing directory\level 1\level 2\file4_level2.txt,
                            My-Word-Statistics-Project\testing directory\level 1\level 2\file5_level2.txt]
    else
        return pathsFiles =[My-Word-Statistics-Project\testing directory\file1_level0.txt, 
                            My-Word-Statistics-Project\testing directory\file2_level0.txt]
        
    */
}
