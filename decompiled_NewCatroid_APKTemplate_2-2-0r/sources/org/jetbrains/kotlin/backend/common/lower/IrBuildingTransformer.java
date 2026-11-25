package org.jetbrains.kotlin.backend.common.lower;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BackendContext;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.symbols.IrAnonymousInitializerSymbol;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: LowerUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0015H\u0016J*\u0010\u0016\u001a\u0002H\u0017\"\u0004\b\u0000\u0010\u00172\u0006\u0010\u0018\u001a\u00020\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00170\u001bH\u0082\b¢\u0006\u0002\u0010\u001cR\u0014\u0010\u0005\u001a\u00020\u00068DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/IrBuildingTransformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "context", "Lorg/jetbrains/kotlin/backend/common/BackendContext;", "(Lorg/jetbrains/kotlin/backend/common/BackendContext;)V", "builder", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "getBuilder", "()Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "currentBuilder", "visitAnonymousInitializer", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "visitEnumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "visitField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "visitFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "visitScript", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "withBuilder", ExifInterface.GPS_DIRECTION_TRUE, NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "block", "Lkotlin/Function0;", "(Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IrBuildingTransformer extends IrElementTransformerVoid {
    private final BackendContext context;
    private IrBuilderWithScope currentBuilder;

    public IrBuildingTransformer(BackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    protected final IrBuilderWithScope getBuilder() {
        IrBuilderWithScope irBuilderWithScope = this.currentBuilder;
        Intrinsics.checkNotNull(irBuilderWithScope);
        return irBuilderWithScope;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitFunction(IrFunction declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrFunctionSymbol symbol = declaration.getSymbol();
        IrBuilderWithScope irBuilderWithScope = this.currentBuilder;
        this.currentBuilder = LowerUtilsKt.createIrBuilder$default(this.context, symbol, 0, 0, 6, (Object) null);
        try {
            return super.visitFunction(declaration);
        } finally {
            this.currentBuilder = irBuilderWithScope;
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitField(IrField declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrFieldSymbol symbol = declaration.getSymbol();
        IrBuilderWithScope irBuilderWithScope = this.currentBuilder;
        this.currentBuilder = LowerUtilsKt.createIrBuilder$default(this.context, symbol, 0, 0, 6, (Object) null);
        try {
            return super.visitField(declaration);
        } finally {
            this.currentBuilder = irBuilderWithScope;
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitAnonymousInitializer(IrAnonymousInitializer declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrAnonymousInitializerSymbol symbol = declaration.getSymbol();
        IrBuilderWithScope irBuilderWithScope = this.currentBuilder;
        this.currentBuilder = LowerUtilsKt.createIrBuilder$default(this.context, symbol, 0, 0, 6, (Object) null);
        try {
            return super.visitAnonymousInitializer(declaration);
        } finally {
            this.currentBuilder = irBuilderWithScope;
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitEnumEntry(IrEnumEntry declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrEnumEntrySymbol symbol = declaration.getSymbol();
        IrBuilderWithScope irBuilderWithScope = this.currentBuilder;
        this.currentBuilder = LowerUtilsKt.createIrBuilder$default(this.context, symbol, 0, 0, 6, (Object) null);
        try {
            return super.visitEnumEntry(declaration);
        } finally {
            this.currentBuilder = irBuilderWithScope;
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitScript(IrScript declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrScriptSymbol symbol = declaration.getSymbol();
        IrBuilderWithScope irBuilderWithScope = this.currentBuilder;
        this.currentBuilder = LowerUtilsKt.createIrBuilder$default(this.context, symbol, 0, 0, 6, (Object) null);
        try {
            return super.visitScript(declaration);
        } finally {
            this.currentBuilder = irBuilderWithScope;
        }
    }
}
