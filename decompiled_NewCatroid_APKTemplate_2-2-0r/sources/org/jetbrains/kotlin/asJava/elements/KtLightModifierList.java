package org.jetbrains.kotlin.asJava.elements;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.classes.ImplUtilsKt;
import org.jetbrains.kotlin.asJava.elements.KtLightElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtModifierListOwner;

/* JADX INFO: compiled from: KtLightModifierList.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0001\n\u0002\b\u0002\b&\u0018\u0000*\u0016\b\u0000\u0010\u0001 \u0001*\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00022\u00020\u00052\u00020\u00062\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00060\u0002B\r\u0012\u0006\u0010\b\u001a\u00028\u0000¢\u0006\u0002\u0010\tJ\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0018\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u001f2\u0006\u0010#\u001a\u00020\u0013H\u0016J\u000e\u0010$\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH&J\u0012\u0010%\u001a\u0004\u0018\u00010\f2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0015\u0010&\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001d0'H\u0016¢\u0006\u0002\u0010(J\u0015\u0010)\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001d0'H\u0016¢\u0006\u0002\u0010(J\r\u0010*\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u001aJ\u0010\u0010+\u001a\u00020\u00132\u0006\u0010\"\u001a\u00020\u001fH\u0016J\u0012\u0010,\u001a\u00020\u00132\b\u0010-\u001a\u0004\u0018\u00010.H\u0016J\b\u0010/\u001a\u00020\u0013H\u0016J\u001c\u00100\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\f\u00101\u001a\b\u0012\u0004\u0012\u00020\u001d0\u000bH\u0016J\u0018\u00102\u001a\u00020!2\u0006\u0010\"\u001a\u00020\u001f2\u0006\u0010#\u001a\u00020\u0013H\u0016J\b\u00103\u001a\u000204H\u0002J\b\u00105\u001a\u00020\u001fH\u0016R!\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\"\u0010\u0011\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u0012X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\u0016\u001a\u0004\u0018\u00010\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0016\u0010\b\u001a\u00028\u0000X\u0084\u0004¢\u0006\n\n\u0002\u0010\u001b\u001a\u0004\b\u0019\u0010\u001a¨\u00066"}, d2 = {"Lorg/jetbrains/kotlin/asJava/elements/KtLightModifierList;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/asJava/elements/KtLightElement;", "Lorg/jetbrains/kotlin/psi/KtModifierListOwner;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierListOwner;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightElementBase;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierList;", "Lorg/jetbrains/kotlin/psi/KtModifierList;", "owner", "(Lorg/jetbrains/kotlin/asJava/elements/KtLightElement;)V", "_annotations", "", "Lorg/jetbrains/kotlin/asJava/elements/KtLightAbstractAnnotation;", "get_annotations", "()Ljava/util/List;", "_annotations$delegate", "Lkotlin/Lazy;", "annotationsFilter", "Lkotlin/Function1;", "", "getAnnotationsFilter", "()Lkotlin/jvm/functions/Function1;", "kotlinOrigin", "getKotlinOrigin", "()Lorg/jetbrains/kotlin/psi/KtModifierList;", "getOwner", "()Lorg/jetbrains/kotlin/asJava/elements/KtLightElement;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightElement;", "addAnnotation", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotation;", "qualifiedName", "", "checkSetModifierProperty", "", "name", "value", "computeAnnotations", "findAnnotation", "getAnnotations", "", "()[Lcom/intellij/psi/PsiAnnotation;", "getApplicableAnnotations", "getParent", "hasExplicitModifier", "isEquivalentTo", "another", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "isWritable", "nonSourceAnnotationsForAnnotationType", "sourceAnnotations", "setModifierProperty", "throwInvalidOperation", "", "toString", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtLightModifierList<T extends KtLightElement<? extends KtModifierListOwner, ? extends PsiModifierListOwner>> extends KtLightElementBase implements PsiModifierList, KtLightElement<KtModifierList, PsiModifierList> {

    /* JADX INFO: renamed from: _annotations$delegate, reason: from kotlin metadata */
    private final Lazy _annotations;
    private final Function1<KtLightAbstractAnnotation, Boolean> annotationsFilter;
    private final T owner;

    public abstract List<KtLightAbstractAnnotation> computeAnnotations();

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isWritable() {
        return false;
    }

    protected final T getOwner() {
        return this.owner;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtLightModifierList(T owner) {
        super(owner);
        Intrinsics.checkNotNullParameter(owner, "owner");
        this.owner = owner;
        this._annotations = ImplUtilsKt.lazyPub(new Function0<List<? extends KtLightAbstractAnnotation>>(this) { // from class: org.jetbrains.kotlin.asJava.elements.KtLightModifierList$_annotations$2
            final /* synthetic */ KtLightModifierList<T> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
                this.this$0 = this;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function0
            public final List<? extends KtLightAbstractAnnotation> invoke() {
                List<KtLightAbstractAnnotation> listComputeAnnotations = this.this$0.computeAnnotations();
                Function1<KtLightAbstractAnnotation, Boolean> annotationsFilter = this.this$0.getAnnotationsFilter();
                if (annotationsFilter == 0) {
                    return listComputeAnnotations;
                }
                ArrayList arrayList = new ArrayList();
                for (Object obj : listComputeAnnotations) {
                    if (((Boolean) annotationsFilter.invoke(obj)).booleanValue()) {
                        arrayList.add(obj);
                    }
                }
                return arrayList;
            }
        });
    }

    private final List<KtLightAbstractAnnotation> get_annotations() {
        return (List) this._annotations.getValue();
    }

    protected Function1<KtLightAbstractAnnotation, Boolean> getAnnotationsFilter() {
        return this.annotationsFilter;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.asJava.elements.KtLightElement
    public KtModifierList getKotlinOrigin() {
        KtModifierListOwner ktModifierListOwner = (KtModifierListOwner) this.owner.getKotlinOrigin();
        if (ktModifierListOwner != null) {
            return ktModifierListOwner.getModifierList();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public T getLightParent() {
        return this.owner;
    }

    public boolean hasExplicitModifier(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return hasModifierProperty(name2);
    }

    private final Void throwInvalidOperation() {
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierList
    public void setModifierProperty(String name2, boolean value) {
        Intrinsics.checkNotNullParameter(name2, "name");
        throwInvalidOperation();
        throw null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierList
    public void checkSetModifierProperty(String name2, boolean value) {
        Intrinsics.checkNotNullParameter(name2, "name");
        throwInvalidOperation();
        throw null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation addAnnotation(String qualifiedName) {
        Intrinsics.checkNotNullParameter(qualifiedName, "qualifiedName");
        throwInvalidOperation();
        throw null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation[] getApplicableAnnotations() {
        return getAnnotations();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation[] getAnnotations() {
        return (PsiAnnotation[]) get_annotations().toArray(new KtLightAbstractAnnotation[0]);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public KtLightAbstractAnnotation findAnnotation(String qualifiedName) {
        Object next;
        Intrinsics.checkNotNullParameter(qualifiedName, "qualifiedName");
        Iterator<T> it = get_annotations().iterator();
        while (it.hasNext()) {
            next = it.next();
            if (((KtLightAbstractAnnotation) next).fqNameMatches(qualifiedName)) {
                return (KtLightAbstractAnnotation) next;
            }
        }
        next = null;
        return (KtLightAbstractAnnotation) next;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement another) {
        return (another instanceof KtLightModifierList) && Intrinsics.areEqual(this.owner, ((KtLightModifierList) another).owner);
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "Light modifier list of " + this.owner;
    }

    public List<KtLightAbstractAnnotation> nonSourceAnnotationsForAnnotationType(List<? extends PsiAnnotation> sourceAnnotations) {
        Intrinsics.checkNotNullParameter(sourceAnnotations, "sourceAnnotations");
        return CollectionsKt.emptyList();
    }
}
