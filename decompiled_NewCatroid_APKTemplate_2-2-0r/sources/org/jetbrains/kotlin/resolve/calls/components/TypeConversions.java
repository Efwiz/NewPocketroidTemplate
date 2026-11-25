package org.jetbrains.kotlin.resolve.calls.components;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: typeConversions.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u0012B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0004J&\u0010\f\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0004J2\u0010\u000e\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J0\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0010H\u0002¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/TypeConversions;", "", "()V", "performCompositeConversionAfterSubtyping", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "candidate", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "candidateParameter", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "candidateExpectedType", "performCompositeConversionBeforeSubtyping", "Lorg/jetbrains/kotlin/resolve/calls/components/TypeConversions$ConversionData;", "performConversionAfterSubtyping", "conversion", "Lorg/jetbrains/kotlin/resolve/calls/components/ParameterTypeConversion;", "performConversionBeforeSubtyping", "ConversionData", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeConversions {
    public static final TypeConversions INSTANCE = new TypeConversions();

    private TypeConversions() {
    }

    public final ConversionData performCompositeConversionBeforeSubtyping(ResolutionCandidate candidate, KotlinCallArgument argument, ParameterDescriptor candidateParameter, UnwrappedType candidateExpectedType) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(candidateParameter, "candidateParameter");
        Intrinsics.checkNotNullParameter(candidateExpectedType, "candidateExpectedType");
        ConversionData conversionDataPerformConversionBeforeSubtyping = performConversionBeforeSubtyping(candidate, argument, candidateParameter, candidateExpectedType, SamTypeConversions.INSTANCE);
        UnwrappedType convertedType = conversionDataPerformConversionBeforeSubtyping.getConvertedType();
        ConversionData conversionDataPerformConversionBeforeSubtyping2 = performConversionBeforeSubtyping(candidate, argument, candidateParameter, convertedType == null ? candidateExpectedType : convertedType, SuspendTypeConversions.INSTANCE);
        UnwrappedType convertedType2 = conversionDataPerformConversionBeforeSubtyping2.getConvertedType();
        ConversionData conversionDataPerformConversionBeforeSubtyping3 = performConversionBeforeSubtyping(candidate, argument, candidateParameter, (convertedType2 == null && (convertedType2 = conversionDataPerformConversionBeforeSubtyping.getConvertedType()) == null) ? candidateExpectedType : convertedType2, UnitTypeConversions.INSTANCE);
        UnwrappedType convertedType3 = conversionDataPerformConversionBeforeSubtyping3.getConvertedType();
        if (convertedType3 == null && (convertedType3 = conversionDataPerformConversionBeforeSubtyping2.getConvertedType()) == null) {
            convertedType3 = conversionDataPerformConversionBeforeSubtyping.getConvertedType();
        }
        boolean z = false;
        boolean z2 = conversionDataPerformConversionBeforeSubtyping.getWasConversion() || conversionDataPerformConversionBeforeSubtyping2.getWasConversion() || conversionDataPerformConversionBeforeSubtyping3.getWasConversion();
        if (conversionDataPerformConversionBeforeSubtyping.getConversionDefinitelyNotNeeded() && conversionDataPerformConversionBeforeSubtyping2.getConversionDefinitelyNotNeeded() && conversionDataPerformConversionBeforeSubtyping3.getConversionDefinitelyNotNeeded()) {
            z = true;
        }
        return new ConversionData(convertedType3, z2, z);
    }

    public final UnwrappedType performCompositeConversionAfterSubtyping(ResolutionCandidate candidate, KotlinCallArgument argument, ParameterDescriptor candidateParameter, UnwrappedType candidateExpectedType) {
        UnwrappedType unwrappedType;
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(candidateParameter, "candidateParameter");
        Intrinsics.checkNotNullParameter(candidateExpectedType, "candidateExpectedType");
        UnwrappedType unwrappedTypePerformConversionAfterSubtyping = performConversionAfterSubtyping(candidate, argument, candidateParameter, candidateExpectedType, SamTypeConversions.INSTANCE);
        UnwrappedType unwrappedTypePerformConversionAfterSubtyping2 = performConversionAfterSubtyping(candidate, argument, candidateParameter, unwrappedTypePerformConversionAfterSubtyping == null ? candidateExpectedType : unwrappedTypePerformConversionAfterSubtyping, SuspendTypeConversions.INSTANCE);
        if (unwrappedTypePerformConversionAfterSubtyping2 == null) {
            unwrappedType = unwrappedTypePerformConversionAfterSubtyping == null ? candidateExpectedType : unwrappedTypePerformConversionAfterSubtyping;
        } else {
            unwrappedType = unwrappedTypePerformConversionAfterSubtyping2;
        }
        UnwrappedType unwrappedTypePerformConversionAfterSubtyping3 = performConversionAfterSubtyping(candidate, argument, candidateParameter, unwrappedType, UnitTypeConversions.INSTANCE);
        if (unwrappedTypePerformConversionAfterSubtyping3 == null) {
            return unwrappedTypePerformConversionAfterSubtyping2 == null ? unwrappedTypePerformConversionAfterSubtyping : unwrappedTypePerformConversionAfterSubtyping2;
        }
        return unwrappedTypePerformConversionAfterSubtyping3;
    }

    private final UnwrappedType performConversionAfterSubtyping(ResolutionCandidate candidate, KotlinCallArgument argument, ParameterDescriptor candidateParameter, UnwrappedType candidateExpectedType, ParameterTypeConversion conversion) {
        if (!conversion.conversionIsNeededAfterSubtypingCheck(argument) || conversion.conversionDefinitelyNotNeeded(candidate, argument, candidateExpectedType)) {
            return null;
        }
        return conversion.convertParameterType(candidate, argument, candidateParameter, candidateExpectedType);
    }

    private final ConversionData performConversionBeforeSubtyping(ResolutionCandidate candidate, KotlinCallArgument argument, ParameterDescriptor candidateParameter, UnwrappedType candidateExpectedType, ParameterTypeConversion conversion) {
        boolean zConversionDefinitelyNotNeeded = conversion.conversionDefinitelyNotNeeded(candidate, argument, candidateExpectedType);
        boolean zSupportsFeature = candidate.getCallComponents().getLanguageVersionSettings().supportsFeature(LanguageFeature.SuspendOnlySamConversions);
        if (!zConversionDefinitelyNotNeeded && conversion.conversionIsNeededBeforeSubtypingCheck(argument, zSupportsFeature)) {
            return new ConversionData(conversion.convertParameterType(candidate, argument, candidateParameter, candidateExpectedType), true, zConversionDefinitelyNotNeeded);
        }
        return new ConversionData(null, false, zConversionDefinitelyNotNeeded);
    }

    /* JADX INFO: compiled from: typeConversions.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/TypeConversions$ConversionData;", "", "convertedType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "wasConversion", "", "conversionDefinitelyNotNeeded", "(Lorg/jetbrains/kotlin/types/UnwrappedType;ZZ)V", "getConversionDefinitelyNotNeeded", "()Z", "getConvertedType", "()Lorg/jetbrains/kotlin/types/UnwrappedType;", "getWasConversion", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ConversionData {
        private final boolean conversionDefinitelyNotNeeded;
        private final UnwrappedType convertedType;
        private final boolean wasConversion;

        public ConversionData(UnwrappedType unwrappedType, boolean z, boolean z2) {
            this.convertedType = unwrappedType;
            this.wasConversion = z;
            this.conversionDefinitelyNotNeeded = z2;
        }

        public final boolean getConversionDefinitelyNotNeeded() {
            return this.conversionDefinitelyNotNeeded;
        }

        public final UnwrappedType getConvertedType() {
            return this.convertedType;
        }

        public final boolean getWasConversion() {
            return this.wasConversion;
        }
    }
}
