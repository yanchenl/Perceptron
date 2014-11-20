package com.perceptron;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class PerceptronTest {

    static int MAX_STEP = 10000;
    static int ALL_INSTANCE = 958;
    static int TRAIN_INSTANCE = 820;
    static int TEST_INSTANCE = 200; // choose the both 100 positive and 100
                                    // negative data to use as the test data
    static int FEATURE = 9;
    static int THRESHOLD = 0;
    static double RATE = 0.1;

    public static void main(String args[]) {

        // create GUI to let user set input
        GUI gui = new GUI();
        String inputFile1 = gui.getInput1();

        while (inputFile1 == null) {
            try {
                Thread.sleep(6000);
                inputFile1 = gui.getInput1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Begin to run");
        // String inputFile1 = "tic-tac-toe.data";

        double[][] x = new double[ALL_INSTANCE][FEATURE + 1]; // x store all the
                                                              // data
        double[][] y = new double[TEST_INSTANCE][FEATURE + 1]; // y store the
                                                               // testing data
        int[] actual = new int[ALL_INSTANCE]; // actual store the class
        int step, globalError, error, predict;
        double[] w = new double[FEATURE]; // w[] store the weight for each
                                          // feature
        double b;

        // read the file
        ArrayList<String> dataList = getFile(inputFile1);
        int dataSize = dataList.size();
        String data[][] = new String[dataSize][10];

        // data[i][j] is the whole array to store the file
        for (int i = 0; i < dataSize; i++) {
            data[i] = dataList.get(i).split(",");
        }

        // turn the data into numerical value
        for (int i = 0; i < ALL_INSTANCE; i++) {
            for (int j = 0; j < 8; j++) {
                if (data[i][j].equals("x")) {
                    x[i][j] = 2;
                } else if (data[i][j].equals("o")) {
                    x[i][j] = -2;
                } else if (data[i][j].equals("b")) {
                    x[i][j] = 0;
                }
            }
            if (data[i][9].equals("positive")) {
                actual[i] = 1;
            } else if (data[i][9].equals("negative")) {
                actual[i] = 0;
            }
        }

        // randomly generate weight between 0 and 1
        for (int j = 0; j < FEATURE; j++) {
            w[j] = randNum(0, 1);
        }
        // b stands for bias
        b = randNum(0, 1);

        // update weights and bias according to the predict result
        step = 0;
        do {
            step++;
            globalError = 0;
            for (int i = 0; i < TRAIN_INSTANCE; i++) {
                predict = classify(THRESHOLD, w, x[i], b);
                error = predict - actual[i];
                for (int j = 0; j < FEATURE; j++) {
                    w[j] += RATE * error * x[i][j];
                }
                b += RATE * error;
                globalError += error * error;
            }
        } while (globalError != 0 && step <= MAX_STEP);

        // print the feature and weight
        // for (int j = 0; j < FEATURE; j++) {
        // System.out.println("Feature: " + (j + 1) + " Weight: " + w[j]);
        // }
        // System.out.println("b:  " + "Weight:" + b);

        int[] actualTest = new int[TEST_INSTANCE];
        // add first 100 data to do the test
        for (int i = 0; i < TEST_INSTANCE / 2; i++) {
            for (int j = 0; j < 8; j++) {
                y[i][j] = x[i][j];
                actualTest[i] = actual[i];
            }
        }
        // add last 100 data to do the test
        for (int i = ALL_INSTANCE - TEST_INSTANCE / 2; i < ALL_INSTANCE; i++) {
            for (int j = 0; j < 8; j++) {
                y[i - 758][j] = x[i][j];
                actualTest[i - 758] = actual[i];
            }
        }

        int score = 0;
        // do the test
        for (int i = 0; i < TEST_INSTANCE; i++) {
            predict = classify(THRESHOLD, w, y[i], b);
//             System.out.println("Test Instance" + (i + 1) + " Predict: " + predict
//             + " Actual: " + actualTest[i]);
            if (predict == actualTest[i]) {
                score++;
            }
        }
        double precision = (double) score / (double) TEST_INSTANCE;

        System.out.println("Precision = " + precision);

        // create the GUI output
        JFrame frame = new JFrame("Result");
        String[] titles = { "Feature", "Weight" };
        Object[][] info = new Object[TEST_INSTANCE][2];
        for (int j = 0; j < FEATURE; j++) {
            info[j][0] = j + 1;
            info[j][1] = w[j];
        }
        info[FEATURE][0] = "bias";
        info[FEATURE][1] = b;
        info[FEATURE+1][0] = "Precision";
        info[FEATURE+1][1] = precision;

        // JTable for display
        JTable table = new JTable(info, titles);
        JScrollPane scr = new JScrollPane(table);
        frame.add(scr);
        frame.setSize(400, 250);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent arg0) {
                System.exit(1);
            }
        });
        
    }

    // func classify is for the classification of the dataset
    private static int classify(int threshold, double[] w, double[] xi, double b) {
        double pro = b;
        for (int j = 0; j < FEATURE; j++) {
            pro += xi[j] * w[j];
        }
        if (pro >= threshold) {
            return 0;
        } else
            return 1;
    }

    // func randNum is for produce the random number
    public static double randNum(int min, int max) {
        Random rand = new Random();
        double randomValue = min + (max - min) * rand.nextDouble();
        return randomValue;
    }

    // get the file and put its content to an arraylist
    private static ArrayList<String> getFile(String string) {
        Scanner s = null;
        try {
            s = new Scanner(new File(string));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<String> list = new ArrayList<String>();
        while (s.hasNext()) {
            list.add(s.next());
        }
        s.close();
        return list;
    }
}
