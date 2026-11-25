package org.jetbrains.kotlin.library.metadata;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException;

/* JADX INFO: compiled from: KlibMetadataDeserializationUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0003¨\u0006\u0007"}, d2 = {"parseModuleHeader", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataProtoBuf$Header;", "libraryMetadata", "", "parsePackageFragment", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "packageMetadata", "kotlin-util-klib-metadata"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KlibMetadataDeserializationUtilsKt {
    public static final ProtoBuf.PackageFragment parsePackageFragment(byte[] packageMetadata) throws InvalidProtocolBufferException {
        Intrinsics.checkNotNullParameter(packageMetadata, "packageMetadata");
        ProtoBuf.PackageFragment from = ProtoBuf.PackageFragment.parseFrom(packageMetadata, KlibMetadataSerializerProtocol.INSTANCE.getExtensionRegistry());
        Intrinsics.checkNotNullExpressionValue(from, "parseFrom(...)");
        return from;
    }

    public static final KlibMetadataProtoBuf.Header parseModuleHeader(byte[] libraryMetadata) {
        Intrinsics.checkNotNullParameter(libraryMetadata, "libraryMetadata");
        KlibMetadataProtoBuf.Header from = KlibMetadataProtoBuf.Header.parseFrom(libraryMetadata, KlibMetadataSerializerProtocol.INSTANCE.getExtensionRegistry());
        Intrinsics.checkNotNullExpressionValue(from, "parseFrom(...)");
        return from;
    }
}
