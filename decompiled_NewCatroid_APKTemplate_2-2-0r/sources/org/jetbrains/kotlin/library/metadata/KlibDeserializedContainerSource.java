package org.jetbrains.kotlin.library.metadata;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.SourceFile;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration;
import org.jetbrains.kotlin.serialization.deserialization.IncompatibleVersionErrorData;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerAbiStability;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;

/* JADX INFO: compiled from: KlibDeserializedContainerSource.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B'\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nB\u001d\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\f¢\u0006\u0002\u0010\u0010J\b\u0010\u001c\u001a\u00020\u001dH\u0016R\u0014\u0010\u0011\u001a\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u000f\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0019R\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0019R\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/KlibDeserializedContainerSource;", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "library", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "header", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataProtoBuf$Header;", "configuration", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "(Lorg/jetbrains/kotlin/library/KotlinLibrary;Lorg/jetbrains/kotlin/library/metadata/KlibMetadataProtoBuf$Header;Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;Lorg/jetbrains/kotlin/name/FqName;)V", "isPreReleaseInvisible", "", "presentableString", "", "isFromNativeInteropLibrary", "(ZLjava/lang/String;Z)V", "abiStability", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerAbiStability;", "getAbiStability", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerAbiStability;", "incompatibility", "Lorg/jetbrains/kotlin/serialization/deserialization/IncompatibleVersionErrorData;", "getIncompatibility", "()Lorg/jetbrains/kotlin/serialization/deserialization/IncompatibleVersionErrorData;", "()Z", "getPresentableString", "()Ljava/lang/String;", "getContainingFile", "Lorg/jetbrains/kotlin/descriptors/SourceFile;", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KlibDeserializedContainerSource implements DeserializedContainerSource {
    private final boolean isFromNativeInteropLibrary;
    private final boolean isPreReleaseInvisible;
    private final String presentableString;

    @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource
    /* JADX INFO: renamed from: getIncompatibility */
    public IncompatibleVersionErrorData<?> mo6784getIncompatibility() {
        return null;
    }

    public KlibDeserializedContainerSource(boolean z, String presentableString, boolean z2) {
        Intrinsics.checkNotNullParameter(presentableString, "presentableString");
        this.isPreReleaseInvisible = z;
        this.presentableString = presentableString;
        this.isFromNativeInteropLibrary = z2;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource
    /* JADX INFO: renamed from: isPreReleaseInvisible, reason: from getter */
    public boolean getIsPreReleaseInvisible() {
        return this.isPreReleaseInvisible;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource
    public String getPresentableString() {
        return this.presentableString;
    }

    /* JADX INFO: renamed from: isFromNativeInteropLibrary, reason: from getter */
    public final boolean getIsFromNativeInteropLibrary() {
        return this.isFromNativeInteropLibrary;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public KlibDeserializedContainerSource(KotlinLibrary library, KlibMetadataProtoBuf.Header header, DeserializationConfiguration configuration, FqName packageFqName) {
        this(configuration.getReportErrorsOnPreReleaseDependencies() && (header.getFlags() & 2) != 0, "Package '" + packageFqName + '\'', KlibDeserializedContainerSourceKt.isInteropLibrary(library));
        Intrinsics.checkNotNullParameter(library, "library");
        Intrinsics.checkNotNullParameter(header, "header");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource
    public DeserializedContainerAbiStability getAbiStability() {
        return DeserializedContainerAbiStability.STABLE;
    }

    @Override // org.jetbrains.kotlin.descriptors.SourceElement
    public SourceFile getContainingFile() {
        SourceFile NO_SOURCE_FILE = SourceFile.NO_SOURCE_FILE;
        Intrinsics.checkNotNullExpressionValue(NO_SOURCE_FILE, "NO_SOURCE_FILE");
        return NO_SOURCE_FILE;
    }
}
