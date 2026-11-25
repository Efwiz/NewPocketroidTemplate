package org.jetbrains.kotlin.konan.library.impl;

import java.nio.file.FileSystem;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.library.BitcodeKotlinLibraryLayout;
import org.jetbrains.kotlin.konan.target.KonanTarget;

/* JADX INFO: compiled from: KonanLibraryLayoutImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\u0010\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u001b\u0010\n\u001a\u00020\u00028VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\f¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/konan/library/impl/BitcodeLibraryLayoutImpl;", "Lorg/jetbrains/kotlin/konan/library/impl/TargetedLibraryLayoutImpl;", "Lorg/jetbrains/kotlin/konan/library/BitcodeKotlinLibraryLayout;", "klib", "Lorg/jetbrains/kotlin/konan/file/File;", "component", "", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "(Lorg/jetbrains/kotlin/konan/file/File;Ljava/lang/String;Lorg/jetbrains/kotlin/konan/target/KonanTarget;)V", "extractingToTemp", "getExtractingToTemp", "()Lorg/jetbrains/kotlin/konan/library/BitcodeKotlinLibraryLayout;", "extractingToTemp$delegate", "Lkotlin/Lazy;", "directlyFromZip", "zipFileSystem", "Ljava/nio/file/FileSystem;", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class BitcodeLibraryLayoutImpl extends TargetedLibraryLayoutImpl implements BitcodeKotlinLibraryLayout {

    /* JADX INFO: renamed from: extractingToTemp$delegate, reason: from kotlin metadata */
    private final Lazy extractingToTemp;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BitcodeLibraryLayoutImpl(File klib, String component, KonanTarget konanTarget) {
        super(klib, component, konanTarget);
        Intrinsics.checkNotNullParameter(klib, "klib");
        Intrinsics.checkNotNullParameter(component, "component");
        this.extractingToTemp = LazyKt.lazy(new Function0<ExtractingBitcodeLibraryImpl>() { // from class: org.jetbrains.kotlin.konan.library.impl.BitcodeLibraryLayoutImpl$extractingToTemp$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ExtractingBitcodeLibraryImpl invoke() {
                return new ExtractingBitcodeLibraryImpl(this.this$0);
            }
        });
    }

    @Override // org.jetbrains.kotlin.konan.library.impl.TargetedLibraryLayoutImpl, org.jetbrains.kotlin.library.impl.KotlinLibraryLayoutImpl
    public BitcodeKotlinLibraryLayout getExtractingToTemp() {
        return (BitcodeKotlinLibraryLayout) this.extractingToTemp.getValue();
    }

    @Override // org.jetbrains.kotlin.konan.library.impl.TargetedLibraryLayoutImpl, org.jetbrains.kotlin.library.impl.KotlinLibraryLayoutImpl
    public BitcodeKotlinLibraryLayout directlyFromZip(FileSystem zipFileSystem) {
        Intrinsics.checkNotNullParameter(zipFileSystem, "zipFileSystem");
        return new FromZipBitcodeLibraryImpl(this, zipFileSystem);
    }
}
