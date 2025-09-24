package com.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class Controller {

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
        // Obtiene el botón que disparó el evento
        Button button = (Button) event.getSource();

        // Accede al texto del botón
        String textButton = button.getText();

        String newCurrentText = currentText.getText();
        String[] operationStrings = {"+","-","*","/"};

        // Comprueba si ya hay un operador en currentText
        boolean currentTextContainsOperation = containsCharFromArray(newCurrentText, operationStrings);
        
        // Comprueba si el botón pulsado es de operación
        boolean currentButtonIsOperation = containsCharFromArray(textButton, operationStrings);

        // Si intentamos poner una segunda operación al texto, lo impide
        if (currentTextContainsOperation && currentButtonIsOperation) {
            {}
        } else {
            newCurrentText += textButton;
        }

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
            String newCurrentText = text.substring(0, text.length() - 1);
            currentText.setText(newCurrentText);
        }
    }

    @FXML
    private void calculateValue() {
        // Comprueba si puede hacer operación
        boolean isOperationValid = checkIfOperationIsValid(currentText.getText());
        if (!isOperationValid) {
            return;
        }

        // Separa el texto en partes
        String[] parts = preparateParts(currentText.getText());

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

    private String[] preparateParts(String text) {
        String[] operationStrings = {"+","-","*","/"};
        String[] parts = new String[3];

        // Buscamos operador y asignamos los miembros a parts
        for (int i=0; i<text.length(); i++) {
            char c = text.charAt(i);
            String current = String.valueOf(c);

            for (String op : operationStrings) {
                if (op.equals(current)) {
                    
                    // Operador encontrado --> asignamos miembros
                    parts[0] = text.substring(0, i);
                    parts[1] = current;
                    parts[2] = text.substring(i+1);

                    return parts;
                }
            }
        }

        return parts;
    }

    private double valueOfOperation(String[] parts) {
        // Recopilar valores
        double firstNumber = Double.parseDouble(parts[0]);
        String operation = parts[1];
        double secondNumber = Double.parseDouble(parts[2]);

        // Hacer operación
        double result = 0;
        switch (operation) {
            case "+":
            result = firstNumber + secondNumber;
            break;
            case "-":
            result = firstNumber - secondNumber;
            break;
            case "*":
            result = firstNumber * secondNumber;
            break;
            case "/":
            result = firstNumber / secondNumber;
            break;
        }

        return result;
    }
}
