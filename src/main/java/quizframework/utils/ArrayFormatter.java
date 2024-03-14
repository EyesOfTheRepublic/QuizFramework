package quizframework.utils;

import java.util.Iterator;
import java.util.List;

public class ArrayFormatter<T> {

    private final String header;
    private final List<T> dataList;

    public ArrayFormatter(final String header, final List<T> dataList) {
        this.header = header;
        this.dataList = dataList;
    }

    public  String outputItem(final T item) {
        return item.toString();
    }

    public StringBuilder format() {
        final Iterator<T> iter = dataList.iterator();
        final StringBuilder builder = new StringBuilder(header + " = {\n");
        while(iter.hasNext()) {
            final StringBuilder lineBuilder = new StringBuilder(" ".repeat(Utils.MARKDOWN_INDENT));
            do {
               lineBuilder.append(outputItem(iter.next()));
               //lineBuilder.append(iter.next());
               if (iter.hasNext()) {
                   lineBuilder.append(", ");
               }
               if (lineBuilder.length() > 80) {
                   //builder.append(lineBuilder).append("\n");
                   break;
               }
            } while(iter.hasNext());
            builder.append(lineBuilder).append("\n");
        }
        builder.append("};");
        return builder;
    }
}
