package org.jetbrains.kotlin.library.impl;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.library.MetadataKotlinLibraryLayout;

/* JADX INFO: compiled from: KotlinLibraryLayoutImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u001b\u0010\u0006\u001a\u00020\u00078VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/library/impl/ExtractingMetadataLibraryImpl;", "Lorg/jetbrains/kotlin/library/impl/ExtractingKotlinLibraryLayout;", "Lorg/jetbrains/kotlin/library/MetadataKotlinLibraryLayout;", "zipped", "Lorg/jetbrains/kotlin/library/impl/MetadataLibraryLayoutImpl;", "(Lorg/jetbrains/kotlin/library/impl/MetadataLibraryLayoutImpl;)V", "metadataDir", "Lorg/jetbrains/kotlin/konan/file/File;", "getMetadataDir", "()Lorg/jetbrains/kotlin/konan/file/File;", "metadataDir$delegate", "Lkotlin/Lazy;", "getZipped", "()Lorg/jetbrains/kotlin/library/impl/MetadataLibraryLayoutImpl;", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ExtractingMetadataLibraryImpl extends ExtractingKotlinLibraryLayout implements MetadataKotlinLibraryLayout {

    /* JADX INFO: renamed from: metadataDir$delegate, reason: from kotlin metadata */
    private final Lazy metadataDir;
    private final MetadataLibraryLayoutImpl zipped;

    public final MetadataLibraryLayoutImpl getZipped() {
        return this.zipped;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ExtractingMetadataLibraryImpl(MetadataLibraryLayoutImpl zipped) {
        super(zipped);
        Intrinsics.checkNotNullParameter(zipped, "zipped");
        this.zipped = zipped;
        this.metadataDir = LazyKt.lazy(new Function0<File>() { // from class: org.jetbrains.kotlin.library.impl.ExtractingMetadataLibraryImpl$metadataDir$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final File invoke() {
                return KotlinLibraryLayoutImplKt.extractDir(this.this$0.getZipped(), this.this$0.getZipped().getMetadataDir());
            }
        });
    }

    @Override // org.jetbrains.kotlin.library.MetadataKotlinLibraryLayout
    public File getMetadataDir() {
        return (File) this.metadataDir.getValue();
    }
}
