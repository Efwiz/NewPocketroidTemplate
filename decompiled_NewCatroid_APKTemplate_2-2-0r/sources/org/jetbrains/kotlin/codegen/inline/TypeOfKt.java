package org.jetbrains.kotlin.codegen.inline;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.reflect.KVariance;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.types.TypeSystemCommonBackendContext;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeVariance;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: typeOf.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\\\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0000\u001a4\u0010\u0005\u001a\u00020\u0006\"\b\b\u0000\u0010\u0007*\u00020\b*\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u00042\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00070\fH\u0002\u001a7\u0010\r\u001a\u00020\u0006\"\b\b\u0000\u0010\u0007*\u00020\b*\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000e\u001a\u0002H\u00072\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00070\f¢\u0006\u0002\u0010\u000f\u001aA\u0010\r\u001a\u00020\u0006\"\b\b\u0000\u0010\u0007*\u00020\b*\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000e\u001a\u0002H\u00072\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00070\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002¢\u0006\u0002\u0010\u0012\u001a<\u0010\u0013\u001a\u00020\u0006\"\b\b\u0000\u0010\u0007*\u00020\b*\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00070\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002\u001a$\u0010\u0016\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u000e\u001a\u00020\b2\u000e\b\u0002\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00040\u0018H\u0002\u001aD\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a*\u00020\n2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u000e\u001a\u00020\u001b2\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u00060 H\u0082\b¢\u0006\u0002\u0010!¨\u0006\""}, d2 = {"createTypeOfMethodBody", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "typeParameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "generateNonReifiedTypeParameter", "", "KT", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "intrinsicsSupport", "Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeInliner$IntrinsicsSupport;", "generateTypeOf", ModuleXmlParser.TYPE, "(Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeInliner$IntrinsicsSupport;)V", "isTypeParameterBound", "", "(Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeInliner$IntrinsicsSupport;Z)V", "generateTypeOfArgument", "projection", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", "typeReferencesParameterWithRecursiveBound", "used", "", "unrollArrayIfFewerThan", "", "Lorg/jetbrains/org/objectweb/asm/Type;", "n", "", "limit", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lkotlin/Function1;", "(Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;IILorg/jetbrains/org/objectweb/asm/Type;Lkotlin/jvm/functions/Function1;)[Lorg/jetbrains/org/objectweb/asm/Type;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeOfKt {

    /* JADX INFO: compiled from: typeOf.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TypeVariance.values().length];
            try {
                iArr[TypeVariance.INV.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TypeVariance.IN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TypeVariance.OUT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final MethodNode createTypeOfMethodBody(TypeSystemCommonBackendContext typeSystemCommonBackendContext, TypeParameterMarker typeParameter) {
        Intrinsics.checkNotNullParameter(typeSystemCommonBackendContext, "<this>");
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        MethodNode methodNode = new MethodNode(589824, 8, "fake", Type.getMethodDescriptor(AsmTypes.K_TYPE, new Type[0]), null, null);
        InstructionAdapter instructionAdapter = new InstructionAdapter(methodNode);
        String strAsString = typeSystemCommonBackendContext.getName(typeParameter).asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        ReifiedTypeInliner.INSTANCE.putReifiedOperationMarker(ReifiedTypeInliner.OperationKind.TYPE_OF, new ReificationArgument(strAsString, false, 0), instructionAdapter);
        instructionAdapter.aconst(null);
        instructionAdapter.areturn(AsmTypes.K_TYPE);
        instructionAdapter.visitMaxs(2, 0);
        return methodNode;
    }

    public static final <KT extends KotlinTypeMarker> void generateTypeOf(TypeSystemCommonBackendContext typeSystemCommonBackendContext, InstructionAdapter v, KT type, ReifiedTypeInliner.IntrinsicsSupport<KT> intrinsicsSupport) {
        Intrinsics.checkNotNullParameter(typeSystemCommonBackendContext, "<this>");
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(intrinsicsSupport, "intrinsicsSupport");
        generateTypeOf(typeSystemCommonBackendContext, v, type, intrinsicsSupport, false);
    }

    private static final <KT extends KotlinTypeMarker> void generateTypeOf(TypeSystemCommonBackendContext typeSystemCommonBackendContext, InstructionAdapter instructionAdapter, KT kt, ReifiedTypeInliner.IntrinsicsSupport<KT> intrinsicsSupport, boolean z) {
        Type[] typeArr;
        Type[] typeArr2;
        TypeParameterMarker typeParameterClassifier = typeSystemCommonBackendContext.getTypeParameterClassifier(typeSystemCommonBackendContext.typeConstructor(kt));
        if (typeParameterClassifier == null) {
            intrinsicsSupport.putClassInstance(instructionAdapter, kt);
            int iArgumentsCount = typeSystemCommonBackendContext.argumentsCount(kt);
            Type K_TYPE_PROJECTION = AsmTypes.K_TYPE_PROJECTION;
            Intrinsics.checkNotNullExpressionValue(K_TYPE_PROJECTION, "K_TYPE_PROJECTION");
            if (iArgumentsCount < 3) {
                typeArr2 = new Type[iArgumentsCount];
                for (int i = 0; i < iArgumentsCount; i++) {
                    generateTypeOfArgument(typeSystemCommonBackendContext, instructionAdapter, typeSystemCommonBackendContext.getArgument(kt, i), intrinsicsSupport, z);
                    Unit unit = Unit.INSTANCE;
                    typeArr2[i] = K_TYPE_PROJECTION;
                }
            } else {
                instructionAdapter.iconst(iArgumentsCount);
                instructionAdapter.newarray(K_TYPE_PROJECTION);
                for (int i2 = 0; i2 < iArgumentsCount; i2++) {
                    instructionAdapter.dup();
                    instructionAdapter.iconst(i2);
                    generateTypeOfArgument(typeSystemCommonBackendContext, instructionAdapter, typeSystemCommonBackendContext.getArgument(kt, i2), intrinsicsSupport, z);
                    instructionAdapter.astore(K_TYPE_PROJECTION);
                }
                Type arrayType = AsmUtil.getArrayType(K_TYPE_PROJECTION);
                Intrinsics.checkNotNullExpressionValue(arrayType, "getArrayType(...)");
                typeArr2 = new Type[]{arrayType};
            }
            SpreadBuilder spreadBuilder = new SpreadBuilder(2);
            spreadBuilder.add(AsmTypes.JAVA_CLASS_TYPE);
            spreadBuilder.addSpread(typeArr2);
            typeArr = (Type[]) spreadBuilder.toArray(new Type[spreadBuilder.size()]);
        } else {
            if (!z && typeSystemCommonBackendContext.isReified(typeParameterClassifier)) {
                String strAsString = typeSystemCommonBackendContext.getName(typeParameterClassifier).asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                ReifiedTypeInliner.INSTANCE.putReifiedOperationMarker(ReifiedTypeInliner.OperationKind.TYPE_OF, new ReificationArgument(strAsString, typeSystemCommonBackendContext.isMarkedNullable(kt), 0), instructionAdapter);
                instructionAdapter.aconst(null);
                return;
            }
            if (typeReferencesParameterWithRecursiveBound$default(typeSystemCommonBackendContext, kt, null, 2, null)) {
                intrinsicsSupport.reportNonReifiedTypeParameterWithRecursiveBoundUnsupported(typeSystemCommonBackendContext.getName(typeParameterClassifier));
                instructionAdapter.aconst(null);
                return;
            } else {
                generateNonReifiedTypeParameter(typeSystemCommonBackendContext, instructionAdapter, typeParameterClassifier, intrinsicsSupport);
                typeArr = new Type[]{AsmTypes.K_CLASSIFIER_TYPE};
            }
        }
        instructionAdapter.invokestatic(AsmTypes.REFLECTION, typeSystemCommonBackendContext.isMarkedNullable(kt) ? "nullableTypeOf" : "typeOf", Type.getMethodDescriptor(AsmTypes.K_TYPE, (Type[]) Arrays.copyOf(typeArr, typeArr.length)), false);
        if (FunctionTypesKt.isSuspendFunctionType(intrinsicsSupport.toKotlinType(kt))) {
            intrinsicsSupport.reportSuspendTypeUnsupported();
        }
        if (intrinsicsSupport.getState().getConfig().getStableTypeOf()) {
            if (intrinsicsSupport.isMutableCollectionType(kt)) {
                instructionAdapter.invokestatic(AsmTypes.REFLECTION, "mutableCollectionType", Type.getMethodDescriptor(AsmTypes.K_TYPE, AsmTypes.K_TYPE), false);
            } else if (typeSystemCommonBackendContext.isNothingConstructor(typeSystemCommonBackendContext.typeConstructor(kt))) {
                instructionAdapter.invokestatic(AsmTypes.REFLECTION, "nothingType", Type.getMethodDescriptor(AsmTypes.K_TYPE, AsmTypes.K_TYPE), false);
            }
            if (typeSystemCommonBackendContext.isFlexible(kt)) {
                SimpleTypeMarker simpleTypeMarkerUpperBoundIfFlexible = typeSystemCommonBackendContext.upperBoundIfFlexible(kt);
                Intrinsics.checkNotNull(simpleTypeMarkerUpperBoundIfFlexible, "null cannot be cast to non-null type KT of org.jetbrains.kotlin.codegen.inline.TypeOfKt.generateTypeOf");
                generateTypeOf(typeSystemCommonBackendContext, instructionAdapter, simpleTypeMarkerUpperBoundIfFlexible, intrinsicsSupport, z);
                instructionAdapter.invokestatic(AsmTypes.REFLECTION, "platformType", Type.getMethodDescriptor(AsmTypes.K_TYPE, AsmTypes.K_TYPE, AsmTypes.K_TYPE), false);
            }
        }
    }

    private static final <KT extends KotlinTypeMarker> void generateNonReifiedTypeParameter(TypeSystemCommonBackendContext typeSystemCommonBackendContext, InstructionAdapter instructionAdapter, TypeParameterMarker typeParameterMarker, ReifiedTypeInliner.IntrinsicsSupport<KT> intrinsicsSupport) {
        KVariance kVariance;
        Type[] typeArr;
        intrinsicsSupport.generateTypeParameterContainer(instructionAdapter, typeParameterMarker);
        instructionAdapter.aconst(typeSystemCommonBackendContext.getName(typeParameterMarker).asString());
        int i = WhenMappings.$EnumSwitchMapping$0[typeSystemCommonBackendContext.getVariance(typeParameterMarker).ordinal()];
        if (i == 1) {
            kVariance = KVariance.INVARIANT;
        } else if (i == 2) {
            kVariance = KVariance.IN;
        } else {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            kVariance = KVariance.OUT;
        }
        instructionAdapter.getstatic(AsmTypes.K_VARIANCE.getInternalName(), kVariance.name(), AsmTypes.K_VARIANCE.getDescriptor());
        instructionAdapter.iconst(typeSystemCommonBackendContext.isReified(typeParameterMarker) ? 1 : 0);
        instructionAdapter.invokestatic(AsmTypes.REFLECTION, "typeParameter", Type.getMethodDescriptor(AsmTypes.K_TYPE_PARAMETER, AsmTypes.OBJECT_TYPE, AsmTypes.JAVA_STRING_TYPE, AsmTypes.K_VARIANCE, Type.BOOLEAN_TYPE), false);
        if (typeSystemCommonBackendContext.upperBoundCount(typeParameterMarker) == 0) {
            return;
        }
        instructionAdapter.dup();
        int iUpperBoundCount = typeSystemCommonBackendContext.upperBoundCount(typeParameterMarker);
        Type K_TYPE = AsmTypes.K_TYPE;
        Intrinsics.checkNotNullExpressionValue(K_TYPE, "K_TYPE");
        if (iUpperBoundCount < 2) {
            typeArr = new Type[iUpperBoundCount];
            for (int i2 = 0; i2 < iUpperBoundCount; i2++) {
                KotlinTypeMarker upperBound = typeSystemCommonBackendContext.getUpperBound(typeParameterMarker, i2);
                Intrinsics.checkNotNull(upperBound, "null cannot be cast to non-null type KT of org.jetbrains.kotlin.codegen.inline.TypeOfKt.generateNonReifiedTypeParameter$lambda$1");
                generateTypeOf(typeSystemCommonBackendContext, instructionAdapter, upperBound, intrinsicsSupport, true);
                Unit unit = Unit.INSTANCE;
                typeArr[i2] = K_TYPE;
            }
        } else {
            instructionAdapter.iconst(iUpperBoundCount);
            instructionAdapter.newarray(K_TYPE);
            for (int i3 = 0; i3 < iUpperBoundCount; i3++) {
                instructionAdapter.dup();
                instructionAdapter.iconst(i3);
                KotlinTypeMarker upperBound2 = typeSystemCommonBackendContext.getUpperBound(typeParameterMarker, i3);
                Intrinsics.checkNotNull(upperBound2, "null cannot be cast to non-null type KT of org.jetbrains.kotlin.codegen.inline.TypeOfKt.generateNonReifiedTypeParameter$lambda$1");
                generateTypeOf(typeSystemCommonBackendContext, instructionAdapter, upperBound2, intrinsicsSupport, true);
                instructionAdapter.astore(K_TYPE);
            }
            Type arrayType = AsmUtil.getArrayType(K_TYPE);
            Intrinsics.checkNotNullExpressionValue(arrayType, "getArrayType(...)");
            typeArr = new Type[]{arrayType};
        }
        Type type = Type.VOID_TYPE;
        SpreadBuilder spreadBuilder = new SpreadBuilder(2);
        spreadBuilder.add(AsmTypes.K_TYPE_PARAMETER);
        spreadBuilder.addSpread(typeArr);
        instructionAdapter.invokestatic(AsmTypes.REFLECTION, "setUpperBounds", Type.getMethodDescriptor(type, (Type[]) spreadBuilder.toArray(new Type[spreadBuilder.size()])), false);
    }

    static /* synthetic */ boolean typeReferencesParameterWithRecursiveBound$default(TypeSystemCommonBackendContext typeSystemCommonBackendContext, KotlinTypeMarker kotlinTypeMarker, Set set, int i, Object obj) {
        if ((i & 2) != 0) {
            set = new LinkedHashSet();
        }
        return typeReferencesParameterWithRecursiveBound(typeSystemCommonBackendContext, kotlinTypeMarker, set);
    }

    private static final boolean typeReferencesParameterWithRecursiveBound(TypeSystemCommonBackendContext typeSystemCommonBackendContext, KotlinTypeMarker kotlinTypeMarker, Set<TypeParameterMarker> set) {
        TypeParameterMarker typeParameterClassifier = typeSystemCommonBackendContext.getTypeParameterClassifier(typeSystemCommonBackendContext.typeConstructor(kotlinTypeMarker));
        if (typeParameterClassifier != null) {
            if (!set.add(typeParameterClassifier)) {
                return true;
            }
            int iUpperBoundCount = typeSystemCommonBackendContext.upperBoundCount(typeParameterClassifier);
            for (int i = 0; i < iUpperBoundCount; i++) {
                if (typeReferencesParameterWithRecursiveBound(typeSystemCommonBackendContext, typeSystemCommonBackendContext.getUpperBound(typeParameterClassifier, i), set)) {
                    return true;
                }
            }
            set.remove(typeParameterClassifier);
        } else {
            int iArgumentsCount = typeSystemCommonBackendContext.argumentsCount(kotlinTypeMarker);
            for (int i2 = 0; i2 < iArgumentsCount; i2++) {
                TypeArgumentMarker argument = typeSystemCommonBackendContext.getArgument(kotlinTypeMarker, i2);
                if (!typeSystemCommonBackendContext.isStarProjection(argument) && typeReferencesParameterWithRecursiveBound(typeSystemCommonBackendContext, typeSystemCommonBackendContext.getType(argument), set)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static final <KT extends KotlinTypeMarker> void generateTypeOfArgument(TypeSystemCommonBackendContext typeSystemCommonBackendContext, InstructionAdapter instructionAdapter, TypeArgumentMarker typeArgumentMarker, ReifiedTypeInliner.IntrinsicsSupport<KT> intrinsicsSupport, boolean z) {
        String str;
        instructionAdapter.getstatic(AsmTypes.K_TYPE_PROJECTION.getInternalName(), "Companion", AsmTypes.K_TYPE_PROJECTION_COMPANION.getDescriptor());
        if (typeSystemCommonBackendContext.isStarProjection(typeArgumentMarker)) {
            instructionAdapter.invokevirtual(AsmTypes.K_TYPE_PROJECTION_COMPANION.getInternalName(), "getSTAR", Type.getMethodDescriptor(AsmTypes.K_TYPE_PROJECTION, new Type[0]), false);
            return;
        }
        KotlinTypeMarker type = typeSystemCommonBackendContext.getType(typeArgumentMarker);
        Intrinsics.checkNotNull(type, "null cannot be cast to non-null type KT of org.jetbrains.kotlin.codegen.inline.TypeOfKt.generateTypeOfArgument");
        generateTypeOf(typeSystemCommonBackendContext, instructionAdapter, type, intrinsicsSupport, z);
        int i = WhenMappings.$EnumSwitchMapping$0[typeSystemCommonBackendContext.getVariance(typeArgumentMarker).ordinal()];
        if (i == 1) {
            str = "invariant";
        } else if (i == 2) {
            str = "contravariant";
        } else {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            str = "covariant";
        }
        instructionAdapter.invokevirtual(AsmTypes.K_TYPE_PROJECTION_COMPANION.getInternalName(), str, Type.getMethodDescriptor(AsmTypes.K_TYPE_PROJECTION, AsmTypes.K_TYPE), false);
    }
}
