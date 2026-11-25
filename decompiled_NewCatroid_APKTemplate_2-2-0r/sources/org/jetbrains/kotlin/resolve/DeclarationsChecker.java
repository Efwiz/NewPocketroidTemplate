package org.jetbrains.kotlin.resolve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.Implementation;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.builtins.UnsignedTypes;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableSet;
import org.jetbrains.kotlin.com.google.common.collect.Multimap;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorPsiUtilsKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ModalityUtilsKt;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.incremental.KotlinLookupLocation;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtBackingField;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtConstructor;
import org.jetbrains.kotlin.psi.KtConstructorCalleeExpression;
import org.jetbrains.kotlin.psi.KtConstructorDelegationCall;
import org.jetbrains.kotlin.psi.KtContextReceiver;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtModifierListOwner;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtPackageDirective;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPrimaryConstructor;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtPropertyDelegate;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtSuperTypeList;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.psi.KtTypeConstraint;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.psi.KtTypeParameterList;
import org.jetbrains.kotlin.psi.KtTypeParameterListOwner;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.results.TypeSpecificityComparator;
import org.jetbrains.kotlin.resolve.checkers.PlatformDiagnosticSuppressor;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.inline.InlineOnlyKt;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.source.KotlinSourceElement;
import org.jetbrains.kotlin.types.AbbreviatedType;
import org.jetbrains.kotlin.types.DynamicTypesKt;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.IntersectionTypeConstructor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.SubstitutionUtils;
import org.jetbrains.kotlin.types.TypeAliasExpander;
import org.jetbrains.kotlin.types.TypeAliasExpansion;
import org.jetbrains.kotlin.types.TypeAliasExpansionReportStrategy;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: DeclarationsChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Ä\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\b\u0005\n\u0002\u0018\u0002\u0018\u0000 \u0088\u00012\u00020\u0001:\u0004\u0088\u0001\u0089\u0001BM\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013¢\u0006\u0002\u0010\u0014J$\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010$2\b\u0010%\u001a\u0004\u0018\u00010&H\u0002J\u0018\u0010'\u001a\u00020 2\u0006\u0010(\u001a\u00020\u001c2\u0006\u0010!\u001a\u00020\"H\u0002J\u0018\u0010)\u001a\u00020 2\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-H\u0002J\u0010\u0010.\u001a\u00020 2\u0006\u0010/\u001a\u000200H\u0002J\u0010\u00101\u001a\u00020 2\u0006\u0010(\u001a\u00020\u001cH\u0002J\u0018\u00102\u001a\u00020 2\u0006\u00103\u001a\u0002042\u0006\u0010/\u001a\u000200H\u0002J\u0018\u00105\u001a\u00020 2\u0006\u00106\u001a\u0002072\u0006\u00103\u001a\u000204H\u0002J\u001c\u00108\u001a\u00020 2\u0006\u00109\u001a\u00020:2\n\u0010*\u001a\u0006\u0012\u0002\b\u00030;H\u0002J\u0010\u0010<\u001a\u00020 2\u0006\u0010=\u001a\u000207H\u0002J\u0018\u0010>\u001a\u00020 2\u0006\u00109\u001a\u00020:2\u0006\u0010*\u001a\u00020?H\u0002J\u0018\u0010@\u001a\u00020 2\u0006\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020DH\u0002J\u0010\u0010E\u001a\u00020 2\u0006\u0010*\u001a\u00020?H\u0002J\u0018\u0010F\u001a\u00020 2\u0006\u0010*\u001a\u00020?2\u0006\u0010G\u001a\u00020HH\u0002J\u001c\u0010I\u001a\u00020 2\u0006\u00109\u001a\u00020:2\n\u0010*\u001a\u0006\u0012\u0002\b\u00030;H\u0002J\u0018\u0010J\u001a\u00020 2\u0006\u0010K\u001a\u00020L2\u0006\u0010M\u001a\u00020NH\u0002J\u0016\u0010O\u001a\u00020 2\u0006\u0010K\u001a\u00020L2\u0006\u0010M\u001a\u00020PJ\u0018\u0010Q\u001a\u00020 2\u0006\u0010*\u001a\u00020R2\u0006\u0010G\u001a\u00020SH\u0002J\u0018\u0010T\u001a\u00020 2\u0006\u00103\u001a\u00020D2\u0006\u0010/\u001a\u000200H\u0002J\u0016\u0010U\u001a\u00020 2\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-J \u0010V\u001a\u00020 2\u0006\u0010(\u001a\u00020\u001c2\u0006\u0010!\u001a\u00020\"2\u0006\u00103\u001a\u00020DH\u0002J\u0010\u0010W\u001a\u00020 2\u0006\u00103\u001a\u000204H\u0002J\u0010\u0010X\u001a\u00020 2\u0006\u0010Y\u001a\u00020ZH\u0002J\u0018\u0010[\u001a\u00020 2\u0006\u0010*\u001a\u00020\\2\u0006\u00103\u001a\u000204H\u0002J \u0010]\u001a\u00020 2\u0006\u0010G\u001a\u00020^2\u0006\u0010*\u001a\u00020_2\u0006\u0010`\u001a\u00020aH\u0002J\u0010\u0010b\u001a\u00020 2\u0006\u00103\u001a\u000204H\u0002J\u0018\u0010c\u001a\u00020 2\u0006\u0010/\u001a\u0002002\u0006\u00103\u001a\u00020DH\u0002J\u0018\u0010d\u001a\u00020 2\u0006\u0010(\u001a\u00020\u001c2\u0006\u0010!\u001a\u00020\"H\u0002J\u0018\u0010e\u001a\u00020 2\u0006\u0010(\u001a\u00020\u001c2\u0006\u0010!\u001a\u00020\"H\u0002J\u0010\u0010f\u001a\u00020 2\u0006\u0010G\u001a\u00020\"H\u0002J\u0018\u0010g\u001a\u00020 2\u0006\u0010h\u001a\u00020i2\u0006\u0010j\u001a\u00020kH\u0002J\u0018\u0010l\u001a\u00020 2\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-H\u0002J\u0018\u0010m\u001a\u00020 2\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-H\u0002J\u0010\u0010n\u001a\u00020 2\u0006\u0010o\u001a\u00020aH\u0002J\u0010\u0010p\u001a\u00020 2\u0006\u0010o\u001a\u00020aH\u0002J\u0010\u0010q\u001a\u00020 2\u0006\u0010/\u001a\u000200H\u0002J\u0010\u0010r\u001a\u00020 2\u0006\u00106\u001a\u000207H\u0002J\u0018\u0010s\u001a\u00020 2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010t\u001a\u00020SH\u0002J\u001e\u0010u\u001a\b\u0012\u0004\u0012\u00020^0v2\u0006\u0010w\u001a\u00020x2\u0006\u0010y\u001a\u00020-H\u0002J\u0010\u0010z\u001a\u00020{2\u0006\u0010(\u001a\u00020\u001cH\u0002J\u000e\u0010|\u001a\u00020 2\u0006\u0010}\u001a\u00020~JD\u0010\u007f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\b\u0010\u0080\u0001\u001a\u00030\u0081\u00012\u0007\u0010\u0082\u0001\u001a\u00020{2\u0006\u0010(\u001a\u00020\u001c2\u0007\u0010\u0083\u0001\u001a\u00020{2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000bH\u0002J(\u0010\u0084\u0001\u001a\u00020 2\u000e\u0010\u0085\u0001\u001a\t\u0012\u0004\u0012\u00020k0\u0086\u00012\r\u0010\u0087\u0001\u001a\b\u0012\u0004\u0012\u00020k0\u001bH\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0004\u001a\u00060\u0017R\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b*\b\u0012\u0004\u0012\u00020\u001c0\u001b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001e¨\u0006\u008a\u0001²\u0006\f\u0010\u008b\u0001\u001a\u00030\u008c\u0001X\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/resolve/DeclarationsChecker;", "", "descriptorResolver", "Lorg/jetbrains/kotlin/resolve/DescriptorResolver;", "modifiersChecker", "Lorg/jetbrains/kotlin/resolve/ModifiersChecker;", "annotationChecker", "Lorg/jetbrains/kotlin/resolve/AnnotationChecker;", "identifierChecker", "Lorg/jetbrains/kotlin/resolve/IdentifierChecker;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "typeSpecificityComparator", "Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator;", "diagnosticSuppressor", "Lorg/jetbrains/kotlin/resolve/checkers/PlatformDiagnosticSuppressor;", "upperBoundChecker", "Lorg/jetbrains/kotlin/resolve/UpperBoundChecker;", "(Lorg/jetbrains/kotlin/resolve/DescriptorResolver;Lorg/jetbrains/kotlin/resolve/ModifiersChecker;Lorg/jetbrains/kotlin/resolve/AnnotationChecker;Lorg/jetbrains/kotlin/resolve/IdentifierChecker;Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator;Lorg/jetbrains/kotlin/resolve/checkers/PlatformDiagnosticSuppressor;Lorg/jetbrains/kotlin/resolve/UpperBoundChecker;)V", "exposedChecker", "Lorg/jetbrains/kotlin/resolve/ExposedVisibilityChecker;", "Lorg/jetbrains/kotlin/resolve/ModifiersChecker$ModifiersCheckingProcedure;", "shadowedExtensionChecker", "Lorg/jetbrains/kotlin/resolve/ShadowedExtensionChecker;", "deprecationWarning", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory0;", "Lorg/jetbrains/kotlin/psi/KtProperty;", "getDeprecationWarning", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory0;)Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory0;", "checkAccessor", "", "propertyDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX, "Lorg/jetbrains/kotlin/psi/KtPropertyAccessor;", "accessorDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyAccessorDescriptor;", "checkAccessors", "property", "checkActualTypeAlias", "declaration", "Lorg/jetbrains/kotlin/psi/KtTypeAlias;", "typeAliasDescriptor", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "checkAnnotationClassMembers", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "checkBackingField", "checkClass", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptorWithResolutionScopes;", "checkClassButNotObject", "aClass", "Lorg/jetbrains/kotlin/psi/KtClass;", "checkConstructorDeclaration", "constructorDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "Lorg/jetbrains/kotlin/psi/KtConstructor;", "checkConstructorInInterface", "klass", "checkConstructorVisibility", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "checkEnumEntry", "enumEntry", "Lorg/jetbrains/kotlin/psi/KtEnumEntry;", "enumEntryClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "checkExpectDeclarationHasNoExternalModifier", "checkExpectDeclarationModifiers", "descriptor", "Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "checkExpectedClassConstructor", "checkExpectedFunction", "function", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "checkFunction", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "checkImplicitCallableType", "Lorg/jetbrains/kotlin/psi/KtCallableDeclaration;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "checkLocalAnnotation", "checkLocalTypeAliasDeclaration", "checkMemberProperty", "checkMethodsOfAnyInInterface", "checkModifiersAndAnnotationsInPackageDirective", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "checkObject", "Lorg/jetbrains/kotlin/psi/KtObjectDeclaration;", "checkOnlyOneTypeParameterBound", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "Lorg/jetbrains/kotlin/psi/KtTypeParameter;", "owner", "Lorg/jetbrains/kotlin/psi/KtTypeParameterListOwner;", "checkOpenMembers", "checkPrimaryConstructor", "checkProperty", "checkPropertyInitializer", "checkPropertyTypeParametersAreUsedInReceiverType", "checkSupertypesForConsistency", "classifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "sourceElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "checkTypeAliasDeclaration", "checkTypeAliasExpansion", "checkTypeParameterConstraints", "typeParameterListOwner", "checkTypeParameters", "checkTypesInClassHeader", "checkValOnAnnotationParameter", "checkVarargParameters", "callableDescriptor", "getUsedTypeAliasParameters", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "typeAlias", "noExplicitTypeOrGetterType", "", "process", "bodiesResolveContext", "Lorg/jetbrains/kotlin/resolve/BodiesResolveContext;", "reportMustBeInitialized", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "hasAnyAccessorImplementation", "isOpenValDeferredInitDeprecationWarning", "reportVisibilityModifierDiagnostics", "tokens", "", "diagnostic", "Companion", "TypeAliasDeclarationCheckingReportStrategy", "frontend", "delegateClassScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeclarationsChecker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final ImmutableSet<String> METHOD_OF_ANY_NAMES = ImmutableSet.of("toString", "hashCode", Namer.EQUALS_METHOD_NAME);
    private final AnnotationChecker annotationChecker;
    private final DescriptorResolver descriptorResolver;
    private final PlatformDiagnosticSuppressor diagnosticSuppressor;
    private final ExposedVisibilityChecker exposedChecker;
    private final IdentifierChecker identifierChecker;
    private final LanguageVersionSettings languageVersionSettings;
    private final ModifiersChecker.ModifiersCheckingProcedure modifiersChecker;
    private final ShadowedExtensionChecker shadowedExtensionChecker;
    private final BindingTrace trace;
    private final UpperBoundChecker upperBoundChecker;

    public DeclarationsChecker(DescriptorResolver descriptorResolver, ModifiersChecker modifiersChecker, AnnotationChecker annotationChecker, IdentifierChecker identifierChecker, BindingTrace trace, LanguageVersionSettings languageVersionSettings, TypeSpecificityComparator typeSpecificityComparator, PlatformDiagnosticSuppressor diagnosticSuppressor, UpperBoundChecker upperBoundChecker) {
        Intrinsics.checkNotNullParameter(descriptorResolver, "descriptorResolver");
        Intrinsics.checkNotNullParameter(modifiersChecker, "modifiersChecker");
        Intrinsics.checkNotNullParameter(annotationChecker, "annotationChecker");
        Intrinsics.checkNotNullParameter(identifierChecker, "identifierChecker");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(typeSpecificityComparator, "typeSpecificityComparator");
        Intrinsics.checkNotNullParameter(diagnosticSuppressor, "diagnosticSuppressor");
        Intrinsics.checkNotNullParameter(upperBoundChecker, "upperBoundChecker");
        this.descriptorResolver = descriptorResolver;
        this.annotationChecker = annotationChecker;
        this.identifierChecker = identifierChecker;
        this.trace = trace;
        this.languageVersionSettings = languageVersionSettings;
        this.diagnosticSuppressor = diagnosticSuppressor;
        this.upperBoundChecker = upperBoundChecker;
        ModifiersChecker.ModifiersCheckingProcedure modifiersCheckingProcedureWithTrace = modifiersChecker.withTrace(trace);
        Intrinsics.checkNotNullExpressionValue(modifiersCheckingProcedureWithTrace, "withTrace(...)");
        this.modifiersChecker = modifiersCheckingProcedureWithTrace;
        this.exposedChecker = new ExposedVisibilityChecker(languageVersionSettings, trace);
        this.shadowedExtensionChecker = new ShadowedExtensionChecker(typeSpecificityComparator, trace);
    }

    public final void process(BodiesResolveContext bodiesResolveContext) {
        Intrinsics.checkNotNullParameter(bodiesResolveContext, "bodiesResolveContext");
        for (KtFile ktFile : bodiesResolveContext.getFiles()) {
            Intrinsics.checkNotNull(ktFile);
            checkModifiersAndAnnotationsInPackageDirective(ktFile);
            this.annotationChecker.check(ktFile, this.trace, null);
        }
        for (Map.Entry<KtClassOrObject, ClassDescriptorWithResolutionScopes> entry : bodiesResolveContext.getDeclaredClasses().entrySet()) {
            KtClassOrObject key = entry.getKey();
            ClassDescriptorWithResolutionScopes value = entry.getValue();
            Intrinsics.checkNotNull(value);
            Intrinsics.checkNotNull(key);
            checkClass(value, key);
            KtClassOrObject ktClassOrObject = key;
            this.modifiersChecker.checkModifiersForDeclaration(ktClassOrObject, value);
            this.identifierChecker.checkDeclaration(ktClassOrObject, this.trace);
            this.exposedChecker.checkClassHeader(key, value);
        }
        for (Map.Entry<KtNamedFunction, SimpleFunctionDescriptor> entry2 : bodiesResolveContext.getFunctions().entrySet()) {
            KtNamedFunction key2 = entry2.getKey();
            SimpleFunctionDescriptor value2 = entry2.getValue();
            Intrinsics.checkNotNull(key2);
            Intrinsics.checkNotNull(value2);
            checkFunction(key2, value2);
            KtNamedFunction ktNamedFunction = key2;
            this.modifiersChecker.checkModifiersForDeclaration(ktNamedFunction, value2);
            this.identifierChecker.checkDeclaration(ktNamedFunction, this.trace);
        }
        for (Map.Entry<KtProperty, PropertyDescriptor> entry3 : bodiesResolveContext.getProperties().entrySet()) {
            KtProperty key3 = entry3.getKey();
            PropertyDescriptor value3 = entry3.getValue();
            Intrinsics.checkNotNull(key3);
            Intrinsics.checkNotNull(value3);
            checkProperty(key3, value3);
            KtProperty ktProperty = key3;
            this.modifiersChecker.checkModifiersForDeclaration(ktProperty, value3);
            this.identifierChecker.checkDeclaration(ktProperty, this.trace);
        }
        Set<Map.Entry<KtDestructuringDeclarationEntry, PropertyDescriptor>> setEntrySet = bodiesResolveContext.getDestructuringDeclarationEntries().entrySet();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(setEntrySet, 10));
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            arrayList.add(((KtDestructuringDeclarationEntry) ((Map.Entry) it.next()).getKey()).getLightParent());
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (obj instanceof KtDestructuringDeclaration) {
                arrayList2.add(obj);
            }
        }
        for (KtDestructuringDeclaration ktDestructuringDeclaration : CollectionsKt.distinct(arrayList2)) {
            this.modifiersChecker.checkModifiersForDestructuringDeclaration(ktDestructuringDeclaration);
            this.identifierChecker.checkDeclaration(ktDestructuringDeclaration, this.trace);
        }
        for (Map.Entry<KtSecondaryConstructor, ClassConstructorDescriptor> entry4 : bodiesResolveContext.getSecondaryConstructors().entrySet()) {
            KtSecondaryConstructor key4 = entry4.getKey();
            ClassConstructorDescriptor value4 = entry4.getValue();
            Intrinsics.checkNotNull(value4);
            Intrinsics.checkNotNull(key4);
            checkConstructorDeclaration(value4, key4);
            ExposedVisibilityChecker.checkFunction$default(this.exposedChecker, key4, value4, null, 4, null);
        }
        for (Map.Entry<KtTypeAlias, TypeAliasDescriptor> entry5 : bodiesResolveContext.getTypeAliases().entrySet()) {
            KtTypeAlias key5 = entry5.getKey();
            TypeAliasDescriptor value5 = entry5.getValue();
            Intrinsics.checkNotNull(key5);
            Intrinsics.checkNotNull(value5);
            checkTypeAliasDeclaration(key5, value5);
            this.modifiersChecker.checkModifiersForDeclaration(key5, value5);
            this.exposedChecker.checkTypeAlias(key5, value5);
        }
    }

    public final void checkLocalTypeAliasDeclaration(KtTypeAlias declaration, TypeAliasDescriptor typeAliasDescriptor) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(typeAliasDescriptor, "typeAliasDescriptor");
        checkTypeAliasDeclaration(declaration, typeAliasDescriptor);
        this.modifiersChecker.checkModifiersForDeclaration(declaration, typeAliasDescriptor);
        this.exposedChecker.checkTypeAlias(declaration, typeAliasDescriptor);
    }

    private final void checkTypeAliasDeclaration(KtTypeAlias declaration, TypeAliasDescriptor typeAliasDescriptor) {
        KtTypeReference typeReference = declaration.getTypeReference();
        if (typeReference == null) {
            return;
        }
        checkTypeAliasExpansion(declaration, typeAliasDescriptor);
        SimpleType expandedType = typeAliasDescriptor.getExpandedType();
        SimpleType simpleType = expandedType;
        if (KotlinTypeKt.isError(simpleType)) {
            return;
        }
        ClassifierDescriptor this$0 = expandedType.getConstructor().getThis$0();
        if (DynamicTypesKt.isDynamic(simpleType) || (this$0 instanceof TypeParameterDescriptor)) {
            this.trace.report(Errors.TYPEALIAS_SHOULD_EXPAND_TO_CLASS.on(typeReference, expandedType));
        }
        if (TypeUtils.contains(simpleType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.DeclarationsChecker.checkTypeAliasDeclaration.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(UnwrappedType unwrappedType) {
                return Boolean.valueOf(TypeUtilsKt.isArrayOfNothing(unwrappedType));
            }
        })) {
            this.trace.report(Errors.TYPEALIAS_EXPANDED_TO_MALFORMED_TYPE.on(typeReference, expandedType, "Array<Nothing> is illegal"));
        }
        Set<TypeParameterDescriptor> usedTypeAliasParameters = getUsedTypeAliasParameters(simpleType, typeAliasDescriptor);
        for (TypeParameterDescriptor typeParameterDescriptor : typeAliasDescriptor.getDeclaredTypeParameters()) {
            if (!usedTypeAliasParameters.contains(typeParameterDescriptor)) {
                Intrinsics.checkNotNull(typeParameterDescriptor);
                PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(typeParameterDescriptor);
                KtTypeParameter ktTypeParameter = psiElementDescriptorToDeclaration instanceof KtTypeParameter ? (KtTypeParameter) psiElementDescriptorToDeclaration : null;
                if (ktTypeParameter == null) {
                    throw new AssertionError("No source element for type parameter " + typeParameterDescriptor + " of " + typeAliasDescriptor);
                }
                this.trace.report(Errors.UNUSED_TYPEALIAS_PARAMETER.on(ktTypeParameter, typeParameterDescriptor, expandedType));
            }
        }
        if (PsiUtilsKt.hasActualModifier(declaration)) {
            checkActualTypeAlias(declaration, typeAliasDescriptor);
        }
    }

    private final void checkActualTypeAlias(KtTypeAlias declaration, TypeAliasDescriptor typeAliasDescriptor) {
        SimpleType underlyingType = typeAliasDescriptor.getUnderlyingType();
        ClassifierDescriptor this$0 = underlyingType.getConstructor().getThis$0();
        if (!(this$0 instanceof ClassDescriptor)) {
            this.trace.report(Errors.ACTUAL_TYPE_ALIAS_NOT_TO_CLASS.on(declaration));
            return;
        }
        List<TypeParameterDescriptor> declaredTypeParameters = ((ClassDescriptor) this$0).getDeclaredTypeParameters();
        Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "getDeclaredTypeParameters(...)");
        List<TypeParameterDescriptor> list = declaredTypeParameters;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                if (((TypeParameterDescriptor) it.next()).getVariance() != Variance.INVARIANT) {
                    this.trace.report(Errors.ACTUAL_TYPE_ALIAS_TO_CLASS_WITH_DECLARATION_SITE_VARIANCE.on(declaration));
                    return;
                }
            }
        }
        List<TypeProjection> arguments = underlyingType.getArguments();
        if (!(arguments instanceof Collection) || !arguments.isEmpty()) {
            for (TypeProjection typeProjection : arguments) {
                if (typeProjection.getProjectionKind() != Variance.INVARIANT || typeProjection.isStarProjection()) {
                    this.trace.report(Errors.ACTUAL_TYPE_ALIAS_WITH_USE_SITE_VARIANCE.on(declaration));
                    return;
                }
            }
        }
        List<TypeProjection> arguments2 = underlyingType.getArguments();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments2, 10));
        Iterator<T> it2 = arguments2.iterator();
        while (it2.hasNext()) {
            ClassifierDescriptor this$1 = ((TypeProjection) it2.next()).getNullableAnyType().getConstructor().getThis$0();
            arrayList.add(this$1 instanceof TypeParameterDescriptor ? (TypeParameterDescriptor) this$1 : null);
        }
        if (!Intrinsics.areEqual(arrayList, typeAliasDescriptor.getDeclaredTypeParameters())) {
            this.trace.report(Errors.ACTUAL_TYPE_ALIAS_WITH_COMPLEX_SUBSTITUTION.on(declaration));
            return;
        }
        if (this.languageVersionSettings.supportsFeature(LanguageFeature.MultiplatformRestrictions)) {
            if (TypeUtilsKt.isNothing(underlyingType)) {
                this.trace.report(Errors.ACTUAL_TYPE_ALIAS_TO_NOTHING.on(declaration));
            } else if (underlyingType.getIsMarkedNullable()) {
                this.trace.report(Errors.ACTUAL_TYPE_ALIAS_TO_NULLABLE_TYPE.on(declaration));
            }
        }
    }

    private final Set<TypeParameterDescriptor> getUsedTypeAliasParameters(KotlinType type, TypeAliasDescriptor typeAlias) {
        Collection<KotlinType> collectionConstituentTypes = TypeUtilsKt.constituentTypes(type);
        HashSet hashSet = new HashSet();
        Iterator<T> it = collectionConstituentTypes.iterator();
        while (it.hasNext()) {
            ClassifierDescriptor this$0 = ((KotlinType) it.next()).getConstructor().getThis$0();
            TypeParameterDescriptor typeParameterDescriptor = null;
            TypeParameterDescriptor typeParameterDescriptor2 = this$0 instanceof TypeParameterDescriptor ? (TypeParameterDescriptor) this$0 : null;
            if (typeParameterDescriptor2 != null && Intrinsics.areEqual(typeParameterDescriptor2.getContainingModule(), typeAlias)) {
                typeParameterDescriptor = typeParameterDescriptor2;
            }
            if (typeParameterDescriptor != null) {
                hashSet.add(typeParameterDescriptor);
            }
        }
        return hashSet;
    }

    /* JADX INFO: compiled from: DeclarationsChecker.kt */
    @Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ(\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\"\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\u00052\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u001a\u001a\u00020\u0014H\u0016J\u0010\u0010\u001b\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\u0005H\u0016J\u0010\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0018\u0010\u001f\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\u00052\u0006\u0010 \u001a\u00020!H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/resolve/DeclarationsChecker$TypeAliasDeclarationCheckingReportStrategy;", "Lorg/jetbrains/kotlin/types/TypeAliasExpansionReportStrategy;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "typeAliasDescriptor", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "declaration", "Lorg/jetbrains/kotlin/psi/KtTypeAlias;", "upperBoundChecker", "Lorg/jetbrains/kotlin/resolve/UpperBoundChecker;", "(Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;Lorg/jetbrains/kotlin/psi/KtTypeAlias;Lorg/jetbrains/kotlin/resolve/UpperBoundChecker;)V", "typeReference", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "getUpperBoundChecker", "()Lorg/jetbrains/kotlin/resolve/UpperBoundChecker;", "boundsViolationInSubstitution", "", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "unsubstitutedArgument", "Lorg/jetbrains/kotlin/types/KotlinType;", "argument", "typeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "conflictingProjection", "typeAlias", "substitutedArgument", "recursiveTypeAlias", "repeatedAnnotation", "annotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "wrongNumberOfTypeArguments", "numberOfParameters", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class TypeAliasDeclarationCheckingReportStrategy implements TypeAliasExpansionReportStrategy {
        private final BindingTrace trace;
        private final KtTypeReference typeReference;
        private final UpperBoundChecker upperBoundChecker;

        @Override // org.jetbrains.kotlin.types.TypeAliasExpansionReportStrategy
        public void wrongNumberOfTypeArguments(TypeAliasDescriptor typeAlias, int numberOfParameters) {
            Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        }

        public TypeAliasDeclarationCheckingReportStrategy(BindingTrace trace, TypeAliasDescriptor typeAliasDescriptor, KtTypeAlias declaration, UpperBoundChecker upperBoundChecker) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(typeAliasDescriptor, "typeAliasDescriptor");
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(upperBoundChecker, "upperBoundChecker");
            this.trace = trace;
            this.upperBoundChecker = upperBoundChecker;
            KtTypeReference typeReference = declaration.getTypeReference();
            if (typeReference == null) {
                throw new AssertionError("Incorrect type alias declaration for " + typeAliasDescriptor);
            }
            this.typeReference = typeReference;
        }

        public final UpperBoundChecker getUpperBoundChecker() {
            return this.upperBoundChecker;
        }

        @Override // org.jetbrains.kotlin.types.TypeAliasExpansionReportStrategy
        public void conflictingProjection(TypeAliasDescriptor typeAlias, TypeParameterDescriptor typeParameter, KotlinType substitutedArgument) {
            Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
            Intrinsics.checkNotNullParameter(substitutedArgument, "substitutedArgument");
            this.trace.report(Errors.CONFLICTING_PROJECTION_IN_TYPEALIAS_EXPANSION.on(this.typeReference, substitutedArgument));
        }

        @Override // org.jetbrains.kotlin.types.TypeAliasExpansionReportStrategy
        public void recursiveTypeAlias(TypeAliasDescriptor typeAlias) {
            Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
            this.trace.report(Errors.RECURSIVE_TYPEALIAS_EXPANSION.on(this.typeReference, typeAlias));
        }

        @Override // org.jetbrains.kotlin.types.TypeAliasExpansionReportStrategy
        public void boundsViolationInSubstitution(TypeSubstitutor substitutor, KotlinType unsubstitutedArgument, KotlinType argument, TypeParameterDescriptor typeParameter) {
            Intrinsics.checkNotNullParameter(substitutor, "substitutor");
            Intrinsics.checkNotNullParameter(unsubstitutedArgument, "unsubstitutedArgument");
            Intrinsics.checkNotNullParameter(argument, "argument");
            Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
            UpperBoundChecker.checkBounds$default(this.upperBoundChecker, null, argument, typeParameter, substitutor, this.trace, this.typeReference, null, 64, null);
        }

        @Override // org.jetbrains.kotlin.types.TypeAliasExpansionReportStrategy
        public void repeatedAnnotation(AnnotationDescriptor annotation) {
            Intrinsics.checkNotNullParameter(annotation, "annotation");
            SourceElement source = annotation.getSource();
            KotlinSourceElement kotlinSourceElement = source instanceof KotlinSourceElement ? (KotlinSourceElement) source : null;
            KtElement psi = kotlinSourceElement != null ? kotlinSourceElement.getPsi() : null;
            KtAnnotationEntry ktAnnotationEntry = psi instanceof KtAnnotationEntry ? (KtAnnotationEntry) psi : null;
            if (ktAnnotationEntry == null) {
                return;
            }
            this.trace.report(Errors.REPEATED_ANNOTATION.on(ktAnnotationEntry));
        }
    }

    private final void checkTypeAliasExpansion(KtTypeAlias declaration, TypeAliasDescriptor typeAliasDescriptor) {
        new TypeAliasExpander(new TypeAliasDeclarationCheckingReportStrategy(this.trace, typeAliasDescriptor, declaration, this.upperBoundChecker), true).expandWithoutAbbreviation(TypeAliasExpansion.INSTANCE.createWithFormalArguments(typeAliasDescriptor), TypeAttributes.INSTANCE.getEmpty());
    }

    private final void checkConstructorDeclaration(ClassConstructorDescriptor constructorDescriptor, KtConstructor<?> declaration) {
        KtConstructor<?> ktConstructor = declaration;
        this.modifiersChecker.checkModifiersForDeclaration(ktConstructor, constructorDescriptor);
        this.identifierChecker.checkDeclaration(ktConstructor, this.trace);
        checkVarargParameters(this.trace, constructorDescriptor);
        checkConstructorVisibility(constructorDescriptor, ktConstructor);
        checkExpectedClassConstructor(constructorDescriptor, declaration);
    }

    private final void checkExpectedClassConstructor(ClassConstructorDescriptor constructorDescriptor, KtConstructor<?> declaration) {
        if (constructorDescriptor.get$isExpect()) {
            if (declaration.hasBody()) {
                this.trace.report(Errors.EXPECTED_DECLARATION_WITH_BODY.on(declaration));
            }
            if (constructorDescriptor.getContainingModule().getKind() == ClassKind.ENUM_CLASS) {
                this.trace.report(Errors.EXPECTED_ENUM_CONSTRUCTOR.on(declaration));
            }
            if ((declaration instanceof KtPrimaryConstructor) && !DescriptorUtils.isAnnotationClass(constructorDescriptor.getConstructedClass())) {
                ClassDescriptor constructedClass = constructorDescriptor.getConstructedClass();
                Intrinsics.checkNotNullExpressionValue(constructedClass, "getConstructedClass(...)");
                if (!InlineClassesUtilsKt.isValueClass(constructedClass)) {
                    for (KtParameter ktParameter : ((KtPrimaryConstructor) declaration).getValueParameters()) {
                        if (ktParameter.hasValOrVar()) {
                            this.trace.report(Errors.EXPECTED_CLASS_CONSTRUCTOR_PROPERTY_PARAMETER.on(ktParameter));
                        }
                    }
                }
            }
            if (declaration instanceof KtSecondaryConstructor) {
                KtConstructorDelegationCall delegationCall = ((KtSecondaryConstructor) declaration).getDelegationCall();
                if (delegationCall.isImplicit()) {
                    return;
                }
                this.trace.report(Errors.EXPECTED_CLASS_CONSTRUCTOR_DELEGATION_CALL.on(delegationCall));
            }
        }
    }

    private final void checkConstructorVisibility(ClassConstructorDescriptor constructorDescriptor, KtDeclaration declaration) {
        ASTNode node;
        IElementType elementType;
        PsiElement psiElementVisibilityModifier = KtPsiUtilKt.visibilityModifier(declaration);
        if (psiElementVisibilityModifier == null || (node = psiElementVisibilityModifier.getNode()) == null || (elementType = node.getElementType()) == null) {
            return;
        }
        ClassDescriptor containingModule = constructorDescriptor.getContainingModule();
        if (containingModule.getKind() == ClassKind.ENUM_CLASS) {
            if (Intrinsics.areEqual(elementType, KtTokens.PRIVATE_KEYWORD)) {
                return;
            }
            this.trace.report(Errors.NON_PRIVATE_CONSTRUCTOR_IN_ENUM.on(psiElementVisibilityModifier));
        } else if (containingModule.getModality() == Modality.SEALED) {
            boolean zSupportsFeature = this.languageVersionSettings.supportsFeature(LanguageFeature.AllowSealedInheritorsInDifferentFilesOfSamePackage);
            if (Intrinsics.areEqual(elementType, KtTokens.PRIVATE_KEYWORD)) {
                return;
            }
            if (zSupportsFeature && Intrinsics.areEqual(elementType, KtTokens.PROTECTED_KEYWORD)) {
                return;
            }
            this.trace.report((zSupportsFeature ? Errors.NON_PRIVATE_OR_PROTECTED_CONSTRUCTOR_IN_SEALED : Errors.NON_PRIVATE_CONSTRUCTOR_IN_SEALED).on(psiElementVisibilityModifier));
        }
    }

    private final void checkModifiersAndAnnotationsInPackageDirective(KtFile file) {
        KtModifierList modifierList;
        KtSimpleNameExpression constructorReferenceExpression;
        KtPackageDirective packageDirective = file.getPackageDirective();
        if (packageDirective == null || (modifierList = packageDirective.getModifierList()) == null) {
            return;
        }
        Iterator<KtAnnotationEntry> it = modifierList.getAnnotationEntries().iterator();
        while (it.hasNext()) {
            KtConstructorCalleeExpression calleeExpression = it.next().getCalleeExpression();
            if (calleeExpression != null && (constructorReferenceExpression = calleeExpression.getConstructorReferenceExpression()) != null) {
                this.trace.report(Errors.UNRESOLVED_REFERENCE.on(constructorReferenceExpression, constructorReferenceExpression));
            }
        }
        this.annotationChecker.check(packageDirective, this.trace, null);
        ModifierCheckerCore.INSTANCE.check(packageDirective, this.trace, null, this.languageVersionSettings);
    }

    private final void checkClass(ClassDescriptorWithResolutionScopes classDescriptor, KtClassOrObject classOrObject) {
        checkSupertypesForConsistency(classDescriptor, classOrObject);
        ClassDescriptorWithResolutionScopes classDescriptorWithResolutionScopes = classDescriptor;
        checkLocalAnnotation(classDescriptorWithResolutionScopes, classOrObject);
        checkTypesInClassHeader(classOrObject);
        if (classOrObject instanceof KtClass) {
            checkClassButNotObject((KtClass) classOrObject, classDescriptor);
            this.descriptorResolver.checkNamesInConstraints(classOrObject, classDescriptor, classDescriptor.getScopeForClassHeaderResolution(), this.trace);
        } else if (classOrObject instanceof KtObjectDeclaration) {
            checkObject((KtObjectDeclaration) classOrObject, classDescriptor);
        }
        checkPrimaryConstructor(classOrObject, classDescriptorWithResolutionScopes);
        checkExpectDeclarationModifiers(classOrObject, classDescriptor);
    }

    private final void checkLocalAnnotation(ClassDescriptor classDescriptor, KtClassOrObject classOrObject) {
        if (classDescriptor.getKind() == ClassKind.ANNOTATION_CLASS && DescriptorUtils.isLocal(classDescriptor)) {
            this.trace.report(Errors.LOCAL_ANNOTATION_CLASS.on(this.languageVersionSettings, classOrObject));
        }
    }

    private static final KotlinType checkTypesInClassHeader$type(KtTypeReference ktTypeReference, DeclarationsChecker declarationsChecker) {
        return (KotlinType) declarationsChecker.trace.getContext().get(BindingContext.TYPE, ktTypeReference);
    }

    private final void checkTypesInClassHeader(KtClassOrObject classOrObject) {
        KotlinType kotlinTypeCheckTypesInClassHeader$type;
        KtSimpleNameExpression subjectTypeParameterName;
        Name referencedNameAsName;
        KotlinType kotlinTypeCheckTypesInClassHeader$type2;
        KotlinType kotlinTypeCheckTypesInClassHeader$type3;
        Iterator<KtSuperTypeListEntry> it = classOrObject.getSuperTypeListEntries().iterator();
        while (it.hasNext()) {
            KtTypeReference typeReference = it.next().getTypeReference();
            if (typeReference != null && (kotlinTypeCheckTypesInClassHeader$type3 = checkTypesInClassHeader$type(typeReference, this)) != null) {
                this.upperBoundChecker.checkBoundsInSupertype(typeReference, kotlinTypeCheckTypesInClassHeader$type3, this.trace, this.languageVersionSettings);
            }
        }
        if (classOrObject instanceof KtClass) {
            ArrayList<DescriptorResolver.UpperBoundCheckRequest> arrayList = new ArrayList();
            KtClass ktClass = (KtClass) classOrObject;
            for (KtTypeParameter ktTypeParameter : ktClass.getTypeParameters()) {
                KtTypeReference extendsBound = ktTypeParameter.getExtendsBound();
                if (extendsBound != null && (kotlinTypeCheckTypesInClassHeader$type2 = checkTypesInClassHeader$type(extendsBound, this)) != null) {
                    arrayList.add(new DescriptorResolver.UpperBoundCheckRequest(ktTypeParameter.getNameAsName(), extendsBound, kotlinTypeCheckTypesInClassHeader$type2));
                }
            }
            for (KtTypeConstraint ktTypeConstraint : ktClass.getTypeConstraints()) {
                KtTypeReference boundTypeReference = ktTypeConstraint.getBoundTypeReference();
                if (boundTypeReference != null && (kotlinTypeCheckTypesInClassHeader$type = checkTypesInClassHeader$type(boundTypeReference, this)) != null && (subjectTypeParameterName = ktTypeConstraint.getSubjectTypeParameterName()) != null && (referencedNameAsName = subjectTypeParameterName.getReferencedNameAsName()) != null) {
                    arrayList.add(new DescriptorResolver.UpperBoundCheckRequest(referencedNameAsName, boundTypeReference, kotlinTypeCheckTypesInClassHeader$type));
                }
            }
            DescriptorResolver.checkUpperBoundTypes(this.trace, arrayList, false);
            for (DescriptorResolver.UpperBoundCheckRequest upperBoundCheckRequest : arrayList) {
                UpperBoundChecker upperBoundChecker = this.upperBoundChecker;
                KtTypeReference upperBound = upperBoundCheckRequest.upperBound;
                Intrinsics.checkNotNullExpressionValue(upperBound, "upperBound");
                KotlinType upperBoundType = upperBoundCheckRequest.upperBoundType;
                Intrinsics.checkNotNullExpressionValue(upperBoundType, "upperBoundType");
                upperBoundChecker.checkBoundsInSupertype(upperBound, upperBoundType, this.trace, this.languageVersionSettings);
            }
        }
    }

    private final void checkOnlyOneTypeParameterBound(TypeParameterDescriptor descriptor, KtTypeParameter declaration, KtTypeParameterListOwner owner) {
        KtTypeParameter ktTypeParameter;
        Object next;
        KtTypeReference ktTypeReference;
        KotlinType kotlinType;
        List<KotlinType> upperBounds = descriptor.getUpperBounds();
        Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
        List<KotlinType> list = upperBounds;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((KotlinType) it.next()).getConstructor());
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        for (Object obj : arrayList) {
            if (((TypeConstructor) obj).getThis$0() instanceof TypeParameterDescriptor) {
                arrayList2.add(obj);
            } else {
                arrayList3.add(obj);
            }
        }
        Pair pair = new Pair(arrayList2, arrayList3);
        Pair pair2 = TuplesKt.to(CollectionsKt.toSet((Iterable) pair.getFirst()), CollectionsKt.toSet((Iterable) pair.getSecond()));
        Set set = (Set) pair2.component1();
        Set set2 = (Set) pair2.component2();
        if (set.size() > 1 || (set.size() == 1 && !set2.isEmpty())) {
            if (set.size() + set2.size() == 2) {
                List<KtTypeConstraint> typeConstraints = owner.getTypeConstraints();
                Intrinsics.checkNotNullExpressionValue(typeConstraints, "getTypeConstraints(...)");
                ArrayList arrayList4 = new ArrayList();
                for (Object obj2 : typeConstraints) {
                    KtSimpleNameExpression subjectTypeParameterName = ((KtTypeConstraint) obj2).getSubjectTypeParameterName();
                    if (Intrinsics.areEqual(subjectTypeParameterName != null ? subjectTypeParameterName.getReferencedNameAsName() : null, declaration.getNameAsName())) {
                        arrayList4.add(obj2);
                    }
                }
                ArrayList arrayList5 = new ArrayList();
                Iterator it2 = arrayList4.iterator();
                while (it2.hasNext()) {
                    KtTypeReference boundTypeReference = ((KtTypeConstraint) it2.next()).getBoundTypeReference();
                    if (boundTypeReference != null) {
                        arrayList5.add(boundTypeReference);
                    }
                }
                ArrayList<KtTypeReference> arrayList6 = arrayList5;
                ArrayList arrayList7 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList6, 10));
                for (KtTypeReference ktTypeReference2 : arrayList6) {
                    arrayList7.add(TuplesKt.to(ktTypeReference2, this.trace.getContext().get(BindingContext.TYPE, ktTypeReference2)));
                }
                Iterator it3 = arrayList7.iterator();
                do {
                    if (!it3.hasNext()) {
                        next = null;
                        break;
                    } else {
                        next = it3.next();
                        kotlinType = (KotlinType) ((Pair) next).getSecond();
                    }
                } while (Intrinsics.areEqual(kotlinType != null ? kotlinType.getConstructor() : null, CollectionsKt.first(set)));
                Pair pair3 = (Pair) next;
                ktTypeParameter = (pair3 == null || (ktTypeReference = (KtTypeReference) pair3.getFirst()) == null) ? declaration : ktTypeReference;
            } else {
                ktTypeParameter = declaration;
            }
            DeclarationDescriptor containingDeclaration = descriptor.getContainingModule();
            MemberDescriptor memberDescriptor = containingDeclaration instanceof MemberDescriptor ? (MemberDescriptor) containingDeclaration : null;
            if (memberDescriptor == null || !InlineOnlyKt.isInlineOnly(memberDescriptor)) {
                this.trace.report(Errors.BOUNDS_NOT_ALLOWED_IF_BOUNDED_BY_TYPE_PARAMETER.on(ktTypeParameter));
            }
        }
    }

    private final void checkSupertypesForConsistency(ClassifierDescriptor classifier, PsiElement sourceElement) {
        if (classifier instanceof TypeParameterDescriptor) {
            List<KotlinType> upperBounds = ((TypeParameterDescriptor) classifier).getUpperBounds();
            Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
            List<KotlinType> list = upperBounds;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((KotlinType) it.next()).getConstructor());
            }
            ArrayList arrayList2 = arrayList;
            if (arrayList2.size() != CollectionsKt.toSet(arrayList2).size()) {
                return;
            }
        }
        Multimap<TypeParameterDescriptor, TypeProjection> multimapBuildDeepSubstitutionMultimap = SubstitutionUtils.buildDeepSubstitutionMultimap(classifier.getDefaultType());
        Intrinsics.checkNotNullExpressionValue(multimapBuildDeepSubstitutionMultimap, "buildDeepSubstitutionMultimap(...)");
        Map<TypeParameterDescriptor, Collection<TypeProjection>> mapAsMap = multimapBuildDeepSubstitutionMultimap.asMap();
        Intrinsics.checkNotNullExpressionValue(mapAsMap, "asMap(...)");
        for (Map.Entry<TypeParameterDescriptor, Collection<TypeProjection>> entry : mapAsMap.entrySet()) {
            TypeParameterDescriptor key = entry.getKey();
            Collection<TypeProjection> value = entry.getValue();
            if (value.size() > 1) {
                Intrinsics.checkNotNull(value);
                Collection<TypeProjection> collection = value;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
                Iterator<T> it2 = collection.iterator();
                while (it2.hasNext()) {
                    arrayList3.add(((TypeProjection) it2.next()).getNullableAnyType());
                }
                Set mutableSet = CollectionsKt.toMutableSet(arrayList3);
                INSTANCE.removeDuplicateTypes(mutableSet);
                if (mutableSet.size() > 1) {
                    DeclarationDescriptor containingDeclaration = key.getContainingModule();
                    ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
                    if (classDescriptor == null) {
                        throw new AssertionError("Not a class descriptor: " + key.getContainingModule());
                    }
                    if (sourceElement instanceof KtClassOrObject) {
                        KtSuperTypeList superTypeList = ((KtClassOrObject) sourceElement).getSuperTypeList();
                        if (superTypeList != null) {
                            this.trace.report(Errors.INCONSISTENT_TYPE_PARAMETER_VALUES.on(superTypeList, key, classDescriptor, mutableSet));
                        }
                    } else if (sourceElement instanceof KtTypeParameter) {
                        this.trace.report(Errors.INCONSISTENT_TYPE_PARAMETER_BOUNDS.on(sourceElement, key, classDescriptor, mutableSet));
                    }
                } else {
                    continue;
                }
            }
        }
    }

    private final void checkObject(KtObjectDeclaration declaration, ClassDescriptorWithResolutionScopes classDescriptor) {
        checkOpenMembers(classDescriptor);
        if (!declaration.isLocal() || declaration.isCompanion() || declaration.isObjectLiteral()) {
            return;
        }
        this.trace.report(Errors.LOCAL_OBJECT_NOT_ALLOWED.on(declaration, classDescriptor));
    }

    private final void checkClassButNotObject(KtClass aClass, ClassDescriptorWithResolutionScopes classDescriptor) {
        checkOpenMembers(classDescriptor);
        KtClass ktClass = aClass;
        checkTypeParameters(ktClass);
        checkTypeParameterConstraints(ktClass);
        ClassDescriptorWithResolutionScopes classDescriptorWithResolutionScopes = classDescriptor;
        FiniteBoundRestrictionChecker.check(aClass, classDescriptorWithResolutionScopes, this.trace);
        NonExpansiveInheritanceRestrictionChecker.check(aClass, classDescriptorWithResolutionScopes, this.trace);
        if (aClass.isInterface()) {
            checkConstructorInInterface(aClass);
            checkMethodsOfAnyInInterface(classDescriptor);
            if (!aClass.isLocal() || (classDescriptor.getContainingModule() instanceof ClassDescriptor)) {
                return;
            }
            this.trace.report(Errors.LOCAL_INTERFACE_NOT_ALLOWED.on(aClass, classDescriptor));
            return;
        }
        if (classDescriptor.getKind() == ClassKind.ANNOTATION_CLASS) {
            checkAnnotationClassMembers(aClass);
            checkValOnAnnotationParameter(aClass);
        } else if (aClass instanceof KtEnumEntry) {
            checkEnumEntry((KtEnumEntry) aClass, classDescriptorWithResolutionScopes);
        }
    }

    private final void checkPrimaryConstructor(KtClassOrObject classOrObject, ClassDescriptor classDescriptor) {
        KtPrimaryConstructor primaryConstructor;
        PsiElement constructorKeyword;
        KtModifierList modifierList;
        ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = classDescriptor.mo6347getUnsubstitutedPrimaryConstructor();
        if (classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor == null || (primaryConstructor = classOrObject.getPrimaryConstructor()) == null) {
            return;
        }
        for (KtParameter ktParameter : primaryConstructor.getValueParameters()) {
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) this.trace.get(BindingContext.PRIMARY_CONSTRUCTOR_PARAMETER, ktParameter);
            if (propertyDescriptor != null) {
                this.modifiersChecker.checkModifiersForDeclaration(ktParameter, propertyDescriptor);
                LateinitModifierApplicabilityChecker lateinitModifierApplicabilityChecker = LateinitModifierApplicabilityChecker.INSTANCE;
                BindingTrace bindingTrace = this.trace;
                Intrinsics.checkNotNull(ktParameter);
                lateinitModifierApplicabilityChecker.checkLateinitModifierApplicability(bindingTrace, ktParameter, propertyDescriptor, this.languageVersionSettings);
            }
        }
        if (!primaryConstructor.hasConstructorKeyword() && (modifierList = primaryConstructor.getModifierList()) != null) {
            this.trace.report(Errors.MISSING_CONSTRUCTOR_KEYWORD.on(modifierList));
        }
        if (primaryConstructor.getValueParameterList() == null && (constructorKeyword = primaryConstructor.getConstructorKeyword()) != null) {
            this.trace.report(Errors.MISSING_CONSTRUCTOR_BRACKETS.on(constructorKeyword));
        }
        if (!(classOrObject instanceof KtClass)) {
            this.trace.report(Errors.CONSTRUCTOR_IN_OBJECT.on(primaryConstructor));
        }
        checkConstructorDeclaration(classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor, primaryConstructor);
    }

    private final void checkTypeParameters(KtTypeParameterListOwner typeParameterListOwner) {
        for (KtTypeParameter ktTypeParameter : typeParameterListOwner.getTypeParameters()) {
            if (!this.languageVersionSettings.supportsFeature(LanguageFeature.ClassTypeParameterAnnotations)) {
                AnnotationResolverImpl.reportUnsupportedAnnotationForTypeParameter(ktTypeParameter, this.trace, this.languageVersionSettings);
            }
            TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) this.trace.get(BindingContext.TYPE_PARAMETER, ktTypeParameter);
            if (typeParameterDescriptor != null) {
                DescriptorResolver.checkConflictingUpperBounds(this.trace, typeParameterDescriptor, ktTypeParameter);
            }
        }
    }

    private final void checkTypeParameterConstraints(KtTypeParameterListOwner typeParameterListOwner) {
        List<KtTypeConstraint> typeConstraints = typeParameterListOwner.getTypeConstraints();
        Intrinsics.checkNotNullExpressionValue(typeConstraints, "getTypeConstraints(...)");
        if (typeConstraints.isEmpty()) {
            return;
        }
        for (KtTypeParameter ktTypeParameter : typeParameterListOwner.getTypeParameters()) {
            if (ktTypeParameter.getExtendsBound() != null) {
                Companion companion = INSTANCE;
                Intrinsics.checkNotNull(ktTypeParameter);
                if (companion.hasConstraints(ktTypeParameter, typeConstraints)) {
                    this.trace.report(Errors.MISPLACED_TYPE_PARAMETER_CONSTRAINTS.on(ktTypeParameter));
                }
            }
            TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) this.trace.get(BindingContext.TYPE_PARAMETER, ktTypeParameter);
            if (typeParameterDescriptor != null) {
                Intrinsics.checkNotNull(ktTypeParameter);
                checkSupertypesForConsistency(typeParameterDescriptor, ktTypeParameter);
                checkOnlyOneTypeParameterBound(typeParameterDescriptor, ktTypeParameter, typeParameterListOwner);
            }
        }
        Iterator<KtTypeConstraint> it = typeConstraints.iterator();
        while (it.hasNext()) {
            List<KtAnnotationEntry> annotationEntries = it.next().getAnnotationEntries();
            Intrinsics.checkNotNullExpressionValue(annotationEntries, "getAnnotationEntries(...)");
            Iterator<T> it2 = annotationEntries.iterator();
            while (it2.hasNext()) {
                this.trace.report(Errors.ANNOTATION_IN_WHERE_CLAUSE_WARNING.on((KtAnnotationEntry) it2.next()));
            }
        }
    }

    private final void checkConstructorInInterface(KtClass klass) {
        KtPrimaryConstructor primaryConstructor = klass.getPrimaryConstructor();
        if (primaryConstructor != null) {
            this.trace.report(Errors.CONSTRUCTOR_IN_INTERFACE.on(primaryConstructor));
        }
    }

    private final void checkMethodsOfAnyInInterface(ClassDescriptorWithResolutionScopes classDescriptor) {
        for (CallableMemberDescriptor callableMemberDescriptor : classDescriptor.getDeclaredCallableMembers()) {
            if (callableMemberDescriptor instanceof FunctionDescriptor) {
                PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(callableMemberDescriptor);
                if (psiElementDescriptorToDeclaration instanceof KtNamedFunction) {
                    Companion companion = INSTANCE;
                    if (!companion.isHidingParentMemberIfPresent(callableMemberDescriptor) && companion.isImplementingMethodOfAny(callableMemberDescriptor)) {
                        this.trace.report(Errors.METHOD_OF_ANY_IMPLEMENTED_IN_INTERFACE.on(psiElementDescriptorToDeclaration));
                    }
                }
            }
        }
    }

    private final void checkAnnotationClassMembers(KtClassOrObject classOrObject) {
        for (KtDeclaration ktDeclaration : classOrObject.getDeclarations()) {
            if (!(ktDeclaration instanceof KtClassOrObject) || !this.languageVersionSettings.supportsFeature(LanguageFeature.NestedClassesInAnnotations)) {
                this.trace.report(Errors.ANNOTATION_CLASS_MEMBER.on(ktDeclaration));
            }
        }
    }

    private final void checkValOnAnnotationParameter(KtClass aClass) {
        for (KtParameter ktParameter : aClass.getPrimaryConstructorParameters()) {
            if (!ktParameter.hasValOrVar()) {
                this.trace.report(Errors.MISSING_VAL_ON_ANNOTATION_PARAMETER.on(ktParameter));
            } else if (ktParameter.isMutable()) {
                this.trace.report(Errors.VAR_ANNOTATION_PARAMETER.on(ktParameter));
            }
        }
    }

    private final void checkOpenMembers(ClassDescriptorWithResolutionScopes classDescriptor) {
        if (DescriptorUtils.classCanHaveOpenMembers(classDescriptor)) {
            return;
        }
        for (CallableMemberDescriptor callableMemberDescriptor : classDescriptor.getDeclaredCallableMembers()) {
            if (callableMemberDescriptor.getKind() == CallableMemberDescriptor.Kind.DECLARATION) {
                Intrinsics.checkNotNull(callableMemberDescriptor);
                PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(callableMemberDescriptor);
                KtNamedDeclaration ktNamedDeclaration = psiElementDescriptorToDeclaration instanceof KtNamedDeclaration ? (KtNamedDeclaration) psiElementDescriptorToDeclaration : null;
                if (ktNamedDeclaration != null && ktNamedDeclaration.hasModifier(KtTokens.OPEN_KEYWORD)) {
                    if (classDescriptor.getKind() == ClassKind.OBJECT) {
                        this.trace.report(Errors.NON_FINAL_MEMBER_IN_OBJECT.on(ktNamedDeclaration));
                    } else {
                        this.trace.report(Errors.NON_FINAL_MEMBER_IN_FINAL_CLASS.on(ktNamedDeclaration));
                    }
                }
            }
        }
    }

    private final void checkBackingField(KtProperty property) {
        KtBackingField fieldDeclaration = property.getFieldDeclaration();
        if (fieldDeclaration != null) {
            this.trace.report(Errors.EXPLICIT_BACKING_FIELDS_UNSUPPORTED.on(fieldDeclaration));
        }
    }

    private final void checkProperty(KtProperty property, PropertyDescriptor propertyDescriptor) {
        DeclarationDescriptor containingDeclaration = propertyDescriptor.getContainingModule();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        if (containingDeclaration instanceof ClassDescriptor) {
            checkMemberProperty(property, propertyDescriptor, (ClassDescriptor) containingDeclaration);
        }
        KtProperty ktProperty = property;
        LateinitModifierApplicabilityChecker.INSTANCE.checkLateinitModifierApplicability(this.trace, ktProperty, propertyDescriptor, this.languageVersionSettings);
        checkPropertyInitializer(property, propertyDescriptor);
        checkAccessors(property, propertyDescriptor);
        checkTypeParameterConstraints(property);
        ExposedVisibilityChecker.checkProperty$default(this.exposedChecker, property, propertyDescriptor, null, 4, null);
        KtProperty ktProperty2 = property;
        this.shadowedExtensionChecker.checkDeclaration(ktProperty2, propertyDescriptor);
        checkPropertyTypeParametersAreUsedInReceiverType(propertyDescriptor);
        checkImplicitCallableType(ktProperty, propertyDescriptor);
        checkExpectDeclarationModifiers(ktProperty2, propertyDescriptor);
        checkBackingField(property);
    }

    private final void checkExpectDeclarationModifiers(KtDeclaration declaration, MemberDescriptor descriptor) {
        KtModifierList modifierList;
        PsiElement modifier;
        KtDeclaration modifier2;
        if (descriptor.get$isExpect()) {
            if (DescriptorVisibilities.isPrivate(descriptor.getVisibilityImpl())) {
                BindingTrace bindingTrace = this.trace;
                DiagnosticFactory0<PsiElement> diagnosticFactory0 = Errors.EXPECTED_PRIVATE_DECLARATION;
                KtModifierList modifierList2 = declaration.getModifierList();
                if (modifierList2 == null || (modifier2 = modifierList2.getModifier(KtTokens.PRIVATE_KEYWORD)) == null) {
                    modifier2 = declaration;
                }
                bindingTrace.report(diagnosticFactory0.on(modifier2));
            }
            checkExpectDeclarationHasNoExternalModifier(declaration);
            if (!(declaration instanceof KtFunction) || !this.languageVersionSettings.supportsFeature(LanguageFeature.MultiplatformRestrictions) || (modifierList = ((KtFunction) declaration).getModifierList()) == null || (modifier = modifierList.getModifier(KtTokens.TAILREC_KEYWORD)) == null) {
                return;
            }
            this.trace.report(Errors.EXPECTED_TAILREC_FUNCTION.on(modifier));
        }
    }

    private final void checkExpectDeclarationHasNoExternalModifier(KtDeclaration declaration) {
        KtModifierList modifierList;
        PsiElement modifier;
        if (!this.languageVersionSettings.supportsFeature(LanguageFeature.MultiplatformRestrictions) || (modifierList = declaration.getModifierList()) == null || (modifier = modifierList.getModifier(KtTokens.EXTERNAL_KEYWORD)) == null) {
            return;
        }
        this.trace.report(Errors.EXPECTED_EXTERNAL_DECLARATION.on(modifier));
    }

    private final void checkPropertyTypeParametersAreUsedInReceiverType(PropertyDescriptor descriptor) {
        List<TypeParameterDescriptor> typeParameters = descriptor.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        Set set = CollectionsKt.toSet(typeParameters);
        HashSet hashSet = new HashSet();
        ReceiverParameterDescriptor extensionReceiverParameter = descriptor.getExtensionReceiverParameter();
        checkPropertyTypeParametersAreUsedInReceiverType$addAccessibleTypeParametersFromType(set, hashSet, extensionReceiverParameter != null ? extensionReceiverParameter.getType() : null);
        Iterator<ReceiverParameterDescriptor> it = descriptor.getContextReceiverParameters().iterator();
        while (it.hasNext()) {
            checkPropertyTypeParametersAreUsedInReceiverType$addAccessibleTypeParametersFromType(set, hashSet, it.next().getType());
        }
        for (TypeParameterDescriptor typeParameterDescriptor : SetsKt.minus(set, (Iterable) hashSet)) {
            Intrinsics.checkNotNull(typeParameterDescriptor);
            PsiElement sourceFromDescriptor = DescriptorToSourceUtils.getSourceFromDescriptor(typeParameterDescriptor);
            if (sourceFromDescriptor instanceof KtTypeParameter) {
                this.trace.report(Errors.TYPE_PARAMETER_OF_PROPERTY_NOT_USED_IN_RECEIVER.on(sourceFromDescriptor));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkPropertyTypeParametersAreUsedInReceiverType$addAccessibleTypeParametersFromType(final Set<? extends TypeParameterDescriptor> set, final HashSet<TypeParameterDescriptor> hashSet, KotlinType kotlinType) {
        TypeUtils.contains(kotlinType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.DeclarationsChecker$checkPropertyTypeParametersAreUsedInReceiverType$addAccessibleTypeParametersFromType$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(UnwrappedType unwrappedType) {
                ClassifierDescriptor this$0 = unwrappedType.getConstructor().getThis$0();
                if ((this$0 instanceof TypeParameterDescriptor) && set.contains(this$0) && hashSet.add((TypeParameterDescriptor) this$0)) {
                    List<KotlinType> upperBounds = ((TypeParameterDescriptor) this$0).getUpperBounds();
                    Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
                    Set<TypeParameterDescriptor> set2 = set;
                    HashSet<TypeParameterDescriptor> hashSet2 = hashSet;
                    Iterator<T> it = upperBounds.iterator();
                    while (it.hasNext()) {
                        DeclarationsChecker.checkPropertyTypeParametersAreUsedInReceiverType$addAccessibleTypeParametersFromType(set2, hashSet2, (KotlinType) it.next());
                    }
                }
                return false;
            }
        });
    }

    private final void checkMemberProperty(KtProperty property, PropertyDescriptor propertyDescriptor, ClassDescriptor classDescriptor) {
        KtModifierList modifierList = property.getModifierList();
        if (modifierList != null) {
            if (modifierList.hasModifier(KtTokens.ABSTRACT_KEYWORD)) {
                if (!DescriptorUtils.classCanHaveAbstractDeclaration(classDescriptor)) {
                    BindingTrace bindingTrace = this.trace;
                    DiagnosticFactory2<KtModifierListOwner, String, ClassDescriptor> diagnosticFactory2 = Errors.ABSTRACT_PROPERTY_IN_NON_ABSTRACT_CLASS;
                    KtProperty ktProperty = property;
                    String name2 = property.getName();
                    if (name2 == null) {
                        name2 = "";
                    }
                    bindingTrace.report(diagnosticFactory2.on(ktProperty, name2, classDescriptor));
                    return;
                }
            } else if (classDescriptor.getKind() == ClassKind.INTERFACE && modifierList.hasModifier(KtTokens.OPEN_KEYWORD) && propertyDescriptor.getModality() == Modality.ABSTRACT) {
                this.trace.report(Errors.REDUNDANT_OPEN_IN_INTERFACE.on(property));
            }
        }
        if (propertyDescriptor.getModality() == Modality.ABSTRACT) {
            KtExpression initializer = property.getInitializer();
            if (initializer != null) {
                this.trace.report(Errors.ABSTRACT_PROPERTY_WITH_INITIALIZER.on(initializer));
            }
            KtPropertyDelegate delegate = property.getDelegate();
            if (delegate != null) {
                this.trace.report(Errors.ABSTRACT_DELEGATED_PROPERTY.on(delegate));
            }
            KtPropertyAccessor getter = property.getGetter();
            if (getter != null && getter.hasBody()) {
                this.trace.report(Errors.ABSTRACT_PROPERTY_WITH_GETTER.on(getter));
            }
            KtPropertyAccessor setter = property.getSetter();
            if (setter == null || !setter.hasBody()) {
                return;
            }
            this.trace.report(Errors.ABSTRACT_PROPERTY_WITH_SETTER.on(setter));
        }
    }

    private final void checkPropertyInitializer(KtProperty property, PropertyDescriptor propertyDescriptor) {
        KtProperty modifier;
        KotlinType type;
        ClassDescriptor this$0;
        SimpleType expandedType;
        TypeConstructor constructor;
        ReceiverValue extensionReceiver;
        ReceiverValue dispatchReceiver;
        FunctionDescriptor functionDescriptor;
        boolean zHasAnyAccessorImplementation = INSTANCE.hasAnyAccessorImplementation(propertyDescriptor);
        DeclarationDescriptor containingDeclaration = propertyDescriptor.getContainingModule();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        boolean zIsInterface = DescriptorUtils.isInterface(containingDeclaration);
        if (propertyDescriptor.getModality() == Modality.ABSTRACT) {
            if (!property.hasDelegateExpressionOrInitializer() && property.mo7396getTypeReference() == null) {
                this.trace.report(Errors.PROPERTY_WITH_NO_TYPE_NO_INITIALIZER.on(property));
            }
            if (zIsInterface && property.hasModifier(KtTokens.PRIVATE_KEYWORD) && !property.hasModifier(KtTokens.ABSTRACT_KEYWORD)) {
                this.trace.report(Errors.PRIVATE_PROPERTY_IN_INTERFACE.on(property));
                return;
            }
            return;
        }
        Boolean bool = (Boolean) this.trace.getContext().get(BindingContext.BACKING_FIELD_REQUIRED, propertyDescriptor);
        boolean z = false;
        if (bool == null) {
            bool = false;
        }
        boolean zBooleanValue = bool.booleanValue();
        if (zIsInterface && zBooleanValue && zHasAnyAccessorImplementation) {
            this.trace.report(Errors.BACKING_FIELD_IN_INTERFACE.on(property));
        }
        KtExpression initializer = property.getInitializer();
        KtPropertyDelegate delegate = property.getDelegate();
        boolean zIsExpect = propertyDescriptor.get$isExpect();
        if (initializer != null) {
            if (zIsInterface) {
                this.trace.report(Errors.PROPERTY_INITIALIZER_IN_INTERFACE.on(initializer));
                return;
            }
            if (zIsExpect) {
                this.trace.report(Errors.EXPECTED_PROPERTY_INITIALIZER.on(initializer));
                return;
            }
            if (!zBooleanValue) {
                this.trace.report(Errors.PROPERTY_INITIALIZER_NO_BACKING_FIELD.on(initializer));
                return;
            }
            if (property.mo7393getReceiverTypeReference() != null) {
                this.trace.report(Errors.EXTENSION_PROPERTY_WITH_BACKING_FIELD.on(initializer));
                return;
            }
            List<KtContextReceiver> contextReceivers = property.getContextReceivers();
            Intrinsics.checkNotNullExpressionValue(contextReceivers, "getContextReceivers(...)");
            if (contextReceivers.isEmpty()) {
                return;
            }
            this.trace.report(Errors.CONTEXT_RECEIVERS_WITH_BACKING_FIELD.on(initializer));
            return;
        }
        if (delegate == null) {
            Boolean bool2 = (Boolean) this.trace.getContext().get(BindingContext.IS_UNINITIALIZED, propertyDescriptor);
            if (bool2 == null) {
                bool2 = false;
            }
            boolean zBooleanValue2 = bool2.booleanValue();
            boolean zIsEffectivelyExternal = DescriptorUtilsKt.isEffectivelyExternal(propertyDescriptor);
            if (zBooleanValue && !zIsInterface && !propertyDescriptor.isLateInit() && !zIsExpect && zBooleanValue2 && !zIsEffectivelyExternal) {
                if (propertyDescriptor.getExtensionReceiverParameter() != null && !zHasAnyAccessorImplementation) {
                    this.trace.report(Errors.EXTENSION_PROPERTY_MUST_HAVE_ACCESSORS_OR_BE_ABSTRACT.on(property));
                } else if (this.diagnosticSuppressor.shouldReportNoBody(propertyDescriptor)) {
                    boolean z2 = !this.languageVersionSettings.supportsFeature(LanguageFeature.ProhibitOpenValDeferredInitialization) && DeclarationsCheckerKt.getEffectiveModality(propertyDescriptor, this.languageVersionSettings) == Modality.OPEN && !propertyDescriptor.isVar() && Intrinsics.areEqual(this.trace.getContext().get(BindingContext.IS_DEFINITELY_NOT_ASSIGNED_IN_CONSTRUCTOR, propertyDescriptor), (Object) false);
                    if (!z2 || DeclarationsCheckerKt.getEffectiveModality(propertyDescriptor) != Modality.FINAL) {
                        reportMustBeInitialized(propertyDescriptor, containingDeclaration, zHasAnyAccessorImplementation, property, z2, this.languageVersionSettings, this.trace);
                    }
                }
            } else if (property.mo7396getTypeReference() == null && !this.languageVersionSettings.supportsFeature(LanguageFeature.ShortSyntaxForPropertyGetters)) {
                this.trace.report(Errors.UNSUPPORTED_FEATURE.on(property, TuplesKt.to(LanguageFeature.ShortSyntaxForPropertyGetters, this.languageVersionSettings)));
            } else if (noExplicitTypeOrGetterType(property)) {
                this.trace.report(Errors.PROPERTY_WITH_NO_TYPE_NO_INITIALIZER.on(property));
            }
            if (propertyDescriptor.isLateInit()) {
                if (propertyDescriptor.get$isExpect()) {
                    BindingTrace bindingTrace = this.trace;
                    DiagnosticFactory0<PsiElement> diagnosticFactory0 = Errors.EXPECTED_LATEINIT_PROPERTY;
                    KtModifierList modifierList = property.getModifierList();
                    if (modifierList == null || (modifier = modifierList.getModifier(KtTokens.LATEINIT_KEYWORD)) == null) {
                        modifier = property;
                    }
                    bindingTrace.report(diagnosticFactory0.on(modifier));
                }
                if (!zBooleanValue || zIsInterface || zBooleanValue2 || Intrinsics.areEqual(this.trace.get(BindingContext.MUST_BE_LATEINIT, propertyDescriptor), (Object) true)) {
                    return;
                }
                this.trace.report(Errors.UNNECESSARY_LATEINIT.on(property));
                return;
            }
            return;
        }
        if (zIsInterface) {
            this.trace.report(Errors.DELEGATED_PROPERTY_IN_INTERFACE.on(delegate));
            return;
        }
        if (zIsExpect) {
            this.trace.report(Errors.EXPECTED_DELEGATED_PROPERTY.on(delegate));
            return;
        }
        if (property.mo7393getReceiverTypeReference() != null) {
            ResolvedCall resolvedCall = (ResolvedCall) this.trace.get(BindingContext.DELEGATED_PROPERTY_RESOLVED_CALL, propertyDescriptor.getGetter());
            ResolvedCall resolvedCall2 = (ResolvedCall) this.trace.get(BindingContext.PROVIDE_DELEGATE_RESOLVED_CALL, propertyDescriptor);
            Object obj = null;
            if (resolvedCall2 == null || (functionDescriptor = (FunctionDescriptor) resolvedCall2.getResultingDescriptor()) == null || (type = functionDescriptor.getReturnType()) == null) {
                KtExpression expression = delegate.getExpression();
                type = expression != null ? this.trace.getType(expression) : null;
                if (type == null) {
                    return;
                }
            }
            ClassifierDescriptor this$1 = FlexibleTypesKt.lowerIfFlexible(type).unwrap().getConstructor().getThis$0();
            ClassDescriptor classDescriptor = this$1 instanceof ClassDescriptor ? (ClassDescriptor) this$1 : null;
            if (classDescriptor != null) {
                this$0 = classDescriptor;
            } else {
                TypeAliasDescriptor typeAliasDescriptor = this$1 instanceof TypeAliasDescriptor ? (TypeAliasDescriptor) this$1 : null;
                this$0 = (typeAliasDescriptor == null || (expandedType = typeAliasDescriptor.getExpandedType()) == null || (constructor = expandedType.getConstructor()) == null) ? null : constructor.getThis$0();
            }
            final ClassDescriptor classDescriptor2 = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
            if (classDescriptor2 == null) {
                return;
            }
            Lazy lazy = LazyKt.lazy(new Function0<MemberScope>() { // from class: org.jetbrains.kotlin.resolve.DeclarationsChecker$checkPropertyInitializer$delegateClassScope$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final MemberScope invoke() {
                    return classDescriptor2.getUnsubstitutedMemberScope();
                }
            });
            KotlinType type2 = (resolvedCall == null || (dispatchReceiver = resolvedCall.getDispatchReceiver()) == null) ? null : dispatchReceiver.getType();
            KotlinType type3 = (resolvedCall == null || (extensionReceiver = resolvedCall.getExtensionReceiver()) == null) ? null : extensionReceiver.getType();
            List<TypeParameterDescriptor> typeParameters = propertyDescriptor.getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
            for (Object obj2 : typeParameters) {
                final TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) obj2;
                if ((type2 != null && TypeUtilsKt.contains(type2, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.DeclarationsChecker$checkPropertyInitializer$usedParameter$1$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(UnwrappedType it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return Boolean.valueOf(Intrinsics.areEqual(it.getConstructor(), typeParameterDescriptor.getTypeConstructor()));
                    }
                })) || (type3 != null && TypeUtilsKt.contains(type3, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.DeclarationsChecker$checkPropertyInitializer$usedParameter$1$2
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(UnwrappedType it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return Boolean.valueOf(Intrinsics.areEqual(it.getConstructor(), typeParameterDescriptor.getTypeConstructor()));
                    }
                }))) {
                    obj = obj2;
                    break;
                }
            }
            TypeParameterDescriptor typeParameterDescriptor2 = (TypeParameterDescriptor) obj;
            if (typeParameterDescriptor2 != null) {
                Iterator<Name> it = checkPropertyInitializer$lambda$29(lazy).getVariableNames().iterator();
                while (it.hasNext()) {
                    Iterator<T> it2 = checkPropertyInitializer$lambda$29(lazy).getContributedVariables(it.next(), new KotlinLookupLocation(delegate)).iterator();
                    while (it2.hasNext()) {
                        KotlinType type4 = ((PropertyDescriptor) it2.next()).getType();
                        Intrinsics.checkNotNullExpressionValue(type4, "getType(...)");
                        if (TypeUtilsKt.contains(type4, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.DeclarationsChecker$checkPropertyInitializer$1$1
                            @Override // kotlin.jvm.functions.Function1
                            public final Boolean invoke(UnwrappedType it3) {
                                Intrinsics.checkNotNullParameter(it3, "it");
                                return Boolean.valueOf(it3.getConstructor().getThis$0() instanceof TypeParameterDescriptor);
                            }
                        })) {
                            z = true;
                        }
                    }
                }
                if (z) {
                    this.trace.report(Errors.DELEGATE_USES_EXTENSION_PROPERTY_TYPE_PARAMETER.on(this.languageVersionSettings, delegate, typeParameterDescriptor2.getShortName().asString()));
                }
            }
        }
    }

    private static final MemberScope checkPropertyInitializer$lambda$29(Lazy<? extends MemberScope> lazy) {
        return lazy.getValue();
    }

    /* JADX WARN: Code duplicated, block: B:38:0x009f  */
    /* JADX WARN: Code duplicated, block: B:39:0x00a1  */
    private final void reportMustBeInitialized(PropertyDescriptor propertyDescriptor, DeclarationDescriptor containingDeclaration, boolean hasAnyAccessorImplementation, KtProperty property, boolean isOpenValDeferredInitDeprecationWarning, LanguageVersionSettings languageVersionSettings, BindingTrace trace) {
        boolean z;
        DiagnosticFactory0<KtProperty> deprecationWarning;
        if (DeclarationsCheckerKt.getEffectiveModality(propertyDescriptor, languageVersionSettings) == Modality.ABSTRACT) {
            throw new IllegalStateException("reportMustBeInitialized isn't called for abstract properties".toString());
        }
        boolean z2 = containingDeclaration instanceof ClassDescriptor;
        boolean z3 = z2 && !INSTANCE.hasSetterAccessorImplementation(propertyDescriptor) && DeclarationsCheckerKt.getEffectiveModality(propertyDescriptor, languageVersionSettings) != Modality.FINAL && Intrinsics.areEqual(trace.getContext().get(BindingContext.IS_DEFINITELY_NOT_ASSIGNED_IN_CONSTRUCTOR, propertyDescriptor), (Object) false);
        boolean z4 = z2 && !hasAnyAccessorImplementation;
        if (isOpenValDeferredInitDeprecationWarning && !z3 && z4) {
            throw new IllegalStateException("Not reachable case. Every \"open val + deferred init\" case that could be made `abstract`, also could be made `final`".toString());
        }
        if (languageVersionSettings.supportsFeature(LanguageFeature.ProhibitMissedMustBeInitializedWhenThereIsNoPrimaryConstructor) || !z2) {
            z = false;
        } else {
            Collection<ClassConstructorDescriptor> constructors = ((ClassDescriptor) containingDeclaration).getConstructors();
            Intrinsics.checkNotNullExpressionValue(constructors, "getConstructors(...)");
            Collection<ClassConstructorDescriptor> collection = constructors;
            if (collection.isEmpty()) {
                if (Intrinsics.areEqual(trace.getContext().get(BindingContext.IS_DEFINITELY_NOT_ASSIGNED_IN_CONSTRUCTOR, propertyDescriptor), (Object) false)) {
                    z = true;
                } else {
                    z = false;
                }
            } else {
                Iterator<T> it = collection.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (((ClassConstructorDescriptor) it.next()).isPrimary()) {
                        }
                    } else if (Intrinsics.areEqual(trace.getContext().get(BindingContext.IS_DEFINITELY_NOT_ASSIGNED_IN_CONSTRUCTOR, propertyDescriptor), (Object) false)) {
                        z = true;
                    }
                    z = false;
                }
            }
        }
        if (z3 && z4) {
            deprecationWarning = Errors.MUST_BE_INITIALIZED_OR_FINAL_OR_ABSTRACT;
        } else if (z3) {
            deprecationWarning = Errors.MUST_BE_INITIALIZED_OR_BE_FINAL;
        } else if (z4) {
            deprecationWarning = Errors.MUST_BE_INITIALIZED_OR_BE_ABSTRACT;
        } else {
            deprecationWarning = Errors.MUST_BE_INITIALIZED;
        }
        boolean z5 = z || isOpenValDeferredInitDeprecationWarning;
        if (z5) {
            Intrinsics.checkNotNull(deprecationWarning);
            deprecationWarning = getDeprecationWarning(deprecationWarning);
        } else if (z5) {
            throw new NoWhenBranchMatchedException();
        }
        trace.report(deprecationWarning.on(property));
    }

    private final DiagnosticFactory0<KtProperty> getDeprecationWarning(DiagnosticFactory0<KtProperty> diagnosticFactory0) {
        if (Intrinsics.areEqual(diagnosticFactory0, Errors.MUST_BE_INITIALIZED)) {
            DiagnosticFactory0<KtProperty> MUST_BE_INITIALIZED_WARNING = Errors.MUST_BE_INITIALIZED_WARNING;
            Intrinsics.checkNotNullExpressionValue(MUST_BE_INITIALIZED_WARNING, "MUST_BE_INITIALIZED_WARNING");
            return MUST_BE_INITIALIZED_WARNING;
        }
        if (Intrinsics.areEqual(diagnosticFactory0, Errors.MUST_BE_INITIALIZED_OR_BE_ABSTRACT)) {
            DiagnosticFactory0<KtProperty> MUST_BE_INITIALIZED_OR_BE_ABSTRACT_WARNING = Errors.MUST_BE_INITIALIZED_OR_BE_ABSTRACT_WARNING;
            Intrinsics.checkNotNullExpressionValue(MUST_BE_INITIALIZED_OR_BE_ABSTRACT_WARNING, "MUST_BE_INITIALIZED_OR_BE_ABSTRACT_WARNING");
            return MUST_BE_INITIALIZED_OR_BE_ABSTRACT_WARNING;
        }
        if (Intrinsics.areEqual(diagnosticFactory0, Errors.MUST_BE_INITIALIZED_OR_BE_FINAL)) {
            DiagnosticFactory0<KtProperty> MUST_BE_INITIALIZED_OR_BE_FINAL_WARNING = Errors.MUST_BE_INITIALIZED_OR_BE_FINAL_WARNING;
            Intrinsics.checkNotNullExpressionValue(MUST_BE_INITIALIZED_OR_BE_FINAL_WARNING, "MUST_BE_INITIALIZED_OR_BE_FINAL_WARNING");
            return MUST_BE_INITIALIZED_OR_BE_FINAL_WARNING;
        }
        if (!Intrinsics.areEqual(diagnosticFactory0, Errors.MUST_BE_INITIALIZED_OR_FINAL_OR_ABSTRACT)) {
            throw new IllegalStateException("Only MUST_BE_INITIALIZED is supported".toString());
        }
        DiagnosticFactory0<KtProperty> MUST_BE_INITIALIZED_OR_FINAL_OR_ABSTRACT_WARNING = Errors.MUST_BE_INITIALIZED_OR_FINAL_OR_ABSTRACT_WARNING;
        Intrinsics.checkNotNullExpressionValue(MUST_BE_INITIALIZED_OR_FINAL_OR_ABSTRACT_WARNING, "MUST_BE_INITIALIZED_OR_FINAL_OR_ABSTRACT_WARNING");
        return MUST_BE_INITIALIZED_OR_FINAL_OR_ABSTRACT_WARNING;
    }

    private final boolean noExplicitTypeOrGetterType(KtProperty property) {
        if (property.mo7396getTypeReference() == null) {
            if (property.getGetter() != null) {
                KtPropertyAccessor getter = property.getGetter();
                Intrinsics.checkNotNull(getter);
                if (getter.hasBlockBody()) {
                    KtPropertyAccessor getter2 = property.getGetter();
                    Intrinsics.checkNotNull(getter2);
                    if (getter2.getReturnTypeReference() == null) {
                    }
                }
            }
            return true;
        }
        return false;
    }

    public final void checkFunction(KtNamedFunction function, SimpleFunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        KtTypeParameterList typeParameterList = function.mo7395getTypeParameterList();
        PsiElement nameIdentifier = function.mo7392getNameIdentifier();
        if (typeParameterList != null && nameIdentifier != null && typeParameterList.getTextRange().getStartOffset() > nameIdentifier.getTextRange().getStartOffset()) {
            this.trace.report(Errors.DEPRECATED_TYPE_PARAMETER_SYNTAX.on(typeParameterList));
        }
        checkTypeParameterConstraints(function);
        SimpleFunctionDescriptor simpleFunctionDescriptor = functionDescriptor;
        checkImplicitCallableType(function, simpleFunctionDescriptor);
        SimpleFunctionDescriptor simpleFunctionDescriptor2 = functionDescriptor;
        ExposedVisibilityChecker.checkFunction$default(this.exposedChecker, function, simpleFunctionDescriptor2, null, 4, null);
        checkVarargParameters(this.trace, simpleFunctionDescriptor);
        DeclarationDescriptor containingDeclaration = functionDescriptor.getContainingModule();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        boolean zHasModifier = function.hasModifier(KtTokens.ABSTRACT_KEYWORD);
        boolean zIsEffectivelyExternal = DescriptorUtilsKt.isEffectivelyExternal(functionDescriptor);
        if (containingDeclaration instanceof ClassDescriptor) {
            ClassDescriptor classDescriptor = (ClassDescriptor) containingDeclaration;
            boolean z = classDescriptor.getKind() == ClassKind.INTERFACE;
            boolean zIsExpect = classDescriptor.get$isExpect();
            if (zHasModifier && !DescriptorUtils.classCanHaveAbstractDeclaration(classDescriptor)) {
                this.trace.report(Errors.ABSTRACT_FUNCTION_IN_NON_ABSTRACT_CLASS.on(function, functionDescriptor.getShortName().asString(), (ClassDescriptor) containingDeclaration));
            }
            boolean zHasBody = function.hasBody();
            if (zHasBody && zHasModifier) {
                this.trace.report(Errors.ABSTRACT_FUNCTION_WITH_BODY.on(function, functionDescriptor));
            }
            if (!zHasBody && z) {
                if (function.hasModifier(KtTokens.PRIVATE_KEYWORD)) {
                    this.trace.report(Errors.PRIVATE_FUNCTION_WITH_NO_BODY.on(function, functionDescriptor));
                }
                if (!classDescriptor.get$isExpect() && !zHasModifier && function.hasModifier(KtTokens.OPEN_KEYWORD)) {
                    this.trace.report(Errors.REDUNDANT_OPEN_IN_INTERFACE.on(function));
                }
            }
            if (!zHasBody && !zHasModifier && !zIsEffectivelyExternal && !z && !zIsExpect && this.diagnosticSuppressor.shouldReportNoBody(functionDescriptor)) {
                this.trace.report(Errors.NON_ABSTRACT_FUNCTION_WITH_NO_BODY.on(function, functionDescriptor));
            }
        } else if (!function.hasBody() && !zHasModifier && !zIsEffectivelyExternal && !functionDescriptor.get$isExpect() && this.diagnosticSuppressor.shouldReportNoBody(functionDescriptor)) {
            this.trace.report(Errors.NON_MEMBER_FUNCTION_NO_BODY.on(function, functionDescriptor));
        }
        if (functionDescriptor.get$isExpect()) {
            checkExpectedFunction(function, simpleFunctionDescriptor2);
        }
        this.shadowedExtensionChecker.checkDeclaration(function, functionDescriptor);
    }

    private final void checkExpectedFunction(KtNamedFunction function, FunctionDescriptor functionDescriptor) {
        if (function.hasBody()) {
            this.trace.report(Errors.EXPECTED_DECLARATION_WITH_BODY.on(function));
        }
        checkExpectDeclarationModifiers(function, functionDescriptor);
    }

    private final void checkImplicitCallableType(KtCallableDeclaration declaration, CallableDescriptor descriptor) {
        UnwrappedType unwrappedTypeUnwrap;
        KotlinType returnType = descriptor.getReturnType();
        if (returnType == null || (unwrappedTypeUnwrap = returnType.unwrap()) == null) {
            return;
        }
        KtCallableDeclaration nameIdentifier = declaration.mo7392getNameIdentifier();
        if (nameIdentifier == null) {
            nameIdentifier = declaration;
        }
        Intrinsics.checkNotNull(nameIdentifier);
        if (declaration.mo7396getTypeReference() == null) {
            UnwrappedType unwrappedType = unwrappedTypeUnwrap;
            if (TypeUtilsKt.isNothing(unwrappedType) && !declaration.hasModifier(KtTokens.OVERRIDE_KEYWORD)) {
                this.trace.report((declaration instanceof KtProperty ? Errors.IMPLICIT_NOTHING_PROPERTY_TYPE : Errors.IMPLICIT_NOTHING_RETURN_TYPE).on(nameIdentifier));
            }
            if (TypeUtilsKt.contains(unwrappedType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.DeclarationsChecker$checkImplicitCallableType$1$1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(UnwrappedType type) {
                    Intrinsics.checkNotNullParameter(type, "type");
                    return Boolean.valueOf(type.getConstructor() instanceof IntersectionTypeConstructor);
                }
            })) {
                this.trace.report(Errors.IMPLICIT_INTERSECTION_TYPE.on(nameIdentifier, unwrappedTypeUnwrap));
                return;
            }
            return;
        }
        if (TypeUtilsKt.isNothing(unwrappedTypeUnwrap) && (unwrappedTypeUnwrap instanceof AbbreviatedType)) {
            this.trace.report((declaration instanceof KtProperty ? Errors.ABBREVIATED_NOTHING_PROPERTY_TYPE : Errors.ABBREVIATED_NOTHING_RETURN_TYPE).on(nameIdentifier));
        }
    }

    private final void checkAccessors(KtProperty property, PropertyDescriptor propertyDescriptor) {
        for (PropertyAccessorDescriptor propertyAccessorDescriptor : propertyDescriptor.getAccessors()) {
            KtPropertyAccessor getter = propertyAccessorDescriptor instanceof PropertyGetterDescriptor ? property.getGetter() : property.getSetter();
            if (getter != null) {
                KtPropertyAccessor ktPropertyAccessor = getter;
                this.modifiersChecker.checkModifiersForDeclaration(ktPropertyAccessor, propertyAccessorDescriptor);
                this.identifierChecker.checkDeclaration(ktPropertyAccessor, this.trace);
            } else {
                this.modifiersChecker.runDeclarationCheckers(property, propertyAccessorDescriptor);
            }
        }
        checkAccessor(propertyDescriptor, property.getGetter(), propertyDescriptor.getGetter());
        checkAccessor(propertyDescriptor, property.getSetter(), propertyDescriptor.getSetter());
    }

    private final void reportVisibilityModifierDiagnostics(Collection<? extends PsiElement> tokens, DiagnosticFactory0<PsiElement> diagnostic) {
        Iterator<? extends PsiElement> it = tokens.iterator();
        while (it.hasNext()) {
            this.trace.report(diagnostic.on(it.next()));
        }
    }

    private final void checkAccessor(PropertyDescriptor propertyDescriptor, KtPropertyAccessor accessor, PropertyAccessorDescriptor accessorDescriptor) {
        if (accessor == null || accessorDescriptor == null) {
            return;
        }
        if (propertyDescriptor.get$isExpect() && accessor.hasBody()) {
            this.trace.report(Errors.EXPECTED_DECLARATION_WITH_BODY.on(accessor));
        }
        KtModifierList modifierList = accessor.getModifierList();
        if (modifierList == null) {
            return;
        }
        Map<KtModifierKeywordToken, PsiElement> tokensCorrespondingToModifiers = this.modifiersChecker.getTokensCorrespondingToModifiers(modifierList, SetsKt.setOf((Object[]) new KtModifierKeywordToken[]{KtTokens.PUBLIC_KEYWORD, KtTokens.PROTECTED_KEYWORD, KtTokens.PRIVATE_KEYWORD, KtTokens.INTERNAL_KEYWORD}));
        Intrinsics.checkNotNullExpressionValue(tokensCorrespondingToModifiers, "getTokensCorrespondingToModifiers(...)");
        if (accessor.isGetter()) {
            if (!Intrinsics.areEqual(accessorDescriptor.getVisibilityImpl(), propertyDescriptor.getVisibilityImpl())) {
                Collection<PsiElement> collectionValues = tokensCorrespondingToModifiers.values();
                DiagnosticFactory0<PsiElement> GETTER_VISIBILITY_DIFFERS_FROM_PROPERTY_VISIBILITY = Errors.GETTER_VISIBILITY_DIFFERS_FROM_PROPERTY_VISIBILITY;
                Intrinsics.checkNotNullExpressionValue(GETTER_VISIBILITY_DIFFERS_FROM_PROPERTY_VISIBILITY, "GETTER_VISIBILITY_DIFFERS_FROM_PROPERTY_VISIBILITY");
                reportVisibilityModifierDiagnostics(collectionValues, GETTER_VISIBILITY_DIFFERS_FROM_PROPERTY_VISIBILITY);
            } else {
                Collection<PsiElement> collectionValues2 = tokensCorrespondingToModifiers.values();
                DiagnosticFactory0<PsiElement> REDUNDANT_MODIFIER_IN_GETTER = Errors.REDUNDANT_MODIFIER_IN_GETTER;
                Intrinsics.checkNotNullExpressionValue(REDUNDANT_MODIFIER_IN_GETTER, "REDUNDANT_MODIFIER_IN_GETTER");
                reportVisibilityModifierDiagnostics(collectionValues2, REDUNDANT_MODIFIER_IN_GETTER);
            }
        } else if (ModalityUtilsKt.isOverridable(propertyDescriptor) && Intrinsics.areEqual(accessorDescriptor.getVisibilityImpl(), DescriptorVisibilities.PRIVATE) && !Intrinsics.areEqual(propertyDescriptor.getVisibilityImpl(), DescriptorVisibilities.PRIVATE)) {
            if (propertyDescriptor.getModality() == Modality.ABSTRACT) {
                Collection<PsiElement> collectionValues3 = tokensCorrespondingToModifiers.values();
                DiagnosticFactory0<PsiElement> PRIVATE_SETTER_FOR_ABSTRACT_PROPERTY = Errors.PRIVATE_SETTER_FOR_ABSTRACT_PROPERTY;
                Intrinsics.checkNotNullExpressionValue(PRIVATE_SETTER_FOR_ABSTRACT_PROPERTY, "PRIVATE_SETTER_FOR_ABSTRACT_PROPERTY");
                reportVisibilityModifierDiagnostics(collectionValues3, PRIVATE_SETTER_FOR_ABSTRACT_PROPERTY);
            } else {
                Collection<PsiElement> collectionValues4 = tokensCorrespondingToModifiers.values();
                DiagnosticFactory0<PsiElement> PRIVATE_SETTER_FOR_OPEN_PROPERTY = Errors.PRIVATE_SETTER_FOR_OPEN_PROPERTY;
                Intrinsics.checkNotNullExpressionValue(PRIVATE_SETTER_FOR_OPEN_PROPERTY, "PRIVATE_SETTER_FOR_OPEN_PROPERTY");
                reportVisibilityModifierDiagnostics(collectionValues4, PRIVATE_SETTER_FOR_OPEN_PROPERTY);
            }
        } else {
            Integer numCompare = DescriptorVisibilities.compare(accessorDescriptor.getVisibilityImpl(), propertyDescriptor.getVisibilityImpl());
            if (numCompare == null || numCompare.intValue() > 0) {
                Collection<PsiElement> collectionValues5 = tokensCorrespondingToModifiers.values();
                DiagnosticFactory0<PsiElement> diagnosticFactory0 = Errors.SETTER_VISIBILITY_INCONSISTENT_WITH_PROPERTY_VISIBILITY;
                Intrinsics.checkNotNullExpressionValue(diagnosticFactory0, "SETTER_VISIBILITY_INCONS…_WITH_PROPERTY_VISIBILITY");
                reportVisibilityModifierDiagnostics(collectionValues5, diagnosticFactory0);
            }
        }
        if (propertyDescriptor.get$isExpect()) {
            checkExpectDeclarationHasNoExternalModifier(accessor);
        }
    }

    private final void checkEnumEntry(KtEnumEntry enumEntry, ClassDescriptor enumEntryClass) {
        DeclarationDescriptor containingModule = enumEntryClass.getContainingModule();
        Intrinsics.checkNotNull(containingModule, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        ClassDescriptor classDescriptor = (ClassDescriptor) containingModule;
        ClassDescriptor classDescriptor2 = classDescriptor;
        if (DescriptorUtils.isEnumClass(classDescriptor2)) {
            if (!classDescriptor.get$isExpect() || enumEntry.getBody() == null) {
                return;
            }
            this.trace.report(Errors.EXPECTED_ENUM_ENTRY_WITH_BODY.on(enumEntry));
            return;
        }
        DescriptorUtils.isInterface(classDescriptor2);
    }

    private final void checkVarargParameters(BindingTrace trace, CallableDescriptor callableDescriptor) {
        List<ValueParameterDescriptor> valueParameters = callableDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : valueParameters) {
            if (((ValueParameterDescriptor) obj).getVarargElementType() != null) {
                arrayList.add(obj);
            }
        }
        ArrayList<ValueParameterDescriptor> arrayList2 = arrayList;
        if (arrayList2.size() > 1) {
            for (ValueParameterDescriptor valueParameterDescriptor : arrayList2) {
                Intrinsics.checkNotNull(valueParameterDescriptor);
                PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(valueParameterDescriptor);
                KtParameter ktParameter = psiElementDescriptorToDeclaration instanceof KtParameter ? (KtParameter) psiElementDescriptorToDeclaration : null;
                if (ktParameter != null) {
                    trace.report(Errors.MULTIPLE_VARARG_PARAMETERS.on(ktParameter));
                }
            }
        }
        SimpleType nullableNothingType = DescriptorUtilsKt.getBuiltIns(callableDescriptor).getNullableNothingType();
        Intrinsics.checkNotNullExpressionValue(nullableNothingType, "getNullableNothingType(...)");
        for (ValueParameterDescriptor valueParameterDescriptor2 : arrayList2) {
            KotlinType varargElementType = valueParameterDescriptor2.getVarargElementType();
            Intrinsics.checkNotNull(varargElementType);
            SimpleType simpleTypeUpperIfFlexible = FlexibleTypesKt.upperIfFlexible(varargElementType);
            SimpleType simpleType = simpleTypeUpperIfFlexible;
            if (KotlinTypeChecker.DEFAULT.isSubtypeOf(simpleType, nullableNothingType) || (InlineClassesUtilsKt.isInlineClassType(simpleType) && !UnsignedTypes.isUnsignedType(simpleType) && !KotlinBuiltIns.isPrimitiveTypeOrNullablePrimitiveType(simpleType))) {
                Intrinsics.checkNotNull(valueParameterDescriptor2);
                PsiElement psiElementDescriptorToDeclaration2 = DescriptorToSourceUtils.descriptorToDeclaration(valueParameterDescriptor2);
                KtParameter ktParameter2 = psiElementDescriptorToDeclaration2 instanceof KtParameter ? (KtParameter) psiElementDescriptorToDeclaration2 : null;
                if (ktParameter2 != null) {
                    trace.report(Errors.FORBIDDEN_VARARG_PARAMETER_TYPE.on(ktParameter2, simpleTypeUpperIfFlexible));
                }
            }
        }
    }

    /* JADX INFO: compiled from: DeclarationsChecker.kt */
    @Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0002J\u0010\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010\u0011\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u001e\u0010\u0012\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0002J\u0016\u0010\u0016\u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u0014H\u0002J\n\u0010\u001a\u001a\u00020\b*\u00020\u001bJ\f\u0010\u001c\u001a\u00020\b*\u00020\u001bH\u0002R2\u0010\u0003\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/resolve/DeclarationsChecker$Companion;", "", "()V", "METHOD_OF_ANY_NAMES", "Lorg/jetbrains/kotlin/com/google/common/collect/ImmutableSet;", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "hasConstraints", "", "typeParameter", "Lorg/jetbrains/kotlin/psi/KtTypeParameter;", "constraints", "", "Lorg/jetbrains/kotlin/psi/KtTypeConstraint;", "isHidingParentMemberIfPresent", "member", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "isImplementingMethodOfAny", "isImplementingMethodOfAnyInternal", "visitedClasses", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "removeDuplicateTypes", "", "conflictingTypes", "Lorg/jetbrains/kotlin/types/KotlinType;", "hasAnyAccessorImplementation", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "hasSetterAccessorImplementation", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void removeDuplicateTypes(Set<KotlinType> conflictingTypes) {
            Iterator<KotlinType> it = conflictingTypes.iterator();
            while (it.hasNext()) {
                KotlinType next = it.next();
                for (KotlinType kotlinType : conflictingTypes) {
                    boolean zEqualTypes = KotlinTypeChecker.DEFAULT.equalTypes(next, kotlinType);
                    if (next != kotlinType && zEqualTypes) {
                        it.remove();
                        break;
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean hasConstraints(KtTypeParameter typeParameter, List<? extends KtTypeConstraint> constraints) {
            if (typeParameter.getName() == null) {
                return false;
            }
            List<? extends KtTypeConstraint> list = constraints;
            if ((list instanceof Collection) && list.isEmpty()) {
                return false;
            }
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                KtSimpleNameExpression subjectTypeParameterName = ((KtTypeConstraint) it.next()).getSubjectTypeParameterName();
                if (Intrinsics.areEqual(subjectTypeParameterName != null ? subjectTypeParameterName.getText() : null, typeParameter.getName())) {
                    return true;
                }
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isImplementingMethodOfAny(CallableMemberDescriptor member) {
            if (DeclarationsChecker.METHOD_OF_ANY_NAMES.contains(member.getShortName().asString()) && member.getModality() != Modality.ABSTRACT) {
                return isImplementingMethodOfAnyInternal(member, new HashSet());
            }
            return false;
        }

        /* JADX WARN: Multi-variable type inference failed */
        private final boolean isImplementingMethodOfAnyInternal(CallableMemberDescriptor member, Set<ClassDescriptor> visitedClasses) {
            for (CallableMemberDescriptor callableMemberDescriptor : member.getOverriddenDescriptors()) {
                DeclarationDescriptor containingDeclaration = callableMemberDescriptor.getContainingModule();
                Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
                if ((containingDeclaration instanceof ClassDescriptor) && !visitedClasses.contains(containingDeclaration)) {
                    if (Intrinsics.areEqual(DescriptorUtils.getFqName(containingDeclaration), StandardNames.FqNames.any)) {
                        return true;
                    }
                    Intrinsics.checkNotNull(callableMemberDescriptor);
                    if (isHidingParentMemberIfPresent(callableMemberDescriptor)) {
                        continue;
                    } else {
                        visitedClasses.add(containingDeclaration);
                        if (isImplementingMethodOfAnyInternal(callableMemberDescriptor, visitedClasses)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isHidingParentMemberIfPresent(CallableMemberDescriptor member) {
            PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(member);
            KtNamedDeclaration ktNamedDeclaration = psiElementDescriptorToDeclaration instanceof KtNamedDeclaration ? (KtNamedDeclaration) psiElementDescriptorToDeclaration : null;
            if (ktNamedDeclaration == null) {
                return false;
            }
            KtModifierList modifierList = ktNamedDeclaration.getModifierList();
            if (modifierList == null) {
                return true;
            }
            return !modifierList.hasModifier(KtTokens.OVERRIDE_KEYWORD);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean hasSetterAccessorImplementation(PropertyDescriptor propertyDescriptor) {
            PropertySetterDescriptor setter = propertyDescriptor.getSetter();
            return setter != null && DescriptorPsiUtilsKt.hasBody(setter);
        }

        public final boolean hasAnyAccessorImplementation(PropertyDescriptor propertyDescriptor) {
            Intrinsics.checkNotNullParameter(propertyDescriptor, "<this>");
            if (hasSetterAccessorImplementation(propertyDescriptor)) {
                return true;
            }
            PropertyGetterDescriptor getter = propertyDescriptor.getGetter();
            return getter != null && DescriptorPsiUtilsKt.hasBody(getter);
        }
    }
}
