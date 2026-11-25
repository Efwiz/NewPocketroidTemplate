package org.jetbrains.kotlin.fir.resolve.providers.impl;

import java.io.IOException;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.builtins.BuiltInsBinaryVersion;
import org.jetbrains.kotlin.serialization.deserialization.builtins.BuiltInSerializerProtocol;

/* JADX INFO: compiled from: FirBuiltinSymbolProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0082\b\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/impl/BinaryVersionAndPackageFragment;", "", "version", "Lorg/jetbrains/kotlin/metadata/builtins/BuiltInsBinaryVersion;", "packageFragment", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "(Lorg/jetbrains/kotlin/metadata/builtins/BuiltInsBinaryVersion;Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;)V", "getPackageFragment", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "getVersion", "()Lorg/jetbrains/kotlin/metadata/builtins/BuiltInsBinaryVersion;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "Companion", "fir-deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
final /* data */ class BinaryVersionAndPackageFragment {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final ProtoBuf.PackageFragment packageFragment;
    private final BuiltInsBinaryVersion version;

    public static /* synthetic */ BinaryVersionAndPackageFragment copy$default(BinaryVersionAndPackageFragment binaryVersionAndPackageFragment, BuiltInsBinaryVersion builtInsBinaryVersion, ProtoBuf.PackageFragment packageFragment, int i, Object obj) {
        if ((i & 1) != 0) {
            builtInsBinaryVersion = binaryVersionAndPackageFragment.version;
        }
        if ((i & 2) != 0) {
            packageFragment = binaryVersionAndPackageFragment.packageFragment;
        }
        return binaryVersionAndPackageFragment.copy(builtInsBinaryVersion, packageFragment);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final BuiltInsBinaryVersion getVersion() {
        return this.version;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final ProtoBuf.PackageFragment getPackageFragment() {
        return this.packageFragment;
    }

    public final BinaryVersionAndPackageFragment copy(BuiltInsBinaryVersion version, ProtoBuf.PackageFragment packageFragment) {
        Intrinsics.checkNotNullParameter(version, "version");
        Intrinsics.checkNotNullParameter(packageFragment, "packageFragment");
        return new BinaryVersionAndPackageFragment(version, packageFragment);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BinaryVersionAndPackageFragment)) {
            return false;
        }
        BinaryVersionAndPackageFragment binaryVersionAndPackageFragment = (BinaryVersionAndPackageFragment) other;
        return Intrinsics.areEqual(this.version, binaryVersionAndPackageFragment.version) && Intrinsics.areEqual(this.packageFragment, binaryVersionAndPackageFragment.packageFragment);
    }

    public int hashCode() {
        return (this.version.hashCode() * 31) + this.packageFragment.hashCode();
    }

    public String toString() {
        return "BinaryVersionAndPackageFragment(version=" + this.version + ", packageFragment=" + this.packageFragment + ')';
    }

    public BinaryVersionAndPackageFragment(BuiltInsBinaryVersion version, ProtoBuf.PackageFragment packageFragment) {
        Intrinsics.checkNotNullParameter(version, "version");
        Intrinsics.checkNotNullParameter(packageFragment, "packageFragment");
        this.version = version;
        this.packageFragment = packageFragment;
    }

    public final BuiltInsBinaryVersion getVersion() {
        return this.version;
    }

    public final ProtoBuf.PackageFragment getPackageFragment() {
        return this.packageFragment;
    }

    /* JADX INFO: compiled from: FirBuiltinSymbolProvider.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/impl/BinaryVersionAndPackageFragment$Companion;", "", "()V", "createFromStream", "Lorg/jetbrains/kotlin/fir/resolve/providers/impl/BinaryVersionAndPackageFragment;", "stream", "Ljava/io/InputStream;", "fir-deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final BinaryVersionAndPackageFragment createFromStream(InputStream stream) throws IOException {
            Intrinsics.checkNotNullParameter(stream, "stream");
            BuiltInsBinaryVersion from = BuiltInsBinaryVersion.INSTANCE.readFrom(stream);
            if (!from.isCompatibleWithCurrentCompilerVersion()) {
                throw new UnsupportedOperationException("Kotlin built-in definition format version is not supported: expected " + BuiltInsBinaryVersion.INSTANCE + ", actual " + from + ". Please update Kotlin");
            }
            ProtoBuf.PackageFragment from2 = ProtoBuf.PackageFragment.parseFrom(stream, BuiltInSerializerProtocol.INSTANCE.getExtensionRegistry());
            Intrinsics.checkNotNull(from2);
            return new BinaryVersionAndPackageFragment(from, from2);
        }
    }
}
