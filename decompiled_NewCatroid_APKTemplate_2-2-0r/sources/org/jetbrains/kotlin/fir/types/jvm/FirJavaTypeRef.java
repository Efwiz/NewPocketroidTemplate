package org.jetbrains.kotlin.fir.types.jvm;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.types.FirQualifierPart;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability;
import org.jetbrains.kotlin.fir.types.FirUserTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.load.java.structure.JavaType;

/* JADX INFO: compiled from: FirJavaTypeRef.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0005¢\u0006\u0002\u0010\bJ5\u0010\u001c\u001a\u00020\u001d\"\u0004\b\u0000\u0010\u001e\"\u0004\b\u0001\u0010\u001f2\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u0002H\u001e\u0012\u0004\u0012\u0002H\u001f0!2\u0006\u0010\"\u001a\u0002H\u001fH\u0016¢\u0006\u0002\u0010#J\u0016\u0010$\u001a\u00020\u001d2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016J\b\u0010&\u001a\u00020'H\u0016J)\u0010(\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u001f2\f\u0010)\u001a\b\u0012\u0004\u0012\u0002H\u001f0*2\u0006\u0010\"\u001a\u0002H\u001fH\u0016¢\u0006\u0002\u0010+J)\u0010,\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u001f2\f\u0010)\u001a\b\u0012\u0004\u0012\u0002H\u001f0*2\u0006\u0010\"\u001a\u0002H\u001fH\u0016¢\u0006\u0002\u0010+R!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0011R\u001a\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u000bR\u0016\u0010\u0016\u001a\u0004\u0018\u00010\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/jvm/FirJavaTypeRef;", "Lorg/jetbrains/kotlin/fir/types/FirUserTypeRef;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "annotationBuilder", "Lkotlin/Function0;", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "(Lorg/jetbrains/kotlin/load/java/structure/JavaType;Lkotlin/jvm/functions/Function0;)V", "annotations", "getAnnotations", "()Ljava/util/List;", "annotations$delegate", "Lkotlin/Lazy;", "customRenderer", "", "getCustomRenderer", "()Z", "isMarkedNullable", "qualifier", "Lorg/jetbrains/kotlin/fir/types/FirQualifierPart;", "getQualifier", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getType", "()Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "toString", "", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/types/FirUserTypeRef;", "transformChildren", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJavaTypeRef extends FirUserTypeRef {

    /* JADX INFO: renamed from: annotations$delegate, reason: from kotlin metadata */
    private final Lazy annotations;
    private final JavaType type;

    @Override // org.jetbrains.kotlin.fir.types.FirUserTypeRef
    public boolean getCustomRenderer() {
        return true;
    }

    @Override // org.jetbrains.kotlin.fir.types.FirUserTypeRef, org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability, org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.types.FirUserTypeRef, org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability
    /* JADX INFO: renamed from: isMarkedNullable */
    public boolean getIsMarkedNullable() {
        return false;
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationContainer transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirTypeRef transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability, org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirTypeRefWithNullability transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    public final JavaType getType() {
        return this.type;
    }

    public FirJavaTypeRef(JavaType type, final Function0<? extends List<? extends FirAnnotation>> annotationBuilder) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(annotationBuilder, "annotationBuilder");
        this.type = type;
        this.annotations = LazyKt.lazy(new Function0<List<? extends FirAnnotation>>() { // from class: org.jetbrains.kotlin.fir.types.jvm.FirJavaTypeRef$annotations$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends FirAnnotation> invoke() {
                return annotationBuilder.invoke();
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.types.FirUserTypeRef, org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability, org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public List<FirAnnotation> getAnnotations() {
        return (List) this.annotations.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.types.FirUserTypeRef
    public List<FirQualifierPart> getQualifier() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Iterator<FirQualifierPart> it = getQualifier().iterator();
        while (it.hasNext()) {
            Iterator<T> it2 = it.next().getTypeArgumentList().getTypeArguments().iterator();
            while (it2.hasNext()) {
                ((FirTypeProjection) it2.next()).accept(visitor, data);
            }
        }
        Iterator<T> it3 = getAnnotations().iterator();
        while (it3.hasNext()) {
            ((FirAnnotation) it3.next()).accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirUserTypeRef transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        Iterator<FirQualifierPart> it = getQualifier().iterator();
        while (it.hasNext()) {
            List<FirTypeProjection> typeArguments = it.next().getTypeArgumentList().getTypeArguments();
            Intrinsics.checkNotNull(typeArguments, "null cannot be cast to non-null type kotlin.collections.MutableList<org.jetbrains.kotlin.fir.types.FirTypeProjection>");
            FirTransformerUtilKt.transformInplace(TypeIntrinsics.asMutableList(typeArguments), transformer, data);
        }
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.types.FirUserTypeRef, org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability, org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        throw new AssertionError("Mutating annotations for FirJava* is not supported");
    }

    @Override // org.jetbrains.kotlin.fir.types.FirUserTypeRef, org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability, org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirUserTypeRef transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    public String toString() {
        return FirJavaTypeRefKt.render(this.type);
    }
}
