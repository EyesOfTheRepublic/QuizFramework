package questiontypes.termrewriting.utils;

/**
 * Basic data and constants used for the term rewriting questions.
 */
public class CoreRewritingData {

    /*
    Prevent inadvertent instantiation
     */
    private CoreRewritingData() {}

    public static final int MIN_LEN = 60;
    public static final int MAX_LEN = 80;

    public static final char HIGH_RNG = 'c';
    public static final char LOW_RNG = 'a';

    public static final int STEP_MAX = 8;
    public static final int STEP_MIN = 3;

    //The standard set of rewrite rules
    public static final String[][] REWRITE_MAP
            = {{"bYb", "Y"},
            {"c", "Y"},
            {"XXbYaX", "X"},
            {"XXba", "X"},
            {"Xa", "X"},
            {"XY", "X"},
            {"bb", "X"}};

    //The alternate set of rewrite rules
    public static final String[][] ALT_REWRITE_MAP
            = {{"aYb", "Y"},
            {"cY", "Y"},
            {"XabYa", "X"},
            {"XXX", "X"},
            {"Xa", "X"}};
}
