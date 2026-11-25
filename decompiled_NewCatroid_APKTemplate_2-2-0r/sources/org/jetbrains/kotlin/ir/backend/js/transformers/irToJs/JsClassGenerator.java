package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.backend.common.CompilationExceptionKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.backend.js.JsIntrinsics;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.export.ExportModelGeneratorKt;
import org.jetbrains.kotlin.ir.backend.js.lower.ES6ConstructorLoweringKt;
import org.jetbrains.kotlin.ir.backend.js.utils.AnnotationUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.IrResolveUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.JsGenerationContext;
import org.jetbrains.kotlin.ir.backend.js.utils.JsGenerationContextKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.backend.ast.JsArrayLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsClass;
import org.jetbrains.kotlin.js.backend.ast.JsCompositeBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsIntLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsObjectLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsPropertyInitializer;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.common.IdentifierPolicyKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.MemoryOptimizedCollectionUtilKt;

/* JADX INFO: compiled from: JsClassGenerator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\n\u0010(\u001a\u0004\u0018\u00010\u0018H\u0002J\u0006\u0010)\u001a\u00020*J\n\u0010+\u001a\u0004\u0018\u00010,H\u0002J\n\u0010-\u001a\u0004\u0018\u00010.H\u0002J\b\u0010/\u001a\u00020\u0012H\u0002J\n\u00100\u001a\u0004\u0018\u000101H\u0002J\u001e\u00102\u001a\u0010\u0012\u0004\u0012\u00020\u0016\u0012\u0006\u0012\u0004\u0018\u000104032\u0006\u00105\u001a\u000206H\u0002J\b\u00107\u001a\u00020*H\u0002J\n\u00108\u001a\u0004\u0018\u000109H\u0002J\n\u0010:\u001a\u0004\u0018\u000101H\u0002J\b\u0010;\u001a\u00020\u0018H\u0002J\b\u0010<\u001a\u00020=H\u0002J\u000e\u0010>\u001a\u0004\u0018\u00010\u0018*\u000206H\u0002J\u000e\u0010?\u001a\u0004\u0018\u00010\f*\u00020\nH\u0002J\u0014\u0010@\u001a\u00020=*\u0002062\u0006\u0010A\u001a\u00020\u0016H\u0002J,\u0010B\u001a\u0004\u0018\u00010\f*\u0004\u0018\u0001062\u0019\u0010C\u001a\u0015\u0012\u0004\u0012\u000206\u0012\u0006\u0012\u0004\u0018\u0001040D¢\u0006\u0002\bEH\u0082\bJ\f\u0010F\u001a\u00020\"*\u000206H\u0002J\f\u0010G\u001a\u00020\"*\u000206H\u0002J\f\u0010H\u001a\u00020\"*\u00020\nH\u0002J\f\u0010I\u001a\u00020\f*\u000206H\u0002J\f\u0010J\u001a\u00020\"*\u00020\u0003H\u0002J\f\u0010K\u001a\u00020**\u00020\u0012H\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u000b\u001a\u0004\u0018\u00010\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u001a\u001a\u00020\u001b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\u0010\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u000e\u0010!\u001a\u00020\"X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010$\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b&\u0010\u0010\u001a\u0004\b%\u0010\u000eR\u000e\u0010'\u001a\u00020\"X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006L"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsClassGenerator;", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/utils/JsGenerationContext;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/backend/js/utils/JsGenerationContext;)V", "backendContext", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "baseClass", "Lorg/jetbrains/kotlin/ir/types/IrType;", "baseClassRef", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "getBaseClassRef", "()Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "baseClassRef$delegate", "Lkotlin/Lazy;", "classBlock", "Lorg/jetbrains/kotlin/js/backend/ast/JsCompositeBlock;", "classModel", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrClassModel;", "className", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "classNameRef", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "classNameUsedInsideDeclarationStatements", "classPrototypeRef", "Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "getClassPrototypeRef", "()Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "classPrototypeRef$delegate", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/utils/JsGenerationContext;", "es6mode", "", "interfaceDefaultsBlock", "jsUndefined", "getJsUndefined", "jsUndefined$delegate", "perFile", "findDefaultConstructor", CompilerOptions.GENERATE, "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "generateAssociatedObjectKey", "Lorg/jetbrains/kotlin/js/backend/ast/JsIntLiteral;", "generateAssociatedObjects", "Lorg/jetbrains/kotlin/js/backend/ast/JsObjectLiteral;", "generateClassBlock", "generateInterfacesList", "Lorg/jetbrains/kotlin/js/backend/ast/JsArrayLiteral;", "generateMemberFunction", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "generateSetMetadataCall", "generateSimpleName", "Lorg/jetbrains/kotlin/js/backend/ast/JsStringLiteral;", "generateSuspendArity", "getMetadataConstructor", "maybeGeneratePrimaryConstructor", "", "accessorRef", "asConstructorRef", "generateAssignmentIfMangled", "memberName", "getOrGenerateIfFinalOrEs6Mode", "generateFunc", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "hasMangledName", "isDefinedInsideExportedInterface", "isFunctionType", "prototypeAccessRef", "shouldCopyFrom", "wrapInFunction", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsClassGenerator {
    private final JsIrBackendContext backendContext;
    private final IrType baseClass;

    /* JADX INFO: renamed from: baseClassRef$delegate, reason: from kotlin metadata */
    private final Lazy baseClassRef;
    private final JsCompositeBlock classBlock;
    private final JsIrClassModel classModel;
    private final JsName className;
    private final JsNameRef classNameRef;
    private final JsName classNameUsedInsideDeclarationStatements;

    /* JADX INFO: renamed from: classPrototypeRef$delegate, reason: from kotlin metadata */
    private final Lazy classPrototypeRef;
    private final JsGenerationContext context;
    private final boolean es6mode;
    private final JsCompositeBlock interfaceDefaultsBlock;
    private final IrClass irClass;

    /* JADX INFO: renamed from: jsUndefined$delegate, reason: from kotlin metadata */
    private final Lazy jsUndefined;
    private final boolean perFile;

    public JsClassGenerator(IrClass irClass, JsGenerationContext context) {
        Object next;
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(context, "context");
        this.irClass = irClass;
        this.context = context;
        JsIrBackendContext backendContext = context.getStaticContext().getBackendContext();
        this.backendContext = backendContext;
        this.perFile = context.getStaticContext().isPerFile();
        this.es6mode = backendContext.getEs6mode();
        this.className = context.getNameForClass(irClass);
        Iterator<T> it = irClass.getSuperTypes().iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (JsClassGeneratorKt.isInterface(IrTypesKt.getClassifierOrFail((IrType) next)));
        this.baseClass = (IrType) next;
        JsName jsName = this.perFile ? new JsName("$", true) : this.className;
        this.classNameUsedInsideDeclarationStatements = jsName;
        JsNameRef jsNameRefMakeRef = jsName.makeRef();
        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
        this.classNameRef = jsNameRefMakeRef;
        this.classPrototypeRef = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<JsInvocation>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsClassGenerator$classPrototypeRef$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final JsInvocation invoke() {
                return JsAstUtilsKt.prototypeOf(this.this$0.classNameRef, this.this$0.getContext().getStaticContext());
            }
        });
        this.baseClassRef = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsClassGenerator$baseClassRef$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final JsExpression invoke() {
                if (this.this$0.baseClass == null || IrTypePredicatesKt.isAny(this.this$0.baseClass)) {
                    return null;
                }
                return IrTypeUtilsKt.getClassRef(this.this$0.baseClass, this.this$0.getContext().getStaticContext());
            }
        });
        JsIrClassModel jsIrClassModel = new JsIrClassModel(this.irClass);
        this.classModel = jsIrClassModel;
        this.classBlock = new JsCompositeBlock();
        this.interfaceDefaultsBlock = this.perFile ? new JsCompositeBlock() : jsIrClassModel.getPreDeclarationBlock();
        this.jsUndefined = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsClassGenerator$jsUndefined$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final JsExpression invoke() {
                return JsAstUtilsKt.jsUndefined(this.this$0.getContext().getStaticContext());
            }
        });
    }

    public final JsGenerationContext getContext() {
        return this.context;
    }

    private final JsInvocation getClassPrototypeRef() {
        return (JsInvocation) this.classPrototypeRef.getValue();
    }

    private final JsExpression getBaseClassRef() {
        return (JsExpression) this.baseClassRef.getValue();
    }

    private final JsExpression getJsUndefined() {
        return (JsExpression) this.jsUndefined.getValue();
    }

    public final JsStatement generate() {
        JsCompositeBlock jsCompositeBlockGenerateClassBlock = generateClassBlock();
        JsStatement jsStatementWrapInFunction = jsCompositeBlockGenerateClassBlock;
        if (this.perFile) {
            jsStatementWrapInFunction = wrapInFunction(jsCompositeBlockGenerateClassBlock);
        }
        return jsStatementWrapInFunction;
    }

    private final JsStatement wrapInFunction(JsCompositeBlock jsCompositeBlock) {
        JsVars.JsVar jsVar = new JsVars.JsVar(new JsName(this.className.getIdent() + "Class", true));
        JsFunction jsFunction = new JsFunction(JsGenerationContextKt.getEmptyScope(), new JsBlock(), "lazy wrapper for classes in per-file");
        jsFunction.setName(this.className);
        List<JsStatement> statements = jsFunction.getBody().getStatements();
        JsAstUtils jsAstUtils = JsAstUtils.INSTANCE;
        JsNameRef jsNameRefMakeRef = jsVar.getName().makeRef();
        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
        JsBinaryOperation jsBinaryOperationEquality = jsAstUtils.equality(jsNameRefMakeRef, getJsUndefined());
        List<JsStatement> statements2 = this.classModel.getPreDeclarationBlock().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
        List<JsStatement> statements3 = jsCompositeBlock.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements3, "getStatements(...)");
        List listPlus = CollectionsKt.plus((Collection) statements2, (Iterable) statements3);
        List<JsStatement> statements4 = this.classModel.getPostDeclarationBlock().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements4, "getStatements(...)");
        List listPlus2 = CollectionsKt.plus((Collection) listPlus, (Iterable) statements4);
        JsAstUtils jsAstUtils2 = JsAstUtils.INSTANCE;
        JsNameRef jsNameRefMakeRef2 = jsVar.getName().makeRef();
        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef2, "makeRef(...)");
        statements.add(new JsIf(jsBinaryOperationEquality, new JsBlock((List<JsStatement>) CollectionsKt.plus((Collection<? extends JsStatement>) listPlus2, jsAstUtils2.assignment(jsNameRefMakeRef2, this.classNameRef).makeStmt()))));
        statements.add(new JsReturn(jsVar.getName().makeRef()));
        List<JsStatement> statements5 = this.interfaceDefaultsBlock.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements5, "getStatements(...)");
        JsCompositeBlock jsCompositeBlock2 = new JsCompositeBlock((List<? extends JsStatement>) CollectionsKt.plus((Collection) statements5, (Iterable) CollectionsKt.listOf((Object[]) new JsStatement[]{new JsVars(jsVar), jsFunction.makeStmt()})));
        this.classModel.getPreDeclarationBlock().getStatements().clear();
        this.classModel.getPostDeclarationBlock().getStatements().clear();
        return jsCompositeBlock2;
    }

    private final JsCompositeBlock generateClassBlock() {
        boolean z;
        JsExpression jsExpressionGenerateClassBlock$propertyAccessorForwarder;
        JsExpression jsExpressionAccessorRef;
        JsExpression jsExpression;
        IrSimpleFunction getter;
        this.irClass.getIsExpect();
        if (!this.es6mode) {
            maybeGeneratePrimaryConstructor();
        }
        LinkedHashSet<IrProperty> linkedHashSet = new LinkedHashSet();
        JsClass jsClass = new JsClass(this.classNameUsedInsideDeclarationStatements, getBaseClassRef(), null, null, 12, null);
        IrType irType = this.baseClass;
        if (irType != null && !IrTypePredicatesKt.isAny(irType)) {
            jsClass.setBaseClass(getBaseClassRef());
        }
        if (this.es6mode) {
            List<JsStatement> statements = this.classModel.getPreDeclarationBlock().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            statements.add(jsClass.makeStmt());
        }
        for (IrDeclaration irDeclaration : this.irClass.getDeclarations()) {
            if (irDeclaration instanceof IrConstructor) {
                JsFunction jsFunction = (JsFunction) irDeclaration.accept(new IrFunctionToJsTransformer(), this.context);
                if (this.es6mode) {
                    jsFunction.setName(null);
                    jsClass.setConstructor(jsFunction);
                } else {
                    List<JsStatement> statements2 = this.classBlock.getStatements();
                    Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
                    jsFunction.setName(this.classNameUsedInsideDeclarationStatements);
                    statements2.add(jsFunction.makeStmt());
                }
            } else if (irDeclaration instanceof IrSimpleFunction) {
                LinkedHashSet linkedHashSet2 = linkedHashSet;
                IrSimpleFunction irSimpleFunction = (IrSimpleFunction) irDeclaration;
                IrPropertySymbol correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol();
                org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(linkedHashSet2, correspondingPropertySymbol != null ? correspondingPropertySymbol.getOwner() : null);
                if (this.es6mode) {
                    if (!ES6ConstructorLoweringKt.isEs6ConstructorReplacement(irDeclaration) || !IrUtilsKt.isInterface(this.irClass)) {
                        Pair<JsName, JsFunction> pairGenerateMemberFunction = generateMemberFunction(irSimpleFunction);
                        JsName jsNameComponent1 = pairGenerateMemberFunction.component1();
                        JsFunction jsFunctionComponent2 = pairGenerateMemberFunction.component2();
                        if (jsFunctionComponent2 != null) {
                            jsClass.getMembers().add(JsClassGeneratorKt.escapedIfNeed(jsFunctionComponent2));
                        }
                        generateAssignmentIfMangled(irSimpleFunction, jsNameComponent1);
                    }
                } else {
                    Pair<JsName, JsFunction> pairGenerateMemberFunction2 = generateMemberFunction(irSimpleFunction);
                    JsName jsNameComponent2 = pairGenerateMemberFunction2.component1();
                    JsFunction jsFunctionComponent3 = pairGenerateMemberFunction2.component2();
                    JsExpression jsExpressionJsElementAccess = JsAstUtilsKt.jsElementAccess(jsNameComponent2, getClassPrototypeRef());
                    if (jsFunctionComponent3 != null) {
                        List<JsStatement> statements3 = this.classBlock.getStatements();
                        Intrinsics.checkNotNullExpressionValue(statements3, "getStatements(...)");
                        jsFunctionComponent3.setName(null);
                        statements3.add(JsAstUtilsKt.jsAssignment(jsExpressionJsElementAccess, jsFunctionComponent3).makeStmt());
                    }
                    generateAssignmentIfMangled(irSimpleFunction, jsNameComponent2);
                }
            } else if (!(irDeclaration instanceof IrClass) && !(irDeclaration instanceof IrField)) {
                CompilationExceptionKt.compilationException("Unexpected declaration in class", irDeclaration);
                throw null;
            }
        }
        if (!IrUtilsKt.isInterface(this.irClass)) {
            for (IrProperty irProperty : linkedHashSet) {
                IrSimpleFunction getter2 = irProperty.getGetter();
                if ((getter2 != null ? getter2.getExtensionReceiverParameter() : null) == null) {
                    IrSimpleFunction setter = irProperty.getSetter();
                    if ((setter != null ? setter.getExtensionReceiverParameter() : null) == null && irProperty.getVisibility().isPublicAPI() && !IrUtilsKt.isSimpleProperty(irProperty)) {
                        IrProperty irProperty2 = irProperty;
                        if (!AnnotationUtilsKt.isJsExportIgnore(irProperty2) && (!irProperty.getIsFakeOverride() || ExportModelGeneratorKt.isAllowedFakeOverriddenDeclaration(irProperty, this.backendContext))) {
                            IrSimpleFunction getter3 = irProperty.getGetter();
                            List<IrSimpleFunctionSymbol> overriddenSymbols = getter3 != null ? getter3.getOverriddenSymbols() : null;
                            if (overriddenSymbols == null) {
                                overriddenSymbols = CollectionsKt.emptyList();
                            }
                            List<IrSimpleFunctionSymbol> list = overriddenSymbols;
                            boolean z2 = list instanceof Collection;
                            if (!z2 || !list.isEmpty()) {
                                Iterator<T> it = list.iterator();
                                while (true) {
                                    if (it.hasNext()) {
                                        if (isDefinedInsideExportedInterface(((IrSimpleFunctionSymbol) it.next()).getOwner())) {
                                            if (!z2 || !list.isEmpty()) {
                                                Iterator<T> it2 = list.iterator();
                                                while (true) {
                                                    if (it2.hasNext()) {
                                                        if (IrJsUtilsKt.isExportedClass(IrUtilsKt.getParentClassOrNull(((IrSimpleFunctionSymbol) it2.next()).getOwner()), this.backendContext)) {
                                                        }
                                                    }
                                                }
                                            }
                                            z = true;
                                            break;
                                        }
                                    }
                                    z = false;
                                    break;
                                }
                            }
                            z = false;
                            break;
                            IrSimpleFunction getter4 = irProperty.getGetter();
                            boolean z3 = getter4 != null && JsClassGeneratorKt.overridesExternal(getter4);
                            IrSimpleFunction getter5 = irProperty.getGetter();
                            List<IrSimpleFunctionSymbol> overriddenSymbols2 = getter5 != null ? getter5.getOverriddenSymbols() : null;
                            boolean z4 = (overriddenSymbols2 == null || overriddenSymbols2.isEmpty() || (getter = irProperty.getGetter()) == null || !ExportModelGeneratorKt.isOverriddenExported(getter, this.backendContext)) ? false : true;
                            IrSimpleFunction setter2 = irProperty.getSetter();
                            boolean z5 = (z4 && (setter2 != null && !ExportModelGeneratorKt.isOverriddenExported(setter2, this.backendContext))) || ExportModelGeneratorKt.isAllowedFakeOverriddenDeclaration(irProperty, this.backendContext);
                            if ((ExportModelGeneratorKt.isExported(this.irClass, this.backendContext) && (overriddenSymbols.isEmpty() || z5)) || z || z3 || AnnotationUtilsKt.getJsName(irProperty2) != null) {
                                JsName nameForProperty = this.context.getNameForProperty(irProperty);
                                IrSimpleFunction getter6 = irProperty.getGetter();
                                if (!JsClassGeneratorKt.shouldExportAccessor(getter6, this.backendContext)) {
                                    getter6 = null;
                                }
                                if (getter6 == null) {
                                    jsExpressionGenerateClassBlock$propertyAccessorForwarder = null;
                                } else {
                                    jsExpressionGenerateClassBlock$propertyAccessorForwarder = (this.es6mode || getter6.getModality() != Modality.FINAL) ? generateClassBlock$propertyAccessorForwarder(getter6, this, "getter forwarder", new Function1<JsNameRef, JsStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsClassGenerator$generateClassBlock$getterForwarder$2$1
                                        @Override // kotlin.jvm.functions.Function1
                                        public final JsStatement invoke(JsNameRef it3) {
                                            Intrinsics.checkNotNullParameter(it3, "it");
                                            return new JsReturn(new JsInvocation(it3, new JsExpression[0]));
                                        }
                                    }) : accessorRef(getter6);
                                }
                                IrSimpleFunction setter3 = irProperty.getSetter();
                                if (!JsClassGeneratorKt.shouldExportAccessor(setter3, this.backendContext)) {
                                    setter3 = null;
                                }
                                if (setter3 == null) {
                                    jsExpression = null;
                                } else {
                                    if (this.es6mode || setter3.getModality() != Modality.FINAL) {
                                        final JsName jsName = new JsName("value", false);
                                        JsFunction jsFunctionGenerateClassBlock$propertyAccessorForwarder = generateClassBlock$propertyAccessorForwarder(setter3, this, "setter forwarder", new Function1<JsNameRef, JsStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsClassGenerator$generateClassBlock$setterForwarder$2$1
                                            {
                                                super(1);
                                            }

                                            @Override // kotlin.jvm.functions.Function1
                                            public final JsStatement invoke(JsNameRef it3) {
                                                Intrinsics.checkNotNullParameter(it3, "it");
                                                JsStatement jsStatementMakeStmt = new JsInvocation(it3, new JsNameRef(jsName)).makeStmt();
                                                Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
                                                return jsStatementMakeStmt;
                                            }
                                        });
                                        if (jsFunctionGenerateClassBlock$propertyAccessorForwarder != null) {
                                            jsFunctionGenerateClassBlock$propertyAccessorForwarder.getParameters().add(new JsParameter(jsName));
                                            jsExpressionAccessorRef = jsFunctionGenerateClassBlock$propertyAccessorForwarder;
                                        } else {
                                            jsExpressionAccessorRef = null;
                                        }
                                    } else {
                                        jsExpressionAccessorRef = accessorRef(setter3);
                                    }
                                    jsExpression = jsExpressionAccessorRef;
                                }
                                if (this.es6mode) {
                                    List<JsFunction> members = jsClass.getMembers();
                                    JsFunction[] jsFunctionArr = new JsFunction[2];
                                    JsFunction jsFunction2 = jsExpressionGenerateClassBlock$propertyAccessorForwarder instanceof JsFunction ? (JsFunction) jsExpressionGenerateClassBlock$propertyAccessorForwarder : null;
                                    if (jsFunction2 != null) {
                                        jsFunction2.setName(nameForProperty);
                                        jsFunction2.getModifiers().add(JsFunction.Modifier.GET);
                                        Unit unit = Unit.INSTANCE;
                                    } else {
                                        jsFunction2 = null;
                                    }
                                    jsFunctionArr[0] = jsFunction2;
                                    JsFunction jsFunction3 = jsExpression instanceof JsFunction ? (JsFunction) jsExpression : null;
                                    if (jsFunction3 != null) {
                                        jsFunction3.setName(nameForProperty);
                                        jsFunction3.getModifiers().add(JsFunction.Modifier.SET);
                                        Unit unit2 = Unit.INSTANCE;
                                    } else {
                                        jsFunction3 = null;
                                    }
                                    jsFunctionArr[1] = jsFunction3;
                                    CollectionsKt.addAll(members, CollectionsKt.listOfNotNull((Object[]) jsFunctionArr));
                                } else {
                                    List<JsStatement> statements4 = this.classModel.getPostDeclarationBlock().getStatements();
                                    Intrinsics.checkNotNullExpressionValue(statements4, "getStatements(...)");
                                    JsInvocation classPrototypeRef = getClassPrototypeRef();
                                    String ident = nameForProperty.getIdent();
                                    Intrinsics.checkNotNullExpressionValue(ident, "getIdent(...)");
                                    statements4.add(new JsExpressionStatement(JsAstUtilsKt.defineProperty(classPrototypeRef, ident, jsExpressionGenerateClassBlock$propertyAccessorForwarder, jsExpression, this.context.getStaticContext())));
                                }
                            }
                        }
                    }
                }
            }
        }
        List<JsStatement> statements5 = (this.es6mode ? this.classModel.getPostDeclarationBlock() : this.classModel.getPreDeclarationBlock()).getStatements();
        Intrinsics.checkNotNullExpressionValue(statements5, "getStatements(...)");
        statements5.add(generateSetMetadataCall());
        this.context.getStaticContext().getClassModels().put(this.irClass.getSymbol(), this.classModel);
        return this.classBlock;
    }

    private static final JsFunction generateClassBlock$propertyAccessorForwarder(IrSimpleFunction irSimpleFunction, JsClassGenerator jsClassGenerator, String str, Function1<? super JsNameRef, ? extends JsStatement> function1) {
        if (Intrinsics.areEqual(irSimpleFunction.getVisibility(), DescriptorVisibilities.PRIVATE)) {
            return null;
        }
        return new JsFunction(JsGenerationContextKt.getEmptyScope(), new JsBlock(function1.invoke(new JsNameRef(jsClassGenerator.context.getNameForMemberFunction(irSimpleFunction), new JsThisRef()))), str);
    }

    private final boolean isDefinedInsideExportedInterface(IrSimpleFunction irSimpleFunction) {
        IrProperty owner;
        if (AnnotationUtilsKt.isJsExportIgnore(irSimpleFunction)) {
            return false;
        }
        IrPropertySymbol correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol();
        if (correspondingPropertySymbol != null && (owner = correspondingPropertySymbol.getOwner()) != null && AnnotationUtilsKt.isJsExportIgnore(owner)) {
            return false;
        }
        if (irSimpleFunction.getIsFakeOverride() || !IrJsUtilsKt.isExportedInterface(IrUtilsKt.getParentClassOrNull(irSimpleFunction), this.backendContext)) {
            List<IrSimpleFunctionSymbol> overriddenSymbols = irSimpleFunction.getOverriddenSymbols();
            if ((overriddenSymbols instanceof Collection) && overriddenSymbols.isEmpty()) {
                return false;
            }
            Iterator<T> it = overriddenSymbols.iterator();
            while (it.hasNext()) {
                if (isDefinedInsideExportedInterface(((IrSimpleFunctionSymbol) it.next()).getOwner())) {
                }
            }
            return false;
        }
        return true;
    }

    private final JsNameRef accessorRef(IrSimpleFunction irSimpleFunction) {
        if (Intrinsics.areEqual(irSimpleFunction.getVisibility(), DescriptorVisibilities.PRIVATE)) {
            return null;
        }
        return new JsNameRef(this.context.getNameForMemberFunction(irSimpleFunction), getClassPrototypeRef());
    }

    private final void generateAssignmentIfMangled(IrSimpleFunction irSimpleFunction, JsName jsName) {
        if (ExportModelGeneratorKt.isExported(this.irClass, this.backendContext) && irSimpleFunction.getVisibility().isPublicAPI() && hasMangledName(irSimpleFunction) && irSimpleFunction.getCorrespondingPropertySymbol() == null) {
            List<JsStatement> statements = this.classBlock.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            statements.add(JsAstUtilsKt.jsAssignment(prototypeAccessRef(irSimpleFunction), JsAstUtilsKt.jsElementAccess(jsName, getClassPrototypeRef())).makeStmt());
        }
    }

    private final boolean hasMangledName(IrSimpleFunction irSimpleFunction) {
        if (AnnotationUtilsKt.getJsName(irSimpleFunction) == null) {
            String strAsString = irSimpleFunction.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            if (!IdentifierPolicyKt.isValidES5Identifier(strAsString)) {
                return true;
            }
        }
        return false;
    }

    private final JsExpression prototypeAccessRef(IrSimpleFunction irSimpleFunction) {
        String strAsString = irSimpleFunction.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return JsAstUtilsKt.jsElementAccess(strAsString, getClassPrototypeRef());
    }

    private final boolean shouldCopyFrom(IrClass irClass) {
        IrClassSymbol classOrNull;
        IrClass owner;
        if (!IrUtilsKt.isInterface(irClass) || IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) irClass)) {
            return false;
        }
        IrType irType = this.baseClass;
        if (irType == null || (classOrNull = IrTypesKt.getClassOrNull(irType)) == null || (owner = classOrNull.getOwner()) == null) {
            return true;
        }
        return !IrUtilsKt.isSubclassOf(owner, irClass);
    }

    private final Pair<JsName, JsFunction> generateMemberFunction(IrSimpleFunction declaration) {
        Object next;
        JsName nameForMemberFunction = this.context.getNameForMemberFunction(IrResolveUtilsKt.getRealOverrideTarget(declaration));
        IrSimpleFunction irSimpleFunction = declaration;
        if (AdditionalIrUtilsKt.isReal(irSimpleFunction) && declaration.getBody() != null) {
            JsFunction jsFunction = (JsFunction) declaration.accept(new IrFunctionToJsTransformer(), this.context);
            IrDeclarationsKt.isStaticMethodOfClass(irSimpleFunction);
            if (IrUtilsKt.isInterface(this.irClass)) {
                List<JsStatement> statements = this.interfaceDefaultsBlock.getStatements();
                Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                statements.add(jsFunction.makeStmt());
                return new Pair<>(nameForMemberFunction, null);
            }
            return new Pair<>(nameForMemberFunction, jsFunction);
        }
        if (!IrUtilsKt.isInterface(this.irClass)) {
            boolean isFakeOverride = declaration.getIsFakeOverride();
            ArrayList arrayList = new ArrayList();
            Set<IrSimpleFunction> setCollectRealOverrides$default = IrFakeOverrideUtilsKt.collectRealOverrides$default(declaration, null, null, 3, null);
            for (IrSimpleFunction irSimpleFunction2 : setCollectRealOverrides$default) {
                if (isFakeOverride && irSimpleFunction2.getModality() == Modality.ABSTRACT) {
                    arrayList.add(irSimpleFunction2);
                }
            }
            Iterator it = setCollectRealOverrides$default.iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (((IrSimpleFunction) next).getModality() == Modality.ABSTRACT);
            IrSimpleFunction irSimpleFunction3 = (IrSimpleFunction) next;
            if (irSimpleFunction3 != null) {
                IrDeclarationParent parent = irSimpleFunction3.getParent();
                Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                if (shouldCopyFrom((IrClass) parent)) {
                    JsNameRef jsNameRefMakeRef = this.context.getNameForStaticDeclaration(irSimpleFunction3).makeRef();
                    Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
                    List<JsStatement> statements2 = this.classModel.getPostDeclarationBlock().getStatements();
                    Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
                    JsNameRef jsNameRef = jsNameRefMakeRef;
                    statements2.add(JsAstUtilsKt.jsAssignment(JsAstUtilsKt.jsElementAccess(nameForMemberFunction, getClassPrototypeRef()), jsNameRef).makeStmt());
                    if (isFakeOverride) {
                        List<JsStatement> statements3 = this.classModel.getPostDeclarationBlock().getStatements();
                        Intrinsics.checkNotNullExpressionValue(statements3, "getStatements(...)");
                        List<JsStatement> list = statements3;
                        ArrayList arrayList2 = arrayList;
                        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
                        Iterator it2 = arrayList2.iterator();
                        while (it2.hasNext()) {
                            String ident = this.context.getNameForMemberFunction((IrSimpleFunction) it2.next()).getIdent();
                            Intrinsics.checkNotNullExpressionValue(ident, "getIdent(...)");
                            arrayList3.add(JsAstUtilsKt.jsAssignment(JsAstUtilsKt.jsElementAccess(ident, getClassPrototypeRef()), jsNameRef).makeStmt());
                        }
                        CollectionsKt.addAll(list, arrayList3);
                    }
                }
            }
        }
        return new Pair<>(nameForMemberFunction, null);
    }

    private final void maybeGeneratePrimaryConstructor() {
        List<IrDeclaration> declarations = this.irClass.getDeclarations();
        if (!(declarations instanceof Collection) || !declarations.isEmpty()) {
            Iterator<T> it = declarations.iterator();
            while (it.hasNext()) {
                if (((IrDeclaration) it.next()) instanceof IrConstructor) {
                    return;
                }
            }
        }
        JsFunction jsFunction = new JsFunction(JsGenerationContextKt.getEmptyScope(), new JsBlock(), "Ctor for " + this.irClass.getName());
        jsFunction.setName(this.classNameUsedInsideDeclarationStatements);
        List<JsStatement> statements = this.classBlock.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        statements.add(jsFunction.makeStmt());
    }

    private final JsStatement generateSetMetadataCall() {
        List listEmptyList;
        IrSimpleFunction owner = this.backendContext.getIntrinsics().getSetMetadataForSymbol().getOwner();
        JsNameRef jsNameRef = this.classNameRef;
        JsExpression baseClassRef = getBaseClassRef();
        if (baseClassRef == null || this.es6mode) {
            baseClassRef = null;
        }
        JsStringLiteral jsStringLiteralGenerateSimpleName = generateSimpleName();
        JsArrayLiteral jsArrayLiteralGenerateInterfacesList = generateInterfacesList();
        JsNameRef metadataConstructor = getMetadataConstructor();
        JsNameRef jsNameRefFindDefaultConstructor = IrUtilsKt.isClass(this.irClass) ? findDefaultConstructor() : null;
        JsIntLiteral jsIntLiteralGenerateAssociatedObjectKey = generateAssociatedObjectKey();
        JsObjectLiteral jsObjectLiteralGenerateAssociatedObjects = generateAssociatedObjects();
        JsArrayLiteral jsArrayLiteralGenerateSuspendArity = generateSuspendArity();
        JsNameRef jsNameRef2 = new JsNameRef(this.context.getNameForStaticFunction(owner));
        List listListOf = CollectionsKt.listOf((Object[]) new JsExpression[]{jsNameRef, jsStringLiteralGenerateSimpleName, metadataConstructor, baseClassRef, jsArrayLiteralGenerateInterfacesList, jsNameRefFindDefaultConstructor, jsIntLiteralGenerateAssociatedObjectKey, jsObjectLiteralGenerateAssociatedObjects, jsArrayLiteralGenerateSuspendArity});
        if (!listListOf.isEmpty()) {
            ListIterator listIterator = listListOf.listIterator(listListOf.size());
            while (true) {
                if (listIterator.hasPrevious()) {
                    if (((JsExpression) listIterator.previous()) != null) {
                        listEmptyList = CollectionsKt.take(listListOf, listIterator.nextIndex() + 1);
                        break;
                    }
                } else {
                    listEmptyList = CollectionsKt.emptyList();
                    break;
                }
            }
        } else {
            listEmptyList = CollectionsKt.emptyList();
            break;
        }
        List<JsExpression> list = listEmptyList;
        ArrayList arrayList = new ArrayList(list.size());
        for (JsExpression jsUndefined : list) {
            if (jsUndefined == null) {
                jsUndefined = getJsUndefined();
            }
            arrayList.add(jsUndefined);
        }
        JsStatement jsStatementMakeStmt = new JsInvocation(jsNameRef2, (List<? extends JsExpression>) org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList)).makeStmt();
        Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
        return jsStatementMakeStmt;
    }

    private final JsExpression asConstructorRef(IrType irType) {
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irType);
        if (classOrNull != null) {
            if (IrTypePredicatesKt.isAny(irType) || isFunctionType(irType) || IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) classOrNull.getOwner())) {
                classOrNull = null;
            }
            if (classOrNull != null) {
                return IrTypeUtilsKt.getClassRef(classOrNull.getOwner(), this.context.getStaticContext());
            }
        }
        return null;
    }

    private final boolean isFunctionType(IrType irType) {
        return org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isFunctionOrKFunction(irType) || org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isSuspendFunctionOrKFunction(irType);
    }

    private final JsStringLiteral generateSimpleName() {
        Name name2 = this.irClass.getName();
        if (name2.isSpecial()) {
            name2 = null;
        }
        if (name2 != null) {
            return new JsStringLiteral(name2.getIdentifier());
        }
        return null;
    }

    private final JsNameRef getMetadataConstructor() {
        IrSimpleFunctionSymbol metadataObjectConstructorSymbol;
        JsIntrinsics intrinsics = this.backendContext.getIntrinsics();
        if (IrUtilsKt.isInterface(this.irClass)) {
            metadataObjectConstructorSymbol = intrinsics.getMetadataInterfaceConstructorSymbol();
        } else {
            metadataObjectConstructorSymbol = IrUtilsKt.isObject(this.irClass) ? intrinsics.getMetadataObjectConstructorSymbol() : intrinsics.getMetadataClassConstructorSymbol();
        }
        return new JsNameRef(this.context.getNameForStaticFunction(metadataObjectConstructorSymbol.getOwner()));
    }

    private final JsArrayLiteral generateInterfacesList() {
        IrClass owner;
        List<IrType> superTypes = this.irClass.getSuperTypes();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = superTypes.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            IrClassSymbol classOrNull = IrTypesKt.getClassOrNull((IrType) next);
            if (!((classOrNull == null || (owner = classOrNull.getOwner()) == null || !owner.getIsExternal()) ? false : true)) {
                arrayList.add(next);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.size() <= 1 && Intrinsics.areEqual(CollectionsKt.singleOrNull((List) arrayList2), this.baseClass)) {
            arrayList2 = null;
        }
        if (arrayList2 != null) {
            ArrayList arrayList3 = new ArrayList();
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                JsExpression jsExpressionAsConstructorRef = asConstructorRef((IrType) it2.next());
                if (jsExpressionAsConstructorRef != null) {
                    arrayList3.add(jsExpressionAsConstructorRef);
                }
            }
            ArrayList arrayList4 = arrayList3;
            if (arrayList4.isEmpty()) {
                arrayList4 = null;
            }
            if (arrayList4 != null) {
                return new JsArrayLiteral(MemoryOptimizedCollectionUtilKt.toSmartList(arrayList4));
            }
        }
        return null;
    }

    private final JsNameRef findDefaultConstructor() {
        IrFunction irFunctionFindDefaultConstructorFor = IrJsUtilsKt.findDefaultConstructorFor(this.backendContext, this.irClass);
        if (irFunctionFindDefaultConstructorFor instanceof IrConstructor) {
            return this.context.getNameForConstructor((IrConstructor) irFunctionFindDefaultConstructorFor).makeRef();
        }
        if (!(irFunctionFindDefaultConstructorFor instanceof IrSimpleFunction)) {
            return null;
        }
        if (this.es6mode) {
            return new JsNameRef(this.context.getNameForMemberFunction((IrSimpleFunction) irFunctionFindDefaultConstructorFor), this.classNameRef);
        }
        JsNameRef jsNameRefMakeRef = this.context.getNameForStaticFunction((IrSimpleFunction) irFunctionFindDefaultConstructorFor).makeRef();
        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
        return jsNameRefMakeRef;
    }

    private final JsArrayLiteral generateSuspendArity() {
        Collection<IrSimpleFunction> collection = this.backendContext.getMapping().getSuspendArityStore().get(this.irClass);
        if (collection == null) {
            return null;
        }
        Collection<IrSimpleFunction> collection2 = collection;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection2, 10));
        Iterator<T> it = collection2.iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(((IrSimpleFunction) it.next()).getValueParameters().size()));
        }
        List listDistinct = CollectionsKt.distinct(arrayList);
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listDistinct, 10));
        Iterator it2 = listDistinct.iterator();
        while (it2.hasNext()) {
            arrayList2.add(new JsIntLiteral(((Number) it2.next()).intValue()));
        }
        ArrayList arrayList3 = arrayList2;
        JsArrayLiteral jsArrayLiteral = new JsArrayLiteral(MemoryOptimizedCollectionUtilKt.toSmartList(arrayList3));
        if (arrayList3.isEmpty()) {
            return null;
        }
        return jsArrayLiteral;
    }

    private final JsIntLiteral generateAssociatedObjectKey() {
        Integer associatedObjectKey = this.context.getAssociatedObjectKey(this.irClass);
        if (associatedObjectKey != null) {
            return new JsIntLiteral(associatedObjectKey.intValue());
        }
        return null;
    }

    private final JsObjectLiteral generateAssociatedObjects() {
        IrSimpleFunction irSimpleFunction;
        List<IrConstructorCall> annotations = this.irClass.getAnnotations();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = annotations.iterator();
        while (true) {
            JsPropertyInitializer jsPropertyInitializer = null;
            if (!it.hasNext()) {
                break;
            }
            IrConstructorCall irConstructorCall = (IrConstructorCall) it.next();
            Integer associatedObjectKey = this.context.getAssociatedObjectKey(AdditionalIrUtilsKt.getConstructedClass(irConstructorCall.getSymbol().getOwner()));
            if (associatedObjectKey != null) {
                int iIntValue = associatedObjectKey.intValue();
                IrClass irClassAssociatedObject = AnnotationUtilsKt.associatedObject(irConstructorCall);
                if (irClassAssociatedObject != null && (irSimpleFunction = this.backendContext.getMapping().getObjectToGetInstanceFunction().get(irClassAssociatedObject)) != null) {
                    jsPropertyInitializer = new JsPropertyInitializer(new JsIntLiteral(iIntValue), this.context.getStaticContext().getNameForStaticFunction(irSimpleFunction).makeRef());
                }
            }
            if (jsPropertyInitializer != null) {
                arrayList.add(jsPropertyInitializer);
            }
        }
        List smartList = MemoryOptimizedCollectionUtilKt.toSmartList(arrayList);
        if (smartList.isEmpty()) {
            smartList = null;
        }
        if (smartList != null) {
            return new JsObjectLiteral((List<JsPropertyInitializer>) smartList);
        }
        return null;
    }
}
