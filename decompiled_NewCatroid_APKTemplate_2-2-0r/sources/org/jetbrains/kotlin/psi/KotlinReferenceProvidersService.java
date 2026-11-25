package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;

/* JADX INFO: compiled from: KotlinReferenceProvidersService.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¢\u0006\u0002\u0010\b¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/psi/KotlinReferenceProvidersService;", "", "()V", "getReferences", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiReference;", "psiElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "(Lcom/intellij/psi/PsiElement;)[Lcom/intellij/psi/PsiReference;", "Companion", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KotlinReferenceProvidersService {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final KotlinReferenceProvidersService NO_REFERENCES_SERVICE = new KotlinReferenceProvidersService();

    @JvmStatic
    public static final KotlinReferenceProvidersService getInstance(Project project) {
        return INSTANCE.getInstance(project);
    }

    @JvmStatic
    public static final PsiReference[] getReferencesFromProviders(PsiElement psiElement) {
        return INSTANCE.getReferencesFromProviders(psiElement);
    }

    public PsiReference[] getReferences(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "psiElement");
        PsiReference[] EMPTY_ARRAY = PsiReference.EMPTY_ARRAY;
        Intrinsics.checkNotNullExpressionValue(EMPTY_ARRAY, "EMPTY_ARRAY");
        return EMPTY_ARRAY;
    }

    /* JADX INFO: compiled from: KotlinReferenceProvidersService.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0007J\u001b\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\fH\u0007¢\u0006\u0002\u0010\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/psi/KotlinReferenceProvidersService$Companion;", "", "()V", "NO_REFERENCES_SERVICE", "Lorg/jetbrains/kotlin/psi/KotlinReferenceProvidersService;", "getInstance", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "getReferencesFromProviders", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiReference;", "psiElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "(Lcom/intellij/psi/PsiElement;)[Lcom/intellij/psi/PsiReference;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final KotlinReferenceProvidersService getInstance(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            KotlinReferenceProvidersService kotlinReferenceProvidersService = (KotlinReferenceProvidersService) project.getService(KotlinReferenceProvidersService.class);
            return kotlinReferenceProvidersService == null ? KotlinReferenceProvidersService.NO_REFERENCES_SERVICE : kotlinReferenceProvidersService;
        }

        @JvmStatic
        public final PsiReference[] getReferencesFromProviders(PsiElement psiElement) {
            Intrinsics.checkNotNullParameter(psiElement, "psiElement");
            Project project = psiElement.getProject();
            Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
            return getInstance(project).getReferences(psiElement);
        }
    }
}
