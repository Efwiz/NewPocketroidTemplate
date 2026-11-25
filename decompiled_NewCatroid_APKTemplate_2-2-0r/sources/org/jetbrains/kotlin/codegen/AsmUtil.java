package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import java.util.Map;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableMap;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.descriptors.java.JavaVisibilities;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.resolve.jvm.JvmPrimitiveType;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: loaded from: classes3.dex */
public class AsmUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String BOUND_REFERENCE_RECEIVER = "receiver";
    public static final String CAPTURED_LABELED_THIS_FIELD = "$this_";
    public static final String CAPTURED_PREFIX = "$";
    public static final String CAPTURED_RECEIVER_FIELD = "receiver$0";
    public static final String CAPTURED_THIS_FIELD = "this$0";
    public static final String INLINE_DECLARATION_SITE_THIS = "this_";
    public static final String LABELED_THIS_FIELD = "this_";
    public static final String LABELED_THIS_PARAMETER = "$this$";
    public static final String LOCAL_FUNCTION_VARIABLE_PREFIX = "$fun$";
    public static final int NO_FLAG_PACKAGE_PRIVATE = 0;
    public static final String RECEIVER_PARAMETER_NAME = "$receiver";
    public static final String THIS = "this";
    public static final String THIS_IN_DEFAULT_IMPLS = "$this";
    private static final ImmutableMap<Integer, JvmPrimitiveType> primitiveTypeByAsmSort;
    private static final ImmutableMap<Type, Type> primitiveTypeByBoxedType;
    private static final Map<Visibility, Integer> visibilityToAccessFlag = ImmutableMap.builder().put(Visibilities.Private.INSTANCE, 2).put(Visibilities.PrivateToThis.INSTANCE, 2).put(Visibilities.Protected.INSTANCE, 4).put(JavaVisibilities.ProtectedStaticVisibility.INSTANCE, 4).put(JavaVisibilities.ProtectedAndPackage.INSTANCE, 4).put(Visibilities.Public.INSTANCE, 1).put(Visibilities.Internal.INSTANCE, 1).put(Visibilities.Local.INSTANCE, 0).put(JavaVisibilities.PackageVisibility.INSTANCE, 0).build();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 8 || i == 11 || i == 15 || i == 19 || i == 21 || i == 56 || i == 58 || i == 60 || i == 5 || i == 6 || i == 31 || i == 32) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 8 || i == 11 || i == 15 || i == 19 || i == 21 || i == 56 || i == 58 || i == 60 || i == 5 || i == 6 || i == 31 || i == 32) ? 2 : 3];
        switch (i) {
            case 1:
            case 5:
            case 6:
            case 8:
            case 11:
            case 15:
            case 19:
            case 21:
            case 31:
            case 32:
            case 56:
            case 58:
            case 60:
                objArr[0] = "org/jetbrains/kotlin/codegen/AsmUtil";
                break;
            case 2:
                objArr[0] = "callableName";
                break;
            case 3:
                objArr[0] = "prefix";
                break;
            case 4:
                objArr[0] = "defaultName";
                break;
            case 7:
            case 9:
            case 18:
            case 27:
            case 29:
            case 34:
            case 39:
            case 41:
            case 44:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 10:
            case 12:
            case 13:
            case 16:
                objArr[0] = "boxedType";
                break;
            case 14:
                objArr[0] = "boxedOrPrimitiveType";
                break;
            case 17:
                objArr[0] = "unboxedType";
                break;
            case 20:
                objArr[0] = "componentType";
                break;
            case 22:
                objArr[0] = "name";
                break;
            case 23:
                objArr[0] = "returnType";
                break;
            case 24:
                objArr[0] = "parameterTypes";
                break;
            case 25:
            case 28:
            case 33:
            case 35:
            case 38:
            case 40:
            case 42:
            case 43:
            case 45:
            case 46:
            case 49:
            case 61:
            case 62:
                objArr[0] = RegisterSpec.PREFIX;
                break;
            case 26:
                objArr[0] = K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_EXCEPTION;
                break;
            case 30:
                objArr[0] = "expectedType";
                break;
            case 36:
            case 47:
            case 50:
                objArr[0] = "topOfStack";
                break;
            case 37:
            case 48:
            case 51:
                objArr[0] = "afterTop";
                break;
            case 52:
                objArr[0] = "av";
                break;
            case 53:
                objArr[0] = "data";
                break;
            case 54:
                objArr[0] = "strings";
                break;
            case 55:
            case 59:
                objArr[0] = "fqName";
                break;
            case 57:
                objArr[0] = "classId";
                break;
            default:
                objArr[0] = "originalName";
                break;
        }
        if (i == 1) {
            objArr[1] = "getCapturedFieldName";
        } else if (i == 8) {
            objArr[1] = "boxType";
        } else if (i == 11) {
            objArr[1] = "unboxType";
        } else if (i == 15) {
            objArr[1] = "unboxUnlessPrimitive";
        } else if (i == 19) {
            objArr[1] = "correctElementType";
        } else if (i == 21) {
            objArr[1] = "getArrayType";
        } else if (i == 56) {
            objArr[1] = "asmTypeByFqNameWithoutInnerClasses";
        } else if (i == 58) {
            objArr[1] = "asmTypeByClassId";
        } else if (i == 60) {
            objArr[1] = "internalNameByFqNameWithoutInnerClasses";
        } else if (i == 5 || i == 6) {
            objArr[1] = "getLabeledThisName";
        } else if (i == 31 || i == 32) {
            objArr[1] = "numberFunctionOperandType";
        } else {
            objArr[1] = "org/jetbrains/kotlin/codegen/AsmUtil";
        }
        switch (i) {
            case 1:
            case 5:
            case 6:
            case 8:
            case 11:
            case 15:
            case 19:
            case 21:
            case 31:
            case 32:
            case 56:
            case 58:
            case 60:
                break;
            case 2:
            case 3:
            case 4:
                objArr[2] = "getLabeledThisName";
                break;
            case 7:
                objArr[2] = "boxType";
                break;
            case 9:
                objArr[2] = "boxPrimitiveType";
                break;
            case 10:
                objArr[2] = "unboxType";
                break;
            case 12:
                objArr[2] = "unboxPrimitiveTypeOrNull";
                break;
            case 13:
                objArr[2] = "isBoxedPrimitiveType";
                break;
            case 14:
                objArr[2] = "unboxUnlessPrimitive";
                break;
            case 16:
            case 17:
                objArr[2] = "isBoxedTypeOf";
                break;
            case 18:
                objArr[2] = "correctElementType";
                break;
            case 20:
                objArr[2] = "getArrayType";
                break;
            case 22:
            case 23:
            case 24:
                objArr[2] = "method";
                break;
            case 25:
            case 26:
                objArr[2] = "genThrow";
                break;
            case 27:
            case 28:
                objArr[2] = "pushDefaultValueOnStack";
                break;
            case 29:
                objArr[2] = "defaultValueOpcode";
                break;
            case 30:
                objArr[2] = "numberFunctionOperandType";
                break;
            case 33:
            case 34:
                objArr[2] = "pop";
                break;
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
                objArr[2] = "pop2";
                break;
            case 40:
            case 41:
            case 42:
            case 49:
            case 50:
            case 51:
                objArr[2] = "dup";
                break;
            case 43:
            case 44:
            case 45:
                objArr[2] = "dupx";
                break;
            case 46:
            case 47:
            case 48:
                objArr[2] = "dupSecond";
                break;
            case 52:
            case 53:
            case 54:
                objArr[2] = "writeAnnotationData";
                break;
            case 55:
                objArr[2] = "asmTypeByFqNameWithoutInnerClasses";
                break;
            case 57:
                objArr[2] = "asmTypeByClassId";
                break;
            case 59:
                objArr[2] = "internalNameByFqNameWithoutInnerClasses";
                break;
            case 61:
                objArr[2] = "wrapJavaClassIntoKClass";
                break;
            case 62:
                objArr[2] = "wrapJavaClassesIntoKClasses";
                break;
            default:
                objArr[2] = "getCapturedFieldName";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 8 && i != 11 && i != 15 && i != 19 && i != 21 && i != 56 && i != 58 && i != 60 && i != 5 && i != 6 && i != 31 && i != 32) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        ImmutableMap.Builder builder2 = ImmutableMap.builder();
        for (JvmPrimitiveType jvmPrimitiveType : JvmPrimitiveType.values()) {
            Type type = Type.getType(jvmPrimitiveType.getDesc());
            builder.put(Integer.valueOf(type.getSort()), jvmPrimitiveType);
            builder2.put(asmTypeByFqNameWithoutInnerClasses(jvmPrimitiveType.getWrapperFqName()), type);
        }
        primitiveTypeByAsmSort = builder.build();
        primitiveTypeByBoxedType = builder2.build();
    }

    private AsmUtil() {
    }

    public static String getCapturedFieldName(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        String str2 = "$" + str;
        if (str2 == null) {
            $$$reportNull$$$0(1);
        }
        return str2;
    }

    public static String getLabeledThisName(String str, String str2, String str3) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        if (str2 == null) {
            $$$reportNull$$$0(3);
        }
        if (str3 == null) {
            $$$reportNull$$$0(4);
        }
        if (!Name.isValidIdentifier(str)) {
            if (str3 == null) {
                $$$reportNull$$$0(5);
            }
            return str3;
        }
        String str4 = str2 + CommonVariableAsmNameManglingUtils.mangleNameIfNeeded(str);
        if (str4 == null) {
            $$$reportNull$$$0(6);
        }
        return str4;
    }

    public static Type boxType(Type type) {
        if (type == null) {
            $$$reportNull$$$0(7);
        }
        Type typeBoxPrimitiveType = boxPrimitiveType(type);
        if (typeBoxPrimitiveType != null) {
            type = typeBoxPrimitiveType;
        }
        if (type == null) {
            $$$reportNull$$$0(8);
        }
        return type;
    }

    public static Type boxPrimitiveType(Type type) {
        if (type == null) {
            $$$reportNull$$$0(9);
        }
        JvmPrimitiveType jvmPrimitiveType = primitiveTypeByAsmSort.get(Integer.valueOf(type.getSort()));
        if (jvmPrimitiveType != null) {
            return asmTypeByFqNameWithoutInnerClasses(jvmPrimitiveType.getWrapperFqName());
        }
        return null;
    }

    public static Type unboxType(Type type) {
        if (type == null) {
            $$$reportNull$$$0(10);
        }
        Type typeUnboxPrimitiveTypeOrNull = unboxPrimitiveTypeOrNull(type);
        if (typeUnboxPrimitiveTypeOrNull == null) {
            throw new UnsupportedOperationException("Unboxing: " + type);
        }
        if (typeUnboxPrimitiveTypeOrNull == null) {
            $$$reportNull$$$0(11);
        }
        return typeUnboxPrimitiveTypeOrNull;
    }

    public static Type unboxPrimitiveTypeOrNull(Type type) {
        if (type == null) {
            $$$reportNull$$$0(12);
        }
        return primitiveTypeByBoxedType.get(type);
    }

    public static boolean isBoxedPrimitiveType(Type type) {
        if (type == null) {
            $$$reportNull$$$0(13);
        }
        return primitiveTypeByBoxedType.get(type) != null;
    }

    public static Type unboxUnlessPrimitive(Type type) {
        if (type == null) {
            $$$reportNull$$$0(14);
        }
        if (!isPrimitive(type)) {
            return unboxType(type);
        }
        if (type == null) {
            $$$reportNull$$$0(15);
        }
        return type;
    }

    public static boolean isBoxedTypeOf(Type type, Type type2) {
        if (type == null) {
            $$$reportNull$$$0(16);
        }
        if (type2 == null) {
            $$$reportNull$$$0(17);
        }
        return unboxPrimitiveTypeOrNull(type) == type2;
    }

    public static boolean isIntPrimitive(Type type) {
        return type == Type.INT_TYPE || type == Type.SHORT_TYPE || type == Type.BYTE_TYPE || type == Type.CHAR_TYPE;
    }

    public static boolean isIntOrLongPrimitive(Type type) {
        return isIntPrimitive(type) || type == Type.LONG_TYPE;
    }

    public static boolean isPrimitive(Type type) {
        return (type.getSort() == 10 || type.getSort() == 9) ? false : true;
    }

    public static Type correctElementType(Type type) {
        if (type == null) {
            $$$reportNull$$$0(18);
        }
        Type type2 = Type.getType(type.getInternalName().substring(1));
        if (type2 == null) {
            $$$reportNull$$$0(19);
        }
        return type2;
    }

    public static Type getArrayType(Type type) {
        if (type == null) {
            $$$reportNull$$$0(20);
        }
        Type type2 = Type.getType("[" + type.getDescriptor());
        if (type2 == null) {
            $$$reportNull$$$0(21);
        }
        return type2;
    }

    public static PrimitiveType asmPrimitiveTypeToLangPrimitiveType(Type type) {
        JvmPrimitiveType jvmPrimitiveType = primitiveTypeByAsmSort.get(Integer.valueOf(type.getSort()));
        if (jvmPrimitiveType != null) {
            return jvmPrimitiveType.getPrimitiveType();
        }
        return null;
    }

    public static Method method(String str, Type type, Type... typeArr) {
        if (str == null) {
            $$$reportNull$$$0(22);
        }
        if (type == null) {
            $$$reportNull$$$0(23);
        }
        if (typeArr == null) {
            $$$reportNull$$$0(24);
        }
        return new Method(str, Type.getMethodDescriptor(type, typeArr));
    }

    public static Type stringValueOfType(Type type) {
        int sort = type.getSort();
        if (sort == 10 || sort == 9) {
            return AsmTypes.OBJECT_TYPE;
        }
        return (sort == 3 || sort == 4) ? Type.INT_TYPE : type;
    }

    public static void genThrow(InstructionAdapter instructionAdapter, String str, String str2) {
        if (instructionAdapter == null) {
            $$$reportNull$$$0(25);
        }
        if (str == null) {
            $$$reportNull$$$0(26);
        }
        instructionAdapter.anew(Type.getObjectType(str));
        instructionAdapter.dup();
        if (str2 == null) {
            instructionAdapter.invokespecial(str, "<init>", "()V", false);
        } else {
            instructionAdapter.aconst(str2);
            instructionAdapter.invokespecial(str, "<init>", "(Ljava/lang/String;)V", false);
        }
        instructionAdapter.athrow();
    }

    public static void genStringBuilderConstructor(InstructionAdapter instructionAdapter) {
        instructionAdapter.visitTypeInsn(187, "java/lang/StringBuilder");
        instructionAdapter.dup();
        instructionAdapter.invokespecial("java/lang/StringBuilder", "<init>", "()V", false);
    }

    public static void genInvertBoolean(InstructionAdapter instructionAdapter) {
        instructionAdapter.iconst(1);
        instructionAdapter.xor(Type.INT_TYPE);
    }

    public static void numConst(int i, Type type, InstructionAdapter instructionAdapter) {
        if (type == Type.FLOAT_TYPE) {
            instructionAdapter.fconst(i);
            return;
        }
        if (type == Type.DOUBLE_TYPE) {
            instructionAdapter.dconst(i);
            return;
        }
        if (type == Type.LONG_TYPE) {
            instructionAdapter.lconst(i);
        } else {
            if (type == Type.CHAR_TYPE || type == Type.BYTE_TYPE || type == Type.SHORT_TYPE || type == Type.INT_TYPE) {
                instructionAdapter.iconst(i);
                return;
            }
            throw new IllegalArgumentException("Primitive numeric type expected, got: " + type);
        }
    }

    public static void swap(InstructionAdapter instructionAdapter, Type type, Type type2) {
        if (type.getSize() == 1) {
            if (type2.getSize() == 1) {
                instructionAdapter.swap();
                return;
            } else {
                instructionAdapter.dupX2();
                instructionAdapter.pop();
                return;
            }
        }
        if (type2.getSize() == 1) {
            instructionAdapter.dup2X1();
        } else {
            instructionAdapter.dup2X2();
        }
        instructionAdapter.pop2();
    }

    public static void pushDefaultValueOnStack(Type type, InstructionAdapter instructionAdapter) {
        if (type == null) {
            $$$reportNull$$$0(27);
        }
        if (instructionAdapter == null) {
            $$$reportNull$$$0(28);
        }
        instructionAdapter.visitInsn(defaultValueOpcode(type));
    }

    public static int defaultValueOpcode(Type type) {
        if (type == null) {
            $$$reportNull$$$0(29);
        }
        if (type.getSort() == 10 || type.getSort() == 9) {
            return 1;
        }
        if (type.getSort() == 6) {
            return 11;
        }
        if (type.getSort() == 8) {
            return 14;
        }
        return type.getSort() == 7 ? 9 : 3;
    }

    public static Type comparisonOperandType(Type type, Type type2) {
        if (!isPrimitive(type) || !isPrimitive(type2)) {
            throw new IllegalArgumentException("Cannot compute comparison operand type: " + type + ", " + type2);
        }
        if (type == Type.BOOLEAN_TYPE || type2 == Type.BOOLEAN_TYPE) {
            if (type != Type.BOOLEAN_TYPE || type2 != Type.BOOLEAN_TYPE) {
                throw new IllegalArgumentException("Cannot compare boolean with non-boolean: " + type + ", " + type2);
            }
            return Type.BOOLEAN_TYPE;
        }
        if (type == Type.DOUBLE_TYPE || type2 == Type.DOUBLE_TYPE) {
            return Type.DOUBLE_TYPE;
        }
        if (type == Type.FLOAT_TYPE || type2 == Type.FLOAT_TYPE) {
            return Type.FLOAT_TYPE;
        }
        if (type == Type.LONG_TYPE || type2 == Type.LONG_TYPE) {
            return Type.LONG_TYPE;
        }
        if (type == Type.CHAR_TYPE || type2 == Type.CHAR_TYPE) {
            return Type.CHAR_TYPE;
        }
        return Type.INT_TYPE;
    }

    public static Type numberFunctionOperandType(Type type) {
        if (type == null) {
            $$$reportNull$$$0(30);
        }
        if (type != Type.SHORT_TYPE && type != Type.BYTE_TYPE && type != Type.CHAR_TYPE) {
            if (type == null) {
                $$$reportNull$$$0(32);
            }
            return type;
        }
        Type type2 = Type.INT_TYPE;
        if (type2 == null) {
            $$$reportNull$$$0(31);
        }
        return type2;
    }

    public static void pop(MethodVisitor methodVisitor, Type type) {
        if (methodVisitor == null) {
            $$$reportNull$$$0(33);
        }
        if (type == null) {
            $$$reportNull$$$0(34);
        }
        if (type.getSize() == 2) {
            methodVisitor.visitInsn(88);
        } else {
            methodVisitor.visitInsn(87);
        }
    }

    public static void pop2(MethodVisitor methodVisitor, Type type, Type type2) {
        if (methodVisitor == null) {
            $$$reportNull$$$0(35);
        }
        if (type == null) {
            $$$reportNull$$$0(36);
        }
        if (type2 == null) {
            $$$reportNull$$$0(37);
        }
        if (type.getSize() == 1 && type2.getSize() == 1) {
            methodVisitor.visitInsn(88);
        } else {
            pop(methodVisitor, type);
            pop(methodVisitor, type2);
        }
    }

    public static void pop2(MethodVisitor methodVisitor, Type type) {
        if (methodVisitor == null) {
            $$$reportNull$$$0(38);
        }
        if (type == null) {
            $$$reportNull$$$0(39);
        }
        if (type.getSize() == 2) {
            methodVisitor.visitInsn(88);
            methodVisitor.visitInsn(88);
        } else {
            methodVisitor.visitInsn(88);
        }
    }

    public static void dup(InstructionAdapter instructionAdapter, Type type) {
        if (instructionAdapter == null) {
            $$$reportNull$$$0(40);
        }
        if (type == null) {
            $$$reportNull$$$0(41);
        }
        dup(instructionAdapter, type.getSize());
    }

    private static void dup(InstructionAdapter instructionAdapter, int i) {
        if (instructionAdapter == null) {
            $$$reportNull$$$0(42);
        }
        if (i == 2) {
            instructionAdapter.dup2();
        } else {
            if (i == 1) {
                instructionAdapter.dup();
                return;
            }
            throw new UnsupportedOperationException();
        }
    }

    public static void dupx(InstructionAdapter instructionAdapter, Type type) {
        if (instructionAdapter == null) {
            $$$reportNull$$$0(43);
        }
        if (type == null) {
            $$$reportNull$$$0(44);
        }
        dupx(instructionAdapter, type.getSize());
    }

    private static void dupx(InstructionAdapter instructionAdapter, int i) {
        if (instructionAdapter == null) {
            $$$reportNull$$$0(45);
        }
        if (i == 2) {
            instructionAdapter.dup2X2();
        } else {
            if (i == 1) {
                instructionAdapter.dupX1();
                return;
            }
            throw new UnsupportedOperationException();
        }
    }

    public static void dupSecond(InstructionAdapter instructionAdapter, Type type, Type type2) {
        if (instructionAdapter == null) {
            $$$reportNull$$$0(46);
        }
        if (type == null) {
            $$$reportNull$$$0(47);
        }
        if (type2 == null) {
            $$$reportNull$$$0(48);
        }
        if (type2.getSize() == 0) {
            return;
        }
        if (type.getSize() == 0) {
            dup(instructionAdapter, type2);
            return;
        }
        if (type.getSize() == 1 && type2.getSize() == 1) {
            instructionAdapter.dup2();
            instructionAdapter.pop();
            return;
        }
        swap(instructionAdapter, type, type2);
        if (type.getSize() == 1 && type2.getSize() == 2) {
            instructionAdapter.dup2X1();
        } else if (type.getSize() == 2 && type2.getSize() == 1) {
            instructionAdapter.dupX2();
        } else {
            instructionAdapter.dup2X2();
        }
    }

    public static void dup(InstructionAdapter instructionAdapter, Type type, Type type2) {
        if (instructionAdapter == null) {
            $$$reportNull$$$0(49);
        }
        if (type == null) {
            $$$reportNull$$$0(50);
        }
        if (type2 == null) {
            $$$reportNull$$$0(51);
        }
        if (type.getSize() == 0 && type2.getSize() == 0) {
            return;
        }
        if (type.getSize() == 0) {
            dup(instructionAdapter, type2);
            return;
        }
        if (type2.getSize() == 0) {
            dup(instructionAdapter, type);
            return;
        }
        if (type2.getSize() == 1) {
            if (type.getSize() == 1) {
                dup(instructionAdapter, 2);
                return;
            }
            instructionAdapter.dup2X1();
            instructionAdapter.pop2();
            instructionAdapter.dupX2();
            instructionAdapter.dupX2();
            instructionAdapter.pop();
            instructionAdapter.dup2X1();
            return;
        }
        throw new UnsupportedOperationException("Don't know how generate dup3/dup4 for: " + type + " and " + type2);
    }

    public static void writeAnnotationData(AnnotationVisitor annotationVisitor, String[] strArr, String[] strArr2) {
        if (annotationVisitor == null) {
            $$$reportNull$$$0(52);
        }
        if (strArr == null) {
            $$$reportNull$$$0(53);
        }
        if (strArr2 == null) {
            $$$reportNull$$$0(54);
        }
        AnnotationVisitor annotationVisitorVisitArray = annotationVisitor.visitArray(JvmAnnotationNames.METADATA_DATA_FIELD_NAME);
        for (String str : strArr) {
            annotationVisitorVisitArray.visit(null, str);
        }
        annotationVisitorVisitArray.visitEnd();
        AnnotationVisitor annotationVisitorVisitArray2 = annotationVisitor.visitArray(JvmAnnotationNames.METADATA_STRINGS_FIELD_NAME);
        for (String str2 : strArr2) {
            annotationVisitorVisitArray2.visit(null, str2);
        }
        annotationVisitorVisitArray2.visitEnd();
    }

    public static Type asmTypeByFqNameWithoutInnerClasses(FqName fqName) {
        if (fqName == null) {
            $$$reportNull$$$0(55);
        }
        Type objectType = Type.getObjectType(internalNameByFqNameWithoutInnerClasses(fqName));
        if (objectType == null) {
            $$$reportNull$$$0(56);
        }
        return objectType;
    }

    public static Type asmTypeByClassId(ClassId classId) {
        if (classId == null) {
            $$$reportNull$$$0(57);
        }
        Type objectType = Type.getObjectType(classId.asString().replace('.', '$'));
        if (objectType == null) {
            $$$reportNull$$$0(58);
        }
        return objectType;
    }

    public static String internalNameByFqNameWithoutInnerClasses(FqName fqName) {
        if (fqName == null) {
            $$$reportNull$$$0(59);
        }
        String internalName = JvmClassName.byFqNameWithoutInnerClasses(fqName).getInternalName();
        if (internalName == null) {
            $$$reportNull$$$0(60);
        }
        return internalName;
    }

    public static void wrapJavaClassIntoKClass(InstructionAdapter instructionAdapter) {
        if (instructionAdapter == null) {
            $$$reportNull$$$0(61);
        }
        instructionAdapter.invokestatic(AsmTypes.REFLECTION, "getOrCreateKotlinClass", Type.getMethodDescriptor(AsmTypes.K_CLASS_TYPE, AsmTypes.getType(Class.class)), false);
    }

    public static void wrapJavaClassesIntoKClasses(InstructionAdapter instructionAdapter) {
        if (instructionAdapter == null) {
            $$$reportNull$$$0(62);
        }
        instructionAdapter.invokestatic(AsmTypes.REFLECTION, "getOrCreateKotlinClasses", Type.getMethodDescriptor(AsmTypes.K_CLASS_ARRAY_TYPE, AsmTypes.getType(Class[].class)), false);
    }

    public static Integer getVisibilityAccessFlag(Visibility visibility) {
        return visibilityToAccessFlag.get(visibility);
    }
}
