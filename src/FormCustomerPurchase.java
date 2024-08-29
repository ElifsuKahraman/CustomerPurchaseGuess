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
    private ReadFile readFile;

    public FormCustomerPurchase(String[] args) {
        add(panel1);
        setSize(600, 600);
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
                        setFile(selectedFile);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Zaten bir dosya seçildi. Sürükleyip bırakma yöntemi kullanılamaz.");
                }
            }
        });
        guessButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedFile != null) {
                    try {
                        if (selectedFile.getName().endsWith(".csv")) {
                            readFile = new ReadFile(selectedFile);
                            CsvToArff csvToArff = new CsvToArff(readFile);
                            csvToArff.file();
                            JOptionPane.showMessageDialog(null,"Dosyanız .arff formatına dönüştürüldü.");
                        } else if (selectedFile.getName().endsWith(".arff")) {
                            readFile = new ReadFile(selectedFile);
                        }else{
                            JOptionPane.showMessageDialog(null,"Hata");
                        }
                        CreatingAlgorithm creatingAlgorithm = new CreatingAlgorithm(readFile);
                        creatingAlgorithm.randomTree();
                        creatingAlgorithm.comment();

                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Lütfen bir dosya seçin.");
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

