package org.jetbrains.kotlin.backend.common.ir;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.ReferenceSymbolTable;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: Ir.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u001a\b&\u0018\u0000 :2\u00020\u0001:\u0001:B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\"\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0018\u00010\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0012\u0010\r\u001a\u00020\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0012\u0010\u0011\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0012\u0010\u0014\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0013R\u0012\u0010\u0016\u001a\u00020\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0010R\u0012\u0010\u0018\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0013R\u0012\u0010\u001a\u001a\u00020\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0010R\u0012\u0010\u001c\u001a\u00020\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0010R\u0012\u0010\u001e\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u0013R\u0016\u0010 \u001a\u0004\u0018\u00010!X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0012\u0010$\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b%\u0010\u0013R\u0016\u0010&\u001a\u0004\u0018\u00010!X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b'\u0010#R\u0012\u0010(\u001a\u00020\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b)\u0010\u0010R\u0012\u0010*\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b+\u0010\u0013R\u0014\u0010,\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b-\u0010\u0013R\u0014\u0010.\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b/\u0010\u0013R\u0012\u00100\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b1\u0010\u0013R\u0012\u00102\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b3\u0010\u0013R\u0012\u00104\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b5\u0010\u0013R\u0012\u00106\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b7\u0010\u0013R\u0016\u00108\u001a\u0004\u0018\u00010\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b9\u0010\u0013¨\u0006;"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/ir/Symbols;", "Lorg/jetbrains/kotlin/backend/common/ir/BuiltinSymbolsBase;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "(Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;)V", "arraysContentEquals", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getArraysContentEquals", "()Ljava/util/Map;", "continuationClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getContinuationClass", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "coroutineContextGetter", "getCoroutineContextGetter", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "coroutineGetContext", "getCoroutineGetContext", "coroutineImpl", "getCoroutineImpl", "coroutineSuspendedGetter", "getCoroutineSuspendedGetter", "defaultConstructorMarker", "getDefaultConstructorMarker", "functionAdapter", "getFunctionAdapter", "getContinuation", "getGetContinuation", "getWithoutBoundCheckName", "Lorg/jetbrains/kotlin/name/Name;", "getGetWithoutBoundCheckName", "()Lorg/jetbrains/kotlin/name/Name;", "returnIfSuspended", "getReturnIfSuspended", "setWithoutBoundCheckName", "getSetWithoutBoundCheckName", "stringBuilder", "getStringBuilder", "suspendCoroutineUninterceptedOrReturn", "getSuspendCoroutineUninterceptedOrReturn", Namer.THROW_ILLEGAL_ARGUMENT_EXCEPTION_FUN_NAME, "getThrowIAE", "throwISE", "getThrowISE", "throwKotlinNothingValueException", "getThrowKotlinNothingValueException", "throwNullPointerException", "getThrowNullPointerException", "throwTypeCastException", "getThrowTypeCastException", "throwUninitializedPropertyAccessException", "getThrowUninitializedPropertyAccessException", "unsafeCoerceIntrinsic", "getUnsafeCoerceIntrinsic", "Companion", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class Symbols extends BuiltinSymbolsBase {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Map<IrType, IrSimpleFunctionSymbol> arraysContentEquals;
    private final Name getWithoutBoundCheckName;
    private final Name setWithoutBoundCheckName;
    private final IrSimpleFunctionSymbol unsafeCoerceIntrinsic;

    public abstract IrClassSymbol getContinuationClass();

    public abstract IrSimpleFunctionSymbol getCoroutineContextGetter();

    public abstract IrSimpleFunctionSymbol getCoroutineGetContext();

    public abstract IrClassSymbol getCoroutineImpl();

    public abstract IrSimpleFunctionSymbol getCoroutineSuspendedGetter();

    public abstract IrClassSymbol getDefaultConstructorMarker();

    public abstract IrClassSymbol getFunctionAdapter();

    public abstract IrSimpleFunctionSymbol getGetContinuation();

    public abstract IrSimpleFunctionSymbol getReturnIfSuspended();

    /* JADX INFO: renamed from: getStringBuilder */
    public abstract IrClassSymbol mo7086getStringBuilder();

    public abstract IrSimpleFunctionSymbol getSuspendCoroutineUninterceptedOrReturn();

    public abstract IrSimpleFunctionSymbol getThrowKotlinNothingValueException();

    public abstract IrSimpleFunctionSymbol getThrowNullPointerException();

    public abstract IrSimpleFunctionSymbol getThrowTypeCastException();

    public abstract IrSimpleFunctionSymbol getThrowUninitializedPropertyAccessException();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Symbols(IrBuiltIns irBuiltIns, ReferenceSymbolTable symbolTable) {
        super(irBuiltIns, symbolTable);
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
    }

    public IrSimpleFunctionSymbol getThrowISE() {
        throw new IllegalStateException("throwISE is not implemented".toString());
    }

    public IrSimpleFunctionSymbol getThrowIAE() {
        throw new IllegalStateException("throwIAE is not implemented".toString());
    }

    public IrSimpleFunctionSymbol getUnsafeCoerceIntrinsic() {
        return this.unsafeCoerceIntrinsic;
    }

    public Name getGetWithoutBoundCheckName() {
        return this.getWithoutBoundCheckName;
    }

    public Name getSetWithoutBoundCheckName() {
        return this.setWithoutBoundCheckName;
    }

    public Map<IrType, IrSimpleFunctionSymbol> getArraysContentEquals() {
        return this.arraysContentEquals;
    }

    /* JADX INFO: compiled from: Ir.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/ir/Symbols$Companion;", "", "()V", "isLateinitIsInitializedPropertyGetter", "", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "isTypeOfIntrinsic", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean isLateinitIsInitializedPropertyGetter(IrFunctionSymbol symbol) {
            FqName fqNameWhenAvailable;
            IrType type;
            IrClassSymbol classOrNull;
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            if (symbol instanceof IrSimpleFunctionSymbol) {
                IrSimpleFunctionSymbol irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) symbol;
                IrSimpleFunction owner = irSimpleFunctionSymbol.getOwner();
                if (Intrinsics.areEqual(owner.getName().asString(), "<get-isInitialized>")) {
                    IrSimpleFunction irSimpleFunction = owner;
                    if (org.jetbrains.kotlin.ir.util.IrUtilsKt.isTopLevel(irSimpleFunction) && Intrinsics.areEqual(org.jetbrains.kotlin.ir.util.IrUtilsKt.getPackageFragment((IrDeclaration) irSimpleFunction).getPackageFqName().asString(), IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME) && owner.getValueParameters().isEmpty()) {
                        IrValueParameter extensionReceiverParameter = irSimpleFunctionSymbol.getOwner().getExtensionReceiverParameter();
                        FqNameUnsafe unsafe = null;
                        IrClass owner2 = (extensionReceiverParameter == null || (type = extensionReceiverParameter.getType()) == null || (classOrNull = IrTypesKt.getClassOrNull(type)) == null) ? null : classOrNull.getOwner();
                        if (owner2 != null && (fqNameWhenAvailable = org.jetbrains.kotlin.ir.util.IrUtilsKt.getFqNameWhenAvailable(owner2)) != null) {
                            unsafe = fqNameWhenAvailable.toUnsafe();
                        }
                        if (Intrinsics.areEqual(unsafe, StandardNames.FqNames.kProperty0)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        public final boolean isTypeOfIntrinsic(IrFunctionSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            if (symbol instanceof IrSimpleFunctionSymbol) {
                IrSimpleFunction owner = ((IrSimpleFunctionSymbol) symbol).getOwner();
                if (Intrinsics.areEqual(owner.getName().asString(), "typeOf") && owner.getValueParameters().isEmpty()) {
                    IrDeclarationParent parent = owner.getParent();
                    IrPackageFragment irPackageFragment = parent instanceof IrPackageFragment ? (IrPackageFragment) parent : null;
                    if (Intrinsics.areEqual(irPackageFragment != null ? irPackageFragment.getPackageFqName() : null, StandardNames.KOTLIN_REFLECT_FQ_NAME)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
