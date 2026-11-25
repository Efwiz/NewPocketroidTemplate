package org.eclipse.jdt.internal.compiler.codegen;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ClassFile;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeConstants;
import org.eclipse.jdt.internal.compiler.lookup.TypeIds;
import org.eclipse.jdt.internal.compiler.util.HashtableOfObject;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: loaded from: classes14.dex */
public class ConstantPool implements ClassFileConstants, TypeIds {
    public static final char[] ALTMETAFACTORY;
    public static final char[] AddSuppressed;
    public static final char[] AddSuppressedSignature;
    public static final int CLASS_INITIAL_SIZE = 86;
    public static final int CONSTANTPOOL_GROW_SIZE = 6000;
    public static final int CONSTANTPOOL_INITIAL_SIZE = 2000;
    public static final char[] ClinitSignature;
    public static final char[] Clone;
    public static final char[] CloneSignature;
    public static final char[] Close;
    public static final char[] CloseSignature;
    public static final char[] DOUBLEVALUE_DOUBLE_METHOD_NAME;
    public static final char[] DOUBLEVALUE_DOUBLE_METHOD_SIGNATURE;
    public static final int DOUBLE_INITIAL_SIZE = 5;
    public static final char[] DefaultConstructorSignature;
    public static final char[] DesiredAssertionStatus;
    public static final char[] DesiredAssertionStatusSignature;
    public static final char[] DoubleConstrSignature;
    public static final char[] EnumName;
    public static final char[] EnumOrdinal;
    public static final char[] Equals;
    public static final char[] EqualsSignature;
    public static final char[] Exit;
    public static final char[] ExitIntSignature;
    public static final char[] FLOATVALUE_FLOAT_METHOD_NAME;
    public static final char[] FLOATVALUE_FLOAT_METHOD_SIGNATURE;
    public static final int FLOAT_INITIAL_SIZE = 3;
    public static final char[] FloatConstrSignature;
    public static final char[] ForName;
    public static final char[] ForNameSignature;
    public static final char[] GETDECLAREDCONSTRUCTOR_NAME;
    public static final char[] GETDECLAREDCONSTRUCTOR_SIGNATURE;
    public static final char[] GETDECLAREDFIELD_NAME;
    public static final char[] GETDECLAREDFIELD_SIGNATURE;
    public static final char[] GETDECLAREDMETHOD_NAME;
    public static final char[] GETDECLAREDMETHOD_SIGNATURE;
    public static final char[] GET_BOOLEAN_METHOD_NAME;
    public static final char[] GET_BOOLEAN_METHOD_SIGNATURE;
    public static final char[] GET_BYTE_METHOD_NAME;
    public static final char[] GET_BYTE_METHOD_SIGNATURE;
    public static final char[] GET_CHAR_METHOD_NAME;
    public static final char[] GET_CHAR_METHOD_SIGNATURE;
    public static final char[] GET_DOUBLE_METHOD_NAME;
    public static final char[] GET_DOUBLE_METHOD_SIGNATURE;
    public static final char[] GET_FLOAT_METHOD_NAME;
    public static final char[] GET_FLOAT_METHOD_SIGNATURE;
    public static final char[] GET_INT_METHOD_NAME;
    public static final char[] GET_INT_METHOD_SIGNATURE;
    public static final char[] GET_LONG_METHOD_NAME;
    public static final char[] GET_LONG_METHOD_SIGNATURE;
    public static final char[] GET_OBJECT_METHOD_NAME;
    public static final char[] GET_OBJECT_METHOD_SIGNATURE;
    public static final char[] GET_SHORT_METHOD_NAME;
    public static final char[] GET_SHORT_METHOD_SIGNATURE;
    public static final char[] GetCapturedArg;
    public static final char[] GetCapturedArgSignature;
    public static final char[] GetClass;
    public static final char[] GetClassSignature;
    public static final char[] GetComponentType;
    public static final char[] GetComponentTypeSignature;
    public static final char[] GetConstructor;
    public static final char[] GetConstructorSignature;
    public static final char[] GetFunctionalInterfaceClass;
    public static final char[] GetFunctionalInterfaceClassSignature;
    public static final char[] GetFunctionalInterfaceMethodName;
    public static final char[] GetFunctionalInterfaceMethodNameSignature;
    public static final char[] GetFunctionalInterfaceMethodSignature;
    public static final char[] GetFunctionalInterfaceMethodSignatureSignature;
    public static final char[] GetImplClass;
    public static final char[] GetImplClassSignature;
    public static final char[] GetImplMethodKind;
    public static final char[] GetImplMethodKindSignature;
    public static final char[] GetImplMethodName;
    public static final char[] GetImplMethodNameSignature;
    public static final char[] GetImplMethodSignature;
    public static final char[] GetImplMethodSignatureSignature;
    public static final char[] GetMessage;
    public static final char[] GetMessageSignature;
    public static final char[] HasNext;
    public static final char[] HasNextSignature;
    public static final char[] HashCode;
    public static final char[] HashCodeSignature;
    public static final char[] INTVALUE_INTEGER_METHOD_NAME;
    public static final char[] INTVALUE_INTEGER_METHOD_SIGNATURE;
    public static final int INT_INITIAL_SIZE = 248;
    public static final char[] INVOKE_METHOD_METHOD_NAME;
    public static final char[] INVOKE_METHOD_METHOD_SIGNATURE;
    public static final char[] ITERATOR_NAME;
    public static final char[] ITERATOR_SIGNATURE;
    public static final char[] IllegalArgumentExceptionConstructorSignature;
    public static final char[] Init;
    public static final char[] IntConstrSignature;
    public static final char[] IntIntegerSignature;
    public static final char[] Intern;
    public static final char[] InternSignature;
    public static final char[] JAVALANGREFLECTACCESSIBLEOBJECT_CONSTANTPOOLNAME;
    public static final char[] JAVALANGREFLECTARRAY_CONSTANTPOOLNAME;
    public static final char[] JAVALANGREFLECTFIELD_CONSTANTPOOLNAME;
    public static final char[] JAVALANGREFLECTMETHOD_CONSTANTPOOLNAME;
    public static final char[] JAVA_LANG_ANNOTATION_DOCUMENTED;
    public static final char[] JAVA_LANG_ANNOTATION_ELEMENTTYPE;
    public static final char[] JAVA_LANG_ANNOTATION_INHERITED;
    public static final char[] JAVA_LANG_ANNOTATION_REPEATABLE;
    public static final char[] JAVA_LANG_ANNOTATION_RETENTION;
    public static final char[] JAVA_LANG_ANNOTATION_RETENTIONPOLICY;
    public static final char[] JAVA_LANG_ANNOTATION_TARGET;
    public static final char[] JAVA_LANG_DEPRECATED;
    public static final char[] JAVA_LANG_INVOKE_LAMBDAMETAFACTORY_ALTMETAFACTORY_SIGNATURE;
    public static final char[] JAVA_LANG_INVOKE_LAMBDAMETAFACTORY_METAFACTORY_SIGNATURE;
    public static final char[] JAVA_LANG_INVOKE_METHODHANDLE_POLYMORPHICSIGNATURE;
    public static final char[][] JAVA_LANG_REFLECT_ACCESSIBLEOBJECT;
    public static final char[][] JAVA_LANG_REFLECT_ARRAY;
    public static final char[] JAVA_LANG_SAFEVARARGS;
    public static final char[] JavaIoPrintStreamSignature;
    public static final char[] JavaLangAssertionErrorConstantPoolName;
    public static final char[] JavaLangBooleanConstantPoolName;
    public static final char[] JavaLangByteConstantPoolName;
    public static final char[] JavaLangCharacterConstantPoolName;
    public static final char[] JavaLangClassConstantPoolName;
    public static final char[] JavaLangClassNotFoundExceptionConstantPoolName;
    public static final char[] JavaLangClassSignature;
    public static final char[] JavaLangDoubleConstantPoolName;
    public static final char[] JavaLangEnumConstantPoolName;
    public static final char[] JavaLangErrorConstantPoolName;
    public static final char[] JavaLangExceptionConstantPoolName;
    public static final char[] JavaLangFloatConstantPoolName;
    public static final char[] JavaLangIllegalArgumentExceptionConstantPoolName;
    public static final char[] JavaLangIntegerConstantPoolName;
    public static final char[] JavaLangInvokeSerializedLambda;
    public static final char[] JavaLangInvokeSerializedLambdaConstantPoolName;
    public static final char[] JavaLangLongConstantPoolName;
    public static final char[] JavaLangNoClassDefFoundErrorConstantPoolName;
    public static final char[] JavaLangNoSuchFieldErrorConstantPoolName;
    public static final char[] JavaLangObjectConstantPoolName;
    public static final char[] JavaLangObjectSignature;
    public static final char[] JavaLangReflectConstructorConstantPoolName;
    public static final char[] JavaLangReflectConstructorNewInstanceSignature;
    public static final char[] JavaLangShortConstantPoolName;
    public static final char[] JavaLangStringBufferConstantPoolName;
    public static final char[] JavaLangStringBuilderConstantPoolName;
    public static final char[] JavaLangStringConstantPoolName;
    public static final char[] JavaLangStringSignature;
    public static final char[] JavaLangSystemConstantPoolName;
    public static final char[] JavaLangThrowableConstantPoolName;
    public static final char[] JavaLangVoidConstantPoolName;
    public static final char[] JavaUtilIteratorConstantPoolName;
    public static final char[] LONGVALUE_LONG_METHOD_NAME;
    public static final char[] LONGVALUE_LONG_METHOD_SIGNATURE;
    public static final int LONG_INITIAL_SIZE = 5;
    public static final char[] LongConstrSignature;
    public static final char[] METAFACTORY;
    public static final int METHODS_AND_FIELDS_INITIAL_SIZE = 450;
    public static final int NAMEANDTYPE_INITIAL_SIZE = 272;
    public static final char[] Name;
    public static final char[] NewInstance;
    public static final char[] NewInstanceSignature;
    public static final char[] Next;
    public static final char[] NextSignature;
    public static final char[] ObjectConstrSignature;
    public static final char[] ObjectSignature;
    public static final char[] Ordinal;
    public static final char[] OrdinalSignature;
    public static final char[] Out;
    public static final char[] SETACCESSIBLE_NAME;
    public static final char[] SETACCESSIBLE_SIGNATURE;
    public static final char[] SET_BOOLEAN_METHOD_NAME;
    public static final char[] SET_BOOLEAN_METHOD_SIGNATURE;
    public static final char[] SET_BYTE_METHOD_NAME;
    public static final char[] SET_BYTE_METHOD_SIGNATURE;
    public static final char[] SET_CHAR_METHOD_NAME;
    public static final char[] SET_CHAR_METHOD_SIGNATURE;
    public static final char[] SET_DOUBLE_METHOD_NAME;
    public static final char[] SET_DOUBLE_METHOD_SIGNATURE;
    public static final char[] SET_FLOAT_METHOD_NAME;
    public static final char[] SET_FLOAT_METHOD_SIGNATURE;
    public static final char[] SET_INT_METHOD_NAME;
    public static final char[] SET_INT_METHOD_SIGNATURE;
    public static final char[] SET_LONG_METHOD_NAME;
    public static final char[] SET_LONG_METHOD_SIGNATURE;
    public static final char[] SET_OBJECT_METHOD_NAME;
    public static final char[] SET_OBJECT_METHOD_SIGNATURE;
    public static final char[] SET_SHORT_METHOD_NAME;
    public static final char[] SET_SHORT_METHOD_SIGNATURE;
    public static final char[] SHORTVALUE_SHORT_METHOD_NAME;
    public static final char[] SHORTVALUE_SHORT_METHOD_SIGNATURE;
    public static final int STRING_INITIAL_SIZE = 761;
    public static final char[] ShortConstrSignature;
    public static final char[] StringBufferAppendBooleanSignature;
    public static final char[] StringBufferAppendCharSignature;
    public static final char[] StringBufferAppendDoubleSignature;
    public static final char[] StringBufferAppendFloatSignature;
    public static final char[] StringBufferAppendIntSignature;
    public static final char[] StringBufferAppendLongSignature;
    public static final char[] StringBufferAppendObjectSignature;
    public static final char[] StringBufferAppendStringSignature;
    public static final char[] StringBuilderAppendBooleanSignature;
    public static final char[] StringBuilderAppendCharSignature;
    public static final char[] StringBuilderAppendDoubleSignature;
    public static final char[] StringBuilderAppendFloatSignature;
    public static final char[] StringBuilderAppendIntSignature;
    public static final char[] StringBuilderAppendLongSignature;
    public static final char[] StringBuilderAppendObjectSignature;
    public static final char[] StringBuilderAppendStringSignature;
    public static final char[] StringConstructorSignature;
    public static final char[] TYPE;
    public static final char[] This;
    public static final char[] ToString;
    public static final char[] ToStringSignature;
    public static final int UTF8_INITIAL_SIZE = 778;
    public static final char[] ValueOf;
    public static final char[] ValueOfBooleanSignature;
    public static final char[] ValueOfCharSignature;
    public static final char[] ValueOfDoubleSignature;
    public static final char[] ValueOfFloatSignature;
    public static final char[] ValueOfIntSignature;
    public static final char[] ValueOfLongSignature;
    public static final char[] ValueOfObjectSignature;
    public static final char[] ValueOfStringClassSignature;
    public static final char[] doubleDoubleSignature;
    public static final char[] floatFloatSignature;
    public static final char[] longLongSignature;
    public static final char[] shortShortSignature;
    public ClassFile classFile;
    public int currentOffset;
    protected DoubleCache doubleCache;
    protected FloatCache floatCache;
    protected IntegerCache intCache;
    protected LongCache longCache;
    public byte[] poolContent;
    public static final char[] Append = "append".toCharArray();
    public static final char[] ARRAY_NEWINSTANCE_NAME = TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME.toCharArray();
    public static final char[] ARRAY_NEWINSTANCE_SIGNATURE = "(Ljava/lang/Class;[I)Ljava/lang/Object;".toCharArray();
    public static final char[] ArrayCopy = "arraycopy".toCharArray();
    public static final char[] ArrayCopySignature = "(Ljava/lang/Object;ILjava/lang/Object;II)V".toCharArray();
    public static final char[] ArrayJavaLangClassConstantPoolName = "[Ljava/lang/Class;".toCharArray();
    public static final char[] ArrayJavaLangObjectConstantPoolName = "[Ljava/lang/Object;".toCharArray();
    public static final char[] booleanBooleanSignature = "(Z)Ljava/lang/Boolean;".toCharArray();
    public static final char[] BooleanConstrSignature = "(Z)V".toCharArray();
    public static final char[] BOOLEANVALUE_BOOLEAN_METHOD_NAME = "booleanValue".toCharArray();
    public static final char[] BOOLEANVALUE_BOOLEAN_METHOD_SIGNATURE = "()Z".toCharArray();
    public static final char[] byteByteSignature = "(B)Ljava/lang/Byte;".toCharArray();
    public static final char[] ByteConstrSignature = "(B)V".toCharArray();
    public static final char[] BYTEVALUE_BYTE_METHOD_NAME = "byteValue".toCharArray();
    public static final char[] BYTEVALUE_BYTE_METHOD_SIGNATURE = "()B".toCharArray();
    public static final char[] charCharacterSignature = "(C)Ljava/lang/Character;".toCharArray();
    public static final char[] CharConstrSignature = "(C)V".toCharArray();
    public static final char[] CHARVALUE_CHARACTER_METHOD_NAME = "charValue".toCharArray();
    public static final char[] CHARVALUE_CHARACTER_METHOD_SIGNATURE = "()C".toCharArray();
    public static final char[] Clinit = MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME.toCharArray();
    public int currentIndex = 1;
    public CharArrayCache UTF8Cache = new CharArrayCache(UTF8_INITIAL_SIZE);
    protected CharArrayCache stringCache = new CharArrayCache(STRING_INITIAL_SIZE);
    protected HashtableOfObject methodsAndFieldsCache = new HashtableOfObject(METHODS_AND_FIELDS_INITIAL_SIZE);
    protected CharArrayCache classCache = new CharArrayCache(86);
    protected HashtableOfObject nameAndTypeCacheForFieldsAndMethods = new HashtableOfObject(272);
    public int[] offsets = new int[5];

    static {
        char[] charArray = "()V".toCharArray();
        DefaultConstructorSignature = charArray;
        ClinitSignature = charArray;
        Close = "close".toCharArray();
        CloseSignature = "()V".toCharArray();
        DesiredAssertionStatus = "desiredAssertionStatus".toCharArray();
        DesiredAssertionStatusSignature = "()Z".toCharArray();
        DoubleConstrSignature = "(D)V".toCharArray();
        doubleDoubleSignature = "(D)Ljava/lang/Double;".toCharArray();
        DOUBLEVALUE_DOUBLE_METHOD_NAME = "doubleValue".toCharArray();
        DOUBLEVALUE_DOUBLE_METHOD_SIGNATURE = "()D".toCharArray();
        EnumName = "$enum$name".toCharArray();
        EnumOrdinal = "$enum$ordinal".toCharArray();
        Exit = "exit".toCharArray();
        ExitIntSignature = "(I)V".toCharArray();
        FloatConstrSignature = "(F)V".toCharArray();
        floatFloatSignature = "(F)Ljava/lang/Float;".toCharArray();
        FLOATVALUE_FLOAT_METHOD_NAME = "floatValue".toCharArray();
        FLOATVALUE_FLOAT_METHOD_SIGNATURE = "()F".toCharArray();
        ForName = "forName".toCharArray();
        ForNameSignature = "(Ljava/lang/String;)Ljava/lang/Class;".toCharArray();
        GET_BOOLEAN_METHOD_NAME = "getBoolean".toCharArray();
        GET_BOOLEAN_METHOD_SIGNATURE = "(Ljava/lang/Object;)Z".toCharArray();
        GET_BYTE_METHOD_NAME = "getByte".toCharArray();
        GET_BYTE_METHOD_SIGNATURE = "(Ljava/lang/Object;)B".toCharArray();
        GET_CHAR_METHOD_NAME = "getChar".toCharArray();
        GET_CHAR_METHOD_SIGNATURE = "(Ljava/lang/Object;)C".toCharArray();
        GET_DOUBLE_METHOD_NAME = "getDouble".toCharArray();
        GET_DOUBLE_METHOD_SIGNATURE = "(Ljava/lang/Object;)D".toCharArray();
        GET_FLOAT_METHOD_NAME = "getFloat".toCharArray();
        GET_FLOAT_METHOD_SIGNATURE = "(Ljava/lang/Object;)F".toCharArray();
        GET_INT_METHOD_NAME = "getInt".toCharArray();
        GET_INT_METHOD_SIGNATURE = "(Ljava/lang/Object;)I".toCharArray();
        GET_LONG_METHOD_NAME = "getLong".toCharArray();
        GET_LONG_METHOD_SIGNATURE = "(Ljava/lang/Object;)J".toCharArray();
        GET_OBJECT_METHOD_NAME = "get".toCharArray();
        GET_OBJECT_METHOD_SIGNATURE = "(Ljava/lang/Object;)Ljava/lang/Object;".toCharArray();
        GET_SHORT_METHOD_NAME = "getShort".toCharArray();
        GET_SHORT_METHOD_SIGNATURE = "(Ljava/lang/Object;)S".toCharArray();
        GetClass = "getClass".toCharArray();
        char[] charArray2 = "()Ljava/lang/Class;".toCharArray();
        GetClassSignature = charArray2;
        GetComponentType = "getComponentType".toCharArray();
        GetComponentTypeSignature = charArray2;
        GetConstructor = "getConstructor".toCharArray();
        GetConstructorSignature = TypeProxy.SilentConstruction.Appender.GET_DECLARED_CONSTRUCTOR_METHOD_DESCRIPTOR.toCharArray();
        GETDECLAREDCONSTRUCTOR_NAME = TypeProxy.SilentConstruction.Appender.GET_DECLARED_CONSTRUCTOR_METHOD_NAME.toCharArray();
        GETDECLAREDCONSTRUCTOR_SIGNATURE = TypeProxy.SilentConstruction.Appender.GET_DECLARED_CONSTRUCTOR_METHOD_DESCRIPTOR.toCharArray();
        GETDECLAREDFIELD_NAME = "getDeclaredField".toCharArray();
        GETDECLAREDFIELD_SIGNATURE = "(Ljava/lang/String;)Ljava/lang/reflect/Field;".toCharArray();
        GETDECLAREDMETHOD_NAME = "getDeclaredMethod".toCharArray();
        GETDECLAREDMETHOD_SIGNATURE = "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;".toCharArray();
        GetMessage = "getMessage".toCharArray();
        char[] charArray3 = "()Ljava/lang/String;".toCharArray();
        GetMessageSignature = charArray3;
        HasNext = "hasNext".toCharArray();
        HasNextSignature = "()Z".toCharArray();
        Init = "<init>".toCharArray();
        IntConstrSignature = "(I)V".toCharArray();
        ITERATOR_NAME = "iterator".toCharArray();
        ITERATOR_SIGNATURE = "()Ljava/util/Iterator;".toCharArray();
        Intern = "intern".toCharArray();
        InternSignature = charArray3;
        IntIntegerSignature = "(I)Ljava/lang/Integer;".toCharArray();
        INTVALUE_INTEGER_METHOD_NAME = "intValue".toCharArray();
        INTVALUE_INTEGER_METHOD_SIGNATURE = "()I".toCharArray();
        INVOKE_METHOD_METHOD_NAME = "invoke".toCharArray();
        INVOKE_METHOD_METHOD_SIGNATURE = "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;".toCharArray();
        JAVA_LANG_REFLECT_ACCESSIBLEOBJECT = new char[][]{TypeConstants.JAVA, TypeConstants.LANG, TypeConstants.REFLECT, "AccessibleObject".toCharArray()};
        JAVA_LANG_REFLECT_ARRAY = new char[][]{TypeConstants.JAVA, TypeConstants.LANG, TypeConstants.REFLECT, "Array".toCharArray()};
        IllegalArgumentExceptionConstructorSignature = "(Ljava/lang/String;)V".toCharArray();
        JavaIoPrintStreamSignature = "Ljava/io/PrintStream;".toCharArray();
        JavaLangAssertionErrorConstantPoolName = "java/lang/AssertionError".toCharArray();
        JavaLangBooleanConstantPoolName = "java/lang/Boolean".toCharArray();
        JavaLangByteConstantPoolName = "java/lang/Byte".toCharArray();
        JavaLangCharacterConstantPoolName = "java/lang/Character".toCharArray();
        JavaLangClassConstantPoolName = TypeProxy.SilentConstruction.Appender.JAVA_LANG_CLASS_INTERNAL_NAME.toCharArray();
        JavaLangClassNotFoundExceptionConstantPoolName = "java/lang/ClassNotFoundException".toCharArray();
        JavaLangClassSignature = "Ljava/lang/Class;".toCharArray();
        JavaLangDoubleConstantPoolName = "java/lang/Double".toCharArray();
        JavaLangEnumConstantPoolName = "java/lang/Enum".toCharArray();
        JavaLangErrorConstantPoolName = "java/lang/Error".toCharArray();
        JavaLangExceptionConstantPoolName = "java/lang/Exception".toCharArray();
        JavaLangFloatConstantPoolName = "java/lang/Float".toCharArray();
        JavaLangIntegerConstantPoolName = "java/lang/Integer".toCharArray();
        JavaLangLongConstantPoolName = "java/lang/Long".toCharArray();
        JavaLangNoClassDefFoundErrorConstantPoolName = "java/lang/NoClassDefFoundError".toCharArray();
        JavaLangNoSuchFieldErrorConstantPoolName = "java/lang/NoSuchFieldError".toCharArray();
        JavaLangObjectConstantPoolName = TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME.toCharArray();
        JAVALANGREFLECTACCESSIBLEOBJECT_CONSTANTPOOLNAME = "java/lang/reflect/AccessibleObject".toCharArray();
        JAVALANGREFLECTARRAY_CONSTANTPOOLNAME = "java/lang/reflect/Array".toCharArray();
        JavaLangReflectConstructorConstantPoolName = TypeProxy.SilentConstruction.Appender.JAVA_LANG_CONSTRUCTOR_INTERNAL_NAME.toCharArray();
        JavaLangReflectConstructorNewInstanceSignature = TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_DESCRIPTOR.toCharArray();
        JAVALANGREFLECTFIELD_CONSTANTPOOLNAME = "java/lang/reflect/Field".toCharArray();
        JAVALANGREFLECTMETHOD_CONSTANTPOOLNAME = "java/lang/reflect/Method".toCharArray();
        JavaLangShortConstantPoolName = "java/lang/Short".toCharArray();
        JavaLangStringBufferConstantPoolName = "java/lang/StringBuffer".toCharArray();
        JavaLangStringBuilderConstantPoolName = "java/lang/StringBuilder".toCharArray();
        JavaLangStringConstantPoolName = "java/lang/String".toCharArray();
        JavaLangStringSignature = "Ljava/lang/String;".toCharArray();
        JavaLangObjectSignature = TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR.toCharArray();
        JavaLangSystemConstantPoolName = "java/lang/System".toCharArray();
        JavaLangThrowableConstantPoolName = "java/lang/Throwable".toCharArray();
        JavaLangIllegalArgumentExceptionConstantPoolName = "java/lang/IllegalArgumentException".toCharArray();
        JavaLangVoidConstantPoolName = "java/lang/Void".toCharArray();
        JavaUtilIteratorConstantPoolName = "java/util/Iterator".toCharArray();
        LongConstrSignature = "(J)V".toCharArray();
        longLongSignature = "(J)Ljava/lang/Long;".toCharArray();
        LONGVALUE_LONG_METHOD_NAME = "longValue".toCharArray();
        LONGVALUE_LONG_METHOD_SIGNATURE = "()J".toCharArray();
        Name = "name".toCharArray();
        NewInstance = TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME.toCharArray();
        NewInstanceSignature = "(Ljava/lang/Class;[I)Ljava/lang/Object;".toCharArray();
        Next = ES6Iterator.NEXT_METHOD.toCharArray();
        NextSignature = "()Ljava/lang/Object;".toCharArray();
        ObjectConstrSignature = ReifiedTypeInliner.pluginIntrinsicsMarkerSignature.toCharArray();
        ObjectSignature = TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR.toCharArray();
        Ordinal = "ordinal".toCharArray();
        OrdinalSignature = "()I".toCharArray();
        Out = "out".toCharArray();
        SET_BOOLEAN_METHOD_NAME = "setBoolean".toCharArray();
        SET_BOOLEAN_METHOD_SIGNATURE = "(Ljava/lang/Object;Z)V".toCharArray();
        SET_BYTE_METHOD_NAME = "setByte".toCharArray();
        SET_BYTE_METHOD_SIGNATURE = "(Ljava/lang/Object;B)V".toCharArray();
        SET_CHAR_METHOD_NAME = "setChar".toCharArray();
        SET_CHAR_METHOD_SIGNATURE = "(Ljava/lang/Object;C)V".toCharArray();
        SET_DOUBLE_METHOD_NAME = "setDouble".toCharArray();
        SET_DOUBLE_METHOD_SIGNATURE = "(Ljava/lang/Object;D)V".toCharArray();
        SET_FLOAT_METHOD_NAME = "setFloat".toCharArray();
        SET_FLOAT_METHOD_SIGNATURE = "(Ljava/lang/Object;F)V".toCharArray();
        SET_INT_METHOD_NAME = "setInt".toCharArray();
        SET_INT_METHOD_SIGNATURE = "(Ljava/lang/Object;I)V".toCharArray();
        SET_LONG_METHOD_NAME = "setLong".toCharArray();
        SET_LONG_METHOD_SIGNATURE = "(Ljava/lang/Object;J)V".toCharArray();
        SET_OBJECT_METHOD_NAME = FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX.toCharArray();
        SET_OBJECT_METHOD_SIGNATURE = "(Ljava/lang/Object;Ljava/lang/Object;)V".toCharArray();
        SET_SHORT_METHOD_NAME = "setShort".toCharArray();
        SET_SHORT_METHOD_SIGNATURE = "(Ljava/lang/Object;S)V".toCharArray();
        SETACCESSIBLE_NAME = "setAccessible".toCharArray();
        SETACCESSIBLE_SIGNATURE = "(Z)V".toCharArray();
        ShortConstrSignature = "(S)V".toCharArray();
        shortShortSignature = "(S)Ljava/lang/Short;".toCharArray();
        SHORTVALUE_SHORT_METHOD_NAME = "shortValue".toCharArray();
        SHORTVALUE_SHORT_METHOD_SIGNATURE = "()S".toCharArray();
        StringBufferAppendBooleanSignature = "(Z)Ljava/lang/StringBuffer;".toCharArray();
        StringBufferAppendCharSignature = "(C)Ljava/lang/StringBuffer;".toCharArray();
        StringBufferAppendDoubleSignature = "(D)Ljava/lang/StringBuffer;".toCharArray();
        StringBufferAppendFloatSignature = "(F)Ljava/lang/StringBuffer;".toCharArray();
        StringBufferAppendIntSignature = "(I)Ljava/lang/StringBuffer;".toCharArray();
        StringBufferAppendLongSignature = "(J)Ljava/lang/StringBuffer;".toCharArray();
        StringBufferAppendObjectSignature = "(Ljava/lang/Object;)Ljava/lang/StringBuffer;".toCharArray();
        StringBufferAppendStringSignature = "(Ljava/lang/String;)Ljava/lang/StringBuffer;".toCharArray();
        StringBuilderAppendBooleanSignature = "(Z)Ljava/lang/StringBuilder;".toCharArray();
        StringBuilderAppendCharSignature = "(C)Ljava/lang/StringBuilder;".toCharArray();
        StringBuilderAppendDoubleSignature = "(D)Ljava/lang/StringBuilder;".toCharArray();
        StringBuilderAppendFloatSignature = "(F)Ljava/lang/StringBuilder;".toCharArray();
        StringBuilderAppendIntSignature = "(I)Ljava/lang/StringBuilder;".toCharArray();
        StringBuilderAppendLongSignature = "(J)Ljava/lang/StringBuilder;".toCharArray();
        StringBuilderAppendObjectSignature = "(Ljava/lang/Object;)Ljava/lang/StringBuilder;".toCharArray();
        StringBuilderAppendStringSignature = "(Ljava/lang/String;)Ljava/lang/StringBuilder;".toCharArray();
        StringConstructorSignature = "(Ljava/lang/String;)V".toCharArray();
        This = "this".toCharArray();
        ToString = "toString".toCharArray();
        ToStringSignature = charArray3;
        TYPE = "TYPE".toCharArray();
        ValueOf = "valueOf".toCharArray();
        ValueOfBooleanSignature = "(Z)Ljava/lang/String;".toCharArray();
        ValueOfCharSignature = "(C)Ljava/lang/String;".toCharArray();
        ValueOfDoubleSignature = "(D)Ljava/lang/String;".toCharArray();
        ValueOfFloatSignature = "(F)Ljava/lang/String;".toCharArray();
        ValueOfIntSignature = "(I)Ljava/lang/String;".toCharArray();
        ValueOfLongSignature = "(J)Ljava/lang/String;".toCharArray();
        ValueOfObjectSignature = "(Ljava/lang/Object;)Ljava/lang/String;".toCharArray();
        ValueOfStringClassSignature = "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;".toCharArray();
        JAVA_LANG_ANNOTATION_DOCUMENTED = "Ljava/lang/annotation/Documented;".toCharArray();
        JAVA_LANG_ANNOTATION_ELEMENTTYPE = "Ljava/lang/annotation/ElementType;".toCharArray();
        JAVA_LANG_ANNOTATION_RETENTION = "Ljava/lang/annotation/Retention;".toCharArray();
        JAVA_LANG_ANNOTATION_RETENTIONPOLICY = "Ljava/lang/annotation/RetentionPolicy;".toCharArray();
        JAVA_LANG_ANNOTATION_TARGET = "Ljava/lang/annotation/Target;".toCharArray();
        JAVA_LANG_DEPRECATED = "Ljava/lang/Deprecated;".toCharArray();
        JAVA_LANG_ANNOTATION_INHERITED = "Ljava/lang/annotation/Inherited;".toCharArray();
        JAVA_LANG_SAFEVARARGS = "Ljava/lang/SafeVarargs;".toCharArray();
        JAVA_LANG_INVOKE_METHODHANDLE_POLYMORPHICSIGNATURE = "Ljava/lang/invoke/MethodHandle$PolymorphicSignature;".toCharArray();
        METAFACTORY = "metafactory".toCharArray();
        JAVA_LANG_INVOKE_LAMBDAMETAFACTORY_METAFACTORY_SIGNATURE = "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;".toCharArray();
        ALTMETAFACTORY = "altMetafactory".toCharArray();
        JAVA_LANG_INVOKE_LAMBDAMETAFACTORY_ALTMETAFACTORY_SIGNATURE = "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite;".toCharArray();
        JavaLangInvokeSerializedLambda = "Ljava/lang/invoke/SerializedLambda;".toCharArray();
        JavaLangInvokeSerializedLambdaConstantPoolName = "java/lang/invoke/SerializedLambda".toCharArray();
        GetImplMethodName = "getImplMethodName".toCharArray();
        GetImplMethodNameSignature = "()Ljava/lang/String;".toCharArray();
        GetImplMethodKind = "getImplMethodKind".toCharArray();
        GetImplMethodKindSignature = "()I".toCharArray();
        GetFunctionalInterfaceClass = "getFunctionalInterfaceClass".toCharArray();
        GetFunctionalInterfaceClassSignature = "()Ljava/lang/String;".toCharArray();
        GetFunctionalInterfaceMethodName = "getFunctionalInterfaceMethodName".toCharArray();
        GetFunctionalInterfaceMethodNameSignature = "()Ljava/lang/String;".toCharArray();
        GetFunctionalInterfaceMethodSignature = "getFunctionalInterfaceMethodSignature".toCharArray();
        GetFunctionalInterfaceMethodSignatureSignature = "()Ljava/lang/String;".toCharArray();
        GetImplClass = "getImplClass".toCharArray();
        GetImplClassSignature = "()Ljava/lang/String;".toCharArray();
        GetImplMethodSignature = "getImplMethodSignature".toCharArray();
        GetImplMethodSignatureSignature = "()Ljava/lang/String;".toCharArray();
        GetCapturedArg = "getCapturedArg".toCharArray();
        GetCapturedArgSignature = "(I)Ljava/lang/Object;".toCharArray();
        JAVA_LANG_ANNOTATION_REPEATABLE = "Ljava/lang/annotation/Repeatable;".toCharArray();
        HashCode = "hashCode".toCharArray();
        HashCodeSignature = "()I".toCharArray();
        Equals = Namer.EQUALS_METHOD_NAME.toCharArray();
        EqualsSignature = "(Ljava/lang/Object;)Z".toCharArray();
        AddSuppressed = "addSuppressed".toCharArray();
        AddSuppressedSignature = "(Ljava/lang/Throwable;)V".toCharArray();
        Clone = "clone".toCharArray();
        CloneSignature = "()Ljava/lang/Object;".toCharArray();
    }

    public ConstantPool(ClassFile classFile) {
        initialize(classFile);
    }

    public void initialize(ClassFile classFile) {
        this.poolContent = classFile.header;
        this.currentOffset = classFile.headerOffset;
        this.currentIndex = 1;
        this.classFile = classFile;
    }

    public byte[] dumpBytes() {
        byte[] bArr = this.poolContent;
        int i = this.currentOffset;
        byte[] bArr2 = new byte[i];
        this.poolContent = bArr2;
        System.arraycopy(bArr, 0, bArr2, 0, i);
        return this.poolContent;
    }

    public int literalIndex(byte[] bArr, char[] cArr) {
        int iPutIfAbsent = this.UTF8Cache.putIfAbsent(cArr, this.currentIndex);
        if (iPutIfAbsent < 0) {
            iPutIfAbsent = -iPutIfAbsent;
            if (iPutIfAbsent > 65535) {
                this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
            }
            this.currentIndex++;
            int[] iArr = this.offsets;
            int length = iArr.length;
            if (length <= iPutIfAbsent) {
                int[] iArr2 = new int[iPutIfAbsent * 2];
                this.offsets = iArr2;
                System.arraycopy(iArr, 0, iArr2, 0, length);
            }
            this.offsets[iPutIfAbsent] = this.currentOffset;
            writeU1(1);
            int length2 = bArr.length;
            if (this.currentOffset + 2 + length2 >= this.poolContent.length) {
                resizePoolContents(length2 + 2);
            }
            byte[] bArr2 = this.poolContent;
            int i = this.currentOffset;
            int i2 = i + 1;
            this.currentOffset = i2;
            bArr2[i] = (byte) (length2 >> 8);
            int i3 = i + 2;
            this.currentOffset = i3;
            bArr2[i2] = (byte) length2;
            System.arraycopy(bArr, 0, bArr2, i3, length2);
            this.currentOffset += length2;
        }
        return iPutIfAbsent;
    }

    public int literalIndex(TypeBinding typeBinding) {
        TypeBinding typeBindingLeafComponentType = typeBinding.leafComponentType();
        if ((typeBindingLeafComponentType.tagBits & 2048) != 0) {
            Util.recordNestedType(this.classFile, typeBindingLeafComponentType);
        }
        return literalIndex(typeBinding.signature());
    }

    public int literalIndex(char[] cArr) {
        int iPutIfAbsent = this.UTF8Cache.putIfAbsent(cArr, this.currentIndex);
        if (iPutIfAbsent >= 0) {
            return iPutIfAbsent;
        }
        int i = -iPutIfAbsent;
        if (i > 65535) {
            this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
        }
        int[] iArr = this.offsets;
        int length = iArr.length;
        if (length <= i) {
            int[] iArr2 = new int[i * 2];
            this.offsets = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, length);
        }
        this.offsets[i] = this.currentOffset;
        writeU1(1);
        int i2 = this.currentOffset;
        if (i2 + 2 >= this.poolContent.length) {
            resizePoolContents(2);
        }
        this.currentOffset += 2;
        int i3 = 0;
        for (char c : cArr) {
            if (c >= 1 && c <= 127) {
                writeU1(c);
                i3++;
            } else if (c > 2047) {
                i3 += 3;
                writeU1(((c >> '\f') & 15) | 224);
                writeU1(((c >> 6) & 63) | 128);
                writeU1((c & MangleConstant.Q_MARK) | 128);
            } else {
                i3 += 2;
                writeU1(((c >> 6) & 31) | 192);
                writeU1((c & MangleConstant.Q_MARK) | 128);
            }
        }
        if (i3 >= 65535) {
            this.currentOffset = i2 - 1;
            this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceForConstant(this.classFile.referenceBinding.scope.referenceType());
        }
        if (i > 65535) {
            this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
        }
        this.currentIndex++;
        byte[] bArr = this.poolContent;
        bArr[i2] = (byte) (i3 >> 8);
        bArr[i2 + 1] = (byte) i3;
        return i;
    }

    public int literalIndex(char[] cArr, byte[] bArr) {
        int iPutIfAbsent = this.stringCache.putIfAbsent(cArr, this.currentIndex);
        if (iPutIfAbsent < 0) {
            this.currentIndex++;
            iPutIfAbsent = -iPutIfAbsent;
            if (iPutIfAbsent > 65535) {
                this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
            }
            int[] iArr = this.offsets;
            int length = iArr.length;
            if (length <= iPutIfAbsent) {
                int[] iArr2 = new int[iPutIfAbsent * 2];
                this.offsets = iArr2;
                System.arraycopy(iArr, 0, iArr2, 0, length);
            }
            this.offsets[iPutIfAbsent] = this.currentOffset;
            writeU1(8);
            int i = this.currentOffset;
            if (i + 2 >= this.poolContent.length) {
                resizePoolContents(2);
            }
            this.currentOffset += 2;
            int iLiteralIndex = literalIndex(bArr, cArr);
            byte[] bArr2 = this.poolContent;
            bArr2[i] = (byte) (iLiteralIndex >> 8);
            bArr2[i + 1] = (byte) iLiteralIndex;
        }
        return iPutIfAbsent;
    }

    public int literalIndex(double d) {
        if (this.doubleCache == null) {
            this.doubleCache = new DoubleCache(5);
        }
        int iPutIfAbsent = this.doubleCache.putIfAbsent(d, this.currentIndex);
        if (iPutIfAbsent < 0) {
            iPutIfAbsent = -iPutIfAbsent;
            if (iPutIfAbsent > 65535) {
                this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
            }
            this.currentIndex += 2;
            int[] iArr = this.offsets;
            int length = iArr.length;
            if (length <= iPutIfAbsent) {
                int[] iArr2 = new int[iPutIfAbsent * 2];
                this.offsets = iArr2;
                System.arraycopy(iArr, 0, iArr2, 0, length);
            }
            this.offsets[iPutIfAbsent] = this.currentOffset;
            writeU1(6);
            long jDoubleToLongBits = Double.doubleToLongBits(d);
            if (this.currentOffset + 8 >= this.poolContent.length) {
                resizePoolContents(8);
            }
            byte[] bArr = this.poolContent;
            int i = this.currentOffset;
            int i2 = i + 1;
            this.currentOffset = i2;
            bArr[i] = (byte) (jDoubleToLongBits >>> 56);
            int i3 = i + 2;
            this.currentOffset = i3;
            bArr[i2] = (byte) (jDoubleToLongBits >>> 48);
            int i4 = i + 3;
            this.currentOffset = i4;
            bArr[i3] = (byte) (jDoubleToLongBits >>> 40);
            int i5 = i + 4;
            this.currentOffset = i5;
            bArr[i4] = (byte) (jDoubleToLongBits >>> 32);
            int i6 = i + 5;
            this.currentOffset = i6;
            bArr[i5] = (byte) (jDoubleToLongBits >>> 24);
            int i7 = i + 6;
            this.currentOffset = i7;
            bArr[i6] = (byte) (jDoubleToLongBits >>> 16);
            int i8 = i + 7;
            this.currentOffset = i8;
            bArr[i7] = (byte) (jDoubleToLongBits >>> 8);
            this.currentOffset = i + 8;
            bArr[i8] = (byte) jDoubleToLongBits;
        }
        return iPutIfAbsent;
    }

    public int literalIndex(float f) {
        if (this.floatCache == null) {
            this.floatCache = new FloatCache(3);
        }
        int iPutIfAbsent = this.floatCache.putIfAbsent(f, this.currentIndex);
        if (iPutIfAbsent < 0) {
            iPutIfAbsent = -iPutIfAbsent;
            if (iPutIfAbsent > 65535) {
                this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
            }
            this.currentIndex++;
            int[] iArr = this.offsets;
            int length = iArr.length;
            if (length <= iPutIfAbsent) {
                int[] iArr2 = new int[iPutIfAbsent * 2];
                this.offsets = iArr2;
                System.arraycopy(iArr, 0, iArr2, 0, length);
            }
            this.offsets[iPutIfAbsent] = this.currentOffset;
            writeU1(4);
            int iFloatToIntBits = Float.floatToIntBits(f);
            if (this.currentOffset + 4 >= this.poolContent.length) {
                resizePoolContents(4);
            }
            byte[] bArr = this.poolContent;
            int i = this.currentOffset;
            int i2 = i + 1;
            this.currentOffset = i2;
            bArr[i] = (byte) (iFloatToIntBits >>> 24);
            int i3 = i + 2;
            this.currentOffset = i3;
            bArr[i2] = (byte) (iFloatToIntBits >>> 16);
            int i4 = i + 3;
            this.currentOffset = i4;
            bArr[i3] = (byte) (iFloatToIntBits >>> 8);
            this.currentOffset = i + 4;
            bArr[i4] = (byte) iFloatToIntBits;
        }
        return iPutIfAbsent;
    }

    public int literalIndex(int i) {
        if (this.intCache == null) {
            this.intCache = new IntegerCache(248);
        }
        int iPutIfAbsent = this.intCache.putIfAbsent(i, this.currentIndex);
        if (iPutIfAbsent < 0) {
            this.currentIndex++;
            iPutIfAbsent = -iPutIfAbsent;
            if (iPutIfAbsent > 65535) {
                this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
            }
            int[] iArr = this.offsets;
            int length = iArr.length;
            if (length <= iPutIfAbsent) {
                int[] iArr2 = new int[iPutIfAbsent * 2];
                this.offsets = iArr2;
                System.arraycopy(iArr, 0, iArr2, 0, length);
            }
            this.offsets[iPutIfAbsent] = this.currentOffset;
            writeU1(3);
            if (this.currentOffset + 4 >= this.poolContent.length) {
                resizePoolContents(4);
            }
            byte[] bArr = this.poolContent;
            int i2 = this.currentOffset;
            int i3 = i2 + 1;
            this.currentOffset = i3;
            bArr[i2] = (byte) (i >>> 24);
            int i4 = i2 + 2;
            this.currentOffset = i4;
            bArr[i3] = (byte) (i >>> 16);
            int i5 = i2 + 3;
            this.currentOffset = i5;
            bArr[i4] = (byte) (i >>> 8);
            this.currentOffset = i2 + 4;
            bArr[i5] = (byte) i;
        }
        return iPutIfAbsent;
    }

    public int literalIndex(long j) {
        if (this.longCache == null) {
            this.longCache = new LongCache(5);
        }
        int iPutIfAbsent = this.longCache.putIfAbsent(j, this.currentIndex);
        if (iPutIfAbsent < 0) {
            iPutIfAbsent = -iPutIfAbsent;
            if (iPutIfAbsent > 65535) {
                this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
            }
            this.currentIndex += 2;
            int[] iArr = this.offsets;
            int length = iArr.length;
            if (length <= iPutIfAbsent) {
                int[] iArr2 = new int[iPutIfAbsent * 2];
                this.offsets = iArr2;
                System.arraycopy(iArr, 0, iArr2, 0, length);
            }
            this.offsets[iPutIfAbsent] = this.currentOffset;
            writeU1(5);
            if (this.currentOffset + 8 >= this.poolContent.length) {
                resizePoolContents(8);
            }
            byte[] bArr = this.poolContent;
            int i = this.currentOffset;
            int i2 = i + 1;
            this.currentOffset = i2;
            bArr[i] = (byte) (j >>> 56);
            int i3 = i + 2;
            this.currentOffset = i3;
            bArr[i2] = (byte) (j >>> 48);
            int i4 = i + 3;
            this.currentOffset = i4;
            bArr[i3] = (byte) (j >>> 40);
            int i5 = i + 4;
            this.currentOffset = i5;
            bArr[i4] = (byte) (j >>> 32);
            int i6 = i + 5;
            this.currentOffset = i6;
            bArr[i5] = (byte) (j >>> 24);
            int i7 = i + 6;
            this.currentOffset = i7;
            bArr[i6] = (byte) (j >>> 16);
            int i8 = i + 7;
            this.currentOffset = i8;
            bArr[i7] = (byte) (j >>> 8);
            this.currentOffset = i + 8;
            bArr[i8] = (byte) j;
        }
        return iPutIfAbsent;
    }

    public int literalIndex(String str) {
        char[] charArray = str.toCharArray();
        int iPutIfAbsent = this.stringCache.putIfAbsent(charArray, this.currentIndex);
        if (iPutIfAbsent < 0) {
            this.currentIndex++;
            iPutIfAbsent = -iPutIfAbsent;
            if (iPutIfAbsent > 65535) {
                this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
            }
            int[] iArr = this.offsets;
            int length = iArr.length;
            if (length <= iPutIfAbsent) {
                int[] iArr2 = new int[iPutIfAbsent * 2];
                this.offsets = iArr2;
                System.arraycopy(iArr, 0, iArr2, 0, length);
            }
            this.offsets[iPutIfAbsent] = this.currentOffset;
            writeU1(8);
            int i = this.currentOffset;
            if (i + 2 >= this.poolContent.length) {
                resizePoolContents(2);
            }
            this.currentOffset += 2;
            int iLiteralIndex = literalIndex(charArray);
            byte[] bArr = this.poolContent;
            bArr[i] = (byte) (iLiteralIndex >> 8);
            bArr[i + 1] = (byte) iLiteralIndex;
        }
        return iPutIfAbsent;
    }

    public int literalIndexForType(char[] cArr) {
        int iPutIfAbsent = this.classCache.putIfAbsent(cArr, this.currentIndex);
        if (iPutIfAbsent < 0) {
            this.currentIndex++;
            iPutIfAbsent = -iPutIfAbsent;
            if (iPutIfAbsent > 65535) {
                this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
            }
            int[] iArr = this.offsets;
            int length = iArr.length;
            if (length <= iPutIfAbsent) {
                int[] iArr2 = new int[iPutIfAbsent * 2];
                this.offsets = iArr2;
                System.arraycopy(iArr, 0, iArr2, 0, length);
            }
            this.offsets[iPutIfAbsent] = this.currentOffset;
            writeU1(7);
            int i = this.currentOffset;
            if (i + 2 >= this.poolContent.length) {
                resizePoolContents(2);
            }
            this.currentOffset += 2;
            int iLiteralIndex = literalIndex(cArr);
            byte[] bArr = this.poolContent;
            bArr[i] = (byte) (iLiteralIndex >> 8);
            bArr[i + 1] = (byte) iLiteralIndex;
        }
        return iPutIfAbsent;
    }

    public int literalIndexForType(TypeBinding typeBinding) {
        TypeBinding typeBindingLeafComponentType = typeBinding.leafComponentType();
        if ((typeBindingLeafComponentType.tagBits & 2048) != 0) {
            Util.recordNestedType(this.classFile, typeBindingLeafComponentType);
        }
        return literalIndexForType(typeBinding.constantPoolName());
    }

    public int literalIndexForMethod(char[] cArr, char[] cArr2, char[] cArr3, boolean z) {
        int iPutInCacheIfAbsent = putInCacheIfAbsent(cArr, cArr2, cArr3, this.currentIndex);
        if (iPutInCacheIfAbsent < 0) {
            this.currentIndex++;
            iPutInCacheIfAbsent = -iPutInCacheIfAbsent;
            if (iPutInCacheIfAbsent > 65535) {
                this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
            }
            int[] iArr = this.offsets;
            int length = iArr.length;
            if (length <= iPutInCacheIfAbsent) {
                int[] iArr2 = new int[iPutInCacheIfAbsent * 2];
                this.offsets = iArr2;
                System.arraycopy(iArr, 0, iArr2, 0, length);
            }
            this.offsets[iPutInCacheIfAbsent] = this.currentOffset;
            writeU1(z ? 11 : 10);
            int i = this.currentOffset;
            if (i + 4 >= this.poolContent.length) {
                resizePoolContents(4);
            }
            this.currentOffset += 4;
            int iLiteralIndexForType = literalIndexForType(cArr);
            int iLiteralIndexForNameAndType = literalIndexForNameAndType(cArr2, cArr3);
            byte[] bArr = this.poolContent;
            bArr[i] = (byte) (iLiteralIndexForType >> 8);
            bArr[i + 1] = (byte) iLiteralIndexForType;
            bArr[i + 2] = (byte) (iLiteralIndexForNameAndType >> 8);
            bArr[i + 3] = (byte) iLiteralIndexForNameAndType;
        }
        return iPutInCacheIfAbsent;
    }

    public int literalIndexForMethod(TypeBinding typeBinding, char[] cArr, char[] cArr2, boolean z) {
        if ((typeBinding.tagBits & 2048) != 0) {
            Util.recordNestedType(this.classFile, typeBinding);
        }
        return literalIndexForMethod(typeBinding.constantPoolName(), cArr, cArr2, z);
    }

    public int literalIndexForNameAndType(char[] cArr, char[] cArr2) {
        int iPutInNameAndTypeCacheIfAbsent = putInNameAndTypeCacheIfAbsent(cArr, cArr2, this.currentIndex);
        if (iPutInNameAndTypeCacheIfAbsent < 0) {
            this.currentIndex++;
            iPutInNameAndTypeCacheIfAbsent = -iPutInNameAndTypeCacheIfAbsent;
            if (iPutInNameAndTypeCacheIfAbsent > 65535) {
                this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
            }
            int[] iArr = this.offsets;
            int length = iArr.length;
            if (length <= iPutInNameAndTypeCacheIfAbsent) {
                int[] iArr2 = new int[iPutInNameAndTypeCacheIfAbsent * 2];
                this.offsets = iArr2;
                System.arraycopy(iArr, 0, iArr2, 0, length);
            }
            this.offsets[iPutInNameAndTypeCacheIfAbsent] = this.currentOffset;
            writeU1(12);
            int i = this.currentOffset;
            if (i + 4 >= this.poolContent.length) {
                resizePoolContents(4);
            }
            this.currentOffset += 4;
            int iLiteralIndex = literalIndex(cArr);
            int iLiteralIndex2 = literalIndex(cArr2);
            byte[] bArr = this.poolContent;
            bArr[i] = (byte) (iLiteralIndex >> 8);
            bArr[i + 1] = (byte) iLiteralIndex;
            bArr[i + 2] = (byte) (iLiteralIndex2 >> 8);
            bArr[i + 3] = (byte) iLiteralIndex2;
        }
        return iPutInNameAndTypeCacheIfAbsent;
    }

    /* JADX WARN: Code duplicated, block: B:6:0x0010  */
    public int literalIndexForMethodHandle(MethodBinding methodBinding) {
        int i;
        boolean zIsInterface = methodBinding.declaringClass.isInterface();
        int i2 = 7;
        if (zIsInterface) {
            if (methodBinding.isStatic()) {
                i = 6;
            } else {
                if (!methodBinding.isPrivate()) {
                    i2 = 9;
                }
                i = i2;
            }
        } else {
            if (methodBinding.isConstructor()) {
                i2 = 8;
            } else if (methodBinding.isStatic()) {
                i = 6;
            } else if (!methodBinding.isPrivate()) {
                i2 = 5;
            }
            i = i2;
        }
        return literalIndexForMethodHandle(i, methodBinding.declaringClass, methodBinding.selector, methodBinding.signature(), zIsInterface);
    }

    public int literalIndexForMethodHandle(int i, TypeBinding typeBinding, char[] cArr, char[] cArr2, boolean z) {
        int iLiteralIndexForMethod = literalIndexForMethod(typeBinding, cArr, cArr2, z);
        int i2 = this.currentIndex;
        this.currentIndex = i2 + 1;
        int[] iArr = this.offsets;
        int length = iArr.length;
        if (length <= i2) {
            int[] iArr2 = new int[i2 * 2];
            this.offsets = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, length);
        }
        this.offsets[i2] = this.currentOffset;
        writeU1(15);
        writeU1(i);
        writeU2(iLiteralIndexForMethod);
        return i2;
    }

    public int literalIndexForMethodType(char[] cArr) {
        int iLiteralIndex = literalIndex(cArr);
        int i = this.currentIndex;
        this.currentIndex = i + 1;
        int[] iArr = this.offsets;
        int length = iArr.length;
        if (length <= i) {
            int[] iArr2 = new int[i * 2];
            this.offsets = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, length);
        }
        this.offsets[i] = this.currentOffset;
        writeU1(16);
        writeU2(iLiteralIndex);
        return i;
    }

    public int literalIndexForInvokeDynamic(int i, char[] cArr, char[] cArr2) {
        int iLiteralIndexForNameAndType = literalIndexForNameAndType(cArr, cArr2);
        int i2 = this.currentIndex;
        this.currentIndex = i2 + 1;
        int[] iArr = this.offsets;
        int length = iArr.length;
        if (length <= i2) {
            int[] iArr2 = new int[i2 * 2];
            this.offsets = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, length);
        }
        this.offsets[i2] = this.currentOffset;
        writeU1(18);
        writeU2(i);
        writeU2(iLiteralIndexForNameAndType);
        return i2;
    }

    public int literalIndexForField(char[] cArr, char[] cArr2, char[] cArr3) {
        int iPutInCacheIfAbsent = putInCacheIfAbsent(cArr, cArr2, cArr3, this.currentIndex);
        if (iPutInCacheIfAbsent < 0) {
            this.currentIndex++;
            iPutInCacheIfAbsent = -iPutInCacheIfAbsent;
            if (iPutInCacheIfAbsent > 65535) {
                this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
            }
            int[] iArr = this.offsets;
            int length = iArr.length;
            if (length <= iPutInCacheIfAbsent) {
                int[] iArr2 = new int[iPutInCacheIfAbsent * 2];
                this.offsets = iArr2;
                System.arraycopy(iArr, 0, iArr2, 0, length);
            }
            this.offsets[iPutInCacheIfAbsent] = this.currentOffset;
            writeU1(9);
            int i = this.currentOffset;
            if (i + 4 >= this.poolContent.length) {
                resizePoolContents(4);
            }
            this.currentOffset += 4;
            int iLiteralIndexForType = literalIndexForType(cArr);
            int iLiteralIndexForNameAndType = literalIndexForNameAndType(cArr2, cArr3);
            byte[] bArr = this.poolContent;
            bArr[i] = (byte) (iLiteralIndexForType >> 8);
            bArr[i + 1] = (byte) iLiteralIndexForType;
            bArr[i + 2] = (byte) (iLiteralIndexForNameAndType >> 8);
            bArr[i + 3] = (byte) iLiteralIndexForNameAndType;
        }
        return iPutInCacheIfAbsent;
    }

    public int literalIndexForLdc(char[] cArr) {
        int i = this.currentIndex;
        int i2 = this.currentOffset;
        int iPutIfAbsent = this.stringCache.putIfAbsent(cArr, i);
        if (iPutIfAbsent < 0) {
            iPutIfAbsent = -iPutIfAbsent;
            int i3 = 65535;
            if (iPutIfAbsent > 65535) {
                this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
            }
            char c = 1;
            this.currentIndex++;
            int[] iArr = this.offsets;
            int length = iArr.length;
            int i4 = 0;
            if (length <= iPutIfAbsent) {
                int[] iArr2 = new int[iPutIfAbsent * 2];
                this.offsets = iArr2;
                System.arraycopy(iArr, 0, iArr2, 0, length);
            }
            this.offsets[iPutIfAbsent] = this.currentOffset;
            writeU1(8);
            int i5 = this.currentOffset;
            if (i5 + 2 >= this.poolContent.length) {
                resizePoolContents(2);
            }
            this.currentOffset += 2;
            int iPutIfAbsent2 = this.UTF8Cache.putIfAbsent(cArr, this.currentIndex);
            if (iPutIfAbsent2 < 0) {
                int i6 = -iPutIfAbsent2;
                if (i6 > 65535) {
                    this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
                }
                this.currentIndex++;
                int[] iArr3 = this.offsets;
                int length2 = iArr3.length;
                if (length2 <= i6) {
                    int[] iArr4 = new int[i6 * 2];
                    this.offsets = iArr4;
                    System.arraycopy(iArr3, 0, iArr4, 0, length2);
                }
                this.offsets[i6] = this.currentOffset;
                writeU1(1);
                int i7 = this.currentOffset;
                if (i7 + 2 >= this.poolContent.length) {
                    resizePoolContents(2);
                }
                this.currentOffset += 2;
                int i8 = 0;
                int i9 = 0;
                while (i8 < cArr.length) {
                    char c2 = cArr[i8];
                    if (c2 >= c && c2 <= 127) {
                        i9++;
                        if (this.currentOffset + c >= this.poolContent.length) {
                            resizePoolContents(c);
                        }
                        byte[] bArr = this.poolContent;
                        int i10 = this.currentOffset;
                        this.currentOffset = i10 + 1;
                        bArr[i10] = (byte) c2;
                    } else if (c2 > 2047) {
                        i9 += 3;
                        if (this.currentOffset + 3 >= this.poolContent.length) {
                            resizePoolContents(3);
                        }
                        byte[] bArr2 = this.poolContent;
                        int i11 = this.currentOffset;
                        int i12 = i11 + 1;
                        this.currentOffset = i12;
                        bArr2[i11] = (byte) (((c2 >> '\f') & 15) | 224);
                        int i13 = i11 + 2;
                        this.currentOffset = i13;
                        bArr2[i12] = (byte) (((c2 >> 6) & 63) | 128);
                        this.currentOffset = i11 + 3;
                        bArr2[i13] = (byte) ((c2 & MangleConstant.Q_MARK) | 128);
                    } else {
                        if (this.currentOffset + 2 >= this.poolContent.length) {
                            resizePoolContents(2);
                        }
                        i9 += 2;
                        byte[] bArr3 = this.poolContent;
                        int i14 = this.currentOffset;
                        int i15 = i14 + 1;
                        this.currentOffset = i15;
                        bArr3[i14] = (byte) (((c2 >> 6) & 31) | 192);
                        this.currentOffset = i14 + 2;
                        bArr3[i15] = (byte) ((c2 & MangleConstant.Q_MARK) | 128);
                    }
                    i8++;
                    i3 = 65535;
                    c = 1;
                    i4 = 0;
                }
                if (i9 >= i3) {
                    this.currentOffset = i2;
                    this.currentIndex = i;
                    this.stringCache.remove(cArr);
                    this.UTF8Cache.remove(cArr);
                    return i4;
                }
                byte[] bArr4 = this.poolContent;
                bArr4[i7] = (byte) (i9 >> 8);
                bArr4[i7 + 1] = (byte) i9;
                iPutIfAbsent2 = i6;
            }
            byte[] bArr5 = this.poolContent;
            bArr5[i5] = (byte) (iPutIfAbsent2 >> 8);
            bArr5[i5 + 1] = (byte) iPutIfAbsent2;
        }
        return iPutIfAbsent;
    }

    private int putInNameAndTypeCacheIfAbsent(char[] cArr, char[] cArr2, int i) {
        Object obj = this.nameAndTypeCacheForFieldsAndMethods.get(cArr);
        if (obj == null) {
            CachedIndexEntry cachedIndexEntry = new CachedIndexEntry(cArr2, i);
            int i2 = -i;
            this.nameAndTypeCacheForFieldsAndMethods.put(cArr, cachedIndexEntry);
            return i2;
        }
        if (obj instanceof CachedIndexEntry) {
            CachedIndexEntry cachedIndexEntry2 = (CachedIndexEntry) obj;
            if (CharOperation.equals(cArr2, cachedIndexEntry2.signature)) {
                return cachedIndexEntry2.index;
            }
            CharArrayCache charArrayCache = new CharArrayCache();
            charArrayCache.putIfAbsent(cachedIndexEntry2.signature, cachedIndexEntry2.index);
            int iPutIfAbsent = charArrayCache.putIfAbsent(cArr2, i);
            this.nameAndTypeCacheForFieldsAndMethods.put(cArr, charArrayCache);
            return iPutIfAbsent;
        }
        return ((CharArrayCache) obj).putIfAbsent(cArr2, i);
    }

    private int putInCacheIfAbsent(char[] cArr, char[] cArr2, char[] cArr3, int i) {
        HashtableOfObject hashtableOfObject = (HashtableOfObject) this.methodsAndFieldsCache.get(cArr);
        if (hashtableOfObject == null) {
            HashtableOfObject hashtableOfObject2 = new HashtableOfObject();
            this.methodsAndFieldsCache.put(cArr, hashtableOfObject2);
            CachedIndexEntry cachedIndexEntry = new CachedIndexEntry(cArr3, i);
            int i2 = -i;
            hashtableOfObject2.put(cArr2, cachedIndexEntry);
            return i2;
        }
        Object obj = hashtableOfObject.get(cArr2);
        if (obj == null) {
            CachedIndexEntry cachedIndexEntry2 = new CachedIndexEntry(cArr3, i);
            int i3 = -i;
            hashtableOfObject.put(cArr2, cachedIndexEntry2);
            return i3;
        }
        if (obj instanceof CachedIndexEntry) {
            CachedIndexEntry cachedIndexEntry3 = (CachedIndexEntry) obj;
            if (CharOperation.equals(cArr3, cachedIndexEntry3.signature)) {
                return cachedIndexEntry3.index;
            }
            CharArrayCache charArrayCache = new CharArrayCache();
            charArrayCache.putIfAbsent(cachedIndexEntry3.signature, cachedIndexEntry3.index);
            int iPutIfAbsent = charArrayCache.putIfAbsent(cArr3, i);
            hashtableOfObject.put(cArr2, charArrayCache);
            return iPutIfAbsent;
        }
        return ((CharArrayCache) obj).putIfAbsent(cArr3, i);
    }

    public void resetForClinit(int i, int i2) {
        this.currentIndex = i;
        this.currentOffset = i2;
        if (this.UTF8Cache.get(AttributeNamesConstants.CodeName) >= i) {
            this.UTF8Cache.remove(AttributeNamesConstants.CodeName);
        }
        CharArrayCache charArrayCache = this.UTF8Cache;
        char[] cArr = ClinitSignature;
        if (charArrayCache.get(cArr) >= i) {
            this.UTF8Cache.remove(cArr);
        }
        CharArrayCache charArrayCache2 = this.UTF8Cache;
        char[] cArr2 = Clinit;
        if (charArrayCache2.get(cArr2) >= i) {
            this.UTF8Cache.remove(cArr2);
        }
    }

    private final void resizePoolContents(int i) {
        byte[] bArr = this.poolContent;
        int length = bArr.length;
        if (length >= i) {
            i = length;
        }
        byte[] bArr2 = new byte[i + length];
        this.poolContent = bArr2;
        System.arraycopy(bArr, 0, bArr2, 0, length);
    }

    protected final void writeU1(int i) {
        if (this.currentOffset + 1 >= this.poolContent.length) {
            resizePoolContents(1);
        }
        byte[] bArr = this.poolContent;
        int i2 = this.currentOffset;
        this.currentOffset = i2 + 1;
        bArr[i2] = (byte) i;
    }

    protected final void writeU2(int i) {
        if (this.currentOffset + 2 >= this.poolContent.length) {
            resizePoolContents(2);
        }
        byte[] bArr = this.poolContent;
        int i2 = this.currentOffset;
        int i3 = i2 + 1;
        this.currentOffset = i3;
        bArr[i2] = (byte) (i >>> 8);
        this.currentOffset = i2 + 2;
        bArr[i3] = (byte) i;
    }

    public void reset() {
        DoubleCache doubleCache = this.doubleCache;
        if (doubleCache != null) {
            doubleCache.clear();
        }
        FloatCache floatCache = this.floatCache;
        if (floatCache != null) {
            floatCache.clear();
        }
        IntegerCache integerCache = this.intCache;
        if (integerCache != null) {
            integerCache.clear();
        }
        LongCache longCache = this.longCache;
        if (longCache != null) {
            longCache.clear();
        }
        this.UTF8Cache.clear();
        this.stringCache.clear();
        this.methodsAndFieldsCache.clear();
        this.classCache.clear();
        this.nameAndTypeCacheForFieldsAndMethods.clear();
        this.currentIndex = 1;
        this.currentOffset = 0;
    }

    public void resetForAttributeName(char[] cArr, int i, int i2) {
        this.currentIndex = i;
        this.currentOffset = i2;
        if (this.UTF8Cache.get(cArr) >= i) {
            this.UTF8Cache.remove(cArr);
        }
    }
}
