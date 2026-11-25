package org.jetbrains.kotlin.js.translate.context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import org.antlr.stringtemplate.StringTemplate;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.com.google.common.collect.Sets;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.util.containers.hash.LinkedHashMap;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.js.backend.ast.JsArrayAccess;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsDynamicScope;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsImportedModule;
import org.jetbrains.kotlin.js.backend.ast.JsImportedModuleKey;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameBinding;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.backend.ast.JsProgram;
import org.jetbrains.kotlin.js.backend.ast.JsProgramFragment;
import org.jetbrains.kotlin.js.backend.ast.JsRootScope;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SideEffectKind;
import org.jetbrains.kotlin.js.backend.ast.metadata.SpecialFunction;
import org.jetbrains.kotlin.js.common.IdentifierPolicyKt;
import org.jetbrains.kotlin.js.config.JsConfig;
import org.jetbrains.kotlin.js.naming.NameSuggestion;
import org.jetbrains.kotlin.js.naming.SuggestedName;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsBuiltinNameClashChecker;
import org.jetbrains.kotlin.js.sourceMap.SourceFilePathResolver;
import org.jetbrains.kotlin.js.translate.context.generator.Generator;
import org.jetbrains.kotlin.js.translate.context.generator.Rule;
import org.jetbrains.kotlin.js.translate.declaration.ClassModelGenerator;
import org.jetbrains.kotlin.js.translate.intrinsic.Intrinsics;
import org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.JsDescriptorUtils;
import org.jetbrains.kotlin.js.translate.utils.SignatureUtilsKt;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.tasks.DynamicCallsKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;
import org.jetbrains.kotlin.serialization.js.ModuleKind;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: loaded from: classes5.dex */
public final class StaticContext {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Set<String> BUILTIN_JS_PROPERTIES = Sets.union(JsBuiltinNameClashChecker.PROHIBITED_MEMBER_NAMES, JsBuiltinNameClashChecker.PROHIBITED_STATIC_NAMES);
    private final Map<VariableDescriptorWithAccessors, JsName> backingFieldNameCache;
    private final BindingTrace bindingTrace;
    private final ClassModelGenerator classModelGenerator;
    private final Map<MemberDescriptor, List<DeclarationDescriptor>> classOrConstructorClosure;
    private final JsConfig config;
    private final ModuleDescriptor currentModule;
    private final JsImportedModule currentModuleAsImported;
    private final Map<ClassDescriptor, List<DeferredCallSite>> deferredCallSites;
    private final DeclarationExporter exporter;
    private final Map<DeclarationDescriptor, JsExpression> fqnCache;
    private final JsProgramFragment fragment;
    private final Map<JsImportedModuleKey, JsImportedModule> importedModules;
    private final Set<String> inlineFunctionTags;
    private final Generator<JsName> innerNames;
    private final Map<String, JsName> intrinsicNames;
    private final Intrinsics intrinsics;
    private final boolean isStdlib;
    private final Map<String, JsExpression> modulesImportedForInline;
    private final Map<DeclarationDescriptor, JsName> nameCache;
    private JsName nameForImportsForInline;
    private final NameSuggestion nameSuggestion;
    private final Namer namer;
    private final Generator<JsName> objectInstanceNames;
    private final Map<FqName, JsScope> packageScopes;
    private final JsProgram program;
    private final Map<VariableDescriptorWithAccessors, JsName> propertyMetadataVariables;
    private final JsScope rootScope;
    private final Map<JsScope, JsFunction> scopeToFunction;
    private final Generator<JsScope> scopes;
    private final SourceFilePathResolver sourceFilePathResolver;
    private final Map<SpecialFunction, JsName> specialFunctions;
    private final Map<DeclarationDescriptor, String> tagCache;

    /* JADX WARN: Code duplicated, block: B:18:0x0039 A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 13 && i != 14 && i != 36 && i != 37 && i != 45 && i != 46 && i != 78 && i != 79 && i != 81 && i != 82) {
            switch (i) {
                default:
                    switch (i) {
                        default:
                            switch (i) {
                                default:
                                    switch (i) {
                                        case 73:
                                        case 74:
                                        case 75:
                                            break;
                                        default:
                                            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                                            break;
                                    }
                                case 23:
                                case 24:
                                case 25:
                                case 26:
                                case 27:
                                case 28:
                                case 29:
                                case 30:
                                case 31:
                                case 32:
                                case 33:
                                case 34:
                                    str = "@NotNull method %s.%s must not return null";
                                    break;
                            }
                        case 16:
                        case 20:
                        case 39:
                        case 41:
                        case 43:
                        case 50:
                        case 54:
                        case 57:
                        case 59:
                        case 62:
                        case 67:
                        case 69:
                        case 86:
                        case 88:
                        case 90:
                        case 92:
                        case 95:
                            str = "@NotNull method %s.%s must not return null";
                            break;
                    }
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 13 && i != 14 && i != 36 && i != 37 && i != 45 && i != 46 && i != 78 && i != 79 && i != 81 && i != 82) {
            switch (i) {
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 16:
                        case 20:
                        case 39:
                        case 41:
                        case 43:
                        case 50:
                        case 54:
                        case 57:
                        case 59:
                        case 62:
                        case 67:
                        case 69:
                        case 86:
                        case 88:
                        case 90:
                        case 92:
                        case 95:
                            i2 = 2;
                            break;
                        default:
                            switch (i) {
                                case 23:
                                case 24:
                                case 25:
                                case 26:
                                case 27:
                                case 28:
                                case 29:
                                case 30:
                                case 31:
                                case 32:
                                case 33:
                                case 34:
                                    i2 = 2;
                                    break;
                                default:
                                    switch (i) {
                                        case 73:
                                        case 74:
                                        case 75:
                                            i2 = 2;
                                            break;
                                        default:
                                            i2 = 3;
                                            break;
                                    }
                                    break;
                            }
                            break;
                    }
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "config";
                break;
            case 2:
                objArr[0] = "moduleDescriptor";
                break;
            case 3:
                objArr[0] = "sourceFilePathResolver";
                break;
            case 4:
                objArr[0] = "packageFqn";
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
            case 16:
            case 20:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 36:
            case 37:
            case 39:
            case 41:
            case 43:
            case 45:
            case 46:
            case 50:
            case 54:
            case 57:
            case 59:
            case 62:
            case 67:
            case 69:
            case 73:
            case 74:
            case 75:
            case 78:
            case 79:
            case 81:
            case 82:
            case 86:
            case 88:
            case 90:
            case 92:
            case 95:
                objArr[0] = "org/jetbrains/kotlin/js/translate/context/StaticContext";
                break;
            case 12:
            case 15:
            case 17:
            case 18:
            case 19:
            case 21:
            case 22:
            case 35:
            case 40:
            case 42:
            case 55:
            case 58:
            case 61:
            case 63:
            case 64:
            case 65:
            case 72:
            case 77:
            case 80:
                objArr[0] = "descriptor";
                break;
            case 38:
            case 94:
                objArr[0] = "property";
                break;
            case 44:
                objArr[0] = "suggested";
                break;
            case 47:
            case 51:
            case 56:
                objArr[0] = "suggestedName";
                break;
            case 48:
            case 52:
            case 60:
                objArr[0] = "tag";
                break;
            case 49:
            case 53:
            case 68:
            case 83:
                objArr[0] = "declaration";
                break;
            case 66:
                objArr[0] = "baseName";
                break;
            case 70:
                objArr[0] = "localClass";
                break;
            case 71:
                objArr[0] = "closure";
                break;
            case 76:
                objArr[0] = "classDescriptor";
                break;
            case 84:
                objArr[0] = "moduleId";
                break;
            case 85:
                objArr[0] = "moduleName";
                break;
            case 87:
                objArr[0] = "specialFunction";
                break;
            case 89:
            case 91:
            case 93:
                objArr[0] = "name";
                break;
            default:
                objArr[0] = "bindingTrace";
                break;
        }
        if (i == 13 || i == 14) {
            objArr[1] = "getScopeForDescriptor";
        } else if (i == 36 || i == 37) {
            objArr[1] = "getNameForDescriptor";
        } else if (i == 45) {
            objArr[1] = "getActualNameFromSuggested";
        } else if (i == 46) {
            objArr[1] = "getConfig";
        } else if (i == 78) {
            objArr[1] = "getNameSuggestion";
        } else if (i == 79) {
            objArr[1] = "getCurrentModule";
        } else if (i != 81 && i != 82) {
            switch (i) {
                case 5:
                    objArr[1] = "getProgram";
                    break;
                case 6:
                    objArr[1] = "getFragment";
                    break;
                case 7:
                    objArr[1] = "getBindingTrace";
                    break;
                case 8:
                    objArr[1] = "getBindingContext";
                    break;
                case 9:
                    objArr[1] = "getIntrinsics";
                    break;
                case 10:
                    objArr[1] = "getNamer";
                    break;
                case 11:
                    objArr[1] = "getSourceFilePathResolver";
                    break;
                default:
                    switch (i) {
                        case 16:
                            objArr[1] = "getFunctionWithScope";
                            break;
                        case 20:
                            objArr[1] = "getQualifiedExpression";
                            break;
                        case 39:
                            objArr[1] = "getNameForBackingField";
                            break;
                        case 41:
                            objArr[1] = "getInnerNameForDescriptor";
                            break;
                        case 43:
                            objArr[1] = "getNameForObjectInstance";
                            break;
                        case 50:
                            objArr[1] = "importDeclaration";
                            break;
                        case 54:
                            objArr[1] = "importDeclarationImpl";
                            break;
                        case 57:
                            objArr[1] = "localOrImportedName";
                            break;
                        case 59:
                            objArr[1] = "getInlineFunctionTags";
                            break;
                        case 62:
                            objArr[1] = "getSuggestedName";
                            break;
                        case 67:
                            objArr[1] = "getImportedModule";
                            break;
                        case 69:
                            objArr[1] = "getPlainId";
                            break;
                        case 86:
                            objArr[1] = "exportModuleForInline";
                            break;
                        case 88:
                            objArr[1] = "getNameForSpecialFunction";
                            break;
                        case 90:
                            objArr[1] = "getReferenceToIntrinsic";
                            break;
                        case 92:
                            objArr[1] = "getNameForIntrinsic";
                            break;
                        case 95:
                            objArr[1] = "getVariableForPropertyMetadata";
                            break;
                        default:
                            switch (i) {
                                case 23:
                                case 24:
                                case 25:
                                case 26:
                                case 27:
                                case 28:
                                case 29:
                                case 30:
                                case 31:
                                case 32:
                                case 33:
                                case 34:
                                    objArr[1] = "buildQualifiedExpression";
                                    break;
                                default:
                                    switch (i) {
                                        case 73:
                                            objArr[1] = "getDeferredCallSites";
                                            break;
                                        case 74:
                                            objArr[1] = "getTopLevelStatements";
                                            break;
                                        case 75:
                                            objArr[1] = "getDeclarationStatements";
                                            break;
                                        default:
                                            objArr[1] = "org/jetbrains/kotlin/js/translate/context/StaticContext";
                                            break;
                                    }
                                    break;
                            }
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "getNameForImportsForInline";
        }
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
            case 16:
            case 20:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 36:
            case 37:
            case 39:
            case 41:
            case 43:
            case 45:
            case 46:
            case 50:
            case 54:
            case 57:
            case 59:
            case 62:
            case 67:
            case 69:
            case 73:
            case 74:
            case 75:
            case 78:
            case 79:
            case 81:
            case 82:
            case 86:
            case 88:
            case 90:
            case 92:
            case 95:
                break;
            case 12:
                objArr[2] = "getScopeForDescriptor";
                break;
            case 15:
                objArr[2] = "getFunctionWithScope";
                break;
            case 17:
                objArr[2] = "getQualifiedReference";
                break;
            case 18:
                objArr[2] = "getTag";
                break;
            case 19:
                objArr[2] = "getQualifiedExpression";
                break;
            case 21:
                objArr[2] = "suggestName";
                break;
            case 22:
                objArr[2] = "buildQualifiedExpression";
                break;
            case 35:
                objArr[2] = "getNameForDescriptor";
                break;
            case 38:
                objArr[2] = "getNameForBackingField";
                break;
            case 40:
                objArr[2] = "getInnerNameForDescriptor";
                break;
            case 42:
                objArr[2] = "getNameForObjectInstance";
                break;
            case 44:
                objArr[2] = "getActualNameFromSuggested";
                break;
            case 47:
            case 48:
            case 49:
                objArr[2] = "importDeclaration";
                break;
            case 51:
            case 52:
            case 53:
                objArr[2] = "importDeclarationImpl";
                break;
            case 55:
            case 56:
                objArr[2] = "localOrImportedName";
                break;
            case 58:
                objArr[2] = "isLocallyRedeclaredBuiltin";
                break;
            case 60:
                objArr[2] = "reportInlineFunctionTag";
                break;
            case 61:
                objArr[2] = "getSuggestedName";
                break;
            case 63:
                objArr[2] = "getModuleExpressionFor";
                break;
            case 64:
                objArr[2] = "getModuleInnerName";
                break;
            case 65:
                objArr[2] = "getJsImportedModule";
                break;
            case 66:
                objArr[2] = "getImportedModule";
                break;
            case 68:
                objArr[2] = "getPlainId";
                break;
            case 70:
            case 71:
                objArr[2] = "putClassOrConstructorClosure";
                break;
            case 72:
                objArr[2] = "getClassOrConstructorClosure";
                break;
            case 76:
                objArr[2] = "addClass";
                break;
            case 77:
                objArr[2] = "export";
                break;
            case 80:
                objArr[2] = "addInlineCall";
                break;
            case 83:
            case 84:
            case 85:
                objArr[2] = "exportModuleForInline";
                break;
            case 87:
                objArr[2] = "getNameForSpecialFunction";
                break;
            case 89:
                objArr[2] = "getReferenceToIntrinsic";
                break;
            case 91:
                objArr[2] = "getNameForIntrinsic";
                break;
            case 93:
                objArr[2] = "findDescriptorForIntrinsic";
                break;
            case 94:
                objArr[2] = "getVariableForPropertyMetadata";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 13 && i != 14 && i != 36 && i != 37 && i != 45 && i != 46 && i != 78 && i != 79 && i != 81 && i != 82) {
            switch (i) {
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    break;
                default:
                    switch (i) {
                        case 16:
                        case 20:
                        case 39:
                        case 41:
                        case 43:
                        case 50:
                        case 54:
                        case 57:
                        case 59:
                        case 62:
                        case 67:
                        case 69:
                        case 86:
                        case 88:
                        case 90:
                        case 92:
                        case 95:
                            break;
                        default:
                            switch (i) {
                                case 23:
                                case 24:
                                case 25:
                                case 26:
                                case 27:
                                case 28:
                                case 29:
                                case 30:
                                case 31:
                                case 32:
                                case 33:
                                case 34:
                                    break;
                                default:
                                    switch (i) {
                                        case 73:
                                        case 74:
                                        case 75:
                                            break;
                                        default:
                                            throw new IllegalArgumentException(str2);
                                    }
                                    break;
                            }
                            break;
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    public StaticContext(BindingTrace bindingTrace, JsConfig jsConfig, ModuleDescriptor moduleDescriptor, SourceFilePathResolver sourceFilePathResolver, String str) {
        boolean z = false;
        if (bindingTrace == null) {
            $$$reportNull$$$0(0);
        }
        if (jsConfig == null) {
            $$$reportNull$$$0(1);
        }
        if (moduleDescriptor == null) {
            $$$reportNull$$$0(2);
        }
        if (sourceFilePathResolver == null) {
            $$$reportNull$$$0(3);
        }
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        this.innerNames = new InnerNameGenerator();
        this.scopes = new ScopeGenerator();
        this.objectInstanceNames = new ObjectInstanceNameGenerator();
        this.scopeToFunction = new HashMap();
        this.classOrConstructorClosure = new HashMap();
        this.deferredCallSites = new HashMap();
        this.nameCache = new HashMap();
        this.backingFieldNameCache = new HashMap();
        this.fqnCache = new HashMap();
        this.tagCache = new HashMap();
        this.importedModules = new LinkedHashMap();
        this.exporter = new DeclarationExporter(this);
        this.packageScopes = new HashMap();
        this.modulesImportedForInline = new HashMap();
        this.specialFunctions = new EnumMap(SpecialFunction.class);
        this.intrinsicNames = new HashMap();
        this.propertyMetadataVariables = new HashMap();
        this.inlineFunctionTags = new HashSet();
        JsProgram jsProgram = new JsProgram();
        this.program = jsProgram;
        JsProgramFragment jsProgramFragment = new JsProgramFragment(JsAstUtils.createFunctionWithEmptyBody(jsProgram.getScope()).getScope(), str);
        this.fragment = jsProgramFragment;
        this.bindingTrace = bindingTrace;
        this.nameSuggestion = new NameSuggestion();
        this.namer = Namer.newInstance(jsProgram.getRootScope());
        this.intrinsics = new Intrinsics();
        JsScope scope = jsProgramFragment.getScope();
        this.rootScope = scope;
        this.config = jsConfig;
        this.currentModule = moduleDescriptor;
        this.currentModuleAsImported = new JsImportedModule(Namer.getRootPackageName(), scope.declareName(Namer.getRootPackageName()), null);
        createImportedModule(new JsImportedModuleKey(Namer.KOTLIN_LOWER_NAME, null), Namer.KOTLIN_LOWER_NAME, scope.declareName("Kotlin"), null);
        this.classModelGenerator = new ClassModelGenerator(TranslationContext.rootContext(this));
        this.sourceFilePathResolver = sourceFilePathResolver;
        ClassDescriptor classDescriptorFindClassAcrossModuleDependencies = FindClassInModuleKt.findClassAcrossModuleDependencies(moduleDescriptor, ClassId.topLevel(new FqName("kotlin.Exception")));
        if (classDescriptorFindClassAcrossModuleDependencies != null && DescriptorUtils.getContainingModule(classDescriptorFindClassAcrossModuleDependencies) == moduleDescriptor) {
            z = true;
        }
        this.isStdlib = z;
    }

    public JsProgram getProgram() {
        JsProgram jsProgram = this.program;
        if (jsProgram == null) {
            $$$reportNull$$$0(5);
        }
        return jsProgram;
    }

    public JsProgramFragment getFragment() {
        JsProgramFragment jsProgramFragment = this.fragment;
        if (jsProgramFragment == null) {
            $$$reportNull$$$0(6);
        }
        return jsProgramFragment;
    }

    public BindingTrace getBindingTrace() {
        BindingTrace bindingTrace = this.bindingTrace;
        if (bindingTrace == null) {
            $$$reportNull$$$0(7);
        }
        return bindingTrace;
    }

    public BindingContext getBindingContext() {
        BindingContext context = this.bindingTrace.getContext();
        if (context == null) {
            $$$reportNull$$$0(8);
        }
        return context;
    }

    public Intrinsics getIntrinsics() {
        Intrinsics intrinsics = this.intrinsics;
        if (intrinsics == null) {
            $$$reportNull$$$0(9);
        }
        return intrinsics;
    }

    public Namer getNamer() {
        Namer namer = this.namer;
        if (namer == null) {
            $$$reportNull$$$0(10);
        }
        return namer;
    }

    public SourceFilePathResolver getSourceFilePathResolver() {
        SourceFilePathResolver sourceFilePathResolver = this.sourceFilePathResolver;
        if (sourceFilePathResolver == null) {
            $$$reportNull$$$0(11);
        }
        return sourceFilePathResolver;
    }

    public JsScope getScopeForDescriptor(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(12);
        }
        if (declarationDescriptor instanceof ModuleDescriptor) {
            JsScope jsScope = this.rootScope;
            if (jsScope == null) {
                $$$reportNull$$$0(13);
            }
            return jsScope;
        }
        JsScope jsScope2 = this.scopes.get(declarationDescriptor.getOriginal());
        if (jsScope2 == null) {
            $$$reportNull$$$0(14);
        }
        return jsScope2;
    }

    public JsFunction getFunctionWithScope(CallableDescriptor callableDescriptor) {
        if (callableDescriptor == null) {
            $$$reportNull$$$0(15);
        }
        JsFunction jsFunction = this.scopeToFunction.get(getScopeForDescriptor(callableDescriptor));
        if (jsFunction == null) {
            $$$reportNull$$$0(16);
        }
        return jsFunction;
    }

    public JsNameRef getQualifiedReference(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(17);
        }
        return (JsNameRef) getQualifiedExpression(declarationDescriptor);
    }

    public String getTag(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(18);
        }
        if (!this.tagCache.containsKey(declarationDescriptor)) {
            String strGenerateSignature = SignatureUtilsKt.generateSignature(declarationDescriptor);
            this.tagCache.put(declarationDescriptor, strGenerateSignature);
            return strGenerateSignature;
        }
        return this.tagCache.get(declarationDescriptor);
    }

    private JsExpression getQualifiedExpression(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(19);
        }
        JsExpression jsExpressionBuildQualifiedExpression = this.fqnCache.get(declarationDescriptor);
        if (jsExpressionBuildQualifiedExpression == null) {
            jsExpressionBuildQualifiedExpression = buildQualifiedExpression(declarationDescriptor);
            this.fqnCache.put(declarationDescriptor, jsExpressionBuildQualifiedExpression);
        }
        JsExpression jsExpressionDeepCopy = jsExpressionBuildQualifiedExpression.deepCopy();
        if (jsExpressionDeepCopy == null) {
            $$$reportNull$$$0(20);
        }
        return jsExpressionDeepCopy;
    }

    public SuggestedName suggestName(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(21);
        }
        return this.nameSuggestion.suggest(declarationDescriptor, getBindingContext());
    }

    private JsExpression buildQualifiedExpression(DeclarationDescriptor declarationDescriptor) {
        JsExpression qualifiedExpression;
        String moduleName;
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(22);
        }
        if (declarationDescriptor instanceof ClassDescriptor) {
            ClassDescriptor classDescriptor = (ClassDescriptor) declarationDescriptor;
            SimpleType defaultType = classDescriptor.getDefaultType();
            if (KotlinBuiltIns.isAny(classDescriptor)) {
                JsNameRef jsNameRefPureFqn = JsAstUtils.pureFqn(CommonClassNames.JAVA_LANG_OBJECT_SHORT, (JsExpression) null);
                if (jsNameRefPureFqn == null) {
                    $$$reportNull$$$0(23);
                }
                return jsNameRefPureFqn;
            }
            if (KotlinBuiltIns.isInt(defaultType) || KotlinBuiltIns.isShort(defaultType) || KotlinBuiltIns.isByte(defaultType) || KotlinBuiltIns.isFloat(defaultType) || KotlinBuiltIns.isDouble(defaultType)) {
                JsNameRef jsNameRefPureFqn2 = JsAstUtils.pureFqn("Number", (JsExpression) null);
                if (jsNameRefPureFqn2 == null) {
                    $$$reportNull$$$0(24);
                }
                return jsNameRefPureFqn2;
            }
            if (KotlinBuiltIns.isLong(defaultType)) {
                JsNameRef jsNameRefPureFqn3 = JsAstUtils.pureFqn("Long", Namer.kotlinObject());
                if (jsNameRefPureFqn3 == null) {
                    $$$reportNull$$$0(25);
                }
                return jsNameRefPureFqn3;
            }
            if (KotlinBuiltIns.isChar(defaultType)) {
                JsNameRef jsNameRefPureFqn4 = JsAstUtils.pureFqn("BoxedChar", Namer.kotlinObject());
                if (jsNameRefPureFqn4 == null) {
                    $$$reportNull$$$0(26);
                }
                return jsNameRefPureFqn4;
            }
            if (KotlinBuiltIns.isString(defaultType)) {
                JsNameRef jsNameRefPureFqn5 = JsAstUtils.pureFqn(CommonClassNames.JAVA_LANG_STRING_SHORT, (JsExpression) null);
                if (jsNameRefPureFqn5 == null) {
                    $$$reportNull$$$0(27);
                }
                return jsNameRefPureFqn5;
            }
            if (KotlinBuiltIns.isBoolean(defaultType)) {
                JsNameRef jsNameRefPureFqn6 = JsAstUtils.pureFqn("Boolean", (JsExpression) null);
                if (jsNameRefPureFqn6 == null) {
                    $$$reportNull$$$0(28);
                }
                return jsNameRefPureFqn6;
            }
            if (KotlinBuiltIns.isArrayOrPrimitiveArray(classDescriptor)) {
                JsNameRef jsNameRefPureFqn7 = JsAstUtils.pureFqn("Array", (JsExpression) null);
                if (jsNameRefPureFqn7 == null) {
                    $$$reportNull$$$0(29);
                }
                return jsNameRefPureFqn7;
            }
            if (FunctionTypesKt.isBuiltinFunctionalType(defaultType)) {
                JsNameRef jsNameRefPureFqn8 = JsAstUtils.pureFqn("Function", (JsExpression) null);
                if (jsNameRefPureFqn8 == null) {
                    $$$reportNull$$$0(30);
                }
                return jsNameRefPureFqn8;
            }
            if (TypeUtilsKt.isNotNullThrowable(classDescriptor.getDefaultType())) {
                JsNameRef jsNameRefPureFqn9 = JsAstUtils.pureFqn("Error", (JsExpression) null);
                if (jsNameRefPureFqn9 == null) {
                    $$$reportNull$$$0(31);
                }
                return jsNameRefPureFqn9;
            }
        }
        SuggestedName suggestedNameSuggestName = suggestName(declarationDescriptor);
        if (suggestedNameSuggestName == null) {
            JsExpression moduleExpressionFor = getModuleExpressionFor(DescriptorUtils.getContainingModule(declarationDescriptor));
            if (moduleExpressionFor == null) {
                moduleExpressionFor = JsAstUtils.pureFqn(Namer.getRootPackageName(), (JsExpression) null);
            }
            if (moduleExpressionFor == null) {
                $$$reportNull$$$0(32);
            }
            return moduleExpressionFor;
        }
        if (this.config.getModuleKind() != ModuleKind.PLAIN && (moduleName = AnnotationsUtils.getModuleName(suggestedNameSuggestName.getDescriptor())) != null) {
            JsNameRef jsNameRefPureFqn10 = JsAstUtils.pureFqn(getImportedModule(moduleName, suggestedNameSuggestName.getDescriptor()).getInternalName(), (JsExpression) null);
            if (jsNameRefPureFqn10 == null) {
                $$$reportNull$$$0(33);
            }
            return jsNameRefPureFqn10;
        }
        List<JsName> actualNameFromSuggested = getActualNameFromSuggested(suggestedNameSuggestName);
        if (AnnotationsUtils.isLibraryObject(suggestedNameSuggestName.getDescriptor())) {
            qualifiedExpression = Namer.kotlinObject();
        } else {
            qualifiedExpression = ((!AnnotationsUtils.isNativeObject(suggestedNameSuggestName.getDescriptor()) || AnnotationsUtils.isNativeObject(suggestedNameSuggestName.getScope())) && !((suggestedNameSuggestName.getDescriptor() instanceof CallableDescriptor) && (suggestedNameSuggestName.getScope() instanceof FunctionDescriptor))) ? getQualifiedExpression(suggestedNameSuggestName.getScope()) : null;
        }
        if (AnnotationsUtils.isNativeObject(suggestedNameSuggestName.getDescriptor()) && DescriptorUtils.isTopLevelDeclaration(suggestedNameSuggestName.getDescriptor())) {
            String fileModuleName = AnnotationsUtils.getFileModuleName(getBindingContext(), suggestedNameSuggestName.getDescriptor());
            if (fileModuleName != null) {
                qualifiedExpression = JsAstUtils.pureFqn(getImportedModule(fileModuleName, null).getInternalName(), qualifiedExpression);
            }
            String fileQualifier = AnnotationsUtils.getFileQualifier(getBindingContext(), suggestedNameSuggestName.getDescriptor());
            if (fileQualifier != null) {
                Iterator<String> it = StringUtil.split(fileQualifier, ".").iterator();
                while (it.hasNext()) {
                    qualifiedExpression = JsAstUtils.pureFqn(it.next(), qualifiedExpression);
                }
            }
        }
        Iterator<JsName> it2 = actualNameFromSuggested.iterator();
        while (it2.hasNext()) {
            JsNameRef jsNameRef = new JsNameRef(it2.next(), qualifiedExpression);
            applySideEffects(jsNameRef, suggestedNameSuggestName.getDescriptor());
            qualifiedExpression = jsNameRef;
        }
        if (qualifiedExpression == null) {
            $$$reportNull$$$0(34);
        }
        return qualifiedExpression;
    }

    public JsName getNameForDescriptor(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(35);
        }
        if ((declarationDescriptor instanceof ClassDescriptor) && KotlinBuiltIns.isAny((ClassDescriptor) declarationDescriptor)) {
            JsName jsNameDeclareName = this.rootScope.declareName(CommonClassNames.JAVA_LANG_OBJECT_SHORT);
            MetadataProperties.setDescriptor(jsNameDeclareName, declarationDescriptor);
            if (jsNameDeclareName == null) {
                $$$reportNull$$$0(36);
            }
            return jsNameDeclareName;
        }
        SuggestedName suggestedNameSuggest = this.nameSuggestion.suggest(declarationDescriptor, getBindingContext());
        if (suggestedNameSuggest == null) {
            throw new IllegalArgumentException("Can't generate name for root declarations: " + declarationDescriptor);
        }
        JsName jsName = getActualNameFromSuggested(suggestedNameSuggest).get(0);
        if (jsName == null) {
            $$$reportNull$$$0(37);
        }
        return jsName;
    }

    public JsName getNameForBackingField(VariableDescriptorWithAccessors variableDescriptorWithAccessors) {
        if (variableDescriptorWithAccessors == null) {
            $$$reportNull$$$0(38);
        }
        JsName jsNameDeclareFreshName = this.backingFieldNameCache.get(variableDescriptorWithAccessors);
        if (jsNameDeclareFreshName == null) {
            SuggestedName suggestedNameSuggest = this.nameSuggestion.suggest(variableDescriptorWithAccessors, getBindingContext());
            jsNameDeclareFreshName = getScopeForDescriptor(suggestedNameSuggest.getScope()).declareFreshName(NameSuggestion.getPrivateMangledName(suggestedNameSuggest.getNames().get(0), variableDescriptorWithAccessors) + "_0");
            this.backingFieldNameCache.put(variableDescriptorWithAccessors, jsNameDeclareFreshName);
        }
        if (jsNameDeclareFreshName == null) {
            $$$reportNull$$$0(39);
        }
        return jsNameDeclareFreshName;
    }

    public JsName getInnerNameForDescriptor(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(40);
        }
        JsName jsName = this.innerNames.get(declarationDescriptor.getOriginal());
        if (jsName == null) {
            $$$reportNull$$$0(41);
        }
        return jsName;
    }

    public JsName getNameForObjectInstance(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(42);
        }
        JsName jsName = this.objectInstanceNames.get(classDescriptor.getOriginal());
        if (jsName == null) {
            $$$reportNull$$$0(43);
        }
        return jsName;
    }

    private List<JsName> getActualNameFromSuggested(SuggestedName suggestedName) {
        if (suggestedName == null) {
            $$$reportNull$$$0(44);
        }
        JsScope scopeForDescriptor = getScopeForDescriptor(suggestedName.getScope());
        if (DynamicCallsKt.isDynamic(suggestedName.getDescriptor())) {
            scopeForDescriptor = JsDynamicScope.INSTANCE;
        } else if (AnnotationsUtils.isPredefinedObject(suggestedName.getDescriptor()) && DescriptorUtils.isTopLevelDeclaration(suggestedName.getDescriptor())) {
            scopeForDescriptor = this.rootScope;
        }
        ArrayList arrayList = new ArrayList();
        int i = 0;
        if (suggestedName.getStable()) {
            String tag = getTag(suggestedName.getDescriptor());
            Iterator<String> it = suggestedName.getNames().iterator();
            while (it.hasNext()) {
                JsName jsNameDeclareName = scopeForDescriptor.declareName(it.next());
                MetadataProperties.setDescriptor(jsNameDeclareName, suggestedName.getDescriptor());
                if (tag != null && !AnnotationsUtils.isNativeObject(suggestedName.getDescriptor()) && !AnnotationsUtils.isLibraryObject(suggestedName.getDescriptor())) {
                    this.fragment.getNameBindings().add(new JsNameBinding(i + ":" + tag, jsNameDeclareName));
                    i++;
                }
                arrayList.add(jsNameDeclareName);
            }
        } else {
            JsName jsNameDeclareTemporaryName = this.nameCache.get(suggestedName.getDescriptor());
            if (jsNameDeclareTemporaryName == null) {
                String strSanitizeName = NameSuggestion.sanitizeName(suggestedName.getNames().get(0));
                if ((suggestedName.getDescriptor() instanceof LocalVariableDescriptor) || (suggestedName.getDescriptor() instanceof ValueParameterDescriptor)) {
                    jsNameDeclareTemporaryName = JsScope.declareTemporaryName(strSanitizeName);
                } else {
                    if (!DescriptorUtils.isDescriptorWithLocalVisibility(suggestedName.getDescriptor())) {
                        strSanitizeName = strSanitizeName + "_0";
                    }
                    jsNameDeclareTemporaryName = scopeForDescriptor.declareFreshName(strSanitizeName);
                }
            }
            this.nameCache.put(suggestedName.getDescriptor(), jsNameDeclareTemporaryName);
            MetadataProperties.setDescriptor(jsNameDeclareTemporaryName, suggestedName.getDescriptor());
            String tag2 = getTag(suggestedName.getDescriptor());
            if (tag2 != null) {
                this.fragment.getNameBindings().add(new JsNameBinding(tag2, jsNameDeclareTemporaryName));
            }
            arrayList.add(jsNameDeclareTemporaryName);
        }
        return arrayList;
    }

    public JsConfig getConfig() {
        JsConfig jsConfig = this.config;
        if (jsConfig == null) {
            $$$reportNull$$$0(46);
        }
        return jsConfig;
    }

    JsName importDeclaration(String str, String str2, JsExpression jsExpression) {
        if (str == null) {
            $$$reportNull$$$0(47);
        }
        if (str2 == null) {
            $$$reportNull$$$0(48);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(49);
        }
        JsName jsNameImportDeclarationImpl = importDeclarationImpl(str, str2, jsExpression);
        this.fragment.getNameBindings().add(new JsNameBinding(str2, jsNameImportDeclarationImpl));
        if (jsNameImportDeclarationImpl == null) {
            $$$reportNull$$$0(50);
        }
        return jsNameImportDeclarationImpl;
    }

    private JsName importDeclarationImpl(String str, String str2, JsExpression jsExpression) {
        if (str == null) {
            $$$reportNull$$$0(51);
        }
        if (str2 == null) {
            $$$reportNull$$$0(52);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(53);
        }
        JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(str);
        MetadataProperties.setImported(jsNameDeclareTemporaryName, true);
        this.fragment.getImports().put(str2, jsExpression);
        if (jsNameDeclareTemporaryName == null) {
            $$$reportNull$$$0(54);
        }
        return jsNameDeclareTemporaryName;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public JsName localOrImportedName(DeclarationDescriptor declarationDescriptor, String str) {
        JsName jsNameDeclareTemporaryName;
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(55);
        }
        if (str == null) {
            $$$reportNull$$$0(56);
        }
        ModuleDescriptor module = DescriptorUtilsKt.getModule(declarationDescriptor);
        String tag = getTag(declarationDescriptor);
        boolean z = AnnotationsUtils.isNativeObject(declarationDescriptor) || AnnotationsUtils.isLibraryObject(declarationDescriptor);
        if ((module != this.currentModule && !isLocallyRedeclaredBuiltin(declarationDescriptor)) || z) {
            JsNameRef qualifiedReference = getQualifiedReference(declarationDescriptor);
            if (z && qualifiedReference.getQualifier() == null && qualifiedReference.getName() != null) {
                jsNameDeclareTemporaryName = qualifiedReference.getName();
                tag = null;
            } else {
                jsNameDeclareTemporaryName = importDeclarationImpl(str, tag, qualifiedReference);
            }
        } else {
            jsNameDeclareTemporaryName = JsScope.declareTemporaryName(str);
        }
        if (tag != null) {
            this.fragment.getNameBindings().add(new JsNameBinding(tag, jsNameDeclareTemporaryName));
        }
        MetadataProperties.setDescriptor(jsNameDeclareTemporaryName, declarationDescriptor);
        if (jsNameDeclareTemporaryName == null) {
            $$$reportNull$$$0(57);
        }
        return jsNameDeclareTemporaryName;
    }

    private boolean isLocallyRedeclaredBuiltin(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(58);
        }
        if (!(declarationDescriptor instanceof ClassDescriptor)) {
            return false;
        }
        ClassDescriptor classDescriptorFindClassAcrossModuleDependencies = FindClassInModuleKt.findClassAcrossModuleDependencies(this.currentModule, ClassId.topLevel(DescriptorUtils.getFqNameSafe(declarationDescriptor)));
        return classDescriptorFindClassAcrossModuleDependencies != null && DescriptorUtils.getContainingModule(classDescriptorFindClassAcrossModuleDependencies) == this.currentModule;
    }

    public Set<String> getInlineFunctionTags() {
        Set<String> set = this.inlineFunctionTags;
        if (set == null) {
            $$$reportNull$$$0(59);
        }
        return set;
    }

    public void reportInlineFunctionTag(String str) {
        if (str == null) {
            $$$reportNull$$$0(60);
        }
        this.inlineFunctionTags.add(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class InnerNameGenerator extends Generator<JsName> {
        public InnerNameGenerator() {
            addRule(new Rule() { // from class: org.jetbrains.kotlin.js.translate.context.StaticContext$InnerNameGenerator$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.js.translate.context.generator.Rule
                public final Object apply(DeclarationDescriptor declarationDescriptor) {
                    return this.f$0.m7289xf8ede37f(declarationDescriptor);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$new$0$org-jetbrains-kotlin-js-translate-context-StaticContext$InnerNameGenerator, reason: not valid java name */
        /* synthetic */ JsName m7289xf8ede37f(DeclarationDescriptor declarationDescriptor) {
            FunctionDescriptor initialSignatureDescriptor;
            if ((declarationDescriptor instanceof PackageFragmentDescriptor) && DescriptorUtils.getContainingModule(declarationDescriptor) == StaticContext.this.currentModule) {
                return StaticContext.this.exporter.getLocalPackageName(((PackageFragmentDescriptor) declarationDescriptor).getFqName());
            }
            if ((declarationDescriptor instanceof FunctionDescriptor) && (initialSignatureDescriptor = ((FunctionDescriptor) declarationDescriptor).getInitialSignatureDescriptor()) != null) {
                return StaticContext.this.getInnerNameForDescriptor(initialSignatureDescriptor);
            }
            if (declarationDescriptor instanceof ModuleDescriptor) {
                return StaticContext.this.getModuleInnerName(declarationDescriptor);
            }
            if ((declarationDescriptor instanceof LocalVariableDescriptor) || (declarationDescriptor instanceof ParameterDescriptor)) {
                return StaticContext.this.getNameForDescriptor(declarationDescriptor);
            }
            if (declarationDescriptor instanceof ConstructorDescriptor) {
                ConstructorDescriptor constructorDescriptor = (ConstructorDescriptor) declarationDescriptor;
                if (constructorDescriptor.isPrimary()) {
                    return StaticContext.this.getInnerNameForDescriptor(constructorDescriptor.getConstructedClass());
                }
            }
            return StaticContext.this.localOrImportedName(declarationDescriptor, StaticContext.getSuggestedName(declarationDescriptor));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class ObjectInstanceNameGenerator extends Generator<JsName> {
        public ObjectInstanceNameGenerator() {
            addRule(new Rule() { // from class: org.jetbrains.kotlin.js.translate.context.StaticContext$ObjectInstanceNameGenerator$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.js.translate.context.generator.Rule
                public final Object apply(DeclarationDescriptor declarationDescriptor) {
                    return this.f$0.m7290xaa16d4e7(declarationDescriptor);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$new$0$org-jetbrains-kotlin-js-translate-context-StaticContext$ObjectInstanceNameGenerator, reason: not valid java name */
        /* synthetic */ JsName m7290xaa16d4e7(DeclarationDescriptor declarationDescriptor) {
            JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(StaticContext.getSuggestedName(declarationDescriptor) + Namer.OBJECT_INSTANCE_FUNCTION_SUFFIX);
            String strGenerateSignature = SignatureUtilsKt.generateSignature(declarationDescriptor);
            if (strGenerateSignature != null) {
                StaticContext.this.fragment.getNameBindings().add(new JsNameBinding("object:" + strGenerateSignature, jsNameDeclareTemporaryName));
            }
            return jsNameDeclareTemporaryName;
        }
    }

    public static String getSuggestedName(DeclarationDescriptor declarationDescriptor) {
        String strSanitizeName;
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(61);
        }
        if (declarationDescriptor instanceof PropertyGetterDescriptor) {
            strSanitizeName = "get_" + getSuggestedName(((PropertyGetterDescriptor) declarationDescriptor).getCorrespondingProperty());
        } else if (declarationDescriptor instanceof PropertySetterDescriptor) {
            strSanitizeName = "set_" + getSuggestedName(((PropertySetterDescriptor) declarationDescriptor).getCorrespondingProperty());
        } else if (declarationDescriptor instanceof ConstructorDescriptor) {
            strSanitizeName = getSuggestedName(((ConstructorDescriptor) declarationDescriptor).getContainingModule()) + "_init";
            declarationDescriptor = declarationDescriptor.getContainingModule();
        } else if (declarationDescriptor.getShortName().isSpecial()) {
            if (declarationDescriptor instanceof ClassDescriptor) {
                if (DescriptorUtils.isAnonymousObject(declarationDescriptor)) {
                    strSanitizeName = "ObjectLiteral";
                } else {
                    strSanitizeName = "Anonymous";
                }
            } else if (declarationDescriptor instanceof FunctionDescriptor) {
                strSanitizeName = "lambda";
            } else {
                strSanitizeName = StringTemplate.ANONYMOUS_ST_NAME;
            }
        } else {
            strSanitizeName = NameSuggestion.sanitizeName(declarationDescriptor.getShortName().asString());
        }
        if (!(declarationDescriptor instanceof PackageFragmentDescriptor) && !DescriptorUtils.isTopLevelDeclaration(declarationDescriptor)) {
            strSanitizeName = getSuggestedName(declarationDescriptor.getContainingModule()) + "$" + NameSuggestion.sanitizeName(strSanitizeName);
        }
        if (strSanitizeName == null) {
            $$$reportNull$$$0(62);
        }
        return strSanitizeName;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public JsScope getScopeForPackage(FqName fqName) {
        JsScope jsScopeInnerObjectScope = this.packageScopes.get(fqName);
        if (jsScopeInnerObjectScope == null) {
            if (fqName.isRoot()) {
                jsScopeInnerObjectScope = new JsRootScope(this.program);
            } else {
                jsScopeInnerObjectScope = getScopeForPackage(fqName.parent()).innerObjectScope(fqName.shortName().asString());
            }
            this.packageScopes.put(fqName, jsScopeInnerObjectScope);
        }
        return jsScopeInnerObjectScope;
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class ScopeGenerator extends Generator<JsScope> {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        public ScopeGenerator() {
            Rule rule = new Rule() { // from class: org.jetbrains.kotlin.js.translate.context.StaticContext$ScopeGenerator$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.js.translate.context.generator.Rule
                public final Object apply(DeclarationDescriptor declarationDescriptor) {
                    return this.f$0.m7291x4b60d30c(declarationDescriptor);
                }
            };
            Rule rule2 = new Rule() { // from class: org.jetbrains.kotlin.js.translate.context.StaticContext$ScopeGenerator$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.js.translate.context.generator.Rule
                public final Object apply(DeclarationDescriptor declarationDescriptor) {
                    return this.f$0.m7292xcdab87eb(declarationDescriptor);
                }
            };
            Rule rule3 = new Rule() { // from class: org.jetbrains.kotlin.js.translate.context.StaticContext$ScopeGenerator$$ExternalSyntheticLambda2
                @Override // org.jetbrains.kotlin.js.translate.context.generator.Rule
                public final Object apply(DeclarationDescriptor declarationDescriptor) {
                    return this.f$0.m7293x4ff63cca(declarationDescriptor);
                }
            };
            Rule rule4 = new Rule() { // from class: org.jetbrains.kotlin.js.translate.context.StaticContext$ScopeGenerator$$ExternalSyntheticLambda3
                @Override // org.jetbrains.kotlin.js.translate.context.generator.Rule
                public final Object apply(DeclarationDescriptor declarationDescriptor) {
                    return this.f$0.m7294xd240f1a9(declarationDescriptor);
                }
            };
            Rule rule5 = new Rule() { // from class: org.jetbrains.kotlin.js.translate.context.StaticContext$ScopeGenerator$$ExternalSyntheticLambda4
                @Override // org.jetbrains.kotlin.js.translate.context.generator.Rule
                public final Object apply(DeclarationDescriptor declarationDescriptor) {
                    return this.f$0.m7295x548ba688(declarationDescriptor);
                }
            };
            addRule(new Rule() { // from class: org.jetbrains.kotlin.js.translate.context.StaticContext$ScopeGenerator$$ExternalSyntheticLambda5
                @Override // org.jetbrains.kotlin.js.translate.context.generator.Rule
                public final Object apply(DeclarationDescriptor declarationDescriptor) {
                    return this.f$0.m7296xd6d65b67(declarationDescriptor);
                }
            });
            addRule(rule5);
            addRule(rule);
            addRule(rule2);
            addRule(rule3);
            addRule(rule4);
        }

        /* JADX INFO: renamed from: lambda$new$0$org-jetbrains-kotlin-js-translate-context-StaticContext$ScopeGenerator, reason: not valid java name */
        /* synthetic */ JsScope m7291x4b60d30c(DeclarationDescriptor declarationDescriptor) {
            if (!(declarationDescriptor instanceof ClassDescriptor) || JsDescriptorUtils.getSuperclass((ClassDescriptor) declarationDescriptor) != null) {
                return null;
            }
            JsFunction jsFunction = new JsFunction(new JsRootScope(StaticContext.this.program), new JsBlock(), declarationDescriptor.toString());
            Iterator it = StaticContext.BUILTIN_JS_PROPERTIES.iterator();
            while (it.hasNext()) {
                jsFunction.getScope().declareName((String) it.next());
            }
            StaticContext.this.scopeToFunction.put(jsFunction.getScope(), jsFunction);
            return jsFunction.getScope();
        }

        /* JADX INFO: renamed from: lambda$new$1$org-jetbrains-kotlin-js-translate-context-StaticContext$ScopeGenerator, reason: not valid java name */
        /* synthetic */ JsScope m7292xcdab87eb(DeclarationDescriptor declarationDescriptor) {
            ClassDescriptor superclass;
            if ((declarationDescriptor instanceof ClassDescriptor) && (superclass = JsDescriptorUtils.getSuperclass((ClassDescriptor) declarationDescriptor)) != null) {
                return StaticContext.this.getScopeForDescriptor(superclass).innerObjectScope("Scope for class " + declarationDescriptor.getShortName());
            }
            return null;
        }

        /* JADX INFO: renamed from: lambda$new$2$org-jetbrains-kotlin-js-translate-context-StaticContext$ScopeGenerator, reason: not valid java name */
        /* synthetic */ JsScope m7293x4ff63cca(DeclarationDescriptor declarationDescriptor) {
            return StaticContext.this.fragment.getScope();
        }

        /* JADX INFO: renamed from: lambda$new$3$org-jetbrains-kotlin-js-translate-context-StaticContext$ScopeGenerator, reason: not valid java name */
        /* synthetic */ JsScope m7294xd240f1a9(DeclarationDescriptor declarationDescriptor) {
            return StaticContext.this.fragment.getScope().innerObjectScope("Scope for member " + declarationDescriptor.getShortName());
        }

        /* JADX INFO: renamed from: lambda$new$4$org-jetbrains-kotlin-js-translate-context-StaticContext$ScopeGenerator, reason: not valid java name */
        /* synthetic */ JsScope m7295x548ba688(DeclarationDescriptor declarationDescriptor) {
            if (!(declarationDescriptor instanceof CallableDescriptor)) {
                return null;
            }
            JsFunction jsFunctionCreateFunctionWithEmptyBody = JsAstUtils.createFunctionWithEmptyBody(StaticContext.this.fragment.getScope());
            StaticContext.this.scopeToFunction.put(jsFunctionCreateFunctionWithEmptyBody.getScope(), jsFunctionCreateFunctionWithEmptyBody);
            jsFunctionCreateFunctionWithEmptyBody.setSource(PsiSourceElementKt.getPsi(((CallableDescriptor) declarationDescriptor).getSourceElement()));
            return jsFunctionCreateFunctionWithEmptyBody.getScope();
        }

        /* JADX INFO: renamed from: lambda$new$5$org-jetbrains-kotlin-js-translate-context-StaticContext$ScopeGenerator, reason: not valid java name */
        /* synthetic */ JsScope m7296xd6d65b67(DeclarationDescriptor declarationDescriptor) {
            if (declarationDescriptor instanceof PackageFragmentDescriptor) {
                return StaticContext.this.getScopeForPackage(((PackageFragmentDescriptor) declarationDescriptor).getFqName());
            }
            return null;
        }
    }

    private JsExpression getModuleExpressionFor(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(63);
        }
        JsName moduleInnerName = getModuleInnerName(declarationDescriptor);
        if (moduleInnerName != null) {
            return JsAstUtils.pureFqn(moduleInnerName, (JsExpression) null);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public JsName getModuleInnerName(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(64);
        }
        JsImportedModule jsImportedModule = getJsImportedModule(declarationDescriptor);
        if (jsImportedModule == null) {
            return null;
        }
        return jsImportedModule.getInternalName();
    }

    private JsImportedModule getJsImportedModule(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(65);
        }
        ModuleDescriptor containingModule = DescriptorUtils.getContainingModule(declarationDescriptor);
        if (this.currentModule == containingModule) {
            return this.currentModuleAsImported;
        }
        String moduleName = JsDescriptorUtils.getModuleName(containingModule);
        if ("<unknown>".equals(moduleName)) {
            return null;
        }
        return getImportedModule(moduleName, null);
    }

    public JsImportedModule getImportedModule(String str, DeclarationDescriptor declarationDescriptor) {
        if (str == null) {
            $$$reportNull$$$0(66);
        }
        String plainId = (declarationDescriptor == null || this.config.getModuleKind() != ModuleKind.UMD) ? null : getPlainId(declarationDescriptor);
        JsImportedModuleKey jsImportedModuleKey = new JsImportedModuleKey(str, plainId);
        JsImportedModule jsImportedModuleCreateImportedModule = this.importedModules.get(jsImportedModuleKey);
        if (jsImportedModuleCreateImportedModule == null) {
            jsImportedModuleCreateImportedModule = createImportedModule(jsImportedModuleKey, str, JsScope.declareTemporaryName(Namer.LOCAL_MODULE_PREFIX + Namer.suggestedModuleName(str)), plainId != null ? JsAstUtils.pureFqn(plainId, (JsExpression) null) : null);
        }
        if (jsImportedModuleCreateImportedModule == null) {
            $$$reportNull$$$0(67);
        }
        return jsImportedModuleCreateImportedModule;
    }

    private JsImportedModule createImportedModule(JsImportedModuleKey jsImportedModuleKey, String str, JsName jsName, JsExpression jsExpression) {
        JsImportedModule jsImportedModule = new JsImportedModule(str, jsName, jsExpression);
        this.importedModules.put(jsImportedModuleKey, jsImportedModule);
        this.fragment.getImportedModules().add(jsImportedModule);
        return jsImportedModule;
    }

    private String getPlainId(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(68);
        }
        String str = this.nameSuggestion.suggest(declarationDescriptor, getBindingContext()).getNames().get(0);
        if (str == null) {
            $$$reportNull$$$0(69);
        }
        return str;
    }

    private static void applySideEffects(JsExpression jsExpression, DeclarationDescriptor declarationDescriptor) {
        if ((declarationDescriptor instanceof FunctionDescriptor) || (declarationDescriptor instanceof PackageFragmentDescriptor) || (declarationDescriptor instanceof ClassDescriptor)) {
            MetadataProperties.setSideEffects(jsExpression, SideEffectKind.PURE);
        }
    }

    public void putClassOrConstructorClosure(MemberDescriptor memberDescriptor, List<DeclarationDescriptor> list) {
        if (memberDescriptor == null) {
            $$$reportNull$$$0(70);
        }
        if (list == null) {
            $$$reportNull$$$0(71);
        }
        this.classOrConstructorClosure.put(memberDescriptor, Lists.newArrayList(list));
    }

    public List<DeclarationDescriptor> getClassOrConstructorClosure(MemberDescriptor memberDescriptor) {
        if (memberDescriptor == null) {
            $$$reportNull$$$0(72);
        }
        List<DeclarationDescriptor> list = this.classOrConstructorClosure.get(memberDescriptor);
        if (list != null) {
            return Lists.newArrayList(list);
        }
        return null;
    }

    public Map<ClassDescriptor, List<DeferredCallSite>> getDeferredCallSites() {
        Map<ClassDescriptor, List<DeferredCallSite>> map = this.deferredCallSites;
        if (map == null) {
            $$$reportNull$$$0(73);
        }
        return map;
    }

    public List<JsStatement> getTopLevelStatements() {
        List<JsStatement> statements = this.fragment.getInitializerBlock().getStatements();
        if (statements == null) {
            $$$reportNull$$$0(74);
        }
        return statements;
    }

    public List<JsStatement> getDeclarationStatements() {
        List<JsStatement> statements = this.fragment.getDeclarationBlock().getStatements();
        if (statements == null) {
            $$$reportNull$$$0(75);
        }
        return statements;
    }

    public void addClass(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(76);
        }
        if (AnnotationsUtils.isNativeObject(classDescriptor) || AnnotationsUtils.isLibraryObject(classDescriptor)) {
            return;
        }
        this.fragment.getClasses().put(getInnerNameForDescriptor(classDescriptor), this.classModelGenerator.generateClassModel(classDescriptor));
    }

    public void export(MemberDescriptor memberDescriptor, boolean z) {
        if (memberDescriptor == null) {
            $$$reportNull$$$0(77);
        }
        this.exporter.export(memberDescriptor, z);
    }

    public NameSuggestion getNameSuggestion() {
        NameSuggestion nameSuggestion = this.nameSuggestion;
        if (nameSuggestion == null) {
            $$$reportNull$$$0(78);
        }
        return nameSuggestion;
    }

    public ModuleDescriptor getCurrentModule() {
        ModuleDescriptor moduleDescriptor = this.currentModule;
        if (moduleDescriptor == null) {
            $$$reportNull$$$0(79);
        }
        return moduleDescriptor;
    }

    public void addInlineCall(CallableDescriptor callableDescriptor) {
        if (callableDescriptor == null) {
            $$$reportNull$$$0(80);
        }
        CallableDescriptor callableDescriptor2 = (CallableDescriptor) JsDescriptorUtils.findRealInlineDeclaration(callableDescriptor);
        String functionTag = Namer.getFunctionTag(callableDescriptor2, this.config, getBindingContext());
        JsExpression jsExpressionExportModuleForInline = exportModuleForInline(DescriptorUtils.getContainingModule(callableDescriptor2));
        if (jsExpressionExportModuleForInline == null) {
            jsExpressionExportModuleForInline = getModuleExpressionFor(callableDescriptor2);
        }
        this.fragment.getInlineModuleMap().put(functionTag, jsExpressionExportModuleForInline);
    }

    private JsName getNameForImportsForInline() {
        JsName jsName = this.nameForImportsForInline;
        if (jsName != null) {
            if (jsName == null) {
                $$$reportNull$$$0(82);
            }
            return jsName;
        }
        JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(Namer.IMPORTS_FOR_INLINE_PROPERTY);
        this.fragment.getNameBindings().add(new JsNameBinding(Namer.IMPORTS_FOR_INLINE_PROPERTY, jsNameDeclareTemporaryName));
        this.nameForImportsForInline = jsNameDeclareTemporaryName;
        if (jsNameDeclareTemporaryName == null) {
            $$$reportNull$$$0(81);
        }
        return jsNameDeclareTemporaryName;
    }

    public JsExpression exportModuleForInline(ModuleDescriptor moduleDescriptor) {
        JsImportedModule jsImportedModule;
        if (moduleDescriptor == null) {
            $$$reportNull$$$0(83);
        }
        String moduleName = JsDescriptorUtils.getModuleName(moduleDescriptor);
        if (moduleName.equals(Namer.KOTLIN_LOWER_NAME) || (jsImportedModule = getJsImportedModule(moduleDescriptor)) == null) {
            return null;
        }
        return exportModuleForInline(moduleName, jsImportedModule);
    }

    public JsExpression exportModuleForInline(String str, JsImportedModule jsImportedModule) {
        JsExpression jsArrayAccess;
        if (str == null) {
            $$$reportNull$$$0(84);
        }
        if (jsImportedModule == null) {
            $$$reportNull$$$0(85);
        }
        JsExpression jsExpressionPureFqn = this.modulesImportedForInline.get(str);
        if (jsExpressionPureFqn == null) {
            JsNameRef jsNameRefPureFqn = JsAstUtils.pureFqn(Namer.IMPORTS_FOR_INLINE_PROPERTY, JsAstUtils.pureFqn(getInnerNameForDescriptor(getCurrentModule()), (JsExpression) null));
            JsNameRef jsNameRefPureFqn2 = JsAstUtils.pureFqn(getNameForImportsForInline(), (JsExpression) null);
            if (IdentifierPolicyKt.isValidES5Identifier(str)) {
                jsExpressionPureFqn = JsAstUtils.pureFqn(str, jsNameRefPureFqn);
                jsArrayAccess = JsAstUtils.pureFqn(str, jsNameRefPureFqn2);
            } else {
                JsArrayAccess jsArrayAccess2 = new JsArrayAccess(jsNameRefPureFqn, new JsStringLiteral(str));
                MetadataProperties.setSideEffects(jsArrayAccess2, SideEffectKind.PURE);
                jsArrayAccess = new JsArrayAccess(jsNameRefPureFqn2, new JsStringLiteral(str));
                jsExpressionPureFqn = jsArrayAccess2;
            }
            MetadataProperties.setLocalAlias(jsExpressionPureFqn, jsImportedModule);
            JsExpressionStatement jsExpressionStatement = new JsExpressionStatement(JsAstUtils.assignment(jsArrayAccess, jsImportedModule.getInternalName().makeRef()));
            MetadataProperties.setExportedTag(jsExpressionStatement, "imports:" + str);
            getFragment().getExportBlock().getStatements().add(jsExpressionStatement);
            this.modulesImportedForInline.put(str, jsExpressionPureFqn);
        }
        JsExpression jsExpressionDeepCopy = jsExpressionPureFqn.deepCopy();
        if (jsExpressionDeepCopy == null) {
            $$$reportNull$$$0(86);
        }
        return jsExpressionDeepCopy;
    }

    public JsName getNameForSpecialFunction(final SpecialFunction specialFunction) {
        if (specialFunction == null) {
            $$$reportNull$$$0(87);
        }
        JsName jsNameComputeIfAbsent = this.specialFunctions.computeIfAbsent(specialFunction, new Function() { // from class: org.jetbrains.kotlin.js.translate.context.StaticContext$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m7287xf88335a0(specialFunction, (SpecialFunction) obj);
            }
        });
        if (jsNameComputeIfAbsent == null) {
            $$$reportNull$$$0(88);
        }
        return jsNameComputeIfAbsent;
    }

    /* JADX INFO: renamed from: lambda$getNameForSpecialFunction$0$org-jetbrains-kotlin-js-translate-context-StaticContext, reason: not valid java name */
    /* synthetic */ JsName m7287xf88335a0(SpecialFunction specialFunction, SpecialFunction specialFunction2) {
        JsName jsNameImportDeclaration = importDeclaration(specialFunction2.getSuggestedName(), TranslationUtils.getTagForSpecialFunction(specialFunction2), Namer.createSpecialFunction(specialFunction));
        MetadataProperties.setSpecialFunction(jsNameImportDeclaration, specialFunction2);
        return jsNameImportDeclaration;
    }

    public JsExpression getReferenceToIntrinsic(String str) {
        if (str == null) {
            $$$reportNull$$$0(89);
        }
        JsNameRef jsNameRefPureFqn = JsAstUtils.pureFqn(getNameForIntrinsic(str), (JsExpression) null);
        if (jsNameRefPureFqn == null) {
            $$$reportNull$$$0(90);
        }
        return jsNameRefPureFqn;
    }

    public JsName getNameForIntrinsic(final String str) {
        if (str == null) {
            $$$reportNull$$$0(91);
        }
        JsName jsNameComputeIfAbsent = this.intrinsicNames.computeIfAbsent(str, new Function() { // from class: org.jetbrains.kotlin.js.translate.context.StaticContext$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m7286x38c99735(str, (String) obj);
            }
        });
        if (jsNameComputeIfAbsent == null) {
            $$$reportNull$$$0(92);
        }
        return jsNameComputeIfAbsent;
    }

    /* JADX INFO: renamed from: lambda$getNameForIntrinsic$1$org-jetbrains-kotlin-js-translate-context-StaticContext, reason: not valid java name */
    /* synthetic */ JsName m7286x38c99735(String str, String str2) {
        DeclarationDescriptor declarationDescriptorFindDescriptorForIntrinsic;
        if (this.isStdlib && (declarationDescriptorFindDescriptorForIntrinsic = findDescriptorForIntrinsic(str)) != null) {
            return getInnerNameForDescriptor(declarationDescriptorFindDescriptorForIntrinsic);
        }
        return importDeclaration(NameSuggestion.sanitizeName(str), "intrinsic:" + str, TranslationUtils.getIntrinsicFqn(str));
    }

    private DeclarationDescriptor findDescriptorForIntrinsic(String str) {
        if (str == null) {
            $$$reportNull$$$0(93);
        }
        PackageViewDescriptor packageViewDescriptor = this.currentModule.getPackage(FqName.ROOT);
        FunctionDescriptor functionByNameOrNull = DescriptorUtils.getFunctionByNameOrNull(packageViewDescriptor.getMemberScope(), Name.identifier(str));
        if (functionByNameOrNull != null) {
            return functionByNameOrNull;
        }
        ClassifierDescriptor contributedClassifier = packageViewDescriptor.getMemberScope().mo7465getContributedClassifier(Name.identifier(str), NoLookupLocation.FROM_BACKEND);
        if (contributedClassifier != null) {
            return contributedClassifier;
        }
        return null;
    }

    public JsName getVariableForPropertyMetadata(final VariableDescriptorWithAccessors variableDescriptorWithAccessors) {
        if (variableDescriptorWithAccessors == null) {
            $$$reportNull$$$0(94);
        }
        JsName jsNameComputeIfAbsent = this.propertyMetadataVariables.computeIfAbsent(variableDescriptorWithAccessors, new Function() { // from class: org.jetbrains.kotlin.js.translate.context.StaticContext$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m7288x8e6fa8ee(variableDescriptorWithAccessors, (VariableDescriptorWithAccessors) obj);
            }
        });
        if (jsNameComputeIfAbsent == null) {
            $$$reportNull$$$0(95);
        }
        return jsNameComputeIfAbsent;
    }

    /* JADX INFO: renamed from: lambda$getVariableForPropertyMetadata$2$org-jetbrains-kotlin-js-translate-context-StaticContext, reason: not valid java name */
    /* synthetic */ JsName m7288x8e6fa8ee(VariableDescriptorWithAccessors variableDescriptorWithAccessors, VariableDescriptorWithAccessors variableDescriptorWithAccessors2) {
        JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(NameSuggestion.sanitizeName(getSuggestedName(variableDescriptorWithAccessors) + "_metadata"));
        MetadataProperties.setImported(jsNameDeclareTemporaryName, true);
        this.fragment.getDeclarationBlock().getStatements().add(JsAstUtils.newVar(jsNameDeclareTemporaryName, new JsNew(getReferenceToIntrinsic("PropertyMetadata"), Collections.singletonList(new JsStringLiteral(variableDescriptorWithAccessors.getShortName().asString())))));
        return jsNameDeclareTemporaryName;
    }
}
