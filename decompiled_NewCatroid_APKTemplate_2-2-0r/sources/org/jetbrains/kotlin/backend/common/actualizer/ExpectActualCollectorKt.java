package org.jetbrains.kotlin.backend.common.actualizer;

import java.io.File;
import java.nio.file.Path;
import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.ir.PsiIrFileEntry;
import org.jetbrains.kotlin.ir.declarations.IrFile;

/* JADX INFO: compiled from: ExpectActualCollector.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u0002¨\u0006\u0003"}, d2 = {"toIoFile", "Ljava/io/File;", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "ir.backend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ExpectActualCollectorKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final File toIoFile(IrFile irFile) {
        IrFileEntry fileEntry = irFile.getFileEntry();
        if (!(fileEntry instanceof PsiIrFileEntry)) {
            File file = new File(irFile.getFileEntry().getName());
            if (file.exists()) {
                return file;
            }
            return null;
        }
        VirtualFile virtualFile = ((PsiIrFileEntry) fileEntry).getPsiFile().getVirtualFile();
        Path nioPath = virtualFile.getFileSystem().getNioPath(virtualFile);
        if (nioPath != null) {
            return nioPath.toFile();
        }
        return null;
    }
}
