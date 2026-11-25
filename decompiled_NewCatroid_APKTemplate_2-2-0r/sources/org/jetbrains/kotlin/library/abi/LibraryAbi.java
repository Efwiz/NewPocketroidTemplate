package org.jetbrains.kotlin.library.abi;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;

/* JADX INFO: compiled from: LibraryAbi.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b\u0007\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/LibraryAbi;", "", KotlinLibraryLayoutKt.KLIB_MANIFEST_FILE_NAME, "Lorg/jetbrains/kotlin/library/abi/LibraryManifest;", "uniqueName", "", "signatureVersions", "", "Lorg/jetbrains/kotlin/library/abi/AbiSignatureVersion;", "topLevelDeclarations", "Lorg/jetbrains/kotlin/library/abi/AbiTopLevelDeclarations;", "(Lorg/jetbrains/kotlin/library/abi/LibraryManifest;Ljava/lang/String;Ljava/util/Set;Lorg/jetbrains/kotlin/library/abi/AbiTopLevelDeclarations;)V", "getManifest", "()Lorg/jetbrains/kotlin/library/abi/LibraryManifest;", "getSignatureVersions", "()Ljava/util/Set;", "getTopLevelDeclarations", "()Lorg/jetbrains/kotlin/library/abi/AbiTopLevelDeclarations;", "getUniqueName", "()Ljava/lang/String;", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public final class LibraryAbi {
    private final LibraryManifest manifest;
    private final Set<AbiSignatureVersion> signatureVersions;
    private final AbiTopLevelDeclarations topLevelDeclarations;
    private final String uniqueName;

    /* JADX WARN: Multi-variable type inference failed */
    public LibraryAbi(LibraryManifest manifest, String uniqueName, Set<? extends AbiSignatureVersion> signatureVersions, AbiTopLevelDeclarations topLevelDeclarations) {
        Intrinsics.checkNotNullParameter(manifest, "manifest");
        Intrinsics.checkNotNullParameter(uniqueName, "uniqueName");
        Intrinsics.checkNotNullParameter(signatureVersions, "signatureVersions");
        Intrinsics.checkNotNullParameter(topLevelDeclarations, "topLevelDeclarations");
        this.manifest = manifest;
        this.uniqueName = uniqueName;
        this.signatureVersions = signatureVersions;
        this.topLevelDeclarations = topLevelDeclarations;
    }

    public final LibraryManifest getManifest() {
        return this.manifest;
    }

    public final String getUniqueName() {
        return this.uniqueName;
    }

    public final Set<AbiSignatureVersion> getSignatureVersions() {
        return this.signatureVersions;
    }

    public final AbiTopLevelDeclarations getTopLevelDeclarations() {
        return this.topLevelDeclarations;
    }
}
