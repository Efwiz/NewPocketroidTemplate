package org.jetbrains.kotlin.resolve.multiplatform;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ExpectActualMemberDiff.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u0006\u0012\u0002\b\u00030\u0002¨\u0006\u0003"}, d2 = {"toMemberDiffKind", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualMemberDiff$Kind;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible;", "compiler.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ExpectActualMemberDiffKt {
    public static final ExpectActualMemberDiff.Kind toMemberDiffKind(ExpectActualCompatibility.Incompatible<?> incompatible) {
        Intrinsics.checkNotNullParameter(incompatible, "<this>");
        if (Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.CallableKind.INSTANCE) ? true : Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.ParameterCount.INSTANCE) ? true : Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.ParameterShape.INSTANCE) ? true : Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.ParameterTypes.INSTANCE) ? true : Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.FunctionTypeParameterCount.INSTANCE) ? true : Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.FunctionTypeParameterUpperBounds.INSTANCE)) {
            throw new IllegalStateException(("It's not allowed to call this function with receiver: " + incompatible).toString());
        }
        if (Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.ReturnType.INSTANCE)) {
            return ExpectActualMemberDiff.Kind.ReturnTypeChangedInOverride;
        }
        if (Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.ClassTypeParameterCount.INSTANCE)) {
            throw new IllegalStateException("Not applicable because ExpectActualMemberDiff is about members".toString());
        }
        if (Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.ClassTypeParameterUpperBounds.INSTANCE)) {
            throw new IllegalStateException("Not applicable because ExpectActualMemberDiff is about members".toString());
        }
        if (!Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.ActualFunctionWithDefaultParameters.INSTANCE)) {
            if (Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.ClassKind.INSTANCE)) {
                throw new IllegalStateException("Not applicable because ExpectActualMemberDiff is about members".toString());
            }
            if (Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.ClassModifiers.INSTANCE)) {
                throw new IllegalStateException("Not applicable because ExpectActualMemberDiff is about members".toString());
            }
            if (incompatible instanceof ExpectActualCompatibility.Incompatible.ClassScopes) {
                throw new IllegalStateException("Not applicable because ExpectActualMemberDiff is about members".toString());
            }
            if (Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.EnumEntries.INSTANCE)) {
                throw new IllegalStateException("Not applicable because ExpectActualMemberDiff is about members".toString());
            }
            if (Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.FunInterfaceModifier.INSTANCE)) {
                throw new IllegalStateException("Not applicable because ExpectActualMemberDiff is about members".toString());
            }
            if (!Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.FunctionModifiersDifferent.INSTANCE) && !Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.FunctionModifiersNotSubset.INSTANCE)) {
                if (Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.Modality.INSTANCE)) {
                    return ExpectActualMemberDiff.Kind.ModalityChangedInOverride;
                }
                if (Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.ParameterNames.INSTANCE)) {
                    return ExpectActualMemberDiff.Kind.ParameterNameChangedInOverride;
                }
                if (!Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.PropertyConstModifier.INSTANCE)) {
                    if (Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.PropertyKind.INSTANCE)) {
                        return ExpectActualMemberDiff.Kind.PropertyKindChangedInOverride;
                    }
                    if (Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.PropertyLateinitModifier.INSTANCE)) {
                        return ExpectActualMemberDiff.Kind.LateinitChangedInOverride;
                    }
                    if (Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.PropertySetterVisibility.INSTANCE)) {
                        return ExpectActualMemberDiff.Kind.SetterVisibilityChangedInOverride;
                    }
                    if (Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.Supertypes.INSTANCE)) {
                        throw new IllegalStateException("Not applicable because ExpectActualMemberDiff is about members".toString());
                    }
                    if (Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.TypeParameterNames.INSTANCE)) {
                        return ExpectActualMemberDiff.Kind.TypeParameterNamesChangedInOverride;
                    }
                    if (!Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.TypeParameterReified.INSTANCE) && !Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.TypeParameterVariance.INSTANCE) && !Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.Unknown.INSTANCE) && !Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.ValueParameterCrossinline.INSTANCE) && !Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.ValueParameterNoinline.INSTANCE)) {
                        if (Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.ValueParameterVararg.INSTANCE)) {
                            return ExpectActualMemberDiff.Kind.VarargChangedInOverride;
                        }
                        if (Intrinsics.areEqual(incompatible, ExpectActualCompatibility.Incompatible.Visibility.INSTANCE)) {
                            return ExpectActualMemberDiff.Kind.VisibilityChangedInOverride;
                        }
                        throw new NoWhenBranchMatchedException();
                    }
                }
            }
        }
        return null;
    }
}
