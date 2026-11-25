package org.jetbrains.kotlin.diagnostics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.diagnostics.Diagnostics;

/* JADX INFO: loaded from: classes4.dex */
public class DiagnosticUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final Comparator<TextRange> TEXT_RANGE_COMPARATOR = new Comparator() { // from class: org.jetbrains.kotlin.diagnostics.DiagnosticUtils$$ExternalSyntheticLambda1
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return DiagnosticUtils.lambda$static$0((TextRange) obj, (TextRange) obj2);
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 6 || i == 8 || i == 10 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 6 || i == 8 || i == 10 || i == 3 || i == 4) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 6:
            case 8:
            case 10:
                objArr[0] = "org/jetbrains/kotlin/diagnostics/DiagnosticUtils";
                break;
            case 2:
            case 5:
                objArr[0] = "diagnostic";
                break;
            case 7:
                objArr[0] = "ranges";
                break;
            case 9:
                objArr[0] = "diagnostics";
                break;
            default:
                objArr[0] = "node";
                break;
        }
        if (i == 1) {
            objArr[1] = "getContainingFile";
        } else if (i == 6) {
            objArr[1] = "getLineAndColumnRange";
        } else if (i == 8) {
            objArr[1] = "firstRange";
        } else if (i == 10) {
            objArr[1] = "sortedDiagnostics";
        } else if (i == 3) {
            objArr[1] = "getLineAndColumn";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/diagnostics/DiagnosticUtils";
        } else {
            objArr[1] = "getLineAndColumnInPsiFile";
        }
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 6:
            case 8:
            case 10:
                break;
            case 2:
                objArr[2] = "getLineAndColumn";
                break;
            case 5:
                objArr[2] = "getLineAndColumnRange";
                break;
            case 7:
                objArr[2] = "firstRange";
                break;
            case 9:
                objArr[2] = "sortedDiagnostics";
                break;
            default:
                objArr[2] = "getContainingFile";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 6 && i != 8 && i != 10 && i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ int lambda$static$0(TextRange textRange, TextRange textRange2) {
        int endOffset;
        int endOffset2;
        if (textRange.getStartOffset() != textRange2.getStartOffset()) {
            endOffset = textRange.getStartOffset();
            endOffset2 = textRange2.getStartOffset();
        } else {
            endOffset = textRange.getEndOffset();
            endOffset2 = textRange2.getEndOffset();
        }
        return endOffset - endOffset2;
    }

    private DiagnosticUtils() {
    }

    public static String atLocation(DeclarationDescriptor declarationDescriptor) {
        PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(declarationDescriptor);
        if (psiElementDescriptorToDeclaration == null) {
            psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(declarationDescriptor.getOriginal());
        }
        if (psiElementDescriptorToDeclaration == null && (declarationDescriptor instanceof ASTNode)) {
            psiElementDescriptorToDeclaration = PsiUtilsKt.closestPsiElement((ASTNode) declarationDescriptor);
        }
        if (psiElementDescriptorToDeclaration != null) {
            return PsiDiagnosticUtils.atLocation(psiElementDescriptorToDeclaration);
        }
        return "unknown location";
    }

    public static PsiFile getContainingFile(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        PsiFile containingFile = PsiUtilsKt.closestPsiElement(aSTNode).getContainingFile();
        if (containingFile == null) {
            $$$reportNull$$$0(1);
        }
        return containingFile;
    }

    public static PsiDiagnosticUtils.LineAndColumn getLineAndColumn(Diagnostic diagnostic) {
        if (diagnostic == null) {
            $$$reportNull$$$0(2);
        }
        PsiFile psiFile = diagnostic.getPsiFile();
        List<TextRange> textRanges = diagnostic.getTextRanges();
        if (!textRanges.isEmpty()) {
            return getLineAndColumnInPsiFile(psiFile, firstRange(textRanges));
        }
        PsiDiagnosticUtils.LineAndColumn lineAndColumn = PsiDiagnosticUtils.LineAndColumn.NONE;
        if (lineAndColumn == null) {
            $$$reportNull$$$0(3);
        }
        return lineAndColumn;
    }

    public static PsiDiagnosticUtils.LineAndColumn getLineAndColumnInPsiFile(PsiFile psiFile, TextRange textRange) {
        PsiDiagnosticUtils.LineAndColumn lineAndColumnOffsetToLineAndColumn = PsiDiagnosticUtils.offsetToLineAndColumn(psiFile.getViewProvider().getDocument(), textRange.getStartOffset());
        if (lineAndColumnOffsetToLineAndColumn == null) {
            $$$reportNull$$$0(4);
        }
        return lineAndColumnOffsetToLineAndColumn;
    }

    public static PsiDiagnosticUtils.LineAndColumnRange getLineAndColumnRange(Diagnostic diagnostic) {
        if (diagnostic == null) {
            $$$reportNull$$$0(5);
        }
        return getLineAndColumnRange(diagnostic.getPsiFile(), diagnostic.getTextRanges());
    }

    public static PsiDiagnosticUtils.LineAndColumnRange getLineAndColumnRange(PsiFile psiFile, List<TextRange> list) {
        if (!list.isEmpty()) {
            return getLineAndColumnRangeInPsiFile(psiFile, firstRange(list));
        }
        PsiDiagnosticUtils.LineAndColumnRange lineAndColumnRange = PsiDiagnosticUtils.LineAndColumnRange.NONE;
        if (lineAndColumnRange == null) {
            $$$reportNull$$$0(6);
        }
        return lineAndColumnRange;
    }

    public static PsiDiagnosticUtils.LineAndColumnRange getLineAndColumnRangeInPsiFile(PsiFile psiFile, TextRange textRange) {
        Document document = psiFile.getViewProvider().getDocument();
        return new PsiDiagnosticUtils.LineAndColumnRange(PsiDiagnosticUtils.offsetToLineAndColumn(document, textRange.getStartOffset()), PsiDiagnosticUtils.offsetToLineAndColumn(document, textRange.getEndOffset()));
    }

    public static void throwIfRunningOnServer(Throwable th) {
        if (System.getProperty("kotlin.running.in.server.mode", "false").equals("true") || ApplicationManager.getApplication().isUnitTestMode()) {
            if (th instanceof RuntimeException) {
                throw ((RuntimeException) th);
            }
            if (th instanceof Error) {
                throw ((Error) th);
            }
            throw new RuntimeException(th);
        }
    }

    public static TextRange firstRange(List<TextRange> list) {
        if (list == null) {
            $$$reportNull$$$0(7);
        }
        TextRange textRange = (TextRange) Collections.min(list, TEXT_RANGE_COMPARATOR);
        if (textRange == null) {
            $$$reportNull$$$0(8);
        }
        return textRange;
    }

    public static List<Diagnostic> sortedDiagnostics(Collection<Diagnostic> collection) {
        if (collection == null) {
            $$$reportNull$$$0(9);
        }
        ArrayList arrayListNewArrayList = Lists.newArrayList(collection);
        arrayListNewArrayList.sort(new Comparator() { // from class: org.jetbrains.kotlin.diagnostics.DiagnosticUtils$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return DiagnosticUtils.lambda$sortedDiagnostics$1((Diagnostic) obj, (Diagnostic) obj2);
            }
        });
        if (arrayListNewArrayList == null) {
            $$$reportNull$$$0(10);
        }
        return arrayListNewArrayList;
    }

    static /* synthetic */ int lambda$sortedDiagnostics$1(Diagnostic diagnostic, Diagnostic diagnostic2) {
        PsiFile psiFile = diagnostic.getPsiFile();
        PsiFile psiFile2 = diagnostic2.getPsiFile();
        String path = psiFile.getViewProvider().getVirtualFile().getPath();
        String path2 = psiFile2.getViewProvider().getVirtualFile().getPath();
        if (!path.equals(path2)) {
            return path.compareTo(path2);
        }
        TextRange textRangeFirstRange = firstRange(diagnostic.getTextRanges());
        TextRange textRangeFirstRange2 = firstRange(diagnostic2.getTextRanges());
        if (!textRangeFirstRange.equals(textRangeFirstRange2)) {
            return TEXT_RANGE_COMPARATOR.compare(textRangeFirstRange, textRangeFirstRange2);
        }
        return diagnostic.getFactory().getName().compareTo(diagnostic2.getFactory().getName());
    }

    public static boolean hasError(Diagnostics diagnostics) {
        Iterator<Diagnostic> it = diagnostics.all().iterator();
        while (it.hasNext()) {
            if (it.next().getSeverity() == Severity.ERROR) {
                return true;
            }
        }
        return false;
    }
}
