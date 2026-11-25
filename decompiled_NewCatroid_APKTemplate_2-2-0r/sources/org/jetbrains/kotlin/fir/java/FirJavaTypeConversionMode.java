package org.jetbrains.kotlin.fir.java;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;

/* JADX INFO: compiled from: JavaTypeConversion.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/FirJavaTypeConversionMode;", "", "(Ljava/lang/String;I)V", "DEFAULT", "ANNOTATION_MEMBER", "SUPERTYPE", "TYPE_PARAMETER_BOUND_FIRST_ROUND", "TYPE_PARAMETER_BOUND_AFTER_FIRST_ROUND", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum FirJavaTypeConversionMode {
    DEFAULT,
    ANNOTATION_MEMBER,
    SUPERTYPE,
    TYPE_PARAMETER_BOUND_FIRST_ROUND,
    TYPE_PARAMETER_BOUND_AFTER_FIRST_ROUND;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    public static EnumEntries<FirJavaTypeConversionMode> getEntries() {
        return $ENTRIES;
    }
}
