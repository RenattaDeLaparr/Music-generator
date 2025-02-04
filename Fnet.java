package add;

public class Fnet extends javax.swing.JFrame
{
  /**
   * Base Functionality
   */
  /**
   * constructor
   */
  public Fnet()
  {
    super("Addition");
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    getContentPane().add(ition_);
    setSize(100,100);
    addWindowListener(new java.awt.event.WindowAdapter()
    {
      public void windowOpened(final java.awt.event.WindowEvent e)
      {
        ition_.init();
        ition_.start();
      }
    });
  }
  private final Ition ition_=new Ition();
  /**
   * Dispose
   */
  public void dispose()
  {
    ition_.stop();
    ition_.destroy();
    super.dispose();
  }

  /**
   * Main program
   */

  public static void main(final String[] args)
  {
    Fnet f=new Fnet()
    {
      public void dispose()
      {
        super.dispose();
        System.exit(0);
      }
    };
    java.awt.Dimension s=java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    f.setLocation((s.width-f.getWidth())/2,(s.height-f.getHeight())/2);
    f.setVisible(true);
  }
}
