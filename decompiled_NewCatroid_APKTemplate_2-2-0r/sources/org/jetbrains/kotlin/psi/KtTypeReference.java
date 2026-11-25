package org.jetbrains.kotlin.psi;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;
import org.jetbrains.kotlin.psi.stubs.elements.KtTokenSets;

/* JADX INFO: compiled from: KtTypeReference.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00000\u00020\u00012\u00020\u00032\u00020\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007B\u0015\b\u0016\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00000\u0002¢\u0006\u0002\u0010\tJ5\u0010\u0011\u001a\u0002H\u0012\"\u0004\b\u0000\u0010\u0012\"\u0004\b\u0001\u0010\u00132\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u0002H\u0012\u0012\u0004\u0012\u0002H\u00130\u00152\u0006\u0010\u0016\u001a\u0002H\u0013H\u0016¢\u0006\u0002\u0010\u0017J\u000e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019H\u0016J\u000e\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0019H\u0016J\u0012\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0002J\u0006\u0010!\u001a\u00020\u001eJ\u0014\u0010!\u001a\u0004\u0018\u00010\u001e2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0002J\u0006\u0010\"\u001a\u00020\u000bJ\b\u0010#\u001a\u0004\u0018\u00010\u001eR\u0011\u0010\n\u001a\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\n\u0010\fR\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtTypeReference;", "Lorg/jetbrains/kotlin/psi/KtModifierListOwnerStub;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinPlaceHolderStub;", "Lorg/jetbrains/kotlin/psi/KtAnnotated;", "Lorg/jetbrains/kotlin/psi/KtAnnotationsContainer;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "stub", "(Lorg/jetbrains/kotlin/psi/stubs/KotlinPlaceHolderStub;)V", "isPlaceholder", "", "()Z", "typeElement", "Lorg/jetbrains/kotlin/psi/KtTypeElement;", "getTypeElement", "()Lorg/jetbrains/kotlin/psi/KtTypeElement;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "data", "(Lorg/jetbrains/kotlin/psi/KtVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "getAnnotationEntries", "", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "getAnnotations", "Lorg/jetbrains/kotlin/psi/KtAnnotation;", "getQualifiedName", "", "userType", "Lorg/jetbrains/kotlin/psi/KtUserType;", "getTypeText", "hasParentheses", "nameForReceiverLabel", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtTypeReference extends KtModifierListOwnerStub<KotlinPlaceHolderStub<KtTypeReference>> implements KtAnnotated, KtAnnotationsContainer {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtTypeReference(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtTypeReference(KotlinPlaceHolderStub<KtTypeReference> stub) {
        super(stub, KtStubElementTypes.TYPE_REFERENCE);
        Intrinsics.checkNotNullParameter(stub, "stub");
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitTypeReference(this, data);
    }

    public final boolean isPlaceholder() {
        KtTypeElement typeElement = getTypeElement();
        KtUserType ktUserType = typeElement instanceof KtUserType ? (KtUserType) typeElement : null;
        KtSimpleNameExpression referenceExpression = ktUserType != null ? ktUserType.getReferenceExpression() : null;
        KtNameReferenceExpression ktNameReferenceExpression = referenceExpression instanceof KtNameReferenceExpression ? (KtNameReferenceExpression) referenceExpression : null;
        return ktNameReferenceExpression != null && ktNameReferenceExpression.isPlaceholder();
    }

    public final KtTypeElement getTypeElement() {
        return (KtTypeElement) KtStubbedPsiUtil.getStubOrPsiChild(this, KtTokenSets.TYPE_ELEMENT_TYPES, KtTypeElement.ARRAY_FACTORY);
    }

    @Override // org.jetbrains.kotlin.psi.KtModifierListOwnerStub, org.jetbrains.kotlin.psi.KtAnnotated
    public List<KtAnnotation> getAnnotations() {
        KtModifierList modifierList = getModifierList();
        List<KtAnnotation> annotations = modifierList != null ? modifierList.getAnnotations() : null;
        return annotations == null ? CollectionsKt.emptyList() : annotations;
    }

    @Override // org.jetbrains.kotlin.psi.KtModifierListOwnerStub, org.jetbrains.kotlin.psi.KtAnnotated
    public List<KtAnnotationEntry> getAnnotationEntries() {
        KtModifierList modifierList = getModifierList();
        List<KtAnnotationEntry> annotationEntries = modifierList != null ? modifierList.getAnnotationEntries() : null;
        return annotationEntries == null ? CollectionsKt.emptyList() : annotationEntries;
    }

    public final boolean hasParentheses() {
        return (findChildByType(KtTokens.LPAR) == null || findChildByType(KtTokens.RPAR) == null) ? false : true;
    }

    public final String nameForReceiverLabel() {
        KtTypeElement typeElement = getTypeElement();
        KtUserType ktUserType = typeElement instanceof KtUserType ? (KtUserType) typeElement : null;
        if (ktUserType != null) {
            return ktUserType.getReferencedName();
        }
        return null;
    }

    public final String getTypeText() {
        String typeText;
        if (((KotlinPlaceHolderStub) getStub()) != null && (typeText = getTypeText(getTypeElement())) != null) {
            return typeText;
        }
        String text = getText();
        Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
        return text;
    }

    private final String getQualifiedName(KtUserType userType) {
        KtUserType qualifier = userType.getQualifier();
        return qualifier == null ? userType.getReferencedName() : getQualifiedName(qualifier) + '.' + userType.getReferencedName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String getTypeText(KtTypeElement typeElement) {
        if (typeElement instanceof KtUserType) {
            StringBuilder sb = new StringBuilder();
            KtUserType ktUserType = (KtUserType) typeElement;
            sb.append(getQualifiedName(ktUserType));
            List<KtTypeProjection> typeArguments = ktUserType.getTypeArguments();
            Intrinsics.checkNotNullExpressionValue(typeArguments, "getTypeArguments(...)");
            if (!typeArguments.isEmpty()) {
                sb.append(CollectionsKt.joinToString$default(typeArguments, ", ", "<", ">", 0, null, new Function1<KtTypeProjection, CharSequence>() { // from class: org.jetbrains.kotlin.psi.KtTypeReference$getTypeText$2$1

                    /* JADX INFO: compiled from: KtTypeReference.kt */
                    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
                    public /* synthetic */ class WhenMappings {
                        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                        static {
                            int[] iArr = new int[KtProjectionKind.values().length];
                            try {
                                iArr[KtProjectionKind.IN.ordinal()] = 1;
                            } catch (NoSuchFieldError unused) {
                            }
                            try {
                                iArr[KtProjectionKind.OUT.ordinal()] = 2;
                            } catch (NoSuchFieldError unused2) {
                            }
                            try {
                                iArr[KtProjectionKind.STAR.ordinal()] = 3;
                            } catch (NoSuchFieldError unused3) {
                            }
                            try {
                                iArr[KtProjectionKind.NONE.ordinal()] = 4;
                            } catch (NoSuchFieldError unused4) {
                            }
                            $EnumSwitchMapping$0 = iArr;
                        }
                    }

                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(KtTypeProjection ktTypeProjection) {
                        String str;
                        int i = WhenMappings.$EnumSwitchMapping$0[ktTypeProjection.getProjectionKind().ordinal()];
                        if (i == 1) {
                            str = "in ";
                        } else if (i == 2) {
                            str = "out ";
                        } else if (i == 3) {
                            str = "*";
                        } else {
                            if (i != 4) {
                                throw new NoWhenBranchMatchedException();
                            }
                            str = "";
                        }
                        StringBuilder sbAppend = new StringBuilder().append(str);
                        KtTypeReference ktTypeReference = this.this$0;
                        KtTypeReference typeReference = ktTypeProjection.getTypeReference();
                        String typeText = ktTypeReference.getTypeText(typeReference != null ? typeReference.getTypeElement() : null);
                        return sbAppend.append(typeText != null ? typeText : "").toString();
                    }
                }, 24, null));
            }
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return string;
        }
        if (typeElement instanceof KtFunctionType) {
            StringBuilder sb2 = new StringBuilder();
            KtFunctionType ktFunctionType = (KtFunctionType) typeElement;
            List<KtTypeReference> contextReceiversTypeReferences = ktFunctionType.getContextReceiversTypeReferences();
            Intrinsics.checkNotNull(contextReceiversTypeReferences);
            if (!contextReceiversTypeReferences.isEmpty()) {
                sb2.append(CollectionsKt.joinToString$default(contextReceiversTypeReferences, ", ", "context(", Constant.AFTER_QUTO, 0, null, new Function1<KtTypeReference, CharSequence>() { // from class: org.jetbrains.kotlin.psi.KtTypeReference$getTypeText$3$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(KtTypeReference ktTypeReference) {
                        String typeText = this.this$0.getTypeText(ktTypeReference.getTypeElement());
                        if (typeText == null) {
                            typeText = "";
                        }
                        return typeText;
                    }
                }, 24, null));
            }
            KtTypeReference receiverTypeReference = ktFunctionType.getReceiverTypeReference();
            if (receiverTypeReference != null) {
                sb2.append(getTypeText(receiverTypeReference.getTypeElement()));
            }
            List<KtParameter> parameters = ktFunctionType.getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
            sb2.append(CollectionsKt.joinToString$default(parameters, ", ", "(", Constant.AFTER_QUTO, 0, null, new Function1<KtParameter, CharSequence>() { // from class: org.jetbrains.kotlin.psi.KtTypeReference$getTypeText$3$3
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(KtParameter ktParameter) {
                    StringBuilder sbAppend = new StringBuilder().append(ktParameter.getName()).append(": ");
                    KtTypeReference ktTypeReferenceMo7396getTypeReference = ktParameter.mo7396getTypeReference();
                    return sbAppend.append(ktTypeReferenceMo7396getTypeReference != null ? ktTypeReferenceMo7396getTypeReference.getTypeText() : null).toString();
                }
            }, 24, null));
            KtTypeReference returnTypeReference = ktFunctionType.getReturnTypeReference();
            if (returnTypeReference != null) {
                sb2.append(" -> ");
                sb2.append(getTypeText(returnTypeReference.getTypeElement()));
            }
            String string2 = sb2.toString();
            Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
            return string2;
        }
        if (!(typeElement instanceof KtIntersectionType)) {
            if (typeElement instanceof KtNullableType) {
                return getTypeText(((KtNullableType) typeElement).getInnerType()) + MangleConstant.Q_MARK;
            }
            if (typeElement == null) {
                return null;
            }
            throw new IllegalStateException(("Unsupported type " + typeElement).toString());
        }
        StringBuilder sb3 = new StringBuilder();
        KtIntersectionType ktIntersectionType = (KtIntersectionType) typeElement;
        KtTypeReference leftTypeRef = ktIntersectionType.getLeftTypeRef();
        StringBuilder sbAppend = sb3.append(getTypeText(leftTypeRef != null ? leftTypeRef.getTypeElement() : null)).append(" & ");
        KtTypeReference rightTypeRef = ktIntersectionType.getRightTypeRef();
        return sbAppend.append(getTypeText(rightTypeRef != null ? rightTypeRef.getTypeElement() : null)).toString();
    }
}
