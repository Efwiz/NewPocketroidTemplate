package org.jetbrains.kotlin.util;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.common.Scopes;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;
import net.bytebuddy.asm.Advice;
import org.jetbrains.kotlin.konan.file.File;

/* JADX INFO: compiled from: Util.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0001\u001a\u001e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\b0\u000bH\u0002\u001a\u001c\u0010\f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\b0\u000b\u001a&\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\u00012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\b0\u000bH\u0002\u001a\u001d\u0010\u0010\u001a\u0004\u0018\u0001H\u0011\"\u0004\b\u0000\u0010\u0011*\b\u0012\u0004\u0012\u0002H\u00110\u0012¢\u0006\u0002\u0010\u0013\u001a\u0012\u0010\u0014\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u0001\u001a\u0012\u0010\u0016\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u0001\u001a\u0012\u0010\u0017\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u0001\u001a\u0012\u0010\u0019\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u0001¨\u0006\u001a"}, d2 = {"nTabs", "", "amount", "", "parseSpaceSeparatedArgs", "", "argsString", "printMilliseconds", "", CrashHianalyticsData.MESSAGE, "body", "Lkotlin/Function0;", Scopes.PROFILE, "profileIf", "condition", "", "getValueOrNull", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/Lazy;", "(Lkotlin/Lazy;)Ljava/lang/Object;", "prefixBaseNameIfNot", "prefix", "prefixIfNot", "removeSuffixIfPresent", "suffix", "suffixIfNot", "kotlin-util-io"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class UtilKt {
    public static final void profile(String message, Function0<Unit> body) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(body, "body");
        String property = System.getProperty("konan.profile");
        profileIf(property != null ? property.equals("true") : false, message, body);
    }

    private static final void profileIf(boolean z, String str, Function0<Unit> function0) {
        if (z) {
            printMilliseconds(str, function0);
        } else {
            function0.invoke();
        }
    }

    public static final String nTabs(int i) {
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format("%1$-" + ((i + 1) * 4) + Advice.OffsetMapping.ForOrigin.Renderer.ForJavaSignature.SYMBOL, Arrays.copyOf(new Object[]{""}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        return str;
    }

    public static final String prefixIfNot(String str, String prefix) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        return StringsKt.startsWith$default(str, prefix, false, 2, (Object) null) ? str : prefix + str;
    }

    public static final String prefixBaseNameIfNot(String str, String prefix) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        File absoluteFile = new File(str).getAbsoluteFile();
        return absoluteFile.getParent() + '/' + prefixIfNot(absoluteFile.getName(), prefix);
    }

    public static final String suffixIfNot(String str, String suffix) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(suffix, "suffix");
        return StringsKt.endsWith$default(str, suffix, false, 2, (Object) null) ? str : str + suffix;
    }

    public static final String removeSuffixIfPresent(String str, String suffix) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(suffix, "suffix");
        return StringsKt.endsWith$default(str, suffix, false, 2, (Object) null) ? StringsKt.dropLast(str, suffix.length()) : str;
    }

    public static final <T> T getValueOrNull(Lazy<? extends T> lazy) {
        Intrinsics.checkNotNullParameter(lazy, "<this>");
        if (lazy.isInitialized()) {
            return lazy.getValue();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [T, java.lang.StringBuilder] */
    public static final List<String> parseSpaceSeparatedArgs(String argsString) {
        Intrinsics.checkNotNullParameter(argsString, "argsString");
        ArrayList arrayList = new ArrayList();
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = new StringBuilder();
        String str = argsString;
        boolean z = false;
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\"') {
                boolean z2 = !z;
                if (z) {
                    parseSpaceSeparatedArgs$saveArg(objectRef, arrayList, true);
                }
                z = z2;
            } else if (CharsKt.isWhitespace(cCharAt) && !z) {
                parseSpaceSeparatedArgs$saveArg(objectRef, arrayList, false);
            } else {
                ((StringBuilder) objectRef.element).append(cCharAt);
            }
        }
        if (z) {
            throw new IllegalStateException(("No close-quote was found in " + objectRef.element + '.').toString());
        }
        parseSpaceSeparatedArgs$saveArg(objectRef, arrayList, false);
        return arrayList;
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [T, java.lang.StringBuilder] */
    private static final void parseSpaceSeparatedArgs$saveArg(Ref.ObjectRef<StringBuilder> objectRef, List<String> list, boolean z) {
        if (z || !StringsKt.isBlank(objectRef.element)) {
            String string = objectRef.element.toString();
            Intrinsics.checkNotNullExpressionValue(string, "currentCharSequence.toString()");
            list.add(string);
            objectRef.element = new StringBuilder();
        }
    }

    private static final void printMilliseconds(String str, Function0<Unit> function0) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        function0.invoke();
        System.out.println((Object) (str + ": " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms"));
    }
}
