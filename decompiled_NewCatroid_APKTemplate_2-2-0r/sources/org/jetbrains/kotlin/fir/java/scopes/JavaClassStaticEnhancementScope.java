package org.jetbrains.kotlin.fir.java.scopes;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.java.enhancement.FirSignatureEnhancement;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: JavaClassStaticEnhancementScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0016J\u000e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0016J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\rH\u0016J.\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0011\u001a\u00020\r2\u001c\u0010\u0014\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0016\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00130\u0015H\u0016J\u001c\u0010\u0018\u001a\u00020\u00132\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u00130\u0019H\u0016J$\u0010\u001b\u001a\u00020\u00132\u0006\u0010\u0011\u001a\u00020\r2\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u00130\u0019H\u0016J(\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u0011\u001a\u00020\r2\u0016\u0010\u0014\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001e\u0012\u0004\u0012\u00020\u00130\u0019H\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/scopes/JavaClassStaticEnhancementScope;", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "owner", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "useSiteStaticScope", "Lorg/jetbrains/kotlin/fir/java/scopes/JavaClassStaticUseSiteScope;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;Lorg/jetbrains/kotlin/fir/java/scopes/JavaClassStaticUseSiteScope;)V", "signatureEnhancement", "Lorg/jetbrains/kotlin/fir/java/enhancement/FirSignatureEnhancement;", "getCallableNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getClassifierNames", "mayContainName", "", "name", "processClassifiersByNameWithSubstitution", "", "processor", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "processDeclaredConstructors", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "processFunctionsByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "processPropertiesByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaClassStaticEnhancementScope extends FirContainingNamesAwareScope {
    private final FirSignatureEnhancement signatureEnhancement;
    private final JavaClassStaticUseSiteScope useSiteStaticScope;

    /* JADX WARN: Multi-variable type inference failed */
    public JavaClassStaticEnhancementScope(FirSession session, FirRegularClassSymbol owner, JavaClassStaticUseSiteScope useSiteStaticScope) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(useSiteStaticScope, "useSiteStaticScope");
        this.useSiteStaticScope = useSiteStaticScope;
        this.signatureEnhancement = new FirSignatureEnhancement((FirRegularClass) owner.getFir(), session, new Function1<FirSimpleFunction, List<? extends FirCallableDeclaration>>() { // from class: org.jetbrains.kotlin.fir.java.scopes.JavaClassStaticEnhancementScope$signatureEnhancement$1
            @Override // kotlin.jvm.functions.Function1
            public final List<FirCallableDeclaration> invoke(FirSimpleFunction $receiver) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                return CollectionsKt.emptyList();
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processPropertiesByName(final Name name2, final Function1<? super FirVariableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.useSiteStaticScope.processPropertiesByName(name2, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.java.scopes.JavaClassStaticEnhancementScope.processPropertiesByName.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                invoke2(firVariableSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirVariableSymbol<?> original) {
                Intrinsics.checkNotNullParameter(original, "original");
                processor.invoke(this.signatureEnhancement.enhancedProperty(original, name2));
            }
        });
        super.processPropertiesByName(name2, processor);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processFunctionsByName(final Name name2, final Function1<? super FirNamedFunctionSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.useSiteStaticScope.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.java.scopes.JavaClassStaticEnhancementScope.processFunctionsByName.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                invoke2(firNamedFunctionSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirNamedFunctionSymbol original) {
                Intrinsics.checkNotNullParameter(original, "original");
                FirFunctionSymbol<?> firFunctionSymbolEnhancedFunction = JavaClassStaticEnhancementScope.this.signatureEnhancement.enhancedFunction(original, name2);
                if (firFunctionSymbolEnhancedFunction instanceof FirNamedFunctionSymbol) {
                    processor.invoke((FirNamedFunctionSymbol) firFunctionSymbolEnhancedFunction);
                }
            }
        });
        super.processFunctionsByName(name2, processor);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processClassifiersByNameWithSubstitution(Name name2, Function2<? super FirClassifierSymbol<?>, ? super ConeSubstitutor, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.useSiteStaticScope.processClassifiersByNameWithSubstitution(name2, processor);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processDeclaredConstructors(final Function1<? super FirConstructorSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.useSiteStaticScope.processDeclaredConstructors(new Function1<FirConstructorSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.java.scopes.JavaClassStaticEnhancementScope.processDeclaredConstructors.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirConstructorSymbol firConstructorSymbol) {
                invoke2(firConstructorSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirConstructorSymbol original) {
                Intrinsics.checkNotNullParameter(original, "original");
                FirFunctionSymbol<?> firFunctionSymbolEnhancedFunction = JavaClassStaticEnhancementScope.this.signatureEnhancement.enhancedFunction(original, null);
                Function1<FirConstructorSymbol, Unit> function1 = processor;
                Intrinsics.checkNotNull(firFunctionSymbolEnhancedFunction, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol");
                function1.invoke((FirConstructorSymbol) firFunctionSymbolEnhancedFunction);
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getCallableNames() {
        return this.useSiteStaticScope.getCallableNames();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getClassifierNames() {
        return this.useSiteStaticScope.getClassifierNames();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public boolean mayContainName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.useSiteStaticScope.mayContainName(name2);
    }
}
