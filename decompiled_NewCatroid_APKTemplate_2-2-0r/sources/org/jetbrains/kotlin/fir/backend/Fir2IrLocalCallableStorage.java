package org.jetbrains.kotlin.fir.backend;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;

/* JADX INFO: compiled from: Fir2IrLocalCallableStorage.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0006\u001a\u00020\u0007J\u0010\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000bJ\u0010\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u0013J\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u0017J1\u0010\u0018\u001a\u0004\u0018\u0001H\u0019\"\u0004\b\u0000\u0010\u00192\u0019\u0010\u001a\u001a\u0015\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H\u00190\u001b¢\u0006\u0002\b\u001cH\u0082\b¢\u0006\u0002\u0010\u001dJ\u0006\u0010\u001e\u001a\u00020\u0007J\u0016\u0010\u001f\u001a\u00020\u00072\u0006\u0010 \u001a\u00020\u000b2\u0006\u0010!\u001a\u00020\tJ\u0016\u0010\"\u001a\u00020\u00072\u0006\u0010#\u001a\u00020\u000f2\u0006\u0010$\u001a\u00020\rJ\u0016\u0010%\u001a\u00020\u00072\u0006\u0010&\u001a\u00020\u00132\u0006\u0010'\u001a\u00020\u0011J\u0016\u0010(\u001a\u00020\u00072\u0006\u0010)\u001a\u00020\u00172\u0006\u0010*\u001a\u00020\u0015R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrLocalCallableStorage;", "", "()V", "cacheStack", "", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrScopeCache;", "enterCallable", "", "getDelegatedProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "getLocalFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "localFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "getParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "parameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "getVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "variable", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "last", ExifInterface.GPS_DIRECTION_TRUE, "getter", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "leaveCallable", "putDelegatedProperty", "firProperty", "irProperty", "putLocalFunction", "firFunction", "irFunction", "putParameter", "firParameter", "irParameter", "putVariable", "firVariable", "irVariable", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Fir2IrLocalCallableStorage {
    private final List<Fir2IrScopeCache> cacheStack = new ArrayList();

    public final void enterCallable() {
        this.cacheStack.add(new Fir2IrScopeCache());
    }

    public final void leaveCallable() {
        ((Fir2IrScopeCache) CollectionsKt.last((List) this.cacheStack)).clear();
        List<Fir2IrScopeCache> list = this.cacheStack;
        list.remove(list.size() - 1);
    }

    public final IrValueParameter getParameter(FirValueParameter parameter) {
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Iterator it = CollectionsKt.asReversedMutable(this.cacheStack).iterator();
        while (it.hasNext()) {
            IrValueParameter parameter2 = ((Fir2IrScopeCache) it.next()).getParameter(parameter);
            if (parameter2 != null) {
                return parameter2;
            }
        }
        return null;
    }

    public final void putParameter(FirValueParameter firParameter, IrValueParameter irParameter) {
        Intrinsics.checkNotNullParameter(firParameter, "firParameter");
        Intrinsics.checkNotNullParameter(irParameter, "irParameter");
        ((Fir2IrScopeCache) CollectionsKt.last((List) this.cacheStack)).putParameter(firParameter, irParameter);
    }

    public final void putVariable(FirVariable firVariable, IrVariable irVariable) {
        Intrinsics.checkNotNullParameter(firVariable, "firVariable");
        Intrinsics.checkNotNullParameter(irVariable, "irVariable");
        ((Fir2IrScopeCache) CollectionsKt.last((List) this.cacheStack)).putVariable(firVariable, irVariable);
    }

    public final void putLocalFunction(FirFunction firFunction, IrSimpleFunction irFunction) {
        Intrinsics.checkNotNullParameter(firFunction, "firFunction");
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        ((Fir2IrScopeCache) CollectionsKt.last((List) this.cacheStack)).putLocalFunction(firFunction, irFunction);
    }

    public final void putDelegatedProperty(FirProperty firProperty, IrLocalDelegatedProperty irProperty) {
        Intrinsics.checkNotNullParameter(firProperty, "firProperty");
        Intrinsics.checkNotNullParameter(irProperty, "irProperty");
        ((Fir2IrScopeCache) CollectionsKt.last((List) this.cacheStack)).putDelegatedProperty(firProperty, irProperty);
    }

    public final IrVariable getVariable(FirVariable variable) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        Iterator it = CollectionsKt.asReversedMutable(this.cacheStack).iterator();
        while (it.hasNext()) {
            IrVariable variable2 = ((Fir2IrScopeCache) it.next()).getVariable(variable);
            if (variable2 != null) {
                return variable2;
            }
        }
        return null;
    }

    public final IrSimpleFunction getLocalFunction(FirFunction localFunction) {
        Intrinsics.checkNotNullParameter(localFunction, "localFunction");
        Iterator it = CollectionsKt.asReversedMutable(this.cacheStack).iterator();
        while (it.hasNext()) {
            IrSimpleFunction localFunction2 = ((Fir2IrScopeCache) it.next()).getLocalFunction(localFunction);
            if (localFunction2 != null) {
                return localFunction2;
            }
        }
        return null;
    }

    public final IrLocalDelegatedProperty getDelegatedProperty(FirProperty property) {
        Intrinsics.checkNotNullParameter(property, "property");
        Iterator it = CollectionsKt.asReversedMutable(this.cacheStack).iterator();
        while (it.hasNext()) {
            IrLocalDelegatedProperty delegatedProperty = ((Fir2IrScopeCache) it.next()).getDelegatedProperty(property);
            if (delegatedProperty != null) {
                return delegatedProperty;
            }
        }
        return null;
    }
}
