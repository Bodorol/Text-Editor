import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

class TextEditor extends JFrame {

    public TextEditor() {
        super("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setVisible(true);


        JPanel greenPanel = new JPanel();
        greenPanel.setLayout(new FlowLayout());
        greenPanel.setBounds(0, 20, 500, 400);
        add(greenPanel);

        JTextField fileNameArea = new JTextField(20);
        fileNameArea.setName("FilenameField");

        JButton saveButton = new JButton("Save");
        saveButton.setName("SaveButton");

        JButton loadButton = new JButton("Load");
        loadButton.setName("LoadButton");

        greenPanel.add(fileNameArea);
        greenPanel.add(saveButton);
        greenPanel.add(loadButton);

        JTextArea editArea = new JTextArea();
        editArea.setName("TextArea");
        editArea.setBounds(50, 50, 400, 275);
        JScrollPane scroller = new JScrollPane(editArea);
        scroller.setName("ScrollPane");
        scroller.setBounds(50, 50, 400, 275);
        greenPanel.add(scroller);

        saveButton.addActionListener(e -> {
            File file = new File(fileNameArea.getText());
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.print(editArea.getText());
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        });

        loadButton.addActionListener(e -> {
            File file = new File(fileNameArea.getText());
            try (Scanner scanner = new Scanner(file)) {
                editArea.setText(scanner.useDelimiter("\\A").next());
            } catch (Exception exception) {
                editArea.setText("");
                System.out.println(exception.getMessage());
            }
        });

    }

    public static void main(String[] args) {
        new TextEditor();
    }
}
