package org.jetbrains.kotlin.library.abi;

import kotlin.Metadata;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: LibraryAbi.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\b\b\u0087@\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0000H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b\u000b\u0010\fJ\u001a\u0010\r\u001a\u00020\u000e2\b\u0010\n\u001a\u0004\u0018\u00010\u000fHÖ\u0003¢\u0006\u0004\b\u0010\u0010\u0011J\u0010\u0010\u0012\u001a\u00020\tHÖ\u0001¢\u0006\u0004\b\u0013\u0010\u0014J\u000f\u0010\u0015\u001a\u00020\u0003H\u0016¢\u0006\u0004\b\u0016\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u0088\u0001\u0002\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/AbiSimpleName;", "", "value", "", "constructor-impl", "(Ljava/lang/String;)Ljava/lang/String;", "getValue", "()Ljava/lang/String;", "compareTo", "", "other", "compareTo-C8x1a7g", "(Ljava/lang/String;Ljava/lang/String;)I", Namer.EQUALS_METHOD_NAME, "", "", "equals-impl", "(Ljava/lang/String;Ljava/lang/Object;)Z", "hashCode", "hashCode-impl", "(Ljava/lang/String;)I", "toString", "toString-impl", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@JvmInline
@ExperimentalLibraryAbiReader
public final class AbiSimpleName implements Comparable<AbiSimpleName> {
    private final String value;

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ AbiSimpleName m7328boximpl(String str) {
        return new AbiSimpleName(str);
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m7331equalsimpl(String str, Object obj) {
        return (obj instanceof AbiSimpleName) && Intrinsics.areEqual(str, ((AbiSimpleName) obj).m7336unboximpl());
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m7332equalsimpl0(String str, String str2) {
        return Intrinsics.areEqual(str, str2);
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m7333hashCodeimpl(String str) {
        return str.hashCode();
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m7334toStringimpl(String str) {
        return str;
    }

    public boolean equals(Object obj) {
        return m7331equalsimpl(this.value, obj);
    }

    public int hashCode() {
        return m7333hashCodeimpl(this.value);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ String m7336unboximpl() {
        return this.value;
    }

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(AbiSimpleName abiSimpleName) {
        return m7335compareToC8x1a7g(abiSimpleName.m7336unboximpl());
    }

    private /* synthetic */ AbiSimpleName(String str) {
        this.value = str;
    }

    public final String getValue() {
        return this.value;
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static String m7330constructorimpl(String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        String str = value;
        if (StringsKt.contains$default((CharSequence) str, '.', false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str, '/', false, 2, (Object) null)) {
            throw new IllegalArgumentException(("Simple name contains illegal characters: " + value).toString());
        }
        return value;
    }

    /* JADX INFO: renamed from: compareTo-C8x1a7g, reason: not valid java name */
    public static int m7329compareToC8x1a7g(String str, String other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return str.compareTo(other);
    }

    /* JADX INFO: renamed from: compareTo-C8x1a7g, reason: not valid java name */
    public int m7335compareToC8x1a7g(String other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return m7329compareToC8x1a7g(this.value, other);
    }

    public String toString() {
        return m7334toStringimpl(this.value);
    }
}
