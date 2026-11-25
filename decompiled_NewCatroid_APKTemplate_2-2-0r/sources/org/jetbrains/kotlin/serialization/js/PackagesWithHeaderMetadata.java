package org.jetbrains.kotlin.serialization.js;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.utils.JsMetadataVersion;

/* JADX INFO: compiled from: PackagesWithHeaderMetadata.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/serialization/js/PackagesWithHeaderMetadata;", "", "header", "", "packages", "", "metadataVersion", "Lorg/jetbrains/kotlin/utils/JsMetadataVersion;", "([BLjava/util/List;Lorg/jetbrains/kotlin/utils/JsMetadataVersion;)V", "getHeader", "()[B", "getMetadataVersion", "()Lorg/jetbrains/kotlin/utils/JsMetadataVersion;", "getPackages", "()Ljava/util/List;", "js.serializer"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PackagesWithHeaderMetadata {
    private final byte[] header;
    private final JsMetadataVersion metadataVersion;
    private final List<byte[]> packages;

    public PackagesWithHeaderMetadata(byte[] header, List<byte[]> packages, JsMetadataVersion metadataVersion) {
        Intrinsics.checkNotNullParameter(header, "header");
        Intrinsics.checkNotNullParameter(packages, "packages");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        this.header = header;
        this.packages = packages;
        this.metadataVersion = metadataVersion;
    }

    public final byte[] getHeader() {
        return this.header;
    }

    public final List<byte[]> getPackages() {
        return this.packages;
    }

    public final JsMetadataVersion getMetadataVersion() {
        return this.metadataVersion;
    }
}
