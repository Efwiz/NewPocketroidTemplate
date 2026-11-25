package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.ReflectionTypes;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsToParametersMapper;
import org.jetbrains.kotlin.resolve.calls.components.CallableReferenceArgumentResolver;
import org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionStatelessCallbacks;
import org.jetbrains.kotlin.resolve.calls.components.TypeArgumentsToParametersMapper;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintInjector;
import org.jetbrains.kotlin.resolve.sam.SamConversionOracle;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolver;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.checker.NewKotlinTypeChecker;

/* JADX INFO: compiled from: KotlinResolverContext.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001c\u0018\u00002\u00020\u0001Bm\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u0012\u0006\u0010\u0016\u001a\u00020\u0017\u0012\u0006\u0010\u0018\u001a\u00020\u0019\u0012\u0006\u0010\u001a\u001a\u00020\u001b¢\u0006\u0002\u0010\u001cR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u001a\u001a\u00020\u001b¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010\u0014\u001a\u00020\u0015¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0011\u0010\u0018\u001a\u00020\u0019¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0011\u0010\u0016\u001a\u00020\u0017¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b/\u00100R\u0011\u0010\u0012\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b1\u00102R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b3\u00104R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b5\u00106¨\u00067"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;", "", "statelessCallbacks", "Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionStatelessCallbacks;", "argumentsToParametersMapper", "Lorg/jetbrains/kotlin/resolve/calls/components/ArgumentsToParametersMapper;", "typeArgumentsToParametersMapper", "Lorg/jetbrains/kotlin/resolve/calls/components/TypeArgumentsToParametersMapper;", "constraintInjector", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintInjector;", "reflectionTypes", "Lorg/jetbrains/kotlin/builtins/ReflectionTypes;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "samConversionOracle", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionOracle;", "samConversionResolver", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;", "kotlinTypeChecker", "Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeChecker;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "callableReferenceArgumentResolver", "Lorg/jetbrains/kotlin/resolve/calls/components/CallableReferenceArgumentResolver;", "(Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionStatelessCallbacks;Lorg/jetbrains/kotlin/resolve/calls/components/ArgumentsToParametersMapper;Lorg/jetbrains/kotlin/resolve/calls/components/TypeArgumentsToParametersMapper;Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintInjector;Lorg/jetbrains/kotlin/builtins/ReflectionTypes;Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/resolve/sam/SamConversionOracle;Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeChecker;Lorg/jetbrains/kotlin/incremental/components/LookupTracker;Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;Lorg/jetbrains/kotlin/resolve/calls/components/CallableReferenceArgumentResolver;)V", "getArgumentsToParametersMapper", "()Lorg/jetbrains/kotlin/resolve/calls/components/ArgumentsToParametersMapper;", "getBuiltIns", "()Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getCallableReferenceArgumentResolver", "()Lorg/jetbrains/kotlin/resolve/calls/components/CallableReferenceArgumentResolver;", "getConstraintInjector", "()Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintInjector;", "getKotlinTypeChecker", "()Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeChecker;", "getKotlinTypeRefiner", "()Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getLookupTracker", "()Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "getReflectionTypes", "()Lorg/jetbrains/kotlin/builtins/ReflectionTypes;", "getSamConversionOracle", "()Lorg/jetbrains/kotlin/resolve/sam/SamConversionOracle;", "getSamConversionResolver", "()Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;", "getStatelessCallbacks", "()Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionStatelessCallbacks;", "getTypeArgumentsToParametersMapper", "()Lorg/jetbrains/kotlin/resolve/calls/components/TypeArgumentsToParametersMapper;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinCallComponents {
    private final ArgumentsToParametersMapper argumentsToParametersMapper;
    private final KotlinBuiltIns builtIns;
    private final CallableReferenceArgumentResolver callableReferenceArgumentResolver;
    private final ConstraintInjector constraintInjector;
    private final NewKotlinTypeChecker kotlinTypeChecker;
    private final KotlinTypeRefiner kotlinTypeRefiner;
    private final LanguageVersionSettings languageVersionSettings;
    private final LookupTracker lookupTracker;
    private final ReflectionTypes reflectionTypes;
    private final SamConversionOracle samConversionOracle;
    private final SamConversionResolver samConversionResolver;
    private final KotlinResolutionStatelessCallbacks statelessCallbacks;
    private final TypeArgumentsToParametersMapper typeArgumentsToParametersMapper;

    public KotlinCallComponents(KotlinResolutionStatelessCallbacks statelessCallbacks, ArgumentsToParametersMapper argumentsToParametersMapper, TypeArgumentsToParametersMapper typeArgumentsToParametersMapper, ConstraintInjector constraintInjector, ReflectionTypes reflectionTypes, KotlinBuiltIns builtIns, LanguageVersionSettings languageVersionSettings, SamConversionOracle samConversionOracle, SamConversionResolver samConversionResolver, NewKotlinTypeChecker kotlinTypeChecker, LookupTracker lookupTracker, KotlinTypeRefiner kotlinTypeRefiner, CallableReferenceArgumentResolver callableReferenceArgumentResolver) {
        Intrinsics.checkNotNullParameter(statelessCallbacks, "statelessCallbacks");
        Intrinsics.checkNotNullParameter(argumentsToParametersMapper, "argumentsToParametersMapper");
        Intrinsics.checkNotNullParameter(typeArgumentsToParametersMapper, "typeArgumentsToParametersMapper");
        Intrinsics.checkNotNullParameter(constraintInjector, "constraintInjector");
        Intrinsics.checkNotNullParameter(reflectionTypes, "reflectionTypes");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(samConversionOracle, "samConversionOracle");
        Intrinsics.checkNotNullParameter(samConversionResolver, "samConversionResolver");
        Intrinsics.checkNotNullParameter(kotlinTypeChecker, "kotlinTypeChecker");
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        Intrinsics.checkNotNullParameter(callableReferenceArgumentResolver, "callableReferenceArgumentResolver");
        this.statelessCallbacks = statelessCallbacks;
        this.argumentsToParametersMapper = argumentsToParametersMapper;
        this.typeArgumentsToParametersMapper = typeArgumentsToParametersMapper;
        this.constraintInjector = constraintInjector;
        this.reflectionTypes = reflectionTypes;
        this.builtIns = builtIns;
        this.languageVersionSettings = languageVersionSettings;
        this.samConversionOracle = samConversionOracle;
        this.samConversionResolver = samConversionResolver;
        this.kotlinTypeChecker = kotlinTypeChecker;
        this.lookupTracker = lookupTracker;
        this.kotlinTypeRefiner = kotlinTypeRefiner;
        this.callableReferenceArgumentResolver = callableReferenceArgumentResolver;
    }

    public final KotlinResolutionStatelessCallbacks getStatelessCallbacks() {
        return this.statelessCallbacks;
    }

    public final ArgumentsToParametersMapper getArgumentsToParametersMapper() {
        return this.argumentsToParametersMapper;
    }

    public final TypeArgumentsToParametersMapper getTypeArgumentsToParametersMapper() {
        return this.typeArgumentsToParametersMapper;
    }

    public final ConstraintInjector getConstraintInjector() {
        return this.constraintInjector;
    }

    public final ReflectionTypes getReflectionTypes() {
        return this.reflectionTypes;
    }

    public final KotlinBuiltIns getBuiltIns() {
        return this.builtIns;
    }

    public final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    public final SamConversionOracle getSamConversionOracle() {
        return this.samConversionOracle;
    }

    public final SamConversionResolver getSamConversionResolver() {
        return this.samConversionResolver;
    }

    public final NewKotlinTypeChecker getKotlinTypeChecker() {
        return this.kotlinTypeChecker;
    }

    public final LookupTracker getLookupTracker() {
        return this.lookupTracker;
    }

    public final KotlinTypeRefiner getKotlinTypeRefiner() {
        return this.kotlinTypeRefiner;
    }

    public final CallableReferenceArgumentResolver getCallableReferenceArgumentResolver() {
        return this.callableReferenceArgumentResolver;
    }
}
