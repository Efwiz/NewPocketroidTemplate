package org.jetbrains.kotlin.psi2ir.generators.fragments;

import androidx.exifinterface.media.ExifInterface;
import com.gaurav.avnc.vnc.XKeySym;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOriginImpl;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.impl.IrDelegatingConstructorCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrConstructorPublicSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension;
import org.jetbrains.kotlin.ir.util.DescriptorToIrUtilKt;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.OverridesKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtBlockCodeFragment;
import org.jetbrains.kotlin.psi2ir.generators.DeclarationGeneratorKt;
import org.jetbrains.kotlin.psi2ir.generators.Generator;
import org.jetbrains.kotlin.psi2ir.generators.GeneratorContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: FragmentDeclarationGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0002J\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0010\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u0012H\u0002J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\r\u001a\u00020\u000eH\u0002J0\u0010\u001e\u001a\u0002H\u001f\"\b\b\u0000\u0010\u001f*\u00020 *\u0002H\u001f2\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u0002H\u001f\u0012\u0004\u0012\u00020\u00160\"H\u0082\b¢\u0006\u0002\u0010#J\f\u0010$\u001a\u00020%*\u00020&H\u0002R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/fragments/FragmentDeclarationGenerator;", "Lorg/jetbrains/kotlin/psi2ir/generators/Generator;", "context", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "fragmentInfo", "Lorg/jetbrains/kotlin/psi2ir/generators/fragments/EvaluatorFragmentInfo;", "(Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;Lorg/jetbrains/kotlin/psi2ir/generators/fragments/EvaluatorFragmentInfo;)V", "getContext", "()Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "declareParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "parameterInfo", "Lorg/jetbrains/kotlin/psi2ir/generators/fragments/EvaluatorFragmentParameterInfo;", "delegatingCallToAnyConstructor", "Lorg/jetbrains/kotlin/ir/IrStatement;", "generateClassForCodeFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "ktFile", "Lorg/jetbrains/kotlin/psi/KtBlockCodeFragment;", "generateFragmentValueParameterDeclarations", "", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "generateFunctionForFragment", "generatePrimaryConstructor", "irClass", "shouldPromoteToSharedVariable", "", "buildWithScope", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "builder", "Lkotlin/Function1;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "toIrType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/types/KotlinType;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FragmentDeclarationGenerator implements Generator {
    private final GeneratorContext context;
    private final EvaluatorFragmentInfo fragmentInfo;

    public FragmentDeclarationGenerator(GeneratorContext context, EvaluatorFragmentInfo fragmentInfo) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(fragmentInfo, "fragmentInfo");
        this.context = context;
        this.fragmentInfo = fragmentInfo;
    }

    @Override // org.jetbrains.kotlin.ir.builders.IrGenerator
    public GeneratorContext getContext() {
        return this.context;
    }

    public final IrClass generateClassForCodeFragment(KtBlockCodeFragment ktFile) {
        Intrinsics.checkNotNullParameter(ktFile, "ktFile");
        final ClassDescriptor classDescriptor = this.fragmentInfo.getClassDescriptor();
        final int i = -1;
        final int i2 = -1;
        IrClass irClassDeclareClass = getContext().getSymbolTable().getDescriptorExtension().declareClass(classDescriptor, new Function1<IrClassSymbol, IrClass>() { // from class: org.jetbrains.kotlin.psi2ir.generators.fragments.FragmentDeclarationGenerator.generateClassForCodeFragment.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrClass invoke(IrClassSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrFactory irFactory = FragmentDeclarationGenerator.this.getContext().getIrFactory();
                int i3 = i;
                int i4 = i2;
                IrDeclarationOrigin.DEFINED defined = IrDeclarationOrigin.DEFINED.INSTANCE;
                ClassDescriptor classDescriptor2 = classDescriptor;
                Name nameNameForDeclaration = FragmentDeclarationGenerator.this.getContext().getSymbolTable().getNameProvider().nameForDeclaration(classDescriptor);
                DescriptorVisibility visibilityImpl = classDescriptor.getVisibilityImpl();
                Intrinsics.checkNotNullExpressionValue(visibilityImpl, "getVisibility(...)");
                return DescriptorToIrUtilKt.createIrClassFromDescriptor(irFactory, i3, i4, defined, it, classDescriptor2, nameNameForDeclaration, visibilityImpl, Modality.FINAL);
            }
        });
        SymbolTable symbolTable = getContext().getSymbolTable();
        symbolTable.enterScope(irClassDeclareClass);
        IrClass irClass = irClassDeclareClass;
        DescriptorSymbolTableExtension descriptorExtension = getContext().getSymbolTable().getDescriptorExtension();
        IrDeclarationOrigin.INSTANCE_RECEIVER instance_receiver = IrDeclarationOrigin.INSTANCE_RECEIVER.INSTANCE;
        ReceiverParameterDescriptor thisAsReceiverParameter = classDescriptor.getThisAsReceiverParameter();
        Intrinsics.checkNotNullExpressionValue(thisAsReceiverParameter, "getThisAsReceiverParameter(...)");
        KotlinType type = classDescriptor.getThisAsReceiverParameter().getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        irClass.setThisReceiver(DescriptorSymbolTableExtension.declareValueParameter$default(descriptorExtension, -1, -1, instance_receiver, thisAsReceiverParameter, toIrType(type), null, null, null, false, null, XKeySym.XK_amacron, null));
        generatePrimaryConstructor(irClass);
        irClass.getDeclarations().add(generateFunctionForFragment(ktFile));
        Unit unit = Unit.INSTANCE;
        symbolTable.leaveScope(irClassDeclareClass);
        return irClass;
    }

    private final void generatePrimaryConstructor(IrClass irClass) {
        IrFactory irFactory = getContext().getIrFactory();
        IrDeclarationOrigin.DEFINED defined = IrDeclarationOrigin.DEFINED.INSTANCE;
        Name nameSpecial = Name.special("<init>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        DescriptorVisibility visibility = irClass.getVisibility();
        IrSimpleType defaultType = IrUtilsKt.getDefaultType(irClass);
        IdSignature idSignatureComposeSignature = getContext().getSymbolTable().getSignaturer().composeSignature(irClass.getDescriptor());
        Intrinsics.checkNotNull(idSignatureComposeSignature);
        IrConstructor irConstructorCreateConstructor$default = IrFactory.DefaultImpls.createConstructor$default(irFactory, -1, -1, (IrDeclarationOrigin) defined, nameSpecial, visibility, false, false, (IrType) defaultType, (IrConstructorSymbol) new IrConstructorPublicSymbolImpl(idSignatureComposeSignature, null, 2, null), true, false, (DeserializedContainerSource) null, 2048, (Object) null);
        irConstructorCreateConstructor$default.setParent(irClass);
        IrBlockBody irBlockBodyCreateBlockBody = getContext().getIrFactory().createBlockBody(-1, -1);
        irBlockBodyCreateBlockBody.getStatements().add(delegatingCallToAnyConstructor());
        irConstructorCreateConstructor$default.setBody(irBlockBodyCreateBlockBody);
        IrDeclarationsKt.addMember(irClass, irConstructorCreateConstructor$default);
    }

    private final IrStatement delegatingCallToAnyConstructor() {
        Collection<ClassConstructorDescriptor> constructors = getContext().getIrBuiltIns().getAnyClass().getDescriptor().getConstructors();
        Intrinsics.checkNotNullExpressionValue(constructors, "getConstructors(...)");
        ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor) CollectionsKt.single(constructors);
        IrDelegatingConstructorCallImpl.Companion companion = IrDelegatingConstructorCallImpl.INSTANCE;
        IrType unitType = getContext().getIrBuiltIns().getUnitType();
        DescriptorSymbolTableExtension descriptorExtension = getContext().getSymbolTable().getDescriptorExtension();
        Intrinsics.checkNotNull(classConstructorDescriptor);
        return IrDelegatingConstructorCallImpl.Companion.fromSymbolDescriptor$default(companion, -1, -1, unitType, descriptorExtension.referenceConstructor(classConstructorDescriptor), 0, 0, 48, null);
    }

    private final IrSimpleFunction generateFunctionForFragment(KtBlockCodeFragment ktFile) {
        IrSimpleFunction irSimpleFunctionDeclareSimpleFunctionWithOverrides = OverridesKt.declareSimpleFunctionWithOverrides(getContext().getSymbolTable(), -1, -1, IrDeclarationOrigin.DEFINED.INSTANCE, this.fragmentInfo.getMethodDescriptor());
        SymbolTable symbolTable = getContext().getSymbolTable();
        symbolTable.enterScope(irSimpleFunctionDeclareSimpleFunctionWithOverrides);
        IrSimpleFunction irSimpleFunction = irSimpleFunctionDeclareSimpleFunctionWithOverrides;
        KotlinType returnType = this.fragmentInfo.getMethodDescriptor().getReturnType();
        Intrinsics.checkNotNull(returnType);
        irSimpleFunction.setReturnType(toIrType(returnType));
        generateFragmentValueParameterDeclarations(irSimpleFunction);
        irSimpleFunction.setBody(DeclarationGeneratorKt.createBodyGenerator$default(this, irSimpleFunction.getSymbol(), null, 2, null).generateExpressionBody(ktFile.getContentElement()));
        Unit unit = Unit.INSTANCE;
        symbolTable.leaveScope(irSimpleFunctionDeclareSimpleFunctionWithOverrides);
        return irSimpleFunction;
    }

    private final void generateFragmentValueParameterDeclarations(IrSimpleFunction irFunction) {
        List<ValueParameterDescriptor> valueParameters = irFunction.getDescriptor().getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        int i = 0;
        for (Object obj : valueParameters) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) obj;
            EvaluatorFragmentParameterInfo evaluatorFragmentParameterInfo = this.fragmentInfo.getParameters().get(i);
            List<IrValueParameter> valueParameters2 = irFunction.getValueParameters();
            Intrinsics.checkNotNull(valueParameterDescriptor);
            IrValueParameter irValueParameterDeclareParameter = declareParameter(valueParameterDescriptor, evaluatorFragmentParameterInfo);
            FragmentContext fragmentContext = getContext().getFragmentContext();
            Intrinsics.checkNotNull(fragmentContext);
            fragmentContext.getCapturedDescriptorToFragmentParameterMap().put(evaluatorFragmentParameterInfo.getDescriptor(), irValueParameterDeclareParameter.getSymbol());
            Unit unit = Unit.INSTANCE;
            irFunction.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) valueParameters2, irValueParameterDeclareParameter));
            i = i2;
        }
    }

    private final IrValueParameter declareParameter(ValueParameterDescriptor descriptor, EvaluatorFragmentParameterInfo parameterInfo) {
        DescriptorSymbolTableExtension descriptorExtension = getContext().getSymbolTable().getDescriptorExtension();
        IrDeclarationOriginImpl irDeclarationOriginImpl = shouldPromoteToSharedVariable(parameterInfo) ? IrDeclarationOrigin.SHARED_VARIABLE_IN_EVALUATOR_FRAGMENT.INSTANCE : IrDeclarationOrigin.DEFINED.INSTANCE;
        ValueParameterDescriptor valueParameterDescriptor = descriptor;
        KotlinType type = descriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        IrType irType = toIrType(type);
        KotlinType varargElementType = descriptor.getVarargElementType();
        return DescriptorSymbolTableExtension.declareValueParameter$default(descriptorExtension, -1, -1, irDeclarationOriginImpl, valueParameterDescriptor, irType, varargElementType != null ? toIrType(varargElementType) : null, null, null, parameterInfo.isLValue(), null, 640, null);
    }

    private final boolean shouldPromoteToSharedVariable(EvaluatorFragmentParameterInfo parameterInfo) {
        return parameterInfo.isLValue() || BindingContextUtils.isBoxedLocalCapturedInClosure(getContext().getBindingContext(), parameterInfo.getDescriptor());
    }

    private final IrType toIrType(KotlinType kotlinType) {
        return getContext().getTypeTranslator().translateType(kotlinType);
    }
}
