package wordstatistics;

import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.io.File;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.border.LineBorder;
import javax.swing.JFileChooser;


public class GuiAndUpdate extends JFrame {    
    // --------------------to show wrong screen--------------------
    UIManager ui = new UIManager();
    
    //--------------------variable--------------------
    private static String selectDirectoryPath = "";  // Variable to store the selected folder path
    
    //--------------------screen size--------------------
    // --returns a Dimension object representing the size of the screen.
    Toolkit t = Toolkit.getDefaultToolkit();
    int x = t.getScreenSize().width;
    int y = t.getScreenSize().height;

    //--------------------panels--------------------
    // --a simple container for organizing components (such as buttons, labels, etc.) in a GUI
    JPanel container = new JPanel();

    //--------------------buttons--------------------
    JButton startProcessingButton = new JButton("Start Processing");
    JButton browseButton = new JButton("Browse");
    
    //--------------------longest word & shortest word--------------------
    JLabel directoryLabel = new JLabel("Select Directory ");
    JLabel shortestWord = new JLabel("Shortest Word : ");
    JLabel longestWord =  new JLabel("Longest Word : ");
    static JLabel acuallylongestWord = new JLabel("");
    static JLabel acuallyShortestWord = new JLabel("");

    //--------------------The main table--------------------
    // --used to store and manage data for a table => (used to get count of rows in table & add row in table & set and get value to/from table)
    // --used in add - remove - change - get row or columns
    static DefaultTableModel storeTableModel = new DefaultTableModel(); 
    // --create table of data(storeTableModel)
    JTable mainTable = new JTable(storeTableModel){
        // --Disallows editing of any cell in the table.
        public boolean isCellEditable(int rowIndex, int colIndex) {
            return false; 
        }
    };
    // --used to provide scrolling functionality for the table. It will display vertical and horizontal scroll bars as needed.
    // --using in the main features of the whole table
    JScrollPane scrollTable = new JScrollPane(mainTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    //--------------------check box--------------------
    JCheckBox checkBox = new JCheckBox("Include Sub Directories");
    
    //--------------------calculate Sizes--------------------
    private int percentY(int percent)
    {
        return (int) (y*percent/100);
    }
    private int percentX(int percent)
    {
        return (int) (x*percent/100);
    }

    //--------------------constructor--------------------
    public GuiAndUpdate()
    {
        RunGUI();
    }
    
    //--------------------control gui and show data--------------------
    public void RunGUI(){
        //--------------------the Frame option--------------------
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    // control exit icon (x) => exit from app and run
        this.setResizable(true);   // control the size of app during run time
        this.setSize(x, y);
        this.setTitle("WORD STATISTICS APP");
        this.setLayout(null);
        this.add(container);
                
        //--------------------container options--------------------
        container.setBackground(Color.decode("#ebebeb"));
        container.setSize(x, y);
        container.setLayout(null);
        container.setVisible(true);
        container.add(directoryLabel);        
        container.add(browseButton);
        container.add(checkBox);
        container.add(startProcessingButton);
        container.add(scrollTable); 
        container.add(longestWord);
        container.add(acuallylongestWord);
        container.add(shortestWord);
        container.add(acuallyShortestWord);
        
        //--------------------labels (longestWord & shortestWord & acuallylongestWord & acuallyShortestWord & directoryLabel)--------------------
        directoryLabel.setBounds(percentX(2),percentY(4),percentX(20),30);
        directoryLabel.setFont(new Font("", Font.CENTER_BASELINE, 25));
        longestWord.setBounds(percentX(2),percentY(84),percentX(20),30);
        longestWord.setFont(new Font("", Font.CENTER_BASELINE, 25));
        shortestWord.setBounds(percentX(2),percentY(89),percentX(20),30);
        shortestWord.setFont(new Font("", Font.CENTER_BASELINE, 25));
        acuallylongestWord.setBounds(longestWord.getX()+183,percentY(84),percentX(50),30);
        acuallylongestWord.setFont(new Font("",Font.CENTER_BASELINE,25));
        acuallylongestWord.setForeground(Color.decode("#084c61"));
        acuallyShortestWord.setBounds(shortestWord.getX()+183,percentY(89),percentX(20),30);
        acuallyShortestWord.setFont(new Font("",Font.CENTER_BASELINE,25));
        acuallyShortestWord.setForeground(Color.decode("#084c61"));

        //--------------------browse Button--------------------
        browseButton.setBounds(directoryLabel.getX() + 200, percentY(3), percentX(20),50);
        browseButton.setBackground(Color.decode("#495057"));
        browseButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
        browseButton.setFont(new Font("", Font.CENTER_BASELINE, 18));
        browseButton.setForeground(Color.decode("#ffffff"));
        
        browseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {    // hover
                browseButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                browseButton.setBackground(Color.decode("#084c61"));
            }

            @Override
            public void mouseExited(MouseEvent e) {    // no hover
                browseButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                browseButton.setBackground(Color.decode("#495057"));
            }
        });
        
        // --button that allows users to select a folder on their computer
        browseButton.addActionListener(e -> {   // When the button is clicked  
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // specify that only directories (folders) should be selectable.
            
            // --checks whether the user has selected a directory:
            int result = fileChooser.showOpenDialog(null);  
            if (result == JFileChooser.APPROVE_OPTION) {    
                // User selected a folder
                selectDirectoryPath = fileChooser.getSelectedFile().getAbsolutePath();
            }
        });
        
        //--------------------start processing button--------------------
        startProcessingButton.setBounds((int) (percentX(2)),percentY(16), percentX(14), 50);
        startProcessingButton.setFont(new Font("", Font.BOLD, 20));
        startProcessingButton.setBackground(Color.decode("#084c61"));
        startProcessingButton.setForeground(Color.decode("#ffffff"));
        startProcessingButton.setBorderPainted(false);
        startProcessingButton.setFocusPainted(false);

        startProcessingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {    // hover
                startProcessingButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                startProcessingButton.setBackground(Color.decode("#495057"));
            }

            @Override
            public void mouseExited(MouseEvent e) {    // no hover
                startProcessingButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                startProcessingButton.setBackground(Color.decode("#084c61"));
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {    // clicked on button
                boolean selctCheckBox;
                
                // --check user select directory or not
                if (selectDirectoryPath == "") {   
                    // --user not selected directory
                    ui.put("OptionPane.messageForeground", Color.decode("#990404"));
                    JOptionPane.showMessageDialog(null, "PLEASE SELECT DIRECTORY");
                } else {
                    selctCheckBox = checkBox.isSelected();
                    storeTableModel.setRowCount(0);
                    acuallylongestWord.setText("");
                    acuallyShortestWord.setText("");
                    
                    // --Call your function and send selectDirectoryPath and selctCheckBox to it
                    BuildThread t = new BuildThread();
                    t.BuildThread(selectDirectoryPath, selctCheckBox);
                }
            }
        });
        
        //--------------------check box--------------------
        checkBox.setBounds(percentX(2), percentY(11),255,30);
        checkBox.setFont(new Font("", Font.CENTER_BASELINE, 20));
        checkBox.setFocusPainted(false);
        checkBox.setBackground(Color.decode("#ffc857"));
        checkBox.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.decode("#B0B0B0")));
        checkBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                checkBox.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
                
        //--------------------table option-------------------
        // --scrollTable option
        scrollTable.setBounds(percentX(2),percentY(23) ,  percentX(96) ,percentY(60));

        // --storeTableModel option
        storeTableModel.addColumn("File Name");
        storeTableModel.addColumn("#Words");
        storeTableModel.addColumn("#Is");
        storeTableModel.addColumn("#ARE");
        storeTableModel.addColumn("#YOU");
        storeTableModel.addColumn("longest Word");
        storeTableModel.addColumn("Shortest Word");

        // --mainTable        
        mainTable.getTableHeader().setBackground(Color.decode("#ffc857"));
        mainTable.getTableHeader().setForeground(Color.decode("#000000"));
        mainTable.getTableHeader().setFont(new Font("", Font.BOLD, 20));
        mainTable.setBackground(Color.decode("#50514f"));
        mainTable.setForeground(Color.decode("#ffffff"));
        mainTable.setFont(new Font("", Font.BOLD, 18));
    
        // --set the Height of rows
        mainTable.setRowHeight(55);

        // --set the Width of columns
        TableColumn column = null;
        column = mainTable.getColumnModel().getColumn(0);
        column.setPreferredWidth(percentX(21));
        column = mainTable.getColumnModel().getColumn(1);
        column.setPreferredWidth(percentX(8));
        column = mainTable.getColumnModel().getColumn(2);
        column.setPreferredWidth(percentX(8));
        column = mainTable.getColumnModel().getColumn(3);
        column.setPreferredWidth(percentX(8));
        column = mainTable.getColumnModel().getColumn(4);
        column.setPreferredWidth(percentX(8));
        column = mainTable.getColumnModel().getColumn(5);
        column.setPreferredWidth(percentX(25));
        column = mainTable.getColumnModel().getColumn(6);
        column.setPreferredWidth(percentX(15));

        //prevent reorder columns in table
        mainTable.getTableHeader().setReorderingAllowed(false);

        // --like text-align:center; in css => renderer for each column in a JTable to center-align the cell content.
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for ( int i = 0 ; i  < 7 ; i++ ) {
            mainTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        // --make JFrame(gui) visible  
        this.setVisible(true);
    }
            
    // --this is critical section, so shouldn’t be two thread updating the same file name at the same time
    public synchronized static void showUpdateData(ArrayList<String> receiveNewLine) {
        int row,column,sum=0;
        
        // --after new row in table it added, this for loop will be executed 
        // --in each iteration => the break is executed and exit from loop and row not increment
        // --,untill receive new file name => answer.get(0) != searchModel.getValueAt(row,0)
        // --in this time the break not executed and row increment and row will be equal the number of rows in the table
        for(row=0;row<storeTableModel.getRowCount();row++) {
            if(receiveNewLine.get(0).equals(storeTableModel.getValueAt(row,0)) ) {
                break;
            }
        }
        
        // --after each break, this condtion will be executed because the row not equal the number of rows in the table because it not increment 
        // --will show the update of each line received
        if(row!=storeTableModel.getRowCount()) {
            // --show #word #is #are #you
            for(column=1;column<storeTableModel.getColumnCount()-2;column++) {
                sum  = Integer.parseInt(receiveNewLine.get(column))+Integer.parseInt( storeTableModel.getValueAt(row,column).toString());
                storeTableModel.setValueAt(sum,row,column);
            }
            // --show longest shortest in file 
            storeTableModel.setValueAt(receiveNewLine.get(5),row,5);
            storeTableModel.setValueAt(receiveNewLine.get(6),row,6);
        }
        
        // --this condtion will be executed when receive new file name because the row increment and row will be equal the number of rows in the table
        if(row==storeTableModel.getRowCount()){
            storeTableModel.addRow(receiveNewLine.toArray());
        }
        
        // --This method is used to notify all registered listeners that the table data has changed.
        storeTableModel.fireTableDataChanged();
        
        // show longest shortest in directory 
        if(acuallylongestWord.getText().length()<receiveNewLine.get(5).length())
            acuallylongestWord.setText(receiveNewLine.get(5));
        if(acuallyShortestWord.getText().length() == 0 || acuallyShortestWord.getText().length() > receiveNewLine.get(6).length())
            acuallyShortestWord.setText(receiveNewLine.get(6));
    }
    /*
        -first call this function:
            add new row (first row) in the table and The receiving line of data (receiveNewLine) is present in it
            
        -if file name in The receiving line of data (receiveNewLine) = file name in current row in the table:
            then the file not completed and show the receiving line of data (receiveNewLine)
    
        -if file name in The receiving line of data (receiveNewLine) != file name in current row in the table:
            then the old file is completed and the receiving line of data (receiveNewLine) come from new file
            so will add new row in the table and The receiving line of data (receiveNewLine) is present in it
    */ 
}