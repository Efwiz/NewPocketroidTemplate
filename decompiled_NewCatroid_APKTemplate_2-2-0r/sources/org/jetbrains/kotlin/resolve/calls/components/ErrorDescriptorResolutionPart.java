package org.jetbrains.kotlin.resolve.calls.components;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.inference.components.EmptySubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.components.FreshVariableNewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ReceiverKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolutionPart;
import org.jetbrains.kotlin.resolve.calls.model.SimpleKotlinCallArgument;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: compiled from: ResolutionParts.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/ErrorDescriptorResolutionPart;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolutionPart;", "()V", "process", "", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "workIndex", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ErrorDescriptorResolutionPart extends ResolutionPart {
    public static final ErrorDescriptorResolutionPart INSTANCE = new ErrorDescriptorResolutionPart();

    private ErrorDescriptorResolutionPart() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
    public void process(ResolutionCandidate resolutionCandidate, int i) {
        Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
        ErrorUtils.isError(resolutionCandidate.getResolvedCall().get_candidateDescriptor());
        resolutionCandidate.getResolvedCall().setTypeArgumentMappingByOriginal(TypeArgumentsToParametersMapper.TypeArgumentsMapping.NoExplicitArguments.INSTANCE);
        resolutionCandidate.getResolvedCall().setArgumentMappingByOriginal(MapsKt.emptyMap());
        resolutionCandidate.getResolvedCall().setFreshVariablesSubstitutor(FreshVariableNewTypeSubstitutor.INSTANCE.getEmpty());
        resolutionCandidate.getResolvedCall().setKnownParametersSubstitutor(EmptySubstitutor.INSTANCE);
        resolutionCandidate.getResolvedCall().setArgumentToCandidateParameter(MapsKt.emptyMap());
        ReceiverKotlinCallArgument explicitReceiver = resolutionCandidate.getResolvedCall().getAtom().getExplicitReceiver();
        SimpleKotlinCallArgument simpleKotlinCallArgument = explicitReceiver instanceof SimpleKotlinCallArgument ? (SimpleKotlinCallArgument) explicitReceiver : null;
        if (simpleKotlinCallArgument != null) {
            ResolutionPartsKt.resolveKotlinArgument(resolutionCandidate, simpleKotlinCallArgument, null, ReceiverInfo.INSTANCE.getNotReceiver());
        }
        Iterator<KotlinCallArgument> it = resolutionCandidate.getResolvedCall().getAtom().getArgumentsInParenthesis().iterator();
        while (it.hasNext()) {
            ResolutionPartsKt.resolveKotlinArgument(resolutionCandidate, it.next(), null, ReceiverInfo.INSTANCE.getNotReceiver());
        }
        KotlinCallArgument externalArgument = resolutionCandidate.getResolvedCall().getAtom().getExternalArgument();
        if (externalArgument != null) {
            ResolutionPartsKt.resolveKotlinArgument(resolutionCandidate, externalArgument, null, ReceiverInfo.INSTANCE.getNotReceiver());
        }
    }
}
