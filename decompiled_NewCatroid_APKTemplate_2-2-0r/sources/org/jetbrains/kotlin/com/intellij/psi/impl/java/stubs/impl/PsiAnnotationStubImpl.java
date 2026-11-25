package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl;

import org.jetbrains.kotlin.com.intellij.codeInsight.AnnotationUtil;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementFactoryImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiAnnotationStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.CharTableImpl;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;
import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class PsiAnnotationStubImpl extends StubBase<PsiAnnotation> implements PsiAnnotationStub {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiAnnotationStubImpl.class);
    private SoftReference<PsiAnnotation> myParsedFromRepository;
    private final String myText;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "text", "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/impl/PsiAnnotationStubImpl", "<init>"));
    }

    static {
        CharTableImpl.addStringsFromClassToStatics(AnnotationUtil.class);
        CharTableImpl.staticIntern("@NotNull");
        CharTableImpl.staticIntern("@Nullable");
        CharTableImpl.staticIntern("@Override");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiAnnotationStubImpl(StubElement stubElement, String str) {
        super(stubElement, JavaStubElementTypes.ANNOTATION);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        CharSequence staticInterned = CharTableImpl.getStaticInterned(str);
        this.myText = staticInterned != null ? staticInterned.toString() : str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiAnnotationStub
    public String getText() {
        return this.myText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiAnnotationStub
    public PsiAnnotation getPsiElement() {
        PsiAnnotation psiAnnotationCreateAnnotationFromText;
        PsiAnnotation psiAnnotation = (PsiAnnotation) SoftReference.dereference(this.myParsedFromRepository);
        if (psiAnnotation != null) {
            return psiAnnotation;
        }
        String text = getText();
        try {
            PsiJavaParserFacade parserFacade = JavaPsiFacade.getInstance(getProject()).getParserFacade();
            if (parserFacade instanceof PsiElementFactoryImpl) {
                psiAnnotationCreateAnnotationFromText = ((PsiElementFactoryImpl) parserFacade).createAnnotationFromText(text, getPsi(), false);
            } else {
                psiAnnotationCreateAnnotationFromText = parserFacade.createAnnotationFromText(text, getPsi());
            }
            ((LightVirtualFile) psiAnnotationCreateAnnotationFromText.getContainingFile().getViewProvider().getVirtualFile()).setWritable(false);
            this.myParsedFromRepository = new SoftReference<>(psiAnnotationCreateAnnotationFromText);
            return psiAnnotationCreateAnnotationFromText;
        } catch (IncorrectOperationException e) {
            LOG.error("Bad annotation in " + fileName(), e);
            return null;
        }
    }

    private String fileName() {
        StubElement parentStub = this;
        while (true) {
            parentStub = parentStub.getParentStub();
            if (parentStub != null) {
                if (parentStub instanceof PsiFileStub) {
                    PsiElement psi = parentStub.getPsi();
                    if (psi instanceof PsiFile) {
                        PsiFile psiFile = (PsiFile) psi;
                        VirtualFile virtualFile = psiFile.getVirtualFile();
                        return virtualFile != null ? virtualFile.getUrl() : psiFile.getMyName();
                    }
                }
            } else {
                return "<unknown file>";
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase, java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "PsiAnnotationStub[" + this.myText + ']';
    }
}
