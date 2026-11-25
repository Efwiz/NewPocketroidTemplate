package org.jetbrains.kotlin.backend.jvm.ir;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredStatementOrigin;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.resolve.inline.InlineOnlyKt;

/* JADX INFO: compiled from: JvmIrInlineUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000L\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u000f\u001a\u0004\u0018\u00010\u0010*\u00020\u0010\u001a\u000e\u0010\u0011\u001a\u0004\u0018\u00010\u0002*\u00020\u0010H\u0002\u001a\n\u0010\u0012\u001a\u00020\n*\u00020\u0013\u001a\u0012\u0010\u0014\u001a\u00020\n*\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017\u001a\n\u0010\u0018\u001a\u00020\n*\u00020\u0002\u001a\n\u0010\u0019\u001a\u00020\n*\u00020\u001a\u001a\n\u0010\u001b\u001a\u00020\n*\u00020\u0015\u001a\n\u0010\u001c\u001a\u00020\n*\u00020\u0015\u001a\f\u0010\u001d\u001a\u0004\u0018\u00010\u001e*\u00020\u001f\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0017\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b\"\u0015\u0010\t\u001a\u00020\n*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\t\u0010\u000b\"\u0018\u0010\f\u001a\u00020\u0002*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006 "}, d2 = {"fileParentBeforeInline", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getFileParentBeforeInline", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "inlineScopeVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "getInlineScopeVisibility", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "isInPublicInlineScope", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Z", "original", "getOriginal", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getAttributeOwnerBeforeInline", "Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "getDeclarationBeforeInline", "isEffectivelyInlineOnly", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithVisibility;", "isInlineFunctionCall", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "isInlineOnly", "isInlineParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "isPrivateInlineSuspend", "isReifiable", "unwrapInlineLambda", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "Lorg/jetbrains/kotlin/ir/IrStatement;", "backend.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmIrInlineUtilsKt {
    public static final boolean isInlineParameter(IrValueParameter irValueParameter) {
        IrExpressionBody defaultValue;
        IrExpression expression;
        IrType type;
        Intrinsics.checkNotNullParameter(irValueParameter, "<this>");
        return irValueParameter.getIndex() >= 0 && !irValueParameter.getIsNoinline() && (IrTypeUtilsKt.isFunction(irValueParameter.getType()) || IrTypeUtilsKt.isSuspendFunction(irValueParameter.getType())) && !(org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.isNullable(irValueParameter.getType()) && ((defaultValue = irValueParameter.getDefaultValue()) == null || (expression = defaultValue.getExpression()) == null || (type = expression.getType()) == null || org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.isNullable(type)));
    }

    public static final DescriptorVisibility getInlineScopeVisibility(IrDeclaration irDeclaration) {
        DescriptorVisibility visibility;
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        IrDeclaration original = getOriginal(irDeclaration);
        DescriptorVisibility visibility2 = null;
        while (original != null) {
            if (original instanceof IrFunction) {
                IrFunction irFunction = (IrFunction) original;
                if (irFunction.getIsInline()) {
                    if (!DescriptorVisibilities.isPrivate(irFunction.getVisibility())) {
                        IrClass parentClassOrNull = IrUtilsKt.getParentClassOrNull(original);
                        if (parentClassOrNull != null && (visibility = parentClassOrNull.getVisibility()) != null && DescriptorVisibilities.isPrivate(visibility)) {
                            visibility2 = DescriptorVisibilities.PRIVATE;
                        } else {
                            return irFunction.getVisibility();
                        }
                    } else {
                        visibility2 = irFunction.getVisibility();
                    }
                }
            }
            IrDeclarationParent parent = original.getParent();
            IrDeclaration irDeclaration2 = parent instanceof IrDeclaration ? (IrDeclaration) parent : null;
            original = irDeclaration2 != null ? getOriginal(irDeclaration2) : null;
        }
        return visibility2;
    }

    public static final boolean isInPublicInlineScope(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        DescriptorVisibility inlineScopeVisibility = getInlineScopeVisibility(irDeclaration);
        return (inlineScopeVisibility == null || DescriptorVisibilities.isPrivate(inlineScopeVisibility)) ? false : true;
    }

    private static final IrDeclaration getOriginal(IrDeclaration irDeclaration) {
        IrAttributeContainer irAttributeContainer = irDeclaration instanceof IrAttributeContainer ? (IrAttributeContainer) irDeclaration : null;
        IrAttributeContainer attributeOwnerId = irAttributeContainer != null ? irAttributeContainer.getAttributeOwnerId() : null;
        IrDeclaration irDeclaration2 = attributeOwnerId instanceof IrDeclaration ? (IrDeclaration) attributeOwnerId : null;
        return irDeclaration2 == null ? irDeclaration : irDeclaration2;
    }

    public static final IrFunctionReference unwrapInlineLambda(IrStatement irStatement) {
        Intrinsics.checkNotNullParameter(irStatement, "<this>");
        if (irStatement instanceof IrBlock) {
            IrStatement irStatement2 = (IrStatement) CollectionsKt.lastOrNull((List) ((IrBlock) irStatement).getStatements());
            if (irStatement2 != null) {
                return unwrapInlineLambda(irStatement2);
            }
            return null;
        }
        if (!(irStatement instanceof IrFunctionReference)) {
            return null;
        }
        if (!Intrinsics.areEqual(((IrFunctionReference) irStatement).getOrigin(), JvmLoweredStatementOrigin.INLINE_LAMBDA.INSTANCE)) {
            irStatement = null;
        }
        return (IrFunctionReference) irStatement;
    }

    public static final boolean isInlineFunctionCall(IrFunction irFunction, JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        if (!context.getConfig().getIsInlineDisabled()) {
            if (!irFunction.getIsInline()) {
            }
            return true;
        }
        List<IrTypeParameter> typeParameters = irFunction.getTypeParameters();
        if (!(typeParameters instanceof Collection) || !typeParameters.isEmpty()) {
            Iterator<T> it = typeParameters.iterator();
            while (it.hasNext()) {
                if (((IrTypeParameter) it.next()).getIsReified()) {
                    if (!irFunction.getIsInline() || AdditionalIrUtilsKt.isInlineArrayConstructor(irFunction, context.getIrBuiltIns())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static final boolean isInlineOnly(IrDeclaration irDeclaration) {
        IrPropertySymbol correspondingPropertySymbol;
        IrProperty owner;
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        if (irDeclaration instanceof IrFunction) {
            if (((IrFunction) irDeclaration).getIsInline() && IrUtilsKt.hasAnnotation(irDeclaration, InlineOnlyKt.getINLINE_ONLY_ANNOTATION_FQ_NAME())) {
                return true;
            }
            if ((irDeclaration instanceof IrSimpleFunction) && (correspondingPropertySymbol = ((IrSimpleFunction) irDeclaration).getCorrespondingPropertySymbol()) != null && (owner = correspondingPropertySymbol.getOwner()) != null && IrUtilsKt.hasAnnotation(owner, InlineOnlyKt.getINLINE_ONLY_ANNOTATION_FQ_NAME())) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isEffectivelyInlineOnly(IrDeclarationWithVisibility irDeclarationWithVisibility) {
        Intrinsics.checkNotNullParameter(irDeclarationWithVisibility, "<this>");
        if (irDeclarationWithVisibility instanceof IrFunction) {
            IrFunction irFunction = (IrFunction) irDeclarationWithVisibility;
            if (isReifiable(irFunction) || isInlineOnly(irDeclarationWithVisibility) || isPrivateInlineSuspend(irFunction)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isPrivateInlineSuspend(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return AdditionalIrUtilsKt.isSuspend(irFunction) && irFunction.getIsInline() && Intrinsics.areEqual(irFunction.getVisibility(), DescriptorVisibilities.PRIVATE);
    }

    public static final boolean isReifiable(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        List<IrTypeParameter> typeParameters = irFunction.getTypeParameters();
        if ((typeParameters instanceof Collection) && typeParameters.isEmpty()) {
            return false;
        }
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            if (((IrTypeParameter) it.next()).getIsReified()) {
                return true;
            }
        }
        return false;
    }

    private static final IrDeclaration getDeclarationBeforeInline(IrAttributeContainer irAttributeContainer) {
        IrAttributeContainer originalBeforeInline = irAttributeContainer.getOriginalBeforeInline();
        if (originalBeforeInline == null) {
            return null;
        }
        return IrUtilsKt.extractRelatedDeclaration(originalBeforeInline);
    }

    public static final IrAttributeContainer getAttributeOwnerBeforeInline(IrAttributeContainer irAttributeContainer) {
        Intrinsics.checkNotNullParameter(irAttributeContainer, "<this>");
        if (irAttributeContainer.getOriginalBeforeInline() == null) {
            return null;
        }
        return (IrAttributeContainer) SequencesKt.last(SequencesKt.generateSequence(irAttributeContainer, new Function1<IrAttributeContainer, IrAttributeContainer>() { // from class: org.jetbrains.kotlin.backend.jvm.ir.JvmIrInlineUtilsKt.getAttributeOwnerBeforeInline.1
            @Override // kotlin.jvm.functions.Function1
            public final IrAttributeContainer invoke(IrAttributeContainer it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getOriginalBeforeInline();
            }
        }));
    }

    public static final IrFile getFileParentBeforeInline(IrDeclaration irDeclaration) {
        IrDeclaration declarationBeforeInline;
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        IrAttributeContainer irAttributeContainer = irDeclaration instanceof IrAttributeContainer ? (IrAttributeContainer) irDeclaration : null;
        if (irAttributeContainer == null || (declarationBeforeInline = getDeclarationBeforeInline(irAttributeContainer)) == null) {
            IrClass parentClassOrNull = IrUtilsKt.getParentClassOrNull(irDeclaration);
            IrDeclaration declarationBeforeInline2 = parentClassOrNull != null ? getDeclarationBeforeInline(parentClassOrNull) : null;
            if (declarationBeforeInline2 != null) {
                irDeclaration = declarationBeforeInline2;
            }
        } else {
            irDeclaration = declarationBeforeInline;
        }
        return JvmIrUtilsKt.getFileParent(irDeclaration);
    }
}
