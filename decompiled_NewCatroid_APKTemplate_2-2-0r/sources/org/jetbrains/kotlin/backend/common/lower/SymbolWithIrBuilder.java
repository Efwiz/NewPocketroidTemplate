package org.jetbrains.kotlin.backend.common.lower;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrBuildUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0002\b&\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u0002*\n\b\u0001\u0010\u0003 \u0001*\u00020\u00042\u00020\u0005B\u0005¢\u0006\u0002\u0010\u0006J\r\u0010\u0014\u001a\u00028\u0001H$¢\u0006\u0002\u0010\tJ\r\u0010\u0015\u001a\u00028\u0000H$¢\u0006\u0002\u0010\u0012J\b\u0010\u0016\u001a\u00020\u0017H\u0014J\u0006\u0010\u0018\u001a\u00020\u0017R\u001b\u0010\u0007\u001a\u00028\u00018BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u000e\u001a\u00028\u00018F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\tR\u001b\u0010\u0010\u001a\u00028\u00008FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u000b\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/SymbolWithIrBuilder;", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "D", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "", "()V", "builtIr", "getBuiltIr", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "builtIr$delegate", "Lkotlin/Lazy;", "initialized", "", KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME, "getIr", NativeSymbol.TYPE_NAME, "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "symbol$delegate", "buildIr", "buildSymbol", "doInitialize", "", "initialize", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class SymbolWithIrBuilder<S extends IrSymbol, D extends IrDeclaration> {
    private boolean initialized;

    /* JADX INFO: renamed from: symbol$delegate, reason: from kotlin metadata */
    private final Lazy symbol = LazyKt.lazy(new Function0<S>(this) { // from class: org.jetbrains.kotlin.backend.common.lower.SymbolWithIrBuilder$symbol$2
        final /* synthetic */ SymbolWithIrBuilder<S, D> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        {
            super(0);
            this.this$0 = this;
        }

        /* JADX WARN: Incorrect return type in method signature: ()TS; */
        @Override // kotlin.jvm.functions.Function0
        public final IrSymbol invoke() {
            return this.this$0.buildSymbol();
        }
    });

    /* JADX INFO: renamed from: builtIr$delegate, reason: from kotlin metadata */
    private final Lazy builtIr = LazyKt.lazy(new Function0<D>(this) { // from class: org.jetbrains.kotlin.backend.common.lower.SymbolWithIrBuilder$builtIr$2
        final /* synthetic */ SymbolWithIrBuilder<S, D> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        {
            super(0);
            this.this$0 = this;
        }

        /* JADX WARN: Incorrect return type in method signature: ()TD; */
        @Override // kotlin.jvm.functions.Function0
        public final IrDeclaration invoke() {
            return this.this$0.buildIr();
        }
    });

    protected abstract D buildIr();

    protected abstract S buildSymbol();

    protected void doInitialize() {
    }

    public final S getSymbol() {
        return (S) this.symbol.getValue();
    }

    private final D getBuiltIr() {
        return (D) this.builtIr.getValue();
    }

    public final void initialize() {
        doInitialize();
        this.initialized = true;
    }

    public final D getIr() {
        if (!this.initialized) {
            throw new Error("Access to IR before initialization");
        }
        return (D) getBuiltIr();
    }
}
