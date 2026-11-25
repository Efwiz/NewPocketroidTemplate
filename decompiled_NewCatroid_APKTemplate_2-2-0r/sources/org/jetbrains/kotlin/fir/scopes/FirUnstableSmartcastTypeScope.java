package org.jetbrains.kotlin.fir.scopes;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirUnstableSmartcastTypeScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0001¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0016J\u000e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0016J\u0012\u0010\u0014\u001a\u00020\u00152\n\u0010\u0016\u001a\u0006\u0012\u0002\b\u00030\u0017J\u0012\u0010\u0018\u001a\u00020\u00192\n\u0010\u0016\u001a\u0006\u0012\u0002\b\u00030\u000fJ.\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u00122\u001c\u0010\u001c\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u00190\u001dH\u0016Jf\u0010 \u001a\u00020\u0019\"\f\b\u0000\u0010!*\u0006\u0012\u0002\b\u00030\u000f2/\u0010\"\u001a+\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u0012\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H!\u0012\u0004\u0012\u00020\u00190$\u0012\u0004\u0012\u00020\u00190#¢\u0006\u0002\b%2\u0006\u0010\u001b\u001a\u00020\u00122\u0014\b\b\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u0002H!\u0012\u0004\u0012\u00020\u00190$H\u0082\bJ*\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)2\u0018\u0010\u001c\u001a\u0014\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020'0\u001dH\u0016J*\u0010*\u001a\u00020'2\u0006\u0010+\u001a\u00020,2\u0018\u0010\u001c\u001a\u0014\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020'0\u001dH\u0016J$\u0010-\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u00122\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020\u00190$H\u0016J(\u0010.\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u00122\u0016\u0010\u001c\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030/\u0012\u0004\u0012\u00020\u00190$H\u0016J}\u00100\u001a\u00020'\"\u0004\b\u0000\u00101\"\f\b\u0001\u0010!*\u0006\u0012\u0002\b\u00030\u000f25\u0010\"\u001a1\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u0002H1\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u0002H!\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020'0\u001d\u0012\u0004\u0012\u00020'0#¢\u0006\u0002\b%2\u0006\u0010\u001b\u001a\u0002H12\u001a\b\b\u0010\u001c\u001a\u0014\u0012\u0004\u0012\u0002H!\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020'0\u001dH\u0082\b¢\u0006\u0002\u00102R\u000e\u0010\u0003\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\r\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000f0\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00063"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/FirUnstableSmartcastTypeScope;", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "smartcastScope", "originalScope", "(Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;)V", "scopeOwnerLookupNames", "", "", "getScopeOwnerLookupNames", "()Ljava/util/List;", "scopeOwnerLookupNames$delegate", "Lkotlin/Lazy;", "scopes", "symbolsFromUnstableSmartcast", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "getCallableNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getClassifierNames", "isSymbolFromUnstableSmartcast", "", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "markSymbolFromUnstableSmartcast", "", "processClassifiersByNameWithSubstitution", "name", "processor", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "processComposite", ExifInterface.GPS_DIRECTION_TRUE, "process", "Lkotlin/Function3;", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "processDirectOverriddenFunctionsWithBaseScope", "Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", "functionSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "processDirectOverriddenPropertiesWithBaseScope", "propertySymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "processFunctionsByName", "processPropertiesByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "processTypedComposite", "N", "(Lkotlin/jvm/functions/Function3;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirUnstableSmartcastTypeScope extends FirTypeScope {
    private final FirTypeScope originalScope;

    /* JADX INFO: renamed from: scopeOwnerLookupNames$delegate, reason: from kotlin metadata */
    private final Lazy scopeOwnerLookupNames;
    private final List<FirTypeScope> scopes;
    private final FirTypeScope smartcastScope;
    private final Set<FirCallableSymbol<?>> symbolsFromUnstableSmartcast;

    public FirUnstableSmartcastTypeScope(FirTypeScope smartcastScope, FirTypeScope originalScope) {
        Intrinsics.checkNotNullParameter(smartcastScope, "smartcastScope");
        Intrinsics.checkNotNullParameter(originalScope, "originalScope");
        this.smartcastScope = smartcastScope;
        this.originalScope = originalScope;
        this.scopes = CollectionsKt.listOf((Object[]) new FirTypeScope[]{originalScope, smartcastScope});
        this.symbolsFromUnstableSmartcast = new LinkedHashSet();
        this.scopeOwnerLookupNames = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<List<? extends String>>() { // from class: org.jetbrains.kotlin.fir.scopes.FirUnstableSmartcastTypeScope$scopeOwnerLookupNames$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends String> invoke() {
                List list = this.this$0.scopes;
                ArrayList arrayList = new ArrayList();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    CollectionsKt.addAll(arrayList, ((FirTypeScope) it.next()).getScopeOwnerLookupNames());
                }
                return arrayList;
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processClassifiersByNameWithSubstitution(Name name2, Function2<? super FirClassifierSymbol<?>, ? super ConeSubstitutor, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        Iterator<FirTypeScope> it = this.scopes.iterator();
        while (it.hasNext()) {
            it.next().processClassifiersByNameWithSubstitution(name2, processor);
        }
    }

    public final boolean isSymbolFromUnstableSmartcast(FirBasedSymbol<?> symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return CollectionsKt.contains(this.symbolsFromUnstableSmartcast, symbol);
    }

    public final void markSymbolFromUnstableSmartcast(FirCallableSymbol<?> symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.symbolsFromUnstableSmartcast.add(symbol);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getCallableNames() {
        List<FirTypeScope> list = this.scopes;
        HashSet hashSet = new HashSet();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(hashSet, ((FirTypeScope) it.next()).getCallableNames());
        }
        return hashSet;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getClassifierNames() {
        List<FirTypeScope> list = this.scopes;
        HashSet hashSet = new HashSet();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(hashSet, ((FirTypeScope) it.next()).getClassifierNames());
        }
        return hashSet;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public List<String> getScopeOwnerLookupNames() {
        return (List) this.scopeOwnerLookupNames.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processFunctionsByName(Name name2, Function1<? super FirNamedFunctionSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        this.originalScope.processFunctionsByName(name2, new FirUnstableSmartcastTypeScope$processComposite$1(linkedHashSet, processor));
        this.smartcastScope.processFunctionsByName(name2, new FirUnstableSmartcastTypeScope$processComposite$2(linkedHashSet, this, processor));
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processPropertiesByName(Name name2, Function1<? super FirVariableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        this.originalScope.processPropertiesByName(name2, new FirUnstableSmartcastTypeScope$processComposite$1(linkedHashSet, processor));
        this.smartcastScope.processPropertiesByName(name2, new FirUnstableSmartcastTypeScope$processComposite$2(linkedHashSet, this, processor));
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirTypeScope
    public ProcessorAction processDirectOverriddenFunctionsWithBaseScope(FirNamedFunctionSymbol functionSymbol, Function2<? super FirNamedFunctionSymbol, ? super FirTypeScope, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
        Intrinsics.checkNotNullParameter(processor, "processor");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        if (this.originalScope.processDirectOverriddenFunctionsWithBaseScope(functionSymbol, new FirUnstableSmartcastTypeScope$processTypedComposite$1(linkedHashSet, processor)) != ProcessorAction.STOP && this.smartcastScope.processDirectOverriddenFunctionsWithBaseScope(functionSymbol, new FirUnstableSmartcastTypeScope$processTypedComposite$3(linkedHashSet, this, processor)) != ProcessorAction.STOP) {
            return ProcessorAction.NEXT;
        }
        return ProcessorAction.STOP;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirTypeScope
    public ProcessorAction processDirectOverriddenPropertiesWithBaseScope(FirPropertySymbol propertySymbol, Function2<? super FirPropertySymbol, ? super FirTypeScope, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
        Intrinsics.checkNotNullParameter(processor, "processor");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        if (this.originalScope.processDirectOverriddenPropertiesWithBaseScope(propertySymbol, new FirUnstableSmartcastTypeScope$processTypedComposite$1(linkedHashSet, processor)) != ProcessorAction.STOP && this.smartcastScope.processDirectOverriddenPropertiesWithBaseScope(propertySymbol, new FirUnstableSmartcastTypeScope$processTypedComposite$3(linkedHashSet, this, processor)) != ProcessorAction.STOP) {
            return ProcessorAction.NEXT;
        }
        return ProcessorAction.STOP;
    }
}
