package org.jetbrains.kotlin.serialization.deserialization;

import java.io.IOException;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.builtins.BuiltInsBinaryVersion;
import org.jetbrains.kotlin.metadata.deserialization.NameResolverImpl;
import org.jetbrains.kotlin.serialization.deserialization.builtins.BuiltInSerializerProtocol;

/* JADX INFO: compiled from: MetadataUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a \u0010\u0000\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00012\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"readProto", "Lkotlin/Triple;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolverImpl;", "Lorg/jetbrains/kotlin/metadata/builtins/BuiltInsBinaryVersion;", "stream", "Ljava/io/InputStream;", "deserialization.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class MetadataUtilKt {
    public static final Triple<ProtoBuf.PackageFragment, NameResolverImpl, BuiltInsBinaryVersion> readProto(InputStream stream) throws IOException {
        Intrinsics.checkNotNullParameter(stream, "stream");
        BuiltInsBinaryVersion from = BuiltInsBinaryVersion.INSTANCE.readFrom(stream);
        if (!from.isCompatibleWithCurrentCompilerVersion()) {
            throw new UnsupportedOperationException("Kotlin metadata definition format version is not supported: expected " + BuiltInsBinaryVersion.INSTANCE + ", actual " + from + ". Please update Kotlin");
        }
        ProtoBuf.PackageFragment from2 = ProtoBuf.PackageFragment.parseFrom(stream, BuiltInSerializerProtocol.INSTANCE.getExtensionRegistry());
        ProtoBuf.StringTable strings = from2.getStrings();
        Intrinsics.checkNotNullExpressionValue(strings, "getStrings(...)");
        ProtoBuf.QualifiedNameTable qualifiedNames = from2.getQualifiedNames();
        Intrinsics.checkNotNullExpressionValue(qualifiedNames, "getQualifiedNames(...)");
        return new Triple<>(from2, new NameResolverImpl(strings, qualifiedNames), from);
    }
}
