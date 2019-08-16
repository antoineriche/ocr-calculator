package fr.ar.occ.calculator.view;

import javax.swing.*;
import java.awt.*;

public class Screen extends JLabel {

    /**
     * Color of the Screen border
     */
    private static final Color borderColor = Color.decode("#2E7D32");
    /**
     * Color of the screen background
     */
    private static final Color screenColor = Color.decode("#E3F2FD");
    /**
     * The default value for screen
     */
    private static final Double DEFAULT_VALUE = 0d;
    /**
     * Error message
     */
    private static final String ERROR_MESSAGE = "ERROR";

    public Screen() {
        this.setHorizontalAlignment(SwingConstants.RIGHT);
        this.setBackground(screenColor);
        this.setOpaque(true);
        this.setFont(new Font("Arial", Font.BOLD, 35));
        this.setForeground(Color.GREEN);
        this.setBorder(BorderFactory.createLineBorder(borderColor, 2));
    }

    /**
     * Read value from String
     * @return the double value in screen or 0 otherwise
     */
    public Double read(){
        double fromScreen = 0;
        try {
            fromScreen = Double.parseDouble(getText());
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
        return fromScreen;
    }

    /**
     * Set the formatted string value of the given result as double
     * @param value the screen value to display
     */
    public void setValue(double value){
        setText(arrangeStringValue(value));
    }

    /**
     * Display the default value
     */
    public void reset(){
       setValue(DEFAULT_VALUE);
    }

    /**
     * Display error message
     */
    public void error(){
        setText(ERROR_MESSAGE);
    }

    /**
     * Aggregate the current string value with the given symbol
     * @param symbol the symbol to aggregate
     */
    public void write(String symbol){
        setText(
                getText().equals(ERROR_MESSAGE) ||
                (Double.compare(read(), DEFAULT_VALUE) == 0) ?
                symbol : getText() + symbol);
    }

    /**
     * Concat the current string with an integer
     * @param number integer to aggregate
     */
    public void write(int number){
        write(String.valueOf(number));
    }

    /**
     * Get an Integer string or a Double string according to given value
     * @param value the value
     * @return the String value
     */
    private String arrangeStringValue(double value){
        return isAnInteger(value) ?
                String.valueOf((int) value) : String.valueOf(value);
    }

    /**
     * Is value an Integer
     * @param value the value to test
     * @return true if value is an integer false otherwise
     */
    private boolean isAnInteger(Double value){
        return (value != null && value % 1 == 0);
    }

    @Override
    public void setText(String text) {
        setForeground(text.equals(ERROR_MESSAGE) ? Color.RED : Color.BLACK);
        super.setText(text);
    }
}
