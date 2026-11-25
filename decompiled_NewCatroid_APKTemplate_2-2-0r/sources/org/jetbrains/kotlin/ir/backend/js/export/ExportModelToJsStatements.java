package org.jetbrains.kotlin.ir.backend.js.export;

import com.gaurav.avnc.vnc.XKeySym;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.ir.backend.js.JsLoweredDeclarationOrigin;
import org.jetbrains.kotlin.ir.backend.js.lower.ES6ConstructorLoweringKt;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsAstUtils;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsAstUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.AnnotationUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.JsGenerationContextKt;
import org.jetbrains.kotlin.ir.backend.js.utils.JsStaticContext;
import org.jetbrains.kotlin.ir.backend.js.utils.Namer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExport;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsObjectLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsPropertyInitializer;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: ExportModelToJsStatements.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ2\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u00052\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0017J&\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u0015\u001a\u00020\u0005J\u0014\u0010\u001d\u001a\u00020\u001e*\u00020\u00142\u0006\u0010\u001f\u001a\u00020\u0014H\u0002J\u0014\u0010 \u001a\u00020\u0010*\u00020!2\u0006\u0010\"\u001a\u00020\u001cH\u0002J\f\u0010#\u001a\u00020$*\u00020%H\u0002J\u001a\u0010&\u001a\u0010\u0012\u0004\u0012\u00020\u001c\u0012\u0006\u0012\u0004\u0018\u00010\u00100'*\u00020!H\u0002R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\n\u001a\u001e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\f0\u000bj\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\f`\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportModelToJsStatements;", "", "staticContext", "Lorg/jetbrains/kotlin/ir/backend/js/utils/JsStaticContext;", "es6mode", "", "declareNewNamespace", "Lkotlin/Function1;", "", "(Lorg/jetbrains/kotlin/ir/backend/js/utils/JsStaticContext;ZLkotlin/jvm/functions/Function1;)V", "namespaceToRefMap", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "Lkotlin/collections/HashMap;", "generateDeclarationExport", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedDeclaration;", "namespace", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "esModules", "parentClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "generateModuleExport", "module", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedModule;", "internalModuleName", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "bindToThis", "Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "bindTo", "generateInnerClassAssignment", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedClass;", "outerClassName", "generateTopLevelGetters", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars$JsVar;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedProperty;", "getNameAndInitialization", "Lkotlin/Pair;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExportModelToJsStatements {
    private final Function1<String, String> declareNewNamespace;
    private final boolean es6mode;
    private final HashMap<String, JsNameRef> namespaceToRefMap;
    private final JsStaticContext staticContext;

    /* JADX WARN: Multi-variable type inference failed */
    public ExportModelToJsStatements(JsStaticContext staticContext, boolean z, Function1<? super String, String> declareNewNamespace) {
        Intrinsics.checkNotNullParameter(staticContext, "staticContext");
        Intrinsics.checkNotNullParameter(declareNewNamespace, "declareNewNamespace");
        this.staticContext = staticContext;
        this.es6mode = z;
        this.declareNewNamespace = declareNewNamespace;
        this.namespaceToRefMap = new HashMap<>();
    }

    public final List<JsStatement> generateModuleExport(ExportedModule module, JsName internalModuleName, boolean esModules) {
        Intrinsics.checkNotNullParameter(module, "module");
        List<ExportedDeclaration> declarations = module.getDeclarations();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = declarations.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, generateDeclarationExport$default(this, (ExportedDeclaration) it.next(), internalModuleName != null ? internalModuleName.makeRef() : null, esModules, null, 8, null));
        }
        return arrayList;
    }

    public static /* synthetic */ List generateDeclarationExport$default(ExportModelToJsStatements exportModelToJsStatements, ExportedDeclaration exportedDeclaration, JsExpression jsExpression, boolean z, IrClass irClass, int i, Object obj) {
        if ((i & 8) != 0) {
            irClass = null;
        }
        return exportModelToJsStatements.generateDeclarationExport(exportedDeclaration, jsExpression, z, irClass);
    }

    public final List<JsStatement> generateDeclarationExport(ExportedDeclaration declaration, JsExpression namespace, boolean esModules, IrClass parentClass) {
        JsInvocation jsInvocationPrototypeOf;
        JsExport jsExport;
        List<JsStatement> listGenerateDeclarationExport;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof ExportedNamespace) {
            if (namespace == null) {
                throw new IllegalArgumentException("Only namespaced namespaces are allowed".toString());
            }
            ArrayList arrayList = new ArrayList();
            ExportedNamespace exportedNamespace = (ExportedNamespace) declaration;
            String str = "";
            JsNameRef jsNameRef = namespace;
            for (String str2 : StringsKt.split$default((CharSequence) exportedNamespace.getName(), new String[]{"."}, false, 0, 6, (Object) null)) {
                str = str + '$' + str2;
                HashMap<String, JsNameRef> map = this.namespaceToRefMap;
                JsNameRef jsNameRef2 = map.get(str);
                if (jsNameRef2 == null) {
                    JsName jsName = new JsName(this.declareNewNamespace.invoke(str), false);
                    JsExpression jsExpressionJsElementAccess = JsAstUtilsKt.jsElementAccess(str2, jsNameRef);
                    arrayList.add(new JsVars(new JsVars.JsVar(jsName, JsAstUtils.INSTANCE.or(jsExpressionJsElementAccess, JsAstUtilsKt.jsAssignment(jsExpressionJsElementAccess, new JsObjectLiteral())))));
                    JsNameRef jsNameRef3 = new JsNameRef(jsName);
                    map.put(str, jsNameRef3);
                    jsNameRef2 = jsNameRef3;
                }
                jsNameRef = jsNameRef2;
            }
            ArrayList arrayList2 = arrayList;
            List<ExportedDeclaration> declarations = exportedNamespace.getDeclarations();
            ArrayList arrayList3 = new ArrayList();
            Iterator<T> it = declarations.iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(arrayList3, generateDeclarationExport$default(this, (ExportedDeclaration) it.next(), jsNameRef, esModules, null, 8, null));
            }
            return CollectionsKt.plus((Collection) arrayList2, (Iterable) arrayList3);
        }
        if (declaration instanceof ExportedFunction) {
            ExportedFunction exportedFunction = (ExportedFunction) declaration;
            JsName nameForStaticDeclaration = this.staticContext.getNameForStaticDeclaration(exportedFunction.getIr());
            if (namespace != null) {
                return CollectionsKt.listOf(JsAstUtilsKt.jsAssignment(JsAstUtilsKt.jsElementAccess(exportedFunction.getName(), namespace), new JsNameRef(nameForStaticDeclaration)).makeStmt());
            }
            if (esModules) {
                JsNameRef jsNameRefMakeRef = nameForStaticDeclaration.makeRef();
                Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
                return CollectionsKt.listOf(new JsExport(jsNameRefMakeRef, new JsName(exportedFunction.getName(), false)));
            }
            return CollectionsKt.emptyList();
        }
        if (!(declaration instanceof ExportedConstructor) && !(declaration instanceof ExportedConstructSignature)) {
            if (declaration instanceof ExportedProperty) {
                if (namespace == null && !esModules) {
                    throw new IllegalArgumentException("Only namespaced properties are allowed".toString());
                }
                if (namespace == null) {
                    ExportedProperty exportedProperty = (ExportedProperty) declaration;
                    JsVars.JsVar jsVarGenerateTopLevelGetters = generateTopLevelGetters(exportedProperty);
                    JsNameRef jsNameRefMakeRef2 = jsVarGenerateTopLevelGetters.getName().makeRef();
                    Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef2, "makeRef(...)");
                    return CollectionsKt.listOf((Object[]) new JsNode[]{new JsVars(jsVarGenerateTopLevelGetters), new JsExport(jsNameRefMakeRef2, new JsName(exportedProperty.getName(), false))});
                }
                ExportedProperty exportedProperty2 = (ExportedProperty) declaration;
                IrFunction irGetter = exportedProperty2.getIrGetter();
                JsName nameForStaticDeclaration2 = irGetter != null ? this.staticContext.getNameForStaticDeclaration(irGetter) : null;
                IrFunction irSetter = exportedProperty2.getIrSetter();
                JsName nameForStaticDeclaration3 = irSetter != null ? this.staticContext.getNameForStaticDeclaration(irSetter) : null;
                return CollectionsKt.listOf(JsAstUtilsKt.defineProperty(namespace, exportedProperty2.getName(), nameForStaticDeclaration2 != null ? nameForStaticDeclaration2.makeRef() : null, (JsExpression) (nameForStaticDeclaration3 != null ? nameForStaticDeclaration3.makeRef() : null), this.staticContext).makeStmt());
            }
            if (declaration instanceof ErrorDeclaration) {
                return CollectionsKt.emptyList();
            }
            if (declaration instanceof ExportedObject) {
                if (namespace == null && !esModules) {
                    throw new IllegalArgumentException("Only namespaced properties are allowed".toString());
                }
                Pair<JsName, JsStatement> nameAndInitialization = getNameAndInitialization((ExportedClass) declaration);
                JsName jsNameComponent1 = nameAndInitialization.component1();
                JsStatement jsStatementComponent2 = nameAndInitialization.component2();
                JsExpression jsExpressionJsElementAccess2 = JsAstUtilsKt.jsElementAccess(Namer.INSTANCE.getPROTOTYPE_NAME(), jsNameComponent1.makeRef());
                ExportedObject exportedObject = (ExportedObject) declaration;
                List<ExportedClass> nestedClasses = exportedObject.getNestedClasses();
                ArrayList arrayList4 = new ArrayList();
                Iterator<T> it2 = nestedClasses.iterator();
                while (it2.hasNext()) {
                    CollectionsKt.addAll(arrayList4, generateDeclarationExport((ExportedClass) it2.next(), jsExpressionJsElementAccess2, esModules, exportedObject.getIr()));
                }
                ArrayList arrayList5 = arrayList4;
                if (this.es6mode || namespace == null) {
                    listGenerateDeclarationExport = generateDeclarationExport(new ExportedProperty(exportedObject.getName(), ExportedType.Primitive.Any.INSTANCE, false, parentClass != null, !(parentClass != null && IrUtilsKt.isObject(parentClass)), false, false, false, exportedObject.getIrGetter(), null, false, XKeySym.XK_Cyrillic_DE, null), namespace, esModules, parentClass);
                } else {
                    listGenerateDeclarationExport = CollectionsKt.listOf(JsAstUtilsKt.defineProperty(namespace, exportedObject.getName(), this.staticContext.getNameForStaticDeclaration(exportedObject.getIrGetter()).makeRef(), null, this.staticContext).makeStmt());
                }
                return CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.listOfNotNull(arrayList5.isEmpty() ? null : jsStatementComponent2), (Iterable) listGenerateDeclarationExport), (Iterable) arrayList5);
            }
            if (!(declaration instanceof ExportedRegularClass)) {
                throw new NoWhenBranchMatchedException();
            }
            ExportedRegularClass exportedRegularClass = (ExportedRegularClass) declaration;
            if (exportedRegularClass.isInterface()) {
                return CollectionsKt.emptyList();
            }
            Pair<JsName, JsStatement> nameAndInitialization2 = getNameAndInitialization((ExportedClass) declaration);
            JsName jsNameComponent2 = nameAndInitialization2.component1();
            JsStatement jsStatementComponent3 = nameAndInitialization2.component2();
            if (namespace != null) {
                jsInvocationPrototypeOf = JsAstUtilsKt.jsElementAccess(exportedRegularClass.getName(), namespace);
            } else if (esModules) {
                JsNameRef jsNameRefMakeRef3 = jsNameComponent2.makeRef();
                Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef3, "makeRef(...)");
                jsInvocationPrototypeOf = jsNameRefMakeRef3;
            } else {
                JsNameRef jsNameRefMakeRef4 = jsNameComponent2.makeRef();
                Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef4, "makeRef(...)");
                jsInvocationPrototypeOf = JsAstUtilsKt.prototypeOf(jsNameRefMakeRef4, this.staticContext);
            }
            if (namespace != null) {
                JsNameRef jsNameRefMakeRef5 = jsNameComponent2.makeRef();
                Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef5, "makeRef(...)");
                jsExport = JsAstUtilsKt.jsAssignment(jsInvocationPrototypeOf, jsNameRefMakeRef5).makeStmt();
            } else if (esModules) {
                JsNameRef jsNameRefMakeRef6 = jsNameComponent2.makeRef();
                Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef6, "makeRef(...)");
                jsExport = new JsExport(jsNameRefMakeRef6, new JsName(exportedRegularClass.getName(), false));
            } else {
                jsExport = null;
            }
            List<ExportedDeclaration> members = exportedRegularClass.getMembers();
            ArrayList arrayList6 = new ArrayList();
            for (Object obj : members) {
                ExportedDeclaration exportedDeclaration = (ExportedDeclaration) obj;
                if (exportedDeclaration instanceof ExportedFunction) {
                    ExportedFunction exportedFunction2 = (ExportedFunction) exportedDeclaration;
                    if (exportedFunction2.isStatic() && !ES6ConstructorLoweringKt.isEs6ConstructorReplacement(exportedFunction2.getIr())) {
                        arrayList6.add(obj);
                    }
                }
            }
            Object objEmptyList = exportedRegularClass.getIr().getIsInner() ? null : arrayList6;
            if (objEmptyList == null) {
                objEmptyList = CollectionsKt.emptyList();
            }
            List<ExportedDeclaration> members2 = exportedRegularClass.getMembers();
            ArrayList arrayList7 = new ArrayList();
            for (Object obj2 : members2) {
                ExportedDeclaration exportedDeclaration2 = (ExportedDeclaration) obj2;
                if ((exportedDeclaration2 instanceof ExportedProperty) && ((ExportedProperty) exportedDeclaration2).isStatic()) {
                    arrayList7.add(obj2);
                }
            }
            ArrayList arrayList8 = arrayList7;
            List<ExportedClass> nestedClasses2 = exportedRegularClass.getNestedClasses();
            ArrayList arrayList9 = new ArrayList();
            for (Object obj3 : nestedClasses2) {
                if (((ExportedClass) obj3).getIr().getIsInner()) {
                    arrayList9.add(obj3);
                }
            }
            ArrayList arrayList10 = arrayList9;
            ArrayList arrayList11 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList10, 10));
            Iterator it3 = arrayList10.iterator();
            while (it3.hasNext()) {
                arrayList11.add(generateInnerClassAssignment((ExportedClass) it3.next(), jsNameComponent2));
            }
            ArrayList arrayList12 = arrayList11;
            List listPlus = CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) objEmptyList, (Iterable) arrayList8), (Iterable) exportedRegularClass.getNestedClasses());
            ArrayList arrayList13 = new ArrayList();
            Iterator it4 = listPlus.iterator();
            while (it4.hasNext()) {
                CollectionsKt.addAll(arrayList13, generateDeclarationExport((ExportedDeclaration) it4.next(), jsInvocationPrototypeOf, esModules, exportedRegularClass.getIr()));
            }
            return CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.listOfNotNull((Object[]) new JsStatement[]{jsStatementComponent3, jsExport}), (Iterable) arrayList13), (Iterable) arrayList12);
        }
        return CollectionsKt.emptyList();
    }

    private final JsVars.JsVar generateTopLevelGetters(ExportedProperty exportedProperty) {
        IrFunction irGetter = exportedProperty.getIrGetter();
        JsName nameForStaticDeclaration = irGetter != null ? this.staticContext.getNameForStaticDeclaration(irGetter) : null;
        IrFunction irSetter = exportedProperty.getIrSetter();
        JsName nameForStaticDeclaration2 = irSetter != null ? this.staticContext.getNameForStaticDeclaration(irSetter) : null;
        JsName jsName = new JsName(exportedProperty.getName(), false);
        JsObjectLiteral jsObjectLiteral = new JsObjectLiteral(false);
        if (nameForStaticDeclaration != null) {
            IrFunction irGetter2 = exportedProperty.getIrGetter();
            String str = Intrinsics.areEqual(irGetter2 != null ? irGetter2.getOrigin() : null, JsLoweredDeclarationOrigin.OBJECT_GET_INSTANCE_FUNCTION.INSTANCE) ? "getInstance" : "get";
            List<JsPropertyInitializer> propertyInitializers = jsObjectLiteral.getPropertyInitializers();
            Intrinsics.checkNotNullExpressionValue(propertyInitializers, "getPropertyInitializers(...)");
            propertyInitializers.add(new JsPropertyInitializer(new JsStringLiteral(str), nameForStaticDeclaration.makeRef()));
        }
        if (nameForStaticDeclaration2 != null) {
            List<JsPropertyInitializer> propertyInitializers2 = jsObjectLiteral.getPropertyInitializers();
            Intrinsics.checkNotNullExpressionValue(propertyInitializers2, "getPropertyInitializers(...)");
            propertyInitializers2.add(new JsPropertyInitializer(new JsStringLiteral(FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX), nameForStaticDeclaration2.makeRef()));
        }
        return new JsVars.JsVar(jsName, jsObjectLiteral);
    }

    private final JsStatement generateInnerClassAssignment(ExportedClass exportedClass, JsName jsName) {
        Iterable<ExportedFunction> iterableEmptyList;
        JsNameRef jsNameRefMakeRef;
        JsExpression classRef = IrTypeUtilsKt.getClassRef(exportedClass.getIr(), this.staticContext);
        JsNameRef jsNameRefMakeRef2 = jsName.makeRef();
        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef2, "makeRef(...)");
        IrClass irClassCompanionObject = AdditionalIrUtilsKt.companionObject(exportedClass.getIr());
        List<ExportedDeclaration> members = exportedClass.getMembers();
        if (members.isEmpty()) {
            iterableEmptyList = CollectionsKt.emptyList();
        } else {
            SmartList smartList = new SmartList();
            for (Object obj : members) {
                if ((obj instanceof ExportedFunction) && ((ExportedFunction) obj).isStatic()) {
                    smartList.add(obj);
                }
            }
            iterableEmptyList = smartList;
        }
        JsName jsName2 = new JsName("__bind_constructor_", false);
        List listMutableListOf = CollectionsKt.mutableListOf(new JsVars(new JsVars.JsVar(jsName2, bindToThis(classRef, classRef))));
        if (irClassCompanionObject != null) {
            String identifier = AnnotationUtilsKt.getJsNameOrKotlinName(irClassCompanionObject).getIdentifier();
            Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
            JsStatement jsStatementMakeStmt = JsAstUtilsKt.jsAssignment(new JsNameRef(identifier, jsName2.makeRef()), new JsNameRef(identifier, classRef)).makeStmt();
            Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
            listMutableListOf.add(jsStatementMakeStmt);
        }
        for (ExportedFunction exportedFunction : iterableEmptyList) {
            if (ES6ConstructorLoweringKt.isEs6ConstructorReplacement(exportedFunction.getIr())) {
                jsNameRefMakeRef = new JsNameRef(this.staticContext.getNameForMemberFunction(exportedFunction.getIr()), classRef);
            } else {
                jsNameRefMakeRef = this.staticContext.getNameForStaticDeclaration(exportedFunction.getIr()).makeRef();
                Intrinsics.checkNotNull(jsNameRefMakeRef);
            }
            JsStatement jsStatementMakeStmt2 = JsAstUtilsKt.jsAssignment(new JsNameRef(exportedFunction.getName(), jsName2.makeRef()), bindToThis(jsNameRefMakeRef, classRef)).makeStmt();
            Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt2, "makeStmt(...)");
            listMutableListOf.add(jsStatementMakeStmt2);
        }
        listMutableListOf.add(new JsReturn(jsName2.makeRef()));
        JsScope emptyScope = JsGenerationContextKt.getEmptyScope();
        JsStatement[] jsStatementArr = (JsStatement[]) listMutableListOf.toArray(new JsStatement[0]);
        JsStatement jsStatementMakeStmt3 = JsAstUtilsKt.defineProperty(JsAstUtilsKt.prototypeOf(jsNameRefMakeRef2, this.staticContext), exportedClass.getName(), new JsFunction(emptyScope, new JsBlock((JsStatement[]) Arrays.copyOf(jsStatementArr, jsStatementArr.length)), "inner class '" + exportedClass.getName() + "' getter"), null, this.staticContext).makeStmt();
        Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt3, "makeStmt(...)");
        return jsStatementMakeStmt3;
    }

    private final Pair<JsName, JsStatement> getNameAndInitialization(ExportedClass exportedClass) {
        JsExpression classRef = IrTypeUtilsKt.getClassRef(exportedClass.getIr(), this.staticContext);
        if (!(classRef instanceof JsNameRef)) {
            JsName jsName = new JsName(exportedClass.getName(), true);
            return TuplesKt.to(jsName, new JsVars(new JsVars.JsVar(jsName, classRef)));
        }
        JsName name2 = ((JsNameRef) classRef).getName();
        Intrinsics.checkNotNull(name2);
        return TuplesKt.to(name2, null);
    }

    private final JsInvocation bindToThis(JsExpression jsExpression, JsExpression jsExpression2) {
        return new JsInvocation(new JsNameRef("bind", jsExpression), jsExpression2, new JsThisRef());
    }
}
