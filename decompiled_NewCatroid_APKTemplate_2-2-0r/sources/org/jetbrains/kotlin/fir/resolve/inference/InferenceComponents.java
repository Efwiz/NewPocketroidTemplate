package org.jetbrains.kotlin.fir.resolve.inference;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeTypeApproximator;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintIncorporator;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintInjector;
import org.jetbrains.kotlin.resolve.calls.inference.components.PostponedArgumentInputTypesResolver;
import org.jetbrains.kotlin.resolve.calls.inference.components.ResultTypeResolver;
import org.jetbrains.kotlin.resolve.calls.inference.components.TrivialConstraintTypeInferenceOracle;
import org.jetbrains.kotlin.resolve.calls.inference.components.VariableFixationFinder;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl;

/* JADX INFO: compiled from: InferenceComponents.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001%B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010#\u001a\u00020$R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0015\u0010\u0007\u001a\u00060\bR\u00020\u0000¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0019\u001a\u00020\u001a¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u001f\u001a\u00020 ¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/InferenceComponents;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "approximator", "Lorg/jetbrains/kotlin/fir/types/ConeTypeApproximator;", "constraintSystemFactory", "Lorg/jetbrains/kotlin/fir/resolve/inference/InferenceComponents$ConstraintSystemFactory;", "getConstraintSystemFactory", "()Lorg/jetbrains/kotlin/fir/resolve/inference/InferenceComponents$ConstraintSystemFactory;", "incorporator", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintIncorporator;", "injector", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintInjector;", "postponedArgumentInputTypesResolver", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/PostponedArgumentInputTypesResolver;", "getPostponedArgumentInputTypesResolver", "()Lorg/jetbrains/kotlin/resolve/calls/inference/components/PostponedArgumentInputTypesResolver;", "resultTypeResolver", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ResultTypeResolver;", "getResultTypeResolver", "()Lorg/jetbrains/kotlin/resolve/calls/inference/components/ResultTypeResolver;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "trivialConstraintTypeInferenceOracle", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/TrivialConstraintTypeInferenceOracle;", "getTrivialConstraintTypeInferenceOracle", "()Lorg/jetbrains/kotlin/resolve/calls/inference/components/TrivialConstraintTypeInferenceOracle;", "typeContext", "Lorg/jetbrains/kotlin/fir/types/ConeInferenceContext;", "variableFixationFinder", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/VariableFixationFinder;", "getVariableFixationFinder", "()Lorg/jetbrains/kotlin/resolve/calls/inference/components/VariableFixationFinder;", "createConstraintSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintSystemImpl;", "ConstraintSystemFactory", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InferenceComponents implements FirSessionComponent {
    private final ConeTypeApproximator approximator;
    private final ConstraintSystemFactory constraintSystemFactory;
    private final ConstraintIncorporator incorporator;
    private final ConstraintInjector injector;
    private final PostponedArgumentInputTypesResolver postponedArgumentInputTypesResolver;
    private final ResultTypeResolver resultTypeResolver;
    private final FirSession session;
    private final TrivialConstraintTypeInferenceOracle trivialConstraintTypeInferenceOracle;
    private final ConeInferenceContext typeContext;
    private final VariableFixationFinder variableFixationFinder;

    public InferenceComponents(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
        ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(session);
        this.typeContext = typeContext;
        ConeTypeApproximator typeApproximator = TypeComponentsKt.getTypeApproximator(session);
        this.approximator = typeApproximator;
        TrivialConstraintTypeInferenceOracle trivialConstraintTypeInferenceOracleCreate = TrivialConstraintTypeInferenceOracle.INSTANCE.create(typeContext);
        this.trivialConstraintTypeInferenceOracle = trivialConstraintTypeInferenceOracleCreate;
        ConstraintIncorporator constraintIncorporator = new ConstraintIncorporator(typeApproximator, trivialConstraintTypeInferenceOracleCreate, ConeConstraintSystemUtilContext.INSTANCE);
        this.incorporator = constraintIncorporator;
        this.injector = new ConstraintInjector(constraintIncorporator, typeApproximator, FirLanguageSettingsComponentKt.getLanguageVersionSettings(session));
        ResultTypeResolver resultTypeResolver = new ResultTypeResolver(typeApproximator, trivialConstraintTypeInferenceOracleCreate, FirLanguageSettingsComponentKt.getLanguageVersionSettings(session));
        this.resultTypeResolver = resultTypeResolver;
        VariableFixationFinder variableFixationFinder = new VariableFixationFinder(trivialConstraintTypeInferenceOracleCreate, FirLanguageSettingsComponentKt.getLanguageVersionSettings(session));
        this.variableFixationFinder = variableFixationFinder;
        this.postponedArgumentInputTypesResolver = new PostponedArgumentInputTypesResolver(resultTypeResolver, variableFixationFinder, ConeConstraintSystemUtilContext.INSTANCE, FirLanguageSettingsComponentKt.getLanguageVersionSettings(session));
        this.constraintSystemFactory = new ConstraintSystemFactory();
    }

    public final FirSession getSession() {
        return this.session;
    }

    public final TrivialConstraintTypeInferenceOracle getTrivialConstraintTypeInferenceOracle() {
        return this.trivialConstraintTypeInferenceOracle;
    }

    public final ResultTypeResolver getResultTypeResolver() {
        return this.resultTypeResolver;
    }

    public final VariableFixationFinder getVariableFixationFinder() {
        return this.variableFixationFinder;
    }

    public final PostponedArgumentInputTypesResolver getPostponedArgumentInputTypesResolver() {
        return this.postponedArgumentInputTypesResolver;
    }

    public final ConstraintSystemFactory getConstraintSystemFactory() {
        return this.constraintSystemFactory;
    }

    public final NewConstraintSystemImpl createConstraintSystem() {
        return new NewConstraintSystemImpl(this.injector, this.typeContext, FirLanguageSettingsComponentKt.getLanguageVersionSettings(this.session));
    }

    /* JADX INFO: compiled from: InferenceComponents.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/InferenceComponents$ConstraintSystemFactory;", "", "(Lorg/jetbrains/kotlin/fir/resolve/inference/InferenceComponents;)V", "createConstraintSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintSystemImpl;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class ConstraintSystemFactory {
        public ConstraintSystemFactory() {
        }

        public final NewConstraintSystemImpl createConstraintSystem() {
            return InferenceComponents.this.createConstraintSystem();
        }
    }
}
