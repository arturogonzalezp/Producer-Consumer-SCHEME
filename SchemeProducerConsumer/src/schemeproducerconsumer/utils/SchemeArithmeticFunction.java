package schemeproducerconsumer.utils;

import schemeproducerconsumer.exceptions.InvalidSchemeOperation;

/**
 * @author César Arturo González Pérez
 * @github arturogonzalezp
 */

public interface SchemeArithmeticFunction {
    void setValues(Number x, Number y);
    Double getResult() throws InvalidSchemeOperation;
    String getFunctionString();
}
