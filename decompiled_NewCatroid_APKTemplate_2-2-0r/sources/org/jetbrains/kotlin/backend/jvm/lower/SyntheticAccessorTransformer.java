package org.jetbrains.kotlin.backend.jvm.lower;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.jvm.CachedSyntheticDeclarations;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.ir.IrInlineReferenceLocatorKt;
import org.jetbrains.kotlin.backend.jvm.ir.IrInlineScopeResolver;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrInlinedFunctionBlock;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDelegatingConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.ir.util.IrInlineUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.load.java.JavaDescriptorVisibilities;

/* JADX INFO: compiled from: SyntheticAccessorLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\u0010\u0010\u0016\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00180\u0017H\u0002J\u001a\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0002J\u0018\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020#H\u0002J\u0018\u0010$\u001a\u00020\u001c2\u0006\u0010!\u001a\u00020%2\u0006\u0010\"\u001a\u00020&H\u0002J\u0018\u0010'\u001a\u00020\u001c2\u0006\u0010!\u001a\u00020(2\u0006\u0010\"\u001a\u00020&H\u0002J\u0010\u0010)\u001a\u00020\u001a2\u0006\u0010*\u001a\u00020+H\u0016J\u0010\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/H\u0016J\u0010\u00100\u001a\u00020\u001a2\u0006\u0010*\u001a\u00020 H\u0016J\u0010\u00101\u001a\u00020\u001a2\u0006\u0010*\u001a\u000202H\u0016J\u0010\u00103\u001a\u00020\u001a2\u0006\u0010*\u001a\u00020%H\u0016J\u0010\u00104\u001a\u00020\u001a2\u0006\u0010*\u001a\u00020(H\u0016J\"\u00105\u001a\u0002H6\"\u0004\b\u0000\u001062\f\u00107\u001a\b\u0012\u0004\u0012\u0002H608H\u0082\b¢\u0006\u0002\u00109J\u001e\u0010:\u001a\u00020\u0015*\u00020;2\u0006\u0010<\u001a\u00020\u00152\b\u0010=\u001a\u0004\u0018\u00010\u001eH\u0002J\u0016\u0010>\u001a\u00020\u0015*\u00020#2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0002J\u001b\u0010?\u001a\u0002H6\"\b\b\u0000\u00106*\u00020#*\u0002H6H\u0002¢\u0006\u0002\u0010@R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006A"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/SyntheticAccessorTransformer;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "pendingAccessorsToAdd", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;Lorg/jetbrains/kotlin/ir/declarations/IrFile;Ljava/util/Set;)V", "accessorGenerator", "Lorg/jetbrains/kotlin/backend/jvm/CachedSyntheticDeclarations;", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "inlineScopeResolver", "Lorg/jetbrains/kotlin/backend/jvm/ir/IrInlineScopeResolver;", "getIrFile", "()Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "getPendingAccessorsToAdd", "()Ljava/util/Set;", "processingIrInlinedFun", "", "createAccessorMarkerArgument", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstImpl;", "", "handleLambdaMetafactoryIntrinsic", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "thisSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "modifyFunctionAccessExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "oldExpression", "accessorSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "modifyGetterExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "modifySetterExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "visitBlock", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "visitConstructor", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "visitFunctionAccess", "visitFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "visitGetField", "visitSetField", "withinIrInlinedFun", ExifInterface.GPS_DIRECTION_TRUE, "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isAccessible", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "withSuper", "thisObjReference", "isAccessibleFromSyntheticProxy", "save", "(Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;)Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class SyntheticAccessorTransformer extends IrElementTransformerVoidWithContext {
    private final CachedSyntheticDeclarations accessorGenerator;
    private final JvmBackendContext context;
    private final IrInlineScopeResolver inlineScopeResolver;
    private final IrFile irFile;
    private final Set<IrFunction> pendingAccessorsToAdd;
    private boolean processingIrInlinedFun;

    public final JvmBackendContext getContext() {
        return this.context;
    }

    public final IrFile getIrFile() {
        return this.irFile;
    }

    public final Set<IrFunction> getPendingAccessorsToAdd() {
        return this.pendingAccessorsToAdd;
    }

    public SyntheticAccessorTransformer(JvmBackendContext context, IrFile irFile, Set<IrFunction> pendingAccessorsToAdd) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        Intrinsics.checkNotNullParameter(pendingAccessorsToAdd, "pendingAccessorsToAdd");
        this.context = context;
        this.irFile = irFile;
        this.pendingAccessorsToAdd = pendingAccessorsToAdd;
        this.accessorGenerator = context.getCachedDeclarations().getSyntheticAccessorGenerator();
        this.inlineScopeResolver = IrInlineReferenceLocatorKt.findInlineCallSites(irFile, context);
    }

    private final <T extends IrFunctionSymbol> T save(T t) {
        if (!Intrinsics.areEqual(IrUtilsKt.getFileOrNull(t.getOwner()), this.irFile)) {
            boolean z = this.processingIrInlinedFun;
        }
        if (Intrinsics.areEqual(IrUtilsKt.getFileOrNull(t.getOwner()), this.irFile)) {
            this.pendingAccessorsToAdd.add(t.getOwner());
        }
        return t;
    }

    private final boolean isAccessible(IrSymbol irSymbol, boolean z, IrClassSymbol irClassSymbol) {
        return SyntheticAccessorLowering.INSTANCE.isAccessible(irSymbol, this.context, getCurrentScope(), this.inlineScopeResolver, z, irClassSymbol, (32 & 32) != 0 ? false : false);
    }

    /* JADX WARN: Code duplicated, block: B:39:0x0094 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:40:0x0096  */
    /* JADX WARN: Code duplicated, block: B:42:0x00a0  */
    /* JADX WARN: Code duplicated, block: B:43:0x00ad  */
    /* JADX WARN: Code duplicated, block: B:45:0x00b7  */
    /* JADX WARN: Code duplicated, block: B:48:0x00d0  */
    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitFunctionAccess(IrFunctionAccessExpression expression) {
        IrConstructorSymbol irConstructorSymbolSave;
        IrConstructor irConstructor;
        IrExpression dispatchReceiver;
        IrType type;
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (IrUtilsKt.usesDefaultArguments(expression)) {
            return super.visitFunctionAccess(expression);
        }
        IrFunction owner = expression.getSymbol().getOwner();
        boolean z = expression instanceof IrCall;
        IrCall irCall = z ? (IrCall) expression : null;
        boolean z2 = (irCall != null ? irCall.getSuperQualifierSymbol() : null) != null;
        IrCall irCall2 = z ? (IrCall) expression : null;
        IrClassifierSymbol classifierOrNull = (irCall2 == null || (dispatchReceiver = irCall2.getDispatchReceiver()) == null || (type = dispatchReceiver.getType()) == null) ? null : IrTypesKt.getClassifierOrNull(type);
        IrClassSymbol irClassSymbol = classifierOrNull instanceof IrClassSymbol ? (IrClassSymbol) classifierOrNull : null;
        if (z && Intrinsics.areEqual(owner.getSymbol(), this.context.getIr().getSymbols().getIndyLambdaMetafactoryIntrinsic())) {
            return super.visitExpression(handleLambdaMetafactoryIntrinsic((IrCall) expression, irClassSymbol));
        }
        boolean z3 = owner instanceof IrConstructor;
        if (z3) {
            IrConstructor irConstructor2 = (IrConstructor) owner;
            if (this.accessorGenerator.isOrShouldBeHiddenAsSealedClassConstructor(irConstructor2)) {
                irConstructorSymbolSave = this.accessorGenerator.getSyntheticConstructorOfSealedClass(irConstructor2).getSymbol();
            } else if (z3) {
                irConstructor = (IrConstructor) owner;
                if (this.accessorGenerator.isOrShouldBeHiddenSinceHasMangledParams(irConstructor)) {
                    irConstructorSymbolSave = this.accessorGenerator.getSyntheticConstructorWithMangledParams(irConstructor).getSymbol();
                } else if (!isAccessible(expression.getSymbol(), z2, irClassSymbol)) {
                    irConstructorSymbolSave = save(this.accessorGenerator.getSyntheticFunctionAccessor(expression, getAllScopes()));
                } else {
                    return super.visitFunctionAccess(expression);
                }
            } else if (!isAccessible(expression.getSymbol(), z2, irClassSymbol)) {
                irConstructorSymbolSave = save(this.accessorGenerator.getSyntheticFunctionAccessor(expression, getAllScopes()));
            } else {
                return super.visitFunctionAccess(expression);
            }
        } else if (z3) {
            irConstructor = (IrConstructor) owner;
            if (this.accessorGenerator.isOrShouldBeHiddenSinceHasMangledParams(irConstructor)) {
                irConstructorSymbolSave = this.accessorGenerator.getSyntheticConstructorWithMangledParams(irConstructor).getSymbol();
            } else if (!isAccessible(expression.getSymbol(), z2, irClassSymbol)) {
                irConstructorSymbolSave = save(this.accessorGenerator.getSyntheticFunctionAccessor(expression, getAllScopes()));
            } else {
                return super.visitFunctionAccess(expression);
            }
        } else if (!isAccessible(expression.getSymbol(), z2, irClassSymbol)) {
            irConstructorSymbolSave = save(this.accessorGenerator.getSyntheticFunctionAccessor(expression, getAllScopes()));
        } else {
            return super.visitFunctionAccess(expression);
        }
        return super.visitExpression(modifyFunctionAccessExpression(expression, irConstructorSymbolSave));
    }

    private final IrExpression handleLambdaMetafactoryIntrinsic(IrCall call, IrClassSymbol thisSymbol) {
        int i;
        IrExpression valueArgument = call.getValueArgument(1);
        IrFunctionReference irFunctionReference = valueArgument instanceof IrFunctionReference ? (IrFunctionReference) valueArgument : null;
        if (irFunctionReference == null) {
            throw new AssertionError("'implMethodReference' is expected to be 'IrFunctionReference': " + DumpIrTreeKt.dump$default(call, null, 1, null));
        }
        IrFunctionSymbol symbol = irFunctionReference.getSymbol();
        if (isAccessibleFromSyntheticProxy(symbol, thisSymbol)) {
            return call;
        }
        IrFunctionSymbol irFunctionSymbolSave = save(this.accessorGenerator.getSyntheticFunctionAccessor(irFunctionReference, getAllScopes()));
        IrFunction owner = irFunctionSymbolSave.getOwner();
        IrFunctionReferenceImpl irFunctionReferenceImpl = new IrFunctionReferenceImpl(irFunctionReference.getStartOffset(), irFunctionReference.getEndOffset(), irFunctionReference.getType(), irFunctionSymbolSave, owner.getTypeParameters().size(), owner.getValueParameters().size(), irFunctionReference.getReflectionTarget(), irFunctionReference.getOrigin());
        int i2 = 0;
        IrExpressionsKt.copyTypeArgumentsFrom$default(irFunctionReferenceImpl, irFunctionReference, 0, 2, null);
        IrFunction owner2 = symbol.getOwner();
        if (owner2.getDispatchReceiverParameter() != null) {
            irFunctionReferenceImpl.putValueArgument(0, irFunctionReference.getDispatchReceiver());
            i = 1;
        } else {
            i = 0;
        }
        if (owner2.getExtensionReceiverParameter() != null) {
            irFunctionReferenceImpl.putValueArgument(i, irFunctionReference.getExtensionReceiver());
            i++;
        }
        int valueArgumentsCount = irFunctionReference.getValueArgumentsCount();
        while (i2 < valueArgumentsCount) {
            irFunctionReferenceImpl.putValueArgument(i, irFunctionReference.getValueArgument(i2));
            i2++;
            i++;
        }
        if (owner instanceof IrConstructor) {
            irFunctionReferenceImpl.putValueArgument(i, createAccessorMarkerArgument());
        }
        call.putValueArgument(1, irFunctionReferenceImpl);
        return call;
    }

    private final boolean isAccessibleFromSyntheticProxy(IrFunctionSymbol irFunctionSymbol, IrClassSymbol irClassSymbol) {
        if (!isAccessible(irFunctionSymbol, false, irClassSymbol)) {
            return false;
        }
        if (!Intrinsics.areEqual(irFunctionSymbol.getOwner().getVisibility(), DescriptorVisibilities.PROTECTED) && !Intrinsics.areEqual(irFunctionSymbol.getOwner().getVisibility(), JavaDescriptorVisibilities.PROTECTED_STATIC_VISIBILITY)) {
            return true;
        }
        IrInlineScopeResolver irInlineScopeResolver = this.inlineScopeResolver;
        ScopeWithIr currentScope = getCurrentScope();
        Intrinsics.checkNotNull(currentScope);
        return Intrinsics.areEqual(irInlineScopeResolver.findContainer(currentScope.getIrElement()), IrUtilsKt.getParentAsClass(irFunctionSymbol.getOwner()));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitGetField(IrGetField expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrExpression receiver = expression.getReceiver();
        IrType type = receiver != null ? receiver.getType() : null;
        IrClassifierSymbol classifierOrNull = type != null ? IrTypesKt.getClassifierOrNull(type) : null;
        if (isAccessible(expression.getSymbol(), false, classifierOrNull instanceof IrClassSymbol ? (IrClassSymbol) classifierOrNull : null)) {
            return super.visitExpression(expression);
        }
        return super.visitExpression(modifyGetterExpression(expression, (IrSimpleFunctionSymbol) save(this.accessorGenerator.getSyntheticGetter(expression, getAllScopes()))));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitSetField(IrSetField expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrPropertySymbol correspondingPropertySymbol = expression.getSymbol().getOwner().getCorrespondingPropertySymbol();
        IrProperty owner = correspondingPropertySymbol != null ? correspondingPropertySymbol.getOwner() : null;
        if (owner != null && !owner.getIsVar()) {
            return super.visitExpression(expression);
        }
        IrExpression receiver = expression.getReceiver();
        IrType type = receiver != null ? receiver.getType() : null;
        IrClassifierSymbol classifierOrNull = type != null ? IrTypesKt.getClassifierOrNull(type) : null;
        if (isAccessible(expression.getSymbol(), false, classifierOrNull instanceof IrClassSymbol ? (IrClassSymbol) classifierOrNull : null)) {
            return super.visitExpression(expression);
        }
        return super.visitExpression(modifySetterExpression(expression, (IrSimpleFunctionSymbol) save(this.accessorGenerator.getSyntheticSetter(expression, getAllScopes()))));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitConstructor(IrConstructor declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (this.accessorGenerator.isOrShouldBeHiddenSinceHasMangledParams(declaration)) {
            save(this.accessorGenerator.getSyntheticConstructorWithMangledParams(declaration).getSymbol());
            DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
            Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
            declaration.setVisibility(PRIVATE);
        } else if (this.accessorGenerator.isOrShouldBeHiddenAsSealedClassConstructor(declaration)) {
            save(this.accessorGenerator.getSyntheticConstructorOfSealedClass(declaration).getSymbol());
            DescriptorVisibility PRIVATE2 = DescriptorVisibilities.PRIVATE;
            Intrinsics.checkNotNullExpressionValue(PRIVATE2, "PRIVATE");
            declaration.setVisibility(PRIVATE2);
        }
        return super.visitConstructor(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitFunctionReference(IrFunctionReference expression) {
        IrConstructor syntheticConstructorOfSealedClass;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrFunction owner = expression.getSymbol().getOwner();
        if (!IrUtilsKt.isLambda(expression.getOrigin()) && (owner instanceof IrConstructor)) {
            IrConstructor irConstructor = (IrConstructor) owner;
            if (this.accessorGenerator.isOrShouldBeHiddenSinceHasMangledParams(irConstructor)) {
                syntheticConstructorOfSealedClass = this.accessorGenerator.getSyntheticConstructorWithMangledParams(irConstructor);
            } else {
                if (!this.accessorGenerator.isOrShouldBeHiddenAsSealedClassConstructor(irConstructor)) {
                    return super.visitFunctionReference(expression);
                }
                syntheticConstructorOfSealedClass = this.accessorGenerator.getSyntheticConstructorOfSealedClass(irConstructor);
            }
            transformChildrenVoid(expression);
            return new IrFunctionReferenceImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), syntheticConstructorOfSealedClass.getSymbol(), syntheticConstructorOfSealedClass.getTypeParameters().size(), syntheticConstructorOfSealedClass.getValueParameters().size(), syntheticConstructorOfSealedClass.getSymbol(), expression.getOrigin());
        }
        return super.visitFunctionReference(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitBlock(IrBlock expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (expression instanceof IrInlinedFunctionBlock) {
            IrInlinedFunctionBlock irInlinedFunctionBlock = (IrInlinedFunctionBlock) expression;
            if (IrInlineUtilsKt.isFunctionInlining(irInlinedFunctionBlock)) {
                IrDeclaration inlineDeclaration = IrInlineUtilsKt.getInlineDeclaration(irInlinedFunctionBlock);
                IrClass parentClassOrNull = IrUtilsKt.getParentClassOrNull(inlineDeclaration);
                if (parentClassOrNull == null) {
                    return super.visitBlock(expression);
                }
                boolean z = this.processingIrInlinedFun;
                try {
                    this.processingIrInlinedFun = true;
                    SyntheticAccessorTransformer syntheticAccessorTransformer = this;
                    syntheticAccessorTransformer.unsafeEnterScope(parentClassOrNull);
                    SyntheticAccessorTransformer syntheticAccessorTransformer2 = this;
                    syntheticAccessorTransformer2.unsafeEnterScope(inlineDeclaration);
                    IrExpression irExpressionVisitBlock = super.visitBlock(expression);
                    syntheticAccessorTransformer2.unsafeLeaveScope();
                    syntheticAccessorTransformer.unsafeLeaveScope();
                    return irExpressionVisitBlock;
                } finally {
                    this.processingIrInlinedFun = z;
                }
            }
        }
        return super.visitBlock(expression);
    }

    private final IrFunctionAccessExpression modifyFunctionAccessExpression(IrFunctionAccessExpression oldExpression, IrFunctionSymbol accessorSymbol) {
        IrConstructorCallImpl irConstructorCallImplFromSymbolOwner$default;
        if (oldExpression instanceof IrCall) {
            IrCallImpl.Companion companion = IrCallImpl.INSTANCE;
            int startOffset = oldExpression.getStartOffset();
            int endOffset = oldExpression.getEndOffset();
            IrType type = oldExpression.getType();
            Intrinsics.checkNotNull(accessorSymbol, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol");
            IrSimpleFunctionSymbol irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) accessorSymbol;
            irConstructorCallImplFromSymbolOwner$default = companion.fromSymbolOwner(startOffset, endOffset, type, irSimpleFunctionSymbol, (240 & 16) != 0 ? irSimpleFunctionSymbol.getOwner().getTypeParameters().size() : oldExpression.getTypeArgumentsCount(), (240 & 32) != 0 ? irSimpleFunctionSymbol.getOwner().getValueParameters().size() : 0, (240 & 64) != 0 ? null : oldExpression.getOrigin(), (240 & 128) != 0 ? null : null);
        } else if (oldExpression instanceof IrDelegatingConstructorCall) {
            IrDelegatingConstructorCallImpl.Companion companion2 = IrDelegatingConstructorCallImpl.INSTANCE;
            int startOffset2 = oldExpression.getStartOffset();
            int endOffset2 = oldExpression.getEndOffset();
            IrType unitType = this.context.getIrBuiltIns().getUnitType();
            Intrinsics.checkNotNull(accessorSymbol, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol");
            irConstructorCallImplFromSymbolOwner$default = IrDelegatingConstructorCallImpl.Companion.fromSymbolOwner$default(companion2, startOffset2, endOffset2, unitType, (IrConstructorSymbol) accessorSymbol, oldExpression.getTypeArgumentsCount(), 0, 32, null);
        } else if (oldExpression instanceof IrConstructorCall) {
            IrConstructorCallImpl.Companion companion3 = IrConstructorCallImpl.INSTANCE;
            int startOffset3 = oldExpression.getStartOffset();
            int endOffset3 = oldExpression.getEndOffset();
            IrType type2 = oldExpression.getType();
            Intrinsics.checkNotNull(accessorSymbol, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol");
            irConstructorCallImplFromSymbolOwner$default = IrConstructorCallImpl.Companion.fromSymbolOwner$default(companion3, startOffset3, endOffset3, type2, (IrConstructorSymbol) accessorSymbol, null, 16, null);
        } else {
            throw new IllegalStateException(("Unexpected IrFunctionAccessExpression: " + oldExpression).toString());
        }
        int i = 0;
        IrExpressionsKt.copyTypeArgumentsFrom$default(irConstructorCallImplFromSymbolOwner$default, oldExpression, 0, 2, null);
        List<IrExpression> listReceiverAndArgs = JvmIrUtilsKt.receiverAndArgs(oldExpression);
        for (Object obj : listReceiverAndArgs) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            irConstructorCallImplFromSymbolOwner$default.putValueArgument(i, (IrExpression) obj);
            i = i2;
        }
        if (accessorSymbol instanceof IrConstructorSymbol) {
            irConstructorCallImplFromSymbolOwner$default.putValueArgument(listReceiverAndArgs.size(), createAccessorMarkerArgument());
        }
        return irConstructorCallImplFromSymbolOwner$default;
    }

    private final IrConstImpl createAccessorMarkerArgument() {
        return IrConstImpl.INSTANCE.constNull(-1, -1, IrTypesKt.makeNullable(IrTypesKt.getDefaultType(this.context.getIr().getSymbols().getDefaultConstructorMarker())));
    }

    private final IrCall modifyGetterExpression(IrGetField oldExpression, IrSimpleFunctionSymbol accessorSymbol) {
        IrCallImpl irCallImpl = new IrCallImpl(oldExpression.getStartOffset(), oldExpression.getEndOffset(), oldExpression.getType(), accessorSymbol, 0, accessorSymbol.getOwner().getValueParameters().size(), oldExpression.getOrigin(), null, 128, null);
        if (oldExpression.getReceiver() != null) {
            irCallImpl.putValueArgument(0, oldExpression.getReceiver());
        }
        return irCallImpl;
    }

    private final IrCall modifySetterExpression(IrSetField oldExpression, IrSimpleFunctionSymbol accessorSymbol) {
        IrCallImpl irCallImpl = new IrCallImpl(oldExpression.getStartOffset(), oldExpression.getEndOffset(), oldExpression.getType(), accessorSymbol, 0, accessorSymbol.getOwner().getValueParameters().size(), oldExpression.getOrigin(), null, 128, null);
        if (oldExpression.getReceiver() != null) {
            irCallImpl.putValueArgument(0, oldExpression.getReceiver());
        }
        irCallImpl.putValueArgument(irCallImpl.getValueArgumentsCount() - 1, oldExpression.getValue());
        return irCallImpl;
    }
}
