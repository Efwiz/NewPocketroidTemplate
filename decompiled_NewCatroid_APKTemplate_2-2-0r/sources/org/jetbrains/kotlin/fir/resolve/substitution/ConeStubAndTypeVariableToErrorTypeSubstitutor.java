package org.jetbrains.kotlin.fir.resolve.substitution;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeTypeVariableTypeIsNotInferred;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeStubType;
import org.jetbrains.kotlin.fir.types.ConeStubTypeConstructor;
import org.jetbrains.kotlin.fir.types.ConeTypeContext;
import org.jetbrains.kotlin.fir.types.ConeTypeVariableType;

/* JADX INFO: compiled from: Substitutors.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0012\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\tH\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeStubAndTypeVariableToErrorTypeSubstitutor;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/AbstractConeSubstitutor;", "typeContext", "Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;", "stubTypesToReplace", "", "Lorg/jetbrains/kotlin/fir/types/ConeStubTypeConstructor;", "(Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;Ljava/util/Collection;)V", "substituteType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", ModuleXmlParser.TYPE, "toString", "", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeStubAndTypeVariableToErrorTypeSubstitutor extends AbstractConeSubstitutor {
    private final Collection<ConeStubTypeConstructor> stubTypesToReplace;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConeStubAndTypeVariableToErrorTypeSubstitutor(ConeTypeContext typeContext, Collection<ConeStubTypeConstructor> stubTypesToReplace) {
        super(typeContext);
        Intrinsics.checkNotNullParameter(typeContext, "typeContext");
        Intrinsics.checkNotNullParameter(stubTypesToReplace, "stubTypesToReplace");
        this.stubTypesToReplace = stubTypesToReplace;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.substitution.AbstractConeSubstitutor
    public ConeKotlinType substituteType(ConeKotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (type instanceof ConeTypeVariableType) {
            return new ConeErrorType(new ConeTypeVariableTypeIsNotInferred((ConeTypeVariableType) type, null, 2, null), true, null, null, 12, null);
        }
        if (!(type instanceof ConeStubType)) {
            return null;
        }
        ConeStubType coneStubType = (ConeStubType) type;
        return this.stubTypesToReplace.contains(coneStubType.getConstructor()) ? new ConeErrorType(new ConeSimpleDiagnostic("Type for stub of " + coneStubType.getConstructor().getVariable().getTypeConstructor().getDebugName() + " is not inferred", DiagnosticKind.InferenceError), true, null, null, 12, null) : null;
    }

    public String toString() {
        return "{<Stub type> -> <Error type>}";
    }
}
