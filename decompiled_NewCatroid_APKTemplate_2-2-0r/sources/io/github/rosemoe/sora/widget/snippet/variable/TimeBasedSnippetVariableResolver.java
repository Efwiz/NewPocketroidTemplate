package io.github.rosemoe.sora.widget.snippet.variable;

import io.github.rosemoe.sora.text.TextUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;

/* JADX INFO: loaded from: classes11.dex */
public class TimeBasedSnippetVariableResolver implements ISnippetVariableResolver {
    private static String getDisplayName(int i, boolean z) {
        Calendar calendar = Calendar.getInstance();
        String displayName = calendar.getDisplayName(i, z ? 1 : 2, Locale.getDefault());
        if (displayName == null && z) {
            displayName = calendar.getDisplayName(i, 2, Locale.getDefault());
        }
        if (displayName == null) {
            displayName = calendar.getDisplayName(i, z ? 1 : 2, Locale.US);
        }
        return displayName == null ? Integer.toString(calendar.get(i)) : displayName;
    }

    @Override // io.github.rosemoe.sora.widget.snippet.variable.ISnippetVariableResolver
    public String[] getResolvableNames() {
        return new String[]{"CURRENT_YEAR", "CURRENT_YEAR_SHORT", "CURRENT_MONTH", "CURRENT_DATE", "CURRENT_HOUR", "CURRENT_MINUTE", "CURRENT_SECOND", "CURRENT_DAY_NAME", "CURRENT_DAY_NAME_SHORT", "CURRENT_MONTH_NAME", "CURRENT_MONTH_NAME_SHORT", "CURRENT_SECONDS_UNIX"};
    }

    @Override // io.github.rosemoe.sora.widget.snippet.variable.ISnippetVariableResolver
    public String resolve(String str) {
        str.hashCode();
        switch (str) {
            case "CURRENT_MONTH":
                return TextUtils.padStart(Integer.toString(Calendar.getInstance().get(2)), ExternalAnnotationProvider.NULLABLE, 2);
            case "CURRENT_MONTH_NAME_SHORT":
                return getDisplayName(2, true);
            case "CURRENT_MINUTE":
                return TextUtils.padStart(Integer.toString(Calendar.getInstance().get(12)), ExternalAnnotationProvider.NULLABLE, 2);
            case "CURRENT_SECOND":
                return TextUtils.padStart(Integer.toString(Calendar.getInstance().get(13)), ExternalAnnotationProvider.NULLABLE, 2);
            case "CURRENT_DAY_NAME":
                return getDisplayName(7, false);
            case "CURRENT_DATE":
                return SimpleDateFormat.getDateInstance().format(new Date());
            case "CURRENT_HOUR":
                return TextUtils.padStart(Integer.toString(Calendar.getInstance().get(11)), ExternalAnnotationProvider.NULLABLE, 2);
            case "CURRENT_YEAR":
                return Integer.toString(Calendar.getInstance().get(1));
            case "CURRENT_DAY_NAME_SHORT":
                return getDisplayName(7, true);
            case "CURRENT_SECONDS_UNIX":
                return Long.toString(Math.round(System.currentTimeMillis() / 1000.0d));
            case "CURRENT_MONTH_NAME":
                return getDisplayName(2, false);
            case "CURRENT_YEAR_SHORT":
                return TextUtils.padStart(Integer.toString(Calendar.getInstance().get(1) % 100), ExternalAnnotationProvider.NULLABLE, 2);
            default:
                throw new IllegalArgumentException("Unsupported variable name:" + str);
        }
    }
}
