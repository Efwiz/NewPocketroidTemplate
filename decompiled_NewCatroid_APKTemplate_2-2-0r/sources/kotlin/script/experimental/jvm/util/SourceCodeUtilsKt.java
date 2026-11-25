package kotlin.script.experimental.jvm.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.SourceCode;
import kotlin.text.StringsKt;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: sourceCodeUtils.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\n\u0010\u0005\u001a\u00020\u0006*\u00020\u0006\u001a&\u0010\u0007\u001a\u00020\u0001*\u00020\u00062\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u00012\b\b\u0002\u0010\n\u001a\u00020\u0001H\u0000\u001a\u0012\u0010\u000b\u001a\u00020\u0002*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\f"}, d2 = {"calcAbsolute", "", "Lkotlin/script/experimental/api/SourceCode$Position;", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "Lkotlin/script/experimental/api/SourceCode;", "determineSep", "", "findNth", "s", "n", "start", "toSourceCodePosition", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SourceCodeUtilsKt {
    public static /* synthetic */ int findNth$default(String str, String str2, int i, int i2, int i3, Object obj) {
        if ((i3 & 4) != 0) {
            i2 = 0;
        }
        return findNth(str, str2, i, i2);
    }

    public static final int findNth(String str, String s, int i, int i2) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(s, "s");
        int i3 = 1;
        if (i < 1) {
            return -1;
        }
        if (1 <= i) {
            int length = i2;
            while (true) {
                int iIndexOf$default = StringsKt.indexOf$default((CharSequence) str, s, length, false, 4, (Object) null);
                if (iIndexOf$default == -1) {
                    return -1;
                }
                length = iIndexOf$default + s.length();
                if (i3 != i) {
                    i3++;
                } else {
                    i2 = length;
                }
            }
        }
        return i2 - s.length();
    }

    public static final SourceCode.Position toSourceCodePosition(int i, SourceCode code) {
        Intrinsics.checkNotNullParameter(code, "code");
        String strSubstring = code.getText().substring(0, i);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        String str = strSubstring;
        int i2 = 0;
        for (int i3 = 0; i3 < str.length(); i3++) {
            if (str.charAt(i3) == '\n') {
                i2++;
            }
        }
        String strDetermineSep = determineSep(code.getText());
        return new SourceCode.Position(i2 + 1, ((strSubstring.length() + 1) - StringsKt.lastIndexOf$default((CharSequence) str, strDetermineSep, 0, false, 6, (Object) null)) - strDetermineSep.length(), Integer.valueOf(i));
    }

    public static final String determineSep(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return StringsKt.indexOf$default((CharSequence) str, "\r\n", 0, false, 6, (Object) null) == -1 ? "\n" : "\r\n";
    }

    public static final int calcAbsolute(SourceCode.Position position, SourceCode code) {
        Intrinsics.checkNotNullParameter(position, "<this>");
        Intrinsics.checkNotNullParameter(code, "code");
        if (position.getAbsolutePos() != null) {
            Integer absolutePos = position.getAbsolutePos();
            Intrinsics.checkNotNull(absolutePos);
            return absolutePos.intValue();
        }
        if (position.getLine() == 1) {
            return position.getCol() - 1;
        }
        String strDetermineSep = determineSep(code.getText());
        return ((findNth$default(code.getText(), strDetermineSep, position.getLine() - 1, 0, 4, null) + strDetermineSep.length()) + position.getCol()) - 1;
    }
}
