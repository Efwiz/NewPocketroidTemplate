package org.jetbrains.kotlin.library;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: KotlinLibraryVersioning.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B=\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bJ\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\tHÆ\u0003JI\u0010\u0019\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tHÆ\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001J\t\u0010\u001f\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000f¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/library/KotlinLibraryVersioning;", "", "libraryVersion", "", "compilerVersion", "abiVersion", "Lorg/jetbrains/kotlin/library/KotlinAbiVersion;", "metadataVersion", "irSignatureVersions", "", "Lorg/jetbrains/kotlin/library/KotlinIrSignatureVersion;", "(Ljava/lang/String;Ljava/lang/String;Lorg/jetbrains/kotlin/library/KotlinAbiVersion;Ljava/lang/String;Ljava/util/Set;)V", "getAbiVersion", "()Lorg/jetbrains/kotlin/library/KotlinAbiVersion;", "getCompilerVersion", "()Ljava/lang/String;", "getIrSignatureVersions", "()Ljava/util/Set;", "getLibraryVersion", "getMetadataVersion", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final /* data */ class KotlinLibraryVersioning {
    private final KotlinAbiVersion abiVersion;
    private final String compilerVersion;
    private final Set<KotlinIrSignatureVersion> irSignatureVersions;
    private final String libraryVersion;
    private final String metadataVersion;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ KotlinLibraryVersioning copy$default(KotlinLibraryVersioning kotlinLibraryVersioning, String str, String str2, KotlinAbiVersion kotlinAbiVersion, String str3, Set set, int i, Object obj) {
        if ((i & 1) != 0) {
            str = kotlinLibraryVersioning.libraryVersion;
        }
        if ((i & 2) != 0) {
            str2 = kotlinLibraryVersioning.compilerVersion;
        }
        String str4 = str2;
        if ((i & 4) != 0) {
            kotlinAbiVersion = kotlinLibraryVersioning.abiVersion;
        }
        KotlinAbiVersion kotlinAbiVersion2 = kotlinAbiVersion;
        if ((i & 8) != 0) {
            str3 = kotlinLibraryVersioning.metadataVersion;
        }
        String str5 = str3;
        if ((i & 16) != 0) {
            set = kotlinLibraryVersioning.irSignatureVersions;
        }
        return kotlinLibraryVersioning.copy(str, str4, kotlinAbiVersion2, str5, set);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getLibraryVersion() {
        return this.libraryVersion;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getCompilerVersion() {
        return this.compilerVersion;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final KotlinAbiVersion getAbiVersion() {
        return this.abiVersion;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getMetadataVersion() {
        return this.metadataVersion;
    }

    public final Set<KotlinIrSignatureVersion> component5() {
        return this.irSignatureVersions;
    }

    public final KotlinLibraryVersioning copy(String libraryVersion, String compilerVersion, KotlinAbiVersion abiVersion, String metadataVersion, Set<KotlinIrSignatureVersion> irSignatureVersions) {
        Intrinsics.checkNotNullParameter(irSignatureVersions, "irSignatureVersions");
        return new KotlinLibraryVersioning(libraryVersion, compilerVersion, abiVersion, metadataVersion, irSignatureVersions);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof KotlinLibraryVersioning)) {
            return false;
        }
        KotlinLibraryVersioning kotlinLibraryVersioning = (KotlinLibraryVersioning) other;
        return Intrinsics.areEqual(this.libraryVersion, kotlinLibraryVersioning.libraryVersion) && Intrinsics.areEqual(this.compilerVersion, kotlinLibraryVersioning.compilerVersion) && Intrinsics.areEqual(this.abiVersion, kotlinLibraryVersioning.abiVersion) && Intrinsics.areEqual(this.metadataVersion, kotlinLibraryVersioning.metadataVersion) && Intrinsics.areEqual(this.irSignatureVersions, kotlinLibraryVersioning.irSignatureVersions);
    }

    public int hashCode() {
        String str = this.libraryVersion;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.compilerVersion;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        KotlinAbiVersion kotlinAbiVersion = this.abiVersion;
        int iHashCode3 = (iHashCode2 + (kotlinAbiVersion == null ? 0 : kotlinAbiVersion.hashCode())) * 31;
        String str3 = this.metadataVersion;
        return ((iHashCode3 + (str3 != null ? str3.hashCode() : 0)) * 31) + this.irSignatureVersions.hashCode();
    }

    public String toString() {
        return "KotlinLibraryVersioning(libraryVersion=" + this.libraryVersion + ", compilerVersion=" + this.compilerVersion + ", abiVersion=" + this.abiVersion + ", metadataVersion=" + this.metadataVersion + ", irSignatureVersions=" + this.irSignatureVersions + ')';
    }

    public KotlinLibraryVersioning(String str, String str2, KotlinAbiVersion kotlinAbiVersion, String str3, Set<KotlinIrSignatureVersion> irSignatureVersions) {
        Intrinsics.checkNotNullParameter(irSignatureVersions, "irSignatureVersions");
        this.libraryVersion = str;
        this.compilerVersion = str2;
        this.abiVersion = kotlinAbiVersion;
        this.metadataVersion = str3;
        this.irSignatureVersions = irSignatureVersions;
        if (irSignatureVersions.isEmpty()) {
            throw new IllegalArgumentException("Signature versions must not be empty!".toString());
        }
    }

    public final String getLibraryVersion() {
        return this.libraryVersion;
    }

    public final String getCompilerVersion() {
        return this.compilerVersion;
    }

    public final KotlinAbiVersion getAbiVersion() {
        return this.abiVersion;
    }

    public final String getMetadataVersion() {
        return this.metadataVersion;
    }

    public /* synthetic */ KotlinLibraryVersioning(String str, String str2, KotlinAbiVersion kotlinAbiVersion, String str3, Set set, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, kotlinAbiVersion, str3, (i & 16) != 0 ? KotlinIrSignatureVersion.INSTANCE.getCURRENTLY_SUPPORTED_VERSIONS() : set);
    }

    public final Set<KotlinIrSignatureVersion> getIrSignatureVersions() {
        return this.irSignatureVersions;
    }
}
