package org.jetbrains.kotlin.codegen;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.jvm.checkers.DalvikIdentifierUtils;

/* JADX INFO: compiled from: CommonVariableAsmNameManglingUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0010\f\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001\u001a\f\u0010\u0003\u001a\u00020\u0004*\u00020\u0005H\u0002¨\u0006\u0006"}, d2 = {"mangleNameIfNeeded", "", "name", "isValidCharacter", "", "", "backend.common.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CommonVariableAsmNameManglingUtils {
    public static final String mangleNameIfNeeded(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        String str = name2;
        for (int i = 0; i < str.length(); i++) {
            if (!isValidCharacter(str.charAt(i))) {
                StringBuilder sb = new StringBuilder();
                int length = name2.length();
                for (int i2 = 0; i2 < length; i2++) {
                    char cCharAt = name2.charAt(i2);
                    if (isValidCharacter(cCharAt)) {
                        sb.append(cCharAt);
                    } else {
                        String hexString = Integer.toHexString(cCharAt);
                        hexString.length();
                        sb.append("_u").append(hexString);
                    }
                }
                String string = sb.toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                return string;
            }
        }
        return name2;
    }

    private static final boolean isValidCharacter(char c) {
        return (c == '$' || c == '-' || !DalvikIdentifierUtils.isValidDalvikCharacter(c)) ? false : true;
    }
}
