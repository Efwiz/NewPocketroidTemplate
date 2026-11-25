package org.jetbrains.kotlin.com.intellij.codeInsight;

import java.util.List;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair;
import org.jetbrains.kotlin.com.intellij.util.messages.Topic;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ExternalAnnotationsManager {
    public static final String ANNOTATIONS_XML = "annotations.xml";
    public static final Topic<ExternalAnnotationsListener> TOPIC = new Topic<>("external annotations", ExternalAnnotationsListener.class);

    public enum AnnotationPlace {
        IN_CODE,
        EXTERNAL,
        NEED_ASK_USER,
        NOWHERE
    }

    public static class CanceledConfigurationException extends RuntimeException {
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else if (i != 2) {
            objArr[0] = "project";
        } else {
            objArr[0] = "oldExternalName";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/codeInsight/ExternalAnnotationsManager";
        if (i == 1 || i == 2) {
            objArr[2] = "elementRenamedOrMoved";
        } else {
            objArr[2] = "getInstance";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public abstract void annotateExternally(PsiModifierListOwner psiModifierListOwner, String str, PsiFile psiFile, PsiNameValuePair[] psiNameValuePairArr) throws CanceledConfigurationException;

    public abstract AnnotationPlace chooseAnnotationsPlace(PsiElement psiElement);

    public abstract AnnotationPlace chooseAnnotationsPlaceNoUi(PsiElement psiElement);

    public abstract boolean deannotate(PsiModifierListOwner psiModifierListOwner, String str);

    public abstract boolean editExternalAnnotation(PsiModifierListOwner psiModifierListOwner, String str, PsiNameValuePair[] psiNameValuePairArr);

    public void elementRenamedOrMoved(PsiModifierListOwner psiModifierListOwner, String str) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(1);
        }
        if (str == null) {
            $$$reportNull$$$0(2);
        }
    }

    public abstract List<PsiAnnotation> findDefaultConstructorExternalAnnotations(PsiClass psiClass);

    public abstract List<PsiAnnotation> findDefaultConstructorExternalAnnotations(PsiClass psiClass, String str);

    public abstract PsiAnnotation findExternalAnnotation(PsiModifierListOwner psiModifierListOwner, String str);

    public abstract List<PsiAnnotation> findExternalAnnotations(PsiModifierListOwner psiModifierListOwner, String str);

    public abstract PsiAnnotation[] findExternalAnnotations(PsiModifierListOwner psiModifierListOwner);

    public abstract List<PsiFile> findExternalAnnotationsFiles(PsiModifierListOwner psiModifierListOwner);

    public abstract boolean hasAnnotationRootsForFile(VirtualFile virtualFile);

    public abstract boolean hasConfiguredAnnotationRoot(PsiModifierListOwner psiModifierListOwner);

    public abstract boolean isExternalAnnotation(PsiAnnotation psiAnnotation);

    public abstract boolean isExternalAnnotationWritable(PsiModifierListOwner psiModifierListOwner, String str);

    public static ExternalAnnotationsManager getInstance(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        return (ExternalAnnotationsManager) project.getService(ExternalAnnotationsManager.class);
    }
}
