package org.jetbrains.kotlin.plugin.references;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.idea.references.KtSimpleNameReference;
import org.jetbrains.kotlin.psi.KtPsiFactory;

/* JADX INFO: compiled from: SimpleNameReferenceExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\bf\u0018\u0000 \r2\u00020\u0001:\u0001\rJ\"\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u0003H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000eÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/plugin/references/SimpleNameReferenceExtension;", "", "handleElementRename", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "reference", "Lorg/jetbrains/kotlin/idea/references/KtSimpleNameReference;", "psiFactory", "Lorg/jetbrains/kotlin/psi/KtPsiFactory;", "newElementName", "", "isReferenceTo", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Companion", "kt-references"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SimpleNameReferenceExtension {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    PsiElement handleElementRename(KtSimpleNameReference reference, KtPsiFactory psiFactory, String newElementName);

    boolean isReferenceTo(KtSimpleNameReference reference, PsiElement element);

    /* JADX INFO: compiled from: SimpleNameReferenceExtension.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/plugin/references/SimpleNameReferenceExtension$Companion;", "", "()V", "EP_NAME", "Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/ExtensionPointName;", "Lorg/jetbrains/kotlin/plugin/references/SimpleNameReferenceExtension;", "getEP_NAME", "()Lcom/intellij/openapi/extensions/ExtensionPointName;", "kt-references"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final ExtensionPointName<SimpleNameReferenceExtension> EP_NAME;

        private Companion() {
        }

        public final ExtensionPointName<SimpleNameReferenceExtension> getEP_NAME() {
            return EP_NAME;
        }

        static {
            ExtensionPointName<SimpleNameReferenceExtension> extensionPointNameCreate = ExtensionPointName.create("org.jetbrains.kotlin.simpleNameReferenceExtension");
            Intrinsics.checkNotNullExpressionValue(extensionPointNameCreate, "create(...)");
            EP_NAME = extensionPointNameCreate;
        }
    }
}
