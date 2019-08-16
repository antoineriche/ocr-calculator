package fr.ar.occ.calculator;

public enum Operator {
    Plus("+"),
    Minus("-"),
    Multi("*"),
    Div("/");

    String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

}
