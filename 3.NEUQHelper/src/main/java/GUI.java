import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame{
    private JPanel panel1;
    private JList list1;
    private JEditorPane editorPane1;

    public GUI(JPanel panel1, JList list1, JEditorPane editorPane1) throws HeadlessException {
        this.panel1 = panel1;
        this.list1 = list1;
        this.editorPane1 = editorPane1;
        this.setVisible(true);
    }
}
