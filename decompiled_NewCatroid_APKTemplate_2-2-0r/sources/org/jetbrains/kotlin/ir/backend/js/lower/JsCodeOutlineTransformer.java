package org.jetbrains.kotlin.ir.backend.js.lower;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CompilationExceptionKt;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.UtilsKt;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsCodeKt;
import org.jetbrains.kotlin.ir.backend.js.utils.JsGenerationContextKt;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrValueParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsIntLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsMultiLineComment;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsPrefixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsSingleLineComment;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsUnaryOperator;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: JsCodeOutliningLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0013\u001a\u00020\u000eJ\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u0017J\u000e\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u000fJ\u0010\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u001dH\u0016J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0016J\u0010\u0010\"\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020#H\u0016J\u0010\u0010$\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020%H\u0016J%\u0010&\u001a\u0002H'\"\u0004\b\u0000\u0010'2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H'0)H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010*R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR#\u0010\u000b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r0\f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/JsCodeOutlineTransformer;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "backendContext", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)V", "getBackendContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "getContainer", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "localScopes", "", "", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "getLocalScopes", "()Ljava/util/List;", "findValueDeclarationWithName", "name", "outlineJsCodeIfNeeded", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "registerValueDeclaration", "", "irValueDeclaration", "visitCall", "visitContainerExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "visitDeclaration", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "visitValueParameterNew", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "visitVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "withLocalScope", ExifInterface.GPS_DIRECTION_TRUE, "body", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class JsCodeOutlineTransformer extends IrElementTransformerVoidWithContext {
    private final JsIrBackendContext backendContext;
    private final IrDeclaration container;
    private final List<Map<String, IrValueDeclaration>> localScopes;

    public final JsIrBackendContext getBackendContext() {
        return this.backendContext;
    }

    public final IrDeclaration getContainer() {
        return this.container;
    }

    public JsCodeOutlineTransformer(JsIrBackendContext backendContext, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(backendContext, "backendContext");
        Intrinsics.checkNotNullParameter(container, "container");
        this.backendContext = backendContext;
        this.container = container;
        this.localScopes = CollectionsKt.mutableListOf(new LinkedHashMap());
        if (container instanceof IrFunction) {
            Iterator<T> it = ((IrFunction) container).getValueParameters().iterator();
            while (it.hasNext()) {
                registerValueDeclaration((IrValueParameter) it.next());
            }
        }
    }

    public final List<Map<String, IrValueDeclaration>> getLocalScopes() {
        return this.localScopes;
    }

    public final <T> T withLocalScope(Function0<? extends T> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        UtilsKt.push(getLocalScopes(), new LinkedHashMap());
        T tInvoke = body.invoke();
        UtilsKt.pop(getLocalScopes());
        return tInvoke;
    }

    public final void registerValueDeclaration(IrValueDeclaration irValueDeclaration) {
        Intrinsics.checkNotNullParameter(irValueDeclaration, "irValueDeclaration");
        Name name2 = irValueDeclaration.getName();
        if (name2.isSpecial()) {
            return;
        }
        String identifier = name2.getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        Map map = (Map) CollectionsKt.lastOrNull((List) this.localScopes);
        if (map == null) {
            CompilationExceptionKt.compilationException("Expecting a scope", (IrDeclaration) irValueDeclaration);
            throw null;
        }
        map.put(identifier, irValueDeclaration);
    }

    public final IrValueDeclaration findValueDeclarationWithName(String name2) {
        IrValueDeclaration irValueDeclaration;
        Intrinsics.checkNotNullParameter(name2, "name");
        int size = this.localScopes.size();
        do {
            size--;
            if (-1 >= size) {
                return null;
            }
            irValueDeclaration = this.localScopes.get(size).get(name2);
        } while (irValueDeclaration == null);
        return irValueDeclaration;
    }

    @Override // org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
    public IrStatement visitValueParameterNew(IrValueParameter declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrStatement irStatementVisitValueParameterNew = super.visitValueParameterNew(declaration);
        registerValueDeclaration(declaration);
        return irStatementVisitValueParameterNew;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitVariable(IrVariable declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrStatement irStatementVisitVariable = super.visitVariable(declaration);
        registerValueDeclaration(declaration);
        return irStatementVisitVariable;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitCall(IrCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrExpression irExpressionOutlineJsCodeIfNeeded = outlineJsCodeIfNeeded(expression);
        return irExpressionOutlineJsCodeIfNeeded == null ? super.visitCall(expression) : irExpressionOutlineJsCodeIfNeeded;
    }

    public final IrExpression outlineJsCodeIfNeeded(IrCall expression) {
        String str;
        Name name2;
        String strAsString;
        Intrinsics.checkNotNullParameter(expression, "expression");
        JsStatement jsStatement = null;
        if (!Intrinsics.areEqual(expression.getSymbol(), this.backendContext.getIntrinsics().getJsCode())) {
            return null;
        }
        int i = 0;
        IrExpression valueArgument = expression.getValueArgument(0);
        if (valueArgument == null) {
            CompilationExceptionKt.compilationException("Expected js code string", expression);
            throw null;
        }
        List<JsStatement> listTranslateJsCodeIntoStatementList = JsCodeKt.translateJsCodeIntoStatementList(valueArgument, this.backendContext, this.container);
        if (listTranslateJsCodeIntoStatementList == null) {
            return null;
        }
        JsScopesCollector jsScopesCollector = new JsScopesCollector();
        jsScopesCollector.acceptList(listTranslateJsCodeIntoStatementList);
        KotlinLocalsUsageCollector kotlinLocalsUsageCollector = new KotlinLocalsUsageCollector(jsScopesCollector, new JsCodeOutlineTransformer$outlineJsCodeIfNeeded$localsUsageCollector$1(this));
        kotlinLocalsUsageCollector.acceptList(listTranslateJsCodeIntoStatementList);
        Map<JsName, IrValueDeclaration> usedLocals = kotlinLocalsUsageCollector.getUsedLocals();
        if (usedLocals.isEmpty()) {
            return null;
        }
        IrFactory irFactory = this.backendContext.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        IrDeclaration irDeclaration = this.container;
        if (!(irDeclaration instanceof IrDeclarationWithName)) {
            irDeclaration = null;
        }
        IrDeclarationWithName irDeclarationWithName = (IrDeclarationWithName) irDeclaration;
        if (irDeclarationWithName == null || (name2 = irDeclarationWithName.getName()) == null || (strAsString = name2.asString()) == null || (str = strAsString + "$outlinedJsCode$") == null) {
            str = "outlinedJsCode$";
        }
        Name nameIdentifier = Name.identifier(str);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFunctionBuilder.setName(nameIdentifier);
        irFunctionBuilder.setReturnType(this.backendContext.getDynamicType());
        irFunctionBuilder.setExternal(true);
        irFunctionBuilder.setOrigin(JsCodeOutliningLowering.INSTANCE.getOUTLINED_JS_CODE_ORIGIN());
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setBody(this.backendContext.getIrFactory().createBlockBody(-1, -1));
        irSimpleFunctionBuildFunction.setParent(IrUtilsKt.getFile(this.container));
        IrUtilsKt.getFile(this.container).getDeclarations().add(irSimpleFunctionBuildFunction);
        for (IrValueDeclaration irValueDeclaration : usedLocals.values()) {
            IrSimpleFunction irSimpleFunction = irSimpleFunctionBuildFunction;
            IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
            irValueParameterBuilder.setName(irValueDeclaration.getName());
            irValueParameterBuilder.setType(irValueDeclaration.getType());
            if (irValueParameterBuilder.getIndex() == -1) {
                irValueParameterBuilder.setIndex(irSimpleFunction.getValueParameters().size());
            }
            irSimpleFunction.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irSimpleFunction.getValueParameters(), DeclarationBuildersKt.buildValueParameter(irSimpleFunction.getFactory(), irValueParameterBuilder, irSimpleFunction)));
        }
        ListIterator<JsStatement> listIterator = listTranslateJsCodeIntoStatementList.listIterator(listTranslateJsCodeIntoStatementList.size());
        while (listIterator.hasPrevious()) {
            JsStatement jsStatementPrevious = listIterator.previous();
            JsStatement jsStatement2 = jsStatementPrevious;
            if (!(jsStatement2 instanceof JsSingleLineComment) && !(jsStatement2 instanceof JsMultiLineComment)) {
                jsStatement = jsStatementPrevious;
                break;
            }
        }
        JsStatement jsStatement3 = jsStatement;
        List mutableList = CollectionsKt.toMutableList((Collection) listTranslateJsCodeIntoStatementList);
        if (!(jsStatement3 instanceof JsReturn)) {
            if (jsStatement3 instanceof JsExpressionStatement) {
                mutableList.set(CollectionsKt.getLastIndex(listTranslateJsCodeIntoStatementList), new JsReturn(((JsExpressionStatement) jsStatement3).getExpression()));
            } else {
                mutableList.add(new JsReturn(new JsPrefixOperation(JsUnaryOperator.VOID, new JsIntLiteral(3))));
            }
        }
        JsFunction jsFunction = new JsFunction(JsGenerationContextKt.getEmptyScope(), new JsBlock((List<JsStatement>) mutableList), "Outlined js() call");
        Iterator<T> it = usedLocals.keySet().iterator();
        while (it.hasNext()) {
            jsFunction.getParameters().add(new JsParameter((JsName) it.next()));
        }
        this.backendContext.addOutlinedJsCode(irSimpleFunctionBuildFunction.getSymbol(), jsFunction);
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.backendContext, this.container.getSymbol(), 0, 0, 6, (Object) null);
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(declarationIrBuilderCreateIrBuilder$default, irSimpleFunctionBuildFunction);
        for (Object obj : usedLocals.values()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            irFunctionAccessExpressionIrCall.putValueArgument(i, ExpressionHelpersKt.irGet(declarationIrBuilderCreateIrBuilder$default, (IrValueDeclaration) obj));
            i = i2;
        }
        return irFunctionAccessExpressionIrCall;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitContainerExpression(IrContainerExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        UtilsKt.push(getLocalScopes(), new LinkedHashMap());
        IrExpression irExpressionVisitContainerExpression = super.visitContainerExpression(expression);
        UtilsKt.pop(getLocalScopes());
        return irExpressionVisitContainerExpression;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitDeclaration(IrDeclarationBase declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        UtilsKt.push(getLocalScopes(), new LinkedHashMap());
        IrStatement irStatementVisitDeclaration = super.visitDeclaration(declaration);
        UtilsKt.pop(getLocalScopes());
        return irStatementVisitDeclaration;
    }
}
