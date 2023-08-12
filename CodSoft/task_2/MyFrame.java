package task_2;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MyFrame extends JFrame {

    HashMap<String, Integer> map = new HashMap<>();
    Path path;
    String[] arrString;
    String str1;
    int count1 = 0;
    JLabel heading;
    JLabel subheadingLabel;
    JLabel l;
    JLabel label1;
    JTextField textField;
    JButton button, submit;
    ButtonListener buttonListener;
    ImageIcon image;
    JRadioButton viewStatistics;

    // Constructor
    public MyFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Word counter !");
        // Get the content pane
        Container contentPane = getContentPane();
        contentPane.setBackground(new Color(11, 12, 16));
        this.setBounds(500, 150, 500, 500);
        this.setLayout(null);

        /* heading label */
        heading = new JLabel("Word Counter ");
        heading.setForeground(new Color(195, 7, 63));
        heading.setBounds(120, 5, 400, 50);
        heading.setFont(new Font("times new roman", Font.BOLD, 35));

        // heading 2 (Enter text )
        subheadingLabel = new JLabel("Enter a text or select file :");
        subheadingLabel.setForeground(Color.white);
        subheadingLabel.setBounds(50, 80, 300, 50);
        subheadingLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));

        /** Text field **/
        textField = new JTextField(10);
        textField.setBounds(50, 140, 400, 30);
        textField.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
        textField.setForeground(Color.RED);
        textField.setBackground(new Color(173, 181, 189));
        textField.setCaretColor(new Color(31, 40, 51));

        // instance of jButton
        button = new JButton("Select file");
        button.setBounds(350, 200, 100, 30);
        button.setBorder(new LineBorder(new Color(195, 7, 63)));
        button.setBackground(new Color(11, 12, 16));
        button.setForeground(Color.WHITE);
        button.setFocusable(false);// to get rid of the box around text on the button
        buttonListener = new ButtonListener();
        button.addActionListener(buttonListener);

        l = new JLabel("no file selected");
        l.setBounds(100, 200, 300, 30);

        label1 = new JLabel("Total words : ");
        label1.setBounds(50, 250, 300, 30);
        label1.setForeground(Color.white);
        label1.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));

        submit = new JButton("Submit");
        submit.setBounds(350, 400, 100, 30);
        submit.setBorder(new LineBorder(new Color(195, 7, 63)));
        submit.setBackground(new Color(11, 12, 16));
        submit.setForeground(Color.WHITE);
        submit.setFocusable(false);// to get rid of the box around text on the button
        // buttonListener = new ButtonListener();
        submit.addActionListener(buttonListener);

        // imageLabel
        image = new ImageIcon("task_2/Images/stock-chart (1).png");
        viewStatistics = new JRadioButton("view Statistics");
        viewStatistics.addActionListener(buttonListener);
        viewStatistics.setIcon(image);
        viewStatistics.setBounds(60, 280, 250, 200);
        viewStatistics.setBackground(new Color(11, 12, 16));
        viewStatistics.setForeground(Color.white);

        contentPane.add(viewStatistics);
        contentPane.add(submit);
        contentPane.add(l);
        contentPane.add(label1);
        contentPane.add(button);
        contentPane.add(subheadingLabel);
        contentPane.add(textField);
        contentPane.add(heading);
        this.setVisible(true);
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String com = e.getActionCommand();
            System.out.println(com);// checking
            if (com.equals("Select file")) {
                JFileChooser fileChooser = new JFileChooser();

                // restrict the user to select files of all types
                fileChooser.setAcceptAllFileFilterUsed(false);
                // set a title for the dialog
                fileChooser.setDialogTitle("Select a .txt file");

                // only allow files of .txt extension
                FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt");
                fileChooser.addChoosableFileFilter(restrict);

                // to open our dialog menu
                int response = fileChooser.showOpenDialog(null);// this will select the file to open
                System.out.println(response);

                // if the user selects a file
                if (response == JFileChooser.APPROVE_OPTION) {
                    // set the label to the path of the selected file
                    l.setText(fileChooser.getSelectedFile().getAbsolutePath());
                    path = Path.of(fileChooser.getSelectedFile().getAbsolutePath());
                    try {
                        str1 = Files.readString(path);
                    } catch (IOException iException) {
                        System.err.print(iException);
                    }
                }
                // if the user cancelled the operation
                else {
                    l.setText("the user cancelled the operation");
                }
            } else if (com.equals("view Statistics")) {
                //to display the barchart on click of view statistics
                displayChart();

            } else {
                count1 = 0;
                String s = textField.getText();
                String delimiters = "[\\s,.!?;:]+"; // Proper word boundary regular expression
                if (textField.getText().isEmpty()) {
                    arrString = str1.split(delimiters);
                } else {
                    arrString = s.split(delimiters);
                }
                WordCounter();
            }
        }
    }

    private void WordCounter() {
        try {
            count1 = 0;
            // Printing the string

            for (String word : arrString) {
                if (map.containsKey(word)) {
                    map.put(word, map.get(word) + 1);
                } else {
                    map.put(word, 1);
                }
                count1++;
            }           
            label1.setText("Total words : " + count1);
        } catch (Exception exception) {
            System.err.print(exception);
        }
    }

    private JFreeChart createChart(CategoryDataset dataset) {
        return ChartFactory.createBarChart(
                "Word Counts",
                "Word",
                "Count",
                dataset);
    }

    // Main method to display the chart
    private void displayChart() {
        try {
            count1 = 0;
            // Create and display the chart
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            HashMap<String, Integer> map1 = new HashMap<>();
            for (String word : arrString) {
                if (map1.containsKey(word)) {
                    map1.put(word, map1.get(word) + 1);
                } else {
                    map1.put(word, 1);
                }
            }
            for (String word : map1.keySet()) {
                int count = map1.get(word);
                dataset.addValue(count, "Word Count", word);
            }

            JFreeChart chart = createChart(dataset);
            ChartPanel chartPanel = new ChartPanel(chart);
            JFrame chartFrame = new JFrame("Word Counts");
            chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            chartFrame.add(chartPanel);
            chartFrame.pack();
            chartFrame.setVisible(true);
        } catch (Exception exception) {
            System.err.print(exception);
        }
    }

}

