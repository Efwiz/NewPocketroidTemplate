package org.jetbrains.kotlin.contracts.interpretation;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.description.CallsEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.EffectDeclaration;
import org.jetbrains.kotlin.contracts.model.ESEffect;
import org.jetbrains.kotlin.contracts.model.structure.ESCalls;
import org.jetbrains.kotlin.contracts.model.structure.ESVariable;

/* JADX INFO: compiled from: EffectsInterpreters.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/contracts/interpretation/CallsEffectInterpreter;", "Lorg/jetbrains/kotlin/contracts/interpretation/EffectDeclarationInterpreter;", "dispatcher", "Lorg/jetbrains/kotlin/contracts/interpretation/ContractInterpretationDispatcher;", "(Lorg/jetbrains/kotlin/contracts/interpretation/ContractInterpretationDispatcher;)V", "tryInterpret", "Lorg/jetbrains/kotlin/contracts/model/ESEffect;", "effectDeclaration", "Lorg/jetbrains/kotlin/contracts/description/EffectDeclaration;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallsEffectInterpreter implements EffectDeclarationInterpreter {
    private final ContractInterpretationDispatcher dispatcher;

    public CallsEffectInterpreter(ContractInterpretationDispatcher dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, "dispatcher");
        this.dispatcher = dispatcher;
    }

    @Override // org.jetbrains.kotlin.contracts.interpretation.EffectDeclarationInterpreter
    public ESEffect tryInterpret(EffectDeclaration effectDeclaration) {
        Intrinsics.checkNotNullParameter(effectDeclaration, "effectDeclaration");
        if (!(effectDeclaration instanceof CallsEffectDeclaration)) {
            return null;
        }
        CallsEffectDeclaration callsEffectDeclaration = (CallsEffectDeclaration) effectDeclaration;
        ESVariable eSVariableInterpretVariable$resolution = this.dispatcher.interpretVariable$resolution(callsEffectDeclaration.getVariableReference());
        if (eSVariableInterpretVariable$resolution == null) {
            return null;
        }
        return new ESCalls(eSVariableInterpretVariable$resolution, callsEffectDeclaration.getKind());
    }
}
