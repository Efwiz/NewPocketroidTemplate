package org.jetbrains.kotlin.backend.jvm;

import com.reandroid.arsc.value.CompoundEntry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: MfvcNodeInstance.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0016\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\u0002\u0010\fJ\u0013\u0010\u0017\u001a\u0004\u0018\u00010\u00002\u0006\u0010\u0018\u001a\u00020\u0019H\u0096\u0002J,\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\n2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\"0!H\u0016J\u0016\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\n2\u0006\u0010\u001c\u001a\u00020#H\u0002J&\u0010$\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020#2\u0006\u0010\u001e\u001a\u00020\u001f2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\"0!H\u0016J$\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\n2\u0006\u0010\u001c\u001a\u00020#2\f\u0010'\u001a\b\u0012\u0004\u0012\u00020\u001b0\nH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R$\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/ValueDeclarationMfvcNodeInstance;", "Lorg/jetbrains/kotlin/backend/jvm/MfvcNodeInstance;", "node", "Lorg/jetbrains/kotlin/backend/jvm/MfvcNode;", "typeArguments", "", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/backend/jvm/TypeArguments;", "valueDeclarations", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "(Lorg/jetbrains/kotlin/backend/jvm/MfvcNode;Ljava/util/Map;Ljava/util/List;)V", "getNode", "()Lorg/jetbrains/kotlin/backend/jvm/MfvcNode;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "getTypeArguments", "()Ljava/util/Map;", "getValueDeclarations", "()Ljava/util/List;", "get", "name", "Lorg/jetbrains/kotlin/name/Name;", "makeFlattenedGetterExpressions", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "scope", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;", "currentClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "registerPossibleExtraBoxCreation", "Lkotlin/Function0;", "", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "makeGetterExpression", "makeSetterStatements", "Lorg/jetbrains/kotlin/ir/IrStatement;", CompoundEntry.NAME_values, "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ValueDeclarationMfvcNodeInstance implements MfvcNodeInstance {
    private final MfvcNode node;
    private final IrSimpleType type;
    private final Map<IrTypeParameterSymbol, IrType> typeArguments;
    private final List<IrValueDeclaration> valueDeclarations;

    /* JADX WARN: Multi-variable type inference failed */
    public ValueDeclarationMfvcNodeInstance(MfvcNode node, Map<IrTypeParameterSymbol, ? extends IrType> typeArguments, List<? extends IrValueDeclaration> valueDeclarations) {
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        Intrinsics.checkNotNullParameter(valueDeclarations, "valueDeclarations");
        this.node = node;
        this.typeArguments = typeArguments;
        this.valueDeclarations = valueDeclarations;
        ValueDeclarationMfvcNodeInstance valueDeclarationMfvcNodeInstance = this;
        if (valueDeclarations.size() == MfvcNodeInstanceKt.getSize(valueDeclarationMfvcNodeInstance)) {
            this.type = MfvcNodeInstanceKt.makeTypeFromMfvcNodeAndTypeArguments(getNode(), getTypeArguments());
            return;
        }
        throw new IllegalArgumentException(("Expected value declarations list of size " + MfvcNodeInstanceKt.getSize(valueDeclarationMfvcNodeInstance) + " but got of size " + valueDeclarations.size()).toString());
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MfvcNodeInstance
    public MfvcNode getNode() {
        return this.node;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MfvcNodeInstance
    public Map<IrTypeParameterSymbol, IrType> getTypeArguments() {
        return this.typeArguments;
    }

    public final List<IrValueDeclaration> getValueDeclarations() {
        return this.valueDeclarations;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MfvcNodeInstance
    public IrSimpleType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MfvcNodeInstance
    public List<IrExpression> makeFlattenedGetterExpressions(IrBlockBuilder scope, IrClass currentClass, Function0<Unit> registerPossibleExtraBoxCreation) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(currentClass, "currentClass");
        Intrinsics.checkNotNullParameter(registerPossibleExtraBoxCreation, "registerPossibleExtraBoxCreation");
        return makeFlattenedGetterExpressions(scope);
    }

    private final List<IrExpression> makeFlattenedGetterExpressions(IrBuilderWithScope scope) {
        List<IrValueDeclaration> list = this.valueDeclarations;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(ExpressionHelpersKt.irGet(scope, (IrValueDeclaration) it.next()));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MfvcNodeInstance
    public IrExpression makeGetterExpression(IrBuilderWithScope scope, IrClass currentClass, Function0<Unit> registerPossibleExtraBoxCreation) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(currentClass, "currentClass");
        Intrinsics.checkNotNullParameter(registerPossibleExtraBoxCreation, "registerPossibleExtraBoxCreation");
        MfvcNode node = getNode();
        if (node instanceof LeafMfvcNode) {
            return (IrExpression) CollectionsKt.single((List) makeFlattenedGetterExpressions(scope));
        }
        if (node instanceof MfvcNodeWithSubnodes) {
            return MfvcNodeKt.makeBoxedExpression((MfvcNodeWithSubnodes) getNode(), scope, getTypeArguments(), makeFlattenedGetterExpressions(scope), registerPossibleExtraBoxCreation);
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MfvcNodeInstance
    public ValueDeclarationMfvcNodeInstance get(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Pair<NameableMfvcNode, IntRange> subnodeAndIndices = MfvcNodeKt.getSubnodeAndIndices(getNode(), name2);
        if (subnodeAndIndices == null) {
            return null;
        }
        return new ValueDeclarationMfvcNodeInstance(subnodeAndIndices.component1(), getTypeArguments(), CollectionsKt.slice((List) this.valueDeclarations, subnodeAndIndices.component2()));
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MfvcNodeInstance
    public List<IrStatement> makeSetterStatements(IrBuilderWithScope scope, List<? extends IrExpression> values) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(values, "values");
        MfvcNodeInstanceKt.checkValuesCount(this, values);
        List<IrValueDeclaration> list = this.valueDeclarations;
        Iterator<T> it = list.iterator();
        List<? extends IrExpression> list2 = values;
        Iterator<T> it2 = list2.iterator();
        ArrayList arrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(list, 10), CollectionsKt.collectionSizeOrDefault(list2, 10)));
        while (it.hasNext() && it2.hasNext()) {
            arrayList.add(ExpressionHelpersKt.irSet$default(scope, (IrValueDeclaration) it.next(), (IrExpression) it2.next(), (IrStatementOrigin) null, 4, (Object) null));
        }
        return arrayList;
    }
}
