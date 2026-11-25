package org.jetbrains.kotlin.ir.descriptors;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.functions.FunctionClassDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOriginImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.util.SymbolTable;

/* JADX INFO: compiled from: IrDescriptorBasedFunctionFactory.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b&\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006J;\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062)\u0010\n\u001a%\u0012\u0004\u0012\u00020\f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\b0\r\u0012\u0004\u0012\u00020\b0\u000b¢\u0006\u0002\b\u000fH&J\u0010\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u000e\u0010\u0011\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006J;\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062)\u0010\n\u001a%\u0012\u0004\u0012\u00020\f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\b0\r\u0012\u0004\u0012\u00020\b0\u000b¢\u0006\u0002\b\u000fH&J\u0010\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u000e\u0010\u0013\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006J;\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062)\u0010\n\u001a%\u0012\u0004\u0012\u00020\f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\b0\r\u0012\u0004\u0012\u00020\b0\u000b¢\u0006\u0002\b\u000fH&J\u0010\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u000e\u0010\u0015\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006J;\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062)\u0010\n\u001a%\u0012\u0004\u0012\u00020\f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\b0\r\u0012\u0004\u0012\u00020\b0\u000b¢\u0006\u0002\b\u000fH&¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrAbstractDescriptorBasedFunctionFactory;", "", "()V", "functionClassDescriptor", "Lorg/jetbrains/kotlin/builtins/functions/FunctionClassDescriptor;", "arity", "", "functionN", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "n", "declarator", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "Lkotlin/ExtensionFunctionType;", "kFunctionClassDescriptor", "kFunctionN", "kSuspendFunctionClassDescriptor", "kSuspendFunctionN", "suspendFunctionClassDescriptor", "suspendFunctionN", "Companion", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrAbstractDescriptorBasedFunctionFactory {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final IrDeclarationOriginImpl classOrigin = new IrDeclarationOriginImpl() { // from class: org.jetbrains.kotlin.ir.descriptors.IrAbstractDescriptorBasedFunctionFactory$Companion$classOrigin$1
    };
    private static final IrDeclarationOriginImpl memberOrigin = new IrDeclarationOriginImpl() { // from class: org.jetbrains.kotlin.ir.descriptors.IrAbstractDescriptorBasedFunctionFactory$Companion$memberOrigin$1
    };
    public static final int offset = -2;

    public abstract FunctionClassDescriptor functionClassDescriptor(int arity);

    public abstract IrClass functionN(int arity, Function2<? super SymbolTable, ? super Function1<? super IrClassSymbol, ? extends IrClass>, ? extends IrClass> declarator);

    public abstract FunctionClassDescriptor kFunctionClassDescriptor(int arity);

    public abstract IrClass kFunctionN(int arity, Function2<? super SymbolTable, ? super Function1<? super IrClassSymbol, ? extends IrClass>, ? extends IrClass> declarator);

    public abstract FunctionClassDescriptor kSuspendFunctionClassDescriptor(int arity);

    public abstract IrClass kSuspendFunctionN(int arity, Function2<? super SymbolTable, ? super Function1<? super IrClassSymbol, ? extends IrClass>, ? extends IrClass> declarator);

    public abstract FunctionClassDescriptor suspendFunctionClassDescriptor(int arity);

    public abstract IrClass suspendFunctionN(int arity, Function2<? super SymbolTable, ? super Function1<? super IrClassSymbol, ? extends IrClass>, ? extends IrClass> declarator);

    public final IrClass functionN(final int n) {
        return functionN(n, new Function2<SymbolTable, Function1<? super IrClassSymbol, ? extends IrClass>, IrClass>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrAbstractDescriptorBasedFunctionFactory.functionN.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final IrClass invoke(SymbolTable functionN, final Function1<? super IrClassSymbol, ? extends IrClass> callback) {
                Intrinsics.checkNotNullParameter(functionN, "$this$functionN");
                Intrinsics.checkNotNullParameter(callback, "callback");
                return functionN.getDescriptorExtension().declareClass(IrAbstractDescriptorBasedFunctionFactory.this.functionClassDescriptor(n), new Function1<IrClassSymbol, IrClass>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrAbstractDescriptorBasedFunctionFactory.functionN.1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final IrClass invoke(IrClassSymbol symbol) {
                        Intrinsics.checkNotNullParameter(symbol, "symbol");
                        return callback.invoke(symbol);
                    }
                });
            }
        });
    }

    public final IrClass kFunctionN(final int n) {
        return kFunctionN(n, new Function2<SymbolTable, Function1<? super IrClassSymbol, ? extends IrClass>, IrClass>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrAbstractDescriptorBasedFunctionFactory.kFunctionN.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final IrClass invoke(SymbolTable kFunctionN, final Function1<? super IrClassSymbol, ? extends IrClass> callback) {
                Intrinsics.checkNotNullParameter(kFunctionN, "$this$kFunctionN");
                Intrinsics.checkNotNullParameter(callback, "callback");
                return kFunctionN.getDescriptorExtension().declareClass(IrAbstractDescriptorBasedFunctionFactory.this.kFunctionClassDescriptor(n), new Function1<IrClassSymbol, IrClass>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrAbstractDescriptorBasedFunctionFactory.kFunctionN.1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final IrClass invoke(IrClassSymbol symbol) {
                        Intrinsics.checkNotNullParameter(symbol, "symbol");
                        return callback.invoke(symbol);
                    }
                });
            }
        });
    }

    public final IrClass suspendFunctionN(final int n) {
        return suspendFunctionN(n, new Function2<SymbolTable, Function1<? super IrClassSymbol, ? extends IrClass>, IrClass>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrAbstractDescriptorBasedFunctionFactory.suspendFunctionN.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final IrClass invoke(SymbolTable suspendFunctionN, final Function1<? super IrClassSymbol, ? extends IrClass> callback) {
                Intrinsics.checkNotNullParameter(suspendFunctionN, "$this$suspendFunctionN");
                Intrinsics.checkNotNullParameter(callback, "callback");
                return suspendFunctionN.getDescriptorExtension().declareClass(IrAbstractDescriptorBasedFunctionFactory.this.suspendFunctionClassDescriptor(n), new Function1<IrClassSymbol, IrClass>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrAbstractDescriptorBasedFunctionFactory.suspendFunctionN.1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final IrClass invoke(IrClassSymbol symbol) {
                        Intrinsics.checkNotNullParameter(symbol, "symbol");
                        return callback.invoke(symbol);
                    }
                });
            }
        });
    }

    public final IrClass kSuspendFunctionN(final int n) {
        return kSuspendFunctionN(n, new Function2<SymbolTable, Function1<? super IrClassSymbol, ? extends IrClass>, IrClass>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrAbstractDescriptorBasedFunctionFactory.kSuspendFunctionN.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final IrClass invoke(SymbolTable kSuspendFunctionN, final Function1<? super IrClassSymbol, ? extends IrClass> callback) {
                Intrinsics.checkNotNullParameter(kSuspendFunctionN, "$this$kSuspendFunctionN");
                Intrinsics.checkNotNullParameter(callback, "callback");
                return kSuspendFunctionN.getDescriptorExtension().declareClass(IrAbstractDescriptorBasedFunctionFactory.this.kSuspendFunctionClassDescriptor(n), new Function1<IrClassSymbol, IrClass>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrAbstractDescriptorBasedFunctionFactory.kSuspendFunctionN.1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final IrClass invoke(IrClassSymbol symbol) {
                        Intrinsics.checkNotNullParameter(symbol, "symbol");
                        return callback.invoke(symbol);
                    }
                });
            }
        });
    }

    /* JADX INFO: compiled from: IrDescriptorBasedFunctionFactory.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J%\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\nH\u0000¢\u0006\u0002\b\u0011R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u000e\u0010\t\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrAbstractDescriptorBasedFunctionFactory$Companion;", "", "()V", "classOrigin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "getClassOrigin", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "memberOrigin", "getMemberOrigin", TypedValues.CycleType.S_WAVE_OFFSET, "", "functionClassName", "", "isK", "", "isSuspend", "arity", "functionClassName$ir_psi2ir", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final IrDeclarationOriginImpl getClassOrigin() {
            return IrAbstractDescriptorBasedFunctionFactory.classOrigin;
        }

        public final IrDeclarationOriginImpl getMemberOrigin() {
            return IrAbstractDescriptorBasedFunctionFactory.memberOrigin;
        }

        public final String functionClassName$ir_psi2ir(boolean isK, boolean isSuspend, int arity) {
            return (isK ? "K" : "") + (isSuspend ? "Suspend" : "") + "Function" + arity;
        }
    }
}
