package schemeproducerconsumer.utils;

/**
 * @author César Arturo González Pérez
 * @github arturogonzalezp
 */

public interface SchemeAritmeticFunction {
    void setValues(Number x, Number y);
    Double getResult() throws InvalidSchemeOperation;
    String getFunctionString();
}
