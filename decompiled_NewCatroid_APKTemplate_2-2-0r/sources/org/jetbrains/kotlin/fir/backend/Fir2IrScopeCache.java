package org.jetbrains.kotlin.fir.backend;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: Fir2IrScopeCache.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0014\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0010\u001a\u00020\u0011J\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0013\u001a\u00020\u0005J\u0010\u0010\u0014\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0015\u001a\u00020\bJ\u0010\u0010\u0016\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0017\u001a\u00020\u000bJ\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0019\u001a\u00020\u000eJ\u0016\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\u0006J\u0016\u0010\u001d\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\b2\u0006\u0010\u001e\u001a\u00020\tJ\u0016\u0010\u001f\u001a\u00020\u00112\u0006\u0010 \u001a\u00020\u000b2\u0006\u0010!\u001a\u00020\fJ\u0016\u0010\"\u001a\u00020\u00112\u0006\u0010#\u001a\u00020\u000e2\u0006\u0010$\u001a\u00020\u000fR\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrScopeCache;", "", "()V", "delegatedPropertyCache", "", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "localFunctionCache", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "parameterCache", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "variableCache", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", LineReader.CLEAR, "", "getDelegatedProperty", "property", "getLocalFunction", "localFunction", "getParameter", "parameter", "getVariable", "variable", "putDelegatedProperty", "firProperty", "irProperty", "putLocalFunction", "irFunction", "putParameter", "firParameter", "irParameter", "putVariable", "firVariable", "irVariable", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Fir2IrScopeCache {
    private final Map<FirValueParameter, IrValueParameter> parameterCache = new LinkedHashMap();
    private final Map<FirVariable, IrVariable> variableCache = new LinkedHashMap();
    private final Map<FirFunction, IrSimpleFunction> localFunctionCache = new LinkedHashMap();
    private final Map<FirProperty, IrLocalDelegatedProperty> delegatedPropertyCache = new LinkedHashMap();

    public final IrValueParameter getParameter(FirValueParameter parameter) {
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        return this.parameterCache.get(parameter);
    }

    public final void putParameter(FirValueParameter firParameter, IrValueParameter irParameter) {
        Intrinsics.checkNotNullParameter(firParameter, "firParameter");
        Intrinsics.checkNotNullParameter(irParameter, "irParameter");
        this.parameterCache.put(firParameter, irParameter);
    }

    public final IrVariable getVariable(FirVariable variable) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        return this.variableCache.get(variable);
    }

    public final void putVariable(FirVariable firVariable, IrVariable irVariable) {
        Intrinsics.checkNotNullParameter(firVariable, "firVariable");
        Intrinsics.checkNotNullParameter(irVariable, "irVariable");
        this.variableCache.put(firVariable, irVariable);
    }

    public final IrSimpleFunction getLocalFunction(FirFunction localFunction) {
        Intrinsics.checkNotNullParameter(localFunction, "localFunction");
        return this.localFunctionCache.get(localFunction);
    }

    public final void putLocalFunction(FirFunction localFunction, IrSimpleFunction irFunction) {
        Intrinsics.checkNotNullParameter(localFunction, "localFunction");
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        if ((localFunction instanceof FirSimpleFunction) && !Intrinsics.areEqual(localFunction.getStatus().getVisibility(), Visibilities.Local.INSTANCE)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        this.localFunctionCache.put(localFunction, irFunction);
    }

    public final IrLocalDelegatedProperty getDelegatedProperty(FirProperty property) {
        Intrinsics.checkNotNullParameter(property, "property");
        return this.delegatedPropertyCache.get(property);
    }

    public final void putDelegatedProperty(FirProperty firProperty, IrLocalDelegatedProperty irProperty) {
        Intrinsics.checkNotNullParameter(firProperty, "firProperty");
        Intrinsics.checkNotNullParameter(irProperty, "irProperty");
        this.delegatedPropertyCache.put(firProperty, irProperty);
    }

    public final void clear() {
        this.parameterCache.clear();
        this.variableCache.clear();
        this.localFunctionCache.clear();
        this.delegatedPropertyCache.clear();
    }
}
