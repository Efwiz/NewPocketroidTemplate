package org.jetbrains.kotlin.ir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.types.impl.IrTypeBase;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrType.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\b&\u0018\u00002\u00020\u00012\u00020\u0002B!\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\nR\u0011\u0010\u000b\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/IrErrorType;", "Lorg/jetbrains/kotlin/ir/types/impl/IrTypeBase;", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "errorClassStubSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "isMarkedNullable", "", "(Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;Z)V", "()Z", NativeSymbol.TYPE_NAME, "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrErrorType extends IrTypeBase implements SimpleTypeMarker {
    private final IrClassSymbol errorClassStubSymbol;
    private final boolean isMarkedNullable;

    public /* synthetic */ IrErrorType(KotlinType kotlinType, IrClassSymbol irClassSymbol, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(kotlinType, irClassSymbol, (i & 4) != 0 ? false : z);
    }

    /* JADX INFO: renamed from: isMarkedNullable, reason: from getter */
    public final boolean getIsMarkedNullable() {
        return this.isMarkedNullable;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrErrorType(KotlinType kotlinType, IrClassSymbol errorClassStubSymbol, boolean z) {
        super(kotlinType);
        Intrinsics.checkNotNullParameter(errorClassStubSymbol, "errorClassStubSymbol");
        this.errorClassStubSymbol = errorClassStubSymbol;
        this.isMarkedNullable = z;
    }

    /* JADX INFO: renamed from: getSymbol, reason: from getter */
    public final IrClassSymbol getErrorClassStubSymbol() {
        return this.errorClassStubSymbol;
    }
}
