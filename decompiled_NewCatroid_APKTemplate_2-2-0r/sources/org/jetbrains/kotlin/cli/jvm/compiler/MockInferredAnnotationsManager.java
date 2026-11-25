package org.jetbrains.kotlin.cli.jvm.compiler;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.codeInsight.InferredAnnotationsManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;

/* JADX INFO: compiled from: MockInferredAnnotationsManager.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u001d\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\n2\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¢\u0006\u0002\u0010\u000bJ\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0004H\u0016¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/MockInferredAnnotationsManager;", "Lorg/jetbrains/kotlin/com/intellij/codeInsight/InferredAnnotationsManager;", "()V", "findInferredAnnotation", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotation;", "listOwner", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierListOwner;", "annotationFQN", "", "findInferredAnnotations", "", "(Lcom/intellij/psi/PsiModifierListOwner;)[Lcom/intellij/psi/PsiAnnotation;", "isInferredAnnotation", "", "annotation", "Companion", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MockInferredAnnotationsManager extends InferredAnnotationsManager {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final PsiAnnotation[] EMPTY_PSI_ANNOTATION_ARRAY = new PsiAnnotation[0];

    @Override // org.jetbrains.kotlin.com.intellij.codeInsight.InferredAnnotationsManager
    public PsiAnnotation findInferredAnnotation(PsiModifierListOwner listOwner, String annotationFQN) {
        Intrinsics.checkNotNullParameter(listOwner, "listOwner");
        Intrinsics.checkNotNullParameter(annotationFQN, "annotationFQN");
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.codeInsight.InferredAnnotationsManager
    public boolean isInferredAnnotation(PsiAnnotation annotation) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.codeInsight.InferredAnnotationsManager
    public PsiAnnotation[] findInferredAnnotations(PsiModifierListOwner listOwner) {
        Intrinsics.checkNotNullParameter(listOwner, "listOwner");
        return EMPTY_PSI_ANNOTATION_ARRAY;
    }

    /* JADX INFO: compiled from: MockInferredAnnotationsManager.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0019\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/MockInferredAnnotationsManager$Companion;", "", "()V", "EMPTY_PSI_ANNOTATION_ARRAY", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotation;", "getEMPTY_PSI_ANNOTATION_ARRAY", "()[Lcom/intellij/psi/PsiAnnotation;", "[Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotation;", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final PsiAnnotation[] getEMPTY_PSI_ANNOTATION_ARRAY() {
            return MockInferredAnnotationsManager.EMPTY_PSI_ANNOTATION_ARRAY;
        }
    }
}
