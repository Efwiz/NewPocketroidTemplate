package org.jetbrains.kotlin.konan.library.impl;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.library.TargetedKotlinLibraryLayout;
import org.jetbrains.kotlin.library.impl.ExtractingKotlinLibraryLayout;
import org.jetbrains.kotlin.library.impl.KotlinLibraryLayoutImplKt;

/* JADX INFO: compiled from: KonanLibraryLayoutImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u001b\u0010\u0006\u001a\u00020\u00078VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\t¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/konan/library/impl/ExtractingTargetedLibraryImpl;", "Lorg/jetbrains/kotlin/library/impl/ExtractingKotlinLibraryLayout;", "Lorg/jetbrains/kotlin/konan/library/TargetedKotlinLibraryLayout;", "zipped", "Lorg/jetbrains/kotlin/konan/library/impl/TargetedLibraryLayoutImpl;", "(Lorg/jetbrains/kotlin/konan/library/impl/TargetedLibraryLayoutImpl;)V", "includedDir", "Lorg/jetbrains/kotlin/konan/file/File;", "getIncludedDir", "()Lorg/jetbrains/kotlin/konan/file/File;", "includedDir$delegate", "Lkotlin/Lazy;", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public class ExtractingTargetedLibraryImpl extends ExtractingKotlinLibraryLayout implements TargetedKotlinLibraryLayout {

    /* JADX INFO: renamed from: includedDir$delegate, reason: from kotlin metadata */
    private final Lazy includedDir;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ExtractingTargetedLibraryImpl(final TargetedLibraryLayoutImpl zipped) {
        super(zipped);
        Intrinsics.checkNotNullParameter(zipped, "zipped");
        this.includedDir = LazyKt.lazy(new Function0<File>() { // from class: org.jetbrains.kotlin.konan.library.impl.ExtractingTargetedLibraryImpl$includedDir$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final File invoke() {
                TargetedLibraryLayoutImpl targetedLibraryLayoutImpl = zipped;
                return KotlinLibraryLayoutImplKt.extractDir(targetedLibraryLayoutImpl, targetedLibraryLayoutImpl.getIncludedDir());
            }
        });
    }

    @Override // org.jetbrains.kotlin.konan.library.TargetedKotlinLibraryLayout
    public File getIncludedDir() {
        return (File) this.includedDir.getValue();
    }
}
