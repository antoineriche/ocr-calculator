package fr.ar.occ.calculator.view;

import fr.ar.occ.calculator.NumberListener;
import fr.ar.occ.calculator.Operator;
import fr.ar.occ.calculator.OperatorListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements
        NumberListener, OperatorListener {

    /**
     * The current value
     */
    private Double value;
    /**
     * Flag is typing new number
     */
    private boolean FLAG_IS_TYPING = false;
    /**
     * The Historic panel
     */
    private JLabel history = new JLabel();
    /**
     * Flag to show/hide Historic panel
     */
    private boolean FLAG_HISTORY_VISIBLE = false;
    /**
     * The current @link {@link Operator}
     */
    private Operator currentOperation;
    /**
     * Name of the window
     */
    private static final String NAME = "calculator";
    /**
     * Dimension of the window
     */
    private static final Dimension WINDOW = new Dimension(250, 300);
    /**
     * The screen component
     */
    private Screen screen = new Screen();
    /**
     * Background color
     */
    private static final Color backgroundColor = Color.decode("#A5D6A7");

    public Calculator() {
        this.setTitle(NAME.toUpperCase());
        this.setSize(WINDOW);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        this.initPanel();
        this.setVisible(true);

        screen.reset();
        reset();
    }

    private class RAZListener   implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            reset();
            screen.reset();
        }
    }
    private class EqualListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(FLAG_IS_TYPING && (null != currentOperation)){
                try {
                    value = compute(value, screen.read(), currentOperation);
                    screen.setValue(value);
                    reset();
                } catch (Exception ex) {
                    screen.error();
                    reset();
                }
            }
        }
    }
    private class PointListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(!FLAG_IS_TYPING){
                screen.reset();
                FLAG_IS_TYPING = true;
                screen.write(".");
            } else {
                if(!screen.getText().contains(".")){
                    screen.write(".");
                }
            }
        }
    }

    /**
     * Compute value1 with value2 according to the current Operation
     * @param value1 first value
     * @param value2 second value
     * @param operation arithmetic operation
     * @return result of the computing
     */
    private Double compute(Double value1, Double value2, Operator operation) throws Exception {
        double result = 0d;
        switch (operation){
            case Plus:
                result = value1 + value2;
                break;
            case Minus:
                result = value1 - value2;
                break;
            case Multi:
                result = value1 * value2;
                break;
            case Div:
                if(value2 != 0) {
                    result = value1 / value2;
                } else {
                    throw new Exception();
                }
                break;
            default:
                break;
        }
        history.setText(String.format("%f %s %f = %f",
                value1, operation.getSymbol(), value2, result));
        return result;
    }

    /**
     * Restore default values
     */
    private void reset(){
        value = null;
        FLAG_IS_TYPING = false;
        currentOperation = null;
    }

    /**
     * Init main panel
     */
    private void initPanel(){

        JButton button;

        // Buttons number, "." and "="
        JPanel numericPanel = new JPanel(new GridLayout(4, 3));
        numericPanel.add(new NumberButton(1, this));
        numericPanel.add(new NumberButton(2, this));
        numericPanel.add(new NumberButton(3, this));
        numericPanel.add(new NumberButton(4, this));
        numericPanel.add(new NumberButton(5, this));
        numericPanel.add(new NumberButton(6, this));
        numericPanel.add(new NumberButton(7, this));
        numericPanel.add(new NumberButton(8, this));
        numericPanel.add(new NumberButton(9, this));
        numericPanel.add(new NumberButton(0, this));

        // Button "."
        button = new JButton(".");
        button.addActionListener(new PointListener());
        numericPanel.add(button);

        // Button "="
        button = new JButton("=");
        button.addActionListener(new EqualListener());
        numericPanel.add(button);

        // Button "C"
        button = new JButton("C");
        button.addActionListener(new RAZListener());

        // Operators Panels
        JPanel operatorPanel = new JPanel(new GridLayout(5, 1));
        operatorPanel.add(button);
        operatorPanel.add(new OperatorButton(Operator.Plus, this));
        operatorPanel.add(new OperatorButton(Operator.Minus, this));
        operatorPanel.add(new OperatorButton(Operator.Multi, this));
        operatorPanel.add(new OperatorButton(Operator.Div, this));

        // Button Historic
        button = new JButton("H");
        button.addActionListener(e -> toggleHistoryVisibility());

        // North Panel
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(screen, BorderLayout.CENTER);
        northPanel.add(button, BorderLayout.EAST);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
//        northPanel.setBackground(backgroundColor);
//        northPanel.setOpaque(true);
        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(numericPanel, BorderLayout.CENTER);
        mainPanel.add(operatorPanel, BorderLayout.EAST);
        mainPanel.add(history, BorderLayout.SOUTH);
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainPanel.setBackground(backgroundColor);
        mainPanel.setOpaque(true);

        for(Component c : mainPanel.getComponents()){
            c.setBackground(backgroundColor);
        }

        history.setText("Bonjour");
        history.setVisible(FLAG_HISTORY_VISIBLE);

        this.setContentPane(mainPanel);
    }

    /**
     * Toggle historic panel visibility
     */
    private void toggleHistoryVisibility(){
        FLAG_HISTORY_VISIBLE = !FLAG_HISTORY_VISIBLE;
        history.setVisible(FLAG_HISTORY_VISIBLE);
    }

    // Interfaces

    @Override
    public void clickOnNumber(int number) {

        if(!FLAG_IS_TYPING){
            screen.reset();
            FLAG_IS_TYPING = true;
        }
        screen.write(number);
    }

    @Override
    public void clickOnOperator(Operator operator) {
        if(FLAG_IS_TYPING){

            currentOperation = currentOperation != null ?
                    currentOperation : operator;

            if(value != null){
                try {
                    value = compute(value, screen.read(),  currentOperation);
                    screen.setValue(value);
                } catch (Exception e) {
                    screen.error();
                    reset();
                }
            } else {
                value = screen.read();
                screen.setValue(value);
            }
            FLAG_IS_TYPING = false;
        } else {
            value = screen.read();
        }

        currentOperation = operator;
    }

}
