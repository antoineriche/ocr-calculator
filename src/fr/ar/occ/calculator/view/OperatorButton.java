package fr.ar.occ.calculator.view;

import fr.ar.occ.calculator.Operator;
import fr.ar.occ.calculator.OperatorListener;

import javax.swing.*;

/**
 * Button for operators
 */
public class OperatorButton extends JButton {

    public OperatorButton(Operator operator, OperatorListener listener) {
        super(operator.getSymbol());
        this.addActionListener(e -> listener.clickOnOperator(operator));
    }
}
