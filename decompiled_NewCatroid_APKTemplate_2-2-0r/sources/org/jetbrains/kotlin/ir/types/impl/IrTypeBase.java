package org.jetbrains.kotlin.ir.types.impl;

import kotlin.Metadata;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: IrTypeBase.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0005R\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u00018VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/impl/IrTypeBase;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/ir/types/IrTypeProjection;", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/types/KotlinType;)V", "getKotlinType", "()Lorg/jetbrains/kotlin/types/KotlinType;", ModuleXmlParser.TYPE, "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrTypeBase extends IrType implements IrTypeProjection {
    private final KotlinType kotlinType;

    public IrTypeBase(KotlinType kotlinType) {
        this.kotlinType = kotlinType;
    }

    public final KotlinType getKotlinType() {
        return this.kotlinType;
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeProjection
    public IrType getType() {
        return this;
    }
}
