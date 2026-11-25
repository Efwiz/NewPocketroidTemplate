package org.jetbrains.kotlin.backend.common.serialization;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.library.IrLibrary;

/* JADX INFO: compiled from: IrFileDeserializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005H\u0016J\u0012\u0010\n\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\u0005H\u0016J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005H\u0016J\u0010\u0010\f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005H\u0016J\u0010\u0010\r\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005H\u0016J\u0010\u0010\u000e\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/IrKlibBytesSource;", "Lorg/jetbrains/kotlin/backend/common/serialization/IrLibraryBytesSource;", "klib", "Lorg/jetbrains/kotlin/library/IrLibrary;", "fileIndex", "", "(Lorg/jetbrains/kotlin/library/IrLibrary;I)V", "body", "", "index", "debugInfo", "irDeclaration", "signature", "string", ModuleXmlParser.TYPE, "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrKlibBytesSource extends IrLibraryBytesSource {
    private final int fileIndex;
    private final IrLibrary klib;

    public IrKlibBytesSource(IrLibrary klib, int i) {
        Intrinsics.checkNotNullParameter(klib, "klib");
        this.klib = klib;
        this.fileIndex = i;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrLibraryBytesSource
    public byte[] irDeclaration(int index) {
        return this.klib.irDeclaration(index, this.fileIndex);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrLibraryBytesSource
    public byte[] type(int index) {
        return this.klib.type(index, this.fileIndex);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrLibraryBytesSource
    public byte[] signature(int index) {
        return this.klib.signature(index, this.fileIndex);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrLibraryBytesSource
    public byte[] string(int index) {
        return this.klib.string(index, this.fileIndex);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrLibraryBytesSource
    public byte[] body(int index) {
        return this.klib.body(index, this.fileIndex);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrLibraryBytesSource
    public byte[] debugInfo(int index) {
        return this.klib.debugInfo(index, this.fileIndex);
    }
}
