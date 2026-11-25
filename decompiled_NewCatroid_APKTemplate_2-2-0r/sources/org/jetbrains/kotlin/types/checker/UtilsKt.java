package org.jetbrains.kotlin.types.checker;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.resolve.calls.inference.CapturedTypeConstructorKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeConstructorSubstitution;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.typesApproximation.CapturedTypeApproximationKt;

/* JADX INFO: compiled from: utils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\u001a\"\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u001a\f\u0010\u0006\u001a\u00020\u0001*\u00020\u0001H\u0002\u001a\f\u0010\u0007\u001a\u00020\b*\u00020\tH\u0002¨\u0006\n"}, d2 = {"findCorrespondingSupertype", "Lorg/jetbrains/kotlin/types/KotlinType;", "subtype", "supertype", "typeCheckingProcedureCallbacks", "Lorg/jetbrains/kotlin/types/checker/TypeCheckingProcedureCallbacks;", "approximate", "debugInfo", "", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UtilsKt {
    public static /* synthetic */ KotlinType findCorrespondingSupertype$default(KotlinType kotlinType, KotlinType kotlinType2, TypeCheckingProcedureCallbacks typeCheckingProcedureCallbacks, int i, Object obj) {
        if ((i & 4) != 0) {
            typeCheckingProcedureCallbacks = new TypeCheckerProcedureCallbacksImpl();
        }
        return findCorrespondingSupertype(kotlinType, kotlinType2, typeCheckingProcedureCallbacks);
    }

    public static final KotlinType findCorrespondingSupertype(KotlinType subtype, KotlinType supertype, TypeCheckingProcedureCallbacks typeCheckingProcedureCallbacks) {
        Intrinsics.checkNotNullParameter(subtype, "subtype");
        Intrinsics.checkNotNullParameter(supertype, "supertype");
        Intrinsics.checkNotNullParameter(typeCheckingProcedureCallbacks, "typeCheckingProcedureCallbacks");
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.add(new SubtypePathNode(subtype, null));
        TypeConstructor constructor = supertype.getConstructor();
        while (!arrayDeque.isEmpty()) {
            SubtypePathNode subtypePathNode = (SubtypePathNode) arrayDeque.poll();
            KotlinType type = subtypePathNode.getType();
            TypeConstructor constructor2 = type.getConstructor();
            if (typeCheckingProcedureCallbacks.assertEqualTypeConstructors(constructor2, constructor)) {
                boolean isMarkedNullable = type.getIsMarkedNullable();
                for (SubtypePathNode previous = subtypePathNode.getPrevious(); previous != null; previous = previous.getPrevious()) {
                    KotlinType type2 = previous.getType();
                    List<TypeProjection> arguments = type2.getArguments();
                    if (!(arguments instanceof Collection) || !arguments.isEmpty()) {
                        Iterator<T> it = arguments.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                type = TypeConstructorSubstitution.INSTANCE.create(type2).buildSubstitutor().safeSubstitute(type, Variance.INVARIANT);
                                Intrinsics.checkNotNull(type);
                                break;
                            }
                            if (((TypeProjection) it.next()).getProjectionKind() != Variance.INVARIANT) {
                                KotlinType kotlinTypeSafeSubstitute = CapturedTypeConstructorKt.wrapWithCapturingSubstitution$default(TypeConstructorSubstitution.INSTANCE.create(type2), false, 1, null).buildSubstitutor().safeSubstitute(type, Variance.INVARIANT);
                                Intrinsics.checkNotNullExpressionValue(kotlinTypeSafeSubstitute, "safeSubstitute(...)");
                                type = approximate(kotlinTypeSafeSubstitute);
                                break;
                            }
                        }
                    } else {
                        type = TypeConstructorSubstitution.INSTANCE.create(type2).buildSubstitutor().safeSubstitute(type, Variance.INVARIANT);
                        Intrinsics.checkNotNull(type);
                        break;
                    }
                    isMarkedNullable = isMarkedNullable || type2.getIsMarkedNullable();
                }
                TypeConstructor constructor3 = type.getConstructor();
                if (typeCheckingProcedureCallbacks.assertEqualTypeConstructors(constructor3, constructor)) {
                    return TypeUtils.makeNullableAsSpecified(type, isMarkedNullable);
                }
                throw new AssertionError("Type constructors should be equals!\nsubstitutedSuperType: " + debugInfo(constructor3) + ", \n\nsupertype: " + debugInfo(constructor) + " \n" + typeCheckingProcedureCallbacks.assertEqualTypeConstructors(constructor3, constructor));
            }
            for (KotlinType kotlinType : constructor2.mo7438getSupertypes()) {
                Intrinsics.checkNotNull(kotlinType);
                arrayDeque.add(new SubtypePathNode(kotlinType, subtypePathNode));
            }
        }
        return null;
    }

    private static final KotlinType approximate(KotlinType kotlinType) {
        return CapturedTypeApproximationKt.approximateCapturedTypes(kotlinType).getUpper();
    }

    private static final String debugInfo(TypeConstructor typeConstructor) {
        StringBuilder sb = new StringBuilder();
        debugInfo$lambda$1$unaryPlus("type: " + typeConstructor, sb);
        debugInfo$lambda$1$unaryPlus("hashCode: " + typeConstructor.hashCode(), sb);
        debugInfo$lambda$1$unaryPlus("javaClass: " + typeConstructor.getClass().getCanonicalName(), sb);
        for (ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = typeConstructor.getThis$0(); classifierDescriptorMo7434getDeclarationDescriptor != null; classifierDescriptorMo7434getDeclarationDescriptor = classifierDescriptorMo7434getDeclarationDescriptor.getContainingDeclaration()) {
            debugInfo$lambda$1$unaryPlus("fqName: " + DescriptorRenderer.FQ_NAMES_IN_TYPES.render(classifierDescriptorMo7434getDeclarationDescriptor), sb);
            debugInfo$lambda$1$unaryPlus("javaClass: " + classifierDescriptorMo7434getDeclarationDescriptor.getClass().getCanonicalName(), sb);
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    private static final StringBuilder debugInfo$lambda$1$unaryPlus(String str, StringBuilder sb) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        StringBuilder sbAppend = sb.append(str);
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        StringBuilder sbAppend2 = sbAppend.append('\n');
        Intrinsics.checkNotNullExpressionValue(sbAppend2, "append(...)");
        return sbAppend2;
    }
}
