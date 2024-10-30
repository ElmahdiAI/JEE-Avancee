package com.example.tutowsdl;

public class ClientApp {
    public static void main(String[] args) {

        Calculator calculatorService = new Calculator();
        CalculatorSoap calculatorPort = calculatorService.getCalculatorSoap();

        int resultAdd = calculatorPort.add(100, 200);
        System.out.println("Addition Result: " + resultAdd);

        int resultSubstract = calculatorPort.subtract(200, 120);
        System.out.println("Substraction Result: " + resultSubstract);

        int resultMultiplay = calculatorPort.multiply(2, 9);
        System.out.println("Multiplication Result: " + resultMultiplay);

        int resultDevide = calculatorPort.divide(100, 25);
        System.out.println("Division Result: " + resultDevide);
    }
}
