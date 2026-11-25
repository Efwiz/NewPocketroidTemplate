package org.jetbrains.kotlin.builtins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKindExtractor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.annotations.BuiltInAnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.FilteredAnnotations;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.IntValue;
import org.jetbrains.kotlin.resolve.constants.StringValue;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeAttributesKt;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitutionKt;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.utils.DFS;

/* JADX INFO: compiled from: functionTypes.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000v\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\u001a`\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u00022\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00020!2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00020!2\u000e\u0010#\u001a\n\u0012\u0004\u0012\u00020$\u0018\u00010!2\u0006\u0010%\u001a\u00020\u00022\b\b\u0002\u0010&\u001a\u00020\u0006H\u0007\u001a\u001e\u0010'\u001a\u00020(2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u0006\u001aR\u0010,\u001a\b\u0012\u0004\u0012\u00020-0!2\b\u0010\u001f\u001a\u0004\u0018\u00010\u00022\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00020!2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00020!2\u000e\u0010#\u001a\n\u0012\u0004\u0012\u00020$\u0018\u00010!2\u0006\u0010%\u001a\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u001c\u001a\u001e\u0010.\u001a\u00020(2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u0006\u001a\u000e\u0010/\u001a\u00020\u00062\u0006\u00100\u001a\u000201\u001a\u000e\u00102\u001a\u00020\u00062\u0006\u00103\u001a\u000204\u001a\n\u00105\u001a\u00020**\u00020\u0002\u001a\n\u00106\u001a\u00020\u0002*\u00020\u0002\u001a\f\u00107\u001a\u0004\u0018\u00010$*\u00020\u0002\u001a\u0010\u00108\u001a\b\u0012\u0004\u0012\u00020\u00020!*\u00020\u0002\u001a\f\u0010\u0003\u001a\u0004\u0018\u00010\u0001*\u00020\t\u001a\u000e\u0010\u0003\u001a\u0004\u0018\u00010\u0001*\u000204H\u0002\u001a\u0010\u00109\u001a\b\u0012\u0004\u0012\u00020\u00020!*\u00020\u0002\u001a\f\u0010:\u001a\u0004\u0018\u00010\u0002*\u00020\u0002\u001a\n\u0010;\u001a\u00020\u0002*\u00020\u0002\u001a\u0018\u0010<\u001a\b\u0012\u0004\u0012\u00020-0!*\u00020\u00022\u0006\u0010=\u001a\u00020\u0006\u001a\u0010\u0010>\u001a\b\u0012\u0004\u0012\u00020-0!*\u00020\u0002\u001a\n\u0010?\u001a\u00020\u0006*\u00020\u001e\u001a\u001e\u0010\u0011\u001a\u00020\u0006*\u00020\u00022\u0012\u0010@\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060A\u001a \u0010B\u001a\u00020\u0006*\u00020\u00022\u0012\u0010@\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060AH\u0002\u001a\u0012\u0010C\u001a\u00020\u0002*\u00020\u00022\u0006\u0010D\u001a\u00020\u0002\u001a\u001a\u0010E\u001a\u00020\u001e*\u00020\u001e2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010F\u001a\u00020*\u001a\u0012\u0010G\u001a\u00020\u001e*\u00020\u001e2\u0006\u0010\u001b\u001a\u00020\u001c\u001a\n\u0010H\u001a\u00020I*\u00020\u001e\"\u0017\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0007\"\u0015\u0010\b\u001a\u00020\u0006*\u00020\t8F¢\u0006\u0006\u001a\u0004\b\b\u0010\n\"\u0015\u0010\u000b\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0007\"\u0015\u0010\f\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\f\u0010\u0007\"\u0015\u0010\r\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\r\u0010\u0007\"\u0015\u0010\u000e\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0007\"\u0015\u0010\u000f\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0007\"\u0015\u0010\u0010\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0007\"\u0015\u0010\u0011\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0007\"\u0015\u0010\u0012\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0007\"\u0015\u0010\u0013\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0007\"\u0015\u0010\u0014\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0007\"\u0015\u0010\u0015\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0007\"\u0015\u0010\u0016\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0007\"\u0015\u0010\u0017\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0007\"\u0018\u0010\u0018\u001a\u00020\u0006*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0007¨\u0006J"}, d2 = {"functionTypeKind", "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "Lorg/jetbrains/kotlin/types/KotlinType;", "getFunctionTypeKind", "(Lorg/jetbrains/kotlin/types/KotlinType;)Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "isBuiltinExtensionFunctionalType", "", "(Lorg/jetbrains/kotlin/types/KotlinType;)Z", "isBuiltinFunctionalClassDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)Z", "isBuiltinFunctionalType", "isBuiltinFunctionalTypeOrSubtype", "isExtensionFunctionType", "isFunctionOrKFunctionTypeWithAnySuspendability", "isFunctionOrSuspendFunctionType", "isFunctionType", "isFunctionTypeOrSubtype", "isKFunctionType", "isKSuspendFunctionType", "isNonExtensionFunctionType", "isSuspendExtensionFunctionType", "isSuspendFunctionType", "isSuspendFunctionTypeOrSubtype", "isTypeAnnotatedWithExtensionFunctionType", "createFunctionType", "Lorg/jetbrains/kotlin/types/SimpleType;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "receiverType", "contextReceiverTypes", "", "parameterTypes", "parameterNames", "Lorg/jetbrains/kotlin/name/Name;", "returnType", "suspendFunction", "getFunctionDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "parameterCount", "", "isSuspendFunction", "getFunctionTypeArgumentProjections", "Lorg/jetbrains/kotlin/types/TypeProjection;", "getKFunctionDescriptor", "isBuiltinFunctionClass", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "isNumberedFunctionClassFqName", "fqName", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "contextFunctionTypeParamsCount", "extractFunctionalTypeFromSupertypes", "extractParameterNameFromFunctionTypeArgument", "getContextReceiverTypesFromFunctionType", "getPureArgumentsForFunctionalTypeOrSubtype", "getReceiverTypeFromFunctionType", "getReturnTypeFromFunctionType", "getValueParameterTypesFromCallableReflectionType", "isCallableTypeWithExtension", "getValueParameterTypesFromFunctionType", "hasExtensionFunctionAnnotation", "predicate", "Lkotlin/Function1;", "isTypeOrSubtypeOf", "replaceReturnType", "newReturnType", "withContextReceiversFunctionAnnotation", "contextReceiversCount", "withExtensionFunctionAnnotation", "withoutExtensionFunctionAnnotation", "Lorg/jetbrains/kotlin/descriptors/annotations/FilteredAnnotations;", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FunctionTypesKt {
    public static final SimpleType createFunctionType(KotlinBuiltIns builtIns, Annotations annotations, KotlinType kotlinType, List<? extends KotlinType> contextReceiverTypes, List<? extends KotlinType> parameterTypes, List<Name> list, KotlinType returnType) {
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(contextReceiverTypes, "contextReceiverTypes");
        Intrinsics.checkNotNullParameter(parameterTypes, "parameterTypes");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        return createFunctionType$default(builtIns, annotations, kotlinType, contextReceiverTypes, parameterTypes, list, returnType, false, 128, null);
    }

    private static final boolean isTypeOrSubtypeOf(KotlinType kotlinType, final Function1<? super KotlinType, Boolean> function1) {
        if (!function1.invoke(kotlinType).booleanValue()) {
            Object objDfsFromNode = DFS.dfsFromNode(kotlinType, new DFS.Neighbors() { // from class: org.jetbrains.kotlin.builtins.FunctionTypesKt$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
                public final Iterable getNeighbors(Object obj) {
                    return FunctionTypesKt.isTypeOrSubtypeOf$lambda$0((KotlinType) obj);
                }
            }, new DFS.VisitedWithSet(), new DFS.AbstractNodeHandler<KotlinType, Boolean>() { // from class: org.jetbrains.kotlin.builtins.FunctionTypesKt.isTypeOrSubtypeOf.2
                private boolean result;

                @Override // org.jetbrains.kotlin.utils.DFS.AbstractNodeHandler, org.jetbrains.kotlin.utils.DFS.NodeHandler
                public boolean beforeChildren(KotlinType current) {
                    Intrinsics.checkNotNullParameter(current, "current");
                    if (function1.invoke(current).booleanValue()) {
                        this.result = true;
                    }
                    return !this.result;
                }

                @Override // org.jetbrains.kotlin.utils.DFS.NodeHandler
                public Boolean result() {
                    return Boolean.valueOf(this.result);
                }
            });
            Intrinsics.checkNotNullExpressionValue(objDfsFromNode, "dfsFromNode(...)");
            if (!((Boolean) objDfsFromNode).booleanValue()) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable isTypeOrSubtypeOf$lambda$0(KotlinType kotlinType) {
        return kotlinType.getConstructor().mo7438getSupertypes();
    }

    public static final boolean isFunctionTypeOrSubtype(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return isTypeOrSubtypeOf(kotlinType, new Function1<KotlinType, Boolean>() { // from class: org.jetbrains.kotlin.builtins.FunctionTypesKt.isFunctionTypeOrSubtype.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KotlinType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(FunctionTypesKt.isFunctionType(it));
            }
        });
    }

    public static final boolean isSuspendFunctionTypeOrSubtype(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return isTypeOrSubtypeOf(kotlinType, new Function1<KotlinType, Boolean>() { // from class: org.jetbrains.kotlin.builtins.FunctionTypesKt.isSuspendFunctionTypeOrSubtype.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KotlinType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(FunctionTypesKt.isSuspendFunctionType(it));
            }
        });
    }

    public static final boolean isBuiltinFunctionalTypeOrSubtype(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return isTypeOrSubtypeOf(kotlinType, new Function1<KotlinType, Boolean>() { // from class: org.jetbrains.kotlin.builtins.FunctionTypesKt.isBuiltinFunctionalTypeOrSubtype.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KotlinType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(FunctionTypesKt.isBuiltinFunctionalType(it));
            }
        });
    }

    public static final boolean isFunctionTypeOrSubtype(KotlinType kotlinType, final Function1<? super KotlinType, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        return isTypeOrSubtypeOf(kotlinType, new Function1<KotlinType, Boolean>() { // from class: org.jetbrains.kotlin.builtins.FunctionTypesKt.isFunctionTypeOrSubtype.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KotlinType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(FunctionTypesKt.isFunctionType(it) && predicate.invoke(it).booleanValue());
            }
        });
    }

    public static final FunctionTypeKind getFunctionTypeKind(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        if (this$0 != null) {
            return getFunctionTypeKind(this$0);
        }
        return null;
    }

    public static final boolean isFunctionType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return Intrinsics.areEqual(getFunctionTypeKind(kotlinType), FunctionTypeKind.Function.INSTANCE);
    }

    public static final boolean isKFunctionType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return Intrinsics.areEqual(getFunctionTypeKind(kotlinType), FunctionTypeKind.KFunction.INSTANCE);
    }

    public static final boolean isSuspendFunctionType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return Intrinsics.areEqual(getFunctionTypeKind(kotlinType), FunctionTypeKind.SuspendFunction.INSTANCE);
    }

    public static final boolean isKSuspendFunctionType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return Intrinsics.areEqual(getFunctionTypeKind(kotlinType), FunctionTypeKind.KSuspendFunction.INSTANCE);
    }

    public static final boolean isFunctionOrSuspendFunctionType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return isFunctionType(kotlinType) || isSuspendFunctionType(kotlinType);
    }

    public static final boolean isFunctionOrKFunctionTypeWithAnySuspendability(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return isFunctionType(kotlinType) || isSuspendFunctionType(kotlinType) || isKFunctionType(kotlinType) || isKSuspendFunctionType(kotlinType);
    }

    public static final boolean isBuiltinFunctionalType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        return this$0 != null && isBuiltinFunctionalClassDescriptor(this$0);
    }

    public static final boolean isBuiltinFunctionalClassDescriptor(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        FunctionTypeKind functionTypeKind = getFunctionTypeKind(declarationDescriptor);
        return Intrinsics.areEqual(functionTypeKind, FunctionTypeKind.Function.INSTANCE) || Intrinsics.areEqual(functionTypeKind, FunctionTypeKind.SuspendFunction.INSTANCE);
    }

    public static final boolean isBuiltinFunctionClass(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        if (!classId.startsWith(StandardNames.BUILT_INS_PACKAGE_NAME)) {
            return false;
        }
        FqNameUnsafe unsafe = classId.asSingleFqName().toUnsafe();
        Intrinsics.checkNotNullExpressionValue(unsafe, "toUnsafe(...)");
        FunctionTypeKind functionTypeKind = getFunctionTypeKind(unsafe);
        return Intrinsics.areEqual(functionTypeKind, FunctionTypeKind.Function.INSTANCE) || Intrinsics.areEqual(functionTypeKind, FunctionTypeKind.SuspendFunction.INSTANCE);
    }

    public static final boolean isNonExtensionFunctionType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return isFunctionType(kotlinType) && !isTypeAnnotatedWithExtensionFunctionType(kotlinType);
    }

    public static final boolean isExtensionFunctionType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return isFunctionType(kotlinType) && isTypeAnnotatedWithExtensionFunctionType(kotlinType);
    }

    public static final boolean isSuspendExtensionFunctionType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return isSuspendFunctionType(kotlinType) && isTypeAnnotatedWithExtensionFunctionType(kotlinType);
    }

    public static final boolean isBuiltinExtensionFunctionalType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return isBuiltinFunctionalType(kotlinType) && isTypeAnnotatedWithExtensionFunctionType(kotlinType);
    }

    private static final boolean isTypeAnnotatedWithExtensionFunctionType(KotlinType kotlinType) {
        return kotlinType.getAnnotations().mo6752findAnnotation(StandardNames.FqNames.extensionFunctionType) != null;
    }

    public static final boolean isNumberedFunctionClassFqName(FqNameUnsafe fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return fqName.startsWith(StandardNames.BUILT_INS_PACKAGE_NAME) && Intrinsics.areEqual(getFunctionTypeKind(fqName), FunctionTypeKind.Function.INSTANCE);
    }

    public static final FunctionTypeKind getFunctionTypeKind(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        if ((declarationDescriptor instanceof ClassDescriptor) && KotlinBuiltIns.isUnderKotlinPackage(declarationDescriptor)) {
            return getFunctionTypeKind(DescriptorUtilsKt.getFqNameUnsafe(declarationDescriptor));
        }
        return null;
    }

    private static final FunctionTypeKind getFunctionTypeKind(FqNameUnsafe fqNameUnsafe) {
        if (!fqNameUnsafe.isSafe() || fqNameUnsafe.isRoot()) {
            return null;
        }
        FunctionTypeKindExtractor functionTypeKindExtractor = FunctionTypeKindExtractor.INSTANCE.getDefault();
        FqName fqNameParent = fqNameUnsafe.toSafe().parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        String strAsString = fqNameUnsafe.shortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return functionTypeKindExtractor.getFunctionalClassKind(fqNameParent, strAsString);
    }

    public static final int contextFunctionTypeParamsCount(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        AnnotationDescriptor annotationDescriptorMo6752findAnnotation = kotlinType.getAnnotations().mo6752findAnnotation(StandardNames.FqNames.contextFunctionTypeParams);
        if (annotationDescriptorMo6752findAnnotation == null) {
            return 0;
        }
        ConstantValue constantValue = (ConstantValue) MapsKt.getValue(annotationDescriptorMo6752findAnnotation.getAllValueArguments(), StandardNames.CONTEXT_FUNCTION_TYPE_PARAMETER_COUNT_NAME);
        Intrinsics.checkNotNull(constantValue, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.constants.IntValue");
        return ((IntValue) constantValue).getValue().intValue();
    }

    public static final KotlinType getReceiverTypeFromFunctionType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        isBuiltinFunctionalType(kotlinType);
        if (!isTypeAnnotatedWithExtensionFunctionType(kotlinType)) {
            return null;
        }
        return kotlinType.getArguments().get(contextFunctionTypeParamsCount(kotlinType)).getType();
    }

    public static final List<KotlinType> getContextReceiverTypesFromFunctionType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        isBuiltinFunctionalType(kotlinType);
        int iContextFunctionTypeParamsCount = contextFunctionTypeParamsCount(kotlinType);
        if (iContextFunctionTypeParamsCount == 0) {
            return CollectionsKt.emptyList();
        }
        List<TypeProjection> listSubList = kotlinType.getArguments().subList(0, iContextFunctionTypeParamsCount);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSubList, 10));
        Iterator<T> it = listSubList.iterator();
        while (it.hasNext()) {
            KotlinType type = ((TypeProjection) it.next()).getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            arrayList.add(type);
        }
        return arrayList;
    }

    public static final KotlinType getReturnTypeFromFunctionType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        isBuiltinFunctionalType(kotlinType);
        KotlinType type = ((TypeProjection) CollectionsKt.last((List) kotlinType.getArguments())).getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return type;
    }

    public static final KotlinType replaceReturnType(KotlinType kotlinType, KotlinType newReturnType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        Intrinsics.checkNotNullParameter(newReturnType, "newReturnType");
        isBuiltinFunctionalType(kotlinType);
        List mutableList = CollectionsKt.toMutableList((Collection) kotlinType.getArguments());
        mutableList.set(mutableList.size() - 1, new TypeProjectionImpl(newReturnType));
        return TypeSubstitutionKt.replace$default(kotlinType, mutableList, null, null, 6, null);
    }

    public static final List<TypeProjection> getValueParameterTypesFromFunctionType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        isBuiltinFunctionalType(kotlinType);
        List<TypeProjection> arguments = kotlinType.getArguments();
        return arguments.subList(contextFunctionTypeParamsCount(kotlinType) + (isBuiltinExtensionFunctionalType(kotlinType) ? 1 : 0), arguments.size() - 1);
    }

    public static final List<TypeProjection> getValueParameterTypesFromCallableReflectionType(KotlinType kotlinType, boolean z) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        ReflectionTypes.INSTANCE.isKCallableType(kotlinType);
        List<TypeProjection> arguments = kotlinType.getArguments();
        return arguments.subList(z ? 1 : 0, arguments.size() - 1);
    }

    public static final KotlinType extractFunctionalTypeFromSupertypes(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        isBuiltinFunctionalTypeOrSubtype(kotlinType);
        if (isBuiltinFunctionalType(kotlinType)) {
            return kotlinType;
        }
        for (KotlinType kotlinType2 : TypeUtilsKt.supertypes(kotlinType)) {
            if (isBuiltinFunctionalType(kotlinType2)) {
                return kotlinType2;
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    public static final List<KotlinType> getPureArgumentsForFunctionalTypeOrSubtype(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        isBuiltinFunctionalTypeOrSubtype(kotlinType);
        List listDropLast = CollectionsKt.dropLast(extractFunctionalTypeFromSupertypes(kotlinType).getArguments(), 1);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listDropLast, 10));
        Iterator it = listDropLast.iterator();
        while (it.hasNext()) {
            arrayList.add(((TypeProjection) it.next()).getType());
        }
        return arrayList;
    }

    public static final Name extractParameterNameFromFunctionTypeArgument(KotlinType kotlinType) {
        String value;
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        AnnotationDescriptor annotationDescriptorMo6752findAnnotation = kotlinType.getAnnotations().mo6752findAnnotation(StandardNames.FqNames.parameterName);
        if (annotationDescriptorMo6752findAnnotation == null) {
            return null;
        }
        Object objSingleOrNull = CollectionsKt.singleOrNull(annotationDescriptorMo6752findAnnotation.getAllValueArguments().values());
        StringValue stringValue = objSingleOrNull instanceof StringValue ? (StringValue) objSingleOrNull : null;
        if (stringValue != null && (value = stringValue.getValue()) != null) {
            if (!Name.isValidIdentifier(value)) {
                value = null;
            }
            if (value != null) {
                return Name.identifier(value);
            }
        }
        return null;
    }

    public static final List<TypeProjection> getFunctionTypeArgumentProjections(KotlinType kotlinType, List<? extends KotlinType> contextReceiverTypes, List<? extends KotlinType> parameterTypes, List<Name> list, KotlinType returnType, KotlinBuiltIns builtIns) {
        Name name2;
        Intrinsics.checkNotNullParameter(contextReceiverTypes, "contextReceiverTypes");
        Intrinsics.checkNotNullParameter(parameterTypes, "parameterTypes");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        int i = 0;
        ArrayList arrayList = new ArrayList(parameterTypes.size() + contextReceiverTypes.size() + (kotlinType != null ? 1 : 0) + 1);
        List<? extends KotlinType> list2 = contextReceiverTypes;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList2.add(TypeUtilsKt.asTypeProjection((KotlinType) it.next()));
        }
        arrayList.addAll(arrayList2);
        ArrayList arrayList3 = arrayList;
        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList3, kotlinType != null ? TypeUtilsKt.asTypeProjection(kotlinType) : null);
        for (Object obj : parameterTypes) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            KotlinType kotlinTypeReplaceAnnotations = (KotlinType) obj;
            if (list == null || (name2 = list.get(i)) == null || name2.isSpecial()) {
                name2 = null;
            }
            if (name2 != null) {
                FqName fqName = StandardNames.FqNames.parameterName;
                Name name3 = StandardNames.NAME;
                String strAsString = name2.asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                kotlinTypeReplaceAnnotations = TypeUtilsKt.replaceAnnotations(kotlinTypeReplaceAnnotations, Annotations.INSTANCE.create(CollectionsKt.plus(kotlinTypeReplaceAnnotations.getAnnotations(), new BuiltInAnnotationDescriptor(builtIns, fqName, MapsKt.mapOf(TuplesKt.to(name3, new StringValue(strAsString))), false, 8, null))));
            }
            arrayList3.add(TypeUtilsKt.asTypeProjection(kotlinTypeReplaceAnnotations));
            i = i2;
        }
        arrayList.add(TypeUtilsKt.asTypeProjection(returnType));
        return arrayList;
    }

    public static /* synthetic */ SimpleType createFunctionType$default(KotlinBuiltIns kotlinBuiltIns, Annotations annotations, KotlinType kotlinType, List list, List list2, List list3, KotlinType kotlinType2, boolean z, int i, Object obj) {
        return createFunctionType(kotlinBuiltIns, annotations, kotlinType, list, list2, list3, kotlinType2, (i & 128) != 0 ? false : z);
    }

    public static final SimpleType createFunctionType(KotlinBuiltIns builtIns, Annotations annotations, KotlinType kotlinType, List<? extends KotlinType> contextReceiverTypes, List<? extends KotlinType> parameterTypes, List<Name> list, KotlinType returnType, boolean z) {
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(contextReceiverTypes, "contextReceiverTypes");
        Intrinsics.checkNotNullParameter(parameterTypes, "parameterTypes");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        List<TypeProjection> functionTypeArgumentProjections = getFunctionTypeArgumentProjections(kotlinType, contextReceiverTypes, parameterTypes, list, returnType, builtIns);
        ClassDescriptor functionDescriptor = getFunctionDescriptor(builtIns, parameterTypes.size() + contextReceiverTypes.size() + (kotlinType == null ? 0 : 1), z);
        if (kotlinType != null) {
            annotations = withExtensionFunctionAnnotation(annotations, builtIns);
        }
        if (!contextReceiverTypes.isEmpty()) {
            annotations = withContextReceiversFunctionAnnotation(annotations, builtIns, contextReceiverTypes.size());
        }
        return KotlinTypeFactory.simpleNotNullType(TypeAttributesKt.toDefaultAttributes(annotations), functionDescriptor, functionTypeArgumentProjections);
    }

    public static final boolean hasExtensionFunctionAnnotation(Annotations annotations) {
        Intrinsics.checkNotNullParameter(annotations, "<this>");
        return annotations.hasAnnotation(StandardNames.FqNames.extensionFunctionType);
    }

    public static final FilteredAnnotations withoutExtensionFunctionAnnotation(Annotations annotations) {
        Intrinsics.checkNotNullParameter(annotations, "<this>");
        return new FilteredAnnotations(annotations, true, new Function1<FqName, Boolean>() { // from class: org.jetbrains.kotlin.builtins.FunctionTypesKt.withoutExtensionFunctionAnnotation.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(FqName it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(!Intrinsics.areEqual(it, StandardNames.FqNames.extensionFunctionType));
            }
        });
    }

    public static final Annotations withExtensionFunctionAnnotation(Annotations annotations, KotlinBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(annotations, "<this>");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        if (annotations.hasAnnotation(StandardNames.FqNames.extensionFunctionType)) {
            return annotations;
        }
        return Annotations.INSTANCE.create(CollectionsKt.plus(annotations, new BuiltInAnnotationDescriptor(builtIns, StandardNames.FqNames.extensionFunctionType, MapsKt.emptyMap(), false, 8, null)));
    }

    public static final Annotations withContextReceiversFunctionAnnotation(Annotations annotations, KotlinBuiltIns builtIns, int i) {
        Intrinsics.checkNotNullParameter(annotations, "<this>");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        if (annotations.hasAnnotation(StandardNames.FqNames.contextFunctionTypeParams)) {
            return annotations;
        }
        return Annotations.INSTANCE.create(CollectionsKt.plus(annotations, new BuiltInAnnotationDescriptor(builtIns, StandardNames.FqNames.contextFunctionTypeParams, MapsKt.mapOf(TuplesKt.to(StandardNames.CONTEXT_FUNCTION_TYPE_PARAMETER_COUNT_NAME, new IntValue(i))), false, 8, null)));
    }

    public static final ClassDescriptor getFunctionDescriptor(KotlinBuiltIns builtIns, int i, boolean z) {
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        ClassDescriptor suspendFunction = z ? builtIns.getSuspendFunction(i) : builtIns.getFunction(i);
        Intrinsics.checkNotNull(suspendFunction);
        return suspendFunction;
    }

    public static final ClassDescriptor getKFunctionDescriptor(KotlinBuiltIns builtIns, int i, boolean z) {
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        ClassDescriptor kSuspendFunction = z ? builtIns.getKSuspendFunction(i) : builtIns.getKFunction(i);
        Intrinsics.checkNotNull(kSuspendFunction);
        return kSuspendFunction;
    }
}
