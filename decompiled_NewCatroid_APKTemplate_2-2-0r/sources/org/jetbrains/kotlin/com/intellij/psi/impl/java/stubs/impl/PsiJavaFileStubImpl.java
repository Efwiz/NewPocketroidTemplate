package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl;

import org.jetbrains.kotlin.com.intellij.lang.java.JavaParserDefinition;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.ClsStubPsiFactory;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiJavaFileStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.SourceStubPsiFactory;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.StubPsiFactory;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType;

/* JADX INFO: loaded from: classes3.dex */
public class PsiJavaFileStubImpl extends PsiFileStubImpl<PsiJavaFile> implements PsiJavaFileStub {
    private final boolean myCompiled;
    private StubPsiFactory myFactory;
    private final LanguageLevel myLanguageLevel;
    private final String myPackageName;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[2];
        objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/impl/PsiJavaFileStubImpl";
        if (i != 1) {
            objArr[1] = "getType";
        } else {
            objArr[1] = "getClasses";
        }
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
    }

    public PsiJavaFileStubImpl(String str, boolean z) {
        this(null, str, null, z);
    }

    public PsiJavaFileStubImpl(PsiJavaFile psiJavaFile, String str, LanguageLevel languageLevel, boolean z) {
        super(psiJavaFile);
        this.myPackageName = str;
        this.myLanguageLevel = languageLevel;
        this.myCompiled = z;
        this.myFactory = z ? ClsStubPsiFactory.INSTANCE : SourceStubPsiFactory.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStubImpl, org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStub
    public IStubFileElementType getType() {
        IStubFileElementType iStubFileElementType = JavaParserDefinition.JAVA_FILE;
        if (iStubFileElementType == null) {
            $$$reportNull$$$0(0);
        }
        return iStubFileElementType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.PsiClassHolderFileStub
    public PsiClass[] getClasses() {
        PsiClass[] psiClassArr = (PsiClass[]) getChildrenByType(JavaStubElementTypes.CLASS, PsiClass.ARRAY_FACTORY);
        if (psiClassArr == null) {
            $$$reportNull$$$0(1);
        }
        return psiClassArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiJavaFileStub
    public PsiJavaModule getModule() {
        StubElement stubElementFindChildStubByType = findChildStubByType(JavaStubElementTypes.MODULE);
        if (stubElementFindChildStubByType != null) {
            return (PsiJavaModule) stubElementFindChildStubByType.getPsi();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiJavaFileStub
    public String getPackageName() {
        return this.myPackageName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiJavaFileStub
    public LanguageLevel getLanguageLevel() {
        return this.myLanguageLevel;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiJavaFileStub
    public boolean isCompiled() {
        return this.myCompiled;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiJavaFileStub
    public StubPsiFactory getPsiFactory() {
        return this.myFactory;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiJavaFileStub
    public void setPsiFactory(StubPsiFactory stubPsiFactory) {
        this.myFactory = stubPsiFactory;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PsiJavaFileStubImpl psiJavaFileStubImpl = (PsiJavaFileStubImpl) obj;
        if (this.myCompiled != psiJavaFileStubImpl.myCompiled) {
            return false;
        }
        String str = this.myPackageName;
        if (str == null ? psiJavaFileStubImpl.myPackageName == null : str.equals(psiJavaFileStubImpl.myPackageName)) {
            return this.myLanguageLevel == psiJavaFileStubImpl.myLanguageLevel;
        }
        return false;
    }

    public int hashCode() {
        String str = this.myPackageName;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        LanguageLevel languageLevel = this.myLanguageLevel;
        return ((iHashCode + (languageLevel != null ? languageLevel.hashCode() : 0)) * 31) + (this.myCompiled ? 1 : 0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase, java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "PsiJavaFileStub [" + this.myPackageName + "]";
    }
}
