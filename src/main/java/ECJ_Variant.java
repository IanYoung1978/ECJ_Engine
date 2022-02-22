public class ECJ_Variant {
    private final short type;

    private boolean booleanData;
    private byte    byteData;
    private short   shortData;
    private char    charData;
    private int     intData;
    private long    longData;
    private float   floatData;
    private double  doubleData;
    private String  stringData;

    ECJ_Variant(boolean booleanData) {
        type = 0;
        this.booleanData = booleanData;
    }
    ECJ_Variant(byte byteData) {
        type = 1;
        this.byteData = byteData;
    }
    ECJ_Variant(short shortData) {
        type = 2;
        this.shortData = shortData;
    }
    ECJ_Variant(char charData) {
        type = 3;
        this.charData = charData;
    }
    ECJ_Variant(int intData) {
        type = 4;
        this.intData = intData;
    }
    ECJ_Variant(long longData) {
        type = 5;
        this.longData = longData;
    }
    ECJ_Variant(float floatData) {
        type = 6;
        this.floatData = floatData;
    }
    ECJ_Variant(double doubleData) {
        type = 7;
        this.doubleData = doubleData;
    }
    ECJ_Variant(String stringData) {
        type = 8;
        this.stringData = stringData;
    }
    public short getType() {
        return type;
    }

    public boolean getBooleanData() {
        return booleanData;
    }

    public byte getByteData() {
        return byteData;
    }

    public short getShortData() {
        return shortData;
    }

    public char getCharData() {
        return charData;
    }

    public int getIntData() {
        return intData;
    }

    public long getLongData() {
        return longData;
    }

    public float getFloatData() {
        return floatData;
    }

    public double getDoubleData() {
        return doubleData;
    }

    public String getStringData() {
        return stringData;
    }
}
