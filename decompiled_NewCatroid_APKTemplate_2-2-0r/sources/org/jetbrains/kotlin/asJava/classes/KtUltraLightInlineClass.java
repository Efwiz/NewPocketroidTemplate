package org.jetbrains.kotlin.asJava.classes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.asJava.elements.KtLightField;
import org.jetbrains.kotlin.asJava.elements.KtLightMethod;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiSuperMethodImplUtil;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPrimaryConstructor;
import org.jetbrains.kotlin.psi.KtProperty;

/* JADX INFO: compiled from: KtUltraLightInlineClass.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0013\u001a\u00020\u0000H\u0016J\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\bH\u0016J\u000e\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0016J\n\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016J\u000e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aH\u0016R!\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000bR\u001b\u0010\u000e\u001a\u00020\u000f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\r\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightInlineClass;", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightClass;", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "support", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "(Lorg/jetbrains/kotlin/psi/KtClassOrObject;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;)V", "_ownMethods", "", "Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;", "get_ownMethods", "()Ljava/util/List;", "_ownMethods$delegate", "Lkotlin/Lazy;", "membersBuilder", "Lorg/jetbrains/kotlin/asJava/classes/UltraLightMembersCreator;", "getMembersBuilder", "()Lorg/jetbrains/kotlin/asJava/classes/UltraLightMembersCreator;", "membersBuilder$delegate", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "getOwnFields", "Lorg/jetbrains/kotlin/asJava/elements/KtLightField;", "getOwnMethods", "getScope", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getVisibleSignatures", "", "Lorg/jetbrains/kotlin/com/intellij/psi/HierarchicalMethodSignature;", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtUltraLightInlineClass extends KtUltraLightClass {

    /* JADX INFO: renamed from: _ownMethods$delegate, reason: from kotlin metadata */
    private final Lazy _ownMethods;

    /* JADX INFO: renamed from: membersBuilder$delegate, reason: from kotlin metadata */
    private final Lazy membersBuilder;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtUltraLightInlineClass(final KtClassOrObject classOrObject, final KtUltraLightSupport support) {
        super(classOrObject, support);
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        Intrinsics.checkNotNullParameter(support, "support");
        this.membersBuilder = ImplUtilsKt.lazyPub(new Function0<UltraLightMembersCreator>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightInlineClass$membersBuilder$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final UltraLightMembersCreator invoke() {
                return new UltraLightMembersCreator(this.this$0, false, false, false, support);
            }
        });
        this._ownMethods = ImplUtilsKt.lazyPub(new Function0<ArrayList<KtLightMethod>>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightInlineClass$_ownMethods$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ArrayList<KtLightMethod> invoke() {
                List<KtParameter> valueParameters;
                ArrayList<KtLightMethod> arrayList = new ArrayList<>();
                List<KtDeclaration> declarations = this.this$0.getClassOrObject().getDeclarations();
                ArrayList arrayList2 = new ArrayList();
                for (Object obj : declarations) {
                    if (((KtDeclaration) obj).hasModifier(KtTokens.OVERRIDE_KEYWORD)) {
                        arrayList2.add(obj);
                    }
                }
                KtUltraLightSupport ktUltraLightSupport = support;
                ArrayList<KtDeclaration> arrayList3 = new ArrayList();
                for (Object obj2 : arrayList2) {
                    if (!UltraLightUtilsKt.isHiddenByDeprecation((KtDeclaration) obj2, ktUltraLightSupport)) {
                        arrayList3.add(obj2);
                    }
                }
                for (KtDeclaration ktDeclaration : arrayList3) {
                    if (ktDeclaration instanceof KtNamedFunction) {
                        arrayList.addAll(UltraLightMembersCreator.createMethods$default(this.this$0.getMembersBuilder(), (KtFunction) ktDeclaration, false, false, false, null, 28, null));
                    } else if (ktDeclaration instanceof KtProperty) {
                        arrayList.addAll(this.this$0.getMembersBuilder().propertyAccessors((KtCallableDeclaration) ktDeclaration, ((KtProperty) ktDeclaration).isVar(), false, false, (48 & 16) != 0 ? false : false, (48 & 32) != 0 ? false : false, (48 & 64) != 0 ? false : false, (48 & 128) != 0 ? null : null));
                    }
                }
                KtPrimaryConstructor primaryConstructor = classOrObject.getPrimaryConstructor();
                KtParameter ktParameter = (primaryConstructor == null || (valueParameters = primaryConstructor.getValueParameters()) == null) ? null : (KtParameter) CollectionsKt.firstOrNull((List) valueParameters);
                if (ktParameter != null) {
                    arrayList.addAll(this.this$0.getMembersBuilder().propertyAccessors(ktParameter, false, false, false, (48 & 16) != 0 ? false : false, (48 & 32) != 0 ? false : false, (48 & 64) != 0 ? false : false, (48 & 128) != 0 ? null : null));
                }
                return arrayList;
            }
        });
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiElement getScope() {
        return getLightParent();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final UltraLightMembersCreator getMembersBuilder() {
        return (UltraLightMembersCreator) this.membersBuilder.getValue();
    }

    private final List<KtLightMethod> get_ownMethods() {
        return (List) this._ownMethods.getValue();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.asJava.classes.KtLightClassBase, org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public List<KtLightField> getOwnFields() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.asJava.classes.KtLightClassBase, org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public List<KtLightMethod> getOwnMethods() {
        return get_ownMethods();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public Collection<HierarchicalMethodSignature> getVisibleSignatures() {
        Collection<HierarchicalMethodSignature> visibleSignatures = PsiSuperMethodImplUtil.getVisibleSignatures(this);
        Intrinsics.checkNotNullExpressionValue(visibleSignatures, "getVisibleSignatures(...)");
        return visibleSignatures;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.asJava.classes.KtLightClassImpl, org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public KtUltraLightInlineClass copy() {
        return new KtUltraLightInlineClass(getClassOrObject(), getSupport());
    }
}
