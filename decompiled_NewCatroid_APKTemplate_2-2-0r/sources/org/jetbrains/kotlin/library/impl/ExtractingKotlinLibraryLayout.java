package org.jetbrains.kotlin.library.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.library.KotlinLibraryLayout;

/* JADX INFO: compiled from: KotlinLibraryLayoutImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\b¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/library/impl/ExtractingKotlinLibraryLayout;", "Lorg/jetbrains/kotlin/library/KotlinLibraryLayout;", "zipped", "Lorg/jetbrains/kotlin/library/impl/KotlinLibraryLayoutImpl;", "(Lorg/jetbrains/kotlin/library/impl/KotlinLibraryLayoutImpl;)V", "component", "", "getComponent", "()Ljava/lang/String;", "libFile", "Lorg/jetbrains/kotlin/konan/file/File;", "getLibFile", "()Lorg/jetbrains/kotlin/konan/file/File;", "libraryName", "getLibraryName", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public class ExtractingKotlinLibraryLayout implements KotlinLibraryLayout {
    private final String component;
    private final String libraryName;

    public ExtractingKotlinLibraryLayout(KotlinLibraryLayoutImpl zipped) {
        Intrinsics.checkNotNullParameter(zipped, "zipped");
        this.libraryName = zipped.getLibraryName();
        this.component = zipped.getComponent();
    }

    @Override // org.jetbrains.kotlin.library.KotlinLibraryLayout
    public File getLibFile() {
        throw new IllegalStateException("Extracting layout doesn't extract its own root".toString());
    }

    @Override // org.jetbrains.kotlin.library.KotlinLibraryLayout
    public String getLibraryName() {
        return this.libraryName;
    }

    @Override // org.jetbrains.kotlin.library.KotlinLibraryLayout
    public String getComponent() {
        return this.component;
    }
}
