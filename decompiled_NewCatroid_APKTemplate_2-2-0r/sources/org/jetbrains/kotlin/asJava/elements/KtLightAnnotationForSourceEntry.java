package org.jetbrains.kotlin.asJava.elements;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.classes.ImplUtilsKt;
import org.jetbrains.kotlin.asJava.elements.KtLightAnnotationForSourceEntry.KtLightAnnotationParameterList;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtCallElement;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtNameReferenceExpression;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtValueArgument;
import org.jetbrains.kotlin.psi.KtValueArgumentList;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.VarargValueArgument;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;

/* JADX INFO: compiled from: lightAnnotations.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010'\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0007\u0018\u00002\u00020\u0001:\u00017B/\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\b\u0010\u0017\u001a\u00020\u0018H\u0016J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0096\u0002J\u0014\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u0016J\u0014\u0010\u001f\u001a\u0004\u0018\u00010\u001e2\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010 \u001a\u0004\u0018\u00010\u001e2\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010!\u001a\u00020\u001aH\u0002J\u001e\u0010\"\u001a\u0010\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020%\u0018\u00010#2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\n\u0010&\u001a\u0004\u0018\u00010\u0003H\u0016J\b\u0010'\u001a\u00020(H\u0016J\n\u0010)\u001a\u0004\u0018\u00010*H\u0016J\b\u0010+\u001a\u00020,H\u0016J\n\u0010-\u001a\u0004\u0018\u00010\u0003H\u0016J\b\u0010.\u001a\u00020/H\u0016J-\u00100\u001a\u000201\"\n\b\u0000\u00102*\u0004\u0018\u00010\u001e2\b\u00103\u001a\u0004\u0018\u00010\u00032\b\u00104\u001a\u0004\u0018\u0001H2H\u0016¢\u0006\u0002\u00105J\b\u00106\u001a\u00020\u0003H\u0016R\u001d\u0010\u000b\u001a\u0004\u0018\u00010\u00038BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001f\u0010\u0012\u001a\u00060\u0013R\u00020\u00008BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u000f\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/asJava/elements/KtLightAnnotationForSourceEntry;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightAbstractAnnotation;", "name", "", "lazyQualifiedName", "Lkotlin/Function0;", "kotlinOrigin", "Lorg/jetbrains/kotlin/psi/KtCallElement;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "(Ljava/lang/String;Lkotlin/jvm/functions/Function0;Lorg/jetbrains/kotlin/psi/KtCallElement;Lcom/intellij/psi/PsiElement;)V", "_qualifiedName", "get_qualifiedName", "()Ljava/lang/String;", "_qualifiedName$delegate", "Lkotlin/Lazy;", "getKotlinOrigin", "()Lorg/jetbrains/kotlin/psi/KtCallElement;", "ktLightAnnotationParameterList", "Lorg/jetbrains/kotlin/asJava/elements/KtLightAnnotationForSourceEntry$KtLightAnnotationParameterList;", "getKtLightAnnotationParameterList", "()Lorg/jetbrains/kotlin/asJava/elements/KtLightAnnotationForSourceEntry$KtLightAnnotationParameterList;", "ktLightAnnotationParameterList$delegate", "delete", "", Namer.EQUALS_METHOD_NAME, "", "other", "", "findAttributeValue", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotationMemberValue;", "findDeclaredAttributeValue", "getAttributeValue", "useDefault", "getCallEntry", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedValueArgument;", "getName", "getNameReferenceElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiJavaCodeReferenceElement;", "getOwner", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotationOwner;", "getParameterList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotationParameterList;", "getQualifiedName", "hashCode", "", "setDeclaredAttributeValue", "", ExifInterface.GPS_DIRECTION_TRUE, "attributeName", "value", "(Ljava/lang/String;Lcom/intellij/psi/PsiAnnotationMemberValue;)Ljava/lang/Void;", "toString", "KtLightAnnotationParameterList", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtLightAnnotationForSourceEntry extends KtLightAbstractAnnotation {

    /* JADX INFO: renamed from: _qualifiedName$delegate, reason: from kotlin metadata */
    private final Lazy _qualifiedName;
    private final KtCallElement kotlinOrigin;

    /* JADX INFO: renamed from: ktLightAnnotationParameterList$delegate, reason: from kotlin metadata */
    private final Lazy ktLightAnnotationParameterList;
    private final Function0<String> lazyQualifiedName;
    private final String name;

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    /* JADX INFO: renamed from: setDeclaredAttributeValue */
    public /* bridge */ /* synthetic */ PsiAnnotationMemberValue mo6097setDeclaredAttributeValue(String str, PsiAnnotationMemberValue psiAnnotationMemberValue) {
        return (PsiAnnotationMemberValue) setDeclaredAttributeValue(str, psiAnnotationMemberValue);
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.asJava.elements.KtLightElement
    public KtCallElement getKotlinOrigin() {
        return this.kotlinOrigin;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtLightAnnotationForSourceEntry(String str, Function0<String> lazyQualifiedName, KtCallElement kotlinOrigin, PsiElement parent) {
        super(parent);
        Intrinsics.checkNotNullParameter(lazyQualifiedName, "lazyQualifiedName");
        Intrinsics.checkNotNullParameter(kotlinOrigin, "kotlinOrigin");
        Intrinsics.checkNotNullParameter(parent, "parent");
        this.name = str;
        this.lazyQualifiedName = lazyQualifiedName;
        this.kotlinOrigin = kotlinOrigin;
        this._qualifiedName = ImplUtilsKt.lazyPub(new Function0<String>() { // from class: org.jetbrains.kotlin.asJava.elements.KtLightAnnotationForSourceEntry$_qualifiedName$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                return (String) this.this$0.lazyQualifiedName.invoke();
            }
        });
        this.ktLightAnnotationParameterList = ImplUtilsKt.lazyPub(new Function0<KtLightAnnotationParameterList>() { // from class: org.jetbrains.kotlin.asJava.elements.KtLightAnnotationForSourceEntry$ktLightAnnotationParameterList$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KtLightAnnotationForSourceEntry.KtLightAnnotationParameterList invoke() {
                return this.this$0.new KtLightAnnotationParameterList();
            }
        });
    }

    private final String get_qualifiedName() {
        return (String) this._qualifiedName.getValue();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightAbstractAnnotation, org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    public PsiAnnotationOwner getOwner() {
        PsiElement parent = getMyParent();
        if (parent instanceof PsiAnnotationOwner) {
            return (PsiAnnotationOwner) parent;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    /* JADX INFO: renamed from: getQualifiedName */
    public String getAnnotationFqName() {
        return get_qualifiedName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName, reason: from getter */
    public String getMyName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    public PsiAnnotationMemberValue findAttributeValue(String name2) {
        return getAttributeValue(name2, true);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    public PsiAnnotationMemberValue findDeclaredAttributeValue(String name2) {
        return getAttributeValue(name2, false);
    }

    private final Map.Entry<ValueParameterDescriptor, ResolvedValueArgument> getCallEntry(String name2) {
        ResolvedCall resolvedCall = LightAnnotationsKt.getResolvedCall(getKotlinOrigin());
        Object obj = null;
        if (resolvedCall == null) {
            return null;
        }
        for (Object obj2 : resolvedCall.getValueArguments().entrySet()) {
            if (Intrinsics.areEqual(((ValueParameterDescriptor) ((Map.Entry) obj2).getKey()).getShortName().asString(), name2)) {
                obj = obj2;
                break;
            }
        }
        return (Map.Entry) obj;
    }

    private final PsiAnnotationMemberValue getAttributeValue(String name2, boolean useDefault) {
        PsiNameValuePair psiNameValuePair;
        PsiNameValuePair[] attributes = getKtLightAnnotationParameterList().getAttributes();
        int length = attributes.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                psiNameValuePair = null;
                break;
            }
            psiNameValuePair = attributes[i];
            if (Intrinsics.areEqual(psiNameValuePair.getName(), name2 == null ? "value" : name2)) {
                break;
            }
            i++;
        }
        if (psiNameValuePair != null) {
            return psiNameValuePair.getValue();
        }
        if (useDefault) {
            if (name2 == null) {
                name2 = "value";
            }
            Map.Entry<ValueParameterDescriptor, ResolvedValueArgument> callEntry = getCallEntry(name2);
            if (callEntry != null && DescriptorUtilsKt.declaresOrInheritsDefaultValue(callEntry.getKey())) {
                SourceElement source = callEntry.getKey().getSourceElement();
                Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
                PsiElement psi = PsiSourceElementKt.getPsi(source);
                if (psi instanceof KtParameter) {
                    KtExpression defaultValue = ((KtParameter) psi).getDefaultValue();
                    if (defaultValue != null) {
                        return LightAnnotationsKt.convertToLightAnnotationMemberValue(getKtLightAnnotationParameterList(), defaultValue);
                    }
                    return null;
                }
                if (psi instanceof PsiAnnotationMethod) {
                    return ((PsiAnnotationMethod) psi).getDefaultValue();
                }
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightAbstractAnnotation, org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    public PsiJavaCodeReferenceElement getNameReferenceElement() {
        PsiElement navigationElement = getKotlinOrigin().getNavigationElement();
        Intrinsics.checkNotNullExpressionValue(navigationElement, "getNavigationElement(...)");
        return new KtLightPsiJavaCodeReferenceElement(navigationElement, new Function0<PsiReference>() { // from class: org.jetbrains.kotlin.asJava.elements.KtLightAnnotationForSourceEntry.getNameReferenceElement.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiReference invoke() {
                KtNameReferenceExpression nameReference;
                PsiReference[] references;
                KtTypeReference typeReference;
                PsiReference reference;
                KtCallElement kotlinOrigin = KtLightAnnotationForSourceEntry.this.getKotlinOrigin();
                KtAnnotationEntry ktAnnotationEntry = kotlinOrigin instanceof KtAnnotationEntry ? (KtAnnotationEntry) kotlinOrigin : null;
                if (ktAnnotationEntry != null && (typeReference = ktAnnotationEntry.getTypeReference()) != null && (reference = typeReference.getReference()) != null) {
                    return reference;
                }
                KtExpression calleeExpression = KtLightAnnotationForSourceEntry.this.getKotlinOrigin().getCalleeExpression();
                if (calleeExpression == null || (nameReference = LightAnnotationsKt.getNameReference(calleeExpression)) == null || (references = nameReference.getReferences()) == null) {
                    return null;
                }
                return (PsiReference) ArraysKt.firstOrNull(references);
            }
        }, Intrinsics.areEqual(getAnnotationFqName(), CommonClassNames.JAVA_LANG_ANNOTATION_REPEATABLE) ? KtToJvmAnnotationsConverterKt.getJAVA_LANG_ANNOTATION_REPEATABLE_SHORT_NAME() : null);
    }

    private final KtLightAnnotationParameterList getKtLightAnnotationParameterList() {
        return (KtLightAnnotationParameterList) this.ktLightAnnotationParameterList.getValue();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightAbstractAnnotation, org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    public PsiAnnotationParameterList getParameterList() {
        return getKtLightAnnotationParameterList();
    }

    /* JADX INFO: compiled from: lightAnnotations.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u001c\u0010\u000f\u001a\u00020\u00102\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\u0012H\u0002J\u0013\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0016¢\u0006\u0002\u0010\bJ\u001e\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\u0012H\u0002J\u001c\u0010\u0019\u001a\u00020\u00172\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\u0012H\u0002R!\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u000b\u001a\u0004\u0018\u00010\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/asJava/elements/KtLightAnnotationForSourceEntry$KtLightAnnotationParameterList;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightElementBase;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotationParameterList;", "(Lorg/jetbrains/kotlin/asJava/elements/KtLightAnnotationForSourceEntry;)V", "_attributes", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiNameValuePair;", "get_attributes", "()[Lcom/intellij/psi/PsiNameValuePair;", "_attributes$delegate", "Lkotlin/Lazy;", "kotlinOrigin", "Lorg/jetbrains/kotlin/psi/KtElement;", "getKotlinOrigin", "()Lorg/jetbrains/kotlin/psi/KtElement;", "checkIfToArrayConversionExpected", "", "callEntry", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedValueArgument;", "getAttributes", "getNotWrappedToArrayNameValuePair", "Lorg/jetbrains/kotlin/asJava/elements/KtLightPsiNameValuePair;", "resolvedArgumentEntry", "getWrappedToArrayNameValuePair", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class KtLightAnnotationParameterList extends KtLightElementBase implements PsiAnnotationParameterList {

        /* JADX INFO: renamed from: _attributes$delegate, reason: from kotlin metadata */
        private final Lazy _attributes;

        @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.asJava.elements.KtLightElement
        public KtElement getKotlinOrigin() {
            return null;
        }

        public KtLightAnnotationParameterList() {
            super(KtLightAnnotationForSourceEntry.this);
            this._attributes = ImplUtilsKt.lazyPub(new Function0<PsiNameValuePair[]>() { // from class: org.jetbrains.kotlin.asJava.elements.KtLightAnnotationForSourceEntry$KtLightAnnotationParameterList$_attributes$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final PsiNameValuePair[] invoke() {
                    if (!ktLightAnnotationForSourceEntry.getKotlinOrigin().getValueArguments().isEmpty()) {
                        ResolvedCall resolvedCall = LightAnnotationsKt.getResolvedCall(ktLightAnnotationForSourceEntry.getKotlinOrigin());
                        Map<ValueParameterDescriptor, ResolvedValueArgument> valueArguments = resolvedCall != null ? resolvedCall.getValueArguments() : null;
                        if (valueArguments != null) {
                            KtLightAnnotationForSourceEntry.KtLightAnnotationParameterList ktLightAnnotationParameterList = this;
                            ArrayList arrayList = new ArrayList();
                            for (Map.Entry<ValueParameterDescriptor, ResolvedValueArgument> entry : valueArguments.entrySet()) {
                                KtLightPsiNameValuePair wrappedToArrayNameValuePair = ktLightAnnotationParameterList.checkIfToArrayConversionExpected(entry) ? ktLightAnnotationParameterList.getWrappedToArrayNameValuePair(entry) : ktLightAnnotationParameterList.getNotWrappedToArrayNameValuePair(entry);
                                if (wrappedToArrayNameValuePair != null) {
                                    arrayList.add(wrappedToArrayNameValuePair);
                                }
                            }
                            return (PsiNameValuePair[]) arrayList.toArray(new PsiNameValuePair[0]);
                        }
                        return new PsiNameValuePair[0];
                    }
                    return new PsiNameValuePair[0];
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean checkIfToArrayConversionExpected(Map.Entry<? extends ValueParameterDescriptor, ? extends ResolvedValueArgument> callEntry) {
            if (!ArgumentsUtilsKt.isVararg(callEntry.getKey())) {
                return false;
            }
            List<ValueArgument> arguments = callEntry.getValue().getArguments();
            Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
            ValueArgument valueArgument = (ValueArgument) CollectionsKt.firstOrNull((List) arguments);
            if (valueArgument == null) {
                return false;
            }
            if (valueArgument instanceof VarargValueArgument) {
                return true;
            }
            if ((valueArgument instanceof KtValueArgument) && ((KtValueArgument) valueArgument).isSpread()) {
                return false;
            }
            return !valueArgument.isNamed();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final KtLightPsiNameValuePair getWrappedToArrayNameValuePair(Map.Entry<? extends ValueParameterDescriptor, ? extends ResolvedValueArgument> resolvedArgumentEntry) {
            List<ValueArgument> arguments = resolvedArgumentEntry.getValue().getArguments();
            Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = arguments.iterator();
            while (it.hasNext()) {
                KtExpression argumentExpression = ((ValueArgument) it.next()).getArgumentExpression();
                if (argumentExpression != null) {
                    arrayList.add(argumentExpression);
                }
            }
            final ArrayList arrayList2 = arrayList;
            PsiElement psiElementFindCommonParent = PsiTreeUtil.findCommonParent(arrayList2);
            final KtCallElement kotlinOrigin = psiElementFindCommonParent instanceof KtElement ? (KtElement) psiElementFindCommonParent : null;
            if (kotlinOrigin == null) {
                KtValueArgumentList valueArgumentList = KtLightAnnotationForSourceEntry.this.getKotlinOrigin().getValueArgumentList();
                if (valueArgumentList != null) {
                    kotlinOrigin = valueArgumentList;
                } else {
                    kotlinOrigin = KtLightAnnotationForSourceEntry.this.getKotlinOrigin();
                }
            }
            String strAsString = resolvedArgumentEntry.getKey().getShortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return new KtLightPsiNameValuePair(kotlinOrigin, strAsString, this, new Function1<KtLightPsiNameValuePair, PsiAnnotationMemberValue>() { // from class: org.jetbrains.kotlin.asJava.elements.KtLightAnnotationForSourceEntry$KtLightAnnotationParameterList$getWrappedToArrayNameValuePair$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final PsiAnnotationMemberValue invoke(KtLightPsiNameValuePair self) {
                    Intrinsics.checkNotNullParameter(self, "self");
                    final List<KtExpression> list = arrayList2;
                    return new KtLightPsiArrayInitializerMemberValue(kotlinOrigin, self, new Function1<KtLightPsiArrayInitializerMemberValue, List<? extends PsiAnnotationMemberValue>>() { // from class: org.jetbrains.kotlin.asJava.elements.KtLightAnnotationForSourceEntry$KtLightAnnotationParameterList$getWrappedToArrayNameValuePair$1.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        /* JADX WARN: Multi-variable type inference failed */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final List<PsiAnnotationMemberValue> invoke(KtLightPsiArrayInitializerMemberValue memberValue) {
                            Intrinsics.checkNotNullParameter(memberValue, "memberValue");
                            List<KtExpression> list2 = list;
                            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                            Iterator<T> it2 = list2.iterator();
                            while (it2.hasNext()) {
                                arrayList3.add(LightAnnotationsKt.convertToLightAnnotationMemberValue(memberValue, (KtExpression) it2.next()));
                            }
                            return arrayList3;
                        }
                    });
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final KtLightPsiNameValuePair getNotWrappedToArrayNameValuePair(Map.Entry<? extends ValueParameterDescriptor, ? extends ResolvedValueArgument> resolvedArgumentEntry) {
            final KtExpression argumentExpression;
            List<ValueArgument> arguments = resolvedArgumentEntry.getValue().getArguments();
            Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
            ValueArgument valueArgument = (ValueArgument) CollectionsKt.firstOrNull((List) arguments);
            if (valueArgument == null || (argumentExpression = valueArgument.getArgumentExpression()) == null) {
                return null;
            }
            String strAsString = resolvedArgumentEntry.getKey().getShortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return new KtLightPsiNameValuePair(valueArgument.getCallElement(), strAsString, this, new Function1<KtLightPsiNameValuePair, PsiAnnotationMemberValue>() { // from class: org.jetbrains.kotlin.asJava.elements.KtLightAnnotationForSourceEntry$KtLightAnnotationParameterList$getNotWrappedToArrayNameValuePair$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final PsiAnnotationMemberValue invoke(KtLightPsiNameValuePair valuePair) {
                    Intrinsics.checkNotNullParameter(valuePair, "valuePair");
                    return LightAnnotationsKt.convertToLightAnnotationMemberValue(valuePair, argumentExpression);
                }
            });
        }

        private final PsiNameValuePair[] get_attributes() {
            return (PsiNameValuePair[]) this._attributes.getValue();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationParameterList
        public PsiNameValuePair[] getAttributes() {
            return get_attributes();
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void delete() {
        getKotlinOrigin().delete();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + getAnnotationFqName();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !Intrinsics.areEqual(other.getClass(), getClass())) {
            return false;
        }
        return Intrinsics.areEqual(getKotlinOrigin(), ((KtLightAnnotationForSourceEntry) other).getKotlinOrigin());
    }

    public int hashCode() {
        return getKotlinOrigin().hashCode();
    }

    public <T extends PsiAnnotationMemberValue> Void setDeclaredAttributeValue(String str, T t) {
        ImplUtilsKt.cannotModify(this);
        throw null;
    }
}
