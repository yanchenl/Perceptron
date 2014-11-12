package com.perceptron;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JFrame frame = new JFrame("Perceptron");
    private JButton input1 = new JButton("Select");
    private JButton submit = new JButton("Submit");
    private JLabel inLabel1 = new JLabel("Data:");
    InMonitor1 inMonitor1 = new InMonitor1();
    SubmitMonitor submitMonitor = new SubmitMonitor();
    TextMonitor textMonitor = new TextMonitor();
    private JTextField data1 = new JTextField(10);
    String inputFile1;

    public GUI() {
        this.frame.setLayout(null);
        this.frame.setSize(600, 150);
        this.inLabel1.setBounds(30, 25, 80, 20);
        this.input1.setBounds(450, 25, 80, 20);
        this.submit.setBounds(450, 70, 80, 20);
        this.data1.setBounds(100, 25, 300, 20);
        this.frame.add(inLabel1);
        this.frame.add(input1);
        this.frame.add(data1);
        this.frame.add(submit);
        this.frame.setVisible(true);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.input1.addActionListener(inMonitor1);
        this.submit.addActionListener(submitMonitor);
        this.submit.addActionListener(textMonitor);
    }

    public class InMonitor1 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            File workingDirectory = new File(System.getProperty("user.dir"));
            fc.setCurrentDirectory(workingDirectory);
            fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fc.showDialog(new JLabel(), "Select Train Data");
            File file = fc.getSelectedFile();
            //System.out.println(file.getAbsolutePath());
            inputFile1 = file.getAbsolutePath();
            data1.setText(inputFile1);
        }
    }

    public class SubmitMonitor implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            inputFile1 = getInput1();
            System.out.println(inputFile1);
            //begin to run the following code
        }
    }

    public class TextMonitor implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String d1 = data1.getText();
            inputFile1 = d1;
        }
    }

    public String getInput1() {
        return inputFile1;
    }


    public static void main(String[] args) throws InterruptedException {
        GUI gui = new GUI();
        Thread.sleep(10000);
        String inputFile1 = gui.getInput1();
        System.out.println(inputFile1);
    }
}
