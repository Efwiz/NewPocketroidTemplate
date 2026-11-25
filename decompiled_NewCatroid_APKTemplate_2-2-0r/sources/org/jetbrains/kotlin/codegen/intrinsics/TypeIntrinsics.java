package org.jetbrains.kotlin.codegen.intrinsics;

import com.android.dx.rop.code.RegisterSpec;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import com.reandroid.dex.sections.Marker;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.MatchGroup;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.InsnNode;
import org.jetbrains.org.objectweb.asm.tree.IntInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LdcInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodInsnNode;
import org.jetbrains.org.objectweb.asm.tree.TypeInsnNode;

/* JADX INFO: compiled from: TypeIntrinsics.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u001bH\u0007J\u0010\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u0012H\u0002J\u0012\u0010\u001d\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0012\u0010\u001e\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0018\u0010!\u001a\u00020 2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\"\u001a\u00020\fH\u0002J\u0012\u0010#\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u001a\u0010$\u001a\u0004\u0018\u00010\u00042\u0006\u0010%\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0010\u0010&\u001a\u00020 2\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0010\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020 H\u0002J \u0010*\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010+\u001a\u00020\u0012H\u0007J(\u0010*\u001a\u00020\u00142\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0012H\u0007J\u0018\u00100\u001a\u0002012\u0006\u00102\u001a\u00020\u00042\u0006\u00103\u001a\u00020\u0004H\u0002J\u001c\u00104\u001a\u00020\u0014*\u00020\u00162\u0006\u00102\u001a\u00020\u00042\u0006\u00103\u001a\u00020\u0004H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n \u0006*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n \u0006*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n \u0006*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0011\u001a\n \u0006*\u0004\u0018\u00010\u00120\u0012X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00065"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/TypeIntrinsics;", "", "()V", "BEFORE_CHECKCAST_TO_FUNCTION_OF_ARITY", "", "BEFORE_CHECKCAST_TO_FUNCTION_OF_ARITY_DESCRIPTOR", JvmProtoBufUtil.PLATFORM_TYPE_ID, "INTRINSICS_CLASS", "IS_FUNCTON_OF_ARITY_DESCRIPTOR", "IS_FUNCTON_OF_ARITY_METHOD_NAME", "IS_MUTABLE_COLLECTION_METHOD_DESCRIPTOR", "KOTLIN_FUNCTION_INTERFACE_REGEX", "Lkotlin/text/Regex;", "KOTLIN_SUSPEND_FUNCTION_INTERFACE_REGEX", "MUTABLE_COLLECTION_TYPE_FQ_NAMES", "", "Lorg/jetbrains/kotlin/name/FqName;", "OBJECT_TYPE", "Lorg/jetbrains/org/objectweb/asm/Type;", "checkcast", "", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "asmType", "safe", "", "getAsMutableCollectionDescriptor", "getAsMutableCollectionMethodName", "getClassFqName", "getFunctionTypeArity", "", "getFunctionTypeArityByRegex", "regex", "getIsMutableCollectionMethodName", "getMutableCollectionMethodName", "prefix", "getSuspendFunctionTypeArity", "iconstNode", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "value", "instanceOf", "boxedAsmType", "instanceofInsn", "Lorg/jetbrains/org/objectweb/asm/tree/TypeInsnNode;", "instructions", "Lorg/jetbrains/org/objectweb/asm/tree/InsnList;", "typeIntrinsicNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodInsnNode;", "methodName", "methodDescriptor", "typeIntrinsic", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeIntrinsics {
    private static final String BEFORE_CHECKCAST_TO_FUNCTION_OF_ARITY;
    private static final String BEFORE_CHECKCAST_TO_FUNCTION_OF_ARITY_DESCRIPTOR;
    private static final Type OBJECT_TYPE;
    public static final TypeIntrinsics INSTANCE = new TypeIntrinsics();
    private static final String INTRINSICS_CLASS = "kotlin/jvm/internal/TypeIntrinsics";
    private static final String IS_FUNCTON_OF_ARITY_METHOD_NAME = "isFunctionOfArity";
    private static final String IS_FUNCTON_OF_ARITY_DESCRIPTOR = Type.getMethodDescriptor(Type.BOOLEAN_TYPE, Type.getObjectType(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME), Type.INT_TYPE);
    private static final Set<FqName> MUTABLE_COLLECTION_TYPE_FQ_NAMES = SetsKt.setOf((Object[]) new FqName[]{StandardNames.FqNames.mutableIterator, StandardNames.FqNames.mutableIterable, StandardNames.FqNames.mutableCollection, StandardNames.FqNames.mutableList, StandardNames.FqNames.mutableListIterator, StandardNames.FqNames.mutableMap, StandardNames.FqNames.mutableSet, StandardNames.FqNames.mutableMapEntry});
    private static final String IS_MUTABLE_COLLECTION_METHOD_DESCRIPTOR = Type.getMethodDescriptor(Type.BOOLEAN_TYPE, Type.getObjectType(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME));
    private static final Regex KOTLIN_FUNCTION_INTERFACE_REGEX = new Regex("^kotlin\\.Function([0-9]+)$");
    private static final Regex KOTLIN_SUSPEND_FUNCTION_INTERFACE_REGEX = new Regex("^kotlin\\.coroutines\\.SuspendFunction([0-9]+)$");

    private TypeIntrinsics() {
    }

    @JvmStatic
    public static final void instanceOf(InstructionAdapter v, KotlinType kotlinType, Type boxedAsmType) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
        Intrinsics.checkNotNullParameter(boxedAsmType, "boxedAsmType");
        TypeIntrinsics typeIntrinsics = INSTANCE;
        int functionTypeArity = typeIntrinsics.getFunctionTypeArity(kotlinType);
        if (functionTypeArity >= 0) {
            v.iconst(functionTypeArity);
            String str = IS_FUNCTON_OF_ARITY_METHOD_NAME;
            String IS_FUNCTON_OF_ARITY_DESCRIPTOR2 = IS_FUNCTON_OF_ARITY_DESCRIPTOR;
            Intrinsics.checkNotNullExpressionValue(IS_FUNCTON_OF_ARITY_DESCRIPTOR2, "IS_FUNCTON_OF_ARITY_DESCRIPTOR");
            typeIntrinsics.typeIntrinsic(v, str, IS_FUNCTON_OF_ARITY_DESCRIPTOR2);
            return;
        }
        int suspendFunctionTypeArity = typeIntrinsics.getSuspendFunctionTypeArity(kotlinType);
        if (suspendFunctionTypeArity >= 0) {
            Label label = new Label();
            Label label2 = new Label();
            v.dup();
            v.instanceOf(AsmTypes.SUSPEND_FUNCTION_TYPE);
            v.ifeq(label);
            v.iconst(suspendFunctionTypeArity + 1);
            String str2 = IS_FUNCTON_OF_ARITY_METHOD_NAME;
            String IS_FUNCTON_OF_ARITY_DESCRIPTOR3 = IS_FUNCTON_OF_ARITY_DESCRIPTOR;
            Intrinsics.checkNotNullExpressionValue(IS_FUNCTON_OF_ARITY_DESCRIPTOR3, "IS_FUNCTON_OF_ARITY_DESCRIPTOR");
            typeIntrinsics.typeIntrinsic(v, str2, IS_FUNCTON_OF_ARITY_DESCRIPTOR3);
            v.goTo(label2);
            v.mark(label);
            v.pop();
            v.iconst(0);
            v.mark(label2);
            return;
        }
        String isMutableCollectionMethodName = typeIntrinsics.getIsMutableCollectionMethodName(kotlinType);
        if (isMutableCollectionMethodName != null) {
            String IS_MUTABLE_COLLECTION_METHOD_DESCRIPTOR2 = IS_MUTABLE_COLLECTION_METHOD_DESCRIPTOR;
            Intrinsics.checkNotNullExpressionValue(IS_MUTABLE_COLLECTION_METHOD_DESCRIPTOR2, "IS_MUTABLE_COLLECTION_METHOD_DESCRIPTOR");
            typeIntrinsics.typeIntrinsic(v, isMutableCollectionMethodName, IS_MUTABLE_COLLECTION_METHOD_DESCRIPTOR2);
            return;
        }
        v.instanceOf(boxedAsmType);
    }

    private final AbstractInsnNode iconstNode(int value) {
        if (value >= -1 && value <= 5) {
            return new InsnNode(value + 3);
        }
        if (value >= -128 && value <= 127) {
            return new IntInsnNode(16, value);
        }
        if (value >= -32768 && value <= 32767) {
            return new IntInsnNode(17, value);
        }
        return new LdcInsnNode(new Integer(value));
    }

    @JvmStatic
    public static final void instanceOf(TypeInsnNode instanceofInsn, InsnList instructions, KotlinType kotlinType, Type asmType) {
        Intrinsics.checkNotNullParameter(instanceofInsn, "instanceofInsn");
        Intrinsics.checkNotNullParameter(instructions, "instructions");
        Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
        Intrinsics.checkNotNullParameter(asmType, "asmType");
        TypeIntrinsics typeIntrinsics = INSTANCE;
        int functionTypeArity = typeIntrinsics.getFunctionTypeArity(kotlinType);
        if (functionTypeArity >= 0) {
            TypeInsnNode typeInsnNode = instanceofInsn;
            instructions.insertBefore(typeInsnNode, typeIntrinsics.iconstNode(functionTypeArity));
            String str = IS_FUNCTON_OF_ARITY_METHOD_NAME;
            String IS_FUNCTON_OF_ARITY_DESCRIPTOR2 = IS_FUNCTON_OF_ARITY_DESCRIPTOR;
            Intrinsics.checkNotNullExpressionValue(IS_FUNCTON_OF_ARITY_DESCRIPTOR2, "IS_FUNCTON_OF_ARITY_DESCRIPTOR");
            instructions.insertBefore(typeInsnNode, typeIntrinsics.typeIntrinsicNode(str, IS_FUNCTON_OF_ARITY_DESCRIPTOR2));
            instructions.remove(typeInsnNode);
            return;
        }
        String isMutableCollectionMethodName = typeIntrinsics.getIsMutableCollectionMethodName(kotlinType);
        if (isMutableCollectionMethodName != null) {
            TypeInsnNode typeInsnNode2 = instanceofInsn;
            String IS_MUTABLE_COLLECTION_METHOD_DESCRIPTOR2 = IS_MUTABLE_COLLECTION_METHOD_DESCRIPTOR;
            Intrinsics.checkNotNullExpressionValue(IS_MUTABLE_COLLECTION_METHOD_DESCRIPTOR2, "IS_MUTABLE_COLLECTION_METHOD_DESCRIPTOR");
            instructions.insertBefore(typeInsnNode2, typeIntrinsics.typeIntrinsicNode(isMutableCollectionMethodName, IS_MUTABLE_COLLECTION_METHOD_DESCRIPTOR2));
            instructions.remove(typeInsnNode2);
            return;
        }
        instanceofInsn.desc = asmType.getInternalName();
    }

    @JvmStatic
    public static final void checkcast(InstructionAdapter v, KotlinType kotlinType, Type asmType, boolean safe) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
        Intrinsics.checkNotNullParameter(asmType, "asmType");
        if (safe) {
            v.checkcast(asmType);
            return;
        }
        TypeIntrinsics typeIntrinsics = INSTANCE;
        int functionTypeArity = typeIntrinsics.getFunctionTypeArity(kotlinType);
        if (functionTypeArity >= 0) {
            v.iconst(functionTypeArity);
            String str = BEFORE_CHECKCAST_TO_FUNCTION_OF_ARITY;
            String BEFORE_CHECKCAST_TO_FUNCTION_OF_ARITY_DESCRIPTOR2 = BEFORE_CHECKCAST_TO_FUNCTION_OF_ARITY_DESCRIPTOR;
            Intrinsics.checkNotNullExpressionValue(BEFORE_CHECKCAST_TO_FUNCTION_OF_ARITY_DESCRIPTOR2, "BEFORE_CHECKCAST_TO_FUNCTION_OF_ARITY_DESCRIPTOR");
            typeIntrinsics.typeIntrinsic(v, str, BEFORE_CHECKCAST_TO_FUNCTION_OF_ARITY_DESCRIPTOR2);
            v.checkcast(asmType);
            return;
        }
        String asMutableCollectionMethodName = typeIntrinsics.getAsMutableCollectionMethodName(kotlinType);
        if (asMutableCollectionMethodName != null) {
            typeIntrinsics.typeIntrinsic(v, asMutableCollectionMethodName, typeIntrinsics.getAsMutableCollectionDescriptor(asmType));
        } else {
            v.checkcast(asmType);
        }
    }

    static {
        Type objectType = Type.getObjectType(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME);
        OBJECT_TYPE = objectType;
        BEFORE_CHECKCAST_TO_FUNCTION_OF_ARITY = "beforeCheckcastToFunctionOfArity";
        BEFORE_CHECKCAST_TO_FUNCTION_OF_ARITY_DESCRIPTOR = Type.getMethodDescriptor(objectType, objectType, Type.INT_TYPE);
    }

    private final String getMutableCollectionMethodName(String prefix, KotlinType kotlinType) {
        String strAsString;
        FqName classFqName = getClassFqName(kotlinType);
        if (classFqName == null || !MUTABLE_COLLECTION_TYPE_FQ_NAMES.contains(classFqName)) {
            return null;
        }
        if (Intrinsics.areEqual(classFqName, StandardNames.FqNames.mutableMapEntry)) {
            strAsString = "MutableMapEntry";
        } else {
            strAsString = classFqName.shortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        }
        return prefix + strAsString;
    }

    private final String getIsMutableCollectionMethodName(KotlinType kotlinType) {
        return getMutableCollectionMethodName(LanguageCodeUtil.IS, kotlinType);
    }

    private final String getAsMutableCollectionMethodName(KotlinType kotlinType) {
        return getMutableCollectionMethodName("as", kotlinType);
    }

    private final FqName getClassFqName(KotlinType kotlinType) {
        ClassDescriptor classDescriptor = TypeUtils.getClassDescriptor(kotlinType);
        if (classDescriptor == null) {
            return null;
        }
        return DescriptorUtils.getFqName(classDescriptor).toSafe();
    }

    private final int getFunctionTypeArity(KotlinType kotlinType) {
        return getFunctionTypeArityByRegex(kotlinType, KOTLIN_FUNCTION_INTERFACE_REGEX);
    }

    private final int getFunctionTypeArityByRegex(KotlinType kotlinType, Regex regex) {
        FqName classFqName = getClassFqName(kotlinType);
        if (classFqName == null) {
            return -1;
        }
        String strAsString = classFqName.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        MatchResult matchResultFind$default = Regex.find$default(regex, strAsString, 0, 2, null);
        if (matchResultFind$default == null) {
            return -1;
        }
        MatchGroup matchGroup = matchResultFind$default.getGroups().get(1);
        Intrinsics.checkNotNull(matchGroup);
        Integer numValueOf = Integer.valueOf(matchGroup.getValue());
        Intrinsics.checkNotNullExpressionValue(numValueOf, "valueOf(...)");
        return numValueOf.intValue();
    }

    private final int getSuspendFunctionTypeArity(KotlinType kotlinType) {
        return getFunctionTypeArityByRegex(kotlinType, KOTLIN_SUSPEND_FUNCTION_INTERFACE_REGEX);
    }

    private final MethodInsnNode typeIntrinsicNode(String methodName, String methodDescriptor) {
        return new MethodInsnNode(184, INTRINSICS_CLASS, methodName, methodDescriptor, false);
    }

    private final void typeIntrinsic(InstructionAdapter instructionAdapter, String str, String str2) {
        instructionAdapter.invokestatic(INTRINSICS_CLASS, str, str2, false);
    }

    private final String getAsMutableCollectionDescriptor(Type asmType) {
        String methodDescriptor = Type.getMethodDescriptor(asmType, OBJECT_TYPE);
        Intrinsics.checkNotNullExpressionValue(methodDescriptor, "getMethodDescriptor(...)");
        return methodDescriptor;
    }
}
