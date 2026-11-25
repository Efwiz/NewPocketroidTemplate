package org.jetbrains.kotlin.library.abi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: LibraryAbi.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\f\b\u0087@\u0018\u0000 $2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001$B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u0000H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016J\u001a\u0010\u0017\u001a\u00020\u00182\b\u0010\u0014\u001a\u0004\u0018\u00010\u0019HÖ\u0003¢\u0006\u0004\b\u001a\u0010\u001bJ\u0010\u0010\u001c\u001a\u00020\fHÖ\u0001¢\u0006\u0004\b\u001d\u0010\u000eJ\u001b\u0010\u001e\u001a\u00020\u00182\u0006\u0010\u001f\u001a\u00020\u0000H\u0086\u0004ø\u0001\u0000¢\u0006\u0004\b \u0010!J\u000f\u0010\"\u001a\u00020\u0003H\u0016¢\u0006\u0004\b#\u0010\u0005R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00078F¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u000f\u001a\u00020\b8Fø\u0001\u0000ø\u0001\u0001¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u0088\u0001\u0002\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/AbiCompoundName;", "", "value", "", "constructor-impl", "(Ljava/lang/String;)Ljava/lang/String;", "nameSegments", "", "Lorg/jetbrains/kotlin/library/abi/AbiSimpleName;", "getNameSegments-impl", "(Ljava/lang/String;)Ljava/util/List;", "nameSegmentsCount", "", "getNameSegmentsCount-impl", "(Ljava/lang/String;)I", Namer.METADATA_SIMPLE_NAME, "getSimpleName-t-3xSXQ", "getValue", "()Ljava/lang/String;", "compareTo", "other", "compareTo-6OaPNJE", "(Ljava/lang/String;Ljava/lang/String;)I", Namer.EQUALS_METHOD_NAME, "", "", "equals-impl", "(Ljava/lang/String;Ljava/lang/Object;)Z", "hashCode", "hashCode-impl", "isContainerOf", "member", "isContainerOf-6OaPNJE", "(Ljava/lang/String;Ljava/lang/String;)Z", "toString", "toString-impl", "Companion", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
@JvmInline
public final class AbiCompoundName implements Comparable<AbiCompoundName> {
    public static final char SEPARATOR = '.';
    private final String value;

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ AbiCompoundName m7308boximpl(String str) {
        return new AbiCompoundName(str);
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m7311equalsimpl(String str, Object obj) {
        return (obj instanceof AbiCompoundName) && Intrinsics.areEqual(str, ((AbiCompoundName) obj).m7320unboximpl());
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m7312equalsimpl0(String str, String str2) {
        return Intrinsics.areEqual(str, str2);
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m7316hashCodeimpl(String str) {
        return str.hashCode();
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m7318toStringimpl(String str) {
        return str;
    }

    public boolean equals(Object obj) {
        return m7311equalsimpl(this.value, obj);
    }

    public int hashCode() {
        return m7316hashCodeimpl(this.value);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ String m7320unboximpl() {
        return this.value;
    }

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(AbiCompoundName abiCompoundName) {
        return m7319compareTo6OaPNJE(abiCompoundName.m7320unboximpl());
    }

    private /* synthetic */ AbiCompoundName(String str) {
        this.value = str;
    }

    public final String getValue() {
        return this.value;
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static String m7310constructorimpl(String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        if (StringsKt.contains$default((CharSequence) value, '/', false, 2, (Object) null)) {
            throw new IllegalArgumentException(("Compound name contains illegal characters: " + value).toString());
        }
        return value;
    }

    /* JADX INFO: renamed from: getNameSegments-impl, reason: not valid java name */
    public static final List<AbiSimpleName> m7313getNameSegmentsimpl(String str) {
        List listSplit$default = StringsKt.split$default((CharSequence) str, new char[]{'.'}, false, 0, 6, (Object) null);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default, 10));
        Iterator it = listSplit$default.iterator();
        while (it.hasNext()) {
            arrayList.add(AbiSimpleName.m7328boximpl(AbiSimpleName.m7330constructorimpl((String) it.next())));
        }
        return arrayList;
    }

    /* JADX INFO: renamed from: getNameSegmentsCount-impl, reason: not valid java name */
    public static final int m7314getNameSegmentsCountimpl(String str) {
        String str2 = str;
        int i = 0;
        for (int i2 = 0; i2 < str2.length(); i2++) {
            if (str2.charAt(i2) == '.') {
                i++;
            }
        }
        return i + 1;
    }

    /* JADX INFO: renamed from: getSimpleName-t-3xSXQ, reason: not valid java name */
    public static final String m7315getSimpleNamet3xSXQ(String str) {
        return AbiSimpleName.m7330constructorimpl(StringsKt.substringAfterLast$default(str, '.', (String) null, 2, (Object) null));
    }

    /* JADX INFO: renamed from: compareTo-6OaPNJE, reason: not valid java name */
    public static int m7309compareTo6OaPNJE(String str, String other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return str.compareTo(other);
    }

    /* JADX INFO: renamed from: compareTo-6OaPNJE, reason: not valid java name */
    public int m7319compareTo6OaPNJE(String other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return m7309compareTo6OaPNJE(this.value, other);
    }

    public String toString() {
        return m7318toStringimpl(this.value);
    }

    /* JADX INFO: renamed from: isContainerOf-6OaPNJE, reason: not valid java name */
    public static final boolean m7317isContainerOf6OaPNJE(String str, String member) {
        Intrinsics.checkNotNullParameter(member, "member");
        int length = str.length();
        if (length == 0) {
            return true;
        }
        return member.length() > length + 1 && StringsKt.startsWith$default(member, str, false, 2, (Object) null) && member.charAt(length) == '.';
    }
}
