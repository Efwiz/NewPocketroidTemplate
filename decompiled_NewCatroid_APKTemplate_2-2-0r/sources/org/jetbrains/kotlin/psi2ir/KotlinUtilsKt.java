package org.jetbrains.kotlin.psi2ir;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SyntheticPropertyDescriptor;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtPureElement;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMapping;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMatch;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: compiled from: KotlinUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\\\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\f\u0010\u0014\u001a\u00020\u0015*\u00020\u0016H\u0000\u001a\n\u0010\u0017\u001a\u00020\u0018*\u00020\u0018\u001a\u0012\u0010\u0019\u001a\u00020\u000e*\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c\u001a\u001b\u0010\u001d\u001a\u0004\u0018\u00010\u0001*\u00020\u00182\u0006\u0010\u001e\u001a\u00020\u001fH\u0000¢\u0006\u0002\u0010 \u001a\u0014\u0010!\u001a\u00020\u0015*\u00020\"2\u0006\u0010#\u001a\u00020$H\u0000\u001a\u0010\u0010%\u001a\u00020\u0015*\u0006\u0012\u0002\b\u00030&H\u0000\u001a\f\u0010'\u001a\u00020\u000f*\u00020\u000fH\u0000\"\u001a\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u001a\u0010\u0005\u001a\u00020\u0001*\u0004\u0018\u00010\u00068@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b\"\u001a\u0010\t\u001a\u00020\u0001*\u0004\u0018\u00010\u00068@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\b\"\u001a\u0010\u000b\u001a\u00020\u0001*\u0004\u0018\u00010\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u0004\"\u001a\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u000f8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011\"\u001a\u0010\u0012\u001a\u0004\u0018\u00010\u000e*\u00020\u000f8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0011¨\u0006("}, d2 = {"endOffsetOrUndefined", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getEndOffsetOrUndefined", "(Lcom/intellij/psi/PsiElement;)I", "pureEndOffsetOrUndefined", "Lorg/jetbrains/kotlin/psi/KtPureElement;", "getPureEndOffsetOrUndefined", "(Lorg/jetbrains/kotlin/psi/KtPureElement;)I", "pureStartOffsetOrUndefined", "getPureStartOffsetOrUndefined", "startOffsetOrUndefined", "getStartOffsetOrUndefined", "unwrappedGetMethod", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getUnwrappedGetMethod", "(Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;)Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "unwrappedSetMethod", "getUnwrappedSetMethod", "containsNull", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "deparenthesize", "Lorg/jetbrains/kotlin/psi/KtElement;", "findSingleFunction", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getChildTokenStartOffsetOrNull", "tokenSet", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/TokenSet;", "(Lorg/jetbrains/kotlin/psi/KtElement;Lcom/intellij/psi/tree/TokenSet;)Ljava/lang/Integer;", "isConstructorDelegatingToSuper", "Lorg/jetbrains/kotlin/psi/KtSecondaryConstructor;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "isValueArgumentReorderingRequired", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "resolveFakeOverride", "ir.psi2ir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinUtilsKt {
    public static final boolean containsNull(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return TypeUtils.isNullableType(kotlinType);
    }

    public static final KtElement deparenthesize(KtElement ktElement) {
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        if (!(ktElement instanceof KtExpression)) {
            return ktElement;
        }
        KtExpression ktExpressionSafeDeparenthesize = KtPsiUtil.safeDeparenthesize((KtExpression) ktElement);
        Intrinsics.checkNotNullExpressionValue(ktExpressionSafeDeparenthesize, "safeDeparenthesize(...)");
        return ktExpressionSafeDeparenthesize;
    }

    public static final boolean isValueArgumentReorderingRequired(ResolvedCall<?> resolvedCall) throws Exception {
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        Iterator<? extends ValueArgument> it = resolvedCall.getCall().getValueArguments().iterator();
        int i = -1;
        while (it.hasNext()) {
            ArgumentMapping argumentMapping = resolvedCall.getArgumentMapping(it.next());
            ArgumentMatch argumentMatch = argumentMapping instanceof ArgumentMatch ? (ArgumentMatch) argumentMapping : null;
            if (argumentMatch == null) {
                throw new Exception("Value argument in function call is mapped with error");
            }
            int index = argumentMatch.getValueParameter().getIndex();
            if (index < i) {
                return true;
            }
            i = index;
        }
        return false;
    }

    public static final boolean isConstructorDelegatingToSuper(KtSecondaryConstructor ktSecondaryConstructor, BindingContext bindingContext) {
        Intrinsics.checkNotNullParameter(ktSecondaryConstructor, "<this>");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        ConstructorDescriptor constructorDescriptor = (ConstructorDescriptor) bindingContext.get(BindingContext.CONSTRUCTOR, ktSecondaryConstructor);
        if (constructorDescriptor == null) {
            return false;
        }
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(ktSecondaryConstructor.getDelegationCall(), bindingContext);
        if (resolvedCall != null) {
            ClassifierDescriptorWithTypeParameters containingDeclaration = constructorDescriptor.getContainingDeclaration();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            DeclarationDescriptor containingDeclaration2 = resolvedCall.getResultingDescriptor().getContainingDeclaration();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration2, "getContainingDeclaration(...)");
            if (Intrinsics.areEqual(containingDeclaration2, containingDeclaration)) {
                return false;
            }
        } else if (constructorDescriptor.getConstructedClass().getKind() != ClassKind.ENUM_CLASS) {
            return false;
        }
        return true;
    }

    public static final FunctionDescriptor findSingleFunction(MemberScope memberScope, Name name2) {
        Intrinsics.checkNotNullParameter(memberScope, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        return (FunctionDescriptor) CollectionsKt.single(memberScope.getContributedFunctions(name2, NoLookupLocation.FROM_BACKEND));
    }

    public static final int getStartOffsetOrUndefined(PsiElement psiElement) {
        if (psiElement != null) {
            return PsiUtilsKt.getStartOffsetSkippingComments(psiElement);
        }
        return -1;
    }

    public static final int getEndOffsetOrUndefined(PsiElement psiElement) {
        if (psiElement != null) {
            return PsiUtilsKt.getEndOffset(psiElement);
        }
        return -1;
    }

    public static final FunctionDescriptor getUnwrappedGetMethod(PropertyDescriptor propertyDescriptor) {
        Intrinsics.checkNotNullParameter(propertyDescriptor, "<this>");
        return propertyDescriptor instanceof SyntheticPropertyDescriptor ? ((SyntheticPropertyDescriptor) propertyDescriptor).getGetMethod() : propertyDescriptor.getGetter();
    }

    public static final FunctionDescriptor getUnwrappedSetMethod(PropertyDescriptor propertyDescriptor) {
        Intrinsics.checkNotNullParameter(propertyDescriptor, "<this>");
        return propertyDescriptor instanceof SyntheticPropertyDescriptor ? ((SyntheticPropertyDescriptor) propertyDescriptor).getSetMethod() : propertyDescriptor.getSetter();
    }

    public static final PropertyDescriptor resolveFakeOverride(PropertyDescriptor propertyDescriptor) {
        Intrinsics.checkNotNullParameter(propertyDescriptor, "<this>");
        propertyDescriptor.getGetter();
        while (propertyDescriptor.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
            Collection<? extends PropertyDescriptor> overriddenDescriptors = propertyDescriptor.getOverriddenDescriptors();
            Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
            Iterator<T> it = overriddenDescriptors.iterator();
            Object obj = null;
            boolean z = false;
            Object obj2 = null;
            while (true) {
                if (!it.hasNext()) {
                    if (!z) {
                        break;
                    }
                    obj = obj2;
                    break;
                }
                Object next = it.next();
                DeclarationDescriptor containingDeclaration = ((PropertyDescriptor) next).getContainingDeclaration();
                Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                if (((ClassDescriptor) containingDeclaration).getKind() != ClassKind.INTERFACE) {
                    if (z) {
                        break;
                    }
                    z = true;
                    obj2 = next;
                }
            }
            PropertyDescriptor propertyDescriptor2 = (PropertyDescriptor) obj;
            if (propertyDescriptor2 == null) {
                Collection<? extends PropertyDescriptor> overriddenDescriptors2 = propertyDescriptor.getOverriddenDescriptors();
                Intrinsics.checkNotNullExpressionValue(overriddenDescriptors2, "getOverriddenDescriptors(...)");
                PropertyDescriptor propertyDescriptor3 = (PropertyDescriptor) CollectionsKt.firstOrNull(overriddenDescriptors2);
                if (propertyDescriptor3 == null) {
                    throw new IllegalStateException(("Fake override descriptor of Java field " + propertyDescriptor + " should has no overridden descriptors").toString());
                }
                propertyDescriptor = propertyDescriptor3;
            } else {
                propertyDescriptor = propertyDescriptor2;
            }
        }
        return propertyDescriptor;
    }

    public static final int getPureStartOffsetOrUndefined(KtPureElement ktPureElement) {
        KtElement psiOrParent;
        if (ktPureElement == null || (psiOrParent = ktPureElement.getPsiOrParent()) == null) {
            return -1;
        }
        return PsiUtilsKt.getStartOffsetSkippingComments(psiOrParent);
    }

    public static final int getPureEndOffsetOrUndefined(KtPureElement ktPureElement) {
        KtElement psiOrParent;
        if (ktPureElement == null || (psiOrParent = ktPureElement.getPsiOrParent()) == null) {
            return -1;
        }
        return PsiUtilsKt.getEndOffset(psiOrParent);
    }

    public static final Integer getChildTokenStartOffsetOrNull(KtElement ktElement, TokenSet tokenSet) {
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        Intrinsics.checkNotNullParameter(tokenSet, "tokenSet");
        ASTNode aSTNodeFindChildByType = ktElement.getNode().findChildByType(tokenSet);
        if (aSTNodeFindChildByType != null) {
            return Integer.valueOf(aSTNodeFindChildByType.getStartOffset());
        }
        return null;
    }
}
