package org.jetbrains.kotlin.resolve.calls.inference.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext;
import org.jetbrains.kotlin.types.model.DefinitelyNotNullTypeMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;
import org.jetbrains.kotlin.utils.SmartList;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;

/* JADX INFO: compiled from: MutableConstraintStorage.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001b\b\u0016\u0012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007B\u001b\b\u0016\u0012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004\u0012\u0006\u0010\b\u001a\u00020\u0001¢\u0006\u0002\u0010\tB+\b\u0002\u0012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u000e\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b¢\u0006\u0002\u0010\rJ\u001a\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u001c0\u001b2\u0006\u0010\u001d\u001a\u00020\fJ \u0010\u001e\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020!0\u001b0\u001f2\u0006\u0010\"\u001a\u00020#J*\u0010$\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\f2\u0018\u0010%\u001a\u0014\u0012\u0004\u0012\u00020\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0&H\u0002J\u0018\u0010'\u001a\u00020\u001c2\u0006\u0010(\u001a\u00020\f2\u0006\u0010)\u001a\u00020\fH\u0002J!\u0010*\u001a\u00020+2\u0012\u0010,\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u001c0-H\u0000¢\u0006\u0002\b.J\u0015\u0010/\u001a\u00020+2\u0006\u00100\u001a\u00020\u0014H\u0000¢\u0006\u0002\b1J\b\u00102\u001a\u000203H\u0016J\f\u00104\u001a\u00020\u001c*\u00020\fH\u0002J\u0014\u00105\u001a\u00020\u001c*\u00020\f2\u0006\u0010\b\u001a\u00020\fH\u0002J\u0018\u00106\u001a\b\u0012\u0004\u0012\u00020\f0\u0011*\b\u0012\u0004\u0012\u00020\f0\u0011H\u0002J\u0018\u00107\u001a\b\u0012\u0004\u0012\u00020\f0\u0011*\b\u0012\u0004\u0012\u00020\f0\u0011H\u0002J\u0018\u00108\u001a\b\u0012\u0004\u0012\u00020\f0\u0011*\b\u0012\u0004\u0012\u00020\f0\u0011H\u0002R\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0012\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0010\u001a\u0010\u0012\f\u0012\n \u0012*\u0004\u0018\u00010\f0\f0\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0013\u001a\u00020\u00148F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0016\u0010\u0017\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019¨\u00069"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/MutableVariableWithConstraints;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/VariableWithConstraints;", "context", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/Context;", "typeVariable", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "(Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;)V", "other", "(Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;Lorg/jetbrains/kotlin/resolve/calls/inference/model/VariableWithConstraints;)V", "constraints", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/Constraint;", "(Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;Ljava/util/List;)V", "getConstraints", "()Ljava/util/List;", "mutableConstraints", "Lorg/jetbrains/kotlin/utils/SmartList;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "rawConstraintsCount", "", "getRawConstraintsCount", "()I", "simplifiedConstraints", "getTypeVariable", "()Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "addConstraint", "Lkotlin/Pair;", "", "constraint", "getProjectedInputCallTypes", "", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintKind;", "utilContext", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemUtilContext;", "isUsefulConstraint", "equalityConstraints", "", "newConstraintIsUseless", "old", PsiKeyword.NEW, "removeConstrains", "", "shouldRemove", "Lkotlin/Function1;", "removeConstrains$resolution_common", "removeLastConstraints", "sinceIndex", "removeLastConstraints$resolution_common", "toString", "", "isLowerAndFlexibleTypeWithDefNotNullLowerBound", "isStrongerThanLowerAndFlexibleTypeWithDefNotNullLowerBound", "simplifyConstraints", "simplifyEqualityConstraints", "simplifyLowerConstraints", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MutableVariableWithConstraints implements VariableWithConstraints {
    private final TypeSystemInferenceExtensionContext context;
    private final SmartList<Constraint> mutableConstraints;
    private SmartList<Constraint> simplifiedConstraints;
    private final TypeVariableMarker typeVariable;

    /* JADX INFO: compiled from: MutableConstraintStorage.kt */
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

    private MutableVariableWithConstraints(TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext, TypeVariableMarker typeVariableMarker, List<Constraint> list) {
        this.context = typeSystemInferenceExtensionContext;
        this.typeVariable = typeVariableMarker;
        SmartList<Constraint> smartList = list == null ? new SmartList<>() : new SmartList<>((Collection) list);
        this.mutableConstraints = smartList;
        this.simplifiedConstraints = smartList;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.model.VariableWithConstraints
    public TypeVariableMarker getTypeVariable() {
        return this.typeVariable;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public MutableVariableWithConstraints(TypeSystemInferenceExtensionContext context, TypeVariableMarker typeVariable) {
        this(context, typeVariable, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public MutableVariableWithConstraints(TypeSystemInferenceExtensionContext context, VariableWithConstraints other) {
        this(context, other.getTypeVariable(), other.getConstraints());
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(other, "other");
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.model.VariableWithConstraints
    public List<Constraint> getConstraints() {
        if (this.simplifiedConstraints == null) {
            this.simplifiedConstraints = simplifyConstraints(this.mutableConstraints);
        }
        SmartList<Constraint> smartList = this.simplifiedConstraints;
        Intrinsics.checkNotNull(smartList);
        return smartList;
    }

    public final Collection<Pair<KotlinTypeMarker, ConstraintKind>> getProjectedInputCallTypes(ConstraintSystemUtilContext utilContext) {
        Intrinsics.checkNotNullParameter(utilContext, "utilContext");
        SmartList<Constraint> smartList = this.mutableConstraints;
        SmartList smartList2 = new SmartList();
        for (Constraint constraint : smartList) {
            Pair pair = ((constraint.getPosition().getFrom() instanceof OnlyInputTypeConstraintPosition) || constraint.getInputTypePositionBeforeIncorporation() != null) ? TuplesKt.to(utilContext.unCapture(constraint.getType()), constraint.getKind()) : null;
            if (pair != null) {
                smartList2.add(pair);
            }
        }
        return smartList2;
    }

    public final int getRawConstraintsCount() {
        return this.mutableConstraints.size();
    }

    public final Pair<Constraint, Boolean> addConstraint(Constraint constraint) {
        boolean zIsUpper;
        Constraint constraint2 = constraint;
        Intrinsics.checkNotNullParameter(constraint2, "constraint");
        boolean zIsLowerAndFlexibleTypeWithDefNotNullLowerBound = isLowerAndFlexibleTypeWithDefNotNullLowerBound(constraint);
        for (Constraint constraint3 : getConstraints()) {
            if (constraint3.getTypeHashCode() == constraint.getTypeHashCode() && Intrinsics.areEqual(constraint3.getType(), constraint.getType()) && constraint3.getIsNullabilityConstraint() == constraint.getIsNullabilityConstraint()) {
                TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext = this.context;
                KotlinTypeMarker type = constraint3.getType();
                KotlinTypeMarker type2 = constraint.getType();
                boolean z = !(typeSystemInferenceExtensionContext.hasCustomAttributes(type) || typeSystemInferenceExtensionContext.hasCustomAttributes(type2)) || Intrinsics.areEqual(typeSystemInferenceExtensionContext.getCustomAttributes(type), typeSystemInferenceExtensionContext.getCustomAttributes(type2));
                if (newConstraintIsUseless(constraint3, constraint2) && z) {
                    return TuplesKt.to(constraint3, false);
                }
                int i = WhenMappings.$EnumSwitchMapping$0[constraint3.getKind().ordinal()];
                if (i == 1) {
                    zIsUpper = constraint.getKind().isUpper();
                } else if (i == 2) {
                    zIsUpper = constraint.getKind().isLower();
                } else {
                    if (i != 3) {
                        throw new NoWhenBranchMatchedException();
                    }
                    zIsUpper = true;
                }
                if (zIsUpper && z) {
                    if (constraint.getKind() != ConstraintKind.EQUALITY) {
                        ConstraintKind constraintKind = ConstraintKind.EQUALITY;
                        KotlinTypeMarker type3 = constraint.getType();
                        IncorporationConstraintPosition position = constraint.getPosition();
                        if (position.getFrom() instanceof DeclaredUpperBoundConstraintPosition) {
                            position = null;
                        }
                        if (position == null) {
                            position = constraint3.getPosition();
                        }
                        constraint2 = new Constraint(constraintKind, type3, position, constraint.getTypeHashCode(), constraint.getDerivedFrom(), false, null, 64, null);
                    }
                    this.mutableConstraints.add(constraint2);
                    this.simplifiedConstraints = null;
                    return TuplesKt.to(constraint2, true);
                }
            }
            if (zIsLowerAndFlexibleTypeWithDefNotNullLowerBound && isStrongerThanLowerAndFlexibleTypeWithDefNotNullLowerBound(constraint3, constraint2)) {
                return TuplesKt.to(constraint3, false);
            }
        }
        this.mutableConstraints.add(constraint2);
        SmartList<Constraint> smartList = this.simplifiedConstraints;
        if (smartList != null && smartList != this.mutableConstraints) {
            Intrinsics.checkNotNull(smartList);
            smartList.add(constraint2);
        }
        if (this.simplifiedConstraints != null && zIsLowerAndFlexibleTypeWithDefNotNullLowerBound) {
            this.simplifiedConstraints = null;
        }
        return TuplesKt.to(constraint2, true);
    }

    public final void removeLastConstraints$resolution_common(int sinceIndex) {
        AddToStdlibKt.trimToSize(this.mutableConstraints, sinceIndex);
        if (this.simplifiedConstraints != this.mutableConstraints) {
            this.simplifiedConstraints = null;
        }
    }

    public final void removeConstrains$resolution_common(Function1<? super Constraint, Boolean> shouldRemove) {
        Intrinsics.checkNotNullParameter(shouldRemove, "shouldRemove");
        CollectionsKt.removeAll((List) this.mutableConstraints, (Function1) shouldRemove);
        if (this.simplifiedConstraints != this.mutableConstraints) {
            this.simplifiedConstraints = null;
        }
    }

    private final boolean newConstraintIsUseless(Constraint old, Constraint constraint) {
        if ((old.getPosition().getFrom() instanceof DeclaredUpperBoundConstraintPosition) && !(constraint.getPosition().getFrom() instanceof DeclaredUpperBoundConstraintPosition)) {
            return false;
        }
        if ((old.getPosition().getFrom() instanceof ExpectedTypeConstraintPosition) && !(constraint.getPosition().getFrom() instanceof ExpectedTypeConstraintPosition) && old.getKind().isUpper() && constraint.getKind().isUpper()) {
            return false;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[old.getKind().ordinal()];
        if (i == 1) {
            return constraint.getKind().isLower();
        }
        if (i == 2) {
            return constraint.getKind().isUpper();
        }
        if (i == 3) {
            return true;
        }
        throw new NoWhenBranchMatchedException();
    }

    private final SmartList<Constraint> simplifyConstraints(SmartList<Constraint> smartList) {
        return simplifyEqualityConstraints(simplifyLowerConstraints(smartList));
    }

    private final SmartList<Constraint> simplifyLowerConstraints(SmartList<Constraint> smartList) {
        Constraint next;
        SmartList<Constraint> smartList2 = new SmartList<>();
        for (Constraint constraint : smartList) {
            Intrinsics.checkNotNull(constraint);
            if (!isLowerAndFlexibleTypeWithDefNotNullLowerBound(constraint)) {
                smartList2.add(constraint);
            } else {
                SmartList<Constraint> smartList3 = smartList;
                if (!(smartList3 instanceof Collection) || !smartList3.isEmpty()) {
                    Iterator<Constraint> it = smartList3.iterator();
                    do {
                        if (it.hasNext()) {
                            next = it.next();
                            Intrinsics.checkNotNull(next);
                        }
                    } while (!isStrongerThanLowerAndFlexibleTypeWithDefNotNullLowerBound(next, constraint));
                }
                smartList2.add(constraint);
                break;
            }
        }
        return smartList2;
    }

    private final boolean isLowerAndFlexibleTypeWithDefNotNullLowerBound(Constraint constraint) {
        TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext = this.context;
        return constraint.getKind() == ConstraintKind.LOWER && typeSystemInferenceExtensionContext.isFlexible(constraint.getType()) && typeSystemInferenceExtensionContext.isDefinitelyNotNullType(typeSystemInferenceExtensionContext.lowerBoundIfFlexible(constraint.getType()));
    }

    private final boolean isStrongerThanLowerAndFlexibleTypeWithDefNotNullLowerBound(Constraint constraint, Constraint constraint2) {
        if (constraint != constraint2 && constraint.getTypeHashCode() == constraint2.getTypeHashCode() && constraint.getKind() != ConstraintKind.UPPER) {
            TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext = this.context;
            if (typeSystemInferenceExtensionContext.isFlexible(constraint.getType()) && typeSystemInferenceExtensionContext.isFlexible(constraint2.getType())) {
                SimpleTypeMarker simpleTypeMarkerLowerBoundIfFlexible = typeSystemInferenceExtensionContext.lowerBoundIfFlexible(constraint2.getType());
                if (!typeSystemInferenceExtensionContext.isDefinitelyNotNullType(simpleTypeMarkerLowerBoundIfFlexible)) {
                    return false;
                }
                if (simpleTypeMarkerLowerBoundIfFlexible instanceof DefinitelyNotNullTypeMarker) {
                    return Intrinsics.areEqual(typeSystemInferenceExtensionContext.lowerBoundIfFlexible(constraint.getType()), typeSystemInferenceExtensionContext.original((DefinitelyNotNullTypeMarker) simpleTypeMarkerLowerBoundIfFlexible)) && Intrinsics.areEqual(typeSystemInferenceExtensionContext.upperBoundIfFlexible(constraint.getType()), typeSystemInferenceExtensionContext.upperBoundIfFlexible(constraint2.getType()));
                }
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
        }
        return false;
    }

    private final SmartList<Constraint> simplifyEqualityConstraints(SmartList<Constraint> smartList) {
        SmartList<Constraint> smartList2 = smartList;
        ArrayList arrayList = new ArrayList();
        for (Constraint constraint : smartList2) {
            if (constraint.getKind() == ConstraintKind.EQUALITY) {
                arrayList.add(constraint);
            }
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : arrayList) {
            Integer numValueOf = Integer.valueOf(((Constraint) obj).getTypeHashCode());
            Object obj2 = linkedHashMap.get(numValueOf);
            if (obj2 == null) {
                obj2 = (List) new ArrayList();
                linkedHashMap.put(numValueOf, obj2);
            }
            ((List) obj2).add(obj);
        }
        if (linkedHashMap.isEmpty()) {
            return smartList;
        }
        SmartList<Constraint> smartList3 = new SmartList<>();
        for (Constraint constraint2 : smartList2) {
            Constraint constraint3 = constraint2;
            Intrinsics.checkNotNull(constraint3);
            if (isUsefulConstraint(constraint3, linkedHashMap)) {
                smartList3.add(constraint2);
            }
        }
        return smartList3;
    }

    private final boolean isUsefulConstraint(Constraint constraint, Map<Integer, ? extends List<Constraint>> equalityConstraints) {
        List<Constraint> list;
        if (constraint.getKind() == ConstraintKind.EQUALITY || (list = equalityConstraints.get(Integer.valueOf(constraint.getTypeHashCode()))) == null) {
            return true;
        }
        List<Constraint> list2 = list;
        if ((list2 instanceof Collection) && list2.isEmpty()) {
            return true;
        }
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(((Constraint) it.next()).getType(), constraint.getType())) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        return "Constraints for " + getTypeVariable();
    }
}
