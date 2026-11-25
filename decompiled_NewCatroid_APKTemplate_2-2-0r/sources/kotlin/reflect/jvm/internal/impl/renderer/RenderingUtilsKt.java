package kotlin.reflect.jvm.internal.impl.renderer;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;

/* JADX INFO: compiled from: RenderingUtils.kt */
/* JADX INFO: loaded from: classes12.dex */
public final class RenderingUtilsKt {
    public static final String render(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "<this>");
        boolean zShouldBeEscaped = shouldBeEscaped(name2);
        String strAsString = name2.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString()");
        return zShouldBeEscaped ? Intrinsics.stringPlus("`" + strAsString, "`") : strAsString;
    }

    private static final boolean shouldBeEscaped(Name name2) {
        if (name2.isSpecial()) {
            return false;
        }
        String strAsString = name2.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString()");
        if (!KeywordStringsGenerated.KEYWORDS.contains(strAsString)) {
            String str = strAsString;
            int i = 0;
            while (i < str.length()) {
                char cCharAt = str.charAt(i);
                i++;
                if (Character.isLetterOrDigit(cCharAt) || cCharAt == '_') {
                }
            }
            return false;
        }
        return true;
    }

    public static final String render(FqNameUnsafe fqNameUnsafe) {
        Intrinsics.checkNotNullParameter(fqNameUnsafe, "<this>");
        List<Name> listPathSegments = fqNameUnsafe.pathSegments();
        Intrinsics.checkNotNullExpressionValue(listPathSegments, "pathSegments()");
        return renderFqName(listPathSegments);
    }

    public static final String renderFqName(List<Name> pathSegments) {
        Intrinsics.checkNotNullParameter(pathSegments, "pathSegments");
        StringBuilder sb = new StringBuilder();
        for (Name name2 : pathSegments) {
            if (sb.length() > 0) {
                sb.append(".");
            }
            sb.append(render(name2));
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }
}
