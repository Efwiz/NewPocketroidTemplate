package org.jetbrains.kotlin.library.impl;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;

/* JADX INFO: compiled from: KotlinLibraryLayoutImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u001b\u0010\u0005\u001a\u00020\u00068VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u001b\u0010\u000b\u001a\u00020\u00068VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\n\u001a\u0004\b\f\u0010\b¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/library/impl/ExtractingBaseLibraryImpl;", "Lorg/jetbrains/kotlin/library/impl/ExtractingKotlinLibraryLayout;", "zipped", "Lorg/jetbrains/kotlin/library/impl/KotlinLibraryLayoutImpl;", "(Lorg/jetbrains/kotlin/library/impl/KotlinLibraryLayoutImpl;)V", "manifestFile", "Lorg/jetbrains/kotlin/konan/file/File;", "getManifestFile", "()Lorg/jetbrains/kotlin/konan/file/File;", "manifestFile$delegate", "Lkotlin/Lazy;", "resourcesDir", "getResourcesDir", "resourcesDir$delegate", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ExtractingBaseLibraryImpl extends ExtractingKotlinLibraryLayout {

    /* JADX INFO: renamed from: manifestFile$delegate, reason: from kotlin metadata */
    private final Lazy manifestFile;

    /* JADX INFO: renamed from: resourcesDir$delegate, reason: from kotlin metadata */
    private final Lazy resourcesDir;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ExtractingBaseLibraryImpl(final KotlinLibraryLayoutImpl zipped) {
        super(zipped);
        Intrinsics.checkNotNullParameter(zipped, "zipped");
        this.manifestFile = LazyKt.lazy(new Function0<File>() { // from class: org.jetbrains.kotlin.library.impl.ExtractingBaseLibraryImpl$manifestFile$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final File invoke() {
                KotlinLibraryLayoutImpl kotlinLibraryLayoutImpl = zipped;
                return KotlinLibraryLayoutImplKt.extract(kotlinLibraryLayoutImpl, kotlinLibraryLayoutImpl.getManifestFile());
            }
        });
        this.resourcesDir = LazyKt.lazy(new Function0<File>() { // from class: org.jetbrains.kotlin.library.impl.ExtractingBaseLibraryImpl$resourcesDir$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final File invoke() {
                KotlinLibraryLayoutImpl kotlinLibraryLayoutImpl = zipped;
                return KotlinLibraryLayoutImplKt.extractDir(kotlinLibraryLayoutImpl, kotlinLibraryLayoutImpl.getResourcesDir());
            }
        });
    }

    @Override // org.jetbrains.kotlin.library.KotlinLibraryLayout
    public File getManifestFile() {
        return (File) this.manifestFile.getValue();
    }

    @Override // org.jetbrains.kotlin.library.KotlinLibraryLayout
    public File getResourcesDir() {
        return (File) this.resourcesDir.getValue();
    }
}
