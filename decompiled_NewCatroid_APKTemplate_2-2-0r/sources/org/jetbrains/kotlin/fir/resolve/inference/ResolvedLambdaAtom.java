package org.jetbrains.kotlin.fir.resolve.inference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeVariableType;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: PostponedAtoms.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\u001e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001Bk\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\n\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\n\u0012\u0006\u0010\f\u001a\u00020\u0005\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0012¢\u0006\u0002\u0010\u0013J\u0016\u00101\u001a\u0002022\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u00103\u001a\u000204J\u000e\u00105\u001a\u0002022\u0006\u0010\r\u001a\u00020\u000eR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\n¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\"\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u001c\u001a\u0004\u0018\u00010\u0005@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00050 8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u0014\u0010#\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b$\u0010\u001eR\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\n¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0019R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001eR \u0010'\u001a\b\u0012\u0004\u0012\u00020(0 X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\"\"\u0004\b*\u0010+R\u001a\u0010\f\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\u001e\"\u0004\b-\u0010.R\"\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\u0010\u001c\u001a\u0004\u0018\u00010\u000e@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b/\u00100¨\u00066"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/ResolvedLambdaAtom;", "Lorg/jetbrains/kotlin/fir/resolve/inference/PostponedResolvedAtom;", "atom", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "expectedType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "expectedFunctionTypeKind", "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "receiver", "contextReceivers", "", "parameters", "returnType", "typeVariableForLambdaReturnType", "Lorg/jetbrains/kotlin/fir/resolve/inference/ConeTypeVariableForLambdaReturnType;", "candidateOfOuterCall", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "coerceFirstParameterToExtensionReceiver", "", "(Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/fir/resolve/inference/ConeTypeVariableForLambdaReturnType;Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;Z)V", "getAtom", "()Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "getCoerceFirstParameterToExtensionReceiver", "()Z", "getContextReceivers", "()Ljava/util/List;", "getExpectedFunctionTypeKind", "()Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "<set-?>", "getExpectedType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "inputTypes", "", "getInputTypes", "()Ljava/util/Collection;", "outputType", "getOutputType", "getParameters", "getReceiver", "returnStatements", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getReturnStatements", "setReturnStatements", "(Ljava/util/Collection;)V", "getReturnType", "setReturnType", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)V", "getTypeVariableForLambdaReturnType", "()Lorg/jetbrains/kotlin/fir/resolve/inference/ConeTypeVariableForLambdaReturnType;", "replaceExpectedType", "", "newReturnType", "Lorg/jetbrains/kotlin/fir/types/ConeTypeVariableType;", "replaceTypeVariableForLambdaReturnType", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ResolvedLambdaAtom extends PostponedResolvedAtom {
    private final FirAnonymousFunction atom;
    private final boolean coerceFirstParameterToExtensionReceiver;
    private final List<ConeKotlinType> contextReceivers;
    private final FunctionTypeKind expectedFunctionTypeKind;
    private ConeKotlinType expectedType;
    private final List<ConeKotlinType> parameters;
    private final ConeKotlinType receiver;
    public Collection<? extends FirExpression> returnStatements;
    private ConeKotlinType returnType;
    private ConeTypeVariableForLambdaReturnType typeVariableForLambdaReturnType;

    @Override // org.jetbrains.kotlin.fir.resolve.inference.PostponedResolvedAtom
    public FirAnonymousFunction getAtom() {
        return this.atom;
    }

    public final FunctionTypeKind getExpectedFunctionTypeKind() {
        return this.expectedFunctionTypeKind;
    }

    public final ConeKotlinType getReceiver() {
        return this.receiver;
    }

    public final List<ConeKotlinType> getContextReceivers() {
        return this.contextReceivers;
    }

    public final List<ConeKotlinType> getParameters() {
        return this.parameters;
    }

    public final ConeKotlinType getReturnType() {
        return this.returnType;
    }

    public final void setReturnType(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<set-?>");
        this.returnType = coneKotlinType;
    }

    public final boolean getCoerceFirstParameterToExtensionReceiver() {
        return this.coerceFirstParameterToExtensionReceiver;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ResolvedLambdaAtom(FirAnonymousFunction atom, ConeKotlinType coneKotlinType, FunctionTypeKind functionTypeKind, ConeKotlinType coneKotlinType2, List<? extends ConeKotlinType> contextReceivers, List<? extends ConeKotlinType> parameters, ConeKotlinType returnType, ConeTypeVariableForLambdaReturnType coneTypeVariableForLambdaReturnType, Candidate candidate, boolean z) {
        super(null);
        Intrinsics.checkNotNullParameter(atom, "atom");
        Intrinsics.checkNotNullParameter(contextReceivers, "contextReceivers");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        this.atom = atom;
        this.expectedFunctionTypeKind = functionTypeKind;
        this.receiver = coneKotlinType2;
        this.contextReceivers = contextReceivers;
        this.parameters = parameters;
        this.returnType = returnType;
        this.coerceFirstParameterToExtensionReceiver = z;
        if (candidate != null) {
            candidate.getPostponedAtoms().add(this);
        }
        this.typeVariableForLambdaReturnType = coneTypeVariableForLambdaReturnType;
        this.expectedType = coneKotlinType;
    }

    public final ConeTypeVariableForLambdaReturnType getTypeVariableForLambdaReturnType() {
        return this.typeVariableForLambdaReturnType;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker
    public ConeKotlinType getExpectedType() {
        return this.expectedType;
    }

    public final Collection<FirExpression> getReturnStatements() {
        Collection collection = this.returnStatements;
        if (collection != null) {
            return collection;
        }
        Intrinsics.throwUninitializedPropertyAccessException("returnStatements");
        return null;
    }

    public final void setReturnStatements(Collection<? extends FirExpression> collection) {
        Intrinsics.checkNotNullParameter(collection, "<set-?>");
        this.returnStatements = collection;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.inference.PostponedResolvedAtom, org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker
    public Collection<ConeKotlinType> getInputTypes() {
        if (this.receiver == null && this.contextReceivers.isEmpty()) {
            return this.parameters;
        }
        ArrayList arrayList = new ArrayList(this.parameters.size() + this.contextReceivers.size() + (this.receiver != null ? 1 : 0));
        arrayList.addAll(this.parameters);
        ArrayList arrayList2 = arrayList;
        CollectionsKt.addIfNotNull(arrayList2, this.receiver);
        arrayList.addAll(this.contextReceivers);
        return arrayList2;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker
    /* JADX INFO: renamed from: getOutputType, reason: from getter */
    public ConeKotlinType getReturnType() {
        return this.returnType;
    }

    public final void replaceExpectedType(ConeKotlinType expectedType, ConeTypeVariableType newReturnType) {
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        Intrinsics.checkNotNullParameter(newReturnType, "newReturnType");
        this.expectedType = expectedType;
        this.returnType = newReturnType;
    }

    public final void replaceTypeVariableForLambdaReturnType(ConeTypeVariableForLambdaReturnType typeVariableForLambdaReturnType) {
        Intrinsics.checkNotNullParameter(typeVariableForLambdaReturnType, "typeVariableForLambdaReturnType");
        this.typeVariableForLambdaReturnType = typeVariableForLambdaReturnType;
    }
}
