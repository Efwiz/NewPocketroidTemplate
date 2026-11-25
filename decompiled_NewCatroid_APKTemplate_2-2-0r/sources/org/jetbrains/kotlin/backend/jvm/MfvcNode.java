package org.jetbrains.kotlin.backend.jvm;

import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: MfvcNode.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001JN\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0016\u0010\u000e\u001a\u0012\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00070\u000fj\u0002`\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00190\u0017H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t\u0082\u0001\u0002\u001a\u001bø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001cÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/MfvcNode;", "", "leavesCount", "", "getLeavesCount", "()I", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "createInstanceFromBox", "Lorg/jetbrains/kotlin/backend/jvm/ReceiverBasedMfvcNodeInstance;", "scope", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;", "typeArguments", "", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "Lorg/jetbrains/kotlin/backend/jvm/TypeArguments;", "receiver", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "accessType", "Lorg/jetbrains/kotlin/backend/jvm/AccessType;", "saveVariable", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "", "Lorg/jetbrains/kotlin/backend/jvm/MfvcNodeWithSubnodes;", "Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNode;", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface MfvcNode {
    ReceiverBasedMfvcNodeInstance createInstanceFromBox(IrBlockBuilder scope, Map<IrTypeParameterSymbol, ? extends IrType> typeArguments, IrExpression receiver, AccessType accessType, Function1<? super IrVariable, Unit> saveVariable);

    int getLeavesCount();

    IrType getType();
}
