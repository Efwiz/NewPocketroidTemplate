package org.jetbrains.kotlin.asJava.classes;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.reandroid.apk.ApkUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.swing.Icon;
import kotlin.Deprecated;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.asJava.UltraLightClassModifierExtension;
import org.jetbrains.kotlin.asJava.builder.LightMemberOrigin;
import org.jetbrains.kotlin.asJava.builder.LightMemberOriginForDeclaration;
import org.jetbrains.kotlin.asJava.elements.KtLightAbstractAnnotation;
import org.jetbrains.kotlin.asJava.elements.KtLightField;
import org.jetbrains.kotlin.asJava.elements.KtLightMethod;
import org.jetbrains.kotlin.asJava.elements.LightAnnotationsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.signature.JvmSignatureWriter;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightMethodBuilder;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightModifierList;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightParameterListBuilder;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureBackedByPsiMethod;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.EffectiveVisibilityUtilsKt;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotated;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.kotlin.TypeMappingMode;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.JvmNames;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.KtAnnotated;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtConstructor;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.inline.InlineOnlyKt;
import org.jetbrains.kotlin.resolve.jvm.annotations.JvmAnnotationUtilKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKind;
import org.jetbrains.kotlin.types.SimpleType;

/* JADX INFO: compiled from: ultraLightMembersCreator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000¨\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u0000\u0018\u00002\u00020\u0001:\u0003KLMB-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002JT\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u00052\b\b\u0002\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a2\b\b\u0002\u0010\u001c\u001a\u00020\u00052\u0016\b\u0002\u0010\u001d\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u001f\u0018\u00010\u001eH\u0002J \u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020!2\u0006\u0010%\u001a\u00020&H\u0002JH\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00140(2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00052\b\b\u0002\u0010\u0018\u001a\u00020\u00052\b\b\u0002\u0010\u001c\u001a\u00020\u00052\u0016\b\u0002\u0010\u001d\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u001f\u0018\u00010\u001eJ0\u0010)\u001a\u0004\u0018\u00010*2\u0006\u0010+\u001a\u00020\u000e2\u0016\u0010,\u001a\u0012\u0012\u0004\u0012\u00020!0-j\b\u0012\u0004\u0012\u00020!`.2\u0006\u0010\u0017\u001a\u00020\u0005J&\u0010/\u001a\u00020!2\u0006\u00100\u001a\u00020!2\u0016\u00101\u001a\u0012\u0012\u0004\u0012\u00020!0-j\b\u0012\u0004\u0012\u00020!`.J\u0010\u00102\u001a\u00020\u00052\u0006\u00103\u001a\u00020\u000eH\u0002J\u0010\u00104\u001a\u00020\u00052\u0006\u0010\"\u001a\u00020#H\u0002J\u0010\u00105\u001a\u00020\u00052\u0006\u00106\u001a\u00020#H\u0002J\u0011\u00107\u001a\u00020\u00052\u0006\u0010\"\u001a\u00020#H\u0082\u0010J4\u00108\u001a\u00020\u00122\u0006\u0010$\u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u0017\u001a\u00020\u00052\b\b\u0002\u0010\u0018\u001a\u00020\u00052\b\b\u0002\u0010\u001c\u001a\u00020\u0005H\u0002J \u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020#2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010<\u001a\u00020\u0005H\u0002Jb\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00140>2\u0006\u0010\"\u001a\u00020\u000e2\u0006\u0010?\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u00052\u0006\u0010@\u001a\u00020\u00052\b\b\u0002\u0010A\u001a\u00020\u00052\b\b\u0002\u0010B\u001a\u00020\u00052\b\b\u0002\u0010\u001c\u001a\u00020\u00052\u0016\b\u0002\u0010\u001d\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u001f\u0018\u00010\u001eJ\u000e\u0010C\u001a\u0004\u0018\u00010D*\u00020DH\u0002J\u0014\u0010E\u001a\u00020\u0005*\u00020F2\u0006\u0010$\u001a\u00020GH\u0002J\f\u0010H\u001a\u00020\u0005*\u00020\u000eH\u0002J\f\u0010I\u001a\u00020\u0005*\u00020\u000eH\u0002J\f\u0010J\u001a\u00020\u0005*\u00020\u000eH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006N²\u0006\f\u0010O\u001a\u0004\u0018\u00010:X\u008a\u0084\u0002²\u0006\n\u0010P\u001a\u00020:X\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/UltraLightMembersCreator;", "", "containingClass", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;", "containingClassIsNamedObject", "", "containingClassIsSealed", "mangleInternalFunctions", "support", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "(Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;ZZZLorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;)V", "addReceiverParameter", "", "callable", "Lorg/jetbrains/kotlin/psi/KtCallableDeclaration;", "wrapper", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethod;", "associatedBuilder", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/light/LightMethodBuilder;", "asJavaMethod", "Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;", "ktFunction", "Lorg/jetbrains/kotlin/psi/KtFunction;", "forceStatic", "forcePrivate", "numberOfDefaultParametersToAdd", "", "methodIndex", "forceNonFinal", "additionalReceiverParameter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightParameter;", "computeMethodName", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "name", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/asJava/classes/UltraLightMembersCreator$MethodType;", "createMethods", "", "createPropertyField", "Lorg/jetbrains/kotlin/asJava/elements/KtLightField;", "variable", "usedPropertyNames", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "generateUniqueFieldName", ApkUtil.DEF_MODULE_NAME, "usedNames", "hasBackingField", "property", "isFinal", "isInternal", "f", "isInternalNonPublishedApi", "lightMethod", "methodReturnType", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "ktDeclaration", "isSuspendFunction", "propertyAccessors", "", "mutable", "onlyJvmStatic", "createAsAnnotationMethod", "isJvmRecord", "getterIfProperty", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "hasAnnotation", "Lorg/jetbrains/kotlin/psi/KtAnnotated;", "Lorg/jetbrains/kotlin/name/FqName;", "hasReifiedParameters", "isConstOrJvmField", "isJvmField", "KtUltraLightAnnotationMethod", "MethodType", "UltraLightModifierListForMember", "light-classes", "returnType", "getterType"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UltraLightMembersCreator {
    private final KtLightClass containingClass;
    private final boolean containingClassIsNamedObject;
    private final boolean containingClassIsSealed;
    private final boolean mangleInternalFunctions;
    private final KtUltraLightSupport support;

    /* JADX INFO: compiled from: ultraLightMembersCreator.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/UltraLightMembersCreator$MethodType;", "", "(Ljava/lang/String;I)V", "REGULAR", "GETTER", "SETTER", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private enum MethodType {
        REGULAR,
        GETTER,
        SETTER;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<MethodType> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: ultraLightMembersCreator.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[MethodType.values().length];
            try {
                iArr[MethodType.GETTER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[MethodType.SETTER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public UltraLightMembersCreator(KtLightClass containingClass, boolean z, boolean z2, boolean z3, KtUltraLightSupport support) {
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(support, "support");
        this.containingClass = containingClass;
        this.containingClassIsNamedObject = z;
        this.containingClassIsSealed = z2;
        this.mangleInternalFunctions = z3;
        this.support = support;
    }

    public final String generateUniqueFieldName(String base, HashSet<String> usedNames) {
        Intrinsics.checkNotNullParameter(base, "base");
        Intrinsics.checkNotNullParameter(usedNames, "usedNames");
        if (usedNames.add(base)) {
            return base;
        }
        int i = 1;
        while (true) {
            String str = base + '$' + i;
            if (usedNames.add(str)) {
                return str;
            }
            i++;
        }
    }

    public final KtLightField createPropertyField(KtCallableDeclaration variable, HashSet<String> usedPropertyNames, boolean forceStatic) {
        boolean zIsMutable;
        Intrinsics.checkNotNullParameter(variable, "variable");
        Intrinsics.checkNotNullParameter(usedPropertyNames, "usedPropertyNames");
        if (variable.getNameAsSafeName().isSpecial() || !hasBackingField(variable)) {
            return null;
        }
        KtCallableDeclaration ktCallableDeclaration = variable;
        if (hasAnnotation(ktCallableDeclaration, JvmNames.JVM_SYNTHETIC_ANNOTATION_FQ_NAME) || PsiUtilsKt.hasExpectModifier(variable)) {
            return null;
        }
        boolean z = variable instanceof KtProperty;
        KtProperty ktProperty = z ? (KtProperty) variable : null;
        boolean z2 = ktProperty != null && ktProperty.hasDelegate();
        StringBuilder sb = new StringBuilder();
        String name2 = variable.getName();
        if (name2 == null) {
            name2 = "";
        }
        String strGenerateUniqueFieldName = generateUniqueFieldName(sb.append(name2).append(z2 ? JvmAbi.DELEGATED_PROPERTY_NAME_SUFFIX : "").toString(), usedPropertyNames);
        String strSimpleVisibility = "private";
        if (!variable.hasModifier(KtTokens.PRIVATE_KEYWORD) && (variable.hasModifier(KtTokens.LATEINIT_KEYWORD) || isConstOrJvmField(variable))) {
            KtPropertyAccessor setter = ktProperty != null ? ktProperty.getSetter() : null;
            strSimpleVisibility = UltraLightUtilsKt.simpleVisibility(setter == null ? variable : setter);
        }
        HashSet hashSetHashSetOf = SetsKt.hashSetOf(strSimpleVisibility);
        if (z) {
            zIsMutable = ((KtProperty) variable).isVar();
        } else {
            if (!(variable instanceof KtParameter)) {
                throw new IllegalStateException(("Unexpected type of variable: " + variable.getClass()).toString());
            }
            zIsMutable = ((KtParameter) variable).isMutable();
        }
        if (!zIsMutable || variable.hasModifier(KtTokens.CONST_KEYWORD) || z2) {
            hashSetHashSetOf.add("final");
        }
        if (forceStatic || (this.containingClassIsNamedObject && UltraLightUtilsKt.isJvmStatic(ktCallableDeclaration, this.support))) {
            hashSetHashSetOf.add("static");
        }
        return new KtUltraLightFieldForSourceDeclaration(variable, strGenerateUniqueFieldName, this.containingClass, this.support, hashSetHashSetOf);
    }

    private final boolean hasBackingField(KtCallableDeclaration property) {
        if (property.hasModifier(KtTokens.ABSTRACT_KEYWORD)) {
            return false;
        }
        if (property.hasModifier(KtTokens.LATEINIT_KEYWORD) || (property instanceof KtParameter)) {
            return true;
        }
        if (!(property instanceof KtProperty)) {
            return false;
        }
        KtProperty ktProperty = (KtProperty) property;
        if (!ktProperty.hasInitializer()) {
            KtPropertyAccessor getter = ktProperty.getGetter();
            KtPropertyAccessor ktPropertyAccessor = null;
            if (getter == null || !getter.hasBody()) {
                getter = null;
            }
            if (getter != null) {
                KtPropertyAccessor setter = ktProperty.getSetter();
                if (setter != null && setter.hasBody()) {
                    ktPropertyAccessor = setter;
                }
                if (ktPropertyAccessor != null || !ktProperty.isVar()) {
                    return false;
                }
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Collection createMethods$default(UltraLightMembersCreator ultraLightMembersCreator, KtFunction ktFunction, boolean z, boolean z2, boolean z3, Function1 function1, int i, Object obj) {
        boolean z4 = (i & 4) != 0 ? false : z2;
        boolean z5 = (i & 8) != 0 ? false : z3;
        if ((i & 16) != 0) {
            function1 = null;
        }
        return ultraLightMembersCreator.createMethods(ktFunction, z, z4, z5, function1);
    }

    public final Collection<KtLightMethod> createMethods(KtFunction ktFunction, boolean forceStatic, boolean forcePrivate, boolean forceNonFinal, Function1<? super KtUltraLightMethod, ? extends KtUltraLightParameter> additionalReceiverParameter) {
        Intrinsics.checkNotNullParameter(ktFunction, "ktFunction");
        if (!PsiUtilsKt.hasExpectModifier(ktFunction) && !hasReifiedParameters(ktFunction)) {
            KtFunction ktFunction2 = ktFunction;
            if (!hasAnnotation(ktFunction2, JvmNames.JVM_SYNTHETIC_ANNOTATION_FQ_NAME)) {
                int i = 8;
                int i2 = 0;
                List listMutableListOf = CollectionsKt.mutableListOf(asJavaMethod$default(this, ktFunction, forceStatic, forcePrivate, 0, 8, forceNonFinal, additionalReceiverParameter, 8, null));
                if (hasAnnotation(ktFunction2, JvmNames.INSTANCE.getJVM_OVERLOADS_FQ_NAME())) {
                    List<KtParameter> valueParameters = ktFunction.getValueParameters();
                    Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                    List<KtParameter> list = valueParameters;
                    if (!(list instanceof Collection) || !list.isEmpty()) {
                        Iterator<T> it = list.iterator();
                        while (it.hasNext()) {
                            if (((KtParameter) it.next()).hasDefaultValue() && (i2 = i2 + 1) < 0) {
                                CollectionsKt.throwCountOverflow();
                            }
                        }
                    }
                    for (int i3 = i2 - 1; -1 < i3; i3--) {
                        i++;
                        listMutableListOf.add(asJavaMethod(ktFunction, forceStatic, forcePrivate, i3, i, forceNonFinal, additionalReceiverParameter));
                    }
                }
                return listMutableListOf;
            }
        }
        return CollectionsKt.emptyList();
    }

    /* JADX INFO: compiled from: ultraLightMembersCreator.kt */
    @Metadata(d1 = {"\u0000ø\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\f\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0010\u0019\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0001\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u001e\u001a\u00020\u001f2\r\b\u0001\u0010 \u001a\u00070!¢\u0006\u0002\b\"H\u0096\u0001J\u0018\u0010#\u001a\u00020\u001f2\r\b\u0001\u0010 \u001a\u00070!¢\u0006\u0002\b\"H\u0096\u0001J \u0010$\u001a\n &*\u0004\u0018\u00010%0%2\r\b\u0001\u0010 \u001a\u00070%¢\u0006\u0002\b\"H\u0096\u0001J1\u0010'\u001a\n &*\u0004\u0018\u00010%0%2\r\b\u0001\u0010 \u001a\u00070%¢\u0006\u0002\b\"2\u000f\b\u0001\u0010(\u001a\t\u0018\u00010%¢\u0006\u0002\b)H\u0096\u0001J1\u0010*\u001a\n &*\u0004\u0018\u00010%0%2\r\b\u0001\u0010 \u001a\u00070%¢\u0006\u0002\b\"2\u000f\b\u0001\u0010(\u001a\t\u0018\u00010%¢\u0006\u0002\b)H\u0096\u0001J1\u0010+\u001a\n &*\u0004\u0018\u00010%0%2\u000e\u0010 \u001a\n &*\u0004\u0018\u00010%0%2\u000e\u0010(\u001a\n &*\u0004\u0018\u00010%0%H\u0096\u0001JA\u0010,\u001a\n &*\u0004\u0018\u00010%0%2\u000e\u0010 \u001a\n &*\u0004\u0018\u00010%0%2\u000e\u0010(\u001a\n &*\u0004\u0018\u00010%0%2\u000e\u0010-\u001a\n &*\u0004\u0018\u00010%0%H\u0096\u0001J?\u0010.\u001a\n &*\u0004\u0018\u00010%0%2\r\b\u0001\u0010 \u001a\u00070%¢\u0006\u0002\b\"2\r\b\u0001\u0010(\u001a\u00070%¢\u0006\u0002\b\"2\u000e\u0010-\u001a\n &*\u0004\u0018\u00010%0%H\u0096\u0001J\t\u0010/\u001a\u00020\rH\u0096\u0001J\t\u00100\u001a\u00020\rH\u0096\u0001J\u0018\u00101\u001a\u00020\u001f2\r\b\u0001\u0010 \u001a\u00070%¢\u0006\u0002\b\"H\u0097\u0001J\t\u00102\u001a\u00020\u001fH\u0097\u0001J\u0011\u00103\u001a\n &*\u0004\u0018\u00010%0%H\u0096\u0001J\t\u00104\u001a\u00020\u001fH\u0096\u0001J)\u00105\u001a\u00020\u001f2\u000e\u0010 \u001a\n &*\u0004\u0018\u00010%0%2\u000e\u0010(\u001a\n &*\u0004\u0018\u00010%0%H\u0096\u0001J\u0013\u00106\u001a\u00020\r2\b\u00107\u001a\u0004\u0018\u000108H\u0096\u0002J\u0010\u00109\u001a\t\u0018\u00010:¢\u0006\u0002\b)H\u0097\u0001J<\u0010;\u001a0\u0012\f\u0012\n &*\u0004\u0018\u00010:0: &*\u0017\u0012\u000e\b\u0001\u0012\n &*\u0004\u0018\u00010:0:0<¢\u0006\u0002\b\"0<¢\u0006\u0002\b\"H\u0096\u0001¢\u0006\u0002\u0010=J\u0018\u0010>\u001a\t\u0018\u00010%¢\u0006\u0002\b)2\u0006\u0010 \u001a\u00020?H\u0097\u0001J\u0018\u0010@\u001a\t\u0018\u00010A¢\u0006\u0002\b)2\u0006\u0010 \u001a\u00020?H\u0097\u0001J=\u0010B\u001a.\u0012\f\u0012\n &*\u0004\u0018\u00010D0D &*\u0015\u0012\f\u0012\n &*\u0004\u0018\u00010D0D0\b¢\u0006\u0002\b\"0C¢\u0006\u0002\b\"2\u0006\u0010 \u001a\u00020\rH\u0097\u0001J<\u0010E\u001a0\u0012\f\u0012\n &*\u0004\u0018\u00010:0: &*\u0017\u0012\u000e\b\u0001\u0012\n &*\u0004\u0018\u00010:0:0<¢\u0006\u0002\b\"0<¢\u0006\u0002\b\"H\u0096\u0001¢\u0006\u0002\u0010=JL\u0010E\u001a0\u0012\f\u0012\n &*\u0004\u0018\u00010:0: &*\u0017\u0012\u000e\b\u0001\u0012\n &*\u0004\u0018\u00010:0:0<¢\u0006\u0002\b\"0<¢\u0006\u0002\b\"2\u000e\u0010 \u001a\n &*\u0004\u0018\u00010F0FH\u0096\u0001¢\u0006\u0002\u0010GJD\u0010E\u001a0\u0012\f\u0012\n &*\u0004\u0018\u00010:0: &*\u0017\u0012\u000e\b\u0001\u0012\n &*\u0004\u0018\u00010:0:0<¢\u0006\u0002\b\"0<¢\u0006\u0002\b\"2\u0006\u0010 \u001a\u00020\rH\u0096\u0001¢\u0006\u0002\u0010HJ\u0010\u0010I\u001a\t\u0018\u00010J¢\u0006\u0002\b)H\u0097\u0001J<\u0010K\u001a0\u0012\f\u0012\n &*\u0004\u0018\u00010%0% &*\u0017\u0012\u000e\b\u0001\u0012\n &*\u0004\u0018\u00010%0%0<¢\u0006\u0002\b\"0<¢\u0006\u0002\b\"H\u0097\u0001¢\u0006\u0002\u0010LJ\t\u0010M\u001a\u00020NH\u0096\u0001J\u0011\u0010O\u001a\n &*\u0004\u0018\u00010P0PH\u0097\u0001J\u0010\u0010Q\u001a\t\u0018\u00010%¢\u0006\u0002\b)H\u0097\u0001JD\u0010R\u001a\t\u0018\u0001HS¢\u0006\u0002\b)\"\u0010\b\u0000\u0010S*\n &*\u0004\u0018\u000108082\u001b\b\u0001\u0010 \u001a\u0015\u0012\f\u0012\n &*\u0004\u0018\u0001HSHS0T¢\u0006\u0002\b\"H\u0097\u0001¢\u0006\u0002\u0010UJ\b\u0010V\u001a\u00020\u0019H\u0016J\u0010\u0010W\u001a\t\u0018\u00010X¢\u0006\u0002\b)H\u0097\u0001J\u0011\u0010Y\u001a\n &*\u0004\u0018\u00010%0%H\u0097\u0001J\u000e\u0010Z\u001a\u00070[¢\u0006\u0002\b\"H\u0097\u0001J\u001b\u0010\\\u001a\n &*\u0004\u0018\u00010]0]2\b\b\u0001\u0010 \u001a\u00020?H\u0096\u0001J\u000e\u0010^\u001a\u00070_¢\u0006\u0002\b\"H\u0097\u0001J\u0011\u0010`\u001a\n &*\u0004\u0018\u00010%0%H\u0097\u0001J\u0011\u0010a\u001a\n &*\u0004\u0018\u00010b0bH\u0097\u0001J\u000e\u0010c\u001a\u00070d¢\u0006\u0002\b\"H\u0097\u0001J\u0013\u0010e\u001a\f0f¢\u0006\u0002\b\"¢\u0006\u0002\bgH\u0097\u0001J\u0010\u0010h\u001a\t\u0018\u00010i¢\u0006\u0002\b)H\u0097\u0001J\u000e\u0010j\u001a\u00070%¢\u0006\u0002\b\"H\u0097\u0001J\u0011\u0010k\u001a\n &*\u0004\u0018\u00010%0%H\u0097\u0001J\u0011\u0010l\u001a\n &*\u0004\u0018\u00010m0mH\u0097\u0001J\u0011\u0010n\u001a\n &*\u0004\u0018\u00010%0%H\u0097\u0001J\u000e\u0010o\u001a\u00070p¢\u0006\u0002\b\"H\u0097\u0001J\u0011\u0010q\u001a\n &*\u0004\u0018\u00010%0%H\u0097\u0001J\u0010\u0010r\u001a\t\u0018\u00010s¢\u0006\u0002\b)H\u0097\u0001J\u0011\u0010t\u001a\n &*\u0004\u0018\u00010%0%H\u0097\u0001J\u000e\u0010u\u001a\u00070v¢\u0006\u0002\b\"H\u0097\u0001J\u0010\u0010w\u001a\t\u0018\u00010A¢\u0006\u0002\b)H\u0097\u0001J<\u0010x\u001a0\u0012\f\u0012\n &*\u0004\u0018\u00010A0A &*\u0017\u0012\u000e\b\u0001\u0012\n &*\u0004\u0018\u00010A0A0<¢\u0006\u0002\b\"0<¢\u0006\u0002\b\"H\u0097\u0001¢\u0006\u0002\u0010yJ\u000e\u0010z\u001a\u00070{¢\u0006\u0002\b\"H\u0097\u0001J\u0010\u0010|\u001a\t\u0018\u00010}¢\u0006\u0002\b)H\u0097\u0001J\u0010\u0010~\u001a\t\u0018\u00010\u007f¢\u0006\u0002\b)H\u0097\u0001J \u0010\u0080\u0001\u001a\b0\u0081\u0001¢\u0006\u0002\b\"2\u000e\b\u0001\u0010 \u001a\b0\u0082\u0001¢\u0006\u0002\b\"H\u0097\u0001J\u000b\u0010\u0083\u0001\u001a\u0004\u0018\u00010%H\u0016J\n\u0010\u0084\u0001\u001a\u00020?H\u0097\u0001J\u001c\u0010\u0085\u0001\u001a\u0014 &*\t\u0018\u00010f¢\u0006\u0002\bg0f¢\u0006\u0002\bgH\u0097\u0001J\n\u0010\u0086\u0001\u001a\u00020?H\u0097\u0001J\n\u0010\u0087\u0001\u001a\u00020?H\u0097\u0001J\u0014\u0010\u0088\u0001\u001a\f &*\u0005\u0018\u00010\u0089\u00010\u0089\u0001H\u0097\u0001J\u0010\u0010\u008a\u0001\u001a\b0\u008b\u0001¢\u0006\u0002\b\"H\u0097\u0001J\u0012\u0010\u008c\u0001\u001a\n\u0018\u00010\u008d\u0001¢\u0006\u0002\b)H\u0097\u0001JB\u0010\u008e\u0001\u001a4\u0012\u000e\u0012\f &*\u0005\u0018\u00010\u008f\u00010\u008f\u0001 &*\u0019\u0012\u0010\b\u0001\u0012\f &*\u0005\u0018\u00010\u008f\u00010\u008f\u00010<¢\u0006\u0002\b\"0<¢\u0006\u0002\b\"H\u0096\u0001¢\u0006\u0003\u0010\u0090\u0001J\u0010\u0010\u0091\u0001\u001a\b0\u0092\u0001¢\u0006\u0002\b\"H\u0097\u0001JE\u0010\u0093\u0001\u001a\t\u0018\u0001HS¢\u0006\u0002\b)\"\u0010\b\u0000\u0010S*\n &*\u0004\u0018\u000108082\u001b\b\u0001\u0010 \u001a\u0015\u0012\f\u0012\n &*\u0004\u0018\u0001HSHS0T¢\u0006\u0002\b\"H\u0097\u0001¢\u0006\u0002\u0010UJ\u001f\u0010\u0094\u0001\u001a\u00020\r2\u0013\b\u0001\u0010 \u001a\r0f¢\u0006\u0003\b\u0095\u0001¢\u0006\u0002\b\"H\u0096\u0001J\n\u0010\u0096\u0001\u001a\u00020\rH\u0096\u0001J\t\u0010\u0097\u0001\u001a\u00020?H\u0016J\n\u0010\u0098\u0001\u001a\u00020\rH\u0096\u0001J\n\u0010\u0099\u0001\u001a\u00020\rH\u0096\u0001J\u001a\u0010\u009a\u0001\u001a\u00020\r2\u000e\u0010 \u001a\n &*\u0004\u0018\u00010%0%H\u0097\u0001J\n\u0010\u009b\u0001\u001a\u00020\rH\u0097\u0001J\n\u0010\u009c\u0001\u001a\u00020\rH\u0097\u0001J\n\u0010\u009d\u0001\u001a\u00020\rH\u0096\u0001J\n\u0010\u009e\u0001\u001a\u00020\rH\u0097\u0001J\u0012\u0010\u009f\u0001\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\rH\u0096\u0001JK\u0010 \u0001\u001a\u00020\r2\u000e\b\u0001\u0010 \u001a\b0¡\u0001¢\u0006\u0002\b\"2\u000e\b\u0001\u0010(\u001a\b0¢\u0001¢\u0006\u0002\b\"2\u000f\b\u0001\u0010-\u001a\t\u0018\u00010%¢\u0006\u0002\b)2\u000e\b\u0001\u0010£\u0001\u001a\u00070%¢\u0006\u0002\b\"H\u0096\u0001JP\u0010¤\u0001\u001a\u00020\u001f\"\u0010\b\u0000\u0010S*\n &*\u0004\u0018\u000108082\u001b\b\u0001\u0010 \u001a\u0015\u0012\f\u0012\n &*\u0004\u0018\u0001HSHS0T¢\u0006\u0002\b\"2\u000f\b\u0001\u0010(\u001a\t\u0018\u0001HS¢\u0006\u0002\b)H\u0096\u0001¢\u0006\u0003\u0010¥\u0001JP\u0010¦\u0001\u001a\u00020\u001f\"\u0010\b\u0000\u0010S*\n &*\u0004\u0018\u000108082\u001b\b\u0001\u0010 \u001a\u0015\u0012\f\u0012\n &*\u0004\u0018\u0001HSHS0T¢\u0006\u0002\b\"2\u000f\b\u0001\u0010(\u001a\t\u0018\u0001HS¢\u0006\u0002\b)H\u0096\u0001¢\u0006\u0003\u0010¥\u0001J!\u0010§\u0001\u001a\n &*\u0004\u0018\u00010%0%2\r\b\u0001\u0010 \u001a\u00070%¢\u0006\u0002\b\"H\u0096\u0001J'\u0010¨\u0001\u001a\n &*\u0004\u0018\u00010%0%2\u0013\b\u0001\u0010 \u001a\r0f¢\u0006\u0003\b\u0095\u0001¢\u0006\u0002\b\"H\u0096\u0001J\u0013\u0010©\u0001\u001a\u00020\r2\u0007\u0010 \u001a\u00030ª\u0001H\u0097\u0001J \u0010«\u0001\u001a\u00020\r2\u0014\b\u0001\u0010 \u001a\u000e0¬\u0001¢\u0006\u0002\b\"¢\u0006\u0003\b\u0095\u0001H\u0097\u0001J\u0019\u0010«\u0001\u001a\u00020\r2\r\b\u0001\u0010 \u001a\u00070%¢\u0006\u0002\b\"H\u0097\u0001J\u0010\u0010\u00ad\u0001\u001a\b0®\u0001¢\u0006\u0002\b\"H\u0097\u0001J\t\u0010¯\u0001\u001a\u00020fH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\b8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\f\u0010\u000eR\u0012\u0010\u000f\u001a\u00020\rX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u000eR\u0014\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0003\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0018\u001a\u00020\u00198BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001a\u0010\u001b¨\u0006°\u0001"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/UltraLightMembersCreator$KtUltraLightAnnotationMethod;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotationMethod;", "psiMethod", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "(Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;Lorg/jetbrains/kotlin/psi/KtExpression;)V", "givenAnnotations", "", "Lorg/jetbrains/kotlin/asJava/elements/KtLightAbstractAnnotation;", "getGivenAnnotations", "()Ljava/util/List;", "isDelegated", "", "()Z", "isMangled", "kotlinOrigin", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "getKotlinOrigin", "()Lorg/jetbrains/kotlin/psi/KtDeclaration;", "lightMemberOrigin", "Lorg/jetbrains/kotlin/asJava/builder/LightMemberOrigin;", "getLightMemberOrigin", "()Lorg/jetbrains/kotlin/asJava/builder/LightMemberOrigin;", "value", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotationMemberValue;", "getValue", "()Lcom/intellij/psi/PsiAnnotationMemberValue;", "value$delegate", "Lkotlin/Lazy;", "accept", "", "p0", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElementVisitor;", "Lorg/jetbrains/annotations/NotNull;", "acceptChildren", ProductAction.ACTION_ADD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "addAfter", "p1", "Lorg/jetbrains/annotations/Nullable;", "addBefore", "addRange", "addRangeAfter", "p2", "addRangeBefore", "canNavigate", "canNavigateToSource", "checkAdd", "checkDelete", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "delete", "deleteChildRange", Namer.EQUALS_METHOD_NAME, "other", "", "findDeepestSuperMethod", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMethod;", "findDeepestSuperMethods", "", "()[Lcom/intellij/psi/PsiMethod;", "findElementAt", "", "findReferenceAt", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiReference;", "findSuperMethodSignaturesIncludingStatic", "", "Lorg/jetbrains/kotlin/com/intellij/psi/util/MethodSignatureBackedByPsiMethod;", "findSuperMethods", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "(Lcom/intellij/psi/PsiClass;)[Lcom/intellij/psi/PsiMethod;", "(Z)[Lcom/intellij/psi/PsiMethod;", "getBody", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiCodeBlock;", "getChildren", "()[Lcom/intellij/psi/PsiElement;", "getContainingClass", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;", "getContainingFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "getContext", "getCopyableUserData", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/com/intellij/openapi/util/Key;", "(Lcom/intellij/openapi/util/Key;)Ljava/lang/Object;", "getDefaultValue", "getDocComment", "Lorg/jetbrains/kotlin/com/intellij/psi/javadoc/PsiDocComment;", "getFirstChild", "getHierarchicalMethodSignature", "Lorg/jetbrains/kotlin/com/intellij/psi/HierarchicalMethodSignature;", "getIcon", "Ljavax/swing/Icon;", "getLanguage", "Lorg/jetbrains/kotlin/com/intellij/lang/Language;", "getLastChild", "getManager", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiManager;", "getModifierList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierList;", "getName", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/NlsSafe;", "getNameIdentifier", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiIdentifier;", "getNavigationElement", "getNextSibling", "getNode", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "getOriginalElement", "getParameterList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiParameterList;", "getParent", "getPresentation", "Lorg/jetbrains/kotlin/com/intellij/navigation/ItemPresentation;", "getPrevSibling", "getProject", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "getReference", "getReferences", "()[Lcom/intellij/psi/PsiReference;", "getResolveScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "getReturnType", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "getReturnTypeElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeElement;", "getSignature", "Lorg/jetbrains/kotlin/com/intellij/psi/util/MethodSignature;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiSubstitutor;", "getSourceElement", "getStartOffsetInParent", "getText", "getTextLength", "getTextOffset", "getTextRange", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "getThrowsList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiReferenceList;", "getTypeParameterList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameterList;", "getTypeParameters", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameter;", "()[Lcom/intellij/psi/PsiTypeParameter;", "getUseScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/SearchScope;", "getUserData", "hasModifierProperty", "Lorg/jetbrains/annotations/NonNls;", "hasTypeParameters", "hashCode", "isConstructor", "isDeprecated", "isEquivalentTo", "isPhysical", "isValid", "isVarArgs", "isWritable", "navigate", "processDeclarations", "Lorg/jetbrains/kotlin/com/intellij/psi/scope/PsiScopeProcessor;", "Lorg/jetbrains/kotlin/com/intellij/psi/ResolveState;", "p3", "putCopyableUserData", "(Lcom/intellij/openapi/util/Key;Ljava/lang/Object;)V", "putUserData", SchemaSymbols.ATTVAL_REPLACE, "setName", "textContains", "", "textMatches", "", "textToCharArray", "", "toString", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class KtUltraLightAnnotationMethod implements PsiAnnotationMethod, KtLightMethod {
        private final KtExpression expression;
        private final KtLightMethod psiMethod;

        /* JADX INFO: renamed from: value$delegate, reason: from kotlin metadata */
        private final Lazy value;

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public void accept(PsiElementVisitor p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            this.psiMethod.accept(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public void acceptChildren(PsiElementVisitor p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            this.psiMethod.acceptChildren(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement add(PsiElement p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return this.psiMethod.add(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement addAfter(PsiElement p0, PsiElement p1) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return this.psiMethod.addAfter(p0, p1);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement addBefore(PsiElement p0, PsiElement p1) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return this.psiMethod.addBefore(p0, p1);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement addRange(PsiElement p0, PsiElement p1) {
            return this.psiMethod.addRange(p0, p1);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement addRangeAfter(PsiElement p0, PsiElement p1, PsiElement p2) {
            return this.psiMethod.addRangeAfter(p0, p1, p2);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement addRangeBefore(PsiElement p0, PsiElement p1, PsiElement p2) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p1, "p1");
            return this.psiMethod.addRangeBefore(p0, p1, p2);
        }

        @Override // org.jetbrains.kotlin.com.intellij.pom.Navigatable
        public boolean canNavigate() {
            return this.psiMethod.canNavigate();
        }

        @Override // org.jetbrains.kotlin.com.intellij.pom.Navigatable
        public boolean canNavigateToSource() {
            return this.psiMethod.canNavigateToSource();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        @Deprecated(message = "Deprecated in Java")
        public void checkAdd(PsiElement p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            this.psiMethod.checkAdd(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        @Deprecated(message = "Deprecated in Java")
        public void checkDelete() {
            this.psiMethod.checkDelete();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement copy() {
            return this.psiMethod.copy();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public void delete() {
            this.psiMethod.delete();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public void deleteChildRange(PsiElement p0, PsiElement p1) {
            this.psiMethod.deleteChildRange(p0, p1);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        @Deprecated(message = "Deprecated in Java")
        public PsiMethod findDeepestSuperMethod() {
            return this.psiMethod.findDeepestSuperMethod();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public PsiMethod[] findDeepestSuperMethods() {
            return this.psiMethod.findDeepestSuperMethods();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement findElementAt(int p0) {
            return this.psiMethod.findElementAt(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiReference findReferenceAt(int p0) {
            return this.psiMethod.findReferenceAt(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public List<MethodSignatureBackedByPsiMethod> findSuperMethodSignaturesIncludingStatic(boolean p0) {
            return this.psiMethod.findSuperMethodSignaturesIncludingStatic(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public PsiMethod[] findSuperMethods() {
            return this.psiMethod.findSuperMethods();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public PsiMethod[] findSuperMethods(PsiClass psiClass) {
            return this.psiMethod.findSuperMethods(psiClass);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public PsiMethod[] findSuperMethods(boolean z) {
            return this.psiMethod.findSuperMethods(z);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiParameterListOwner
        public PsiCodeBlock getBody() {
            return this.psiMethod.getBody();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement[] getChildren() {
            return this.psiMethod.getChildren();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMember, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
        public KtLightClass getContainingClass() {
            return (KtLightClass) this.psiMethod.getContainingClass();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiFile getContainingFile() {
            return this.psiMethod.getContainingFile();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement getContext() {
            return this.psiMethod.getContext();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public <T> T getCopyableUserData(Key<T> p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return (T) this.psiMethod.getCopyableUserData(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement
        /* JADX INFO: renamed from: getDocComment */
        public PsiDocComment mo6091getDocComment() {
            return this.psiMethod.mo6091getDocComment();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement getFirstChild() {
            return this.psiMethod.getFirstChild();
        }

        @Override // org.jetbrains.kotlin.asJava.elements.KtLightElement
        public List<KtLightAbstractAnnotation> getGivenAnnotations() {
            return this.psiMethod.getGivenAnnotations();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public HierarchicalMethodSignature getHierarchicalMethodSignature() {
            return this.psiMethod.getHierarchicalMethodSignature();
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Iconable
        public Icon getIcon(int p0) {
            return this.psiMethod.getIcon(p0);
        }

        @Override // org.jetbrains.kotlin.asJava.elements.KtLightElement
        public KtDeclaration getKotlinOrigin() {
            return (KtDeclaration) this.psiMethod.getKotlinOrigin();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public Language getLanguage() {
            return this.psiMethod.getLanguage();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement getLastChild() {
            return this.psiMethod.getLastChild();
        }

        @Override // org.jetbrains.kotlin.asJava.elements.KtLightMember
        public LightMemberOrigin getLightMemberOrigin() {
            return this.psiMethod.getLightMemberOrigin();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiManager getManager() {
            return this.psiMethod.getManager();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
        public PsiModifierList getModifierList() {
            return this.psiMethod.getModifierList();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
        public String getName() {
            return this.psiMethod.getName();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
        /* JADX INFO: renamed from: getNameIdentifier */
        public PsiIdentifier mo7392getNameIdentifier() {
            return this.psiMethod.mo7392getNameIdentifier();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement getNavigationElement() {
            return this.psiMethod.getNavigationElement();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement getNextSibling() {
            return this.psiMethod.getNextSibling();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public ASTNode getNode() {
            return this.psiMethod.getNode();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        /* JADX INFO: renamed from: getOriginalElement */
        public PsiElement getFunDelegate() {
            return this.psiMethod.getFunDelegate();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiParameterListOwner
        public PsiParameterList getParameterList() {
            return this.psiMethod.getParameterList();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        /* JADX INFO: renamed from: getParent */
        public PsiElement getLightParent() {
            return this.psiMethod.getLightParent();
        }

        @Override // org.jetbrains.kotlin.com.intellij.navigation.NavigationItem
        public ItemPresentation getPresentation() {
            return this.psiMethod.getPresentation();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement getPrevSibling() {
            return this.psiMethod.getPrevSibling();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public Project getProject() {
            return this.psiMethod.getProject();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiReference getReference() {
            return this.psiMethod.getReference();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiReference[] getReferences() {
            return this.psiMethod.getReferences();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public GlobalSearchScope getResolveScope() {
            return this.psiMethod.getResolveScope();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public PsiType getReturnType() {
            return this.psiMethod.getReturnType();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public PsiTypeElement getReturnTypeElement() {
            return this.psiMethod.getReturnTypeElement();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public MethodSignature getSignature(PsiSubstitutor p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return this.psiMethod.getSignature(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public int getStartOffsetInParent() {
            return this.psiMethod.getStartOffsetInParent();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public String getText() {
            return this.psiMethod.getText();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public int getTextLength() {
            return this.psiMethod.getTextLength();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public int getTextOffset() {
            return this.psiMethod.getTextOffset();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public TextRange getTextRange() {
            return this.psiMethod.getTextRange();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public PsiReferenceList getThrowsList() {
            return this.psiMethod.getThrowsList();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
        /* JADX INFO: renamed from: getTypeParameterList */
        public PsiTypeParameterList mo6093getTypeParameterList() {
            return this.psiMethod.mo6093getTypeParameterList();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
        public PsiTypeParameter[] getTypeParameters() {
            return this.psiMethod.getTypeParameters();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public SearchScope getUseScope() {
            return this.psiMethod.getUseScope();
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder
        public <T> T getUserData(Key<T> p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return (T) this.psiMethod.getUserData(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
        public boolean hasModifierProperty(@PsiModifier.ModifierConstant String p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return this.psiMethod.hasModifierProperty(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
        public boolean hasTypeParameters() {
            return this.psiMethod.hasTypeParameters();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public boolean isConstructor() {
            return this.psiMethod.isConstructor();
        }

        @Override // org.jetbrains.kotlin.asJava.elements.KtLightMethod
        public boolean isDelegated() {
            return this.psiMethod.isDelegated();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner
        public boolean isDeprecated() {
            return this.psiMethod.isDeprecated();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public boolean isEquivalentTo(PsiElement p0) {
            return this.psiMethod.isEquivalentTo(p0);
        }

        @Override // org.jetbrains.kotlin.asJava.elements.KtLightMethod
        public boolean isMangled() {
            return this.psiMethod.isMangled();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        /* JADX INFO: renamed from: isPhysical */
        public boolean getIsPhysical() {
            return this.psiMethod.getIsPhysical();
        }

        @Override // org.jetbrains.kotlin.com.intellij.pom.PomTarget
        public boolean isValid() {
            return this.psiMethod.isValid();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public boolean isVarArgs() {
            return this.psiMethod.isVarArgs();
        }

        @Override // org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget, org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public boolean isWritable() {
            return this.psiMethod.isWritable();
        }

        @Override // org.jetbrains.kotlin.com.intellij.pom.Navigatable
        public void navigate(boolean p0) {
            this.psiMethod.navigate(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public boolean processDeclarations(PsiScopeProcessor p0, ResolveState p1, PsiElement p2, PsiElement p3) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p1, "p1");
            Intrinsics.checkNotNullParameter(p3, "p3");
            return this.psiMethod.processDeclarations(p0, p1, p2, p3);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public <T> void putCopyableUserData(Key<T> p0, T t) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            this.psiMethod.putCopyableUserData(p0, t);
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder
        public <T> void putUserData(Key<T> p0, T t) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            this.psiMethod.putUserData(p0, t);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement replace(PsiElement p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return this.psiMethod.replace(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
        public PsiElement setName(String p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return this.psiMethod.setName(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public boolean textContains(char p0) {
            return this.psiMethod.textContains(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public boolean textMatches(CharSequence p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return this.psiMethod.textMatches(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public boolean textMatches(PsiElement p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return this.psiMethod.textMatches(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public char[] textToCharArray() {
            return this.psiMethod.textToCharArray();
        }

        public KtUltraLightAnnotationMethod(KtLightMethod psiMethod, KtExpression expression) {
            Intrinsics.checkNotNullParameter(psiMethod, "psiMethod");
            Intrinsics.checkNotNullParameter(expression, "expression");
            this.psiMethod = psiMethod;
            this.expression = expression;
            this.value = ImplUtilsKt.lazyPub(new Function0<PsiAnnotationMemberValue>() { // from class: org.jetbrains.kotlin.asJava.classes.UltraLightMembersCreator$KtUltraLightAnnotationMethod$value$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final PsiAnnotationMemberValue invoke() {
                    return LightAnnotationsKt.convertToLightAnnotationMemberValue(this.this$0.psiMethod, this.this$0.expression);
                }
            });
        }

        private final PsiAnnotationMemberValue getValue() {
            return (PsiAnnotationMemberValue) this.value.getValue();
        }

        public boolean equals(Object other) {
            if (other != this) {
                if (other instanceof KtUltraLightAnnotationMethod) {
                    KtUltraLightAnnotationMethod ktUltraLightAnnotationMethod = (KtUltraLightAnnotationMethod) other;
                    if (!Intrinsics.areEqual(ktUltraLightAnnotationMethod.psiMethod, this.psiMethod) || !Intrinsics.areEqual(ktUltraLightAnnotationMethod.expression, this.expression)) {
                    }
                }
                return false;
            }
            return true;
        }

        public int hashCode() {
            return this.psiMethod.hashCode();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public String toString() {
            return "KtUltraLightAnnotationMethod(method=" + this.psiMethod + ", expression=" + this.expression;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMethod
        public PsiAnnotationMemberValue getDefaultValue() {
            return getValue();
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.jvm.JvmElement, org.jetbrains.kotlin.com.intellij.psi.PsiJvmModifiersOwner
        public PsiElement getSourceElement() {
            return this.psiMethod.getSourceElement();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ KtLightMethod asJavaMethod$default(UltraLightMembersCreator ultraLightMembersCreator, KtFunction ktFunction, boolean z, boolean z2, int i, int i2, boolean z3, Function1 function1, int i3, Object obj) {
        return ultraLightMembersCreator.asJavaMethod(ktFunction, z, z2, (i3 & 8) != 0 ? -1 : i, i2, (i3 & 32) != 0 ? false : z3, (i3 & 64) != 0 ? null : function1);
    }

    private final KtLightMethod asJavaMethod(final KtFunction ktFunction, boolean forceStatic, boolean forcePrivate, int numberOfDefaultParametersToAdd, int methodIndex, boolean forceNonFinal, Function1<? super KtUltraLightMethod, ? extends KtUltraLightParameter> additionalReceiverParameter) {
        String strComputeMethodName;
        ProgressManager.checkCanceled();
        final boolean z = ktFunction instanceof KtConstructor;
        if (z) {
            strComputeMethodName = this.containingClass.getName();
        } else {
            KtFunction ktFunction2 = ktFunction;
            String name2 = ktFunction.getName();
            if (name2 == null) {
                name2 = SpecialNames.NO_NAME_PROVIDED.asString();
            }
            Intrinsics.checkNotNull(name2);
            strComputeMethodName = computeMethodName(ktFunction2, name2, MethodType.REGULAR);
        }
        KtFunction ktFunction3 = ktFunction;
        LightMethodBuilder lightMethodBuilderLightMethod = lightMethod(strComputeMethodName == null ? "" : strComputeMethodName, ktFunction3, forceStatic, forcePrivate, forceNonFinal);
        final KtUltraLightMethodForSourceDeclaration ktUltraLightMethodForSourceDeclaration = new KtUltraLightMethodForSourceDeclaration(lightMethodBuilderLightMethod, ktFunction3, this.support, this.containingClass, methodIndex);
        if (additionalReceiverParameter != null) {
            lightMethodBuilderLightMethod.addParameter(additionalReceiverParameter.invoke(ktUltraLightMethodForSourceDeclaration));
        }
        KtFunction ktFunction4 = ktFunction;
        KtUltraLightMethodForSourceDeclaration ktUltraLightMethodForSourceDeclaration2 = ktUltraLightMethodForSourceDeclaration;
        addReceiverParameter(ktFunction4, ktUltraLightMethodForSourceDeclaration2, lightMethodBuilderLightMethod);
        int size = numberOfDefaultParametersToAdd >= 0 ? numberOfDefaultParametersToAdd : ktFunction.getValueParameters().size();
        for (KtParameter ktParameter : ktFunction.getValueParameters()) {
            if (ktParameter.hasDefaultValue()) {
                if (size != 0) {
                    size--;
                }
            }
            String str = ktParameter.getName();
            if (str == null) {
                str = "";
            }
            Intrinsics.checkNotNull(ktParameter);
            lightMethodBuilderLightMethod.addParameter(new KtUltraLightParameterForSource(str, ktParameter, this.support, ktUltraLightMethodForSourceDeclaration2, ktFunction4));
        }
        KtModifierList modifierList = ktFunction.getModifierList();
        boolean z2 = false;
        if (modifierList != null && PsiUtilsKt.hasSuspendModifier(modifierList)) {
            z2 = true;
        }
        if (z2) {
            lightMethodBuilderLightMethod.addParameter(new KtUltraLightSuspendContinuationParameter(ktFunction, this.support, ktUltraLightMethodForSourceDeclaration));
        }
        final boolean z3 = z2;
        final Lazy lazyLazyPub = ImplUtilsKt.lazyPub(new Function0<PsiType>() { // from class: org.jetbrains.kotlin.asJava.classes.UltraLightMembersCreator$asJavaMethod$returnType$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiType invoke() {
                if (z) {
                    return null;
                }
                return this.methodReturnType(ktFunction, ktUltraLightMethodForSourceDeclaration, z3);
            }
        });
        lightMethodBuilderLightMethod.setMethodReturnType(new Computable() { // from class: org.jetbrains.kotlin.asJava.classes.UltraLightMembersCreator$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return UltraLightMembersCreator.asJavaMethod$lambda$4(lazyLazyPub);
            }
        });
        return ktUltraLightMethodForSourceDeclaration;
    }

    private static final PsiType asJavaMethod$lambda$3(Lazy<? extends PsiType> lazy) {
        return lazy.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final PsiType asJavaMethod$lambda$4(Lazy returnType$delegate) {
        Intrinsics.checkNotNullParameter(returnType$delegate, "$returnType$delegate");
        return asJavaMethod$lambda$3(returnType$delegate);
    }

    private final void addReceiverParameter(KtCallableDeclaration callable, KtUltraLightMethod wrapper, LightMethodBuilder associatedBuilder) {
        if (callable.mo7393getReceiverTypeReference() == null) {
            return;
        }
        if (!Intrinsics.areEqual(wrapper.getDelegate(), associatedBuilder)) {
            throw new IllegalArgumentException("Invalid use. Wrapper does not wrap an associated method builder.".toString());
        }
        associatedBuilder.addParameter(new KtUltraLightReceiverParameter(callable, this.support, wrapper));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final PsiType methodReturnType(KtDeclaration ktDeclaration, KtUltraLightMethod wrapper, boolean isSuspendFunction) {
        if (isSuspendFunction) {
            SimpleType nullableAnyType = this.support.getModuleDescriptor().getBuiltIns().getNullableAnyType();
            Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getNullableAnyType(...)");
            return UltraLightUtilsKt.asPsiType(nullableAnyType, this.support, TypeMappingMode.DEFAULT, wrapper);
        }
        if (ktDeclaration instanceof KtNamedFunction) {
            KtNamedFunction ktNamedFunction = (KtNamedFunction) ktDeclaration;
            if (ktNamedFunction.hasBlockBody() && !ktNamedFunction.hasDeclaredReturnType()) {
                PsiPrimitiveType VOID = PsiType.VOID;
                Intrinsics.checkNotNullExpressionValue(VOID, "VOID");
                return VOID;
            }
        }
        DeclarationDescriptor declarationDescriptorResolve = UltraLightUtilsKt.resolve(ktDeclaration);
        DeclarationDescriptor declarationDescriptor = declarationDescriptorResolve != null ? getterIfProperty(declarationDescriptorResolve) : null;
        final CallableDescriptor callableDescriptor = declarationDescriptor instanceof CallableDescriptor ? (CallableDescriptor) declarationDescriptor : null;
        if (callableDescriptor == null) {
            PsiPrimitiveType NULL = PsiType.NULL;
            Intrinsics.checkNotNullExpressionValue(NULL, "NULL");
            return NULL;
        }
        return UltraLightUtilsKt.mapType(this.support, callableDescriptor.getReturnType(), wrapper, new Function2<KotlinTypeMapper, JvmSignatureWriter, Unit>() { // from class: org.jetbrains.kotlin.asJava.classes.UltraLightMembersCreator.methodReturnType.1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(KotlinTypeMapper kotlinTypeMapper, JvmSignatureWriter jvmSignatureWriter) {
                invoke2(kotlinTypeMapper, jvmSignatureWriter);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(KotlinTypeMapper typeMapper, JvmSignatureWriter signatureWriter) {
                Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
                Intrinsics.checkNotNullParameter(signatureWriter, "signatureWriter");
                typeMapper.mapReturnType(callableDescriptor, signatureWriter);
            }
        });
    }

    private final DeclarationDescriptor getterIfProperty(DeclarationDescriptor declarationDescriptor) {
        return declarationDescriptor instanceof PropertyDescriptor ? ((PropertyDescriptor) declarationDescriptor).getGetter() : declarationDescriptor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: ultraLightMembersCreator.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0082\u0004\u0018\u00002\u00020\u0001B;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\n\u001a\u00020\b¢\u0006\u0002\u0010\u000bJ\u0010\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u000f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\f\u0010\u0010\u001a\u00020\b*\u00020\u0003H\u0002J\f\u0010\u0011\u001a\u00020\b*\u00020\u0003H\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/UltraLightMembersCreator$UltraLightModifierListForMember;", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/light/LightModifierList;", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "accessedProperty", "Lorg/jetbrains/kotlin/psi/KtProperty;", "outerDeclaration", "forceStatic", "", "forcePrivate", "forceNonFinal", "(Lorg/jetbrains/kotlin/asJava/classes/UltraLightMembersCreator;Lorg/jetbrains/kotlin/psi/KtDeclaration;Lorg/jetbrains/kotlin/psi/KtProperty;Lorg/jetbrains/kotlin/psi/KtDeclaration;ZZZ)V", "hasModifier", "name", "", "hasModifierProperty", "isInlineOnly", "isPrivate", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class UltraLightModifierListForMember extends LightModifierList {
        private final KtProperty accessedProperty;
        private final KtDeclaration declaration;
        private final boolean forceNonFinal;
        private final boolean forcePrivate;
        private final boolean forceStatic;
        private final KtDeclaration outerDeclaration;
        final /* synthetic */ UltraLightMembersCreator this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public UltraLightModifierListForMember(UltraLightMembersCreator ultraLightMembersCreator, KtDeclaration declaration, KtProperty ktProperty, KtDeclaration outerDeclaration, boolean z, boolean z2, boolean z3) {
            super(declaration.getManager(), declaration.getLanguage(), new String[0]);
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(outerDeclaration, "outerDeclaration");
            this.this$0 = ultraLightMembersCreator;
            this.declaration = declaration;
            this.accessedProperty = ktProperty;
            this.outerDeclaration = outerDeclaration;
            this.forceStatic = z;
            this.forcePrivate = z2;
            this.forceNonFinal = z3;
        }

        public /* synthetic */ UltraLightModifierListForMember(UltraLightMembersCreator ultraLightMembersCreator, KtDeclaration ktDeclaration, KtProperty ktProperty, KtDeclaration ktDeclaration2, boolean z, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(ultraLightMembersCreator, ktDeclaration, ktProperty, ktDeclaration2, z, (i & 16) != 0 ? false : z2, (i & 32) != 0 ? false : z3);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightModifierList, org.jetbrains.kotlin.com.intellij.psi.PsiModifierList
        public boolean hasModifierProperty(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            boolean zHasModifier = hasModifier(name2);
            if (!Intrinsics.areEqual(name2, "final")) {
                return zHasModifier;
            }
            if (!zHasModifier) {
                return false;
            }
            Lazy<? extends DeclarationDescriptor> lazy = LazyKt.lazy(new Function0<DeclarationDescriptor>() { // from class: org.jetbrains.kotlin.asJava.classes.UltraLightMembersCreator$UltraLightModifierListForMember$hasModifierProperty$descriptor$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final DeclarationDescriptor invoke() {
                    return UltraLightUtilsKt.resolve(this.this$0.declaration);
                }
            });
            Project project = getProject();
            Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
            Iterator<T> it = UltraLightClassModifierExtension.INSTANCE.getInstances(project).iterator();
            String strInterceptModalityBuilding = "final";
            while (it.hasNext()) {
                strInterceptModalityBuilding = ((UltraLightClassModifierExtension) it.next()).interceptModalityBuilding(this.declaration, lazy, strInterceptModalityBuilding);
            }
            return Intrinsics.areEqual(strInterceptModalityBuilding, "final");
        }

        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        private final boolean hasModifier(String name2) {
            KtProperty ktProperty;
            KtProperty ktProperty2;
            int iHashCode = name2.hashCode();
            if (iHashCode == -977423767 ? name2.equals("public") : iHashCode == -608539730 ? name2.equals("protected") : iHashCode == -314497661 && name2.equals("private")) {
                if (this.forcePrivate || isPrivate(this.declaration) || ((ktProperty = this.accessedProperty) != null && isPrivate(ktProperty))) {
                    return Intrinsics.areEqual(name2, "private");
                }
                if (this.declaration.hasModifier(KtTokens.PROTECTED_KEYWORD) || (((ktProperty2 = this.accessedProperty) != null && ktProperty2.hasModifier(KtTokens.PROTECTED_KEYWORD)) || ((this.declaration instanceof KtConstructor) && this.this$0.containingClassIsSealed))) {
                    return Intrinsics.areEqual(name2, "protected");
                }
                if (this.outerDeclaration.hasModifier(KtTokens.OVERRIDE_KEYWORD)) {
                    DeclarationDescriptor declarationDescriptorResolve = UltraLightUtilsKt.resolve(this.outerDeclaration);
                    CallableDescriptor callableDescriptor = declarationDescriptorResolve instanceof CallableDescriptor ? (CallableDescriptor) declarationDescriptorResolve : null;
                    DescriptorVisibility visibility = callableDescriptor != null ? callableDescriptor.getVisibilityImpl() : null;
                    if (Intrinsics.areEqual(visibility, DescriptorVisibilities.PUBLIC)) {
                        return Intrinsics.areEqual(name2, "public");
                    }
                    if (Intrinsics.areEqual(visibility, DescriptorVisibilities.PRIVATE)) {
                        return Intrinsics.areEqual(name2, "private");
                    }
                    if (Intrinsics.areEqual(visibility, DescriptorVisibilities.PROTECTED)) {
                        return Intrinsics.areEqual(name2, "protected");
                    }
                }
                return Intrinsics.areEqual(name2, "public");
            }
            switch (name2.hashCode()) {
                case -1466596076:
                    if (!name2.equals("synchronized")) {
                        return false;
                    }
                    KtDeclaration ktDeclaration = this.declaration;
                    return (ktDeclaration instanceof KtFunction) && this.this$0.hasAnnotation(ktDeclaration, JvmNames.SYNCHRONIZED_ANNOTATION_FQ_NAME);
                case -1052618729:
                    if (!name2.equals("native")) {
                        return false;
                    }
                    KtDeclaration ktDeclaration2 = this.declaration;
                    return (ktDeclaration2 instanceof KtFunction) && ktDeclaration2.hasModifier(KtTokens.EXTERNAL_KEYWORD);
                case -892481938:
                    if (!name2.equals("static")) {
                        return false;
                    }
                    if (this.forceStatic) {
                        return true;
                    }
                    if (this.this$0.containingClassIsNamedObject) {
                        return UltraLightUtilsKt.isJvmStatic(this.outerDeclaration, this.this$0.support) || UltraLightUtilsKt.isJvmStatic(this.declaration, this.this$0.support);
                    }
                    return false;
                case 97436022:
                    if (!name2.equals("final") || this.forceNonFinal || this.this$0.containingClass.isInterface()) {
                        return false;
                    }
                    KtDeclaration ktDeclaration3 = this.outerDeclaration;
                    return !(ktDeclaration3 instanceof KtConstructor) && this.this$0.isFinal(ktDeclaration3);
                case 1732898850:
                    if (name2.equals("abstract")) {
                        return this.this$0.containingClass.isInterface() || this.outerDeclaration.hasModifier(KtTokens.ABSTRACT_KEYWORD);
                    }
                    return false;
                case 1794694483:
                    if (!name2.equals("strictfp")) {
                        return false;
                    }
                    KtDeclaration ktDeclaration4 = this.declaration;
                    return (ktDeclaration4 instanceof KtFunction) && this.this$0.hasAnnotation(ktDeclaration4, JvmNames.STRICTFP_ANNOTATION_FQ_NAME);
                default:
                    return false;
            }
        }

        private final boolean isPrivate(KtDeclaration ktDeclaration) {
            return ktDeclaration.hasModifier(KtTokens.PRIVATE_KEYWORD) || isInlineOnly(ktDeclaration);
        }

        private final boolean isInlineOnly(KtDeclaration ktDeclaration) {
            if (!(ktDeclaration instanceof KtCallableDeclaration) || !ktDeclaration.hasModifier(KtTokens.INLINE_KEYWORD) || ((KtCallableDeclaration) ktDeclaration).getAnnotationEntries().isEmpty()) {
                return false;
            }
            DeclarationDescriptor declarationDescriptorResolve = UltraLightUtilsKt.resolve(ktDeclaration);
            CallableMemberDescriptor callableMemberDescriptor = declarationDescriptorResolve instanceof CallableMemberDescriptor ? (CallableMemberDescriptor) declarationDescriptorResolve : null;
            if (callableMemberDescriptor == null) {
                return false;
            }
            return InlineOnlyKt.isInlineOnly(callableMemberDescriptor);
        }
    }

    static /* synthetic */ LightMethodBuilder lightMethod$default(UltraLightMembersCreator ultraLightMembersCreator, String str, KtDeclaration ktDeclaration, boolean z, boolean z2, boolean z3, int i, Object obj) {
        return ultraLightMembersCreator.lightMethod(str, ktDeclaration, z, (i & 8) != 0 ? false : z2, (i & 16) != 0 ? false : z3);
    }

    private final LightMethodBuilder lightMethod(String name2, KtDeclaration declaration, boolean forceStatic, boolean forcePrivate, boolean forceNonFinal) {
        KtProperty property = declaration instanceof KtPropertyAccessor ? ((KtPropertyAccessor) declaration).getProperty() : null;
        KtDeclaration ktDeclaration = property != null ? property : declaration;
        PsiManager manager = declaration.getManager();
        Language language = declaration.getLanguage();
        Intrinsics.checkNotNullExpressionValue(language, "getLanguage(...)");
        LightMethodBuilder constructor = new LightMethodBuilder(manager, language, name2, new LightParameterListBuilder(manager, language), new UltraLightModifierListForMember(this, declaration, property, ktDeclaration, forceStatic, forcePrivate, forceNonFinal)).setConstructor(declaration instanceof KtConstructor);
        Intrinsics.checkNotNullExpressionValue(constructor, "setConstructor(...)");
        return constructor;
    }

    private static final String computeMethodName$tryCompute(UltraLightMembersCreator ultraLightMembersCreator, KtDeclaration ktDeclaration, MethodType methodType) {
        PropertyGetterDescriptor getter;
        FqName JVM_NAME = DescriptorUtils.JVM_NAME;
        Intrinsics.checkNotNullExpressionValue(JVM_NAME, "JVM_NAME");
        if (!ultraLightMembersCreator.hasAnnotation(ktDeclaration, JVM_NAME)) {
            return null;
        }
        DeclarationDescriptor declarationDescriptorResolve = UltraLightUtilsKt.resolve(ktDeclaration);
        DeclarationDescriptor declarationDescriptor = declarationDescriptorResolve instanceof Annotated ? declarationDescriptorResolve : null;
        if (declarationDescriptor == null) {
            return null;
        }
        String jvmName = DescriptorUtils.getJvmName(declarationDescriptor);
        if (jvmName != null || methodType == MethodType.REGULAR) {
            return jvmName;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[methodType.ordinal()];
        if (i == 1) {
            PropertyDescriptor propertyDescriptor = declarationDescriptor instanceof PropertyDescriptor ? (PropertyDescriptor) declarationDescriptor : null;
            getter = propertyDescriptor != null ? propertyDescriptor.getGetter() : null;
        } else if (i == 2) {
            PropertyDescriptor propertyDescriptor2 = declarationDescriptor instanceof PropertyDescriptor ? (PropertyDescriptor) declarationDescriptor : null;
            getter = propertyDescriptor2 != null ? propertyDescriptor2.getSetter() : null;
        } else {
            throw new NotImplementedError(null, 1, null);
        }
        if (getter != null) {
            return DescriptorUtils.getJvmName(getter);
        }
        return null;
    }

    private final String computeMethodName(KtDeclaration declaration, String name2, MethodType type) {
        String strComputeMethodName$tryCompute = computeMethodName$tryCompute(this, declaration, type);
        if (strComputeMethodName$tryCompute != null) {
            return strComputeMethodName$tryCompute;
        }
        return (this.mangleInternalFunctions && isInternalNonPublishedApi(declaration)) ? KotlinTypeMapper.InternalNameMapper.INSTANCE.mangleInternalName(name2, this.support.getModuleName()) : name2;
    }

    private final boolean isInternalNonPublishedApi(KtDeclaration declaration) {
        KtProperty property;
        DeclarationDescriptor declarationDescriptorResolve;
        while (!declaration.hasModifier(KtTokens.PRIVATE_KEYWORD) && !declaration.hasModifier(KtTokens.PROTECTED_KEYWORD) && !declaration.hasModifier(KtTokens.PUBLIC_KEYWORD)) {
            if (isInternal(declaration) && ((declarationDescriptorResolve = UltraLightUtilsKt.resolve(declaration)) == null || !DescriptorUtilsKt.isPublishedApi(declarationDescriptorResolve))) {
                return true;
            }
            KtPropertyAccessor ktPropertyAccessor = declaration instanceof KtPropertyAccessor ? (KtPropertyAccessor) declaration : null;
            if (ktPropertyAccessor == null || (property = ktPropertyAccessor.getProperty()) == null) {
                break;
            }
            declaration = property;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean hasAnnotation(KtAnnotated ktAnnotated, FqName fqName) {
        return UltraLightUtilsKt.findAnnotation(this.support, ktAnnotated, fqName) != null;
    }

    private final boolean isInternal(KtDeclaration f) {
        if (f.hasModifier(KtTokens.OVERRIDE_KEYWORD)) {
            DeclarationDescriptor declarationDescriptorResolve = UltraLightUtilsKt.resolve(f);
            if (!(declarationDescriptorResolve instanceof CallableDescriptor)) {
                return false;
            }
            DescriptorVisibility visibility = ((CallableDescriptor) declarationDescriptorResolve).getVisibilityImpl();
            Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
            return Intrinsics.areEqual(EffectiveVisibilityUtilsKt.effectiveVisibility(visibility, declarationDescriptorResolve, false), EffectiveVisibility.Internal.INSTANCE);
        }
        return f.hasModifier(KtTokens.INTERNAL_KEYWORD);
    }

    public final List<KtLightMethod> propertyAccessors(final KtCallableDeclaration declaration, boolean mutable, boolean forceStatic, boolean onlyJvmStatic, boolean createAsAnnotationMethod, boolean isJvmRecord, boolean forceNonFinal, Function1<? super KtUltraLightMethod, ? extends KtUltraLightParameter> additionalReceiverParameter) {
        ArrayList arrayList;
        KtUltraLightParameterForSetterParameter ktUltraLightParameterForSetterParameter;
        KtUltraLightParameter ktUltraLightParameterInvoke;
        KtUltraLightMethodForSourceDeclaration ktUltraLightMethodForSourceDeclaration;
        KtUltraLightParameter ktUltraLightParameterInvoke2;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        String name2 = declaration.getName();
        if (name2 == null) {
            return CollectionsKt.emptyList();
        }
        if (isConstOrJvmField(declaration) || hasReifiedParameters(declaration) || PsiUtilsKt.hasExpectModifier(declaration)) {
            return CollectionsKt.emptyList();
        }
        boolean z = declaration instanceof KtProperty;
        KtProperty ktProperty = z ? (KtProperty) declaration : null;
        KtPropertyAccessor getter = ktProperty != null ? ktProperty.getGetter() : null;
        KtProperty ktProperty2 = z ? (KtProperty) declaration : null;
        KtPropertyAccessor setter = ktProperty2 != null ? ktProperty2.getSetter() : null;
        boolean z2 = !forceStatic && declaration.hasModifier(KtTokens.PRIVATE_KEYWORD);
        if (z2 && !z) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList2 = new ArrayList();
        if (propertyAccessors$needsAccessor(onlyJvmStatic, declaration, this, z2, getter, MethodType.GETTER)) {
            KtDeclaration ktDeclaration = getter != null ? getter : declaration;
            LightMemberOriginForDeclaration lightMemberOriginForDeclaration = new LightMemberOriginForDeclaration(declaration, JvmDeclarationOriginKind.OTHER, null, ktDeclaration, 4, null);
            arrayList = arrayList2;
            LightMethodBuilder lightMethodBuilderLightMethod$default = lightMethod$default(this, computeMethodName(ktDeclaration, (createAsAnnotationMethod || isJvmRecord) ? name2 : JvmAbi.getterName(name2), MethodType.GETTER), ktDeclaration, onlyJvmStatic || forceStatic, false, forceNonFinal, 8, null);
            final KtUltraLightMethodForSourceDeclaration ktUltraLightMethodForSourceDeclaration2 = new KtUltraLightMethodForSourceDeclaration(lightMethodBuilderLightMethod$default, lightMemberOriginForDeclaration, this.support, this.containingClass, createAsAnnotationMethod, 1);
            final Lazy lazyLazyPub = ImplUtilsKt.lazyPub(new Function0<PsiType>() { // from class: org.jetbrains.kotlin.asJava.classes.UltraLightMembersCreator$propertyAccessors$getterType$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final PsiType invoke() {
                    return this.this$0.methodReturnType(declaration, ktUltraLightMethodForSourceDeclaration2, false);
                }
            });
            lightMethodBuilderLightMethod$default.setMethodReturnType(new Computable() { // from class: org.jetbrains.kotlin.asJava.classes.UltraLightMembersCreator$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
                public final Object compute() {
                    return UltraLightMembersCreator.propertyAccessors$lambda$8(lazyLazyPub);
                }
            });
            if (additionalReceiverParameter != null && (ktUltraLightParameterInvoke2 = additionalReceiverParameter.invoke(ktUltraLightMethodForSourceDeclaration2)) != null) {
                lightMethodBuilderLightMethod$default.addParameter(ktUltraLightParameterInvoke2);
            }
            addReceiverParameter(declaration, ktUltraLightMethodForSourceDeclaration2, lightMethodBuilderLightMethod$default);
            KtExpression defaultValue = (createAsAnnotationMethod && (declaration instanceof KtParameter)) ? ((KtParameter) declaration).getDefaultValue() : null;
            if (defaultValue != null) {
                ktUltraLightMethodForSourceDeclaration = (KtLightMethod) new KtUltraLightAnnotationMethod(ktUltraLightMethodForSourceDeclaration2, defaultValue);
            } else {
                ktUltraLightMethodForSourceDeclaration = ktUltraLightMethodForSourceDeclaration2;
            }
            arrayList.add(ktUltraLightMethodForSourceDeclaration);
        } else {
            arrayList = arrayList2;
        }
        if (!createAsAnnotationMethod && mutable) {
            if (propertyAccessors$needsAccessor(onlyJvmStatic, declaration, this, z2, setter, MethodType.SETTER)) {
                KtDeclaration ktDeclaration2 = setter != null ? setter : declaration;
                KtCallableDeclaration ktCallableDeclaration = declaration;
                LightMemberOriginForDeclaration lightMemberOriginForDeclaration2 = new LightMemberOriginForDeclaration(ktCallableDeclaration, JvmDeclarationOriginKind.OTHER, null, ktDeclaration2, 4, null);
                LightMethodBuilder methodReturnType = lightMethod$default(this, computeMethodName(ktDeclaration2, JvmAbi.setterName(name2), MethodType.SETTER), ktDeclaration2, onlyJvmStatic || forceStatic, false, forceNonFinal, 8, null).setMethodReturnType(PsiType.VOID);
                Intrinsics.checkNotNull(methodReturnType);
                KtUltraLightMethodForSourceDeclaration ktUltraLightMethodForSourceDeclaration3 = new KtUltraLightMethodForSourceDeclaration(methodReturnType, lightMemberOriginForDeclaration2, this.support, this.containingClass, false, 2, 16, null);
                if (additionalReceiverParameter != null && (ktUltraLightParameterInvoke = additionalReceiverParameter.invoke(ktUltraLightMethodForSourceDeclaration3)) != null) {
                    methodReturnType.addParameter(ktUltraLightParameterInvoke);
                }
                KtUltraLightMethodForSourceDeclaration ktUltraLightMethodForSourceDeclaration4 = ktUltraLightMethodForSourceDeclaration3;
                addReceiverParameter(declaration, ktUltraLightMethodForSourceDeclaration4, methodReturnType);
                KtParameter parameter = setter != null ? setter.getParameter() : null;
                if (parameter != null) {
                    String str = parameter.getName();
                    if (str != null) {
                        name2 = str;
                    }
                    ktUltraLightParameterForSetterParameter = new KtUltraLightParameterForSource(name2, parameter, this.support, ktUltraLightMethodForSourceDeclaration4, declaration);
                } else {
                    String strAsString = SpecialNames.IMPLICIT_SET_PARAMETER.asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    ktUltraLightParameterForSetterParameter = new KtUltraLightParameterForSetterParameter(strAsString, ktCallableDeclaration, this.support, ktUltraLightMethodForSourceDeclaration4, declaration);
                }
                methodReturnType.addParameter(ktUltraLightParameterForSetterParameter);
                arrayList.add(ktUltraLightMethodForSourceDeclaration3);
            }
        }
        return arrayList;
    }

    private static final boolean propertyAccessors$needsAccessor(boolean z, KtCallableDeclaration ktCallableDeclaration, UltraLightMembersCreator ultraLightMembersCreator, boolean z2, KtPropertyAccessor ktPropertyAccessor, MethodType methodType) {
        PropertyGetterDescriptor getter;
        boolean z3 = false;
        if (z && !UltraLightUtilsKt.isJvmStatic(ktCallableDeclaration, ultraLightMembersCreator.support) && (ktPropertyAccessor == null || !UltraLightUtilsKt.isJvmStatic(ktPropertyAccessor, ultraLightMembersCreator.support))) {
            return false;
        }
        if ((ktCallableDeclaration instanceof KtProperty) && ((KtProperty) ktCallableDeclaration).hasDelegate()) {
            return true;
        }
        if ((ktPropertyAccessor == null || !ktPropertyAccessor.hasBody()) && ((ktPropertyAccessor != null && ktPropertyAccessor.hasModifier(KtTokens.PRIVATE_KEYWORD)) || ((ktPropertyAccessor != null && ultraLightMembersCreator.hasAnnotation(ktPropertyAccessor, JvmNames.JVM_SYNTHETIC_ANNOTATION_FQ_NAME)) || z2))) {
            return false;
        }
        if (!ultraLightMembersCreator.hasAnnotation(ktCallableDeclaration, JvmNames.JVM_SYNTHETIC_ANNOTATION_FQ_NAME)) {
            return true;
        }
        DeclarationDescriptor declarationDescriptorResolve = UltraLightUtilsKt.resolve(ktCallableDeclaration);
        PropertyDescriptor propertyDescriptor = declarationDescriptorResolve instanceof PropertyDescriptor ? (PropertyDescriptor) declarationDescriptorResolve : null;
        if (propertyDescriptor == null) {
            return false;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[methodType.ordinal()];
        if (i == 1) {
            getter = propertyDescriptor.getGetter();
        } else if (i == 2) {
            getter = propertyDescriptor.getSetter();
        } else {
            throw new NotImplementedError(null, 1, null);
        }
        if (getter != null && JvmAnnotationUtilKt.hasJvmSyntheticAnnotation(getter)) {
            z3 = true;
        }
        return !z3;
    }

    private static final PsiType propertyAccessors$lambda$7(Lazy<? extends PsiType> lazy) {
        return lazy.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final PsiType propertyAccessors$lambda$8(Lazy getterType$delegate) {
        Intrinsics.checkNotNullParameter(getterType$delegate, "$getterType$delegate");
        return propertyAccessors$lambda$7(getterType$delegate);
    }

    private final boolean hasReifiedParameters(KtCallableDeclaration ktCallableDeclaration) {
        List<KtTypeParameter> typeParameters = ktCallableDeclaration.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        List<KtTypeParameter> list = typeParameters;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (((KtTypeParameter) it.next()).hasModifier(KtTokens.REIFIED_KEYWORD)) {
                return true;
            }
        }
        return false;
    }

    private final boolean isConstOrJvmField(KtCallableDeclaration ktCallableDeclaration) {
        return ktCallableDeclaration.hasModifier(KtTokens.CONST_KEYWORD) || isJvmField(ktCallableDeclaration);
    }

    private final boolean isJvmField(KtCallableDeclaration ktCallableDeclaration) {
        return hasAnnotation(ktCallableDeclaration, JvmAbi.JVM_FIELD_ANNOTATION_FQ_NAME);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isFinal(KtDeclaration declaration) {
        if (declaration.hasModifier(KtTokens.FINAL_KEYWORD)) {
            return true;
        }
        return ((declaration instanceof KtPropertyAccessor) || declaration.hasModifier(KtTokens.OPEN_KEYWORD) || declaration.hasModifier(KtTokens.OVERRIDE_KEYWORD) || declaration.hasModifier(KtTokens.ABSTRACT_KEYWORD)) ? false : true;
    }
}
