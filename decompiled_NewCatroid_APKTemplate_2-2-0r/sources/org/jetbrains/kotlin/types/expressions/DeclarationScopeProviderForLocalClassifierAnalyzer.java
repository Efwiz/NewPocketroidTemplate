package org.jetbrains.kotlin.types.expressions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.lazy.DeclarationScopeProviderImpl;
import org.jetbrains.kotlin.resolve.lazy.FileScopeProvider;
import org.jetbrains.kotlin.resolve.lazy.LazyDeclarationResolver;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;

/* JADX INFO: compiled from: LocalClassifierAnalyzer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/DeclarationScopeProviderForLocalClassifierAnalyzer;", "Lorg/jetbrains/kotlin/resolve/lazy/DeclarationScopeProviderImpl;", "lazyDeclarationResolver", "Lorg/jetbrains/kotlin/resolve/lazy/LazyDeclarationResolver;", "fileScopeProvider", "Lorg/jetbrains/kotlin/resolve/lazy/FileScopeProvider;", "localClassDescriptorManager", "Lorg/jetbrains/kotlin/types/expressions/LocalClassDescriptorHolder;", "(Lorg/jetbrains/kotlin/resolve/lazy/LazyDeclarationResolver;Lorg/jetbrains/kotlin/resolve/lazy/FileScopeProvider;Lorg/jetbrains/kotlin/types/expressions/LocalClassDescriptorHolder;)V", "getOuterDataFlowInfoForDeclaration", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "elementOfDeclaration", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getResolutionScopeForDeclaration", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeclarationScopeProviderForLocalClassifierAnalyzer extends DeclarationScopeProviderImpl {
    private final LocalClassDescriptorHolder localClassDescriptorManager;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeclarationScopeProviderForLocalClassifierAnalyzer(LazyDeclarationResolver lazyDeclarationResolver, FileScopeProvider fileScopeProvider, LocalClassDescriptorHolder localClassDescriptorManager) {
        super(lazyDeclarationResolver, fileScopeProvider);
        Intrinsics.checkNotNullParameter(lazyDeclarationResolver, "lazyDeclarationResolver");
        Intrinsics.checkNotNullParameter(fileScopeProvider, "fileScopeProvider");
        Intrinsics.checkNotNullParameter(localClassDescriptorManager, "localClassDescriptorManager");
        this.localClassDescriptorManager = localClassDescriptorManager;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.DeclarationScopeProviderImpl, org.jetbrains.kotlin.resolve.lazy.DeclarationScopeProvider
    public LexicalScope getResolutionScopeForDeclaration(PsiElement elementOfDeclaration) {
        Intrinsics.checkNotNullParameter(elementOfDeclaration, "elementOfDeclaration");
        if (this.localClassDescriptorManager.isMyClass(elementOfDeclaration)) {
            return this.localClassDescriptorManager.getResolutionScopeForClass((KtClassOrObject) elementOfDeclaration);
        }
        LexicalScope resolutionScopeForDeclaration = super.getResolutionScopeForDeclaration(elementOfDeclaration);
        Intrinsics.checkNotNullExpressionValue(resolutionScopeForDeclaration, "getResolutionScopeForDeclaration(...)");
        return resolutionScopeForDeclaration;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.DeclarationScopeProviderImpl, org.jetbrains.kotlin.resolve.lazy.DeclarationScopeProvider
    public DataFlowInfo getOuterDataFlowInfoForDeclaration(PsiElement elementOfDeclaration) {
        Intrinsics.checkNotNullParameter(elementOfDeclaration, "elementOfDeclaration");
        if (this.localClassDescriptorManager.insideMyClass(elementOfDeclaration)) {
            DataFlowInfo dataFlowInfo = this.localClassDescriptorManager.getExpressionTypingContext().dataFlowInfo;
            Intrinsics.checkNotNullExpressionValue(dataFlowInfo, "dataFlowInfo");
            return dataFlowInfo;
        }
        DataFlowInfo outerDataFlowInfoForDeclaration = super.getOuterDataFlowInfoForDeclaration(elementOfDeclaration);
        Intrinsics.checkNotNullExpressionValue(outerDataFlowInfoForDeclaration, "getOuterDataFlowInfoForDeclaration(...)");
        return outerDataFlowInfoForDeclaration;
    }
}
