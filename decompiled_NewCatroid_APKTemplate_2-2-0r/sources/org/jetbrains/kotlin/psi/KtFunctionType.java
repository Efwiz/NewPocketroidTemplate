package org.jetbrains.kotlin.psi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.lexer.KtToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KtFunctionType extends KtElementImplStub<KotlinPlaceHolderStub<KtFunctionType>> implements KtTypeElement {
    public static final KtToken RETURN_TYPE_SEPARATOR = KtTokens.ARROW;

    /* JADX WARN: Code duplicated, block: B:19:0x002b  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 4) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "stub";
        } else if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/psi/KtFunctionType";
        } else if (i == 3) {
            objArr[0] = "visitor";
        } else if (i != 4) {
            objArr[0] = "node";
        } else {
            objArr[0] = "org/jetbrains/kotlin/psi/KtFunctionType";
        }
        if (i == 2) {
            objArr[1] = "getTypeArgumentsAsTypes";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtFunctionType";
        } else {
            objArr[1] = "getParameters";
        }
        if (i != 2) {
            if (i == 3) {
                objArr[2] = "accept";
            } else if (i != 4) {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtFunctionType(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtFunctionType(KotlinPlaceHolderStub<KtFunctionType> kotlinPlaceHolderStub) {
        super(kotlinPlaceHolderStub, KtStubElementTypes.FUNCTION_TYPE);
        if (kotlinPlaceHolderStub == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtTypeElement
    public List<KtTypeReference> getTypeArgumentsAsTypes() {
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        List<KtTypeReference> contextReceiversTypeReferences = getContextReceiversTypeReferences();
        if (contextReceiversTypeReferences != null) {
            arrayListNewArrayList.addAll(contextReceiversTypeReferences);
        }
        KtTypeReference receiverTypeReference = getReceiverTypeReference();
        if (receiverTypeReference != null) {
            arrayListNewArrayList.add(receiverTypeReference);
        }
        Iterator<KtParameter> it = getParameters().iterator();
        while (it.hasNext()) {
            arrayListNewArrayList.add(it.next().mo7396getTypeReference());
        }
        KtTypeReference returnTypeReference = getReturnTypeReference();
        if (returnTypeReference != null) {
            arrayListNewArrayList.add(returnTypeReference);
        }
        if (arrayListNewArrayList == null) {
            $$$reportNull$$$0(2);
        }
        return arrayListNewArrayList;
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(3);
        }
        return ktVisitor.visitFunctionType(this, d);
    }

    public KtParameterList getParameterList() {
        return (KtParameterList) getStubOrPsiChild(KtStubElementTypes.VALUE_PARAMETER_LIST);
    }

    public List<KtParameter> getParameters() {
        KtParameterList parameterList = getParameterList();
        List<KtParameter> parameters = parameterList != null ? parameterList.getParameters() : Collections.emptyList();
        if (parameters == null) {
            $$$reportNull$$$0(4);
        }
        return parameters;
    }

    public KtFunctionTypeReceiver getReceiver() {
        return (KtFunctionTypeReceiver) getStubOrPsiChild(KtStubElementTypes.FUNCTION_TYPE_RECEIVER);
    }

    public KtTypeReference getReceiverTypeReference() {
        KtFunctionTypeReceiver receiver = getReceiver();
        if (receiver == null) {
            return null;
        }
        return receiver.getTypeReference();
    }

    public KtContextReceiverList getContextReceiverList() {
        return (KtContextReceiverList) getStubOrPsiChild(KtStubElementTypes.CONTEXT_RECEIVER_LIST);
    }

    public List<KtTypeReference> getContextReceiversTypeReferences() {
        KtContextReceiverList contextReceiverList = getContextReceiverList();
        if (contextReceiverList != null) {
            return contextReceiverList.typeReferences();
        }
        return Collections.emptyList();
    }

    public KtTypeReference getReturnTypeReference() {
        return (KtTypeReference) getStubOrPsiChild(KtStubElementTypes.TYPE_REFERENCE);
    }
}
