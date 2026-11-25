package org.jetbrains.kotlin.com.intellij.psi.impl;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentEvent;
import org.jetbrains.kotlin.com.intellij.openapi.util.ProperTextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.FileElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ForeignLeafPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class ChangedPsiRangeUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "text";
                break;
            case 2:
            case 5:
                objArr[0] = "file";
                break;
            case 3:
            default:
                objArr[0] = "treeElement";
                break;
            case 4:
            case 8:
                objArr[0] = "newDocumentText";
                break;
            case 6:
                objArr[0] = "document";
                break;
            case 7:
                objArr[0] = "oldDocumentText";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/ChangedPsiRangeUtil";
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                objArr[2] = "getChangedPsiRange";
                break;
            default:
                objArr[2] = "getMatchingLength";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private static int getLeafMatchingLength(CharSequence charSequence, CharSequence charSequence2, int i, int i2, int i3) {
        int i4 = 0;
        int length = i3 == 1 ? 0 : charSequence.length() - 1;
        int length2 = i3 == 1 ? charSequence.length() - 1 : 0;
        while (charSequence.charAt(length) == charSequence2.charAt(i)) {
            i4++;
            if (length == length2 || i == i2) {
                break;
            }
            length += i3;
            i += i3;
        }
        return i4;
    }

    /* JADX WARN: Code duplicated, block: B:26:0x0037  */
    /* JADX WARN: Code duplicated, block: B:28:0x003b  */
    /* JADX WARN: Code duplicated, block: B:30:0x0045  */
    /* JADX WARN: Code duplicated, block: B:33:0x0051 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:35:0x0054  */
    /* JADX WARN: Code duplicated, block: B:41:0x005d A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:45:0x0062 A[EDGE_INSN: B:45:0x0062->B:40:0x0062 BREAK  A[LOOP:0: B:21:0x002e->B:47:0x002e], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:46:0x0058 A[SYNTHETIC] */
    private static int getMatchingLength(FileElement fileElement, CharSequence charSequence, boolean z) {
        CharSequence chars;
        int leafMatchingLength;
        if (fileElement == null) {
            $$$reportNull$$$0(0);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(1);
        }
        int length = z ? 0 : charSequence.length() - 1;
        int length2 = z ? charSequence.length() - 1 : 0;
        int i = z ? 1 : -1;
        ASTNode aSTNodeFindFirstLeaf = z ? TreeUtil.findFirstLeaf(fileElement, false) : TreeUtil.findLastLeaf(fileElement, false);
        int i2 = 0;
        while (aSTNodeFindFirstLeaf != null) {
            if (!z) {
                if (length < length2) {
                    break;
                }
                if (!(aSTNodeFindFirstLeaf instanceof ForeignLeafPsiElement)) {
                    chars = aSTNodeFindFirstLeaf.getChars();
                    if (chars.length() > 0) {
                        leafMatchingLength = getLeafMatchingLength(chars, charSequence, length, length2, i);
                        i2 += leafMatchingLength;
                        if (leafMatchingLength != chars.length()) {
                            break;
                            break;
                        }
                        if (!z) {
                            leafMatchingLength = -leafMatchingLength;
                        }
                        length += leafMatchingLength;
                    }
                }
                if (z) {
                }
            } else {
                if (length > length2) {
                    break;
                }
                if (!(aSTNodeFindFirstLeaf instanceof ForeignLeafPsiElement)) {
                    chars = aSTNodeFindFirstLeaf.getChars();
                    if (chars.length() > 0) {
                        leafMatchingLength = getLeafMatchingLength(chars, charSequence, length, length2, i);
                        i2 += leafMatchingLength;
                        if (leafMatchingLength != chars.length()) {
                            break;
                        }
                        if (!z) {
                            leafMatchingLength = -leafMatchingLength;
                        }
                        length += leafMatchingLength;
                    }
                }
                aSTNodeFindFirstLeaf = z ? TreeUtil.nextLeaf(aSTNodeFindFirstLeaf, false) : TreeUtil.prevLeaf(aSTNodeFindFirstLeaf, false);
            }
        }
        return i2;
    }

    public static TextRange getChangedPsiRange(PsiFile psiFile, FileElement fileElement, CharSequence charSequence) {
        if (psiFile == null) {
            $$$reportNull$$$0(2);
        }
        if (fileElement == null) {
            $$$reportNull$$$0(3);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(4);
        }
        int textLength = fileElement.getTextLength();
        if (!psiFile.getViewProvider().supportsIncrementalReparse(psiFile.getLanguage())) {
            return new TextRange(0, textLength);
        }
        int matchingLength = getMatchingLength(fileElement, charSequence, true);
        if (matchingLength == charSequence.length() && charSequence.length() == textLength) {
            return null;
        }
        return new TextRange(matchingLength, textLength - Math.min(getMatchingLength(fileElement, charSequence, false), textLength - matchingLength));
    }

    static ProperTextRange getChangedPsiRange(PsiFile psiFile, Document document, CharSequence charSequence, CharSequence charSequence2) {
        if (psiFile == null) {
            $$$reportNull$$$0(5);
        }
        if (document == null) {
            $$$reportNull$$$0(6);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(7);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(8);
        }
        int length = charSequence.length();
        if (!psiFile.getViewProvider().supportsIncrementalReparse(psiFile.getLanguage())) {
            return new ProperTextRange(0, length);
        }
        int iMin = Integer.MAX_VALUE;
        int iMin2 = Integer.MAX_VALUE;
        int oldLength = length;
        for (DocumentEvent documentEvent : ((PsiDocumentManagerBase) PsiDocumentManager.getInstance(psiFile.getProject())).getEventsSinceCommit(document)) {
            iMin = Math.min(iMin, documentEvent.getOffset());
            iMin2 = Math.min(iMin2, (oldLength - documentEvent.getOffset()) - documentEvent.getOldLength());
            oldLength = (oldLength - documentEvent.getOldLength()) + documentEvent.getNewLength();
        }
        if ((iMin == length || iMin2 == length) && charSequence2.length() == length) {
            return null;
        }
        int iMin3 = Math.min(length, charSequence2.length());
        while (iMin < iMin3 && charSequence.charAt(iMin) == charSequence2.charAt(iMin)) {
            iMin++;
        }
        while (iMin2 < iMin3 - iMin && charSequence.charAt((length - iMin2) - 1) == charSequence2.charAt((charSequence2.length() - iMin2) - 1)) {
            iMin2++;
        }
        int iMax = Math.max(iMin, length - iMin2);
        if (iMax == iMin && charSequence2.length() == charSequence.length()) {
            return null;
        }
        return ProperTextRange.create(iMin, iMax);
    }
}
