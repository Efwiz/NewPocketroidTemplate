package org.jetbrains.kotlin.resolve.calls.inference;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPositionKind;
import org.jetbrains.kotlin.resolve.calls.results.SimpleConstraintSystem;
import org.jetbrains.kotlin.resolve.descriptorUtil.AnnotationsForResolveUtilsKt;
import org.jetbrains.kotlin.types.CustomTypeParameter;
import org.jetbrains.kotlin.types.DescriptorSubstitutor;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.FunctionPlaceholdersKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.TypeCapabilitiesKt;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeConstructorSubstitution;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitution;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.TypeCheckingProcedure;
import org.jetbrains.kotlin.types.checker.TypeCheckingProcedureCallbacks;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: ConstraintSystemBuilderImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000²\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\n\n\u0002\u0010\u001e\n\u0002\b\r\b\u0016\u0018\u0000 [2\u00020\u0001:\u0004[\\]^B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0001H\u0016J-\u0010 \u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\u00072\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'H\u0000¢\u0006\u0002\b(J*\u0010)\u001a\u00020\u001e2\u0006\u0010*\u001a\u00020+2\b\u0010,\u001a\u0004\u0018\u00010#2\b\u0010-\u001a\u0004\u0018\u00010#2\u0006\u0010&\u001a\u00020'J$\u0010.\u001a\u00020\u001e2\b\u0010\"\u001a\u0004\u0018\u00010#2\b\u0010/\u001a\u0004\u0018\u00010#2\u0006\u00100\u001a\u000201H\u0016J\b\u00102\u001a\u000203H\u0016J4\u00104\u001a\u00020\u001e2\u0006\u0010*\u001a\u00020+2\b\u0010,\u001a\u0004\u0018\u00010#2\b\u0010-\u001a\u0004\u0018\u00010#2\u0006\u0010&\u001a\u00020'2\u0006\u00105\u001a\u000206H\u0002J\u0010\u00107\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\u0007H\u0002J\b\u00108\u001a\u00020\u001eH\u0016J(\u00109\u001a\u00020\u001e2\u0006\u0010:\u001a\u00020#2\u0006\u0010\"\u001a\u00020#2\u0006\u0010;\u001a\u00020%2\u0006\u0010&\u001a\u00020'H\u0002J(\u0010<\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\u00072\u0006\u0010=\u001a\u00020>2\u0006\u0010&\u001a\u00020'2\u0006\u0010?\u001a\u00020@H\u0002J\u0012\u0010A\u001a\u0004\u0018\u00010\u00072\u0006\u0010B\u001a\u00020CH\u0002J\u0017\u0010A\u001a\u0004\u0018\u00010\u00072\u0006\u0010D\u001a\u00020#H\u0000¢\u0006\u0002\bEJ\u001b\u0010F\u001a\b\u0012\u0004\u0012\u00020\u00070G2\u0006\u0010D\u001a\u00020#H\u0000¢\u0006\u0002\bHJ\u0015\u0010I\u001a\u00020\b2\u0006\u0010J\u001a\u00020\u0007H\u0000¢\u0006\u0002\bKJ\u001a\u0010L\u001a\u00020@2\b\u0010D\u001a\u0004\u0018\u00010#2\u0006\u00100\u001a\u000201H\u0002J\u0010\u0010M\u001a\u00020@2\u0006\u0010B\u001a\u00020CH\u0002J\u0015\u0010M\u001a\u00020@2\u0006\u0010D\u001a\u00020#H\u0000¢\u0006\u0002\bNJ&\u0010O\u001a\u00020\u00152\u0006\u0010P\u001a\u00020\u00142\f\u0010Q\u001a\b\u0012\u0004\u0012\u00020C0R2\u0006\u0010S\u001a\u00020@H\u0016J\u0018\u0010T\u001a\u00020#2\u0006\u0010D\u001a\u00020#2\u0006\u0010U\u001a\u00020@H\u0002J(\u0010V\u001a\u00020\u001e2\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020#2\u0006\u0010-\u001a\u00020#2\u0006\u0010W\u001a\u000201H\u0002J\u0018\u0010X\u001a\u00020\u00152\u0006\u0010P\u001a\u00020\u00142\u0006\u0010Y\u001a\u00020\u0015H\u0002J\f\u0010Z\u001a\u00020@*\u00020#H\u0002R \u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\fX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00150\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\nR&\u0010\u0017\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00190\u0018X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001c¨\u0006_"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilderImpl;", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystem$Builder;", "mode", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilderImpl$Mode;", "(Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilderImpl$Mode;)V", "allTypeParameterBounds", "Ljava/util/LinkedHashMap;", "Lorg/jetbrains/kotlin/resolve/calls/inference/TypeVariable;", "Lorg/jetbrains/kotlin/resolve/calls/inference/TypeBoundsImpl;", "getAllTypeParameterBounds$frontend", "()Ljava/util/LinkedHashMap;", "errors", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintError;", "getErrors$frontend", "()Ljava/util/ArrayList;", "initialConstraints", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilderImpl$Constraint;", "getInitialConstraints$frontend", "typeVariableSubstitutors", "Lorg/jetbrains/kotlin/resolve/calls/inference/CallHandle;", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "getTypeVariableSubstitutors", "usedInBounds", "Ljava/util/HashMap;", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/TypeBounds$Bound;", "getUsedInBounds$frontend", "()Ljava/util/HashMap;", ProductAction.ACTION_ADD, "", "other", "addBound", "typeVariable", "constrainingType", "Lorg/jetbrains/kotlin/types/KotlinType;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/inference/TypeBounds$BoundKind;", "constraintContext", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintContext;", "addBound$frontend", "addConstraint", "constraintKind", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilderImpl$ConstraintKind;", "subType", "superType", "addSubtypeConstraint", "subjectType", "constraintPosition", "Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPosition;", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystem;", "doAddConstraint", "typeCheckingProcedure", "Lorg/jetbrains/kotlin/types/checker/TypeCheckingProcedure;", "fixVariable", "fixVariables", "generateTypeParameterBound", "parameterType", "boundKind", "generateTypeParameterCaptureConstraint", "constrainingTypeProjection", "Lorg/jetbrains/kotlin/types/TypeProjection;", "isTypeMarkedNullable", "", "getMyTypeVariable", "typeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", ModuleXmlParser.TYPE, "getMyTypeVariable$frontend", "getNestedTypeVariables", "", "getNestedTypeVariables$frontend", "getTypeBounds", "variable", "getTypeBounds$frontend", "isErrorOrSpecialType", "isMyTypeVariable", "isMyTypeVariable$frontend", "registerTypeVariables", "call", "typeParameters", "", "external", "simplifyType", "isInitialConstraint", "storeInitialConstraint", "position", "storeSubstitutor", "substitutor", "isProper", "Companion", "Constraint", "ConstraintKind", "Mode", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ConstraintSystemBuilderImpl implements ConstraintSystem.Builder {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final LinkedHashMap<TypeVariable, TypeBoundsImpl> allTypeParameterBounds;
    private final ArrayList<ConstraintError> errors;
    private final ArrayList<Constraint> initialConstraints;
    private final Mode mode;
    private final LinkedHashMap<CallHandle, TypeSubstitutor> typeVariableSubstitutors;
    private final HashMap<TypeVariable, List<TypeBounds.Bound>> usedInBounds;

    /* JADX INFO: compiled from: ConstraintSystemBuilderImpl.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilderImpl$Mode;", "", "(Ljava/lang/String;I)V", "INFERENCE", "SPECIFICITY", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum Mode {
        INFERENCE,
        SPECIFICITY;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<Mode> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ConstraintSystemBuilderImpl() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public ConstraintSystemBuilderImpl(Mode mode) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        this.mode = mode;
        this.allTypeParameterBounds = new LinkedHashMap<>();
        this.usedInBounds = new HashMap<>();
        this.errors = new ArrayList<>();
        this.initialConstraints = new ArrayList<>();
        this.typeVariableSubstitutors = new LinkedHashMap<>();
    }

    public /* synthetic */ ConstraintSystemBuilderImpl(Mode mode, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? Mode.INFERENCE : mode);
    }

    /* JADX INFO: compiled from: ConstraintSystemBuilderImpl.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0080\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0014\u001a\u00020\bHÆ\u0003J1\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\bHÆ\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000f¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilderImpl$Constraint;", "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilderImpl$ConstraintKind;", "subtype", "Lorg/jetbrains/kotlin/types/KotlinType;", "superType", "position", "Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPosition;", "(Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilderImpl$ConstraintKind;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPosition;)V", "getKind", "()Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilderImpl$ConstraintKind;", "getPosition", "()Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPosition;", "getSubtype", "()Lorg/jetbrains/kotlin/types/KotlinType;", "getSuperType", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Constraint {
        private final ConstraintKind kind;
        private final ConstraintPosition position;
        private final KotlinType subtype;
        private final KotlinType superType;

        public static /* synthetic */ Constraint copy$default(Constraint constraint, ConstraintKind constraintKind, KotlinType kotlinType, KotlinType kotlinType2, ConstraintPosition constraintPosition, int i, Object obj) {
            if ((i & 1) != 0) {
                constraintKind = constraint.kind;
            }
            if ((i & 2) != 0) {
                kotlinType = constraint.subtype;
            }
            if ((i & 4) != 0) {
                kotlinType2 = constraint.superType;
            }
            if ((i & 8) != 0) {
                constraintPosition = constraint.position;
            }
            return constraint.copy(constraintKind, kotlinType, kotlinType2, constraintPosition);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final ConstraintKind getKind() {
            return this.kind;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final KotlinType getSubtype() {
            return this.subtype;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final KotlinType getSuperType() {
            return this.superType;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final ConstraintPosition getPosition() {
            return this.position;
        }

        public final Constraint copy(ConstraintKind kind, KotlinType subtype, KotlinType superType, ConstraintPosition position) {
            Intrinsics.checkNotNullParameter(kind, "kind");
            Intrinsics.checkNotNullParameter(subtype, "subtype");
            Intrinsics.checkNotNullParameter(superType, "superType");
            Intrinsics.checkNotNullParameter(position, "position");
            return new Constraint(kind, subtype, superType, position);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Constraint)) {
                return false;
            }
            Constraint constraint = (Constraint) other;
            return this.kind == constraint.kind && Intrinsics.areEqual(this.subtype, constraint.subtype) && Intrinsics.areEqual(this.superType, constraint.superType) && Intrinsics.areEqual(this.position, constraint.position);
        }

        public int hashCode() {
            return (((((this.kind.hashCode() * 31) + this.subtype.hashCode()) * 31) + this.superType.hashCode()) * 31) + this.position.hashCode();
        }

        public String toString() {
            return "Constraint(kind=" + this.kind + ", subtype=" + this.subtype + ", superType=" + this.superType + ", position=" + this.position + ')';
        }

        public Constraint(ConstraintKind kind, KotlinType subtype, KotlinType superType, ConstraintPosition position) {
            Intrinsics.checkNotNullParameter(kind, "kind");
            Intrinsics.checkNotNullParameter(subtype, "subtype");
            Intrinsics.checkNotNullParameter(superType, "superType");
            Intrinsics.checkNotNullParameter(position, "position");
            this.kind = kind;
            this.subtype = subtype;
            this.superType = superType;
            this.position = position;
        }

        public final ConstraintKind getKind() {
            return this.kind;
        }

        public final ConstraintPosition getPosition() {
            return this.position;
        }

        public final KotlinType getSubtype() {
            return this.subtype;
        }

        public final KotlinType getSuperType() {
            return this.superType;
        }
    }

    /* JADX INFO: compiled from: ConstraintSystemBuilderImpl.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilderImpl$ConstraintKind;", "", "bound", "Lorg/jetbrains/kotlin/resolve/calls/inference/TypeBounds$BoundKind;", "(Ljava/lang/String;ILorg/jetbrains/kotlin/resolve/calls/inference/TypeBounds$BoundKind;)V", "getBound", "()Lorg/jetbrains/kotlin/resolve/calls/inference/TypeBounds$BoundKind;", "SUB_TYPE", "EQUAL", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum ConstraintKind {
        SUB_TYPE(TypeBounds.BoundKind.UPPER_BOUND),
        EQUAL(TypeBounds.BoundKind.EXACT_BOUND);

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
        private final TypeBounds.BoundKind bound;

        public static EnumEntries<ConstraintKind> getEntries() {
            return $ENTRIES;
        }

        ConstraintKind(TypeBounds.BoundKind boundKind) {
            this.bound = boundKind;
        }

        public final TypeBounds.BoundKind getBound() {
            return this.bound;
        }
    }

    public final LinkedHashMap<TypeVariable, TypeBoundsImpl> getAllTypeParameterBounds$frontend() {
        return this.allTypeParameterBounds;
    }

    public final HashMap<TypeVariable, List<TypeBounds.Bound>> getUsedInBounds$frontend() {
        return this.usedInBounds;
    }

    public final ArrayList<ConstraintError> getErrors$frontend() {
        return this.errors;
    }

    public final ArrayList<Constraint> getInitialConstraints$frontend() {
        return this.initialConstraints;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystem.Builder
    public LinkedHashMap<CallHandle, TypeSubstitutor> getTypeVariableSubstitutors() {
        return this.typeVariableSubstitutors;
    }

    private final TypeSubstitutor storeSubstitutor(CallHandle call, TypeSubstitutor substitutor) {
        if (getTypeVariableSubstitutors().containsKey(call)) {
            throw new IllegalStateException("Type variables for the same call can be registered only once: " + call);
        }
        getTypeVariableSubstitutors().put(call, substitutor);
        return substitutor;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystem.Builder
    public TypeSubstitutor registerTypeVariables(CallHandle call, Collection<? extends TypeParameterDescriptor> typeParameters, boolean external) {
        ArrayList arrayList;
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        if (typeParameters.isEmpty()) {
            TypeSubstitutor EMPTY = TypeSubstitutor.EMPTY;
            Intrinsics.checkNotNullExpressionValue(EMPTY, "EMPTY");
            return storeSubstitutor(call, EMPTY);
        }
        if (external) {
            Collection<? extends TypeParameterDescriptor> collection = typeParameters;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
            for (TypeParameterDescriptor typeParameterDescriptor : collection) {
                arrayList2.add(new TypeVariable(call, typeParameterDescriptor, typeParameterDescriptor, true));
            }
            arrayList = arrayList2;
        } else {
            ArrayList arrayList3 = new ArrayList(typeParameters.size());
            Collection<? extends TypeParameterDescriptor> collection2 = typeParameters;
            DescriptorSubstitutor.substituteTypeParameters(CollectionsKt.toList(collection2), TypeSubstitution.EMPTY, ((TypeParameterDescriptor) CollectionsKt.first(collection2)).getContainingDeclaration(), arrayList3);
            List<Pair> listZip = CollectionsKt.zip(arrayList3, collection2);
            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listZip, 10));
            for (Pair pair : listZip) {
                arrayList4.add(new TypeVariable(call, (TypeParameterDescriptor) pair.component1(), (TypeParameterDescriptor) pair.component2(), external));
            }
            arrayList = arrayList4;
        }
        Collection<? extends TypeParameterDescriptor> collection3 = typeParameters;
        ArrayList arrayList5 = arrayList;
        Iterator it = CollectionsKt.zip(collection3, arrayList5).iterator();
        while (it.hasNext()) {
            TypeVariable typeVariable = (TypeVariable) ((Pair) it.next()).component2();
            this.allTypeParameterBounds.put(typeVariable, new TypeBoundsImpl(typeVariable));
        }
        Iterator<Map.Entry<TypeVariable, TypeBoundsImpl>> it2 = this.allTypeParameterBounds.entrySet().iterator();
        while (it2.hasNext()) {
            TypeVariable key = it2.next().getKey();
            for (KotlinType kotlinType : key.getFreshTypeParameter().getUpperBounds()) {
                Intrinsics.checkNotNull(kotlinType);
                if (!TypeUtilsKt.isDefaultBound(kotlinType)) {
                    addBound$frontend(key, kotlinType, TypeBounds.BoundKind.UPPER_BOUND, new ConstraintContext(ConstraintPositionKind.TYPE_BOUND_POSITION.position(key.getOriginalTypeParameter().getIndex()), null, false, false, 14, null));
                }
            }
        }
        TypeConstructorSubstitution.Companion companion = TypeConstructorSubstitution.INSTANCE;
        ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList5, 10));
        Iterator it3 = arrayList5.iterator();
        while (it3.hasNext()) {
            arrayList6.add(((TypeVariable) it3.next()).getType());
        }
        TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(companion.createByParametersMap(MapsKt.toMap(CollectionsKt.zip(collection3, TypeUtilsKt.defaultProjections(arrayList6)))));
        Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate, "create(...)");
        return storeSubstitutor(call, typeSubstitutorCreate);
    }

    private final boolean isProper(KotlinType kotlinType) {
        return !TypeUtils.contains(kotlinType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilderImpl.isProper.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(UnwrappedType unwrappedType) {
                ClassifierDescriptor this$0 = unwrappedType.getConstructor().getThis$0();
                return Boolean.valueOf((this$0 instanceof TypeParameterDescriptor) && ConstraintSystemBuilderImpl.this.isMyTypeVariable((TypeParameterDescriptor) this$0));
            }
        });
    }

    public final List<TypeVariable> getNestedTypeVariables$frontend(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        List<TypeParameterDescriptor> nestedTypeParameters = ConstraintSystemUtilsKt.getNestedTypeParameters(type);
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = nestedTypeParameters.iterator();
        while (it.hasNext()) {
            TypeVariable myTypeVariable = getMyTypeVariable((TypeParameterDescriptor) it.next());
            if (myTypeVariable != null) {
                arrayList.add(myTypeVariable);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystem.Builder
    public void addSubtypeConstraint(KotlinType constrainingType, KotlinType subjectType, ConstraintPosition constraintPosition) {
        Intrinsics.checkNotNullParameter(constraintPosition, "constraintPosition");
        addConstraint(ConstraintKind.SUB_TYPE, constrainingType, subjectType, new ConstraintContext(constraintPosition, null, true, true, 2, null));
    }

    public final void addConstraint(ConstraintKind constraintKind, KotlinType subType, KotlinType superType, ConstraintContext constraintContext) {
        Intrinsics.checkNotNullParameter(constraintKind, "constraintKind");
        Intrinsics.checkNotNullParameter(constraintContext, "constraintContext");
        final ConstraintPosition position = constraintContext.getPosition();
        final ConstraintContext constraintContext2 = new ConstraintContext(constraintContext.getPosition(), null, false, constraintContext.getInitialReduction());
        doAddConstraint(constraintKind, subType, superType, constraintContext, new TypeCheckingProcedure(new TypeCheckingProcedureCallbacks() { // from class: org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilderImpl$addConstraint$typeCheckingProcedure$1
            private int depth;

            @Override // org.jetbrains.kotlin.types.checker.TypeCheckingProcedureCallbacks
            public boolean assertEqualTypes(KotlinType a, KotlinType b, TypeCheckingProcedure typeCheckingProcedure) {
                Intrinsics.checkNotNullParameter(a, "a");
                Intrinsics.checkNotNullParameter(b, "b");
                Intrinsics.checkNotNullParameter(typeCheckingProcedure, "typeCheckingProcedure");
                this.depth++;
                this.this$0.doAddConstraint(ConstraintSystemBuilderImpl.ConstraintKind.EQUAL, a, b, constraintContext2, typeCheckingProcedure);
                this.depth--;
                return true;
            }

            @Override // org.jetbrains.kotlin.types.checker.TypeCheckingProcedureCallbacks
            public boolean assertEqualTypeConstructors(TypeConstructor a, TypeConstructor b) {
                Intrinsics.checkNotNullParameter(a, "a");
                Intrinsics.checkNotNullParameter(b, "b");
                return Intrinsics.areEqual(a, b);
            }

            @Override // org.jetbrains.kotlin.types.checker.TypeCheckingProcedureCallbacks
            public boolean assertSubtype(KotlinType subtype, KotlinType supertype, TypeCheckingProcedure typeCheckingProcedure) {
                Intrinsics.checkNotNullParameter(subtype, "subtype");
                Intrinsics.checkNotNullParameter(supertype, "supertype");
                Intrinsics.checkNotNullParameter(typeCheckingProcedure, "typeCheckingProcedure");
                this.depth++;
                this.this$0.doAddConstraint(ConstraintSystemBuilderImpl.ConstraintKind.SUB_TYPE, subtype, supertype, constraintContext2, typeCheckingProcedure);
                this.depth--;
                return true;
            }

            @Override // org.jetbrains.kotlin.types.checker.TypeCheckingProcedureCallbacks
            public boolean capture(KotlinType type, TypeProjection typeProjection) {
                TypeVariable myTypeVariable$frontend;
                Intrinsics.checkNotNullParameter(type, "type");
                Intrinsics.checkNotNullParameter(typeProjection, "typeProjection");
                ConstraintSystemBuilderImpl constraintSystemBuilderImpl = this.this$0;
                KotlinType nullableAnyType = typeProjection.getNullableAnyType();
                Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
                if (constraintSystemBuilderImpl.isMyTypeVariable$frontend(nullableAnyType) || this.depth > 0 || (myTypeVariable$frontend = this.this$0.getMyTypeVariable$frontend(type)) == null || !position.isParameter()) {
                    return false;
                }
                this.this$0.generateTypeParameterCaptureConstraint(myTypeVariable$frontend, typeProjection, constraintContext2, type.getIsMarkedNullable());
                return true;
            }

            @Override // org.jetbrains.kotlin.types.checker.TypeCheckingProcedureCallbacks
            public boolean noCorrespondingSupertype(KotlinType subtype, KotlinType supertype) {
                Intrinsics.checkNotNullParameter(subtype, "subtype");
                Intrinsics.checkNotNullParameter(supertype, "supertype");
                this.this$0.getErrors$frontend().add(ConstraintErrorKt.newTypeInferenceOrParameterConstraintError(position));
                return true;
            }
        }));
    }

    private final boolean isErrorOrSpecialType(KotlinType type, ConstraintPosition constraintPosition) {
        if (!TypeUtils.isDontCarePlaceholder(type) && !ErrorUtils.isUninferredTypeVariable(type)) {
            if (type != null && (!KotlinTypeKt.isError(type) || FunctionPlaceholdersKt.isFunctionPlaceholder(type))) {
                return false;
            }
            this.errors.add(new ErrorInConstrainingType(constraintPosition));
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void doAddConstraint(ConstraintKind constraintKind, KotlinType subType, KotlinType superType, ConstraintContext constraintContext, TypeCheckingProcedure typeCheckingProcedure) {
        KotlinType kotlinTypeCreateTypeForFunctionPlaceholder;
        ConstraintPosition position = constraintContext.getPosition();
        if (isErrorOrSpecialType(subType, position) || isErrorOrSpecialType(superType, position) || subType == null || superType == null) {
            return;
        }
        if (constraintContext.getInitialReduction() && ((AnnotationsForResolveUtilsKt.hasExactAnnotation(subType) || AnnotationsForResolveUtilsKt.hasExactAnnotation(superType)) && constraintKind != ConstraintKind.EQUAL)) {
            doAddConstraint(ConstraintKind.EQUAL, subType, superType, constraintContext, typeCheckingProcedure);
            return;
        }
        FunctionPlaceholdersKt.isFunctionPlaceholder(superType);
        if (constraintKind != ConstraintKind.SUB_TYPE || !FunctionPlaceholdersKt.isFunctionPlaceholder(subType)) {
            kotlinTypeCreateTypeForFunctionPlaceholder = subType;
        } else if (isMyTypeVariable$frontend(superType)) {
            return;
        } else {
            kotlinTypeCreateTypeForFunctionPlaceholder = ConstraintSystemBuilderImplKt.createTypeForFunctionPlaceholder(subType, superType);
        }
        if (constraintContext.getInitial()) {
            storeInitialConstraint(constraintKind, subType, superType, position);
        }
        if (AnnotationsForResolveUtilsKt.hasNoInferAnnotation(subType) || AnnotationsForResolveUtilsKt.hasNoInferAnnotation(superType)) {
            return;
        }
        doAddConstraint$simplifyConstraint(this, constraintKind, constraintContext, typeCheckingProcedure, position, kotlinTypeCreateTypeForFunctionPlaceholder, superType);
    }

    private static final void doAddConstraint$simplifyConstraint(ConstraintSystemBuilderImpl constraintSystemBuilderImpl, ConstraintKind constraintKind, ConstraintContext constraintContext, TypeCheckingProcedure typeCheckingProcedure, ConstraintPosition constraintPosition, KotlinType kotlinType, KotlinType kotlinType2) {
        boolean zIsSubtypeOf;
        if (constraintSystemBuilderImpl.isMyTypeVariable$frontend(kotlinType)) {
            constraintSystemBuilderImpl.generateTypeParameterBound(kotlinType, kotlinType2, constraintKind.getBound(), constraintContext);
            return;
        }
        if (constraintSystemBuilderImpl.isMyTypeVariable$frontend(kotlinType2)) {
            constraintSystemBuilderImpl.generateTypeParameterBound(kotlinType2, kotlinType, TypeBoundsKt.reverse(constraintKind.getBound()), constraintContext);
            return;
        }
        KotlinType kotlinTypeSimplifyType = constraintSystemBuilderImpl.simplifyType(kotlinType, constraintContext.getInitial());
        KotlinType kotlinTypeSimplifyType2 = constraintSystemBuilderImpl.simplifyType(kotlinType2, constraintContext.getInitial());
        if (constraintKind == ConstraintKind.EQUAL) {
            zIsSubtypeOf = typeCheckingProcedure.equalTypes(kotlinTypeSimplifyType, kotlinTypeSimplifyType2);
        } else {
            zIsSubtypeOf = typeCheckingProcedure.isSubtypeOf(kotlinTypeSimplifyType, kotlinType2);
        }
        if (zIsSubtypeOf) {
            return;
        }
        constraintSystemBuilderImpl.errors.add(ConstraintErrorKt.newTypeInferenceOrParameterConstraintError(constraintPosition));
    }

    private final KotlinType simplifyType(KotlinType type, boolean isInitialConstraint) {
        if (this.mode == Mode.SPECIFICITY || !isInitialConstraint) {
            return type;
        }
        KotlinType kotlinTypeMakeNotNullable = TypeUtils.makeNotNullable(type);
        Intrinsics.checkNotNull(kotlinTypeMakeNotNullable);
        return kotlinTypeMakeNotNullable;
    }

    public final void addBound$frontend(TypeVariable typeVariable, KotlinType constrainingType, TypeBounds.BoundKind kind, ConstraintContext constraintContext) {
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        Intrinsics.checkNotNullParameter(constrainingType, "constrainingType");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(constraintContext, "constraintContext");
        ConstraintPosition position = constraintContext.getPosition();
        boolean zIsProper = isProper(constrainingType);
        Set<TypeVariable> derivedFrom = constraintContext.getDerivedFrom();
        if (derivedFrom == null) {
            derivedFrom = SetsKt.emptySet();
        }
        TypeBounds.Bound bound = new TypeBounds.Bound(typeVariable, constrainingType, kind, position, zIsProper, derivedFrom);
        TypeBoundsImpl typeBounds$frontend = getTypeBounds$frontend(typeVariable);
        if (typeBounds$frontend.getBounds().contains(bound)) {
            return;
        }
        typeBounds$frontend.addBound(bound);
        if (!bound.getIsProper()) {
            for (TypeVariable typeVariable2 : getNestedTypeVariables$frontend(bound.getConstrainingType())) {
                HashMap<TypeVariable, List<TypeBounds.Bound>> map = this.usedInBounds;
                ArrayList arrayList = map.get(typeVariable2);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    map.put(typeVariable2, arrayList);
                }
                arrayList.add(bound);
            }
        }
        ConstraintIncorporationKt.incorporateBound(this, bound);
    }

    private final void generateTypeParameterBound(KotlinType parameterType, KotlinType constrainingType, TypeBounds.BoundKind boundKind, ConstraintContext constraintContext) {
        CustomTypeParameter customTypeParameter;
        TypeVariable myTypeVariable$frontend = getMyTypeVariable$frontend(parameterType);
        Intrinsics.checkNotNull(myTypeVariable$frontend);
        if (FlexibleTypesKt.isFlexible(parameterType) && (customTypeParameter = TypeCapabilitiesKt.getCustomTypeParameter(parameterType)) != null) {
            constrainingType = customTypeParameter.substitutionResult(constrainingType);
        }
        if (!parameterType.getIsMarkedNullable() || !TypeUtils.isNullableType(constrainingType)) {
            addBound$frontend(myTypeVariable$frontend, constrainingType, boundKind, constraintContext);
            return;
        }
        KotlinType kotlinTypeMakeNotNullable = TypeUtils.makeNotNullable(constrainingType);
        Intrinsics.checkNotNullExpressionValue(kotlinTypeMakeNotNullable, "makeNotNullable(...)");
        if (boundKind == TypeBounds.BoundKind.EXACT_BOUND || boundKind == TypeBounds.BoundKind.LOWER_BOUND) {
            addBound$frontend(myTypeVariable$frontend, kotlinTypeMakeNotNullable, TypeBounds.BoundKind.LOWER_BOUND, constraintContext);
        }
        if (boundKind == TypeBounds.BoundKind.EXACT_BOUND || boundKind == TypeBounds.BoundKind.UPPER_BOUND) {
            addBound$frontend(myTypeVariable$frontend, constrainingType, TypeBounds.BoundKind.UPPER_BOUND, constraintContext);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:7:0x0024  */
    /* JADX WARN: Code duplicated, block: B:9:0x002c  */
    public final void generateTypeParameterCaptureConstraint(TypeVariable typeVariable, TypeProjection constrainingTypeProjection, ConstraintContext constraintContext, boolean isTypeMarkedNullable) {
        List<KotlinType> upperBounds = typeVariable.getOriginalTypeParameter().getUpperBounds();
        if (upperBounds.size() == 1) {
            Intrinsics.checkNotNull(upperBounds);
            Object objSingle = CollectionsKt.single((List<? extends Object>) upperBounds);
            Intrinsics.checkNotNullExpressionValue(objSingle, "single(...)");
            if (!TypeUtilsKt.isDefaultBound((KotlinType) objSingle)) {
                if (constrainingTypeProjection.getProjectionKind() == Variance.IN_VARIANCE) {
                    this.errors.add(new CannotCapture(constraintContext.getPosition(), typeVariable));
                }
            }
        } else if (constrainingTypeProjection.getProjectionKind() == Variance.IN_VARIANCE) {
            this.errors.add(new CannotCapture(constraintContext.getPosition(), typeVariable));
        }
        if (isTypeMarkedNullable) {
            constrainingTypeProjection = new TypeProjectionImpl(constrainingTypeProjection.getProjectionKind(), TypeUtils.makeNotNullable(constrainingTypeProjection.getNullableAnyType()));
        }
        addBound$frontend(typeVariable, CapturedTypeConstructorKt.createCapturedType(constrainingTypeProjection), TypeBounds.BoundKind.EXACT_BOUND, constraintContext);
    }

    public final TypeBoundsImpl getTypeBounds$frontend(TypeVariable variable) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        TypeBoundsImpl typeBoundsImpl = this.allTypeParameterBounds.get(variable);
        if (typeBoundsImpl != null) {
            return typeBoundsImpl;
        }
        throw new IllegalArgumentException("TypeParameterDescriptor is not a type variable for constraint system: " + variable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isMyTypeVariable(TypeParameterDescriptor typeParameter) {
        return getMyTypeVariable(typeParameter) != null;
    }

    public final boolean isMyTypeVariable$frontend(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return getMyTypeVariable$frontend(type) != null;
    }

    public final TypeVariable getMyTypeVariable$frontend(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        ClassifierDescriptor this$0 = type.getConstructor().getThis$0();
        TypeParameterDescriptor typeParameterDescriptor = this$0 instanceof TypeParameterDescriptor ? (TypeParameterDescriptor) this$0 : null;
        if (typeParameterDescriptor == null) {
            return null;
        }
        return getMyTypeVariable(typeParameterDescriptor);
    }

    private final TypeVariable getMyTypeVariable(TypeParameterDescriptor typeParameter) {
        Object next;
        Set<TypeVariable> setKeySet = this.allTypeParameterBounds.keySet();
        Intrinsics.checkNotNullExpressionValue(setKeySet, "<get-keys>(...)");
        Iterator<T> it = setKeySet.iterator();
        while (it.hasNext()) {
            next = it.next();
            if (Intrinsics.areEqual(((TypeVariable) next).getFreshTypeParameter(), typeParameter)) {
                return (TypeVariable) next;
            }
        }
        next = null;
        return (TypeVariable) next;
    }

    private final void storeInitialConstraint(ConstraintKind constraintKind, KotlinType subType, KotlinType superType, ConstraintPosition position) {
        this.initialConstraints.add(new Constraint(constraintKind, subType, superType, position));
    }

    private final void fixVariable(TypeVariable typeVariable) {
        TypeBoundsImpl typeBounds$frontend = getTypeBounds$frontend(typeVariable);
        if (typeBounds$frontend.getIsFixed()) {
            return;
        }
        typeBounds$frontend.setFixed();
        ArrayList<TypeBounds.Bound> bounds = typeBounds$frontend.getBounds();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = bounds.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, getNestedTypeVariables$frontend(((TypeBounds.Bound) it.next()).getConstrainingType()));
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            fixVariable((TypeVariable) it2.next());
        }
        KotlinType value = typeBounds$frontend.getValue();
        if (value == null) {
            return;
        }
        addBound$frontend(typeVariable, value, TypeBounds.BoundKind.EXACT_BOUND, new ConstraintContext(ConstraintPositionKind.FROM_COMPLETER.position(), null, false, false, 14, null));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystem.Builder
    public void add(ConstraintSystem.Builder other) {
        Intrinsics.checkNotNullParameter(other, "other");
        if (!(other instanceof ConstraintSystemBuilderImpl)) {
            throw new IllegalArgumentException("Unknown constraint system builder implementation: " + other);
        }
        ConstraintSystemBuilderImpl constraintSystemBuilderImpl = (ConstraintSystemBuilderImpl) other;
        if (!Collections.disjoint(getTypeVariableSubstitutors().keySet(), constraintSystemBuilderImpl.getTypeVariableSubstitutors().keySet())) {
            throw new IllegalArgumentException("Combining two constraint systems only makes sense when they were created for different calls. Calls of the first system: " + getTypeVariableSubstitutors().keySet() + ", second: " + constraintSystemBuilderImpl.getTypeVariableSubstitutors().keySet());
        }
        if (!Collections.disjoint(constraintSystemBuilderImpl.allTypeParameterBounds.keySet(), this.allTypeParameterBounds.keySet())) {
            throw new IllegalArgumentException("Combining two constraint systems only makes sense when they have no common variables. First system variables: " + this.allTypeParameterBounds.keySet() + ", second: " + constraintSystemBuilderImpl.allTypeParameterBounds.keySet());
        }
        this.allTypeParameterBounds.putAll(constraintSystemBuilderImpl.allTypeParameterBounds);
        this.usedInBounds.putAll(constraintSystemBuilderImpl.usedInBounds);
        this.errors.addAll(constraintSystemBuilderImpl.errors);
        this.initialConstraints.addAll(constraintSystemBuilderImpl.initialConstraints);
        getTypeVariableSubstitutors().putAll(constraintSystemBuilderImpl.getTypeVariableSubstitutors());
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystem.Builder
    public void fixVariables() {
        Set<TypeVariable> setKeySet = this.allTypeParameterBounds.keySet();
        Intrinsics.checkNotNullExpressionValue(setKeySet, "<get-keys>(...)");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : setKeySet) {
            if (((TypeVariable) obj).getIsExternal()) {
                arrayList.add(obj);
            } else {
                arrayList2.add(obj);
            }
        }
        Pair pair = new Pair(arrayList, arrayList2);
        List<TypeVariable> list = (List) pair.component1();
        List<TypeVariable> list2 = (List) pair.component2();
        for (TypeVariable typeVariable : list) {
            Intrinsics.checkNotNull(typeVariable);
            fixVariable(typeVariable);
        }
        for (TypeVariable typeVariable2 : list2) {
            Intrinsics.checkNotNull(typeVariable2);
            fixVariable(typeVariable2);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystem.Builder
    public ConstraintSystem build() {
        return new ConstraintSystemImpl(this.allTypeParameterBounds, this.usedInBounds, this.errors, this.initialConstraints, getTypeVariableSubstitutors());
    }

    /* JADX INFO: compiled from: ConstraintSystemBuilderImpl.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilderImpl$Companion;", "", "()V", "forSpecificity", "Lorg/jetbrains/kotlin/resolve/calls/results/SimpleConstraintSystem;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final SimpleConstraintSystem forSpecificity() {
            return new ConstraintSystemBuilderImpl$Companion$forSpecificity$1(Mode.SPECIFICITY);
        }
    }
}
