package org.jetbrains.kotlin.backend.common.phaser;

import com.google.android.gms.cast.MediaTrack;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.common.LoggingContext;

/* JADX INFO: compiled from: CompilerPhase.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000e\b&\u0018\u0000*\n\b\u0000\u0010\u0001 \u0000*\u00020\u0002*\u0004\b\u0001\u0010\u0003*\u0004\b\u0002\u0010\u00042\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u00040\u0005B\u0087\u0001\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u001a\b\u0002\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00000\n\u0012$\b\u0002\u0010\u000b\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\r0\fj\b\u0012\u0004\u0012\u00028\u0001`\u000e0\n\u0012$\b\u0002\u0010\u000f\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00020\r0\fj\b\u0012\u0004\u0012\u00028\u0002`\u000e0\n\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J\u001c\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00020\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00010\u001dH&J3\u0010\u001f\u001a\u00028\u00022\u0006\u0010 \u001a\u00020!2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00010\u001d2\u0006\u0010\"\u001a\u00028\u00002\u0006\u0010#\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010$J3\u0010%\u001a\u00028\u00022\u0006\u0010 \u001a\u00020!2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00010\u001d2\u0006\u0010\"\u001a\u00028\u00002\u0006\u0010#\u001a\u00028\u0001H&¢\u0006\u0002\u0010$J3\u0010&\u001a\u00028\u00022\u0006\u0010 \u001a\u00020!2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00010\u001d2\u0006\u0010\"\u001a\u00028\u00002\u0006\u0010#\u001a\u00028\u0001H&¢\u0006\u0002\u0010$J;\u0010'\u001a\u00020\r2\u0006\u0010 \u001a\u00020!2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00020\u001d2\u0006\u0010\"\u001a\u00028\u00002\u0006\u0010#\u001a\u00028\u00012\u0006\u0010(\u001a\u00028\u0002H&¢\u0006\u0002\u0010)J3\u0010*\u001a\u00028\u00022\u0006\u0010 \u001a\u00020!2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00010\u001d2\u0006\u0010\"\u001a\u00028\u00002\u0006\u0010+\u001a\u00028\u0001H\u0002¢\u0006\u0002\u0010$J3\u0010,\u001a\u00020\r2\u0006\u0010 \u001a\u00020!2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00010\u001d2\u0006\u0010\"\u001a\u00028\u00002\u0006\u0010#\u001a\u00028\u0001H&¢\u0006\u0002\u0010-J\b\u0010.\u001a\u00020\u0007H\u0016R\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0014\u0010\u0010\u001a\u00020\u0011X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R-\u0010\u000f\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00020\r0\fj\b\u0012\u0004\u0012\u00028\u0002`\u000e0\n¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R-\u0010\u000b\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\r0\fj\b\u0012\u0004\u0012\u00028\u0001`\u000e0\n¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0019R#\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00000\n¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0019¨\u0006/"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/phaser/AbstractNamedCompilerPhase;", "Context", "Lorg/jetbrains/kotlin/backend/common/LoggingContext;", "Input", "Output", "Lorg/jetbrains/kotlin/backend/common/phaser/CompilerPhase;", "name", "", MediaTrack.ROLE_DESCRIPTION, "prerequisite", "", "preconditions", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/backend/common/phaser/Checker;", "postconditions", "nlevels", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;I)V", "getDescription", "()Ljava/lang/String;", "getName", "getNlevels", "()I", "getPostconditions", "()Ljava/util/Set;", "getPreconditions", "getPrerequisite", "changePhaserStateType", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState;", "phaserState", "invoke", "phaseConfig", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;", "context", "input", "(Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState;Lorg/jetbrains/kotlin/backend/common/LoggingContext;Ljava/lang/Object;)Ljava/lang/Object;", "outputIfNotEnabled", "phaseBody", "runAfter", "output", "(Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState;Lorg/jetbrains/kotlin/backend/common/LoggingContext;Ljava/lang/Object;Ljava/lang/Object;)V", "runAndProfile", FirebaseAnalytics.Param.SOURCE, "runBefore", "(Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState;Lorg/jetbrains/kotlin/backend/common/LoggingContext;Ljava/lang/Object;)V", "toString", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractNamedCompilerPhase<Context extends LoggingContext, Input, Output> implements CompilerPhase<Context, Input, Output> {
    private final String description;
    private final String name;
    private final int nlevels;
    private final Set<Function1<Output, Unit>> postconditions;
    private final Set<Function1<Input, Unit>> preconditions;
    private final Set<AbstractNamedCompilerPhase<?, ?, ?>> prerequisite;

    public abstract PhaserState<Output> changePhaserStateType(PhaserState<Input> phaserState);

    public abstract Output outputIfNotEnabled(PhaseConfigurationService phaseConfig, PhaserState<Input> phaserState, Context context, Input input);

    public abstract Output phaseBody(PhaseConfigurationService phaseConfig, PhaserState<Input> phaserState, Context context, Input input);

    public abstract void runAfter(PhaseConfigurationService phaseConfig, PhaserState<Output> phaserState, Context context, Input input, Output output);

    public abstract void runBefore(PhaseConfigurationService phaseConfig, PhaserState<Input> phaserState, Context context, Input input);

    /* JADX WARN: Multi-variable type inference failed */
    public AbstractNamedCompilerPhase(String name2, String description, Set<? extends AbstractNamedCompilerPhase<?, ?, ?>> prerequisite, Set<? extends Function1<? super Input, Unit>> preconditions, Set<? extends Function1<? super Output, Unit>> postconditions, int i) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(prerequisite, "prerequisite");
        Intrinsics.checkNotNullParameter(preconditions, "preconditions");
        Intrinsics.checkNotNullParameter(postconditions, "postconditions");
        this.name = name2;
        this.description = description;
        this.prerequisite = prerequisite;
        this.preconditions = preconditions;
        this.postconditions = postconditions;
        this.nlevels = i;
    }

    public final String getName() {
        return this.name;
    }

    public final String getDescription() {
        return this.description;
    }

    public /* synthetic */ AbstractNamedCompilerPhase(String str, String str2, Set set, Set set2, Set set3, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i2 & 4) != 0 ? SetsKt.emptySet() : set, (i2 & 8) != 0 ? SetsKt.emptySet() : set2, (i2 & 16) != 0 ? SetsKt.emptySet() : set3, (i2 & 32) != 0 ? 0 : i);
    }

    public final Set<AbstractNamedCompilerPhase<?, ?, ?>> getPrerequisite() {
        return this.prerequisite;
    }

    public final Set<Function1<Input, Unit>> getPreconditions() {
        return this.preconditions;
    }

    public final Set<Function1<Output, Unit>> getPostconditions() {
        return this.postconditions;
    }

    protected final int getNlevels() {
        return this.nlevels;
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.CompilerPhase
    public Output invoke(PhaseConfigurationService phaseConfig, PhaserState<Input> phaserState, Context context, Input input) {
        Output outputRunAndProfile;
        Intrinsics.checkNotNullParameter(phaseConfig, "phaseConfig");
        Intrinsics.checkNotNullParameter(phaserState, "phaserState");
        Intrinsics.checkNotNullParameter(context, "context");
        if (!phaseConfig.isEnabled(this)) {
            return outputIfNotEnabled(phaseConfig, phaserState, context, input);
        }
        phaserState.getAlreadyDone().containsAll(this.prerequisite);
        context.setInVerbosePhase(phaseConfig.isVerbose(this));
        runBefore(phaseConfig, phaserState, context, input);
        if (phaseConfig.getNeedProfiling()) {
            outputRunAndProfile = runAndProfile(phaseConfig, phaserState, context, input);
        } else {
            int i = this.nlevels;
            phaserState.setDepth(phaserState.getDepth() + i);
            Output outputPhaseBody = phaseBody(phaseConfig, phaserState, context, input);
            phaserState.setDepth(phaserState.getDepth() - i);
            outputRunAndProfile = outputPhaseBody;
        }
        runAfter(phaseConfig, changePhaserStateType(phaserState), context, input, outputRunAndProfile);
        phaserState.getAlreadyDone().add(this);
        phaserState.setPhaseCount(phaserState.getPhaseCount() + 1);
        return outputRunAndProfile;
    }

    private final Output runAndProfile(PhaseConfigurationService phaseConfig, PhaserState<Input> phaserState, Context context, Input source) {
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        long jCurrentTimeMillis = System.currentTimeMillis();
        int i = this.nlevels;
        phaserState.setDepth(phaserState.getDepth() + i);
        Output outputPhaseBody = phaseBody(phaseConfig, phaserState, context, source);
        phaserState.setDepth(phaserState.getDepth() - i);
        objectRef.element = outputPhaseBody;
        System.out.println((Object) (StringsKt.repeat("\t", phaserState.getDepth()) + this.description + ": " + (System.currentTimeMillis() - jCurrentTimeMillis) + " msec"));
        Output output = (Output) objectRef.element;
        Intrinsics.checkNotNull(output);
        return output;
    }

    public String toString() {
        return "Compiler Phase @" + this.name;
    }
}
