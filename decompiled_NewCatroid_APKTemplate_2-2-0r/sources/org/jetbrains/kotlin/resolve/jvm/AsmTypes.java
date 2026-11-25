package org.jetbrains.kotlin.resolve.jvm;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import kotlin.collections.CollectionsKt;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: loaded from: classes6.dex */
public class AsmTypes {
    public static final Type BOOLEAN_WRAPPER_TYPE;
    public static final Type CHARACTER_WRAPPER_TYPE;
    public static final Type DEFAULT_CONSTRUCTOR_MARKER;
    public static final Type ENUM_ENTRIES;
    public static final Type ENUM_TYPE;
    public static final Type FUNCTION;
    public static final Type FUNCTION0;
    public static final Type FUNCTION1;
    public static final Type FUNCTION_ADAPTER;
    public static final Type FUNCTION_REFERENCE;
    public static final Type FUNCTION_REFERENCE_IMPL;
    public static final Type JAVA_CLASS_ARRAY_TYPE;
    public static final Type JAVA_CLASS_TYPE;
    public static final Type K_CLASSIFIER_TYPE;
    public static final Type K_CLASS_ARRAY_TYPE;
    public static final Type K_CLASS_TYPE;
    public static final Type K_DECLARATION_CONTAINER_TYPE;
    public static final Type K_FUNCTION;
    public static final Type K_MUTABLE_PROPERTY0_TYPE;
    public static final Type K_MUTABLE_PROPERTY1_TYPE;
    public static final Type K_MUTABLE_PROPERTY2_TYPE;
    public static final Type K_PROPERTY0_TYPE;
    public static final Type K_PROPERTY1_TYPE;
    public static final Type K_PROPERTY2_TYPE;
    public static final Type K_PROPERTY_TYPE;
    public static final Type K_TYPE;
    public static final Type K_TYPE_PARAMETER;
    public static final Type K_TYPE_PROJECTION;
    public static final Type K_TYPE_PROJECTION_COMPANION;
    public static final Type K_VARIANCE;
    public static final Type LAMBDA;
    public static final Type MUTABLE_PROPERTY_REFERENCE0;
    public static final Type MUTABLE_PROPERTY_REFERENCE1;
    public static final Type MUTABLE_PROPERTY_REFERENCE2;
    public static final Type[] MUTABLE_PROPERTY_REFERENCE_IMPL;
    public static final Type NUMBER_TYPE;
    public static final Type OBJECT_REF_TYPE;
    public static final List<Type> OPTIMIZED_PROPERTY_REFERENCE_SUPERTYPES;
    public static final Type PROPERTY_REFERENCE0;
    public static final Type PROPERTY_REFERENCE1;
    public static final Type PROPERTY_REFERENCE2;
    public static final Type[] PROPERTY_REFERENCE_IMPL;
    public static final String REFLECTION = "kotlin/jvm/internal/Reflection";
    public static final String REF_TYPE_PREFIX = "kotlin/jvm/internal/Ref$";
    public static final Type SUSPEND_FUNCTION_TYPE;
    public static final Type UNIT_TYPE;
    public static final Type VOID_WRAPPER_TYPE;
    private static final Map<Class<?>, Type> TYPES_MAP = new ConcurrentHashMap();
    public static final Type OBJECT_TYPE = getType(Object.class);
    public static final Type JAVA_STRING_TYPE = getType(String.class);
    public static final Type JAVA_THROWABLE_TYPE = getType(Throwable.class);

    /* JADX WARN: Code duplicated, block: B:7:0x000d  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1 && i != 14) {
            switch (i) {
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 1 && i != 14) {
            switch (i) {
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 14:
                objArr[0] = "org/jetbrains/kotlin/resolve/jvm/AsmTypes";
                break;
            case 2:
            case 15:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 3:
                objArr[0] = "primitiveType";
                break;
            case 13:
                objArr[0] = "javaClass";
                break;
            default:
                objArr[0] = "className";
                break;
        }
        if (i == 1) {
            objArr[1] = "reflect";
        } else if (i != 14) {
            switch (i) {
                case 4:
                    objArr[1] = "sharedTypeForPrimitive";
                    break;
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                    objArr[1] = "valueTypeForPrimitive";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/resolve/jvm/AsmTypes";
                    break;
            }
        } else {
            objArr[1] = "getType";
        }
        switch (i) {
            case 1:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 14:
                break;
            case 2:
                objArr[2] = "isSharedVarType";
                break;
            case 3:
                objArr[2] = "sharedTypeForPrimitive";
                break;
            case 13:
                objArr[2] = "getType";
                break;
            case 15:
                objArr[2] = "isOptimizedPropertyReferenceSupertype";
                break;
            default:
                objArr[2] = "reflect";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 14) {
            switch (i) {
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    static {
        Type type = getType(Class.class);
        JAVA_CLASS_TYPE = type;
        JAVA_CLASS_ARRAY_TYPE = Type.getObjectType("[" + type.getDescriptor());
        ENUM_TYPE = getType(Enum.class);
        NUMBER_TYPE = getType(Number.class);
        BOOLEAN_WRAPPER_TYPE = getType(Boolean.class);
        CHARACTER_WRAPPER_TYPE = getType(Character.class);
        VOID_WRAPPER_TYPE = getType(Void.class);
        UNIT_TYPE = Type.getObjectType("kotlin/Unit");
        LAMBDA = Type.getObjectType("kotlin/jvm/internal/Lambda");
        FUNCTION_ADAPTER = Type.getObjectType("kotlin/jvm/internal/FunctionAdapter");
        FUNCTION_REFERENCE = Type.getObjectType("kotlin/jvm/internal/FunctionReference");
        FUNCTION_REFERENCE_IMPL = Type.getObjectType("kotlin/jvm/internal/FunctionReferenceImpl");
        PROPERTY_REFERENCE0 = Type.getObjectType("kotlin/jvm/internal/PropertyReference0");
        PROPERTY_REFERENCE1 = Type.getObjectType("kotlin/jvm/internal/PropertyReference1");
        PROPERTY_REFERENCE2 = Type.getObjectType("kotlin/jvm/internal/PropertyReference2");
        MUTABLE_PROPERTY_REFERENCE0 = Type.getObjectType("kotlin/jvm/internal/MutablePropertyReference0");
        MUTABLE_PROPERTY_REFERENCE1 = Type.getObjectType("kotlin/jvm/internal/MutablePropertyReference1");
        MUTABLE_PROPERTY_REFERENCE2 = Type.getObjectType("kotlin/jvm/internal/MutablePropertyReference2");
        FUNCTION = Type.getObjectType("kotlin/Function");
        FUNCTION0 = Type.getObjectType("kotlin/jvm/functions/Function0");
        FUNCTION1 = Type.getObjectType("kotlin/jvm/functions/Function1");
        Type[] typeArr = {Type.getObjectType("kotlin/jvm/internal/PropertyReference0Impl"), Type.getObjectType("kotlin/jvm/internal/PropertyReference1Impl"), Type.getObjectType("kotlin/jvm/internal/PropertyReference2Impl")};
        PROPERTY_REFERENCE_IMPL = typeArr;
        Type[] typeArr2 = {Type.getObjectType("kotlin/jvm/internal/MutablePropertyReference0Impl"), Type.getObjectType("kotlin/jvm/internal/MutablePropertyReference1Impl"), Type.getObjectType("kotlin/jvm/internal/MutablePropertyReference2Impl")};
        MUTABLE_PROPERTY_REFERENCE_IMPL = typeArr2;
        Type typeReflect = reflect("KClass");
        K_CLASS_TYPE = typeReflect;
        K_CLASS_ARRAY_TYPE = Type.getObjectType("[" + typeReflect.getDescriptor());
        K_CLASSIFIER_TYPE = reflect("KClassifier");
        K_DECLARATION_CONTAINER_TYPE = reflect("KDeclarationContainer");
        K_FUNCTION = reflect(StandardNames.K_FUNCTION_PREFIX);
        K_PROPERTY_TYPE = reflect(StandardNames.K_PROPERTY_PREFIX);
        K_PROPERTY0_TYPE = reflect("KProperty0");
        K_PROPERTY1_TYPE = reflect("KProperty1");
        K_PROPERTY2_TYPE = reflect("KProperty2");
        K_MUTABLE_PROPERTY0_TYPE = reflect("KMutableProperty0");
        K_MUTABLE_PROPERTY1_TYPE = reflect("KMutableProperty1");
        K_MUTABLE_PROPERTY2_TYPE = reflect("KMutableProperty2");
        K_TYPE = reflect("KType");
        K_TYPE_PROJECTION = reflect("KTypeProjection");
        K_TYPE_PROJECTION_COMPANION = reflect("KTypeProjection$Companion");
        K_TYPE_PARAMETER = reflect("KTypeParameter");
        K_VARIANCE = reflect("KVariance");
        SUSPEND_FUNCTION_TYPE = Type.getObjectType("kotlin/coroutines/jvm/internal/SuspendFunction");
        OBJECT_REF_TYPE = Type.getObjectType("kotlin/jvm/internal/Ref$ObjectRef");
        DEFAULT_CONSTRUCTOR_MARKER = Type.getObjectType("kotlin/jvm/internal/DefaultConstructorMarker");
        ENUM_ENTRIES = Type.getObjectType("kotlin/enums/EnumEntries");
        OPTIMIZED_PROPERTY_REFERENCE_SUPERTYPES = CollectionsKt.flatten(Arrays.asList(Arrays.asList(typeArr), Arrays.asList(typeArr2)));
    }

    private static Type reflect(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        Type objectType = Type.getObjectType("kotlin/reflect/" + str);
        if (objectType == null) {
            $$$reportNull$$$0(1);
        }
        return objectType;
    }

    public static boolean isSharedVarType(Type type) {
        if (type == null) {
            $$$reportNull$$$0(2);
        }
        return type.getSort() == 10 && type.getInternalName().startsWith(REF_TYPE_PREFIX);
    }

    public static Type sharedTypeForPrimitive(PrimitiveType primitiveType) {
        if (primitiveType == null) {
            $$$reportNull$$$0(3);
        }
        Type objectType = Type.getObjectType(REF_TYPE_PREFIX + primitiveType.getTypeName().getIdentifier() + "Ref");
        if (objectType == null) {
            $$$reportNull$$$0(4);
        }
        return objectType;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.resolve.jvm.AsmTypes$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$builtins$PrimitiveType;

        static {
            int[] iArr = new int[PrimitiveType.values().length];
            $SwitchMap$org$jetbrains$kotlin$builtins$PrimitiveType = iArr;
            try {
                iArr[PrimitiveType.BOOLEAN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$builtins$PrimitiveType[PrimitiveType.CHAR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$builtins$PrimitiveType[PrimitiveType.BYTE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$builtins$PrimitiveType[PrimitiveType.SHORT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$builtins$PrimitiveType[PrimitiveType.INT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$builtins$PrimitiveType[PrimitiveType.FLOAT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$builtins$PrimitiveType[PrimitiveType.LONG.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$builtins$PrimitiveType[PrimitiveType.DOUBLE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    public static Type valueTypeForPrimitive(PrimitiveType primitiveType) {
        switch (AnonymousClass1.$SwitchMap$org$jetbrains$kotlin$builtins$PrimitiveType[primitiveType.ordinal()]) {
            case 1:
                Type type = Type.BOOLEAN_TYPE;
                if (type == null) {
                    $$$reportNull$$$0(5);
                }
                return type;
            case 2:
                Type type2 = Type.CHAR_TYPE;
                if (type2 == null) {
                    $$$reportNull$$$0(6);
                }
                return type2;
            case 3:
                Type type3 = Type.BYTE_TYPE;
                if (type3 == null) {
                    $$$reportNull$$$0(7);
                }
                return type3;
            case 4:
                Type type4 = Type.SHORT_TYPE;
                if (type4 == null) {
                    $$$reportNull$$$0(8);
                }
                return type4;
            case 5:
                Type type5 = Type.INT_TYPE;
                if (type5 == null) {
                    $$$reportNull$$$0(9);
                }
                return type5;
            case 6:
                Type type6 = Type.FLOAT_TYPE;
                if (type6 == null) {
                    $$$reportNull$$$0(10);
                }
                return type6;
            case 7:
                Type type7 = Type.LONG_TYPE;
                if (type7 == null) {
                    $$$reportNull$$$0(11);
                }
                return type7;
            case 8:
                Type type8 = Type.DOUBLE_TYPE;
                if (type8 == null) {
                    $$$reportNull$$$0(12);
                }
                return type8;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public static Type getType(final Class<?> cls) {
        if (cls == null) {
            $$$reportNull$$$0(13);
        }
        Type typeComputeIfAbsent = TYPES_MAP.computeIfAbsent(cls, new Function() { // from class: org.jetbrains.kotlin.resolve.jvm.AsmTypes$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Type.getType((Class<?>) cls);
            }
        });
        if (typeComputeIfAbsent == null) {
            $$$reportNull$$$0(14);
        }
        return typeComputeIfAbsent;
    }

    public static boolean isOptimizedPropertyReferenceSupertype(Type type) {
        if (type == null) {
            $$$reportNull$$$0(15);
        }
        return OPTIMIZED_PROPERTY_REFERENCE_SUPERTYPES.contains(type);
    }

    private AsmTypes() {
    }
}
