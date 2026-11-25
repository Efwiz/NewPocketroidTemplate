package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: FirArgumentsToParametersMapper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\"\u0010\u0002\u001a\u001e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003j\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005`\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\u0002\u0010\nJ%\u0010\u000f\u001a\u001e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003j\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005`\u0006HÆ\u0003J\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\t0\bHÆ\u0003J?\u0010\u0011\u001a\u00020\u00002$\b\u0002\u0010\u0002\u001a\u001e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003j\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005`\u00062\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bHÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\u0006\u0010\u0017\u001a\u00020\u0016J\"\u0010\u0018\u001a\u001e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00040\u0003j\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u0004`\u0006J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR-\u0010\u0002\u001a\u001e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003j\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005`\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/ArgumentMapping;", "", "parameterToCallArgumentMap", "Ljava/util/LinkedHashMap;", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolvedCallArgument;", "Lkotlin/collections/LinkedHashMap;", "diagnostics", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionDiagnostic;", "(Ljava/util/LinkedHashMap;Ljava/util/List;)V", "getDiagnostics", "()Ljava/util/List;", "getParameterToCallArgumentMap", "()Ljava/util/LinkedHashMap;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "numDefaults", "toArgumentToParameterMapping", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "toString", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ArgumentMapping {
    private final List<ResolutionDiagnostic> diagnostics;
    private final LinkedHashMap<FirValueParameter, ResolvedCallArgument> parameterToCallArgumentMap;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ArgumentMapping copy$default(ArgumentMapping argumentMapping, LinkedHashMap linkedHashMap, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            linkedHashMap = argumentMapping.parameterToCallArgumentMap;
        }
        if ((i & 2) != 0) {
            list = argumentMapping.diagnostics;
        }
        return argumentMapping.copy(linkedHashMap, list);
    }

    public final LinkedHashMap<FirValueParameter, ResolvedCallArgument> component1() {
        return this.parameterToCallArgumentMap;
    }

    public final List<ResolutionDiagnostic> component2() {
        return this.diagnostics;
    }

    public final ArgumentMapping copy(LinkedHashMap<FirValueParameter, ResolvedCallArgument> parameterToCallArgumentMap, List<? extends ResolutionDiagnostic> diagnostics) {
        Intrinsics.checkNotNullParameter(parameterToCallArgumentMap, "parameterToCallArgumentMap");
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        return new ArgumentMapping(parameterToCallArgumentMap, diagnostics);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ArgumentMapping)) {
            return false;
        }
        ArgumentMapping argumentMapping = (ArgumentMapping) other;
        return Intrinsics.areEqual(this.parameterToCallArgumentMap, argumentMapping.parameterToCallArgumentMap) && Intrinsics.areEqual(this.diagnostics, argumentMapping.diagnostics);
    }

    public int hashCode() {
        return (this.parameterToCallArgumentMap.hashCode() * 31) + this.diagnostics.hashCode();
    }

    public String toString() {
        return "ArgumentMapping(parameterToCallArgumentMap=" + this.parameterToCallArgumentMap + ", diagnostics=" + this.diagnostics + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ArgumentMapping(LinkedHashMap<FirValueParameter, ResolvedCallArgument> parameterToCallArgumentMap, List<? extends ResolutionDiagnostic> diagnostics) {
        Intrinsics.checkNotNullParameter(parameterToCallArgumentMap, "parameterToCallArgumentMap");
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        this.parameterToCallArgumentMap = parameterToCallArgumentMap;
        this.diagnostics = diagnostics;
    }

    public final LinkedHashMap<FirValueParameter, ResolvedCallArgument> getParameterToCallArgumentMap() {
        return this.parameterToCallArgumentMap;
    }

    public final List<ResolutionDiagnostic> getDiagnostics() {
        return this.diagnostics;
    }

    public final LinkedHashMap<FirExpression, FirValueParameter> toArgumentToParameterMapping() {
        LinkedHashMap<FirExpression, FirValueParameter> linkedHashMap = new LinkedHashMap<>();
        for (Map.Entry<FirValueParameter, ResolvedCallArgument> entry : this.parameterToCallArgumentMap.entrySet()) {
            FirValueParameter key = entry.getKey();
            ResolvedCallArgument value = entry.getValue();
            if (value instanceof ResolvedCallArgument.SimpleArgument) {
                linkedHashMap.put(((ResolvedCallArgument.SimpleArgument) value).getCallArgument(), key);
            } else if (!(value instanceof ResolvedCallArgument.VarargArgument)) {
                Intrinsics.areEqual(value, ResolvedCallArgument.DefaultArgument.INSTANCE);
            } else {
                Iterator<T> it = value.getArguments().iterator();
                while (it.hasNext()) {
                    linkedHashMap.put((FirExpression) it.next(), key);
                }
            }
        }
        return linkedHashMap;
    }

    public final int numDefaults() {
        Collection<ResolvedCallArgument> collectionValues = this.parameterToCallArgumentMap.values();
        Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
        Collection<ResolvedCallArgument> collection = collectionValues;
        int i = 0;
        if (!collection.isEmpty()) {
            Iterator<T> it = collection.iterator();
            while (it.hasNext()) {
                if (Intrinsics.areEqual((ResolvedCallArgument) it.next(), ResolvedCallArgument.DefaultArgument.INSTANCE) && (i = i + 1) < 0) {
                    CollectionsKt.throwCountOverflow();
                }
            }
        }
        return i;
    }
}
