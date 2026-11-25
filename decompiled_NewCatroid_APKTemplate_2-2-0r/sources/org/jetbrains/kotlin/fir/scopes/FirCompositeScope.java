package org.jetbrains.kotlin.fir.scopes;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
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
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirCompositeScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\u0010\u0004J.\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u001c\u0010\u0012\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0014\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u000f0\u0013H\u0016JP\u0010\u0016\u001a\u00020\u000f\"\u0004\b\u0000\u0010\u00172)\u0010\u0018\u001a%\u0012\u0004\u0012\u00020\u0001\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0017\u0012\u0004\u0012\u00020\u000f0\u0019\u0012\u0004\u0012\u00020\u000f0\u0013¢\u0006\u0002\b\u001a2\u0014\b\b\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u0002H\u0017\u0012\u0004\u0012\u00020\u000f0\u0019H\u0082\bJ^\u0010\u0016\u001a\u00020\u000f\"\u0004\b\u0000\u0010\u00172/\u0010\u0018\u001a+\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u0011\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0017\u0012\u0004\u0012\u00020\u000f0\u0019\u0012\u0004\u0012\u00020\u000f0\u001b¢\u0006\u0002\b\u001a2\u0006\u0010\u0010\u001a\u00020\u00112\u0014\b\b\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u0002H\u0017\u0012\u0004\u0012\u00020\u000f0\u0019H\u0082\bJ\u001c\u0010\u001c\u001a\u00020\u000f2\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u000f0\u0019H\u0016J$\u0010\u001e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u000f0\u0019H\u0016J(\u0010 \u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0016\u0010\u0012\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030!\u0012\u0004\u0012\u00020\u000f0\u0019H\u0016R!\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/FirCompositeScope;", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "scopes", "", "(Ljava/lang/Iterable;)V", "scopeOwnerLookupNames", "", "", "getScopeOwnerLookupNames", "()Ljava/util/List;", "scopeOwnerLookupNames$delegate", "Lkotlin/Lazy;", "getScopes", "()Ljava/lang/Iterable;", "processClassifiersByNameWithSubstitution", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "processor", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "processComposite", ExifInterface.GPS_DIRECTION_TRUE, "process", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "Lkotlin/Function3;", "processDeclaredConstructors", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "processFunctionsByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "processPropertiesByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirCompositeScope extends FirScope {

    /* JADX INFO: renamed from: scopeOwnerLookupNames$delegate, reason: from kotlin metadata */
    private final Lazy scopeOwnerLookupNames;
    private final Iterable<FirScope> scopes;

    /* JADX WARN: Multi-variable type inference failed */
    public FirCompositeScope(Iterable<? extends FirScope> scopes) {
        Intrinsics.checkNotNullParameter(scopes, "scopes");
        this.scopes = scopes;
        this.scopeOwnerLookupNames = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<List<? extends String>>() { // from class: org.jetbrains.kotlin.fir.scopes.FirCompositeScope$scopeOwnerLookupNames$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends String> invoke() {
                Iterable<FirScope> scopes2 = this.this$0.getScopes();
                ArrayList arrayList = new ArrayList();
                Iterator<FirScope> it = scopes2.iterator();
                while (it.hasNext()) {
                    CollectionsKt.addAll(arrayList, it.next().getScopeOwnerLookupNames());
                }
                return arrayList;
            }
        });
    }

    public final Iterable<FirScope> getScopes() {
        return this.scopes;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processClassifiersByNameWithSubstitution(Name name2, Function2<? super FirClassifierSymbol<?>, ? super ConeSubstitutor, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        Iterator<FirScope> it = this.scopes.iterator();
        while (it.hasNext()) {
            it.next().processClassifiersByNameWithSubstitution(name2, processor);
        }
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
        Iterator<FirScope> it = this.scopes.iterator();
        while (it.hasNext()) {
            it.next().processFunctionsByName(name2, new FirCompositeScope$processComposite$1(linkedHashSet, processor));
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processPropertiesByName(Name name2, Function1<? super FirVariableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<FirScope> it = this.scopes.iterator();
        while (it.hasNext()) {
            it.next().processPropertiesByName(name2, new FirCompositeScope$processComposite$1(linkedHashSet, processor));
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processDeclaredConstructors(final Function1<? super FirConstructorSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(processor, "processor");
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<FirScope> it = this.scopes.iterator();
        while (it.hasNext()) {
            it.next().processDeclaredConstructors((Function1) new Function1<T, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.FirCompositeScope$processComposite$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(1);
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
                    invoke2(obj);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(T t) {
                    if (linkedHashSet.add(t)) {
                        processor.invoke(t);
                    }
                }
            });
        }
    }
}
