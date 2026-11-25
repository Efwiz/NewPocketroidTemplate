package org.jetbrains.kotlin.fir.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: Fir2IrSpecialSymbolProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fH&J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u0004R\u001a\u0010\u0003\u001a\u00020\u0004X\u0084.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrSpecialSymbolProvider;", "", "()V", "components", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "getComponents", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "setComponents", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;)V", "getClassSymbolById", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "id", "Lorg/jetbrains/kotlin/name/ClassId;", "initComponents", "", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class Fir2IrSpecialSymbolProvider {
    protected Fir2IrComponents components;

    public abstract IrClassSymbol getClassSymbolById(ClassId id2);

    protected final Fir2IrComponents getComponents() {
        Fir2IrComponents fir2IrComponents = this.components;
        if (fir2IrComponents != null) {
            return fir2IrComponents;
        }
        Intrinsics.throwUninitializedPropertyAccessException("components");
        return null;
    }

    protected final void setComponents(Fir2IrComponents fir2IrComponents) {
        Intrinsics.checkNotNullParameter(fir2IrComponents, "<set-?>");
        this.components = fir2IrComponents;
    }

    public final void initComponents(Fir2IrComponents components) {
        Intrinsics.checkNotNullParameter(components, "components");
        setComponents(components);
    }
}
