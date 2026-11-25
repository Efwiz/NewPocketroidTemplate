package org.jetbrains.kotlin.backend.jvm;

import com.beust.jcommander.Parameters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: MfvcNode.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000Ê\u0001\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u001e\u0010\u001f\u001a\u0012\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020!0\u0017j\u0002`\"2\u0006\u0010#\u001a\u00020$\u001a%\u0010%\u001a\u00020&2\u0016\u0010'\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010)0(\"\u0004\u0018\u00010)H\u0002¢\u0006\u0002\u0010*\u001a%\u0010+\u001a\u00020&2\u0016\u0010,\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010-0(\"\u0004\u0018\u00010-H\u0002¢\u0006\u0002\u0010.\u001a\u0010\u0010/\u001a\u00020&2\u0006\u00100\u001a\u00020\u001cH\u0002\u001a\u0012\u00101\u001a\u000202*\b\u0012\u0004\u0012\u0002030\u0001H\u0002\u001a\u0012\u00104\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0001*\u000205H\u0002\u001a6\u00106\u001a\u000207*\u00020\u00062\u0006\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020=2\u0012\u0010>\u001a\u000e\u0012\u0004\u0012\u00020@\u0012\u0004\u0012\u00020&0?\u001aV\u0010A\u001a\u00020B*\u00020\u00062\u0006\u00108\u001a\u00020C2\u0016\u0010D\u001a\u0012\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020!0\u0017j\u0002`\"2\u0006\u0010\u0011\u001a\u00020\t2\u0012\u0010>\u001a\u000e\u0012\u0004\u0012\u00020@\u0012\u0004\u0012\u00020&0?2\u0006\u0010E\u001a\u0002022\u0006\u0010F\u001a\u00020G\u001aF\u0010H\u001a\u00020B*\u00020\u00062\u0006\u00108\u001a\u00020C2\u0006\u0010#\u001a\u00020$2\u0006\u0010\u0011\u001a\u00020\t2\u0012\u0010>\u001a\u000e\u0012\u0004\u0012\u00020@\u0012\u0004\u0012\u00020&0?2\u0006\u0010E\u001a\u0002022\u0006\u0010F\u001a\u00020G\u001a \u0010H\u001a\u00020B*\u00020\u00062\u0006\u0010#\u001a\u00020$2\f\u0010I\u001a\b\u0012\u0004\u0012\u00020J0\u0001\u001a0\u0010K\u001a\b\u0012\u0004\u0012\u0002HL0\u0001\"\u0004\b\u0000\u0010L*\u00020\u00062\u0018\u0010M\u001a\u0014\u0012\u0004\u0012\u00020\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u0002HL0\u00010?\u001a\u001d\u0010N\u001a\u0004\u0018\u00010\u0006*\u0002052\f\u0010O\u001a\b\u0012\u0004\u0012\u00020\t0\u0001H\u0086\u0002\u001a\f\u0010P\u001a\u0004\u0018\u00010\u0002*\u00020\u001c\u001a \u0010Q\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0018\u0018\u00010R*\u00020\u00062\u0006\u0010\u0011\u001a\u00020\t\u001a\u0016\u0010S\u001a\u000202*\u00020\u001c2\n\b\u0002\u0010T\u001a\u0004\u0018\u00010\u0002\u001aF\u0010U\u001a\u00020;*\u0002052\u0006\u00108\u001a\u00020C2\u0016\u0010D\u001a\u0012\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020!0\u0017j\u0002`\"2\f\u0010V\u001a\b\u0012\u0004\u0012\u00020;0\u00012\f\u0010W\u001a\b\u0012\u0004\u0012\u00020&0X\u001a8\u0010Y\u001a\b\u0012\u0004\u0012\u0002HL0\u0001\"\u0004\b\u0000\u0010L*\b\u0012\u0004\u0012\u00020\u00030\u00012\u0014\b\u0004\u0010M\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u0002HL0?H\u0086\bø\u0001\u0000\u001a2\u0010Y\u001a\b\u0012\u0004\u0012\u0002HL0\u0001\"\u0004\b\u0000\u0010L*\u00020\u00062\u0014\b\u0004\u0010M\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u0002HL0?H\u0086\bø\u0001\u0000\u001a\n\u0010Z\u001a\u000202*\u00020!\"#\u0010\u0000\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u0001*\b\u0012\u0004\u0012\u00020\u00030\u00018F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"\u001d\u0010\u0000\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u0001*\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0007\"\u0015\u0010\b\u001a\u00020\t*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b\"\u0015\u0010\f\u001a\u00020\t*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000b\"!\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0001*\b\u0012\u0004\u0012\u00020\u00030\u00018F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0005\"\u0015\u0010\u0011\u001a\u00020\t*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u000b\"\u001b\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\t0\u0001*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015\"'\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00180\u0017*\b\u0012\u0004\u0012\u00020\u00030\u00018F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a\"\u0015\u0010\u001b\u001a\u00020\u001c*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001e*\"\u0010[\"\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020!0\u00172\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020!0\u0017\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\\"}, d2 = {"fields", "", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNode;", "getFields", "(Ljava/util/List;)Ljava/util/List;", "Lorg/jetbrains/kotlin/backend/jvm/MfvcNode;", "(Lorg/jetbrains/kotlin/backend/jvm/MfvcNode;)Ljava/util/List;", "fullFieldName", "Lorg/jetbrains/kotlin/name/Name;", "getFullFieldName", "(Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNode;)Lorg/jetbrains/kotlin/name/Name;", "fullMethodName", "getFullMethodName", "leaves", "Lorg/jetbrains/kotlin/backend/jvm/LeafMfvcNode;", "getLeaves", "name", "getName", "nameParts", "getNameParts", "(Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNode;)Ljava/util/List;", "subnodeIndices", "", "Lkotlin/ranges/IntRange;", "getSubnodeIndices", "(Ljava/util/List;)Ljava/util/Map;", "unboxMethod", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getUnboxMethod", "(Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNode;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "makeTypeArgumentsFromType", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/backend/jvm/TypeArguments;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "requireSameClasses", "", "classes", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "([Lorg/jetbrains/kotlin/ir/declarations/IrClass;)V", "requireSameSizes", "sizes", "", "([Ljava/lang/Integer;)V", "validateGettingAccessorParameters", "function", "allEqual", "", "", "collectLeavesUnboxMethods", "Lorg/jetbrains/kotlin/backend/jvm/MfvcNodeWithSubnodes;", "createInstanceFromBox", "Lorg/jetbrains/kotlin/backend/jvm/ReceiverBasedMfvcNodeInstance;", "scope", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;", "receiver", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "accessType", "Lorg/jetbrains/kotlin/backend/jvm/AccessType;", "saveVariable", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "createInstanceFromValueDeclarations", "Lorg/jetbrains/kotlin/backend/jvm/ValueDeclarationMfvcNodeInstance;", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "typeArguments", "isVar", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "createInstanceFromValueDeclarationsAndBoxType", "fieldValues", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "flatMapLeaves", "R", "f", "get", "names", "getGetterField", "getSubnodeAndIndices", "Lkotlin/Pair;", "isDefaultGetter", "expectedField", "makeBoxedExpression", "valueArguments", "registerPossibleExtraBoxCreation", "Lkotlin/Function0;", "mapLeaves", "needsMfvcFlattening", "TypeArguments", "backend.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class MfvcNodeKt {
    public static final ReceiverBasedMfvcNodeInstance createInstanceFromBox(MfvcNode mfvcNode, IrBlockBuilder scope, IrExpression receiver, AccessType accessType, Function1<? super IrVariable, Unit> saveVariable) {
        Intrinsics.checkNotNullParameter(mfvcNode, "<this>");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        Intrinsics.checkNotNullParameter(accessType, "accessType");
        Intrinsics.checkNotNullParameter(saveVariable, "saveVariable");
        IrType type = receiver.getType();
        Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
        return mfvcNode.createInstanceFromBox(scope, makeTypeArgumentsFromType((IrSimpleType) type), receiver, accessType, saveVariable);
    }

    public static final ValueDeclarationMfvcNodeInstance createInstanceFromValueDeclarationsAndBoxType(MfvcNode mfvcNode, IrBuilderWithScope scope, IrSimpleType type, Name name2, Function1<? super IrVariable, Unit> saveVariable, boolean z, IrDeclarationOrigin origin) {
        Intrinsics.checkNotNullParameter(mfvcNode, "<this>");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(saveVariable, "saveVariable");
        Intrinsics.checkNotNullParameter(origin, "origin");
        return createInstanceFromValueDeclarations(mfvcNode, scope, makeTypeArgumentsFromType(type), name2, saveVariable, z, origin);
    }

    public static final ValueDeclarationMfvcNodeInstance createInstanceFromValueDeclarationsAndBoxType(MfvcNode mfvcNode, IrSimpleType type, List<? extends IrValueDeclaration> fieldValues) {
        Intrinsics.checkNotNullParameter(mfvcNode, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(fieldValues, "fieldValues");
        return new ValueDeclarationMfvcNodeInstance(mfvcNode, makeTypeArgumentsFromType(type), fieldValues);
    }

    public static final Map<IrTypeParameterSymbol, IrType> makeTypeArgumentsFromType(IrSimpleType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        IrSimpleType irSimpleType = type;
        if (!(IrTypesKt.getClassifierOrNull(irSimpleType) instanceof IrClassSymbol)) {
            return MapsKt.emptyMap();
        }
        List<IrTypeParameter> typeParameters = JvmIrTypeUtilsKt.getErasedUpperBound(irSimpleType).getTypeParameters();
        List<IrTypeArgument> arguments = type.getArguments();
        if (typeParameters.size() != arguments.size()) {
            throw new IllegalArgumentException(("Number of type parameters (" + CollectionsKt.joinToString$default(typeParameters, null, null, null, 0, null, new Function1<IrTypeParameter, CharSequence>() { // from class: org.jetbrains.kotlin.backend.jvm.MfvcNodeKt$makeTypeArgumentsFromType$1$1
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(IrTypeParameter it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return RenderIrElementKt.render$default(it, (DumpIrTreeOptions) null, 1, (Object) null);
                }
            }, 31, null) + ") is not equal to number of type arguments (" + CollectionsKt.joinToString$default(arguments, null, null, null, 0, null, new Function1<IrTypeArgument, CharSequence>() { // from class: org.jetbrains.kotlin.backend.jvm.MfvcNodeKt$makeTypeArgumentsFromType$1$2
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(IrTypeArgument it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return RenderIrElementKt.render$default(it, (DumpIrTreeOptions) null, 1, (Object) null);
                }
            }, 31, null) + ").").toString());
        }
        List<IrTypeParameter> list = typeParameters;
        Iterator<T> it = list.iterator();
        List<IrTypeArgument> list2 = arguments;
        Iterator<T> it2 = list2.iterator();
        ArrayList arrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(list, 10), CollectionsKt.collectionSizeOrDefault(list2, 10)));
        while (it.hasNext() && it2.hasNext()) {
            Object next = it.next();
            IrTypeArgument irTypeArgument = (IrTypeArgument) it2.next();
            IrTypeParameter irTypeParameter = (IrTypeParameter) next;
            IrTypeParameterSymbol symbol = irTypeParameter.getSymbol();
            IrSimpleType typeOrNull = IrTypesKt.getTypeOrNull(irTypeArgument);
            if (typeOrNull == null) {
                typeOrNull = IrTypesKt.getDefaultType(irTypeParameter);
            }
            arrayList.add(TuplesKt.to(symbol, typeOrNull));
        }
        return MapsKt.toMap(arrayList);
    }

    public static final List<Name> getNameParts(NameableMfvcNode nameableMfvcNode) {
        Intrinsics.checkNotNullParameter(nameableMfvcNode, "<this>");
        return nameableMfvcNode.getNamedNodeImpl().getNameParts();
    }

    public static final Name getName(NameableMfvcNode nameableMfvcNode) {
        Intrinsics.checkNotNullParameter(nameableMfvcNode, "<this>");
        return (Name) CollectionsKt.last((List) getNameParts(nameableMfvcNode));
    }

    public static final IrSimpleFunction getUnboxMethod(NameableMfvcNode nameableMfvcNode) {
        Intrinsics.checkNotNullParameter(nameableMfvcNode, "<this>");
        return nameableMfvcNode.getNamedNodeImpl().getUnboxMethod();
    }

    public static final Name getFullMethodName(NameableMfvcNode nameableMfvcNode) {
        Intrinsics.checkNotNullParameter(nameableMfvcNode, "<this>");
        return nameableMfvcNode.getNamedNodeImpl().getFullMethodName();
    }

    public static final Name getFullFieldName(NameableMfvcNode nameableMfvcNode) {
        Intrinsics.checkNotNullParameter(nameableMfvcNode, "<this>");
        return nameableMfvcNode.getNamedNodeImpl().getFullFieldName();
    }

    public static final Pair<NameableMfvcNode, IntRange> getSubnodeAndIndices(MfvcNode mfvcNode, Name name2) {
        NameableMfvcNode nameableMfvcNode;
        Intrinsics.checkNotNullParameter(mfvcNode, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        MfvcNodeWithSubnodes mfvcNodeWithSubnodes = mfvcNode instanceof MfvcNodeWithSubnodes ? (MfvcNodeWithSubnodes) mfvcNode : null;
        if (mfvcNodeWithSubnodes == null || (nameableMfvcNode = mfvcNodeWithSubnodes.get(name2)) == null) {
            return null;
        }
        IntRange intRange = ((MfvcNodeWithSubnodes) mfvcNode).getSubnodeIndices().get(nameableMfvcNode);
        if (intRange == null) {
            throw new IllegalStateException("existing node without indices".toString());
        }
        return TuplesKt.to(nameableMfvcNode, intRange);
    }

    public static final IrExpression makeBoxedExpression(MfvcNodeWithSubnodes mfvcNodeWithSubnodes, IrBuilderWithScope scope, Map<IrTypeParameterSymbol, ? extends IrType> typeArguments, List<? extends IrExpression> valueArguments, Function0<Unit> registerPossibleExtraBoxCreation) {
        Intrinsics.checkNotNullParameter(mfvcNodeWithSubnodes, "<this>");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        Intrinsics.checkNotNullParameter(valueArguments, "valueArguments");
        Intrinsics.checkNotNullParameter(registerPossibleExtraBoxCreation, "registerPossibleExtraBoxCreation");
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(scope, mfvcNodeWithSubnodes.getBoxMethod());
        IrType irTypeSubstitute = IrTypeUtilsKt.substitute(irFunctionAccessExpressionIrCall.getType(), typeArguments);
        Intrinsics.checkNotNull(irTypeSubstitute, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
        IrSimpleType irSimpleType = (IrSimpleType) irTypeSubstitute;
        IrSimpleType irSimpleType2 = irSimpleType;
        if (!Intrinsics.areEqual(JvmIrTypeUtilsKt.getErasedUpperBound(irSimpleType2), JvmIrTypeUtilsKt.getErasedUpperBound(irFunctionAccessExpressionIrCall.getType()))) {
            throw new IllegalArgumentException(("Substitution of " + irFunctionAccessExpressionIrCall.getType() + " led to " + irSimpleType).toString());
        }
        Iterator<IrTypeArgument> it = irSimpleType.getArguments().iterator();
        int i = 0;
        int i2 = 0;
        while (it.hasNext()) {
            int i3 = i2 + 1;
            IrSimpleType typeOrNull = IrTypesKt.getTypeOrNull(it.next());
            if (typeOrNull == null) {
                typeOrNull = IrTypesKt.getDefaultType(JvmIrTypeUtilsKt.getErasedUpperBound(irSimpleType2).getTypeParameters().get(i2));
            }
            irFunctionAccessExpressionIrCall.putTypeArgument(i2, typeOrNull);
            i2 = i3;
        }
        Iterator<? extends IrExpression> it2 = valueArguments.iterator();
        while (it2.hasNext()) {
            irFunctionAccessExpressionIrCall.putValueArgument(i, it2.next());
            i++;
        }
        registerPossibleExtraBoxCreation.invoke();
        return irFunctionAccessExpressionIrCall;
    }

    public static final MfvcNode get(MfvcNodeWithSubnodes mfvcNodeWithSubnodes, List<Name> names) {
        NameableMfvcNode nameableMfvcNode;
        Intrinsics.checkNotNullParameter(mfvcNodeWithSubnodes, "<this>");
        Intrinsics.checkNotNullParameter(names, "names");
        NameableMfvcNode nameableMfvcNode2 = mfvcNodeWithSubnodes;
        for (Name name2 : names) {
            MfvcNodeWithSubnodes mfvcNodeWithSubnodes2 = nameableMfvcNode2 instanceof MfvcNodeWithSubnodes ? (MfvcNodeWithSubnodes) nameableMfvcNode2 : null;
            if (mfvcNodeWithSubnodes2 == null || (nameableMfvcNode = mfvcNodeWithSubnodes2.get(name2)) == null) {
                return null;
            }
            nameableMfvcNode2 = nameableMfvcNode;
        }
        return nameableMfvcNode2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean allEqual(List<? extends Object> list) {
        List<? extends Object> list2 = list;
        if ((list2 instanceof Collection) && list2.isEmpty()) {
            return true;
        }
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            if (!Intrinsics.areEqual(it.next(), CollectionsKt.first((List) list))) {
                return false;
            }
        }
        return true;
    }

    public static final Map<NameableMfvcNode, IntRange> getSubnodeIndices(List<? extends NameableMfvcNode> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Map mapCreateMapBuilder = MapsKt.createMapBuilder();
        int i = 0;
        for (NameableMfvcNode nameableMfvcNode : list) {
            if (nameableMfvcNode instanceof IntermediateMfvcNode) {
                int leavesCount = nameableMfvcNode.getLeavesCount() + i;
                mapCreateMapBuilder.put(nameableMfvcNode, RangesKt.until(i, leavesCount));
                Map<NameableMfvcNode, IntRange> subnodeIndices = ((IntermediateMfvcNode) nameableMfvcNode).getSubnodeIndices();
                LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(subnodeIndices.size()));
                Iterator<T> it = subnodeIndices.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    Object key = entry.getKey();
                    IntRange intRange = (IntRange) entry.getValue();
                    linkedHashMap.put(key, new IntRange(intRange.getFirst() + i, intRange.getLast() + i));
                }
                mapCreateMapBuilder.putAll(linkedHashMap);
                i = leavesCount;
            } else if (nameableMfvcNode instanceof LeafMfvcNode) {
                mapCreateMapBuilder.put(nameableMfvcNode, new IntRange(i, i));
                i++;
            }
        }
        return MapsKt.build(mapCreateMapBuilder);
    }

    /* JADX INFO: Add missing generic type declarations: [R] */
    /* JADX INFO: renamed from: org.jetbrains.kotlin.backend.jvm.MfvcNodeKt$mapLeaves$1, reason: invalid class name */
    /* JADX INFO: compiled from: MfvcNode.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0002\b\u0005"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "R", "it", "Lorg/jetbrains/kotlin/backend/jvm/LeafMfvcNode;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 176)
    public static final class AnonymousClass1<R> extends Lambda implements Function1<LeafMfvcNode, List<? extends R>> {
        final /* synthetic */ Function1<LeafMfvcNode, R> $f;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass1(Function1<? super LeafMfvcNode, ? extends R> function1) {
            super(1);
            this.$f = function1;
        }

        @Override // kotlin.jvm.functions.Function1
        public final List<R> invoke(LeafMfvcNode it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return CollectionsKt.listOf(this.$f.invoke(it));
        }
    }

    public static final <R> List<R> mapLeaves(MfvcNode mfvcNode, Function1<? super LeafMfvcNode, ? extends R> f) {
        Intrinsics.checkNotNullParameter(mfvcNode, "<this>");
        Intrinsics.checkNotNullParameter(f, "f");
        return flatMapLeaves(mfvcNode, new AnonymousClass1(f));
    }

    public static final <R> List<R> flatMapLeaves(MfvcNode mfvcNode, Function1<? super LeafMfvcNode, ? extends List<? extends R>> f) {
        Intrinsics.checkNotNullParameter(mfvcNode, "<this>");
        Intrinsics.checkNotNullParameter(f, "f");
        if (!(mfvcNode instanceof MfvcNodeWithSubnodes)) {
            if (mfvcNode instanceof LeafMfvcNode) {
                return f.invoke(mfvcNode);
            }
            throw new NoWhenBranchMatchedException();
        }
        List<NameableMfvcNode> subnodes = ((MfvcNodeWithSubnodes) mfvcNode).getSubnodes();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = subnodes.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, flatMapLeaves((NameableMfvcNode) it.next(), f));
        }
        return arrayList;
    }

    public static final <R> List<R> mapLeaves(List<? extends NameableMfvcNode> list, Function1<? super LeafMfvcNode, ? extends R> f) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(f, "f");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, flatMapLeaves((NameableMfvcNode) it.next(), new AnonymousClass1(f)));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void requireSameClasses(IrClass... irClassArr) {
        List listEmptyList;
        List listFilterNotNull = ArraysKt.filterNotNull(irClassArr);
        Iterator it = listFilterNotNull.iterator();
        if (it.hasNext()) {
            ArrayList arrayList = new ArrayList();
            Object next = it.next();
            while (it.hasNext()) {
                Object next2 = it.next();
                arrayList.add(Boolean.valueOf(Intrinsics.areEqual((IrClass) next, (IrClass) next2)));
                next = next2;
            }
            listEmptyList = arrayList;
        } else {
            listEmptyList = CollectionsKt.emptyList();
        }
        List list = listEmptyList;
        if ((list instanceof Collection) && list.isEmpty()) {
            return;
        }
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            if (!((Boolean) it2.next()).booleanValue()) {
                throw new IllegalArgumentException(("Found different classes: " + CollectionsKt.joinToString$default(listFilterNotNull, "\n", null, null, 0, null, new Function1<IrClass, CharSequence>() { // from class: org.jetbrains.kotlin.backend.jvm.MfvcNodeKt$requireSameClasses$3$1
                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(IrClass it3) {
                        Intrinsics.checkNotNullParameter(it3, "it");
                        return RenderIrElementKt.render$default(it3, (DumpIrTreeOptions) null, 1, (Object) null);
                    }
                }, 30, null)).toString());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void requireSameSizes(Integer... numArr) {
        if (SequencesKt.count(SequencesKt.distinct(SequencesKt.filterNotNull(ArraysKt.asSequence(numArr)))) != 1) {
            throw new IllegalArgumentException(("Found different sizes: " + ArraysKt.joinToString$default(numArr, (CharSequence) null, (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 63, (Object) null)).toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void validateGettingAccessorParameters(IrSimpleFunction irSimpleFunction) {
        if (!irSimpleFunction.getValueParameters().isEmpty()) {
            throw new IllegalArgumentException(("Value parameters are not expected for " + RenderIrElementKt.render$default(irSimpleFunction, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        if (irSimpleFunction.getExtensionReceiverParameter() != null) {
            throw new IllegalArgumentException(("Extension receiver is not expected for " + RenderIrElementKt.render$default(irSimpleFunction, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        if (irSimpleFunction.getContextReceiverParametersCount() != 0) {
            throw new IllegalArgumentException(("Context receivers is not expected for " + RenderIrElementKt.render$default(irSimpleFunction, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        if (!irSimpleFunction.getTypeParameters().isEmpty()) {
            throw new IllegalArgumentException(("Type parameters are not expected for " + RenderIrElementKt.render$default(irSimpleFunction, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
    }

    public static final List<IrField> getFields(MfvcNode mfvcNode) {
        Intrinsics.checkNotNullParameter(mfvcNode, "<this>");
        if (mfvcNode instanceof MfvcNodeWithSubnodes) {
            return ((MfvcNodeWithSubnodes) mfvcNode).getFields();
        }
        if (!(mfvcNode instanceof LeafMfvcNode)) {
            throw new NoWhenBranchMatchedException();
        }
        IrField field = ((LeafMfvcNode) mfvcNode).getField();
        if (field != null) {
            return CollectionsKt.listOf(field);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<IrSimpleFunction> collectLeavesUnboxMethods(MfvcNodeWithSubnodes mfvcNodeWithSubnodes) {
        return flatMapLeaves(mfvcNodeWithSubnodes, new Function1<LeafMfvcNode, List<? extends IrSimpleFunction>>() { // from class: org.jetbrains.kotlin.backend.jvm.MfvcNodeKt$collectLeavesUnboxMethods$$inlined$mapLeaves$1
            @Override // kotlin.jvm.functions.Function1
            public final List<IrSimpleFunction> invoke(LeafMfvcNode it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return CollectionsKt.listOf(MfvcNodeKt.getUnboxMethod(it));
            }
        });
    }

    public static /* synthetic */ boolean isDefaultGetter$default(IrSimpleFunction irSimpleFunction, IrField irField, int i, Object obj) {
        if ((i & 1) != 0) {
            irField = null;
        }
        return isDefaultGetter(irSimpleFunction, irField);
    }

    public static final boolean isDefaultGetter(IrSimpleFunction irSimpleFunction, IrField irField) {
        List<IrStatement> statements;
        IrProperty owner;
        Intrinsics.checkNotNullParameter(irSimpleFunction, "<this>");
        IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
        if (!AdditionalIrUtilsKt.isGetter(irSimpleFunction2)) {
            return false;
        }
        if (irField != null) {
            IrPropertySymbol correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol();
            if (!Intrinsics.areEqual((correspondingPropertySymbol == null || (owner = correspondingPropertySymbol.getOwner()) == null) ? null : owner.getBackingField(), irField)) {
                return false;
            }
        }
        IrBody body = irSimpleFunction.getBody();
        IrStatement irStatement = (body == null || (statements = IrUtilsKt.getStatements(body)) == null) ? null : (IrStatement) CollectionsKt.singleOrNull((List) statements);
        IrReturn irReturn = irStatement instanceof IrReturn ? (IrReturn) irStatement : null;
        IrExpression value = irReturn != null ? irReturn.getValue() : null;
        IrGetField irGetField = value instanceof IrGetField ? (IrGetField) value : null;
        if (irGetField == null) {
            return false;
        }
        IrField owner2 = irGetField.getSymbol().getOwner();
        return irField == null || Intrinsics.areEqual(owner2, irField) || (IrUtilsKt.getParentAsClass(irSimpleFunction2).getIsCompanion() && Intrinsics.areEqual(owner2.getCorrespondingPropertySymbol(), irSimpleFunction.getCorrespondingPropertySymbol()));
    }

    public static final IrField getGetterField(IrSimpleFunction irSimpleFunction) {
        List<IrStatement> statements;
        Intrinsics.checkNotNullParameter(irSimpleFunction, "<this>");
        if (!AdditionalIrUtilsKt.isGetter(irSimpleFunction)) {
            return null;
        }
        IrBody body = irSimpleFunction.getBody();
        IrStatement irStatement = (body == null || (statements = IrUtilsKt.getStatements(body)) == null) ? null : (IrStatement) CollectionsKt.singleOrNull((List) statements);
        IrReturn irReturn = irStatement instanceof IrReturn ? (IrReturn) irStatement : null;
        IrExpression value = irReturn != null ? irReturn.getValue() : null;
        IrGetField irGetField = value instanceof IrGetField ? (IrGetField) value : null;
        if (irGetField == null) {
            return null;
        }
        return irGetField.getSymbol().getOwner();
    }

    public static final boolean needsMfvcFlattening(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        if (!JvmIrTypeUtilsKt.isMultiFieldValueClassType(irType) || org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.isNullable(irType)) {
            IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(irType);
            if (classifierOrNull instanceof IrTypeParameterSymbol) {
                List<IrType> superTypes = ((IrTypeParameterSymbol) classifierOrNull).getOwner().getSuperTypes();
                if (!(superTypes instanceof Collection) || !superTypes.isEmpty()) {
                    Iterator<T> it = superTypes.iterator();
                    while (it.hasNext()) {
                        if (needsMfvcFlattening((IrType) it.next())) {
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    public static final ValueDeclarationMfvcNodeInstance createInstanceFromValueDeclarations(MfvcNode mfvcNode, final IrBuilderWithScope scope, Map<IrTypeParameterSymbol, ? extends IrType> typeArguments, final Name name2, final Function1<? super IrVariable, Unit> saveVariable, final boolean z, final IrDeclarationOrigin origin) {
        Intrinsics.checkNotNullParameter(mfvcNode, "<this>");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(saveVariable, "saveVariable");
        Intrinsics.checkNotNullParameter(origin, "origin");
        return new ValueDeclarationMfvcNodeInstance(mfvcNode, typeArguments, flatMapLeaves(mfvcNode, new Function1<LeafMfvcNode, List<? extends IrVariable>>() { // from class: org.jetbrains.kotlin.backend.jvm.MfvcNodeKt$createInstanceFromValueDeclarations$$inlined$mapLeaves$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final List<IrVariable> invoke(LeafMfvcNode it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return CollectionsKt.listOf(MfvcNodeInstanceKt.savableStandaloneVariable$default(scope, it.getType(), CollectionsKt.joinToString$default(CollectionsKt.listOf((Object[]) new Name[]{name2, MfvcNodeKt.getFullFieldName(it)}), Parameters.DEFAULT_OPTION_PREFIXES, null, null, 0, null, null, 62, null), z, origin, false, saveVariable, 16, null));
            }
        }));
    }

    public static final List<LeafMfvcNode> getLeaves(List<? extends NameableMfvcNode> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, flatMapLeaves((NameableMfvcNode) it.next(), new Function1<LeafMfvcNode, List<? extends LeafMfvcNode>>() { // from class: org.jetbrains.kotlin.backend.jvm.MfvcNodeKt$special$$inlined$mapLeaves$1
                @Override // kotlin.jvm.functions.Function1
                public final List<LeafMfvcNode> invoke(LeafMfvcNode it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    return CollectionsKt.listOf(it2);
                }
            }));
        }
        return arrayList;
    }

    public static final List<IrField> getFields(List<? extends NameableMfvcNode> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, flatMapLeaves((NameableMfvcNode) it.next(), new Function1<LeafMfvcNode, List<? extends IrField>>() { // from class: org.jetbrains.kotlin.backend.jvm.MfvcNodeKt$special$$inlined$mapLeaves$2
                @Override // kotlin.jvm.functions.Function1
                public final List<IrField> invoke(LeafMfvcNode it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    return CollectionsKt.listOf(it2.getField());
                }
            }));
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = arrayList2;
        boolean z = arrayList3 instanceof Collection;
        if (!z || !arrayList3.isEmpty()) {
            Iterator it2 = arrayList3.iterator();
            while (it2.hasNext()) {
                if (((IrField) it2.next()) != null) {
                    if (z && arrayList3.isEmpty()) {
                        return arrayList2;
                    }
                    Iterator it3 = arrayList3.iterator();
                    while (it3.hasNext()) {
                        if (((IrField) it3.next()) == null) {
                            throw new IllegalStateException("IrFields can either exist all or none for MFVC property".toString());
                        }
                    }
                    return arrayList2;
                }
            }
        }
        return null;
    }
}
