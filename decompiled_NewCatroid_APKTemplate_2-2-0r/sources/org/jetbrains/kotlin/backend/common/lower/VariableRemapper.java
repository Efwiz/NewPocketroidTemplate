package org.jetbrains.kotlin.backend.common.lower;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;

/* JADX INFO: compiled from: LowerUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\u0010\u0006J\u0012\u0010\t\u001a\u0004\u0018\u00010\u00052\u0006\u0010\n\u001a\u00020\u0005H\u0014R\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/VariableRemapper;", "Lorg/jetbrains/kotlin/backend/common/lower/AbstractVariableRemapper;", "mapping", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "(Ljava/util/Map;)V", "getMapping", "()Ljava/util/Map;", "remapVariable", "value", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class VariableRemapper extends AbstractVariableRemapper {
    private final Map<IrValueParameter, IrValueDeclaration> mapping;

    /* JADX WARN: Multi-variable type inference failed */
    public VariableRemapper(Map<IrValueParameter, ? extends IrValueDeclaration> mapping) {
        Intrinsics.checkNotNullParameter(mapping, "mapping");
        this.mapping = mapping;
    }

    public final Map<IrValueParameter, IrValueDeclaration> getMapping() {
        return this.mapping;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.AbstractVariableRemapper
    protected IrValueDeclaration remapVariable(IrValueDeclaration value) {
        Intrinsics.checkNotNullParameter(value, "value");
        return this.mapping.get(value);
    }
}
