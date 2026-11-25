package org.jetbrains.kotlin.metadata.builtins;

import java.io.InputStream;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.protobuf.ExtensionRegistryLite;

/* JADX INFO: compiled from: readPackageFragment.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0004\u0012\u00020\u00030\u0001*\u00020\u0004¨\u0006\u0005"}, d2 = {"readBuiltinsPackageFragment", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "Lorg/jetbrains/kotlin/metadata/builtins/BuiltInsBinaryVersion;", "Ljava/io/InputStream;", "metadata"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ReadPackageFragmentKt {
    public static final Pair<ProtoBuf.PackageFragment, BuiltInsBinaryVersion> readBuiltinsPackageFragment(InputStream inputStream) {
        ProtoBuf.PackageFragment from;
        Intrinsics.checkNotNullParameter(inputStream, "<this>");
        InputStream inputStream2 = inputStream;
        try {
            InputStream inputStream3 = inputStream2;
            BuiltInsBinaryVersion from2 = BuiltInsBinaryVersion.INSTANCE.readFrom(inputStream3);
            if (from2.isCompatibleWithCurrentCompilerVersion()) {
                ExtensionRegistryLite extensionRegistryLiteNewInstance = ExtensionRegistryLite.newInstance();
                BuiltInsProtoBuf.registerAllExtensions(extensionRegistryLiteNewInstance);
                from = ProtoBuf.PackageFragment.parseFrom(inputStream3, extensionRegistryLiteNewInstance);
            } else {
                from = null;
            }
            Pair<ProtoBuf.PackageFragment, BuiltInsBinaryVersion> pair = TuplesKt.to(from, from2);
            CloseableKt.closeFinally(inputStream2, null);
            return pair;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(inputStream2, th);
                throw th2;
            }
        }
    }
}
