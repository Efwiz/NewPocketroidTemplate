package org.jetbrains.kotlin.psi;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: compiled from: KtPsiFactory.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a\u001c\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0007\u001a\u001a\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00032\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0007\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"3\u0010\u0004\u001a\u0004\u0018\u00010\u0003*\u00020\u00052\b\u0010\u0002\u001a\u0004\u0018\u00010\u00038F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t\"3\u0010\f\u001a\u0004\u0018\u00010\u0001*\u00020\u00052\b\u0010\u0002\u001a\u0004\u0018\u00010\u00018F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u0011\u0010\u000b\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u0019"}, d2 = {"DO_NOT_ANALYZE_NOTIFICATION", "", "<set-?>", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "analysisContext", "Lorg/jetbrains/kotlin/psi/KtFile;", "getAnalysisContext", "(Lorg/jetbrains/kotlin/psi/KtFile;)Lcom/intellij/psi/PsiElement;", "setAnalysisContext", "(Lorg/jetbrains/kotlin/psi/KtFile;Lcom/intellij/psi/PsiElement;)V", "analysisContext$delegate", "Lorg/jetbrains/kotlin/psi/UserDataProperty;", "doNotAnalyze", "getDoNotAnalyze", "(Lorg/jetbrains/kotlin/psi/KtFile;)Ljava/lang/String;", "setDoNotAnalyze", "(Lorg/jetbrains/kotlin/psi/KtFile;Ljava/lang/String;)V", "doNotAnalyze$delegate", "KtPsiFactory", "Lorg/jetbrains/kotlin/psi/KtPsiFactory;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "markGenerated", "", "elementForProject", "psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KtPsiFactoryKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(KtPsiFactoryKt.class, "psi"), "doNotAnalyze", "getDoNotAnalyze(Lorg/jetbrains/kotlin/psi/KtFile;)Ljava/lang/String;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(KtPsiFactoryKt.class, "psi"), "analysisContext", "getAnalysisContext(Lorg/jetbrains/kotlin/psi/KtFile;)Lcom/intellij/psi/PsiElement;"))};
    private static final UserDataProperty analysisContext$delegate;
    private static final UserDataProperty doNotAnalyze$delegate;

    @Deprecated(level = DeprecationLevel.WARNING, message = "Use 'KtPsiFactory' constructor instead", replaceWith = @ReplaceWith(expression = "KtPsiFactory(project!!, markGenerated)", imports = {"org.jetbrains.kotlin.psi.KtPsiFactory"}))
    public static final KtPsiFactory KtPsiFactory(Project project) {
        return KtPsiFactory$default(project, false, 2, (Object) null);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Use 'KtPsiFactory' constructor instead", replaceWith = @ReplaceWith(expression = "KtPsiFactory(elementForProject.project, markGenerated)", imports = {"org.jetbrains.kotlin.psi.KtPsiFactory"}))
    public static final KtPsiFactory KtPsiFactory(PsiElement elementForProject) {
        Intrinsics.checkNotNullParameter(elementForProject, "elementForProject");
        return KtPsiFactory$default(elementForProject, false, 2, (Object) null);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Use 'KtPsiFactory' constructor instead", replaceWith = @ReplaceWith(expression = "KtPsiFactory(project!!, markGenerated)", imports = {"org.jetbrains.kotlin.psi.KtPsiFactory"}))
    public static final KtPsiFactory KtPsiFactory(Project project, boolean z) {
        Intrinsics.checkNotNull(project);
        return new KtPsiFactory(project, z);
    }

    public static /* synthetic */ KtPsiFactory KtPsiFactory$default(Project project, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return KtPsiFactory(project, z);
    }

    public static /* synthetic */ KtPsiFactory KtPsiFactory$default(PsiElement psiElement, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return KtPsiFactory(psiElement, z);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Use 'KtPsiFactory' constructor instead", replaceWith = @ReplaceWith(expression = "KtPsiFactory(elementForProject.project, markGenerated)", imports = {"org.jetbrains.kotlin.psi.KtPsiFactory"}))
    public static final KtPsiFactory KtPsiFactory(PsiElement elementForProject, boolean z) {
        Intrinsics.checkNotNullParameter(elementForProject, "elementForProject");
        Project project = elementForProject.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        return new KtPsiFactory(project, z);
    }

    static {
        Key keyCreate = Key.create("DO_NOT_ANALYZE");
        Intrinsics.checkNotNullExpressionValue(keyCreate, "create(...)");
        doNotAnalyze$delegate = new UserDataProperty(keyCreate);
        Key keyCreate2 = Key.create("ANALYSIS_CONTEXT");
        Intrinsics.checkNotNullExpressionValue(keyCreate2, "create(...)");
        analysisContext$delegate = new UserDataProperty(keyCreate2);
    }

    public static final String getDoNotAnalyze(KtFile ktFile) {
        Intrinsics.checkNotNullParameter(ktFile, "<this>");
        return (String) doNotAnalyze$delegate.getValue(ktFile, $$delegatedProperties[0]);
    }

    public static final void setDoNotAnalyze(KtFile ktFile, String str) {
        Intrinsics.checkNotNullParameter(ktFile, "<this>");
        doNotAnalyze$delegate.setValue(ktFile, $$delegatedProperties[0], str);
    }

    public static final PsiElement getAnalysisContext(KtFile ktFile) {
        Intrinsics.checkNotNullParameter(ktFile, "<this>");
        return (PsiElement) analysisContext$delegate.getValue(ktFile, $$delegatedProperties[1]);
    }

    public static final void setAnalysisContext(KtFile ktFile, PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(ktFile, "<this>");
        analysisContext$delegate.setValue(ktFile, $$delegatedProperties[1], psiElement);
    }
}
