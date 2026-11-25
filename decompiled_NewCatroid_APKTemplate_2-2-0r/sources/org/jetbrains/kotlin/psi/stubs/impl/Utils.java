package org.jetbrains.kotlin.psi.stubs.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;

/* JADX INFO: compiled from: Utils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/Utils;", "", "()V", "wrapStrings", "", "Lorg/jetbrains/kotlin/com/intellij/util/io/StringRef;", "names", "", "", "(Ljava/util/List;)[Lcom/intellij/util/io/StringRef;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Utils {
    public static final Utils INSTANCE = new Utils();

    private Utils() {
    }

    public final StringRef[] wrapStrings(List<String> names) {
        Intrinsics.checkNotNullParameter(names, "names");
        int size = names.size();
        StringRef[] stringRefArr = new StringRef[size];
        for (int i = 0; i < size; i++) {
            StringRef stringRefFromString = StringRef.fromString(names.get(i));
            Intrinsics.checkNotNull(stringRefFromString);
            stringRefArr[i] = stringRefFromString;
        }
        return stringRefArr;
    }
}
