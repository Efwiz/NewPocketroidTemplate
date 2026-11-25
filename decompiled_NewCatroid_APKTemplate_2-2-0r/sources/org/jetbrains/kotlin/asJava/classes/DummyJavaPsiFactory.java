package org.jetbrains.kotlin.asJava.classes;

import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.ide.highlighter.JavaFileType;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.GenericsUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFileFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: compiled from: KtFakeLightClass.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\u0004H\u0002J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/DummyJavaPsiFactory;", "", "()V", "DUMMY_FILE_NAME", "", "createDummyClass", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "createDummyJavaFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiJavaFile;", "text", "createDummyVoidMethod", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMethod;", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DummyJavaPsiFactory {
    public static final DummyJavaPsiFactory INSTANCE = new DummyJavaPsiFactory();
    private static final String DUMMY_FILE_NAME = "_Dummy_." + JavaFileType.INSTANCE.getDefaultExtension();

    private DummyJavaPsiFactory() {
    }

    public final PsiMethod createDummyVoidMethod(Project project) {
        Intrinsics.checkNotNullParameter(project, "project");
        String canonicalText = GenericsUtil.getVariableTypeByExpressionType(PsiType.VOID).getCanonicalText(true);
        Intrinsics.checkNotNullExpressionValue(canonicalText, "getCanonicalText(...)");
        PsiClass[] classes = createDummyJavaFile(project, "class _Dummy_ { public " + canonicalText + " dummy() {\n} }").getClasses();
        Intrinsics.checkNotNullExpressionValue(classes, "getClasses(...)");
        PsiClass psiClass = (PsiClass) ArraysKt.singleOrNull(classes);
        if (psiClass == null) {
            throw new IncorrectOperationException("Class was not created. Method name: dummy; return type: " + canonicalText);
        }
        PsiMethod[] methods = psiClass.getMethods();
        Intrinsics.checkNotNullExpressionValue(methods, "getMethods(...)");
        PsiMethod psiMethod = (PsiMethod) ArraysKt.singleOrNull(methods);
        if (psiMethod != null) {
            return psiMethod;
        }
        throw new IncorrectOperationException("Method was not created. Method name: dummy; return type: " + canonicalText);
    }

    public final PsiClass createDummyClass(Project project) {
        Intrinsics.checkNotNullParameter(project, "project");
        PsiClass psiClassCreateClass = PsiElementFactory.getInstance(project).createClass("dummy");
        Intrinsics.checkNotNullExpressionValue(psiClassCreateClass, "createClass(...)");
        return psiClassCreateClass;
    }

    private final PsiJavaFile createDummyJavaFile(Project project, String text) {
        PsiFile psiFileCreateFileFromText = PsiFileFactory.getInstance(project).createFileFromText(DUMMY_FILE_NAME, JavaFileType.INSTANCE, text);
        Intrinsics.checkNotNull(psiFileCreateFileFromText, "null cannot be cast to non-null type com.intellij.psi.PsiJavaFile");
        return (PsiJavaFile) psiFileCreateFileFromText;
    }
}
