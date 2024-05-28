package questiontypes.numbers.utils;

/**
 * Constants used across the range of questions in the numbers package.
 */
public class CoreData {

    /*
    Prevent inadvertent instantiation
     */
    private CoreData() {}

    /**
     * Basis of the typical maximum size of data values that are randomly generated.
     */
    public static final int LIM_VAL = Integer.MAX_VALUE / 3;
    /**
     * Alternative typical maximum size of data values that are randomly generated
     */
    public static final int MULT_LIM = (int) (Math.sqrt(Integer.MAX_VALUE) / 2);
    /**
     * Maximum number of elements in a randomly-generated data array
     */
    public static final int MAX_NUM = 27;
    /**
     * Minimum number of elements in a randomly-generated data array
     */
    public static final int MIN_NUM = 17;
}
