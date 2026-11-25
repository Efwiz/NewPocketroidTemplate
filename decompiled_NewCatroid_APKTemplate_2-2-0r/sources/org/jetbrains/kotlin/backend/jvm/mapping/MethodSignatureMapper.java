package org.jetbrains.kotlin.backend.jvm.mapping;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import net.bytebuddy.description.method.MethodDescription;
import org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLoweringKt;
import org.jetbrains.kotlin.backend.jvm.InlineClassAbiKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.JvmCodegenUtil;
import org.jetbrains.kotlin.codegen.signature.BothSignatureWriter;
import org.jetbrains.kotlin.codegen.signature.JvmSignatureWriter;
import org.jetbrains.kotlin.codegen.state.TypeMappingUtil;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.lazy.IrLazyClass;
import org.jetbrains.kotlin.ir.declarations.lazy.IrLazyFunctionBase;
import org.jetbrains.kotlin.ir.declarations.lazy.IrMaybeDeserializedClass;
import org.jetbrains.kotlin.ir.descriptors.IrBasedDescriptorsKt;
import org.jetbrains.kotlin.ir.descriptors.IrBasedSimpleFunctionDescriptor;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContext;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.BuiltinSpecialProperties;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.SpecialBuiltinMembers;
import org.jetbrains.kotlin.load.java.SpecialGenericSignatures;
import org.jetbrains.kotlin.load.kotlin.JvmPackagePartSource;
import org.jetbrains.kotlin.load.kotlin.SignatureBuildingComponents;
import org.jetbrains.kotlin.load.kotlin.TypeMappingMode;
import org.jetbrains.kotlin.load.kotlin.TypeMappingModeExtensionsKt;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.deserialization.ProtoBufUtilKt;
import org.jetbrains.kotlin.metadata.jvm.JvmProtoBuf;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.NameUtils;
import org.jetbrains.kotlin.protobuf.GeneratedMessageLite;
import org.jetbrains.kotlin.resolve.jvm.JdkClassesKt;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodGenericSignature;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterKind;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.org.objectweb.asm.Handle;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: MethodSignatureMapper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000º\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001]B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\nH\u0002J\u0010\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u0017H\u0002J\u0010\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u0017H\u0002J\u0010\u0010\u0019\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u0017H\u0002J\u0012\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u001c\u001a\u00020\u000bH\u0002J\u0012\u0010\u001d\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u0014\u001a\u00020\u001eH\u0002J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0017H\u0002J\u0010\u0010\"\u001a\u00020\u001b2\u0006\u0010\u0014\u001a\u00020\u000bH\u0002J\u0010\u0010#\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u0017H\u0002J\u0010\u0010$\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u0017H\u0002J\u0018\u0010%\u001a\u00020\u001b2\u0006\u0010&\u001a\u00020\u001b2\u0006\u0010\u0014\u001a\u00020\u000bH\u0002J\u000e\u0010'\u001a\u00020(2\u0006\u0010\u0014\u001a\u00020\u0017J\u0018\u0010)\u001a\u00020(2\u0006\u0010\u0014\u001a\u00020\u000b2\b\b\u0002\u0010\u0015\u001a\u00020\nJ\u000e\u0010*\u001a\u00020+2\u0006\u0010\u0014\u001a\u00020\u0017J\u0010\u0010,\u001a\u0004\u0018\u00010\u001b2\u0006\u0010-\u001a\u00020.J\u0018\u0010/\u001a\u00020\u001b2\u0006\u0010\u0014\u001a\u00020\u00172\b\b\u0002\u00100\u001a\u00020\nJ\u001a\u00101\u001a\u0004\u0018\u00010+2\u0006\u00102\u001a\u00020\u00172\u0006\u00103\u001a\u00020\nH\u0002J$\u00104\u001a\u0002052\u0006\u00106\u001a\u00020\u000e2\n\b\u0002\u00107\u001a\u0004\u0018\u0001082\b\b\u0002\u00109\u001a\u00020\nJ,\u00104\u001a\u0002052\u0006\u00106\u001a\u00020\u000e2\u0006\u0010:\u001a\u00020;2\b\u00107\u001a\u0004\u0018\u0001082\b\b\u0002\u00109\u001a\u00020\nH\u0002J,\u0010<\u001a\u00020=2\u0006\u0010\u0014\u001a\u00020\u00172\u0006\u0010>\u001a\u00020\n2\b\b\u0002\u00100\u001a\u00020\n2\b\b\u0002\u00109\u001a\u00020\nH\u0002J\u000e\u0010?\u001a\u00020+2\u0006\u0010\u0014\u001a\u00020\u0017J\u000e\u0010@\u001a\u00020=2\u0006\u0010\u0014\u001a\u00020\u0017J\u0018\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020D2\b\u0010E\u001a\u0004\u0018\u00010\u0017J\u000e\u0010F\u001a\u00020G2\u0006\u0010!\u001a\u00020\u0017J\u000e\u0010H\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u0017J2\u0010I\u001a\u00020J2\u0006\u00107\u001a\u0002082\u0006\u0010K\u001a\u00020L2\u0006\u0010M\u001a\u00020;2\u0006\u0010\u0014\u001a\u00020\u00172\b\b\u0002\u00109\u001a\u00020\nH\u0002J*\u0010N\u001a\u00020J2\u0006\u00107\u001a\u0002082\u0006\u0010M\u001a\u00020;2\u0006\u00106\u001a\u00020\u000e2\b\b\u0002\u00109\u001a\u00020\nH\u0002J\f\u0010O\u001a\u00020\u001b*\u00020\u0017H\u0002J\u000e\u0010P\u001a\u0004\u0018\u00010\u001b*\u00020\u000bH\u0002J\u000e\u0010Q\u001a\u0004\u0018\u00010\u001b*\u00020\u000bH\u0002J\u000e\u0010R\u001a\u0004\u0018\u00010\u000b*\u00020\u000bH\u0002J\u0013\u0010S\u001a\u0004\u0018\u00010\n*\u00020TH\u0002¢\u0006\u0002\u0010UJ\f\u0010V\u001a\u00020\n*\u00020\u000bH\u0002J\f\u0010W\u001a\u00020\n*\u00020;H\u0002J\f\u0010X\u001a\u00020\n*\u00020\u000bH\u0002J\u0013\u0010Y\u001a\u0004\u0018\u00010\n*\u00020\u000eH\u0002¢\u0006\u0002\u0010ZJ\f\u0010[\u001a\u00020\\*\u00020\u0017H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\t\u001a\u00020\n*\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\fR\u0018\u0010\r\u001a\u00020\n*\u00020\u000e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000fR\u001a\u0010\u0010\u001a\u0004\u0018\u00010\u000b*\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012¨\u0006^"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/mapping/MethodSignatureMapper;", "", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "typeMapper", "Lorg/jetbrains/kotlin/backend/jvm/mapping/IrTypeMapper;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;Lorg/jetbrains/kotlin/backend/jvm/mapping/IrTypeMapper;)V", "typeSystem", "Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "isBuiltIn", "", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)Z", "isMethodWithDeclarationSiteWildcards", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Z", "originalForDefaultAdapter", "getOriginalForDefaultAdapter", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "findSuperDeclaration", "function", "isSuperCall", "forceBoxedReturnType", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "forceBoxedReturnTypeOnDefaultImplFun", "forceFoxedReturnTypeOnOverride", "getJvmMethodNameIfSpecial", "", "irFunction", "getJvmModuleNameForDeserialized", "Lorg/jetbrains/kotlin/ir/declarations/lazy/IrLazyFunctionBase;", "getMethodKindTag", "", "irFun", "getModuleName", "hasVoidReturnType", "isBoxMethodForInlineClass", "mangleMemberNameIfRequired", "name", "mapAsmMethod", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "mapCalleeToAsmMethod", "mapFakeOverrideSignatureSkipGeneric", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "mapFieldSignature", "field", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "mapFunctionName", "skipSpecial", "mapOverriddenSpecialBuiltinIfNeeded", "callee", "superCall", "mapReturnType", "Lorg/jetbrains/org/objectweb/asm/Type;", "declaration", "sw", "Lorg/jetbrains/kotlin/codegen/signature/JvmSignatureWriter;", "materialized", "returnType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "mapSignature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodGenericSignature;", "skipGenericSignature", "mapSignatureSkipGeneric", "mapSignatureWithGeneric", "mapToCallableMethod", "Lorg/jetbrains/kotlin/backend/jvm/mapping/IrCallableMethod;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "caller", "mapToMethodHandle", "Lorg/jetbrains/org/objectweb/asm/Handle;", "shouldBoxSingleValueParameterForSpecialCaseOfRemove", "writeParameter", "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodParameterKind;", ModuleXmlParser.TYPE, "writeParameterType", "computeJvmSignature", "getBuiltinSpecialPropertyGetterName", "getDifferentNameForJvmBuiltinFunction", "getInternalFunctionForManglingIfNeeded", "getSuppressWildcardsAnnotationValue", "Lorg/jetbrains/kotlin/ir/declarations/IrAnnotationContainer;", "(Lorg/jetbrains/kotlin/ir/declarations/IrAnnotationContainer;)Ljava/lang/Boolean;", "isInvisibleInMultifilePart", "isJavaLangRecord", "isPublishedApi", "suppressWildcardsMode", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Ljava/lang/Boolean;", "toIrBasedDescriptorWithOriginalOverrides", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "IrBasedSimpleFunctionDescriptorWithOriginalOverrides", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MethodSignatureMapper {
    private final JvmBackendContext context;
    private final IrTypeMapper typeMapper;
    private final IrTypeSystemContext typeSystem;

    public MethodSignatureMapper(JvmBackendContext context, IrTypeMapper typeMapper) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
        this.context = context;
        this.typeMapper = typeMapper;
        this.typeSystem = typeMapper.getTypeSystem();
    }

    public final Method mapAsmMethod(IrFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        Method asmMethod = mapSignatureSkipGeneric(function).getAsmMethod();
        Intrinsics.checkNotNullExpressionValue(asmMethod, "getAsmMethod(...)");
        return asmMethod;
    }

    public final String mapFieldSignature(IrField field) {
        IrProperty owner;
        Intrinsics.checkNotNullParameter(field, "field");
        BothSignatureWriter bothSignatureWriter = new BothSignatureWriter(BothSignatureWriter.Mode.TYPE);
        IrPropertySymbol correspondingPropertySymbol = field.getCorrespondingPropertySymbol();
        if (correspondingPropertySymbol != null && (owner = correspondingPropertySymbol.getOwner()) != null && owner.getIsVar()) {
            writeParameterType$default(this, bothSignatureWriter, field.getType(), field, false, 8, null);
        } else {
            mapReturnType$default(this, field, field.getType(), bothSignatureWriter, false, 8, null);
        }
        return bothSignatureWriter.makeJavaGenericSignature();
    }

    public static /* synthetic */ String mapFunctionName$default(MethodSignatureMapper methodSignatureMapper, IrFunction irFunction, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return methodSignatureMapper.mapFunctionName(irFunction, z);
    }

    /* JADX WARN: Code duplicated, block: B:44:0x00b5  */
    /* JADX WARN: Code duplicated, block: B:46:0x00bd  */
    /* JADX WARN: Code duplicated, block: B:47:0x00c2  */
    public final String mapFunctionName(IrFunction function, boolean skipSpecial) {
        String str;
        String jvmNameFromAnnotation;
        Intrinsics.checkNotNullParameter(function, "function");
        if (!(function instanceof IrSimpleFunction)) {
            String strAsString = function.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return strAsString;
        }
        if (!skipSpecial) {
            if (!Intrinsics.areEqual(function.getOrigin(), IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE) && (jvmNameFromAnnotation = JvmIrUtilsKt.getJvmNameFromAnnotation(function)) != null) {
                return jvmNameFromAnnotation;
            }
            String jvmMethodNameIfSpecial = getJvmMethodNameIfSpecial((IrSimpleFunction) function);
            if (jvmMethodNameIfSpecial != null) {
                return jvmMethodNameIfSpecial;
            }
        }
        IrSimpleFunction irSimpleFunction = (IrSimpleFunction) function;
        IrPropertySymbol correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol();
        IrProperty owner = correspondingPropertySymbol != null ? correspondingPropertySymbol.getOwner() : null;
        if (owner != null) {
            String strAsString2 = owner.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
            IrClass parentAsClass = IrUtilsKt.getParentAsClass(owner);
            if (!IrUtilsKt.isAnnotationClass(parentAsClass)) {
                List<IrType> superTypes = parentAsClass.getSuperTypes();
                if ((superTypes instanceof Collection) && superTypes.isEmpty()) {
                    if (IrUtilsKt.isEnumClass(parentAsClass)) {
                        if (function.getName().isSpecial()) {
                            if (AdditionalIrUtilsKt.isGetter(function)) {
                                str = JvmAbi.getterName(strAsString2);
                            } else {
                                str = JvmAbi.setterName(strAsString2);
                            }
                            return mangleMemberNameIfRequired(str, irSimpleFunction);
                        }
                    } else if (function.getName().isSpecial()) {
                        if (AdditionalIrUtilsKt.isGetter(function)) {
                            str = JvmAbi.getterName(strAsString2);
                        } else {
                            str = JvmAbi.setterName(strAsString2);
                        }
                        return mangleMemberNameIfRequired(str, irSimpleFunction);
                    }
                } else {
                    Iterator<T> it = superTypes.iterator();
                    while (it.hasNext()) {
                        if (isJavaLangRecord((IrType) it.next())) {
                        }
                    }
                    if ((IrUtilsKt.isEnumClass(parentAsClass) && !IrUtilsKt.isEnumEntry(parentAsClass)) || (!Intrinsics.areEqual(strAsString2, "name") && !Intrinsics.areEqual(strAsString2, "ordinal"))) {
                        if (function.getName().isSpecial()) {
                            if (AdditionalIrUtilsKt.isGetter(function)) {
                                str = JvmAbi.getterName(strAsString2);
                            } else {
                                str = JvmAbi.setterName(strAsString2);
                            }
                            return mangleMemberNameIfRequired(str, irSimpleFunction);
                        }
                    }
                }
            }
            return strAsString2;
        }
        String strAsString3 = function.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString3, "asString(...)");
        return mangleMemberNameIfRequired(strAsString3, irSimpleFunction);
    }

    private final boolean isJavaLangRecord(IrType irType) {
        IrClass irClass = IrTypesKt.getClass(irType);
        Intrinsics.checkNotNull(irClass);
        return AdditionalIrUtilsKt.hasEqualFqName(irClass, JdkClassesKt.getJAVA_LANG_RECORD_FQ_NAME());
    }

    /* JADX WARN: Code duplicated, block: B:10:0x003f  */
    private final String mangleMemberNameIfRequired(String name2, IrSimpleFunction function) {
        String strSanitizeAsJavaIdentifier;
        String strSanitizeNameIfNeeded = JvmCodegenUtil.sanitizeNameIfNeeded(name2, this.context.getState().getLanguageVersionSettings());
        Intrinsics.checkNotNullExpressionValue(strSanitizeNameIfNeeded, "sanitizeNameIfNeeded(...)");
        IrSimpleFunction irSimpleFunction = function;
        if (IrUtilsKt.isTopLevel(irSimpleFunction)) {
            if (isInvisibleInMultifilePart(function)) {
                strSanitizeAsJavaIdentifier = IrUtilsKt.getParentAsClass(irSimpleFunction).getName().asString();
            } else {
                strSanitizeAsJavaIdentifier = null;
            }
        } else {
            IrSimpleFunction internalFunctionForManglingIfNeeded = getInternalFunctionForManglingIfNeeded(function);
            if (internalFunctionForManglingIfNeeded != null) {
                strSanitizeAsJavaIdentifier = NameUtils.sanitizeAsJavaIdentifier(getModuleName(internalFunctionForManglingIfNeeded));
            } else {
                strSanitizeAsJavaIdentifier = null;
            }
        }
        if (strSanitizeAsJavaIdentifier == null) {
            return strSanitizeNameIfNeeded;
        }
        if (Intrinsics.areEqual(function.getOrigin(), IrDeclarationOrigin.FUNCTION_FOR_DEFAULT_PARAMETER.INSTANCE)) {
            StringsKt.endsWith$default(strSanitizeNameIfNeeded, "$default", false, 2, (Object) null);
            return StringsKt.substringBeforeLast$default(strSanitizeNameIfNeeded, "$default", (String) null, 2, (Object) null) + '$' + strSanitizeAsJavaIdentifier + "$default";
        }
        return strSanitizeNameIfNeeded + '$' + strSanitizeAsJavaIdentifier;
    }

    private final boolean isInvisibleInMultifilePart(IrSimpleFunction irSimpleFunction) {
        if (!Intrinsics.areEqual(irSimpleFunction.getName().asString(), MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME)) {
            Set<IrClass> setKeySet = this.context.getMultifileFacadeForPart().keySet();
            IrDeclarationParent parent = irSimpleFunction.getParent();
            IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
            if (CollectionsKt.contains(setKeySet, irClass != null ? irClass.getAttributeOwnerId() : null)) {
                if (DescriptorVisibilities.isPrivate(JvmIrUtilsKt.suspendFunctionOriginal(irSimpleFunction).getVisibility())) {
                    return true;
                }
                IrSimpleFunction originalForDefaultAdapter = getOriginalForDefaultAdapter(irSimpleFunction);
                if (originalForDefaultAdapter != null && isInvisibleInMultifilePart(originalForDefaultAdapter)) {
                    return true;
                }
            }
        }
        return false;
    }

    private final IrSimpleFunction getInternalFunctionForManglingIfNeeded(IrSimpleFunction irSimpleFunction) {
        IrSimpleFunction internalFunctionForManglingIfNeeded;
        IrSimpleFunction internalFunctionForManglingIfNeeded2;
        if (Intrinsics.areEqual(irSimpleFunction.getVisibility(), DescriptorVisibilities.INTERNAL) && !Intrinsics.areEqual(irSimpleFunction.getOrigin(), JvmLoweredDeclarationOrigin.STATIC_INLINE_CLASS_CONSTRUCTOR.INSTANCE) && !Intrinsics.areEqual(irSimpleFunction.getOrigin(), JvmLoweredDeclarationOrigin.STATIC_MULTI_FIELD_VALUE_CLASS_CONSTRUCTOR.INSTANCE) && !Intrinsics.areEqual(irSimpleFunction.getOrigin(), JvmLoweredDeclarationOrigin.SYNTHETIC_METHOD_FOR_PROPERTY_OR_TYPEALIAS_ANNOTATIONS.INSTANCE) && !Intrinsics.areEqual(irSimpleFunction.getOrigin(), IrDeclarationOrigin.PROPERTY_DELEGATE.INSTANCE) && !isPublishedApi(irSimpleFunction)) {
            IrFunction originalFunction = IrUtilsKt.getOriginalFunction(irSimpleFunction);
            if (Intrinsics.areEqual(originalFunction, irSimpleFunction)) {
                originalFunction = null;
            }
            IrSimpleFunction irSimpleFunction2 = originalFunction instanceof IrSimpleFunction ? (IrSimpleFunction) originalFunction : null;
            return (irSimpleFunction2 == null || (internalFunctionForManglingIfNeeded2 = getInternalFunctionForManglingIfNeeded(irSimpleFunction2)) == null) ? irSimpleFunction : internalFunctionForManglingIfNeeded2;
        }
        IrSimpleFunction originalForDefaultAdapter = getOriginalForDefaultAdapter(irSimpleFunction);
        if (originalForDefaultAdapter == null || (internalFunctionForManglingIfNeeded = getInternalFunctionForManglingIfNeeded(originalForDefaultAdapter)) == null) {
            return null;
        }
        return internalFunctionForManglingIfNeeded;
    }

    private final IrSimpleFunction getOriginalForDefaultAdapter(IrSimpleFunction irSimpleFunction) {
        if (!Intrinsics.areEqual(irSimpleFunction.getOrigin(), IrDeclarationOrigin.FUNCTION_FOR_DEFAULT_PARAMETER.INSTANCE)) {
            return null;
        }
        IrElement attributeOwnerId = irSimpleFunction.getAttributeOwnerId();
        Intrinsics.checkNotNull(attributeOwnerId, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrFunction");
        IrFunction owner = ((IrFunction) attributeOwnerId).getSymbol().getOwner();
        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
        return (IrSimpleFunction) owner;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final String getModuleName(IrSimpleFunction function) {
        String jvmModuleNameForDeserialized = function instanceof IrLazyFunctionBase ? getJvmModuleNameForDeserialized((IrLazyFunctionBase) function) : null;
        return jvmModuleNameForDeserialized == null ? this.context.getState().getModuleName() : jvmModuleNameForDeserialized;
    }

    private final boolean isPublishedApi(IrSimpleFunction irSimpleFunction) {
        return AdditionalIrUtilsKt.hasAnnotation(JvmIrUtilsKt.getPropertyIfAccessor(irSimpleFunction).getAnnotations(), StandardNames.FqNames.publishedApi);
    }

    public static /* synthetic */ Type mapReturnType$default(MethodSignatureMapper methodSignatureMapper, IrDeclaration irDeclaration, JvmSignatureWriter jvmSignatureWriter, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            jvmSignatureWriter = null;
        }
        if ((i & 4) != 0) {
            z = true;
        }
        return methodSignatureMapper.mapReturnType(irDeclaration, jvmSignatureWriter, z);
    }

    public final Type mapReturnType(IrDeclaration declaration, JvmSignatureWriter sw, boolean materialized) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (!(declaration instanceof IrFunction)) {
            if (!(declaration instanceof IrField)) {
                throw new IllegalArgumentException(("Unsupported declaration: " + declaration).toString());
            }
            return mapReturnType(declaration, ((IrField) declaration).getType(), sw, materialized);
        }
        IrFunction irFunction = (IrFunction) declaration;
        if (hasVoidReturnType(irFunction)) {
            if (sw != null) {
                sw.writeAsmType(Type.VOID_TYPE);
            }
            Type type = Type.VOID_TYPE;
            Intrinsics.checkNotNull(type);
            return type;
        }
        if (forceBoxedReturnType(irFunction)) {
            return this.typeMapper.mapType(irFunction.getReturnType(), TypeMappingMode.RETURN_TYPE_BOXED, sw, materialized);
        }
        return mapReturnType(declaration, irFunction.getReturnType(), sw, materialized);
    }

    static /* synthetic */ Type mapReturnType$default(MethodSignatureMapper methodSignatureMapper, IrDeclaration irDeclaration, IrType irType, JvmSignatureWriter jvmSignatureWriter, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = true;
        }
        return methodSignatureMapper.mapReturnType(irDeclaration, irType, jvmSignatureWriter, z);
    }

    private final Type mapReturnType(IrDeclaration declaration, IrType returnType, JvmSignatureWriter sw, boolean materialized) {
        IrDeclarationParent parent = declaration.getParent();
        boolean z = (parent instanceof IrClass) && IrUtilsKt.isAnnotationClass((IrClass) parent);
        if (sw == null || sw.skipGenericSignature()) {
            return this.typeMapper.mapType(returnType, TypeMappingMode.INSTANCE.getModeForReturnTypeNoGeneric(z), sw, materialized);
        }
        IrType irType = returnType;
        TypeMappingMode typeMappingModeExtractTypeMappingModeFromAnnotation = TypeMappingUtil.extractTypeMappingModeFromAnnotation(this.typeSystem, suppressWildcardsMode(declaration), irType, z, false);
        if (typeMappingModeExtractTypeMappingModeFromAnnotation != null) {
            return this.typeMapper.mapType(returnType, typeMappingModeExtractTypeMappingModeFromAnnotation, sw, materialized);
        }
        return this.typeMapper.mapType(returnType, TypeMappingModeExtensionsKt.getOptimalModeForReturnType(this.typeSystem, irType, z), sw, materialized);
    }

    private final boolean hasVoidReturnType(IrFunction function) {
        return (function instanceof IrConstructor) || (IrTypePredicatesKt.isUnit(function.getReturnType()) && !AdditionalIrUtilsKt.isGetter(function));
    }

    private final boolean forceBoxedReturnType(IrFunction function) {
        return isBoxMethodForInlineClass(function) || forceFoxedReturnTypeOnOverride(function) || forceBoxedReturnTypeOnDefaultImplFun(function) || (IrUtilsKt.isFromJava(function) && JvmIrTypeUtilsKt.isInlineClassType(function.getReturnType()));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean forceFoxedReturnTypeOnOverride(IrFunction function) {
        if (!(function instanceof IrSimpleFunction) || !IrTypePredicatesKt.isPrimitiveType$default(function.getReturnType(), false, 1, null)) {
            return false;
        }
        List listAllOverridden$default = IrUtilsKt.allOverridden$default((IrOverridableDeclaration) function, false, 1, null);
        if ((listAllOverridden$default instanceof Collection) && listAllOverridden$default.isEmpty()) {
            return false;
        }
        Iterator it = listAllOverridden$default.iterator();
        while (it.hasNext()) {
            if (!IrTypePredicatesKt.isPrimitiveType$default(((IrSimpleFunction) it.next()).getReturnType(), false, 1, null)) {
                return true;
            }
        }
        return false;
    }

    private final boolean forceBoxedReturnTypeOnDefaultImplFun(IrFunction function) {
        IrSimpleFunction originalFunctionForDefaultImpl;
        if ((function instanceof IrSimpleFunction) && (originalFunctionForDefaultImpl = this.context.getCachedDeclarations().getOriginalFunctionForDefaultImpl((IrSimpleFunction) function)) != null) {
            return forceFoxedReturnTypeOnOverride(originalFunctionForDefaultImpl);
        }
        return false;
    }

    private final boolean isBoxMethodForInlineClass(IrFunction function) {
        IrDeclarationParent parent = function.getParent();
        return (parent instanceof IrClass) && IrDeclarationsKt.isSingleFieldValueClass((IrClass) parent) && Intrinsics.areEqual(function.getOrigin(), JvmLoweredDeclarationOrigin.SYNTHETIC_INLINE_CLASS_MEMBER.INSTANCE) && Intrinsics.areEqual(function.getName().asString(), "box-impl");
    }

    public final JvmMethodSignature mapFakeOverrideSignatureSkipGeneric(IrFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return mapSignature$default(this, function, true, false, false, 4, null);
    }

    public final JvmMethodSignature mapSignatureSkipGeneric(IrFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return mapSignature$default(this, function, true, false, false, 12, null);
    }

    public final JvmMethodGenericSignature mapSignatureWithGeneric(IrFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return mapSignature$default(this, function, false, false, false, 12, null);
    }

    static /* synthetic */ JvmMethodGenericSignature mapSignature$default(MethodSignatureMapper methodSignatureMapper, IrFunction irFunction, boolean z, boolean z2, boolean z3, int i, Object obj) {
        if ((i & 4) != 0) {
            z2 = false;
        }
        if ((i & 8) != 0) {
            z3 = true;
        }
        return methodSignatureMapper.mapSignature(irFunction, z, z2, z3);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0050, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r2 != null ? r2.getOrigin() : null, org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE) != false) goto L27;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodGenericSignature mapSignature(org.jetbrains.kotlin.ir.declarations.IrFunction r11, boolean r12, boolean r13, boolean r14) {
        /*
            Method dump skipped, instruction units count: 392
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.backend.jvm.mapping.MethodSignatureMapper.mapSignature(org.jetbrains.kotlin.ir.declarations.IrFunction, boolean, boolean, boolean):org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodGenericSignature");
    }

    private final FunctionDescriptor toIrBasedDescriptorWithOriginalOverrides(IrFunction irFunction) {
        IrBasedSimpleFunctionDescriptorWithOriginalOverrides irBasedSimpleFunctionDescriptorWithOriginalOverrides;
        if (irFunction instanceof IrConstructor) {
            return IrBasedDescriptorsKt.toIrBasedDescriptor((IrConstructor) irFunction);
        }
        if (irFunction instanceof IrSimpleFunction) {
            if (IrDeclarationsKt.isPropertyAccessor(irFunction)) {
                irBasedSimpleFunctionDescriptorWithOriginalOverrides = IrBasedDescriptorsKt.toIrBasedDescriptor((IrSimpleFunction) irFunction);
            } else {
                irBasedSimpleFunctionDescriptorWithOriginalOverrides = new IrBasedSimpleFunctionDescriptorWithOriginalOverrides((IrSimpleFunction) irFunction, this.context);
            }
            return irBasedSimpleFunctionDescriptorWithOriginalOverrides;
        }
        throw new AssertionError("Unexpected function kind: " + irFunction);
    }

    /* JADX INFO: compiled from: MethodSignatureMapper.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/mapping/MethodSignatureMapper$IrBasedSimpleFunctionDescriptorWithOriginalOverrides;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedSimpleFunctionDescriptor;", "owner", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getOverriddenDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class IrBasedSimpleFunctionDescriptorWithOriginalOverrides extends IrBasedSimpleFunctionDescriptor {
        private final JvmBackendContext context;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public IrBasedSimpleFunctionDescriptorWithOriginalOverrides(IrSimpleFunction owner, JvmBackendContext context) {
            super(owner);
            Intrinsics.checkNotNullParameter(owner, "owner");
            Intrinsics.checkNotNullParameter(context, "context");
            this.context = context;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedSimpleFunctionDescriptor, org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
        public List<FunctionDescriptor> getOverriddenDescriptors() {
            List<IrSimpleFunctionSymbol> overridesWithoutStubs = this.context.getOverridesWithoutStubs((IrSimpleFunction) getOwner());
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(overridesWithoutStubs, 10));
            Iterator<T> it = overridesWithoutStubs.iterator();
            while (it.hasNext()) {
                arrayList.add(new IrBasedSimpleFunctionDescriptorWithOriginalOverrides(((IrSimpleFunctionSymbol) it.next()).getOwner(), this.context));
            }
            return arrayList;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean shouldBoxSingleValueParameterForSpecialCaseOfRemove(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        if (!(irFunction instanceof IrSimpleFunction)) {
            return false;
        }
        if (!Intrinsics.areEqual(irFunction.getName().asString(), ProductAction.ACTION_REMOVE)) {
            String strAsString = irFunction.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            if (!StringsKt.startsWith$default(strAsString, "remove-", false, 2, (Object) null)) {
                return false;
            }
        }
        if (IrUtilsKt.isFromJava(irFunction) || irFunction.getValueParameters().size() != 1 || !IrTypePredicatesKt.isInt(InlineClassAbiKt.unboxInlineClass(irFunction.getValueParameters().get(0).getType()))) {
            return false;
        }
        List listAllOverridden = IrUtilsKt.allOverridden((IrOverridableDeclaration) irFunction, false);
        if ((listAllOverridden instanceof Collection) && listAllOverridden.isEmpty()) {
            return false;
        }
        Iterator it = listAllOverridden.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(AdditionalIrUtilsKt.getKotlinFqName(((IrSimpleFunction) it.next()).getParent()), StandardNames.FqNames.mutableCollection)) {
                return true;
            }
        }
        return false;
    }

    static /* synthetic */ void writeParameter$default(MethodSignatureMapper methodSignatureMapper, JvmSignatureWriter jvmSignatureWriter, JvmMethodParameterKind jvmMethodParameterKind, IrType irType, IrFunction irFunction, boolean z, int i, Object obj) {
        if ((i & 16) != 0) {
            z = true;
        }
        methodSignatureMapper.writeParameter(jvmSignatureWriter, jvmMethodParameterKind, irType, irFunction, z);
    }

    private final void writeParameter(JvmSignatureWriter sw, JvmMethodParameterKind kind, IrType type, IrFunction function, boolean materialized) {
        sw.writeParameterType(kind);
        writeParameterType(sw, type, function, materialized);
        sw.writeParameterTypeEnd();
    }

    static /* synthetic */ void writeParameterType$default(MethodSignatureMapper methodSignatureMapper, JvmSignatureWriter jvmSignatureWriter, IrType irType, IrDeclaration irDeclaration, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = true;
        }
        methodSignatureMapper.writeParameterType(jvmSignatureWriter, irType, irDeclaration, z);
    }

    private final void writeParameterType(JvmSignatureWriter sw, IrType type, IrDeclaration declaration, boolean materialized) {
        if (sw.skipGenericSignature()) {
            if (JvmIrTypeUtilsKt.isInlineClassType(type) && IrUtilsKt.isFromJava(declaration)) {
                this.typeMapper.mapType(type, TypeMappingMode.GENERIC_ARGUMENT, sw, materialized);
                return;
            } else {
                this.typeMapper.mapType(type, TypeMappingMode.DEFAULT, sw, materialized);
                return;
            }
        }
        IrTypeSystemContext irTypeSystemContext = this.typeSystem;
        IrType irType = type;
        TypeMappingMode typeMappingModeExtractTypeMappingModeFromAnnotation = TypeMappingUtil.extractTypeMappingModeFromAnnotation(irTypeSystemContext, suppressWildcardsMode(declaration), irType, false, false);
        if (typeMappingModeExtractTypeMappingModeFromAnnotation == null) {
            if (isMethodWithDeclarationSiteWildcards(declaration) && !JvmIrUtilsKt.isStaticInlineClassReplacement(declaration) && irTypeSystemContext.argumentsCount(irType) != 0) {
                typeMappingModeExtractTypeMappingModeFromAnnotation = TypeMappingMode.GENERIC_ARGUMENT;
            } else {
                typeMappingModeExtractTypeMappingModeFromAnnotation = TypeMappingModeExtensionsKt.getOptimalModeForValueParameter(this.typeSystem, irType);
            }
        }
        this.typeMapper.mapType(type, typeMappingModeExtractTypeMappingModeFromAnnotation, sw, materialized);
    }

    private final boolean isMethodWithDeclarationSiteWildcards(IrDeclaration irDeclaration) {
        if (!(irDeclaration instanceof IrSimpleFunction)) {
            return false;
        }
        List listAllOverridden$default = IrUtilsKt.allOverridden$default((IrOverridableDeclaration) irDeclaration, false, 1, null);
        if ((listAllOverridden$default instanceof Collection) && listAllOverridden$default.isEmpty()) {
            return false;
        }
        Iterator it = listAllOverridden$default.iterator();
        while (it.hasNext()) {
            if (TypeMappingUtil.isMethodWithDeclarationSiteWildcardsFqName(IrUtilsKt.getFqNameWhenAvailable((IrSimpleFunction) it.next()))) {
                return true;
            }
        }
        return false;
    }

    private final Boolean suppressWildcardsMode(IrDeclaration irDeclaration) {
        return (Boolean) SequencesKt.firstOrNull(SequencesKt.mapNotNull(LocalDeclarationsLoweringKt.getParentsWithSelf(irDeclaration), new Function1<IrDeclarationParent, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.mapping.MethodSignatureMapper.suppressWildcardsMode.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrDeclarationParent declaration) {
                IrProperty owner;
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                if (declaration instanceof IrField) {
                    IrPropertySymbol correspondingPropertySymbol = ((IrField) declaration).getCorrespondingPropertySymbol();
                    if (correspondingPropertySymbol == null || (owner = correspondingPropertySymbol.getOwner()) == null) {
                        return null;
                    }
                    return MethodSignatureMapper.this.getSuppressWildcardsAnnotationValue(owner);
                }
                if (declaration instanceof IrAnnotationContainer) {
                    return MethodSignatureMapper.this.getSuppressWildcardsAnnotationValue((IrAnnotationContainer) declaration);
                }
                return null;
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Boolean getSuppressWildcardsAnnotationValue(IrAnnotationContainer irAnnotationContainer) {
        IrConstructorCall annotation = IrUtilsKt.getAnnotation(irAnnotationContainer, TypeMappingUtil.getJVM_SUPPRESS_WILDCARDS_ANNOTATION_FQ_NAME());
        if (annotation == null || annotation.getValueArgumentsCount() <= 0) {
            return null;
        }
        IrExpression valueArgument = annotation.getValueArgument(0);
        IrConst irConst = valueArgument instanceof IrConst ? (IrConst) valueArgument : null;
        Object value = irConst != null ? irConst.getValue() : null;
        Boolean bool = value instanceof Boolean ? (Boolean) value : null;
        return Boolean.valueOf(bool != null ? bool.booleanValue() : true);
    }

    public final IrCallableMethod mapToCallableMethod(IrCall expression, IrFunction caller) {
        IrClass parentAsClass;
        IrType type;
        IrClassSymbol classOrNull;
        int i;
        JvmMethodSignature jvmMethodSignatureMapOverriddenSpecialBuiltinIfNeeded;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrSimpleFunction owner = expression.getSymbol().getOwner();
        IrClassSymbol superQualifierSymbol = expression.getSuperQualifierSymbol();
        if (superQualifierSymbol == null || (parentAsClass = superQualifierSymbol.getOwner()) == null) {
            IrExpression dispatchReceiver = expression.getDispatchReceiver();
            if (dispatchReceiver != null && (type = dispatchReceiver.getType()) != null && (classOrNull = IrTypesKt.getClassOrNull(type)) != null && (parentAsClass = classOrNull.getOwner()) != null) {
                if (JvmIrUtilsKt.isJvmInterface(parentAsClass) && IrUtilsKt.isMethodOfAny(owner)) {
                    parentAsClass = this.context.getIrBuiltIns().getAnyClass().getOwner();
                }
            } else {
                parentAsClass = IrUtilsKt.getParentAsClass(owner);
            }
        }
        Type typeMapOwner = IrTypeMappingKt.mapOwner(this.typeMapper, parentAsClass);
        boolean zIsJvmInterface = JvmIrUtilsKt.isJvmInterface(parentAsClass);
        boolean z = expression.getSuperQualifierSymbol() != null;
        if (owner.getDispatchReceiverParameter() == null) {
            i = 184;
        } else {
            i = 183;
            if (!z) {
                if (zIsJvmInterface && !DescriptorVisibilities.isPrivate(owner.getVisibility())) {
                    i = 185;
                } else if (!DescriptorVisibilities.isPrivate(owner.getVisibility())) {
                    i = 182;
                }
            }
        }
        int i2 = i;
        IrSimpleFunction irSimpleFunctionFindSuperDeclaration = findSuperDeclaration(owner, z);
        if (caller != null && JvmIrUtilsKt.isBridge(caller)) {
            jvmMethodSignatureMapOverriddenSpecialBuiltinIfNeeded = mapSignatureSkipGeneric(irSimpleFunctionFindSuperDeclaration);
        } else {
            IrSimpleFunction irSimpleFunction = irSimpleFunctionFindSuperDeclaration;
            jvmMethodSignatureMapOverriddenSpecialBuiltinIfNeeded = mapOverriddenSpecialBuiltinIfNeeded(irSimpleFunction, z);
            if (jvmMethodSignatureMapOverriddenSpecialBuiltinIfNeeded == null) {
                jvmMethodSignatureMapOverriddenSpecialBuiltinIfNeeded = mapSignatureSkipGeneric(irSimpleFunction);
            }
        }
        return new IrCallableMethod(typeMapOwner, i2, jvmMethodSignatureMapOverriddenSpecialBuiltinIfNeeded, zIsJvmInterface, irSimpleFunctionFindSuperDeclaration.getReturnType());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final JvmMethodSignature mapOverriddenSpecialBuiltinIfNeeded(IrFunction callee, boolean superCall) {
        if (JvmIrUtilsKt.isStaticValueClassReplacement(callee)) {
            return null;
        }
        IrBasedSimpleFunctionDescriptor irBasedSimpleFunctionDescriptor = (IrBasedSimpleFunctionDescriptor) SpecialBuiltinMembers.getOverriddenBuiltinReflectingJvmDescriptor(IrBasedDescriptorsKt.toIrBasedDescriptor(callee));
        IrSimpleFunction irSimpleFunction = irBasedSimpleFunctionDescriptor != null ? (IrSimpleFunction) irBasedSimpleFunctionDescriptor.getOwner() : null;
        if (irSimpleFunction == null || superCall) {
            return null;
        }
        return mapSignatureSkipGeneric(irSimpleFunction);
    }

    public static /* synthetic */ Method mapCalleeToAsmMethod$default(MethodSignatureMapper methodSignatureMapper, IrSimpleFunction irSimpleFunction, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return methodSignatureMapper.mapCalleeToAsmMethod(irSimpleFunction, z);
    }

    public final Method mapCalleeToAsmMethod(IrSimpleFunction function, boolean isSuperCall) {
        Intrinsics.checkNotNullParameter(function, "function");
        return mapAsmMethod(findSuperDeclaration(function, isSuperCall));
    }

    private final IrSimpleFunction findSuperDeclaration(IrSimpleFunction function, boolean isSuperCall) {
        return JvmIrUtilsKt.findSuperDeclaration(function, isSuperCall, this.context.getConfig().getJvmDefaultMode());
    }

    private final String getJvmMethodNameIfSpecial(IrSimpleFunction irFunction) {
        if (Intrinsics.areEqual(irFunction.getOrigin(), JvmLoweredDeclarationOrigin.STATIC_INLINE_CLASS_REPLACEMENT.INSTANCE) || Intrinsics.areEqual(irFunction.getOrigin(), JvmLoweredDeclarationOrigin.STATIC_MULTI_FIELD_VALUE_CLASS_REPLACEMENT.INSTANCE)) {
            return null;
        }
        String builtinSpecialPropertyGetterName = getBuiltinSpecialPropertyGetterName(irFunction);
        return builtinSpecialPropertyGetterName == null ? getDifferentNameForJvmBuiltinFunction(irFunction) : builtinSpecialPropertyGetterName;
    }

    private final boolean isBuiltIn(IrSimpleFunction irSimpleFunction) {
        FqName fqNameWhenAvailable;
        FqNameUnsafe unsafe;
        if (!Intrinsics.areEqual(IrUtilsKt.getPackageFragment((IrDeclaration) irSimpleFunction).getPackageFqName(), StandardNames.BUILT_INS_PACKAGE_FQ_NAME)) {
            IrDeclarationParent parent = irSimpleFunction.getParent();
            ClassId classIdMapKotlinToJava = null;
            IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
            if (irClass != null && (fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(irClass)) != null && (unsafe = fqNameWhenAvailable.toUnsafe()) != null) {
                classIdMapKotlinToJava = JavaToKotlinClassMap.INSTANCE.mapKotlinToJava(unsafe);
            }
            if (classIdMapKotlinToJava == null) {
                return false;
            }
        }
        return true;
    }

    private final String getDifferentNameForJvmBuiltinFunction(IrSimpleFunction irSimpleFunction) {
        if (!SpecialGenericSignatures.INSTANCE.getORIGINAL_SHORT_NAMES().contains(irSimpleFunction.getName()) || !isBuiltIn(irSimpleFunction)) {
            return null;
        }
        List listAllOverridden = IrUtilsKt.allOverridden(irSimpleFunction, true);
        ArrayList arrayList = new ArrayList();
        for (Object obj : listAllOverridden) {
            if (isBuiltIn((IrSimpleFunction) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Name name2 = SpecialGenericSignatures.INSTANCE.getSIGNATURE_TO_JVM_REPRESENTATION_NAME().get(computeJvmSignature((IrSimpleFunction) it.next()));
            String strAsString = name2 != null ? name2.asString() : null;
            if (strAsString != null) {
                arrayList2.add(strAsString);
            }
        }
        return (String) CollectionsKt.firstOrNull((List) arrayList2);
    }

    private final String getBuiltinSpecialPropertyGetterName(IrSimpleFunction irSimpleFunction) {
        IrProperty owner;
        Name name2;
        IrPropertySymbol correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol();
        if (correspondingPropertySymbol == null || (owner = correspondingPropertySymbol.getOwner()) == null || (name2 = owner.getName()) == null || !BuiltinSpecialProperties.INSTANCE.getSPECIAL_SHORT_NAMES().contains(name2) || !isBuiltIn(irSimpleFunction)) {
            return null;
        }
        List listAllOverridden = IrUtilsKt.allOverridden(irSimpleFunction, true);
        ArrayList arrayList = new ArrayList();
        Iterator it = listAllOverridden.iterator();
        while (it.hasNext()) {
            IrPropertySymbol correspondingPropertySymbol2 = ((IrSimpleFunction) it.next()).getCorrespondingPropertySymbol();
            Intrinsics.checkNotNull(correspondingPropertySymbol2);
            Name name3 = BuiltinSpecialProperties.INSTANCE.getPROPERTY_FQ_NAME_TO_JVM_GETTER_NAME_MAP().get(IrUtilsKt.getFqNameWhenAvailable(correspondingPropertySymbol2.getOwner()));
            String strAsString = name3 != null ? name3.asString() : null;
            if (strAsString != null) {
                arrayList.add(strAsString);
            }
        }
        return (String) CollectionsKt.firstOrNull((List) arrayList);
    }

    private final String getJvmModuleNameForDeserialized(IrLazyFunctionBase function) {
        NameResolver nameResolver;
        String string;
        IrDeclarationParent parent = function.getParent();
        while (parent != null) {
            if (parent instanceof IrLazyClass) {
                IrLazyClass irLazyClass = (IrLazyClass) parent;
                ProtoBuf.Class classProto = irLazyClass.getClassProto();
                if (classProto == null || (nameResolver = irLazyClass.getNameResolver()) == null) {
                    return null;
                }
                GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, Integer> classModuleName = JvmProtoBuf.classModuleName;
                Intrinsics.checkNotNullExpressionValue(classModuleName, "classModuleName");
                Integer num = (Integer) ProtoBufUtilKt.getExtensionOrNull(classProto, classModuleName);
                return (num == null || (string = nameResolver.getString(num.intValue())) == null) ? "main" : string;
            }
            if (parent instanceof IrMaybeDeserializedClass) {
                return ((IrMaybeDeserializedClass) parent).getModuleName();
            }
            if (parent instanceof IrExternalPackageFragment) {
                DeserializedContainerSource containerSource = ((IrExternalPackageFragment) parent).getContainerSource();
                if (containerSource == null) {
                    return null;
                }
                JvmPackagePartSource jvmPackagePartSource = containerSource instanceof JvmPackagePartSource ? (JvmPackagePartSource) containerSource : null;
                if (jvmPackagePartSource != null) {
                    return jvmPackagePartSource.getModuleName();
                }
                return null;
            }
            IrDeclaration irDeclaration = parent instanceof IrDeclaration ? (IrDeclaration) parent : null;
            if (irDeclaration == null || (parent = irDeclaration.getParent()) == null) {
                break;
            }
        }
        return null;
    }

    public final Handle mapToMethodHandle(IrFunction irFun) {
        Intrinsics.checkNotNullParameter(irFun, "irFun");
        if (!(irFun instanceof IrConstructor)) {
            if (irFun instanceof IrSimpleFunction) {
                irFun = JvmIrUtilsKt.findSuperDeclaration((IrSimpleFunction) irFun, false, this.context.getConfig().getJvmDefaultMode());
            } else {
                throw new AssertionError("Simple function or constructor expected: " + RenderIrElementKt.render$default(irFun, (DumpIrTreeOptions) null, 1, (Object) null));
            }
        }
        IrDeclarationParent parent = irFun.getParent();
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        if (irClass == null) {
            throw new AssertionError("Unexpected parent: " + RenderIrElementKt.render$default(irFun.getParent(), (DumpIrTreeOptions) null, 1, (Object) null));
        }
        Type typeMapOwner = IrTypeMappingKt.mapOwner(this.typeMapper, irClass);
        Method methodMapAsmMethod = mapAsmMethod(irFun);
        return new Handle(getMethodKindTag(irFun), typeMapOwner.getInternalName(), methodMapAsmMethod.getName(), methodMapAsmMethod.getDescriptor(), JvmIrUtilsKt.isJvmInterface(irClass));
    }

    private final int getMethodKindTag(IrFunction irFun) {
        if (irFun instanceof IrConstructor) {
            return 8;
        }
        if (irFun.getDispatchReceiverParameter() == null) {
            return 6;
        }
        return JvmIrUtilsKt.isJvmInterface(IrUtilsKt.getParentAsClass(irFun)) ? 9 : 5;
    }

    private final String computeJvmSignature(IrFunction irFunction) {
        SignatureBuildingComponents signatureBuildingComponents = SignatureBuildingComponents.INSTANCE;
        String internalName = IrTypeMapper.mapType$default(this.typeMapper, IrUtilsKt.getDefaultType(IrUtilsKt.getParentAsClass(irFunction)), null, null, false, 14, null).getInternalName();
        String string = mapSignature$default(this, irFunction, false, true, false, 8, null).toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        Intrinsics.checkNotNull(internalName);
        return signatureBuildingComponents.signature(internalName, string);
    }
}
