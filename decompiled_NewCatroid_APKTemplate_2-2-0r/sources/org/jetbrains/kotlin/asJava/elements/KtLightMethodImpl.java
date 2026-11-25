package org.jetbrains.kotlin.asJava.elements;

import java.util.List;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.LightClassUtilsKt;
import org.jetbrains.kotlin.asJava.builder.LightMemberOriginForDeclaration;
import org.jetbrains.kotlin.asJava.classes.ImplUtilsKt;
import org.jetbrains.kotlin.asJava.classes.KtLightClass;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureBackedByPsiMethod;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtil;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtLiteralStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtPsiFactory;

/* JADX INFO: compiled from: KtLightMethodImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000ê\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0019\b\u0004\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u000e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eH$J\n\u0010 \u001a\u0004\u0018\u00010\u0015H$J\b\u0010!\u001a\u00020\u001aH\u0016J\u0013\u0010\"\u001a\u00020\u000b2\b\u0010#\u001a\u0004\u0018\u00010$H\u0096\u0002J\n\u0010%\u001a\u0004\u0018\u00010\u0002H&J\u0015\u0010&\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020'H&¢\u0006\u0002\u0010(J\u0016\u0010)\u001a\b\u0012\u0004\u0012\u00020*0\u001e2\u0006\u0010+\u001a\u00020\u000bH&J\u0015\u0010,\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020'H&¢\u0006\u0002\u0010(J\u001f\u0010,\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020'2\b\u0010-\u001a\u0004\u0018\u00010.H&¢\u0006\u0002\u0010/J\u001d\u0010,\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020'2\u0006\u0010+\u001a\u00020\u000bH&¢\u0006\u0002\u00100J\n\u00101\u001a\u0004\u0018\u000102H\u0016J\n\u00103\u001a\u0004\u0018\u000104H&J\b\u00105\u001a\u000206H&J\b\u00107\u001a\u000208H&J\b\u00109\u001a\u00020\u000fH\u0016J\n\u0010:\u001a\u0004\u0018\u00010;H\u0016J\n\u0010<\u001a\u0004\u0018\u00010=H&J\u0010\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020AH&J\b\u0010B\u001a\u00020CH\u0016J\b\u0010D\u001a\u00020EH\u0016J\b\u0010F\u001a\u00020GH\u0016J*\u0010H\u001a\u0004\u0018\u0001HI\"\u0004\b\u0000\u0010I2\u0012\u0010J\u001a\u000e\u0012\u0004\u0012\u00020L\u0012\u0004\u0012\u0002HI0KH\u0082\b¢\u0006\u0002\u0010MJ\b\u0010N\u001a\u00020OH&J\n\u0010P\u001a\u0004\u0018\u00010\u0015H\u0016J\u0013\u0010Q\u001a\b\u0012\u0004\u0012\u00020R0'H\u0016¢\u0006\u0002\u0010SJ\b\u0010T\u001a\u00020\u000bH\u0016J\b\u0010U\u001a\u00020EH\u0016J\b\u0010V\u001a\u00020\u000bH&J\b\u0010W\u001a\u00020\u000bH&J*\u0010X\u001a\u00020\u000b2\u0006\u0010Y\u001a\u00020Z2\u0006\u0010[\u001a\u00020\\2\b\u0010]\u001a\u0004\u0018\u00010^2\u0006\u0010_\u001a\u00020^H\u0016J\u0012\u0010`\u001a\u0004\u0018\u00010^2\u0006\u0010a\u001a\u00020CH\u0016R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001b\u0010\u000e\u001a\u00020\u000f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0010\u0010\u0011R\u001d\u0010\u0014\u001a\u0004\u0018\u00010\u00158BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\u0013\u001a\u0004\b\u0016\u0010\u0017¨\u0006b"}, d2 = {"Lorg/jetbrains/kotlin/asJava/elements/KtLightMethodImpl;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightMemberImpl;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMethod;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;", "lightMemberOrigin", "Lorg/jetbrains/kotlin/asJava/builder/LightMemberOriginForDeclaration;", "containingClass", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;", "(Lorg/jetbrains/kotlin/asJava/builder/LightMemberOriginForDeclaration;Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;)V", "calculatingReturnType", "Ljava/lang/ThreadLocal;", "", "isMangled", "()Z", "paramsList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiParameterList;", "getParamsList", "()Lcom/intellij/psi/PsiParameterList;", "paramsList$delegate", "Lkotlin/Lazy;", "typeParamsList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameterList;", "getTypeParamsList", "()Lcom/intellij/psi/PsiTypeParameterList;", "typeParamsList$delegate", "accept", "", "visitor", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElementVisitor;", "buildParametersForList", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiParameter;", "buildTypeParameterList", "delete", Namer.EQUALS_METHOD_NAME, "other", "", "findDeepestSuperMethod", "findDeepestSuperMethods", "", "()[Lcom/intellij/psi/PsiMethod;", "findSuperMethodSignaturesIncludingStatic", "Lorg/jetbrains/kotlin/com/intellij/psi/util/MethodSignatureBackedByPsiMethod;", "checkAccess", "findSuperMethods", "parentClass", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "(Lcom/intellij/psi/PsiClass;)[Lcom/intellij/psi/PsiMethod;", "(Z)[Lcom/intellij/psi/PsiMethod;", "getBody", "", "getDefaultValue", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotationMemberValue;", "getHierarchicalMethodSignature", "Lorg/jetbrains/kotlin/com/intellij/psi/HierarchicalMethodSignature;", "getModifierList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierList;", "getParameterList", "getReturnType", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "getReturnTypeElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeElement;", "getSignature", "Lorg/jetbrains/kotlin/com/intellij/psi/util/MethodSignature;", "substitutor", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiSubstitutor;", "getText", "", "getTextOffset", "", "getTextRange", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "getTextVariantFromPropertyAccessorIfNeeded", "R", "retriever", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/psi/KtPropertyAccessor;", "(Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "getThrowsList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiReferenceList;", "getTypeParameterList", "getTypeParameters", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameter;", "()[Lcom/intellij/psi/PsiTypeParameter;", "hasTypeParameters", "hashCode", "isConstructor", "isVarArgs", "processDeclarations", "processor", "Lorg/jetbrains/kotlin/com/intellij/psi/scope/PsiScopeProcessor;", "state", "Lorg/jetbrains/kotlin/com/intellij/psi/ResolveState;", "lastParent", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "place", "setName", "name", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtLightMethodImpl extends KtLightMemberImpl<PsiMethod> implements KtLightMethod {
    private final ThreadLocal<Boolean> calculatingReturnType;

    /* JADX INFO: renamed from: paramsList$delegate, reason: from kotlin metadata */
    private final Lazy paramsList;

    /* JADX INFO: renamed from: typeParamsList$delegate, reason: from kotlin metadata */
    private final Lazy typeParamsList;

    protected abstract List<PsiParameter> buildParametersForList();

    protected abstract PsiTypeParameterList buildTypeParameterList();

    public abstract PsiMethod findDeepestSuperMethod();

    public abstract PsiMethod[] findDeepestSuperMethods();

    public abstract List<MethodSignatureBackedByPsiMethod> findSuperMethodSignaturesIncludingStatic(boolean checkAccess);

    public abstract PsiMethod[] findSuperMethods();

    public abstract PsiMethod[] findSuperMethods(PsiClass psiClass);

    public abstract PsiMethod[] findSuperMethods(boolean z);

    public Void getBody() {
        return null;
    }

    public abstract PsiAnnotationMemberValue getDefaultValue();

    public abstract HierarchicalMethodSignature getHierarchicalMethodSignature();

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMemberImpl, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public abstract PsiModifierList getModifierList();

    public abstract PsiTypeElement getReturnTypeElement();

    public abstract MethodSignature getSignature(PsiSubstitutor substitutor);

    public abstract PsiReferenceList getThrowsList();

    public abstract boolean isConstructor();

    public abstract boolean isVarArgs();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiParameterListOwner
    public /* bridge */ /* synthetic */ PsiCodeBlock getBody() {
        return (PsiCodeBlock) getBody();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiParameterListOwner
    public /* bridge */ /* synthetic */ PsiElement getBody() {
        return (PsiElement) getBody();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected KtLightMethodImpl(LightMemberOriginForDeclaration lightMemberOriginForDeclaration, KtLightClass containingClass) {
        super(lightMemberOriginForDeclaration, containingClass);
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        this.calculatingReturnType = new ThreadLocal<>();
        this.paramsList = ImplUtilsKt.lazyPub(new Function0<KtLightParameterList>() { // from class: org.jetbrains.kotlin.asJava.elements.KtLightMethodImpl$paramsList$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KtLightParameterList invoke() {
                final List<PsiParameter> listBuildParametersForList = this.this$0.buildParametersForList();
                return new KtLightParameterList(this.this$0, listBuildParametersForList.size(), new Function0<List<? extends PsiParameter>>() { // from class: org.jetbrains.kotlin.asJava.elements.KtLightMethodImpl$paramsList$2.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final List<? extends PsiParameter> invoke() {
                        return listBuildParametersForList;
                    }
                });
            }
        });
        this.typeParamsList = ImplUtilsKt.lazyPub(new Function0<PsiTypeParameterList>() { // from class: org.jetbrains.kotlin.asJava.elements.KtLightMethodImpl$typeParamsList$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiTypeParameterList invoke() {
                return this.this$0.buildTypeParameterList();
            }
        });
    }

    private final PsiParameterList getParamsList() {
        return (PsiParameterList) this.paramsList.getValue();
    }

    private final PsiTypeParameterList getTypeParamsList() {
        return (PsiTypeParameterList) this.typeParamsList.getValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        if (visitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) visitor).visitMethod(this);
        } else {
            visitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMethod
    public boolean isMangled() {
        return LightClassUtilsKt.checkIsMangled(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        PsiAnnotation psiAnnotationFindAnnotation = getModifierList().findAnnotation(JvmFileClassUtil.INSTANCE.getJVM_NAME().asString());
        PsiElement unwrapped = psiAnnotationFindAnnotation != null ? LightClassUtilsKt.getUnwrapped(psiAnnotationFindAnnotation) : null;
        KtAnnotationEntry ktAnnotationEntry = unwrapped instanceof KtAnnotationEntry ? (KtAnnotationEntry) unwrapped : null;
        String strDemangleInternalName = isMangled() ? LightClassUtilsKt.demangleInternalName(name2) : null;
        if (strDemangleInternalName == null) {
            strDemangleInternalName = name2;
        }
        String strPropertyNameByAccessor = LightClassUtilsKt.propertyNameByAccessor(strDemangleInternalName, this);
        if (strPropertyNameByAccessor != null) {
            strDemangleInternalName = strPropertyNameByAccessor;
        }
        KtDeclaration kotlinOrigin = getKtDeclaration();
        if (Intrinsics.areEqual(strDemangleInternalName, kotlinOrigin != null ? kotlinOrigin.getName() : null)) {
            if (ktAnnotationEntry != null) {
                ktAnnotationEntry.delete();
            }
            return this;
        }
        KtLiteralStringTemplateEntry literalStringEntryFromAnnotation = ktAnnotationEntry != null ? JvmFileClassUtil.INSTANCE.getLiteralStringEntryFromAnnotation(ktAnnotationEntry) : null;
        if (literalStringEntryFromAnnotation != null) {
            Project project = getProject();
            Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
            literalStringEntryFromAnnotation.replace(new KtPsiFactory(project, false, 2, (DefaultConstructorMarker) null).createLiteralStringTemplateEntry(name2));
        } else {
            KtDeclaration kotlinOrigin2 = getKtDeclaration();
            PsiNamedElement psiNamedElement = kotlinOrigin2 instanceof PsiNamedElement ? (PsiNamedElement) kotlinOrigin2 : null;
            if (psiNamedElement == null) {
                ImplUtilsKt.cannotModify(this);
                throw null;
            }
            psiNamedElement.setName(strDemangleInternalName);
        }
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void delete() {
        Unit unit;
        KtDeclaration kotlinOrigin = getKtDeclaration();
        if (kotlinOrigin != null) {
            if (kotlinOrigin.isValid()) {
                kotlinOrigin.delete();
            }
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit != null) {
            return;
        }
        ImplUtilsKt.cannotModify(this);
        throw null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiParameterListOwner
    public PsiParameterList getParameterList() {
        return getParamsList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameterList */
    public PsiTypeParameterList mo6093getTypeParameterList() {
        return getTypeParamsList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public PsiTypeParameter[] getTypeParameters() {
        PsiTypeParameterList psiTypeParameterListMo6093getTypeParameterList = mo6093getTypeParameterList();
        PsiTypeParameter[] typeParameters = psiTypeParameterListMo6093getTypeParameterList != null ? psiTypeParameterListMo6093getTypeParameterList.getTypeParameters() : null;
        if (typeParameters != null) {
            return typeParameters;
        }
        PsiTypeParameter[] EMPTY_ARRAY = PsiTypeParameter.EMPTY_ARRAY;
        Intrinsics.checkNotNullExpressionValue(EMPTY_ARRAY, "EMPTY_ARRAY");
        return EMPTY_ARRAY;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public boolean hasTypeParameters() {
        return !(getTypeParameters().length == 0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor processor, ResolveState state, PsiElement lastParent, PsiElement place) {
        Intrinsics.checkNotNullParameter(processor, "processor");
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(place, "place");
        for (PsiTypeParameter psiTypeParameter : getTypeParameters()) {
            if (!processor.execute(psiTypeParameter, state)) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object other) {
        if (other != this) {
            if ((other instanceof KtLightMethodImpl) && Intrinsics.areEqual(other.getClass(), getClass())) {
                KtLightMethodImpl ktLightMethodImpl = (KtLightMethodImpl) other;
                if (!Intrinsics.areEqual(ktLightMethodImpl.getContainingClass(), getContainingClass()) || !Intrinsics.areEqual(ktLightMethodImpl.getLightMemberOrigin(), getLightMemberOrigin())) {
                }
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (getName().hashCode() * 31) + getContainingClass().hashCode();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiType getReturnType() {
        this.calculatingReturnType.set(true);
        try {
            PsiTypeElement returnTypeElement = getReturnTypeElement();
            return returnTypeElement != null ? returnTypeElement.getType() : null;
        } finally {
            this.calculatingReturnType.set(false);
        }
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        LightMemberOriginForDeclaration lightMemberOrigin = getLightMemberOrigin();
        KtDeclaration auxiliaryOriginalElement = lightMemberOrigin != null ? lightMemberOrigin.getAuxiliaryOriginalElement() : null;
        KtPropertyAccessor ktPropertyAccessor = auxiliaryOriginalElement instanceof KtPropertyAccessor ? (KtPropertyAccessor) auxiliaryOriginalElement : null;
        String text = ktPropertyAccessor != null ? ktPropertyAccessor.getText() : null;
        return text == null ? super.getText() : text;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        LightMemberOriginForDeclaration lightMemberOrigin = getLightMemberOrigin();
        KtDeclaration auxiliaryOriginalElement = lightMemberOrigin != null ? lightMemberOrigin.getAuxiliaryOriginalElement() : null;
        KtPropertyAccessor ktPropertyAccessor = auxiliaryOriginalElement instanceof KtPropertyAccessor ? (KtPropertyAccessor) auxiliaryOriginalElement : null;
        Integer numValueOf = ktPropertyAccessor != null ? Integer.valueOf(ktPropertyAccessor.getTextOffset()) : null;
        return numValueOf != null ? numValueOf.intValue() : super.getTextOffset();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public TextRange getTextRange() {
        LightMemberOriginForDeclaration lightMemberOrigin = getLightMemberOrigin();
        KtDeclaration auxiliaryOriginalElement = lightMemberOrigin != null ? lightMemberOrigin.getAuxiliaryOriginalElement() : null;
        KtPropertyAccessor ktPropertyAccessor = auxiliaryOriginalElement instanceof KtPropertyAccessor ? (KtPropertyAccessor) auxiliaryOriginalElement : null;
        TextRange textRange = ktPropertyAccessor != null ? ktPropertyAccessor.getTextRange() : null;
        if (textRange != null) {
            return textRange;
        }
        TextRange textRange2 = super.getTextRange();
        Intrinsics.checkNotNullExpressionValue(textRange2, "getTextRange(...)");
        return textRange2;
    }
}
