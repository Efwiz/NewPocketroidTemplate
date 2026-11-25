package org.jetbrains.kotlin.backend.jvm;

import com.reandroid.arsc.value.CompoundEntry;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: MfvcNodeInstance.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u00002\u0006\u0010\u0012\u001a\u00020\u0013H¦\u0002J,\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cH&J&\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u001f2\u0006\u0010\u0019\u001a\u00020\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cH&J$\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u00152\u0006\u0010\u0017\u001a\u00020\u001f2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\"\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bj\u0002`\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006#À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/MfvcNodeInstance;", "", "node", "Lorg/jetbrains/kotlin/backend/jvm/MfvcNode;", "getNode", "()Lorg/jetbrains/kotlin/backend/jvm/MfvcNode;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "typeArguments", "", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/backend/jvm/TypeArguments;", "getTypeArguments", "()Ljava/util/Map;", "get", "name", "Lorg/jetbrains/kotlin/name/Name;", "makeFlattenedGetterExpressions", "", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "scope", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;", "currentClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "registerPossibleExtraBoxCreation", "Lkotlin/Function0;", "", "makeGetterExpression", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "makeSetterStatements", "Lorg/jetbrains/kotlin/ir/IrStatement;", CompoundEntry.NAME_values, "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface MfvcNodeInstance {
    MfvcNodeInstance get(Name name2);

    MfvcNode getNode();

    IrSimpleType getType();

    Map<IrTypeParameterSymbol, IrType> getTypeArguments();

    List<IrExpression> makeFlattenedGetterExpressions(IrBlockBuilder scope, IrClass currentClass, Function0<Unit> registerPossibleExtraBoxCreation);

    IrExpression makeGetterExpression(IrBuilderWithScope scope, IrClass currentClass, Function0<Unit> registerPossibleExtraBoxCreation);

    List<IrStatement> makeSetterStatements(IrBuilderWithScope scope, List<? extends IrExpression> values);
}
