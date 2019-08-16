package fr.ar.occ.calculator.view;

import fr.ar.occ.calculator.NumberListener;

import javax.swing.*;

/**
 * Button for numeric values
 */
public class NumberButton extends JButton {

    public NumberButton(int number, NumberListener listener) {
        super(String.valueOf(number));
        this.addActionListener(e -> listener.clickOnNumber(number));
    }
}
