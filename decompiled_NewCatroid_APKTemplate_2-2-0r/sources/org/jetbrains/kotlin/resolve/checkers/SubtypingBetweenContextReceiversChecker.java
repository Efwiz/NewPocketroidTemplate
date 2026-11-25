package org.jetbrains.kotlin.resolve.checkers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecursiveElementWalkingVisitor;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.psi.KtContextReceiverList;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.ContextReceiversUtilKt;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ContextualDeclarationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/SubtypingBetweenContextReceiversChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SubtypingBetweenContextReceiversChecker implements DeclarationChecker {
    public static final SubtypingBetweenContextReceiversChecker INSTANCE = new SubtypingBetweenContextReceiversChecker();

    private SubtypingBetweenContextReceiversChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        List<ReceiverParameterDescriptor> contextReceivers;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.ContextReceivers) && KtPsiUtilKt.isContextualDeclaration(declaration)) {
            if (descriptor instanceof CallableDescriptor) {
                contextReceivers = ((CallableDescriptor) descriptor).getContextReceiverParameters();
            } else if (!(descriptor instanceof ClassDescriptor)) {
                return;
            } else {
                contextReceivers = ((ClassDescriptor) descriptor).getContextReceivers();
            }
            Intrinsics.checkNotNull(contextReceivers);
            KtDeclaration ktDeclaration = declaration;
            final SubtypingBetweenContextReceiversChecker$check$$inlined$findDescendantOfType$default$1 subtypingBetweenContextReceiversChecker$check$$inlined$findDescendantOfType$default$1 = new Function1<KtContextReceiverList, Boolean>() { // from class: org.jetbrains.kotlin.resolve.checkers.SubtypingBetweenContextReceiversChecker$check$$inlined$findDescendantOfType$default$1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(KtContextReceiverList it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return true;
                }
            };
            PsiUtilsKt.checkDecompiledText(ktDeclaration);
            final Ref.ObjectRef objectRef = new Ref.ObjectRef();
            ktDeclaration.accept(new PsiRecursiveElementWalkingVisitor() { // from class: org.jetbrains.kotlin.resolve.checkers.SubtypingBetweenContextReceiversChecker$check$$inlined$findDescendantOfType$default$2
                /* JADX WARN: Multi-variable type inference failed */
                @Override // org.jetbrains.kotlin.com.intellij.psi.PsiRecursiveElementWalkingVisitor, org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
                public void visitElement(PsiElement element) {
                    Intrinsics.checkNotNullParameter(element, "element");
                    if ((element instanceof KtContextReceiverList) && ((Boolean) subtypingBetweenContextReceiversChecker$check$$inlined$findDescendantOfType$default$1.invoke(element)).booleanValue()) {
                        objectRef.element = element;
                        stopWalking();
                    } else {
                        super.visitElement(element);
                    }
                }
            });
            KtContextReceiverList ktContextReceiverList = (KtContextReceiverList) ((PsiElement) objectRef.element);
            if (ktContextReceiverList == null) {
                return;
            }
            BindingTrace trace = context.getTrace();
            List<ReceiverParameterDescriptor> list = contextReceivers;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                KotlinType type = ((ReceiverParameterDescriptor) it.next()).getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                arrayList.add(type);
            }
            ContextReceiversUtilKt.checkSubtypingBetweenContextReceivers(trace, ktContextReceiverList, arrayList);
        }
    }
}
