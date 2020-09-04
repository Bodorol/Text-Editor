import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        JTextField searchTextArea = new JTextField(15);
        searchTextArea.setPreferredSize(new Dimension(15, 35));
        searchTextArea.setFont(searchTextArea.getFont().deriveFont(20f));
        searchTextArea.setName("SearchField");

        JButton saveButton = new JButton(new ImageIcon("save.png"));
        saveButton.setPreferredSize(new Dimension(30, 35));
        saveButton.setName("SaveButton");

        JButton loadButton = new JButton(new ImageIcon("load.png"));
        loadButton.setPreferredSize(new Dimension(30, 35));
        loadButton.setName("OpenButton");

        JButton searchButton = new JButton(new ImageIcon("search.png"));
        searchButton.setPreferredSize(new Dimension(30, 35));
        searchButton.setName("StartSearchButton");

        JButton previousButton = new JButton(new ImageIcon("previous.png"));
        previousButton.setPreferredSize(new Dimension(30, 35));
        previousButton.setName("PreviousMatchButton");

        JButton nextButton = new JButton(new ImageIcon("next.png"));
        nextButton.setPreferredSize(new Dimension(30, 35));
        nextButton.setName("NextMatchButton");

        JCheckBox regexCheck = new JCheckBox("Use regex");
        regexCheck.setName("UseRegExCheckbox");

        JTextArea editArea = new JTextArea();
        editArea.setName("TextArea");
        JScrollPane scroller = new JScrollPane(editArea);
        scroller.setName("ScrollPane");

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setName("FileChooser");
        fileChooser.setVisible(false);

        saveButton.addActionListener(event -> {
            fileChooser.setVisible(true);
            int returnValue = fileChooser.showSaveDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedFile.getAbsolutePath();
                try (PrintWriter writer = new PrintWriter(selectedFile)) {
                    writer.print(editArea.getText());
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
                fileChooser.setVisible(false);
            }
        });

        loadButton.addActionListener(event -> {
            fileChooser.setVisible(true);
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedFile.getAbsolutePath();
                try (Scanner scanner = new Scanner(selectedFile)) {
                    editArea.setText(scanner.useDelimiter("\\A").next());
                } catch (Exception exception) {
                    editArea.setText("");
                    System.out.println(exception.getMessage());
                }
                fileChooser.setVisible(false);
            }
        });

        searchButton.addActionListener(event -> {
            SearchThread search = new SearchThread(editArea, searchTextArea.getText(), regexCheck.isSelected());
            search.run();
        });

        previousButton.addActionListener(event -> {
            SearchThread.getResult("previous");
        });

        nextButton.addActionListener(event -> {
            SearchThread.getResult("next");
        });



        JMenuBar fileMenuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("MenuFile");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenu searchMenu = new JMenu("Search");
        searchMenu.setName("MenuSearch");
        searchMenu.setMnemonic(KeyEvent.VK_S);

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.setName("MenuSave");
        JMenuItem loadItem = new JMenuItem("Open");
        loadItem.setName("MenuOpen");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setName("MenuExit");

        JMenuItem searchItem = new JMenuItem("Start Search");
        searchItem.setName("MenuStartSearch");
        JMenuItem previousItem = new JMenuItem("Previous Match");
        previousItem.setName("MenuPreviousMatch");
        JMenuItem nextItem = new JMenuItem("Next Match");
        nextItem.setName("MenuNextMatch");
        JMenuItem regexItem = new JMenuItem("Use Regular Expressions");
        regexItem.setName("MenuUseRegExp");

        saveItem.addActionListener(event -> {
            fileChooser.setVisible(true);
            int returnValue = fileChooser.showSaveDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedFile.getAbsolutePath();
                try (PrintWriter writer = new PrintWriter(selectedFile)) {
                    writer.print(editArea.getText());
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
                fileChooser.setVisible(false);
            }
        });

        loadItem.addActionListener(event -> {
            fileChooser.setVisible(true);
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedFile.getAbsolutePath();
                try (Scanner scanner = new Scanner(selectedFile)) {
                    editArea.setText(scanner.useDelimiter("\\A").next());
                } catch (Exception exception) {
                    editArea.setText("");
                    System.out.println(exception.getMessage());
                }
                fileChooser.setVisible(false);
            }
        });

        searchItem.addActionListener(event -> {
            SearchThread search = new SearchThread(editArea, searchTextArea.getText(), regexCheck.isSelected());
            search.run();
        });

        previousItem.addActionListener(event -> {
            SearchThread.getResult("previous");
        });

        nextItem.addActionListener(event -> {
            SearchThread.getResult("next");
        });

        regexItem.addActionListener(event -> {
            regexCheck.setSelected(true);
        });

        exitItem.addActionListener(event -> System.exit(0));

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        searchMenu.add(searchItem);
        searchMenu.add(previousItem);
        searchMenu.add(nextItem);
        searchMenu.add(regexItem);

        fileMenuBar.add(fileMenu);
        fileMenuBar.add(searchMenu);
        setJMenuBar(fileMenuBar);

        textPanel.add(scroller, BorderLayout.CENTER);
        savePanel.add(saveButton);
        savePanel.add(loadButton);
        savePanel.add(searchTextArea);
        savePanel.add(searchButton);
        savePanel.add(previousButton);
        savePanel.add(nextButton);
        savePanel.add(regexCheck);

        add(fileChooser);
        add(textPanel);
        add(savePanel);


        setVisible(true);
    }

    public static void main(String[] args) {
        TextEditor text = new TextEditor();

    }
}

class SearchThread extends Thread {

    private static String query;
    private static JTextArea textArea;
    private volatile static List<Integer> matches = new LinkedList<>();
    private static ListIterator<Integer> iter;
    private volatile static boolean done;
    private static boolean regex;
    private static String previous;

    public SearchThread(JTextArea textArea, String query, boolean regex) {
        this.textArea = textArea;
        this.query = query;
        this.done = false;
        this.regex = regex;
    }

    public static void getResult(String choice) {
        String text = textArea.getText();
        Pattern pattern = Pattern.compile(query);
        int index = -1;
        if (done) {
            if ("next".equals(choice)) {
                if ("previous".equals(previous)) {
                    iter.next();
                }
                if (!iter.hasNext()) {
                    iter = matches.listIterator();
                }
                previous = "next";
                index = iter.next();
            } else if ("previous".equals(choice) ) {
                if ("next".equals(previous)) {
                    iter.previous();
                }
                if (!iter.hasPrevious()) {
                    while (iter.hasNext()) {
                        iter.next();
                    }
                }
                index = iter.previous();
                previous = "previous";
            }
        }
        if (index >= 0) {
            Matcher matcher = pattern.matcher(text.substring(index));
            if (matcher.find()) {
                String foundText = matcher.group();
                textArea.setCaretPosition(index + foundText.length());
                textArea.select(index, index + foundText.length());
                textArea.grabFocus();
            }
        }
    }

    @Override
    public void run() {
        matches = new LinkedList<>();
        String text = textArea.getText();
        if (regex) {
            Pattern pattern = Pattern.compile(query);
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                matches.add(matcher.start());
            }
        } else {
            int length = query.length();
            int index = text.indexOf(query);
            while (index != -1) {
                matches.add(index);
                index = text.indexOf(query, index + length);
            }
        }
        iter = matches.listIterator();
        previous = "";
        done = true;
        SearchThread.getResult("next");
    }
}
