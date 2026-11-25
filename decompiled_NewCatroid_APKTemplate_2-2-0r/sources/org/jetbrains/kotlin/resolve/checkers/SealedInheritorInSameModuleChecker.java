package org.jetbrains.kotlin.resolve.checkers;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorUtilKt;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.SourceFile;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.bindingContextUtil.BindingContextUtilsKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.multiplatform.IsCommonSourceKt;
import org.jetbrains.kotlin.resolve.source.PsiSourceFile;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: SealedInheritorInSameModuleChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0018\u0010\u0003\u001a\u00020\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0006¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/SealedInheritorInSameModuleChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "isFromCommonSource", "", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;)Z", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SealedInheritorInSameModuleChecker implements DeclarationChecker {
    public static final SealedInheritorInSameModuleChecker INSTANCE = new SealedInheritorInSameModuleChecker();

    private SealedInheritorInSameModuleChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        UnwrappedType unwrappedTypeUnwrap;
        ClassifierDescriptor this$0;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if ((descriptor instanceof ClassDescriptor) && (declaration instanceof KtClassOrObject)) {
            ModuleDescriptor module = DescriptorUtilsKt.getModule(descriptor);
            Iterator<KtSuperTypeListEntry> it = ((KtClassOrObject) declaration).getSuperTypeListEntries().iterator();
            while (it.hasNext()) {
                KtTypeReference typeReference = it.next().getTypeReference();
                if (typeReference != null) {
                    BindingContext context2 = context.getTrace().getContext();
                    Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
                    KotlinType abbreviatedTypeOrType = BindingContextUtilsKt.getAbbreviatedTypeOrType(typeReference, context2);
                    if (abbreviatedTypeOrType != null && (unwrappedTypeUnwrap = abbreviatedTypeOrType.unwrap()) != null && (this$0 = unwrappedTypeUnwrap.getConstructor().getThis$0()) != null) {
                        ClassifierDescriptor classifierDescriptor = this$0;
                        if (!DescriptorUtilKt.isSealed(classifierDescriptor)) {
                            continue;
                        } else {
                            if (isFromCommonSource((ClassifierDescriptor) descriptor) && Intrinsics.areEqual(DescriptorUtilsKt.getModule(classifierDescriptor), module)) {
                                return;
                            }
                            if (!((ClassDescriptor) this$0).get$isExpect() && (!Intrinsics.areEqual(DescriptorUtilsKt.getModule(classifierDescriptor), module) || isFromCommonSource(this$0))) {
                                context.getTrace().report(Errors.SEALED_INHERITOR_IN_DIFFERENT_MODULE.on(typeReference));
                            }
                        }
                    }
                }
            }
        }
    }

    private final boolean isFromCommonSource(ClassifierDescriptor classifierDescriptor) {
        Boolean boolIsCommonSource;
        SourceFile containingFile = classifierDescriptor.getSourceElement().getContainingFile();
        PsiSourceFile psiSourceFile = containingFile instanceof PsiSourceFile ? (PsiSourceFile) containingFile : null;
        PsiFile psiFile = psiSourceFile != null ? psiSourceFile.getPsiFile() : null;
        KtFile ktFile = psiFile instanceof KtFile ? (KtFile) psiFile : null;
        if (ktFile == null || (boolIsCommonSource = IsCommonSourceKt.isCommonSource(ktFile)) == null) {
            return false;
        }
        return boolIsCommonSource.booleanValue();
    }
}
