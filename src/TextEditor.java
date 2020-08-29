import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Scanner;

class TextEditor extends JFrame {

    public TextEditor() {
        super("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 475);
        setLocationRelativeTo(null);

        JPanel savePanel = new JPanel();
        savePanel.setLayout(new FlowLayout());

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        textPanel.setBounds(18, 50, 500, 350);

        JTextField fileNameArea = new JTextField(20);
        fileNameArea.setName("FilenameField");

        JButton saveButton = new JButton("Save");
        saveButton.setName("SaveButton");

        JButton loadButton = new JButton("Load");
        loadButton.setName("LoadButton");

        JTextArea editArea = new JTextArea();
        editArea.setName("TextArea");
        JScrollPane scroller = new JScrollPane(editArea);
        scroller.setName("ScrollPane");

        saveButton.addActionListener(event -> {
            File file = new File(fileNameArea.getText());
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.print(editArea.getText());
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        });

        loadButton.addActionListener(event -> {
            File file = new File(fileNameArea.getText());
            try (Scanner scanner = new Scanner(file)) {
                editArea.setText(scanner.useDelimiter("\\A").next());
            } catch (Exception exception) {
                editArea.setText("");
                System.out.println(exception.getMessage());
            }
        });

        JMenuBar fileMenuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("MenuFile");
        fileMenu.setMnemonic(KeyEvent.VK_F);


        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.setName("MenuSave");
        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.setName("MenuLoad");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setName("MenuExit");

        saveItem.addActionListener(event -> {
            File file = new File(fileNameArea.getText());
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.print(editArea.getText());
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        });

        loadItem.addActionListener(event -> {
            File file = new File(fileNameArea.getText());
            try (Scanner scanner = new Scanner(file)) {
                editArea.setText(scanner.useDelimiter("\\A").next());
            } catch (Exception exception) {
                editArea.setText("");
                System.out.println(exception.getMessage());
            }
        });

        exitItem.addActionListener(event -> System.exit(0));

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        fileMenuBar.add(fileMenu);
        setJMenuBar(fileMenuBar);

        textPanel.add(scroller, BorderLayout.CENTER);
        savePanel.add(fileNameArea);
        savePanel.add(saveButton);
        savePanel.add(loadButton);

        add(textPanel);
        add(savePanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        TextEditor text = new TextEditor();
    }
}
