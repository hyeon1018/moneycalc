
package Frame;

import java.awt.EventQueue;

import javax.swing.UIManager;

public class Main {

	
	/** Launch the application. **/
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public static void setUIFont (javax.swing.plaf.FontUIResource f)
    {
        java.util.Enumeration keys = UIManager.getDefaults().keys();
    
        while (keys.hasMoreElements()) 
        {
          Object key = keys.nextElement();
          Object value = UIManager.get (key);
    
          if (value instanceof javax.swing.plaf.FontUIResource)
            UIManager.put (key, f);
        }
    }   
}
