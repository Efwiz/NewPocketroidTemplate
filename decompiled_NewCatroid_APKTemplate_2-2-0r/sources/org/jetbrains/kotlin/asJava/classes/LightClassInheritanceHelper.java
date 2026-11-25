package org.jetbrains.kotlin.asJava.classes;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.ImpreciseResolveResult;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;

/* JADX INFO: compiled from: KtLightClassForSourceDeclaration.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\bf\u0018\u0000 \n2\u00020\u0001:\u0002\n\u000bJ \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/LightClassInheritanceHelper;", "", "isInheritor", "Lorg/jetbrains/kotlin/asJava/ImpreciseResolveResult;", "lightClass", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;", "baseClass", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "checkDeep", "", "Companion", "NoHelp", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface LightClassInheritanceHelper {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    ImpreciseResolveResult isInheritor(KtLightClass lightClass, PsiClass baseClass, boolean checkDeep);

    /* JADX INFO: compiled from: KtLightClassForSourceDeclaration.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/LightClassInheritanceHelper$NoHelp;", "Lorg/jetbrains/kotlin/asJava/classes/LightClassInheritanceHelper;", "()V", "isInheritor", "Lorg/jetbrains/kotlin/asJava/ImpreciseResolveResult;", "lightClass", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;", "baseClass", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "checkDeep", "", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NoHelp implements LightClassInheritanceHelper {
        public static final NoHelp INSTANCE = new NoHelp();

        private NoHelp() {
        }

        @Override // org.jetbrains.kotlin.asJava.classes.LightClassInheritanceHelper
        public ImpreciseResolveResult isInheritor(KtLightClass lightClass, PsiClass baseClass, boolean checkDeep) {
            Intrinsics.checkNotNullParameter(lightClass, "lightClass");
            Intrinsics.checkNotNullParameter(baseClass, "baseClass");
            return ImpreciseResolveResult.UNSURE;
        }
    }

    /* JADX INFO: compiled from: KtLightClassForSourceDeclaration.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/LightClassInheritanceHelper$Companion;", "", "()V", "getService", "Lorg/jetbrains/kotlin/asJava/classes/LightClassInheritanceHelper;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final LightClassInheritanceHelper getService(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            LightClassInheritanceHelper lightClassInheritanceHelper = (LightClassInheritanceHelper) project.getService(LightClassInheritanceHelper.class);
            return lightClassInheritanceHelper == null ? NoHelp.INSTANCE : lightClassInheritanceHelper;
        }
    }
}
