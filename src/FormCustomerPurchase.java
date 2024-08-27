import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;

public class FormCustomerPurchase extends JFrame {
    private JButton guessButton;
    private JPanel panel1;
    private JButton selectFileButton;
    private File selectedFile;
    private boolean fileSelected = false;
    private JTextArea textArea1;
    private JButton csvtoArffButton;
    private ReadFile readFile;

    public FormCustomerPurchase() {
        add(panel1);
        setSize(400, 400);
        setTitle("Tahmin");
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(null);
        textArea1.setBounds(50, 100, 200, 60);
        DragAndDrop drag = new DragAndDrop(textArea1, this);
        new DropTarget(textArea1, drag);
        selectFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser files = new JFileChooser();
                int returnVal = files.showOpenDialog(null);
                if (!fileSelected) {
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        selectedFile = files.getSelectedFile();
                        textArea1.setText("");
                        JOptionPane.showMessageDialog(null, "Dosya başarıyla eklendi: " + selectedFile.getName());
                        fileSelected = true;
                        selectFileButton.setEnabled(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Zaten bir dosya seçildi. Sürükleyip bırakma yöntemi kullanılamaz.");
                }
            }
        });
        guessButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedFile!=null) {
                    ReadFile readFile = new ReadFile(selectedFile);
                    CreatingAlgorithm creatingAlgorithm = new CreatingAlgorithm(readFile);
                    try {
                        creatingAlgorithm.randomTree();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Lütfen bir dosya seçin.");
                }
            }
        });
        csvtoArffButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CsvToArff csvToArff = new CsvToArff();
                try {
                    csvToArff.file();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void setReadFile(ReadFile readFile) {
        this.readFile = readFile;
    }

    public void setFile(File file) {
        this.selectedFile = file;
        this.fileSelected = true;
        this.readFile = new ReadFile(file);
    }

    public void disableSelectFileButton() {
        this.selectFileButton.setEnabled(false);
    }

    public boolean isFileSelected() {
        return this.fileSelected;
    }
}

