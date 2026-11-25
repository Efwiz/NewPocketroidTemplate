package org.jetbrains.kotlin.load.java.structure.impl;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: classNamesUtil.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0001\u001a\u0012\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003*\u00020\u0001H\u0002¨\u0006\u0004"}, d2 = {"convertCanonicalNameToQName", "", "splitCanonicalFqName", "", "resolution.common.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ClassNamesUtilKt {
    public static final String convertCanonicalNameToQName(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return CollectionsKt.joinToString$default(splitCanonicalFqName(str), ".", null, null, 0, null, new Function1<String, CharSequence>() { // from class: org.jetbrains.kotlin.load.java.structure.impl.ClassNamesUtilKt.convertCanonicalNameToQName.1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return StringsKt.substringBefore$default(it, '<', (String) null, 2, (Object) null);
            }
        }, 30, null);
    }

    private static final String splitCanonicalFqName$toNonEmpty(String str) {
        if (str.length() > 0) {
            return str;
        }
        String strAsString = SpecialNames.SAFE_IDENTIFIER_FOR_NO_NAME.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }

    private static final List<String> splitCanonicalFqName(String str) {
        ArrayList arrayList = new ArrayList();
        int length = str.length();
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            char cCharAt = str.charAt(i3);
            if (cCharAt == '.') {
                if (i2 == 0) {
                    String strSubstring = str.substring(i, i3);
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                    arrayList.add(splitCanonicalFqName$toNonEmpty(strSubstring));
                    i = i3 + 1;
                }
            } else if (cCharAt == '<') {
                i2++;
            } else if (cCharAt == '>') {
                i2--;
            }
        }
        String strSubstring2 = str.substring(i);
        Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
        arrayList.add(splitCanonicalFqName$toNonEmpty(strSubstring2));
        return arrayList;
    }
}
