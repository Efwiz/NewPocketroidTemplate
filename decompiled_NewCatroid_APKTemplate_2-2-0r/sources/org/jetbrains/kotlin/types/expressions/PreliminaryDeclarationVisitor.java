package org.jetbrains.kotlin.types.expressions;

import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;

/* JADX INFO: compiled from: PreliminaryDeclarationVisitor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000e¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/PreliminaryDeclarationVisitor;", "Lorg/jetbrains/kotlin/types/expressions/AssignedVariablesSearcher;", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/psi/KtDeclaration;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "getDeclaration", "()Lorg/jetbrains/kotlin/psi/KtDeclaration;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "lazyTrigger", "", "getLazyTrigger", "()Lkotlin/Unit;", "lazyTrigger$delegate", "Lkotlin/Lazy;", "writers", "", "Lorg/jetbrains/kotlin/types/expressions/AssignedVariablesSearcher$Writer;", "variableDescriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PreliminaryDeclarationVisitor extends AssignedVariablesSearcher {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final KtDeclaration declaration;
    private final LanguageVersionSettings languageVersionSettings;

    /* JADX INFO: renamed from: lazyTrigger$delegate, reason: from kotlin metadata */
    private final Lazy lazyTrigger;

    public final KtDeclaration getDeclaration() {
        return this.declaration;
    }

    public final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    public PreliminaryDeclarationVisitor(KtDeclaration declaration, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.declaration = declaration;
        this.languageVersionSettings = languageVersionSettings;
        this.lazyTrigger = LazyKt.lazy(new Function0<Unit>() { // from class: org.jetbrains.kotlin.types.expressions.PreliminaryDeclarationVisitor$lazyTrigger$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                this.this$0.getDeclaration().accept(this.this$0);
            }
        });
    }

    @Override // org.jetbrains.kotlin.types.expressions.AssignedVariablesSearcher
    public Set<AssignedVariablesSearcher.Writer> writers(VariableDescriptor variableDescriptor) {
        Intrinsics.checkNotNullParameter(variableDescriptor, "variableDescriptor");
        getLazyTrigger();
        return super.writers(variableDescriptor);
    }

    private final Unit getLazyTrigger() {
        this.lazyTrigger.getValue();
        return Unit.INSTANCE;
    }

    /* JADX INFO: compiled from: PreliminaryDeclarationVisitor.kt */
    @Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u001e\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0018\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013J\u0015\u0010\u0014\u001a\u00070\u0006¢\u0006\u0002\b\u00152\u0006\u0010\u0005\u001a\u00020\u0006H\u0002¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/PreliminaryDeclarationVisitor$Companion;", "", "()V", "createForDeclaration", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "createForExpression", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getVisitorByVariable", "Lorg/jetbrains/kotlin/types/expressions/PreliminaryDeclarationVisitor;", "variableDescriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "topMostNonClassDeclaration", "Lkotlin/internal/NoInfer;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void createForExpression(KtExpression expression, BindingTrace trace, LanguageVersionSettings languageVersionSettings) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            KtDeclaration ktDeclaration = (KtDeclaration) PsiTreeUtil.getParentOfType(expression, KtDeclaration.class, true);
            if (ktDeclaration != null) {
                PreliminaryDeclarationVisitor.INSTANCE.createForDeclaration(ktDeclaration, trace, languageVersionSettings);
            }
        }

        private final KtDeclaration topMostNonClassDeclaration(KtDeclaration declaration) {
            Sequence sequenceFilter = SequencesKt.filter(PsiUtilsKt.getParentsWithSelf(declaration), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.types.expressions.PreliminaryDeclarationVisitor$Companion$topMostNonClassDeclaration$$inlined$filterIsInstance$1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(Object obj) {
                    return Boolean.valueOf(obj instanceof KtDeclaration);
                }
            });
            Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
            Object obj = null;
            for (Object obj2 : sequenceFilter) {
                if (!(((KtDeclaration) obj2) instanceof KtClassOrObject)) {
                    obj = obj2;
                }
            }
            KtDeclaration ktDeclaration = (KtDeclaration) obj;
            return ktDeclaration == null ? declaration : ktDeclaration;
        }

        public final void createForDeclaration(KtDeclaration declaration, BindingTrace trace, LanguageVersionSettings languageVersionSettings) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            KtDeclaration ktDeclaration = topMostNonClassDeclaration(declaration);
            if (trace.get(BindingContext.PRELIMINARY_VISITOR, ktDeclaration) != null) {
                return;
            }
            trace.record(BindingContext.PRELIMINARY_VISITOR, ktDeclaration, new PreliminaryDeclarationVisitor(ktDeclaration, languageVersionSettings));
        }

        public final PreliminaryDeclarationVisitor getVisitorByVariable(VariableDescriptor variableDescriptor, BindingContext bindingContext) {
            Intrinsics.checkNotNullParameter(variableDescriptor, "variableDescriptor");
            Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
            DeclarationDescriptor containingDeclaration = variableDescriptor.getDeclarationDescriptor();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(containingDeclaration);
            KtDeclaration ktDeclaration = psiElementDescriptorToDeclaration instanceof KtDeclaration ? (KtDeclaration) psiElementDescriptorToDeclaration : null;
            if (ktDeclaration == null) {
                return null;
            }
            PreliminaryDeclarationVisitor preliminaryDeclarationVisitor = (PreliminaryDeclarationVisitor) bindingContext.get(BindingContext.PRELIMINARY_VISITOR, ktDeclaration);
            while (preliminaryDeclarationVisitor == null && ktDeclaration != null) {
                ktDeclaration = (KtDeclaration) PsiTreeUtil.getParentOfType(ktDeclaration, KtDeclaration.class, true);
                preliminaryDeclarationVisitor = (PreliminaryDeclarationVisitor) bindingContext.get(BindingContext.PRELIMINARY_VISITOR, ktDeclaration);
            }
            return preliminaryDeclarationVisitor;
        }
    }
}
