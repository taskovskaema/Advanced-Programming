import java.util.Scanner;

class Calculator {
    private double result;

    public Calculator() {
        result = 0;
    }

    public double getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "Calculator { Updated result = " + result + " }";
    }

    public String init() {
        return "Calculator { result = " + result + " }";
    }

    static class UnknownOperatorException extends Exception {
        public UnknownOperatorException(char operator) {
            super(operator + " is an unknown operator");
        }
    }

    public double execute(char operator, double value) throws UnknownOperatorException {
        switch (operator) {
            case '+':
                result += value;
                break;
            case '-':
                result -= value;
                break;
            case '*':
                result *= value;
                break;
            case '/':
                if (value == 0) throw new ArithmeticException("Cannot divide by zero");
                result /= value;
                break;
            default:
                throw new UnknownOperatorException(operator);
        }
        return result;
    }
}

public class Main {
    public static void main(String[] args) {
        final char RESULT = 'r';
        Scanner scanner = new Scanner(System.in);

        while (true) {
            Calculator calculator = new Calculator();
            System.out.println(calculator.init());

            while (true) {
                System.out.print("Enter operator and value (or 'r' to show result): ");
                String line = scanner.nextLine().trim();

                if (line.isEmpty()) continue;
                char choice = getCharLower(line);

                if (choice == RESULT) {
                    System.out.printf("Final result = %.2f%n", calculator.getResult());
                    break;
                }

                String[] parts = line.split("\\s+");
                if (parts.length < 2) {
                    System.out.println("Invalid input. Example: + 5");
                    continue;
                }

                char operator = parts[0].charAt(0);
                double value;
                try {
                    value = Double.parseDouble(parts[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format.");
                    continue;
                }

                try {
                    double result = calculator.execute(operator, value);
                    System.out.println("Result = " + result);
                    System.out.println(calculator);
                } catch (Calculator.UnknownOperatorException | ArithmeticException e) {
                    System.out.println(e.getMessage());
                }
            }

            System.out.print("Do you want to start a new calculation? (Y/N): ");
            String response = scanner.nextLine().trim();
            if (response.length() > 0 && Character.toLowerCase(response.charAt(0)) == 'n') {
                break;
            }
        }

        scanner.close();
    }

    static char getCharLower(String line) {
        if (line.trim().length() > 0) {
            return Character.toLowerCase(line.charAt(0));
        }
        return '?';
    }
}
