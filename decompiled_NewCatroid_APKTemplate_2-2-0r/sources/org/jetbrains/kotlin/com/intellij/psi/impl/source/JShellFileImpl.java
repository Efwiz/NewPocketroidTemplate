package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import java.util.Collection;
import org.jetbrains.kotlin.com.intellij.ide.highlighter.JShellFileType;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.java.JShellLanguage;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiComment;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiEmptyStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJShellFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJShellHolderMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiJShellRootClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JShellElementType;
import org.jetbrains.kotlin.com.intellij.util.SmartList;

/* JADX INFO: loaded from: classes3.dex */
public class JShellFileImpl extends PsiJavaFileBaseImpl implements PsiJShellFile {
    private static final Condition<PsiElement> EXECUTABLE_PREDICATE = new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.JShellFileImpl$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
        public final boolean value(Object obj) {
            return JShellFileImpl.lambda$static$0((PsiElement) obj);
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[2];
        objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/JShellFileImpl";
        if (i != 1) {
            objArr[1] = "getLanguage";
        } else {
            objArr[1] = "getFileType";
        }
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
    }

    public JShellFileImpl(FileViewProvider fileViewProvider) {
        super(JShellElementType.FILE, JShellElementType.FILE, fileViewProvider);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJavaFileBaseImpl, org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Language getLanguage() {
        JShellLanguage jShellLanguage = JShellLanguage.INSTANCE;
        if (jShellLanguage == null) {
            $$$reportNull$$$0(0);
        }
        return jShellLanguage;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile
    public FileType getFileType() {
        JShellFileType jShellFileType = JShellFileType.INSTANCE;
        if (jShellFileType == null) {
            $$$reportNull$$$0(1);
        }
        return jShellFileType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: isPhysical */
    public boolean getIsPhysical() {
        return getViewProvider().isPhysical();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJShellFile
    public Collection<PsiElement> getExecutableSnippets() {
        SmartList smartList = new SmartList();
        collectExecutableSnippets(this, smartList);
        return smartList;
    }

    private static void collectExecutableSnippets(PsiElement psiElement, Collection<? super PsiElement> collection) {
        for (PsiElement firstChild = psiElement.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (firstChild instanceof PsiJShellRootClass) {
                collectExecutableSnippets(firstChild, collection);
            } else if (isExecutable(firstChild)) {
                collection.add(firstChild);
            }
        }
    }

    static /* synthetic */ boolean lambda$static$0(PsiElement psiElement) {
        return (psiElement == null || (psiElement instanceof PsiWhiteSpace) || (psiElement instanceof PsiEmptyStatement) || (psiElement instanceof PsiComment)) ? false : true;
    }

    private static boolean isExecutable(PsiElement psiElement) {
        if (psiElement instanceof PsiJShellHolderMethod) {
            for (PsiElement firstChild = psiElement.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
                if (EXECUTABLE_PREDICATE.value(firstChild)) {
                    return true;
                }
            }
            return false;
        }
        return EXECUTABLE_PREDICATE.value(psiElement);
    }
}
