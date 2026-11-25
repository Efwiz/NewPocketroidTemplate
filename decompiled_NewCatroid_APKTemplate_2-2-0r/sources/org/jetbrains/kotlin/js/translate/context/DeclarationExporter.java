package org.jetbrains.kotlin.js.translate.context;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsObjectLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsPropertyInitializer;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.config.JsConfig;
import org.jetbrains.kotlin.js.descriptorUtils.DescriptorUtilsKt;
import org.jetbrains.kotlin.js.naming.NameSuggestion;
import org.jetbrains.kotlin.js.naming.SuggestedName;
import org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.JsDescriptorUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.js.translate.utils.UtilsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.inline.InlineOnlyKt;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;

/* JADX INFO: compiled from: DeclarationExporter.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0016\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\u001eJ\u0018\u0010\u001f\u001a\u00020\u00172\u0006\u0010 \u001a\u00020!2\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0018\u0010\"\u001a\u00020\u00172\u0006\u0010 \u001a\u00020#2\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u000e\u0010$\u001a\u00020\r2\u0006\u0010%\u001a\u00020\fJ\u0014\u0010&\u001a\u00020'*\u00020\u001b2\u0006\u0010 \u001a\u00020\u0019H\u0002J\u0014\u0010(\u001a\u00020\u001e*\u00020\t2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00128BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/context/DeclarationExporter;", "", "context", "Lorg/jetbrains/kotlin/js/translate/context/StaticContext;", "(Lorg/jetbrains/kotlin/js/translate/context/StaticContext;)V", "getContext", "()Lorg/jetbrains/kotlin/js/translate/context/StaticContext;", "exportedDeclarations", "", "Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "localPackageNames", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "objectLikeKinds", "", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "statements", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "getStatements", "()Ljava/util/List;", "assign", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "qualifier", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "export", "force", "", "exportObject", "declaration", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "exportProperty", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getLocalPackageName", "packageName", "exportStatement", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpressionStatement;", "shouldBeExported", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeclarationExporter {
    private final StaticContext context;
    private final Set<MemberDescriptor> exportedDeclarations;
    private final Map<FqName, JsName> localPackageNames;
    private final Set<ClassKind> objectLikeKinds;

    public DeclarationExporter(StaticContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.objectLikeKinds = SetsKt.setOf((Object[]) new ClassKind[]{ClassKind.OBJECT, ClassKind.ENUM_ENTRY});
        this.exportedDeclarations = new LinkedHashSet();
        this.localPackageNames = new LinkedHashMap();
    }

    public final StaticContext getContext() {
        return this.context;
    }

    private final List<JsStatement> getStatements() {
        List<JsStatement> statements = this.context.getFragment().getExportBlock().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        return statements;
    }

    public final void export(MemberDescriptor descriptor, boolean force) {
        JsNameRef jsNameRefMakeRef;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (this.exportedDeclarations.contains(descriptor)) {
            return;
        }
        if ((descriptor instanceof ConstructorDescriptor) && ((ConstructorDescriptor) descriptor).isPrimary()) {
            return;
        }
        MemberDescriptor memberDescriptor = descriptor;
        if (AnnotationsUtils.isNativeObject(memberDescriptor) || AnnotationsUtils.isLibraryObject(memberDescriptor) || InlineOnlyKt.isEffectivelyInlineOnly(descriptor)) {
            return;
        }
        NameSuggestion nameSuggestion = this.context.getNameSuggestion();
        BindingContext bindingContext = this.context.getBindingContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
        SuggestedName suggestedNameSuggest = nameSuggestion.suggest(memberDescriptor, bindingContext);
        if (suggestedNameSuggest == null) {
            return;
        }
        DeclarationDescriptor scope = suggestedNameSuggest.getScope();
        if (shouldBeExported(descriptor, force)) {
            this.exportedDeclarations.add(descriptor);
            boolean z = scope instanceof PackageFragmentDescriptor;
            if (z) {
                jsNameRefMakeRef = getLocalPackageName(((PackageFragmentDescriptor) scope).getFqName()).makeRef();
            } else if (DescriptorUtils.isObject(scope)) {
                jsNameRefMakeRef = JsAstUtils.prototypeOf(this.context.getInnerNameForDescriptor(scope).makeRef());
            } else {
                jsNameRefMakeRef = this.context.getInnerNameForDescriptor(scope).makeRef();
            }
            Intrinsics.checkNotNull(jsNameRefMakeRef);
            if (descriptor instanceof ClassDescriptor) {
                ClassDescriptor classDescriptor = (ClassDescriptor) descriptor;
                if (this.objectLikeKinds.contains(classDescriptor.getClassKind())) {
                    exportObject(classDescriptor, jsNameRefMakeRef);
                    return;
                }
            }
            if ((descriptor instanceof PropertyDescriptor) && z) {
                exportProperty((PropertyDescriptor) descriptor, jsNameRefMakeRef);
            } else {
                assign(memberDescriptor, jsNameRefMakeRef);
            }
        }
    }

    private final void assign(DeclarationDescriptor descriptor, JsExpression qualifier) {
        JsName innerNameForDescriptor = this.context.getInnerNameForDescriptor(descriptor);
        Intrinsics.checkNotNullExpressionValue(innerNameForDescriptor, "getInnerNameForDescriptor(...)");
        JsNameRef jsNameRefMakeRef = innerNameForDescriptor.makeRef();
        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
        JsName nameForDescriptor = this.context.getNameForDescriptor(descriptor);
        Intrinsics.checkNotNullExpressionValue(nameForDescriptor, "getNameForDescriptor(...)");
        if (MetadataProperties.getStaticRef(nameForDescriptor) == null && !Intrinsics.areEqual(innerNameForDescriptor, nameForDescriptor)) {
            MetadataProperties.setStaticRef(nameForDescriptor, jsNameRefMakeRef);
        }
        List<JsStatement> statements = getStatements();
        JsBinaryOperation jsBinaryOperationAssignment = JsAstUtils.assignment(new JsNameRef(nameForDescriptor, qualifier), jsNameRefMakeRef);
        Intrinsics.checkNotNullExpressionValue(jsBinaryOperationAssignment, "assignment(...)");
        statements.add(exportStatement(jsBinaryOperationAssignment, descriptor));
    }

    private final void exportObject(ClassDescriptor declaration, JsExpression qualifier) {
        ClassDescriptor classDescriptor = declaration;
        JsName nameForDescriptor = this.context.getNameForDescriptor(classDescriptor);
        Intrinsics.checkNotNullExpressionValue(nameForDescriptor, "getNameForDescriptor(...)");
        JsExpression jsExpressionDefineGetter = JsAstUtils.defineGetter(qualifier, nameForDescriptor.getIdent(), this.context.getNameForObjectInstance(declaration).makeRef());
        Intrinsics.checkNotNullExpressionValue(jsExpressionDefineGetter, "defineGetter(...)");
        getStatements().add(exportStatement(jsExpressionDefineGetter, classDescriptor));
    }

    private final void exportProperty(PropertyDescriptor declaration, JsExpression qualifier) {
        JsNameRef jsFunction;
        JsNameRef jsNameRef;
        JsObjectLiteral jsObjectLiteral = new JsObjectLiteral(true);
        PropertyDescriptor propertyDescriptor = declaration;
        String ident = this.context.getNameForDescriptor(propertyDescriptor).getIdent();
        Intrinsics.checkNotNullExpressionValue(ident, "getIdent(...)");
        boolean z = JsDescriptorUtils.isSimpleFinalProperty(declaration) && !TranslationUtils.shouldAccessViaFunctions((CallableDescriptor) declaration);
        if (z) {
            JsName innerNameForDescriptor = this.context.getInnerNameForDescriptor(propertyDescriptor);
            Intrinsics.checkNotNullExpressionValue(innerNameForDescriptor, "getInnerNameForDescriptor(...)");
            jsFunction = new JsFunction(this.context.getFragment().getScope(), new JsBlock(new JsReturn(innerNameForDescriptor.makeRef())), declaration + " getter");
        } else {
            StaticContext staticContext = this.context;
            PropertyGetterDescriptor getter = declaration.getGetter();
            Intrinsics.checkNotNull(getter);
            JsName innerNameForDescriptor2 = staticContext.getInnerNameForDescriptor(getter);
            Intrinsics.checkNotNullExpressionValue(innerNameForDescriptor2, "getInnerNameForDescriptor(...)");
            JsNameRef jsNameRefMakeRef = innerNameForDescriptor2.makeRef();
            Intrinsics.checkNotNull(jsNameRefMakeRef);
            jsFunction = jsNameRefMakeRef;
        }
        List<JsPropertyInitializer> propertyInitializers = jsObjectLiteral.getPropertyInitializers();
        Intrinsics.checkNotNullExpressionValue(propertyInitializers, "getPropertyInitializers(...)");
        propertyInitializers.add(new JsPropertyInitializer(new JsNameRef("get"), jsFunction));
        if (declaration.isVar()) {
            if (z) {
                ArrayList arrayList = new ArrayList();
                JsFunction jsFunction2 = new JsFunction(this.context.getFragment().getScope(), new JsBlock(arrayList), declaration + " setter");
                SourceElement source = declaration.getSourceElement();
                Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
                jsFunction2.setSource(PsiSourceElementKt.getPsi(source));
                JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName("value");
                Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName, "declareTemporaryName(...)");
                List<JsParameter> parameters = jsFunction2.getParameters();
                Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
                parameters.add(new JsParameter(jsNameDeclareTemporaryName));
                arrayList.add(JsAstUtils.assignment(this.context.getInnerNameForDescriptor(propertyDescriptor).makeRef(), jsNameDeclareTemporaryName.makeRef()).makeStmt());
                jsNameRef = jsFunction2;
            } else {
                StaticContext staticContext2 = this.context;
                PropertySetterDescriptor setter = declaration.getSetter();
                Intrinsics.checkNotNull(setter);
                JsNameRef jsNameRefMakeRef2 = staticContext2.getInnerNameForDescriptor(setter).makeRef();
                Intrinsics.checkNotNull(jsNameRefMakeRef2);
                jsNameRef = jsNameRefMakeRef2;
            }
            List<JsPropertyInitializer> propertyInitializers2 = jsObjectLiteral.getPropertyInitializers();
            Intrinsics.checkNotNullExpressionValue(propertyInitializers2, "getPropertyInitializers(...)");
            propertyInitializers2.add(new JsPropertyInitializer(new JsNameRef(FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX), jsNameRef));
        }
        List<JsStatement> statements = getStatements();
        JsInvocation jsInvocationDefineProperty = JsAstUtils.defineProperty(qualifier, ident, jsObjectLiteral);
        Intrinsics.checkNotNullExpressionValue(jsInvocationDefineProperty, "defineProperty(...)");
        statements.add(exportStatement(jsInvocationDefineProperty, propertyDescriptor));
    }

    public final JsName getLocalPackageName(FqName packageName) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        if (packageName.isRoot()) {
            JsName jsNameDeclareName = this.context.getFragment().getScope().declareName(Namer.getRootPackageName());
            Intrinsics.checkNotNullExpressionValue(jsNameDeclareName, "declareName(...)");
            return jsNameDeclareName;
        }
        JsName jsName = this.localPackageNames.get(packageName);
        if (jsName != null) {
            return jsName;
        }
        JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName("package$" + packageName.shortName().asString());
        this.localPackageNames.put(packageName, jsNameDeclareTemporaryName);
        List<JsStatement> statements = getStatements();
        String strAsString = packageName.shortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        String strAsString2 = packageName.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
        FqName fqNameParent = packageName.parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        JsNameRef jsNameRefMakeRef = getLocalPackageName(fqNameParent).makeRef();
        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
        statements.add(UtilsKt.definePackageAlias(strAsString, jsNameDeclareTemporaryName, strAsString2, jsNameRefMakeRef));
        return jsNameDeclareTemporaryName;
    }

    private final JsExpressionStatement exportStatement(JsExpression jsExpression, DeclarationDescriptor declarationDescriptor) {
        JsExpressionStatement jsExpressionStatement = new JsExpressionStatement(jsExpression);
        MetadataProperties.setExportedTag(jsExpressionStatement, this.context.getTag(declarationDescriptor));
        return jsExpressionStatement;
    }

    private final boolean shouldBeExported(MemberDescriptor memberDescriptor, boolean z) {
        if (!z) {
            JsConfig config = this.context.getConfig();
            Intrinsics.checkNotNullExpressionValue(config, "getConfig(...)");
            if (!DescriptorUtilsKt.shouldBeExported(memberDescriptor, config)) {
                return false;
            }
        }
        return true;
    }
}
