package schemeproducerconsumer.utils;

/**
 * @author César Arturo González Pérez
 * @github arturogonzalezp
 */

public class SchemeSum implements SchemeAritmeticFunction{
    private Double x,y;
    public SchemeSum(Number x, Number y){
        setValues(x, y);
    }
    @Override
    public void setValues(Number x, Number y) {
        this.x = x.doubleValue();
        this.y = y.doubleValue();
    }

    @Override
    public Double getResult() throws ArithmeticException {
        return x + y;
    }

    @Override
    public String getFunctionString() {
        return "(+ " + x + " " + y + ")";
    }
}
