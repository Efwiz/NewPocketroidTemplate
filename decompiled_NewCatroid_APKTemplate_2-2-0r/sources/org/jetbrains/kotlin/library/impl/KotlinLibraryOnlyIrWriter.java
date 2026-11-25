package org.jetbrains.kotlin.library.impl;

import java.io.IOException;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.library.KotlinLibraryVersioning;
import org.jetbrains.kotlin.library.SerializedIrModule;

/* JADX INFO: compiled from: KotlinLibraryWriterImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\n\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ>\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\n2\u0006\u0010\u0017\u001a\u00020\u0013H\u0002J\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u001cR\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/library/impl/KotlinLibraryOnlyIrWriter;", "", "output", "", "moduleName", "versions", "Lorg/jetbrains/kotlin/library/KotlinLibraryVersioning;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/library/impl/BuiltInsPlatform;", "nativeTargets", "", "perFile", "", "(Ljava/lang/String;Ljava/lang/String;Lorg/jetbrains/kotlin/library/KotlinLibraryVersioning;Lorg/jetbrains/kotlin/library/impl/BuiltInsPlatform;Ljava/util/List;Z)V", "library", "Lorg/jetbrains/kotlin/library/impl/KotlinLibraryWriterImpl;", "getLibrary", "()Lorg/jetbrains/kotlin/library/impl/KotlinLibraryWriterImpl;", ModuleXmlParser.OUTPUT_DIR, "Lorg/jetbrains/kotlin/konan/file/File;", "getOutputDir", "()Lorg/jetbrains/kotlin/konan/file/File;", "createLibrary", "directory", "invalidate", "", "writeIr", "serializedIrModule", "Lorg/jetbrains/kotlin/library/SerializedIrModule;", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class KotlinLibraryOnlyIrWriter {
    private final KotlinLibraryWriterImpl library;
    private final File outputDir;

    public KotlinLibraryOnlyIrWriter(String output, String moduleName, KotlinLibraryVersioning versions, BuiltInsPlatform platform, List<String> nativeTargets, boolean z) {
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(versions, "versions");
        Intrinsics.checkNotNullParameter(platform, "platform");
        Intrinsics.checkNotNullParameter(nativeTargets, "nativeTargets");
        File file = new File(output);
        this.outputDir = file;
        this.library = createLibrary(z, moduleName, versions, platform, nativeTargets, file);
    }

    public final File getOutputDir() {
        return this.outputDir;
    }

    public final KotlinLibraryWriterImpl getLibrary() {
        return this.library;
    }

    private final KotlinLibraryWriterImpl createLibrary(boolean perFile, String moduleName, KotlinLibraryVersioning versions, BuiltInsPlatform platform, List<String> nativeTargets, File directory) {
        KotlinLibraryLayoutForWriter kotlinLibraryLayoutForWriter = new KotlinLibraryLayoutForWriter(directory, directory, null, 4, null);
        return new KotlinLibraryWriterImpl(moduleName, versions, platform, nativeTargets, true, null, kotlinLibraryLayoutForWriter, null, null, perFile ? new IrPerFileWriterImpl(kotlinLibraryLayoutForWriter) : new IrMonoliticWriterImpl(kotlinLibraryLayoutForWriter), 416, null);
    }

    public final void invalidate() throws IOException {
        this.outputDir.deleteRecursively();
        this.library.getLayout().getIrDir().mkdirs();
    }

    public final void writeIr(SerializedIrModule serializedIrModule) {
        Intrinsics.checkNotNullParameter(serializedIrModule, "serializedIrModule");
        this.library.addIr(serializedIrModule);
    }
}
