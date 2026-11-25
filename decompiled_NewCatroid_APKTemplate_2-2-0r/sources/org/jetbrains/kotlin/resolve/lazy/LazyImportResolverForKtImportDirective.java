package org.jetbrains.kotlin.resolve.lazy;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.google.common.collect.HashMultimap;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.incremental.KotlinLookupLocation;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtImportDirective;
import org.jetbrains.kotlin.psi.KtImportInfo;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.LazyExplicitImportScope;
import org.jetbrains.kotlin.resolve.PlatformClassesMappedToKotlinChecker;
import org.jetbrains.kotlin.resolve.scopes.ImportingScope;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNotNull;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;

/* JADX INFO: compiled from: LazyImportScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B;\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\u0002\u0010\u000fJ\u0010\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u0010\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0002H\u0016J\b\u0010\u0019\u001a\u00020\u0012H\u0016R\u001a\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u0014X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/LazyImportResolverForKtImportDirective;", "Lorg/jetbrains/kotlin/resolve/lazy/LazyImportResolver;", "Lorg/jetbrains/kotlin/psi/KtImportDirective;", "Lorg/jetbrains/kotlin/resolve/lazy/ImportForceResolver;", "components", "Lorg/jetbrains/kotlin/resolve/lazy/ImportResolutionComponents;", "indexedImports", "Lorg/jetbrains/kotlin/resolve/lazy/IndexedImports;", "excludedImportNames", "", "Lorg/jetbrains/kotlin/name/FqName;", "traceForImportResolve", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "packageFragment", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "(Lorg/jetbrains/kotlin/resolve/lazy/ImportResolutionComponents;Lorg/jetbrains/kotlin/resolve/lazy/IndexedImports;Ljava/util/Collection;Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;)V", "forceResolveImportDirective", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNotNull;", "", "forceResolveNonDefaultImportsTask", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "checkResolvedImportDirective", "importDirective", "Lorg/jetbrains/kotlin/psi/KtImportInfo;", "forceResolveImport", "forceResolveNonDefaultImports", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LazyImportResolverForKtImportDirective extends LazyImportResolver<KtImportDirective> implements ImportForceResolver {
    private final MemoizedFunctionToNotNull<KtImportDirective, Unit> forceResolveImportDirective;
    private final NotNullLazyValue<Unit> forceResolveNonDefaultImportsTask;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LazyImportResolverForKtImportDirective(final ImportResolutionComponents components, final IndexedImports<KtImportDirective> indexedImports, Collection<FqName> excludedImportNames, final BindingTrace traceForImportResolve, PackageFragmentDescriptor packageFragmentDescriptor) {
        super(components, indexedImports, excludedImportNames, traceForImportResolve, packageFragmentDescriptor);
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(indexedImports, "indexedImports");
        Intrinsics.checkNotNullParameter(excludedImportNames, "excludedImportNames");
        Intrinsics.checkNotNullParameter(traceForImportResolve, "traceForImportResolve");
        this.forceResolveImportDirective = components.getStorageManager().createMemoizedFunction(new Function1<KtImportDirective, Unit>() { // from class: org.jetbrains.kotlin.resolve.lazy.LazyImportResolverForKtImportDirective$forceResolveImportDirective$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(KtImportDirective ktImportDirective) {
                invoke2(ktImportDirective);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(KtImportDirective directive) {
                Intrinsics.checkNotNullParameter(directive, "directive");
                ImportingScope importScope = this.this$0.getImportScope(directive);
                if (importScope instanceof LazyExplicitImportScope) {
                    PlatformClassesMappedToKotlinChecker.checkPlatformClassesMappedToKotlin(components.getPlatformToKotlinClassMapper(), traceForImportResolve, directive, ((LazyExplicitImportScope) importScope).storeReferencesToDescriptors$frontend());
                }
            }
        });
        this.forceResolveNonDefaultImportsTask = components.getStorageManager().createLazyValue(new Function0<Unit>() { // from class: org.jetbrains.kotlin.resolve.lazy.LazyImportResolverForKtImportDirective$forceResolveNonDefaultImportsTask$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                HashMultimap hashMultimapCreate = HashMultimap.create();
                for (KtImportDirective ktImportDirective : (KtImportDirective[]) indexedImports.getImports()) {
                    this.forceResolveImport(ktImportDirective);
                    KtImportDirective ktImportDirective2 = ktImportDirective;
                    ImportingScope importScope = this.getImportScope(ktImportDirective2);
                    Name importedName = ktImportDirective.getImportedName();
                    if (importedName != null && importScope.mo7465getContributedClassifier(importedName, new KotlinLookupLocation(ktImportDirective)) != null) {
                        hashMultimapCreate.put(importedName.asString(), ktImportDirective);
                    }
                    this.checkResolvedImportDirective(ktImportDirective2);
                }
                for (Map.Entry entry : hashMultimapCreate.entries()) {
                    Intrinsics.checkNotNull(entry);
                    String str = (String) entry.getKey();
                    KtImportDirective ktImportDirective3 = (KtImportDirective) entry.getValue();
                    Intrinsics.checkNotNull(str);
                    String str2 = str;
                    int i = 0;
                    while (true) {
                        if (i >= str2.length()) {
                            traceForImportResolve.report(Errors.UNDERSCORE_IS_RESERVED.on(ktImportDirective3));
                            break;
                        } else if (str2.charAt(i) != '_') {
                            break;
                        } else {
                            i++;
                        }
                    }
                }
                for (String str3 : hashMultimapCreate.keySet()) {
                    Set set = hashMultimapCreate.get((Object) str3);
                    if (set.size() > 1) {
                        Intrinsics.checkNotNull(set);
                        BindingTrace bindingTrace = traceForImportResolve;
                        Iterator it = set.iterator();
                        while (it.hasNext()) {
                            bindingTrace.report(Errors.CONFLICTING_IMPORT.on((KtImportDirective) it.next(), str3));
                        }
                    }
                }
            }
        });
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.ImportForceResolver
    public void forceResolveNonDefaultImports() {
        this.forceResolveNonDefaultImportsTask.invoke();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkResolvedImportDirective(KtImportInfo importDirective) {
        KtExpression importedReference;
        KtSimpleNameExpression lastReference;
        DeclarationDescriptor declarationDescriptor;
        if (!(importDirective instanceof KtImportDirective) || (importedReference = ((KtImportDirective) importDirective).getImportedReference()) == null || (lastReference = KtPsiUtil.getLastReference(importedReference)) == null || (declarationDescriptor = (DeclarationDescriptor) getTraceForImportResolve().getContext().get(BindingContext.REFERENCE_TARGET, lastReference)) == null) {
            return;
        }
        String aliasName = importDirective.getAliasName();
        if ((declarationDescriptor instanceof FunctionDescriptor) && ((FunctionDescriptor) declarationDescriptor).isOperator() && aliasName != null && OperatorConventions.isConventionName(Name.identifier(aliasName))) {
            getTraceForImportResolve().report(Errors.OPERATOR_RENAMED_ON_IMPORT.on(lastReference));
        }
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.ImportForceResolver
    public void forceResolveImport(KtImportDirective importDirective) {
        Intrinsics.checkNotNullParameter(importDirective, "importDirective");
        this.forceResolveImportDirective.invoke(importDirective);
    }
}
