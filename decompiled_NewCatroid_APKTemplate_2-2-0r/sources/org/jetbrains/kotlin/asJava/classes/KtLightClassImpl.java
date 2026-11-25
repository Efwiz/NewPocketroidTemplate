package org.jetbrains.kotlin.asJava.classes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.asJava.LightClassGenerationSupport;
import org.jetbrains.kotlin.asJava.LightClassUtilsKt;
import org.jetbrains.kotlin.asJava.UltraLightClassModifierExtension;
import org.jetbrains.kotlin.asJava.elements.FakeFileForLightClass;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.InheritanceImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.psi.KtClassBody;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtConstructor;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: KtLightClassImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\f\b&\u0018\u0000 *2\u00020\u0001:\u0001*B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0012\u001a\u00020\u000eH\u0014J\u000e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0014J\b\u0010\u0016\u001a\u00020\u0017H&J\b\u0010\u0018\u001a\u00020\u0019H$J\n\u0010\u001a\u001a\u0004\u0018\u00010\u0019H\u0016J\n\u0010\u001b\u001a\u0004\u0018\u00010\bH\u0016J\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dJ\u000e\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00190\u001fH\u0016J\n\u0010 \u001a\u0004\u0018\u00010\u0017H\u0016J\n\u0010!\u001a\u0004\u0018\u00010\u0015H\u0016J\b\u0010\"\u001a\u00020\u000eH\u0002J\b\u0010#\u001a\u00020\u000eH\u0002J\b\u0010$\u001a\u00020\u000eH\u0002J\b\u0010%\u001a\u00020\u000eH\u0016J\u0018\u0010&\u001a\u00020\u000e2\u0006\u0010'\u001a\u00020\u00192\u0006\u0010(\u001a\u00020\u000eH\u0016J\b\u0010)\u001a\u00020\u000eH\u0002R\u001b\u0010\u0007\u001a\u00020\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\f\u001a\u0004\b\u000f\u0010\u0010¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtLightClassImpl;", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClassForSourceDeclaration;", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "jvmDefaultMode", "Lorg/jetbrains/kotlin/config/JvmDefaultMode;", "(Lorg/jetbrains/kotlin/psi/KtClassOrObject;Lorg/jetbrains/kotlin/config/JvmDefaultMode;)V", "_containingFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "get_containingFile", "()Lcom/intellij/psi/PsiFile;", "_containingFile$delegate", "Lkotlin/Lazy;", "_deprecated", "", "get_deprecated", "()Z", "_deprecated$delegate", "computeIsFinal", "computeModifiersByPsi", "", "", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "createClassForInterfaceDefaultImpls", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "getContainingClass", "getContainingFile", "getDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getOwnInnerClasses", "", "getParent", "getQualifiedName", "hasAbstractMember", "hasEnumEntryWhichRequiresSubclass", "isAbstract", "isDeprecated", "isInheritor", "baseClass", "checkDeep", "isSealed", "Companion", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtLightClassImpl extends KtLightClassForSourceDeclaration {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final List<Pair<KtModifierKeywordToken, String>> ktTokenToPsiModifier = CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to(KtTokens.PUBLIC_KEYWORD, "public"), TuplesKt.to(KtTokens.INTERNAL_KEYWORD, "public"), TuplesKt.to(KtTokens.PROTECTED_KEYWORD, "protected")});

    /* JADX INFO: renamed from: _containingFile$delegate, reason: from kotlin metadata */
    private final Lazy _containingFile;

    /* JADX INFO: renamed from: _deprecated$delegate, reason: from kotlin metadata */
    private final Lazy _deprecated;

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public abstract PsiElement copy();

    protected abstract PsiClass createClassForInterfaceDefaultImpls();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtLightClassImpl(final KtClassOrObject classOrObject, JvmDefaultMode jvmDefaultMode) {
        super(classOrObject, jvmDefaultMode);
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        Intrinsics.checkNotNullParameter(jvmDefaultMode, "jvmDefaultMode");
        this._deprecated = ImplUtilsKt.lazyPub(new Function0<Boolean>() { // from class: org.jetbrains.kotlin.asJava.classes.KtLightClassImpl$_deprecated$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                return Boolean.valueOf(UltraLightUtilsKt.isDeprecated$default(classOrObject, null, 1, null));
            }
        });
        this._containingFile = ImplUtilsKt.lazyPub(new Function0<KtLightClassImpl$_containingFile$2.AnonymousClass1>() { // from class: org.jetbrains.kotlin.asJava.classes.KtLightClassImpl$_containingFile$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Type inference failed for: r2v0, types: [org.jetbrains.kotlin.asJava.classes.KtLightClassImpl$_containingFile$2$1] */
            @Override // kotlin.jvm.functions.Function0
            public final AnonymousClass1 invoke() {
                KtLightClassImpl lightClass;
                if (classOrObject.isTopLevel()) {
                    lightClass = this;
                } else {
                    lightClass = LightClassUtilsKt.toLightClass(KtLightClassForSourceDeclarationKt.getOutermostClassOrObject(classOrObject));
                    Intrinsics.checkNotNull(lightClass);
                }
                return new FakeFileForLightClass(lightClass, this, classOrObject.getContainingKtFile()) { // from class: org.jetbrains.kotlin.asJava.classes.KtLightClassImpl$_containingFile$2.1
                    final /* synthetic */ KtLightClassImpl this$0;

                    {
                        Intrinsics.checkNotNull(ktFile);
                        int i = 4;
                        DefaultConstructorMarker defaultConstructorMarker = null;
                        FqName fqName = null;
                    }

                    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFileImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
                    public PsiReference findReferenceAt(int offset) {
                        return getKtFile().findReferenceAt(offset);
                    }

                    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFileImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
                    public boolean processDeclarations(PsiScopeProcessor processor, ResolveState state, PsiElement lastParent, PsiElement place) {
                        PsiPackage psiPackageFindPackage;
                        Intrinsics.checkNotNullParameter(processor, "processor");
                        Intrinsics.checkNotNullParameter(state, "state");
                        Intrinsics.checkNotNullParameter(place, "place");
                        if (!super.processDeclarations(processor, state, lastParent, place)) {
                            return false;
                        }
                        String packageName = getPackageName();
                        return packageName.length() > 0 || (psiPackageFindPackage = JavaPsiFacade.getInstance(this.this$0.myManager.getProject()).findPackage(packageName)) == null || psiPackageFindPackage.processDeclarations(processor, state, null, place);
                    }
                };
            }
        });
    }

    public final ClassDescriptor getDescriptor() {
        LightClassGenerationSupport.Companion companion = LightClassGenerationSupport.INSTANCE;
        Project project = getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        DeclarationDescriptor declarationDescriptorResolveToDescriptor = companion.getInstance(project).resolveToDescriptor(getClassOrObject());
        if (declarationDescriptorResolveToDescriptor instanceof ClassDescriptor) {
            return (ClassDescriptor) declarationDescriptorResolveToDescriptor;
        }
        return null;
    }

    private final boolean get_deprecated() {
        return ((Boolean) this._deprecated.getValue()).booleanValue();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner
    public boolean isDeprecated() {
        return get_deprecated();
    }

    protected Set<String> computeModifiersByPsi() {
        HashSet hashSet = new HashSet();
        for (Pair<KtModifierKeywordToken, String> pair : ktTokenToPsiModifier) {
            if (getClassOrObject().hasModifier(pair.getFirst())) {
                hashSet.add(pair.getSecond());
            }
        }
        if (getClassOrObject().hasModifier(KtTokens.PRIVATE_KEYWORD)) {
            hashSet.add(getClassOrObject().isTopLevel() ? PsiModifier.PACKAGE_LOCAL : "private");
        } else if (!hashSet.contains("protected")) {
            hashSet.add("public");
        }
        if (isAbstract() || isSealed()) {
            hashSet.add("abstract");
        }
        if (!getClassOrObject().isTopLevel() && !getClassOrObject().hasModifier(KtTokens.INNER_KEYWORD)) {
            hashSet.add("static");
        }
        return hashSet;
    }

    protected boolean computeIsFinal() {
        if (getClassOrObject().hasModifier(KtTokens.FINAL_KEYWORD)) {
            return true;
        }
        if (!isAbstract() && !isSealed()) {
            if (isEnum()) {
                if (!hasEnumEntryWhichRequiresSubclass()) {
                    return true;
                }
            } else if (!getClassOrObject().hasModifier(KtTokens.OPEN_KEYWORD)) {
                Lazy<? extends DeclarationDescriptor> lazy = LazyKt.lazy(new Function0<ClassDescriptor>() { // from class: org.jetbrains.kotlin.asJava.classes.KtLightClassImpl$computeIsFinal$descriptor$1
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
                Iterator<T> it = UltraLightClassModifierExtension.INSTANCE.getInstances(project).iterator();
                String strInterceptModalityBuilding = "final";
                while (it.hasNext()) {
                    strInterceptModalityBuilding = ((UltraLightClassModifierExtension) it.next()).interceptModalityBuilding(getKotlinOrigin(), lazy, strInterceptModalityBuilding);
                }
                return Intrinsics.areEqual(strInterceptModalityBuilding, "final");
            }
        }
        return false;
    }

    private final boolean hasEnumEntryWhichRequiresSubclass() {
        List<KtDeclaration> declarations = getClassOrObject().getDeclarations();
        if ((declarations instanceof Collection) && declarations.isEmpty()) {
            return false;
        }
        for (KtDeclaration ktDeclaration : declarations) {
            if (ktDeclaration instanceof KtEnumEntry) {
                List<KtDeclaration> declarations2 = ((KtEnumEntry) ktDeclaration).getDeclarations();
                if (!(declarations2 instanceof Collection) || !declarations2.isEmpty()) {
                    Iterator<T> it = declarations2.iterator();
                    while (it.hasNext()) {
                        if (!(((KtDeclaration) it.next()) instanceof KtConstructor)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private final boolean isAbstract() {
        return getClassOrObject().hasModifier(KtTokens.ABSTRACT_KEYWORD) || isInterface() || (isEnum() && hasAbstractMember());
    }

    private final boolean hasAbstractMember() {
        ClassDescriptor descriptor = getDescriptor();
        if (descriptor == null) {
            return false;
        }
        MemberScope unsubstitutedMemberScope = descriptor.getUnsubstitutedMemberScope();
        Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope, "getUnsubstitutedMemberScope(...)");
        Collection<DeclarationDescriptor> contributedDescriptors$default = ResolutionScope.DefaultImpls.getContributedDescriptors$default(unsubstitutedMemberScope, null, null, 3, null);
        if ((contributedDescriptors$default instanceof Collection) && contributedDescriptors$default.isEmpty()) {
            return false;
        }
        for (DeclarationDescriptor declarationDescriptor : contributedDescriptors$default) {
            MemberDescriptor memberDescriptor = declarationDescriptor instanceof MemberDescriptor ? (MemberDescriptor) declarationDescriptor : null;
            if ((memberDescriptor != null ? memberDescriptor.getModality() : null) == Modality.ABSTRACT) {
                return true;
            }
        }
        return false;
    }

    private final boolean isSealed() {
        return getClassOrObject().hasModifier(KtTokens.SEALED_KEYWORD);
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isInheritor(PsiClass baseClass, boolean checkDeep) {
        String qualifiedName;
        FqNameUnsafe fqName;
        Intrinsics.checkNotNullParameter(baseClass, "baseClass");
        if (getManager().areElementsEquivalent(baseClass, this)) {
            return false;
        }
        LightClassInheritanceHelper.Companion companion = LightClassInheritanceHelper.INSTANCE;
        Project project = getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        int i = KtLightClassImpl$isInheritor$$inlined$ifSure$1$wm$ImpreciseResolveResult$WhenMappings.$EnumSwitchMapping$0[companion.getService(project).isInheritor(this, baseClass, checkDeep).ordinal()];
        if (i == 1) {
            return true;
        }
        if (i == 2) {
            return false;
        }
        if (i == 3) {
            if (baseClass instanceof KtLightClassImpl) {
                ClassDescriptor descriptor = ((KtLightClassImpl) baseClass).getDescriptor();
                qualifiedName = (descriptor == null || (fqName = DescriptorUtils.getFqName(descriptor)) == null) ? null : fqName.asString();
            } else {
                qualifiedName = baseClass.getQualifiedName();
            }
            ClassDescriptor descriptor2 = getDescriptor();
            if (qualifiedName == null || descriptor2 == null) {
                return InheritanceImplUtil.isInheritor(this, baseClass, checkDeep);
            }
            return !Intrinsics.areEqual(qualifiedName, DescriptorUtils.getFqName(descriptor2).asString()) && INSTANCE.checkSuperTypeByFQName(descriptor2, qualifiedName, checkDeep);
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getQualifiedName() {
        FqName fqName = getClassOrObject().mo7391getFqName();
        if (fqName != null) {
            return fqName.asString();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getLightOwner() {
        if (getClassOrObject().isTopLevel()) {
            return getContainingFile();
        }
        return mo6092getContainingClass();
    }

    private final PsiFile get_containingFile() {
        return (PsiFile) this._containingFile.getValue();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        return get_containingFile();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public List<PsiClass> getOwnInnerClasses() {
        ArrayList arrayList = new ArrayList();
        List<KtDeclaration> declarations = getClassOrObject().getDeclarations();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof KtClassOrObject) {
                arrayList2.add(obj);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : arrayList2) {
            if (((KtClassOrObject) obj2).get_name() != null) {
                arrayList3.add(obj2);
            }
        }
        Iterator it = arrayList3.iterator();
        while (it.hasNext()) {
            KtLightClass lightClass = LightClassUtilsKt.toLightClass((KtClassOrObject) it.next());
            if (lightClass != null) {
                arrayList.add(lightClass);
            }
        }
        if (LightClassUtilsKt.getHasInterfaceDefaultImpls(getClassOrObject()) && getJvmDefaultMode() != JvmDefaultMode.ALL_INCOMPATIBLE) {
            arrayList.add(createClassForInterfaceDefaultImpls());
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    /* JADX INFO: renamed from: getContainingClass, reason: merged with bridge method [inline-methods] */
    public PsiClass mo6092getContainingClass() {
        if (getClassOrObject().getLightOwner() == getClassOrObject().getContainingFile()) {
            return null;
        }
        PsiElement parent = getClassOrObject().getLightOwner();
        KtClassBody ktClassBody = parent instanceof KtClassBody ? (KtClassBody) parent : null;
        PsiElement lightOwner = ktClassBody != null ? ktClassBody.getLightOwner() : null;
        KtClassOrObject ktClassOrObject = lightOwner instanceof KtClassOrObject ? (KtClassOrObject) lightOwner : null;
        if (ktClassOrObject != null) {
            return LightClassUtilsKt.toLightClass(ktClassOrObject);
        }
        return null;
    }

    /* JADX INFO: compiled from: KtLightClassImpl.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\nH\u0002R(\u0010\u0003\u001a\u001c\u0012\u0018\u0012\u0016\u0012\f\u0012\n \u0007*\u0004\u0018\u00010\u00060\u0006\u0012\u0004\u0012\u00020\b0\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtLightClassImpl$Companion;", "", "()V", "ktTokenToPsiModifier", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "", "checkSuperTypeByFQName", "", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "qualifiedName", "deep", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean checkSuperTypeByFQName(ClassDescriptor classDescriptor, String qualifiedName, boolean deep) {
            FqName fqNameAsSingleFqName;
            if (Intrinsics.areEqual(CommonClassNames.JAVA_LANG_OBJECT, qualifiedName) || Intrinsics.areEqual(qualifiedName, DescriptorUtils.getFqName(classDescriptor).asString())) {
                return true;
            }
            FqNameUnsafe fqNameUnsafe = new FqNameUnsafe(qualifiedName);
            String strAsString = null;
            if (fqNameUnsafe.isSafe()) {
                JavaToKotlinClassMap javaToKotlinClassMap = JavaToKotlinClassMap.INSTANCE;
                FqName safe = fqNameUnsafe.toSafe();
                Intrinsics.checkNotNullExpressionValue(safe, "toSafe(...)");
                ClassId classIdMapJavaToKotlin = javaToKotlinClassMap.mapJavaToKotlin(safe);
                if (classIdMapJavaToKotlin != null && (fqNameAsSingleFqName = classIdMapJavaToKotlin.asSingleFqName()) != null) {
                    strAsString = fqNameAsSingleFqName.asString();
                }
            }
            if (Intrinsics.areEqual(qualifiedName, strAsString)) {
                return true;
            }
            Iterator<KotlinType> it = classDescriptor.getTypeConstructor().mo7438getSupertypes().iterator();
            while (it.hasNext()) {
                ClassifierDescriptor this$0 = it.next().getConstructor().getThis$0();
                if (this$0 instanceof ClassDescriptor) {
                    String strAsString2 = DescriptorUtils.getFqName(this$0).asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                    if (Intrinsics.areEqual(strAsString2, qualifiedName) || Intrinsics.areEqual(strAsString2, strAsString) || (deep && checkSuperTypeByFQName((ClassDescriptor) this$0, qualifiedName, true))) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
