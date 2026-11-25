package org.jetbrains.kotlin.ir.types;

import kotlin.Metadata;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;

/* JADX INFO: compiled from: IrTypeSubstitutor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H&¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/AbstractIrTypeSubstitutor;", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "()V", "substitute", "Lorg/jetbrains/kotlin/ir/types/IrType;", ModuleXmlParser.TYPE, "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractIrTypeSubstitutor implements TypeSubstitutorMarker {
    public abstract IrType substitute(IrType type);
}
