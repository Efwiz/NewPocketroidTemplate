package org.jetbrains.kotlin.ir.util;

import kotlin.Metadata;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: TypeRemapper.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0003H&J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH&¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/TypeRemapper;", "", "enterScope", "", "irTypeParametersContainer", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;", "leaveScope", "remapType", "Lorg/jetbrains/kotlin/ir/types/IrType;", ModuleXmlParser.TYPE, "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface TypeRemapper {
    void enterScope(IrTypeParametersContainer irTypeParametersContainer);

    void leaveScope();

    IrType remapType(IrType type);
}
