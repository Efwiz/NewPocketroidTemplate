package org.jetbrains.kotlin.resolve.calls.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.components.ReturnArgumentsInfo;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableForLambdaReturnType;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: ResolutionAtoms.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u001e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001BW\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u0012\u0006\u0010\u000b\u001a\u00020\u0007\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\u000fJ\u001e\u0010'\u001a\u00020(2\b\u0010)\u001a\u0004\u0018\u00010 2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020+0\tR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00070\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u001aR\u0014\u0010\u001b\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0015R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\t¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0013R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0015R\"\u0010!\u001a\u0004\u0018\u00010 2\b\u0010\u001f\u001a\u0004\u0018\u00010 @BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0011\u0010\u000b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0015R\u0013\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedLambdaAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/PostponedResolvedAtom;", "atom", "Lorg/jetbrains/kotlin/resolve/calls/model/LambdaKotlinCallArgument;", "isSuspend", "", "receiver", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "contextReceivers", "", "parameters", "returnType", "typeVariableForLambdaReturnType", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/TypeVariableForLambdaReturnType;", "expectedType", "(Lorg/jetbrains/kotlin/resolve/calls/model/LambdaKotlinCallArgument;ZLorg/jetbrains/kotlin/types/UnwrappedType;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/types/UnwrappedType;Lorg/jetbrains/kotlin/resolve/calls/inference/model/TypeVariableForLambdaReturnType;Lorg/jetbrains/kotlin/types/UnwrappedType;)V", "getAtom", "()Lorg/jetbrains/kotlin/resolve/calls/model/LambdaKotlinCallArgument;", "getContextReceivers", "()Ljava/util/List;", "getExpectedType", "()Lorg/jetbrains/kotlin/types/UnwrappedType;", "inputTypes", "", "getInputTypes", "()Ljava/util/Collection;", "()Z", "outputType", "getOutputType", "getParameters", "getReceiver", "<set-?>", "Lorg/jetbrains/kotlin/resolve/calls/components/ReturnArgumentsInfo;", "resultArgumentsInfo", "getResultArgumentsInfo", "()Lorg/jetbrains/kotlin/resolve/calls/components/ReturnArgumentsInfo;", "getReturnType", "getTypeVariableForLambdaReturnType", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/TypeVariableForLambdaReturnType;", "setAnalyzedResults", "", "resultArguments", "subResolvedAtoms", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedAtom;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ResolvedLambdaAtom extends PostponedResolvedAtom {
    private final LambdaKotlinCallArgument atom;
    private final List<UnwrappedType> contextReceivers;
    private final UnwrappedType expectedType;
    private final boolean isSuspend;
    private final List<UnwrappedType> parameters;
    private final UnwrappedType receiver;
    private ReturnArgumentsInfo resultArgumentsInfo;
    private final UnwrappedType returnType;
    private final TypeVariableForLambdaReturnType typeVariableForLambdaReturnType;

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom
    public LambdaKotlinCallArgument getAtom() {
        return this.atom;
    }

    /* JADX INFO: renamed from: isSuspend, reason: from getter */
    public final boolean getIsSuspend() {
        return this.isSuspend;
    }

    public final UnwrappedType getReceiver() {
        return this.receiver;
    }

    public final List<UnwrappedType> getContextReceivers() {
        return this.contextReceivers;
    }

    public final List<UnwrappedType> getParameters() {
        return this.parameters;
    }

    public final UnwrappedType getReturnType() {
        return this.returnType;
    }

    public final TypeVariableForLambdaReturnType getTypeVariableForLambdaReturnType() {
        return this.typeVariableForLambdaReturnType;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker
    public UnwrappedType getExpectedType() {
        return this.expectedType;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ResolvedLambdaAtom(LambdaKotlinCallArgument atom, boolean z, UnwrappedType unwrappedType, List<? extends UnwrappedType> contextReceivers, List<? extends UnwrappedType> parameters, UnwrappedType returnType, TypeVariableForLambdaReturnType typeVariableForLambdaReturnType, UnwrappedType unwrappedType2) {
        super(null);
        Intrinsics.checkNotNullParameter(atom, "atom");
        Intrinsics.checkNotNullParameter(contextReceivers, "contextReceivers");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        this.atom = atom;
        this.isSuspend = z;
        this.receiver = unwrappedType;
        this.contextReceivers = contextReceivers;
        this.parameters = parameters;
        this.returnType = returnType;
        this.typeVariableForLambdaReturnType = typeVariableForLambdaReturnType;
        this.expectedType = unwrappedType2;
    }

    public final ReturnArgumentsInfo getResultArgumentsInfo() {
        return this.resultArgumentsInfo;
    }

    public final void setAnalyzedResults(ReturnArgumentsInfo resultArguments, List<? extends ResolvedAtom> subResolvedAtoms) {
        Intrinsics.checkNotNullParameter(subResolvedAtoms, "subResolvedAtoms");
        this.resultArgumentsInfo = resultArguments;
        setAnalyzedResults(subResolvedAtoms);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtom, org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker
    public Collection<UnwrappedType> getInputTypes() {
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
    public UnwrappedType getReturnType() {
        return this.returnType;
    }
}
