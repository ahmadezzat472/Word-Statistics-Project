package wordstatistics;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BuildThread {

    public void BuildThread(String directoryPath, boolean check) {
        ArrayList<String> pathsFiles = new ArrayList();
        ReadFiles R = new ReadFiles();
        pathsFiles = R.getFiles(directoryPath,check);   // pathsFiles => contain all path of all files
        RunnableClass f1 = new RunnableClass();
        f1.setfiles(pathsFiles);
        Thread t1 = new Thread(f1);
        Thread t2 = new Thread(f1);
        Thread t3 = new Thread(f1);
        Thread t4 = new Thread(f1);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        }
    }
