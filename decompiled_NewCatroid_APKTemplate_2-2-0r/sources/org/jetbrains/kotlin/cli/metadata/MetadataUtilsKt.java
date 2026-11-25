package org.jetbrains.kotlin.cli.metadata;

import java.io.File;
import java.io.IOException;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.KotlinCompilerVersion;
import org.jetbrains.kotlin.library.KotlinAbiVersion;
import org.jetbrains.kotlin.library.KotlinLibraryVersioning;
import org.jetbrains.kotlin.library.SerializedMetadata;
import org.jetbrains.kotlin.library.impl.BuiltInsPlatform;
import org.jetbrains.kotlin.library.impl.KotlinLibraryWriterImplKt;
import org.jetbrains.kotlin.library.metadata.KlibMetadataVersion;

/* JADX INFO: compiled from: MetadataUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\b"}, d2 = {"buildKotlinMetadataLibrary", "", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "serializedMetadata", "Lorg/jetbrains/kotlin/library/SerializedMetadata;", "destDir", "Ljava/io/File;", "cli"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class MetadataUtilsKt {
    public static final void buildKotlinMetadataLibrary(CompilerConfiguration configuration, SerializedMetadata serializedMetadata, File destDir) throws IOException {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(serializedMetadata, "serializedMetadata");
        Intrinsics.checkNotNullParameter(destDir, "destDir");
        KotlinLibraryVersioning kotlinLibraryVersioning = new KotlinLibraryVersioning(null, KotlinCompilerVersion.getVersion(), KotlinAbiVersion.INSTANCE.getCURRENT(), KlibMetadataVersion.INSTANCE.toString(), null, 16, null);
        List listEmptyList = CollectionsKt.emptyList();
        String absolutePath = destDir.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
        Object obj = configuration.get(CommonConfigurationKeys.MODULE_NAME);
        Intrinsics.checkNotNull(obj);
        KotlinLibraryWriterImplKt.buildKotlinLibrary(listEmptyList, serializedMetadata, null, kotlinLibraryVersioning, absolutePath, (String) obj, true, false, null, null, BuiltInsPlatform.COMMON, (2048 & 2048) != 0 ? CollectionsKt.emptyList() : null);
    }
}
