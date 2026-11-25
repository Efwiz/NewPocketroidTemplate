package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.method.ParameterDescription;
import org.catrobat.catroid.common.Constants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.CompilationExceptionKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrElementsKt;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrBreak;
import org.jetbrains.kotlin.ir.expressions.IrBreakContinue;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrCallableReference;
import org.jetbrains.kotlin.ir.expressions.IrCatch;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrComposite;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstantArray;
import org.jetbrains.kotlin.ir.expressions.IrConstantObject;
import org.jetbrains.kotlin.ir.expressions.IrConstantPrimitive;
import org.jetbrains.kotlin.ir.expressions.IrConstantValue;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrContinue;
import org.jetbrains.kotlin.ir.expressions.IrDeclarationReference;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDoWhileLoop;
import org.jetbrains.kotlin.ir.expressions.IrDynamicExpression;
import org.jetbrains.kotlin.ir.expressions.IrDynamicMemberExpression;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperator;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperatorExpression;
import org.jetbrains.kotlin.ir.expressions.IrElseBranch;
import org.jetbrains.kotlin.ir.expressions.IrEnumConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrErrorCallExpression;
import org.jetbrains.kotlin.ir.expressions.IrErrorExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrFieldAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrGetClass;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;
import org.jetbrains.kotlin.ir.expressions.IrGetSingletonValue;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrInstanceInitializerCall;
import org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrRawFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.IrSpreadElement;
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.expressions.IrSuspendableExpression;
import org.jetbrains.kotlin.ir.expressions.IrSuspensionPoint;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBody;
import org.jetbrains.kotlin.ir.expressions.IrThrow;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrBranchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCatchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDoWhileLoopImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrElseBranchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetClassImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetObjectValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSpreadElementImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrStringConcatenationImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrThrowImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTryImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrVarargImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrWhenImpl;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.TransformKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: BlockDecomposerLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000¢\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001:\u0003>?@B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0016\u0010!\u001a\b\u0012\u0004\u0012\u00020#0\"2\u0006\u0010$\u001a\u00020#H\u0002J\b\u0010%\u001a\u00020&H\u0002J\u001c\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020\u001c2\n\b\u0002\u0010*\u001a\u0004\u0018\u00010\u0006H\u0002J$\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020,2\u0012\u0010.\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020#0/H\u0002J\u0016\u00100\u001a\u0002012\f\u00102\u001a\b\u0012\u0004\u0012\u00020#03H\u0002J\u0010\u00104\u001a\u0002052\u0006\u00106\u001a\u000205H\u0016J\u0010\u00107\u001a\u00020#2\u0006\u00108\u001a\u000209H\u0016J\u0010\u0010:\u001a\u00020#2\u0006\u00108\u001a\u00020;H\u0016J\u0014\u0010<\u001a\u00020\u0006*\u00020#2\u0006\u0010=\u001a\u00020\u0006H\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0012\u001a\u00060\u0013R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082.¢\u0006\u0002\n\u0000R\u0012\u0010\u0016\u001a\u00060\u0017R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001d\u001a\u00020\u001e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006A"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/BlockDecomposerTransformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "unreachableExpression", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;Lkotlin/jvm/functions/Function0;)V", "booleanNotSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "boxIntrinsic", "constFalse", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstImpl;", "", "getConstFalse", "()Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstImpl;", "constTrue", "getConstTrue", "expressionTransformer", "Lorg/jetbrains/kotlin/ir/backend/js/lower/BlockDecomposerTransformer$ExpressionTransformer;", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "statementTransformer", "Lorg/jetbrains/kotlin/ir/backend/js/lower/BlockDecomposerTransformer$StatementTransformer;", "tmpVarCounter", "", "unboxIntrinsic", "unitType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "unitValue", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrGetObjectValueImpl;", "getUnitValue", "()Lorg/jetbrains/kotlin/ir/expressions/impl/IrGetObjectValueImpl;", "destructureComposite", "", "Lorg/jetbrains/kotlin/ir/IrStatement;", "expression", "makeLoopLabel", "", "makeTempVar", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", ModuleXmlParser.TYPE, ConfigConstants.CONFIG_INIT_SECTION, "materializeLastExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrComposite;", "composite", "block", "Lkotlin/Function1;", "processStatements", "", "statements", "", "visitElement", "Lorg/jetbrains/kotlin/ir/IrElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "visitFunction", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "visitScript", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "asExpression", "last", "BreakContinueUpdater", "ExpressionTransformer", "StatementTransformer", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BlockDecomposerTransformer extends IrElementTransformerVoid {
    private final IrSimpleFunctionSymbol booleanNotSymbol;
    private final IrSimpleFunctionSymbol boxIntrinsic;
    private final JsCommonBackendContext context;
    private final ExpressionTransformer expressionTransformer;
    private IrDeclarationParent function;
    private final StatementTransformer statementTransformer;
    private int tmpVarCounter;
    private final IrSimpleFunctionSymbol unboxIntrinsic;
    private final IrType unitType;
    private final Function0<IrExpression> unreachableExpression;

    /* JADX WARN: Multi-variable type inference failed */
    public BlockDecomposerTransformer(JsCommonBackendContext context, Function0<? extends IrExpression> unreachableExpression) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(unreachableExpression, "unreachableExpression");
        this.context = context;
        this.unreachableExpression = unreachableExpression;
        this.statementTransformer = new StatementTransformer();
        this.expressionTransformer = new ExpressionTransformer();
        this.unitType = context.getIrBuiltIns().getUnitType();
        this.booleanNotSymbol = context.getIrBuiltIns().getBooleanNotSymbol();
        this.boxIntrinsic = context.getInlineClassesUtils().getBoxIntrinsic();
        this.unboxIntrinsic = context.getInlineClassesUtils().getUnboxIntrinsic();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrConstImpl<Boolean> getConstTrue() {
        return JsIrBuilder.INSTANCE.buildBoolean(this.context.getIrBuiltIns().getBooleanType(), true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrConstImpl<Boolean> getConstFalse() {
        return JsIrBuilder.INSTANCE.buildBoolean(this.context.getIrBuiltIns().getBooleanType(), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrGetObjectValueImpl getUnitValue() {
        return JsIrBuilder.INSTANCE.buildGetObjectValue(this.unitType, this.context.getIrBuiltIns().getUnitClass());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitScript(IrScript declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        this.function = declaration;
        ArrayList arrayList = new ArrayList();
        for (IrStatement irStatement : declaration.getStatements()) {
            IrStatement irStatementTransformStatement = IrElementsKt.transformStatement(irStatement, irStatement == CollectionsKt.last((List) declaration.getStatements()) ? this.expressionTransformer : this.statementTransformer);
            if (irStatementTransformStatement instanceof IrComposite) {
                arrayList.addAll(((IrComposite) irStatementTransformStatement).getStatements());
            } else {
                arrayList.add(irStatementTransformStatement);
            }
        }
        declaration.getStatements().clear();
        declaration.getStatements().addAll(arrayList);
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitFunction(IrFunction declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        this.function = declaration;
        this.tmpVarCounter = 0;
        return IrElementsKt.transformStatement(declaration, this.statementTransformer);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrElement visitElement(IrElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        return element.transform(this.statementTransformer, null);
    }

    static /* synthetic */ IrVariable makeTempVar$default(BlockDecomposerTransformer blockDecomposerTransformer, IrType irType, IrExpression irExpression, int i, Object obj) {
        if ((i & 2) != 0) {
            irExpression = null;
        }
        return blockDecomposerTransformer.makeTempVar(irType, irExpression);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrVariable makeTempVar(IrType type, IrExpression init) {
        JsIrBuilder jsIrBuilder = JsIrBuilder.INSTANCE;
        IrDeclarationParent irDeclarationParent = this.function;
        if (irDeclarationParent == null) {
            Intrinsics.throwUninitializedPropertyAccessException("function");
            irDeclarationParent = null;
        }
        return jsIrBuilder.buildVar(type, irDeclarationParent, (180 & 4) != 0 ? Constants.TMP_DIRECTORY_NAME : null, (180 & 8) != 0 ? false : true, (180 & 16) != 0 ? false : false, (180 & 32) != 0 ? false : false, (180 & 64) != 0 ? null : init, (180 & 128) != 0 ? JsIrBuilder.SYNTHESIZED_DECLARATION.INSTANCE : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String makeLoopLabel() {
        StringBuilder sb = new StringBuilder("$l$");
        int i = this.tmpVarCounter;
        this.tmpVarCounter = i + 1;
        return sb.append(i).toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression asExpression(IrStatement irStatement, IrExpression irExpression) {
        IrCompositeImpl irCompositeImplBuildComposite$default = JsIrBuilder.buildComposite$default(JsIrBuilder.INSTANCE, irExpression.getType(), null, 2, null);
        irCompositeImplBuildComposite$default.getStatements().add(IrElementsKt.transformStatement(irStatement, this.statementTransformer));
        irCompositeImplBuildComposite$default.getStatements().add(irExpression);
        return irCompositeImplBuildComposite$default;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrComposite materializeLastExpression(IrComposite composite, Function1<? super IrExpression, ? extends IrStatement> block) {
        List<IrStatement> statements = composite.getStatements();
        Object objLastOrNull = CollectionsKt.lastOrNull((List<? extends Object>) statements);
        IrExpression irExpression = objLastOrNull instanceof IrExpression ? (IrExpression) objLastOrNull : null;
        if (irExpression == null) {
            return composite;
        }
        statements.set(CollectionsKt.getLastIndex(statements), block.invoke(irExpression));
        return composite;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<IrStatement> destructureComposite(IrStatement expression) {
        List<IrStatement> statements;
        IrComposite irComposite = expression instanceof IrComposite ? (IrComposite) expression : null;
        return (irComposite == null || (statements = irComposite.getStatements()) == null) ? CollectionsKt.listOf(expression) : statements;
    }

    /* JADX INFO: compiled from: BlockDecomposerLowering.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002¢\u0006\u0002\u0010\u0005J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u0002H\u0016J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\u000e2\u0006\u0010\f\u001a\u00020\u0002H\u0016R\u0011\u0010\u0003\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/BlockDecomposerTransformer$BreakContinueUpdater;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "breakLoop", "continueLoop", "(Lorg/jetbrains/kotlin/ir/backend/js/lower/BlockDecomposerTransformer;Lorg/jetbrains/kotlin/ir/expressions/IrLoop;Lorg/jetbrains/kotlin/ir/expressions/IrLoop;)V", "getBreakLoop", "()Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "getContinueLoop", "visitBreak", "Lorg/jetbrains/kotlin/ir/expressions/IrBreak;", "jump", "data", "visitContinue", "Lorg/jetbrains/kotlin/ir/expressions/IrContinue;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class BreakContinueUpdater implements IrElementTransformer<IrLoop> {
        private final IrLoop breakLoop;
        private final IrLoop continueLoop;
        final /* synthetic */ BlockDecomposerTransformer this$0;

        public BreakContinueUpdater(BlockDecomposerTransformer blockDecomposerTransformer, IrLoop breakLoop, IrLoop continueLoop) {
            Intrinsics.checkNotNullParameter(breakLoop, "breakLoop");
            Intrinsics.checkNotNullParameter(continueLoop, "continueLoop");
            this.this$0 = blockDecomposerTransformer;
            this.breakLoop = breakLoop;
            this.continueLoop = continueLoop;
        }

        public final IrLoop getBreakLoop() {
            return this.breakLoop;
        }

        public final IrLoop getContinueLoop() {
            return this.continueLoop;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitAnonymousInitializer, reason: merged with bridge method [inline-methods] */
        public IrStatement visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBlock, reason: merged with bridge method [inline-methods] */
        public IrExpression visitBlock2(IrBlock irBlock, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitBlock(this, irBlock, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBlockBody, reason: merged with bridge method [inline-methods] */
        public IrBody visitBlockBody2(IrBlockBody irBlockBody, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitBlockBody(this, irBlockBody, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBody, reason: merged with bridge method [inline-methods] */
        public IrBody visitBody2(IrBody irBody, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitBody(this, irBody, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBranch, reason: merged with bridge method [inline-methods] */
        public IrBranch visitBranch2(IrBranch irBranch, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitBranch(this, irBranch, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBreakContinue, reason: merged with bridge method [inline-methods] */
        public IrExpression visitBreakContinue2(IrBreakContinue irBreakContinue, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitBreakContinue(this, irBreakContinue, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitCall, reason: merged with bridge method [inline-methods] */
        public IrElement visitCall2(IrCall irCall, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitCall(this, irCall, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ IrElement visitCallableReference(IrCallableReference irCallableReference, Object obj) {
            return visitCallableReference((IrCallableReference<?>) irCallableReference, (IrLoop) obj);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
        public /* bridge */ /* synthetic */ IrElement visitCallableReference2(IrCallableReference irCallableReference, Object obj) {
            return visitCallableReference((IrCallableReference<?>) irCallableReference, (IrLoop) obj);
        }

        public IrElement visitCallableReference(IrCallableReference<?> irCallableReference, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitCallableReference(this, irCallableReference, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitCatch, reason: merged with bridge method [inline-methods] */
        public IrCatch visitCatch2(IrCatch irCatch, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitCatch(this, irCatch, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitClass, reason: merged with bridge method [inline-methods] */
        public IrStatement visitClass2(IrClass irClass, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitClass(this, irClass, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitClassReference, reason: merged with bridge method [inline-methods] */
        public IrExpression visitClassReference2(IrClassReference irClassReference, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitClassReference(this, irClassReference, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitComposite, reason: merged with bridge method [inline-methods] */
        public IrExpression visitComposite2(IrComposite irComposite, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitComposite(this, irComposite, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ IrElement visitConst(IrConst irConst, Object obj) {
            return visitConst((IrConst<?>) irConst, (IrLoop) obj);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
        public /* bridge */ /* synthetic */ IrElement visitConst2(IrConst irConst, Object obj) {
            return visitConst((IrConst<?>) irConst, (IrLoop) obj);
        }

        public IrExpression visitConst(IrConst<?> irConst, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitConst(this, irConst, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantArray, reason: merged with bridge method [inline-methods] */
        public IrConstantValue visitConstantArray2(IrConstantArray irConstantArray, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitConstantArray(this, irConstantArray, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantObject, reason: merged with bridge method [inline-methods] */
        public IrConstantValue visitConstantObject2(IrConstantObject irConstantObject, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitConstantObject(this, irConstantObject, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantPrimitive, reason: merged with bridge method [inline-methods] */
        public IrConstantValue visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantValue, reason: merged with bridge method [inline-methods] */
        public IrConstantValue visitConstantValue2(IrConstantValue irConstantValue, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitConstantValue(this, irConstantValue, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstructor, reason: merged with bridge method [inline-methods] */
        public IrStatement visitConstructor2(IrConstructor irConstructor, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitConstructor(this, irConstructor, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstructorCall, reason: merged with bridge method [inline-methods] */
        public IrElement visitConstructorCall2(IrConstructorCall irConstructorCall, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitConstructorCall(this, irConstructorCall, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitContainerExpression, reason: merged with bridge method [inline-methods] */
        public IrExpression visitContainerExpression2(IrContainerExpression irContainerExpression, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitContainerExpression(this, irContainerExpression, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDeclaration, reason: merged with bridge method [inline-methods] */
        public IrStatement visitDeclaration2(IrDeclarationBase irDeclarationBase, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitDeclaration(this, irDeclarationBase, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDeclarationReference, reason: merged with bridge method [inline-methods] */
        public IrExpression visitDeclarationReference2(IrDeclarationReference irDeclarationReference, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: merged with bridge method [inline-methods] */
        public IrElement visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDoWhileLoop, reason: merged with bridge method [inline-methods] */
        public IrExpression visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicExpression, reason: merged with bridge method [inline-methods] */
        public IrExpression visitDynamicExpression2(IrDynamicExpression irDynamicExpression, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: merged with bridge method [inline-methods] */
        public IrExpression visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: merged with bridge method [inline-methods] */
        public IrExpression visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitElement, reason: merged with bridge method [inline-methods] */
        public IrElement visitElement2(IrElement irElement, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitElement(this, irElement, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitElseBranch, reason: merged with bridge method [inline-methods] */
        public IrElseBranch visitElseBranch2(IrElseBranch irElseBranch, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitElseBranch(this, irElseBranch, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitEnumConstructorCall, reason: merged with bridge method [inline-methods] */
        public IrElement visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitEnumEntry, reason: merged with bridge method [inline-methods] */
        public IrStatement visitEnumEntry2(IrEnumEntry irEnumEntry, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitEnumEntry(this, irEnumEntry, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorCallExpression, reason: merged with bridge method [inline-methods] */
        public IrExpression visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorDeclaration, reason: merged with bridge method [inline-methods] */
        public IrStatement visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorExpression, reason: merged with bridge method [inline-methods] */
        public IrExpression visitErrorExpression2(IrErrorExpression irErrorExpression, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitErrorExpression(this, irErrorExpression, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExpression, reason: merged with bridge method [inline-methods] */
        public IrExpression visitExpression2(IrExpression irExpression, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitExpression(this, irExpression, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExpressionBody, reason: merged with bridge method [inline-methods] */
        public IrBody visitExpressionBody2(IrExpressionBody irExpressionBody, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitExpressionBody(this, irExpressionBody, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExternalPackageFragment, reason: merged with bridge method [inline-methods] */
        public IrExternalPackageFragment visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitField, reason: merged with bridge method [inline-methods] */
        public IrStatement visitField2(IrField irField, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitField(this, irField, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFieldAccess, reason: merged with bridge method [inline-methods] */
        public IrExpression visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFile, reason: merged with bridge method [inline-methods] */
        public IrFile visitFile2(IrFile irFile, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitFile(this, irFile, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunction, reason: merged with bridge method [inline-methods] */
        public IrStatement visitFunction2(IrFunction irFunction, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitFunction(this, irFunction, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionAccess, reason: merged with bridge method [inline-methods] */
        public IrElement visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionExpression, reason: merged with bridge method [inline-methods] */
        public IrElement visitFunctionExpression2(IrFunctionExpression irFunctionExpression, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionReference, reason: merged with bridge method [inline-methods] */
        public IrElement visitFunctionReference2(IrFunctionReference irFunctionReference, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitFunctionReference(this, irFunctionReference, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetClass, reason: merged with bridge method [inline-methods] */
        public IrExpression visitGetClass2(IrGetClass irGetClass, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitGetClass(this, irGetClass, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetEnumValue, reason: merged with bridge method [inline-methods] */
        public IrExpression visitGetEnumValue2(IrGetEnumValue irGetEnumValue, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetField, reason: merged with bridge method [inline-methods] */
        public IrExpression visitGetField2(IrGetField irGetField, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitGetField(this, irGetField, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetObjectValue, reason: merged with bridge method [inline-methods] */
        public IrExpression visitGetObjectValue2(IrGetObjectValue irGetObjectValue, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetValue, reason: merged with bridge method [inline-methods] */
        public IrExpression visitGetValue2(IrGetValue irGetValue, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitGetValue(this, irGetValue, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: merged with bridge method [inline-methods] */
        public IrExpression visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: merged with bridge method [inline-methods] */
        public IrStatement visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: merged with bridge method [inline-methods] */
        public IrElement visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLoop, reason: merged with bridge method [inline-methods] */
        public IrExpression visitLoop2(IrLoop irLoop, IrLoop irLoop2) {
            return IrElementTransformer.DefaultImpls.visitLoop(this, irLoop, irLoop2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ IrElement visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
            return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (IrLoop) obj);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
        public /* bridge */ /* synthetic */ IrElement visitMemberAccess2(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
            return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (IrLoop) obj);
        }

        public IrElement visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitModuleFragment, reason: merged with bridge method [inline-methods] */
        public IrModuleFragment visitModuleFragment2(IrModuleFragment irModuleFragment, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitModuleFragment(this, irModuleFragment, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitPackageFragment, reason: merged with bridge method [inline-methods] */
        public IrElement visitPackageFragment2(IrPackageFragment irPackageFragment, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitPackageFragment(this, irPackageFragment, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitProperty, reason: merged with bridge method [inline-methods] */
        public IrStatement visitProperty2(IrProperty irProperty, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitProperty(this, irProperty, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitPropertyReference, reason: merged with bridge method [inline-methods] */
        public IrElement visitPropertyReference2(IrPropertyReference irPropertyReference, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitPropertyReference(this, irPropertyReference, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitRawFunctionReference, reason: merged with bridge method [inline-methods] */
        public IrExpression visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitReturn, reason: merged with bridge method [inline-methods] */
        public IrExpression visitReturn2(IrReturn irReturn, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitReturn(this, irReturn, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitScript, reason: merged with bridge method [inline-methods] */
        public IrStatement visitScript2(IrScript irScript, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitScript(this, irScript, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSetField, reason: merged with bridge method [inline-methods] */
        public IrExpression visitSetField2(IrSetField irSetField, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitSetField(this, irSetField, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSetValue, reason: merged with bridge method [inline-methods] */
        public IrExpression visitSetValue2(IrSetValue irSetValue, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitSetValue(this, irSetValue, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSimpleFunction, reason: merged with bridge method [inline-methods] */
        public IrStatement visitSimpleFunction2(IrSimpleFunction irSimpleFunction, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSingletonReference, reason: merged with bridge method [inline-methods] */
        public IrExpression visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSpreadElement, reason: merged with bridge method [inline-methods] */
        public IrSpreadElement visitSpreadElement2(IrSpreadElement irSpreadElement, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitSpreadElement(this, irSpreadElement, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitStringConcatenation, reason: merged with bridge method [inline-methods] */
        public IrExpression visitStringConcatenation2(IrStringConcatenation irStringConcatenation, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSuspendableExpression, reason: merged with bridge method [inline-methods] */
        public IrExpression visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSuspensionPoint, reason: merged with bridge method [inline-methods] */
        public IrExpression visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSyntheticBody, reason: merged with bridge method [inline-methods] */
        public IrBody visitSyntheticBody2(IrSyntheticBody irSyntheticBody, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitThrow, reason: merged with bridge method [inline-methods] */
        public IrExpression visitThrow2(IrThrow irThrow, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitThrow(this, irThrow, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTry, reason: merged with bridge method [inline-methods] */
        public IrExpression visitTry2(IrTry irTry, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitTry(this, irTry, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeAlias, reason: merged with bridge method [inline-methods] */
        public IrStatement visitTypeAlias2(IrTypeAlias irTypeAlias, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitTypeAlias(this, irTypeAlias, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeOperator, reason: merged with bridge method [inline-methods] */
        public IrExpression visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeParameter, reason: merged with bridge method [inline-methods] */
        public IrStatement visitTypeParameter2(IrTypeParameter irTypeParameter, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitTypeParameter(this, irTypeParameter, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitValueAccess, reason: merged with bridge method [inline-methods] */
        public IrExpression visitValueAccess2(IrValueAccessExpression irValueAccessExpression, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitValueAccess(this, irValueAccessExpression, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitValueParameter, reason: merged with bridge method [inline-methods] */
        public IrStatement visitValueParameter2(IrValueParameter irValueParameter, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitValueParameter(this, irValueParameter, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitVararg, reason: merged with bridge method [inline-methods] */
        public IrExpression visitVararg2(IrVararg irVararg, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitVararg(this, irVararg, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitVariable, reason: merged with bridge method [inline-methods] */
        public IrStatement visitVariable2(IrVariable irVariable, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitVariable(this, irVariable, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitWhen, reason: merged with bridge method [inline-methods] */
        public IrExpression visitWhen2(IrWhen irWhen, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitWhen(this, irWhen, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitWhileLoop, reason: merged with bridge method [inline-methods] */
        public IrExpression visitWhileLoop2(IrWhileLoop irWhileLoop, IrLoop irLoop) {
            return IrElementTransformer.DefaultImpls.visitWhileLoop(this, irWhileLoop, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBreak, reason: merged with bridge method [inline-methods] */
        public IrBreak visitBreak2(IrBreak jump, IrLoop data) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            Intrinsics.checkNotNullParameter(data, "data");
            if (Intrinsics.areEqual(jump.getLoop(), data)) {
                jump.setLoop(this.breakLoop);
            }
            return jump;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitContinue, reason: merged with bridge method [inline-methods] */
        public IrContinue visitContinue2(IrContinue jump, IrLoop data) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            Intrinsics.checkNotNullParameter(data, "data");
            if (Intrinsics.areEqual(jump.getLoop(), data)) {
                jump.setLoop(this.continueLoop);
            }
            return jump;
        }
    }

    /* JADX INFO: compiled from: BlockDecomposerLowering.kt */
    @Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0016J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H\u0016J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000b\u001a\u00020\rH\u0016J\u0010\u0010\u0011\u001a\u00020\r2\u0006\u0010\u000b\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\r2\u0006\u0010\u000b\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\r2\u0006\u0010\u000b\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\r2\u0006\u0010\u000b\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001aH\u0016J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0010\u0010 \u001a\u00020\r2\u0006\u0010\u000b\u001a\u00020!H\u0016J\u0010\u0010\"\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020#H\u0016¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/BlockDecomposerTransformer$StatementTransformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "(Lorg/jetbrains/kotlin/ir/backend/js/lower/BlockDecomposerTransformer;)V", "visitBlockBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "body", "visitBreakContinue", "Lorg/jetbrains/kotlin/ir/expressions/IrBreakContinue;", "jump", "visitContainerExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "expression", "visitDoWhileLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrDoWhileLoop;", "visitExpression", "visitReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "visitSetField", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "visitSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "visitThrow", "Lorg/jetbrains/kotlin/ir/expressions/IrThrow;", "visitTry", "Lorg/jetbrains/kotlin/ir/expressions/IrTry;", "aTry", "visitVariable", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "visitWhen", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "visitWhileLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrWhileLoop;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class StatementTransformer extends IrElementTransformerVoid {
        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrBreakContinue visitBreakContinue(IrBreakContinue jump) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            return jump;
        }

        public StatementTransformer() {
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrBlockBody visitBlockBody(IrBlockBody body) {
            Intrinsics.checkNotNullParameter(body, "body");
            BlockDecomposerTransformer.this.processStatements(body.getStatements());
            return body;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrContainerExpression visitContainerExpression(IrContainerExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            BlockDecomposerTransformer.this.processStatements(expression.getStatements());
            return expression;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitExpression(IrExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return expression.transform((IrElementTransformer<? super Object>) BlockDecomposerTransformer.this.expressionTransformer, (Object) null);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitReturn(final IrReturn expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrElementTransformerVoidKt.transformChildrenVoid(expression, BlockDecomposerTransformer.this.expressionTransformer);
            IrExpression value = expression.getValue();
            IrComposite irComposite = value instanceof IrComposite ? (IrComposite) value : null;
            return irComposite == null ? expression : BlockDecomposerTransformer.this.materializeLastExpression(irComposite, new Function1<IrExpression, IrStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.BlockDecomposerTransformer$StatementTransformer$visitReturn$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrStatement invoke(IrExpression it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return new IrReturnImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), expression.getReturnTargetSymbol(), it);
                }
            });
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitThrow(final IrThrow expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrElementTransformerVoidKt.transformChildrenVoid(expression, BlockDecomposerTransformer.this.expressionTransformer);
            IrExpression value = expression.getValue();
            IrComposite irComposite = value instanceof IrComposite ? (IrComposite) value : null;
            return irComposite == null ? expression : BlockDecomposerTransformer.this.materializeLastExpression(irComposite, new Function1<IrExpression, IrStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.BlockDecomposerTransformer$StatementTransformer$visitThrow$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrStatement invoke(IrExpression it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return new IrThrowImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), it);
                }
            });
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrStatement visitVariable(final IrVariable declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrElementTransformerVoidKt.transformChildrenVoid(declaration, BlockDecomposerTransformer.this.expressionTransformer);
            IrExpression initializer = declaration.getInitializer();
            IrComposite irComposite = initializer instanceof IrComposite ? (IrComposite) initializer : null;
            return irComposite == null ? declaration : BlockDecomposerTransformer.this.materializeLastExpression(irComposite, new Function1<IrExpression, IrStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.BlockDecomposerTransformer$StatementTransformer$visitVariable$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrStatement invoke(IrExpression it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    IrVariable irVariable = declaration;
                    irVariable.setInitializer(it);
                    return irVariable;
                }
            });
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitSetField(final IrSetField expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrElementTransformerVoidKt.transformChildrenVoid(expression, BlockDecomposerTransformer.this.expressionTransformer);
            IrExpression receiver = expression.getReceiver();
            final IrGetValueImpl irGetValueImplBuildGetValue$default = null;
            IrComposite irComposite = receiver instanceof IrComposite ? (IrComposite) receiver : null;
            IrExpression value = expression.getValue();
            IrComposite irComposite2 = value instanceof IrComposite ? (IrComposite) value : null;
            if (irComposite == null && irComposite2 == null) {
                return expression;
            }
            if (irComposite == null || irComposite2 == null) {
                if (irComposite != null) {
                    return BlockDecomposerTransformer.this.materializeLastExpression(irComposite, new Function1<IrExpression, IrStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.BlockDecomposerTransformer$StatementTransformer$visitSetField$4
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final IrStatement invoke(IrExpression it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            IrSetField irSetField = expression;
                            return new IrSetFieldImpl(irSetField.getStartOffset(), irSetField.getEndOffset(), irSetField.getSymbol(), it, irSetField.getValue(), irSetField.getType(), irSetField.getOrigin(), irSetField.getSuperQualifierSymbol());
                        }
                    });
                }
                IrExpression receiver2 = expression.getReceiver();
                if (receiver2 != null) {
                    IrVariable irVariableMakeTempVar = BlockDecomposerTransformer.this.makeTempVar(receiver2.getType(), receiver2);
                    Intrinsics.checkNotNull(irComposite2);
                    irComposite2.getStatements().add(0, irVariableMakeTempVar);
                    irGetValueImplBuildGetValue$default = JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, irVariableMakeTempVar.getSymbol(), null, 2, null);
                }
                BlockDecomposerTransformer blockDecomposerTransformer = BlockDecomposerTransformer.this;
                Intrinsics.checkNotNull(irComposite2);
                return blockDecomposerTransformer.materializeLastExpression(irComposite2, new Function1<IrExpression, IrStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.BlockDecomposerTransformer$StatementTransformer$visitSetField$5
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final IrStatement invoke(IrExpression it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        IrSetField irSetField = expression;
                        return new IrSetFieldImpl(irSetField.getStartOffset(), irSetField.getEndOffset(), irSetField.getSymbol(), irGetValueImplBuildGetValue$default, it, irSetField.getType(), irSetField.getOrigin(), irSetField.getSuperQualifierSymbol());
                    }
                });
            }
            IrCompositeImpl irCompositeImpl = new IrCompositeImpl(irComposite.getStartOffset(), expression.getEndOffset(), BlockDecomposerTransformer.this.unitType, null, 8, null);
            Object objLast = CollectionsKt.last((List<? extends Object>) irComposite.getStatements());
            Intrinsics.checkNotNull(objLast, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
            IrExpression irExpression = (IrExpression) objLast;
            IrVariable irVariableMakeTempVar2 = BlockDecomposerTransformer.this.makeTempVar(irExpression.getType(), irExpression);
            Object objLast2 = CollectionsKt.last((List<? extends Object>) irComposite2.getStatements());
            Intrinsics.checkNotNull(objLast2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
            List<IrStatement> statements = irCompositeImpl.getStatements();
            List<IrStatement> statements2 = irComposite.getStatements();
            CollectionsKt.addAll(statements, statements2.subList(0, CollectionsKt.getLastIndex(statements2)));
            irCompositeImpl.getStatements().add(irVariableMakeTempVar2);
            List<IrStatement> statements3 = irCompositeImpl.getStatements();
            List<IrStatement> statements4 = irComposite2.getStatements();
            CollectionsKt.addAll(statements3, statements4.subList(0, CollectionsKt.getLastIndex(statements4)));
            irCompositeImpl.getStatements().add(new IrSetFieldImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getSymbol(), JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, irVariableMakeTempVar2.getSymbol(), null, 2, null), (IrExpression) objLast2, expression.getType(), expression.getOrigin(), expression.getSuperQualifierSymbol()));
            return irCompositeImpl;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitSetValue(final IrSetValue expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrElementTransformerVoidKt.transformChildrenVoid(expression, BlockDecomposerTransformer.this.expressionTransformer);
            IrExpression value = expression.getValue();
            IrComposite irComposite = value instanceof IrComposite ? (IrComposite) value : null;
            return irComposite == null ? expression : BlockDecomposerTransformer.this.materializeLastExpression(irComposite, new Function1<IrExpression, IrStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.BlockDecomposerTransformer$StatementTransformer$visitSetValue$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrStatement invoke(IrExpression it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    IrSetValue irSetValue = expression;
                    return new IrSetValueImpl(irSetValue.getStartOffset(), irSetValue.getEndOffset(), irSetValue.getType(), irSetValue.getSymbol(), it, irSetValue.getOrigin());
                }
            });
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitWhileLoop(IrWhileLoop loop) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            IrExpression body = loop.getBody();
            IrExpression irExpressionTransform = body != null ? body.transform((IrElementTransformer<? super Object>) BlockDecomposerTransformer.this.statementTransformer, (Object) null) : null;
            IrExpression irExpressionTransform2 = loop.getCondition().transform((IrElementTransformer<? super Object>) BlockDecomposerTransformer.this.expressionTransformer, (Object) null);
            if (irExpressionTransform2 instanceof IrComposite) {
                IrBlockImpl irBlockImpl = new IrBlockImpl(loop.getStartOffset(), loop.getEndOffset(), loop.getType(), loop.getOrigin());
                List<IrStatement> statements = irBlockImpl.getStatements();
                IrComposite irComposite = (IrComposite) irExpressionTransform2;
                List<IrStatement> statements2 = irComposite.getStatements();
                CollectionsKt.addAll(statements, statements2.subList(0, CollectionsKt.getLastIndex(statements2)));
                IrBlockImpl irBlockImplBuildBlock = JsIrBuilder.INSTANCE.buildBlock(BlockDecomposerTransformer.this.unitType, CollectionsKt.listOf(JsIrBuilder.INSTANCE.buildBreak(BlockDecomposerTransformer.this.unitType, loop)));
                Object objLast = CollectionsKt.last((List<? extends Object>) irComposite.getStatements());
                Intrinsics.checkNotNull(objLast, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, BlockDecomposerTransformer.this.booleanNotSymbol, null, null, null, null, 30, null);
                irCallBuildCall$default.setDispatchReceiver((IrExpression) objLast);
                irBlockImpl.getStatements().add(JsIrBuilder.buildIfElse$default(JsIrBuilder.INSTANCE, BlockDecomposerTransformer.this.unitType, irCallBuildCall$default, irBlockImplBuildBlock, null, 0, 0, 0, 0, 248, null));
                org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(irBlockImpl.getStatements(), irExpressionTransform);
                loop.setCondition(BlockDecomposerTransformer.this.getConstTrue());
                loop.setBody(irBlockImpl);
                return loop;
            }
            loop.setBody(irExpressionTransform);
            loop.setCondition(irExpressionTransform2);
            return loop;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitDoWhileLoop(IrDoWhileLoop loop) {
            IrType type;
            Intrinsics.checkNotNullParameter(loop, "loop");
            IrExpression body = loop.getBody();
            IrExpression irExpressionTransform = body != null ? body.transform((IrElementTransformer<? super Object>) BlockDecomposerTransformer.this.statementTransformer, (Object) null) : null;
            IrExpression irExpressionTransform2 = loop.getCondition().transform((IrElementTransformer<? super Object>) BlockDecomposerTransformer.this.expressionTransformer, (Object) null);
            if (irExpressionTransform2 instanceof IrComposite) {
                IrDoWhileLoopImpl irDoWhileLoopImpl = new IrDoWhileLoopImpl(loop.getStartOffset(), loop.getEndOffset(), BlockDecomposerTransformer.this.unitType, loop.getOrigin());
                BlockDecomposerTransformer blockDecomposerTransformer = BlockDecomposerTransformer.this;
                irDoWhileLoopImpl.setCondition(blockDecomposerTransformer.getConstFalse());
                irDoWhileLoopImpl.setBody(irExpressionTransform);
                irDoWhileLoopImpl.setLabel(blockDecomposerTransformer.makeLoopLabel());
                IrComposite irComposite = (IrComposite) irExpressionTransform2;
                Object objLast = CollectionsKt.last((List<? extends Object>) irComposite.getStatements());
                Intrinsics.checkNotNull(objLast, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                IrExpression irExpression = (IrExpression) objLast;
                int startOffset = irExpressionTransform != null ? irExpressionTransform.getStartOffset() : irExpressionTransform2.getStartOffset();
                int endOffset = irExpressionTransform != null ? irExpressionTransform.getEndOffset() : irExpressionTransform2.getEndOffset();
                if (irExpressionTransform == null || (type = irExpressionTransform.getType()) == null) {
                    type = BlockDecomposerTransformer.this.unitType;
                }
                IrBlockImpl irBlockImpl = new IrBlockImpl(startOffset, endOffset, type, null, 8, null);
                irBlockImpl.getStatements().add(irDoWhileLoopImpl);
                List<IrStatement> statements = irBlockImpl.getStatements();
                List<IrStatement> statements2 = irComposite.getStatements();
                CollectionsKt.addAll(statements, statements2.subList(0, CollectionsKt.getLastIndex(statements2)));
                IrDoWhileLoopImpl irDoWhileLoopImpl2 = new IrDoWhileLoopImpl(loop.getStartOffset(), loop.getEndOffset(), BlockDecomposerTransformer.this.unitType, loop.getOrigin());
                BlockDecomposerTransformer blockDecomposerTransformer2 = BlockDecomposerTransformer.this;
                irDoWhileLoopImpl2.setCondition(irExpression);
                irDoWhileLoopImpl2.setBody(irBlockImpl.transform((IrElementTransformer) new BreakContinueUpdater(blockDecomposerTransformer2, irDoWhileLoopImpl2, irDoWhileLoopImpl), (Object) loop));
                String label = loop.getLabel();
                if (label == null) {
                    label = blockDecomposerTransformer2.makeLoopLabel();
                }
                irDoWhileLoopImpl2.setLabel(label);
                return irDoWhileLoopImpl2;
            }
            loop.setBody(irExpressionTransform);
            loop.setCondition(irExpressionTransform2);
            return loop;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitWhen(IrWhen expression) {
            List<IrStatement> listListOf;
            Intrinsics.checkNotNullParameter(expression, "expression");
            List<IrBranch> branches = expression.getBranches();
            BlockDecomposerTransformer blockDecomposerTransformer = BlockDecomposerTransformer.this;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(branches, 10));
            int i = 0;
            for (IrBranch irBranch : branches) {
                IrExpression irExpressionTransform = irBranch.getCondition().transform((IrElementTransformer<? super Object>) blockDecomposerTransformer.expressionTransformer, (Object) null);
                IrExpression irExpressionTransform2 = irBranch.getResult().transform((IrElementTransformer<? super Object>) blockDecomposerTransformer.statementTransformer, (Object) null);
                if (irExpressionTransform instanceof IrComposite) {
                    i++;
                }
                arrayList.add(new Triple(irExpressionTransform, irExpressionTransform2, irBranch));
            }
            ArrayList arrayList2 = arrayList;
            if (i == 0) {
                ArrayList<Triple> arrayList3 = arrayList2;
                ArrayList arrayList4 = new ArrayList(arrayList3.size());
                for (Triple triple : arrayList3) {
                    IrExpression irExpression = (IrExpression) triple.component1();
                    IrExpression irExpression2 = (IrExpression) triple.component2();
                    IrBranch irBranch2 = (IrBranch) triple.component3();
                    arrayList4.add(IrUtilsKt.isElseBranch(irBranch2) ? new IrElseBranchImpl(irBranch2.getStartOffset(), irBranch2.getEndOffset(), irExpression, irExpression2) : new IrBranchImpl(irBranch2.getStartOffset(), irBranch2.getEndOffset(), irExpression, irExpression2));
                }
                return new IrWhenImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), expression.getOrigin(), org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList4));
            }
            IrBlockImpl irBlockImpl = new IrBlockImpl(expression.getStartOffset(), expression.getEndOffset(), BlockDecomposerTransformer.this.unitType, expression.getOrigin());
            BlockDecomposerTransformer blockDecomposerTransformer2 = BlockDecomposerTransformer.this;
            IrBlockImpl irBlockImpl2 = irBlockImpl;
            int i2 = 0;
            for (Object obj : arrayList2) {
                int i3 = i2 + 1;
                if (i2 < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                Triple triple2 = (Triple) obj;
                IrExpression irExpression3 = (IrExpression) triple2.component1();
                IrExpression irExpression4 = (IrExpression) triple2.component2();
                IrBranch irBranch3 = (IrBranch) triple2.component3();
                List listDestructureComposite = blockDecomposerTransformer2.destructureComposite(irExpression3);
                Object objLast = CollectionsKt.last((List<? extends Object>) listDestructureComposite);
                Intrinsics.checkNotNull(objLast, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                IrExpression irExpression5 = (IrExpression) objLast;
                CollectionsKt.addAll(irBlockImpl2.getStatements(), listDestructureComposite.subList(0, CollectionsKt.getLastIndex(listDestructureComposite)));
                IrBlockImpl irBlockImplBuildBlock = JsIrBuilder.INSTANCE.buildBlock(blockDecomposerTransformer2.unitType);
                IrBlockImpl irBlockImpl3 = CollectionsKt.getLastIndex(arrayList2) != i2 ? irBlockImplBuildBlock : null;
                if (IrUtilsKt.isElseBranch(irBranch3)) {
                    IrBlock irBlock = irExpression4 instanceof IrBlock ? (IrBlock) irExpression4 : null;
                    if (irBlock == null || (listListOf = irBlock.getStatements()) == null) {
                        listListOf = CollectionsKt.listOf(irExpression4);
                    }
                } else {
                    listListOf = CollectionsKt.listOf(JsIrBuilder.INSTANCE.buildIfElse(-1, -1, blockDecomposerTransformer2.unitType, irExpression5, irExpression4, irBlockImpl3, expression.getOrigin(), irBranch3.getStartOffset(), irBranch3.getEndOffset(), -1, -1));
                }
                CollectionsKt.addAll(irBlockImpl2.getStatements(), listListOf);
                i2 = i3;
                irBlockImpl2 = irBlockImplBuildBlock;
            }
            return irBlockImpl;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrTry visitTry(IrTry aTry) {
            Intrinsics.checkNotNullParameter(aTry, "aTry");
            IrElementTransformerVoidKt.transformChildrenVoid(aTry, this);
            return aTry;
        }
    }

    /* JADX INFO: compiled from: BlockDecomposerLowering.kt */
    @Metadata(d1 = {"\u0000¸\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00042\u0006\u0010\u0007\u001a\u00020\u0005H\u0002J@\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\t2\u000e\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u001aH\u0016J\u0010\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0016J\u0010\u0010\u001e\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u001fH\u0016J\u0010\u0010 \u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020!H\u0016J\u0010\u0010\"\u001a\u00020\u00052\u0006\u0010#\u001a\u00020$H\u0016J\u0014\u0010%\u001a\u00020\u00052\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030&H\u0016J\u0010\u0010'\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020(H\u0016J\u0010\u0010)\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020*H\u0016J\u0010\u0010+\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020,H\u0016J\u0010\u0010-\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020.H\u0016J\u0010\u0010/\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u000200H\u0016J\u0010\u00101\u001a\u00020\u00052\u0006\u00102\u001a\u000203H\u0016J\u0010\u00104\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u000205H\u0016J\u0010\u00106\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u000207H\u0016J\u0010\u00108\u001a\u00020\u00052\u0006\u00109\u001a\u00020\u0006H\u0016J\u0010\u0010:\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020;H\u0016J\u0010\u0010<\u001a\u00020=2\u0006\u0010\u0017\u001a\u00020\u0005H\u0002J\u0018\u0010<\u001a\u00020=2\u0006\u0010\u0017\u001a\u00020\u00052\u0006\u0010>\u001a\u00020\u0006H\u0002J\f\u0010?\u001a\u00020\u0012*\u00020\u001cH\u0002¨\u0006@"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/BlockDecomposerTransformer$ExpressionTransformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "(Lorg/jetbrains/kotlin/ir/backend/js/lower/BlockDecomposerTransformer;)V", "mapArgument", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", ParameterDescription.NAME_PREFIX, "mapArguments", "", "oldArguments", "", "compositeCount", "", "newStatements", "", "Lorg/jetbrains/kotlin/ir/IrStatement;", "dontDetachFirstArgument", "", "visitBreakContinue", "jump", "Lorg/jetbrains/kotlin/ir/expressions/IrBreakContinue;", "visitContainerExpression", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "visitDynamicMemberExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicMemberExpression;", "visitDynamicOperatorExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperatorExpression;", "visitExpression", "visitGetClass", "Lorg/jetbrains/kotlin/ir/expressions/IrGetClass;", "visitGetField", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "visitLoop", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "visitMemberAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "visitReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "visitSetField", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "visitSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "visitStringConcatenation", "Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;", "visitThrow", "Lorg/jetbrains/kotlin/ir/expressions/IrThrow;", "visitTry", "aTry", "Lorg/jetbrains/kotlin/ir/expressions/IrTry;", "visitTypeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "visitVararg", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "visitVariable", "declaration", "visitWhen", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "wrap", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "variable", "isReceiverNonDetachable", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class ExpressionTransformer extends IrElementTransformerVoid {
        public ExpressionTransformer() {
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitExpression(IrExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            transformChildrenVoid(expression);
            return expression;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitGetField(final IrGetField expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrElementTransformerVoidKt.transformChildrenVoid(expression, BlockDecomposerTransformer.this.expressionTransformer);
            IrExpression receiver = expression.getReceiver();
            IrComposite irComposite = receiver instanceof IrComposite ? (IrComposite) receiver : null;
            return irComposite == null ? expression : BlockDecomposerTransformer.this.materializeLastExpression(irComposite, new Function1<IrExpression, IrStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.BlockDecomposerTransformer$ExpressionTransformer$visitGetField$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrStatement invoke(IrExpression it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    IrGetField irGetField = expression;
                    return new IrGetFieldImpl(irGetField.getStartOffset(), irGetField.getEndOffset(), irGetField.getSymbol(), irGetField.getType(), it, irGetField.getOrigin(), irGetField.getSuperQualifierSymbol());
                }
            });
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitTypeOperator(final IrTypeOperatorCall expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrElementTransformerVoidKt.transformChildrenVoid(expression, BlockDecomposerTransformer.this.expressionTransformer);
            IrExpression argument = expression.getArgument();
            IrComposite irComposite = argument instanceof IrComposite ? (IrComposite) argument : null;
            return irComposite == null ? expression : BlockDecomposerTransformer.this.materializeLastExpression(irComposite, new Function1<IrExpression, IrStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.BlockDecomposerTransformer$ExpressionTransformer$visitTypeOperator$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrStatement invoke(IrExpression it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    IrTypeOperatorCall irTypeOperatorCall = expression;
                    irTypeOperatorCall.setArgument(it);
                    return irTypeOperatorCall;
                }
            });
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitGetClass(final IrGetClass expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrElementTransformerVoidKt.transformChildrenVoid(expression, BlockDecomposerTransformer.this.expressionTransformer);
            IrExpression argument = expression.getArgument();
            IrComposite irComposite = argument instanceof IrComposite ? (IrComposite) argument : null;
            return irComposite == null ? expression : BlockDecomposerTransformer.this.materializeLastExpression(irComposite, new Function1<IrExpression, IrStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.BlockDecomposerTransformer$ExpressionTransformer$visitGetClass$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrStatement invoke(IrExpression it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    IrGetClass irGetClass = expression;
                    return new IrGetClassImpl(irGetClass.getStartOffset(), irGetClass.getEndOffset(), irGetClass.getType(), it);
                }
            });
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitLoop(IrLoop loop) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            BlockDecomposerTransformer blockDecomposerTransformer = BlockDecomposerTransformer.this;
            return blockDecomposerTransformer.asExpression(loop, blockDecomposerTransformer.getUnitValue());
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitSetValue(IrSetValue expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            BlockDecomposerTransformer blockDecomposerTransformer = BlockDecomposerTransformer.this;
            return blockDecomposerTransformer.asExpression(expression, blockDecomposerTransformer.getUnitValue());
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitSetField(IrSetField expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            BlockDecomposerTransformer blockDecomposerTransformer = BlockDecomposerTransformer.this;
            return blockDecomposerTransformer.asExpression(expression, blockDecomposerTransformer.getUnitValue());
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitBreakContinue(IrBreakContinue jump) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            BlockDecomposerTransformer blockDecomposerTransformer = BlockDecomposerTransformer.this;
            return blockDecomposerTransformer.asExpression(jump, (IrExpression) blockDecomposerTransformer.unreachableExpression.invoke());
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitThrow(IrThrow expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            BlockDecomposerTransformer blockDecomposerTransformer = BlockDecomposerTransformer.this;
            return blockDecomposerTransformer.asExpression(expression, (IrExpression) blockDecomposerTransformer.unreachableExpression.invoke());
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitReturn(IrReturn expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            BlockDecomposerTransformer blockDecomposerTransformer = BlockDecomposerTransformer.this;
            return blockDecomposerTransformer.asExpression(expression, (IrExpression) blockDecomposerTransformer.unreachableExpression.invoke());
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitVariable(IrVariable declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            BlockDecomposerTransformer blockDecomposerTransformer = BlockDecomposerTransformer.this;
            return blockDecomposerTransformer.asExpression(declaration, blockDecomposerTransformer.getUnitValue());
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitStringConcatenation(IrStringConcatenation expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrElementTransformerVoidKt.transformChildrenVoid(expression, BlockDecomposerTransformer.this.expressionTransformer);
            List<IrExpression> arguments = expression.getArguments();
            int i = 0;
            if (!(arguments instanceof Collection) || !arguments.isEmpty()) {
                Iterator<T> it = arguments.iterator();
                while (it.hasNext()) {
                    if ((((IrExpression) it.next()) instanceof IrComposite) && (i = i + 1) < 0) {
                        CollectionsKt.throwCountOverflow();
                    }
                }
            }
            int i2 = i;
            if (i2 == 0) {
                return expression;
            }
            ArrayList arrayList = new ArrayList();
            List listMapArguments$default = mapArguments$default(this, expression.getArguments(), i2, arrayList, false, 8, null);
            ArrayList arrayList2 = arrayList;
            int startOffset = expression.getStartOffset();
            int endOffset = expression.getEndOffset();
            IrType type = expression.getType();
            List<IrExpression> list = listMapArguments$default;
            ArrayList arrayList3 = new ArrayList(list.size());
            for (IrExpression irExpression : list) {
                Intrinsics.checkNotNull(irExpression);
                arrayList3.add(irExpression);
            }
            arrayList2.add(new IrStringConcatenationImpl(startOffset, endOffset, type, org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList3)));
            return JsIrBuilder.INSTANCE.buildComposite(expression.getType(), arrayList);
        }

        static /* synthetic */ List mapArguments$default(ExpressionTransformer expressionTransformer, Collection collection, int i, List list, boolean z, int i2, Object obj) {
            if ((i2 & 8) != 0) {
                z = false;
            }
            return expressionTransformer.mapArguments(collection, i, list, z);
        }

        private final List<IrExpression> mapArguments(Collection<? extends IrExpression> oldArguments, int compositeCount, List<IrStatement> newStatements, boolean dontDetachFirstArgument) {
            ArrayList arrayList = new ArrayList();
            int i = 0;
            for (IrExpression irExpression : oldArguments) {
                int i2 = i + 1;
                if (irExpression instanceof IrComposite) {
                    compositeCount--;
                    IrComposite irComposite = (IrComposite) irExpression;
                    List<IrStatement> statements = irComposite.getStatements();
                    CollectionsKt.addAll(newStatements, statements.subList(0, CollectionsKt.getLastIndex(statements)));
                    Object objLast = CollectionsKt.last((List<? extends Object>) irComposite.getStatements());
                    Intrinsics.checkNotNull(objLast, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                    irExpression = (IrExpression) objLast;
                }
                IrExpression irExpressionComponent1 = irExpression;
                if (compositeCount != 0 && ((i != 0 || !dontDetachFirstArgument) && irExpressionComponent1 != null && !org.jetbrains.kotlin.backend.common.ir.IrUtilsKt.isPure$default(irExpressionComponent1, false, false, BlockDecomposerTransformer.this.context, 2, null))) {
                    Pair<IrExpression, IrVariable> pairMapArgument = mapArgument(irExpressionComponent1);
                    irExpressionComponent1 = pairMapArgument.component1();
                    newStatements.add(pairMapArgument.component2());
                }
                arrayList.add(irExpressionComponent1);
                i = i2;
            }
            return arrayList;
        }

        /* JADX WARN: Code duplicated, block: B:22:0x00ab  */
        private final Pair<IrExpression, IrVariable> mapArgument(IrExpression arg) {
            IrGetValueImpl irGetValueImpl;
            IrCall irCallBuildCall$default = null;
            IrCall irCall = null;
            while (arg instanceof IrCall) {
                IrCall irCall2 = (IrCall) arg;
                if (!Intrinsics.areEqual(irCall2.getSymbol(), BlockDecomposerTransformer.this.boxIntrinsic) && !Intrinsics.areEqual(irCall2.getSymbol(), BlockDecomposerTransformer.this.unboxIntrinsic)) {
                    break;
                }
                if (irCall == null) {
                    irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, irCall2.getSymbol(), arg.getType(), CollectionsKt.filterNotNull(IrJsUtilsKt.getTypeArguments((IrFunctionAccessExpression) arg)), null, null, 24, null);
                    irCall = irCallBuildCall$default;
                } else {
                    IrCall irCallBuildCall$default2 = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, irCall2.getSymbol(), arg.getType(), CollectionsKt.filterNotNull(IrJsUtilsKt.getTypeArguments((IrFunctionAccessExpression) arg)), null, null, 24, null);
                    irCall.putValueArgument(0, irCallBuildCall$default2);
                    irCall = irCallBuildCall$default2;
                }
                arg = irCall2.getValueArgument(0);
                if (arg == null) {
                    throw new IllegalStateException("expect passing 1 argument to boxing intrinsic".toString());
                }
            }
            IrVariable irVariableMakeTempVar = BlockDecomposerTransformer.this.makeTempVar(arg.getType(), arg);
            IrGetValueImpl irGetValueImplBuildGetValue$default = JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, irVariableMakeTempVar.getSymbol(), null, 2, null);
            if (irCall != null) {
                irCall.putValueArgument(0, irGetValueImplBuildGetValue$default);
                if (irCallBuildCall$default != null) {
                    irGetValueImpl = irCallBuildCall$default;
                } else {
                    irGetValueImpl = irGetValueImplBuildGetValue$default;
                }
            } else {
                irGetValueImpl = irGetValueImplBuildGetValue$default;
            }
            return TuplesKt.to(irGetValueImpl, irVariableMakeTempVar);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitVararg(IrVararg expression) {
            IrExpression expression2;
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrElementTransformerVoidKt.transformChildrenVoid(expression, BlockDecomposerTransformer.this.expressionTransformer);
            List<IrVarargElement> elements = expression.getElements();
            int i = 0;
            if (!(elements instanceof Collection) || !elements.isEmpty()) {
                Iterator<T> it = elements.iterator();
                while (it.hasNext()) {
                    if ((((IrVarargElement) it.next()) instanceof IrComposite) && (i = i + 1) < 0) {
                        CollectionsKt.throwCountOverflow();
                    }
                }
            }
            int i2 = i;
            if (i2 == 0) {
                return expression;
            }
            ArrayList arrayList = new ArrayList();
            List<IrVarargElement> elements2 = expression.getElements();
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(elements2, 10));
            Iterator<T> it2 = elements2.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                IrVarargElement irVarargElement = (IrVarargElement) it2.next();
                IrSpreadElement irSpreadElement = irVarargElement instanceof IrSpreadElement ? (IrSpreadElement) irVarargElement : null;
                if (irSpreadElement == null || (expression2 = irSpreadElement.getExpression()) == null) {
                    Intrinsics.checkNotNull(irVarargElement, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                    expression2 = (IrExpression) irVarargElement;
                }
                arrayList2.add(expression2);
            }
            List listMapArguments$default = mapArguments$default(this, arrayList2, i2, arrayList, false, 8, null);
            Iterable<IndexedValue> iterableWithIndex = CollectionsKt.withIndex(expression.getElements());
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterableWithIndex, 10));
            for (IndexedValue indexedValue : iterableWithIndex) {
                int index = indexedValue.getIndex();
                IrVarargElement irVarargElement2 = (IrVarargElement) indexedValue.component2();
                Object obj = listMapArguments$default.get(index);
                Intrinsics.checkNotNull(obj);
                IrExpression irExpression = (IrExpression) obj;
                IrSpreadElement irSpreadElement2 = irVarargElement2 instanceof IrSpreadElement ? (IrSpreadElement) irVarargElement2 : null;
                arrayList3.add(irSpreadElement2 != null ? new IrSpreadElementImpl(irSpreadElement2.getStartOffset(), irSpreadElement2.getEndOffset(), irExpression) : irExpression);
            }
            arrayList.add(new IrVarargImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), expression.getVarargElementType(), arrayList3));
            return new IrCompositeImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), null, arrayList);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitMemberAccess(IrMemberAccessExpression<?> expression) {
            int i;
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrElementTransformerVoidKt.transformChildrenVoid(expression, BlockDecomposerTransformer.this.expressionTransformer);
            List listMutableListOf = CollectionsKt.mutableListOf(expression.getDispatchReceiver(), expression.getExtensionReceiver());
            int valueArgumentsCount = expression.getValueArgumentsCount();
            for (int i2 = 0; i2 < valueArgumentsCount; i2++) {
                listMutableListOf.add(expression.getValueArgument(i2));
            }
            List list = listMutableListOf;
            if ((list instanceof Collection) && list.isEmpty()) {
                i = 0;
            } else {
                Iterator it = list.iterator();
                int i3 = 0;
                while (it.hasNext()) {
                    if ((((IrExpression) it.next()) instanceof IrComposite) && (i3 = i3 + 1) < 0) {
                        CollectionsKt.throwCountOverflow();
                    }
                }
                i = i3;
            }
            if (i == 0) {
                return expression;
            }
            ArrayList arrayList = new ArrayList();
            List listMapArguments$default = mapArguments$default(this, listMutableListOf, i, arrayList, false, 8, null);
            expression.setDispatchReceiver((IrExpression) listMapArguments$default.get(0));
            expression.setExtensionReceiver((IrExpression) listMapArguments$default.get(1));
            int valueArgumentsCount2 = expression.getValueArgumentsCount();
            for (int i4 = 0; i4 < valueArgumentsCount2; i4++) {
                expression.putValueArgument(i4, (IrExpression) listMapArguments$default.get(i4 + 2));
            }
            arrayList.add(expression);
            return new IrCompositeImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), expression.getOrigin(), arrayList);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitDynamicMemberExpression(final IrDynamicMemberExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrElementTransformerVoidKt.transformChildrenVoid(expression, BlockDecomposerTransformer.this.expressionTransformer);
            IrExpression receiver = expression.getReceiver();
            IrComposite irComposite = receiver instanceof IrComposite ? (IrComposite) receiver : null;
            return irComposite == null ? expression : BlockDecomposerTransformer.this.materializeLastExpression(irComposite, new Function1<IrExpression, IrStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.BlockDecomposerTransformer$ExpressionTransformer$visitDynamicMemberExpression$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrStatement invoke(IrExpression it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    IrDynamicMemberExpression irDynamicMemberExpression = expression;
                    irDynamicMemberExpression.setReceiver(it);
                    return irDynamicMemberExpression;
                }
            });
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitDynamicOperatorExpression(IrDynamicOperatorExpression expression) {
            int i;
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrDynamicOperatorExpression irDynamicOperatorExpression = expression;
            IrElementTransformerVoidKt.transformChildrenVoid(irDynamicOperatorExpression, BlockDecomposerTransformer.this.expressionTransformer);
            List listPlus = CollectionsKt.plus((Collection) CollectionsKt.listOf(expression.getReceiver()), (Iterable) expression.getArguments());
            List list = listPlus;
            int i2 = 0;
            if ((list instanceof Collection) && list.isEmpty()) {
                i = 0;
            } else {
                Iterator it = list.iterator();
                i = 0;
                while (it.hasNext()) {
                    if ((((IrExpression) it.next()) instanceof IrComposite) && (i = i + 1) < 0) {
                        CollectionsKt.throwCountOverflow();
                    }
                }
            }
            if (i == 0) {
                return expression;
            }
            ArrayList arrayList = new ArrayList();
            List<IrExpression> listMapArguments = mapArguments(listPlus, i, arrayList, isReceiverNonDetachable(expression));
            IrExpression irExpression = listMapArguments.get(0);
            if (irExpression == null) {
                CompilationExceptionKt.compilationException("No new receiver in destructured composite", irDynamicOperatorExpression);
                throw null;
            }
            expression.setReceiver(irExpression);
            int size = expression.getArguments().size();
            while (i2 < size) {
                List<IrExpression> arguments = expression.getArguments();
                int i3 = i2 + 1;
                IrExpression irExpression2 = listMapArguments.get(i3);
                if (irExpression2 == null) {
                    CompilationExceptionKt.compilationException("No argument #" + i2 + " in destructured composite", irDynamicOperatorExpression);
                    throw null;
                }
                arguments.set(i2, irExpression2);
                i2 = i3;
            }
            arrayList.add(expression);
            return new IrCompositeImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), null, arrayList);
        }

        private final boolean isReceiverNonDetachable(IrDynamicOperatorExpression irDynamicOperatorExpression) {
            IrElement irElement;
            IrElement receiver = irDynamicOperatorExpression.getReceiver();
            if (receiver instanceof IrComposite) {
                irElement = (IrStatement) CollectionsKt.lastOrNull((List) ((IrComposite) receiver).getStatements());
                if (irElement == null) {
                    return false;
                }
            } else {
                irElement = (IrStatement) receiver;
            }
            return irDynamicOperatorExpression.getOperator().getIsAssignmentOperator() || (((irElement instanceof IrDynamicMemberExpression) || ((irElement instanceof IrDynamicOperatorExpression) && ((IrDynamicOperatorExpression) irElement).getOperator() == IrDynamicOperator.ARRAY_ACCESS)) && (irDynamicOperatorExpression.getOperator() == IrDynamicOperator.INVOKE));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitContainerExpression(IrContainerExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            BlockDecomposerTransformer blockDecomposerTransformer = BlockDecomposerTransformer.this;
            if (expression.getStatements().isEmpty()) {
                return new IrCompositeImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), expression.getOrigin(), CollectionsKt.listOf(blockDecomposerTransformer.getUnitValue()));
            }
            ArrayList arrayList = new ArrayList();
            int lastIndex = CollectionsKt.getLastIndex(expression.getStatements());
            for (int i = 0; i < lastIndex; i++) {
                CollectionsKt.addAll(arrayList, BlockDecomposerTransformer.this.destructureComposite(IrElementsKt.transformStatement(expression.getStatements().get(i), BlockDecomposerTransformer.this.statementTransformer)));
            }
            CollectionsKt.addAll(arrayList, BlockDecomposerTransformer.this.destructureComposite(IrElementsKt.transformStatement((IrStatement) CollectionsKt.last((List) expression.getStatements()), BlockDecomposerTransformer.this.expressionTransformer)));
            return JsIrBuilder.INSTANCE.buildComposite(expression.getType(), arrayList);
        }

        private final IrBlock wrap(IrExpression expression) {
            IrBlock irBlock = expression instanceof IrBlock ? (IrBlock) expression : null;
            return irBlock == null ? new IrBlockImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), null, CollectionsKt.listOf(expression)) : irBlock;
        }

        private final IrBlock wrap(IrExpression expression, IrVariable variable) {
            return wrap(JsIrBuilder.INSTANCE.buildSetVariable(variable.getSymbol(), expression, BlockDecomposerTransformer.this.unitType));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitTry(IrTry aTry) {
            Intrinsics.checkNotNullParameter(aTry, "aTry");
            IrVariable irVariableMakeTempVar$default = BlockDecomposerTransformer.makeTempVar$default(BlockDecomposerTransformer.this, aTry.getType(), null, 2, null);
            IrBlock irBlockWrap = wrap(aTry.getTryResult(), irVariableMakeTempVar$default);
            List<IrCatch> catches = aTry.getCatches();
            ArrayList arrayList = new ArrayList(catches.size());
            for (IrCatch irCatch : catches) {
                arrayList.add(new IrCatchImpl(irCatch.getStartOffset(), irCatch.getEndOffset(), irCatch.getCatchParameter(), wrap(irCatch.getResult(), irVariableMakeTempVar$default)));
            }
            IrTryImpl irTryImpl = new IrTryImpl(aTry.getStartOffset(), aTry.getEndOffset(), BlockDecomposerTransformer.this.unitType, irBlockWrap, org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList), aTry.getFinallyExpression());
            IrElementTransformerVoidKt.transformChildrenVoid(irTryImpl, BlockDecomposerTransformer.this.statementTransformer);
            return JsIrBuilder.INSTANCE.buildComposite(aTry.getType(), CollectionsKt.listOf((Object[]) new IrElement[]{irVariableMakeTempVar$default, irTryImpl, JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, irVariableMakeTempVar$default.getSymbol(), null, 2, null)}));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitWhen(IrWhen expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            List<IrBranch> branches = expression.getBranches();
            BlockDecomposerTransformer blockDecomposerTransformer = BlockDecomposerTransformer.this;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(branches, 10));
            boolean z = false;
            for (IrBranch irBranch : branches) {
                IrExpression irExpressionTransform = irBranch.getCondition().transform((IrElementTransformer<? super Object>) blockDecomposerTransformer.expressionTransformer, (Object) null);
                IrExpression irExpressionTransform2 = irBranch.getResult().transform((IrElementTransformer<? super Object>) blockDecomposerTransformer.expressionTransformer, (Object) null);
                z = z || (irExpressionTransform instanceof IrComposite) || (irExpressionTransform2 instanceof IrComposite);
                arrayList.add(new Triple(irBranch, irExpressionTransform, irExpressionTransform2));
            }
            ArrayList arrayList2 = arrayList;
            if (z) {
                IrVariable irVariableMakeTempVar$default = BlockDecomposerTransformer.makeTempVar$default(BlockDecomposerTransformer.this, expression.getType(), null, 2, null);
                ArrayList<Triple> arrayList3 = arrayList2;
                ArrayList arrayList4 = new ArrayList(arrayList3.size());
                for (Triple triple : arrayList3) {
                    IrBranch irBranch2 = (IrBranch) triple.component1();
                    IrExpression irExpression = (IrExpression) triple.component2();
                    IrBlock irBlockWrap = wrap((IrExpression) triple.component3(), irVariableMakeTempVar$default);
                    arrayList4.add(IrUtilsKt.isElseBranch(irBranch2) ? new IrElseBranchImpl(irBranch2.getStartOffset(), irBranch2.getEndOffset(), irExpression, irBlockWrap) : new IrBranchImpl(irBranch2.getStartOffset(), irBranch2.getEndOffset(), irExpression, irBlockWrap));
                }
                return JsIrBuilder.INSTANCE.buildComposite(expression.getType(), CollectionsKt.listOf((Object[]) new IrElement[]{irVariableMakeTempVar$default, new IrWhenImpl(expression.getStartOffset(), expression.getEndOffset(), BlockDecomposerTransformer.this.unitType, expression.getOrigin(), org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList4)).transform((IrElementTransformer) BlockDecomposerTransformer.this.statementTransformer, (Object) null), JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, irVariableMakeTempVar$default.getSymbol(), null, 2, null)}));
            }
            ArrayList<Triple> arrayList5 = arrayList2;
            ArrayList arrayList6 = new ArrayList(arrayList5.size());
            for (Triple triple2 : arrayList5) {
                IrBranch irBranch3 = (IrBranch) triple2.component1();
                IrExpression irExpression2 = (IrExpression) triple2.component2();
                IrExpression irExpression3 = (IrExpression) triple2.component3();
                arrayList6.add(IrUtilsKt.isElseBranch(irBranch3) ? new IrElseBranchImpl(irBranch3.getStartOffset(), irBranch3.getEndOffset(), irExpression2, irExpression3) : new IrBranchImpl(irBranch3.getStartOffset(), irBranch3.getEndOffset(), irExpression2, irExpression3));
            }
            return new IrWhenImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), expression.getOrigin(), org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList6));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void processStatements(List<IrStatement> statements) {
        int iReplaceInPlace = 0;
        while (iReplaceInPlace < statements.size()) {
            iReplaceInPlace = TransformKt.replaceInPlace(statements, destructureComposite(IrElementsKt.transformStatement(statements.get(iReplaceInPlace), this.statementTransformer)), iReplaceInPlace);
        }
    }
}
