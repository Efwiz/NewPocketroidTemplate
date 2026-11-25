package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.common.ClassLoweringPass;
import org.jetbrains.kotlin.backend.common.ir.IrInlineUtilsKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmCachedDeclarations;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrExpressionBodyImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: InterfaceLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u001a\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\u000fH\u0002J\u0010\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u0010\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u0010\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0010\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001fH\u0016J\u0010\u0010 \u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020!H\u0016J\u0014\u0010\"\u001a\u00020\u0014*\u00020#2\u0006\u0010$\u001a\u00020\u000fH\u0002J\u0014\u0010%\u001a\u00020\u0014*\u00020#2\u0006\u0010$\u001a\u00020\u000fH\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R*\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\n0\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/InterfaceLowering;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "Lorg/jetbrains/kotlin/backend/common/ClassLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "removedFunctions", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "Lkotlin/collections/HashMap;", "removedFunctionsWithoutRemapping", "", "createDefaultImpl", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "function", "forCompatibility", "", "createJvmDefaultCompatibilityDelegate", "", "handleAnnotationClass", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "handleInterface", "lower", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "visitFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "visitReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "bridgeToStatic", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "callTarget", "bridgeViaAccessorTo", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InterfaceLowering extends IrElementTransformerVoid implements ClassLoweringPass {
    private final JvmBackendContext context;
    private final HashMap<IrSimpleFunctionSymbol, IrSimpleFunctionSymbol> removedFunctions;
    private final Set<IrSimpleFunctionSymbol> removedFunctionsWithoutRemapping;

    /* JADX INFO: compiled from: InterfaceLowering.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ClassKind.values().length];
            try {
                iArr[ClassKind.INTERFACE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClassKind.ANNOTATION_CLASS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public InterfaceLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.removedFunctions = new HashMap<>();
        this.removedFunctionsWithoutRemapping = new LinkedHashSet();
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.ClassLoweringPass
    public void lower(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        if (JvmIrUtilsKt.isJvmInterface(irClass)) {
            int i = WhenMappings.$EnumSwitchMapping$0[irClass.getKind().ordinal()];
            if (i == 1) {
                handleInterface(irClass);
            } else if (i != 2) {
                return;
            } else {
                handleAnnotationClass(irClass);
            }
            CollectionsKt.removeAll((List) irClass.getDeclarations(), (Function1) new Function1<IrDeclaration, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.InterfaceLowering.lower.1
                {
                    super(1);
                }

                /* JADX WARN: Code duplicated, block: B:9:0x0031  */
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrDeclaration it) {
                    boolean z;
                    Intrinsics.checkNotNullParameter(it, "it");
                    if (it instanceof IrFunction) {
                        IrFunction irFunction = (IrFunction) it;
                        if (InterfaceLowering.this.removedFunctions.containsKey(irFunction.getSymbol()) || CollectionsKt.contains(InterfaceLowering.this.removedFunctionsWithoutRemapping, irFunction.getSymbol())) {
                            z = true;
                        } else {
                            z = false;
                        }
                    } else {
                        z = false;
                    }
                    return Boolean.valueOf(z);
                }
            });
            IrClass defaultImplsClass = this.context.getCachedDeclarations().getDefaultImplsClass(irClass);
            if (!defaultImplsClass.getDeclarations().isEmpty()) {
                irClass.getDeclarations().add(defaultImplsClass);
            }
            IrElementTransformerVoidKt.transformChildrenVoid(irClass, this);
        }
    }

    private final void handleInterface(IrClass irClass) {
        DescriptorVisibility visibility;
        JvmDefaultMode jvmDefaultMode = this.context.getConfig().getJvmDefaultMode();
        boolean z = (jvmDefaultMode.isCompatibility() && !JvmIrUtilsKt.hasJvmDefaultNoCompatibilityAnnotation(irClass)) || (jvmDefaultMode == JvmDefaultMode.ALL_INCOMPATIBLE && JvmIrUtilsKt.hasJvmDefaultWithCompatibilityAnnotation(irClass));
        for (IrSimpleFunction irSimpleFunction : IrUtilsKt.getFunctions(irClass)) {
            if (irSimpleFunction.getModality() != Modality.ABSTRACT) {
                if (irSimpleFunction.getIsFakeOverride()) {
                    String strAsString = irSimpleFunction.getName().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    if (StringsKt.endsWith$default(strAsString, "$default", false, 2, (Object) null)) {
                        continue;
                    } else {
                        IrSimpleFunction irSimpleFunction2 = (IrSimpleFunction) IrFakeOverrideUtilsKt.resolveFakeOverride$default(irSimpleFunction, false, null, 3, null);
                        if (irSimpleFunction2 == null) {
                            throw new IllegalStateException(("No single implementation found for: " + RenderIrElementKt.render$default(irSimpleFunction, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                        }
                        if (!DescriptorVisibilities.isPrivate(irSimpleFunction2.getVisibility()) && !IrUtilsKt.isMethodOfAny(irSimpleFunction2)) {
                            if (!InheritedDefaultMethodsOnClassesLoweringKt.isDefinitelyNotDefaultImplsMethod(irSimpleFunction, jvmDefaultMode, irSimpleFunction2)) {
                                bridgeToStatic(createDefaultImpl$default(this, irSimpleFunction, false, 2, null), JvmCachedDeclarations.getDefaultImplsFunction$default(this.context.getCachedDeclarations(), JvmIrUtilsKt.firstSuperMethodFromKotlin(irSimpleFunction, irSimpleFunction2).getOwner(), false, 2, null));
                            } else if (z && JvmIrUtilsKt.isCompiledToJvmDefault(irSimpleFunction2, jvmDefaultMode)) {
                                createJvmDefaultCompatibilityDelegate(irSimpleFunction);
                            }
                        }
                    }
                } else if ((!JvmIrUtilsKt.isCompiledToJvmDefault(irSimpleFunction, jvmDefaultMode) && (DescriptorVisibilities.isPrivate(irSimpleFunction.getVisibility()) || Intrinsics.areEqual(irSimpleFunction.getOrigin(), IrDeclarationOrigin.FUNCTION_FOR_DEFAULT_PARAMETER.INSTANCE) || Intrinsics.areEqual(irSimpleFunction.getOrigin(), JvmLoweredDeclarationOrigin.SYNTHETIC_METHOD_FOR_PROPERTY_OR_TYPEALIAS_ANNOTATIONS.INSTANCE))) || (Intrinsics.areEqual(irSimpleFunction.getOrigin(), JvmLoweredDeclarationOrigin.SYNTHETIC_METHOD_FOR_PROPERTY_OR_TYPEALIAS_ANNOTATIONS.INSTANCE) && ((z || jvmDefaultMode == JvmDefaultMode.ENABLE) && JvmIrUtilsKt.isCompiledToJvmDefault(irSimpleFunction, jvmDefaultMode)))) {
                    if (Intrinsics.areEqual(irSimpleFunction.getOrigin(), JvmLoweredDeclarationOrigin.INLINE_LAMBDA.INSTANCE)) {
                        IrClass defaultImplsClass = this.context.getCachedDeclarations().getDefaultImplsClass(irClass);
                        defaultImplsClass.getDeclarations().add(irSimpleFunction);
                        this.removedFunctionsWithoutRemapping.add(irSimpleFunction.getSymbol());
                        irSimpleFunction.setParent(defaultImplsClass);
                    } else {
                        IrSimpleFunction irSimpleFunctionCreateDefaultImpl$default = createDefaultImpl$default(this, irSimpleFunction, false, 2, null);
                        irSimpleFunctionCreateDefaultImpl$default.setBody(IrInlineUtilsKt.moveBodyTo(irSimpleFunction, irSimpleFunctionCreateDefaultImpl$default));
                        this.removedFunctions.put(irSimpleFunction.getSymbol(), irSimpleFunctionCreateDefaultImpl$default.getSymbol());
                    }
                } else if (!JvmIrUtilsKt.isCompiledToJvmDefault(irSimpleFunction, jvmDefaultMode)) {
                    IrSimpleFunction irSimpleFunctionCreateDefaultImpl$default2 = createDefaultImpl$default(this, irSimpleFunction, false, 2, null);
                    irSimpleFunctionCreateDefaultImpl$default2.setBody(IrInlineUtilsKt.moveBodyTo(irSimpleFunction, irSimpleFunctionCreateDefaultImpl$default2));
                    irSimpleFunction.setBody(null);
                } else if (z) {
                    if (Intrinsics.areEqual(irSimpleFunction.getOrigin(), IrDeclarationOrigin.FUNCTION_FOR_DEFAULT_PARAMETER.INSTANCE)) {
                        IrFunction irFunction = this.context.getMapping().getDefaultArgumentsOriginalFunction().get(irSimpleFunction);
                        Intrinsics.checkNotNull(irFunction);
                        visibility = irFunction.getVisibility();
                    } else {
                        visibility = irSimpleFunction.getVisibility();
                    }
                    if (!DescriptorVisibilities.isPrivate(visibility)) {
                        createJvmDefaultCompatibilityDelegate(irSimpleFunction);
                    }
                }
            }
        }
        IrClass defaultImplsClass2 = this.context.getCachedDeclarations().getDefaultImplsClass(irClass);
        List<IrDeclaration> declarations = irClass.getDeclarations();
        ArrayList<IrField> arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof IrField) {
                arrayList.add(obj);
            }
        }
        for (IrField irField : arrayList) {
            if ((!jvmDefaultMode.getForAllMethodsWithBody() && Intrinsics.areEqual(irField.getOrigin(), JvmLoweredDeclarationOrigin.GENERATED_PROPERTY_REFERENCE.INSTANCE)) || Intrinsics.areEqual(irField.getOrigin(), JvmLoweredDeclarationOrigin.GENERATED_ASSERTION_ENABLED_FIELD.INSTANCE)) {
                irClass.getDeclarations().remove(irField);
                defaultImplsClass2.getDeclarations().add(0, irField);
                irField.setParent(defaultImplsClass2);
            }
        }
    }

    private final void createJvmDefaultCompatibilityDelegate(IrSimpleFunction function) {
        bridgeViaAccessorTo(createDefaultImpl(function, true), function);
    }

    private final void handleAnnotationClass(IrClass irClass) {
        Sequence<IrSimpleFunction> sequenceFilter = SequencesKt.filter(IrUtilsKt.getFunctions(irClass), new Function1<IrSimpleFunction, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.InterfaceLowering$handleAnnotationClass$annotationsMethods$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrSimpleFunction it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(Intrinsics.areEqual(it.getOrigin(), JvmLoweredDeclarationOrigin.SYNTHETIC_METHOD_FOR_PROPERTY_OR_TYPEALIAS_ANNOTATIONS.INSTANCE));
            }
        });
        if (SequencesKt.none(sequenceFilter)) {
            return;
        }
        for (IrSimpleFunction irSimpleFunction : sequenceFilter) {
            IrSimpleFunction irSimpleFunctionCreateDefaultImpl$default = createDefaultImpl$default(this, irSimpleFunction, false, 2, null);
            irSimpleFunctionCreateDefaultImpl$default.setBody(IrInlineUtilsKt.moveBodyTo(irSimpleFunction, irSimpleFunctionCreateDefaultImpl$default));
            this.removedFunctions.put(irSimpleFunction.getSymbol(), irSimpleFunctionCreateDefaultImpl$default.getSymbol());
        }
    }

    static /* synthetic */ IrSimpleFunction createDefaultImpl$default(InterfaceLowering interfaceLowering, IrSimpleFunction irSimpleFunction, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return interfaceLowering.createDefaultImpl(irSimpleFunction, z);
    }

    private final IrSimpleFunction createDefaultImpl(IrSimpleFunction function, boolean forCompatibility) {
        IrSimpleFunction defaultImplsFunction = this.context.getCachedDeclarations().getDefaultImplsFunction(function, forCompatibility);
        IrUtilsKt.getParentAsClass(defaultImplsFunction).getDeclarations().add(defaultImplsFunction);
        return defaultImplsFunction;
    }

    private final void bridgeToStatic(IrFunction irFunction, IrSimpleFunction irSimpleFunction) {
        IrCallImpl.Companion companion = IrCallImpl.INSTANCE;
        int startOffset = irFunction.getStartOffset();
        int endOffset = irFunction.getEndOffset();
        IrType returnType = irFunction.getReturnType();
        IrSimpleFunctionSymbol symbol = irSimpleFunction.getSymbol();
        IrCallImpl irCallImplFromSymbolOwner = companion.fromSymbolOwner(startOffset, endOffset, returnType, symbol, (240 & 16) != 0 ? symbol.getOwner().getTypeParameters().size() : 0, (240 & 32) != 0 ? symbol.getOwner().getValueParameters().size() : 0, (240 & 64) != 0 ? null : null, (240 & 128) != 0 ? null : null);
        int i = 0;
        int i2 = 0;
        for (Object obj : irSimpleFunction.getTypeParameters()) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            irCallImplFromSymbolOwner.putTypeArgument(i2, JvmIrUtilsKt.createPlaceholderAnyNType(this.context.getIrBuiltIns()));
            i2 = i3;
        }
        for (Object obj2 : irFunction.getValueParameters()) {
            int i4 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            irCallImplFromSymbolOwner.putValueArgument(i, new IrGetValueImpl(irFunction.getStartOffset(), irFunction.getEndOffset(), ((IrValueParameter) obj2).getSymbol(), null, 8, null));
            i = i4;
        }
        irFunction.setBody(new IrExpressionBodyImpl(irCallImplFromSymbolOwner));
    }

    private final void bridgeViaAccessorTo(IrFunction irFunction, IrSimpleFunction irSimpleFunction) {
        IrCallImpl.Companion companion = IrCallImpl.INSTANCE;
        int startOffset = irFunction.getStartOffset();
        int endOffset = irFunction.getEndOffset();
        IrType returnType = irFunction.getReturnType();
        IrSimpleFunctionSymbol symbol = irSimpleFunction.getSymbol();
        IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
        IrCallImpl irCallImplFromSymbolOwner = companion.fromSymbolOwner(startOffset, endOffset, returnType, symbol, (240 & 16) != 0 ? symbol.getOwner().getTypeParameters().size() : 0, (240 & 32) != 0 ? symbol.getOwner().getValueParameters().size() : 0, (240 & 64) != 0 ? null : null, (240 & 128) != 0 ? null : IrUtilsKt.getParentAsClass(irSimpleFunction2).getSymbol());
        int i = 0;
        int i2 = 0;
        for (Object obj : CollectionsKt.drop(irFunction.getTypeParameters(), IrUtilsKt.getParentAsClass(irSimpleFunction2).getTypeParameters().size())) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            irCallImplFromSymbolOwner.putTypeArgument(i2, IrTypesKt.getDefaultType((IrTypeParameter) obj));
            i2 = i3;
        }
        if (irSimpleFunction.getDispatchReceiverParameter() != null) {
            irCallImplFromSymbolOwner.setDispatchReceiver(new IrGetValueImpl(irFunction.getStartOffset(), irFunction.getEndOffset(), irFunction.getValueParameters().get(0).getSymbol(), null, 8, null));
            i = 1;
        }
        if (irSimpleFunction.getExtensionReceiverParameter() != null) {
            irCallImplFromSymbolOwner.setExtensionReceiver(new IrGetValueImpl(irFunction.getStartOffset(), irFunction.getEndOffset(), irFunction.getValueParameters().get(i).getSymbol(), null, 8, null));
            i++;
        }
        int size = irFunction.getValueParameters().size();
        for (int i4 = i; i4 < size; i4++) {
            irCallImplFromSymbolOwner.putValueArgument(i4 - i, new IrGetValueImpl(irFunction.getStartOffset(), irFunction.getEndOffset(), irFunction.getValueParameters().get(i4).getSymbol(), null, 8, null));
        }
        irFunction.setBody(new IrExpressionBodyImpl(irCallImplFromSymbolOwner));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitReturn(IrReturn expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = this.removedFunctions.get(expression.getReturnTargetSymbol());
        IrSimpleFunction owner = irSimpleFunctionSymbol != null ? irSimpleFunctionSymbol.getOwner() : null;
        if (owner != null) {
            expression = new IrReturnImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), owner.getSymbol(), expression.getValue());
        }
        return super.visitReturn(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitCall(IrCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = this.removedFunctions.get(expression.getSymbol());
        IrSimpleFunction owner = irSimpleFunctionSymbol != null ? irSimpleFunctionSymbol.getOwner() : null;
        if (owner != null) {
            expression = JvmIrUtilsKt.createDelegatingCallWithPlaceholderTypeArguments(expression, owner, this.context.getIrBuiltIns());
        }
        return super.visitCall(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitFunctionReference(IrFunctionReference expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = this.removedFunctions.get(expression.getSymbol());
        IrSimpleFunction owner = irSimpleFunctionSymbol != null ? irSimpleFunctionSymbol.getOwner() : null;
        if (owner != null) {
            IrFunctionReferenceImpl irFunctionReferenceImpl = new IrFunctionReferenceImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), owner.getSymbol(), owner.getTypeParameters().size(), owner.getValueParameters().size(), expression.getReflectionTarget(), expression.getOrigin());
            JvmIrUtilsKt.copyFromWithPlaceholderTypeArguments(irFunctionReferenceImpl, expression, this.context.getIrBuiltIns());
            IrDeclarationsKt.copyAttributes(irFunctionReferenceImpl, expression);
            expression = irFunctionReferenceImpl;
        }
        return super.visitFunctionReference(expression);
    }
}
