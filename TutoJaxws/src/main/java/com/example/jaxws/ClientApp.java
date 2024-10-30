package com.example.jaxws;

public class ClientApp {
    public static void main(String[] args) {

        Calculator calculatorService = new Calculator();
        CalculatorSoap calculatorPort = calculatorService.getCalculatorSoap();

        System.out.println("120 + 301 = " + calculatorPort.add(120, 301));

        System.out.println("501 - 30 = " + calculatorPort.subtract(501, 30));

        System.out.println("2 * 9 = " + calculatorPort.multiply(2, 9));

        System.out.println("200 / 25 " + calculatorPort.divide(200, 25));
    }
}
