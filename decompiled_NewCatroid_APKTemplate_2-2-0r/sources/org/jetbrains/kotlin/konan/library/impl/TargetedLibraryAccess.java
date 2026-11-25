package org.jetbrains.kotlin.konan.library.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.target.KonanTarget;
import org.jetbrains.kotlin.library.KotlinLibraryLayout;
import org.jetbrains.kotlin.library.impl.BaseLibraryAccess;

/* JADX INFO: compiled from: KonanLibraryLayoutImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u001f\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nR\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/konan/library/impl/TargetedLibraryAccess;", "L", "Lorg/jetbrains/kotlin/library/KotlinLibraryLayout;", "Lorg/jetbrains/kotlin/library/impl/BaseLibraryAccess;", "klib", "Lorg/jetbrains/kotlin/konan/file/File;", "component", "", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "(Lorg/jetbrains/kotlin/konan/file/File;Ljava/lang/String;Lorg/jetbrains/kotlin/konan/target/KonanTarget;)V", "layout", "Lorg/jetbrains/kotlin/konan/library/impl/TargetedLibraryLayoutImpl;", "getLayout", "()Lorg/jetbrains/kotlin/konan/library/impl/TargetedLibraryLayoutImpl;", "getTarget", "()Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public class TargetedLibraryAccess<L extends KotlinLibraryLayout> extends BaseLibraryAccess<L> {
    private final TargetedLibraryLayoutImpl layout;
    private final KonanTarget target;

    public final KonanTarget getTarget() {
        return this.target;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TargetedLibraryAccess(File klib, String component, KonanTarget konanTarget) {
        super(klib, component, null, 4, null);
        Intrinsics.checkNotNullParameter(klib, "klib");
        Intrinsics.checkNotNullParameter(component, "component");
        this.target = konanTarget;
        this.layout = new TargetedLibraryLayoutImpl(klib, component, konanTarget);
    }

    @Override // org.jetbrains.kotlin.library.impl.BaseLibraryAccess
    public TargetedLibraryLayoutImpl getLayout() {
        return this.layout;
    }
}
