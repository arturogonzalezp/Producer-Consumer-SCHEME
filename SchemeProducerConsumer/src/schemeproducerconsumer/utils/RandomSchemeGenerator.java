package schemeproducerconsumer.utils;

import java.util.Random;

/**
 * @author César Arturo González Pérez
 * @github arturogonzalezp
 */

public class RandomSchemeGenerator {
    private Random random;
    
    public RandomSchemeGenerator(){
        random = new Random();
    }
    private Integer getRandomNumber(Integer min, Integer max){
        return random.nextInt(max) + min;
    }
    public SchemeArithmeticFunction getRandomFunction(){
        int option = getRandomNumber(0,4);
        int x = getRandomNumber(0, 100);
        int y = getRandomNumber(0, 100);
        switch(option){
            case 0:
                return new SchemeSum(x, y);
            case 1:
                return new SchemeSub(x, y);
            case 2:
                return new SchemeMultiply(x, y);
            case 3:
                return new SchemeDiv(x, y);
        }
        return null;
    }
}
