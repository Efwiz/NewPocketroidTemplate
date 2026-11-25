package org.jetbrains.kotlin.ir.backend.js.ir;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.archive.PathTree;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsStatementOrigins;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrValueParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOriginImpl;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrRawFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrBranchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrBreakImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCatchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrContinueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDelegatingConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrElseBranchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetObjectValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrIfThenElseImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrRawFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrThrowImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTryImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrVarargImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrWhenImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.symbols.IrVariableSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrBuilder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Â\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001zB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bJ\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\bJ\u001c\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0006J\u001c\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u0010J\u0016\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u0015JB\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0010\b\u0002\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00062\b\b\u0002\u0010\u001b\u001a\u00020\u001c2\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u001eJ\u0016\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u000bJ\u001e\u0010$\u001a\u00020%2\u0006\u0010\u0007\u001a\u00020\b2\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0006J@\u0010&\u001a\u00020'2\u0006\u0010\u0018\u001a\u00020(2\u0012\b\u0002\u0010\u001a\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u00062\u0012\b\u0002\u0010)\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u00062\b\b\u0002\u0010\u001b\u001a\u00020\u001cJ\u0016\u0010*\u001a\u00020+2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u0015J\"\u0010,\u001a\u00020-2\u0006\u0010\u0018\u001a\u00020(2\u0012\b\u0002\u0010\u001a\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u0006J\u0016\u0010.\u001a\u00020/2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u00100\u001a\u000201JR\u00102\u001a\u0002032\u0006\u00104\u001a\u0002052\n\b\u0002\u00106\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\b\u0002\u00107\u001a\u0002082\b\b\u0002\u00109\u001a\u0002082\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001cJ\u0016\u0010:\u001a\u00020;2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010<\u001a\u00020\u001eJ\u0018\u0010=\u001a\u00020>2\u0006\u00104\u001a\u00020?2\b\b\u0002\u0010\u001b\u001a\u00020\u001cJn\u0010@\u001a\u00020A2\u0006\u00107\u001a\u0002082\u0006\u00109\u001a\u0002082\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010B\u001a\u00020\u00042\u0006\u0010C\u001a\u00020\u00042\n\b\u0002\u0010D\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\b\b\u0002\u0010E\u001a\u0002082\b\b\u0002\u0010F\u001a\u0002082\b\b\u0002\u0010G\u001a\u0002082\b\b\u0002\u0010H\u001a\u000208JR\u0010@\u001a\u00020A2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010B\u001a\u00020\u00042\u0006\u0010C\u001a\u00020\u00042\n\b\u0002\u0010D\u001a\u0004\u0018\u00010\u00042\b\b\u0002\u0010E\u001a\u0002082\b\b\u0002\u0010F\u001a\u0002082\b\b\u0002\u0010G\u001a\u0002082\b\b\u0002\u0010H\u001a\u000208J\u0016\u0010I\u001a\u00020J2\u0006\u0010K\u001a\u00020\u00042\u0006\u0010L\u001a\u00020\bJ\u001c\u0010M\u001a\b\u0012\u0004\u0012\u0002080\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0011\u001a\u000208J\u0016\u0010N\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010O0\u000f2\u0006\u0010\u0007\u001a\u00020\bJ\u0016\u0010P\u001a\u00020Q2\u0006\u0010R\u001a\u00020S2\u0006\u0010\u0007\u001a\u00020\bJ\u0016\u0010T\u001a\u00020J2\u0006\u0010K\u001a\u00020\u00042\u0006\u0010L\u001a\u00020\bJ\u001e\u0010U\u001a\u00020V2\u0006\u0010R\u001a\u00020S2\u0006\u0010K\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bJ4\u0010W\u001a\u00020X2\u0006\u00104\u001a\u0002052\b\u00106\u001a\u0004\u0018\u00010\u00042\u0006\u0010K\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u001eJ\u0016\u0010Y\u001a\u00020Z2\u0006\u00104\u001a\u00020?2\u0006\u0010K\u001a\u00020\u0004J\u001e\u0010[\u001a\u00020Z2\u0006\u00104\u001a\u00020\\2\u0006\u0010K\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bJ\u001c\u0010]\u001a\b\u0012\u0004\u0012\u00020^0\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010_\u001a\u00020^J\u0016\u0010`\u001a\u00020a2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010K\u001a\u00020\u0004J\u000e\u0010b\u001a\u00020c2\u0006\u0010\u0007\u001a\u00020\bJ&\u0010d\u001a\u00020J2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010e\u001a\u00020f2\u0006\u0010g\u001a\u00020\u00042\u0006\u0010L\u001a\u00020\bJ:\u0010h\u001a\u00020i2\u0006\u0010j\u001a\u00020k2\u0006\u0010l\u001a\u00020^2\u0006\u0010m\u001a\u0002082\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010n\u001a\u00020\u00102\b\b\u0002\u0010\u001b\u001a\u00020oJV\u0010p\u001a\u00020\"2\u0006\u0010\u0007\u001a\u00020\b2\b\u0010j\u001a\u0004\u0018\u00010q2\b\b\u0002\u0010l\u001a\u00020^2\b\b\u0002\u0010r\u001a\u00020\u00102\b\b\u0002\u0010s\u001a\u00020\u00102\b\b\u0002\u0010t\u001a\u00020\u00102\n\b\u0002\u0010u\u001a\u0004\u0018\u00010\u00042\b\b\u0002\u0010\u001b\u001a\u00020oJ&\u0010v\u001a\u00020w2\u0006\u0010\u0007\u001a\u00020\b2\f\u0010x\u001a\b\u0012\u0004\u0012\u00020y0\u00062\b\b\u0002\u0010\u001b\u001a\u00020\u001c¨\u0006{"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ir/JsIrBuilder;", "", "()V", "buildArray", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", PathTree.NAME_elements, "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "elementType", "buildBlock", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrBlockImpl;", "statements", "Lorg/jetbrains/kotlin/ir/IrStatement;", "buildBoolean", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstImpl;", "", RegisterSpec.PREFIX, "buildBreak", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrBreakImpl;", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "buildCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "target", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "typeArguments", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "superQualifierSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "buildCatch", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrCatchImpl;", "ex", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "block", "buildComposite", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrCompositeImpl;", "buildConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "constructorTypeArguments", "buildContinue", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrContinueImpl;", "buildDelegatingConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;", "buildFunctionExpression", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrFunctionExpressionImpl;", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "buildGetField", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrGetFieldImpl;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "receiver", "startOffset", "", "endOffset", "buildGetObjectValue", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrGetObjectValueImpl;", "classSymbol", "buildGetValue", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrGetValueImpl;", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "buildIfElse", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "cond", "thenBranch", "elseBranch", "thenBranchStartOffset", "thenBranchEndOffset", "elseBranchStartOffset", "elseBranchEndOffset", "buildImplicitCast", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrTypeOperatorCallImpl;", "value", "toType", "buildInt", "buildNull", "", "buildRawReference", "Lorg/jetbrains/kotlin/ir/expressions/IrRawFunctionReference;", "targetSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "buildReinterpretCast", "buildReturn", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrReturnImpl;", "buildSetField", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrSetFieldImpl;", "buildSetValue", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrSetValueImpl;", "buildSetVariable", "Lorg/jetbrains/kotlin/ir/symbols/IrVariableSymbol;", "buildString", "", "s", "buildThrow", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrThrowImpl;", "buildTry", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrTryImpl;", "buildTypeOperator", "operator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperator;", "argument", "buildValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "name", "index", "isAssignable", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "buildVar", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "isVar", "isConst", "isLateinit", "initializer", "buildWhen", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrWhenImpl;", "branches", "Lorg/jetbrains/kotlin/ir/expressions/IrBranch;", "SYNTHESIZED_DECLARATION", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsIrBuilder {
    public static final JsIrBuilder INSTANCE = new JsIrBuilder();

    /* JADX INFO: compiled from: IrBuilder.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ir/JsIrBuilder$SYNTHESIZED_DECLARATION;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SYNTHESIZED_DECLARATION extends IrDeclarationOriginImpl {
        public static final SYNTHESIZED_DECLARATION INSTANCE = new SYNTHESIZED_DECLARATION();

        private SYNTHESIZED_DECLARATION() {
            super("SYNTHESIZED_DECLARATION", false, 2, null);
        }
    }

    private JsIrBuilder() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ IrCall buildCall$default(JsIrBuilder jsIrBuilder, IrSimpleFunctionSymbol irSimpleFunctionSymbol, IrType irType, List list, IrStatementOrigin irStatementOrigin, IrClassSymbol irClassSymbol, int i, Object obj) {
        IrType irType2 = (i & 2) != 0 ? null : irType;
        List list2 = (i & 4) != 0 ? null : list;
        if ((i & 8) != 0) {
            irStatementOrigin = JsStatementOrigins.SYNTHESIZED_STATEMENT.INSTANCE;
        }
        return jsIrBuilder.buildCall(irSimpleFunctionSymbol, irType2, list2, irStatementOrigin, (i & 16) != 0 ? null : irClassSymbol);
    }

    public final IrCall buildCall(IrSimpleFunctionSymbol target, IrType type, List<? extends IrType> typeArguments, IrStatementOrigin origin, IrClassSymbol superQualifierSymbol) {
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(origin, "origin");
        IrSimpleFunction owner = target.getOwner();
        if (type == null) {
            type = owner.getReturnType();
        }
        IrCallImpl irCallImpl = new IrCallImpl(-1, -1, type, target, owner.getTypeParameters().size(), owner.getValueParameters().size(), origin, superQualifierSymbol);
        if (typeArguments != null) {
            typeArguments.size();
            irCallImpl.getTypeArgumentsCount();
            for (IndexedValue indexedValue : CollectionsKt.withIndex(typeArguments)) {
                irCallImpl.putTypeArgument(indexedValue.getIndex(), (IrType) indexedValue.component2());
            }
        }
        return irCallImpl;
    }

    public final IrExpression buildArray(List<? extends IrExpression> elements, IrType type, IrType elementType) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(elementType, "elementType");
        return new IrVarargImpl(-1, -1, type, elementType, elements);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ IrDelegatingConstructorCall buildDelegatingConstructorCall$default(JsIrBuilder jsIrBuilder, IrConstructorSymbol irConstructorSymbol, List list, int i, Object obj) {
        if ((i & 2) != 0) {
            list = null;
        }
        return jsIrBuilder.buildDelegatingConstructorCall(irConstructorSymbol, list);
    }

    public final IrDelegatingConstructorCall buildDelegatingConstructorCall(IrConstructorSymbol target, List<? extends IrType> typeArguments) {
        Intrinsics.checkNotNullParameter(target, "target");
        IrConstructor owner = target.getOwner();
        IrDelegatingConstructorCallImpl irDelegatingConstructorCallImpl = new IrDelegatingConstructorCallImpl(-1, -1, owner.getReturnType(), target, IrUtilsKt.getParentAsClass(owner).getTypeParameters().size(), owner.getValueParameters().size());
        if (typeArguments != null) {
            typeArguments.size();
            irDelegatingConstructorCallImpl.getTypeArgumentsCount();
            for (IndexedValue indexedValue : CollectionsKt.withIndex(typeArguments)) {
                irDelegatingConstructorCallImpl.putTypeArgument(indexedValue.getIndex(), (IrType) indexedValue.component2());
            }
        }
        return irDelegatingConstructorCallImpl;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ IrConstructorCall buildConstructorCall$default(JsIrBuilder jsIrBuilder, IrConstructorSymbol irConstructorSymbol, List list, List list2, IrStatementOrigin irStatementOrigin, int i, Object obj) {
        if ((i & 2) != 0) {
            list = null;
        }
        if ((i & 4) != 0) {
            list2 = null;
        }
        if ((i & 8) != 0) {
            irStatementOrigin = JsStatementOrigins.SYNTHESIZED_STATEMENT.INSTANCE;
        }
        return jsIrBuilder.buildConstructorCall(irConstructorSymbol, list, list2, irStatementOrigin);
    }

    public final IrConstructorCall buildConstructorCall(IrConstructorSymbol target, List<? extends IrType> typeArguments, List<? extends IrType> constructorTypeArguments, IrStatementOrigin origin) {
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(origin, "origin");
        IrConstructor owner = target.getOwner();
        int i = -1;
        int i2 = -1;
        SourceElement sourceElement = null;
        IrConstructorCallImpl irConstructorCallImpl = new IrConstructorCallImpl(i, i2, owner.getReturnType(), target, IrUtilsKt.getParentAsClass(owner).getTypeParameters().size(), owner.getTypeParameters().size(), owner.getValueParameters().size(), origin, sourceElement, 256, null);
        if (typeArguments != null) {
            typeArguments.size();
            irConstructorCallImpl.getTypeArgumentsCount();
            for (IndexedValue indexedValue : CollectionsKt.withIndex(typeArguments)) {
                irConstructorCallImpl.putTypeArgument(indexedValue.getIndex(), (IrType) indexedValue.component2());
            }
        }
        if (constructorTypeArguments != null) {
            constructorTypeArguments.size();
            irConstructorCallImpl.getTypeArgumentsCount();
            for (IndexedValue indexedValue2 : CollectionsKt.withIndex(constructorTypeArguments)) {
                irConstructorCallImpl.putTypeArgument(indexedValue2.getIndex(), (IrType) indexedValue2.component2());
            }
        }
        return irConstructorCallImpl;
    }

    public final IrRawFunctionReference buildRawReference(IrFunctionSymbol targetSymbol, IrType type) {
        Intrinsics.checkNotNullParameter(targetSymbol, "targetSymbol");
        Intrinsics.checkNotNullParameter(type, "type");
        return new IrRawFunctionReferenceImpl(-1, -1, type, targetSymbol);
    }

    public final IrReturnImpl buildReturn(IrFunctionSymbol targetSymbol, IrExpression value, IrType type) {
        Intrinsics.checkNotNullParameter(targetSymbol, "targetSymbol");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(type, "type");
        return new IrReturnImpl(-1, -1, type, targetSymbol, value);
    }

    public final IrThrowImpl buildThrow(IrType type, IrExpression value) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(value, "value");
        return new IrThrowImpl(-1, -1, type, value);
    }

    public static /* synthetic */ IrValueParameter buildValueParameter$default(JsIrBuilder jsIrBuilder, IrFunction irFunction, String str, int i, IrType irType, boolean z, IrDeclarationOrigin irDeclarationOrigin, int i2, Object obj) {
        if ((i2 & 16) != 0) {
            z = false;
        }
        boolean z2 = z;
        if ((i2 & 32) != 0) {
            irDeclarationOrigin = SYNTHESIZED_DECLARATION.INSTANCE;
        }
        return jsIrBuilder.buildValueParameter(irFunction, str, i, irType, z2, irDeclarationOrigin);
    }

    public final IrGetObjectValueImpl buildGetObjectValue(IrType type, IrClassSymbol classSymbol) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(classSymbol, "classSymbol");
        return new IrGetObjectValueImpl(-1, -1, type, classSymbol);
    }

    public static /* synthetic */ IrGetValueImpl buildGetValue$default(JsIrBuilder jsIrBuilder, IrValueSymbol irValueSymbol, IrStatementOrigin irStatementOrigin, int i, Object obj) {
        if ((i & 2) != 0) {
            irStatementOrigin = JsStatementOrigins.SYNTHESIZED_STATEMENT.INSTANCE;
        }
        return jsIrBuilder.buildGetValue(irValueSymbol, irStatementOrigin);
    }

    public final IrGetValueImpl buildGetValue(IrValueSymbol symbol, IrStatementOrigin origin) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(origin, "origin");
        return new IrGetValueImpl(-1, -1, symbol.getOwner().getType(), symbol, origin);
    }

    public final IrSetValueImpl buildSetValue(IrValueSymbol symbol, IrExpression value) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(value, "value");
        return new IrSetValueImpl(-1, -1, symbol.getOwner().getType(), symbol, value, JsStatementOrigins.SYNTHESIZED_STATEMENT.INSTANCE);
    }

    public final IrSetValueImpl buildSetVariable(IrVariableSymbol symbol, IrExpression value, IrType type) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(type, "type");
        return new IrSetValueImpl(-1, -1, type, symbol, value, JsStatementOrigins.SYNTHESIZED_STATEMENT.INSTANCE);
    }

    public final IrGetFieldImpl buildGetField(IrFieldSymbol symbol, IrExpression receiver, IrClassSymbol superQualifierSymbol, IrType type, int startOffset, int endOffset, IrStatementOrigin origin) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return new IrGetFieldImpl(startOffset, endOffset, symbol, type == null ? symbol.getOwner().getType() : type, receiver, origin, superQualifierSymbol);
    }

    public static /* synthetic */ IrSetFieldImpl buildSetField$default(JsIrBuilder jsIrBuilder, IrFieldSymbol irFieldSymbol, IrExpression irExpression, IrExpression irExpression2, IrType irType, IrClassSymbol irClassSymbol, int i, Object obj) {
        if ((i & 16) != 0) {
            irClassSymbol = null;
        }
        return jsIrBuilder.buildSetField(irFieldSymbol, irExpression, irExpression2, irType, irClassSymbol);
    }

    public final IrSetFieldImpl buildSetField(IrFieldSymbol symbol, IrExpression receiver, IrExpression value, IrType type, IrClassSymbol superQualifierSymbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(type, "type");
        return new IrSetFieldImpl(-1, -1, symbol, receiver, value, type, JsStatementOrigins.SYNTHESIZED_STATEMENT.INSTANCE, superQualifierSymbol);
    }

    public final IrBlockImpl buildBlock(IrType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return new IrBlockImpl(-1, -1, type, JsStatementOrigins.SYNTHESIZED_STATEMENT.INSTANCE);
    }

    public final IrBlockImpl buildBlock(IrType type, List<? extends IrStatement> statements) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(statements, "statements");
        return new IrBlockImpl(-1, -1, type, JsStatementOrigins.SYNTHESIZED_STATEMENT.INSTANCE, statements);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ IrCompositeImpl buildComposite$default(JsIrBuilder jsIrBuilder, IrType irType, List list, int i, Object obj) {
        if ((i & 2) != 0) {
            list = CollectionsKt.emptyList();
        }
        return jsIrBuilder.buildComposite(irType, list);
    }

    public final IrCompositeImpl buildComposite(IrType type, List<? extends IrStatement> statements) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(statements, "statements");
        return new IrCompositeImpl(-1, -1, type, JsStatementOrigins.SYNTHESIZED_STATEMENT.INSTANCE, statements);
    }

    public final IrFunctionExpressionImpl buildFunctionExpression(IrType type, IrSimpleFunction function) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(function, "function");
        return new IrFunctionExpressionImpl(-1, -1, type, function, JsStatementOrigins.SYNTHESIZED_STATEMENT.INSTANCE);
    }

    public final IrVariable buildVar(IrType type, IrDeclarationParent parent, String name2, boolean isVar, boolean isConst, boolean isLateinit, IrExpression initializer, IrDeclarationOrigin origin) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Name nameIdentifier = Name.identifier(name2);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        IrVariable irVariableBuildVariable = DeclarationBuildersKt.buildVariable(parent, -1, -1, origin, nameIdentifier, type, isVar, isConst, isLateinit);
        irVariableBuildVariable.setInitializer(initializer);
        return irVariableBuildVariable;
    }

    public final IrBreakImpl buildBreak(IrType type, IrLoop loop) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(loop, "loop");
        return new IrBreakImpl(-1, -1, type, loop);
    }

    public final IrContinueImpl buildContinue(IrType type, IrLoop loop) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(loop, "loop");
        return new IrContinueImpl(-1, -1, type, loop);
    }

    public static /* synthetic */ IrWhen buildIfElse$default(JsIrBuilder jsIrBuilder, IrType irType, IrExpression irExpression, IrExpression irExpression2, IrExpression irExpression3, int i, int i2, int i3, int i4, int i5, Object obj) {
        int endOffset;
        IrExpression irExpression4 = (i5 & 8) != 0 ? null : irExpression3;
        int startOffset = (i5 & 16) != 0 ? irExpression.getStartOffset() : i;
        int endOffset2 = (i5 & 32) != 0 ? irExpression2.getEndOffset() : i2;
        int i6 = (i5 & 64) != 0 ? -1 : i3;
        if ((i5 & 128) != 0) {
            endOffset = irExpression4 != null ? irExpression4.getEndOffset() : -1;
        } else {
            endOffset = i4;
        }
        return jsIrBuilder.buildIfElse(irType, irExpression, irExpression2, irExpression4, startOffset, endOffset2, i6, endOffset);
    }

    public final IrWhen buildIfElse(IrType type, IrExpression cond, IrExpression thenBranch, IrExpression elseBranch, int thenBranchStartOffset, int thenBranchEndOffset, int elseBranchStartOffset, int elseBranchEndOffset) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(cond, "cond");
        Intrinsics.checkNotNullParameter(thenBranch, "thenBranch");
        return buildIfElse(-1, -1, type, cond, thenBranch, elseBranch, JsStatementOrigins.SYNTHESIZED_STATEMENT.INSTANCE, thenBranchStartOffset, thenBranchEndOffset, elseBranchStartOffset, elseBranchEndOffset);
    }

    public static /* synthetic */ IrWhen buildIfElse$default(JsIrBuilder jsIrBuilder, int i, int i2, IrType irType, IrExpression irExpression, IrExpression irExpression2, IrExpression irExpression3, IrStatementOrigin irStatementOrigin, int i3, int i4, int i5, int i6, int i7, Object obj) {
        int endOffset;
        IrExpression irExpression4 = (i7 & 32) != 0 ? null : irExpression3;
        IrStatementOrigin irStatementOrigin2 = (i7 & 64) != 0 ? null : irStatementOrigin;
        int startOffset = (i7 & 128) != 0 ? irExpression.getStartOffset() : i3;
        int endOffset2 = (i7 & 256) != 0 ? irExpression2.getEndOffset() : i4;
        int i8 = (i7 & 512) != 0 ? -1 : i5;
        if ((i7 & 1024) != 0) {
            endOffset = irExpression4 != null ? irExpression4.getEndOffset() : -1;
        } else {
            endOffset = i6;
        }
        return jsIrBuilder.buildIfElse(i, i2, irType, irExpression, irExpression2, irExpression4, irStatementOrigin2, startOffset, endOffset2, i8, endOffset);
    }

    public final IrWhen buildIfElse(int startOffset, int endOffset, IrType type, IrExpression cond, IrExpression thenBranch, IrExpression elseBranch, IrStatementOrigin origin, int thenBranchStartOffset, int thenBranchEndOffset, int elseBranchStartOffset, int elseBranchEndOffset) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(cond, "cond");
        Intrinsics.checkNotNullParameter(thenBranch, "thenBranch");
        IrIfThenElseImpl irIfThenElseImpl = new IrIfThenElseImpl(startOffset, endOffset, type, origin);
        irIfThenElseImpl.getBranches().add(new IrBranchImpl(thenBranchStartOffset, thenBranchEndOffset, cond, thenBranch));
        if (elseBranch != null) {
            irIfThenElseImpl.getBranches().add(new IrElseBranchImpl(elseBranchStartOffset, elseBranchEndOffset, IrConstImpl.INSTANCE.constTrue(-1, -1, cond.getType()), elseBranch));
        }
        return irIfThenElseImpl;
    }

    public static /* synthetic */ IrWhenImpl buildWhen$default(JsIrBuilder jsIrBuilder, IrType irType, List list, IrStatementOrigin irStatementOrigin, int i, Object obj) {
        if ((i & 4) != 0) {
            irStatementOrigin = JsStatementOrigins.SYNTHESIZED_STATEMENT.INSTANCE;
        }
        return jsIrBuilder.buildWhen(irType, list, irStatementOrigin);
    }

    public final IrWhenImpl buildWhen(IrType type, List<? extends IrBranch> branches, IrStatementOrigin origin) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(branches, "branches");
        Intrinsics.checkNotNullParameter(origin, "origin");
        return new IrWhenImpl(-1, -1, type, origin, branches);
    }

    public final IrTypeOperatorCallImpl buildTypeOperator(IrType type, IrTypeOperator operator, IrExpression argument, IrType toType) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(operator, "operator");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(toType, "toType");
        return new IrTypeOperatorCallImpl(-1, -1, type, operator, toType, argument);
    }

    public final IrTypeOperatorCallImpl buildImplicitCast(IrExpression value, IrType toType) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(toType, "toType");
        return buildTypeOperator(toType, IrTypeOperator.IMPLICIT_CAST, value, toType);
    }

    public final IrTypeOperatorCallImpl buildReinterpretCast(IrExpression value, IrType toType) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(toType, "toType");
        return buildTypeOperator(toType, IrTypeOperator.REINTERPRET_CAST, value, toType);
    }

    public final IrConstImpl buildNull(IrType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return IrConstImpl.INSTANCE.constNull(-1, -1, type);
    }

    public final IrConstImpl<Boolean> buildBoolean(IrType type, boolean v) {
        Intrinsics.checkNotNullParameter(type, "type");
        return IrConstImpl.INSTANCE.m7206boolean(-1, -1, type, v);
    }

    public final IrConstImpl<Integer> buildInt(IrType type, int v) {
        Intrinsics.checkNotNullParameter(type, "type");
        return IrConstImpl.INSTANCE.m7211int(-1, -1, type, v);
    }

    public final IrConstImpl<String> buildString(IrType type, String s) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(s, "s");
        return IrConstImpl.INSTANCE.string(-1, -1, type, s);
    }

    public final IrTryImpl buildTry(IrType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return new IrTryImpl(-1, -1, type);
    }

    public final IrCatchImpl buildCatch(IrVariable ex, IrBlockImpl block) {
        Intrinsics.checkNotNullParameter(ex, "ex");
        Intrinsics.checkNotNullParameter(block, "block");
        return new IrCatchImpl(-1, -1, ex, block);
    }

    public final IrValueParameter buildValueParameter(IrFunction parent, String name2, int index, IrType type, boolean isAssignable, IrDeclarationOrigin origin) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(origin, "origin");
        IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
        irValueParameterBuilder.setOrigin(origin);
        Name nameIdentifier = Name.identifier(name2);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irValueParameterBuilder.setName(nameIdentifier);
        irValueParameterBuilder.setIndex(index);
        irValueParameterBuilder.setType(type);
        irValueParameterBuilder.setAssignable(isAssignable);
        IrFunction irFunction = parent;
        return DeclarationBuildersKt.buildValueParameter(irFunction.getFactory(), irValueParameterBuilder, irFunction);
    }
}
