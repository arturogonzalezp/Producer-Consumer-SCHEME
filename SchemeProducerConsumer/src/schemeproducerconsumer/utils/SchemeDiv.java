package schemeproducerconsumer.utils;

import schemeproducerconsumer.exceptions.InvalidSchemeOperation;

/**
 * @author César Arturo González Pérez
 * @github arturogonzalezp
 */

public class SchemeDiv implements SchemeArithmeticFunction{
    private Double x,y;
    public SchemeDiv(Number x, Number y){
        setValues(x, y);
    }
    @Override
    public void setValues(Number x, Number y) {
        this.x = x.doubleValue();
        this.y = y.doubleValue();
    }

    @Override
    public Double getResult() throws InvalidSchemeOperation {
        if(y == 0)
            throw new InvalidSchemeOperation("Invalid division by 0:\n" + getFunctionString());
        return x / y;
    }

    @Override
    public String getFunctionString() {
        return "(/ " + x + " " + y + ")";
    }
}
