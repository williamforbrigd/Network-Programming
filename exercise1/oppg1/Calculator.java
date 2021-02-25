package oppg1;

public class Calculator {
    char operator;
    int num1;
    int num2;
    int res;

    public Calculator(char operator, int num1, int num2) {
        this.operator = operator;
        this.num1 = num1;
        this.num2 = num2;
    }

    public int calculate() {
        if(operator == '+') {
            res = num1+num2;
        } else if(operator == '-') {
            res = num1-num2;
        }
        return res;
    }

    @Override
    public String toString() {
        String str = "";
        if(operator == '+') {
            str = num1 + " + " + num2 + " = " + res;
        } else {
            str = num1 + " - " + num2 + " = " + res;
        }
        return str;
    }
}
