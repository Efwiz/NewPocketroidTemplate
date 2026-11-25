package app.cash.quickjs;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public final class QuickJsException extends RuntimeException {
    private static final String STACK_TRACE_CLASS_NAME = "JavaScript";
    private static final Pattern STACK_TRACE_PATTERN = Pattern.compile("\\s*at ([^\\s]+) \\(([^\\s]+(?<!cpp))[:(\\d+)]?\\).*$");

    public QuickJsException(String detailMessage) {
        this(detailMessage, null);
    }

    public QuickJsException(String detailMessage, String jsStackTrace) {
        super(detailMessage);
        if (jsStackTrace != null) {
            addJavaScriptStack(this, jsStackTrace);
        }
    }

    private static void addJavaScriptStack(Throwable throwable, String detailMessage) {
        String[] strArrSplit = detailMessage.split("\n", -1);
        if (strArrSplit.length == 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        for (StackTraceElement stackTraceElement : throwable.getStackTrace()) {
            if (!z && stackTraceElement.isNativeMethod() && stackTraceElement.getClassName().equals(QuickJs.class.getName())) {
                for (String str : strArrSplit) {
                    StackTraceElement stackTraceElement2 = toStackTraceElement(str);
                    if (stackTraceElement2 != null) {
                        arrayList.add(stackTraceElement2);
                    }
                }
                z = true;
            }
            arrayList.add(stackTraceElement);
        }
        throwable.setStackTrace((StackTraceElement[]) arrayList.toArray(new StackTraceElement[arrayList.size()]));
    }

    private static StackTraceElement toStackTraceElement(String s) {
        Matcher matcher = STACK_TRACE_PATTERN.matcher(s);
        if (matcher.matches()) {
            return new StackTraceElement("JavaScript", matcher.group(1), matcher.group(2), matcher.groupCount() > 3 ? Integer.parseInt(matcher.group(3)) : -1);
        }
        return null;
    }
}
