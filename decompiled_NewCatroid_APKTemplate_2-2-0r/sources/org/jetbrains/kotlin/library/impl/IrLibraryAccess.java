package org.jetbrains.kotlin.library.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.file.ZipFileSystemAccessor;
import org.jetbrains.kotlin.library.KotlinLibraryLayout;

/* JADX INFO: compiled from: KotlinLibraryLayoutImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B!\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nR\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/library/impl/IrLibraryAccess;", "L", "Lorg/jetbrains/kotlin/library/KotlinLibraryLayout;", "Lorg/jetbrains/kotlin/library/impl/BaseLibraryAccess;", "klib", "Lorg/jetbrains/kotlin/konan/file/File;", "component", "", "zipAccessor", "Lorg/jetbrains/kotlin/konan/file/ZipFileSystemAccessor;", "(Lorg/jetbrains/kotlin/konan/file/File;Ljava/lang/String;Lorg/jetbrains/kotlin/konan/file/ZipFileSystemAccessor;)V", "layout", "Lorg/jetbrains/kotlin/library/impl/IrLibraryLayoutImpl;", "getLayout", "()Lorg/jetbrains/kotlin/library/impl/IrLibraryLayoutImpl;", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class IrLibraryAccess<L extends KotlinLibraryLayout> extends BaseLibraryAccess<L> {
    private final IrLibraryLayoutImpl layout;

    public /* synthetic */ IrLibraryAccess(File file, String str, ZipFileSystemAccessor zipFileSystemAccessor, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(file, str, (i & 4) != 0 ? null : zipFileSystemAccessor);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrLibraryAccess(File klib, String component, ZipFileSystemAccessor zipFileSystemAccessor) {
        super(klib, component, zipFileSystemAccessor);
        Intrinsics.checkNotNullParameter(klib, "klib");
        Intrinsics.checkNotNullParameter(component, "component");
        this.layout = new IrLibraryLayoutImpl(klib, component);
    }

    @Override // org.jetbrains.kotlin.library.impl.BaseLibraryAccess
    public IrLibraryLayoutImpl getLayout() {
        return this.layout;
    }
}
