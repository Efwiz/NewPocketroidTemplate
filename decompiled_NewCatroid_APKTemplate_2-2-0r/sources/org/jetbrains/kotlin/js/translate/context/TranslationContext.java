package org.jetbrains.kotlin.js.translate.context;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.android.gms.cast.MediaTrack;
import com.reandroid.apk.PathMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassOrPackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorUtilKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsClassModel;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsImportedModule;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsProgram;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.backend.ast.metadata.LocalAlias;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SpecialFunction;
import org.jetbrains.kotlin.js.config.JsConfig;
import org.jetbrains.kotlin.js.descriptorUtils.DescriptorUtilsKt;
import org.jetbrains.kotlin.js.naming.NameSuggestion;
import org.jetbrains.kotlin.js.naming.SuggestedName;
import org.jetbrains.kotlin.js.sourceMap.SourceFilePathResolver;
import org.jetbrains.kotlin.js.translate.declaration.ClassModelGenerator;
import org.jetbrains.kotlin.js.translate.intrinsic.Intrinsics;
import org.jetbrains.kotlin.js.translate.reference.CallExpressionTranslator;
import org.jetbrains.kotlin.js.translate.reference.ReferenceTranslator;
import org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.js.translate.utils.UtilsKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueKindUtilsKt;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExtensionReceiver;
import org.jetbrains.kotlin.serialization.js.ModuleKind;
import org.jetbrains.kotlin.types.expressions.PreliminaryDeclarationVisitor;

/* JADX INFO: loaded from: classes5.dex */
public class TranslationContext {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final AliasingContext aliasingContext;
    private final ClassDescriptor classDescriptor;
    private final ValueParameterDescriptor continuationParameterDescriptor;
    private final DeclarationDescriptor declarationDescriptor;
    private final DynamicContext dynamicContext;
    private final Map<JsExpression, TemporaryConstVariable> expressionToTempConstVariableCache;
    private InlineFunctionContext inlineFunctionContext;
    private final TranslationContext parent;
    private final StaticContext staticContext;
    private final UsageTracker usageTracker;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 4:
            case 12:
            case 17:
            case 19:
            case 21:
            case 22:
            case 24:
            case 26:
            case 28:
            case 30:
            case 32:
            case 35:
            case 37:
            case 39:
            case 41:
            case 47:
            case 49:
            case 51:
            case 52:
            case 54:
            case 56:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 66:
            case 72:
            case 75:
            case 76:
            case 77:
            case 81:
            case 85:
            case 87:
            case 92:
            case 93:
            case 94:
            case 96:
            case 97:
            case 105:
            case 110:
            case 114:
            case 115:
            case 119:
            case 120:
            case 122:
            case 123:
            case 124:
            case 126:
            case 127:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 4:
            case 12:
            case 17:
            case 19:
            case 21:
            case 22:
            case 24:
            case 26:
            case 28:
            case 30:
            case 32:
            case 35:
            case 37:
            case 39:
            case 41:
            case 47:
            case 49:
            case 51:
            case 52:
            case 54:
            case 56:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 66:
            case 72:
            case 75:
            case 76:
            case 77:
            case 81:
            case 85:
            case 87:
            case 92:
            case 93:
            case 94:
            case 96:
            case 97:
            case 105:
            case 110:
            case 114:
            case 115:
            case 119:
            case 120:
            case 122:
            case 123:
            case 124:
            case 126:
            case 127:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 2:
                objArr[0] = "dynamicContext";
                break;
            case 3:
                objArr[0] = "aliasingContext";
                break;
            case 4:
            case 12:
            case 17:
            case 19:
            case 21:
            case 22:
            case 24:
            case 26:
            case 28:
            case 30:
            case 32:
            case 35:
            case 37:
            case 39:
            case 41:
            case 47:
            case 49:
            case 51:
            case 52:
            case 54:
            case 56:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 66:
            case 72:
            case 75:
            case 76:
            case 77:
            case 81:
            case 85:
            case 87:
            case 92:
            case 93:
            case 94:
            case 96:
            case 97:
            case 105:
            case 110:
            case 114:
            case 115:
            case 119:
            case 120:
            case 122:
            case 123:
            case 124:
            case 126:
            case 127:
                objArr[0] = "org/jetbrains/kotlin/js/translate/context/TranslationContext";
                break;
            case 5:
            case 6:
            case 7:
                objArr[0] = "fun";
                break;
            case 8:
            case 9:
            case 10:
            case 13:
            case 20:
            case 23:
            case 27:
            case 29:
            case 31:
            case 33:
            case 38:
            case 40:
            case 42:
            case 46:
            case 48:
            case 65:
            case 73:
            case 74:
            case 82:
            case 83:
            case 84:
            case 88:
            case 91:
            case 95:
            case 101:
            case 104:
            case 108:
            case 109:
            case 111:
                objArr[0] = "descriptor";
                break;
            case 11:
            case 70:
                objArr[0] = "block";
                break;
            case 14:
                objArr[0] = "correspondingDescriptor";
                break;
            case 15:
                objArr[0] = PathMap.NAME_alias;
                break;
            case 16:
            case 18:
                objArr[0] = "aliases";
                break;
            case 25:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 34:
            case 36:
                objArr[0] = "intrinsicName";
                break;
            case 43:
            case 55:
            case 57:
            case 112:
                objArr[0] = "expression";
                break;
            case 44:
                objArr[0] = "expectedModuleName";
                break;
            case 45:
                objArr[0] = "reexportExpr";
                break;
            case 50:
            case 125:
                objArr[0] = "property";
                break;
            case 53:
                objArr[0] = "initExpression";
                break;
            case 67:
            case 102:
            case 103:
                objArr[0] = "statement";
                break;
            case 68:
                objArr[0] = "statements";
                break;
            case 69:
            case 71:
                objArr[0] = "context";
                break;
            case 78:
                objArr[0] = TypedValues.TransitionType.S_FROM;
                break;
            case 79:
                objArr[0] = "to";
                break;
            case 80:
                objArr[0] = "qualifier";
                break;
            case 86:
                objArr[0] = "name";
                break;
            case 89:
                objArr[0] = "closure";
                break;
            case 90:
                objArr[0] = "classOrConstructor";
                break;
            case 98:
            case 99:
                objArr[0] = JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME;
                break;
            case 100:
                objArr[0] = "invocationArgs";
                break;
            case 106:
                objArr[0] = MediaTrack.ROLE_DESCRIPTION;
                break;
            case 107:
                objArr[0] = "classDescriptor";
                break;
            case 113:
            case 118:
                objArr[0] = "value";
                break;
            case 116:
                objArr[0] = "suggestedName";
                break;
            case 117:
            case 128:
                objArr[0] = "tag";
                break;
            case 121:
                objArr[0] = "function";
                break;
            default:
                objArr[0] = "staticContext";
                break;
        }
        switch (i) {
            case 4:
                objArr[1] = "dynamicContext";
                break;
            case 12:
                objArr[1] = "innerBlock";
                break;
            case 17:
                objArr[1] = "innerContextWithAliasesForExpressions";
                break;
            case 19:
                objArr[1] = "innerContextWithDescriptorsAliased";
                break;
            case 21:
                objArr[1] = "bindingContext";
                break;
            case 22:
                objArr[1] = "bindingTrace";
                break;
            case 24:
                objArr[1] = "getScopeForDescriptor";
                break;
            case 26:
                objArr[1] = "getNameForElement";
                break;
            case 28:
                objArr[1] = "getNameForDescriptor";
                break;
            case 30:
                objArr[1] = "getInnerNameForDescriptor";
                break;
            case 32:
                objArr[1] = "getInlineableInnerNameForDescriptor";
                break;
            case 35:
                objArr[1] = "getReferenceToIntrinsic";
                break;
            case 37:
                objArr[1] = "getNameForIntrinsic";
                break;
            case 39:
                objArr[1] = "getNameForObjectInstance";
                break;
            case 41:
                objArr[1] = "getQualifiedReference";
                break;
            case 47:
                objArr[1] = "getInnerReference";
                break;
            case 49:
                objArr[1] = "createInlineLocalImportExpression";
                break;
            case 51:
                objArr[1] = "getNameForBackingField";
                break;
            case 52:
                objArr[1] = "declareTemporary";
                break;
            case 54:
                objArr[1] = "defineTemporary";
                break;
            case 56:
                objArr[1] = "cacheExpressionIfNeeded";
                break;
            case 58:
                objArr[1] = "getOrDeclareTemporaryConstVariable";
                break;
            case 59:
                objArr[1] = "namer";
                break;
            case 60:
                objArr[1] = "intrinsics";
                break;
            case 61:
                objArr[1] = ConfigConstants.CONFIG_KEY_PROGRAM;
                break;
            case 62:
                objArr[1] = "getConfig";
                break;
            case 63:
                objArr[1] = "scope";
                break;
            case 64:
                objArr[1] = "aliasingContext";
                break;
            case 66:
                objArr[1] = "getFunctionObject";
                break;
            case 72:
                objArr[1] = "getCurrentBlock";
                break;
            case 75:
            case 76:
            case 77:
                objArr[1] = "getDispatchReceiver";
                break;
            case 81:
                objArr[1] = "getDispatchReceiverPath";
                break;
            case 85:
                objArr[1] = "getCapturedTypeName";
                break;
            case 87:
                objArr[1] = "getCapturedReference";
                break;
            case 92:
            case 93:
            case 94:
                objArr[1] = "getArgumentForClosureConstructor";
                break;
            case 96:
                objArr[1] = "getTypeArgumentForClosureConstructor";
                break;
            case 97:
                objArr[1] = "endDeclaration";
                break;
            case 105:
                objArr[1] = "createRootScopedFunction";
                break;
            case 110:
                objArr[1] = "getCurrentModule";
                break;
            case 114:
            case 115:
            case 119:
            case 120:
                objArr[1] = "declareConstantValue";
                break;
            case 122:
            case 123:
                objArr[1] = "getNameForSpecialFunction";
                break;
            case 124:
                objArr[1] = "getSourceFilePathResolver";
                break;
            case 126:
                objArr[1] = "getVariableForPropertyMetadata";
                break;
            case 127:
                objArr[1] = "getLanguageVersionSettings";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/js/translate/context/TranslationContext";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
                objArr[2] = "<init>";
                break;
            case 4:
            case 12:
            case 17:
            case 19:
            case 21:
            case 22:
            case 24:
            case 26:
            case 28:
            case 30:
            case 32:
            case 35:
            case 37:
            case 39:
            case 41:
            case 47:
            case 49:
            case 51:
            case 52:
            case 54:
            case 56:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 66:
            case 72:
            case 75:
            case 76:
            case 77:
            case 81:
            case 85:
            case 87:
            case 92:
            case 93:
            case 94:
            case 96:
            case 97:
            case 105:
            case 110:
            case 114:
            case 115:
            case 119:
            case 120:
            case 122:
            case 123:
            case 124:
            case 126:
            case 127:
                break;
            case 5:
                objArr[2] = "contextWithScope";
                break;
            case 6:
                objArr[2] = "newFunctionBody";
                break;
            case 7:
            case 8:
                objArr[2] = "newFunctionBodyWithUsageTracker";
                break;
            case 9:
                objArr[2] = "innerWithUsageTracker";
                break;
            case 10:
                objArr[2] = "inner";
                break;
            case 11:
                objArr[2] = "innerBlock";
                break;
            case 13:
                objArr[2] = "newDeclaration";
                break;
            case 14:
            case 15:
                objArr[2] = "innerContextWithAliased";
                break;
            case 16:
                objArr[2] = "innerContextWithAliasesForExpressions";
                break;
            case 18:
                objArr[2] = "innerContextWithDescriptorsAliased";
                break;
            case 20:
                objArr[2] = "getBlockForDescriptor";
                break;
            case 23:
                objArr[2] = "getScopeForDescriptor";
                break;
            case 25:
                objArr[2] = "getNameForElement";
                break;
            case 27:
                objArr[2] = "getNameForDescriptor";
                break;
            case 29:
                objArr[2] = "getInnerNameForDescriptor";
                break;
            case 31:
                objArr[2] = "getInlineableInnerNameForDescriptor";
                break;
            case 33:
                objArr[2] = "createInlineableInnerNameForDescriptor";
                break;
            case 34:
                objArr[2] = "getReferenceToIntrinsic";
                break;
            case 36:
                objArr[2] = "getNameForIntrinsic";
                break;
            case 38:
                objArr[2] = "getNameForObjectInstance";
                break;
            case 40:
                objArr[2] = "getQualifiedReference";
                break;
            case 42:
                objArr[2] = "isInlineFunction";
                break;
            case 43:
            case 44:
            case 45:
                objArr[2] = "replaceModuleReference";
                break;
            case 46:
                objArr[2] = "getInnerReference";
                break;
            case 48:
                objArr[2] = "createInlineLocalImportExpression";
                break;
            case 50:
                objArr[2] = "getNameForBackingField";
                break;
            case 53:
                objArr[2] = "defineTemporary";
                break;
            case 55:
                objArr[2] = "cacheExpressionIfNeeded";
                break;
            case 57:
                objArr[2] = "getOrDeclareTemporaryConstVariable";
                break;
            case 65:
                objArr[2] = "getFunctionObject";
                break;
            case 67:
                objArr[2] = "addStatementToCurrentBlock";
                break;
            case 68:
                objArr[2] = "addStatementsToCurrentBlock";
                break;
            case 69:
            case 70:
                objArr[2] = "addStatementsToCurrentBlockFrom";
                break;
            case 71:
                objArr[2] = "moveVarsFrom";
                break;
            case 73:
                objArr[2] = "getAliasForDescriptor";
                break;
            case 74:
                objArr[2] = "getDispatchReceiver";
                break;
            case 78:
            case 79:
            case 80:
                objArr[2] = "getDispatchReceiverPath";
                break;
            case 82:
                objArr[2] = "captureIfNeedAndGetCapturedName";
                break;
            case 83:
                objArr[2] = "captureTypeIfNeedAndGetCapturedName";
                break;
            case 84:
                objArr[2] = "getCapturedTypeName";
                break;
            case 86:
                objArr[2] = "getCapturedReference";
                break;
            case 88:
            case 89:
                objArr[2] = "putClassOrConstructorClosure";
                break;
            case 90:
                objArr[2] = "getClassOrConstructorClosure";
                break;
            case 91:
                objArr[2] = "getArgumentForClosureConstructor";
                break;
            case 95:
                objArr[2] = "getTypeArgumentForClosureConstructor";
                break;
            case 98:
                objArr[2] = "shouldBeDeferred";
                break;
            case 99:
            case 100:
                objArr[2] = "deferConstructorCall";
                break;
            case 101:
                objArr[2] = "addInlineCall";
                break;
            case 102:
                objArr[2] = "addDeclarationStatement";
                break;
            case 103:
                objArr[2] = "addTopLevelStatement";
                break;
            case 104:
            case 106:
                objArr[2] = "createRootScopedFunction";
                break;
            case 107:
                objArr[2] = "addClass";
                break;
            case 108:
                objArr[2] = "export";
                break;
            case 109:
                objArr[2] = "isFromCurrentModule";
                break;
            case 111:
            case 112:
            case 113:
            case 116:
            case 117:
            case 118:
                objArr[2] = "declareConstantValue";
                break;
            case 121:
                objArr[2] = "getNameForSpecialFunction";
                break;
            case 125:
                objArr[2] = "getVariableForPropertyMetadata";
                break;
            case 128:
                objArr[2] = "reportInlineFunctionTag";
                break;
            default:
                objArr[2] = "rootContext";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 4:
            case 12:
            case 17:
            case 19:
            case 21:
            case 22:
            case 24:
            case 26:
            case 28:
            case 30:
            case 32:
            case 35:
            case 37:
            case 39:
            case 41:
            case 47:
            case 49:
            case 51:
            case 52:
            case 54:
            case 56:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 66:
            case 72:
            case 75:
            case 76:
            case 77:
            case 81:
            case 85:
            case 87:
            case 92:
            case 93:
            case 94:
            case 96:
            case 97:
            case 105:
            case 110:
            case 114:
            case 115:
            case 119:
            case 120:
            case 122:
            case 123:
            case 124:
            case 126:
            case 127:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    public static TranslationContext rootContext(StaticContext staticContext) {
        if (staticContext == null) {
            $$$reportNull$$$0(0);
        }
        return new TranslationContext(null, staticContext, DynamicContext.rootContext(staticContext.getFragment().getScope(), staticContext.getFragment().getInitializerBlock()), AliasingContext.getCleanContext(), null, null);
    }

    private TranslationContext(TranslationContext translationContext, StaticContext staticContext, DynamicContext dynamicContext, AliasingContext aliasingContext, UsageTracker usageTracker, DeclarationDescriptor declarationDescriptor) {
        if (staticContext == null) {
            $$$reportNull$$$0(1);
        }
        if (dynamicContext == null) {
            $$$reportNull$$$0(2);
        }
        if (aliasingContext == null) {
            $$$reportNull$$$0(3);
        }
        this.expressionToTempConstVariableCache = new HashMap();
        this.parent = translationContext;
        this.dynamicContext = dynamicContext;
        this.staticContext = staticContext;
        this.aliasingContext = aliasingContext;
        this.usageTracker = usageTracker;
        this.declarationDescriptor = declarationDescriptor;
        if (declarationDescriptor instanceof ClassDescriptor) {
            this.classDescriptor = (ClassDescriptor) declarationDescriptor;
        } else {
            this.classDescriptor = translationContext != null ? translationContext.classDescriptor : null;
        }
        this.continuationParameterDescriptor = calculateContinuationParameter();
        this.inlineFunctionContext = translationContext != null ? translationContext.inlineFunctionContext : null;
        if ((translationContext != null ? translationContext.declarationDescriptor : null) != declarationDescriptor && (declarationDescriptor instanceof CallableDescriptor) && InlineUtil.isInline(declarationDescriptor)) {
            this.inlineFunctionContext = new InlineFunctionContext((CallableDescriptor) declarationDescriptor);
        }
    }

    private ValueParameterDescriptor calculateContinuationParameter() {
        TranslationContext translationContext = this.parent;
        if (translationContext != null && translationContext.declarationDescriptor == this.declarationDescriptor) {
            return translationContext.continuationParameterDescriptor;
        }
        DeclarationDescriptor declarationDescriptor = this.declarationDescriptor;
        if (!(declarationDescriptor instanceof FunctionDescriptor)) {
            return null;
        }
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) declarationDescriptor;
        if (!functionDescriptor.isSuspend()) {
            return null;
        }
        return new ValueParameterDescriptorImpl(functionDescriptor, null, functionDescriptor.getValueParameters().size(), Annotations.INSTANCE.getEMPTY(), Name.identifier("continuation"), DescriptorUtilKt.findContinuationClassDescriptor(getCurrentModule(), NoLookupLocation.FROM_BACKEND).getDefaultType(), false, false, false, null, SourceElement.NO_SOURCE);
    }

    public UsageTracker usageTracker() {
        return this.usageTracker;
    }

    public DynamicContext dynamicContext() {
        DynamicContext dynamicContext = this.dynamicContext;
        if (dynamicContext == null) {
            $$$reportNull$$$0(4);
        }
        return dynamicContext;
    }

    public InlineFunctionContext getInlineFunctionContext() {
        return this.inlineFunctionContext;
    }

    public TranslationContext contextWithScope(JsFunction jsFunction) {
        if (jsFunction == null) {
            $$$reportNull$$$0(5);
        }
        return newFunctionBody(jsFunction, this.aliasingContext, this.declarationDescriptor);
    }

    private TranslationContext newFunctionBody(JsFunction jsFunction, AliasingContext aliasingContext, DeclarationDescriptor declarationDescriptor) {
        if (jsFunction == null) {
            $$$reportNull$$$0(6);
        }
        DynamicContext dynamicContextNewContext = DynamicContext.newContext(jsFunction.getScope(), jsFunction.getBody());
        if (aliasingContext == null) {
            aliasingContext = this.aliasingContext.inner();
        }
        return new TranslationContext(this, this.staticContext, dynamicContextNewContext, aliasingContext, this.usageTracker, declarationDescriptor);
    }

    public TranslationContext newFunctionBodyWithUsageTracker(JsFunction jsFunction, MemberDescriptor memberDescriptor) {
        if (jsFunction == null) {
            $$$reportNull$$$0(7);
        }
        if (memberDescriptor == null) {
            $$$reportNull$$$0(8);
        }
        return new TranslationContext(this, this.staticContext, DynamicContext.newContext(jsFunction.getScope(), jsFunction.getBody()), this.aliasingContext.inner(), new UsageTracker(this.usageTracker, memberDescriptor, bindingContext()), memberDescriptor);
    }

    public TranslationContext innerWithUsageTracker(MemberDescriptor memberDescriptor) {
        if (memberDescriptor == null) {
            $$$reportNull$$$0(9);
        }
        return new TranslationContext(this, this.staticContext, this.dynamicContext, this.aliasingContext.inner(), new UsageTracker(this.usageTracker, memberDescriptor, bindingContext()), memberDescriptor);
    }

    public TranslationContext inner(MemberDescriptor memberDescriptor) {
        if (memberDescriptor == null) {
            $$$reportNull$$$0(10);
        }
        return new TranslationContext(this, this.staticContext, this.dynamicContext, this.aliasingContext.inner(), this.usageTracker, memberDescriptor);
    }

    public TranslationContext innerBlock(JsBlock jsBlock) {
        if (jsBlock == null) {
            $$$reportNull$$$0(11);
        }
        return new TranslationContext(this, this.staticContext, this.dynamicContext.innerBlock(jsBlock), this.aliasingContext, this.usageTracker, this.declarationDescriptor);
    }

    public TranslationContext innerBlock() {
        TranslationContext translationContextInnerBlock = innerBlock(new JsBlock());
        if (translationContextInnerBlock == null) {
            $$$reportNull$$$0(12);
        }
        return translationContextInnerBlock;
    }

    public TranslationContext newDeclaration(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(13);
        }
        JsBlock blockForDescriptor = getBlockForDescriptor(declarationDescriptor);
        if (blockForDescriptor == null) {
            blockForDescriptor = this.dynamicContext.jsBlock();
        }
        return new TranslationContext(this, this.staticContext, DynamicContext.newContext(getScopeForDescriptor(declarationDescriptor), blockForDescriptor), this.aliasingContext, this.usageTracker, declarationDescriptor);
    }

    private TranslationContext innerWithAliasingContext(AliasingContext aliasingContext) {
        return new TranslationContext(this, this.staticContext, this.dynamicContext, aliasingContext, this.usageTracker, this.declarationDescriptor);
    }

    public TranslationContext innerContextWithAliased(DeclarationDescriptor declarationDescriptor, JsExpression jsExpression) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(14);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(15);
        }
        return innerWithAliasingContext(this.aliasingContext.inner(declarationDescriptor, jsExpression));
    }

    public TranslationContext innerContextWithAliasesForExpressions(Map<KtExpression, JsExpression> map) {
        if (map == null) {
            $$$reportNull$$$0(16);
        }
        return map.isEmpty() ? this : innerWithAliasingContext(this.aliasingContext.withExpressionsAliased(map));
    }

    public TranslationContext innerContextWithDescriptorsAliased(Map<DeclarationDescriptor, JsExpression> map) {
        if (map == null) {
            $$$reportNull$$$0(18);
        }
        return map.isEmpty() ? this : innerWithAliasingContext(this.aliasingContext.withDescriptorsAliased(map));
    }

    private JsBlock getBlockForDescriptor(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(20);
        }
        if (declarationDescriptor instanceof CallableDescriptor) {
            return getFunctionObject((CallableDescriptor) declarationDescriptor).getBody();
        }
        return null;
    }

    public ClassDescriptor getClassDescriptor() {
        return this.classDescriptor;
    }

    public BindingContext bindingContext() {
        BindingContext bindingContext = this.staticContext.getBindingContext();
        if (bindingContext == null) {
            $$$reportNull$$$0(21);
        }
        return bindingContext;
    }

    public BindingTrace bindingTrace() {
        BindingTrace bindingTrace = this.staticContext.getBindingTrace();
        if (bindingTrace == null) {
            $$$reportNull$$$0(22);
        }
        return bindingTrace;
    }

    public JsScope getScopeForDescriptor(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(23);
        }
        JsScope scopeForDescriptor = this.staticContext.getScopeForDescriptor(declarationDescriptor);
        if (scopeForDescriptor == null) {
            $$$reportNull$$$0(24);
        }
        return scopeForDescriptor;
    }

    public JsName getNameForElement(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(25);
        }
        JsName nameForDescriptor = getNameForDescriptor(BindingUtils.getDescriptorForElement(bindingContext(), psiElement));
        if (nameForDescriptor == null) {
            $$$reportNull$$$0(26);
        }
        return nameForDescriptor;
    }

    public JsName getNameForDescriptor(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(27);
        }
        JsName nameForDescriptor = this.staticContext.getNameForDescriptor(declarationDescriptor);
        if (nameForDescriptor == null) {
            $$$reportNull$$$0(28);
        }
        return nameForDescriptor;
    }

    public JsName getInnerNameForDescriptor(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(29);
        }
        JsName innerNameForDescriptor = this.staticContext.getInnerNameForDescriptor(declarationDescriptor);
        if (innerNameForDescriptor == null) {
            $$$reportNull$$$0(30);
        }
        return innerNameForDescriptor;
    }

    public JsName getInlineableInnerNameForDescriptor(DeclarationDescriptor declarationDescriptor) {
        JsName innerNameForDescriptor;
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(31);
        }
        if (this.inlineFunctionContext == null || !isPublicInlineFunction() || !DescriptorUtilsKt.shouldBeExported(declarationDescriptor, getConfig()) || DescriptorUtils.isAncestor(this.inlineFunctionContext.getDescriptor(), declarationDescriptor, false)) {
            innerNameForDescriptor = getInnerNameForDescriptor(declarationDescriptor);
        } else {
            String tag = this.staticContext.getTag(declarationDescriptor);
            innerNameForDescriptor = this.inlineFunctionContext.getImports().get(tag);
            if (innerNameForDescriptor == null) {
                innerNameForDescriptor = createInlineableInnerNameForDescriptor(declarationDescriptor);
                this.inlineFunctionContext.getImports().put(tag, innerNameForDescriptor);
            }
        }
        if (innerNameForDescriptor == null) {
            $$$reportNull$$$0(32);
        }
        return innerNameForDescriptor;
    }

    private JsName createInlineableInnerNameForDescriptor(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(33);
        }
        JsExpression jsExpressionCreateInlineLocalImportExpression = createInlineLocalImportExpression(declarationDescriptor);
        if (jsExpressionCreateInlineLocalImportExpression instanceof JsNameRef) {
            JsNameRef jsNameRef = (JsNameRef) jsExpressionCreateInlineLocalImportExpression;
            if (jsNameRef.getQualifier() == null && jsNameRef.getIdent().equals(Namer.getRootPackageName()) && ((declarationDescriptor instanceof PackageFragmentDescriptor) || (declarationDescriptor instanceof ModuleDescriptor))) {
                return jsNameRef.getName();
            }
        }
        JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(StaticContext.getSuggestedName(declarationDescriptor));
        if (isFromCurrentModule(declarationDescriptor) && !AnnotationsUtils.isNativeObject(declarationDescriptor)) {
            MetadataProperties.setLocalAlias(jsNameDeclareTemporaryName, new LocalAlias(getInnerNameForDescriptor(declarationDescriptor), this.staticContext.getTag(declarationDescriptor)));
        }
        MetadataProperties.setDescriptor(jsNameDeclareTemporaryName, declarationDescriptor);
        MetadataProperties.setStaticRef(jsNameDeclareTemporaryName, jsExpressionCreateInlineLocalImportExpression);
        MetadataProperties.setImported(jsNameDeclareTemporaryName, true);
        this.inlineFunctionContext.getImportBlock().getStatements().add(JsAstUtils.newVar(jsNameDeclareTemporaryName, jsExpressionCreateInlineLocalImportExpression));
        return jsNameDeclareTemporaryName;
    }

    public JsExpression getReferenceToIntrinsic(String str) {
        if (str == null) {
            $$$reportNull$$$0(34);
        }
        JsNameRef jsNameRefPureFqn = JsAstUtils.pureFqn(getNameForIntrinsic(str), (JsExpression) null);
        if (jsNameRefPureFqn == null) {
            $$$reportNull$$$0(35);
        }
        return jsNameRefPureFqn;
    }

    public JsName getNameForIntrinsic(final String str) {
        JsName nameForIntrinsic;
        if (str == null) {
            $$$reportNull$$$0(36);
        }
        if (this.inlineFunctionContext == null || !isPublicInlineFunction()) {
            nameForIntrinsic = this.staticContext.getNameForIntrinsic(str);
        } else {
            nameForIntrinsic = this.inlineFunctionContext.getImports().computeIfAbsent("intrinsic:" + str, new Function() { // from class: org.jetbrains.kotlin.js.translate.context.TranslationContext$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.m7298x61ade6cb(str, (String) obj);
                }
            });
        }
        if (nameForIntrinsic == null) {
            $$$reportNull$$$0(37);
        }
        return nameForIntrinsic;
    }

    /* JADX INFO: renamed from: lambda$getNameForIntrinsic$0$org-jetbrains-kotlin-js-translate-context-TranslationContext, reason: not valid java name */
    /* synthetic */ JsName m7298x61ade6cb(String str, String str2) {
        JsExpression intrinsicFqn = TranslationUtils.getIntrinsicFqn(str);
        JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(NameSuggestion.sanitizeName(str));
        MetadataProperties.setImported(jsNameDeclareTemporaryName, true);
        this.inlineFunctionContext.getImportBlock().getStatements().add(JsAstUtils.newVar(jsNameDeclareTemporaryName, intrinsicFqn));
        return jsNameDeclareTemporaryName;
    }

    public JsName getNameForObjectInstance(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(38);
        }
        JsName nameForObjectInstance = this.staticContext.getNameForObjectInstance(classDescriptor);
        if (nameForObjectInstance == null) {
            $$$reportNull$$$0(39);
        }
        return nameForObjectInstance;
    }

    public JsExpression getQualifiedReference(DeclarationDescriptor declarationDescriptor) {
        JsExpression jsExpressionExportModuleForInline;
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(40);
        }
        JsExpression qualifiedReference = this.staticContext.getQualifiedReference(declarationDescriptor);
        if (isPublicInlineFunction()) {
            if (isFromCurrentModule(declarationDescriptor)) {
                if (declarationDescriptor instanceof MemberDescriptor) {
                    this.staticContext.export((MemberDescriptor) declarationDescriptor, true);
                }
            } else {
                ModuleDescriptor containingModule = DescriptorUtils.getContainingModule(declarationDescriptor);
                if (containingModule != this.staticContext.getCurrentModule() && !isInlineFunction(declarationDescriptor) && (jsExpressionExportModuleForInline = this.staticContext.exportModuleForInline(containingModule)) != null) {
                    qualifiedReference = replaceModuleReference(qualifiedReference, getInnerNameForDescriptor(containingModule), jsExpressionExportModuleForInline);
                }
            }
        }
        if (qualifiedReference == null) {
            $$$reportNull$$$0(41);
        }
        return qualifiedReference;
    }

    private boolean isInlineFunction(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(42);
        }
        if (declarationDescriptor instanceof CallableDescriptor) {
            return CallExpressionTranslator.shouldBeInlined((CallableDescriptor) declarationDescriptor, this);
        }
        return false;
    }

    private static JsExpression replaceModuleReference(JsExpression jsExpression, JsName jsName, JsExpression jsExpression2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(43);
        }
        if (jsName == null) {
            $$$reportNull$$$0(44);
        }
        if (jsExpression2 == null) {
            $$$reportNull$$$0(45);
        }
        if (jsExpression instanceof JsNameRef) {
            JsNameRef jsNameRef = (JsNameRef) jsExpression;
            if (jsNameRef.getQualifier() == null) {
                return jsName == jsNameRef.getName() ? jsExpression2 : jsExpression;
            }
            JsExpression jsExpressionReplaceModuleReference = replaceModuleReference(jsNameRef.getQualifier(), jsName, jsExpression2);
            if (jsExpressionReplaceModuleReference == jsNameRef.getQualifier()) {
                return jsExpression;
            }
            if (jsNameRef.getName() != null) {
                jsExpression = new JsNameRef(jsNameRef.getName(), jsExpressionReplaceModuleReference);
            } else {
                jsExpression = new JsNameRef(jsNameRef.getIdent(), jsExpressionReplaceModuleReference);
            }
            jsExpression.copyMetadataFrom(jsNameRef);
        }
        return jsExpression;
    }

    public JsExpression getInnerReference(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(46);
        }
        JsNameRef jsNameRefPureFqn = JsAstUtils.pureFqn(getInlineableInnerNameForDescriptor(declarationDescriptor), (JsExpression) null);
        if (jsNameRefPureFqn == null) {
            $$$reportNull$$$0(47);
        }
        return jsNameRefPureFqn;
    }

    private JsExpression createInlineLocalImportExpression(DeclarationDescriptor declarationDescriptor) {
        String fileModuleName;
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(48);
        }
        JsExpression qualifiedReference = getQualifiedReference(declarationDescriptor);
        JsName innerNameForDescriptor = getInnerNameForDescriptor(declarationDescriptor);
        SuggestedName suggestedNameSuggestName = this.staticContext.suggestName(declarationDescriptor);
        if (suggestedNameSuggestName != null && getConfig().getModuleKind() != ModuleKind.PLAIN && isPublicInlineFunction()) {
            String moduleName = AnnotationsUtils.getModuleName(suggestedNameSuggestName.getDescriptor());
            if (moduleName != null) {
                qualifiedReference = replaceModuleReference(qualifiedReference, innerNameForDescriptor, this.staticContext.exportModuleForInline(moduleName, new JsImportedModule(moduleName, innerNameForDescriptor, null)));
            } else if (AnnotationsUtils.isNativeObject(suggestedNameSuggestName.getDescriptor()) && DescriptorUtils.isTopLevelDeclaration(suggestedNameSuggestName.getDescriptor()) && (fileModuleName = AnnotationsUtils.getFileModuleName(bindingContext(), suggestedNameSuggestName.getDescriptor())) != null) {
                JsImportedModule importedModule = this.staticContext.getImportedModule(fileModuleName, null);
                qualifiedReference = replaceModuleReference(this.staticContext.getQualifiedReference(declarationDescriptor), importedModule.getInternalName(), this.staticContext.exportModuleForInline(fileModuleName, importedModule));
            }
        }
        if (qualifiedReference == null) {
            $$$reportNull$$$0(49);
        }
        return qualifiedReference;
    }

    public JsName getNameForBackingField(VariableDescriptorWithAccessors variableDescriptorWithAccessors) {
        if (variableDescriptorWithAccessors == null) {
            $$$reportNull$$$0(50);
        }
        JsName nameForBackingField = this.staticContext.getNameForBackingField(variableDescriptorWithAccessors);
        if (nameForBackingField == null) {
            $$$reportNull$$$0(51);
        }
        return nameForBackingField;
    }

    public TemporaryVariable declareTemporary(JsExpression jsExpression, Object obj) {
        TemporaryVariable temporaryVariableDeclareTemporary = this.dynamicContext.declareTemporary(jsExpression, obj);
        if (temporaryVariableDeclareTemporary == null) {
            $$$reportNull$$$0(52);
        }
        return temporaryVariableDeclareTemporary;
    }

    public JsExpression defineTemporary(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(53);
        }
        TemporaryVariable temporaryVariableDeclareTemporary = this.dynamicContext.declareTemporary(jsExpression, jsExpression.getSource());
        addStatementToCurrentBlock(temporaryVariableDeclareTemporary.assignmentStatement());
        JsNameRef jsNameRefReference = temporaryVariableDeclareTemporary.reference();
        if (jsNameRefReference == null) {
            $$$reportNull$$$0(54);
        }
        return jsNameRefReference;
    }

    public JsExpression cacheExpressionIfNeeded(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(55);
        }
        if (TranslationUtils.isCacheNeeded(jsExpression)) {
            jsExpression = defineTemporary(jsExpression);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(56);
        }
        return jsExpression;
    }

    public TemporaryConstVariable getOrDeclareTemporaryConstVariable(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(57);
        }
        TemporaryConstVariable temporaryConstVariable = this.expressionToTempConstVariableCache.get(jsExpression);
        if (temporaryConstVariable == null) {
            TemporaryVariable temporaryVariableDeclareTemporary = declareTemporary(jsExpression, jsExpression.getSource());
            TemporaryConstVariable temporaryConstVariable2 = new TemporaryConstVariable(temporaryVariableDeclareTemporary.name(), temporaryVariableDeclareTemporary.assignmentExpression());
            this.expressionToTempConstVariableCache.put(jsExpression, temporaryConstVariable2);
            this.expressionToTempConstVariableCache.put(temporaryVariableDeclareTemporary.assignmentExpression(), temporaryConstVariable2);
            temporaryConstVariable = temporaryConstVariable2;
        }
        if (temporaryConstVariable == null) {
            $$$reportNull$$$0(58);
        }
        return temporaryConstVariable;
    }

    public Namer namer() {
        Namer namer = this.staticContext.getNamer();
        if (namer == null) {
            $$$reportNull$$$0(59);
        }
        return namer;
    }

    public Intrinsics intrinsics() {
        Intrinsics intrinsics = this.staticContext.getIntrinsics();
        if (intrinsics == null) {
            $$$reportNull$$$0(60);
        }
        return intrinsics;
    }

    public JsProgram program() {
        JsProgram program = this.staticContext.getProgram();
        if (program == null) {
            $$$reportNull$$$0(61);
        }
        return program;
    }

    public JsConfig getConfig() {
        JsConfig config = this.staticContext.getConfig();
        if (config == null) {
            $$$reportNull$$$0(62);
        }
        return config;
    }

    public JsScope scope() {
        JsScope scope = this.dynamicContext.getScope();
        if (scope == null) {
            $$$reportNull$$$0(63);
        }
        return scope;
    }

    public AliasingContext aliasingContext() {
        AliasingContext aliasingContext = this.aliasingContext;
        if (aliasingContext == null) {
            $$$reportNull$$$0(64);
        }
        return aliasingContext;
    }

    public JsFunction getFunctionObject(CallableDescriptor callableDescriptor) {
        if (callableDescriptor == null) {
            $$$reportNull$$$0(65);
        }
        JsFunction functionWithScope = this.staticContext.getFunctionWithScope(callableDescriptor);
        if (functionWithScope == null) {
            $$$reportNull$$$0(66);
        }
        return functionWithScope;
    }

    public void addStatementToCurrentBlock(JsStatement jsStatement) {
        if (jsStatement == null) {
            $$$reportNull$$$0(67);
        }
        this.dynamicContext.jsBlock().getStatements().add(jsStatement);
    }

    public void addStatementsToCurrentBlock(Collection<JsStatement> collection) {
        if (collection == null) {
            $$$reportNull$$$0(68);
        }
        this.dynamicContext.jsBlock().getStatements().addAll(collection);
    }

    public void addStatementsToCurrentBlockFrom(TranslationContext translationContext) {
        if (translationContext == null) {
            $$$reportNull$$$0(69);
        }
        addStatementsToCurrentBlockFrom(translationContext.dynamicContext().jsBlock());
    }

    public void addStatementsToCurrentBlockFrom(JsBlock jsBlock) {
        if (jsBlock == null) {
            $$$reportNull$$$0(70);
        }
        this.dynamicContext.jsBlock().getStatements().addAll(jsBlock.getStatements());
    }

    public boolean currentBlockIsEmpty() {
        return this.dynamicContext.jsBlock().isEmpty();
    }

    public void moveVarsFrom(TranslationContext translationContext) {
        if (translationContext == null) {
            $$$reportNull$$$0(71);
        }
        this.dynamicContext.moveVarsFrom(translationContext.dynamicContext());
    }

    public JsBlock getCurrentBlock() {
        JsBlock jsBlock = this.dynamicContext.jsBlock();
        if (jsBlock == null) {
            $$$reportNull$$$0(72);
        }
        return jsBlock;
    }

    public JsExpression getAliasForDescriptor(DeclarationDescriptor declarationDescriptor) {
        JsExpression jsExpressionCaptureIfNeedAndGetCapturedName;
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(73);
        }
        if (this.continuationParameterDescriptor != declarationDescriptor && (jsExpressionCaptureIfNeedAndGetCapturedName = captureIfNeedAndGetCapturedName(declarationDescriptor)) != null) {
            return jsExpressionCaptureIfNeedAndGetCapturedName;
        }
        JsExpression aliasForDescriptor = this.aliasingContext.getAliasForDescriptor(declarationDescriptor);
        if (aliasForDescriptor != null) {
            return aliasForDescriptor.deepCopy();
        }
        return null;
    }

    public JsExpression getDispatchReceiver(ReceiverParameterDescriptor receiverParameterDescriptor) {
        if (receiverParameterDescriptor == null) {
            $$$reportNull$$$0(74);
        }
        JsExpression aliasForDescriptor = getAliasForDescriptor(receiverParameterDescriptor);
        if (aliasForDescriptor != null) {
            if (aliasForDescriptor == null) {
                $$$reportNull$$$0(75);
            }
            return aliasForDescriptor;
        }
        if (DescriptorUtilsKt.isCoroutineLambda(receiverParameterDescriptor.getPackageFragment())) {
            JsNameRef jsNameRef = new JsNameRef("$$controller$$", JsAstUtils.stateMachineReceiver());
            MetadataProperties.setCoroutineController(jsNameRef, true);
            return jsNameRef;
        }
        if (DescriptorUtils.isObject(receiverParameterDescriptor.getPackageFragment())) {
            if (isConstructorOrDirectScope(receiverParameterDescriptor.getPackageFragment())) {
                return new JsThisRef();
            }
            JsExpression jsExpressionTranslateAsValueReference = ReferenceTranslator.translateAsValueReference((ClassDescriptor) receiverParameterDescriptor.getPackageFragment(), this);
            if (jsExpressionTranslateAsValueReference == null) {
                $$$reportNull$$$0(77);
            }
            return jsExpressionTranslateAsValueReference;
        }
        if (receiverParameterDescriptor.getValue() instanceof ExtensionReceiver) {
            return new JsThisRef();
        }
        return getDispatchReceiverPath(this.classDescriptor, (ClassDescriptor) receiverParameterDescriptor.getValue().getType().getConstructor().getThis$0(), new JsThisRef());
    }

    private boolean isConstructorOrDirectScope(DeclarationDescriptor declarationDescriptor) {
        return declarationDescriptor == DescriptorUtils.getParentOfType(this.declarationDescriptor, ClassDescriptor.class, false);
    }

    private JsExpression getDispatchReceiverPath(ClassDescriptor classDescriptor, ClassDescriptor classDescriptor2, JsExpression jsExpression) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(78);
        }
        if (classDescriptor2 == null) {
            $$$reportNull$$$0(79);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(80);
        }
        while (true) {
            JsExpression aliasForDescriptor = getAliasForDescriptor(classDescriptor.getThisAsReceiverParameter());
            if (aliasForDescriptor != null) {
                jsExpression = aliasForDescriptor;
            }
            if (classDescriptor == classDescriptor2 || !classDescriptor.getIsInner() || !(classDescriptor.getPackageFragment() instanceof ClassDescriptor)) {
                break;
            }
            JsNameRef jsNameRef = new JsNameRef(Namer.OUTER_FIELD_NAME, jsExpression);
            classDescriptor = (ClassDescriptor) classDescriptor.getPackageFragment();
            jsExpression = jsNameRef;
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(81);
        }
        return jsExpression;
    }

    private JsExpression captureIfNeedAndGetCapturedName(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(82);
        }
        UsageTracker usageTracker = this.usageTracker;
        if (usageTracker == null) {
            return null;
        }
        usageTracker.used(declarationDescriptor);
        JsName nameForCapturedDescriptor = UsageTrackerKt.getNameForCapturedDescriptor(this.usageTracker, declarationDescriptor);
        if (nameForCapturedDescriptor != null) {
            return getCapturedReference(nameForCapturedDescriptor);
        }
        return null;
    }

    public JsExpression captureTypeIfNeedAndGetCapturedName(TypeParameterDescriptor typeParameterDescriptor) {
        if (typeParameterDescriptor == null) {
            $$$reportNull$$$0(83);
        }
        UsageTracker usageTracker = this.usageTracker;
        if (usageTracker == null) {
            return null;
        }
        usageTracker.used(typeParameterDescriptor);
        JsName jsName = this.usageTracker.getCapturedTypes().get(typeParameterDescriptor);
        if (jsName != null) {
            return getCapturedReference(jsName);
        }
        return null;
    }

    public JsName getCapturedTypeName(TypeParameterDescriptor typeParameterDescriptor) {
        if (typeParameterDescriptor == null) {
            $$$reportNull$$$0(84);
        }
        UsageTracker usageTracker = this.usageTracker;
        JsName nameForDescriptor = usageTracker != null ? usageTracker.getCapturedTypes().get(typeParameterDescriptor) : null;
        if (nameForDescriptor == null) {
            nameForDescriptor = getNameForDescriptor(typeParameterDescriptor);
        }
        if (nameForDescriptor == null) {
            $$$reportNull$$$0(85);
        }
        return nameForDescriptor;
    }

    private JsExpression getCapturedReference(JsName jsName) {
        JsNameRef jsNameRefMakeRef;
        if (jsName == null) {
            $$$reportNull$$$0(86);
        }
        if (shouldCaptureViaThis()) {
            JsExpression jsThisRef = new JsThisRef();
            int outerLocalClassDepth = getOuterLocalClassDepth();
            int i = 0;
            while (i < outerLocalClassDepth) {
                i++;
                jsThisRef = new JsNameRef(Namer.OUTER_FIELD_NAME, jsThisRef);
            }
            jsNameRefMakeRef = new JsNameRef(jsName, jsThisRef);
        } else {
            jsNameRefMakeRef = jsName.makeRef();
        }
        if (jsNameRefMakeRef == null) {
            $$$reportNull$$$0(87);
        }
        return jsNameRefMakeRef;
    }

    private int getOuterLocalClassDepth() {
        UsageTracker usageTracker = this.usageTracker;
        if (usageTracker == null) {
            return 0;
        }
        MemberDescriptor containingDescriptor = usageTracker.getContainingDescriptor();
        if (!(containingDescriptor instanceof ClassDescriptor)) {
            return 0;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) containingDescriptor;
        ClassDescriptor classDescriptor2 = this.classDescriptor;
        if (classDescriptor2 == null) {
            return 0;
        }
        int i = 0;
        while (classDescriptor2 != classDescriptor) {
            DeclarationDescriptor containingDeclaration = classDescriptor2.getPackageFragment();
            if (!(containingDeclaration instanceof ClassDescriptor)) {
                return 0;
            }
            classDescriptor2 = (ClassDescriptor) containingDeclaration;
            i++;
        }
        return i;
    }

    private boolean shouldCaptureViaThis() {
        DeclarationDescriptor declarationDescriptor = this.declarationDescriptor;
        if (declarationDescriptor == null || DescriptorUtils.isDescriptorWithLocalVisibility(declarationDescriptor)) {
            return false;
        }
        DeclarationDescriptor declarationDescriptor2 = this.declarationDescriptor;
        return ((declarationDescriptor2 instanceof ConstructorDescriptor) && DescriptorUtils.isDescriptorWithLocalVisibility(declarationDescriptor2.getPackageFragment())) ? false : true;
    }

    public DeclarationDescriptor getDeclarationDescriptor() {
        return this.declarationDescriptor;
    }

    public void putClassOrConstructorClosure(MemberDescriptor memberDescriptor, List<DeclarationDescriptor> list) {
        if (memberDescriptor == null) {
            $$$reportNull$$$0(88);
        }
        if (list == null) {
            $$$reportNull$$$0(89);
        }
        this.staticContext.putClassOrConstructorClosure(memberDescriptor, list);
    }

    public List<DeclarationDescriptor> getClassOrConstructorClosure(MemberDescriptor memberDescriptor) {
        if (memberDescriptor == null) {
            $$$reportNull$$$0(90);
        }
        if (memberDescriptor instanceof TypeAliasConstructorDescriptor) {
            return getClassOrConstructorClosure(((TypeAliasConstructorDescriptor) memberDescriptor).getUnderlyingConstructorDescriptor());
        }
        List<DeclarationDescriptor> classOrConstructorClosure = this.staticContext.getClassOrConstructorClosure(memberDescriptor);
        return (classOrConstructorClosure == null && (memberDescriptor instanceof ConstructorDescriptor) && ((ConstructorDescriptor) memberDescriptor).isPrimary()) ? this.staticContext.getClassOrConstructorClosure((ClassDescriptor) memberDescriptor.getPackageFragment()) : classOrConstructorClosure;
    }

    public JsExpression getArgumentForClosureConstructor(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(91);
        }
        JsExpression aliasForDescriptor = getAliasForDescriptor(declarationDescriptor);
        if (aliasForDescriptor != null) {
            if (aliasForDescriptor == null) {
                $$$reportNull$$$0(92);
            }
            return aliasForDescriptor;
        }
        if (declarationDescriptor instanceof ReceiverParameterDescriptor) {
            JsExpression dispatchReceiver = getDispatchReceiver((ReceiverParameterDescriptor) declarationDescriptor);
            if (dispatchReceiver == null) {
                $$$reportNull$$$0(93);
            }
            return dispatchReceiver;
        }
        if (DescriptorUtilsKt.isCoroutineLambda(declarationDescriptor)) {
            return new JsThisRef();
        }
        JsNameRef jsNameRefMakeRef = getNameForDescriptor(declarationDescriptor).makeRef();
        if (jsNameRefMakeRef == null) {
            $$$reportNull$$$0(94);
        }
        return jsNameRefMakeRef;
    }

    public JsExpression getTypeArgumentForClosureConstructor(TypeParameterDescriptor typeParameterDescriptor) {
        JsName jsName;
        if (typeParameterDescriptor == null) {
            $$$reportNull$$$0(95);
        }
        UsageTracker usageTracker = this.usageTracker;
        JsNameRef jsNameRefMakeRef = (usageTracker == null || (jsName = usageTracker.getCapturedTypes().get(typeParameterDescriptor)) == null) ? null : jsName.makeRef();
        if (jsNameRefMakeRef == null) {
            jsNameRefMakeRef = getNameForDescriptor(typeParameterDescriptor).makeRef();
        }
        if (jsNameRefMakeRef == null) {
            $$$reportNull$$$0(96);
        }
        return jsNameRefMakeRef;
    }

    public JsName getOuterClassReference(ClassDescriptor classDescriptor) {
        if ((classDescriptor.getPackageFragment() instanceof ClassDescriptor) && classDescriptor.getIsInner()) {
            return this.staticContext.getScopeForDescriptor(classDescriptor).declareName(Namer.OUTER_FIELD_NAME);
        }
        return null;
    }

    public void startDeclaration() {
        ClassDescriptor classDescriptor = this.classDescriptor;
        if (classDescriptor == null || (classDescriptor.getPackageFragment() instanceof ClassOrPackageFragmentDescriptor)) {
            return;
        }
        this.staticContext.getDeferredCallSites().put(classDescriptor, new ArrayList());
    }

    public List<DeferredCallSite> endDeclaration() {
        List<DeferredCallSite> listRemove = this.classDescriptor != null ? this.staticContext.getDeferredCallSites().remove(this.classDescriptor) : null;
        if (listRemove == null) {
            listRemove = Collections.emptyList();
        }
        if (listRemove == null) {
            $$$reportNull$$$0(97);
        }
        return listRemove;
    }

    public boolean shouldBeDeferred(ClassConstructorDescriptor classConstructorDescriptor) {
        if (classConstructorDescriptor == null) {
            $$$reportNull$$$0(98);
        }
        return this.staticContext.getDeferredCallSites().containsKey(classConstructorDescriptor.getPackageFragment());
    }

    private boolean isValWithWriterInDifferentScope(VariableDescriptor variableDescriptor) {
        if (!(variableDescriptor instanceof LocalVariableDescriptor)) {
            return false;
        }
        PreliminaryDeclarationVisitor visitorByVariable = PreliminaryDeclarationVisitor.INSTANCE.getVisitorByVariable(variableDescriptor, bindingContext());
        return visitorByVariable == null || !DataFlowValueKindUtilsKt.hasNoWritersInClosures(variableDescriptor.getPackageFragment(), visitorByVariable.writers(variableDescriptor), bindingContext());
    }

    public boolean isBoxedLocalCapturedInClosure(CallableDescriptor callableDescriptor) {
        if (!BindingContextUtils.isCapturedInClosure(bindingContext(), callableDescriptor)) {
            return false;
        }
        VariableDescriptor variableDescriptor = (VariableDescriptor) callableDescriptor;
        return variableDescriptor.isVar() || isValWithWriterInDifferentScope(variableDescriptor);
    }

    public void deferConstructorCall(ClassConstructorDescriptor classConstructorDescriptor, List<JsExpression> list) {
        if (classConstructorDescriptor == null) {
            $$$reportNull$$$0(99);
        }
        if (list == null) {
            $$$reportNull$$$0(100);
        }
        List<DeferredCallSite> list2 = this.staticContext.getDeferredCallSites().get(classConstructorDescriptor.getPackageFragment());
        if (list2 == null) {
            throw new IllegalStateException("This method should be call only when `shouldBeDeferred` method reports true for given constructor: " + classConstructorDescriptor);
        }
        list2.add(new DeferredCallSite(classConstructorDescriptor, list, this));
    }

    public void addInlineCall(CallableDescriptor callableDescriptor) {
        if (callableDescriptor == null) {
            $$$reportNull$$$0(101);
        }
        this.staticContext.addInlineCall(callableDescriptor);
    }

    public void addDeclarationStatement(JsStatement jsStatement) {
        if (jsStatement == null) {
            $$$reportNull$$$0(102);
        }
        InlineFunctionContext inlineFunctionContext = this.inlineFunctionContext;
        if (inlineFunctionContext != null) {
            inlineFunctionContext.getDeclarationsBlock().getStatements().add(jsStatement);
        } else {
            this.staticContext.getDeclarationStatements().add(jsStatement);
        }
    }

    public void addTopLevelStatement(JsStatement jsStatement) {
        if (jsStatement == null) {
            $$$reportNull$$$0(103);
        }
        this.staticContext.getTopLevelStatements().add(jsStatement);
    }

    public JsFunction createRootScopedFunction(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(104);
        }
        JsFunction jsFunctionCreateRootScopedFunction = createRootScopedFunction(declarationDescriptor.toString());
        if (jsFunctionCreateRootScopedFunction == null) {
            $$$reportNull$$$0(105);
        }
        return jsFunctionCreateRootScopedFunction;
    }

    public JsFunction createRootScopedFunction(String str) {
        if (str == null) {
            $$$reportNull$$$0(106);
        }
        return new JsFunction(this.staticContext.getFragment().getScope(), new JsBlock(), str);
    }

    public void addClass(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(107);
        }
        if (this.inlineFunctionContext != null) {
            JsClassModel jsClassModelGenerateClassModel = new ClassModelGenerator(this).generateClassModel(classDescriptor);
            List<JsStatement> statements = this.inlineFunctionContext.getPrototypeBlock().getStatements();
            JsName superName = jsClassModelGenerateClassModel.getSuperName();
            if (superName != null) {
                statements.addAll(UtilsKt.createPrototypeStatements(superName, jsClassModelGenerateClassModel.getName()));
            }
            statements.addAll(jsClassModelGenerateClassModel.getPostDeclarationBlock().getStatements());
            return;
        }
        this.staticContext.addClass(classDescriptor);
    }

    public void export(MemberDescriptor memberDescriptor) {
        if (memberDescriptor == null) {
            $$$reportNull$$$0(108);
        }
        this.staticContext.export(memberDescriptor, false);
    }

    public boolean isFromCurrentModule(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(109);
        }
        return this.staticContext.getCurrentModule() == org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt.getModule(declarationDescriptor);
    }

    public boolean isPublicInlineFunction() {
        InlineFunctionContext inlineFunctionContext = this.inlineFunctionContext;
        if (inlineFunctionContext == null) {
            return false;
        }
        return DescriptorUtilsKt.shouldBeExported(inlineFunctionContext.getDescriptor(), getConfig());
    }

    public ValueParameterDescriptor getContinuationParameterDescriptor() {
        return this.continuationParameterDescriptor;
    }

    public ModuleDescriptor getCurrentModule() {
        ModuleDescriptor currentModule = this.staticContext.getCurrentModule();
        if (currentModule == null) {
            $$$reportNull$$$0(110);
        }
        return currentModule;
    }

    public TranslationContext getParent() {
        return this.parent;
    }

    public JsExpression declareConstantValue(DeclarationDescriptor declarationDescriptor, KtSimpleNameExpression ktSimpleNameExpression, JsExpression jsExpression) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(111);
        }
        if (ktSimpleNameExpression == null) {
            $$$reportNull$$$0(112);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(113);
        }
        if (!isPublicInlineFunction() && isFromCurrentModule(declarationDescriptor)) {
            JsExpression jsExpressionTranslateSimpleName = ReferenceTranslator.translateSimpleName(ktSimpleNameExpression, this);
            if (jsExpressionTranslateSimpleName == null) {
                $$$reportNull$$$0(114);
            }
            return jsExpressionTranslateSimpleName;
        }
        JsExpression jsExpressionDeclareConstantValue = declareConstantValue(StaticContext.getSuggestedName(declarationDescriptor), (String) Objects.requireNonNull(this.staticContext.getTag(declarationDescriptor)), jsExpression, declarationDescriptor);
        if (jsExpressionDeclareConstantValue == null) {
            $$$reportNull$$$0(115);
        }
        return jsExpressionDeclareConstantValue;
    }

    public JsExpression declareConstantValue(final String str, final String str2, final JsExpression jsExpression, final DeclarationDescriptor declarationDescriptor) {
        if (str == null) {
            $$$reportNull$$$0(116);
        }
        if (str2 == null) {
            $$$reportNull$$$0(117);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(118);
        }
        if (this.inlineFunctionContext == null || !isPublicInlineFunction()) {
            JsNameRef jsNameRefMakeRef = this.staticContext.importDeclaration(str, str2, jsExpression).makeRef();
            if (jsNameRefMakeRef == null) {
                $$$reportNull$$$0(119);
            }
            return jsNameRefMakeRef;
        }
        JsNameRef jsNameRefMakeRef2 = this.inlineFunctionContext.getImports().computeIfAbsent(str2, new Function() { // from class: org.jetbrains.kotlin.js.translate.context.TranslationContext$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m7297xa892364a(str, declarationDescriptor, str2, jsExpression, (String) obj);
            }
        }).makeRef();
        if (jsNameRefMakeRef2 == null) {
            $$$reportNull$$$0(120);
        }
        return jsNameRefMakeRef2;
    }

    /* JADX INFO: renamed from: lambda$declareConstantValue$1$org-jetbrains-kotlin-js-translate-context-TranslationContext, reason: not valid java name */
    /* synthetic */ JsName m7297xa892364a(String str, DeclarationDescriptor declarationDescriptor, String str2, JsExpression jsExpression, String str3) {
        JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(str);
        MetadataProperties.setImported(jsNameDeclareTemporaryName, true);
        if (declarationDescriptor != null && isFromCurrentModule(declarationDescriptor) && !AnnotationsUtils.isNativeObject(declarationDescriptor)) {
            MetadataProperties.setLocalAlias(jsNameDeclareTemporaryName, new LocalAlias(getInnerNameForDescriptor(declarationDescriptor), str2));
        }
        this.inlineFunctionContext.getImportBlock().getStatements().add(JsAstUtils.newVar(jsNameDeclareTemporaryName, jsExpression));
        return jsNameDeclareTemporaryName;
    }

    public JsName getNameForSpecialFunction(final SpecialFunction specialFunction) {
        if (specialFunction == null) {
            $$$reportNull$$$0(121);
        }
        if (this.inlineFunctionContext == null || !isPublicInlineFunction()) {
            JsName nameForSpecialFunction = this.staticContext.getNameForSpecialFunction(specialFunction);
            if (nameForSpecialFunction == null) {
                $$$reportNull$$$0(122);
            }
            return nameForSpecialFunction;
        }
        JsName jsNameComputeIfAbsent = this.inlineFunctionContext.getImports().computeIfAbsent(TranslationUtils.getTagForSpecialFunction(specialFunction), new Function() { // from class: org.jetbrains.kotlin.js.translate.context.TranslationContext$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m7299x9e98e21d(specialFunction, (String) obj);
            }
        });
        if (jsNameComputeIfAbsent == null) {
            $$$reportNull$$$0(123);
        }
        return jsNameComputeIfAbsent;
    }

    /* JADX INFO: renamed from: lambda$getNameForSpecialFunction$2$org-jetbrains-kotlin-js-translate-context-TranslationContext, reason: not valid java name */
    /* synthetic */ JsName m7299x9e98e21d(SpecialFunction specialFunction, String str) {
        JsExpression jsExpressionCreateSpecialFunction = Namer.createSpecialFunction(specialFunction);
        JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(specialFunction.getSuggestedName());
        MetadataProperties.setImported(jsNameDeclareTemporaryName, true);
        MetadataProperties.setSpecialFunction(jsNameDeclareTemporaryName, specialFunction);
        this.inlineFunctionContext.getImportBlock().getStatements().add(JsAstUtils.newVar(jsNameDeclareTemporaryName, jsExpressionCreateSpecialFunction));
        return jsNameDeclareTemporaryName;
    }

    public SourceFilePathResolver getSourceFilePathResolver() {
        SourceFilePathResolver sourceFilePathResolver = this.staticContext.getSourceFilePathResolver();
        if (sourceFilePathResolver == null) {
            $$$reportNull$$$0(124);
        }
        return sourceFilePathResolver;
    }

    public JsName getVariableForPropertyMetadata(VariableDescriptorWithAccessors variableDescriptorWithAccessors) {
        if (variableDescriptorWithAccessors == null) {
            $$$reportNull$$$0(125);
        }
        JsName variableForPropertyMetadata = this.staticContext.getVariableForPropertyMetadata(variableDescriptorWithAccessors);
        if (variableForPropertyMetadata == null) {
            $$$reportNull$$$0(126);
        }
        return variableForPropertyMetadata;
    }

    public LanguageVersionSettings getLanguageVersionSettings() {
        LanguageVersionSettings languageVersionSettings = CommonConfigurationKeysKt.getLanguageVersionSettings(this.staticContext.getConfig().getConfiguration());
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(127);
        }
        return languageVersionSettings;
    }

    public void reportInlineFunctionTag(String str) {
        if (str == null) {
            $$$reportNull$$$0(128);
        }
        this.staticContext.reportInlineFunctionTag(str);
    }
}
