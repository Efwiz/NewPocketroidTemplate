package org.jetbrains.kotlin.konan.library.impl;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.library.BitcodeKotlinLibraryLayout;
import org.jetbrains.kotlin.library.impl.KotlinLibraryLayoutImplKt;

/* JADX INFO: compiled from: KonanLibraryLayoutImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u001b\u0010\u0006\u001a\u00020\u00078VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u001b\u0010\f\u001a\u00020\u00078VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000b\u001a\u0004\b\r\u0010\t¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/konan/library/impl/ExtractingBitcodeLibraryImpl;", "Lorg/jetbrains/kotlin/konan/library/impl/ExtractingTargetedLibraryImpl;", "Lorg/jetbrains/kotlin/konan/library/BitcodeKotlinLibraryLayout;", "zipped", "Lorg/jetbrains/kotlin/konan/library/impl/BitcodeLibraryLayoutImpl;", "(Lorg/jetbrains/kotlin/konan/library/impl/BitcodeLibraryLayoutImpl;)V", "kotlinDir", "Lorg/jetbrains/kotlin/konan/file/File;", "getKotlinDir", "()Lorg/jetbrains/kotlin/konan/file/File;", "kotlinDir$delegate", "Lkotlin/Lazy;", "nativeDir", "getNativeDir", "nativeDir$delegate", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ExtractingBitcodeLibraryImpl extends ExtractingTargetedLibraryImpl implements BitcodeKotlinLibraryLayout {

    /* JADX INFO: renamed from: kotlinDir$delegate, reason: from kotlin metadata */
    private final Lazy kotlinDir;

    /* JADX INFO: renamed from: nativeDir$delegate, reason: from kotlin metadata */
    private final Lazy nativeDir;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ExtractingBitcodeLibraryImpl(final BitcodeLibraryLayoutImpl zipped) {
        super(zipped);
        Intrinsics.checkNotNullParameter(zipped, "zipped");
        this.kotlinDir = LazyKt.lazy(new Function0<File>() { // from class: org.jetbrains.kotlin.konan.library.impl.ExtractingBitcodeLibraryImpl$kotlinDir$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final File invoke() {
                BitcodeLibraryLayoutImpl bitcodeLibraryLayoutImpl = zipped;
                return KotlinLibraryLayoutImplKt.extractDir(bitcodeLibraryLayoutImpl, bitcodeLibraryLayoutImpl.getKotlinDir());
            }
        });
        this.nativeDir = LazyKt.lazy(new Function0<File>() { // from class: org.jetbrains.kotlin.konan.library.impl.ExtractingBitcodeLibraryImpl$nativeDir$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final File invoke() {
                BitcodeLibraryLayoutImpl bitcodeLibraryLayoutImpl = zipped;
                return KotlinLibraryLayoutImplKt.extractDir(bitcodeLibraryLayoutImpl, bitcodeLibraryLayoutImpl.getNativeDir());
            }
        });
    }

    @Override // org.jetbrains.kotlin.konan.library.BitcodeKotlinLibraryLayout
    public File getKotlinDir() {
        return (File) this.kotlinDir.getValue();
    }

    @Override // org.jetbrains.kotlin.konan.library.BitcodeKotlinLibraryLayout
    public File getNativeDir() {
        return (File) this.nativeDir.getValue();
    }
}
