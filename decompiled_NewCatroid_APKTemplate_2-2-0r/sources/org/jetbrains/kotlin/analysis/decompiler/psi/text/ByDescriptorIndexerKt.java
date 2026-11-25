package org.jetbrains.kotlin.analysis.decompiler.psi.text;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.psi.KtDeclarationModifierList;
import org.jetbrains.kotlin.psi.KtElementImplStub;
import org.jetbrains.kotlin.psi.KtFunctionType;
import org.jetbrains.kotlin.psi.KtNullableType;
import org.jetbrains.kotlin.psi.KtTypeElement;
import org.jetbrains.kotlin.psi.KtUserType;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: ByDescriptorIndexer.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001a\u0010\u0000\u001a\u0004\u0018\u00010\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u0012\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0006\u001a\u00020\u0007H\u0002\u001a\u001b\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\t*\u0006\u0012\u0002\b\u00030\u000b¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"getQualifiedName", "", "typeElement", "Lorg/jetbrains/kotlin/psi/KtTypeElement;", "isSuspend", "", "userType", "Lorg/jetbrains/kotlin/psi/KtUserType;", "getAllModifierLists", "", "Lorg/jetbrains/kotlin/psi/KtDeclarationModifierList;", "Lorg/jetbrains/kotlin/psi/KtElementImplStub;", "(Lorg/jetbrains/kotlin/psi/KtElementImplStub;)[Lorg/jetbrains/kotlin/psi/KtDeclarationModifierList;", "decompiler-to-psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ByDescriptorIndexerKt {
    public static final String getQualifiedName(KtTypeElement ktTypeElement, boolean z) {
        if (ktTypeElement instanceof KtUserType) {
            return getQualifiedName((KtUserType) ktTypeElement);
        }
        if (ktTypeElement instanceof KtFunctionType) {
            KtFunctionType ktFunctionType = (KtFunctionType) ktTypeElement;
            int size = ktFunctionType.getParameters().size();
            if (ktFunctionType.getReceiverTypeReference() != null) {
                size++;
            }
            if (z) {
                return StandardNames.getSuspendFunctionClassId(size).asFqNameString();
            }
            return StandardNames.getFunctionClassId(size).asFqNameString();
        }
        if (ktTypeElement instanceof KtNullableType) {
            return getQualifiedName(KtPsiUtilKt.unwrapNullability(ktTypeElement), z);
        }
        return null;
    }

    private static final String getQualifiedName(KtUserType ktUserType) {
        KtUserType qualifier = ktUserType.getQualifier();
        return qualifier == null ? ktUserType.getReferencedName() : getQualifiedName(qualifier) + '.' + ktUserType.getReferencedName();
    }

    public static final KtDeclarationModifierList[] getAllModifierLists(KtElementImplStub<?> ktElementImplStub) {
        Intrinsics.checkNotNullParameter(ktElementImplStub, "<this>");
        PsiElement[] stubOrPsiChildren = ktElementImplStub.getStubOrPsiChildren(KtStubElementTypes.MODIFIER_LIST, KtStubElementTypes.MODIFIER_LIST.getArrayFactory());
        Intrinsics.checkNotNullExpressionValue(stubOrPsiChildren, "getStubOrPsiChildren(...)");
        return (KtDeclarationModifierList[]) stubOrPsiChildren;
    }
}
