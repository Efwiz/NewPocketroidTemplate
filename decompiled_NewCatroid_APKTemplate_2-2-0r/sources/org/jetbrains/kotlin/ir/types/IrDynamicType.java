package org.jetbrains.kotlin.ir.types;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.types.impl.IrTypeBase;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.model.DynamicTypeMarker;

/* JADX INFO: compiled from: IrType.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/IrDynamicType;", "Lorg/jetbrains/kotlin/ir/types/impl/IrTypeBase;", "Lorg/jetbrains/kotlin/types/model/DynamicTypeMarker;", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/types/KotlinType;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrDynamicType extends IrTypeBase implements DynamicTypeMarker {
    public IrDynamicType(KotlinType kotlinType) {
        super(kotlinType);
    }
}
