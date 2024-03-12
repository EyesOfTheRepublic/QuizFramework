package quizframework;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/* Think about this...*/

public abstract class FormatArrayOutput<T> {

    protected List<T> data;
    private String headerText;

    @Override
    public abstract String toString();

    public FormatArrayOutput(final String headerText, final List<T> data) {
        this.headerText = headerText;
        this.data = data;
    }

    public String format() {
        StringBuilder builder = new StringBuilder(headerText);
        builder.append(" = {\n");
        boolean done = false;
        Iterator<T> items= data.iterator();
        while(items.hasNext()) {
            StringBuilder line = new StringBuilder(" ".repeat(QuizUtils.MARKDOWN_INDENT * 2));
            do {
                line.append(items.next());
                if(line.length() > 80) {
                    line.append("\n");
                    break;
                }
                builder.append(items);
            } while (items.hasNext());
        }
        builder.append("};");
        return builder.toString();
    }
}
