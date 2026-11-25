package org.jetbrains.kotlin.ir.types;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;

/* JADX INFO: compiled from: IrType.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;", "Lorg/jetbrains/kotlin/ir/declarations/IrAnnotationContainer;", "arguments", "", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "getArguments", "()Ljava/util/List;", "hasQuestionMark", "", "getHasQuestionMark", "()Z", "typeAlias", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "getTypeAlias", "()Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrTypeAbbreviation extends IrAnnotationContainer {
    List<IrTypeArgument> getArguments();

    boolean getHasQuestionMark();

    IrTypeAliasSymbol getTypeAlias();
}
