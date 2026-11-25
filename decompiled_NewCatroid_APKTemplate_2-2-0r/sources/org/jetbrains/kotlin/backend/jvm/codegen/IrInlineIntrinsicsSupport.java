package org.jetbrains.kotlin.backend.jvm.codegen;

import com.android.dx.rop.code.RegisterSpec;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension;
import org.jetbrains.kotlin.backend.common.extensions.IrIntrinsicExtension;
import org.jetbrains.kotlin.backend.jvm.intrinsics.SignatureString;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReporterWithContext;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.descriptors.IrBasedDescriptorsKt;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmBackendErrors;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.FieldInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;

/* JADX INFO: compiled from: IrInlineIntrinsicsSupport.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ0\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0012\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001f\u001a\u00020\u0002H\u0016J\u0018\u0010 \u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0018\u0010!\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\"\u001a\u00020#H\u0002J\u0018\u0010$\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010%\u001a\u00020&H\u0016J\u0010\u0010'\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\u0002H\u0016J\u0018\u0010(\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020\u0002H\u0016J\u0010\u0010)\u001a\u00020\u00122\u0006\u0010*\u001a\u00020+H\u0016J\b\u0010,\u001a\u00020\u0012H\u0016J(\u0010-\u001a\u00020\u001c2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u0002012\u0006\u0010\u001f\u001a\u00020\u0002H\u0016J\u0010\u00102\u001a\u0002032\u0006\u0010\u001f\u001a\u00020\u0002H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u00064"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/IrInlineIntrinsicsSupport;", "Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeInliner$IntrinsicsSupport;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "classCodegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;", "reportErrorsOn", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "containingFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "(Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/declarations/IrFile;)V", "pluginExtensions", "", "Lorg/jetbrains/kotlin/backend/jvm/codegen/JvmIrIntrinsicExtension;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "getState", "()Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "generateCallableReference", "", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "implClass", "Lorg/jetbrains/org/objectweb/asm/Type;", "withArity", "", "generateExternalEntriesForEnumTypeIfNeeded", "Lorg/jetbrains/org/objectweb/asm/tree/FieldInsnNode;", ModuleXmlParser.TYPE, "generateFunctionReference", "generatePropertyReference", "property", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "generateTypeParameterContainer", "typeParameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "isMutableCollectionType", "putClassInstance", "reportNonReifiedTypeParameterWithRecursiveBoundUnsupported", "typeParameterName", "Lorg/jetbrains/kotlin/name/Name;", "reportSuspendTypeUnsupported", "rewritePluginDefinedOperationMarker", "reifiedInsn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "instructions", "Lorg/jetbrains/org/objectweb/asm/tree/InsnList;", "toKotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrInlineIntrinsicsSupport implements ReifiedTypeInliner.IntrinsicsSupport<IrType> {
    private final ClassCodegen classCodegen;
    private final IrFile containingFile;
    private final List<JvmIrIntrinsicExtension> pluginExtensions;
    private final IrExpression reportErrorsOn;

    public IrInlineIntrinsicsSupport(ClassCodegen classCodegen, IrExpression reportErrorsOn, IrFile containingFile) {
        Intrinsics.checkNotNullParameter(classCodegen, "classCodegen");
        Intrinsics.checkNotNullParameter(reportErrorsOn, "reportErrorsOn");
        Intrinsics.checkNotNullParameter(containingFile, "containingFile");
        this.classCodegen = classCodegen;
        this.reportErrorsOn = reportErrorsOn;
        this.containingFile = containingFile;
        List<IrGenerationExtension> instances = IrGenerationExtension.INSTANCE.getInstances(classCodegen.getContext().getState().getProject());
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = instances.iterator();
        while (it.hasNext()) {
            IrIntrinsicExtension platformIntrinsicExtension = ((IrGenerationExtension) it.next()).getPlatformIntrinsicExtension(this.classCodegen.getContext());
            JvmIrIntrinsicExtension jvmIrIntrinsicExtension = platformIntrinsicExtension instanceof JvmIrIntrinsicExtension ? (JvmIrIntrinsicExtension) platformIntrinsicExtension : null;
            if (jvmIrIntrinsicExtension != null) {
                arrayList.add(jvmIrIntrinsicExtension);
            }
        }
        this.pluginExtensions = arrayList;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner.IntrinsicsSupport
    public GenerationState getState() {
        return this.classCodegen.getContext().getState();
    }

    @Override // org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner.IntrinsicsSupport
    public void putClassInstance(InstructionAdapter v, IrType type) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(type, "type");
        ExpressionCodegen.INSTANCE.generateClassInstance$backend_jvm_codegen(v, type, this.classCodegen.getTypeMapper(), false);
    }

    @Override // org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner.IntrinsicsSupport
    public void generateTypeParameterContainer(InstructionAdapter v, TypeParameterMarker typeParameter) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        if (!(typeParameter instanceof IrTypeParameterSymbol)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        IrTypeParameterSymbol irTypeParameterSymbol = (IrTypeParameterSymbol) typeParameter;
        IrDeclarationParent parent = irTypeParameterSymbol.getOwner().getParent();
        if (parent instanceof IrClass) {
            putClassInstance(v, (IrType) IrUtilsKt.getDefaultType((IrClass) parent));
            Unit unit = Unit.INSTANCE;
            AsmUtil.wrapJavaClassIntoKClass(v);
        } else {
            if (parent instanceof IrSimpleFunction) {
                if (!this.classCodegen.getContext().getConfig().getGenerateOptimizedCallableReferenceSuperClasses()) {
                    throw new IllegalStateException(("typeOf() of a non-reified type parameter is only allowed if optimized callable references are enabled.\nPlease make sure API version is set to 1.4, and -Xno-optimized-callable-references is NOT used.\nContainer: " + parent).toString());
                }
                IrPropertySymbol correspondingPropertySymbol = ((IrSimpleFunction) parent).getCorrespondingPropertySymbol();
                if (correspondingPropertySymbol != null) {
                    generatePropertyReference(v, correspondingPropertySymbol.getOwner());
                    return;
                } else {
                    generateFunctionReference(v, (IrFunction) parent);
                    return;
                }
            }
            throw new IllegalStateException(("Unknown parent of type parameter: " + RenderIrElementKt.render$default(parent, (DumpIrTreeOptions) null, 1, (Object) null) + StringUtil.SPACE + irTypeParameterSymbol.getOwner().getName() + ')').toString());
        }
    }

    private final void generateFunctionReference(InstructionAdapter v, IrFunction function) {
        Type FUNCTION_REFERENCE_IMPL = AsmTypes.FUNCTION_REFERENCE_IMPL;
        Intrinsics.checkNotNullExpressionValue(FUNCTION_REFERENCE_IMPL, "FUNCTION_REFERENCE_IMPL");
        generateCallableReference(v, function, function, FUNCTION_REFERENCE_IMPL, true);
    }

    private final void generatePropertyReference(InstructionAdapter v, IrProperty property) {
        IrSimpleFunction getter = property.getGetter();
        if (getter == null) {
            throw new IllegalStateException(("Property without getter: " + RenderIrElementKt.render$default(property, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        IrSimpleFunction irSimpleFunction = getter;
        int allParametersCount = IrUtilsKt.getAllParametersCount(irSimpleFunction);
        Type[] typeArr = property.getIsVar() ? AsmTypes.MUTABLE_PROPERTY_REFERENCE_IMPL : AsmTypes.PROPERTY_REFERENCE_IMPL;
        Intrinsics.checkNotNull(typeArr);
        Type type = (Type) ArraysKt.getOrNull(typeArr, allParametersCount);
        if (type == null) {
            throw new IllegalStateException(("No property reference impl class with arity " + allParametersCount + " (" + RenderIrElementKt.render$default(property, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        generateCallableReference(v, property, irSimpleFunction, type, false);
    }

    private final void generateCallableReference(InstructionAdapter v, IrDeclarationWithName declaration, IrFunction function, Type implClass, boolean withArity) {
        v.anew(implClass);
        v.dup();
        if (withArity) {
            v.iconst(IrUtilsKt.getAllParametersCount(function));
        }
        putClassInstance(v, JvmIrUtilsKt.getCallableReferenceOwnerKClassType(declaration.getParent(), this.classCodegen.getContext()));
        v.aconst(declaration.getName().asString());
        SignatureString.INSTANCE.generateSignatureString$backend_jvm_codegen(v, function, this.classCodegen);
        v.iconst(JvmIrUtilsKt.getCallableReferenceTopLevelFlag(declaration));
        List listPlus = CollectionsKt.plus((Collection) (withArity ? CollectionsKt.listOf(Type.INT_TYPE) : CollectionsKt.emptyList()), (Iterable) CollectionsKt.listOf((Object[]) new Type[]{AsmTypes.JAVA_CLASS_TYPE, AsmTypes.JAVA_STRING_TYPE, AsmTypes.JAVA_STRING_TYPE, Type.INT_TYPE}));
        String internalName = implClass.getInternalName();
        Type type = Type.VOID_TYPE;
        Type[] typeArr = (Type[]) listPlus.toArray(new Type[0]);
        v.invokespecial(internalName, "<init>", Type.getMethodDescriptor(type, (Type[]) Arrays.copyOf(typeArr, typeArr.length)), false);
    }

    @Override // org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner.IntrinsicsSupport
    public boolean isMutableCollectionType(IrType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(type);
        if (classOrNull != null) {
            JavaToKotlinClassMap javaToKotlinClassMap = JavaToKotlinClassMap.INSTANCE;
            FqName fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(classOrNull.getOwner());
            if (javaToKotlinClassMap.isMutable(fqNameWhenAvailable != null ? fqNameWhenAvailable.toUnsafe() : null)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner.IntrinsicsSupport
    public KotlinType toKotlinType(IrType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return IrBasedDescriptorsKt.toIrBasedKotlinType$default(type, null, 1, null);
    }

    @Override // org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner.IntrinsicsSupport
    public FieldInsnNode generateExternalEntriesForEnumTypeIfNeeded(IrType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return IrCodegenUtilsKt.generateExternalEntriesForEnumTypeIfNeeded(type, this.classCodegen);
    }

    @Override // org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner.IntrinsicsSupport
    public void reportSuspendTypeUnsupported() {
        this.classCodegen.getContext().getKtDiagnosticReporter().at(this.reportErrorsOn, this.containingFile).report(JvmBackendErrors.INSTANCE.getTYPEOF_SUSPEND_TYPE());
    }

    @Override // org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner.IntrinsicsSupport
    public void reportNonReifiedTypeParameterWithRecursiveBoundUnsupported(Name typeParameterName) {
        Intrinsics.checkNotNullParameter(typeParameterName, "typeParameterName");
        KtDiagnosticReporterWithContext.DiagnosticContextImpl diagnosticContextImplAt = this.classCodegen.getContext().getKtDiagnosticReporter().at(this.reportErrorsOn, this.containingFile);
        KtDiagnosticFactory1<String> typeof_non_reified_type_parameter_with_recursive_bound = JvmBackendErrors.INSTANCE.getTYPEOF_NON_REIFIED_TYPE_PARAMETER_WITH_RECURSIVE_BOUND();
        String strAsString = typeParameterName.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        diagnosticContextImplAt.report(typeof_non_reified_type_parameter_with_recursive_bound, strAsString);
    }

    @Override // org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner.IntrinsicsSupport
    public boolean rewritePluginDefinedOperationMarker(InstructionAdapter v, AbstractInsnNode reifiedInsn, InsnList instructions, IrType type) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(reifiedInsn, "reifiedInsn");
        Intrinsics.checkNotNullParameter(instructions, "instructions");
        Intrinsics.checkNotNullParameter(type, "type");
        List<JvmIrIntrinsicExtension> list = this.pluginExtensions;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (((JvmIrIntrinsicExtension) it.next()).rewritePluginDefinedOperationMarker(v, reifiedInsn, instructions, type)) {
                return true;
            }
        }
        return false;
    }
}
