package org.jetbrains.kotlin.library.abi;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: LibraryAbi.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0087\b\u0018\u0000 \u001a2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u001aB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0011\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0000H\u0096\u0002J\u0016\u0010\r\u001a\u00020\u0003HÆ\u0003ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b\u000e\u0010\u0007J\u0016\u0010\u000f\u001a\u00020\u0003HÆ\u0003ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u0007J'\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001ø\u0001\u0000¢\u0006\u0004\b\u0012\u0010\u0013J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\f\u001a\u0004\u0018\u00010\u0016HÖ\u0003J\t\u0010\u0017\u001a\u00020\u000bHÖ\u0001J\b\u0010\u0018\u001a\u00020\u0019H\u0016R\u0019\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007R\u0019\u0010\u0004\u001a\u00020\u0003ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\t\u0010\u0007\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", "", "packageName", "Lorg/jetbrains/kotlin/library/abi/AbiCompoundName;", "relativeName", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getPackageName-l9-qHIQ", "()Ljava/lang/String;", "Ljava/lang/String;", "getRelativeName-l9-qHIQ", "compareTo", "", "other", "component1", "component1-l9-qHIQ", "component2", "component2-l9-qHIQ", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "copy-z9QQr6Q", "(Ljava/lang/String;Ljava/lang/String;)Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", Namer.EQUALS_METHOD_NAME, "", "", "hashCode", "toString", "", "Companion", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public final /* data */ class AbiQualifiedName implements Comparable<AbiQualifiedName> {
    public static final char SEPARATOR = '/';
    private final String packageName;
    private final String relativeName;

    public /* synthetic */ AbiQualifiedName(String str, String str2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2);
    }

    /* JADX INFO: renamed from: copy-z9QQr6Q$default, reason: not valid java name */
    public static /* synthetic */ AbiQualifiedName m7321copyz9QQr6Q$default(AbiQualifiedName abiQualifiedName, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = abiQualifiedName.packageName;
        }
        if ((i & 2) != 0) {
            str2 = abiQualifiedName.relativeName;
        }
        return abiQualifiedName.m7324copyz9QQr6Q(str, str2);
    }

    /* JADX INFO: renamed from: component1-l9-qHIQ, reason: not valid java name and from getter */
    public final String getPackageName() {
        return this.packageName;
    }

    /* JADX INFO: renamed from: component2-l9-qHIQ, reason: not valid java name and from getter */
    public final String getRelativeName() {
        return this.relativeName;
    }

    /* JADX INFO: renamed from: copy-z9QQr6Q, reason: not valid java name */
    public final AbiQualifiedName m7324copyz9QQr6Q(String packageName, String relativeName) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(relativeName, "relativeName");
        return new AbiQualifiedName(packageName, relativeName, null);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AbiQualifiedName)) {
            return false;
        }
        AbiQualifiedName abiQualifiedName = (AbiQualifiedName) other;
        return AbiCompoundName.m7312equalsimpl0(this.packageName, abiQualifiedName.packageName) && AbiCompoundName.m7312equalsimpl0(this.relativeName, abiQualifiedName.relativeName);
    }

    public int hashCode() {
        return (AbiCompoundName.m7316hashCodeimpl(this.packageName) * 31) + AbiCompoundName.m7316hashCodeimpl(this.relativeName);
    }

    private AbiQualifiedName(String packageName, String relativeName) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(relativeName, "relativeName");
        this.packageName = packageName;
        this.relativeName = relativeName;
        if (relativeName.length() <= 0) {
            throw new IllegalArgumentException("Empty relative name".toString());
        }
    }

    /* JADX INFO: renamed from: getPackageName-l9-qHIQ, reason: not valid java name */
    public final String m7325getPackageNamel9qHIQ() {
        return this.packageName;
    }

    /* JADX INFO: renamed from: getRelativeName-l9-qHIQ, reason: not valid java name */
    public final String m7326getRelativeNamel9qHIQ() {
        return this.relativeName;
    }

    @Override // java.lang.Comparable
    public int compareTo(AbiQualifiedName other) {
        Intrinsics.checkNotNullParameter(other, "other");
        int iM7309compareTo6OaPNJE = AbiCompoundName.m7309compareTo6OaPNJE(this.packageName, other.packageName);
        return iM7309compareTo6OaPNJE != 0 ? iM7309compareTo6OaPNJE : AbiCompoundName.m7309compareTo6OaPNJE(this.relativeName, other.relativeName);
    }

    public String toString() {
        return new StringBuilder().append((Object) AbiCompoundName.m7318toStringimpl(this.packageName)).append('/').append((Object) AbiCompoundName.m7318toStringimpl(this.relativeName)).toString();
    }
}
