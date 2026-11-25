package org.jetbrains.kotlin.backend.common.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.impl.IrErrorExpressionImpl;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.CollectionsKt;
import org.jetbrains.kotlin.utils.MemoryOptimizedCollectionUtilKt;

/* JADX INFO: compiled from: DefaultArgumentFunctionFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bJF\u0010\r\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u000b2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013J>\u0010\u0015\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00102\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u000bH\u0002J\u0014\u0010\u001a\u001a\u00020\u001b*\u00020\b2\u0006\u0010\u001c\u001a\u00020\bH\u0004J\u0014\u0010\u001d\u001a\u00020\u001b*\u00020\b2\u0006\u0010\u001c\u001a\u00020\bH\u0004J\u0014\u0010\u001e\u001a\u00020\u001b*\u00020\b2\u0006\u0010\u001c\u001a\u00020\bH\u0004J\u0014\u0010\u001f\u001a\u00020\u001b*\u00020\b2\u0006\u0010\u001c\u001a\u00020\bH\u0004J\u001c\u0010 \u001a\u00020\u001b*\u00020\b2\u0006\u0010\u001c\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u000bH$J\f\u0010!\u001a\u00020\"*\u00020\bH\u0004J\f\u0010#\u001a\u00020\u000b*\u00020$H\u0014R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/DefaultArgumentFunctionFactory;", "", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "findBaseFunctionWithDefaultArgumentsFor", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "declaration", "skipInlineMethods", "", "skipExternalMethods", "generateDefaultsFunction", "forceSetOverrideSymbols", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "useConstructorMarker", "copiedAnnotations", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "generateDefaultsFunctionImpl", "newOrigin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "newVisibility", "isFakeOverride", "copyAttributesFrom", "", "original", "copyReceiversFrom", "copyReturnTypeFrom", "copyValueParametersFrom", "generateDefaultArgumentStubFrom", "generateDefaultArgumentsFunctionName", "Lorg/jetbrains/kotlin/name/Name;", "hasNullAsUndefinedValue", "Lorg/jetbrains/kotlin/ir/types/IrType;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DefaultArgumentFunctionFactory {
    private final CommonBackendContext context;

    protected abstract void generateDefaultArgumentStubFrom(IrFunction irFunction, IrFunction irFunction2, boolean z);

    protected boolean hasNullAsUndefinedValue(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return true;
    }

    public DefaultArgumentFunctionFactory(CommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final CommonBackendContext getContext() {
        return this.context;
    }

    protected final Name generateDefaultArgumentsFunctionName(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Name nameIdentifier = Name.identifier(irFunction.getName() + "$default");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return nameIdentifier;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected final void copyAttributesFrom(IrFunction irFunction, IrFunction original) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(original, "original");
        IrAttributeContainer irAttributeContainer = irFunction instanceof IrAttributeContainer ? (IrAttributeContainer) irFunction : null;
        if (irAttributeContainer != null) {
            IrDeclarationsKt.copyAttributes(irAttributeContainer, original instanceof IrAttributeContainer ? (IrAttributeContainer) original : null);
        }
    }

    protected final void copyReturnTypeFrom(IrFunction irFunction, IrFunction original) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(original, "original");
        irFunction.setReturnType(IrUtilsKt.remapTypeParameters$default(original.getReturnType(), IrUtilsKt.getClassIfConstructor(original), IrUtilsKt.getClassIfConstructor(irFunction), null, 4, null));
    }

    protected final void copyReceiversFrom(IrFunction irFunction, IrFunction original) {
        IrFunction irFunction2;
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(original, "original");
        IrValueParameter dispatchReceiverParameter = original.getDispatchReceiverParameter();
        IrValueParameter irValueParameterCopyTo$default = null;
        irFunction.setDispatchReceiverParameter(dispatchReceiverParameter != null ? IrUtilsKt.copyTo$default(dispatchReceiverParameter, irFunction, null, 0, 0, 0, null, null, null, null, null, false, false, false, 8190, null) : null);
        IrValueParameter extensionReceiverParameter = original.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            irFunction2 = irFunction;
            irValueParameterCopyTo$default = IrUtilsKt.copyTo$default(extensionReceiverParameter, irFunction2, null, 0, 0, 0, null, null, null, null, null, false, false, false, 8190, null);
        } else {
            irFunction2 = irFunction;
        }
        irFunction2.setExtensionReceiverParameter(irValueParameterCopyTo$default);
        irFunction2.setContextReceiverParametersCount(original.getContextReceiverParametersCount());
    }

    protected final void copyValueParametersFrom(IrFunction irFunction, IrFunction irFunction2) {
        IrExpressionBody irExpressionBodyCreateExpressionBody;
        IrFunction irFunction3 = irFunction;
        IrFunction original = irFunction2;
        Intrinsics.checkNotNullParameter(irFunction3, "<this>");
        Intrinsics.checkNotNullParameter(original, "original");
        List<IrValueParameter> valueParameters = irFunction2.getValueParameters();
        ArrayList arrayList = new ArrayList(valueParameters.size());
        for (IrValueParameter irValueParameter : valueParameters) {
            IrType irTypeRemapTypeParameters$default = IrUtilsKt.remapTypeParameters$default(irValueParameter.getType(), IrUtilsKt.getClassIfConstructor(original), IrUtilsKt.getClassIfConstructor(irFunction3), null, 4, null);
            if (irValueParameter.getDefaultValue() != null && hasNullAsUndefinedValue(irValueParameter.getType())) {
                irTypeRemapTypeParameters$default = IrTypesKt.makeNullable(irTypeRemapTypeParameters$default);
            }
            IrType irType = irTypeRemapTypeParameters$default;
            if (irValueParameter.getDefaultValue() != null) {
                IrFactory factory = irFunction2.getFactory();
                IrErrorExpressionImpl irErrorExpressionImpl = new IrErrorExpressionImpl(-1, -1, irValueParameter.getType(), "Default Stub");
                IrExpressionBody defaultValue = irValueParameter.getDefaultValue();
                Intrinsics.checkNotNull(defaultValue);
                irErrorExpressionImpl.setAttributeOwnerId(defaultValue.getExpression());
                Unit unit = Unit.INSTANCE;
                irExpressionBodyCreateExpressionBody = IrFactoryHelpersKt.createExpressionBody(factory, irErrorExpressionImpl);
            } else {
                irExpressionBodyCreateExpressionBody = null;
            }
            ArrayList arrayList2 = arrayList;
            arrayList2.add(IrUtilsKt.copyTo$default(irValueParameter, irFunction, null, 0, 0, 0, null, null, irType, null, irExpressionBodyCreateExpressionBody, false, false, irValueParameter.getDefaultValue() != null, 3454, null));
            irFunction3 = irFunction;
            original = irFunction2;
            arrayList = arrayList2;
        }
        irFunction.setValueParameters(CollectionsKt.compactIfPossible(arrayList));
    }

    public final IrFunction findBaseFunctionWithDefaultArgumentsFor(IrFunction declaration, boolean skipInlineMethods, boolean skipExternalMethods) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return findBaseFunctionWithDefaultArgumentsFor$dfsImpl(declaration, new LinkedHashSet(), skipInlineMethods, skipExternalMethods);
    }

    private static final IrFunction findBaseFunctionWithDefaultArgumentsFor$dfsImpl(IrFunction irFunction, Set<IrFunction> set, boolean z, boolean z2) {
        IrFunction irFunctionFindBaseFunctionWithDefaultArgumentsFor$dfsImpl;
        set.add(irFunction);
        if (irFunction.getIsInline() && z) {
            return null;
        }
        if (z2 && IrUtilsKt.isExternalOrInheritedFromExternal(irFunction)) {
            return null;
        }
        if (irFunction instanceof IrSimpleFunction) {
            Iterator<T> it = ((IrSimpleFunction) irFunction).getOverriddenSymbols().iterator();
            while (it.hasNext()) {
                IrSimpleFunction owner = ((IrSimpleFunctionSymbol) it.next()).getOwner();
                if (!set.contains(owner) && (irFunctionFindBaseFunctionWithDefaultArgumentsFor$dfsImpl = findBaseFunctionWithDefaultArgumentsFor$dfsImpl(owner, set, z, z2)) != null) {
                    return irFunctionFindBaseFunctionWithDefaultArgumentsFor$dfsImpl;
                }
            }
        }
        List<IrValueParameter> valueParameters = irFunction.getValueParameters();
        if (!(valueParameters instanceof Collection) || !valueParameters.isEmpty()) {
            Iterator<T> it2 = valueParameters.iterator();
            while (it2.hasNext()) {
                if (((IrValueParameter) it2.next()).getDefaultValue() != null) {
                    return irFunction;
                }
            }
        }
        return null;
    }

    public final IrFunction generateDefaultsFunction(IrFunction declaration, boolean skipInlineMethods, boolean skipExternalMethods, boolean forceSetOverrideSymbols, DescriptorVisibility visibility, boolean useConstructorMarker, List<? extends IrConstructorCall> copiedAnnotations) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(copiedAnnotations, "copiedAnnotations");
        if (skipInlineMethods && declaration.getIsInline()) {
            return null;
        }
        if (skipExternalMethods && IrUtilsKt.isExternalOrInheritedFromExternal(declaration)) {
            return null;
        }
        IrFunction irFunction = declaration;
        if (this.context.getMapping().getDefaultArgumentsOriginalFunction().get(irFunction) != null) {
            return null;
        }
        IrFunction irFunction2 = this.context.getMapping().getDefaultArgumentsDispatchFunction().get(irFunction);
        if (irFunction2 != null) {
            return irFunction2;
        }
        if (declaration instanceof IrSimpleFunction) {
            IrSimpleFunction irSimpleFunction = (IrSimpleFunction) declaration;
            List<IrSimpleFunctionSymbol> overriddenSymbols = irSimpleFunction.getOverriddenSymbols();
            if (!(overriddenSymbols instanceof Collection) || !overriddenSymbols.isEmpty()) {
                Iterator<T> it = overriddenSymbols.iterator();
                while (it.hasNext()) {
                    if (findBaseFunctionWithDefaultArgumentsFor(((IrSimpleFunctionSymbol) it.next()).getOwner(), skipInlineMethods, skipExternalMethods) != null) {
                        IrFunction irFunctionGenerateDefaultsFunctionImpl = generateDefaultsFunctionImpl(declaration, IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE, visibility, copiedAnnotations, true, useConstructorMarker);
                        this.context.getMapping().getDefaultArgumentsDispatchFunction().set(irFunction, irFunctionGenerateDefaultsFunctionImpl);
                        this.context.getMapping().getDefaultArgumentsOriginalFunction().set(irFunctionGenerateDefaultsFunctionImpl, declaration);
                        if (forceSetOverrideSymbols) {
                            Intrinsics.checkNotNull(irFunctionGenerateDefaultsFunctionImpl, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
                            IrSimpleFunction irSimpleFunction2 = (IrSimpleFunction) irFunctionGenerateDefaultsFunctionImpl;
                            List<IrSimpleFunctionSymbol> overriddenSymbols2 = irSimpleFunction2.getOverriddenSymbols();
                            List<IrSimpleFunctionSymbol> overriddenSymbols3 = irSimpleFunction.getOverriddenSymbols();
                            ArrayList arrayList = new ArrayList();
                            for (IrSimpleFunctionSymbol irSimpleFunctionSymbol : overriddenSymbols3) {
                                List<IrSimpleFunctionSymbol> list = overriddenSymbols2;
                                IrFunction irFunctionGenerateDefaultsFunction = generateDefaultsFunction(irSimpleFunctionSymbol.getOwner(), skipInlineMethods, skipExternalMethods, forceSetOverrideSymbols, visibility, useConstructorMarker, IrUtilsKt.copyAnnotations(irSimpleFunctionSymbol.getOwner()));
                                IrSimpleFunctionSymbol irSimpleFunctionSymbol2 = (IrSimpleFunctionSymbol) (irFunctionGenerateDefaultsFunction != null ? irFunctionGenerateDefaultsFunction.getSymbol() : null);
                                if (irSimpleFunctionSymbol2 != null) {
                                    arrayList.add(irSimpleFunctionSymbol2);
                                }
                                overriddenSymbols2 = list;
                            }
                            irSimpleFunction2.setOverriddenSymbols(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus((List) overriddenSymbols2, (List) arrayList));
                        }
                        return irFunctionGenerateDefaultsFunctionImpl;
                    }
                }
            }
        }
        List<IrValueParameter> valueParameters = declaration.getValueParameters();
        if (!(valueParameters instanceof Collection) || !valueParameters.isEmpty()) {
            Iterator<T> it2 = valueParameters.iterator();
            while (it2.hasNext()) {
                if (((IrValueParameter) it2.next()).getDefaultValue() != null) {
                    IrFunction irFunctionGenerateDefaultsFunctionImpl2 = generateDefaultsFunctionImpl(declaration, IrDeclarationOrigin.FUNCTION_FOR_DEFAULT_PARAMETER.INSTANCE, visibility, copiedAnnotations, false, useConstructorMarker);
                    this.context.getMapping().getDefaultArgumentsDispatchFunction().set(irFunction, irFunctionGenerateDefaultsFunctionImpl2);
                    this.context.getMapping().getDefaultArgumentsOriginalFunction().set(irFunctionGenerateDefaultsFunctionImpl2, declaration);
                    return irFunctionGenerateDefaultsFunctionImpl2;
                }
            }
        }
        return null;
    }

    private final IrFunction generateDefaultsFunctionImpl(IrFunction declaration, IrDeclarationOrigin newOrigin, DescriptorVisibility newVisibility, List<? extends IrConstructorCall> copiedAnnotations, boolean isFakeOverride, boolean useConstructorMarker) {
        IrSimpleFunction irSimpleFunctionBuildFunction;
        if (declaration instanceof IrConstructor) {
            IrFactory factory = declaration.getFactory();
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            irFunctionBuilder.updateFrom(declaration);
            irFunctionBuilder.setOrigin(newOrigin);
            irFunctionBuilder.setExternal(false);
            irFunctionBuilder.setPrimary(false);
            irFunctionBuilder.setExpect(false);
            irFunctionBuilder.setVisibility(newVisibility);
            irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
        } else if (declaration instanceof IrSimpleFunction) {
            IrFactory factory2 = declaration.getFactory();
            IrFunctionBuilder irFunctionBuilder2 = new IrFunctionBuilder();
            irFunctionBuilder2.updateFrom(declaration);
            irFunctionBuilder2.setName(generateDefaultArgumentsFunctionName(declaration));
            irFunctionBuilder2.setOrigin(newOrigin);
            irFunctionBuilder2.setFakeOverride(isFakeOverride);
            irFunctionBuilder2.setModality(Modality.FINAL);
            irFunctionBuilder2.setExternal(false);
            irFunctionBuilder2.setTailrec(false);
            irFunctionBuilder2.setVisibility(newVisibility);
            irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(factory2, irFunctionBuilder2);
        } else {
            throw new IllegalStateException("Unknown function type");
        }
        irSimpleFunctionBuildFunction.setParent(declaration.getParent());
        generateDefaultArgumentStubFrom(irSimpleFunctionBuildFunction, declaration, useConstructorMarker);
        irSimpleFunctionBuildFunction.setAnnotations(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus((List) irSimpleFunctionBuildFunction.getAnnotations(), (List) copiedAnnotations));
        return irSimpleFunctionBuildFunction;
    }
}
