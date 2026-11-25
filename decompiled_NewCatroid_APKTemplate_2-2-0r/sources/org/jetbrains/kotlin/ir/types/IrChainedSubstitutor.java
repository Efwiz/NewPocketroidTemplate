package org.jetbrains.kotlin.ir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: IrTypeSubstitutor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0001¢\u0006\u0002\u0010\u0004J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0016R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0003\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/IrChainedSubstitutor;", "Lorg/jetbrains/kotlin/ir/types/AbstractIrTypeSubstitutor;", "first", "second", "(Lorg/jetbrains/kotlin/ir/types/AbstractIrTypeSubstitutor;Lorg/jetbrains/kotlin/ir/types/AbstractIrTypeSubstitutor;)V", "getFirst", "()Lorg/jetbrains/kotlin/ir/types/AbstractIrTypeSubstitutor;", "getSecond", "substitute", "Lorg/jetbrains/kotlin/ir/types/IrType;", ModuleXmlParser.TYPE, "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrChainedSubstitutor extends AbstractIrTypeSubstitutor {
    private final AbstractIrTypeSubstitutor first;
    private final AbstractIrTypeSubstitutor second;

    public IrChainedSubstitutor(AbstractIrTypeSubstitutor first, AbstractIrTypeSubstitutor second) {
        Intrinsics.checkNotNullParameter(first, "first");
        Intrinsics.checkNotNullParameter(second, "second");
        this.first = first;
        this.second = second;
    }

    public final AbstractIrTypeSubstitutor getFirst() {
        return this.first;
    }

    public final AbstractIrTypeSubstitutor getSecond() {
        return this.second;
    }

    @Override // org.jetbrains.kotlin.ir.types.AbstractIrTypeSubstitutor
    public IrType substitute(IrType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return this.second.substitute(this.first.substitute(type));
    }
}
