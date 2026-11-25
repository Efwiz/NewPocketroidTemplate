package org.jetbrains.kotlin.fir.resolve.inference;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.expressions.FirResolvable;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateFactoryKt;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionContext;
import org.jetbrains.kotlin.fir.resolve.inference.model.ConeBuilderInferenceSubstitutionConstraintPosition;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeNullability;
import org.jetbrains.kotlin.fir.types.ConeStubTypeConstructor;
import org.jetbrains.kotlin.fir.types.ConeStubTypeForChainInference;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionContext;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintKind;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.InitialConstraint;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.MarkerExtensionsKt;
import org.jetbrains.kotlin.types.model.StubTypeMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;

/* JADX INFO: compiled from: FirInferenceSessionForChainedResolve.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J+\u0010\u0017\u001a\u00020\u0018\"\f\b\u0000\u0010\u0019*\u00020\u0007*\u00020\u001a2\u0006\u0010\u001b\u001a\u0002H\u00192\u0006\u0010\u0014\u001a\u00020\u000fH\u0016¢\u0006\u0002\u0010\u001cJ#\u0010\u001d\u001a\u00020\u0018\"\f\b\u0000\u0010\u0019*\u00020\u0007*\u00020\u001a2\u0006\u0010\u001b\u001a\u0002H\u0019H\u0016¢\u0006\u0002\u0010\u001eJ\b\u0010\u001f\u001a\u00020\u0018H\u0016J\u0018\u0010 \u001a\u00020\u00182\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0016J<\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020,2\u0012\u0010.\u001a\u000e\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u0002010/H\u0004J\u001c\u00102\u001a\u00020\u00182\u0012\u00103\u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u0002040/H\u0016J\u0014\u00105\u001a\u00020**\u00020*2\u0006\u00106\u001a\u000207H\u0004J(\u00105\u001a\u00020**\u00020*2\u0006\u00106\u001a\u0002072\u0012\u0010.\u001a\u000e\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u0002010/H\u0004R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\t8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR&\u0010\f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000f0\u000e0\rX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0018\u0010\u0014\u001a\u00020\u000f*\u00020\u00078DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/FirInferenceSessionForChainedResolve;", "Lorg/jetbrains/kotlin/fir/resolve/inference/FirInferenceSession;", "resolutionContext", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;)V", "completedCalls", "", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;", "components", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "getComponents", "()Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "partiallyResolvedCalls", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "getPartiallyResolvedCalls", "()Ljava/util/List;", "getResolutionContext", "()Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "candidate", "getCandidate", "(Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;)Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "addCompletedCall", "", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "call", "(Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;)V", "addPartiallyResolvedCall", "(Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;)V", LineReader.CLEAR, "fixSyntheticTypeVariableWithNotEnoughInformation", "typeVariable", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "completionContext", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionContext;", "integrateConstraintToSystem", "", "commonSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintSystemImpl;", "initialConstraint", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/InitialConstraint;", "callSubstitutor", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "nonFixedToVariablesSubstitutor", "fixedTypeVariables", "", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "registerStubTypes", "map", "Lorg/jetbrains/kotlin/types/model/StubTypeMarker;", "substitute", "substitutor", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirInferenceSessionForChainedResolve extends FirInferenceSession {
    private final Set<FirResolvable> completedCalls;
    private final List<Pair<FirResolvable, Candidate>> partiallyResolvedCalls;
    private final ResolutionContext resolutionContext;

    /* JADX INFO: compiled from: FirInferenceSessionForChainedResolve.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ConstraintKind.values().length];
            try {
                iArr[ConstraintKind.LOWER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ConstraintKind.UPPER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ConstraintKind.EQUALITY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSession
    public <T extends FirResolvable & FirStatement> void addCompletedCall(T call, Candidate candidate) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(candidate, "candidate");
    }

    @Override // org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSession
    public void fixSyntheticTypeVariableWithNotEnoughInformation(TypeVariableMarker typeVariable, ConstraintSystemCompletionContext completionContext) {
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        Intrinsics.checkNotNullParameter(completionContext, "completionContext");
    }

    @Override // org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSession
    public void registerStubTypes(Map<TypeVariableMarker, ? extends StubTypeMarker> map) {
        Intrinsics.checkNotNullParameter(map, "map");
    }

    protected final ResolutionContext getResolutionContext() {
        return this.resolutionContext;
    }

    public FirInferenceSessionForChainedResolve(ResolutionContext resolutionContext) {
        Intrinsics.checkNotNullParameter(resolutionContext, "resolutionContext");
        this.resolutionContext = resolutionContext;
        this.partiallyResolvedCalls = new ArrayList();
        this.completedCalls = new LinkedHashSet();
    }

    protected final List<Pair<FirResolvable, Candidate>> getPartiallyResolvedCalls() {
        return this.partiallyResolvedCalls;
    }

    protected final BodyResolveComponents getComponents() {
        return this.resolutionContext.getBodyResolveComponents();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSession
    public <T extends FirResolvable & FirStatement> void addPartiallyResolvedCall(T call) {
        Intrinsics.checkNotNullParameter(call, "call");
        this.partiallyResolvedCalls.add(TuplesKt.to(call, getCandidate(call)));
    }

    protected final Candidate getCandidate(FirResolvable firResolvable) {
        Intrinsics.checkNotNullParameter(firResolvable, "<this>");
        Candidate candidate = CandidateFactoryKt.candidate(firResolvable);
        Intrinsics.checkNotNull(candidate);
        return candidate;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSession
    public void clear() {
        this.partiallyResolvedCalls.clear();
        this.completedCalls.clear();
    }

    protected final boolean integrateConstraintToSystem(NewConstraintSystemImpl commonSystem, InitialConstraint initialConstraint, ConeSubstitutor callSubstitutor, ConeSubstitutor nonFixedToVariablesSubstitutor, Map<TypeConstructorMarker, ? extends KotlinTypeMarker> fixedTypeVariables) {
        Intrinsics.checkNotNullParameter(commonSystem, "commonSystem");
        Intrinsics.checkNotNullParameter(initialConstraint, "initialConstraint");
        Intrinsics.checkNotNullParameter(callSubstitutor, "callSubstitutor");
        Intrinsics.checkNotNullParameter(nonFixedToVariablesSubstitutor, "nonFixedToVariablesSubstitutor");
        Intrinsics.checkNotNullParameter(fixedTypeVariables, "fixedTypeVariables");
        InitialConstraint initialConstraintSubstitute = substitute(substitute(initialConstraint, callSubstitutor), nonFixedToVariablesSubstitutor, fixedTypeVariables);
        KotlinTypeMarker a = initialConstraintSubstitute.getA();
        KotlinTypeMarker b = initialConstraintSubstitute.getB();
        if (commonSystem.isProperType(a) && (Intrinsics.areEqual(a, b) || commonSystem.isProperType(b))) {
            return false;
        }
        ConstraintPosition position = initialConstraintSubstitute.getPosition();
        int i = WhenMappings.$EnumSwitchMapping$0[initialConstraint.getConstraintKind().ordinal()];
        if (i == 1) {
            throw new IllegalStateException("LOWER constraint shouldn't be used, please use UPPER".toString());
        }
        if (i == 2) {
            commonSystem.addSubtypeConstraint(a, b, position);
        } else if (i == 3) {
            commonSystem.addSubtypeConstraint(a, b, position);
            commonSystem.addSubtypeConstraint(b, a, position);
        }
        return true;
    }

    protected final InitialConstraint substitute(InitialConstraint initialConstraint, TypeSubstitutorMarker substitutor) {
        Intrinsics.checkNotNullParameter(initialConstraint, "<this>");
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        KotlinTypeMarker kotlinTypeMarkerSafeSubstitute = MarkerExtensionsKt.safeSubstitute(substitutor, this.resolutionContext.getTypeContext(), initialConstraint.getA());
        KotlinTypeMarker kotlinTypeMarkerSafeSubstitute2 = MarkerExtensionsKt.safeSubstitute(substitutor, this.resolutionContext.getTypeContext(), initialConstraint.getB());
        return (Intrinsics.areEqual(kotlinTypeMarkerSafeSubstitute, initialConstraint.getA()) && Intrinsics.areEqual(kotlinTypeMarkerSafeSubstitute2, initialConstraint.getB())) ? initialConstraint : new InitialConstraint(kotlinTypeMarkerSafeSubstitute, kotlinTypeMarkerSafeSubstitute2, initialConstraint.getConstraintKind(), new ConeBuilderInferenceSubstitutionConstraintPosition(initialConstraint));
    }

    protected final InitialConstraint substitute(InitialConstraint initialConstraint, TypeSubstitutorMarker substitutor, Map<TypeConstructorMarker, ? extends KotlinTypeMarker> fixedTypeVariables) {
        Intrinsics.checkNotNullParameter(initialConstraint, "<this>");
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        Intrinsics.checkNotNullParameter(fixedTypeVariables, "fixedTypeVariables");
        InitialConstraint initialConstraintSubstitute = substitute(initialConstraint, substitutor);
        KotlinTypeMarker a = initialConstraint.getA();
        if ((a instanceof ConeStubTypeForChainInference) && !(initialConstraintSubstitute.getA() instanceof ConeStubTypeForChainInference)) {
            ConeStubTypeConstructor constructor = ((ConeStubTypeForChainInference) a).getConstructor();
            KotlinTypeMarker kotlinTypeMarker = fixedTypeVariables.get(constructor.getVariable().getTypeConstructor());
            if ((kotlinTypeMarker instanceof ConeStubTypeForChainInference) && ((ConeStubTypeForChainInference) kotlinTypeMarker).getConstructor() == constructor && ConeTypeUtilsKt.isMarkedNullable((ConeKotlinType) kotlinTypeMarker)) {
                KotlinTypeMarker a2 = initialConstraintSubstitute.getA();
                KotlinTypeMarker b = initialConstraintSubstitute.getB();
                Intrinsics.checkNotNull(b, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinType");
                return new InitialConstraint(a2, TypeUtilsKt.withNullability$default((ConeKotlinType) b, ConeNullability.NULLABLE, this.resolutionContext.getTypeContext(), null, false, 12, null), initialConstraintSubstitute.getConstraintKind(), initialConstraintSubstitute.getPosition());
            }
        }
        return initialConstraintSubstitute;
    }
}
