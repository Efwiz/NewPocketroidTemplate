package org.jetbrains.kotlin.build;

import java.io.File;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.incremental.LocalFileKotlinClass;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;

/* JADX INFO: compiled from: generatedFiles.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B#\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/build/GeneratedJvmClass;", "Lorg/jetbrains/kotlin/build/GeneratedFile;", "sourceFiles", "", "Ljava/io/File;", "outputFile", "jvmMetadataVersionFromLanguageVersion", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "(Ljava/util/Collection;Ljava/io/File;Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;)V", "outputClass", "Lorg/jetbrains/kotlin/incremental/LocalFileKotlinClass;", "getOutputClass", "()Lorg/jetbrains/kotlin/incremental/LocalFileKotlinClass;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class GeneratedJvmClass extends GeneratedFile {
    private final LocalFileKotlinClass outputClass;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GeneratedJvmClass(Collection<? extends File> sourceFiles, File outputFile, JvmMetadataVersion jvmMetadataVersionFromLanguageVersion) {
        super(sourceFiles, outputFile);
        Intrinsics.checkNotNullParameter(sourceFiles, "sourceFiles");
        Intrinsics.checkNotNullParameter(outputFile, "outputFile");
        Intrinsics.checkNotNullParameter(jvmMetadataVersionFromLanguageVersion, "jvmMetadataVersionFromLanguageVersion");
        LocalFileKotlinClass localFileKotlinClassCreate = LocalFileKotlinClass.INSTANCE.create(outputFile, jvmMetadataVersionFromLanguageVersion);
        if (localFileKotlinClassCreate == null) {
            throw new AssertionError("Couldn't load KotlinClass from " + outputFile + "; it may happen because class doesn't have valid Kotlin annotations");
        }
        this.outputClass = localFileKotlinClassCreate;
    }

    public final LocalFileKotlinClass getOutputClass() {
        return this.outputClass;
    }
}
