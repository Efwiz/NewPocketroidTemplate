package org.jetbrains.kotlin.resolve.lazy.descriptors;

import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeImpl;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeKind;

/* JADX INFO: compiled from: ClassResolutionScopesSupport.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u001a$\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¨\u0006\t"}, d2 = {"scopeForInitializerResolution", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "classDescriptor", "Lorg/jetbrains/kotlin/resolve/lazy/descriptors/LazyClassDescriptor;", "parentDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "primaryConstructorParameters", "", "Lorg/jetbrains/kotlin/psi/KtParameter;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ClassResolutionScopesSupportKt {
    public static final LexicalScope scopeForInitializerResolution(final LazyClassDescriptor classDescriptor, DeclarationDescriptor parentDescriptor, final List<? extends KtParameter> primaryConstructorParameters) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        Intrinsics.checkNotNullParameter(parentDescriptor, "parentDescriptor");
        Intrinsics.checkNotNullParameter(primaryConstructorParameters, "primaryConstructorParameters");
        LexicalScope scopeForMemberDeclarationResolution = classDescriptor.getScopeForMemberDeclarationResolution();
        Intrinsics.checkNotNullExpressionValue(scopeForMemberDeclarationResolution, "getScopeForMemberDeclarationResolution(...)");
        return new LexicalScopeImpl(scopeForMemberDeclarationResolution, parentDescriptor, false, null, CollectionsKt.emptyList(), LexicalScopeKind.CLASS_INITIALIZER, null, new Function1<LexicalScopeImpl.InitializeHandler, Unit>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.ClassResolutionScopesSupportKt.scopeForInitializerResolution.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(LexicalScopeImpl.InitializeHandler initializeHandler) {
                invoke2(initializeHandler);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(LexicalScopeImpl.InitializeHandler $receiver) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                if (primaryConstructorParameters.isEmpty()) {
                    return;
                }
                ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = classDescriptor.mo6347getUnsubstitutedPrimaryConstructor();
                Intrinsics.checkNotNull(classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor);
                List<ValueParameterDescriptor> valueParameters = classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor.getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                valueParameters.size();
                primaryConstructorParameters.size();
                for (Pair pair : CollectionsKt.zip(primaryConstructorParameters, valueParameters)) {
                    KtParameter ktParameter = (KtParameter) pair.component1();
                    ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) pair.component2();
                    if (!ktParameter.hasValOrVar()) {
                        Intrinsics.checkNotNull(valueParameterDescriptor);
                        $receiver.addVariableDescriptor(valueParameterDescriptor);
                    }
                }
            }
        }, 64, null);
    }
}
