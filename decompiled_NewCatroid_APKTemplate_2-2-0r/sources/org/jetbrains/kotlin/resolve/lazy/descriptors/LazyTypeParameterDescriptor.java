package org.jetbrains.kotlin.resolve.lazy.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.AbstractLazyTypeParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtStubbedPsiUtil;
import org.jetbrains.kotlin.psi.KtTypeConstraint;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.lazy.ForceResolveUtil;
import org.jetbrains.kotlin.resolve.lazy.LazyClassContext;
import org.jetbrains.kotlin.resolve.lazy.LazyEntity;
import org.jetbrains.kotlin.resolve.source.KotlinSourceElementKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;

/* JADX INFO: loaded from: classes6.dex */
public class LazyTypeParameterDescriptor extends AbstractLazyTypeParameterDescriptor implements LazyEntity {
    private final LazyClassContext c;
    private final KtTypeParameter typeParameter;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 5 || i == 7 || i == 8) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 5 || i == 7 || i == 8) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "containingDeclaration";
                break;
            case 2:
                objArr[0] = "typeParameter";
                break;
            case 3:
                objArr[0] = "annotations";
                break;
            case 4:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 5:
            case 7:
            case 8:
                objArr[0] = "org/jetbrains/kotlin/resolve/lazy/descriptors/LazyTypeParameterDescriptor";
                break;
            case 6:
                objArr[0] = "boundTypeReference";
                break;
            default:
                objArr[0] = "c";
                break;
        }
        if (i == 5) {
            objArr[1] = "resolveUpperBounds";
        } else if (i == 7) {
            objArr[1] = "resolveBoundType";
        } else if (i != 8) {
            objArr[1] = "org/jetbrains/kotlin/resolve/lazy/descriptors/LazyTypeParameterDescriptor";
        } else {
            objArr[1] = "getContainingDeclaration";
        }
        switch (i) {
            case 4:
                objArr[2] = "reportSupertypeLoopError";
                break;
            case 5:
            case 7:
            case 8:
                break;
            case 6:
                objArr[2] = "resolveBoundType";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 7 && i != 8) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LazyTypeParameterDescriptor(LazyClassContext lazyClassContext, LazyClassDescriptor lazyClassDescriptor, KtTypeParameter ktTypeParameter, Annotations annotations, int i) {
        super(lazyClassContext.getStorageManager(), lazyClassDescriptor, annotations, ktTypeParameter.getNameAsSafeName(), ktTypeParameter.getVariance(), ktTypeParameter.hasModifier(KtTokens.REIFIED_KEYWORD), i, KotlinSourceElementKt.toSourceElement(ktTypeParameter), lazyClassContext.getSupertypeLoopChecker());
        if (lazyClassContext == null) {
            $$$reportNull$$$0(0);
        }
        if (lazyClassDescriptor == null) {
            $$$reportNull$$$0(1);
        }
        if (ktTypeParameter == null) {
            $$$reportNull$$$0(2);
        }
        if (annotations == null) {
            $$$reportNull$$$0(3);
        }
        this.c = lazyClassContext;
        this.typeParameter = ktTypeParameter;
        lazyClassContext.getTrace().record(BindingContext.TYPE_PARAMETER, ktTypeParameter, this);
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.AbstractTypeParameterDescriptor
    /* JADX INFO: renamed from: reportSupertypeLoopError */
    protected void mo7470reportSupertypeLoopError(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(4);
        }
        for (KtTypeReference ktTypeReference : getAllUpperBounds()) {
            if (resolveBoundType(ktTypeReference).getConstructor().equals(kotlinType.getConstructor())) {
                this.c.getTrace().report(Errors.CYCLIC_GENERIC_UPPER_BOUND.on(ktTypeReference));
                return;
            }
        }
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.AbstractTypeParameterDescriptor
    protected List<KotlinType> resolveUpperBounds() {
        ArrayList arrayList = new ArrayList(1);
        Iterator<KtTypeReference> it = getAllUpperBounds().iterator();
        while (it.hasNext()) {
            KotlinType kotlinTypeResolveBoundType = resolveBoundType(it.next());
            if (!KotlinTypeKt.isError(kotlinTypeResolveBoundType)) {
                arrayList.add(kotlinTypeResolveBoundType);
            }
        }
        if (arrayList.isEmpty()) {
            arrayList.add(this.c.getModuleDescriptor().getBuiltIns().getDefaultBound());
        }
        return arrayList;
    }

    private Collection<KtTypeReference> getAllUpperBounds() {
        List listEmptyList;
        if (this.typeParameter.getExtendsBound() != null) {
            listEmptyList = Collections.singletonList(this.typeParameter.getExtendsBound());
        } else {
            listEmptyList = Collections.emptyList();
        }
        return CollectionsKt.plus((Collection) listEmptyList, (Iterable) getUpperBoundsFromWhereClause());
    }

    private Collection<KtTypeReference> getUpperBoundsFromWhereClause() {
        ArrayList arrayList = new ArrayList();
        KtClassOrObject ktClassOrObject = (KtClassOrObject) KtStubbedPsiUtil.getPsiOrStubParent(this.typeParameter, KtClassOrObject.class, true);
        if (ktClassOrObject instanceof KtClass) {
            for (KtTypeConstraint ktTypeConstraint : ktClassOrObject.getTypeConstraints()) {
                KtSimpleNameExpression subjectTypeParameterName = ktTypeConstraint.getSubjectTypeParameterName();
                if (subjectTypeParameterName != null && getShortName().equals(subjectTypeParameterName.getReferencedNameAsName())) {
                    this.c.getTrace().record(BindingContext.REFERENCE_TARGET, subjectTypeParameterName, this);
                    KtTypeReference boundTypeReference = ktTypeConstraint.getBoundTypeReference();
                    if (boundTypeReference != null) {
                        arrayList.add(boundTypeReference);
                    }
                }
            }
        }
        return arrayList;
    }

    private KotlinType resolveBoundType(KtTypeReference ktTypeReference) {
        if (ktTypeReference == null) {
            $$$reportNull$$$0(6);
        }
        KotlinType kotlinTypeResolveType = this.c.getTypeResolver().resolveType(getContainingModule().getScopeForClassHeaderResolution(), ktTypeReference, this.c.getTrace(), false);
        if (kotlinTypeResolveType == null) {
            $$$reportNull$$$0(7);
        }
        return kotlinTypeResolveType;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorNonRootImpl, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: getContainingDeclaration */
    public LazyClassDescriptor getContainingModule() {
        LazyClassDescriptor lazyClassDescriptor = (LazyClassDescriptor) super.getContainingModule();
        if (lazyClassDescriptor == null) {
            $$$reportNull$$$0(8);
        }
        return lazyClassDescriptor;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.LazyEntity
    public void forceResolveAllContents() {
        ForceResolveUtil.forceResolveAllContents(getAnnotations());
        getContainingModule();
        getDefaultType();
        getIndex();
        getOriginal();
        ForceResolveUtil.forceResolveAllContents(getTypeConstructor());
        ForceResolveUtil.forceResolveAllContents((Collection<KotlinType>) getUpperBounds());
        getVariance();
    }
}
