package org.jetbrains.kotlin.konan.library.impl;

import java.nio.file.FileSystem;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.library.TargetedKotlinLibraryLayout;
import org.jetbrains.kotlin.konan.target.KonanTarget;
import org.jetbrains.kotlin.library.impl.KotlinLibraryLayoutImpl;

/* JADX INFO: compiled from: KonanLibraryLayoutImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\u0010\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u001b\u0010\n\u001a\u00020\u00028VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/konan/library/impl/TargetedLibraryLayoutImpl;", "Lorg/jetbrains/kotlin/library/impl/KotlinLibraryLayoutImpl;", "Lorg/jetbrains/kotlin/konan/library/TargetedKotlinLibraryLayout;", "klib", "Lorg/jetbrains/kotlin/konan/file/File;", "component", "", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "(Lorg/jetbrains/kotlin/konan/file/File;Ljava/lang/String;Lorg/jetbrains/kotlin/konan/target/KonanTarget;)V", "extractingToTemp", "getExtractingToTemp", "()Lorg/jetbrains/kotlin/konan/library/TargetedKotlinLibraryLayout;", "extractingToTemp$delegate", "Lkotlin/Lazy;", "getTarget", "()Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "directlyFromZip", "zipFileSystem", "Ljava/nio/file/FileSystem;", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public class TargetedLibraryLayoutImpl extends KotlinLibraryLayoutImpl implements TargetedKotlinLibraryLayout {

    /* JADX INFO: renamed from: extractingToTemp$delegate, reason: from kotlin metadata */
    private final Lazy extractingToTemp;
    private final KonanTarget target;

    @Override // org.jetbrains.kotlin.konan.library.TargetedKotlinLibraryLayout
    public KonanTarget getTarget() {
        return this.target;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TargetedLibraryLayoutImpl(File klib, String component, KonanTarget konanTarget) {
        super(klib, component);
        Intrinsics.checkNotNullParameter(klib, "klib");
        Intrinsics.checkNotNullParameter(component, "component");
        this.target = konanTarget;
        this.extractingToTemp = LazyKt.lazy(new Function0<ExtractingTargetedLibraryImpl>() { // from class: org.jetbrains.kotlin.konan.library.impl.TargetedLibraryLayoutImpl$extractingToTemp$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ExtractingTargetedLibraryImpl invoke() {
                return new ExtractingTargetedLibraryImpl(this.this$0);
            }
        });
    }

    @Override // org.jetbrains.kotlin.library.impl.KotlinLibraryLayoutImpl
    public TargetedKotlinLibraryLayout getExtractingToTemp() {
        return (TargetedKotlinLibraryLayout) this.extractingToTemp.getValue();
    }

    @Override // org.jetbrains.kotlin.library.impl.KotlinLibraryLayoutImpl
    public TargetedKotlinLibraryLayout directlyFromZip(FileSystem zipFileSystem) {
        Intrinsics.checkNotNullParameter(zipFileSystem, "zipFileSystem");
        return new FromZipTargetedLibraryImpl(this, zipFileSystem);
    }
}
