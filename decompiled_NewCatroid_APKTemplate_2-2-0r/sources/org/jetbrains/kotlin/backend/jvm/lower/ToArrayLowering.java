package org.jetbrains.kotlin.backend.jvm.lower;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.common.ClassLoweringPass;
import org.jetbrains.kotlin.backend.common.descriptors.DescriptorUtilsKt;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.JvmSymbols;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrTypeParameterBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrValueParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ToArrayLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0013B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J>\u0010\u000b\u001a\u00020\b*\u00020\n2\u0006\u0010\f\u001a\u00020\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\r0\u000f2\u0014\u0010\u0011\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0012\u0012\u0004\u0012\u00020\u00100\u000fH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/ToArrayLowering;", "Lorg/jetbrains/kotlin/backend/common/ClassLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "symbols", "Lorg/jetbrains/kotlin/backend/jvm/JvmSymbols;", "lower", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "findOrCreate", "indirectSubclass", "", "matcher", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "fallback", "Lorg/jetbrains/kotlin/backend/jvm/lower/ToArrayLowering$ToArrayBridge;", "ToArrayBridge", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ToArrayLowering implements ClassLoweringPass {
    private final JvmBackendContext context;
    private final JvmSymbols symbols;

    public ToArrayLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.symbols = context.getIr().getSymbols();
    }

    @Override // org.jetbrains.kotlin.backend.common.ClassLoweringPass
    public void lower(final IrClass irClass) {
        Object next;
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        if (JvmIrUtilsKt.isJvmInterface(irClass) || !ToArrayLoweringKt.isCollectionSubClass(irClass)) {
            return;
        }
        Iterator it = SequencesKt.generateSequence(ToArrayLoweringKt.getSuperClass(irClass), new PropertyReference1() { // from class: org.jetbrains.kotlin.backend.jvm.lower.ToArrayLowering$lower$indirectCollectionSubClass$1
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getSuperClass(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrClass;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ToArrayLoweringKt.getSuperClass((IrClass) obj);
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "superClass";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinPackage(ToArrayLoweringKt.class, "backend.jvm.lower");
            }
        }).iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (Intrinsics.areEqual(((IrClass) next).getOrigin(), IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE));
        IrClass irClass2 = (IrClass) next;
        boolean z = false;
        if (irClass2 != null && ToArrayLoweringKt.isCollectionSubClass(irClass2)) {
            z = true;
        }
        findOrCreate(irClass, z, new Function1<IrSimpleFunction, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.ToArrayLowering.lower.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrSimpleFunction it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return Boolean.valueOf(ToArrayLoweringKt.isGenericToArray(it2, ToArrayLowering.this.context));
            }
        }, new Function1<ToArrayBridge, IrSimpleFunction>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.ToArrayLowering.lower.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrSimpleFunction invoke(ToArrayBridge toArrayBridge) {
                IrClass irClass3 = irClass;
                IrFactory factory = irClass3.getFactory();
                IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                Name nameIdentifier = Name.identifier("toArray");
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                irFunctionBuilder.setName(nameIdentifier);
                irFunctionBuilder.setOrigin(JvmLoweredDeclarationOrigin.TO_ARRAY.INSTANCE);
                irFunctionBuilder.setModality(toArrayBridge != null ? Modality.FINAL : Modality.OPEN);
                IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(factory, irFunctionBuilder);
                IrClass irClass4 = irClass3;
                irClass4.getDeclarations().add(irSimpleFunctionBuildFunction);
                irSimpleFunctionBuildFunction.setParent(irClass4);
                ToArrayLowering toArrayLowering = this;
                IrClass irClass5 = irClass;
                IrSimpleFunction irSimpleFunction = irSimpleFunctionBuildFunction;
                IrTypeParameterBuilder irTypeParameterBuilder = new IrTypeParameterBuilder();
                Name nameIdentifier2 = Name.identifier(ExifInterface.GPS_DIRECTION_TRUE);
                Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
                irTypeParameterBuilder.setName(nameIdentifier2);
                irTypeParameterBuilder.setOrigin(JvmLoweredDeclarationOrigin.TO_ARRAY.INSTANCE);
                irTypeParameterBuilder.getSuperTypes().add(toArrayLowering.context.getIrBuiltIns().getAnyNType());
                if (irTypeParameterBuilder.getIndex() == -1) {
                    irTypeParameterBuilder.setIndex(irSimpleFunction.getTypeParameters().size());
                }
                IrTypeParameter irTypeParameterBuildTypeParameter = DeclarationBuildersKt.buildTypeParameter(irSimpleFunction.getFactory(), irTypeParameterBuilder, irSimpleFunction);
                irSimpleFunction.setTypeParameters(CollectionsKt.plus((Collection<? extends IrTypeParameter>) irSimpleFunction.getTypeParameters(), irTypeParameterBuildTypeParameter));
                irSimpleFunctionBuildFunction.setReturnType(IrTypesKt.typeWith(toArrayLowering.context.getIrBuiltIns().getArrayClass(), IrTypesKt.getDefaultType(irTypeParameterBuildTypeParameter)));
                IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
                irValueParameterBuilder.setType(IrUtilsKt.getDefaultType(irClass5));
                irValueParameterBuilder.setOrigin(JvmLoweredDeclarationOrigin.TO_ARRAY.INSTANCE);
                irValueParameterBuilder.setIndex(-1);
                irValueParameterBuilder.setName(DescriptorUtilsKt.getSynthesizedName("this"));
                IrValueParameter irValueParameterBuildValueParameter = DeclarationBuildersKt.buildValueParameter(irSimpleFunctionBuildFunction.getFactory(), irValueParameterBuilder, irSimpleFunctionBuildFunction);
                irSimpleFunctionBuildFunction.setDispatchReceiverParameter(irValueParameterBuildValueParameter);
                IrValueParameter irValueParameterAddValueParameter = DeclarationBuildersKt.addValueParameter(irSimpleFunctionBuildFunction, "array", irSimpleFunctionBuildFunction.getReturnType(), JvmLoweredDeclarationOrigin.TO_ARRAY.INSTANCE);
                DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(toArrayLowering.context, irSimpleFunctionBuildFunction.getSymbol(), 0, 0, 6, (Object) null);
                IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), declarationIrBuilderCreateIrBuilder$default.getStartOffset(), declarationIrBuilderCreateIrBuilder$default.getEndOffset());
                if (toArrayBridge == null) {
                    IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
                    IrCall irCallIrCall$default = ExpressionHelpersKt.irCall$default(irBlockBodyBuilder2, toArrayLowering.symbols.getGenericToArray(), toArrayLowering.symbols.getGenericToArray().getOwner().getReturnType(), 0, 0, null, 28, null);
                    irCallIrCall$default.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irValueParameterBuildValueParameter));
                    irCallIrCall$default.putValueArgument(1, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irValueParameterAddValueParameter));
                    Unit unit = Unit.INSTANCE;
                    irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, irCallIrCall$default));
                } else {
                    IrBlockBodyBuilder irBlockBodyBuilder3 = irBlockBodyBuilder;
                    IrCall irCallIrCall$default2 = ExpressionHelpersKt.irCall$default(irBlockBodyBuilder3, toArrayBridge.getTarget().getSymbol(), toArrayBridge.getTarget().getReturnType(), 0, 0, null, 28, null);
                    irCallIrCall$default2.setDispatchReceiver(ExpressionHelpersKt.irGet(irBlockBodyBuilder3, irValueParameterBuildValueParameter));
                    irCallIrCall$default2.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBodyBuilder3, irValueParameterAddValueParameter));
                    Unit unit2 = Unit.INSTANCE;
                    irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder3, irCallIrCall$default2));
                }
                irSimpleFunctionBuildFunction.setBody(irBlockBodyBuilder.getIrBlockBody());
                return irSimpleFunctionBuildFunction;
            }
        });
        findOrCreate(irClass, z, AnonymousClass3.INSTANCE, new Function1<ToArrayBridge, IrSimpleFunction>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.ToArrayLowering.lower.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrSimpleFunction invoke(ToArrayBridge toArrayBridge) {
                IrClass irClass3 = irClass;
                ToArrayLowering toArrayLowering = this;
                IrFactory factory = irClass3.getFactory();
                IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                Name nameIdentifier = Name.identifier("toArray");
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                irFunctionBuilder.setName(nameIdentifier);
                irFunctionBuilder.setOrigin(JvmLoweredDeclarationOrigin.TO_ARRAY.INSTANCE);
                irFunctionBuilder.setModality(toArrayBridge != null ? Modality.FINAL : Modality.OPEN);
                irFunctionBuilder.setReturnType(IrTypesKt.typeWith(toArrayLowering.context.getIrBuiltIns().getArrayClass(), toArrayLowering.context.getIrBuiltIns().getAnyNType()));
                IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(factory, irFunctionBuilder);
                IrClass irClass4 = irClass3;
                irClass4.getDeclarations().add(irSimpleFunctionBuildFunction);
                irSimpleFunctionBuildFunction.setParent(irClass4);
                ToArrayLowering toArrayLowering2 = this;
                IrClass irClass5 = irClass;
                IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
                irValueParameterBuilder.setType(IrUtilsKt.getDefaultType(irClass5));
                irValueParameterBuilder.setOrigin(JvmLoweredDeclarationOrigin.TO_ARRAY.INSTANCE);
                irValueParameterBuilder.setIndex(-1);
                irValueParameterBuilder.setName(DescriptorUtilsKt.getSynthesizedName("this"));
                IrValueParameter irValueParameterBuildValueParameter = DeclarationBuildersKt.buildValueParameter(irSimpleFunctionBuildFunction.getFactory(), irValueParameterBuilder, irSimpleFunctionBuildFunction);
                irSimpleFunctionBuildFunction.setDispatchReceiverParameter(irValueParameterBuildValueParameter);
                DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(toArrayLowering2.context, irSimpleFunctionBuildFunction.getSymbol(), 0, 0, 6, (Object) null);
                IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), declarationIrBuilderCreateIrBuilder$default.getStartOffset(), declarationIrBuilderCreateIrBuilder$default.getEndOffset());
                if (toArrayBridge == null) {
                    IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
                    IrCall irCallIrCall$default = ExpressionHelpersKt.irCall$default(irBlockBodyBuilder2, toArrayLowering2.symbols.getNonGenericToArray(), toArrayLowering2.symbols.getNonGenericToArray().getOwner().getReturnType(), 0, 0, null, 28, null);
                    irCallIrCall$default.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irValueParameterBuildValueParameter));
                    Unit unit = Unit.INSTANCE;
                    irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, irCallIrCall$default));
                } else {
                    IrBlockBodyBuilder irBlockBodyBuilder3 = irBlockBodyBuilder;
                    IrCall irCallIrCall$default2 = ExpressionHelpersKt.irCall$default(irBlockBodyBuilder3, toArrayBridge.getTarget().getSymbol(), toArrayBridge.getTarget().getReturnType(), 0, 0, null, 28, null);
                    irCallIrCall$default2.setDispatchReceiver(ExpressionHelpersKt.irGet(irBlockBodyBuilder3, irValueParameterBuildValueParameter));
                    Unit unit2 = Unit.INSTANCE;
                    irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder3, irCallIrCall$default2));
                }
                irSimpleFunctionBuildFunction.setBody(irBlockBodyBuilder.getIrBlockBody());
                return irSimpleFunctionBuildFunction;
            }
        });
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.backend.jvm.lower.ToArrayLowering$lower$3, reason: invalid class name */
    /* JADX INFO: compiled from: ToArrayLowering.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass3 extends FunctionReference implements Function1<IrSimpleFunction, Boolean> {
        public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

        AnonymousClass3() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "isNonGenericToArray(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)Z";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "isNonGenericToArray";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(ToArrayLoweringKt.class, "backend.jvm.lower");
        }

        @Override // kotlin.jvm.functions.Function1
        public final Boolean invoke(IrSimpleFunction p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return Boolean.valueOf(ToArrayLoweringKt.isNonGenericToArray(p0));
        }
    }

    /* JADX INFO: compiled from: ToArrayLowering.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/ToArrayLowering$ToArrayBridge;", "", "target", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)V", "getTarget", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ToArrayBridge {
        private final IrSimpleFunction target;

        public ToArrayBridge(IrSimpleFunction target) {
            Intrinsics.checkNotNullParameter(target, "target");
            this.target = target;
        }

        public final IrSimpleFunction getTarget() {
            return this.target;
        }
    }

    private final void findOrCreate(IrClass irClass, boolean z, Function1<? super IrSimpleFunction, Boolean> function1, Function1<? super ToArrayBridge, ? extends IrSimpleFunction> function2) {
        IrSimpleFunction next;
        Iterator<IrSimpleFunction> it = IrUtilsKt.getFunctions(irClass).iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!function1.invoke(next).booleanValue());
        IrSimpleFunction irSimpleFunction = next;
        if (irSimpleFunction == null) {
            if (z) {
                return;
            }
            function2.invoke(null);
        } else {
            if (Intrinsics.areEqual(irSimpleFunction.getVisibility(), DescriptorVisibilities.INTERNAL)) {
                function2.invoke(new ToArrayBridge(irSimpleFunction));
                return;
            }
            DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
            Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
            irSimpleFunction.setVisibility(PUBLIC);
        }
    }
}
