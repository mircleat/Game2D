public class T extends Number {

    int integer;
    long longInteger;
    float floatNumber;
    double doubleNumber;

    T(int theInt, long theLong, float theFloat, double theDouble) {
        integer = theInt;
        longInteger = theLong;
        floatNumber = theFloat;
        doubleNumber = theDouble;
    }

    @Override
    public int intValue() {
        return integer;
    }

    @Override
    public long longValue() {
        return longInteger;
    }

    @Override
    public float floatValue() {
        return floatNumber;
    }

    @Override
    public double doubleValue() {
        return doubleNumber;
    }

    boolean amIPositive(T num) {
        return  num.intValue() > 0 ||
                num.longValue() > 0 ||
                num.floatValue() > 0 ||
                num.doubleValue() > 0;
    }
}

