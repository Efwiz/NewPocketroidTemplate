package org.jetbrains.kotlin.fir.resolve.inference;

import androidx.exifinterface.media.ExifInterface;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.fir.expressions.FirResolvable;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeStubType;
import org.jetbrains.kotlin.fir.types.ConeTypeVariableTypeConstructor;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionContext;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionMode;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl;
import org.jetbrains.kotlin.types.model.StubTypeMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;

/* JADX INFO: compiled from: FirInferenceSession.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u0000 /2\u00020\u0001:\u0001/B\u0005¢\u0006\u0002\u0010\u0002J+\u0010\u0007\u001a\u00020\b\"\f\b\u0000\u0010\t*\u00020\n*\u00020\u000b2\u0006\u0010\f\u001a\u0002H\t2\u0006\u0010\r\u001a\u00020\u000eH&¢\u0006\u0002\u0010\u000fJ#\u0010\u0010\u001a\u00020\b\"\f\b\u0000\u0010\t*\u00020\n*\u00020\u000b2\u0006\u0010\f\u001a\u0002H\tH&¢\u0006\u0002\u0010\u0011J\b\u0010\u0012\u001a\u00020\bH&J\u001c\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u00142\u0006\u0010\u0017\u001a\u00020\u0018H&J\u0018\u0010\u0019\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH&J\b\u0010\u001e\u001a\u00020\u001fH&J.\u0010 \u001a\u0010\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\"\u0018\u00010\u00142\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(H&J\u0010\u0010)\u001a\u00020\u001f2\u0006\u0010\u001a\u001a\u00020\u001bH&J\u001c\u0010*\u001a\u00020\b2\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020,0\u0014H&J#\u0010-\u001a\u00020\u001f\"\f\b\u0000\u0010\t*\u00020\n*\u00020\u000b2\u0006\u0010\f\u001a\u0002H\tH&¢\u0006\u0002\u0010.R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/FirInferenceSession;", "", "()V", "currentConstraintStorage", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "getCurrentConstraintStorage", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "addCompletedCall", "", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "call", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "(Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;)V", "addPartiallyResolvedCall", "(Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;)V", LineReader.CLEAR, "createSyntheticStubTypes", "", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "Lorg/jetbrains/kotlin/fir/types/ConeStubType;", "system", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintSystemImpl;", "fixSyntheticTypeVariableWithNotEnoughInformation", "typeVariable", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "completionContext", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionContext;", "hasSyntheticTypeVariables", "", "inferPostponedVariables", "Lorg/jetbrains/kotlin/fir/types/ConeTypeVariableTypeConstructor;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "lambda", "Lorg/jetbrains/kotlin/fir/resolve/inference/ResolvedLambdaAtom;", "constraintSystemBuilder", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilder;", "completionMode", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionMode;", "isSyntheticTypeVariable", "registerStubTypes", "map", "Lorg/jetbrains/kotlin/types/model/StubTypeMarker;", "shouldRunCompletion", "(Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;)Z", "Companion", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirInferenceSession {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final FirInferenceSession DEFAULT = new FirStubInferenceSession() { // from class: org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSession$Companion$DEFAULT$1
    };

    public abstract <T extends FirResolvable & FirStatement> void addCompletedCall(T call, Candidate candidate);

    public abstract <T extends FirResolvable & FirStatement> void addPartiallyResolvedCall(T call);

    public abstract void clear();

    public abstract Map<TypeConstructorMarker, ConeStubType> createSyntheticStubTypes(NewConstraintSystemImpl system);

    public abstract void fixSyntheticTypeVariableWithNotEnoughInformation(TypeVariableMarker typeVariable, ConstraintSystemCompletionContext completionContext);

    public abstract ConstraintStorage getCurrentConstraintStorage();

    public abstract boolean hasSyntheticTypeVariables();

    public abstract Map<ConeTypeVariableTypeConstructor, ConeKotlinType> inferPostponedVariables(ResolvedLambdaAtom lambda, ConstraintSystemBuilder constraintSystemBuilder, ConstraintSystemCompletionMode completionMode);

    public abstract boolean isSyntheticTypeVariable(TypeVariableMarker typeVariable);

    public abstract void registerStubTypes(Map<TypeVariableMarker, ? extends StubTypeMarker> map);

    public abstract <T extends FirResolvable & FirStatement> boolean shouldRunCompletion(T call);

    /* JADX INFO: compiled from: FirInferenceSession.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/FirInferenceSession$Companion;", "", "()V", "DEFAULT", "Lorg/jetbrains/kotlin/fir/resolve/inference/FirInferenceSession;", "getDEFAULT", "()Lorg/jetbrains/kotlin/fir/resolve/inference/FirInferenceSession;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FirInferenceSession getDEFAULT() {
            return FirInferenceSession.DEFAULT;
        }
    }
}
