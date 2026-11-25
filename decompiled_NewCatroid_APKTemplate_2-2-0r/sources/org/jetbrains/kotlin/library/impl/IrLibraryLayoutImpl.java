package org.jetbrains.kotlin.library.impl;

import java.nio.file.FileSystem;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.library.IrKotlinLibraryLayout;

/* JADX INFO: compiled from: KotlinLibraryLayoutImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u001b\u0010\b\u001a\u00020\u00028VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\n¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/library/impl/IrLibraryLayoutImpl;", "Lorg/jetbrains/kotlin/library/impl/KotlinLibraryLayoutImpl;", "Lorg/jetbrains/kotlin/library/IrKotlinLibraryLayout;", "klib", "Lorg/jetbrains/kotlin/konan/file/File;", "component", "", "(Lorg/jetbrains/kotlin/konan/file/File;Ljava/lang/String;)V", "extractingToTemp", "getExtractingToTemp", "()Lorg/jetbrains/kotlin/library/IrKotlinLibraryLayout;", "extractingToTemp$delegate", "Lkotlin/Lazy;", "directlyFromZip", "zipFileSystem", "Ljava/nio/file/FileSystem;", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class IrLibraryLayoutImpl extends KotlinLibraryLayoutImpl implements IrKotlinLibraryLayout {

    /* JADX INFO: renamed from: extractingToTemp$delegate, reason: from kotlin metadata */
    private final Lazy extractingToTemp;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrLibraryLayoutImpl(File klib, String component) {
        super(klib, component);
        Intrinsics.checkNotNullParameter(klib, "klib");
        Intrinsics.checkNotNullParameter(component, "component");
        this.extractingToTemp = LazyKt.lazy(new Function0<ExtractingIrLibraryImpl>() { // from class: org.jetbrains.kotlin.library.impl.IrLibraryLayoutImpl$extractingToTemp$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ExtractingIrLibraryImpl invoke() {
                return new ExtractingIrLibraryImpl(this.this$0);
            }
        });
    }

    @Override // org.jetbrains.kotlin.library.impl.KotlinLibraryLayoutImpl
    public IrKotlinLibraryLayout getExtractingToTemp() {
        return (IrKotlinLibraryLayout) this.extractingToTemp.getValue();
    }

    @Override // org.jetbrains.kotlin.library.impl.KotlinLibraryLayoutImpl
    public IrKotlinLibraryLayout directlyFromZip(FileSystem zipFileSystem) {
        Intrinsics.checkNotNullParameter(zipFileSystem, "zipFileSystem");
        return new FromZipIrLibraryImpl(this, zipFileSystem);
    }
}
