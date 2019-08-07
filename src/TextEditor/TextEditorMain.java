package TextEditor;

import javax.swing.JTextPane;
import javax.swing.UIManager;

public class TextEditorMain extends JTextPane{

	/**
	 * Main Class
	 */
	private static final long serialVersionUID = 1L;
	public final static String NAME = "My Text Editor";
	
	public static void main(String[] args) 
	{
		
		
		try {
            //here you can put the selected theme class name in JTattoo
            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
 
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TextEditorMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TextEditorMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TextEditorMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TextEditorMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

		
		new GUI().setVisible(true);
	}

}
