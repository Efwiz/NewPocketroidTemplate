package org.jetbrains.kotlin.library.impl;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.file.FileKt;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.KotlinLibraryLayout;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;
import org.jetbrains.kotlin.library.KotlinLibraryVersioning;
import org.jetbrains.kotlin.library.SerializedIrModule;
import org.jetbrains.kotlin.library.SerializedMetadata;

/* JADX INFO: compiled from: KotlinLibraryWriterImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000L\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0080\u0001\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u000e\u0010\u0012\u001a\n\u0018\u00010\u0013j\u0004\u0018\u0001`\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u000e\b\u0002\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005\u001a\u0010\u0010\u001a\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u0005\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"KLIB_DEFAULT_COMPONENT_NAME", "", "buildKotlinLibrary", "Lorg/jetbrains/kotlin/library/KotlinLibraryLayout;", "linkDependencies", "", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "metadata", "Lorg/jetbrains/kotlin/library/SerializedMetadata;", KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME, "Lorg/jetbrains/kotlin/library/SerializedIrModule;", "versions", "Lorg/jetbrains/kotlin/library/KotlinLibraryVersioning;", "output", "moduleName", "nopack", "", "perFile", "manifestProperties", "Ljava/util/Properties;", "Lorg/jetbrains/kotlin/konan/properties/Properties;", "dataFlowGraph", "", "builtInsPlatform", "Lorg/jetbrains/kotlin/library/impl/BuiltInsPlatform;", "nativeTargets", "toSpaceSeparatedString", "kotlin-util-klib"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class KotlinLibraryWriterImplKt {
    public static final String KLIB_DEFAULT_COMPONENT_NAME = "default";

    public static final KotlinLibraryLayout buildKotlinLibrary(List<? extends KotlinLibrary> linkDependencies, SerializedMetadata metadata, SerializedIrModule serializedIrModule, KotlinLibraryVersioning versions, String output, String moduleName, boolean z, boolean z2, Properties properties, byte[] bArr, BuiltInsPlatform builtInsPlatform, List<String> nativeTargets) throws IOException {
        File fileCreateTempDir;
        Intrinsics.checkNotNullParameter(linkDependencies, "linkDependencies");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        Intrinsics.checkNotNullParameter(versions, "versions");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(builtInsPlatform, "builtInsPlatform");
        Intrinsics.checkNotNullParameter(nativeTargets, "nativeTargets");
        File file = new File(output);
        if (z) {
            file.isDirectory();
            fileCreateTempDir = file;
        } else {
            fileCreateTempDir = FileKt.createTempDir(moduleName);
        }
        KotlinLibraryLayoutForWriter kotlinLibraryLayoutForWriter = new KotlinLibraryLayoutForWriter(file, fileCreateTempDir, null, 4, null);
        KotlinLibraryWriterImpl kotlinLibraryWriterImpl = new KotlinLibraryWriterImpl(moduleName, versions, builtInsPlatform, nativeTargets, z, null, kotlinLibraryLayoutForWriter, null, null, z2 ? new IrPerFileWriterImpl(kotlinLibraryLayoutForWriter) : new IrMonoliticWriterImpl(kotlinLibraryLayoutForWriter), 416, null);
        kotlinLibraryWriterImpl.addMetadata(metadata);
        if (serializedIrModule != null) {
            kotlinLibraryWriterImpl.addIr(serializedIrModule);
        }
        if (properties != null) {
            kotlinLibraryWriterImpl.addManifestAddend(properties);
        }
        kotlinLibraryWriterImpl.addLinkDependencies(linkDependencies);
        if (bArr != null) {
            kotlinLibraryWriterImpl.addDataFlowGraph(bArr);
        }
        kotlinLibraryWriterImpl.commit();
        return kotlinLibraryWriterImpl.getLayout();
    }

    public static final String toSpaceSeparatedString(List<String> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        return CollectionsKt.joinToString$default(list, " ", null, null, 0, null, new Function1<String, CharSequence>() { // from class: org.jetbrains.kotlin.library.impl.KotlinLibraryWriterImplKt.toSpaceSeparatedString.1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String str = it;
                return StringsKt.contains$default((CharSequence) str, (CharSequence) " ", false, 2, (Object) null) ? "\"" + it + '\"' : str;
            }
        }, 30, null);
    }
}
