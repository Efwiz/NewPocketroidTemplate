package org.jetbrains.kotlin.psi;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProviders;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiErrorElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.psi.impl.CheckUtil;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.psiUtil.ClassIdCalculator;
import org.jetbrains.kotlin.psi.stubs.KotlinClassOrObjectStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KtClassOrObject.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000¸\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\n\b&\u0018\u00002\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00000\u00020\u00012\u00020\u00032\u00020\u00042\u00020\u00052\u00020\u0006B\u000f\b\u0016\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tB'\b\u0016\u0012\u000e\u0010\n\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00000\u0002\u0012\u000e\u0010\u000b\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\f¢\u0006\u0002\u0010\rJ\"\u0010\u0011\u001a\u0002H\u0012\"\n\b\u0000\u0010\u0012\u0018\u0001*\u00020\u00132\u0006\u0010\u0014\u001a\u0002H\u0012H\u0086\b¢\u0006\u0002\u0010\u0015J,\u0010\u0016\u001a\u0002H\u0012\"\n\b\u0000\u0010\u0012\u0018\u0001*\u00020\u00132\u0006\u0010\u0014\u001a\u0002H\u00122\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0086\b¢\u0006\u0002\u0010\u0019J,\u0010\u001a\u001a\u0002H\u0012\"\n\b\u0000\u0010\u0012\u0018\u0001*\u00020\u00132\u0006\u0010\u0014\u001a\u0002H\u00122\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0086\b¢\u0006\u0002\u0010\u0019J\u000e\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001cJ\b\u0010\u001e\u001a\u00020\u001fH\u0016J\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\"0!J\n\u0010#\u001a\u0004\u0018\u00010$H\u0016J\n\u0010%\u001a\u0004\u0018\u00010&H\u0016J\b\u0010'\u001a\u0004\u0018\u00010\u0018J\b\u0010(\u001a\u0004\u0018\u00010)J\u000e\u0010*\u001a\b\u0012\u0004\u0012\u00020+0!H\u0016J\b\u0010,\u001a\u0004\u0018\u00010\u0018J\u000e\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00130!H\u0016J\n\u0010.\u001a\u0004\u0018\u00010/H\u0016J\n\u00100\u001a\u0004\u0018\u000101H\u0016J\n\u00102\u001a\u0004\u0018\u000103H\u0016J\b\u00104\u001a\u0004\u0018\u000105J\u000e\u00106\u001a\b\u0012\u0004\u0012\u0002070!H\u0016J\n\u00108\u001a\u0004\u0018\u000109H\u0004J\u000e\u0010:\u001a\b\u0012\u0004\u0012\u00020;0!H\u0016J\b\u0010<\u001a\u0004\u0018\u00010=J\u000e\u0010>\u001a\b\u0012\u0004\u0012\u00020\u001c0!H\u0016J\b\u0010?\u001a\u00020@H\u0016J\b\u0010A\u001a\u00020@H\u0016J\u0006\u0010B\u001a\u00020@J\u0006\u0010C\u001a\u00020@J\u0006\u0010D\u001a\u00020@J\u0012\u0010E\u001a\u00020@2\b\u0010F\u001a\u0004\u0018\u00010\u0018H\u0016J\b\u0010G\u001a\u00020@H\u0016J\u0006\u0010H\u001a\u00020@J\u000e\u0010I\u001a\u00020\u001f2\u0006\u0010\u001d\u001a\u00020\u001cR\u0013\u0010\u000e\u001a\u00070\u000f¢\u0006\u0002\b\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006J"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "Lorg/jetbrains/kotlin/psi/KtTypeParameterListOwnerStub;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinClassOrObjectStub;", "Lorg/jetbrains/kotlin/psi/KtDeclarationContainer;", "Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;", "Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;", "Lorg/jetbrains/kotlin/psi/KtClassLikeDeclaration;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "stub", "nodeType", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/IStubElementType;", "(Lorg/jetbrains/kotlin/psi/stubs/KotlinClassOrObjectStub;Lcom/intellij/psi/stubs/IStubElementType;)V", "classInterfaceObjectTokenSet", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/TokenSet;", "Lorg/jetbrains/annotations/NotNull;", "addDeclaration", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "declaration", "(Lorg/jetbrains/kotlin/psi/KtDeclaration;)Lorg/jetbrains/kotlin/psi/KtDeclaration;", "addDeclarationAfter", "anchor", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "(Lorg/jetbrains/kotlin/psi/KtDeclaration;Lcom/intellij/psi/PsiElement;)Lorg/jetbrains/kotlin/psi/KtDeclaration;", "addDeclarationBefore", "addSuperTypeListEntry", "Lorg/jetbrains/kotlin/psi/KtSuperTypeListEntry;", "superTypeListEntry", "delete", "", "getAnonymousInitializers", "", "Lorg/jetbrains/kotlin/psi/KtAnonymousInitializer;", "getBody", "Lorg/jetbrains/kotlin/psi/KtClassBody;", "getClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "getColon", "getContextReceiverList", "Lorg/jetbrains/kotlin/psi/KtContextReceiverList;", "getContextReceivers", "Lorg/jetbrains/kotlin/psi/KtContextReceiver;", "getDeclarationKeyword", "getDeclarations", "getPresentation", "Lorg/jetbrains/kotlin/com/intellij/navigation/ItemPresentation;", "getPrimaryConstructor", "Lorg/jetbrains/kotlin/psi/KtPrimaryConstructor;", "getPrimaryConstructorModifierList", "Lorg/jetbrains/kotlin/psi/KtModifierList;", "getPrimaryConstructorParameterList", "Lorg/jetbrains/kotlin/psi/KtParameterList;", "getPrimaryConstructorParameters", "Lorg/jetbrains/kotlin/psi/KtParameter;", "getQualifiedName", "", "getSecondaryConstructors", "Lorg/jetbrains/kotlin/psi/KtSecondaryConstructor;", "getSuperTypeList", "Lorg/jetbrains/kotlin/psi/KtSuperTypeList;", "getSuperTypeListEntries", "hasExplicitPrimaryConstructor", "", "hasPrimaryConstructor", "hasSecondaryConstructors", "isAnnotation", "isData", "isEquivalentTo", "another", "isLocal", "isTopLevel", "removeSuperTypeListEntry", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtClassOrObject extends KtTypeParameterListOwnerStub<KotlinClassOrObjectStub<? extends KtClassOrObject>> implements KtClassLikeDeclaration, KtDeclarationContainer, KtNamedDeclaration, KtPureClassOrObject {
    private final TokenSet classInterfaceObjectTokenSet;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtClassOrObject(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
        TokenSet tokenSetCreate = TokenSet.create(KtTokens.CLASS_KEYWORD, KtTokens.INTERFACE_KEYWORD, KtTokens.OBJECT_KEYWORD);
        Intrinsics.checkNotNullExpressionValue(tokenSetCreate, "create(...)");
        this.classInterfaceObjectTokenSet = tokenSetCreate;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtClassOrObject(KotlinClassOrObjectStub<? extends KtClassOrObject> stub, IStubElementType<?, ?> nodeType) {
        super(stub, nodeType);
        Intrinsics.checkNotNullParameter(stub, "stub");
        Intrinsics.checkNotNullParameter(nodeType, "nodeType");
        TokenSet tokenSetCreate = TokenSet.create(KtTokens.CLASS_KEYWORD, KtTokens.INTERFACE_KEYWORD, KtTokens.OBJECT_KEYWORD);
        Intrinsics.checkNotNullExpressionValue(tokenSetCreate, "create(...)");
        this.classInterfaceObjectTokenSet = tokenSetCreate;
    }

    public final PsiElement getColon() {
        return findChildByType(KtTokens.COLON);
    }

    public final KtSuperTypeList getSuperTypeList() {
        return (KtSuperTypeList) getStubOrPsiChild(KtStubElementTypes.SUPER_TYPE_LIST);
    }

    @Override // org.jetbrains.kotlin.psi.KtPureClassOrObject
    public List<KtSuperTypeListEntry> getSuperTypeListEntries() {
        KtSuperTypeList superTypeList = getSuperTypeList();
        List<KtSuperTypeListEntry> entries = superTypeList != null ? superTypeList.getEntries() : null;
        return entries == null ? CollectionsKt.emptyList() : entries;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final KtSuperTypeListEntry addSuperTypeListEntry(KtSuperTypeListEntry superTypeListEntry) {
        Intrinsics.checkNotNullParameter(superTypeListEntry, "superTypeListEntry");
        KtSuperTypeList superTypeList = getSuperTypeList();
        Object[] objArr = 0;
        if (superTypeList != null) {
            List<KtSuperTypeListEntry> entries = superTypeList.getEntries();
            Intrinsics.checkNotNullExpressionValue(entries, "getEntries(...)");
            KtSuperTypeListEntry ktSuperTypeListEntry = (KtSuperTypeListEntry) CollectionsKt.singleOrNull((List) entries);
            if (ktSuperTypeListEntry != null) {
                KtTypeReference typeReference = ktSuperTypeListEntry.getTypeReference();
                if ((typeReference != null ? typeReference.getTypeElement() : null) == null) {
                    PsiElement psiElementReplace = ktSuperTypeListEntry.replace(superTypeListEntry);
                    Intrinsics.checkNotNull(psiElementReplace, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtSuperTypeListEntry");
                    return (KtSuperTypeListEntry) psiElementReplace;
                }
            }
            return (KtSuperTypeListEntry) EditCommaSeparatedListHelper.addItem$default(EditCommaSeparatedListHelper.INSTANCE, superTypeList, getSuperTypeListEntries(), superTypeListEntry, null, 8, null);
        }
        Project project = getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        KtPsiFactory ktPsiFactory = new KtPsiFactory(project, false, 2, (DefaultConstructorMarker) (objArr == true ? 1 : 0));
        PsiElement psiElementAddAfter = addAfter(ktPsiFactory.createSuperTypeCallEntry("A()").replace(superTypeListEntry).getLightParent(), addBefore(ktPsiFactory.createColon(), getBody()));
        Intrinsics.checkNotNull(psiElementAddAfter, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtSuperTypeList");
        List<KtSuperTypeListEntry> entries2 = ((KtSuperTypeList) psiElementAddAfter).getEntries();
        Intrinsics.checkNotNullExpressionValue(entries2, "getEntries(...)");
        Object objFirst = CollectionsKt.first((List<? extends Object>) entries2);
        Intrinsics.checkNotNullExpressionValue(objFirst, "first(...)");
        return (KtSuperTypeListEntry) objFirst;
    }

    public final void removeSuperTypeListEntry(KtSuperTypeListEntry superTypeListEntry) {
        Intrinsics.checkNotNullParameter(superTypeListEntry, "superTypeListEntry");
        KtSuperTypeList superTypeList = getSuperTypeList();
        if (superTypeList == null) {
            return;
        }
        superTypeListEntry.getLightParent();
        if (superTypeList.getEntries().size() > 1) {
            EditCommaSeparatedListHelper.INSTANCE.removeItem(superTypeListEntry);
            return;
        }
        KtSuperTypeList colon = getColon();
        if (colon == null) {
            colon = superTypeList;
        }
        deleteChildRange(colon, superTypeList);
    }

    public final List<KtAnonymousInitializer> getAnonymousInitializers() {
        KtClassBody body = getBody();
        List<KtAnonymousInitializer> anonymousInitializers = body != null ? body.getAnonymousInitializers() : null;
        return anonymousInitializers == null ? CollectionsKt.emptyList() : anonymousInitializers;
    }

    @Override // org.jetbrains.kotlin.psi.KtPureClassOrObject
    public KtClassBody getBody() {
        return (KtClassBody) getStubOrPsiChild(KtStubElementTypes.CLASS_BODY);
    }

    public final /* synthetic */ <T extends KtDeclaration> T addDeclaration(T declaration) {
        PsiElement psiElementAddAfter;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        KtClassBody orCreateBody = KtClassOrObjectKt.getOrCreateBody(this);
        PsiElement rBrace = orCreateBody.getRBrace();
        if (rBrace == null) {
            rBrace = orCreateBody.getLastChild();
            Intrinsics.checkNotNull(rBrace);
        }
        PsiElement psiElementSkipSiblingsBackward = PsiTreeUtil.skipSiblingsBackward(rBrace, PsiWhiteSpace.class);
        if ((psiElementSkipSiblingsBackward != null ? psiElementSkipSiblingsBackward.getNextSibling() : null) instanceof PsiErrorElement) {
            psiElementAddAfter = orCreateBody.addBefore(declaration, psiElementSkipSiblingsBackward);
        } else {
            psiElementAddAfter = orCreateBody.addAfter(declaration, psiElementSkipSiblingsBackward);
        }
        Intrinsics.reifiedOperationMarker(1, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) psiElementAddAfter;
    }

    public final /* synthetic */ <T extends KtDeclaration> T addDeclarationAfter(T declaration, PsiElement psiElement) {
        PsiElement psiElementAddAfter;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (psiElement == null) {
            KtDeclaration ktDeclaration = (KtDeclaration) CollectionsKt.lastOrNull((List) getDeclarations());
            if (ktDeclaration == null) {
                KtClassBody orCreateBody = KtClassOrObjectKt.getOrCreateBody(this);
                PsiElement rBrace = orCreateBody.getRBrace();
                if (rBrace == null) {
                    rBrace = orCreateBody.getLastChild();
                    Intrinsics.checkNotNull(rBrace);
                }
                PsiElement psiElementSkipSiblingsBackward = PsiTreeUtil.skipSiblingsBackward(rBrace, PsiWhiteSpace.class);
                if ((psiElementSkipSiblingsBackward != null ? psiElementSkipSiblingsBackward.getNextSibling() : null) instanceof PsiErrorElement) {
                    psiElementAddAfter = orCreateBody.addBefore(declaration, psiElementSkipSiblingsBackward);
                } else {
                    psiElementAddAfter = orCreateBody.addAfter(declaration, psiElementSkipSiblingsBackward);
                }
                Intrinsics.reifiedOperationMarker(1, ExifInterface.GPS_DIRECTION_TRUE);
                return (T) psiElementAddAfter;
            }
            psiElement = ktDeclaration;
        }
        PsiElement psiElementAddAfter2 = KtClassOrObjectKt.getOrCreateBody(this).addAfter(declaration, psiElement);
        Intrinsics.reifiedOperationMarker(1, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) psiElementAddAfter2;
    }

    public final /* synthetic */ <T extends KtDeclaration> T addDeclarationBefore(T declaration, PsiElement psiElement) {
        PsiElement psiElementAddAfter;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (psiElement == null) {
            KtDeclaration ktDeclaration = (KtDeclaration) CollectionsKt.firstOrNull((List) getDeclarations());
            if (ktDeclaration == null) {
                KtClassBody orCreateBody = KtClassOrObjectKt.getOrCreateBody(this);
                PsiElement rBrace = orCreateBody.getRBrace();
                if (rBrace == null) {
                    rBrace = orCreateBody.getLastChild();
                    Intrinsics.checkNotNull(rBrace);
                }
                PsiElement psiElementSkipSiblingsBackward = PsiTreeUtil.skipSiblingsBackward(rBrace, PsiWhiteSpace.class);
                if ((psiElementSkipSiblingsBackward != null ? psiElementSkipSiblingsBackward.getNextSibling() : null) instanceof PsiErrorElement) {
                    psiElementAddAfter = orCreateBody.addBefore(declaration, psiElementSkipSiblingsBackward);
                } else {
                    psiElementAddAfter = orCreateBody.addAfter(declaration, psiElementSkipSiblingsBackward);
                }
                Intrinsics.reifiedOperationMarker(1, ExifInterface.GPS_DIRECTION_TRUE);
                return (T) psiElementAddAfter;
            }
            psiElement = ktDeclaration;
        }
        PsiElement psiElementAddBefore = KtClassOrObjectKt.getOrCreateBody(this).addBefore(declaration, psiElement);
        Intrinsics.reifiedOperationMarker(1, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) psiElementAddBefore;
    }

    public final boolean isTopLevel() {
        KotlinClassOrObjectStub kotlinClassOrObjectStub = (KotlinClassOrObjectStub) getStub();
        return kotlinClassOrObjectStub != null ? kotlinClassOrObjectStub.isTopLevel() : getLightParent() instanceof KtFile;
    }

    @Override // org.jetbrains.kotlin.psi.KtClassLikeDeclaration
    public ClassId getClassId() {
        KotlinClassOrObjectStub kotlinClassOrObjectStub = (KotlinClassOrObjectStub) getStub();
        if (kotlinClassOrObjectStub == null) {
            return ClassIdCalculator.INSTANCE.calculateClassId(this);
        }
        return kotlinClassOrObjectStub.getClassId();
    }

    @Override // org.jetbrains.kotlin.psi.KtPureClassOrObject
    public boolean isLocal() {
        KotlinClassOrObjectStub kotlinClassOrObjectStub = (KotlinClassOrObjectStub) getStub();
        return kotlinClassOrObjectStub != null ? kotlinClassOrObjectStub.isLocal() : KtPsiUtil.isLocal(this);
    }

    public final boolean isData() {
        return hasModifier(KtTokens.DATA_KEYWORD);
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationContainer
    public List<KtDeclaration> getDeclarations() {
        KtClassBody body = getBody();
        List<KtDeclaration> declarations = body != null ? body.getDeclarations() : null;
        return declarations == null ? CollectionsKt.emptyList() : declarations;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem
    public ItemPresentation getPresentation() {
        return ItemPresentationProviders.getItemPresentation(this);
    }

    @Override // org.jetbrains.kotlin.psi.KtPureClassOrObject
    public KtPrimaryConstructor getPrimaryConstructor() {
        return (KtPrimaryConstructor) getStubOrPsiChild(KtStubElementTypes.PRIMARY_CONSTRUCTOR);
    }

    @Override // org.jetbrains.kotlin.psi.KtPureClassOrObject
    public KtModifierList getPrimaryConstructorModifierList() {
        KtPrimaryConstructor primaryConstructor = getPrimaryConstructor();
        if (primaryConstructor != null) {
            return primaryConstructor.getModifierList();
        }
        return null;
    }

    public final KtParameterList getPrimaryConstructorParameterList() {
        KtPrimaryConstructor primaryConstructor = getPrimaryConstructor();
        if (primaryConstructor != null) {
            return primaryConstructor.getValueParameterList();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtPureClassOrObject
    public List<KtParameter> getPrimaryConstructorParameters() {
        KtParameterList primaryConstructorParameterList = getPrimaryConstructorParameterList();
        List<KtParameter> parameters = primaryConstructorParameterList != null ? primaryConstructorParameterList.getParameters() : null;
        return parameters == null ? CollectionsKt.emptyList() : parameters;
    }

    @Override // org.jetbrains.kotlin.psi.KtPureClassOrObject
    public boolean hasExplicitPrimaryConstructor() {
        return getPrimaryConstructor() != null;
    }

    @Override // org.jetbrains.kotlin.psi.KtPureClassOrObject
    public boolean hasPrimaryConstructor() {
        return hasExplicitPrimaryConstructor() || !hasSecondaryConstructors();
    }

    public final boolean hasSecondaryConstructors() {
        return !getSecondaryConstructors().isEmpty();
    }

    @Override // org.jetbrains.kotlin.psi.KtPureClassOrObject
    public List<KtSecondaryConstructor> getSecondaryConstructors() {
        KtClassBody body = getBody();
        List<KtSecondaryConstructor> secondaryConstructors$psi = body != null ? body.getSecondaryConstructors$psi() : null;
        return secondaryConstructors$psi == null ? CollectionsKt.emptyList() : secondaryConstructors$psi;
    }

    public final boolean isAnnotation() {
        return hasModifier(KtTokens.ANNOTATION_KEYWORD);
    }

    public final PsiElement getDeclarationKeyword() {
        return findChildByType(this.classInterfaceObjectTokenSet);
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void delete() {
        CheckUtil.checkWritable(this);
        KtFile containingKtFile = getContainingKtFile();
        Intrinsics.checkNotNullExpressionValue(containingKtFile, "getContainingKtFile(...)");
        if (!isTopLevel() || containingKtFile.getDeclarations().size() > 1) {
            super.delete();
        } else {
            containingKtFile.delete();
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement another) {
        String qualifiedName;
        KtClassOrObject ktClassOrObject;
        String qualifiedName2;
        boolean zIsLocal;
        if (this == another) {
            return true;
        }
        if ((another instanceof KtClassOrObject) && (qualifiedName = getQualifiedName()) != null && (qualifiedName2 = (ktClassOrObject = (KtClassOrObject) another).getQualifiedName()) != null && Intrinsics.areEqual(qualifiedName, qualifiedName2) && (zIsLocal = isLocal()) == ktClassOrObject.isLocal()) {
            return !zIsLocal;
        }
        return false;
    }

    protected final String getQualifiedName() {
        KotlinClassOrObjectStub kotlinClassOrObjectStub = (KotlinClassOrObjectStub) getStub();
        if (kotlinClassOrObjectStub != null) {
            FqName fqName = kotlinClassOrObjectStub.mo7402getFqName();
            if (fqName != null) {
                return fqName.asString();
            }
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (KtClassOrObject ktClassOrObject = this; ktClassOrObject != null; ktClassOrObject = (KtClassOrObject) PsiTreeUtil.getParentOfType(ktClassOrObject, KtClassOrObject.class)) {
            String name2 = ktClassOrObject.getName();
            if (name2 == null) {
                return null;
            }
            arrayList.add(name2);
        }
        PsiFile containingFile = getContainingFile();
        KtFile ktFile = containingFile instanceof KtFile ? (KtFile) containingFile : null;
        if (ktFile == null) {
            return null;
        }
        String strAsString = ktFile.getPackageFqName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        if (strAsString.length() != 0) {
            arrayList.add(strAsString);
        }
        CollectionsKt.reverse(arrayList);
        return CollectionsKt.joinToString$default(arrayList, ".", null, null, 0, null, null, 62, null);
    }

    public final KtContextReceiverList getContextReceiverList() {
        return (KtContextReceiverList) getStubOrPsiChild(KtStubElementTypes.CONTEXT_RECEIVER_LIST);
    }

    @Override // org.jetbrains.kotlin.psi.KtPureClassOrObject
    public List<KtContextReceiver> getContextReceivers() {
        KtContextReceiverList contextReceiverList = getContextReceiverList();
        return contextReceiverList != null ? contextReceiverList.contextReceivers() : CollectionsKt.emptyList();
    }
}
