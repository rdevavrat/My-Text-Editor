package TextEditor;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit;

public class GUI extends JFrame implements ActionListener 
{

	private static final long serialVersionUID = 1L;
	
	private final JMenuBar menuBar;
    
	private final JMenu menuFile, menuEdit, menuFind;
	
	private final JMenuItem newFile, openFile, saveFile, close, cut, copy, paste, clearFile, increaseFont, decreaseFont, selectAll, quickFind;
	
	// setup icons - File Menu
    private final ImageIcon newIcon = new ImageIcon("icons/new.png");
    private final ImageIcon openIcon = new ImageIcon("icons/open.png");
    private final ImageIcon saveIcon = new ImageIcon("icons/save.png");
    private final ImageIcon closeIcon = new ImageIcon("icons/close.png");
    
    // setup icons - Edit Menu
    private final ImageIcon clearIcon = new ImageIcon("icons/clear.png");
    private final ImageIcon cutIcon = new ImageIcon("icons/cut.png");
    private final ImageIcon copyIcon = new ImageIcon("icons/copy2.png");
    private final ImageIcon pasteIcon = new ImageIcon("icons/paste2.png");
    private final ImageIcon selectAllIcon = new ImageIcon("icons/selectall.png");
    private final ImageIcon fontPlusIcon = new ImageIcon("icons/fontPlus.png");
    private final ImageIcon fontMinusIcon = new ImageIcon("icons/fontMinus.png");
    
    // setup icons - Search Menu
    private final ImageIcon searchIcon = new ImageIcon("icons/search.png");
    
	private final JTextArea textArea;
	private int fontSize = 18;
	
	private boolean edit = false;
	
	private final Action selectAllAction;
	
	/**
	 * Prepares the Main GUI Window 
	 */
	public GUI()
	{
		// Set the initial size of Window
		setSize(1000, 600);
		
		// Set the title of the window
        setTitle("Untitled | " + TextEditorMain.NAME);
		
        // Set Application Icon
        setIconImage(Toolkit.getDefaultToolkit().getImage("icons/note.png"));
        
		// Set the default close operation (exit when it gets closed)
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // center the frame on the monitor
        setLocationRelativeTo(null);
        
        // Set a default font for the TextArea
        textArea = new JTextArea("", 0, 0);
        textArea.setFont(new Font("Calibri Light", Font.PLAIN, fontSize));
        textArea.setTabSize(2);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setWrapStyleWord(true);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().setLayout(new BorderLayout()); // the BorderLayout bit makes it fill it automatically
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane);
        getContentPane().add(panel);
        
        // Set the Menus
        menuFile = new JMenu("File");
        menuEdit = new JMenu("Edit");
        menuFind = new JMenu("Search");
       
        // Set the Menu Items
        newFile = new JMenuItem("New", newIcon);
        openFile = new JMenuItem("Open", openIcon);
        saveFile = new JMenuItem("Save", saveIcon);
        close = new JMenuItem("Quit", closeIcon);
        clearFile = new JMenuItem("Clear", clearIcon);
        quickFind = new JMenuItem("Quick Find", searchIcon);
        increaseFont = new JMenuItem("Increase Font Size", fontPlusIcon);
        decreaseFont = new JMenuItem("Decrease Font Size", fontMinusIcon);
        
        // Set the ToolBar
        menuBar = new JMenuBar();
        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        menuBar.add(menuFind);
        this.setJMenuBar(menuBar);
        
        // SET THE FILE MENU
        
        	// New File
        	newFile.addActionListener(this);  // Adding an action listener (so we know when it's been clicked).
        	newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK)); // Set a keyboard shortcut
        	menuFile.add(newFile); // Add New File option into File Menu
        	
        	// Open File
            openFile.addActionListener(this);
            openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
            menuFile.add(openFile);

            // Save File
            saveFile.addActionListener(this);
            saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
            menuFile.add(saveFile);
            
            // Close File
            close.addActionListener(this);
            close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
            menuFile.add(close);
       
        // SET THE EDIT MENU   
            
            // Set Actions:
            selectAllAction = new SelectAllAction("Select All", clearIcon, "Select all text", new Integer(KeyEvent.VK_A),
                    textArea);

            this.setJMenuBar(menuBar);
            
            // Select All Text
            selectAll = new JMenuItem(selectAllAction);
            selectAll.setText("Select All");
            selectAll.setIcon(selectAllIcon);
            selectAll.setToolTipText("Select All");
            selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
            menuEdit.add(selectAll);
            
            // Clear File
            clearFile.addActionListener(this);
            clearFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.CTRL_MASK));
            menuEdit.add(clearFile);
            
            // Cut Text
            cut = new JMenuItem(new DefaultEditorKit.CutAction());
            cut.setText("Cut");
            cut.setIcon(cutIcon);
            cut.setToolTipText("Cut");
            cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
            menuEdit.add(cut);
            
            // Copy Text
            copy = new JMenuItem(new DefaultEditorKit.CopyAction());
            copy.setText("Copy");
            copy.setIcon(copyIcon);
            copy.setToolTipText("Copy");
            copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
            menuEdit.add(copy);

            // Paste Text
            paste = new JMenuItem(new DefaultEditorKit.PasteAction());
            paste.setText("Paste");
            paste.setIcon(pasteIcon);
            paste.setToolTipText("Paste");
            paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
            menuEdit.add(paste);
            
            // Increase Font
            increaseFont.addActionListener(this);
            increaseFont.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.CTRL_MASK));
            menuEdit.add(increaseFont);
            
            // Decrease Font
            decreaseFont.addActionListener(this);
            decreaseFont.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.CTRL_MASK));
            menuEdit.add(decreaseFont);
            
       // SET THE SEARCH MENU   
            
            // Find Word
            quickFind.addActionListener(this);
            quickFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
            menuFind.add(quickFind);
	}
	
	// Actions Performed
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		/**
		 * If the source was the "close" file option
		 */ 
		if(e.getSource() == close)
		{
			if(edit)
			{
				Object[] options = {"Save and Exit","Don't Save and Exit", "Cancel"};
				int n = JOptionPane.showOptionDialog(this, "Do you want to save the file ?", "Question",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
				
				if(n == 0)
				{	
					// Save and Exit
					saveFile();
					this.dispose(); // Dispose all the resources and close the application
				}
				else
					if(n == 1)
					{	
						// Don't save and exit
						this.dispose(); // Dispose all the resources and close the application
					}
			}
			else
			{
				this.dispose(); // Dispose all the resources and close the application
			}
		}
		else
			/**
			 * If the source was the "new" file option
			 */
			if(e.getSource() == newFile)
			{
				if(edit)
				{
					 Object[] options = {"Save", "No Save", "Return"};
		                int n = JOptionPane.showOptionDialog(this, "Do you want to save the file at first ?", "Question",
		                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
		                if(n == 0)
		                {
		                	// Save
		                	saveFile();
		                	edit = false;
		                }
		                else
		                	if(n == 1)
		                	{
		                		edit = false;
		                		clear(textArea);
		                	}
				}
				else
				{
					clear(textArea);
				}
			}
			else
				/**
				 * If the source was the "open" file option
				 */ 
				if(e.getSource() == openFile)
				{
					JFileChooser open = new JFileChooser(); // open up a file chooser (a dialog for the user to  browse files to open)
		            int option = open.showOpenDialog(this); // get the option that the user selected (approve or cancel)
		            
		            if(option == JFileChooser.APPROVE_OPTION)
		            {
		            	clear(textArea); // Clear the Text Area before applying the file contents
		            	try
		            	{
		            		File openFile = open.getSelectedFile();
		            		setTitle(openFile.getName() + " | " + TextEditorMain.NAME);
		                    Scanner scan = new Scanner(new FileReader(openFile.getPath()));
		                    while (scan.hasNext()) 
		                    {
		                        textArea.append(scan.nextLine() + "\n");
		                    }
		                    scan.close();
		            	}
		            	catch(Exception ex)
		            	{
		            		System.err.println(ex.getMessage());
		            	}
		            }
				}
				else
					/**
					 * If the source was the "Save" file option
					 */
					if(e.getSource() == saveFile)
					{
						saveFile();
					}
		
		/**
		 * Clear File Code
		 */
		if(e.getSource() == clearFile)
		{
			Object[] options = {"Yes", "No"};
            int n = JOptionPane.showOptionDialog(this, "Are you sure to clear the text Area ?", "Question",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            
            if(n == 0)
            {
            	clear(textArea);
            }
		}
		
		/**
		 * Find and Replace tool
		 */
		if(e.getSource() == quickFind)
		{
			new Find(textArea);
		}
		
		/**
		 * Increase the size of the Font
		 */
		if(e.getSource() == increaseFont)
		{
			increaseFontSize();
		}
		
		/**
		 * Decrease the size of the Font
		 */
		if(e.getSource() == decreaseFont)
		{
			decreaseFontSize();
		}
	}
	
	
	class SelectAllAction extends AbstractAction 
	{
		/**
         * Used for Select All function
         */
        private static final long serialVersionUID = 1L;

        public SelectAllAction(String text, ImageIcon icon, String desc, Integer mnemonic, final JTextArea textArea) 
        {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        public void actionPerformed(ActionEvent e) 
        {
            textArea.selectAll();
        }
    }
	
	
	// Increases the font size
	private void increaseFontSize() 
	{
		try 
		{
				textArea.setFont(new java.awt.Font("Calibri Light", 0, ++fontSize));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	// Decreases the font size
	private void decreaseFontSize() 
	{
		try 
		{
				textArea.setFont(new java.awt.Font("Calibri Light", 0, --fontSize));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	// Clear all the text from the File
	private void clear(JTextArea textArea)
	{
		textArea.setText("");
	}
	
	// Save File Functionality
	private void saveFile() {
        // Open a file chooser
        JFileChooser fileChoose = new JFileChooser();
        // Open the file, only this time we call
        int option = fileChoose.showSaveDialog(this);

        /*
             * ShowSaveDialog instead of showOpenDialog if the user clicked OK
             * (and not cancel)
         */
        if (option == JFileChooser.APPROVE_OPTION) {
            try {
                File openFile = fileChoose.getSelectedFile();
                setTitle(openFile.getName() + " | " + TextEditorMain.NAME);

                BufferedWriter out = new BufferedWriter(new FileWriter(openFile.getPath()));
                out.write(textArea.getText());
                out.close();

                edit = false;
            } catch (Exception ex) { // again, catch any exceptions and...
                // ...write to the debug console
                System.err.println(ex.getMessage());
            }
        }
    }
	
}