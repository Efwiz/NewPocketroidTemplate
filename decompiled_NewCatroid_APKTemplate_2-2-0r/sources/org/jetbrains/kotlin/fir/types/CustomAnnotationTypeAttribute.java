package org.jetbrains.kotlin.fir.types;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.builder.FirAnnotationBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirAnnotationCallBuilder;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;

/* JADX INFO: compiled from: CustomAnnotationTypeAttribute.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B#\b\u0016\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007B'\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0012\b\u0002\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0003¢\u0006\u0002\u0010\tJ\u0012\u0010\u0011\u001a\u00020\u00002\b\u0010\u0012\u001a\u0004\u0018\u00010\u0000H\u0016J\u0006\u0010\u0013\u001a\u00020\u0000J\u0014\u0010\u0014\u001a\u0004\u0018\u00010\u00002\b\u0010\u0012\u001a\u0004\u0018\u00010\u0000H\u0016J\u0012\u0010\u0015\u001a\u00020\u00162\b\u0010\u0012\u001a\u0004\u0018\u00010\u0000H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\u0014\u0010\u0019\u001a\u0004\u0018\u00010\u00002\b\u0010\u0012\u001a\u0004\u0018\u00010\u0000H\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001b\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u001c\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00000\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/CustomAnnotationTypeAttribute;", "Lorg/jetbrains/kotlin/fir/types/ConeAttribute;", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "containerSymbol", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "(Ljava/util/List;Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;)V", "containerSymbols", "(Ljava/util/List;Ljava/util/List;)V", "getAnnotations", "()Ljava/util/List;", "getContainerSymbols", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lkotlin/reflect/KClass;", "getKey", "()Lkotlin/reflect/KClass;", ProductAction.ACTION_ADD, "other", "independentInstance", "intersect", "isSubtypeOf", "", "toString", "", SchemaSymbols.ATTVAL_UNION, Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CustomAnnotationTypeAttribute extends ConeAttribute<CustomAnnotationTypeAttribute> {
    private final List<FirAnnotation> annotations;
    private final List<FirBasedSymbol<?>> containerSymbols;

    @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
    public KClass<? extends CustomAnnotationTypeAttribute> getKey() {
        return Reflection.getOrCreateKotlinClass(CustomAnnotationTypeAttribute.class);
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
    public CustomAnnotationTypeAttribute intersect(CustomAnnotationTypeAttribute other) {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
    public boolean isSubtypeOf(CustomAnnotationTypeAttribute other) {
        return true;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
    public CustomAnnotationTypeAttribute union(CustomAnnotationTypeAttribute other) {
        return null;
    }

    public final List<FirAnnotation> getAnnotations() {
        return this.annotations;
    }

    public /* synthetic */ CustomAnnotationTypeAttribute(List list, List list2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((List<? extends FirAnnotation>) list, (List<? extends FirBasedSymbol<?>>) ((i & 2) != 0 ? CollectionsKt.emptyList() : list2));
    }

    public final List<FirBasedSymbol<?>> getContainerSymbols() {
        return this.containerSymbols;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public CustomAnnotationTypeAttribute(List<? extends FirAnnotation> annotations, List<? extends FirBasedSymbol<?>> containerSymbols) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(containerSymbols, "containerSymbols");
        this.annotations = annotations;
        this.containerSymbols = containerSymbols;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CustomAnnotationTypeAttribute(List<? extends FirAnnotation> annotations, FirBasedSymbol<?> firBasedSymbol) {
        this(annotations, (List<? extends FirBasedSymbol<?>>) CollectionsKt.listOfNotNull(firBasedSymbol));
        Intrinsics.checkNotNullParameter(annotations, "annotations");
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
    public CustomAnnotationTypeAttribute add(CustomAnnotationTypeAttribute other) {
        return (other == null || other == this) ? this : new CustomAnnotationTypeAttribute((List<? extends FirAnnotation>) CollectionsKt.plus((Collection) this.annotations, (Iterable) other.annotations), (List<? extends FirBasedSymbol<?>>) CollectionsKt.plus((Collection) this.containerSymbols, (Iterable) other.containerSymbols));
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeAttribute
    public String toString() {
        return CollectionsKt.joinToString$default(this.annotations, " ", null, null, 0, null, new Function1<FirAnnotation, CharSequence>() { // from class: org.jetbrains.kotlin.fir.types.CustomAnnotationTypeAttribute.toString.1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(FirAnnotation it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return UtilsKt.render(it);
            }
        }, 30, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final CustomAnnotationTypeAttribute independentInstance() {
        FirAnnotationCall firAnnotationCallMo6809build;
        if (this.containerSymbols.isEmpty()) {
            return this;
        }
        List<FirAnnotation> list = this.annotations;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (FirAnnotation firAnnotation : list) {
            if (firAnnotation instanceof FirAnnotationCall) {
                FirAnnotationCall firAnnotationCall = (FirAnnotationCall) firAnnotation;
                FirAnnotationCallBuilder firAnnotationCallBuilder = new FirAnnotationCallBuilder();
                firAnnotationCallBuilder.setSource(firAnnotationCall.getSource());
                firAnnotationCallBuilder.setUseSiteTarget(firAnnotationCall.getUseSiteTarget());
                firAnnotationCallBuilder.setAnnotationTypeRef(firAnnotationCall.getAnnotationTypeRef());
                firAnnotationCallBuilder.getTypeArguments().addAll(firAnnotationCall.getTypeArguments());
                firAnnotationCallBuilder.setArgumentList(firAnnotationCall.getArgumentList());
                firAnnotationCallBuilder.setCalleeReference(firAnnotationCall.getCalleeReference());
                firAnnotationCallBuilder.setArgumentMapping(firAnnotationCall.getArgumentMapping());
                firAnnotationCallBuilder.setAnnotationResolvePhase(firAnnotationCall.getAnnotationResolvePhase());
                firAnnotationCallMo6809build = firAnnotationCallBuilder.mo6809build();
            } else {
                FirAnnotationBuilder firAnnotationBuilder = new FirAnnotationBuilder();
                firAnnotationBuilder.setSource(firAnnotation.getSource());
                firAnnotationBuilder.setUseSiteTarget(firAnnotation.getUseSiteTarget());
                firAnnotationBuilder.setAnnotationTypeRef(firAnnotation.getAnnotationTypeRef());
                firAnnotationBuilder.setArgumentMapping(firAnnotation.getArgumentMapping());
                firAnnotationBuilder.getTypeArguments().addAll(firAnnotation.getTypeArguments());
                firAnnotationCallMo6809build = firAnnotationBuilder.mo6809build();
            }
            arrayList.add(firAnnotationCallMo6809build);
        }
        return new CustomAnnotationTypeAttribute(arrayList, null, 2, 0 == true ? 1 : 0);
    }
}
