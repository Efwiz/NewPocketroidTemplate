package org.jetbrains.kotlin.backend.common.linkage.partial;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;

/* JADX INFO: compiled from: PartialLinkageUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b \u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J)\u0010\t\u001a\u0002H\n\"\u0004\b\u0000\u0010\n2\u0006\u0010\u000b\u001a\u00020\u00032\f\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\n0\rH\u0004¢\u0006\u0002\u0010\u000eJ\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/FileAwareIrElementTransformerVoid;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "startingFile", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File;", "(Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File;)V", "_currentFile", "currentFile", "getCurrentFile", "()Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File;", "runInFile", ExifInterface.GPS_DIRECTION_TRUE, "file", "block", "Lkotlin/Function0;", "(Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "visitFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "declaration", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FileAwareIrElementTransformerVoid extends IrElementTransformerVoid {
    private org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.File _currentFile;

    public final org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.File getCurrentFile() {
        org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.File file = this._currentFile;
        if (file != null) {
            return file;
        }
        throw new IllegalStateException("No information about current file".toString());
    }

    public FileAwareIrElementTransformerVoid(org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.File file) {
        this._currentFile = file;
    }

    protected final <T> T runInFile(org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.File file, Function0<? extends T> block) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(block, "block");
        org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.File file2 = this._currentFile;
        this._currentFile = file;
        try {
            return block.invoke();
        } finally {
            this._currentFile = file2;
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrFile visitFile(final IrFile declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return (IrFile) runInFile(new org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.File.IrBased(declaration), new Function0<IrFile>() { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.FileAwareIrElementTransformerVoid.visitFile.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrFile invoke() {
                return FileAwareIrElementTransformerVoid.super.visitFile(declaration);
            }
        });
    }
}
