package org.jetbrains.kotlin.konan.library.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.file.FileKt;
import org.jetbrains.kotlin.konan.library.KonanLibrary;
import org.jetbrains.kotlin.konan.library.KonanLibraryLayout;
import org.jetbrains.kotlin.konan.target.KonanTarget;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;
import org.jetbrains.kotlin.library.KotlinLibraryVersioning;
import org.jetbrains.kotlin.library.SerializedIrModule;
import org.jetbrains.kotlin.library.SerializedMetadata;
import org.jetbrains.kotlin.library.impl.BuiltInsPlatform;

/* JADX INFO: compiled from: KonanLibraryWriterImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000L\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\u001a\u008e\u0001\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u00032\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u00042\u000e\u0010\u0015\u001a\n\u0018\u00010\u0016j\u0004\u0018\u0001`\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019¨\u0006\u001a"}, d2 = {"buildLibrary", "Lorg/jetbrains/kotlin/konan/library/KonanLibraryLayout;", "natives", "", "", "included", "linkDependencies", "Lorg/jetbrains/kotlin/konan/library/KonanLibrary;", "metadata", "Lorg/jetbrains/kotlin/library/SerializedMetadata;", KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME, "Lorg/jetbrains/kotlin/library/SerializedIrModule;", "versions", "Lorg/jetbrains/kotlin/library/KotlinLibraryVersioning;", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "output", "moduleName", "nopack", "", "shortName", "manifestProperties", "Ljava/util/Properties;", "Lorg/jetbrains/kotlin/konan/properties/Properties;", "dataFlowGraph", "", "kotlin-native-utils"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class KonanLibraryWriterImplKt {
    public static final KonanLibraryLayout buildLibrary(List<String> natives, List<String> included, List<? extends KonanLibrary> linkDependencies, SerializedMetadata metadata, SerializedIrModule serializedIrModule, KotlinLibraryVersioning versions, KonanTarget target, String output, String moduleName, boolean z, String str, Properties properties, byte[] bArr) {
        Intrinsics.checkNotNullParameter(natives, "natives");
        Intrinsics.checkNotNullParameter(included, "included");
        Intrinsics.checkNotNullParameter(linkDependencies, "linkDependencies");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        Intrinsics.checkNotNullParameter(versions, "versions");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        File file = new File(output);
        KonanLibraryWriterImpl konanLibraryWriterImpl = new KonanLibraryWriterImpl(moduleName, versions, target, BuiltInsPlatform.NATIVE, z, str, new KonanLibraryLayoutForWriter(file, z ? file : FileKt.createTempDir("klib"), target), null, null, null, null, 1920, null);
        konanLibraryWriterImpl.addMetadata(metadata);
        if (serializedIrModule != null) {
            konanLibraryWriterImpl.addIr(serializedIrModule);
        }
        Iterator<T> it = natives.iterator();
        while (it.hasNext()) {
            konanLibraryWriterImpl.addNativeBitcode((String) it.next());
        }
        Iterator<T> it2 = included.iterator();
        while (it2.hasNext()) {
            konanLibraryWriterImpl.addIncludedBinary((String) it2.next());
        }
        if (properties != null) {
            konanLibraryWriterImpl.addManifestAddend(properties);
        }
        konanLibraryWriterImpl.addLinkDependencies(linkDependencies);
        if (bArr != null) {
            konanLibraryWriterImpl.addDataFlowGraph(bArr);
        }
        konanLibraryWriterImpl.commit();
        return konanLibraryWriterImpl.getLayout();
    }
}
