package org.jetbrains.kotlin.ir;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;

/* JADX INFO: compiled from: PsiIrFileEntry.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u0019J-\u0010\u0016\u001a\u0004\u0018\u0001H\u001a\"\b\b\u0000\u0010\u001a*\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u001a0\u001c¢\u0006\u0002\u0010\u001dJ\u0010\u0010\u001e\u001a\u00020\u000b2\u0006\u0010\u001f\u001a\u00020\u000bH\u0016J\u0010\u0010 \u001a\u00020\u000b2\u0006\u0010\u001f\u001a\u00020\u000bH\u0016J\u0006\u0010!\u001a\u00020\tJ\b\u0010\"\u001a\u00020\u000fH\u0002J\u0018\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u000b2\u0006\u0010&\u001a\u00020\u000bH\u0016J\b\u0010'\u001a\u00020\u000fH\u0016R\u0013\u0010\u0005\u001a\u00070\u0006¢\u0006\u0002\b\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0018\u0010\u0014\u001a\f0\u000f¢\u0006\u0002\b\u0015¢\u0006\u0002\b\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/ir/PsiIrFileEntry;", "Lorg/jetbrains/kotlin/ir/IrFileEntry;", "psiFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "(Lcom/intellij/psi/PsiFile;)V", "fileViewProvider", "Lorg/jetbrains/kotlin/com/intellij/psi/FileViewProvider;", "Lorg/jetbrains/annotations/NotNull;", "lineStartOffsets", "", "maxOffset", "", "getMaxOffset", "()I", "name", "", "getName", "()Ljava/lang/String;", "getPsiFile", "()Lcom/intellij/psi/PsiFile;", "psiFileName", "Lorg/jetbrains/annotations/NonNls;", "findPsiElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "irElement", "Lorg/jetbrains/kotlin/ir/IrElement;", ExifInterface.LONGITUDE_EAST, "psiElementClass", "Lkotlin/reflect/KClass;", "(Lorg/jetbrains/kotlin/ir/IrElement;Lkotlin/reflect/KClass;)Lcom/intellij/psi/PsiElement;", "getColumnNumber", TypedValues.CycleType.S_WAVE_OFFSET, "getLineNumber", "getLineOffsets", "getRecognizableName", "getSourceRangeInfo", "Lorg/jetbrains/kotlin/ir/SourceRangeInfo;", "beginOffset", "endOffset", "toString", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PsiIrFileEntry implements IrFileEntry {
    private final FileViewProvider fileViewProvider;
    private final int[] lineStartOffsets;
    private final int maxOffset;
    private final PsiFile psiFile;
    private final String psiFileName;

    public PsiIrFileEntry(PsiFile psiFile) {
        Intrinsics.checkNotNullParameter(psiFile, "psiFile");
        this.psiFile = psiFile;
        VirtualFile virtualFile = psiFile.getVirtualFile();
        String name2 = (virtualFile == null || (name2 = virtualFile.getPath()) == null) ? psiFile.getName() : name2;
        Intrinsics.checkNotNull(name2);
        this.psiFileName = name2;
        FileViewProvider viewProvider = psiFile.getViewProvider();
        Intrinsics.checkNotNullExpressionValue(viewProvider, "getViewProvider(...)");
        this.fileViewProvider = viewProvider;
        Document document = viewProvider.getDocument();
        if (document == null) {
            throw new AssertionError("No document for " + psiFile);
        }
        this.maxOffset = document.getTextLength();
        int lineCount = document.getLineCount();
        int[] iArr = new int[lineCount];
        for (int i = 0; i < lineCount; i++) {
            iArr[i] = document.getLineStartOffset(i);
        }
        this.lineStartOffsets = iArr;
    }

    public final PsiFile getPsiFile() {
        return this.psiFile;
    }

    @Override // org.jetbrains.kotlin.ir.IrFileEntry
    public boolean getSupportsDebugInfo() {
        return IrFileEntry.DefaultImpls.getSupportsDebugInfo(this);
    }

    @Override // org.jetbrains.kotlin.ir.IrFileEntry
    public int getMaxOffset() {
        return this.maxOffset;
    }

    @Override // org.jetbrains.kotlin.ir.IrFileEntry
    public int getLineNumber(int offset) {
        if (offset < 0) {
            return -1;
        }
        int iBinarySearch$default = ArraysKt.binarySearch$default(this.lineStartOffsets, offset, 0, 0, 6, (Object) null);
        return iBinarySearch$default >= 0 ? iBinarySearch$default : (-iBinarySearch$default) - 2;
    }

    @Override // org.jetbrains.kotlin.ir.IrFileEntry
    public int getColumnNumber(int offset) {
        int lineNumber;
        if (offset >= 0 && (lineNumber = getLineNumber(offset)) >= 0) {
            return offset - this.lineStartOffsets[lineNumber];
        }
        return -1;
    }

    @Override // org.jetbrains.kotlin.ir.IrFileEntry
    public SourceRangeInfo getSourceRangeInfo(int beginOffset, int endOffset) {
        return new SourceRangeInfo(getPsiFileName(), beginOffset, getLineNumber(beginOffset), getColumnNumber(beginOffset), endOffset, getLineNumber(endOffset), getColumnNumber(endOffset));
    }

    /* JADX INFO: renamed from: getRecognizableName, reason: from getter */
    private final String getPsiFileName() {
        return this.psiFileName;
    }

    @Override // org.jetbrains.kotlin.ir.IrFileEntry
    public String getName() {
        return getPsiFileName();
    }

    public String toString() {
        return getPsiFileName();
    }

    public final int[] getLineOffsets() {
        int[] iArr = this.lineStartOffsets;
        int[] iArrCopyOf = Arrays.copyOf(iArr, iArr.length);
        Intrinsics.checkNotNullExpressionValue(iArrCopyOf, "copyOf(...)");
        return iArrCopyOf;
    }

    public final PsiElement findPsiElement(IrElement irElement) {
        Intrinsics.checkNotNullParameter(irElement, "irElement");
        PsiElement psiElementFindElementAt = this.fileViewProvider.findElementAt(irElement.getStartOffset());
        while (psiElementFindElementAt != null) {
            TextRange textRange = psiElementFindElementAt.getTextRange();
            if (textRange != null && irElement.getEndOffset() == textRange.getEndOffset()) {
                break;
            }
            psiElementFindElementAt = psiElementFindElementAt.getLightParent();
        }
        return psiElementFindElementAt;
    }

    public final <E extends PsiElement> E findPsiElement(IrElement irElement, KClass<E> psiElementClass) {
        Intrinsics.checkNotNullParameter(irElement, "irElement");
        Intrinsics.checkNotNullParameter(psiElementClass, "psiElementClass");
        PsiElement psiElementFindPsiElement = findPsiElement(irElement);
        if (psiElementFindPsiElement != null) {
            return (E) PsiTreeUtil.getParentOfType(psiElementFindPsiElement, JvmClassMappingKt.getJavaClass((KClass) psiElementClass), false);
        }
        return null;
    }
}
