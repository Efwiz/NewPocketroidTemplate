package org.jetbrains.kotlin.backend.common.psi;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.ir.PsiIrFileEntry;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: PsiSourceManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ5\u0010\u0003\u001a\u0004\u0018\u0001H\t\"\b\b\u0000\u0010\t*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\u000b¢\u0006\u0002\u0010\fJ\u0018\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000eJ5\u0010\u0003\u001a\u0004\u0018\u0001H\t\"\b\b\u0000\u0010\t*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\u000b¢\u0006\u0002\u0010\u000fJ\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0007\u001a\u00020\bJ-\u0010\u0003\u001a\u0004\u0018\u0001H\t\"\b\b\u0000\u0010\t*\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\u000b¢\u0006\u0002\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/psi/PsiSourceManager;", "", "()V", "findPsiElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "irElement", "Lorg/jetbrains/kotlin/ir/IrElement;", "irDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", ExifInterface.LONGITUDE_EAST, "psiElementClass", "Lkotlin/reflect/KClass;", "(Lorg/jetbrains/kotlin/ir/IrElement;Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;Lkotlin/reflect/KClass;)Lcom/intellij/psi/PsiElement;", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "(Lorg/jetbrains/kotlin/ir/IrElement;Lorg/jetbrains/kotlin/ir/declarations/IrFile;Lkotlin/reflect/KClass;)Lcom/intellij/psi/PsiElement;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;Lkotlin/reflect/KClass;)Lcom/intellij/psi/PsiElement;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PsiSourceManager {
    public static final PsiSourceManager INSTANCE = new PsiSourceManager();

    private PsiSourceManager() {
    }

    public final <E extends PsiElement> E findPsiElement(IrElement irElement, IrFile irFile, KClass<E> psiElementClass) {
        Intrinsics.checkNotNullParameter(irElement, "irElement");
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        Intrinsics.checkNotNullParameter(psiElementClass, "psiElementClass");
        IrFileEntry fileEntry = irFile.getFileEntry();
        PsiIrFileEntry psiIrFileEntry = fileEntry instanceof PsiIrFileEntry ? (PsiIrFileEntry) fileEntry : null;
        if (psiIrFileEntry == null) {
            return null;
        }
        return (E) psiIrFileEntry.findPsiElement(irElement, psiElementClass);
    }

    public final PsiElement findPsiElement(IrElement irElement, IrFile irFile) {
        Intrinsics.checkNotNullParameter(irElement, "irElement");
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        IrFileEntry fileEntry = irFile.getFileEntry();
        PsiIrFileEntry psiIrFileEntry = fileEntry instanceof PsiIrFileEntry ? (PsiIrFileEntry) fileEntry : null;
        if (psiIrFileEntry == null) {
            return null;
        }
        return psiIrFileEntry.findPsiElement(irElement);
    }

    public final <E extends PsiElement> E findPsiElement(IrElement irElement, IrDeclaration irDeclaration, KClass<E> psiElementClass) {
        Intrinsics.checkNotNullParameter(irElement, "irElement");
        Intrinsics.checkNotNullParameter(irDeclaration, "irDeclaration");
        Intrinsics.checkNotNullParameter(psiElementClass, "psiElementClass");
        IrFile fileOrNull = IrUtilsKt.getFileOrNull(irDeclaration);
        if (fileOrNull == null) {
            return null;
        }
        return (E) findPsiElement(irElement, fileOrNull, psiElementClass);
    }

    public final PsiElement findPsiElement(IrElement irElement, IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irElement, "irElement");
        Intrinsics.checkNotNullParameter(irDeclaration, "irDeclaration");
        IrFile fileOrNull = IrUtilsKt.getFileOrNull(irDeclaration);
        if (fileOrNull == null) {
            return null;
        }
        return findPsiElement(irElement, fileOrNull);
    }

    public final <E extends PsiElement> E findPsiElement(IrDeclaration irDeclaration, KClass<E> psiElementClass) {
        Intrinsics.checkNotNullParameter(irDeclaration, "irDeclaration");
        Intrinsics.checkNotNullParameter(psiElementClass, "psiElementClass");
        return (E) findPsiElement(irDeclaration, irDeclaration, psiElementClass);
    }

    public final PsiElement findPsiElement(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "irDeclaration");
        return findPsiElement(irDeclaration, irDeclaration);
    }
}
