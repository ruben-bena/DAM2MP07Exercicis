package com.project;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class Controller {

    String[] operationStrings = {"+","-","*","/"};

    @FXML
    private Button buttonNumber1;
    @FXML
    private Button buttonNumber2;
    @FXML
    private Button buttonNumber3;
    @FXML
    private Button buttonNumber4;
    @FXML
    private Button buttonNumber5;
    @FXML
    private Button buttonNumber6;
    @FXML
    private Button buttonNumber7;
    @FXML
    private Button buttonNumber8;
    @FXML
    private Button buttonNumber9;
    @FXML
    private Button buttonNumber0;
    @FXML
    private Button buttonPeriod;

    @FXML
    private Button buttonPlus;
    @FXML
    private Button buttonMinus;
    @FXML
    private Button buttonMultiplied;
    @FXML
    private Button buttonDivided;
    @FXML
    private Button buttonEquals;

    @FXML
    private Button buttonAC;
    @FXML
    private Button buttonDEL;

    @FXML
    private Text currentText;
    @FXML
    private Text currentValue;

    @FXML
    private void fillString(ActionEvent event) {
        // Obtiene el botón que disparó el evento y accede a su símbolo
        Button button = (Button) event.getSource();
        String textButton = button.getText();
        if (textButton.equals("Ans")) {
            textButton = currentValue.getText();
        }

        // Añade el contenido del botón a currentText
        String newCurrentText = currentText.getText();
        newCurrentText += textButton;
        

        // Comprueba si ya hay un operador en currentText
        // String[] operationStrings = {"+","-","*","/"};
        // boolean currentTextContainsOperation = containsCharFromArray(newCurrentText, operationStrings);
        
        // // Comprueba si el botón pulsado es de operación
        // boolean currentButtonIsOperation = containsCharFromArray(textButton, operationStrings);

        // Si intentamos poner una segunda operación al texto, lo impide
        // if (currentTextContainsOperation && currentButtonIsOperation) {
        //     {}
        // } else {
        //     newCurrentText += textButton;
        // }

        currentText.setText(newCurrentText);
    }

    private boolean containsCharFromArray(String text, String[] array) {

        for (int i=0; i<text.length(); i++) {
            char c = text.charAt(i);
            String current = String.valueOf(c);

            for (String arrayChar : array) {
                if (arrayChar.equals(current)) {
                    return true;
                }
            }
        }
        return false;
    }

    @FXML
    private void resetCalculatorTexts() {
        currentText.setText("");
        currentValue.setText("0");
    }

    @FXML
    private void deleteCurrentTextLastChar() {
        String text = currentText.getText();
        if (text.length() > 0) {

            // Comprobar si lo último escrito en la calculadora es "Ans"
            String lastChar = String.valueOf(text.charAt(text.length() - 1));
            boolean lastWrittenIsAns = lastChar.equals("s");
            String newCurrentText;

            if (lastWrittenIsAns) {
                newCurrentText = text.substring(0, text.length() - 3);

            } else {
                newCurrentText = text.substring(0, text.length() - 1);

            }

            currentText.setText(newCurrentText);
        }
    }

    @FXML
    private void calculateValue() {
        // Comprueba si puede hacer operación
        // boolean isOperationValid = checkIfOperationIsValid(currentText.getText());
        // if (!isOperationValid) {
        //     return;
        // }

        // Separa el texto en partes
        ArrayList<String> parts = preparateParts(currentText.getText());

        // Calcula valor de la operación
        double value = valueOfOperation(parts);

        // Asigna el valor al currentValue
        String valueString = String.valueOf(value);
        currentValue.setText(valueString);

        // Borramos currentText
        currentText.setText("");
    }

    private boolean checkIfOperationIsValid(String text) {
        // Buscamos el operador del texto
        String[] operationStrings = {"+","-","*","/"};
        String operation = null;
        for (String op : operationStrings) {
            if (text.contains(op))
            {
                operation = op;
                break;
            }
        }

        if (operation == null) {
            return false;
        }

        // Separamos los miembros de la operación
        String[] numbers = text.split(java.util.regex.Pattern.quote(operation)); // Si uso directamente un separador como "+", va mal
        if (numbers.length != 2) {
            return false;
        }

        // Hacemos parse de los números
        try {
            Double.parseDouble(numbers[0]);
            Double.parseDouble(numbers[1]);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    private ArrayList<String> preparateParts(String text) {
        ArrayList<String> parts = new ArrayList<>();
        String currentElement = "";

        for (int i = 0; i < text.length(); i++) {
            String currentChar = String.valueOf(text.charAt(i));

            if (containsCharFromArray(currentChar, operationStrings)) {
                if (!currentElement.isEmpty()) {
                    parts.add(currentElement);
                    currentElement = "";
                }
                parts.add(currentChar);
            } else {
                currentElement += currentChar;
            }
        }

        if (!currentElement.isEmpty()) {
            parts.add(currentElement);
        }

    return parts;
}

    private double valueOfOperation(ArrayList<String> parts) {
        // Primer recorrido (multiplicación y división)
        ArrayList<String> partsAfterFirstRound = new ArrayList<>();
        int i = 0;
        while (i < parts.size()) {
            String part = parts.get(i);

            if (part.equals("*") || part.equals("/")) {
                double left = Double.parseDouble(partsAfterFirstRound.remove(partsAfterFirstRound.size() - 1));
                double right = Double.parseDouble(parts.get(i + 1));
                double result;
                if (part.equals("*")) {
                    result = left * right;

                } else {
                    result = left / right;

                }
                partsAfterFirstRound.add(String.valueOf(result));
                i += 2; // Saltamos el siguiente número porque ya lo procesamos
            } else {
                partsAfterFirstRound.add(part);
                i++;
            }
    }

        // Recorrido final (suma y resta)
        double result = Double.parseDouble(partsAfterFirstRound.get(0));
        for (i = 1; i < partsAfterFirstRound.size(); i += 2) {
            String operator = partsAfterFirstRound.get(i);
            double number = Double.parseDouble(partsAfterFirstRound.get(i + 1));
            if (operator.equals("+")) {
                result += number;
            } else if (operator.equals("-")) {
                result -= number;
            }
        }

        return result;
    }
}
