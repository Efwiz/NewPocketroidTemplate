package org.jetbrains.kotlin.konan.library.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.library.KonanLibraryLayout;
import org.jetbrains.kotlin.konan.target.KonanTarget;
import org.jetbrains.kotlin.library.impl.KotlinLibraryLayoutForWriter;

/* JADX INFO: compiled from: KonanLibraryWriterImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/konan/library/impl/KonanLibraryLayoutForWriter;", "Lorg/jetbrains/kotlin/konan/library/KonanLibraryLayout;", "Lorg/jetbrains/kotlin/library/impl/KotlinLibraryLayoutForWriter;", "libFile", "Lorg/jetbrains/kotlin/konan/file/File;", "unzippedDir", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "(Lorg/jetbrains/kotlin/konan/file/File;Lorg/jetbrains/kotlin/konan/file/File;Lorg/jetbrains/kotlin/konan/target/KonanTarget;)V", "getTarget", "()Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class KonanLibraryLayoutForWriter extends KotlinLibraryLayoutForWriter implements KonanLibraryLayout {
    private final KonanTarget target;

    @Override // org.jetbrains.kotlin.konan.library.TargetedKotlinLibraryLayout
    public KonanTarget getTarget() {
        return this.target;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KonanLibraryLayoutForWriter(File libFile, File unzippedDir, KonanTarget target) {
        super(libFile, unzippedDir, null, 4, null);
        Intrinsics.checkNotNullParameter(libFile, "libFile");
        Intrinsics.checkNotNullParameter(unzippedDir, "unzippedDir");
        Intrinsics.checkNotNullParameter(target, "target");
        this.target = target;
    }
}
