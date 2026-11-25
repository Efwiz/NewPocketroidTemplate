package org.jetbrains.kotlin.asJava.classes;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.elements.KtLightAbstractAnnotation;
import org.jetbrains.kotlin.asJava.elements.KtLightElementBase;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsJavaCodeReferenceElementImpl;
import org.jetbrains.kotlin.psi.KtCallElement;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;

/* JADX INFO: compiled from: ultraLightAnnotations.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0002\b\u0006\u0018\u00002\u00020\u0001:\u0001)BI\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u001c\u0010\u0004\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u0003\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\u00060\u0005\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0012\b\u0002\u0010\n\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\f\u0018\u00010\u000b¢\u0006\u0002\u0010\rJ\u0014\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0003H\u0016J\u0019\u0010\u001c\u001a\t\u0018\u00010\u001a¢\u0006\u0002\b\u001d2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0003H\u0016J\n\u0010\u001e\u001a\u0004\u0018\u00010\fH\u0016J\b\u0010\u001f\u001a\u00020 H\u0016J\n\u0010!\u001a\u0004\u0018\u00010\u0003H\u0016J\b\u0010\"\u001a\u00020\u0003H\u0016J-\u0010#\u001a\u00020$\"\n\b\u0000\u0010%*\u0004\u0018\u00010\u001a2\b\u0010&\u001a\u0004\u0018\u00010\u00032\b\u0010'\u001a\u0004\u0018\u0001H%H\u0016¢\u0006\u0002\u0010(R\u001d\u0010\u000e\u001a\u0004\u0018\u00010\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\u0004\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u0003\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0013\u001a\u0004\u0018\u00010\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0018\u0010\n\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\f\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0017\u001a\u00060\u0018R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSimpleAnnotation;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightAbstractAnnotation;", "annotationFqName", "", "argumentsList", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "nameReferenceElementProvider", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiJavaCodeReferenceElement;", "(Ljava/lang/String;Ljava/util/List;Lcom/intellij/psi/PsiElement;Lkotlin/jvm/functions/Function0;)V", "_nameReferenceElement", "get_nameReferenceElement", "()Lcom/intellij/psi/PsiJavaCodeReferenceElement;", "_nameReferenceElement$delegate", "Lkotlin/Lazy;", "kotlinOrigin", "Lorg/jetbrains/kotlin/psi/KtCallElement;", "getKotlinOrigin", "()Lorg/jetbrains/kotlin/psi/KtCallElement;", "parameterList", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSimpleAnnotation$ParameterListImpl;", "findAttributeValue", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotationMemberValue;", "attributeName", "findDeclaredAttributeValue", "Lorg/jetbrains/annotations/Nullable;", "getNameReferenceElement", "getParameterList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotationParameterList;", "getQualifiedName", "getText", "setDeclaredAttributeValue", "", ExifInterface.GPS_DIRECTION_TRUE, "p0", "p1", "(Ljava/lang/String;Lcom/intellij/psi/PsiAnnotationMemberValue;)Ljava/lang/Void;", "ParameterListImpl", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtUltraLightSimpleAnnotation extends KtLightAbstractAnnotation {

    /* JADX INFO: renamed from: _nameReferenceElement$delegate, reason: from kotlin metadata */
    private final Lazy _nameReferenceElement;
    private final String annotationFqName;
    private final List<Pair<String, ConstantValue<?>>> argumentsList;
    private final Function0<PsiJavaCodeReferenceElement> nameReferenceElementProvider;
    private final ParameterListImpl parameterList;

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.asJava.elements.KtLightElement
    public KtCallElement getKotlinOrigin() {
        return null;
    }

    public /* synthetic */ KtUltraLightSimpleAnnotation(String str, List list, PsiElement psiElement, Function0 function0, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, list, psiElement, (i & 8) != 0 ? null : function0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    /* JADX INFO: renamed from: setDeclaredAttributeValue, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ PsiAnnotationMemberValue mo6097setDeclaredAttributeValue(String str, PsiAnnotationMemberValue psiAnnotationMemberValue) {
        return (PsiAnnotationMemberValue) setDeclaredAttributeValue(str, psiAnnotationMemberValue);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public KtUltraLightSimpleAnnotation(String str, List<? extends Pair<String, ? extends ConstantValue<?>>> argumentsList, final PsiElement parent, Function0<? extends PsiJavaCodeReferenceElement> function0) {
        super(parent);
        Intrinsics.checkNotNullParameter(argumentsList, "argumentsList");
        Intrinsics.checkNotNullParameter(parent, "parent");
        this.annotationFqName = str;
        this.argumentsList = argumentsList;
        this.nameReferenceElementProvider = function0;
        this._nameReferenceElement = ImplUtilsKt.lazyPub(new Function0<PsiJavaCodeReferenceElement>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightSimpleAnnotation$_nameReferenceElement$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiJavaCodeReferenceElement invoke() {
                PsiJavaCodeReferenceElement psiJavaCodeReferenceElement;
                Function0 function1 = this.this$0.nameReferenceElementProvider;
                if (function1 != null && (psiJavaCodeReferenceElement = (PsiJavaCodeReferenceElement) function1.invoke()) != null) {
                    return psiJavaCodeReferenceElement;
                }
                String str2 = this.this$0.annotationFqName;
                if (str2 != null) {
                    return new ClsJavaCodeReferenceElementImpl(parent, str2);
                }
                return null;
            }
        });
        this.parameterList = new ParameterListImpl();
    }

    private final PsiJavaCodeReferenceElement get_nameReferenceElement() {
        return (PsiJavaCodeReferenceElement) this._nameReferenceElement.getValue();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightAbstractAnnotation, org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    public PsiJavaCodeReferenceElement getNameReferenceElement() {
        return get_nameReferenceElement();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightAbstractAnnotation, org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    public PsiAnnotationParameterList getParameterList() {
        return this.parameterList;
    }

    public <T extends PsiAnnotationMemberValue> Void setDeclaredAttributeValue(String str, T t) {
        ImplUtilsKt.cannotModify(this);
        throw null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    public PsiAnnotationMemberValue findAttributeValue(String attributeName) {
        return PsiImplUtil.findAttributeValue(this, attributeName);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    public PsiAnnotationMemberValue findDeclaredAttributeValue(String attributeName) {
        return PsiImplUtil.findDeclaredAttributeValue(this, attributeName);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    /* JADX INFO: renamed from: getQualifiedName, reason: from getter */
    public String getAnnotationFqName() {
        return this.annotationFqName;
    }

    /* JADX INFO: compiled from: ultraLightAnnotations.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0082\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0013\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0016¢\u0006\u0002\u0010\bR!\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u000b\u001a\u0004\u0018\u00010\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSimpleAnnotation$ParameterListImpl;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightElementBase;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotationParameterList;", "(Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSimpleAnnotation;)V", "_attributes", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiNameValuePair;", "get_attributes", "()[Lcom/intellij/psi/PsiNameValuePair;", "_attributes$delegate", "Lkotlin/Lazy;", "kotlinOrigin", "Lorg/jetbrains/kotlin/psi/KtElement;", "getKotlinOrigin", "()Lorg/jetbrains/kotlin/psi/KtElement;", "getAttributes", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class ParameterListImpl extends KtLightElementBase implements PsiAnnotationParameterList {

        /* JADX INFO: renamed from: _attributes$delegate, reason: from kotlin metadata */
        private final Lazy _attributes;

        @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.asJava.elements.KtLightElement
        public KtElement getKotlinOrigin() {
            return null;
        }

        public ParameterListImpl() {
            super(KtUltraLightSimpleAnnotation.this);
            this._attributes = ImplUtilsKt.lazyPub(new Function0<PsiNameValuePair[]>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightSimpleAnnotation$ParameterListImpl$_attributes$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final PsiNameValuePair[] invoke() {
                    List<Pair> list = ktUltraLightSimpleAnnotation.argumentsList;
                    KtUltraLightSimpleAnnotation.ParameterListImpl parameterListImpl = this;
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                    for (Pair pair : list) {
                        arrayList.add(new PsiNameValuePairForAnnotationArgument((String) pair.getFirst(), (ConstantValue) pair.getSecond(), parameterListImpl));
                    }
                    return (PsiNameValuePair[]) arrayList.toArray(new PsiNameValuePair[0]);
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

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + getAnnotationFqName() + '(' + ArraysKt.joinToString$default(this.parameterList.getAttributes(), (CharSequence) null, (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, new Function1<PsiNameValuePair, CharSequence>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightSimpleAnnotation.getText.1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(PsiNameValuePair it) {
                Intrinsics.checkNotNullParameter(it, "it");
                StringBuilder sbAppend = new StringBuilder().append(it.getName()).append('=');
                PsiAnnotationMemberValue value = it.getValue();
                return sbAppend.append(value != null ? value.getText() : null).toString();
            }
        }, 31, (Object) null) + ')';
    }
}
