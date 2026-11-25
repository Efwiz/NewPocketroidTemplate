package org.jetbrains.kotlin.resolve.calls.components;

import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: typeConversions.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&J\u0010\u0010\n\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0018\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u0003H&J*\u0010\r\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\tH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0010À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/ParameterTypeConversion;", "", "conversionDefinitelyNotNeeded", "", "candidate", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "expectedParameterType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "conversionIsNeededAfterSubtypingCheck", "conversionIsNeededBeforeSubtypingCheck", "areSuspendOnlySamConversionsSupported", "convertParameterType", "parameter", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ParameterTypeConversion {
    boolean conversionDefinitelyNotNeeded(ResolutionCandidate candidate, KotlinCallArgument argument, UnwrappedType expectedParameterType);

    boolean conversionIsNeededAfterSubtypingCheck(KotlinCallArgument argument);

    boolean conversionIsNeededBeforeSubtypingCheck(KotlinCallArgument argument, boolean areSuspendOnlySamConversionsSupported);

    UnwrappedType convertParameterType(ResolutionCandidate candidate, KotlinCallArgument argument, ParameterDescriptor parameter, UnwrappedType expectedParameterType);
}
