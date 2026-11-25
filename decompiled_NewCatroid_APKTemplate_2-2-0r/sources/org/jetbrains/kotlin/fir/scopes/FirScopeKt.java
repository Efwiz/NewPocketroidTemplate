package org.jetbrains.kotlin.fir.scopes;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003\u001a\u0018\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00050\u0001*\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007\u001a\u001c\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t0\u0001*\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007\u001a\u0018\u0010\n\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000b*\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007\u001a2\u0010\f\u001a\u00020\r*\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0018\b\b\u0010\u000e\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000b\u0012\u0004\u0012\u00020\r0\u000fH\u0086\bø\u0001\u0000\u001a&\u0010\u0010\u001a\u00020\u0011*\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00052\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00110\u000f\u001a,\u0010\u0014\u001a\u00020\r*\b\u0012\u0004\u0012\u00020\u00120\u00012\u0006\u0010\u0015\u001a\u00020\u00162\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00110\u000f\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0017"}, d2 = {"getDeclaredConstructors", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "getFunctions", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getProperties", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "getSingleClassifier", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "processClassifiersByName", "", "processor", "Lkotlin/Function1;", "processOverriddenFunctionsAndSelf", "Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "functionSymbol", "processOverriddenPropertiesAndSelf", "propertySymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirScopeKt {
    public static final FirClassifierSymbol<?> getSingleClassifier(FirScope firScope, Name name2) {
        Intrinsics.checkNotNullParameter(firScope, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        firScope.processClassifiersByNameWithSubstitution(name2, new AnonymousClass1(new FirScopeKt$getSingleClassifier$1$1(linkedHashSet)));
        return (FirClassifierSymbol) CollectionsKt.singleOrNull(linkedHashSet);
    }

    public static final List<FirNamedFunctionSymbol> getFunctions(FirScope firScope, Name name2) {
        Intrinsics.checkNotNullParameter(firScope, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        ArrayList arrayList = new ArrayList();
        firScope.processFunctionsByName(name2, new FirScopeKt$getFunctions$1$1(arrayList));
        return arrayList;
    }

    public static final List<FirVariableSymbol<?>> getProperties(FirScope firScope, Name name2) {
        Intrinsics.checkNotNullParameter(firScope, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        ArrayList arrayList = new ArrayList();
        firScope.processPropertiesByName(name2, new FirScopeKt$getProperties$1$1(arrayList));
        return arrayList;
    }

    public static final List<FirConstructorSymbol> getDeclaredConstructors(FirScope firScope) {
        Intrinsics.checkNotNullParameter(firScope, "<this>");
        ArrayList arrayList = new ArrayList();
        firScope.processDeclaredConstructors(new FirScopeKt$getDeclaredConstructors$1$1(arrayList));
        return arrayList;
    }

    public static final ProcessorAction processOverriddenFunctionsAndSelf(FirTypeScope firTypeScope, FirNamedFunctionSymbol functionSymbol, Function1<? super FirNamedFunctionSymbol, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(firTypeScope, "<this>");
        Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
        Intrinsics.checkNotNullParameter(processor, "processor");
        return processor.invoke(functionSymbol).not() ? ProcessorAction.STOP : FirTypeScopeKt.processOverriddenFunctions(firTypeScope, functionSymbol, processor);
    }

    public static final void processOverriddenPropertiesAndSelf(List<? extends FirTypeScope> list, FirPropertySymbol propertySymbol, Function1<? super FirPropertySymbol, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
        Intrinsics.checkNotNullParameter(processor, "processor");
        if (processor.invoke(propertySymbol).not()) {
            return;
        }
        FirTypeScopeKt.processOverriddenProperties(list, propertySymbol, processor);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.scopes.FirScopeKt$processClassifiersByName$1, reason: invalid class name */
    /* JADX INFO: compiled from: FirScope.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\n¢\u0006\u0002\b\u0006"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "<anonymous parameter 1>", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 176)
    public static final class AnonymousClass1 extends Lambda implements Function2<FirClassifierSymbol<?>, ConeSubstitutor, Unit> {
        final /* synthetic */ Function1<FirClassifierSymbol<?>, Unit> $processor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass1(Function1<? super FirClassifierSymbol<?>, Unit> function1) {
            super(2);
            this.$processor = function1;
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(FirClassifierSymbol<?> firClassifierSymbol, ConeSubstitutor coneSubstitutor) {
            invoke2(firClassifierSymbol, coneSubstitutor);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(FirClassifierSymbol<?> symbol, ConeSubstitutor coneSubstitutor) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            Intrinsics.checkNotNullParameter(coneSubstitutor, "<anonymous parameter 1>");
            this.$processor.invoke(symbol);
        }
    }

    public static final void processClassifiersByName(FirScope firScope, Name name2, Function1<? super FirClassifierSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(firScope, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        firScope.processClassifiersByNameWithSubstitution(name2, new AnonymousClass1(processor));
    }
}
