package schemeproducerconsumer.utils;

import schemeproducerconsumer.exceptions.InvalidSchemeOperation;

/**
 * @author César Arturo González Pérez
 * @github arturogonzalezp
 */

public class SchemeSub implements SchemeArithmeticFunction{
    private Double x,y;
    public SchemeSub(Number x, Number y){
        setValues(x, y);
    }
    @Override
    public void setValues(Number x, Number y) {
        this.x = x.doubleValue();
        this.y = y.doubleValue();
    }

    @Override
    public Double getResult() throws InvalidSchemeOperation {
        return x - y;
    }

    @Override
    public String getFunctionString() {
        return "(- " + x + " " + y + ")";
    }
}
