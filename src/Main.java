import javax.swing.*;

public class Main {
    public static void main (String[]args) throws Exception {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (UnsupportedLookAndFeelException e) {
                throw new RuntimeException(e);
            }

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    FormCustomerPurchase frm = new FormCustomerPurchase();
                    frm.setVisible(true);
                }
            });
        }
    }



