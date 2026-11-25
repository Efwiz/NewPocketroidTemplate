package org.jetbrains.kotlin.resolve.calls.inference.components;

import androidx.core.app.NotificationCompat;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemOperation;
import org.jetbrains.kotlin.resolve.calls.inference.model.CapturedTypeFromSubtyping;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstrainingTypeIsError;
import org.jetbrains.kotlin.resolve.calls.inference.model.Constraint;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintKind;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintSystemError;
import org.jetbrains.kotlin.resolve.calls.inference.model.DeclaredUpperBoundConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.FixVariableConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.IncorporationConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.InitialConstraint;
import org.jetbrains.kotlin.resolve.calls.inference.model.MutableVariableWithConstraints;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintError;
import org.jetbrains.kotlin.resolve.calls.inference.model.OnlyInputTypeConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.VariableWithConstraints;
import org.jetbrains.kotlin.resolve.checkers.EmptyIntersectionTypeInfo;
import org.jetbrains.kotlin.types.AbstractTypeApproximator;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.TypeApproximatorConfiguration;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.model.AnnotationMarker;
import org.jetbrains.kotlin.types.model.CaptureStatus;
import org.jetbrains.kotlin.types.model.CapturedTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.CapturedTypeMarker;
import org.jetbrains.kotlin.types.model.DefinitelyNotNullTypeMarker;
import org.jetbrains.kotlin.types.model.DynamicTypeMarker;
import org.jetbrains.kotlin.types.model.FlexibleTypeMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.MarkerExtensionsKt;
import org.jetbrains.kotlin.types.model.ObsoleteTypeKind;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.StubTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentListMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContextHelpersKt;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;
import org.jetbrains.kotlin.types.model.TypeVariableTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeVariance;
import org.jetbrains.kotlin.utils.SmartList;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;

/* JADX INFO: compiled from: ConstraintInjector.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\n\u0018\u00002\u00020\u0001:\u00029:B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ,\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\n\u0010\u0016\u001a\u00060\u0017R\u00020\u0000H\u0002J&\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u001cJ&\u0010\u001d\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u001e\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u001cJ,\u0010 \u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u001e\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020\u00142\n\u0010\u0016\u001a\u00060\u0017R\u00020\u0000H\u0002J:\u0010!\u001a\u0016\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020%0#\u0018\u00010\"2\u0006\u0010\u0011\u001a\u00020\u00122\n\u0010\u0016\u001a\u00060\u0017R\u00020\u00002\b\b\u0002\u0010&\u001a\u00020'H\u0002J8\u0010(\u001a\u0016\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020%0#\u0018\u00010\"2\n\u0010\u0016\u001a\u00060\u0017R\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010&\u001a\u00020'H\u0002J6\u0010)\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\n\u0010\u0016\u001a\u00060\u0017R\u00020\u00002\u0018\u0010*\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020%0#0+H\u0002J0\u0010,\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0018\u0010-\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020%0#0+2\u0006\u0010\u001b\u001a\u00020.J0\u0010/\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020.2\u0018\u00100\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020%0#01J\u0018\u00102\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u00103\u001a\u00020\u0014H\u0002J(\u00104\u001a\u00020\u0010*\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u00142\n\u0010\u0016\u001a\u00060\u0017R\u00020\u0000H\u0002J\u0014\u00105\u001a\u00020'*\u00020\u00122\u0006\u00106\u001a\u00020\u0014H\u0002J\u001c\u00107\u001a\u00020'*\u00020\u00122\u0006\u0010\u0013\u001a\u00020$2\u0006\u00108\u001a\u00020%H\u0002R\u000e\u0010\t\u001a\u00020\nX\u0082D¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006;"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintInjector;", "", "constraintIncorporator", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintIncorporator;", "typeApproximator", "Lorg/jetbrains/kotlin/types/AbstractTypeApproximator;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintIncorporator;Lorg/jetbrains/kotlin/types/AbstractTypeApproximator;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "ALLOWED_DEPTH_DELTA_FOR_INCORPORATION", "", "getConstraintIncorporator", "()Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintIncorporator;", "getTypeApproximator", "()Lorg/jetbrains/kotlin/types/AbstractTypeApproximator;", "addEqualityConstraintAndIncorporateIt", "", "c", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintInjector$Context;", "typeVariable", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "equalType", "typeCheckerState", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintInjector$TypeCheckerStateForConstraintInjector;", "addInitialEqualityConstraint", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "position", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintPosition;", "addInitialSubtypeConstraint", "lowerType", "upperType", "addSubTypeConstraintAndIncorporateIt", "processConstraints", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/Constraint;", "skipProperEqualityConstraints", "", "processConstraintsIgnoringForksData", "processGivenConstraints", "constraintsToProcess", "", "processGivenForkPointBranchConstraints", "constraintSet", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/IncorporationConstraintPosition;", "processMissedConstraints", "missedConstraints", "", "updateAllowedTypeDepth", "initialType", "addInitialEqualityConstraintThroughSubtyping", "isAllowedType", ModuleXmlParser.TYPE, "shouldWeSkipConstraint", "constraint", "Context", "TypeCheckerStateForConstraintInjector", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConstraintInjector {
    private final int ALLOWED_DEPTH_DELTA_FOR_INCORPORATION;
    private final ConstraintIncorporator constraintIncorporator;
    private final LanguageVersionSettings languageVersionSettings;
    private final AbstractTypeApproximator typeApproximator;

    /* JADX INFO: compiled from: ConstraintInjector.kt */
    @Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\u0010\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'H&J\u0010\u0010(\u001a\u00020%2\u0006\u0010)\u001a\u00020*H&J*\u0010+\u001a\u00020%2\u0006\u0010,\u001a\u00020\u000f2\u0018\u0010-\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00120\u000e0\rH&J\b\u0010.\u001a\u00020%H&R\u001e\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bRD\u0010\f\u001a4\u00120\u0012.\u0012\u0004\u0012\u00020\u000f\u0012$\u0012\"\u0012\u001a\u0012\u0018\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00120\u000e0\u0011j\u0002`\u00130\u0010j\u0002`\u00140\u000e0\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u001e\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00190\u0018X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0007R\u0018\u0010\u001b\u001a\u00020\u001cX¦\u000e¢\u0006\f\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001e\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\"0\u0018X¦\u0004¢\u0006\u0006\u001a\u0004\b#\u0010\u0007ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006/À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintInjector$Context;", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "allTypeVariables", "", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "getAllTypeVariables", "()Ljava/util/Map;", "atCompletionState", "", "getAtCompletionState", "()Z", "constraintsFromAllForkPoints", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/IncorporationConstraintPosition;", "", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/Constraint;", "Lorg/jetbrains/kotlin/resolve/calls/inference/ForkPointBranchDescription;", "Lorg/jetbrains/kotlin/resolve/calls/inference/ForkPointData;", "getConstraintsFromAllForkPoints", "()Ljava/util/List;", "fixedTypeVariables", "", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "getFixedTypeVariables", "maxTypeDepthFromInitialConstraints", "", "getMaxTypeDepthFromInitialConstraints", "()I", "setMaxTypeDepthFromInitialConstraints", "(I)V", "notFixedTypeVariables", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/MutableVariableWithConstraints;", "getNotFixedTypeVariables", "addError", "", "error", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;", "addInitialConstraint", "initialConstraint", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/InitialConstraint;", "addMissedConstraints", "position", "constraints", "resolveForkPointsConstraints", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface Context extends TypeSystemInferenceExtensionContext {
        void addError(ConstraintSystemError error);

        void addInitialConstraint(InitialConstraint initialConstraint);

        void addMissedConstraints(IncorporationConstraintPosition position, List<Pair<TypeVariableMarker, Constraint>> constraints);

        Map<TypeConstructorMarker, TypeVariableMarker> getAllTypeVariables();

        boolean getAtCompletionState();

        List<Pair<IncorporationConstraintPosition, List<Set<Pair<TypeVariableMarker, Constraint>>>>> getConstraintsFromAllForkPoints();

        Map<TypeConstructorMarker, KotlinTypeMarker> getFixedTypeVariables();

        int getMaxTypeDepthFromInitialConstraints();

        Map<TypeConstructorMarker, MutableVariableWithConstraints> getNotFixedTypeVariables();

        void resolveForkPointsConstraints();

        void setMaxTypeDepthFromInitialConstraints(int i);
    }

    public ConstraintInjector(ConstraintIncorporator constraintIncorporator, AbstractTypeApproximator typeApproximator, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(constraintIncorporator, "constraintIncorporator");
        Intrinsics.checkNotNullParameter(typeApproximator, "typeApproximator");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.constraintIncorporator = constraintIncorporator;
        this.typeApproximator = typeApproximator;
        this.languageVersionSettings = languageVersionSettings;
        this.ALLOWED_DEPTH_DELTA_FOR_INCORPORATION = 1;
    }

    public final ConstraintIncorporator getConstraintIncorporator() {
        return this.constraintIncorporator;
    }

    public final AbstractTypeApproximator getTypeApproximator() {
        return this.typeApproximator;
    }

    public final void addInitialSubtypeConstraint(Context c, KotlinTypeMarker lowerType, KotlinTypeMarker upperType, ConstraintPosition position) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(lowerType, "lowerType");
        Intrinsics.checkNotNullParameter(upperType, "upperType");
        Intrinsics.checkNotNullParameter(position, "position");
        InitialConstraint initialConstraint = new InitialConstraint(lowerType, upperType, ConstraintKind.UPPER, position);
        c.addInitialConstraint(initialConstraint);
        TypeCheckerStateForConstraintInjector typeCheckerStateForConstraintInjector = new TypeCheckerStateForConstraintInjector(this, c, new IncorporationConstraintPosition(initialConstraint, false, 2, null));
        updateAllowedTypeDepth(c, lowerType);
        updateAllowedTypeDepth(c, upperType);
        addSubTypeConstraintAndIncorporateIt(c, lowerType, upperType, typeCheckerStateForConstraintInjector);
    }

    private final void addInitialEqualityConstraintThroughSubtyping(Context context, KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2, TypeCheckerStateForConstraintInjector typeCheckerStateForConstraintInjector) {
        updateAllowedTypeDepth(context, kotlinTypeMarker);
        updateAllowedTypeDepth(context, kotlinTypeMarker2);
        addSubTypeConstraintAndIncorporateIt(context, kotlinTypeMarker, kotlinTypeMarker2, typeCheckerStateForConstraintInjector);
        addSubTypeConstraintAndIncorporateIt(context, kotlinTypeMarker2, kotlinTypeMarker, typeCheckerStateForConstraintInjector);
    }

    public final void addInitialEqualityConstraint(Context c, KotlinTypeMarker a, KotlinTypeMarker b, ConstraintPosition position) {
        Pair pair;
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Intrinsics.checkNotNullParameter(position, "position");
        Context context = c;
        if (TypeSystemContextHelpersKt.typeConstructor(a, context) instanceof TypeVariableTypeConstructorMarker) {
            pair = TuplesKt.to(a, b);
        } else if (!(TypeSystemContextHelpersKt.typeConstructor(b, context) instanceof TypeVariableTypeConstructorMarker)) {
            return;
        } else {
            pair = TuplesKt.to(b, a);
        }
        KotlinTypeMarker kotlinTypeMarker = (KotlinTypeMarker) pair.component1();
        KotlinTypeMarker kotlinTypeMarker2 = (KotlinTypeMarker) pair.component2();
        InitialConstraint initialConstraint = new InitialConstraint(kotlinTypeMarker, kotlinTypeMarker2, ConstraintKind.EQUALITY, position);
        c.addInitialConstraint(initialConstraint);
        TypeCheckerStateForConstraintInjector typeCheckerStateForConstraintInjector = new TypeCheckerStateForConstraintInjector(this, c, new IncorporationConstraintPosition(initialConstraint, false, 2, null));
        if (!c.isSimpleType(kotlinTypeMarker) || c.isMarkedNullable(kotlinTypeMarker)) {
            addInitialEqualityConstraintThroughSubtyping(c, kotlinTypeMarker, kotlinTypeMarker2, typeCheckerStateForConstraintInjector);
        } else {
            updateAllowedTypeDepth(c, kotlinTypeMarker2);
            addEqualityConstraintAndIncorporateIt(c, kotlinTypeMarker, kotlinTypeMarker2, typeCheckerStateForConstraintInjector);
        }
    }

    private final void addSubTypeConstraintAndIncorporateIt(Context c, KotlinTypeMarker lowerType, KotlinTypeMarker upperType, TypeCheckerStateForConstraintInjector typeCheckerState) {
        typeCheckerState.setConstrainingTypesToPrintDebugInfo(lowerType, upperType);
        TypeCheckerStateForConstraintInjector.runIsSubtypeOf$default(typeCheckerState, lowerType, upperType, false, false, 12, null);
        List<Pair<TypeVariableMarker, Constraint>> listProcessConstraints$default = processConstraints$default(this, c, typeCheckerState, false, 4, null);
        if (listProcessConstraints$default != null) {
            c.addMissedConstraints(typeCheckerState.getPosition(), listProcessConstraints$default);
        }
    }

    private final void addEqualityConstraintAndIncorporateIt(Context c, KotlinTypeMarker typeVariable, KotlinTypeMarker equalType, TypeCheckerStateForConstraintInjector typeCheckerState) {
        typeCheckerState.setConstrainingTypesToPrintDebugInfo(typeVariable, equalType);
        typeCheckerState.addEqualityConstraint(TypeSystemContextHelpersKt.typeConstructor(typeVariable, c), equalType);
        List<Pair<TypeVariableMarker, Constraint>> listProcessConstraints$default = processConstraints$default(this, c, typeCheckerState, false, 4, null);
        if (listProcessConstraints$default != null) {
            c.addMissedConstraints(typeCheckerState.getPosition(), listProcessConstraints$default);
        }
    }

    public final void processMissedConstraints(Context c, IncorporationConstraintPosition position, List<? extends Pair<? extends TypeVariableMarker, Constraint>> missedConstraints) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(position, "position");
        Intrinsics.checkNotNullParameter(missedConstraints, "missedConstraints");
        if (this.languageVersionSettings.supportsFeature(LanguageFeature.ProperTypeInferenceConstraintsProcessing)) {
            return;
        }
        TypeCheckerStateForConstraintInjector typeCheckerStateForConstraintInjector = new TypeCheckerStateForConstraintInjector(this, c, position);
        for (Pair<? extends TypeVariableMarker, Constraint> pair : missedConstraints) {
            typeCheckerStateForConstraintInjector.addPossibleNewConstraint(pair.component1(), pair.component2());
        }
        processConstraints(c, typeCheckerStateForConstraintInjector, false);
    }

    public final void processGivenForkPointBranchConstraints(Context c, Collection<? extends Pair<? extends TypeVariableMarker, Constraint>> constraintSet, IncorporationConstraintPosition position) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(constraintSet, "constraintSet");
        Intrinsics.checkNotNullParameter(position, "position");
        processGivenConstraints(c, new TypeCheckerStateForConstraintInjector(this, c, position), constraintSet);
    }

    static /* synthetic */ List processConstraints$default(ConstraintInjector constraintInjector, Context context, TypeCheckerStateForConstraintInjector typeCheckerStateForConstraintInjector, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = true;
        }
        return constraintInjector.processConstraints(context, typeCheckerStateForConstraintInjector, z);
    }

    private final List<Pair<TypeVariableMarker, Constraint>> processConstraints(Context c, TypeCheckerStateForConstraintInjector typeCheckerState, boolean skipProperEqualityConstraints) {
        List<Pair<TypeVariableMarker, Constraint>> listProcessConstraintsIgnoringForksData = processConstraintsIgnoringForksData(typeCheckerState, c, skipProperEqualityConstraints);
        List<List<Set<Pair<TypeVariableMarker, Constraint>>>> listExtractForkPointsData = typeCheckerState.extractForkPointsData();
        if (listExtractForkPointsData != null) {
            List<Pair<IncorporationConstraintPosition, List<Set<Pair<TypeVariableMarker, Constraint>>>>> constraintsFromAllForkPoints = c.getConstraintsFromAllForkPoints();
            Iterator<T> it = listExtractForkPointsData.iterator();
            while (it.hasNext()) {
                constraintsFromAllForkPoints.add(TuplesKt.to(typeCheckerState.getPosition(), (List) it.next()));
            }
            if (c.getAtCompletionState()) {
                c.resolveForkPointsConstraints();
            }
        }
        return listProcessConstraintsIgnoringForksData;
    }

    private final List<Pair<TypeVariableMarker, Constraint>> processConstraintsIgnoringForksData(TypeCheckerStateForConstraintInjector typeCheckerState, Context c, boolean skipProperEqualityConstraints) {
        boolean zSupportsFeature = this.languageVersionSettings.supportsFeature(LanguageFeature.ProperTypeInferenceConstraintsProcessing);
        while (true) {
            if (!typeCheckerState.hasConstraintsToProcess()) {
                return null;
            }
            List<Pair<TypeVariableMarker, Constraint>> listExtractAllConstraints = typeCheckerState.extractAllConstraints();
            Intrinsics.checkNotNull(listExtractAllConstraints);
            processGivenConstraints(c, typeCheckerState, listExtractAllConstraints);
            ConstraintSystemOperation constraintSystemOperation = c instanceof ConstraintSystemOperation ? (ConstraintSystemOperation) c : null;
            if (skipProperEqualityConstraints && !zSupportsFeature && constraintSystemOperation != null) {
                Map<TypeConstructorMarker, MutableVariableWithConstraints> notFixedTypeVariables = c.getNotFixedTypeVariables();
                if (!notFixedTypeVariables.isEmpty()) {
                    Iterator<Map.Entry<TypeConstructorMarker, MutableVariableWithConstraints>> it = notFixedTypeVariables.entrySet().iterator();
                    while (true) {
                        if (it.hasNext()) {
                            List<Constraint> constraints = it.next().getValue().getConstraints();
                            if (!(constraints instanceof Collection) || !constraints.isEmpty()) {
                                Iterator<T> it2 = constraints.iterator();
                                while (true) {
                                    if (!it2.hasNext()) {
                                        break;
                                    }
                                    Constraint constraint = (Constraint) it2.next();
                                    if (constraint.getKind() != ConstraintKind.EQUALITY || !constraintSystemOperation.isProperType(constraint.getType())) {
                                    }
                                }
                            } else {
                                break;
                            }
                        }
                    }
                }
                return typeCheckerState.extractAllConstraints();
            }
        }
    }

    private final void processGivenConstraints(Context c, TypeCheckerStateForConstraintInjector typeCheckerState, Collection<? extends Pair<? extends TypeVariableMarker, Constraint>> constraintsToProcess) {
        for (Pair<? extends TypeVariableMarker, Constraint> pair : constraintsToProcess) {
            TypeVariableMarker typeVariableMarkerComponent1 = pair.component1();
            Constraint constraintComponent2 = pair.component2();
            if (!shouldWeSkipConstraint(c, typeVariableMarkerComponent1, constraintComponent2)) {
                MutableVariableWithConstraints mutableVariableWithConstraints = c.getNotFixedTypeVariables().get(MarkerExtensionsKt.freshTypeConstructor(typeVariableMarkerComponent1, c));
                Constraint constraint = null;
                if (mutableVariableWithConstraints == null) {
                    typeCheckerState.fixedTypeVariable(typeVariableMarkerComponent1);
                    throw null;
                }
                Pair<Constraint, Boolean> pairAddConstraint = mutableVariableWithConstraints.addConstraint(constraintComponent2);
                Constraint constraintComponent1 = pairAddConstraint.component1();
                boolean zBooleanValue = pairAddConstraint.component2().booleanValue();
                ConstraintPosition from = constraintComponent2.getPosition().getFrom();
                if ((zBooleanValue && !constraintComponent2.getIsNullabilityConstraint()) || ((from instanceof FixVariableConstraintPosition) && Intrinsics.areEqual(((FixVariableConstraintPosition) from).getVariable(), typeVariableMarkerComponent1) && constraintComponent2.getKind() == ConstraintKind.EQUALITY)) {
                    constraint = constraintComponent1;
                }
                if (constraint != null) {
                    this.constraintIncorporator.incorporate(typeCheckerState, typeVariableMarkerComponent1, constraint);
                }
            }
        }
    }

    private final void updateAllowedTypeDepth(Context c, KotlinTypeMarker initialType) {
        c.setMaxTypeDepthFromInitialConstraints(Math.max(c.getMaxTypeDepthFromInitialConstraints(), c.typeDepth(initialType)));
    }

    private final boolean shouldWeSkipConstraint(Context context, TypeVariableMarker typeVariableMarker, Constraint constraint) {
        if (constraint.getKind() == ConstraintKind.EQUALITY) {
            return false;
        }
        KotlinTypeMarker type = constraint.getType();
        if (Intrinsics.areEqual(context.typeConstructor(type), context.freshTypeConstructor(typeVariableMarker))) {
            return (context.isMarkedNullable(context.lowerBoundIfFlexible(type)) && constraint.getKind() == ConstraintKind.LOWER) ? false : true;
        }
        return (constraint.getPosition().getFrom() instanceof DeclaredUpperBoundConstraintPosition) && constraint.getKind() == ConstraintKind.UPPER && context.isNullableAny(type);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isAllowedType(Context context, KotlinTypeMarker kotlinTypeMarker) {
        return context.typeDepth(kotlinTypeMarker) <= context.getMaxTypeDepthFromInitialConstraints() + this.ALLOWED_DEPTH_DELTA_FOR_INCORPORATION;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: ConstraintInjector.kt */
    @Metadata(d1 = {"\u0000Î\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0010 \n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b[\n\u0002\u0010(\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0018\b\u0082\u0004\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003:\u0002«\u0002B\u0017\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bB\u001d\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\u000bJ*\u00101\u001a\u0002022\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u00020\u00162\u0006\u00106\u001a\u0002072\b\b\u0002\u00108\u001a\u00020\u0012H\u0002J\u0018\u00109\u001a\u0002022\u0006\u0010:\u001a\u0002042\u0006\u00105\u001a\u00020\u0016H\u0016J \u0010;\u001a\u0002022\u0006\u0010:\u001a\u0002042\u0006\u0010<\u001a\u00020\u00162\u0006\u00108\u001a\u00020\u0012H\u0016J0\u0010=\u001a\u0002022\u0006\u0010>\u001a\u00020\u00162\u0006\u0010?\u001a\u00020\u00162\u0006\u0010@\u001a\u00020\u00122\u0006\u00108\u001a\u00020\u00122\u0006\u0010A\u001a\u00020\u0012H\u0016J \u0010=\u001a\u0002022\u0006\u0010:\u001a\u00020\u001f2\u0006\u00105\u001a\u00020\u00162\u0006\u0010B\u001a\u00020CH\u0016J\u0010\u0010D\u001a\u0002022\u0006\u0010E\u001a\u00020FH\u0002J\u0016\u0010G\u001a\u0002022\u0006\u0010H\u001a\u00020\u001f2\u0006\u0010I\u001a\u00020 J\u0018\u0010J\u001a\u0002022\u0006\u0010:\u001a\u0002042\u0006\u0010K\u001a\u00020\u0016H\u0016J\t\u0010L\u001a\u00020MH\u0096\u0001J\u0019\u0010N\u001a\u00020\u00122\u0006\u0010O\u001a\u0002042\u0006\u0010P\u001a\u000204H\u0096\u0001J\u001b\u0010Q\u001a\u0004\u0018\u00010M2\u0006\u00105\u001a\u00020M2\u0006\u0010R\u001a\u00020SH\u0096\u0001J\u0013\u0010T\u001a\u0004\u0018\u00010\u00162\u0006\u00105\u001a\u00020\u0016H\u0096\u0001J\u0019\u0010U\u001a\u0004\u0018\u00010V2\f\u0010W\u001a\b\u0012\u0004\u0012\u00020\u00160\rH\u0096\u0001J!\u0010X\u001a\u0004\u0018\u00010M2\u0006\u0010:\u001a\u00020.2\f\u0010Y\u001a\b\u0012\u0004\u0012\u00020\u00160\u001cH\u0096\u0001J1\u0010Z\u001a\u00020[2\u0006\u0010\\\u001a\u00020]2\f\u0010^\u001a\b\u0012\u0004\u0012\u00020\u00160\u001c2\b\u0010>\u001a\u0004\u0018\u00010\u00162\u0006\u0010_\u001a\u00020SH\u0096\u0001J\t\u0010`\u001a\u00020aH\u0096\u0001J\u0011\u0010b\u001a\u00020M2\u0006\u0010c\u001a\u00020dH\u0096\u0001J\u0019\u0010e\u001a\u00020\u00162\u0006\u0010f\u001a\u00020M2\u0006\u0010g\u001a\u00020MH\u0096\u0001J?\u0010h\u001a\u00020M2\u0006\u0010i\u001a\u0002042\f\u0010j\u001a\b\u0012\u0004\u0012\u00020]0\u001c2\u0006\u0010k\u001a\u00020\u00122\u0006\u0010l\u001a\u00020\u00122\u000e\u0010m\u001a\n\u0012\u0004\u0012\u00020n\u0018\u00010\u001cH\u0096\u0001J\u0011\u0010o\u001a\u00020]2\u0006\u0010,\u001a\u00020-H\u0096\u0001J\u0011\u0010p\u001a\u00020q2\u0006\u0010:\u001a\u00020\u001fH\u0096\u0001J\u0011\u0010r\u001a\u00020q2\u0006\u0010:\u001a\u00020\u001fH\u0096\u0001J\u0013\u0010s\u001a\u0004\u0018\u00010a2\u0006\u0010t\u001a\u00020\u0016H\u0096\u0001J\u0019\u0010u\u001a\u00020]2\u0006\u00105\u001a\u00020\u00162\u0006\u0010v\u001a\u00020wH\u0096\u0001J\u0019\u0010x\u001a\u00020\u00162\u0006\u0010y\u001a\u00020\u00162\u0006\u0010z\u001a\u00020\u0016H\u0096\u0001J\u0011\u0010{\u001a\u00020\u00162\u0006\u0010i\u001a\u000204H\u0096\u0001J\u001a\u0010|\u001a\u0016\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020 0\u001e\u0018\u00010\u001bJ.\u0010}\u001a*\u0012$\u0012\"\u0012\u001a\u0012\u0018\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020 0\u001e0\u001dj\u0002`!0\u001cj\u0002`\"\u0018\u00010\u001bJ\u0019\u0010~\u001a\u0004\u0018\u00010M2\f\u0010\u007f\u001a\b\u0012\u0004\u0012\u00020M0\u001cH\u0096\u0001J\u0010\u0010\u0080\u0001\u001a\u00030\u0081\u00012\u0006\u0010H\u001a\u00020\u001fJ\u0017\u0010\u0082\u0001\u001a\b\u0012\u0004\u0012\u00020 0\u001c2\u0006\u0010:\u001a\u00020\u001fH\u0016J\u001d\u0010\u0083\u0001\u001a\u0002042\b\u0010\u0084\u0001\u001a\u00030\u0085\u00012\u0007\u00106\u001a\u00030\u0086\u0001H\u0096\u0001J\u001d\u0010\u0087\u0001\u001a\u0002042\b\u0010\u0084\u0001\u001a\u00030\u0085\u00012\u0007\u00106\u001a\u00030\u0086\u0001H\u0096\u0001J\u0014\u0010\u0088\u0001\u001a\u0004\u0018\u00010\u001f2\u0007\u0010\u0089\u0001\u001a\u000204H\u0016J\u0007\u0010\u008a\u0001\u001a\u00020\u0012J\u001c\u0010\u008b\u0001\u001a\u00020\u00122\u0007\u0010\u008c\u0001\u001a\u00020M2\u0007\u0010\u008d\u0001\u001a\u00020MH\u0096\u0001J\u0018\u0010\u008e\u0001\u001a\u00020\u00162\f\u0010W\u001a\b\u0012\u0004\u0012\u00020\u00160\u001cH\u0096\u0001J\u0018\u0010\u008e\u0001\u001a\u00020M2\f\u0010W\u001a\b\u0012\u0004\u0012\u00020M0\u001cH\u0096\u0001J\u0011\u0010\u008f\u0001\u001a\u00020\u00122\u0006\u00105\u001a\u00020\u0016H\u0002J\u0011\u0010\u0090\u0001\u001a\u00020\u00122\u0006\u00105\u001a\u00020MH\u0016J\u001c\u0010\u0091\u0001\u001a\u00020\n2\u0007\u0010\u0092\u0001\u001a\u00020\u00122\u0007\u0010\u0093\u0001\u001a\u00020\u0012H\u0096\u0001J\n\u0010\u0094\u0001\u001a\u00020MH\u0096\u0001J\n\u0010\u0095\u0001\u001a\u00020MH\u0096\u0001J\n\u0010\u0096\u0001\u001a\u00020MH\u0096\u0001J\t\u0010\u0097\u0001\u001a\u00020dH\u0002J&\u0010\u0098\u0001\u001a\u00020\u00122\u001b\u0010\u0099\u0001\u001a\u0016\u0012\u0005\u0012\u00030\u009b\u0001\u0012\u0004\u0012\u0002020\u009a\u0001¢\u0006\u0003\b\u009c\u0001H\u0016J*\u0010E\u001a\u0002022\u0006\u0010>\u001a\u00020\u00162\u0006\u0010?\u001a\u00020\u00162\b\b\u0002\u0010@\u001a\u00020\u00122\b\b\u0002\u00108\u001a\u00020\u0012J\u0017\u0010\u009d\u0001\u001a\u0002022\u0006\u0010>\u001a\u00020\u00162\u0006\u0010?\u001a\u00020\u0016J\u0013\u0010\u009e\u0001\u001a\u00030\u009f\u00012\u0006\u00105\u001a\u00020MH\u0096\u0001J \u0010 \u0001\u001a\u00020a2\u0014\u0010¡\u0001\u001a\u000f\u0012\u0004\u0012\u000204\u0012\u0004\u0012\u00020\u00160¢\u0001H\u0096\u0001J\u001e\u0010£\u0001\u001a\b\u0012\u0004\u0012\u00020n0\u001c2\f\u0010W\u001a\b\u0012\u0004\u0012\u00020\u00160\u001cH\u0096\u0001J\n\u0010¤\u0001\u001a\u00020\u0012H\u0096\u0001J$\u0010¥\u0001\u001a\u00020\u0012*\u00020\u00162\u0014\u0010¦\u0001\u001a\u000f\u0012\u0004\u0012\u00020M\u0012\u0004\u0012\u00020\u00120\u009a\u0001H\u0096\u0001J\u000f\u0010§\u0001\u001a\u00030\u0085\u0001*\u00020\u0016H\u0096\u0001J\u000f\u0010¨\u0001\u001a\u00030©\u0001*\u00020MH\u0096\u0001J\u0010\u0010ª\u0001\u001a\u0004\u0018\u00010[*\u00020MH\u0096\u0001J\u0011\u0010«\u0001\u001a\u0005\u0018\u00010¬\u0001*\u00020MH\u0096\u0001J\u0012\u0010\u00ad\u0001\u001a\u0005\u0018\u00010®\u0001*\u00030¯\u0001H\u0096\u0001J\u0011\u0010°\u0001\u001a\u0005\u0018\u00010¯\u0001*\u00020\u0016H\u0096\u0001J\u0010\u0010±\u0001\u001a\u0004\u0018\u00010M*\u00020\u0016H\u0096\u0001J\u000e\u0010²\u0001\u001a\u00020]*\u00020\u0016H\u0096\u0001J\u000e\u0010³\u0001\u001a\u00020\u0012*\u00020\u0016H\u0096\u0001J\r\u0010_\u001a\u00020S*\u00020[H\u0096\u0001J$\u0010´\u0001\u001a\u00020\u0012*\u00020\u00162\u0014\u0010¦\u0001\u001a\u000f\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00120\u009a\u0001H\u0096\u0001J\u000e\u0010µ\u0001\u001a\u00020\u0016*\u00020\u0016H\u0096\u0001J\u000e\u0010¶\u0001\u001a\u00020M*\u00020\u001fH\u0096\u0001J\u000e\u0010·\u0001\u001a\u00020\u0016*\u00020\u0016H\u0096\u0001J\u0014\u0010¸\u0001\u001a\b\u0012\u0004\u0012\u00020\u00160\u001c*\u00020\u0016H\u0096\u0001J\u0014\u0010¹\u0001\u001a\b\u0012\u0004\u0012\u00020-0\u001d*\u00020\u0016H\u0096\u0001J\u0014\u0010º\u0001\u001a\b\u0012\u0004\u0012\u00020.0\u001d*\u00020\u0016H\u0096\u0001J\u001e\u0010»\u0001\u001a\n\u0012\u0004\u0012\u00020M\u0018\u00010\u001c*\u00020M2\u0006\u0010i\u001a\u000204H\u0096\u0001J\u000e\u0010¼\u0001\u001a\u000204*\u00020\u001fH\u0096\u0001J\u0011\u0010½\u0001\u001a\u0005\u0018\u00010\u0086\u0001*\u00020\u0016H\u0096\u0001J\u0019\u0010¾\u0001\u001a\u00020]*\u00030©\u00012\b\u0010¿\u0001\u001a\u00030\u0085\u0001H\u0096\u0003J\u000e\u0010À\u0001\u001a\u00020\u0016*\u000204H\u0096\u0001J\u0018\u0010Á\u0001\u001a\u00020]*\u00020\u00162\b\u0010¿\u0001\u001a\u00030\u0085\u0001H\u0096\u0001J\u001a\u0010Â\u0001\u001a\u0004\u0018\u00010]*\u00020M2\b\u0010¿\u0001\u001a\u00030\u0085\u0001H\u0096\u0001J\u0014\u0010Ã\u0001\u001a\b\u0012\u0004\u0012\u00020]0\u001c*\u00020\u0016H\u0096\u0001J\u0014\u0010Ä\u0001\u001a\b\u0012\u0004\u0012\u00020n0\u001c*\u00020\u0016H\u0096\u0001J\u0014\u0010Å\u0001\u001a\b\u0012\u0004\u0012\u00020n0\u001c*\u00020\u0016H\u0096\u0001J\u000e\u0010Æ\u0001\u001a\u00020\u0016*\u00020\u0016H\u0096\u0001J\u000e\u0010Ç\u0001\u001a\u00020.*\u00020qH\u0096\u0001J\u0018\u0010È\u0001\u001a\u00020-*\u0002042\b\u0010¿\u0001\u001a\u00030\u0085\u0001H\u0096\u0001J\u0014\u0010É\u0001\u001a\b\u0012\u0004\u0012\u00020-0\u001c*\u000204H\u0096\u0001J\u000e\u0010Ê\u0001\u001a\u00020\u0016*\u00020]H\u0096\u0001J\u000e\u0010Ë\u0001\u001a\u000204*\u00020-H\u0096\u0001J\u0010\u0010Ì\u0001\u001a\u0004\u0018\u00010-*\u000204H\u0096\u0001J\u0018\u0010Í\u0001\u001a\u00020\u0016*\u00020-2\b\u0010¿\u0001\u001a\u00030\u0085\u0001H\u0096\u0001J\u0014\u0010Î\u0001\u001a\b\u0012\u0004\u0012\u00020\u00160\u001c*\u00020-H\u0096\u0001J\u000e\u0010Ï\u0001\u001a\u00020w*\u00020]H\u0096\u0001J\u000e\u0010Ï\u0001\u001a\u00020w*\u00020-H\u0096\u0001J\u000e\u0010Ð\u0001\u001a\u00020\u0012*\u00020\u0016H\u0096\u0001J\u000e\u0010Ñ\u0001\u001a\u00020\u0012*\u00020\u0016H\u0096\u0001J\u000e\u0010Ò\u0001\u001a\u00020\u0012*\u00020\u0016H\u0096\u0001J\u000e\u0010Ó\u0001\u001a\u00020\u0012*\u00020\u0016H\u0096\u0001J\u0019\u0010Ô\u0001\u001a\u00020\u0012*\u00020-2\t\u0010Õ\u0001\u001a\u0004\u0018\u000104H\u0096\u0001J\u000e\u0010Ö\u0001\u001a\u00020\u0012*\u000204H\u0096\u0001J\u000e\u0010×\u0001\u001a\u00020\u0012*\u000204H\u0096\u0001J\u000e\u0010Ø\u0001\u001a\u00020\u0012*\u00020\u0016H\u0096\u0001J\u000e\u0010Ù\u0001\u001a\u00020\u0012*\u00020\u0016H\u0096\u0001J\u000e\u0010Ú\u0001\u001a\u00020\u0012*\u00020\u0016H\u0096\u0001J\u000e\u0010Û\u0001\u001a\u00020\u0012*\u000204H\u0096\u0001J\u000e\u0010Ü\u0001\u001a\u00020\u0012*\u00020MH\u0096\u0001J\u000e\u0010Ý\u0001\u001a\u00020\u0012*\u000204H\u0096\u0001J\u000e\u0010Þ\u0001\u001a\u00020\u0012*\u000204H\u0096\u0001J\u000e\u0010ß\u0001\u001a\u00020\u0012*\u00020.H\u0096\u0001J\u000e\u0010à\u0001\u001a\u00020\u0012*\u000204H\u0096\u0001J\u000e\u0010á\u0001\u001a\u00020\u0012*\u00020\u0016H\u0096\u0001J\u000e\u0010â\u0001\u001a\u00020\u0012*\u000204H\u0096\u0001J\u000e\u0010ã\u0001\u001a\u00020\u0012*\u00020\u0016H\u0096\u0001J\u000e\u0010ä\u0001\u001a\u00020\u0012*\u00020\u0016H\u0096\u0001J\u000e\u0010ä\u0001\u001a\u00020\u0012*\u000204H\u0096\u0001J\r\u0010l\u001a\u00020\u0012*\u00020MH\u0096\u0001J\u000e\u0010å\u0001\u001a\u00020\u0012*\u00020\u0016H\u0096\u0001J\u000e\u0010æ\u0001\u001a\u00020\u0012*\u000204H\u0096\u0001J\u000e\u0010ç\u0001\u001a\u00020\u0012*\u00020\u0016H\u0096\u0001J\u000e\u0010è\u0001\u001a\u00020\u0012*\u00020\u0016H\u0096\u0001J\u000e\u0010é\u0001\u001a\u00020\u0012*\u00020\u0016H\u0096\u0001J\u000e\u0010ê\u0001\u001a\u00020\u0012*\u000204H\u0096\u0001J\u000e\u0010ë\u0001\u001a\u00020\u0012*\u000204H\u0096\u0001J\u000e\u0010ì\u0001\u001a\u00020\u0012*\u00020MH\u0096\u0001J\u000e\u0010í\u0001\u001a\u00020\u0012*\u000204H\u0096\u0001J\u000e\u0010î\u0001\u001a\u00020\u0012*\u000204H\u0096\u0001J\u000e\u0010ï\u0001\u001a\u00020\u0012*\u000204H\u0096\u0001J\u000e\u0010ð\u0001\u001a\u00020\u0012*\u000204H\u0096\u0001J\u000e\u0010ñ\u0001\u001a\u00020\u0012*\u00020\u0016H\u0096\u0001J\u000e\u0010ñ\u0001\u001a\u00020\u0012*\u00020MH\u0096\u0001J\u000e\u0010ò\u0001\u001a\u00020\u0012*\u00020\u0016H\u0097\u0001J\u000e\u0010ó\u0001\u001a\u00020\u0012*\u00020\u0016H\u0096\u0001J\u000e\u0010ô\u0001\u001a\u00020\u0012*\u000204H\u0096\u0001J\u000e\u0010õ\u0001\u001a\u00020\u0012*\u00020\u0016H\u0096\u0001J\u000e\u0010ö\u0001\u001a\u00020\u0012*\u00020\u0016H\u0096\u0001J\u000e\u0010÷\u0001\u001a\u00020\u0012*\u00020\u0016H\u0096\u0001J\u000e\u0010ø\u0001\u001a\u00020\u0012*\u00020[H\u0096\u0001J\u000e\u0010ù\u0001\u001a\u00020\u0012*\u00020MH\u0096\u0001J\u000e\u0010ú\u0001\u001a\u00020\u0012*\u00020[H\u0096\u0001J\u000e\u0010û\u0001\u001a\u00020\u0012*\u00020\u0016H\u0096\u0001J\u000e\u0010ü\u0001\u001a\u00020\u0012*\u00020\u0016H\u0096\u0001J\u000e\u0010ý\u0001\u001a\u00020\u0012*\u00020\u0016H\u0096\u0001J\u000e\u0010þ\u0001\u001a\u00020\u0012*\u00020MH\u0096\u0001J\u000e\u0010ÿ\u0001\u001a\u00020\u0012*\u00020\u0016H\u0096\u0001J\u000e\u0010\u0080\u0002\u001a\u00020\u0012*\u00020]H\u0096\u0001J\u000e\u0010\u0081\u0002\u001a\u00020\u0012*\u00020MH\u0096\u0001J\u000e\u0010\u0082\u0002\u001a\u00020\u0012*\u00020MH\u0096\u0001J\u000e\u0010\u0083\u0002\u001a\u00020\u0012*\u00020MH\u0096\u0001J\u000e\u0010\u0084\u0002\u001a\u00020\u0012*\u000204H\u0096\u0001J\u000e\u0010\u0085\u0002\u001a\u00020\u0012*\u000204H\u0096\u0001J\u000e\u0010\u0086\u0002\u001a\u00020\u0012*\u00020\u0016H\u0096\u0001J\u000e\u0010\u0087\u0002\u001a\u00020\u0012*\u00020\u0016H\u0096\u0001J\u000e\u0010\u0088\u0002\u001a\u00020\u0012*\u00020\u0016H\u0096\u0001J\u000e\u0010\u0089\u0002\u001a\u00020\u0012*\u000204H\u0096\u0001J\u0016\u0010\u008a\u0002\u001a\t\u0012\u0004\u0012\u00020]0\u008b\u0002*\u00030©\u0001H\u0096\u0003J\u000e\u0010f\u001a\u00020M*\u00030¯\u0001H\u0096\u0001J\u000e\u0010\u008c\u0002\u001a\u00020M*\u00020\u0016H\u0096\u0001J\u000f\u0010>\u001a\u0004\u0018\u00010\u0016*\u00020[H\u0096\u0001J\u000e\u0010\u008d\u0002\u001a\u00020\u0016*\u00020\u0016H\u0096\u0001J\u000e\u0010\u008e\u0002\u001a\u00020M*\u00020MH\u0096\u0001J\u000f\u0010\u008f\u0002\u001a\u00020M*\u00030¬\u0001H\u0096\u0001J\u000e\u0010\u0090\u0002\u001a\u00020M*\u00020MH\u0096\u0001J\u000f\u0010\u0091\u0002\u001a\u00030\u0085\u0001*\u000204H\u0096\u0001J\u0014\u0010\u0092\u0002\u001a\b\u0012\u0004\u0012\u00020\u00160\r*\u00020MH\u0096\u0001J\u000f\u0010\u0093\u0002\u001a\u00020]*\u00030\u0094\u0002H\u0096\u0001J\u000e\u0010\u0095\u0002\u001a\u00020\u0016*\u00020\u0016H\u0096\u0001J\u000e\u0010\u0096\u0002\u001a\u00020\u0016*\u00020\u0016H\u0096\u0001J$\u0010\u0097\u0002\u001a\u00020\u0016*\u00020\u00162\u0014\u0010\u0098\u0002\u001a\u000f\u0012\u0004\u0012\u00020]\u0012\u0004\u0012\u00020]0\u009a\u0001H\u0096\u0001J$\u0010\u0097\u0002\u001a\u00020M*\u00020M2\u0014\u0010\u0098\u0002\u001a\u000f\u0012\u0004\u0012\u00020]\u0012\u0004\u0012\u00020]0\u009a\u0001H\u0096\u0001J\u001d\u0010\u0097\u0002\u001a\u00020M*\u00020M2\r\u0010\u0099\u0002\u001a\b\u0012\u0004\u0012\u00020]0\u001cH\u0096\u0001J$\u0010\u009a\u0002\u001a\u00020\u0016*\u00020\u00162\u0014\u0010\u0098\u0002\u001a\u000f\u0012\u0004\u0012\u00020]\u0012\u0004\u0012\u00020]0\u009a\u0001H\u0096\u0001J$\u0010\u009a\u0002\u001a\u00020M*\u00020M2\u0014\u0010\u0098\u0002\u001a\u000f\u0012\u0004\u0012\u00020]\u0012\u0004\u0012\u00020]0\u009a\u0001H\u0096\u0001J\u001d\u0010\u009b\u0002\u001a\u00020\u0016*\u00020\u00162\r\u0010\u009c\u0002\u001a\b\u0012\u0004\u0012\u00020n0\u001cH\u0096\u0001J\u0017\u0010\u009d\u0002\u001a\u00020]*\u00020]2\u0007\u0010\u009e\u0002\u001a\u00020\u0016H\u0096\u0001J\u0016\u0010\u009f\u0002\u001a\u00020\u0016*\u00020a2\u0006\u00105\u001a\u00020\u0016H\u0096\u0001J\u0016\u0010 \u0002\u001a\u0004\u0018\u00010\u0016*\b\u0012\u0004\u0012\u00020\u00160\rH\u0096\u0001J\u0010\u0010¡\u0002\u001a\u00030\u0085\u0001*\u00030©\u0001H\u0096\u0001J\u0014\u0010¢\u0002\u001a\b\u0012\u0004\u0012\u00020\u00160\r*\u000204H\u0096\u0001J\u000e\u0010£\u0002\u001a\u00020M*\u000204H\u0096\u0001J\u000f\u0010\u0089\u0001\u001a\u00030\u0094\u0002*\u00020[H\u0096\u0001J\u000e\u0010\u0089\u0001\u001a\u000204*\u00020\u0016H\u0096\u0001J\u000e\u0010\u0089\u0001\u001a\u000204*\u00020MH\u0096\u0001J\u000e\u0010¤\u0002\u001a\u00020]*\u00020[H\u0096\u0001J\u000f\u0010¥\u0002\u001a\u00030\u0085\u0001*\u00020\u0016H\u0096\u0001J\u000f\u0010¥\u0002\u001a\u00030\u0085\u0001*\u00020MH\u0096\u0001J\u000f\u0010,\u001a\u0004\u0018\u00010-*\u00020[H\u0096\u0001J\u000e\u0010¦\u0002\u001a\u000204*\u000204H\u0096\u0001J\u000e\u0010g\u001a\u00020M*\u00030¯\u0001H\u0096\u0001J\u000f\u0010§\u0002\u001a\u00030\u0085\u0001*\u00020-H\u0096\u0001J\u000e\u0010¨\u0002\u001a\u00020M*\u00020\u0016H\u0096\u0001J\u000e\u0010©\u0002\u001a\u00020\u0016*\u00020[H\u0096\u0001J\u0016\u0010ª\u0002\u001a\u00020\u0016*\u00020\u00162\u0006\u0010k\u001a\u00020\u0012H\u0096\u0001J\u0016\u0010ª\u0002\u001a\u00020M*\u00020M2\u0006\u0010k\u001a\u00020\u0012H\u0096\u0001R\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u00128BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R6\u0010\u001a\u001a*\u0012$\u0012\"\u0012\u001a\u0012\u0018\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020 0\u001e0\u001dj\u0002`!0\u001cj\u0002`\"\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010$\u001a\u00020\u0012X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0014R\u0012\u0010%\u001a\u00020\u0012X\u0096\u0005¢\u0006\u0006\u001a\u0004\b%\u0010\u0014R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\"\u0010(\u001a\u0016\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020 0\u001e\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000RF\u0010)\u001a:\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020 0\u001e0\u001b\u0018\u00010\u001bj\u001c\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020 0\u001e0\u001b\u0018\u0001`*X\u0082\u000e¢\u0006\u0002\n\u0000RZ\u0010+\u001aN\u0012 \u0012\u001e\u0012\u001a\u0012\u0018\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020 0\u001e0\u001dj\u0002`!0\u001b\u0018\u00010\u001bj&\u0012 \u0012\u001e\u0012\u001a\u0012\u0018\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020 0\u001e0\u001dj\u0002`!0\u001b\u0018\u0001`*X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010,\u001a\u0004\u0018\u00010-*\u00020.X\u0096\u0005¢\u0006\u0006\u001a\u0004\b/\u00100¨\u0006¬\u0002"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintInjector$TypeCheckerStateForConstraintInjector;", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/TypeCheckerStateForConstraintSystem;", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintIncorporator$Context;", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "c", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintInjector$Context;", "position", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/IncorporationConstraintPosition;", "(Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintInjector;Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintInjector$Context;Lorg/jetbrains/kotlin/resolve/calls/inference/model/IncorporationConstraintPosition;)V", "baseState", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", "(Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintInjector;Lorg/jetbrains/kotlin/types/TypeCheckerState;Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintInjector$Context;Lorg/jetbrains/kotlin/resolve/calls/inference/model/IncorporationConstraintPosition;)V", "allTypeVariablesWithConstraints", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/VariableWithConstraints;", "getAllTypeVariablesWithConstraints", "()Ljava/util/Collection;", "allowForking", "", "getAllowForking", "()Z", "baseLowerType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "baseUpperType", "getC", "()Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintInjector$Context;", "forkPointsData", "", "", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/Constraint;", "Lorg/jetbrains/kotlin/resolve/calls/inference/ForkPointBranchDescription;", "Lorg/jetbrains/kotlin/resolve/calls/inference/ForkPointData;", "isIncorporatingConstraintFromDeclaredUpperBound", "isInferenceCompatibilityEnabled", "isK2", "getPosition", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/IncorporationConstraintPosition;", "possibleNewConstraints", "stackForConstraintSetFromCurrentForkPointBranch", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/Stack;", "stackForConstraintsSetsFromCurrentForkPoint", "typeParameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;", "getTypeParameter", "(Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;)Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "addConstraint", "", "typeVariableConstructor", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", ModuleXmlParser.TYPE, Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintKind;", "isFromNullabilityConstraint", "addEqualityConstraint", "typeVariable", "addLowerConstraint", "subType", "addNewIncorporatedConstraint", "lowerType", "upperType", "shouldTryUseDifferentFlexibilityForUpperType", "isFromDeclaredUpperBound", "constraintContext", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintContext;", "addNewIncorporatedConstraintFromDeclaredUpperBound", "runIsSubtypeOf", "Ljava/lang/Runnable;", "addPossibleNewConstraint", "variable", "constraint", "addUpperConstraint", "superType", SchemaSymbols.ATTVAL_ANYTYPE, "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "areEqualTypeConstructors", "c1", "c2", "captureFromArguments", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/types/model/CaptureStatus;", "captureFromExpression", "computeEmptyIntersectionTypeKind", "Lorg/jetbrains/kotlin/resolve/checkers/EmptyIntersectionTypeInfo;", SpecTypePair.NAME_types, "createCapturedStarProjectionForSelfType", "typesForRecursiveTypeParameters", "createCapturedType", "Lorg/jetbrains/kotlin/types/model/CapturedTypeMarker;", "constructorProjection", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", "constructorSupertypes", "captureStatus", "createEmptySubstitutor", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "createErrorType", "debugName", "", "createFlexibleType", "lowerBound", "upperBound", "createSimpleType", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "arguments", "nullable", "isExtensionFunction", "attributes", "Lorg/jetbrains/kotlin/types/model/AnnotationMarker;", "createStarProjection", "createStubTypeForBuilderInference", "Lorg/jetbrains/kotlin/types/model/StubTypeMarker;", "createStubTypeForTypeVariablesInSubtyping", "createSubstitutorForSuperTypes", "baseType", "createTypeArgument", "variance", "Lorg/jetbrains/kotlin/types/model/TypeVariance;", "createTypeWithAlternativeForIntersectionResult", "firstCandidate", "secondCandidate", "createUninferredType", "extractAllConstraints", "extractForkPointsData", "findCommonIntegerLiteralTypesSuperType", "explicitSupertypes", "fixedTypeVariable", "", "getConstraintsForVariable", "getNonReflectFunctionTypeConstructor", "parametersNumber", "", "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "getReflectFunctionTypeConstructor", "getTypeVariable", "typeConstructor", "hasConstraintsToProcess", "identicalArguments", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "intersectTypes", "isCapturedTypeFromSubtyping", "isMyTypeVariable", "newTypeCheckerState", "errorTypesEqualToAnything", "stubTypesEqualToAnything", "nothingType", "nullableAnyType", "nullableNothingType", "renderBaseConstraint", "runForkingPoint", "block", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/types/TypeCheckerState$ForkPointContext;", "Lkotlin/ExtensionFunctionType;", "setConstrainingTypesToPrintDebugInfo", "substitutionSupertypePolicy", "Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy;", "typeSubstitutorByTypeConstructor", "map", "", "unionTypeAttributes", "useRefinedBoundsForTypeVariableInFlexiblePosition", "anySuperTypeConstructor", "predicate", "argumentsCount", "asArgumentList", "Lorg/jetbrains/kotlin/types/model/TypeArgumentListMarker;", "asCapturedType", "asDefinitelyNotNullType", "Lorg/jetbrains/kotlin/types/model/DefinitelyNotNullTypeMarker;", "asDynamicType", "Lorg/jetbrains/kotlin/types/model/DynamicTypeMarker;", "Lorg/jetbrains/kotlin/types/model/FlexibleTypeMarker;", "asFlexibleType", "asSimpleType", "asTypeArgument", "canHaveUndefinedNullability", "contains", "convertToNonRaw", "defaultType", "eraseContainingTypeParameters", "extractArgumentsForFunctionTypeOrSubtype", "extractTypeParameters", "extractTypeVariables", "fastCorrespondingSupertypes", "freshTypeConstructor", "functionTypeKind", "get", "index", "getApproximatedIntegerLiteralType", "getArgument", "getArgumentOrNull", "getArguments", "getAttributes", "getCustomAttributes", "getFunctionTypeFromSupertypes", "getOriginalTypeVariable", "getParameter", "getParameters", "getType", "getTypeConstructor", "getTypeParameterClassifier", "getUpperBound", "getUpperBounds", "getVariance", "hasCustomAttributes", "hasExactAnnotation", "hasFlexibleNullability", "hasNoInferAnnotation", "hasRecursiveBounds", "selfConstructor", "isAnonymous", "isAnyConstructor", "isBuiltinFunctionTypeOrSubtype", "isCapturedDynamic", "isCapturedType", "isCapturedTypeConstructor", "isClassType", "isClassTypeConstructor", "isCommonFinalClassConstructor", "isContainedInInvariantOrContravariantPositions", "isDefinitelyClassTypeConstructor", "isDefinitelyNotNullType", "isDenotable", "isDynamic", "isError", "isExtensionFunctionType", "isFinalClassConstructor", "isFlexible", "isFlexibleNothing", "isFunctionOrKFunctionWithAnySuspendability", "isIntegerConstantOperatorTypeConstructor", "isIntegerLiteralConstantTypeConstructor", "isIntegerLiteralType", "isIntegerLiteralTypeConstructor", "isInterface", "isIntersection", "isLocalType", "isMarkedNullable", "isNotNullTypeParameter", "isNothing", "isNothingConstructor", "isNullableAny", "isNullableNothing", "isNullableType", "isOldCapturedType", "isPrimitiveType", "isProjectionNotNull", "isRawType", "isSignedOrUnsignedNumberType", "isSimpleType", "isSingleClassifierType", "isSpecial", "isStarProjection", "isStubType", "isStubTypeForBuilderInference", "isStubTypeForVariableInSubtyping", "isTypeParameterTypeConstructor", "isTypeVariable", "isTypeVariableType", "isUninferredParameter", "isUnit", "isUnitTypeConstructor", "iterator", "", "lowerBoundIfFlexible", "makeDefinitelyNotNullOrNotNull", "makeSimpleTypeDefinitelyNotNullOrNotNull", "original", "originalIfDefinitelyNotNullable", "parametersCount", "possibleIntegerTypes", "projection", "Lorg/jetbrains/kotlin/types/model/CapturedTypeConstructorMarker;", "removeAnnotations", "removeExactAnnotation", "replaceArguments", "replacement", "newArguments", "replaceArgumentsDeeply", "replaceCustomAttributes", "newAttributes", "replaceType", "newType", "safeSubstitute", "singleBestRepresentative", "size", "supertypes", "toErrorType", "typeConstructorProjection", "typeDepth", "unwrapStubTypeVariableConstructor", "upperBoundCount", "upperBoundIfFlexible", "withNotNullProjection", "withNullability", "MyForkCreationContext", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class TypeCheckerStateForConstraintInjector extends TypeCheckerStateForConstraintSystem implements ConstraintIncorporator.Context, TypeSystemInferenceExtensionContext {
        private KotlinTypeMarker baseLowerType;
        private KotlinTypeMarker baseUpperType;
        private final Context c;
        private List<List<Set<Pair<TypeVariableMarker, Constraint>>>> forkPointsData;
        private boolean isIncorporatingConstraintFromDeclaredUpperBound;
        private final boolean isInferenceCompatibilityEnabled;
        private final IncorporationConstraintPosition position;
        private List<Pair<TypeVariableMarker, Constraint>> possibleNewConstraints;
        private List<List<Pair<TypeVariableMarker, Constraint>>> stackForConstraintSetFromCurrentForkPointBranch;
        private List<List<Set<Pair<TypeVariableMarker, Constraint>>>> stackForConstraintsSetsFromCurrentForkPoint;
        final /* synthetic */ ConstraintInjector this$0;

        /* JADX INFO: compiled from: ConstraintInjector.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[CaptureStatus.values().length];
                try {
                    iArr[CaptureStatus.FROM_EXPRESSION.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[CaptureStatus.FOR_SUBTYPING.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[CaptureStatus.FOR_INCORPORATION.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
        public boolean anySuperTypeConstructor(KotlinTypeMarker kotlinTypeMarker, Function1<? super SimpleTypeMarker, Boolean> predicate) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            Intrinsics.checkNotNullParameter(predicate, "predicate");
            return this.c.anySuperTypeConstructor(kotlinTypeMarker, predicate);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
        public SimpleTypeMarker anyType() {
            return this.c.anyType();
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean areEqualTypeConstructors(TypeConstructorMarker c1, TypeConstructorMarker c2) {
            Intrinsics.checkNotNullParameter(c1, "c1");
            Intrinsics.checkNotNullParameter(c2, "c2");
            return this.c.areEqualTypeConstructors(c1, c2);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public int argumentsCount(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.argumentsCount(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public TypeArgumentListMarker asArgumentList(SimpleTypeMarker simpleTypeMarker) {
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
            return this.c.asArgumentList(simpleTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public CapturedTypeMarker asCapturedType(SimpleTypeMarker simpleTypeMarker) {
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
            return this.c.asCapturedType(simpleTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public DefinitelyNotNullTypeMarker asDefinitelyNotNullType(SimpleTypeMarker simpleTypeMarker) {
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
            return this.c.asDefinitelyNotNullType(simpleTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public DynamicTypeMarker asDynamicType(FlexibleTypeMarker flexibleTypeMarker) {
            Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
            return this.c.asDynamicType(flexibleTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public FlexibleTypeMarker asFlexibleType(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.asFlexibleType(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public SimpleTypeMarker asSimpleType(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.asSimpleType(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public TypeArgumentMarker asTypeArgument(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.asTypeArgument(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
        public boolean canHaveUndefinedNullability(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.canHaveUndefinedNullability(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public SimpleTypeMarker captureFromArguments(SimpleTypeMarker type, CaptureStatus status) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(status, "status");
            return this.c.captureFromArguments(type, status);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public KotlinTypeMarker captureFromExpression(KotlinTypeMarker type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return this.c.captureFromExpression(type);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public CaptureStatus captureStatus(CapturedTypeMarker capturedTypeMarker) {
            Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
            return this.c.captureStatus(capturedTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public EmptyIntersectionTypeInfo computeEmptyIntersectionTypeKind(Collection<? extends KotlinTypeMarker> types) {
            Intrinsics.checkNotNullParameter(types, "types");
            return this.c.computeEmptyIntersectionTypeKind(types);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public boolean contains(KotlinTypeMarker kotlinTypeMarker, Function1<? super KotlinTypeMarker, Boolean> predicate) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            Intrinsics.checkNotNullParameter(predicate, "predicate");
            return this.c.contains(kotlinTypeMarker, predicate);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public KotlinTypeMarker convertToNonRaw(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.convertToNonRaw(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public SimpleTypeMarker createCapturedStarProjectionForSelfType(TypeVariableTypeConstructorMarker typeVariable, List<? extends KotlinTypeMarker> typesForRecursiveTypeParameters) {
            Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
            Intrinsics.checkNotNullParameter(typesForRecursiveTypeParameters, "typesForRecursiveTypeParameters");
            return this.c.createCapturedStarProjectionForSelfType(typeVariable, typesForRecursiveTypeParameters);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public CapturedTypeMarker createCapturedType(TypeArgumentMarker constructorProjection, List<? extends KotlinTypeMarker> constructorSupertypes, KotlinTypeMarker lowerType, CaptureStatus captureStatus) {
            Intrinsics.checkNotNullParameter(constructorProjection, "constructorProjection");
            Intrinsics.checkNotNullParameter(constructorSupertypes, "constructorSupertypes");
            Intrinsics.checkNotNullParameter(captureStatus, "captureStatus");
            return this.c.createCapturedType(constructorProjection, constructorSupertypes, lowerType, captureStatus);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public TypeSubstitutorMarker createEmptySubstitutor() {
            return this.c.createEmptySubstitutor();
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
        public SimpleTypeMarker createErrorType(String debugName) {
            Intrinsics.checkNotNullParameter(debugName, "debugName");
            return this.c.createErrorType(debugName);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
        public KotlinTypeMarker createFlexibleType(SimpleTypeMarker lowerBound, SimpleTypeMarker upperBound) {
            Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
            Intrinsics.checkNotNullParameter(upperBound, "upperBound");
            return this.c.createFlexibleType(lowerBound, upperBound);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
        public SimpleTypeMarker createSimpleType(TypeConstructorMarker constructor, List<? extends TypeArgumentMarker> arguments, boolean nullable, boolean isExtensionFunction, List<? extends AnnotationMarker> attributes) {
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            Intrinsics.checkNotNullParameter(arguments, "arguments");
            return this.c.createSimpleType(constructor, arguments, nullable, isExtensionFunction, attributes);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
        public TypeArgumentMarker createStarProjection(TypeParameterMarker typeParameter) {
            Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
            return this.c.createStarProjection(typeParameter);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public StubTypeMarker createStubTypeForBuilderInference(TypeVariableMarker typeVariable) {
            Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
            return this.c.createStubTypeForBuilderInference(typeVariable);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public StubTypeMarker createStubTypeForTypeVariablesInSubtyping(TypeVariableMarker typeVariable) {
            Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
            return this.c.createStubTypeForTypeVariablesInSubtyping(typeVariable);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public TypeSubstitutorMarker createSubstitutorForSuperTypes(KotlinTypeMarker baseType) {
            Intrinsics.checkNotNullParameter(baseType, "baseType");
            return this.c.createSubstitutorForSuperTypes(baseType);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
        public TypeArgumentMarker createTypeArgument(KotlinTypeMarker type, TypeVariance variance) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(variance, "variance");
            return this.c.createTypeArgument(type, variance);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public KotlinTypeMarker createTypeWithAlternativeForIntersectionResult(KotlinTypeMarker firstCandidate, KotlinTypeMarker secondCandidate) {
            Intrinsics.checkNotNullParameter(firstCandidate, "firstCandidate");
            Intrinsics.checkNotNullParameter(secondCandidate, "secondCandidate");
            return this.c.createTypeWithAlternativeForIntersectionResult(firstCandidate, secondCandidate);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
        public KotlinTypeMarker createUninferredType(TypeConstructorMarker constructor) {
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            return this.c.createUninferredType(constructor);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public SimpleTypeMarker defaultType(TypeVariableMarker typeVariableMarker) {
            Intrinsics.checkNotNullParameter(typeVariableMarker, "<this>");
            return this.c.defaultType(typeVariableMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public KotlinTypeMarker eraseContainingTypeParameters(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.eraseContainingTypeParameters(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public List<KotlinTypeMarker> extractArgumentsForFunctionTypeOrSubtype(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.extractArgumentsForFunctionTypeOrSubtype(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public Set<TypeParameterMarker> extractTypeParameters(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.extractTypeParameters(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public Set<TypeVariableTypeConstructorMarker> extractTypeVariables(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.extractTypeVariables(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public List<SimpleTypeMarker> fastCorrespondingSupertypes(SimpleTypeMarker simpleTypeMarker, TypeConstructorMarker constructor) {
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            return this.c.fastCorrespondingSupertypes(simpleTypeMarker, constructor);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
        public SimpleTypeMarker findCommonIntegerLiteralTypesSuperType(List<? extends SimpleTypeMarker> explicitSupertypes) {
            Intrinsics.checkNotNullParameter(explicitSupertypes, "explicitSupertypes");
            return this.c.findCommonIntegerLiteralTypesSuperType(explicitSupertypes);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public TypeConstructorMarker freshTypeConstructor(TypeVariableMarker typeVariableMarker) {
            Intrinsics.checkNotNullParameter(typeVariableMarker, "<this>");
            return this.c.freshTypeConstructor(typeVariableMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public FunctionTypeKind functionTypeKind(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.functionTypeKind(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public TypeArgumentMarker get(TypeArgumentListMarker typeArgumentListMarker, int i) {
            Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
            return this.c.get(typeArgumentListMarker, i);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public KotlinTypeMarker getApproximatedIntegerLiteralType(TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
            return this.c.getApproximatedIntegerLiteralType(typeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public TypeArgumentMarker getArgument(KotlinTypeMarker kotlinTypeMarker, int i) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.getArgument(kotlinTypeMarker, i);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public TypeArgumentMarker getArgumentOrNull(SimpleTypeMarker simpleTypeMarker, int i) {
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
            return this.c.getArgumentOrNull(simpleTypeMarker, i);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public List<TypeArgumentMarker> getArguments(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.getArguments(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public List<AnnotationMarker> getAttributes(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.getAttributes(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public List<AnnotationMarker> getCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.getCustomAttributes(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public KotlinTypeMarker getFunctionTypeFromSupertypes(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.getFunctionTypeFromSupertypes(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public TypeConstructorMarker getNonReflectFunctionTypeConstructor(int parametersNumber, FunctionTypeKind kind) {
            Intrinsics.checkNotNullParameter(kind, "kind");
            return this.c.getNonReflectFunctionTypeConstructor(parametersNumber, kind);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public TypeVariableTypeConstructorMarker getOriginalTypeVariable(StubTypeMarker stubTypeMarker) {
            Intrinsics.checkNotNullParameter(stubTypeMarker, "<this>");
            return this.c.getOriginalTypeVariable(stubTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public TypeParameterMarker getParameter(TypeConstructorMarker typeConstructorMarker, int i) {
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
            return this.c.getParameter(typeConstructorMarker, i);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public List<TypeParameterMarker> getParameters(TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
            return this.c.getParameters(typeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public TypeConstructorMarker getReflectFunctionTypeConstructor(int parametersNumber, FunctionTypeKind kind) {
            Intrinsics.checkNotNullParameter(kind, "kind");
            return this.c.getReflectFunctionTypeConstructor(parametersNumber, kind);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public KotlinTypeMarker getType(TypeArgumentMarker typeArgumentMarker) {
            Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
            return this.c.getType(typeArgumentMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public TypeConstructorMarker getTypeConstructor(TypeParameterMarker typeParameterMarker) {
            Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
            return this.c.getTypeConstructor(typeParameterMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public TypeParameterMarker getTypeParameter(TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeVariableTypeConstructorMarker, "<this>");
            return this.c.getTypeParameter(typeVariableTypeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public TypeParameterMarker getTypeParameterClassifier(TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
            return this.c.getTypeParameterClassifier(typeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public KotlinTypeMarker getUpperBound(TypeParameterMarker typeParameterMarker, int i) {
            Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
            return this.c.getUpperBound(typeParameterMarker, i);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public List<KotlinTypeMarker> getUpperBounds(TypeParameterMarker typeParameterMarker) {
            Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
            return this.c.getUpperBounds(typeParameterMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public TypeVariance getVariance(TypeArgumentMarker typeArgumentMarker) {
            Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
            return this.c.getVariance(typeArgumentMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public TypeVariance getVariance(TypeParameterMarker typeParameterMarker) {
            Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
            return this.c.getVariance(typeParameterMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean hasCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.hasCustomAttributes(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public boolean hasExactAnnotation(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.hasExactAnnotation(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean hasFlexibleNullability(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.hasFlexibleNullability(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public boolean hasNoInferAnnotation(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.hasNoInferAnnotation(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean hasRecursiveBounds(TypeParameterMarker typeParameterMarker, TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
            return this.c.hasRecursiveBounds(typeParameterMarker, typeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemOptimizationContext
        public boolean identicalArguments(SimpleTypeMarker a, SimpleTypeMarker b) {
            Intrinsics.checkNotNullParameter(a, "a");
            Intrinsics.checkNotNullParameter(b, "b");
            return this.c.identicalArguments(a, b);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public KotlinTypeMarker intersectTypes(List<? extends KotlinTypeMarker> types) {
            Intrinsics.checkNotNullParameter(types, "types");
            return this.c.intersectTypes(types);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        /* JADX INFO: renamed from: intersectTypes */
        public SimpleTypeMarker mo6107intersectTypes(List<? extends SimpleTypeMarker> types) {
            Intrinsics.checkNotNullParameter(types, "types");
            return this.c.intersectTypes((List<? extends KotlinTypeMarker>) types);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isAnonymous(TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
            return this.c.isAnonymous(typeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isAnyConstructor(TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
            return this.c.isAnyConstructor(typeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public boolean isBuiltinFunctionTypeOrSubtype(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.isBuiltinFunctionTypeOrSubtype(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isCapturedDynamic(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.isCapturedDynamic(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isCapturedType(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.isCapturedType(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public boolean isCapturedTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
            return this.c.isCapturedTypeConstructor(typeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isClassType(SimpleTypeMarker simpleTypeMarker) {
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
            return this.c.isClassType(simpleTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isClassTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
            return this.c.isClassTypeConstructor(typeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isCommonFinalClassConstructor(TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
            return this.c.isCommonFinalClassConstructor(typeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public boolean isContainedInInvariantOrContravariantPositions(TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeVariableTypeConstructorMarker, "<this>");
            return this.c.isContainedInInvariantOrContravariantPositions(typeVariableTypeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isDefinitelyClassTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
            return this.c.isDefinitelyClassTypeConstructor(typeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isDefinitelyNotNullType(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.isDefinitelyNotNullType(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isDenotable(TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
            return this.c.isDenotable(typeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isDynamic(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.isDynamic(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isError(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.isError(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isError(TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
            return this.c.isError(typeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
        public boolean isExtensionFunction(SimpleTypeMarker simpleTypeMarker) {
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
            return this.c.isExtensionFunction(simpleTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public boolean isExtensionFunctionType(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.isExtensionFunctionType(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public boolean isFinalClassConstructor(TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
            return this.c.isFinalClassConstructor(typeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isFlexible(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.isFlexible(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isFlexibleNothing(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.isFlexibleNothing(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public boolean isFunctionOrKFunctionWithAnySuspendability(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.isFunctionOrKFunctionWithAnySuspendability(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isIntegerConstantOperatorTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
            return this.c.isIntegerConstantOperatorTypeConstructor(typeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isIntegerLiteralConstantTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
            return this.c.isIntegerLiteralConstantTypeConstructor(typeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isIntegerLiteralType(SimpleTypeMarker simpleTypeMarker) {
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
            return this.c.isIntegerLiteralType(simpleTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isIntegerLiteralTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
            return this.c.isIntegerLiteralTypeConstructor(typeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isInterface(TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
            return this.c.isInterface(typeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isIntersection(TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
            return this.c.isIntersection(typeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public boolean isK2() {
            return this.c.isK2();
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isLocalType(TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
            return this.c.isLocalType(typeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isMarkedNullable(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.isMarkedNullable(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isMarkedNullable(SimpleTypeMarker simpleTypeMarker) {
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
            return this.c.isMarkedNullable(simpleTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        @ObsoleteTypeKind
        public boolean isNotNullTypeParameter(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.isNotNullTypeParameter(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isNothing(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.isNothing(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isNothingConstructor(TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
            return this.c.isNothingConstructor(typeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isNullableAny(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.isNullableAny(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isNullableNothing(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.isNullableNothing(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isNullableType(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.isNullableType(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isOldCapturedType(CapturedTypeMarker capturedTypeMarker) {
            Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
            return this.c.isOldCapturedType(capturedTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isPrimitiveType(SimpleTypeMarker simpleTypeMarker) {
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
            return this.c.isPrimitiveType(simpleTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isProjectionNotNull(CapturedTypeMarker capturedTypeMarker) {
            Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
            return this.c.isProjectionNotNull(capturedTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isRawType(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.isRawType(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public boolean isSignedOrUnsignedNumberType(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.isSignedOrUnsignedNumberType(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isSimpleType(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.isSimpleType(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isSingleClassifierType(SimpleTypeMarker simpleTypeMarker) {
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
            return this.c.isSingleClassifierType(simpleTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public boolean isSpecial(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.isSpecial(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isStarProjection(TypeArgumentMarker typeArgumentMarker) {
            Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
            return this.c.isStarProjection(typeArgumentMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isStubType(SimpleTypeMarker simpleTypeMarker) {
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
            return this.c.isStubType(simpleTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isStubTypeForBuilderInference(SimpleTypeMarker simpleTypeMarker) {
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
            return this.c.isStubTypeForBuilderInference(simpleTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isStubTypeForVariableInSubtyping(SimpleTypeMarker simpleTypeMarker) {
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
            return this.c.isStubTypeForVariableInSubtyping(simpleTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isTypeParameterTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
            return this.c.isTypeParameterTypeConstructor(typeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public boolean isTypeVariable(TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
            return this.c.isTypeVariable(typeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isTypeVariableType(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.isTypeVariableType(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public boolean isUninferredParameter(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.isUninferredParameter(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public boolean isUnit(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.isUnit(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public boolean isUnitTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
            return this.c.isUnitTypeConstructor(typeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public Iterator<TypeArgumentMarker> iterator(TypeArgumentListMarker typeArgumentListMarker) {
            Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
            return this.c.iterator(typeArgumentListMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public SimpleTypeMarker lowerBound(FlexibleTypeMarker flexibleTypeMarker) {
            Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
            return this.c.lowerBound(flexibleTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public SimpleTypeMarker lowerBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.lowerBoundIfFlexible(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public KotlinTypeMarker lowerType(CapturedTypeMarker capturedTypeMarker) {
            Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
            return this.c.lowerType(capturedTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public KotlinTypeMarker makeDefinitelyNotNullOrNotNull(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.makeDefinitelyNotNullOrNotNull(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public SimpleTypeMarker makeSimpleTypeDefinitelyNotNullOrNotNull(SimpleTypeMarker simpleTypeMarker) {
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
            return this.c.makeSimpleTypeDefinitelyNotNullOrNotNull(simpleTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeCheckerProviderContext
        public TypeCheckerState newTypeCheckerState(boolean errorTypesEqualToAnything, boolean stubTypesEqualToAnything) {
            return this.c.newTypeCheckerState(errorTypesEqualToAnything, stubTypesEqualToAnything);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
        public SimpleTypeMarker nothingType() {
            return this.c.nothingType();
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
        public SimpleTypeMarker nullableAnyType() {
            return this.c.nullableAnyType();
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
        public SimpleTypeMarker nullableNothingType() {
            return this.c.nullableNothingType();
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public SimpleTypeMarker original(DefinitelyNotNullTypeMarker definitelyNotNullTypeMarker) {
            Intrinsics.checkNotNullParameter(definitelyNotNullTypeMarker, "<this>");
            return this.c.original(definitelyNotNullTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public SimpleTypeMarker originalIfDefinitelyNotNullable(SimpleTypeMarker simpleTypeMarker) {
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
            return this.c.originalIfDefinitelyNotNullable(simpleTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public int parametersCount(TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
            return this.c.parametersCount(typeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public Collection<KotlinTypeMarker> possibleIntegerTypes(SimpleTypeMarker simpleTypeMarker) {
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
            return this.c.possibleIntegerTypes(simpleTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public TypeArgumentMarker projection(CapturedTypeConstructorMarker capturedTypeConstructorMarker) {
            Intrinsics.checkNotNullParameter(capturedTypeConstructorMarker, "<this>");
            return this.c.projection(capturedTypeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public KotlinTypeMarker removeAnnotations(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.removeAnnotations(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public KotlinTypeMarker removeExactAnnotation(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.removeExactAnnotation(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public KotlinTypeMarker replaceArguments(KotlinTypeMarker kotlinTypeMarker, Function1<? super TypeArgumentMarker, ? extends TypeArgumentMarker> replacement) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            Intrinsics.checkNotNullParameter(replacement, "replacement");
            return this.c.replaceArguments(kotlinTypeMarker, replacement);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public SimpleTypeMarker replaceArguments(SimpleTypeMarker simpleTypeMarker, List<? extends TypeArgumentMarker> newArguments) {
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
            Intrinsics.checkNotNullParameter(newArguments, "newArguments");
            return this.c.replaceArguments(simpleTypeMarker, newArguments);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public SimpleTypeMarker replaceArguments(SimpleTypeMarker simpleTypeMarker, Function1<? super TypeArgumentMarker, ? extends TypeArgumentMarker> replacement) {
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
            Intrinsics.checkNotNullParameter(replacement, "replacement");
            return this.c.replaceArguments(simpleTypeMarker, replacement);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public KotlinTypeMarker replaceArgumentsDeeply(KotlinTypeMarker kotlinTypeMarker, Function1<? super TypeArgumentMarker, ? extends TypeArgumentMarker> replacement) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            Intrinsics.checkNotNullParameter(replacement, "replacement");
            return this.c.replaceArgumentsDeeply(kotlinTypeMarker, replacement);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public SimpleTypeMarker replaceArgumentsDeeply(SimpleTypeMarker simpleTypeMarker, Function1<? super TypeArgumentMarker, ? extends TypeArgumentMarker> replacement) {
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
            Intrinsics.checkNotNullParameter(replacement, "replacement");
            return this.c.replaceArgumentsDeeply(simpleTypeMarker, replacement);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
        public KotlinTypeMarker replaceCustomAttributes(KotlinTypeMarker kotlinTypeMarker, List<? extends AnnotationMarker> newAttributes) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            Intrinsics.checkNotNullParameter(newAttributes, "newAttributes");
            return this.c.replaceCustomAttributes(kotlinTypeMarker, newAttributes);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public TypeArgumentMarker replaceType(TypeArgumentMarker typeArgumentMarker, KotlinTypeMarker newType) {
            Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
            Intrinsics.checkNotNullParameter(newType, "newType");
            return this.c.replaceType(typeArgumentMarker, newType);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public KotlinTypeMarker safeSubstitute(TypeSubstitutorMarker typeSubstitutorMarker, KotlinTypeMarker type) {
            Intrinsics.checkNotNullParameter(typeSubstitutorMarker, "<this>");
            Intrinsics.checkNotNullParameter(type, "type");
            return this.c.safeSubstitute(typeSubstitutorMarker, type);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public KotlinTypeMarker singleBestRepresentative(Collection<? extends KotlinTypeMarker> collection) {
            Intrinsics.checkNotNullParameter(collection, "<this>");
            return this.c.singleBestRepresentative(collection);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public int size(TypeArgumentListMarker typeArgumentListMarker) {
            Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
            return this.c.size(typeArgumentListMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public TypeCheckerState.SupertypesPolicy substitutionSupertypePolicy(SimpleTypeMarker type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return this.c.substitutionSupertypePolicy(type);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public Collection<KotlinTypeMarker> supertypes(TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
            return this.c.supertypes(typeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
        public SimpleTypeMarker toErrorType(TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
            return this.c.toErrorType(typeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public CapturedTypeConstructorMarker typeConstructor(CapturedTypeMarker capturedTypeMarker) {
            Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
            return this.c.typeConstructor(capturedTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public TypeConstructorMarker typeConstructor(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.typeConstructor(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public TypeConstructorMarker typeConstructor(SimpleTypeMarker simpleTypeMarker) {
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
            return this.c.typeConstructor(simpleTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public TypeArgumentMarker typeConstructorProjection(CapturedTypeMarker capturedTypeMarker) {
            Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
            return this.c.typeConstructorProjection(capturedTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
        public int typeDepth(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.typeDepth(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
        public int typeDepth(SimpleTypeMarker simpleTypeMarker) {
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
            return this.c.typeDepth(simpleTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public TypeParameterMarker typeParameter(CapturedTypeMarker capturedTypeMarker) {
            Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
            return this.c.typeParameter(capturedTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public TypeSubstitutorMarker typeSubstitutorByTypeConstructor(Map<TypeConstructorMarker, ? extends KotlinTypeMarker> map) {
            Intrinsics.checkNotNullParameter(map, "map");
            return this.c.typeSubstitutorByTypeConstructor(map);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
        public List<AnnotationMarker> unionTypeAttributes(List<? extends KotlinTypeMarker> types) {
            Intrinsics.checkNotNullParameter(types, "types");
            return this.c.unionTypeAttributes(types);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public TypeConstructorMarker unwrapStubTypeVariableConstructor(TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
            return this.c.unwrapStubTypeVariableConstructor(typeConstructorMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public SimpleTypeMarker upperBound(FlexibleTypeMarker flexibleTypeMarker) {
            Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
            return this.c.upperBound(flexibleTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public int upperBoundCount(TypeParameterMarker typeParameterMarker) {
            Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
            return this.c.upperBoundCount(typeParameterMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public SimpleTypeMarker upperBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.upperBoundIfFlexible(kotlinTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public boolean useRefinedBoundsForTypeVariableInFlexiblePosition() {
            return this.c.useRefinedBoundsForTypeVariableInFlexiblePosition();
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
        public KotlinTypeMarker withNotNullProjection(CapturedTypeMarker capturedTypeMarker) {
            Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
            return this.c.withNotNullProjection(capturedTypeMarker);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public KotlinTypeMarker withNullability(KotlinTypeMarker kotlinTypeMarker, boolean z) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            return this.c.withNullability(kotlinTypeMarker, z);
        }

        @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
        public SimpleTypeMarker withNullability(SimpleTypeMarker simpleTypeMarker, boolean z) {
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
            return this.c.withNullability(simpleTypeMarker, z);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public TypeCheckerStateForConstraintInjector(ConstraintInjector constraintInjector, TypeCheckerState baseState, Context c, IncorporationConstraintPosition position) {
            super(c, baseState.getKotlinTypePreparator(), baseState.getKotlinTypeRefiner());
            Intrinsics.checkNotNullParameter(baseState, "baseState");
            Intrinsics.checkNotNullParameter(c, "c");
            Intrinsics.checkNotNullParameter(position, "position");
            this.this$0 = constraintInjector;
            this.c = c;
            this.position = position;
            this.isInferenceCompatibilityEnabled = constraintInjector.languageVersionSettings.supportsFeature(LanguageFeature.InferenceCompatibility);
            this.baseLowerType = position.getInitialConstraint().getA();
            this.baseUpperType = position.getInitialConstraint().getB();
        }

        public final Context getC() {
            return this.c;
        }

        public final IncorporationConstraintPosition getPosition() {
            return this.position;
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public TypeCheckerStateForConstraintInjector(ConstraintInjector constraintInjector, Context c, IncorporationConstraintPosition position) {
            this(constraintInjector, c.newTypeCheckerState(true, true), c, position);
            Intrinsics.checkNotNullParameter(c, "c");
            Intrinsics.checkNotNullParameter(position, "position");
        }

        @Override // org.jetbrains.kotlin.resolve.calls.inference.components.TypeCheckerStateForConstraintSystem
        /* JADX INFO: renamed from: isInferenceCompatibilityEnabled, reason: from getter */
        public boolean getIsInferenceCompatibilityEnabled() {
            return this.isInferenceCompatibilityEnabled;
        }

        private final boolean getAllowForking() {
            return this.this$0.getConstraintIncorporator().getUtilContext().isForcedAllowForkingInferenceSystem();
        }

        public final List<Pair<TypeVariableMarker, Constraint>> extractAllConstraints() {
            List<Pair<TypeVariableMarker, Constraint>> list = this.possibleNewConstraints;
            this.possibleNewConstraints = null;
            return list;
        }

        public final List<List<Set<Pair<TypeVariableMarker, Constraint>>>> extractForkPointsData() {
            List<List<Set<Pair<TypeVariableMarker, Constraint>>>> list = this.forkPointsData;
            this.forkPointsData = null;
            return list;
        }

        public final void addPossibleNewConstraint(TypeVariableMarker variable, Constraint constraint) {
            Intrinsics.checkNotNullParameter(variable, "variable");
            Intrinsics.checkNotNullParameter(constraint, "constraint");
            List<List<Set<Pair<TypeVariableMarker, Constraint>>>> list = this.stackForConstraintsSetsFromCurrentForkPoint;
            if ((list != null ? (List) CollectionsKt.lastOrNull((List) list) : null) != null) {
                List<List<Pair<TypeVariableMarker, Constraint>>> list2 = this.stackForConstraintSetFromCurrentForkPointBranch;
                List list3 = list2 != null ? (List) CollectionsKt.lastOrNull((List) list2) : null;
                if (list3 == null) {
                    throw new IllegalArgumentException("Constraint has been added not under fork {...} call ".toString());
                }
                list3.add(TuplesKt.to(variable, constraint));
                return;
            }
            if (this.possibleNewConstraints == null) {
                this.possibleNewConstraints = new SmartList();
            }
            List<Pair<TypeVariableMarker, Constraint>> list4 = this.possibleNewConstraints;
            Intrinsics.checkNotNull(list4);
            list4.add(TuplesKt.to(variable, constraint));
        }

        @Override // org.jetbrains.kotlin.types.TypeCheckerState
        public boolean runForkingPoint(Function1<? super TypeCheckerState.ForkPointContext, Unit> block) {
            Intrinsics.checkNotNullParameter(block, "block");
            if (!getAllowForking()) {
                return super.runForkingPoint(block);
            }
            if (this.stackForConstraintsSetsFromCurrentForkPoint == null) {
                this.stackForConstraintsSetsFromCurrentForkPoint = new SmartList();
            }
            List<List<Set<Pair<TypeVariableMarker, Constraint>>>> list = this.stackForConstraintsSetsFromCurrentForkPoint;
            Intrinsics.checkNotNull(list);
            list.add(new SmartList());
            MyForkCreationContext myForkCreationContext = new MyForkCreationContext();
            block.invoke(myForkCreationContext);
            boolean anyForkSuccessful = myForkCreationContext.getAnyForkSuccessful();
            List<List<Set<Pair<TypeVariableMarker, Constraint>>>> list2 = this.stackForConstraintsSetsFromCurrentForkPoint;
            List list3 = list2 != null ? (List) AddToStdlibKt.popLast(list2) : null;
            List list4 = list3;
            if (list4 != null && !list4.isEmpty()) {
                if (list3.size() > 1) {
                    if (this.forkPointsData == null) {
                        this.forkPointsData = new SmartList();
                    }
                    List<List<Set<Pair<TypeVariableMarker, Constraint>>>> list5 = this.forkPointsData;
                    Intrinsics.checkNotNull(list5);
                    org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(list5, list3);
                    return true;
                }
                this.this$0.processGivenForkPointBranchConstraints(this.c, (Collection) CollectionsKt.single(list3), this.position);
            }
            return anyForkSuccessful;
        }

        /* JADX INFO: compiled from: ConstraintInjector.kt */
        @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\fH\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintInjector$TypeCheckerStateForConstraintInjector$MyForkCreationContext;", "Lorg/jetbrains/kotlin/types/TypeCheckerState$ForkPointContext;", "(Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintInjector$TypeCheckerStateForConstraintInjector;)V", "anyForkSuccessful", "", "getAnyForkSuccessful", "()Z", "setAnyForkSuccessful", "(Z)V", "fork", "", "block", "Lkotlin/Function0;", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        private final class MyForkCreationContext implements TypeCheckerState.ForkPointContext {
            private boolean anyForkSuccessful;

            public MyForkCreationContext() {
            }

            public final boolean getAnyForkSuccessful() {
                return this.anyForkSuccessful;
            }

            public final void setAnyForkSuccessful(boolean z) {
                this.anyForkSuccessful = z;
            }

            @Override // org.jetbrains.kotlin.types.TypeCheckerState.ForkPointContext
            public void fork(Function0<Boolean> block) {
                List list;
                Intrinsics.checkNotNullParameter(block, "block");
                if (TypeCheckerStateForConstraintInjector.this.stackForConstraintSetFromCurrentForkPointBranch == null) {
                    TypeCheckerStateForConstraintInjector.this.stackForConstraintSetFromCurrentForkPointBranch = new SmartList();
                }
                List list2 = TypeCheckerStateForConstraintInjector.this.stackForConstraintSetFromCurrentForkPointBranch;
                Intrinsics.checkNotNull(list2);
                list2.add(new SmartList());
                this.anyForkSuccessful = this.anyForkSuccessful || block.invoke().booleanValue();
                List list3 = TypeCheckerStateForConstraintInjector.this.stackForConstraintsSetsFromCurrentForkPoint;
                Intrinsics.checkNotNull(list3);
                Collection collection = (Collection) CollectionsKt.last(list3);
                List list4 = TypeCheckerStateForConstraintInjector.this.stackForConstraintSetFromCurrentForkPointBranch;
                Set set = null;
                if (list4 != null && (list = (List) AddToStdlibKt.popLast(list4)) != null) {
                    if (list.isEmpty()) {
                        list = null;
                    }
                    if (list != null) {
                        set = CollectionsKt.toSet(list);
                    }
                }
                org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(collection, set);
            }
        }

        public final boolean hasConstraintsToProcess() {
            return this.possibleNewConstraints != null;
        }

        public final void setConstrainingTypesToPrintDebugInfo(KotlinTypeMarker lowerType, KotlinTypeMarker upperType) {
            Intrinsics.checkNotNullParameter(lowerType, "lowerType");
            Intrinsics.checkNotNullParameter(upperType, "upperType");
            this.baseLowerType = lowerType;
            this.baseUpperType = upperType;
        }

        public static /* synthetic */ void runIsSubtypeOf$default(TypeCheckerStateForConstraintInjector typeCheckerStateForConstraintInjector, KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2, boolean z, boolean z2, int i, Object obj) {
            if ((i & 4) != 0) {
                z = false;
            }
            if ((i & 8) != 0) {
                z2 = false;
            }
            typeCheckerStateForConstraintInjector.runIsSubtypeOf(kotlinTypeMarker, kotlinTypeMarker2, z, z2);
        }

        private static final boolean runIsSubtypeOf$isSubtypeOf(TypeCheckerStateForConstraintInjector typeCheckerStateForConstraintInjector, KotlinTypeMarker kotlinTypeMarker, boolean z, KotlinTypeMarker kotlinTypeMarker2) {
            AbstractTypeChecker abstractTypeChecker = AbstractTypeChecker.INSTANCE;
            Intrinsics.checkNotNull(typeCheckerStateForConstraintInjector, "null cannot be cast to non-null type org.jetbrains.kotlin.types.TypeCheckerState");
            return abstractTypeChecker.isSubtypeOf((TypeCheckerState) typeCheckerStateForConstraintInjector, kotlinTypeMarker, kotlinTypeMarker2, z);
        }

        public final void runIsSubtypeOf(KotlinTypeMarker lowerType, KotlinTypeMarker upperType, boolean shouldTryUseDifferentFlexibilityForUpperType, boolean isFromNullabilityConstraint) {
            Intrinsics.checkNotNullParameter(lowerType, "lowerType");
            Intrinsics.checkNotNullParameter(upperType, "upperType");
            if (runIsSubtypeOf$isSubtypeOf(this, lowerType, isFromNullabilityConstraint, upperType)) {
                return;
            }
            if (shouldTryUseDifferentFlexibilityForUpperType && isSimpleType(upperType)) {
                if (!(upperType instanceof SimpleTypeMarker)) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                SimpleTypeMarker simpleTypeMarker = (SimpleTypeMarker) upperType;
                KotlinTypeMarker kotlinTypeMarkerCreateFlexibleType = createFlexibleType(simpleTypeMarker, withNullability(simpleTypeMarker, true));
                if (runIsSubtypeOf$isSubtypeOf(this, lowerType, isFromNullabilityConstraint, kotlinTypeMarkerCreateFlexibleType)) {
                    return;
                }
                this.c.addError(new NewConstraintError(lowerType, kotlinTypeMarkerCreateFlexibleType, this.position));
                return;
            }
            this.c.addError(new NewConstraintError(lowerType, upperType, this.position));
        }

        @Override // org.jetbrains.kotlin.resolve.calls.inference.components.TypeCheckerStateForConstraintSystem
        public boolean isMyTypeVariable(SimpleTypeMarker type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return this.c.getAllTypeVariables().containsKey(unwrapStubTypeVariableConstructor(typeConstructor(type)));
        }

        @Override // org.jetbrains.kotlin.resolve.calls.inference.components.TypeCheckerStateForConstraintSystem
        public void addUpperConstraint(TypeConstructorMarker typeVariable, KotlinTypeMarker superType) {
            Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
            Intrinsics.checkNotNullParameter(superType, "superType");
            addConstraint$default(this, typeVariable, superType, ConstraintKind.UPPER, false, 8, null);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.inference.components.TypeCheckerStateForConstraintSystem
        public void addLowerConstraint(TypeConstructorMarker typeVariable, KotlinTypeMarker subType, boolean isFromNullabilityConstraint) {
            Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
            Intrinsics.checkNotNullParameter(subType, "subType");
            addConstraint(typeVariable, subType, ConstraintKind.LOWER, isFromNullabilityConstraint);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.inference.components.TypeCheckerStateForConstraintSystem
        public void addEqualityConstraint(TypeConstructorMarker typeVariable, KotlinTypeMarker type) {
            Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
            Intrinsics.checkNotNullParameter(type, "type");
            addConstraint(typeVariable, type, ConstraintKind.EQUALITY, false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isCapturedTypeFromSubtyping(KotlinTypeMarker type) {
            CapturedTypeMarker capturedTypeMarker = type instanceof CapturedTypeMarker ? (CapturedTypeMarker) type : null;
            if (capturedTypeMarker == null || isOldCapturedType(capturedTypeMarker)) {
                return false;
            }
            int i = WhenMappings.$EnumSwitchMapping$0[captureStatus(capturedTypeMarker).ordinal()];
            if (i == 1) {
                return false;
            }
            if (i == 2) {
                return true;
            }
            if (i == 3) {
                throw new IllegalStateException(("Captured type for incorporation shouldn't escape from incorporation: " + type + '\n' + renderBaseConstraint()).toString());
            }
            throw new NoWhenBranchMatchedException();
        }

        static /* synthetic */ void addConstraint$default(TypeCheckerStateForConstraintInjector typeCheckerStateForConstraintInjector, TypeConstructorMarker typeConstructorMarker, KotlinTypeMarker kotlinTypeMarker, ConstraintKind constraintKind, boolean z, int i, Object obj) {
            if ((i & 8) != 0) {
                z = false;
            }
            typeCheckerStateForConstraintInjector.addConstraint(typeConstructorMarker, kotlinTypeMarker, constraintKind, z);
        }

        private final void addConstraint(TypeConstructorMarker typeVariableConstructor, KotlinTypeMarker type, ConstraintKind kind, boolean isFromNullabilityConstraint) {
            TypeVariableMarker typeVariableMarker = this.c.getAllTypeVariables().get(unwrapStubTypeVariableConstructor(typeVariableConstructor));
            if (typeVariableMarker == null) {
                throw new IllegalStateException(("Should by type variableConstructor: " + typeVariableConstructor + ". " + this.c.getAllTypeVariables().values()).toString());
            }
            addNewIncorporatedConstraint(typeVariableMarker, type, new ConstraintContext(kind, SetsKt.emptySet(), null, isFromNullabilityConstraint, 4, null));
        }

        private final void addNewIncorporatedConstraintFromDeclaredUpperBound(Runnable runIsSubtypeOf) {
            this.isIncorporatingConstraintFromDeclaredUpperBound = true;
            runIsSubtypeOf.run();
            this.isIncorporatingConstraintFromDeclaredUpperBound = false;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintIncorporator.Context
        public void addNewIncorporatedConstraint(final KotlinTypeMarker lowerType, final KotlinTypeMarker upperType, final boolean shouldTryUseDifferentFlexibilityForUpperType, final boolean isFromNullabilityConstraint, boolean isFromDeclaredUpperBound) {
            Intrinsics.checkNotNullParameter(lowerType, "lowerType");
            Intrinsics.checkNotNullParameter(upperType, "upperType");
            if (lowerType != upperType && this.this$0.isAllowedType(this.c, lowerType) && this.this$0.isAllowedType(this.c, upperType)) {
                if (!isFromDeclaredUpperBound) {
                    runIsSubtypeOf(lowerType, upperType, shouldTryUseDifferentFlexibilityForUpperType, isFromNullabilityConstraint);
                } else {
                    addNewIncorporatedConstraintFromDeclaredUpperBound(new Runnable() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintInjector$TypeCheckerStateForConstraintInjector$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.runIsSubtypeOf(lowerType, upperType, shouldTryUseDifferentFlexibilityForUpperType, isFromNullabilityConstraint);
                        }
                    });
                }
            }
        }

        @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintIncorporator.Context
        public void addNewIncorporatedConstraint(TypeVariableMarker typeVariable, KotlinTypeMarker type, ConstraintContext constraintContext) {
            KotlinTypeMarker kotlinTypeMarker;
            KotlinTypeMarker kotlinTypeMarkerApproximateToSubType;
            KotlinTypeMarker kotlinTypeMarkerApproximateToSuperType;
            Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(constraintContext, "constraintContext");
            ConstraintKind kind = constraintContext.getKind();
            Set<TypeVariableMarker> setComponent2 = constraintContext.component2();
            OnlyInputTypeConstraintPosition inputTypePositionBeforeIncorporation = constraintContext.getInputTypePositionBeforeIncorporation();
            boolean isNullabilityConstraint = constraintContext.getIsNullabilityConstraint();
            if (isUninferredParameter(type)) {
                return;
            }
            if (isError(type)) {
                this.c.addError(new ConstrainingTypeIsError(typeVariable, type, this.position));
                return;
            }
            if (contains(type, new ConstraintInjector$TypeCheckerStateForConstraintInjector$addNewIncorporatedConstraint$2(this))) {
                if (kind != ConstraintKind.UPPER || (kotlinTypeMarkerApproximateToSubType = this.this$0.getTypeApproximator().approximateToSubType(type, TypeApproximatorConfiguration.SubtypeCapturedTypesApproximation.INSTANCE)) == null) {
                    kotlinTypeMarkerApproximateToSubType = type;
                }
                if (kind == ConstraintKind.LOWER && (kotlinTypeMarkerApproximateToSuperType = this.this$0.getTypeApproximator().approximateToSuperType(type, TypeApproximatorConfiguration.SubtypeCapturedTypesApproximation.INSTANCE)) != null) {
                    kotlinTypeMarkerApproximateToSubType = kotlinTypeMarkerApproximateToSuperType;
                }
                if (kotlinTypeMarkerApproximateToSubType == type) {
                    this.c.addError(new CapturedTypeFromSubtyping(typeVariable, type, this.position));
                    return;
                }
                kotlinTypeMarker = kotlinTypeMarkerApproximateToSubType;
            } else {
                kotlinTypeMarker = type;
            }
            addPossibleNewConstraint(typeVariable, new Constraint(kind, kotlinTypeMarker, this.isIncorporatingConstraintFromDeclaredUpperBound ? IncorporationConstraintPosition.copy$default(this.position, null, true, 1, null) : this.position, 0, setComponent2, isNullabilityConstraint, inputTypePositionBeforeIncorporation, 8, null));
        }

        @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintIncorporator.Context
        public Collection<VariableWithConstraints> getAllTypeVariablesWithConstraints() {
            return this.c.getNotFixedTypeVariables().values();
        }

        @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintIncorporator.Context
        public TypeVariableMarker getTypeVariable(TypeConstructorMarker typeConstructor) {
            Intrinsics.checkNotNullParameter(typeConstructor, "typeConstructor");
            TypeVariableMarker typeVariableMarker = this.c.getAllTypeVariables().get(typeConstructor);
            if (typeVariableMarker == null || this.c.getNotFixedTypeVariables().containsKey(typeConstructor)) {
                return typeVariableMarker;
            }
            fixedTypeVariable(typeVariableMarker);
            throw null;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintIncorporator.Context
        public List<Constraint> getConstraintsForVariable(TypeVariableMarker typeVariable) {
            List<Constraint> constraints;
            Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
            MutableVariableWithConstraints mutableVariableWithConstraints = this.c.getNotFixedTypeVariables().get(freshTypeConstructor(typeVariable));
            if (mutableVariableWithConstraints != null && (constraints = mutableVariableWithConstraints.getConstraints()) != null) {
                return constraints;
            }
            fixedTypeVariable(typeVariable);
            throw null;
        }

        public final Void fixedTypeVariable(TypeVariableMarker variable) {
            Intrinsics.checkNotNullParameter(variable, "variable");
            throw new IllegalStateException(("Type variable " + variable + " should not be fixed!\n" + renderBaseConstraint()).toString());
        }

        private final String renderBaseConstraint() {
            return "Base constraint: " + this.baseLowerType + " <: " + this.baseUpperType + " from position: " + this.position;
        }
    }
}
