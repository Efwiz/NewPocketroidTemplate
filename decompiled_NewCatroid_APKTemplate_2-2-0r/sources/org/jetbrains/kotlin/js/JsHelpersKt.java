package org.jetbrains.kotlin.js;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: JsHelpers.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0004"}, d2 = {"validateQualifier", "", "qualifier", "", "compiler.common.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JsHelpersKt {
    public static final boolean validateQualifier(String qualifier) {
        Intrinsics.checkNotNullParameter(qualifier, "qualifier");
        List listSplit$default = StringsKt.split$default((CharSequence) qualifier, new char[]{'.'}, false, 0, 6, (Object) null);
        if (listSplit$default.isEmpty()) {
            return false;
        }
        List<String> list = listSplit$default;
        if ((list instanceof Collection) && list.isEmpty()) {
            return true;
        }
        for (String str : list) {
            if (str.length() > 0 && Character.isJavaIdentifierStart(str.charAt(0))) {
                String strDrop = StringsKt.drop(str, 1);
                for (int i = 0; i < strDrop.length(); i++) {
                    if (Character.isJavaIdentifierPart(strDrop.charAt(i))) {
                    }
                }
            }
            return false;
        }
        return true;
    }
}
