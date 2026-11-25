package org.jetbrains.kotlin.library.abi;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;

/* JADX INFO: compiled from: LibraryManifest.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001BE\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\nJ\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005HÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0003HÆ\u0003JU\u0010\u0019\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001J\t\u0010\u001f\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\f¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/library/abi/LibraryManifest;", "", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "", "nativeTargets", "", "compilerVersion", "abiVersion", "libraryVersion", "irProviderName", "(Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAbiVersion", "()Ljava/lang/String;", "getCompilerVersion", "getIrProviderName", "getLibraryVersion", "getNativeTargets", "()Ljava/util/List;", "getPlatform", "component1", "component2", "component3", "component4", "component5", "component6", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public final /* data */ class LibraryManifest {
    private final String abiVersion;
    private final String compilerVersion;
    private final String irProviderName;
    private final String libraryVersion;
    private final List<String> nativeTargets;
    private final String platform;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ LibraryManifest copy$default(LibraryManifest libraryManifest, String str, List list, String str2, String str3, String str4, String str5, int i, Object obj) {
        if ((i & 1) != 0) {
            str = libraryManifest.platform;
        }
        if ((i & 2) != 0) {
            list = libraryManifest.nativeTargets;
        }
        List list2 = list;
        if ((i & 4) != 0) {
            str2 = libraryManifest.compilerVersion;
        }
        String str6 = str2;
        if ((i & 8) != 0) {
            str3 = libraryManifest.abiVersion;
        }
        String str7 = str3;
        if ((i & 16) != 0) {
            str4 = libraryManifest.libraryVersion;
        }
        String str8 = str4;
        if ((i & 32) != 0) {
            str5 = libraryManifest.irProviderName;
        }
        return libraryManifest.copy(str, list2, str6, str7, str8, str5);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getPlatform() {
        return this.platform;
    }

    public final List<String> component2() {
        return this.nativeTargets;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getCompilerVersion() {
        return this.compilerVersion;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getAbiVersion() {
        return this.abiVersion;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final String getLibraryVersion() {
        return this.libraryVersion;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final String getIrProviderName() {
        return this.irProviderName;
    }

    public final LibraryManifest copy(String platform, List<String> nativeTargets, String compilerVersion, String abiVersion, String libraryVersion, String irProviderName) {
        Intrinsics.checkNotNullParameter(nativeTargets, "nativeTargets");
        return new LibraryManifest(platform, nativeTargets, compilerVersion, abiVersion, libraryVersion, irProviderName);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LibraryManifest)) {
            return false;
        }
        LibraryManifest libraryManifest = (LibraryManifest) other;
        return Intrinsics.areEqual(this.platform, libraryManifest.platform) && Intrinsics.areEqual(this.nativeTargets, libraryManifest.nativeTargets) && Intrinsics.areEqual(this.compilerVersion, libraryManifest.compilerVersion) && Intrinsics.areEqual(this.abiVersion, libraryManifest.abiVersion) && Intrinsics.areEqual(this.libraryVersion, libraryManifest.libraryVersion) && Intrinsics.areEqual(this.irProviderName, libraryManifest.irProviderName);
    }

    public int hashCode() {
        String str = this.platform;
        int iHashCode = (((str == null ? 0 : str.hashCode()) * 31) + this.nativeTargets.hashCode()) * 31;
        String str2 = this.compilerVersion;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.abiVersion;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.libraryVersion;
        int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.irProviderName;
        return iHashCode4 + (str5 != null ? str5.hashCode() : 0);
    }

    public String toString() {
        return "LibraryManifest(platform=" + this.platform + ", nativeTargets=" + this.nativeTargets + ", compilerVersion=" + this.compilerVersion + ", abiVersion=" + this.abiVersion + ", libraryVersion=" + this.libraryVersion + ", irProviderName=" + this.irProviderName + ')';
    }

    public LibraryManifest(String str, List<String> nativeTargets, String str2, String str3, String str4, String str5) {
        Intrinsics.checkNotNullParameter(nativeTargets, "nativeTargets");
        this.platform = str;
        this.nativeTargets = nativeTargets;
        this.compilerVersion = str2;
        this.abiVersion = str3;
        this.libraryVersion = str4;
        this.irProviderName = str5;
    }

    public final String getPlatform() {
        return this.platform;
    }

    public final List<String> getNativeTargets() {
        return this.nativeTargets;
    }

    public final String getCompilerVersion() {
        return this.compilerVersion;
    }

    public final String getAbiVersion() {
        return this.abiVersion;
    }

    public final String getLibraryVersion() {
        return this.libraryVersion;
    }

    public final String getIrProviderName() {
        return this.irProviderName;
    }
}
