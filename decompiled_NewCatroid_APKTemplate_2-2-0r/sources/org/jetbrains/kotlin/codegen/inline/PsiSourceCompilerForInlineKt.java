package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingUtils;
import org.jetbrains.kotlin.types.expressions.LabelResolver;

/* JADX INFO: compiled from: PsiSourceCompilerForInline.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u001a\f\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\u0006\u001a\n\u0010\t\u001a\u00020\n*\u00020\u0006¨\u0006\u000b"}, d2 = {"getDeclarationLabels", "", "", "lambdaOrFun", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getInlineCallSiteVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "isInlineOrInsideInline", "", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PsiSourceCompilerForInlineKt {
    public static final boolean isInlineOrInsideInline(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return getInlineCallSiteVisibility(declarationDescriptor) != null;
    }

    public static final DescriptorVisibility getInlineCallSiteVisibility(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        DescriptorVisibility visibility = null;
        while (declarationDescriptor != null) {
            if (declarationDescriptor instanceof FunctionDescriptor) {
                FunctionDescriptor functionDescriptor = (FunctionDescriptor) declarationDescriptor;
                if (!functionDescriptor.isInline()) {
                    continue;
                } else {
                    if (!DescriptorVisibilities.isPrivate(functionDescriptor.getVisibility())) {
                        return functionDescriptor.getVisibility();
                    }
                    visibility = functionDescriptor.getVisibility();
                }
            }
            declarationDescriptor = declarationDescriptor.getContainingDeclaration();
        }
        return visibility;
    }

    public static final Set<String> getDeclarationLabels(PsiElement psiElement, DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        HashSet hashSet = new HashSet();
        if (psiElement != null) {
            List<Name> labelNamesIfAny = LabelResolver.INSTANCE.getLabelNamesIfAny(psiElement, false);
            if (!labelNamesIfAny.isEmpty()) {
                Name name2 = (Name) CollectionsKt.singleOrNull((List) labelNamesIfAny);
                if (name2 == null) {
                    throw new IllegalStateException(("Multiple labels for " + psiElement).toString());
                }
                hashSet.add(name2.asString());
            }
        }
        if (!ExpressionTypingUtils.isFunctionLiteral(descriptor)) {
            if (!descriptor.getShortName().isSpecial()) {
                hashSet.add(descriptor.getShortName().asString());
            }
            hashSet.add(InlineCodegenUtilsKt.FIRST_FUN_LABEL);
        }
        return hashSet;
    }
}
