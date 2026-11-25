package org.jetbrains.kotlin.library.impl;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.library.IrKotlinLibraryLayout;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;
import org.jetbrains.kotlin.library.SerializedIrFile;
import org.jetbrains.kotlin.library.SerializedIrModule;

/* JADX INFO: compiled from: IrWriterImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0002¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/library/impl/IrPerFileWriterImpl;", "Lorg/jetbrains/kotlin/library/impl/IrWriterImpl;", "_irLayout", "Lorg/jetbrains/kotlin/library/IrKotlinLibraryLayout;", "(Lorg/jetbrains/kotlin/library/IrKotlinLibraryLayout;)V", "addIr", "", KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME, "Lorg/jetbrains/kotlin/library/SerializedIrModule;", "serializeFile", "file", "Lorg/jetbrains/kotlin/library/SerializedIrFile;", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class IrPerFileWriterImpl extends IrWriterImpl {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrPerFileWriterImpl(IrKotlinLibraryLayout _irLayout) {
        super(_irLayout);
        Intrinsics.checkNotNullParameter(_irLayout, "_irLayout");
    }

    @Override // org.jetbrains.kotlin.library.IrWriter
    public void addIr(SerializedIrModule ir) {
        Intrinsics.checkNotNullParameter(ir, "ir");
        getIrLayout().getIrDir().mkdirs();
        Iterator<T> it = ir.getFiles().iterator();
        while (it.hasNext()) {
            serializeFile((SerializedIrFile) it.next());
        }
    }

    private final void serializeFile(SerializedIrFile file) {
        String fqName = file.getFqName();
        String string = Integer.toString(file.getPath().hashCode(), CharsKt.checkRadix(36));
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        File fileChild = getIrLayout().getIrDir().child(fqName + '.' + string + ".file");
        fileChild.getExists();
        fileChild.mkdirs();
        getIrLayout().irFile(fileChild).writeBytes(file.getFileData());
        getIrLayout().irDeclarations(fileChild).writeBytes(file.getDeclarations());
        getIrLayout().irTypes(fileChild).writeBytes(file.getTypes());
        getIrLayout().irSignatures(fileChild).writeBytes(file.getSignatures());
        getIrLayout().irStrings(fileChild).writeBytes(file.getStrings());
        getIrLayout().irBodies(fileChild).writeBytes(file.getBodies());
    }
}
