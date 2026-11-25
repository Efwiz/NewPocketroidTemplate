package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTryImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: TestGenerator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J<\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000e2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000e0\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000e0\u001a2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u000eH\u0002J\u001e\u0010\u001f\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u000e0 H\u0002J\u0010\u0010!\u001a\u00020\u00172\u0006\u0010\"\u001a\u00020#H\u0016J\u0010\u0010$\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020#H\u0002J\f\u0010%\u001a\u00020\u0005*\u00020\u001dH\u0002J&\u0010&\u001a\u00020\u000e*\u00020'2\u0006\u0010(\u001a\u00020)2\u0006\u0010\u001e\u001a\u00020\u000e2\b\b\u0002\u0010*\u001a\u00020\u0005H\u0002J\u0014\u0010+\u001a\u00020\u0005*\u00020\u00112\u0006\u0010,\u001a\u00020)H\u0002J\f\u0010-\u001a\u00020.*\u00020\u001dH\u0002J\f\u0010/\u001a\u00020\u0005*\u000200H\u0002J\f\u00101\u001a\u00020\u0005*\u000200H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR*\u0010\u000b\u001a\u001e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\fj\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e`\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0010\u001a\u00020\u0005*\u00020\u00118BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0012R\u0018\u0010\u0013\u001a\u00020\u0005*\u00020\u00118BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0012R\u0018\u0010\u0014\u001a\u00020\u0005*\u00020\u00118BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0012R\u0018\u0010\u0015\u001a\u00020\u0005*\u00020\u00118BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0012¨\u00062²\u0006\n\u00103\u001a\u00020\u000eX\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/TestGenerator;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "groupByPackage", "", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;Z)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "getGroupByPackage", "()Z", "packageSuites", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lkotlin/collections/HashMap;", "isAfter", "Lorg/jetbrains/kotlin/ir/declarations/IrAnnotationContainer;", "(Lorg/jetbrains/kotlin/ir/declarations/IrAnnotationContainer;)Z", "isBefore", "isIgnored", "isTest", "generateCodeForTestMethod", "", "testFun", "beforeFuns", "", "afterFuns", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "parentFunction", "generateTestCalls", "Lkotlin/Function0;", "lower", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "suiteForPackage", "canBeInstantiated", "createInvocation", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "name", "", "ignored", "hasAnnotation", "fqName", "instance", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "isEffectivelyVisibleFromTests", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithVisibility;", "isVisibleFromTests", "backend.js", "suiteFunBody"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TestGenerator implements FileLoweringPass {
    private final JsCommonBackendContext context;
    private final boolean groupByPackage;
    private final HashMap<FqName, IrSimpleFunction> packageSuites;

    public TestGenerator(JsCommonBackendContext context, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.groupByPackage = z;
        this.packageSuites = new HashMap<>();
    }

    public final JsCommonBackendContext getContext() {
        return this.context;
    }

    public final boolean getGroupByPackage() {
        return this.groupByPackage;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(final IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        if (irFile.getDeclarations().isEmpty()) {
            return;
        }
        for (IrDeclaration irDeclaration : new ArrayList(irFile.getDeclarations())) {
            if (irDeclaration instanceof IrClass) {
                generateTestCalls((IrClass) irDeclaration, new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.TestGenerator$lower$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final IrSimpleFunction invoke() {
                        return this.this$0.getGroupByPackage() ? this.this$0.suiteForPackage(irFile) : this.this$0.getContext().createTestContainerFun(irFile);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrSimpleFunction suiteForPackage(IrFile irFile) {
        HashMap<FqName, IrSimpleFunction> map = this.packageSuites;
        FqName packageFqName = irFile.getPackageFqName();
        IrSimpleFunction irSimpleFunctionCreateInvocation$default = map.get(packageFqName);
        if (irSimpleFunctionCreateInvocation$default == null) {
            IrSimpleFunctionSymbol suiteFun = this.context.getSuiteFun();
            Intrinsics.checkNotNull(suiteFun);
            String strAsString = irFile.getPackageFqName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            irSimpleFunctionCreateInvocation$default = createInvocation$default(this, suiteFun, strAsString, this.context.createTestContainerFun(irFile), false, 4, null);
            map.put(packageFqName, irSimpleFunctionCreateInvocation$default);
        }
        return irSimpleFunctionCreateInvocation$default;
    }

    static /* synthetic */ IrSimpleFunction createInvocation$default(TestGenerator testGenerator, IrSimpleFunctionSymbol irSimpleFunctionSymbol, String str, IrSimpleFunction irSimpleFunction, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return testGenerator.createInvocation(irSimpleFunctionSymbol, str, irSimpleFunction, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrSimpleFunction createInvocation(IrSimpleFunctionSymbol irSimpleFunctionSymbol, String str, IrSimpleFunction irSimpleFunction, boolean z) {
        IrBlockBody irBlockBodyCreateBlockBody = IrFactoryHelpersKt.createBlockBody(this.context.getIrFactory(), -1, -1, (List<? extends IrStatement>) CollectionsKt.emptyList());
        IrFactory irFactory = this.context.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        Name nameIdentifier = Name.identifier(str + " test fun");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFunctionBuilder.setName(nameIdentifier);
        IrSimpleFunctionSymbol suiteFun = this.context.getSuiteFun();
        Intrinsics.checkNotNull(suiteFun);
        irFunctionBuilder.setReturnType(Intrinsics.areEqual(irSimpleFunctionSymbol, suiteFun) ? this.context.getIrBuiltIns().getUnitType() : this.context.getIrBuiltIns().getAnyNType());
        irFunctionBuilder.setOrigin(JsIrBuilder.SYNTHESIZED_DECLARATION.INSTANCE);
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(irSimpleFunction);
        irSimpleFunctionBuildFunction.setBody(irBlockBodyCreateBlockBody);
        IrBody body = irSimpleFunction.getBody();
        Intrinsics.checkNotNull(body, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBlockBody");
        List<IrStatement> statements = ((IrBlockBody) body).getStatements();
        IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, irSimpleFunctionSymbol, null, null, null, null, 30, null);
        irCallBuildCall$default.putValueArgument(0, JsIrBuilder.INSTANCE.buildString(this.context.getIrBuiltIns().getStringType(), str));
        irCallBuildCall$default.putValueArgument(1, JsIrBuilder.INSTANCE.buildBoolean(this.context.getIrBuiltIns().getBooleanType(), z));
        irCallBuildCall$default.putValueArgument(2, JsIrBuilder.INSTANCE.buildFunctionExpression(new IrSimpleTypeImpl((IrClassifierSymbol) this.context.getIr().getSymbols().functionN(0), false, CollectionsKt.emptyList(), CollectionsKt.emptyList(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null), irSimpleFunctionBuildFunction));
        statements.add(irCallBuildCall$default);
        return irSimpleFunctionBuildFunction;
    }

    private final void generateTestCalls(final IrClass irClass, final Function0<? extends IrSimpleFunction> parentFunction) {
        SmartList smartListEmptyList;
        SmartList smartListEmptyList2;
        if (irClass.getModality() == Modality.ABSTRACT || IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) irClass) || irClass.getIsExpect()) {
            return;
        }
        final Lazy lazy = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.TestGenerator$generateTestCalls$suiteFunBody$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunction invoke() {
                TestGenerator testGenerator = this.this$0;
                IrSimpleFunctionSymbol suiteFun = testGenerator.getContext().getSuiteFun();
                Intrinsics.checkNotNull(suiteFun);
                String strAsString = irClass.getName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                return testGenerator.createInvocation(suiteFun, strAsString, parentFunction.invoke(), this.this$0.isIgnored(irClass));
            }
        });
        List<IrDeclaration> declarations = irClass.getDeclarations();
        if (declarations.isEmpty()) {
            smartListEmptyList = CollectionsKt.emptyList();
        } else {
            SmartList smartList = new SmartList();
            for (Object obj : declarations) {
                if ((obj instanceof IrSimpleFunction) && isBefore((IrSimpleFunction) obj)) {
                    smartList.add(obj);
                }
            }
            smartListEmptyList = smartList;
        }
        List<IrDeclaration> declarations2 = irClass.getDeclarations();
        if (declarations2.isEmpty()) {
            smartListEmptyList2 = CollectionsKt.emptyList();
        } else {
            SmartList smartList2 = new SmartList();
            for (Object obj2 : declarations2) {
                if ((obj2 instanceof IrSimpleFunction) && isAfter((IrSimpleFunction) obj2)) {
                    smartList2.add(obj2);
                }
            }
            smartListEmptyList2 = smartList2;
        }
        List<? extends IrSimpleFunction> list = smartListEmptyList2;
        for (IrDeclaration irDeclaration : irClass.getDeclarations()) {
            if (irDeclaration instanceof IrClass) {
                generateTestCalls((IrClass) irDeclaration, new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.TestGenerator$generateTestCalls$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final IrSimpleFunction invoke() {
                        return TestGenerator.generateTestCalls$lambda$4(lazy);
                    }
                });
            } else if ((irDeclaration instanceof IrSimpleFunction) && isTest(irDeclaration)) {
                generateCodeForTestMethod((IrSimpleFunction) irDeclaration, smartListEmptyList, list, irClass, generateTestCalls$lambda$4(lazy));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrSimpleFunction generateTestCalls$lambda$4(Lazy<? extends IrSimpleFunction> lazy) {
        return lazy.getValue();
    }

    private final boolean isVisibleFromTests(IrDeclarationWithVisibility irDeclarationWithVisibility) {
        return Intrinsics.areEqual(irDeclarationWithVisibility.getVisibility(), DescriptorVisibilities.PUBLIC) || Intrinsics.areEqual(irDeclarationWithVisibility.getVisibility(), DescriptorVisibilities.INTERNAL);
    }

    private final boolean isEffectivelyVisibleFromTests(IrDeclarationWithVisibility irDeclarationWithVisibility) {
        Iterator it = SequencesKt.generateSequence(irDeclarationWithVisibility, new Function1<IrDeclarationWithVisibility, IrDeclarationWithVisibility>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.TestGenerator.isEffectivelyVisibleFromTests.1
            @Override // kotlin.jvm.functions.Function1
            public final IrDeclarationWithVisibility invoke(IrDeclarationWithVisibility it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                IrDeclarationParent parent = it2.getParent();
                if (parent instanceof IrDeclarationWithVisibility) {
                    return (IrDeclarationWithVisibility) parent;
                }
                return null;
            }
        }).iterator();
        while (it.hasNext()) {
            if (!isVisibleFromTests((IrDeclarationWithVisibility) it.next())) {
                return false;
            }
        }
        return true;
    }

    private final boolean canBeInstantiated(IrClass irClass) {
        boolean zIsEffectivelyVisibleFromTests = isEffectivelyVisibleFromTests(irClass);
        if (IrUtilsKt.isObject(irClass)) {
            return zIsEffectivelyVisibleFromTests;
        }
        if (zIsEffectivelyVisibleFromTests) {
            for (IrConstructor irConstructor : IrUtilsKt.getConstructors(irClass)) {
                if (isVisibleFromTests(irConstructor) && IrUtilsKt.getExplicitParametersCount(irConstructor) == irClass.getIsInner()) {
                    return true;
                }
            }
        }
        return false;
    }

    private final void generateCodeForTestMethod(IrSimpleFunction testFun, List<? extends IrSimpleFunction> beforeFuns, List<? extends IrSimpleFunction> afterFuns, IrClass irClass, IrSimpleFunction parentFunction) {
        String string;
        Object next;
        IrSimpleFunctionSymbol testFun2 = this.context.getTestFun();
        Intrinsics.checkNotNull(testFun2);
        String strAsString = testFun.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        IrSimpleFunction irSimpleFunctionCreateInvocation = createInvocation(testFun2, strAsString, parentFunction, isIgnored(testFun));
        IrBody body = irSimpleFunctionCreateInvocation.getBody();
        Intrinsics.checkNotNull(body, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBlockBody");
        IrBlockBody irBlockBody = (IrBlockBody) body;
        if (!testFun.getValueParameters().isEmpty() || !isEffectivelyVisibleFromTests(testFun)) {
            StringBuilder sb = new StringBuilder("Test method ");
            Object fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(irClass);
            if (fqNameWhenAvailable == null) {
                fqNameWhenAvailable = irClass.getName();
            }
            string = sb.append(fqNameWhenAvailable).append("::").append(testFun.getName()).append(" should have public or internal visibility, can not have parameters").toString();
        } else if (canBeInstantiated(irClass)) {
            string = null;
        } else {
            StringBuilder sb2 = new StringBuilder("Test class ");
            Object fqNameWhenAvailable2 = IrUtilsKt.getFqNameWhenAvailable(irClass);
            if (fqNameWhenAvailable2 == null) {
                fqNameWhenAvailable2 = irClass.getName();
            }
            string = sb2.append(fqNameWhenAvailable2).append(" must declare a public or internal constructor with no explicit parameters").toString();
        }
        if (string != null) {
            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, irSimpleFunctionCreateInvocation.getSymbol(), 0, 0, 6, (Object) null);
            List<IrStatement> statements = irBlockBody.getStatements();
            DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder$default;
            IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder, this.context.getIrBuiltIns().getIllegalArgumentExceptionSymbol());
            irCall.putValueArgument(0, ExpressionHelpersKt.irString(declarationIrBuilder, string));
            statements.add(irCall);
            return;
        }
        IrSimpleFunction irSimpleFunction = irSimpleFunctionCreateInvocation;
        IrVariable irVariableBuildVar = JsIrBuilder.INSTANCE.buildVar(IrUtilsKt.getDefaultType(irClass), irSimpleFunction, (180 & 4) != 0 ? Constants.TMP_DIRECTORY_NAME : null, (180 & 8) != 0 ? false : false, (180 & 16) != 0 ? false : false, (180 & 32) != 0 ? false : false, (180 & 64) != 0 ? null : instance(irClass), (180 & 128) != 0 ? JsIrBuilder.SYNTHESIZED_DECLARATION.INSTANCE : null);
        irBlockBody.getStatements().add(irVariableBuildVar);
        List<IrStatement> statements2 = irBlockBody.getStatements();
        List<? extends IrSimpleFunction> list = beforeFuns;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, ((IrSimpleFunction) it.next()).getSymbol(), null, null, null, null, 30, null);
            irCallBuildCall$default.setDispatchReceiver(JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, irVariableBuildVar.getSymbol(), null, 2, null));
            arrayList.add(irCallBuildCall$default);
        }
        CollectionsKt.addAll(statements2, arrayList);
        JsIrBuilder jsIrBuilder = JsIrBuilder.INSTANCE;
        IrSimpleFunctionSymbol symbol = irSimpleFunctionCreateInvocation.getSymbol();
        IrCall irCallBuildCall$default2 = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, testFun.getSymbol(), null, null, null, null, 30, null);
        irCallBuildCall$default2.setDispatchReceiver(JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, irVariableBuildVar.getSymbol(), null, 2, null));
        Unit unit = Unit.INSTANCE;
        IrReturnImpl irReturnImplBuildReturn = jsIrBuilder.buildReturn(symbol, irCallBuildCall$default2, this.context.getIrBuiltIns().getUnitType());
        if (afterFuns.isEmpty()) {
            irBlockBody.getStatements().add(irReturnImplBuildReturn);
            return;
        }
        if (this.context instanceof JsIrBackendContext) {
            IrType returnType = testFun.getReturnType();
            IrSimpleType irSimpleType = returnType instanceof IrSimpleType ? (IrSimpleType) returnType : null;
            if (Intrinsics.areEqual(irSimpleType != null ? irSimpleType.getClassifier() : null, ((JsIrBackendContext) this.context).getIntrinsics().getPromiseClassSymbol())) {
                Iterator<T> it2 = ((JsIrBackendContext) this.context).getIntrinsics().getPromiseClassSymbol().getOwner().getDeclarations().iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it2.next();
                    if ((next instanceof IrSimpleFunction) && Intrinsics.areEqual(((IrSimpleFunction) next).getName().asString(), PsiKeyword.FINALLY)) {
                        break;
                    }
                }
                Intrinsics.checkNotNull(next);
                IrSimpleFunction irSimpleFunction2 = (IrSimpleFunction) next;
                IrSimpleTypeImpl irSimpleTypeImpl = new IrSimpleTypeImpl((IrClassifierSymbol) ((JsIrBackendContext) this.context).getIr().getSymbols().functionN(0), false, CollectionsKt.emptyList(), CollectionsKt.emptyList(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null);
                IrFactory irFactory = this.context.getIrFactory();
                IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                Name nameIdentifier = Name.identifier(irClass.getName().asString() + " after test fun");
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                irFunctionBuilder.setName(nameIdentifier);
                irFunctionBuilder.setReturnType(this.context.getIrBuiltIns().getUnitType());
                irFunctionBuilder.setOrigin(JsIrBuilder.SYNTHESIZED_DECLARATION.INSTANCE);
                IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
                irSimpleFunctionBuildFunction.setParent(irSimpleFunction);
                IrFactory irFactory2 = this.context.getIrFactory();
                List<? extends IrSimpleFunction> list2 = afterFuns;
                ArrayList arrayList2 = new ArrayList(list2.size());
                Iterator<T> it3 = list2.iterator();
                while (it3.hasNext()) {
                    IrCall irCallBuildCall$default3 = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, ((IrSimpleFunction) it3.next()).getSymbol(), null, null, null, null, 30, null);
                    irCallBuildCall$default3.setDispatchReceiver(JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, irVariableBuildVar.getSymbol(), null, 2, null));
                    arrayList2.add(irCallBuildCall$default3);
                }
                irSimpleFunctionBuildFunction.setBody(IrFactoryHelpersKt.createBlockBody(irFactory2, -1, -1, (List<? extends IrStatement>) org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList2)));
                IrFunctionExpressionImpl irFunctionExpressionImplBuildFunctionExpression = JsIrBuilder.INSTANCE.buildFunctionExpression(irSimpleTypeImpl, irSimpleFunctionBuildFunction);
                IrCall irCallBuildCall$default4 = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, irSimpleFunction2.getSymbol(), null, null, null, null, 30, null);
                irCallBuildCall$default4.setDispatchReceiver(irReturnImplBuildReturn.getValue());
                irCallBuildCall$default4.putValueArgument(0, irFunctionExpressionImplBuildFunctionExpression);
                irBlockBody.getStatements().add(JsIrBuilder.INSTANCE.buildReturn(irSimpleFunctionCreateInvocation.getSymbol(), irCallBuildCall$default4, irSimpleFunctionCreateInvocation.getReturnType()));
                return;
            }
        }
        List<IrStatement> statements3 = irBlockBody.getStatements();
        IrTryImpl irTryImplBuildTry = JsIrBuilder.INSTANCE.buildTry(this.context.getIrBuiltIns().getUnitType());
        irTryImplBuildTry.setTryResult(irReturnImplBuildReturn);
        IrCompositeImpl irCompositeImplBuildComposite$default = JsIrBuilder.buildComposite$default(JsIrBuilder.INSTANCE, this.context.getIrBuiltIns().getUnitType(), null, 2, null);
        List<IrStatement> statements4 = irCompositeImplBuildComposite$default.getStatements();
        List<? extends IrSimpleFunction> list3 = afterFuns;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
        Iterator<T> it4 = list3.iterator();
        while (it4.hasNext()) {
            IrCall irCallBuildCall$default5 = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, ((IrSimpleFunction) it4.next()).getSymbol(), null, null, null, null, 30, null);
            irCallBuildCall$default5.setDispatchReceiver(JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, irVariableBuildVar.getSymbol(), null, 2, null));
            arrayList3.add(irCallBuildCall$default5);
        }
        CollectionsKt.addAll(statements4, arrayList3);
        irTryImplBuildTry.setFinallyExpression(irCompositeImplBuildComposite$default);
        statements3.add(irTryImplBuildTry);
    }

    private final IrExpression instance(IrClass irClass) {
        if (irClass.getKind() == ClassKind.OBJECT) {
            return JsIrBuilder.INSTANCE.buildGetObjectValue(IrUtilsKt.getDefaultType(irClass), irClass.getSymbol());
        }
        Sequence<IrConstructor> sequenceFilter = SequencesKt.filter(CollectionsKt.asSequence(irClass.getDeclarations()), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.TestGenerator$instance$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof IrConstructor);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        for (IrConstructor irConstructor : sequenceFilter) {
            if (IrUtilsKt.getExplicitParametersCount(irConstructor) == irClass.getIsInner()) {
                IrConstructorCallImpl irConstructorCallImplFromSymbolOwner$default = IrConstructorCallImpl.Companion.fromSymbolOwner$default(IrConstructorCallImpl.INSTANCE, IrUtilsKt.getDefaultType(irClass), irConstructor.getSymbol(), null, 4, null);
                if (irClass.getIsInner()) {
                    IrDeclarationParent parent = irClass.getParent();
                    Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                    irConstructorCallImplFromSymbolOwner$default.setDispatchReceiver(instance((IrClass) parent));
                }
                return irConstructorCallImplFromSymbolOwner$default;
            }
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    private final boolean isTest(IrAnnotationContainer irAnnotationContainer) {
        return hasAnnotation(irAnnotationContainer, "kotlin.test.Test");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isIgnored(IrAnnotationContainer irAnnotationContainer) {
        return hasAnnotation(irAnnotationContainer, "kotlin.test.Ignore");
    }

    private final boolean isBefore(IrAnnotationContainer irAnnotationContainer) {
        return hasAnnotation(irAnnotationContainer, "kotlin.test.BeforeTest");
    }

    private final boolean isAfter(IrAnnotationContainer irAnnotationContainer) {
        return hasAnnotation(irAnnotationContainer, "kotlin.test.AfterTest");
    }

    private final boolean hasAnnotation(IrAnnotationContainer irAnnotationContainer, String str) {
        FqName fqNameParent;
        List<IrConstructorCall> annotations = irAnnotationContainer.getAnnotations();
        if ((annotations instanceof Collection) && annotations.isEmpty()) {
            return false;
        }
        Iterator<T> it = annotations.iterator();
        while (it.hasNext()) {
            FqName fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(((IrConstructorCall) it.next()).getSymbol().getOwner());
            if (Intrinsics.areEqual((fqNameWhenAvailable == null || (fqNameParent = fqNameWhenAvailable.parent()) == null) ? null : fqNameParent.asString(), str)) {
                return true;
            }
        }
        return false;
    }
}
