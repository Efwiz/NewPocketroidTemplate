package org.jetbrains.kotlin.load.java.structure.impl.source;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;

/* JADX INFO: compiled from: JavaElementSourceFactory.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000 #2\u00020\u0001:\u0001#B\u0005¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\b\b\u0000\u0010\u0005*\u00020\u00062\u000e\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u00020\t0\bH&J(\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\b\b\u0000\u0010\u0005*\u00020\u00062\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\f0\bH&J&\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00042\u000e\u0010\u000f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00100\b2\u0006\u0010\u0011\u001a\u00020\u0012H&J%\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00140\b\"\b\b\u0000\u0010\u0014*\u00020\u00152\u0006\u0010\u0016\u001a\u0002H\u0014H&¢\u0006\u0002\u0010\u0017J&\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00042\u000e\u0010\u000f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00100\b2\u0006\u0010\u0019\u001a\u00020\u0012H&J0\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\b\b\u0000\u0010\u0005*\u00020\u00062\u000e\u0010\u000f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001b0\b2\u0006\u0010\u001c\u001a\u00020\u0012H&J%\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\b\b\u0000\u0010\u0005*\u00020\u00062\u0006\u0010\u001e\u001a\u0002H\u0005H&¢\u0006\u0002\u0010\u001fJ(\u0010 \u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\b\b\u0000\u0010\u0005*\u00020\u00062\u000e\u0010!\u001a\n\u0012\u0006\b\u0001\u0012\u00020\"0\bH&¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementSourceFactory;", "", "()V", "createExpressionTypeSource", "Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementTypeSource;", "TYPE", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "psiExpressionSource", "Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementPsiSource;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiExpression;", "createMethodReturnTypeSource", "psiMethodSource", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMethod;", "createPermittedTypeSource", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClassType;", "psiTypeParameterSource", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "permittedTypeIndex", "", "createPsiSource", "PSI", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "psi", "(Lcom/intellij/psi/PsiElement;)Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementPsiSource;", "createSuperTypeSource", "superTypeIndex", "createTypeParameterUpperBoundTypeSource", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameter;", "boundIndex", "createTypeSource", ModuleXmlParser.TYPE, "(Lcom/intellij/psi/PsiType;)Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementTypeSource;", "createVariableReturnTypeSource", "psiVariableSource", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiVariable;", "Companion", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class JavaElementSourceFactory {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @JvmStatic
    public static final JavaElementSourceFactory getInstance(Project project) {
        return INSTANCE.getInstance(project);
    }

    public abstract <TYPE extends PsiType> JavaElementTypeSource<TYPE> createExpressionTypeSource(JavaElementPsiSource<? extends PsiExpression> psiExpressionSource);

    public abstract <TYPE extends PsiType> JavaElementTypeSource<TYPE> createMethodReturnTypeSource(JavaElementPsiSource<? extends PsiMethod> psiMethodSource);

    public abstract JavaElementTypeSource<PsiClassType> createPermittedTypeSource(JavaElementPsiSource<? extends PsiClass> psiTypeParameterSource, int permittedTypeIndex);

    public abstract <PSI extends PsiElement> JavaElementPsiSource<PSI> createPsiSource(PSI psi);

    public abstract JavaElementTypeSource<PsiClassType> createSuperTypeSource(JavaElementPsiSource<? extends PsiClass> psiTypeParameterSource, int superTypeIndex);

    public abstract <TYPE extends PsiType> JavaElementTypeSource<TYPE> createTypeParameterUpperBoundTypeSource(JavaElementPsiSource<? extends PsiTypeParameter> psiTypeParameterSource, int boundIndex);

    public abstract <TYPE extends PsiType> JavaElementTypeSource<TYPE> createTypeSource(TYPE type);

    public abstract <TYPE extends PsiType> JavaElementTypeSource<TYPE> createVariableReturnTypeSource(JavaElementPsiSource<? extends PsiVariable> psiVariableSource);

    /* JADX INFO: compiled from: JavaElementSourceFactory.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementSourceFactory$Companion;", "", "()V", "getInstance", "Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementSourceFactory;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final JavaElementSourceFactory getInstance(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            Object service = project.getService(JavaElementSourceFactory.class);
            Intrinsics.checkNotNullExpressionValue(service, "getService(...)");
            return (JavaElementSourceFactory) service;
        }
    }
}
