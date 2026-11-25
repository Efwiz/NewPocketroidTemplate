package org.jetbrains.kotlin.asJava.classes;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.asJava.LightClassUtilsKt;
import org.jetbrains.kotlin.asJava.UltraLightClassModifierExtension;
import org.jetbrains.kotlin.asJava.elements.KtLightField;
import org.jetbrains.kotlin.asJava.elements.KtLightMethod;
import org.jetbrains.kotlin.asJava.elements.KtUltraLightModifierList;
import org.jetbrains.kotlin.backend.common.CodegenUtil;
import org.jetbrains.kotlin.backend.common.DataClassMethodGenerator;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.codegen.CodegenUtilKt;
import org.jetbrains.kotlin.codegen.JvmCodegenUtil;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.registry.Registry;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.impl.InheritanceImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiSuperMethodImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightMethodBuilder;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValue;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.idea.KotlinLanguage;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.load.kotlin.TypeMappingMode;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.JvmNames;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnnotated;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassBody;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtClassOrObjectKt;
import org.jetbrains.kotlin.psi.KtConstructor;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDelegatedSuperTypeEntry;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPrimaryConstructor;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.KtSuperTypeList;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DelegationResolver;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.annotations.AnnotationUtilKt;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.EnumValue;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKind;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: ultraLightClass.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000ü\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001:\u0001|B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010$\u001a\u00020%2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u001a0'H\u0002J\u001e\u0010$\u001a\u00020%2\u0006\u0010(\u001a\u00020)2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u001a0'H\u0002J\u0016\u0010*\u001a\u00020%2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u001a0'H\u0002J\u0018\u0010+\u001a\u00020%2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/H\u0002J\u0016\u00100\u001a\u0010\u0012\f\u0012\n 2*\u0004\u0018\u00010/0/01H\u0002J\b\u00103\u001a\u000204H\u0014J\b\u00105\u001a\u00020\u0001H\u0016J\b\u00106\u001a\u000207H\u0014J\u000e\u00108\u001a\b\u0012\u0004\u0012\u0002090\u0013H\u0002J\n\u0010:\u001a\u0004\u0018\u00010;H\u0014J\n\u0010<\u001a\u0004\u0018\u00010;H\u0014J\u0010\u0010=\u001a\u00020;2\u0006\u0010>\u001a\u00020\bH\u0002J\b\u0010?\u001a\u00020@H\u0002J\n\u0010A\u001a\u0004\u0018\u000107H\u0016J\u0013\u0010B\u001a\b\u0012\u0004\u0012\u00020D0CH\u0016¢\u0006\u0002\u0010EJ\u0013\u0010F\u001a\b\u0012\u0004\u0012\u0002070CH\u0016¢\u0006\u0002\u0010GJ\n\u0010H\u001a\u0004\u0018\u00010IH\u0016J\n\u0010J\u001a\u0004\u0018\u00010\u000eH\u0016J\u000e\u0010K\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013H\u0016J\u000e\u0010L\u001a\b\u0012\u0004\u0012\u0002070\u0013H\u0016J\u000e\u0010M\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0013H\u0016J\n\u0010N\u001a\u0004\u0018\u00010IH\u0016J\n\u0010O\u001a\u0004\u0018\u00010IH\u0016J\n\u0010P\u001a\u0004\u0018\u00010IH\u0016J\n\u0010Q\u001a\u0004\u0018\u000107H\u0016J\u0013\u0010R\u001a\b\u0012\u0004\u0012\u00020S0CH\u0016¢\u0006\u0002\u0010TJ\u0013\u0010U\u001a\b\u0012\u0004\u0012\u0002070CH\u0016¢\u0006\u0002\u0010GJ\n\u0010V\u001a\u0004\u0018\u00010WH\u0016J\u000e\u0010X\u001a\b\u0012\u0004\u0012\u00020Z0YH\u0016J\b\u0010[\u001a\u00020\bH\u0016J\u0010\u0010\\\u001a\u00020\b2\u0006\u0010]\u001a\u00020\bH\u0016J\u0010\u0010^\u001a\u00020\b2\u0006\u0010_\u001a\u00020`H\u0002J\u001c\u0010a\u001a\u00020\b2\b\u0010b\u001a\u0004\u0018\u0001072\b\u0010c\u001a\u0004\u0018\u000107H\u0016J\u0010\u0010d\u001a\u00020\b2\u0006\u0010_\u001a\u00020eH\u0002J\b\u0010f\u001a\u00020\bH\u0002J\u0018\u0010g\u001a\u00020\b2\u0006\u0010h\u001a\u00020/2\u0006\u0010>\u001a\u00020\bH\u0002J\u001c\u0010i\u001a\u0004\u0018\u00010S2\u0006\u0010h\u001a\u00020/2\b\b\u0002\u0010j\u001a\u00020\bH\u0002J \u0010k\u001a\u00020@2\u0006\u0010l\u001a\u00020m2\u0006\u0010_\u001a\u00020`2\u0006\u0010n\u001a\u00020oH\u0002J\u000e\u0010p\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0013H\u0002J\u000e\u0010q\u001a\b\u0012\u0004\u0012\u00020r0\u0013H\u0002J\u0010\u0010s\u001a\u00020\b2\u0006\u0010t\u001a\u00020uH\u0002J\u0014\u0010v\u001a\u00020\b*\u00020e2\u0006\u0010w\u001a\u00020xH\u0002J\f\u0010y\u001a\u00020\b*\u00020zH\u0002J\f\u0010{\u001a\u00020\b*\u00020zH\u0002R\u001b\u0010\u0007\u001a\u00020\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u001d\u0010\r\u001a\u0004\u0018\u00010\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\f\u001a\u0004\b\u000f\u0010\u0010R!\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00138BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\f\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00130\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u001b\u001a\u00020\u001c8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001f\u0010\f\u001a\u0004\b\u001d\u0010\u001eR\u0014\u0010 \u001a\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\nR\u0014\u0010\u0004\u001a\u00020\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#¨\u0006}"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightClass;", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClassImpl;", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "support", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "(Lorg/jetbrains/kotlin/psi/KtClassOrObject;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;)V", "_deprecated", "", "get_deprecated", "()Z", "_deprecated$delegate", "Lkotlin/Lazy;", "_modifierList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierList;", "get_modifierList", "()Lcom/intellij/psi/PsiModifierList;", "_modifierList$delegate", "_ownFields", "", "Lorg/jetbrains/kotlin/asJava/elements/KtLightField;", "get_ownFields", "()Ljava/util/List;", "_ownFields$delegate", "_ownMethods", "Lorg/jetbrains/kotlin/com/intellij/psi/util/CachedValue;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMethod;", "membersBuilder", "Lorg/jetbrains/kotlin/asJava/classes/UltraLightMembersCreator;", "getMembersBuilder", "()Lorg/jetbrains/kotlin/asJava/classes/UltraLightMembersCreator;", "membersBuilder$delegate", "shouldGenerateRepeatableAnnotationContainer", "getShouldGenerateRepeatableAnnotationContainer", "getSupport$light_classes", "()Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "addDelegatesToInterfaceMethods", "", "result", "", "superTypeEntry", "Lorg/jetbrains/kotlin/psi/KtDelegatedSuperTypeEntry;", "addMethodsFromDataClass", "addTypeToTypeList", "listBuilder", "Lorg/jetbrains/kotlin/asJava/classes/KotlinSuperTypeListBuilder;", "superType", "Lorg/jetbrains/kotlin/types/KotlinType;", "allSuperTypes", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "buildTypeParameterList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameterList;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "createClassForInterfaceDefaultImpls", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "createConstructors", "Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;", "createExtendsList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiReferenceList;", "createImplementsList", "createInheritanceList", "forExtendsList", "defaultConstructor", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethod;", "getContainingClass", "getInitializers", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClassInitializer;", "()[Lcom/intellij/psi/PsiClassInitializer;", "getInterfaces", "()[Lcom/intellij/psi/PsiClass;", "getLBrace", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getModifierList", "getOwnFields", "getOwnInnerClasses", "getOwnMethods", "getParent", "getRBrace", "getScope", "getSuperClass", "getSuperTypes", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClassType;", "()[Lcom/intellij/psi/PsiClassType;", "getSupers", "getTextRange", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "getVisibleSignatures", "", "Lorg/jetbrains/kotlin/com/intellij/psi/HierarchicalMethodSignature;", "isDeprecated", "isFinal", "isFinalByPsi", "isHiddenByDeprecation", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "isInheritorDeep", "baseClass", "classToByPass", "isJvmStatic", "Lorg/jetbrains/kotlin/psi/KtAnnotated;", "isNamedObject", "isTypeForInheritanceList", "supertype", "mapSupertype", "kotlinCollectionAsIs", "noArgConstructor", "visibility", "", "methodIndex", "", "ownMethods", "propertyParameters", "Lorg/jetbrains/kotlin/psi/KtParameter;", "shouldGenerateNoArgOverload", "primary", "Lorg/jetbrains/kotlin/psi/KtPrimaryConstructor;", "hasAnnotation", "name", "Lorg/jetbrains/kotlin/name/FqName;", "isConstOrJvmField", "Lorg/jetbrains/kotlin/psi/KtCallableDeclaration;", "isJvmField", "KtUltraLightClassModifierList", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KtUltraLightClass extends KtLightClassImpl {

    /* JADX INFO: renamed from: _deprecated$delegate, reason: from kotlin metadata */
    private final Lazy _deprecated;

    /* JADX INFO: renamed from: _modifierList$delegate, reason: from kotlin metadata */
    private final Lazy _modifierList;

    /* JADX INFO: renamed from: _ownFields$delegate, reason: from kotlin metadata */
    private final Lazy _ownFields;
    private final CachedValue<List<PsiMethod>> _ownMethods;

    /* JADX INFO: renamed from: membersBuilder$delegate, reason: from kotlin metadata */
    private final Lazy membersBuilder;
    private final KtUltraLightSupport support;

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiElement getLBrace() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiElement getRBrace() {
        return null;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration
    public boolean isFinal(boolean isFinalByPsi) {
        return isFinalByPsi;
    }

    /* JADX INFO: renamed from: getSupport$light_classes, reason: from getter */
    public final KtUltraLightSupport getSupport() {
        return this.support;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtUltraLightClass(final KtClassOrObject classOrObject, KtUltraLightSupport support) {
        super(classOrObject, UltraLightSupportKt.getJvmDefaultMode(support));
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        Intrinsics.checkNotNullParameter(support, "support");
        this.support = support;
        this.membersBuilder = ImplUtilsKt.lazyPub(new Function0<UltraLightMembersCreator>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightClass$membersBuilder$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final UltraLightMembersCreator invoke() {
                KtUltraLightClass ktUltraLightClass = this.this$0;
                return new UltraLightMembersCreator(ktUltraLightClass, ktUltraLightClass.isNamedObject(), classOrObject.hasModifier(KtTokens.SEALED_KEYWORD), true, this.this$0.getSupport());
            }
        });
        this._deprecated = ImplUtilsKt.lazyPub(new Function0<Boolean>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightClass$_deprecated$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                return Boolean.valueOf(UltraLightUtilsKt.isDeprecated(classOrObject, this.getSupport()));
            }
        });
        this._modifierList = ImplUtilsKt.lazyPub(new Function0<KtUltraLightClassModifierList>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightClass$_modifierList$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KtUltraLightClass.KtUltraLightClassModifierList invoke() {
                KtUltraLightClass ktUltraLightClass = this.this$0;
                KtUltraLightClass ktUltraLightClass2 = ktUltraLightClass;
                KtUltraLightSupport support2 = ktUltraLightClass.getSupport();
                final KtUltraLightClass ktUltraLightClass3 = this.this$0;
                Lazy lazyLazyPub = ImplUtilsKt.lazyPub(new Function0<Set<? extends String>>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightClass$_modifierList$2.1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Set<? extends String> invoke() {
                        return ktUltraLightClass3.computeModifiersByPsi();
                    }
                });
                final KtUltraLightClass ktUltraLightClass4 = this.this$0;
                return new KtUltraLightClass.KtUltraLightClassModifierList(ktUltraLightClass2, support2, lazyLazyPub, ImplUtilsKt.lazyPub(new Function0<Boolean>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightClass$_modifierList$2.2
                    {
                        super(0);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // kotlin.jvm.functions.Function0
                    public final Boolean invoke() {
                        return Boolean.valueOf(ktUltraLightClass4.computeIsFinal());
                    }
                }));
            }
        });
        this._ownFields = ImplUtilsKt.lazyPub(new Function0<ArrayList<KtLightField>>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightClass$_ownFields$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ArrayList<KtLightField> invoke() {
                ArrayList arrayList = new ArrayList();
                HashSet<String> hashSet = new HashSet<>();
                List<KtObjectDeclaration> companionObjects = this.this$0.getClassOrObject().getCompanionObjects();
                Intrinsics.checkNotNullExpressionValue(companionObjects, "getCompanionObjects(...)");
                KtObjectDeclaration ktObjectDeclaration = (KtObjectDeclaration) CollectionsKt.firstOrNull((List) companionObjects);
                if (ktObjectDeclaration != null) {
                    KtUltraLightClass ktUltraLightClass = this.this$0;
                    KtObjectDeclaration ktObjectDeclaration2 = ktObjectDeclaration;
                    UltraLightMembersCreator membersBuilder = ktUltraLightClass.getMembersBuilder();
                    String str = ktObjectDeclaration.get_name();
                    if (str == null) {
                        str = "";
                    }
                    arrayList.add(new KtUltraLightFieldForSourceDeclaration(ktObjectDeclaration2, membersBuilder.generateUniqueFieldName(str, hashSet), ktUltraLightClass, ktUltraLightClass.getSupport(), SetsKt.setOf((Object[]) new String[]{"static", "final", UltraLightUtilsKt.simpleVisibility(ktObjectDeclaration)})));
                    List<KtDeclaration> declarations = ktObjectDeclaration.getDeclarations();
                    ArrayList<KtProperty> arrayList2 = new ArrayList();
                    for (Object obj : declarations) {
                        if (obj instanceof KtProperty) {
                            arrayList2.add(obj);
                        }
                    }
                    for (KtProperty ktProperty : arrayList2) {
                        if (!ktUltraLightClass.isInterface() || ktUltraLightClass.isConstOrJvmField(ktProperty)) {
                            KtLightField ktLightFieldCreatePropertyField = ktUltraLightClass.getMembersBuilder().createPropertyField(ktProperty, hashSet, true);
                            if (ktLightFieldCreatePropertyField != null) {
                                arrayList.add(ktLightFieldCreatePropertyField);
                            }
                        }
                    }
                }
                if (this.this$0.isAnnotationType()) {
                    return invoke$updateWithCompilerPlugins(arrayList, this.this$0, arrayList);
                }
                Iterator it = this.this$0.propertyParameters().iterator();
                while (it.hasNext()) {
                    KtLightField ktLightFieldCreatePropertyField2 = this.this$0.getMembersBuilder().createPropertyField((KtParameter) it.next(), hashSet, false);
                    if (ktLightFieldCreatePropertyField2 != null) {
                        arrayList.add(ktLightFieldCreatePropertyField2);
                    }
                }
                if (!this.this$0.isInterface()) {
                    KtClassOrObject classOrObject2 = this.this$0.getClassOrObject();
                    if (!(classOrObject2 instanceof KtObjectDeclaration)) {
                        classOrObject2 = null;
                    }
                    KtObjectDeclaration ktObjectDeclaration3 = (KtObjectDeclaration) classOrObject2;
                    boolean z = ktObjectDeclaration3 != null && ktObjectDeclaration3.isCompanion();
                    List<KtDeclaration> declarations2 = this.this$0.getClassOrObject().getDeclarations();
                    ArrayList<KtProperty> arrayList3 = new ArrayList();
                    for (Object obj2 : declarations2) {
                        if (obj2 instanceof KtProperty) {
                            arrayList3.add(obj2);
                        }
                    }
                    for (KtProperty ktProperty2 : arrayList3) {
                        if (z) {
                            PsiClass psiClassMo6092getContainingClass = this.this$0.mo6092getContainingClass();
                            if (psiClassMo6092getContainingClass == null || psiClassMo6092getContainingClass.isInterface()) {
                                if (this.this$0.isJvmField(ktProperty2)) {
                                }
                            }
                        }
                        KtLightField ktLightFieldCreatePropertyField3 = this.this$0.getMembersBuilder().createPropertyField(ktProperty2, hashSet, this.this$0.getClassOrObject() instanceof KtObjectDeclaration);
                        if (ktLightFieldCreatePropertyField3 != null) {
                            arrayList.add(ktLightFieldCreatePropertyField3);
                        }
                    }
                }
                if (this.this$0.isNamedObject() && !this.this$0.getClassOrObject().isLocal()) {
                    KtClassOrObject classOrObject3 = this.this$0.getClassOrObject();
                    KtUltraLightClass ktUltraLightClass2 = this.this$0;
                    arrayList.add(new KtUltraLightFieldForSourceDeclaration(classOrObject3, JvmAbi.INSTANCE_FIELD, ktUltraLightClass2, ktUltraLightClass2.getSupport(), SetsKt.setOf((Object[]) new String[]{"static", "final", "public"})));
                }
                if (this.this$0.isEnum()) {
                    List<KtDeclaration> declarations3 = classOrObject.getDeclarations();
                    ArrayList<KtEnumEntry> arrayList4 = new ArrayList();
                    for (Object obj3 : declarations3) {
                        if (obj3 instanceof KtEnumEntry) {
                            arrayList4.add(obj3);
                        }
                    }
                    for (KtEnumEntry ktEnumEntry : arrayList4) {
                        String name2 = ktEnumEntry.get_name();
                        if (name2 != null) {
                            KtUltraLightClass ktUltraLightClass3 = this.this$0;
                            arrayList.add(new KtUltraLightEnumEntry(ktEnumEntry, name2, ktUltraLightClass3, ktUltraLightClass3.getSupport(), SetsKt.setOf((Object[]) new String[]{"static", "final", "public"})));
                        }
                    }
                }
                return invoke$updateWithCompilerPlugins(arrayList, this.this$0, arrayList);
            }

            private static final ArrayList<KtLightField> invoke$updateWithCompilerPlugins(ArrayList<KtLightField> arrayList, final KtUltraLightClass ktUltraLightClass, ArrayList<KtLightField> arrayList2) {
                Lazy<? extends DeclarationDescriptor> lazy = LazyKt.lazy(new Function0<ClassDescriptor>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightClass$_ownFields$2$updateWithCompilerPlugins$1$lazyDescriptor$1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final ClassDescriptor invoke() {
                        return ktUltraLightClass.getDescriptor();
                    }
                });
                Project project = ktUltraLightClass.getProject();
                Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
                Iterator<T> it = UltraLightClassModifierExtension.INSTANCE.getInstances(project).iterator();
                while (it.hasNext()) {
                    ((UltraLightClassModifierExtension) it.next()).interceptFieldsBuilding(ktUltraLightClass.getKotlinOrigin(), lazy, ktUltraLightClass, arrayList2);
                }
                return arrayList;
            }
        });
        CachedValue<List<PsiMethod>> cachedValueCreateCachedValue = CachedValuesManager.getManager(getProject()).createCachedValue(new CachedValueProvider() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightClass$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return KtUltraLightClass._ownMethods$lambda$7(this.f$0, classOrObject);
            }
        }, false);
        Intrinsics.checkNotNullExpressionValue(cachedValueCreateCachedValue, "createCachedValue(...)");
        this._ownMethods = cachedValueCreateCachedValue;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: ultraLightClass.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B7\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0007\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0007¢\u0006\u0002\u0010\fJ\b\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\tH\u0016R\u000e\u0010\u0003\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightClass$KtUltraLightClassModifierList;", "Lorg/jetbrains/kotlin/asJava/elements/KtUltraLightModifierList;", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClassForSourceDeclaration;", "containingClass", "support", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "lazyModifiers", "Lkotlin/Lazy;", "", "", "lazyIsFinal", "", "(Lorg/jetbrains/kotlin/asJava/classes/KtLightClassForSourceDeclaration;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;Lkotlin/Lazy;Lkotlin/Lazy;)V", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "hasModifierProperty", "name", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class KtUltraLightClassModifierList extends KtUltraLightModifierList<KtLightClassForSourceDeclaration> {
        private final KtLightClassForSourceDeclaration containingClass;
        private final Lazy<Boolean> lazyIsFinal;
        private final Lazy<Set<String>> lazyModifiers;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public KtUltraLightClassModifierList(KtLightClassForSourceDeclaration containingClass, KtUltraLightSupport support, Lazy<? extends Set<String>> lazyModifiers, Lazy<Boolean> lazyIsFinal) {
            super(containingClass, support);
            Intrinsics.checkNotNullParameter(containingClass, "containingClass");
            Intrinsics.checkNotNullParameter(support, "support");
            Intrinsics.checkNotNullParameter(lazyModifiers, "lazyModifiers");
            Intrinsics.checkNotNullParameter(lazyIsFinal, "lazyIsFinal");
            this.containingClass = containingClass;
            this.lazyModifiers = lazyModifiers;
            this.lazyIsFinal = lazyIsFinal;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierList
        public boolean hasModifierProperty(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return !Intrinsics.areEqual(name2, "final") ? this.lazyModifiers.getValue().contains(name2) : ((KtLightClassForSourceDeclaration) getOwner()).isFinal(this.lazyIsFinal.getValue().booleanValue());
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement copy() {
            return new KtUltraLightClassModifierList(this.containingClass, getSupport(), this.lazyModifiers, this.lazyIsFinal);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final UltraLightMembersCreator getMembersBuilder() {
        return (UltraLightMembersCreator) this.membersBuilder.getValue();
    }

    private final boolean get_deprecated() {
        return ((Boolean) this._deprecated.getValue()).booleanValue();
    }

    private final PsiModifierList get_modifierList() {
        return (PsiModifierList) this._modifierList.getValue();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        return get_modifierList();
    }

    private final Collection<KotlinType> allSuperTypes() {
        TypeConstructor typeConstructor;
        ClassDescriptor descriptor = getDescriptor();
        Collection<KotlinType> collectionMo7438getSupertypes = (descriptor == null || (typeConstructor = descriptor.getTypeConstructor()) == null) ? null : typeConstructor.mo7438getSupertypes();
        return collectionMo7438getSupertypes == null ? CollectionsKt.emptyList() : collectionMo7438getSupertypes;
    }

    private final PsiClassType mapSupertype(KotlinType supertype, boolean kotlinCollectionAsIs) {
        PsiType psiTypeAsPsiType = UltraLightUtilsKt.asPsiType(supertype, this.support, kotlinCollectionAsIs ? TypeMappingMode.SUPER_TYPE_KOTLIN_COLLECTIONS_AS_IS : TypeMappingMode.SUPER_TYPE, this);
        if (psiTypeAsPsiType instanceof PsiClassType) {
            return (PsiClassType) psiTypeAsPsiType;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration
    protected PsiReferenceList createExtendsList() {
        return createInheritanceList(true);
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration
    protected PsiReferenceList createImplementsList() {
        return createInheritanceList(false);
    }

    private final PsiReferenceList createInheritanceList(boolean forExtendsList) {
        PsiReferenceList.Role role = forExtendsList ? PsiReferenceList.Role.EXTENDS_LIST : PsiReferenceList.Role.IMPLEMENTS_LIST;
        if (isAnnotationType()) {
            return new KotlinLightReferenceListBuilder(getManager(), getLanguage(), role);
        }
        Collection<KotlinType> collectionAllSuperTypes = allSuperTypes();
        ArrayList<KotlinType> arrayList = new ArrayList();
        for (Object obj : collectionAllSuperTypes) {
            KotlinType kotlinType = (KotlinType) obj;
            Intrinsics.checkNotNull(kotlinType);
            if (isTypeForInheritanceList(kotlinType, forExtendsList)) {
                arrayList.add(obj);
            }
        }
        KtSuperTypeList superTypeList = getKotlinOrigin().getSuperTypeList();
        PsiManager manager = getManager();
        Intrinsics.checkNotNullExpressionValue(manager, "getManager(...)");
        KotlinSuperTypeListBuilder kotlinSuperTypeListBuilder = new KotlinSuperTypeListBuilder(this, superTypeList, manager, getLanguage(), role);
        for (KotlinType kotlinType2 : arrayList) {
            Intrinsics.checkNotNull(kotlinType2);
            addTypeToTypeList(kotlinSuperTypeListBuilder, kotlinType2);
        }
        return kotlinSuperTypeListBuilder;
    }

    private final void addTypeToTypeList(KotlinSuperTypeListBuilder listBuilder, KotlinType superType) {
        PsiClassType psiClassTypeMapSupertype;
        PsiClassType psiClassTypeMapSupertype2 = mapSupertype(superType, true);
        if (psiClassTypeMapSupertype2 == null) {
            return;
        }
        listBuilder.addReference(psiClassTypeMapSupertype2);
        String canonicalText = psiClassTypeMapSupertype2.getCanonicalText();
        Intrinsics.checkNotNullExpressionValue(canonicalText, "getCanonicalText(...)");
        if (!StringsKt.startsWith$default(canonicalText, "kotlin.collections.", false, 2, (Object) null) || (psiClassTypeMapSupertype = mapSupertype(superType, false)) == null || Intrinsics.areEqual(psiClassTypeMapSupertype2.getCanonicalText(), psiClassTypeMapSupertype.getCanonicalText())) {
            return;
        }
        listBuilder.addReference(psiClassTypeMapSupertype);
        ClassId classId = DescriptorUtilsKt.getClassId(superType.getConstructor().getThis$0());
        if (classId != null) {
            listBuilder.addMarkerInterfaceIfNeeded(classId);
        }
    }

    private final boolean isTypeForInheritanceList(KotlinType supertype, boolean forExtendsList) {
        if (TypeUtilsKt.isAnyOrNullableAny(supertype)) {
            return false;
        }
        if (isEnum() && KotlinBuiltIns.isEnum(supertype)) {
            return false;
        }
        if (isInterface()) {
            return forExtendsList;
        }
        return forExtendsList == (JvmCodegenUtil.isJvmInterface(supertype) ^ true);
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration
    protected PsiTypeParameterList buildTypeParameterList() {
        return UltraLightUtilsKt.buildTypeParameterListForSourceDeclaration(getClassOrObject(), this, this.support);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getInterfaces() {
        PsiClass[] interfaces = PsiClassImplUtil.getInterfaces(this);
        Intrinsics.checkNotNullExpressionValue(interfaces, "getInterfaces(...)");
        return interfaces;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass getSuperClass() {
        return PsiClassImplUtil.getSuperClass(this);
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getSupers() {
        PsiClass[] supers = PsiClassImplUtil.getSupers(this);
        Intrinsics.checkNotNullExpressionValue(supers, "getSupers(...)");
        return supers;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassType[] getSuperTypes() {
        PsiClassType[] superTypes = PsiClassImplUtil.getSuperTypes(this);
        Intrinsics.checkNotNullExpressionValue(superTypes, "getSuperTypes(...)");
        return superTypes;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public Collection<HierarchicalMethodSignature> getVisibleSignatures() {
        Collection<HierarchicalMethodSignature> visibleSignatures = PsiSuperMethodImplUtil.getVisibleSignatures(this);
        Intrinsics.checkNotNullExpressionValue(visibleSignatures, "getVisibleSignatures(...)");
        return visibleSignatures;
    }

    private final List<KtLightField> get_ownFields() {
        return (List) this._ownFields.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isNamedObject() {
        if (getClassOrObject() instanceof KtObjectDeclaration) {
            KtClassOrObject classOrObject = getClassOrObject();
            if (classOrObject != null) {
                if (!((KtObjectDeclaration) classOrObject).isCompanion()) {
                    return true;
                }
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtObjectDeclaration");
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassBase, org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public List<KtLightField> getOwnFields() {
        return get_ownFields();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<KtParameter> propertyParameters() {
        List<KtParameter> primaryConstructorParameters = getClassOrObject().getPrimaryConstructorParameters();
        ArrayList arrayList = new ArrayList();
        for (Object obj : primaryConstructorParameters) {
            if (((KtParameter) obj).hasValOrVar()) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    private final List<PsiMethod> ownMethods() {
        ArrayList arrayList = new ArrayList();
        List<KtDeclaration> declarations = getClassOrObject().getDeclarations();
        ArrayList<KtDeclaration> arrayList2 = new ArrayList();
        for (Object obj : declarations) {
            if (!UltraLightUtilsKt.isHiddenByDeprecation((KtDeclaration) obj, this.support)) {
                arrayList2.add(obj);
            }
        }
        for (KtDeclaration ktDeclaration : arrayList2) {
            if (!ktDeclaration.hasModifier(KtTokens.PRIVATE_KEYWORD) || !isInterface()) {
                if (ktDeclaration instanceof KtNamedFunction) {
                    arrayList.addAll(UltraLightMembersCreator.createMethods$default(getMembersBuilder(), (KtFunction) ktDeclaration, false, false, false, null, 28, null));
                } else if (ktDeclaration instanceof KtProperty) {
                    arrayList.addAll(getMembersBuilder().propertyAccessors((KtCallableDeclaration) ktDeclaration, ((KtProperty) ktDeclaration).isVar(), false, false, (48 & 16) != 0 ? false : false, (48 & 32) != 0 ? false : false, (48 & 64) != 0 ? false : false, (48 & 128) != 0 ? null : null));
                }
            }
        }
        for (KtParameter ktParameter : propertyParameters()) {
            arrayList.addAll(getMembersBuilder().propertyAccessors(ktParameter, ktParameter.isMutable(), false, false, (48 & 16) != 0 ? false : isAnnotationType(), (48 & 32) != 0 ? false : hasAnnotation(getClassOrObject(), JvmNames.JVM_RECORD_ANNOTATION_FQ_NAME), (48 & 64) != 0 ? false : false, (48 & 128) != 0 ? null : null));
        }
        if (!isInterface()) {
            arrayList.addAll(createConstructors());
        }
        List<KtObjectDeclaration> companionObjects = getClassOrObject().getCompanionObjects();
        Intrinsics.checkNotNullExpressionValue(companionObjects, "getCompanionObjects(...)");
        KtObjectDeclaration ktObjectDeclaration = (KtObjectDeclaration) CollectionsKt.firstOrNull((List) companionObjects);
        if (ktObjectDeclaration != null) {
            List<KtDeclaration> declarations2 = ktObjectDeclaration.getDeclarations();
            ArrayList<KtDeclaration> arrayList3 = new ArrayList();
            for (Object obj2 : declarations2) {
                if (!isHiddenByDeprecation((KtDeclaration) obj2)) {
                    arrayList3.add(obj2);
                }
            }
            for (KtDeclaration ktDeclaration2 : arrayList3) {
                if (ktDeclaration2 instanceof KtNamedFunction) {
                    if (isJvmStatic(ktDeclaration2)) {
                        arrayList.addAll(UltraLightMembersCreator.createMethods$default(getMembersBuilder(), (KtFunction) ktDeclaration2, true, false, false, null, 28, null));
                    }
                } else if (ktDeclaration2 instanceof KtProperty) {
                    arrayList.addAll(getMembersBuilder().propertyAccessors((KtCallableDeclaration) ktDeclaration2, ((KtProperty) ktDeclaration2).isVar(), false, true, (48 & 16) != 0 ? false : false, (48 & 32) != 0 ? false : false, (48 & 64) != 0 ? false : false, (48 & 128) != 0 ? null : null));
                }
            }
        }
        addMethodsFromDataClass(arrayList);
        addDelegatesToInterfaceMethods(arrayList);
        Lazy<? extends DeclarationDescriptor> lazy = LazyKt.lazy(new Function0<ClassDescriptor>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightClass$ownMethods$lazyDescriptor$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ClassDescriptor invoke() {
                return this.this$0.getDescriptor();
            }
        });
        Project project = getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        for (UltraLightClassModifierExtension ultraLightClassModifierExtension : UltraLightClassModifierExtension.INSTANCE.getInstances(project)) {
            ArrayList arrayList4 = new ArrayList();
            ultraLightClassModifierExtension.interceptMethodsBuilding(getKotlinOrigin(), lazy, this, arrayList4);
            arrayList.addAll(arrayList4);
        }
        if (isEnum() && this.support.getLanguageVersionSettings().supportsFeature(LanguageFeature.EnumEntries)) {
            arrayList.add(KotlinClassInnerStuffCacheKt.getEnumEntriesPsiMethod(this));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CachedValueProvider.Result _ownMethods$lambda$7(KtUltraLightClass this$0, KtClassOrObject classOrObject) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(classOrObject, "$classOrObject");
        return CachedValueProvider.Result.create(this$0.ownMethods(), ImplUtilsKt.getExternalDependencies(classOrObject));
    }

    private final void addMethodsFromDataClass(final List<PsiMethod> result) {
        if (getClassOrObject().hasModifier(KtTokens.DATA_KEYWORD)) {
            KtClassOrObject classOrObject = getClassOrObject();
            KtClass ktClass = classOrObject instanceof KtClass ? (KtClass) classOrObject : null;
            if (ktClass == null) {
                return;
            }
            DeclarationDescriptor declarationDescriptorResolve = UltraLightUtilsKt.resolve(getClassOrObject());
            ClassDescriptor classDescriptor = declarationDescriptorResolve instanceof ClassDescriptor ? (ClassDescriptor) declarationDescriptorResolve : null;
            if (classDescriptor == null) {
                return;
            }
            BindingContext bindingContextAnalyze = UltraLightUtilsKt.analyze(getClassOrObject());
            MemberScope unsubstitutedMemberScope = classDescriptor.getUnsubstitutedMemberScope();
            Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope, "getUnsubstitutedMemberScope(...)");
            ResolutionScope.DefaultImpls.getContributedDescriptors$default(unsubstitutedMemberScope, null, null, 3, null);
            List<KtParameter> primaryConstructorParameters = ktClass.getPrimaryConstructorParameters();
            ArrayList arrayList = new ArrayList();
            for (Object obj : primaryConstructorParameters) {
                if (((KtParameter) obj).hasValOrVar()) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = arrayList;
            if (!(arrayList2 instanceof Collection) || !arrayList2.isEmpty()) {
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    if (bindingContextAnalyze.get(BindingContext.PRIMARY_CONSTRUCTOR_PARAMETER, (KtParameter) it.next()) == null) {
                        return;
                    }
                }
            }
            new DataClassMethodGenerator(bindingContextAnalyze, getClassOrObject()) { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightClass.addMethodsFromDataClass.1
                static /* synthetic */ void addFunction$default(AnonymousClass1 anonymousClass1, FunctionDescriptor functionDescriptor, KtDeclaration ktDeclaration, int i, Object obj2) {
                    if ((i & 2) != 0) {
                        ktDeclaration = null;
                    }
                    anonymousClass1.addFunction(functionDescriptor, ktDeclaration);
                }

                private final void addFunction(FunctionDescriptor descriptor, KtDeclaration declarationForOrigin) {
                    result.add(UltraLightUtilsKt.createGeneratedMethodFromDescriptor(this, descriptor, JvmDeclarationOriginKind.OTHER, declarationForOrigin));
                }

                @Override // org.jetbrains.kotlin.backend.common.DataClassMethodGenerator
                protected void generateComponentFunction(FunctionDescriptor function, ValueParameterDescriptor parameter) {
                    Intrinsics.checkNotNullParameter(function, "function");
                    Intrinsics.checkNotNullParameter(parameter, "parameter");
                    PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(parameter);
                    addFunction(function, psiElementDescriptorToDeclaration instanceof KtDeclaration ? (KtDeclaration) psiElementDescriptorToDeclaration : null);
                }

                @Override // org.jetbrains.kotlin.backend.common.DataClassMethodGenerator
                protected void generateCopyFunction(FunctionDescriptor function, List<? extends KtParameter> constructorParameters) {
                    Intrinsics.checkNotNullParameter(function, "function");
                    Intrinsics.checkNotNullParameter(constructorParameters, "constructorParameters");
                    addFunction$default(this, function, null, 2, null);
                }

                @Override // org.jetbrains.kotlin.backend.common.FunctionsFromAnyGenerator
                protected void generateToStringMethod(FunctionDescriptor function, List<? extends PropertyDescriptor> properties) {
                    Intrinsics.checkNotNullParameter(function, "function");
                    Intrinsics.checkNotNullParameter(properties, "properties");
                    addFunction$default(this, function, null, 2, null);
                }

                @Override // org.jetbrains.kotlin.backend.common.FunctionsFromAnyGenerator
                protected void generateHashCodeMethod(FunctionDescriptor function, List<? extends PropertyDescriptor> properties) {
                    Intrinsics.checkNotNullParameter(function, "function");
                    Intrinsics.checkNotNullParameter(properties, "properties");
                    addFunction$default(this, function, null, 2, null);
                }

                @Override // org.jetbrains.kotlin.backend.common.FunctionsFromAnyGenerator
                protected void generateEqualsMethod(FunctionDescriptor function, List<? extends PropertyDescriptor> properties) {
                    Intrinsics.checkNotNullParameter(function, "function");
                    Intrinsics.checkNotNullParameter(properties, "properties");
                    addFunction$default(this, function, null, 2, null);
                }
            }.generate();
        }
    }

    private final void addDelegatesToInterfaceMethods(List<PsiMethod> result) {
        List<KtSuperTypeListEntry> superTypeListEntries = getClassOrObject().getSuperTypeListEntries();
        ArrayList arrayList = new ArrayList();
        for (Object obj : superTypeListEntries) {
            if (obj instanceof KtDelegatedSuperTypeEntry) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            addDelegatesToInterfaceMethods((KtDelegatedSuperTypeEntry) it.next(), result);
        }
    }

    private final void addDelegatesToInterfaceMethods(KtDelegatedSuperTypeEntry superTypeEntry, List<PsiMethod> result) {
        KtTypeReference typeReference;
        BindingContext bindingContextAnalyze;
        ClassDescriptor superClassBySuperTypeListEntry;
        DeclarationDescriptor declarationDescriptorResolve = UltraLightUtilsKt.resolve(getClassOrObject());
        ClassDescriptor classDescriptor = declarationDescriptorResolve instanceof ClassDescriptor ? (ClassDescriptor) declarationDescriptorResolve : null;
        if (classDescriptor == null || (typeReference = superTypeEntry.getTypeReference()) == null || (superClassBySuperTypeListEntry = CodegenUtil.getSuperClassBySuperTypeListEntry(superTypeEntry, (bindingContextAnalyze = UltraLightUtilsKt.analyze(typeReference)))) == null) {
            return;
        }
        for (CallableMemberDescriptor callableMemberDescriptor : DelegationResolver.INSTANCE.getDelegates(classDescriptor, superClassBySuperTypeListEntry, CodegenUtilKt.kotlinType(superTypeEntry.getDelegateExpression(), bindingContextAnalyze)).keySet()) {
            if (!(callableMemberDescriptor instanceof PropertyDescriptor)) {
                if (callableMemberDescriptor instanceof FunctionDescriptor) {
                    result.add(UltraLightUtilsKt.createGeneratedMethodFromDescriptor$default(this, (FunctionDescriptor) callableMemberDescriptor, JvmDeclarationOriginKind.DELEGATION, null, 4, null));
                }
            } else {
                List<PropertyAccessorDescriptor> accessors = ((PropertyDescriptor) callableMemberDescriptor).getAccessors();
                Intrinsics.checkNotNullExpressionValue(accessors, "getAccessors(...)");
                for (PropertyAccessorDescriptor propertyAccessorDescriptor : accessors) {
                    Intrinsics.checkNotNull(propertyAccessorDescriptor);
                    result.add(UltraLightUtilsKt.createGeneratedMethodFromDescriptor$default(this, propertyAccessorDescriptor, JvmDeclarationOriginKind.DELEGATION, null, 4, null));
                }
            }
        }
    }

    private final List<KtLightMethod> createConstructors() {
        ArrayList arrayList = new ArrayList();
        List<KtConstructor<?>> allConstructors = KtClassOrObjectKt.getAllConstructors(getClassOrObject());
        if (allConstructors.isEmpty()) {
            arrayList.add(defaultConstructor());
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : allConstructors) {
            if (!isHiddenByDeprecation((KtConstructor) obj)) {
                arrayList2.add(obj);
            }
        }
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList.addAll(UltraLightMembersCreator.createMethods$default(getMembersBuilder(), (KtConstructor) it.next(), false, isEnum(), false, null, 24, null));
        }
        KtPrimaryConstructor primaryConstructor = getClassOrObject().getPrimaryConstructor();
        if (primaryConstructor != null && shouldGenerateNoArgOverload(primaryConstructor)) {
            KtPrimaryConstructor ktPrimaryConstructor = primaryConstructor;
            arrayList.add(noArgConstructor(UltraLightUtilsKt.simpleVisibility(ktPrimaryConstructor), ktPrimaryConstructor, 5));
        }
        return arrayList;
    }

    /* JADX WARN: Code duplicated, block: B:28:0x0075  */
    /* JADX WARN: Code duplicated, block: B:31:0x007f  */
    /* JADX WARN: Code duplicated, block: B:36:0x009e A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:39:0x00a0 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:41:? A[LOOP:0: B:29:0x0079->B:41:?, LOOP_END, SYNTHETIC] */
    private final boolean shouldGenerateNoArgOverload(KtPrimaryConstructor primary) {
        List<KtConstructor<?>> allConstructors;
        Iterator<T> it;
        if (!primary.hasModifier(KtTokens.PRIVATE_KEYWORD) && !getClassOrObject().hasModifier(KtTokens.INNER_KEYWORD) && !isEnum() && !getClassOrObject().hasModifier(KtTokens.SEALED_KEYWORD) && !primary.getValueParameters().isEmpty()) {
            List<KtParameter> valueParameters = primary.getValueParameters();
            if ((valueParameters instanceof Collection) && valueParameters.isEmpty()) {
                allConstructors = KtClassOrObjectKt.getAllConstructors(getClassOrObject());
                if (!(allConstructors instanceof Collection)) {
                    it = allConstructors.iterator();
                    while (it.hasNext()) {
                        if (((KtConstructor) it.next()).getValueParameters().isEmpty()) {
                        }
                    }
                    if (!hasAnnotation(primary, JvmNames.INSTANCE.getJVM_OVERLOADS_FQ_NAME())) {
                        return true;
                    }
                } else {
                    it = allConstructors.iterator();
                    while (it.hasNext()) {
                        if (((KtConstructor) it.next()).getValueParameters().isEmpty()) {
                        }
                    }
                    if (!hasAnnotation(primary, JvmNames.INSTANCE.getJVM_OVERLOADS_FQ_NAME())) {
                        return true;
                    }
                }
            } else {
                Iterator<T> it2 = valueParameters.iterator();
                while (it2.hasNext()) {
                    if (((KtParameter) it2.next()).getDefaultValue() != null) {
                    }
                }
                allConstructors = KtClassOrObjectKt.getAllConstructors(getClassOrObject());
                if (!(allConstructors instanceof Collection) && allConstructors.isEmpty()) {
                    if (!hasAnnotation(primary, JvmNames.INSTANCE.getJVM_OVERLOADS_FQ_NAME())) {
                        return true;
                    }
                } else {
                    it = allConstructors.iterator();
                    while (it.hasNext()) {
                        if (((KtConstructor) it.next()).getValueParameters().isEmpty()) {
                        }
                    }
                    if (!hasAnnotation(primary, JvmNames.INSTANCE.getJVM_OVERLOADS_FQ_NAME())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private final KtUltraLightMethod defaultConstructor() {
        String str;
        if ((getClassOrObject() instanceof KtObjectDeclaration) || isEnum()) {
            str = "private";
        } else if (getClassOrObject().hasModifier(KtTokens.SEALED_KEYWORD)) {
            str = "protected";
        } else {
            str = getClassOrObject() instanceof KtEnumEntry ? PsiModifier.PACKAGE_LOCAL : "public";
        }
        return noArgConstructor(str, getClassOrObject(), 4);
    }

    private final KtUltraLightMethod noArgConstructor(String visibility, KtDeclaration declaration, int methodIndex) {
        PsiManager manager = getManager();
        KotlinLanguage language = getLanguage();
        String name2 = get_name();
        if (name2 == null) {
            name2 = "";
        }
        LightMethodBuilder lightMethodBuilderAddModifier = new LightMethodBuilder(manager, language, name2).setConstructor(true).addModifier(visibility);
        Intrinsics.checkNotNullExpressionValue(lightMethodBuilderAddModifier, "addModifier(...)");
        return new KtUltraLightMethodForSourceDeclaration(lightMethodBuilderAddModifier, declaration, this.support, this, methodIndex);
    }

    private final boolean isHiddenByDeprecation(KtDeclaration declaration) {
        Name enumEntryName;
        Pair<KtAnnotationEntry, AnnotationDescriptor> pairFindAnnotation = UltraLightUtilsKt.findAnnotation(this.support, declaration, new FqName("kotlin.Deprecated"));
        String strAsString = null;
        AnnotationDescriptor second = pairFindAnnotation != null ? pairFindAnnotation.getSecond() : null;
        ConstantValue<?> constantValueArgumentValue = second != null ? AnnotationUtilKt.argumentValue(second, FirebaseAnalytics.Param.LEVEL) : null;
        EnumValue enumValue = constantValueArgumentValue instanceof EnumValue ? (EnumValue) constantValueArgumentValue : null;
        if (enumValue != null && (enumEntryName = enumValue.getEnumEntryName()) != null) {
            strAsString = enumEntryName.asString();
        }
        return Intrinsics.areEqual(strAsString, VirtualFile.PROP_HIDDEN);
    }

    private final boolean isJvmStatic(KtAnnotated declaration) {
        return hasAnnotation(declaration, AnnotationUtilKt.getJVM_STATIC_ANNOTATION_FQ_NAME());
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassBase, org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public List<PsiMethod> getOwnMethods() {
        List<PsiMethod> value = this._ownMethods.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return value;
    }

    private final boolean hasAnnotation(KtAnnotated ktAnnotated, FqName fqName) {
        return UltraLightUtilsKt.findAnnotation(this.support, ktAnnotated, fqName) != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isConstOrJvmField(KtCallableDeclaration ktCallableDeclaration) {
        return ktCallableDeclaration.hasModifier(KtTokens.CONST_KEYWORD) || isJvmField(ktCallableDeclaration);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isJvmField(KtCallableDeclaration ktCallableDeclaration) {
        return hasAnnotation(ktCallableDeclaration, JvmAbi.JVM_FIELD_ANNOTATION_FQ_NAME);
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassImpl, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    /* JADX INFO: renamed from: getContainingClass */
    public PsiClass mo6092getContainingClass() {
        PsiElement parent = getClassOrObject().getParentMethod();
        KtClassBody ktClassBody = parent instanceof KtClassBody ? (KtClassBody) parent : null;
        PsiElement parentMethod = ktClassBody != null ? ktClassBody.getParentMethod() : null;
        KtClassOrObject ktClassOrObject = parentMethod instanceof KtClassOrObject ? (KtClassOrObject) parentMethod : null;
        if (ktClassOrObject != null) {
            return LightClassUtilsKt.toLightClass(ktClassOrObject);
        }
        PsiElement parent2 = getClassOrObject().getParentMethod();
        KtBlockExpression ktBlockExpression = parent2 instanceof KtBlockExpression ? (KtBlockExpression) parent2 : null;
        PsiElement parentMethod2 = ktBlockExpression != null ? ktBlockExpression.getParentMethod() : null;
        KtScript ktScript = parentMethod2 instanceof KtScript ? (KtScript) parentMethod2 : null;
        if (ktScript != null) {
            return LightClassUtilsKt.toLightClass(ktScript);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassImpl, org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getParentMethod() {
        PsiElement psiElementMo6092getContainingClass = mo6092getContainingClass();
        if (psiElementMo6092getContainingClass == null) {
            psiElementMo6092getContainingClass = getContainingFile();
        }
        return psiElementMo6092getContainingClass;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiElement getScope() {
        return getParentMethod();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isInheritorDeep(PsiClass baseClass, PsiClass classToByPass) {
        if (baseClass != null) {
            return InheritanceImplUtil.isInheritorDeep(this, baseClass, classToByPass);
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassImpl, org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner
    public boolean isDeprecated() {
        return get_deprecated();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassImpl, org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public KtLightClassImpl copy() {
        PsiElement psiElementCopy = getClassOrObject().copy();
        Intrinsics.checkNotNull(psiElementCopy, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtClassOrObject");
        return new KtUltraLightClass((KtClassOrObject) psiElementCopy, this.support);
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public TextRange getTextRange() {
        if (Registry.is("kotlin.ultra.light.classes.empty.text.range", true)) {
            return null;
        }
        return super.getTextRange();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassImpl, org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public List<PsiClass> getOwnInnerClasses() {
        List<PsiClass> ownInnerClasses = super.getOwnInnerClasses();
        return getShouldGenerateRepeatableAnnotationContainer() ? CollectionsKt.plus((Collection<? extends KtUltraLightClassForRepeatableAnnotationContainer>) ownInnerClasses, new KtUltraLightClassForRepeatableAnnotationContainer(getClassOrObject(), this.support)) : ownInnerClasses;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassImpl
    protected PsiClass createClassForInterfaceDefaultImpls() {
        return new KtUltraLightClassForInterfaceDefaultImpls(getClassOrObject(), this.support);
    }

    private final boolean getShouldGenerateRepeatableAnnotationContainer() {
        if (isAnnotationType() && hasAnnotation(getClassOrObject(), StandardNames.FqNames.repeatable)) {
            KtClassOrObject classOrObject = getClassOrObject();
            FqName REPEATABLE_ANNOTATION = JvmAnnotationNames.REPEATABLE_ANNOTATION;
            Intrinsics.checkNotNullExpressionValue(REPEATABLE_ANNOTATION, "REPEATABLE_ANNOTATION");
            if (!hasAnnotation(classOrObject, REPEATABLE_ANNOTATION)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassInitializer[] getInitializers() {
        return new PsiClassInitializer[0];
    }
}
