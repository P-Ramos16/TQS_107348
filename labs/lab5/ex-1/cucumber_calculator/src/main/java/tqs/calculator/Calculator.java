package tqs.calculator;

public class Calculator {

    private Number val0;
    private Number val1;
    private Double result;

    private int stage;


    public Calculator(){
        val0 = null;
        val1 = null;
        result = null;

        stage = 0;
    }

    public void push(Object val) {
        switch (this.stage) {
            case 0:
                try {
                    this.val0 = (Number)val;
                }
                catch(Exception e) {
                    System.out.println("Erro! Número inválido");
                    return;
                }
                stage++;
                break;

            case 1:
                try {
                    this.val1 = (Number)val;
                }
                catch(Exception e) {
                    System.out.println("Erro! Número inválido");
                    return;
                }
                stage++;
                break;
            
            case 2:
                stage++;
                if (val.equals("+")) {
                    this.result = this.val0.doubleValue() + this.val1.doubleValue();
                }
                else if (val.equals("-")) {
                    this.result = this.val0.doubleValue() - this.val1.doubleValue();
                }
                else if (val.equals("*")) {
                    this.result = this.val0.doubleValue() * this.val1.doubleValue();
                }
                else if (val.equals("/")) {
                    this.result = this.val0.doubleValue() / this.val1.doubleValue();
                }
                else {
                    stage--;
                    System.out.println("Error! The provided operation is not allowed!");
                }
        }
    }

    public Double value() {

        if (this.stage != 3) {
            System.out.println("Error! Not all values were filled prior to calculation!");
            return 0.0;
        }

        this.stage = 0;
        return this.result;

    }
}
