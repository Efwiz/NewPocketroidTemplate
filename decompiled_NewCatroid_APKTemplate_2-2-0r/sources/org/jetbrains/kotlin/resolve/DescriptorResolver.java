package org.jetbrains.kotlin.resolve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.google.common.collect.HashMultimap;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SupertypeLoopChecker;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationSplitter;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.annotations.CompositeAnnotations;
import org.jetbrains.kotlin.descriptors.impl.ClassConstructorDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.FieldDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.PropertyDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.PropertyGetterDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.PropertySetterDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.TypeParameterDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtContextReceiver;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtModifierListOwner;
import org.jetbrains.kotlin.psi.KtNullableType;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProjectionKind;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtPureClassOrObject;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.psi.KtTypeConstraint;
import org.jetbrains.kotlin.psi.KtTypeElement;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.psi.KtTypeParameterListOwner;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtUserType;
import org.jetbrains.kotlin.psi.KtVariableDeclaration;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfoFactory;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.UnderscoreUtilKt;
import org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension;
import org.jetbrains.kotlin.resolve.lazy.ForceResolveUtil;
import org.jetbrains.kotlin.resolve.lazy.descriptors.LazyTypeAliasDescriptor;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeKind;
import org.jetbrains.kotlin.resolve.scopes.LexicalWritableScope;
import org.jetbrains.kotlin.resolve.scopes.LocalRedeclarationChecker;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ScopeUtils;
import org.jetbrains.kotlin.resolve.scopes.SubstitutingScope;
import org.jetbrains.kotlin.resolve.scopes.TraceBasedLocalRedeclarationChecker;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.TransientReceiver;
import org.jetbrains.kotlin.resolve.scopes.utils.ScopeUtilsKt;
import org.jetbrains.kotlin.resolve.source.KotlinSourceElementKt;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.DynamicTypesKt;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.SpecialTypesKt;
import org.jetbrains.kotlin.types.TypeApproximator;
import org.jetbrains.kotlin.types.TypeIntersector;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.WrappedTypeFactory;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.expressions.DestructuringDeclarationResolver;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingContext;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingServices;
import org.jetbrains.kotlin.types.expressions.FunctionsTypingVisitor;
import org.jetbrains.kotlin.types.expressions.PreliminaryDeclarationVisitor;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: loaded from: classes6.dex */
public class DescriptorResolver {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final AdditionalClassPartsProvider additionalClassPartsProvider;
    private final AnnotationResolver annotationResolver;
    private final Iterable<DeclarationSignatureAnonymousTypeTransformer> anonymousTypeTransformers;
    private final KotlinBuiltIns builtIns;
    private final DataFlowValueFactory dataFlowValueFactory;
    private final DeclarationReturnTypeSanitizer declarationReturnTypeSanitizer;
    private final DestructuringDeclarationResolver destructuringDeclarationResolver;
    private final ExpressionTypingServices expressionTypingServices;
    private final FunctionsTypingVisitor functionsTypingVisitor;
    private final LanguageVersionSettings languageVersionSettings;
    private final ModifiersChecker modifiersChecker;
    private final OverloadChecker overloadChecker;
    private final StorageManager storageManager;
    private final SupertypeLoopChecker supertypeLoopsResolver;
    private final SyntheticResolveExtension syntheticResolveExtension;
    private final TypeApproximator typeApproximator;
    private final TypeResolver typeResolver;
    private final VariableTypeAndInitializerResolver variableTypeAndInitializerResolver;
    private final WrappedTypeFactory wrappedTypeFactory;

    /* JADX WARN: Code duplicated, block: B:16:0x0033 A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 35 && i != 40 && i != 46 && i != 61 && i != 69 && i != 95 && i != 118 && i != 128 && i != 42 && i != 43) {
            switch (i) {
                default:
                    switch (i) {
                        case 102:
                        case 103:
                        case 104:
                        case 105:
                        case 106:
                        case 107:
                            break;
                        default:
                            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                            break;
                    }
                case 22:
                case 23:
                case 24:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 35 && i != 40 && i != 46 && i != 61 && i != 69 && i != 95 && i != 118 && i != 128 && i != 42 && i != 43) {
            switch (i) {
                case 22:
                case 23:
                case 24:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 102:
                        case 103:
                        case 104:
                        case 105:
                        case 106:
                        case 107:
                            i2 = 2;
                            break;
                        default:
                            i2 = 3;
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
                objArr[0] = "builtIns";
                break;
            case 2:
                objArr[0] = "storageManager";
                break;
            case 3:
                objArr[0] = "typeResolver";
                break;
            case 4:
                objArr[0] = "supertypeLoopsResolver";
                break;
            case 5:
                objArr[0] = "variableTypeAndInitializerResolver";
                break;
            case 6:
                objArr[0] = "expressionTypingServices";
                break;
            case 7:
                objArr[0] = "overloadChecker";
                break;
            case 8:
            case 101:
                objArr[0] = "languageVersionSettings";
                break;
            case 9:
                objArr[0] = "functionsTypingVisitor";
                break;
            case 10:
                objArr[0] = "destructuringDeclarationResolver";
                break;
            case 11:
                objArr[0] = "modifiersChecker";
                break;
            case 12:
                objArr[0] = "wrappedTypeFactory";
                break;
            case 13:
                objArr[0] = "project";
                break;
            case 14:
                objArr[0] = "approximator";
                break;
            case 15:
                objArr[0] = "declarationReturnTypeSanitizer";
                break;
            case 16:
                objArr[0] = "dataFlowValueFactory";
                break;
            case 17:
            case 100:
                objArr[0] = "anonymousTypeTransformers";
                break;
            case 18:
                objArr[0] = "additionalClassPartsProvider";
                break;
            case 19:
            case 29:
            case 36:
            case 56:
            case 59:
            case 64:
            case 66:
            case 119:
            case 124:
            case 131:
            case 133:
            case 138:
                objArr[0] = "scope";
                break;
            case 20:
            case 21:
            case 44:
            case 129:
                objArr[0] = "classDescriptor";
                break;
            case 22:
            case 23:
            case 24:
            case 35:
            case 40:
            case 42:
            case 43:
            case 46:
            case 61:
            case 69:
            case 95:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 118:
            case 128:
                objArr[0] = "org/jetbrains/kotlin/resolve/DescriptorResolver";
                break;
            case 25:
                objArr[0] = "resolver";
                break;
            case 26:
            case 27:
            case 33:
            case 38:
            case 45:
            case 49:
            case 51:
            case 57:
            case 68:
            case 71:
            case 76:
            case 85:
            case 92:
            case 99:
            case 112:
            case 117:
            case 120:
            case 123:
            case 134:
                objArr[0] = "trace";
                break;
            case 28:
            case 32:
            case 63:
            case 98:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 30:
                objArr[0] = "owner";
                break;
            case 31:
            case 130:
                objArr[0] = "valueParameter";
                break;
            case 34:
            case 39:
                objArr[0] = "additionalAnnotations";
                break;
            case 37:
            case 52:
            case 60:
            case 62:
            case 132:
                objArr[0] = "parameter";
                break;
            case 41:
                objArr[0] = "elementType";
                break;
            case 47:
            case 54:
            case 97:
                objArr[0] = "declaration";
                break;
            case 48:
            case 55:
            case 96:
                objArr[0] = "descriptor";
                break;
            case 50:
                objArr[0] = "requests";
                break;
            case 53:
                objArr[0] = "typeParameter";
                break;
            case 58:
                objArr[0] = "upperBoundType";
                break;
            case 65:
            case 72:
            case 81:
                objArr[0] = "containingDeclaration";
                break;
            case 67:
            case 70:
                objArr[0] = "typeAlias";
                break;
            case 73:
            case 82:
            case 89:
            case 113:
                objArr[0] = "scopeForDeclarationResolution";
                break;
            case 74:
            case 83:
            case 90:
                objArr[0] = "scopeForInitializerResolution";
                break;
            case 75:
                objArr[0] = "entry";
                break;
            case 77:
            case 86:
            case 93:
            case 125:
                objArr[0] = "dataFlowInfo";
                break;
            case 78:
            case 87:
                objArr[0] = "inferenceSession";
                break;
            case 79:
                objArr[0] = "destructuringDeclaration";
                break;
            case 80:
                objArr[0] = "context";
                break;
            case 84:
            case 109:
            case 114:
                objArr[0] = "property";
                break;
            case 88:
                objArr[0] = "container";
                break;
            case 91:
                objArr[0] = "variableDeclaration";
                break;
            case 94:
                objArr[0] = "propertyInfo";
                break;
            case 108:
                objArr[0] = "scopeWithTypeParameters";
                break;
            case 110:
            case 115:
                objArr[0] = "propertyDescriptor";
                break;
            case 111:
            case 116:
                objArr[0] = "annotationSplitter";
                break;
            case 121:
                objArr[0] = "getterDescriptor";
                break;
            case 122:
                objArr[0] = "getter";
                break;
            case 126:
                objArr[0] = "function";
                break;
            case 127:
                objArr[0] = "functionDescriptor";
                break;
            case 135:
                objArr[0] = "reportErrorsOn";
                break;
            case 136:
                objArr[0] = "target";
                break;
            case 137:
                objArr[0] = "outer";
                break;
            default:
                objArr[0] = "annotationResolver";
                break;
        }
        if (i == 35) {
            objArr[1] = "resolveValueParameterDescriptor";
        } else if (i == 40) {
            objArr[1] = "resolveValueParameterAnnotations";
        } else if (i == 46) {
            objArr[1] = "createAndRecordPrimaryConstructorForObject";
        } else if (i == 61) {
            objArr[1] = "resolveLocalVariableDescriptor";
        } else if (i == 69) {
            objArr[1] = "resolveTypeAliasDescriptor";
        } else if (i == 95) {
            objArr[1] = "resolveAsPropertyDescriptor";
        } else if (i == 118) {
            objArr[1] = "resolvePropertyGetterDescriptor";
        } else if (i == 128) {
            objArr[1] = "inferReturnTypeFromExpressionBody";
        } else if (i != 42 && i != 43) {
            switch (i) {
                case 22:
                case 23:
                case 24:
                    objArr[1] = "getDefaultSupertype";
                    break;
                default:
                    switch (i) {
                        case 102:
                        case 103:
                        case 104:
                        case 105:
                        case 106:
                        case 107:
                            objArr[1] = "transformAnonymousTypeIfNeeded";
                            break;
                        default:
                            objArr[1] = "org/jetbrains/kotlin/resolve/DescriptorResolver";
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "getVarargParameterType";
        }
        switch (i) {
            case 19:
            case 20:
                objArr[2] = "resolveSupertypes";
                break;
            case 21:
                objArr[2] = "getDefaultSupertype";
                break;
            case 22:
            case 23:
            case 24:
            case 35:
            case 40:
            case 42:
            case 43:
            case 46:
            case 61:
            case 69:
            case 95:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 118:
            case 128:
                break;
            case 25:
                objArr[2] = "resolveSuperTypeListEntries";
                break;
            case 26:
                objArr[2] = "checkNullableSupertypeAndStripQuestionMarks";
                break;
            case 27:
            case 28:
                objArr[2] = "checkProjectionsInImmediateArguments";
                break;
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
                objArr[2] = "resolveValueParameterDescriptor";
                break;
            case 36:
            case 37:
            case 38:
            case 39:
                objArr[2] = "resolveValueParameterAnnotations";
                break;
            case 41:
                objArr[2] = "getVarargParameterType";
                break;
            case 44:
            case 45:
                objArr[2] = "createAndRecordPrimaryConstructorForObject";
                break;
            case 47:
            case 48:
                objArr[2] = "resolveGenericBounds";
                break;
            case 49:
            case 50:
                objArr[2] = "checkUpperBoundTypes";
                break;
            case 51:
            case 52:
            case 53:
                objArr[2] = "checkConflictingUpperBounds";
                break;
            case 54:
            case 55:
            case 56:
            case 57:
                objArr[2] = "checkNamesInConstraints";
                break;
            case 58:
                objArr[2] = "checkUpperBoundType";
                break;
            case 59:
            case 60:
            case 62:
            case 63:
            case 64:
                objArr[2] = "resolveLocalVariableDescriptor";
                break;
            case 65:
            case 66:
            case 67:
            case 68:
                objArr[2] = "resolveTypeAliasDescriptor";
                break;
            case 70:
            case 71:
                objArr[2] = "checkNoGenericBoundsOnTypeAliasParameters";
                break;
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
                objArr[2] = "resolveDestructuringDeclarationEntryAsProperty";
                break;
            case 79:
            case 80:
                objArr[2] = "createReceiverForDestructuringDeclaration";
                break;
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
                objArr[2] = "resolvePropertyDescriptor";
                break;
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
                objArr[2] = "resolveAsPropertyDescriptor";
                break;
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
                objArr[2] = "transformAnonymousTypeIfNeeded";
                break;
            case 108:
            case 109:
            case 110:
            case 111:
            case 112:
                objArr[2] = "resolvePropertySetterDescriptor";
                break;
            case 113:
            case 114:
            case 115:
            case 116:
            case 117:
                objArr[2] = "resolvePropertyGetterDescriptor";
                break;
            case 119:
            case 120:
            case 121:
            case 122:
                objArr[2] = "determineGetterReturnType";
                break;
            case 123:
            case 124:
            case 125:
            case 126:
            case 127:
                objArr[2] = "inferReturnTypeFromExpressionBody";
                break;
            case 129:
            case 130:
            case 131:
            case 132:
                objArr[2] = "resolvePrimaryConstructorParameterToAProperty";
                break;
            case 133:
            case 134:
            case 135:
            case 136:
                objArr[2] = "checkHasOuterClassInstance";
                break;
            case 137:
                objArr[2] = "isInsideOuterClassOrItsSubclass";
                break;
            case 138:
                objArr[2] = "getContainingClass";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 35 && i != 40 && i != 46 && i != 61 && i != 69 && i != 95 && i != 118 && i != 128 && i != 42 && i != 43) {
            switch (i) {
                case 22:
                case 23:
                case 24:
                    break;
                default:
                    switch (i) {
                        case 102:
                        case 103:
                        case 104:
                        case 105:
                        case 106:
                        case 107:
                            break;
                        default:
                            throw new IllegalArgumentException(str2);
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    public DescriptorResolver(AnnotationResolver annotationResolver, KotlinBuiltIns kotlinBuiltIns, StorageManager storageManager, TypeResolver typeResolver, SupertypeLoopChecker supertypeLoopChecker, VariableTypeAndInitializerResolver variableTypeAndInitializerResolver, ExpressionTypingServices expressionTypingServices, OverloadChecker overloadChecker, LanguageVersionSettings languageVersionSettings, FunctionsTypingVisitor functionsTypingVisitor, DestructuringDeclarationResolver destructuringDeclarationResolver, ModifiersChecker modifiersChecker, WrappedTypeFactory wrappedTypeFactory, Project project, TypeApproximator typeApproximator, DeclarationReturnTypeSanitizer declarationReturnTypeSanitizer, DataFlowValueFactory dataFlowValueFactory, Iterable<DeclarationSignatureAnonymousTypeTransformer> iterable, AdditionalClassPartsProvider additionalClassPartsProvider) {
        if (annotationResolver == null) {
            $$$reportNull$$$0(0);
        }
        if (kotlinBuiltIns == null) {
            $$$reportNull$$$0(1);
        }
        if (storageManager == null) {
            $$$reportNull$$$0(2);
        }
        if (typeResolver == null) {
            $$$reportNull$$$0(3);
        }
        if (supertypeLoopChecker == null) {
            $$$reportNull$$$0(4);
        }
        if (variableTypeAndInitializerResolver == null) {
            $$$reportNull$$$0(5);
        }
        if (expressionTypingServices == null) {
            $$$reportNull$$$0(6);
        }
        if (overloadChecker == null) {
            $$$reportNull$$$0(7);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(8);
        }
        if (functionsTypingVisitor == null) {
            $$$reportNull$$$0(9);
        }
        if (destructuringDeclarationResolver == null) {
            $$$reportNull$$$0(10);
        }
        if (modifiersChecker == null) {
            $$$reportNull$$$0(11);
        }
        if (wrappedTypeFactory == null) {
            $$$reportNull$$$0(12);
        }
        if (project == null) {
            $$$reportNull$$$0(13);
        }
        if (typeApproximator == null) {
            $$$reportNull$$$0(14);
        }
        if (declarationReturnTypeSanitizer == null) {
            $$$reportNull$$$0(15);
        }
        if (dataFlowValueFactory == null) {
            $$$reportNull$$$0(16);
        }
        if (iterable == null) {
            $$$reportNull$$$0(17);
        }
        if (additionalClassPartsProvider == null) {
            $$$reportNull$$$0(18);
        }
        this.annotationResolver = annotationResolver;
        this.builtIns = kotlinBuiltIns;
        this.storageManager = storageManager;
        this.typeResolver = typeResolver;
        this.supertypeLoopsResolver = supertypeLoopChecker;
        this.variableTypeAndInitializerResolver = variableTypeAndInitializerResolver;
        this.expressionTypingServices = expressionTypingServices;
        this.overloadChecker = overloadChecker;
        this.languageVersionSettings = languageVersionSettings;
        this.functionsTypingVisitor = functionsTypingVisitor;
        this.destructuringDeclarationResolver = destructuringDeclarationResolver;
        this.modifiersChecker = modifiersChecker;
        this.wrappedTypeFactory = wrappedTypeFactory;
        this.syntheticResolveExtension = SyntheticResolveExtension.INSTANCE.getInstance(project);
        this.typeApproximator = typeApproximator;
        this.declarationReturnTypeSanitizer = declarationReturnTypeSanitizer;
        this.dataFlowValueFactory = dataFlowValueFactory;
        this.anonymousTypeTransformers = iterable;
        this.additionalClassPartsProvider = additionalClassPartsProvider;
    }

    public List<KotlinType> resolveSupertypes(LexicalScope lexicalScope, ClassDescriptor classDescriptor, KtPureClassOrObject ktPureClassOrObject, BindingTrace bindingTrace) {
        if (lexicalScope == null) {
            $$$reportNull$$$0(19);
        }
        if (classDescriptor == null) {
            $$$reportNull$$$0(20);
        }
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        Iterator<KotlinType> it = resolveSuperTypeListEntries(lexicalScope, ktPureClassOrObject == null ? Collections.emptyList() : ktPureClassOrObject.getSuperTypeListEntries(), this.typeResolver, bindingTrace, false).iterator();
        while (it.hasNext()) {
            addValidSupertype(arrayListNewArrayList, it.next());
        }
        if (classDescriptor.getKind() == ClassKind.ENUM_CLASS && !containsClass(arrayListNewArrayList)) {
            arrayListNewArrayList.add(0, this.builtIns.getEnumType(classDescriptor.getDefaultType()));
        }
        this.syntheticResolveExtension.addSyntheticSupertypes(classDescriptor, arrayListNewArrayList);
        arrayListNewArrayList.addAll(this.additionalClassPartsProvider.getAdditionalSupertypes(classDescriptor, arrayListNewArrayList));
        if (arrayListNewArrayList.isEmpty()) {
            addValidSupertype(arrayListNewArrayList, getDefaultSupertype(classDescriptor));
        }
        return arrayListNewArrayList;
    }

    private static void addValidSupertype(List<KotlinType> list, KotlinType kotlinType) {
        if (KotlinTypeKt.isError(kotlinType)) {
            return;
        }
        list.add(kotlinType);
    }

    private static boolean containsClass(Collection<KotlinType> collection) {
        Iterator<KotlinType> it = collection.iterator();
        while (it.hasNext()) {
            ClassifierDescriptor this$0 = it.next().getConstructor().getThis$0();
            if ((this$0 instanceof ClassDescriptor) && ((ClassDescriptor) this$0).getKind() != ClassKind.INTERFACE) {
                return true;
            }
        }
        return false;
    }

    private KotlinType getDefaultSupertype(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(21);
        }
        if (classDescriptor.getKind() == ClassKind.ENUM_ENTRY) {
            SimpleType defaultType = ((ClassDescriptor) classDescriptor.getContainingModule()).getDefaultType();
            if (defaultType == null) {
                $$$reportNull$$$0(22);
            }
            return defaultType;
        }
        if (classDescriptor.getKind() == ClassKind.ANNOTATION_CLASS) {
            SimpleType annotationType = this.builtIns.getAnnotationType();
            if (annotationType == null) {
                $$$reportNull$$$0(23);
            }
            return annotationType;
        }
        SimpleType anyType = this.builtIns.getAnyType();
        if (anyType == null) {
            $$$reportNull$$$0(24);
        }
        return anyType;
    }

    private static Collection<KotlinType> resolveSuperTypeListEntries(LexicalScope lexicalScope, List<KtSuperTypeListEntry> list, TypeResolver typeResolver, BindingTrace bindingTrace, boolean z) {
        if (typeResolver == null) {
            $$$reportNull$$$0(25);
        }
        if (list.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        for (KtSuperTypeListEntry ktSuperTypeListEntry : list) {
            KtTypeReference typeReference = ktSuperTypeListEntry.getTypeReference();
            if (typeReference != null) {
                KotlinType kotlinTypeResolveType = typeResolver.resolveType(lexicalScope, typeReference, bindingTrace, z);
                if (DynamicTypesKt.isDynamic(kotlinTypeResolveType)) {
                    bindingTrace.report(Errors.DYNAMIC_SUPERTYPE.on(typeReference));
                } else {
                    arrayListNewArrayList.add(kotlinTypeResolveType);
                    checkProjectionsInImmediateArguments(bindingTrace, checkNullableSupertypeAndStripQuestionMarks(bindingTrace, typeReference.getTypeElement()), kotlinTypeResolveType);
                }
            } else {
                arrayListNewArrayList.add(ErrorUtils.createErrorType(ErrorTypeKind.UNRESOLVED_TYPE, ktSuperTypeListEntry.getText()));
            }
        }
        return arrayListNewArrayList;
    }

    private static KtTypeElement checkNullableSupertypeAndStripQuestionMarks(BindingTrace bindingTrace, KtTypeElement ktTypeElement) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(26);
        }
        while (ktTypeElement instanceof KtNullableType) {
            KtNullableType ktNullableType = (KtNullableType) ktTypeElement;
            KtTypeElement innerType = ktNullableType.getInnerType();
            if (!(innerType instanceof KtNullableType) && innerType != null) {
                bindingTrace.report(Errors.NULLABLE_SUPERTYPE.on(ktNullableType));
            }
            ktTypeElement = innerType;
        }
        return ktTypeElement;
    }

    private static void checkProjectionsInImmediateArguments(BindingTrace bindingTrace, KtTypeElement ktTypeElement, KotlinType kotlinType) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(27);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(28);
        }
        if (ktTypeElement == null) {
            return;
        }
        boolean z = false;
        if (ktTypeElement instanceof KtUserType) {
            for (KtTypeProjection ktTypeProjection : ((KtUserType) ktTypeElement).getTypeArguments()) {
                if (ktTypeProjection.getProjectionKind() != KtProjectionKind.NONE) {
                    bindingTrace.report(Errors.PROJECTION_IN_IMMEDIATE_ARGUMENT_TO_SUPERTYPE.on(ktTypeProjection));
                    z = true;
                }
            }
        }
        if (KotlinTypeKt.isError(kotlinType) || SpecialTypesKt.getAbbreviatedType(kotlinType) == null || z || !TypeUtilsKt.isInterface(kotlinType) || !TypeUtilsKt.containsTypeProjectionsInTopLevelArguments(kotlinType)) {
            return;
        }
        bindingTrace.report(Errors.EXPANDED_TYPE_CANNOT_BE_INHERITED.on(ktTypeElement, kotlinType));
    }

    public static DescriptorVisibility getDefaultVisibility(KtModifierListOwner ktModifierListOwner, DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor instanceof ClassDescriptor) {
            KtModifierList modifierList = ktModifierListOwner.getModifierList();
            return (modifierList == null || !modifierList.hasModifier(KtTokens.OVERRIDE_KEYWORD)) ? DescriptorVisibilities.DEFAULT_VISIBILITY : DescriptorVisibilities.INHERITED;
        }
        if ((declarationDescriptor instanceof FunctionDescriptor) || (declarationDescriptor instanceof PropertyDescriptor)) {
            return DescriptorVisibilities.LOCAL;
        }
        return DescriptorVisibilities.DEFAULT_VISIBILITY;
    }

    public static Modality getDefaultModality(DeclarationDescriptor declarationDescriptor, DescriptorVisibility descriptorVisibility, boolean z) {
        if (declarationDescriptor instanceof ClassDescriptor) {
            boolean z2 = ((ClassDescriptor) declarationDescriptor).getKind() == ClassKind.INTERFACE;
            return z2 && !z ? Modality.ABSTRACT : (!z2 || DescriptorVisibilities.isPrivate(descriptorVisibility)) ? Modality.FINAL : Modality.OPEN;
        }
        return Modality.FINAL;
    }

    public ValueParameterDescriptorImpl resolveValueParameterDescriptor(final LexicalScope lexicalScope, final FunctionDescriptor functionDescriptor, KtParameter ktParameter, int i, final KotlinType kotlinType, final BindingTrace bindingTrace, Annotations annotations, final InferenceSession inferenceSession) {
        KotlinType kotlinType2;
        KotlinType varargParameterType;
        Function0 function0;
        Name nameSpecial;
        if (lexicalScope == null) {
            $$$reportNull$$$0(29);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(30);
        }
        if (ktParameter == null) {
            $$$reportNull$$$0(31);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(32);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(33);
        }
        if (annotations == null) {
            $$$reportNull$$$0(34);
        }
        if (ktParameter.hasModifier(KtTokens.VARARG_KEYWORD)) {
            varargParameterType = getVarargParameterType(kotlinType);
            kotlinType2 = kotlinType;
        } else {
            kotlinType2 = null;
            varargParameterType = kotlinType;
        }
        Annotations annotationsResolveValueParameterAnnotations = resolveValueParameterAnnotations(lexicalScope, ktParameter, bindingTrace, annotations);
        final KtDestructuringDeclaration destructuringDeclaration = ktParameter.getDestructuringDeclaration();
        if (destructuringDeclaration != null) {
            if (!this.languageVersionSettings.supportsFeature(LanguageFeature.DestructuringLambdaParameters)) {
                bindingTrace.report(Errors.UNSUPPORTED_FEATURE.on(ktParameter, TuplesKt.to(LanguageFeature.DestructuringLambdaParameters, this.languageVersionSettings)));
            }
            function0 = new Function0() { // from class: org.jetbrains.kotlin.resolve.DescriptorResolver$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return this.f$0.m7420x4a807f80(functionDescriptor, lexicalScope, destructuringDeclaration, kotlinType, bindingTrace, inferenceSession);
                }
            };
        } else {
            function0 = null;
        }
        if (destructuringDeclaration != null) {
            nameSpecial = Name.special("<name for destructuring parameter " + i + ">");
        } else if (!ktParameter.hasValOrVar() && UnderscoreUtilKt.isSingleUnderscore(ktParameter)) {
            nameSpecial = Name.special("<anonymous parameter " + i + ">");
        } else {
            nameSpecial = KtPsiUtil.safeName(ktParameter.getName());
        }
        ValueParameterDescriptorImpl valueParameterDescriptorImplCreateWithDestructuringDeclarations = ValueParameterDescriptorImpl.createWithDestructuringDeclarations(functionDescriptor, null, i, annotationsResolveValueParameterAnnotations, nameSpecial, varargParameterType, ktParameter.hasDefaultValue(), ktParameter.hasModifier(KtTokens.CROSSINLINE_KEYWORD), ktParameter.hasModifier(KtTokens.NOINLINE_KEYWORD), kotlinType2, KotlinSourceElementKt.toSourceElement(ktParameter), function0);
        bindingTrace.record(BindingContext.VALUE_PARAMETER, ktParameter, valueParameterDescriptorImplCreateWithDestructuringDeclarations);
        if (valueParameterDescriptorImplCreateWithDestructuringDeclarations == null) {
            $$$reportNull$$$0(35);
        }
        return valueParameterDescriptorImplCreateWithDestructuringDeclarations;
    }

    /* JADX INFO: renamed from: lambda$resolveValueParameterDescriptor$0$org-jetbrains-kotlin-resolve-DescriptorResolver, reason: not valid java name */
    /* synthetic */ List m7420x4a807f80(FunctionDescriptor functionDescriptor, LexicalScope lexicalScope, KtDestructuringDeclaration ktDestructuringDeclaration, KotlinType kotlinType, BindingTrace bindingTrace, InferenceSession inferenceSession) {
        functionDescriptor.mo6753getDispatchReceiverParameter();
        List<VariableDescriptor> listResolveLocalVariablesFromDestructuringDeclaration = this.destructuringDeclarationResolver.resolveLocalVariablesFromDestructuringDeclaration(lexicalScope, ktDestructuringDeclaration, new TransientReceiver(kotlinType), null, ExpressionTypingContext.newContext(bindingTrace, ScopeUtilsKt.createScopeForDestructuring(lexicalScope, functionDescriptor.getExtensionReceiverParameter()), DataFlowInfoFactory.EMPTY, TypeUtils.NO_EXPECTED_TYPE, this.languageVersionSettings, this.dataFlowValueFactory, inferenceSession));
        this.modifiersChecker.withTrace(bindingTrace).checkModifiersForDestructuringDeclaration(ktDestructuringDeclaration);
        return listResolveLocalVariablesFromDestructuringDeclaration;
    }

    private Annotations resolveValueParameterAnnotations(LexicalScope lexicalScope, KtParameter ktParameter, BindingTrace bindingTrace, Annotations annotations) {
        if (lexicalScope == null) {
            $$$reportNull$$$0(36);
        }
        if (ktParameter == null) {
            $$$reportNull$$$0(37);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(38);
        }
        if (annotations == null) {
            $$$reportNull$$$0(39);
        }
        KtModifierList modifierList = ktParameter.getModifierList();
        if (modifierList == null) {
            if (annotations == null) {
                $$$reportNull$$$0(40);
            }
            return annotations;
        }
        Annotations annotationsResolveAnnotationsWithoutArguments = this.annotationResolver.resolveAnnotationsWithoutArguments(lexicalScope, modifierList, bindingTrace);
        if (!ktParameter.hasValOrVar()) {
            return new CompositeAnnotations(annotationsResolveAnnotationsWithoutArguments, annotations);
        }
        return new CompositeAnnotations(new AnnotationSplitter(this.storageManager, annotationsResolveAnnotationsWithoutArguments, SetsKt.setOf(AnnotationUseSiteTarget.CONSTRUCTOR_PARAMETER)).getAnnotationsForTarget(AnnotationUseSiteTarget.CONSTRUCTOR_PARAMETER), annotations);
    }

    private KotlinType getVarargParameterType(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(41);
        }
        SimpleType primitiveArrayKotlinTypeByPrimitiveKotlinType = this.builtIns.getPrimitiveArrayKotlinTypeByPrimitiveKotlinType(kotlinType);
        if (primitiveArrayKotlinTypeByPrimitiveKotlinType != null) {
            if (primitiveArrayKotlinTypeByPrimitiveKotlinType == null) {
                $$$reportNull$$$0(42);
            }
            return primitiveArrayKotlinTypeByPrimitiveKotlinType;
        }
        SimpleType arrayType = this.builtIns.getArrayType(Variance.OUT_VARIANCE, kotlinType);
        if (arrayType == null) {
            $$$reportNull$$$0(43);
        }
        return arrayType;
    }

    public List<TypeParameterDescriptorImpl> resolveTypeParametersForDescriptor(DeclarationDescriptor declarationDescriptor, LexicalWritableScope lexicalWritableScope, LexicalScope lexicalScope, List<KtTypeParameter> list, BindingTrace bindingTrace) {
        List<TypeParameterDescriptorImpl> listResolveTypeParametersForDescriptor = resolveTypeParametersForDescriptor(declarationDescriptor, lexicalScope, list, bindingTrace);
        Iterator<TypeParameterDescriptorImpl> it = listResolveTypeParametersForDescriptor.iterator();
        while (it.hasNext()) {
            lexicalWritableScope.addClassifierDescriptor(it.next());
        }
        return listResolveTypeParametersForDescriptor;
    }

    private List<TypeParameterDescriptorImpl> resolveTypeParametersForDescriptor(DeclarationDescriptor declarationDescriptor, LexicalScope lexicalScope, List<KtTypeParameter> list, BindingTrace bindingTrace) {
        ArrayList arrayList = new ArrayList();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            arrayList.add(resolveTypeParameterForDescriptor(declarationDescriptor, lexicalScope, list.get(i), i, bindingTrace));
        }
        return arrayList;
    }

    private TypeParameterDescriptorImpl resolveTypeParameterForDescriptor(final DeclarationDescriptor declarationDescriptor, LexicalScope lexicalScope, final KtTypeParameter ktTypeParameter, int i, final BindingTrace bindingTrace) {
        if (ktTypeParameter.getVariance() != Variance.INVARIANT) {
            bindingTrace.report(Errors.VARIANCE_ON_TYPE_PARAMETER_NOT_ALLOWED.on(ktTypeParameter));
        }
        TypeParameterDescriptorImpl typeParameterDescriptorImplCreateForFurtherModification = TypeParameterDescriptorImpl.createForFurtherModification(declarationDescriptor, this.annotationResolver.resolveAnnotationsWithArguments(lexicalScope, ktTypeParameter.getModifierList(), bindingTrace), ktTypeParameter.hasModifier(KtTokens.REIFIED_KEYWORD), ktTypeParameter.getVariance(), KtPsiUtil.safeName(ktTypeParameter.getName()), i, KotlinSourceElementKt.toSourceElement(ktTypeParameter), new Function1() { // from class: org.jetbrains.kotlin.resolve.DescriptorResolver$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return DescriptorResolver.lambda$resolveTypeParameterForDescriptor$1(declarationDescriptor, bindingTrace, ktTypeParameter, (KotlinType) obj);
            }
        }, this.supertypeLoopsResolver, this.storageManager);
        bindingTrace.record(BindingContext.TYPE_PARAMETER, ktTypeParameter, typeParameterDescriptorImplCreateForFurtherModification);
        return typeParameterDescriptorImplCreateForFurtherModification;
    }

    static /* synthetic */ Void lambda$resolveTypeParameterForDescriptor$1(DeclarationDescriptor declarationDescriptor, BindingTrace bindingTrace, KtTypeParameter ktTypeParameter, KotlinType kotlinType) {
        if (declarationDescriptor instanceof TypeAliasDescriptor) {
            return null;
        }
        bindingTrace.report(Errors.CYCLIC_GENERIC_UPPER_BOUND.on(ktTypeParameter));
        return null;
    }

    public static ClassConstructorDescriptorImpl createAndRecordPrimaryConstructorForObject(KtPureClassOrObject ktPureClassOrObject, ClassDescriptor classDescriptor, BindingTrace bindingTrace) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(44);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(45);
        }
        ClassConstructorDescriptorImpl classConstructorDescriptorImplCreatePrimaryConstructorForObject = DescriptorFactory.createPrimaryConstructorForObject(classDescriptor, KotlinSourceElementKt.toSourceElement(ktPureClassOrObject));
        if (ktPureClassOrObject instanceof PsiElement) {
            UserDataHolder primaryConstructor = ktPureClassOrObject.getPrimaryConstructor();
            WritableSlice<PsiElement, ConstructorDescriptor> writableSlice = BindingContext.CONSTRUCTOR;
            if (primaryConstructor == null) {
                primaryConstructor = (PsiElement) ktPureClassOrObject;
            }
            bindingTrace.record(writableSlice, primaryConstructor, classConstructorDescriptorImplCreatePrimaryConstructorForObject);
        }
        if (classConstructorDescriptorImplCreatePrimaryConstructorForObject == null) {
            $$$reportNull$$$0(46);
        }
        return classConstructorDescriptorImplCreatePrimaryConstructorForObject;
    }

    static final class UpperBoundCheckRequest {
        public final Name typeParameterName;
        public final KtTypeReference upperBound;
        public final KotlinType upperBoundType;

        UpperBoundCheckRequest(Name name2, KtTypeReference ktTypeReference, KotlinType kotlinType) {
            this.typeParameterName = name2;
            this.upperBound = ktTypeReference;
            this.upperBoundType = kotlinType;
        }
    }

    public void resolveGenericBounds(KtTypeParameterListOwner ktTypeParameterListOwner, DeclarationDescriptor declarationDescriptor, LexicalScope lexicalScope, List<TypeParameterDescriptorImpl> list, BindingTrace bindingTrace) {
        KotlinType kotlinTypeResolveType;
        if (ktTypeParameterListOwner == null) {
            $$$reportNull$$$0(47);
        }
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(48);
        }
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        List<KtTypeParameter> typeParameters = ktTypeParameterListOwner.getTypeParameters();
        HashMap map = new HashMap();
        boolean z = false;
        for (int i = 0; i < typeParameters.size(); i++) {
            KtTypeParameter ktTypeParameter = typeParameters.get(i);
            TypeParameterDescriptorImpl typeParameterDescriptorImpl = list.get(i);
            map.put(typeParameterDescriptorImpl.getShortName(), typeParameterDescriptorImpl);
            KtTypeReference extendsBound = ktTypeParameter.getExtendsBound();
            if (extendsBound != null) {
                KotlinType kotlinTypeResolveType2 = this.typeResolver.resolveType(lexicalScope, extendsBound, bindingTrace, false);
                typeParameterDescriptorImpl.addUpperBound(kotlinTypeResolveType2);
                arrayListNewArrayList.add(new UpperBoundCheckRequest(ktTypeParameter.getNameAsName(), extendsBound, kotlinTypeResolveType2));
            }
        }
        for (KtTypeConstraint ktTypeConstraint : ktTypeParameterListOwner.getTypeConstraints()) {
            KtSimpleNameExpression subjectTypeParameterName = ktTypeConstraint.getSubjectTypeParameterName();
            if (subjectTypeParameterName != null) {
                Name referencedNameAsName = subjectTypeParameterName.getReferencedNameAsName();
                TypeParameterDescriptorImpl typeParameterDescriptorImpl2 = (TypeParameterDescriptorImpl) map.get(referencedNameAsName);
                KtTypeReference boundTypeReference = ktTypeConstraint.getBoundTypeReference();
                if (boundTypeReference != null) {
                    kotlinTypeResolveType = this.typeResolver.resolveType(lexicalScope, boundTypeReference, bindingTrace, z);
                    arrayListNewArrayList.add(new UpperBoundCheckRequest(referencedNameAsName, boundTypeReference, kotlinTypeResolveType));
                } else {
                    kotlinTypeResolveType = null;
                }
                if (typeParameterDescriptorImpl2 != null) {
                    bindingTrace.record(BindingContext.REFERENCE_TARGET, subjectTypeParameterName, typeParameterDescriptorImpl2);
                    if (kotlinTypeResolveType != null) {
                        typeParameterDescriptorImpl2.addUpperBound(kotlinTypeResolveType);
                    }
                }
                z = false;
            }
        }
        for (TypeParameterDescriptorImpl typeParameterDescriptorImpl3 : list) {
            typeParameterDescriptorImpl3.addDefaultUpperBound();
            typeParameterDescriptorImpl3.setInitialized();
        }
        for (TypeParameterDescriptorImpl typeParameterDescriptorImpl4 : list) {
            checkConflictingUpperBounds(bindingTrace, typeParameterDescriptorImpl4, typeParameters.get(typeParameterDescriptorImpl4.getIndex()));
        }
        if (ktTypeParameterListOwner instanceof KtClass) {
            return;
        }
        checkUpperBoundTypes(bindingTrace, arrayListNewArrayList, ktTypeParameterListOwner.hasModifier(KtTokens.OVERRIDE_KEYWORD));
        checkNamesInConstraints(ktTypeParameterListOwner, declarationDescriptor, lexicalScope, bindingTrace);
    }

    public static void checkUpperBoundTypes(BindingTrace bindingTrace, List<UpperBoundCheckRequest> list, boolean z) {
        ClassKind kind;
        if (bindingTrace == null) {
            $$$reportNull$$$0(49);
        }
        if (list == null) {
            $$$reportNull$$$0(50);
        }
        if (list.isEmpty()) {
            return;
        }
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        for (UpperBoundCheckRequest upperBoundCheckRequest : list) {
            Name name2 = upperBoundCheckRequest.typeParameterName;
            KotlinType kotlinType = upperBoundCheckRequest.upperBoundType;
            KtTypeReference ktTypeReference = upperBoundCheckRequest.upperBound;
            if (!KotlinTypeKt.isError(kotlinType)) {
                if (!hashSet2.add(new Pair(name2, kotlinType.getConstructor()))) {
                    bindingTrace.report(Errors.REPEATED_BOUND.on(ktTypeReference));
                } else {
                    ClassDescriptor classDescriptor = TypeUtils.getClassDescriptor(kotlinType);
                    if (classDescriptor != null && (((kind = classDescriptor.getKind()) == ClassKind.CLASS || kind == ClassKind.ENUM_CLASS || kind == ClassKind.OBJECT) && !hashSet.add(name2))) {
                        bindingTrace.report(Errors.ONLY_ONE_CLASS_BOUND_ALLOWED.on(ktTypeReference));
                    }
                }
            }
            checkUpperBoundType(ktTypeReference, kotlinType, bindingTrace, z);
        }
    }

    public static void checkConflictingUpperBounds(BindingTrace bindingTrace, TypeParameterDescriptor typeParameterDescriptor, KtTypeParameter ktTypeParameter) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(51);
        }
        if (typeParameterDescriptor == null) {
            $$$reportNull$$$0(52);
        }
        if (ktTypeParameter == null) {
            $$$reportNull$$$0(53);
        }
        if (KotlinBuiltIns.isNothing(TypeIntersector.getUpperBoundsAsType(typeParameterDescriptor))) {
            bindingTrace.report(Errors.CONFLICTING_UPPER_BOUNDS.on(ktTypeParameter, typeParameterDescriptor));
        }
    }

    public void checkNamesInConstraints(KtTypeParameterListOwner ktTypeParameterListOwner, DeclarationDescriptor declarationDescriptor, LexicalScope lexicalScope, BindingTrace bindingTrace) {
        if (ktTypeParameterListOwner == null) {
            $$$reportNull$$$0(54);
        }
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(55);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(56);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(57);
        }
        for (KtTypeConstraint ktTypeConstraint : ktTypeParameterListOwner.getTypeConstraints()) {
            KtSimpleNameExpression subjectTypeParameterName = ktTypeConstraint.getSubjectTypeParameterName();
            if (subjectTypeParameterName != null) {
                ClassifierDescriptor classifierDescriptorFindClassifier = ScopeUtilsKt.findClassifier(lexicalScope, subjectTypeParameterName.getReferencedNameAsName(), NoLookupLocation.FOR_NON_TRACKED_SCOPE);
                if (!(classifierDescriptorFindClassifier instanceof TypeParameterDescriptor) || classifierDescriptorFindClassifier.getContainingModule() != declarationDescriptor) {
                    if (classifierDescriptorFindClassifier != null) {
                        bindingTrace.report(Errors.NAME_IN_CONSTRAINT_IS_NOT_A_TYPE_PARAMETER.on(subjectTypeParameterName, ktTypeConstraint, ktTypeParameterListOwner));
                        bindingTrace.record(BindingContext.REFERENCE_TARGET, subjectTypeParameterName, classifierDescriptorFindClassifier);
                    } else {
                        bindingTrace.report(Errors.UNRESOLVED_REFERENCE.on(subjectTypeParameterName, subjectTypeParameterName));
                    }
                    KtTypeReference boundTypeReference = ktTypeConstraint.getBoundTypeReference();
                    if (boundTypeReference != null) {
                        this.typeResolver.resolveType(lexicalScope, boundTypeReference, bindingTrace, true);
                    }
                }
            }
        }
    }

    public static void checkUpperBoundType(KtTypeReference ktTypeReference, KotlinType kotlinType, BindingTrace bindingTrace, boolean z) {
        if (kotlinType == null) {
            $$$reportNull$$$0(58);
        }
        if (!z && !TypeUtils.canHaveSubtypes(KotlinTypeChecker.DEFAULT, kotlinType)) {
            bindingTrace.report(Errors.FINAL_UPPER_BOUND.on(ktTypeReference, kotlinType));
        }
        if (DynamicTypesKt.isDynamic(kotlinType)) {
            bindingTrace.report(Errors.DYNAMIC_UPPER_BOUND.on(ktTypeReference));
        }
        if (FunctionTypesKt.isExtensionFunctionType(kotlinType)) {
            bindingTrace.report(Errors.UPPER_BOUND_IS_EXTENSION_FUNCTION_TYPE.on(ktTypeReference));
        }
        if (DefinitelyNonNullableTypesKt.containsIncorrectExplicitDefinitelyNonNullableType(kotlinType)) {
            bindingTrace.report(Errors.INCORRECT_LEFT_COMPONENT_OF_INTERSECTION.on(ktTypeReference));
        }
    }

    public VariableDescriptor resolveLocalVariableDescriptor(LexicalScope lexicalScope, KtParameter ktParameter, BindingTrace bindingTrace) {
        if (lexicalScope == null) {
            $$$reportNull$$$0(59);
        }
        if (ktParameter == null) {
            $$$reportNull$$$0(60);
        }
        VariableDescriptor variableDescriptorResolveLocalVariableDescriptor = resolveLocalVariableDescriptor(ktParameter, resolveParameterType(lexicalScope, ktParameter, bindingTrace), bindingTrace, lexicalScope);
        if (variableDescriptorResolveLocalVariableDescriptor == null) {
            $$$reportNull$$$0(61);
        }
        return variableDescriptorResolveLocalVariableDescriptor;
    }

    private KotlinType resolveParameterType(LexicalScope lexicalScope, KtParameter ktParameter, BindingTrace bindingTrace) {
        KotlinType kotlinTypeCreateErrorType;
        KtTypeReference ktTypeReferenceMo7396getTypeReference = ktParameter.mo7396getTypeReference();
        if (ktTypeReferenceMo7396getTypeReference != null) {
            kotlinTypeCreateErrorType = this.typeResolver.resolveType(lexicalScope, ktTypeReferenceMo7396getTypeReference, bindingTrace, true);
        } else {
            kotlinTypeCreateErrorType = ErrorUtils.createErrorType(ErrorTypeKind.NO_TYPE_SPECIFIED, ktParameter.getText());
        }
        return ktParameter.hasModifier(KtTokens.VARARG_KEYWORD) ? getVarargParameterType(kotlinTypeCreateErrorType) : kotlinTypeCreateErrorType;
    }

    public VariableDescriptor resolveLocalVariableDescriptor(KtParameter ktParameter, KotlinType kotlinType, BindingTrace bindingTrace, LexicalScope lexicalScope) {
        if (ktParameter == null) {
            $$$reportNull$$$0(62);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(63);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(64);
        }
        LocalVariableDescriptor localVariableDescriptor = new LocalVariableDescriptor(lexicalScope.getOwnerDescriptor(), this.annotationResolver.resolveAnnotationsWithArguments(lexicalScope, ktParameter.getModifierList(), bindingTrace), KtPsiUtil.safeName(ktParameter.getName()), this.typeApproximator.approximateDeclarationType(kotlinType, true), KotlinSourceElementKt.toSourceElement(ktParameter));
        bindingTrace.record(BindingContext.VALUE_PARAMETER, ktParameter, localVariableDescriptor);
        ForceResolveUtil.forceResolveAllContents(kotlinType.getAnnotations());
        return localVariableDescriptor;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r5v0 org.jetbrains.kotlin.resolve.BindingTrace
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:236)
        	at jadx.core.dex.visitors.ModVisitor.anonymousCallArgMod(ModVisitor.java:535)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.ModVisitor.processAnonymousConstructor(ModVisitor.java:528)
        	at jadx.core.dex.visitors.ModVisitor.replaceStep(ModVisitor.java:111)
        	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:96)
        */
    public org.jetbrains.kotlin.descriptors.TypeAliasDescriptor resolveTypeAliasDescriptor(org.jetbrains.kotlin.descriptors.DeclarationDescriptor r18, org.jetbrains.kotlin.resolve.scopes.LexicalScope r19, org.jetbrains.kotlin.psi.KtTypeAlias r20, org.jetbrains.kotlin.resolve.BindingTrace r21) {
        /*
            Method dump skipped, instruction units count: 351
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.resolve.DescriptorResolver.resolveTypeAliasDescriptor(org.jetbrains.kotlin.descriptors.DeclarationDescriptor, org.jetbrains.kotlin.resolve.scopes.LexicalScope, org.jetbrains.kotlin.psi.KtTypeAlias, org.jetbrains.kotlin.resolve.BindingTrace):org.jetbrains.kotlin.descriptors.TypeAliasDescriptor");
    }

    /* JADX INFO: renamed from: lambda$resolveTypeAliasDescriptor$2$org-jetbrains-kotlin-resolve-DescriptorResolver, reason: not valid java name */
    /* synthetic */ SimpleType m7418x36036c42(LexicalScope lexicalScope, KtTypeReference ktTypeReference, BindingTrace bindingTrace) {
        return this.typeResolver.resolveAbbreviatedType(lexicalScope, ktTypeReference, bindingTrace);
    }

    /* JADX INFO: renamed from: lambda$resolveTypeAliasDescriptor$3$org-jetbrains-kotlin-resolve-DescriptorResolver, reason: not valid java name */
    /* synthetic */ SimpleType m7419x358d0643(LazyTypeAliasDescriptor lazyTypeAliasDescriptor) {
        return this.typeResolver.resolveExpandedTypeForTypeAlias(lazyTypeAliasDescriptor);
    }

    private static void checkNoGenericBoundsOnTypeAliasParameters(KtTypeAlias ktTypeAlias, BindingTrace bindingTrace) {
        if (ktTypeAlias == null) {
            $$$reportNull$$$0(70);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(71);
        }
        Iterator<KtTypeParameter> it = ktTypeAlias.getTypeParameters().iterator();
        while (it.hasNext()) {
            KtTypeReference extendsBound = it.next().getExtendsBound();
            if (extendsBound != null) {
                bindingTrace.report(Errors.BOUND_ON_TYPE_ALIAS_PARAMETER_NOT_ALLOWED.on(extendsBound));
            }
        }
    }

    public PropertyDescriptor resolveDestructuringDeclarationEntryAsProperty(DeclarationDescriptor declarationDescriptor, LexicalScope lexicalScope, LexicalScope lexicalScope2, KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry, BindingTrace bindingTrace, DataFlowInfo dataFlowInfo, InferenceSession inferenceSession) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(72);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(73);
        }
        if (lexicalScope2 == null) {
            $$$reportNull$$$0(74);
        }
        if (ktDestructuringDeclarationEntry == null) {
            $$$reportNull$$$0(75);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(76);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(77);
        }
        if (inferenceSession == null) {
            $$$reportNull$$$0(78);
        }
        KtDestructuringDeclaration ktDestructuringDeclaration = (KtDestructuringDeclaration) ktDestructuringDeclarationEntry.getLightParent();
        KtExpression initializer = ktDestructuringDeclaration.getInitializer();
        ExpressionTypingContext expressionTypingContextNewContext = ExpressionTypingContext.newContext(bindingTrace, lexicalScope, dataFlowInfo, TypeUtils.NO_EXPECTED_TYPE, this.languageVersionSettings, this.dataFlowValueFactory, inferenceSession);
        return resolveAsPropertyDescriptor(declarationDescriptor, lexicalScope, lexicalScope2, ktDestructuringDeclarationEntry, bindingTrace, dataFlowInfo, inferenceSession, VariableAsPropertyInfo.INSTANCE.createFromDestructuringDeclarationEntry(this.destructuringDeclarationResolver.resolveInitializer(ktDestructuringDeclarationEntry, createReceiverForDestructuringDeclaration(ktDestructuringDeclaration, expressionTypingContextNewContext), initializer, expressionTypingContextNewContext, ktDestructuringDeclaration.getEntries().indexOf(ktDestructuringDeclarationEntry))));
    }

    private ExpressionReceiver createReceiverForDestructuringDeclaration(KtDestructuringDeclaration ktDestructuringDeclaration, ExpressionTypingContext expressionTypingContext) {
        KotlinType type;
        if (ktDestructuringDeclaration == null) {
            $$$reportNull$$$0(79);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(80);
        }
        KtExpression initializer = ktDestructuringDeclaration.getInitializer();
        if (initializer == null || (type = this.expressionTypingServices.getTypeInfo(initializer, expressionTypingContext).getType()) == null) {
            return null;
        }
        return ExpressionReceiver.INSTANCE.create(initializer, type, expressionTypingContext.trace.getContext());
    }

    public PropertyDescriptor resolvePropertyDescriptor(DeclarationDescriptor declarationDescriptor, LexicalScope lexicalScope, LexicalScope lexicalScope2, KtProperty ktProperty, BindingTrace bindingTrace, DataFlowInfo dataFlowInfo, InferenceSession inferenceSession) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(81);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(82);
        }
        if (lexicalScope2 == null) {
            $$$reportNull$$$0(83);
        }
        if (ktProperty == null) {
            $$$reportNull$$$0(84);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(85);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(86);
        }
        if (inferenceSession == null) {
            $$$reportNull$$$0(87);
        }
        return resolveAsPropertyDescriptor(declarationDescriptor, lexicalScope, lexicalScope2, ktProperty, bindingTrace, dataFlowInfo, inferenceSession, VariableAsPropertyInfo.INSTANCE.createFromProperty(ktProperty));
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private PropertyDescriptor resolveAsPropertyDescriptor(DeclarationDescriptor declarationDescriptor, LexicalScope lexicalScope, LexicalScope lexicalScope2, KtVariableDeclaration ktVariableDeclaration, final BindingTrace bindingTrace, DataFlowInfo dataFlowInfo, InferenceSession inferenceSession, VariableAsPropertyInfo variableAsPropertyInfo) {
        List<TypeParameterDescriptorImpl> listEmptyList;
        LexicalScope lexicalScope3;
        LexicalScope lexicalScope4;
        ReceiverParameterDescriptor receiverParameterDescriptorCreateExtensionReceiverParameterForCallable;
        String strNameForReceiverLabel;
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(88);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(89);
        }
        if (lexicalScope2 == null) {
            $$$reportNull$$$0(90);
        }
        if (ktVariableDeclaration == null) {
            $$$reportNull$$$0(91);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(92);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(93);
        }
        if (variableAsPropertyInfo == null) {
            $$$reportNull$$$0(94);
        }
        KtModifierList modifierList = ktVariableDeclaration.getModifierList();
        boolean zIsVar = ktVariableDeclaration.isVar();
        DescriptorVisibility descriptorVisibilityResolveVisibilityFromModifiers = ModifiersChecker.resolveVisibilityFromModifiers(ktVariableDeclaration, getDefaultVisibility(ktVariableDeclaration, declarationDescriptor));
        boolean z = declarationDescriptor instanceof ClassDescriptor;
        Modality modalityResolveMemberModalityFromModifiers = z ? ModifiersChecker.resolveMemberModalityFromModifiers(ktVariableDeclaration, getDefaultModality(declarationDescriptor, descriptorVisibilityResolveVisibilityFromModifiers, variableAsPropertyInfo.getHasBody()), bindingTrace.getContext(), declarationDescriptor) : Modality.FINAL;
        Annotations annotationsResolveAnnotationsWithoutArguments = this.annotationResolver.resolveAnnotationsWithoutArguments(lexicalScope, modifierList, bindingTrace);
        EnumSet enumSetOf = EnumSet.of(AnnotationUseSiteTarget.PROPERTY, AnnotationUseSiteTarget.PROPERTY_GETTER, AnnotationUseSiteTarget.FIELD);
        if (zIsVar) {
            enumSetOf.add(AnnotationUseSiteTarget.PROPERTY_SETTER);
            enumSetOf.add(AnnotationUseSiteTarget.SETTER_PARAMETER);
        }
        if ((ktVariableDeclaration instanceof KtProperty) && ((KtProperty) ktVariableDeclaration).hasDelegate()) {
            enumSetOf.add(AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD);
        }
        AnnotationSplitter annotationSplitter = new AnnotationSplitter(this.storageManager, annotationsResolveAnnotationsWithoutArguments, enumSetOf);
        final PropertyDescriptorImpl propertyDescriptorImplCreate = PropertyDescriptorImpl.create(declarationDescriptor, new CompositeAnnotations((List<? extends Annotations>) CollectionsKt.listOf((Object[]) new Annotations[]{annotationSplitter.getAnnotationsForTarget(AnnotationUseSiteTarget.PROPERTY), annotationSplitter.getOtherAnnotations()})), modalityResolveMemberModalityFromModifiers, descriptorVisibilityResolveVisibilityFromModifiers, zIsVar, KtPsiUtil.safeName(ktVariableDeclaration.getName()), CallableMemberDescriptor.Kind.DECLARATION, KotlinSourceElementKt.toSourceElement(ktVariableDeclaration), modifierList != null && modifierList.hasModifier(KtTokens.LATEINIT_KEYWORD), modifierList != null && modifierList.hasModifier(KtTokens.CONST_KEYWORD), (modifierList != null && PsiUtilsKt.hasExpectModifier(modifierList) && (declarationDescriptor instanceof PackageFragmentDescriptor)) || (z && ((ClassDescriptor) declarationDescriptor).get$isExpect()), modifierList != null && PsiUtilsKt.hasActualModifier(modifierList), modifierList != null && modifierList.hasModifier(KtTokens.EXTERNAL_KEYWORD), variableAsPropertyInfo.getHasDelegate());
        List<KtTypeParameter> typeParameters = ktVariableDeclaration.getTypeParameters();
        if (typeParameters.isEmpty()) {
            lexicalScope3 = lexicalScope2;
            listEmptyList = Collections.emptyList();
            lexicalScope4 = lexicalScope;
        } else {
            LexicalWritableScope lexicalWritableScope = new LexicalWritableScope(lexicalScope, declarationDescriptor, false, new TraceBasedLocalRedeclarationChecker(bindingTrace, this.overloadChecker), LexicalScopeKind.PROPERTY_HEADER);
            LexicalWritableScope lexicalWritableScope2 = new LexicalWritableScope(lexicalScope2, declarationDescriptor, false, LocalRedeclarationChecker.DO_NOTHING.INSTANCE, LexicalScopeKind.PROPERTY_HEADER);
            List<TypeParameterDescriptorImpl> listResolveTypeParametersForDescriptor = resolveTypeParametersForDescriptor(propertyDescriptorImplCreate, lexicalScope, typeParameters, bindingTrace);
            for (TypeParameterDescriptorImpl typeParameterDescriptorImpl : listResolveTypeParametersForDescriptor) {
                lexicalWritableScope.addClassifierDescriptor(typeParameterDescriptorImpl);
                lexicalWritableScope2.addClassifierDescriptor(typeParameterDescriptorImpl);
            }
            lexicalWritableScope.freeze();
            lexicalWritableScope2.freeze();
            resolveGenericBounds(ktVariableDeclaration, propertyDescriptorImplCreate, lexicalWritableScope, listResolveTypeParametersForDescriptor, bindingTrace);
            listEmptyList = listResolveTypeParametersForDescriptor;
            lexicalScope4 = lexicalWritableScope;
            lexicalScope3 = lexicalWritableScope2;
        }
        KtTypeReference receiverTypeReference = ktVariableDeclaration.mo7393getReceiverTypeReference();
        if (receiverTypeReference != null) {
            KotlinType kotlinTypeResolveType = this.typeResolver.resolveType(lexicalScope4, receiverTypeReference, bindingTrace, true);
            receiverParameterDescriptorCreateExtensionReceiverParameterForCallable = DescriptorFactory.createExtensionReceiverParameterForCallable(propertyDescriptorImplCreate, kotlinTypeResolveType, new AnnotationSplitter(this.storageManager, kotlinTypeResolveType.getAnnotations(), EnumSet.of(AnnotationUseSiteTarget.RECEIVER)).getAnnotationsForTarget(AnnotationUseSiteTarget.RECEIVER));
        } else {
            receiverParameterDescriptorCreateExtensionReceiverParameterForCallable = null;
        }
        ReceiverParameterDescriptor receiverParameterDescriptor = receiverParameterDescriptorCreateExtensionReceiverParameterForCallable;
        final List<KtContextReceiver> contextReceivers = ktVariableDeclaration.getContextReceivers();
        final LexicalScope lexicalScope5 = lexicalScope4;
        List<ReceiverParameterDescriptor> list = (List) IntStream.range(0, contextReceivers.size()).mapToObj(new IntFunction() { // from class: org.jetbrains.kotlin.resolve.DescriptorResolver$$ExternalSyntheticLambda5
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return this.f$0.m7417x62197973(contextReceivers, lexicalScope5, bindingTrace, propertyDescriptorImplCreate, i);
            }
        }).collect(Collectors.toList());
        if (this.languageVersionSettings.supportsFeature(LanguageFeature.ContextReceivers)) {
            HashMultimap hashMultimapCreate = HashMultimap.create();
            if (receiverTypeReference != null && (strNameForReceiverLabel = receiverTypeReference.nameForReceiverLabel()) != null) {
                hashMultimapCreate.put(strNameForReceiverLabel, receiverParameterDescriptor);
            }
            for (int i = 0; i < contextReceivers.size(); i++) {
                String strName = contextReceivers.get(i).name();
                if (strName != null) {
                    hashMultimapCreate.put(strName, list.get(i));
                }
            }
            bindingTrace.record(BindingContext.DESCRIPTOR_TO_CONTEXT_RECEIVER_MAP, propertyDescriptorImplCreate, hashMultimapCreate);
        }
        LexicalScope lexicalScopeMakeScopeForPropertyInitializer = ScopeUtils.makeScopeForPropertyInitializer(lexicalScope3, propertyDescriptorImplCreate);
        KotlinType variableType = variableAsPropertyInfo.getVariableType();
        KotlinType kotlinTypeResolveTypeNullable = variableType != null ? variableType : this.variableTypeAndInitializerResolver.resolveTypeNullable(propertyDescriptorImplCreate, lexicalScopeMakeScopeForPropertyInitializer, ktVariableDeclaration, dataFlowInfo, inferenceSession, bindingTrace, false);
        PropertyGetterDescriptorImpl propertyGetterDescriptorImplResolvePropertyGetterDescriptor = resolvePropertyGetterDescriptor(lexicalScope4, ktVariableDeclaration, propertyDescriptorImplCreate, annotationSplitter, bindingTrace, kotlinTypeResolveTypeNullable, variableAsPropertyInfo.getPropertyGetter(), variableAsPropertyInfo.getHasDelegate(), inferenceSession);
        KotlinType returnType = kotlinTypeResolveTypeNullable != null ? kotlinTypeResolveTypeNullable : propertyGetterDescriptorImplResolvePropertyGetterDescriptor.getReturnType();
        this.variableTypeAndInitializerResolver.setConstantForVariableIfNeeded(propertyDescriptorImplCreate, lexicalScopeMakeScopeForPropertyInitializer, ktVariableDeclaration, dataFlowInfo, returnType, inferenceSession, bindingTrace);
        propertyDescriptorImplCreate.setType(returnType, listEmptyList, DescriptorUtils.getDispatchReceiverParameterIfNeeded(declarationDescriptor), receiverParameterDescriptor, list);
        propertyDescriptorImplCreate.initialize(propertyGetterDescriptorImplResolvePropertyGetterDescriptor, resolvePropertySetterDescriptor(lexicalScope4, ktVariableDeclaration, propertyDescriptorImplCreate, annotationSplitter, bindingTrace, variableAsPropertyInfo.getPropertySetter(), variableAsPropertyInfo.getHasDelegate(), inferenceSession), new FieldDescriptorImpl(annotationSplitter.getAnnotationsForTarget(AnnotationUseSiteTarget.FIELD), propertyDescriptorImplCreate), new FieldDescriptorImpl(annotationSplitter.getAnnotationsForTarget(AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD), propertyDescriptorImplCreate));
        bindingTrace.record(BindingContext.VARIABLE, ktVariableDeclaration, propertyDescriptorImplCreate);
        if (propertyDescriptorImplCreate == null) {
            $$$reportNull$$$0(95);
        }
        return propertyDescriptorImplCreate;
    }

    /* JADX INFO: renamed from: lambda$resolveAsPropertyDescriptor$4$org-jetbrains-kotlin-resolve-DescriptorResolver, reason: not valid java name */
    /* synthetic */ ReceiverParameterDescriptor m7417x62197973(List list, LexicalScope lexicalScope, BindingTrace bindingTrace, PropertyDescriptorImpl propertyDescriptorImpl, int i) {
        KtContextReceiver ktContextReceiver = (KtContextReceiver) list.get(i);
        KtTypeReference ktTypeReferenceTypeReference = ktContextReceiver.typeReference();
        if (ktTypeReferenceTypeReference == null) {
            return null;
        }
        KotlinType kotlinTypeResolveType = this.typeResolver.resolveType(lexicalScope, ktTypeReferenceTypeReference, bindingTrace, true);
        return DescriptorFactory.createContextReceiverParameterForCallable(propertyDescriptorImpl, kotlinTypeResolveType, ktContextReceiver.labelNameAsName(), new AnnotationSplitter(this.storageManager, kotlinTypeResolveType.getAnnotations(), EnumSet.of(AnnotationUseSiteTarget.RECEIVER)).getAnnotationsForTarget(AnnotationUseSiteTarget.RECEIVER), i);
    }

    static KotlinType transformAnonymousTypeIfNeeded(DeclarationDescriptorWithVisibility declarationDescriptorWithVisibility, KtDeclaration ktDeclaration, KotlinType kotlinType, BindingTrace bindingTrace, Iterable<DeclarationSignatureAnonymousTypeTransformer> iterable, LanguageVersionSettings languageVersionSettings) {
        if (declarationDescriptorWithVisibility == null) {
            $$$reportNull$$$0(96);
        }
        if (ktDeclaration == null) {
            $$$reportNull$$$0(97);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(98);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(99);
        }
        if (iterable == null) {
            $$$reportNull$$$0(100);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(101);
        }
        Iterator<DeclarationSignatureAnonymousTypeTransformer> it = iterable.iterator();
        while (it.hasNext()) {
            KotlinType kotlinTypeTransformAnonymousType = it.next().transformAnonymousType(declarationDescriptorWithVisibility, kotlinType);
            if (kotlinTypeTransformAnonymousType != null) {
                if (kotlinTypeTransformAnonymousType == null) {
                    $$$reportNull$$$0(102);
                }
                return kotlinTypeTransformAnonymousType;
            }
        }
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        if (this$0 == null || !DescriptorUtils.isAnonymousObject(this$0) || DescriptorUtils.isLocal(declarationDescriptorWithVisibility)) {
            if (kotlinType == null) {
                $$$reportNull$$$0(103);
            }
            return kotlinType;
        }
        boolean zIsPrivate = DescriptorVisibilities.isPrivate(declarationDescriptorWithVisibility.getVisibilityImpl());
        boolean z = (declarationDescriptorWithVisibility instanceof SimpleFunctionDescriptor) && ((SimpleFunctionDescriptor) declarationDescriptorWithVisibility).isInline();
        boolean zSupportsFeature = languageVersionSettings.supportsFeature(LanguageFeature.ApproximateAnonymousReturnTypesInPrivateInlineFunctions);
        if (!zIsPrivate || (z && zSupportsFeature)) {
            if (kotlinType.getConstructor().mo7438getSupertypes().size() == 1) {
                KotlinType next = kotlinType.getConstructor().mo7438getSupertypes().iterator().next();
                MemberScope memberScope = kotlinType.getMemberScope();
                if (memberScope instanceof SubstitutingScope) {
                    next = ((SubstitutingScope) memberScope).substitute(next);
                }
                UnwrappedType unwrappedTypeUnwrap = kotlinType.unwrap();
                boolean zIsMarkedNullable = FlexibleTypesKt.lowerIfFlexible(unwrappedTypeUnwrap).getIsMarkedNullable();
                boolean zIsMarkedNullable2 = FlexibleTypesKt.upperIfFlexible(unwrappedTypeUnwrap).getIsMarkedNullable();
                if (!languageVersionSettings.supportsFeature(LanguageFeature.KeepNullabilityWhenApproximatingLocalType)) {
                    if (zIsMarkedNullable2) {
                        if (zIsMarkedNullable) {
                            bindingTrace.report(Errors.APPROXIMATED_LOCAL_TYPE_WILL_BECOME_NULLABLE.on(ktDeclaration, next));
                        } else {
                            bindingTrace.report(Errors.APPROXIMATED_LOCAL_TYPE_WILL_BECOME_FLEXIBLE.on(ktDeclaration, next));
                        }
                    }
                    if (next == null) {
                        $$$reportNull$$$0(106);
                    }
                    return next;
                }
                if (zIsMarkedNullable != zIsMarkedNullable2) {
                    UnwrappedType unwrappedTypeFlexibleType = KotlinTypeFactory.flexibleType(FlexibleTypesKt.lowerIfFlexible(next), FlexibleTypesKt.upperIfFlexible(next).makeNullableAsSpecified(true));
                    if (unwrappedTypeFlexibleType == null) {
                        $$$reportNull$$$0(104);
                    }
                    return unwrappedTypeFlexibleType;
                }
                KotlinType kotlinTypeMakeNullableIfNeeded = TypeUtils.makeNullableIfNeeded(next, zIsMarkedNullable2);
                if (kotlinTypeMakeNullableIfNeeded == null) {
                    $$$reportNull$$$0(105);
                }
                return kotlinTypeMakeNullableIfNeeded;
            }
            bindingTrace.report(Errors.AMBIGUOUS_ANONYMOUS_TYPE_INFERRED.on(ktDeclaration, kotlinType.getConstructor().mo7438getSupertypes()));
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(107);
        }
        return kotlinType;
    }

    private PropertySetterDescriptor resolvePropertySetterDescriptor(LexicalScope lexicalScope, KtVariableDeclaration ktVariableDeclaration, PropertyDescriptor propertyDescriptor, AnnotationSplitter annotationSplitter, BindingTrace bindingTrace, KtPropertyAccessor ktPropertyAccessor, boolean z, InferenceSession inferenceSession) {
        PropertySetterDescriptorImpl propertySetterDescriptorImplCreateSetter;
        PropertySetterDescriptorImpl propertySetterDescriptorImpl;
        KotlinType type;
        if (lexicalScope == null) {
            $$$reportNull$$$0(108);
        }
        if (ktVariableDeclaration == null) {
            $$$reportNull$$$0(109);
        }
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(110);
        }
        if (annotationSplitter == null) {
            $$$reportNull$$$0(111);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(112);
        }
        Annotations annotationsForTarget = annotationSplitter.getAnnotationsForTarget(AnnotationUseSiteTarget.PROPERTY_SETTER);
        Annotations annotationsForTarget2 = annotationSplitter.getAnnotationsForTarget(AnnotationUseSiteTarget.SETTER_PARAMETER);
        if (ktPropertyAccessor != null) {
            CompositeAnnotations compositeAnnotations = new CompositeAnnotations((List<? extends Annotations>) CollectionsKt.listOf((Object[]) new Annotations[]{annotationsForTarget, this.annotationResolver.resolveAnnotationsWithoutArguments(lexicalScope, ktPropertyAccessor.getModifierList(), bindingTrace)}));
            KtParameter parameter = ktPropertyAccessor.getParameter();
            PropertySetterDescriptorImpl propertySetterDescriptorImpl2 = new PropertySetterDescriptorImpl(propertyDescriptor, compositeAnnotations, ModifiersChecker.resolveMemberModalityFromModifiers(ktPropertyAccessor, propertyDescriptor.getModality(), bindingTrace.getContext(), propertyDescriptor.getContainingModule()), ModifiersChecker.resolveVisibilityFromModifiers(ktPropertyAccessor, propertyDescriptor.getVisibilityImpl()), false, ktPropertyAccessor.hasModifier(KtTokens.EXTERNAL_KEYWORD), ktVariableDeclaration.hasModifier(KtTokens.INLINE_KEYWORD) || ktPropertyAccessor.hasModifier(KtTokens.INLINE_KEYWORD), CallableMemberDescriptor.Kind.DECLARATION, null, KotlinSourceElementKt.toSourceElement(ktPropertyAccessor));
            KtTypeReference returnTypeReference = ktPropertyAccessor.getReturnTypeReference();
            if (returnTypeReference != null && !KotlinBuiltIns.isUnit(this.typeResolver.resolveType(lexicalScope, returnTypeReference, bindingTrace, true))) {
                bindingTrace.report(Errors.WRONG_SETTER_RETURN_TYPE.on(returnTypeReference));
            }
            if (parameter != null) {
                if (parameter.hasDefaultValue()) {
                    bindingTrace.report(Errors.SETTER_PARAMETER_WITH_DEFAULT_VALUE.on(parameter.getDefaultValue()));
                }
                KtTypeReference ktTypeReferenceMo7396getTypeReference = parameter.mo7396getTypeReference();
                if (ktTypeReferenceMo7396getTypeReference == null) {
                    type = propertyDescriptor.getType();
                } else {
                    KotlinType kotlinTypeResolveType = this.typeResolver.resolveType(lexicalScope, ktTypeReferenceMo7396getTypeReference, bindingTrace, true);
                    KotlinType type2 = propertyDescriptor.getType();
                    if (!TypeUtils.equalTypes(kotlinTypeResolveType, type2)) {
                        bindingTrace.report(Errors.WRONG_SETTER_PARAMETER_TYPE.on(ktTypeReferenceMo7396getTypeReference, type2, kotlinTypeResolveType));
                    }
                    type = kotlinTypeResolveType;
                }
                propertySetterDescriptorImpl = propertySetterDescriptorImpl2;
                propertySetterDescriptorImpl.initialize(resolveValueParameterDescriptor(lexicalScope, propertySetterDescriptorImpl2, parameter, 0, type, bindingTrace, annotationsForTarget2, inferenceSession));
            } else {
                propertySetterDescriptorImpl = propertySetterDescriptorImpl2;
                propertySetterDescriptorImpl.initializeDefault();
            }
            bindingTrace.record(BindingContext.PROPERTY_ACCESSOR, ktPropertyAccessor, propertySetterDescriptorImpl);
            propertySetterDescriptorImplCreateSetter = propertySetterDescriptorImpl;
        } else if (ktVariableDeclaration.isVar()) {
            propertySetterDescriptorImplCreateSetter = DescriptorFactory.createSetter(propertyDescriptor, annotationsForTarget, annotationsForTarget2, !z && annotationsForTarget.isEmpty() && annotationsForTarget2.isEmpty(), false, ktVariableDeclaration.hasModifier(KtTokens.INLINE_KEYWORD), propertyDescriptor.getSourceElement());
        } else {
            propertySetterDescriptorImplCreateSetter = null;
        }
        if (!ktVariableDeclaration.isVar() && ktPropertyAccessor != null) {
            bindingTrace.report(Errors.VAL_WITH_SETTER.on(ktPropertyAccessor));
        }
        return propertySetterDescriptorImplCreateSetter;
    }

    private PropertyGetterDescriptorImpl resolvePropertyGetterDescriptor(LexicalScope lexicalScope, KtVariableDeclaration ktVariableDeclaration, PropertyDescriptor propertyDescriptor, AnnotationSplitter annotationSplitter, BindingTrace bindingTrace, KotlinType kotlinType, KtPropertyAccessor ktPropertyAccessor, boolean z, InferenceSession inferenceSession) {
        PropertyGetterDescriptorImpl propertyGetterDescriptorImplCreateGetter;
        KotlinType typeForPropertyWithoutReturnType;
        if (lexicalScope == null) {
            $$$reportNull$$$0(113);
        }
        if (ktVariableDeclaration == null) {
            $$$reportNull$$$0(114);
        }
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(115);
        }
        if (annotationSplitter == null) {
            $$$reportNull$$$0(116);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(117);
        }
        Annotations annotationsForTarget = annotationSplitter.getAnnotationsForTarget(AnnotationUseSiteTarget.PROPERTY_GETTER);
        if (ktPropertyAccessor != null) {
            propertyGetterDescriptorImplCreateGetter = new PropertyGetterDescriptorImpl(propertyDescriptor, new CompositeAnnotations((List<? extends Annotations>) CollectionsKt.listOf((Object[]) new Annotations[]{annotationsForTarget, this.annotationResolver.resolveAnnotationsWithoutArguments(lexicalScope, ktPropertyAccessor.getModifierList(), bindingTrace)})), ModifiersChecker.resolveMemberModalityFromModifiers(ktPropertyAccessor, propertyDescriptor.getModality(), bindingTrace.getContext(), propertyDescriptor.getContainingModule()), ModifiersChecker.resolveVisibilityFromModifiers(ktPropertyAccessor, propertyDescriptor.getVisibilityImpl()), false, ktPropertyAccessor.hasModifier(KtTokens.EXTERNAL_KEYWORD), ktVariableDeclaration.hasModifier(KtTokens.INLINE_KEYWORD) || ktPropertyAccessor.hasModifier(KtTokens.INLINE_KEYWORD), CallableMemberDescriptor.Kind.DECLARATION, null, KotlinSourceElementKt.toSourceElement(ktPropertyAccessor));
            typeForPropertyWithoutReturnType = determineGetterReturnType(lexicalScope, bindingTrace, propertyGetterDescriptorImplCreateGetter, ktPropertyAccessor, kotlinType, inferenceSession);
        } else {
            propertyGetterDescriptorImplCreateGetter = DescriptorFactory.createGetter(propertyDescriptor, annotationsForTarget, !z && annotationsForTarget.isEmpty(), false, ktVariableDeclaration.hasModifier(KtTokens.INLINE_KEYWORD));
            typeForPropertyWithoutReturnType = kotlinType;
        }
        PropertyGetterDescriptorImpl propertyGetterDescriptorImpl = propertyGetterDescriptorImplCreateGetter;
        if (typeForPropertyWithoutReturnType == null) {
            typeForPropertyWithoutReturnType = VariableTypeAndInitializerResolver.getTypeForPropertyWithoutReturnType(propertyDescriptor.getShortName().asString());
        }
        propertyGetterDescriptorImpl.initialize(typeForPropertyWithoutReturnType);
        if (ktPropertyAccessor != null) {
            bindingTrace.record(BindingContext.PROPERTY_ACCESSOR, ktPropertyAccessor, propertyGetterDescriptorImpl);
        }
        if (propertyGetterDescriptorImpl == null) {
            $$$reportNull$$$0(118);
        }
        return propertyGetterDescriptorImpl;
    }

    private KotlinType determineGetterReturnType(LexicalScope lexicalScope, BindingTrace bindingTrace, PropertyGetterDescriptor propertyGetterDescriptor, KtPropertyAccessor ktPropertyAccessor, KotlinType kotlinType, InferenceSession inferenceSession) {
        if (lexicalScope == null) {
            $$$reportNull$$$0(119);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(120);
        }
        if (propertyGetterDescriptor == null) {
            $$$reportNull$$$0(121);
        }
        if (ktPropertyAccessor == null) {
            $$$reportNull$$$0(122);
        }
        KtTypeReference returnTypeReference = ktPropertyAccessor.getReturnTypeReference();
        if (returnTypeReference != null) {
            KotlinType kotlinTypeResolveType = this.typeResolver.resolveType(lexicalScope, returnTypeReference, bindingTrace, true);
            if (kotlinType != null && !TypeUtils.equalTypes(kotlinTypeResolveType, kotlinType)) {
                bindingTrace.report(Errors.WRONG_GETTER_RETURN_TYPE.on(returnTypeReference, kotlinType, kotlinTypeResolveType));
            }
            return kotlinTypeResolveType;
        }
        KtProperty property = ktPropertyAccessor.getProperty();
        return (property.hasDelegateExpressionOrInitializer() || property.mo7396getTypeReference() != null || !ktPropertyAccessor.hasBody() || ktPropertyAccessor.hasBlockBody()) ? kotlinType : inferReturnTypeFromExpressionBody(bindingTrace, lexicalScope, DataFlowInfoFactory.EMPTY, ktPropertyAccessor, propertyGetterDescriptor, inferenceSession);
    }

    KotlinType inferReturnTypeFromExpressionBody(final BindingTrace bindingTrace, final LexicalScope lexicalScope, final DataFlowInfo dataFlowInfo, final KtDeclarationWithBody ktDeclarationWithBody, final FunctionDescriptor functionDescriptor, final InferenceSession inferenceSession) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(123);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(124);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(125);
        }
        if (ktDeclarationWithBody == null) {
            $$$reportNull$$$0(126);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(127);
        }
        KotlinType kotlinTypeCreateRecursionIntolerantDeferredType = this.wrappedTypeFactory.createRecursionIntolerantDeferredType(bindingTrace, new Function0() { // from class: org.jetbrains.kotlin.resolve.DescriptorResolver$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.m7416xeb7fcbee(ktDeclarationWithBody, bindingTrace, lexicalScope, dataFlowInfo, functionDescriptor, inferenceSession);
            }
        });
        if (kotlinTypeCreateRecursionIntolerantDeferredType == null) {
            $$$reportNull$$$0(128);
        }
        return kotlinTypeCreateRecursionIntolerantDeferredType;
    }

    /* JADX INFO: renamed from: lambda$inferReturnTypeFromExpressionBody$5$org-jetbrains-kotlin-resolve-DescriptorResolver, reason: not valid java name */
    /* synthetic */ KotlinType m7416xeb7fcbee(KtDeclarationWithBody ktDeclarationWithBody, BindingTrace bindingTrace, LexicalScope lexicalScope, DataFlowInfo dataFlowInfo, FunctionDescriptor functionDescriptor, InferenceSession inferenceSession) {
        PreliminaryDeclarationVisitor.INSTANCE.createForDeclaration(ktDeclarationWithBody, bindingTrace, this.languageVersionSettings);
        UnwrappedType unwrappedTypeSanitizeReturnType = this.declarationReturnTypeSanitizer.sanitizeReturnType(this.typeApproximator.approximateDeclarationType(transformAnonymousTypeIfNeeded(functionDescriptor, ktDeclarationWithBody, this.expressionTypingServices.getBodyExpressionType(bindingTrace, lexicalScope, dataFlowInfo, ktDeclarationWithBody, functionDescriptor, inferenceSession), bindingTrace, this.anonymousTypeTransformers, this.languageVersionSettings), false), this.wrappedTypeFactory, bindingTrace, this.languageVersionSettings);
        this.functionsTypingVisitor.checkTypesForReturnStatements(ktDeclarationWithBody, bindingTrace, unwrappedTypeSanitizeReturnType);
        return unwrappedTypeSanitizeReturnType;
    }

    public PropertyDescriptor resolvePrimaryConstructorParameterToAProperty(ClassDescriptor classDescriptor, ValueParameterDescriptor valueParameterDescriptor, LexicalScope lexicalScope, KtParameter ktParameter, BindingTrace bindingTrace) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(129);
        }
        if (valueParameterDescriptor == null) {
            $$$reportNull$$$0(130);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(131);
        }
        if (ktParameter == null) {
            $$$reportNull$$$0(132);
        }
        KotlinType kotlinTypeResolveParameterType = resolveParameterType(lexicalScope, ktParameter, bindingTrace);
        Name nameAsSafeName = ktParameter.getNameAsSafeName();
        boolean zIsMutable = ktParameter.isMutable();
        KtModifierList modifierList = ktParameter.getModifierList();
        if (modifierList != null && modifierList.hasModifier(KtTokens.ABSTRACT_KEYWORD)) {
            bindingTrace.report(Errors.ABSTRACT_PROPERTY_IN_PRIMARY_CONSTRUCTOR_PARAMETERS.on(ktParameter));
        }
        Annotations annotationsResolveAnnotationsWithoutArguments = this.annotationResolver.resolveAnnotationsWithoutArguments(lexicalScope, ktParameter.getModifierList(), bindingTrace);
        EnumSet enumSetOf = EnumSet.of(AnnotationUseSiteTarget.PROPERTY, AnnotationUseSiteTarget.PROPERTY_GETTER, AnnotationUseSiteTarget.FIELD, AnnotationUseSiteTarget.CONSTRUCTOR_PARAMETER, AnnotationUseSiteTarget.PROPERTY_SETTER);
        if (zIsMutable) {
            enumSetOf.add(AnnotationUseSiteTarget.PROPERTY_SETTER);
            enumSetOf.add(AnnotationUseSiteTarget.SETTER_PARAMETER);
        }
        AnnotationSplitter annotationSplitter = new AnnotationSplitter(this.storageManager, annotationsResolveAnnotationsWithoutArguments, enumSetOf);
        PropertyDescriptorImpl propertyDescriptorImplCreate = PropertyDescriptorImpl.create(classDescriptor, new CompositeAnnotations(annotationSplitter.getAnnotationsForTarget(AnnotationUseSiteTarget.PROPERTY), annotationSplitter.getOtherAnnotations()), ModifiersChecker.resolveMemberModalityFromModifiers(ktParameter, Modality.FINAL, bindingTrace.getContext(), classDescriptor), ModifiersChecker.resolveVisibilityFromModifiers(ktParameter, getDefaultVisibility(ktParameter, classDescriptor)), zIsMutable, nameAsSafeName, CallableMemberDescriptor.Kind.DECLARATION, KotlinSourceElementKt.toSourceElement(ktParameter), false, false, classDescriptor.get$isExpect(), modifierList != null && PsiUtilsKt.hasActualModifier(modifierList), false, false);
        propertyDescriptorImplCreate.setType(kotlinTypeResolveParameterType, Collections.emptyList(), DescriptorUtils.getDispatchReceiverParameterIfNeeded(classDescriptor), null, CollectionsKt.emptyList());
        Annotations annotationsForTarget = annotationSplitter.getAnnotationsForTarget(AnnotationUseSiteTarget.PROPERTY_SETTER);
        PropertyGetterDescriptorImpl propertyGetterDescriptorImplCreateDefaultGetter = DescriptorFactory.createDefaultGetter(propertyDescriptorImplCreate, new CompositeAnnotations((List<? extends Annotations>) CollectionsKt.listOf(annotationSplitter.getAnnotationsForTarget(AnnotationUseSiteTarget.PROPERTY_GETTER))));
        propertyDescriptorImplCreate.initialize(propertyGetterDescriptorImplCreateDefaultGetter, propertyDescriptorImplCreate.isVar() ? DescriptorFactory.createDefaultSetter(propertyDescriptorImplCreate, annotationsForTarget, annotationSplitter.getAnnotationsForTarget(AnnotationUseSiteTarget.SETTER_PARAMETER)) : null, new FieldDescriptorImpl(annotationSplitter.getAnnotationsForTarget(AnnotationUseSiteTarget.FIELD), propertyDescriptorImplCreate), null);
        propertyGetterDescriptorImplCreateDefaultGetter.initialize(propertyDescriptorImplCreate.getType());
        bindingTrace.record(BindingContext.PRIMARY_CONSTRUCTOR_PARAMETER, ktParameter, propertyDescriptorImplCreate);
        bindingTrace.record(BindingContext.VALUE_PARAMETER_AS_PROPERTY, valueParameterDescriptor, propertyDescriptorImplCreate);
        return propertyDescriptorImplCreate;
    }

    public static boolean checkHasOuterClassInstance(LexicalScope lexicalScope, BindingTrace bindingTrace, PsiElement psiElement, ClassDescriptor classDescriptor) {
        if (lexicalScope == null) {
            $$$reportNull$$$0(133);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(134);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(135);
        }
        if (classDescriptor == null) {
            $$$reportNull$$$0(136);
        }
        ClassDescriptor containingClass = getContainingClass(lexicalScope);
        if (!isInsideOuterClassOrItsSubclass(containingClass, classDescriptor)) {
            return true;
        }
        while (containingClass != null && !DescriptorUtils.isSubclass(containingClass, classDescriptor)) {
            if (DescriptorUtils.isStaticNestedClass(containingClass)) {
                bindingTrace.report(Errors.INACCESSIBLE_OUTER_CLASS_EXPRESSION.on(CallResolverUtilKt.reportOnElement(psiElement), containingClass));
                return false;
            }
            containingClass = (ClassDescriptor) DescriptorUtils.getParentOfType(containingClass, ClassDescriptor.class, true);
        }
        return true;
    }

    private static boolean isInsideOuterClassOrItsSubclass(DeclarationDescriptor declarationDescriptor, ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(137);
        }
        if (declarationDescriptor == null) {
            return false;
        }
        if ((declarationDescriptor instanceof ClassDescriptor) && DescriptorUtils.isSubclass((ClassDescriptor) declarationDescriptor, classDescriptor)) {
            return true;
        }
        return isInsideOuterClassOrItsSubclass(declarationDescriptor.getContainingModule(), classDescriptor);
    }

    public static ClassDescriptor getContainingClass(LexicalScope lexicalScope) {
        if (lexicalScope == null) {
            $$$reportNull$$$0(138);
        }
        return (ClassDescriptor) DescriptorUtils.getParentOfType(lexicalScope.getOwnerDescriptor(), ClassDescriptor.class, false);
    }
}
