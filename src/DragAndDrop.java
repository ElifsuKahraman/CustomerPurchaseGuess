import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

public class DragAndDrop extends DropTargetAdapter {
    private JTextArea textArea1;
    private FormCustomerPurchase form;

    public DragAndDrop (JTextArea textArea1, FormCustomerPurchase form) {
        this.textArea1 = textArea1;
        this.form = form;
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        try {
            if (form.isFileSelected()) {
                JOptionPane.showMessageDialog(null, "Zaten bir dosya seçildi. Sürükleyip bırakma yöntemi kullanılamaz.");
                dtde.rejectDrop();
                return;
            }
            Transferable tr = dtde.getTransferable();
            DataFlavor[] flavors = tr.getTransferDataFlavors();
            for (DataFlavor flavor : flavors) {
                if (flavor.isFlavorJavaFileListType()) {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                    List<File> files = (List<File>) tr.getTransferData(flavor);
                    for (File file : files) {
                        form.setReadFile(new ReadFile(file));
                        form.setFile(file);
                        JOptionPane.showMessageDialog(null, "Dosya başarıyla eklendi: " + file.getName());
                        form.disableSelectFileButton();
                    }
                    dtde.dropComplete(true);
                    return;
                }
            }
            dtde.rejectDrop();
        } catch (Exception e) {
            e.printStackTrace();
            dtde.rejectDrop();
        }
    }
}
