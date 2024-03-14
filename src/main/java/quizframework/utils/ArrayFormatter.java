package quizframework.utils;

import java.util.Iterator;
import java.util.List;

/**
 * Format an array of data over multiple lines - mostly to make the code it forms part of easier to read as well
 * as cut-&-paste into an editor/IDE. The class is parameterized by the type of array data T. Also override the
 * method {@link #outputItem} if the default toString() method for T is not appropriate.
 */
public class ArrayFormatter<T> {

    private final String header;
    private final List<T> dataList;

    /**
     * Create an ArrayFormatter - header is the first line of the declaration (e.g. 'static int[] name =' and datalist
     * is array data as a List object of type T
     * @param header the array header
     * @param dataList the list of data items of type T
     */
    public ArrayFormatter(final String header, final List<T> dataList) {
        this.header = header;
        this.dataList = dataList;
    }

    /**
     * Return the item as a String - normally just calls toString but can be overridden if not appropriate. E.g. if
     * outputting a 2D array and need to include {..}
     * @param item the data item of type T
     * @return
     */
    public  String outputItem(final T item) {
        return item.toString();
    }

    /**
     * Format the output with one level of indenting - useful when only the array appears in the question and it's
     * not part of a larger block of code
     * @return the formatted data as a String
     */
    public StringBuilder format() {
        return this.format(1);
    }

    /**
     * Format the output with a specific level of indenting - useful when the array appears within a larger block of
     * (indented) code
     * @param steps Number of steps to indent
     * @return the formatted data as a String
     */
    public StringBuilder format(final int steps) {
        final Iterator<T> iter = dataList.iterator();
        final StringBuilder builder = new StringBuilder(" ".repeat(CodeUtils.MARKDOWN_INDENT * (steps -1)) + header + " = {\n");
        while(iter.hasNext()) {
            final StringBuilder lineBuilder = new StringBuilder(" ".repeat(CodeUtils.MARKDOWN_INDENT * steps));
            do {
               lineBuilder.append(outputItem(iter.next()));
               if (iter.hasNext()) {
                   lineBuilder.append(", ");
               }
               if (lineBuilder.length() > 80) {
                   break;
               }
            } while(iter.hasNext());
            builder.append(lineBuilder).append("\n");
        }
        builder.append(" ".repeat(CodeUtils.MARKDOWN_INDENT * (steps -1)) +"};");
        return builder;
    }
}
