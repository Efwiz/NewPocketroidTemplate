package org.jetbrains.kotlin.parsing;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.bouncycastle.pqc.math.linearalgebra.Matrix;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiLiteralUtil;
import org.jetbrains.kotlin.com.intellij.util.text.LiteralFormatUtil;
import org.jetbrains.kotlin.utils.NumberWithRadix;
import org.jetbrains.kotlin.utils.NumbersKt;

/* JADX INFO: compiled from: ParseUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0004\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\u001a\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007\u001a\u000e\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u000e\u0010\t\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u000e\u0010\n\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u000e\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u0017\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002¢\u0006\u0002\u0010\u000e\u001a\u0017\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0004\u001a\u00020\u0005H\u0002¢\u0006\u0002\u0010\u0011\u001a\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0017\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0004\u001a\u00020\u0005H\u0002¢\u0006\u0002\u0010\u0016\u001a\u0018\u0010\u0017\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u0007\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"FP_LITERAL_PARTS", "Lkotlin/text/Regex;", "hasIllegalUnderscore", "", "text", "", "elementType", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "hasLongSuffix", "hasUnsignedLongSuffix", "hasUnsignedSuffix", "parseBoolean", "parseDouble", "", "(Ljava/lang/String;)Ljava/lang/Double;", "parseFloat", "", "(Ljava/lang/String;)Ljava/lang/Float;", "parseFloatingLiteral", "", "parseLong", "", "(Ljava/lang/String;)Ljava/lang/Long;", "parseNumericLiteral", ModuleXmlParser.TYPE, "psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ParseUtilsKt {
    private static final Regex FP_LITERAL_PARTS = new Regex("([_\\d]*)\\.?([_\\d]*)e?[+-]?([_\\d]*)[f]?");

    public static final boolean hasIllegalUnderscore(String text, IElementType elementType) {
        List list;
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(elementType, "elementType");
        if (elementType == KtNodeTypes.INTEGER_CONSTANT) {
            int length = text.length();
            int i = (StringsKt.startsWith(text, PsiLiteralUtil.HEX_PREFIX, true) || StringsKt.startsWith(text, PsiLiteralUtil.BIN_PREFIX, true)) ? 2 : 0;
            if (StringsKt.endsWith((CharSequence) text, 'l', true)) {
                length--;
            }
            String strSubstring = text.substring(i, length);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            list = CollectionsKt.listOf(strSubstring);
        } else {
            list = SequencesKt.toList(SequencesKt.flatMapIterable(Regex.findAll$default(FP_LITERAL_PARTS, text, 0, 2, null), new Function1<MatchResult, List<? extends String>>() { // from class: org.jetbrains.kotlin.parsing.ParseUtilsKt$hasIllegalUnderscore$parts$1
                @Override // kotlin.jvm.functions.Function1
                public final List<String> invoke(MatchResult it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return it.getGroupValues();
                }
            }));
        }
        List<String> list2 = list;
        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
            for (String str : list2) {
                if (str != null && (StringsKt.startsWith$default(str, "_", false, 2, (Object) null) || StringsKt.endsWith$default(str, "_", false, 2, (Object) null))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static final boolean hasLongSuffix(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        String str = text;
        return StringsKt.endsWith$default((CharSequence) str, 'l', false, 2, (Object) null) || StringsKt.endsWith$default((CharSequence) str, 'L', false, 2, (Object) null);
    }

    public static final boolean hasUnsignedSuffix(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        String str = text;
        return StringsKt.endsWith$default((CharSequence) str, 'u', false, 2, (Object) null) || StringsKt.endsWith$default((CharSequence) str, Matrix.MATRIX_TYPE_RANDOM_UT, false, 2, (Object) null);
    }

    public static final boolean hasUnsignedLongSuffix(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        return StringsKt.endsWith$default(text, "ul", false, 2, (Object) null) || StringsKt.endsWith$default(text, "uL", false, 2, (Object) null) || StringsKt.endsWith$default(text, "Ul", false, 2, (Object) null) || StringsKt.endsWith$default(text, "UL", false, 2, (Object) null);
    }

    public static final Number parseNumericLiteral(String text, IElementType type) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(type, "type");
        String strRemoveUnderscores = LiteralFormatUtil.removeUnderscores(text);
        Intrinsics.checkNotNullExpressionValue(strRemoveUnderscores, "removeUnderscores(...)");
        if (Intrinsics.areEqual(type, KtNodeTypes.INTEGER_CONSTANT)) {
            return parseLong(strRemoveUnderscores);
        }
        if (Intrinsics.areEqual(type, KtNodeTypes.FLOAT_CONSTANT)) {
            return parseFloatingLiteral(strRemoveUnderscores);
        }
        return null;
    }

    private static final String parseLong$removeSuffix(String str, int i) {
        String strSubstring = str.substring(0, str.length() - i);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        return strSubstring;
    }

    private static final Long parseLong(String str) {
        long unsignedLong;
        try {
            boolean z = true;
            if (hasUnsignedLongSuffix(str)) {
                str = parseLong$removeSuffix(str, 2);
            } else if (hasUnsignedSuffix(str)) {
                str = parseLong$removeSuffix(str, 1);
            } else {
                if (hasLongSuffix(str)) {
                    str = parseLong$removeSuffix(str, 1);
                }
                z = false;
            }
            NumberWithRadix numberWithRadixExtractRadix = NumbersKt.extractRadix(str);
            String number = numberWithRadixExtractRadix.getNumber();
            int radix = numberWithRadixExtractRadix.getRadix();
            if (z) {
                unsignedLong = Long.parseUnsignedLong(number, radix);
            } else {
                unsignedLong = Long.parseLong(number, radix);
            }
            return Long.valueOf(unsignedLong);
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    private static final Number parseFloatingLiteral(String str) {
        String str2 = str;
        if (StringsKt.endsWith$default((CharSequence) str2, 'f', false, 2, (Object) null) || StringsKt.endsWith$default((CharSequence) str2, Util.C_FLOAT, false, 2, (Object) null)) {
            return parseFloat(str);
        }
        return parseDouble(str);
    }

    private static final Double parseDouble(String str) {
        try {
            return Double.valueOf(Double.parseDouble(str));
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    private static final Float parseFloat(String str) {
        try {
            return Float.valueOf(Float.parseFloat(str));
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    public static final boolean parseBoolean(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        if (Intrinsics.areEqual("true", text)) {
            return true;
        }
        if (Intrinsics.areEqual("false", text)) {
            return false;
        }
        throw new IllegalStateException("Must not happen. A boolean literal has text: " + text);
    }
}
