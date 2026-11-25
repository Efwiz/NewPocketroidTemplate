package org.jetbrains.kotlin.cli.jvm.compiler;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.codeInsight.ExternalAnnotationsManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair;

/* JADX INFO: compiled from: MockExternalAnnotationsManager.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J7\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0010\u0010\u000b\u001a\f\u0012\u0006\b\u0001\u0012\u00020\r\u0018\u00010\fH\u0016¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\bH\u0016J/\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\b2\u0010\u0010\u000b\u001a\f\u0012\u0006\b\u0001\u0012\u00020\r\u0018\u00010\fH\u0016¢\u0006\u0002\u0010\u0018J\u0016\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u001e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0016\u001a\u00020\bH\u0016J\u001a\u0010\u001e\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\bH\u0016J\u001f\u0010\u001f\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u001b\u0018\u00010\f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¢\u0006\u0002\u0010 J\u001e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\bH\u0016J\u0018\u0010!\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\u001a2\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\"\u001a\u00020\u00152\u0006\u0010#\u001a\u00020$H\u0016J\u0010\u0010%\u001a\u00020\u00152\u0006\u0010&\u001a\u00020\u0006H\u0016J\u0010\u0010'\u001a\u00020\u00152\u0006\u0010(\u001a\u00020\u001bH\u0016J\u0018\u0010)\u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\bH\u0016¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/MockExternalAnnotationsManager;", "Lorg/jetbrains/kotlin/com/intellij/codeInsight/ExternalAnnotationsManager;", "()V", "annotateExternally", "", "listOwner", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierListOwner;", "annotationFQName", "", "fromFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "value", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiNameValuePair;", "(Lcom/intellij/psi/PsiModifierListOwner;Ljava/lang/String;Lcom/intellij/psi/PsiFile;[Lcom/intellij/psi/PsiNameValuePair;)V", "chooseAnnotationsPlace", "Lorg/jetbrains/kotlin/com/intellij/codeInsight/ExternalAnnotationsManager$AnnotationPlace;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "chooseAnnotationsPlaceNoUi", "deannotate", "", "annotationFQN", "editExternalAnnotation", "(Lcom/intellij/psi/PsiModifierListOwner;Ljava/lang/String;[Lcom/intellij/psi/PsiNameValuePair;)Z", "findDefaultConstructorExternalAnnotations", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotation;", "aClass", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "findExternalAnnotation", "findExternalAnnotations", "(Lcom/intellij/psi/PsiModifierListOwner;)[Lcom/intellij/psi/PsiAnnotation;", "findExternalAnnotationsFiles", "hasAnnotationRootsForFile", "file", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "hasConfiguredAnnotationRoot", "owner", "isExternalAnnotation", "annotation", "isExternalAnnotationWritable", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MockExternalAnnotationsManager extends ExternalAnnotationsManager {
    @Override // org.jetbrains.kotlin.com.intellij.codeInsight.ExternalAnnotationsManager
    public PsiAnnotation findExternalAnnotation(PsiModifierListOwner listOwner, String annotationFQN) {
        Intrinsics.checkNotNullParameter(listOwner, "listOwner");
        Intrinsics.checkNotNullParameter(annotationFQN, "annotationFQN");
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.codeInsight.ExternalAnnotationsManager
    public PsiAnnotation[] findExternalAnnotations(PsiModifierListOwner listOwner) {
        Intrinsics.checkNotNullParameter(listOwner, "listOwner");
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.codeInsight.ExternalAnnotationsManager
    public List<PsiFile> findExternalAnnotationsFiles(PsiModifierListOwner listOwner) {
        Intrinsics.checkNotNullParameter(listOwner, "listOwner");
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.codeInsight.ExternalAnnotationsManager
    public boolean hasAnnotationRootsForFile(VirtualFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.codeInsight.ExternalAnnotationsManager
    public boolean hasConfiguredAnnotationRoot(PsiModifierListOwner owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.codeInsight.ExternalAnnotationsManager
    public boolean isExternalAnnotation(PsiAnnotation annotation) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.codeInsight.ExternalAnnotationsManager
    public boolean isExternalAnnotationWritable(PsiModifierListOwner listOwner, String annotationFQN) {
        Intrinsics.checkNotNullParameter(listOwner, "listOwner");
        Intrinsics.checkNotNullParameter(annotationFQN, "annotationFQN");
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.codeInsight.ExternalAnnotationsManager
    public ExternalAnnotationsManager.AnnotationPlace chooseAnnotationsPlace(PsiElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        return ExternalAnnotationsManager.AnnotationPlace.NOWHERE;
    }

    @Override // org.jetbrains.kotlin.com.intellij.codeInsight.ExternalAnnotationsManager
    public ExternalAnnotationsManager.AnnotationPlace chooseAnnotationsPlaceNoUi(PsiElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        return ExternalAnnotationsManager.AnnotationPlace.NOWHERE;
    }

    @Override // org.jetbrains.kotlin.com.intellij.codeInsight.ExternalAnnotationsManager
    public void annotateExternally(PsiModifierListOwner listOwner, String annotationFQName, PsiFile fromFile, PsiNameValuePair[] psiNameValuePairArr) {
        Intrinsics.checkNotNullParameter(listOwner, "listOwner");
        Intrinsics.checkNotNullParameter(annotationFQName, "annotationFQName");
        Intrinsics.checkNotNullParameter(fromFile, "fromFile");
        throw new UnsupportedOperationException("not implemented");
    }

    @Override // org.jetbrains.kotlin.com.intellij.codeInsight.ExternalAnnotationsManager
    public boolean deannotate(PsiModifierListOwner listOwner, String annotationFQN) {
        Intrinsics.checkNotNullParameter(listOwner, "listOwner");
        Intrinsics.checkNotNullParameter(annotationFQN, "annotationFQN");
        throw new UnsupportedOperationException("not implemented");
    }

    @Override // org.jetbrains.kotlin.com.intellij.codeInsight.ExternalAnnotationsManager
    public boolean editExternalAnnotation(PsiModifierListOwner listOwner, String annotationFQN, PsiNameValuePair[] psiNameValuePairArr) {
        Intrinsics.checkNotNullParameter(listOwner, "listOwner");
        Intrinsics.checkNotNullParameter(annotationFQN, "annotationFQN");
        throw new UnsupportedOperationException("not implemented");
    }

    @Override // org.jetbrains.kotlin.com.intellij.codeInsight.ExternalAnnotationsManager
    public List<PsiAnnotation> findDefaultConstructorExternalAnnotations(PsiClass aClass, String annotationFQN) {
        Intrinsics.checkNotNullParameter(aClass, "aClass");
        Intrinsics.checkNotNullParameter(annotationFQN, "annotationFQN");
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.codeInsight.ExternalAnnotationsManager
    public List<PsiAnnotation> findDefaultConstructorExternalAnnotations(PsiClass aClass) {
        Intrinsics.checkNotNullParameter(aClass, "aClass");
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.codeInsight.ExternalAnnotationsManager
    public List<PsiAnnotation> findExternalAnnotations(PsiModifierListOwner listOwner, String annotationFQN) {
        Intrinsics.checkNotNullParameter(listOwner, "listOwner");
        Intrinsics.checkNotNullParameter(annotationFQN, "annotationFQN");
        return CollectionsKt.emptyList();
    }
}
