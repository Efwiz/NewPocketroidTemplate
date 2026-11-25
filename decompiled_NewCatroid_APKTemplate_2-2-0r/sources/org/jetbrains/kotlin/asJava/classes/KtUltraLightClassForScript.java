package org.jetbrains.kotlin.asJava.classes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.analyzer.KotlinModificationTrackerService;
import org.jetbrains.kotlin.asJava.LightClassUtilsKt;
import org.jetbrains.kotlin.asJava.elements.KtLightField;
import org.jetbrains.kotlin.asJava.elements.KtLightMethod;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightMethodBuilder;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValue;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtScript;

/* JADX INFO: compiled from: ultraLightClassForScript.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0010!\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u001fB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0016\u001a\u00020\u0000H\u0016J\u000e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0016J\u000e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\bH\u0016J\u000e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00100\bH\u0016J\u000e\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00100\bH\u0002J\u0012\u0010\u001c\u001a\u00020\u001d*\b\u0012\u0004\u0012\u00020\u00100\u001eH\u0002R!\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\b0\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0011\u001a\u00020\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\r\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightClassForScript;", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClassForScriptBase;", "script", "Lorg/jetbrains/kotlin/psi/KtScript;", "support", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "(Lorg/jetbrains/kotlin/psi/KtScript;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;)V", "_ownFields", "", "Lorg/jetbrains/kotlin/asJava/elements/KtLightField;", "get_ownFields", "()Ljava/util/List;", "_ownFields$delegate", "Lkotlin/Lazy;", "_ownMethods", "Lorg/jetbrains/kotlin/com/intellij/psi/util/CachedValue;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;", "membersBuilder", "Lorg/jetbrains/kotlin/asJava/classes/UltraLightMembersCreator;", "getMembersBuilder", "()Lorg/jetbrains/kotlin/asJava/classes/UltraLightMembersCreator;", "membersBuilder$delegate", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "getOwnFields", "getOwnInnerClasses", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "getOwnMethods", "ownMethods", "addScriptDefaultMethods", "", "", "KtUltraLightScriptMainParameter", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtUltraLightClassForScript extends KtLightClassForScriptBase {

    /* JADX INFO: renamed from: _ownFields$delegate, reason: from kotlin metadata */
    private final Lazy _ownFields;
    private final CachedValue<List<KtLightMethod>> _ownMethods;

    /* JADX INFO: renamed from: membersBuilder$delegate, reason: from kotlin metadata */
    private final Lazy membersBuilder;
    private final KtUltraLightSupport support;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtUltraLightClassForScript(final KtScript script, KtUltraLightSupport support) {
        super(script);
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(support, "support");
        this.support = support;
        this.membersBuilder = ImplUtilsKt.lazyPub(new Function0<UltraLightMembersCreator>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightClassForScript$membersBuilder$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final UltraLightMembersCreator invoke() {
                KtUltraLightClassForScript ktUltraLightClassForScript = this.this$0;
                return new UltraLightMembersCreator(ktUltraLightClassForScript, false, true, true, ktUltraLightClassForScript.support);
            }
        });
        CachedValue<List<KtLightMethod>> cachedValueCreateCachedValue = CachedValuesManager.getManager(getProject()).createCachedValue(new CachedValueProvider() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightClassForScript$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return KtUltraLightClassForScript._ownMethods$lambda$2(this.f$0);
            }
        }, false);
        Intrinsics.checkNotNullExpressionValue(cachedValueCreateCachedValue, "createCachedValue(...)");
        this._ownMethods = cachedValueCreateCachedValue;
        this._ownFields = ImplUtilsKt.lazyPub(new Function0<ArrayList<KtLightField>>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightClassForScript$_ownFields$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ArrayList<KtLightField> invoke() {
                ArrayList<KtLightField> arrayList = new ArrayList<>();
                HashSet<String> hashSet = new HashSet<>();
                List<KtDeclaration> declarations = script.getDeclarations();
                Intrinsics.checkNotNullExpressionValue(declarations, "getDeclarations(...)");
                ArrayList arrayList2 = new ArrayList();
                for (Object obj : declarations) {
                    if (obj instanceof KtProperty) {
                        arrayList2.add(obj);
                    }
                }
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    KtLightField ktLightFieldCreatePropertyField = this.getMembersBuilder().createPropertyField((KtProperty) it.next(), hashSet, false);
                    if (ktLightFieldCreatePropertyField != null) {
                        arrayList.add(ktLightFieldCreatePropertyField);
                    }
                }
                return arrayList;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final UltraLightMembersCreator getMembersBuilder() {
        return (UltraLightMembersCreator) this.membersBuilder.getValue();
    }

    /* JADX INFO: compiled from: ultraLightClassForScript.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0080\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightClassForScript$KtUltraLightScriptMainParameter;", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightParameter;", "mainMethod", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethod;", "(Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightClassForScript;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethod;)V", "qualifiedNameForNullabilityAnnotation", "", "getQualifiedNameForNullabilityAnnotation", "()Ljava/lang/String;", "getType", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "isVarArgs", "", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class KtUltraLightScriptMainParameter extends KtUltraLightParameter {
        private final String qualifiedNameForNullabilityAnnotation;
        final /* synthetic */ KtUltraLightClassForScript this$0;

        @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightParameter, org.jetbrains.kotlin.asJava.elements.LightParameter, org.jetbrains.kotlin.com.intellij.psi.PsiParameter
        /* JADX INFO: renamed from: isVarArgs */
        public boolean getMyVarArgs() {
            return false;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public KtUltraLightScriptMainParameter(KtUltraLightClassForScript ktUltraLightClassForScript, KtUltraLightMethod mainMethod) {
            super("args", null, ktUltraLightClassForScript.support, mainMethod);
            Intrinsics.checkNotNullParameter(mainMethod, "mainMethod");
            this.this$0 = ktUltraLightClassForScript;
        }

        @Override // org.jetbrains.kotlin.asJava.elements.LightParameter, org.jetbrains.kotlin.com.intellij.psi.PsiParameter, org.jetbrains.kotlin.com.intellij.lang.jvm.JvmParameter
        public PsiType getType() {
            PsiArrayType psiArrayTypeCreateArrayType = PsiType.getJavaLangString(getManager(), getResolveScope()).createArrayType();
            Intrinsics.checkNotNullExpressionValue(psiArrayTypeCreateArrayType, "createArrayType(...)");
            return psiArrayTypeCreateArrayType;
        }

        @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightElementWithNullabilityAnnotation
        public String getQualifiedNameForNullabilityAnnotation() {
            return this.qualifiedNameForNullabilityAnnotation;
        }
    }

    private final void addScriptDefaultMethods(List<KtLightMethod> list) {
        LightMethodBuilder lightMethodBuilderAddModifier = new LightMethodBuilder(getManager(), getLanguage(), get$name()).setConstructor(true).addModifier("public");
        Intrinsics.checkNotNull(lightMethodBuilderAddModifier);
        KtUltraLightClassForScript ktUltraLightClassForScript = this;
        KtUltraLightMethodForSourceDeclaration ktUltraLightMethodForSourceDeclaration = new KtUltraLightMethodForSourceDeclaration(lightMethodBuilderAddModifier, getScript(), this.support, ktUltraLightClassForScript, 4);
        lightMethodBuilderAddModifier.addParameter(new KtUltraLightScriptMainParameter(this, ktUltraLightMethodForSourceDeclaration));
        list.add(ktUltraLightMethodForSourceDeclaration);
        LightMethodBuilder lightMethodBuilder = new LightMethodBuilder(getManager(), getLanguage(), "main");
        lightMethodBuilder.setConstructor(false);
        lightMethodBuilder.addModifiers("public", "static", "final");
        lightMethodBuilder.setMethodReturnType(PsiType.VOID);
        KtUltraLightMethodForSourceDeclaration ktUltraLightMethodForSourceDeclaration2 = new KtUltraLightMethodForSourceDeclaration(lightMethodBuilder, getScript(), this.support, ktUltraLightClassForScript, 7);
        lightMethodBuilder.addParameter(new KtUltraLightScriptMainParameter(this, ktUltraLightMethodForSourceDeclaration2));
        list.add(ktUltraLightMethodForSourceDeclaration2);
    }

    private final List<KtLightMethod> ownMethods() {
        ArrayList arrayList = new ArrayList();
        addScriptDefaultMethods(arrayList);
        List<KtDeclaration> declarations = getScript().getDeclarations();
        Intrinsics.checkNotNullExpressionValue(declarations, "getDeclarations(...)");
        ArrayList<KtDeclaration> arrayList2 = new ArrayList();
        for (Object obj : declarations) {
            KtDeclaration ktDeclaration = (KtDeclaration) obj;
            Intrinsics.checkNotNull(ktDeclaration);
            if (!UltraLightUtilsKt.isHiddenByDeprecation(ktDeclaration, this.support)) {
                arrayList2.add(obj);
            }
        }
        for (KtDeclaration ktDeclaration2 : arrayList2) {
            if (ktDeclaration2 instanceof KtNamedFunction) {
                UltraLightMembersCreator membersBuilder = getMembersBuilder();
                Intrinsics.checkNotNull(ktDeclaration2);
                arrayList.addAll(UltraLightMembersCreator.createMethods$default(membersBuilder, (KtFunction) ktDeclaration2, false, false, false, null, 28, null));
            } else if (ktDeclaration2 instanceof KtProperty) {
                UltraLightMembersCreator membersBuilder2 = getMembersBuilder();
                Intrinsics.checkNotNull(ktDeclaration2);
                arrayList.addAll(membersBuilder2.propertyAccessors((KtCallableDeclaration) ktDeclaration2, ((KtProperty) ktDeclaration2).isVar(), false, false, (48 & 16) != 0 ? false : false, (48 & 32) != 0 ? false : false, (48 & 64) != 0 ? false : false, (48 & 128) != 0 ? null : null));
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CachedValueProvider.Result _ownMethods$lambda$2(KtUltraLightClassForScript this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        List<KtLightMethod> listOwnMethods = this$0.ownMethods();
        KotlinModificationTrackerService.Companion companion = KotlinModificationTrackerService.INSTANCE;
        Project project = this$0.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        return CachedValueProvider.Result.create(listOwnMethods, companion.getInstance(project).getOutOfBlockModificationTracker());
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassBase, org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public List<KtLightMethod> getOwnMethods() {
        List<KtLightMethod> value = this._ownMethods.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return value;
    }

    private final List<KtLightField> get_ownFields() {
        return (List) this._ownFields.getValue();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassBase, org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public List<KtLightField> getOwnFields() {
        return get_ownFields();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForScriptBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public KtUltraLightClassForScript copy() {
        return new KtUltraLightClassForScript(getScript(), this.support);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public List<PsiClass> getOwnInnerClasses() {
        List<KtDeclaration> declarations = getScript().getDeclarations();
        Intrinsics.checkNotNullExpressionValue(declarations, "getDeclarations(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof KtClassOrObject) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : arrayList) {
            if (((KtClassOrObject) obj2).get$name() != null) {
                arrayList2.add(obj2);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            KtLightClass lightClass = LightClassUtilsKt.toLightClass((KtClassOrObject) it.next());
            if (lightClass != null) {
                arrayList3.add(lightClass);
            }
        }
        return arrayList3;
    }
}
