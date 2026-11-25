package org.jetbrains.kotlin.renderer;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.bytebuddy.description.type.TypeDescription;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: RenderingUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0014\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u001a0\u0010\u0005\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u0001\u001a\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u0001\u001a\n\u0010\u000f\u001a\u00020\u0001*\u00020\u0010\u001a\n\u0010\u000f\u001a\u00020\u0001*\u00020\u0011\u001a\n\u0010\u000f\u001a\u00020\u0001*\u00020\u0004\u001a\f\u0010\u0012\u001a\u00020\f*\u00020\u0004H\u0002¨\u0006\u0013"}, d2 = {"renderFqName", "", "pathSegments", "", "Lorg/jetbrains/kotlin/name/Name;", "replacePrefixesInTypeRepresentations", "lowerRendered", "lowerPrefix", "upperRendered", "upperPrefix", "foldedPrefix", "typeStringsDifferOnlyInNullability", "", "lower", "upper", "render", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "shouldBeEscaped", "compiler.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class RenderingUtilsKt {
    public static final String render(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "<this>");
        if (!shouldBeEscaped(name2)) {
            String strAsString = name2.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return strAsString;
        }
        StringBuilder sb = new StringBuilder();
        String strAsString2 = name2.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
        return sb.append("`" + strAsString2).append('`').toString();
    }

    private static final boolean shouldBeEscaped(Name name2) {
        String strAsString = name2.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        if (!KeywordStringsGenerated.KEYWORDS.contains(strAsString)) {
            String str = strAsString;
            for (int i = 0; i < str.length(); i++) {
                char cCharAt = str.charAt(i);
                if (Character.isLetterOrDigit(cCharAt) || cCharAt == '_') {
                }
            }
            if (str.length() != 0 && Character.isJavaIdentifierStart(strAsString.codePointAt(0))) {
                return false;
            }
        }
        return true;
    }

    public static final String render(FqNameUnsafe fqNameUnsafe) {
        Intrinsics.checkNotNullParameter(fqNameUnsafe, "<this>");
        List<Name> listPathSegments = fqNameUnsafe.pathSegments();
        Intrinsics.checkNotNullExpressionValue(listPathSegments, "pathSegments(...)");
        return renderFqName(listPathSegments);
    }

    public static final String render(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "<this>");
        List<Name> listPathSegments = fqName.pathSegments();
        Intrinsics.checkNotNullExpressionValue(listPathSegments, "pathSegments(...)");
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
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public static final String replacePrefixesInTypeRepresentations(String lowerRendered, String lowerPrefix, String upperRendered, String upperPrefix, String foldedPrefix) {
        Intrinsics.checkNotNullParameter(lowerRendered, "lowerRendered");
        Intrinsics.checkNotNullParameter(lowerPrefix, "lowerPrefix");
        Intrinsics.checkNotNullParameter(upperRendered, "upperRendered");
        Intrinsics.checkNotNullParameter(upperPrefix, "upperPrefix");
        Intrinsics.checkNotNullParameter(foldedPrefix, "foldedPrefix");
        if (StringsKt.startsWith$default(lowerRendered, lowerPrefix, false, 2, (Object) null) && StringsKt.startsWith$default(upperRendered, upperPrefix, false, 2, (Object) null)) {
            String strSubstring = lowerRendered.substring(lowerPrefix.length());
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            String strSubstring2 = upperRendered.substring(upperPrefix.length());
            Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
            String str = foldedPrefix + strSubstring;
            if (Intrinsics.areEqual(strSubstring, strSubstring2)) {
                return str;
            }
            if (typeStringsDifferOnlyInNullability(strSubstring, strSubstring2)) {
                return str + '!';
            }
        }
        return null;
    }

    public static final boolean typeStringsDifferOnlyInNullability(String lower, String upper) {
        Intrinsics.checkNotNullParameter(lower, "lower");
        Intrinsics.checkNotNullParameter(upper, "upper");
        return Intrinsics.areEqual(lower, StringsKt.replace$default(upper, TypeDescription.Generic.OfWildcardType.SYMBOL, "", false, 4, (Object) null)) || (StringsKt.endsWith$default(upper, TypeDescription.Generic.OfWildcardType.SYMBOL, false, 2, (Object) null) && Intrinsics.areEqual(new StringBuilder().append(lower).append(MangleConstant.Q_MARK).toString(), upper)) || Intrinsics.areEqual(new StringBuilder("(").append(lower).append(")?").toString(), upper);
    }
}
