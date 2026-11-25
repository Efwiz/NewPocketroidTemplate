package org.jetbrains.kotlin.psi;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProviders;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtConstructor;
import org.jetbrains.kotlin.psi.stubs.KotlinConstructorStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtConstructorElementType;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KtConstructor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\b&\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00002\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00030\u00022\u00020\u0004B\u000f\b\u0014\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007B#\b\u0014\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\n¢\u0006\u0002\u0010\u000bJ\n\u0010\f\u001a\u0004\u0018\u00010\rH\u0016J\u000f\u0010\u000e\u001a\t\u0018\u00010\u000f¢\u0006\u0002\b\u0010H\u0016J\n\u0010\u0011\u001a\u0004\u0018\u00010\u000fH\u0016J\b\u0010\u0012\u001a\u00020\u0013H&J\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0016J\n\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016J\n\u0010\u0019\u001a\u0004\u0018\u00010\u0018H\u0016J\n\u0010\u001a\u001a\u0004\u0018\u00010\u000fH\u0016J\n\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u001eH\u0016J\n\u0010 \u001a\u0004\u0018\u00010\u0018H\u0016J\u000f\u0010!\u001a\t\u0018\u00010\"¢\u0006\u0002\b\u0010H\u0016J\n\u0010#\u001a\u0004\u0018\u00010\u0018H\u0016J\b\u0010$\u001a\u00020%H\u0016J\n\u0010&\u001a\u0004\u0018\u00010\u0018H\u0016J\u000e\u0010'\u001a\b\u0012\u0004\u0012\u00020(0\u0015H\u0016J\n\u0010)\u001a\u0004\u0018\u00010\u0018H\u0016J\u000e\u0010*\u001a\b\u0012\u0004\u0012\u00020+0\u0015H\u0016J\n\u0010,\u001a\u0004\u0018\u00010\u0018H\u0016J\b\u0010-\u001a\u00020.H\u0016J\u000f\u0010/\u001a\t\u0018\u000100¢\u0006\u0002\b\u0010H\u0016J\u0016\u00101\u001a\u0010\u0012\f\u0012\n 3*\u0004\u0018\u000102020\u0015H\u0016J\b\u00104\u001a\u000205H\u0016J\b\u00106\u001a\u000205H\u0016J\u0006\u00107\u001a\u000205J\b\u00108\u001a\u000205H\u0016J\u0006\u00109\u001a\u000205J\b\u0010:\u001a\u000205H\u0016J\u0010\u0010;\u001a\u00020\u000f2\u0006\u0010<\u001a\u00020\u001cH\u0016J\u0012\u0010=\u001a\u00020\u00182\b\u0010>\u001a\u0004\u0018\u00010?H\u0016¨\u0006@"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtConstructor;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/psi/KtDeclarationStub;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinConstructorStub;", "Lorg/jetbrains/kotlin/psi/KtFunction;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "stub", "nodeType", "Lorg/jetbrains/kotlin/psi/stubs/elements/KtConstructorElementType;", "(Lorg/jetbrains/kotlin/psi/stubs/KotlinConstructorStub;Lorg/jetbrains/kotlin/psi/stubs/elements/KtConstructorElementType;)V", "getBodyExpression", "Lorg/jetbrains/kotlin/psi/KtBlockExpression;", "getColon", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Lorg/jetbrains/annotations/Nullable;", "getConstructorKeyword", "getContainingClassOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "getContextReceivers", "", "Lorg/jetbrains/kotlin/psi/KtContextReceiver;", "getEqualsToken", "", "getFqName", "getIdentifyingElement", "getName", "", "getNameAsName", "Lorg/jetbrains/kotlin/name/Name;", "getNameAsSafeName", "getNameIdentifier", "getPresentation", "Lorg/jetbrains/kotlin/com/intellij/navigation/ItemPresentation;", "getReceiverTypeReference", "getTextOffset", "", "getTypeConstraintList", "getTypeConstraints", "Lorg/jetbrains/kotlin/psi/KtTypeConstraint;", "getTypeParameterList", "getTypeParameters", "Lorg/jetbrains/kotlin/psi/KtTypeParameter;", "getTypeReference", "getUseScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/SearchScope;", "getValueParameterList", "Lorg/jetbrains/kotlin/psi/KtParameterList;", "getValueParameters", "Lorg/jetbrains/kotlin/psi/KtParameter;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "hasBlockBody", "", "hasBody", "hasConstructorKeyword", "hasDeclaredReturnType", "isDelegatedCallToThis", "isLocal", "setName", "name", "setTypeReference", "typeRef", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtConstructor<T extends KtConstructor<T>> extends KtDeclarationStub<KotlinConstructorStub<T>> implements KtFunction {
    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithBody
    public KtBlockExpression getBodyExpression() {
        return null;
    }

    public abstract KtClassOrObject getContainingClassOrObject();

    public Void getEqualsToken() {
        return null;
    }

    public Void getFqName() {
        return null;
    }

    public Void getNameIdentifier() {
        return null;
    }

    public Void getReceiverTypeReference() {
        return null;
    }

    public Void getTypeConstraintList() {
        return null;
    }

    public Void getTypeParameterList() {
        return null;
    }

    public Void getTypeReference() {
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithBody
    public boolean hasDeclaredReturnType() {
        return false;
    }

    @Override // org.jetbrains.kotlin.psi.KtFunction
    public boolean isLocal() {
        return false;
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithBody
    /* JADX INFO: renamed from: getEqualsToken, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ PsiElement mo7390getEqualsToken() {
        return (PsiElement) getEqualsToken();
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclaration
    /* JADX INFO: renamed from: getFqName, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ FqName mo7391getFqName() {
        return (FqName) getFqName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ PsiElement mo7392getNameIdentifier() {
        return (PsiElement) getNameIdentifier();
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    /* JADX INFO: renamed from: getReceiverTypeReference, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ KtTypeReference mo7393getReceiverTypeReference() {
        return (KtTypeReference) getReceiverTypeReference();
    }

    @Override // org.jetbrains.kotlin.psi.KtTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeConstraintList, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ KtTypeConstraintList mo7394getTypeConstraintList() {
        return (KtTypeConstraintList) getTypeConstraintList();
    }

    @Override // org.jetbrains.kotlin.psi.KtTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameterList, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ KtTypeParameterList mo7395getTypeParameterList() {
        return (KtTypeParameterList) getTypeParameterList();
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    /* JADX INFO: renamed from: getTypeReference, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ KtTypeReference mo7396getTypeReference() {
        return (KtTypeReference) getTypeReference();
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    /* JADX INFO: renamed from: setTypeReference, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ KtTypeReference mo7397setTypeReference(KtTypeReference ktTypeReference) {
        return (KtTypeReference) setTypeReference(ktTypeReference);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected KtConstructor(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected KtConstructor(KotlinConstructorStub<T> stub, KtConstructorElementType<T> nodeType) {
        super(stub, nodeType);
        Intrinsics.checkNotNullParameter(stub, "stub");
        Intrinsics.checkNotNullParameter(nodeType, "nodeType");
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    public KtParameterList getValueParameterList() {
        return (KtParameterList) getStubOrPsiChild(KtStubElementTypes.VALUE_PARAMETER_LIST);
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration, org.jetbrains.kotlin.psi.KtDeclarationWithBody
    public List<KtParameter> getValueParameters() {
        KtParameterList valueParameterList = getValueParameterList();
        List<KtParameter> parameters = valueParameterList != null ? valueParameterList.getParameters() : null;
        return parameters == null ? CollectionsKt.emptyList() : parameters;
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    public List<KtContextReceiver> getContextReceivers() {
        return CollectionsKt.emptyList();
    }

    public Void setTypeReference(KtTypeReference typeRef) throws IncorrectOperationException {
        throw new IncorrectOperationException("setTypeReference to constructor");
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    public PsiElement getColon() {
        return findChildByType(KtTokens.COLON);
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithBody
    public boolean hasBlockBody() {
        return hasBody();
    }

    public final boolean isDelegatedCallToThis() {
        KotlinConstructorStub kotlinConstructorStub = (KotlinConstructorStub) getStub();
        if (kotlinConstructorStub != null) {
            return kotlinConstructorStub.isDelegatedCallToThis();
        }
        if (this instanceof KtPrimaryConstructor) {
            return false;
        }
        if (!(this instanceof KtSecondaryConstructor)) {
            throw new IllegalStateException("Unknown constructor type: " + this);
        }
        KtConstructorDelegationCall delegationCallOrNull = ((KtSecondaryConstructor) this).getDelegationCallOrNull();
        if (delegationCallOrNull != null) {
            return delegationCallOrNull.isCallToThis();
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithBody
    public boolean hasBody() {
        KotlinConstructorStub kotlinConstructorStub = (KotlinConstructorStub) getStub();
        if (kotlinConstructorStub != null) {
            return kotlinConstructorStub.hasBody();
        }
        return getBodyExpression() != null;
    }

    @Override // org.jetbrains.kotlin.psi.KtTypeParameterListOwner
    public List<KtTypeConstraint> getTypeConstraints() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.psi.KtTypeParameterListOwner
    public List<KtTypeParameter> getTypeParameters() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        return getContainingClassOrObject().getName();
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclaration
    public Name getNameAsSafeName() {
        Name nameSafeName = KtPsiUtil.safeName(getName());
        Intrinsics.checkNotNullExpressionValue(nameSafeName, "safeName(...)");
        return nameSafeName;
    }

    @Override // org.jetbrains.kotlin.psi.KtNamed
    public Name getNameAsName() {
        return getNameAsSafeName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    public PsiElement getIdentifyingElement() {
        return getConstructorKeyword();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String name2) throws IncorrectOperationException {
        Intrinsics.checkNotNullParameter(name2, "name");
        throw new IncorrectOperationException("setName to constructor");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem
    public ItemPresentation getPresentation() {
        return ItemPresentationProviders.getItemPresentation(this);
    }

    public PsiElement getConstructorKeyword() {
        return findChildByType(KtTokens.CONSTRUCTOR_KEYWORD);
    }

    public final boolean hasConstructorKeyword() {
        return (getStub() == null && getConstructorKeyword() == null) ? false : true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        PsiElement constructorKeyword = getConstructorKeyword();
        if (constructorKeyword != null) {
            return constructorKeyword.getTextOffset();
        }
        KtParameterList valueParameterList = getValueParameterList();
        return valueParameterList != null ? valueParameterList.getTextOffset() : super.getTextOffset();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        SearchScope useScope = getContainingClassOrObject().getUseScope();
        Intrinsics.checkNotNullExpressionValue(useScope, "getUseScope(...)");
        return useScope;
    }
}
