package org.jetbrains.kotlin.ir.builders;

import androidx.exifinterface.media.ExifInterface;
import com.android.apksig.internal.apk.AndroidBinXmlParser;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrBreakImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrContinueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDoWhileLoopImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetObjectValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrWhileLoopImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: IrBuilder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000¤\u0001\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a)\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u0002H\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006\u001aH\u0010\u0007\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\b*\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0017\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u0002H\u00010\u000b¢\u0006\u0002\b\rH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u000e\u001aR\u0010\u0007\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\b*\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0017\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u0002H\u00010\u000b¢\u0006\u0002\b\rH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u0011\u001aN\u0010\u0012\u001a\u00020\u0013\"\b\b\u0000\u0010\u0001*\u00020\b*\b\u0012\u0004\u0012\u0002H\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u00162\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u00182\b\b\u0002\u0010\u0019\u001a\u00020\u001a2\b\b\u0002\u0010\u000f\u001a\u00020\u001b2\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u001d\u001a>\u0010\u0012\u001a\u00020\u0013*\u00020\u001e2\u0006\u0010\u0015\u001a\u00020\u00162\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u00182\b\b\u0002\u0010\u0019\u001a\u00020\u001a2\b\b\u0002\u0010\u000f\u001a\u00020\u001b2\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u001d\u001aR\u0010\u0012\u001a\u00020\u0013*\u00020\u001e2\u0006\u0010\u001c\u001a\u00020\u001d2\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u00182\b\b\u0002\u0010\u0019\u001a\u00020\u001a2\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010\u00162\b\b\u0002\u0010\u000f\u001a\u00020\u001b2\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0004\u001aQ\u0010 \u001a\u00020\u0016*\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00102\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\u001d2\u0017\u0010\"\u001a\u0013\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020$0\u000b¢\u0006\u0002\b\rH\u0086\bø\u0001\u0000\u001a9\u0010%\u001a\u00020&*\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0017\u0010\"\u001a\u0013\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020$0\u000b¢\u0006\u0002\b\rH\u0086\bø\u0001\u0000\u001a\u0012\u0010(\u001a\u00020)*\u00020*2\u0006\u0010+\u001a\u00020,\u001aQ\u0010-\u001a\u00020\u0016*\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00102\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\u001d2\u0017\u0010\"\u001a\u0013\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020$0\u000b¢\u0006\u0002\b\rH\u0086\bø\u0001\u0000\u001a\u0012\u0010.\u001a\u00020/*\u00020*2\u0006\u0010+\u001a\u00020,\u001a\u0016\u00100\u001a\u000201*\u00020*2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u001a\u0012\u00102\u001a\u000203*\u00020*2\u0006\u00104\u001a\u000205\u001a\u0016\u00106\u001a\u000207*\u00020*2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u00068"}, d2 = {"at", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/builders/IrBuilder;", "startOffset", "", "endOffset", "(Lorg/jetbrains/kotlin/ir/builders/IrBuilder;II)Lorg/jetbrains/kotlin/ir/builders/IrBuilder;", "buildStatement", "Lorg/jetbrains/kotlin/ir/IrElement;", "Lorg/jetbrains/kotlin/ir/builders/IrGeneratorWithScope;", "builder", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/builders/IrSingleStatementBuilder;", "Lkotlin/ExtensionFunctionType;", "(Lorg/jetbrains/kotlin/ir/builders/IrGeneratorWithScope;IILkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/IrElement;", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "(Lorg/jetbrains/kotlin/ir/builders/IrGeneratorWithScope;IILorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/IrElement;", "createTmpVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "Lorg/jetbrains/kotlin/ir/builders/IrStatementsBuilder;", "irExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "nameHint", "", "isMutable", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "irType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/ir/builders/Scope;", "initializer", "irBlock", "resultType", "body", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;", "", "irBlockBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBodyBuilder;", "irBreak", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrBreakImpl;", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "irComposite", "irContinue", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrContinueImpl;", "irDoWhile", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrDoWhileLoopImpl;", "irGetObject", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrGetObjectValueImpl;", "classSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "irWhile", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrWhileLoopImpl;", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrBuilderKt {
    public static final <T extends IrElement> T buildStatement(IrGeneratorWithScope irGeneratorWithScope, int i, int i2, IrStatementOrigin irStatementOrigin, Function1<? super IrSingleStatementBuilder, ? extends T> builder) {
        Intrinsics.checkNotNullParameter(irGeneratorWithScope, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        return builder.invoke(new IrSingleStatementBuilder(irGeneratorWithScope.getContext(), irGeneratorWithScope.getScope(), i, i2, irStatementOrigin));
    }

    public static final <T extends IrElement> T buildStatement(IrGeneratorWithScope irGeneratorWithScope, int i, int i2, Function1<? super IrSingleStatementBuilder, ? extends T> builder) {
        Intrinsics.checkNotNullParameter(irGeneratorWithScope, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        return builder.invoke(new IrSingleStatementBuilder(irGeneratorWithScope.getContext(), irGeneratorWithScope.getScope(), i, i2, null, 16, null));
    }

    public static final <T extends IrBuilder> T at(T t, int i, int i2) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        t.setStartOffset(i);
        t.setEndOffset(i2);
        return t;
    }

    public static /* synthetic */ IrExpression irBlock$default(IrGeneratorWithScope irGeneratorWithScope, int i, int i2, IrStatementOrigin irStatementOrigin, IrType irType, Function1 body, int i3, Object obj) {
        IrStatementOrigin irStatementOrigin2 = (i3 & 4) != 0 ? null : irStatementOrigin;
        IrType irType2 = (i3 & 8) != 0 ? null : irType;
        Intrinsics.checkNotNullParameter(irGeneratorWithScope, "<this>");
        Intrinsics.checkNotNullParameter(body, "body");
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(irGeneratorWithScope.getContext(), irGeneratorWithScope.getScope(), i, i2, irStatementOrigin2, irType2, false, 64, null);
        body.invoke(irBlockBuilder);
        return irBlockBuilder.getIrBlockBody();
    }

    public static final IrExpression irBlock(IrGeneratorWithScope irGeneratorWithScope, int i, int i2, IrStatementOrigin irStatementOrigin, IrType irType, Function1<? super IrBlockBuilder, Unit> body) {
        Intrinsics.checkNotNullParameter(irGeneratorWithScope, "<this>");
        Intrinsics.checkNotNullParameter(body, "body");
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(irGeneratorWithScope.getContext(), irGeneratorWithScope.getScope(), i, i2, irStatementOrigin, irType, false, 64, null);
        body.invoke(irBlockBuilder);
        return irBlockBuilder.getIrBlockBody();
    }

    public static /* synthetic */ IrExpression irComposite$default(IrGeneratorWithScope irGeneratorWithScope, int i, int i2, IrStatementOrigin irStatementOrigin, IrType irType, Function1 body, int i3, Object obj) {
        IrStatementOrigin irStatementOrigin2 = (i3 & 4) != 0 ? null : irStatementOrigin;
        IrType irType2 = (i3 & 8) != 0 ? null : irType;
        Intrinsics.checkNotNullParameter(irGeneratorWithScope, "<this>");
        Intrinsics.checkNotNullParameter(body, "body");
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(irGeneratorWithScope.getContext(), irGeneratorWithScope.getScope(), i, i2, irStatementOrigin2, irType2, true);
        body.invoke(irBlockBuilder);
        return irBlockBuilder.getIrBlockBody();
    }

    public static final IrExpression irComposite(IrGeneratorWithScope irGeneratorWithScope, int i, int i2, IrStatementOrigin irStatementOrigin, IrType irType, Function1<? super IrBlockBuilder, Unit> body) {
        Intrinsics.checkNotNullParameter(irGeneratorWithScope, "<this>");
        Intrinsics.checkNotNullParameter(body, "body");
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(irGeneratorWithScope.getContext(), irGeneratorWithScope.getScope(), i, i2, irStatementOrigin, irType, true);
        body.invoke(irBlockBuilder);
        return irBlockBuilder.getIrBlockBody();
    }

    public static final IrBlockBody irBlockBody(IrGeneratorWithScope irGeneratorWithScope, int i, int i2, Function1<? super IrBlockBodyBuilder, Unit> body) {
        Intrinsics.checkNotNullParameter(irGeneratorWithScope, "<this>");
        Intrinsics.checkNotNullParameter(body, "body");
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(irGeneratorWithScope.getContext(), irGeneratorWithScope.getScope(), i, i2);
        body.invoke(irBlockBodyBuilder);
        return irBlockBodyBuilder.getIrBlockBody();
    }

    public static /* synthetic */ IrWhileLoopImpl irWhile$default(IrBuilderWithScope irBuilderWithScope, IrStatementOrigin irStatementOrigin, int i, Object obj) {
        if ((i & 1) != 0) {
            irStatementOrigin = null;
        }
        return irWhile(irBuilderWithScope, irStatementOrigin);
    }

    public static final IrWhileLoopImpl irWhile(IrBuilderWithScope irBuilderWithScope, IrStatementOrigin irStatementOrigin) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        return new IrWhileLoopImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), irBuilderWithScope.getContext().getIrBuiltIns().getUnitType(), irStatementOrigin);
    }

    public static /* synthetic */ IrDoWhileLoopImpl irDoWhile$default(IrBuilderWithScope irBuilderWithScope, IrStatementOrigin irStatementOrigin, int i, Object obj) {
        if ((i & 1) != 0) {
            irStatementOrigin = null;
        }
        return irDoWhile(irBuilderWithScope, irStatementOrigin);
    }

    public static final IrDoWhileLoopImpl irDoWhile(IrBuilderWithScope irBuilderWithScope, IrStatementOrigin irStatementOrigin) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        return new IrDoWhileLoopImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), irBuilderWithScope.getContext().getIrBuiltIns().getUnitType(), irStatementOrigin);
    }

    public static final IrBreakImpl irBreak(IrBuilderWithScope irBuilderWithScope, IrLoop loop) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(loop, "loop");
        return new IrBreakImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), irBuilderWithScope.getContext().getIrBuiltIns().getNothingType(), loop);
    }

    public static final IrContinueImpl irContinue(IrBuilderWithScope irBuilderWithScope, IrLoop loop) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(loop, "loop");
        return new IrContinueImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), irBuilderWithScope.getContext().getIrBuiltIns().getNothingType(), loop);
    }

    public static final IrGetObjectValueImpl irGetObject(IrBuilderWithScope irBuilderWithScope, IrClassSymbol classSymbol) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(classSymbol, "classSymbol");
        return new IrGetObjectValueImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), new IrSimpleTypeImpl((IrClassifierSymbol) classSymbol, false, CollectionsKt.emptyList(), CollectionsKt.emptyList(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null), classSymbol);
    }

    public static /* synthetic */ IrVariable createTmpVariable$default(IrStatementsBuilder irStatementsBuilder, IrExpression irExpression, String str, boolean z, IrDeclarationOrigin irDeclarationOrigin, IrType irType, int i, Object obj) {
        String str2 = (i & 2) != 0 ? null : str;
        if ((i & 4) != 0) {
            z = false;
        }
        boolean z2 = z;
        if ((i & 8) != 0) {
            irDeclarationOrigin = IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE;
        }
        return createTmpVariable(irStatementsBuilder, irExpression, str2, z2, irDeclarationOrigin, (i & 16) != 0 ? null : irType);
    }

    public static final <T extends IrElement> IrVariable createTmpVariable(IrStatementsBuilder<? extends T> irStatementsBuilder, IrExpression irExpression, String str, boolean z, IrDeclarationOrigin origin, IrType irType) {
        Intrinsics.checkNotNullParameter(irStatementsBuilder, "<this>");
        Intrinsics.checkNotNullParameter(irExpression, "irExpression");
        Intrinsics.checkNotNullParameter(origin, "origin");
        IrVariable irVariableCreateTmpVariable = createTmpVariable(irStatementsBuilder.getScope(), irExpression, str, z, origin, irType);
        irStatementsBuilder.unaryPlus(irVariableCreateTmpVariable);
        return irVariableCreateTmpVariable;
    }

    public static final IrVariable createTmpVariable(Scope scope, IrType irType, String str, boolean z, IrExpression irExpression, IrDeclarationOrigin origin, int i, int i2) {
        Intrinsics.checkNotNullParameter(scope, "<this>");
        Intrinsics.checkNotNullParameter(irType, "irType");
        Intrinsics.checkNotNullParameter(origin, "origin");
        IrDeclarationParent localDeclarationParent = scope.getLocalDeclarationParent();
        Name nameIdentifier = Name.identifier(str == null ? Constants.TMP_DIRECTORY_NAME : str);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        IrVariable irVariableBuildVariable = DeclarationBuildersKt.buildVariable(localDeclarationParent, i, i2, origin, nameIdentifier, irType, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 64) != 0 ? false : z, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 128) != 0 ? false : false, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 256) != 0 ? false : false);
        irVariableBuildVariable.setInitializer(irExpression);
        return irVariableBuildVariable;
    }

    public static /* synthetic */ IrVariable createTmpVariable$default(Scope scope, IrExpression irExpression, String str, boolean z, IrDeclarationOrigin irDeclarationOrigin, IrType irType, int i, Object obj) {
        String str2 = (i & 2) != 0 ? null : str;
        if ((i & 4) != 0) {
            z = false;
        }
        boolean z2 = z;
        if ((i & 8) != 0) {
            irDeclarationOrigin = IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE;
        }
        return createTmpVariable(scope, irExpression, str2, z2, irDeclarationOrigin, (i & 16) != 0 ? null : irType);
    }

    public static final IrVariable createTmpVariable(Scope scope, IrExpression irExpression, String str, boolean z, IrDeclarationOrigin origin, IrType irType) {
        Intrinsics.checkNotNullParameter(scope, "<this>");
        Intrinsics.checkNotNullParameter(irExpression, "irExpression");
        Intrinsics.checkNotNullParameter(origin, "origin");
        IrDeclarationParent localDeclarationParent = scope.getLocalDeclarationParent();
        int startOffset = irExpression.getStartOffset();
        int endOffset = irExpression.getEndOffset();
        Name nameIdentifier = Name.identifier(str == null ? Constants.TMP_DIRECTORY_NAME : str);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        IrVariable irVariableBuildVariable = DeclarationBuildersKt.buildVariable(localDeclarationParent, startOffset, endOffset, origin, nameIdentifier, irType == null ? irExpression.getType() : irType, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 64) != 0 ? false : z, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 128) != 0 ? false : false, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 256) != 0 ? false : false);
        irVariableBuildVariable.setInitializer(irExpression);
        return irVariableBuildVariable;
    }
}
