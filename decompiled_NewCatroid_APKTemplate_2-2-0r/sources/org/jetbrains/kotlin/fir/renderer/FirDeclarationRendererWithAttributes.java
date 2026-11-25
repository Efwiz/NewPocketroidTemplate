package org.jetbrains.kotlin.fir.renderer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationDataRegistry;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;

/* JADX INFO: compiled from: FirDeclarationRendererWithAttributes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00050\u0004H\u0014J \u0010\b\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\t0\u00050\u0004*\u00020\nH\u0002J\f\u0010\u000b\u001a\u00020\u0006*\u00020\tH\u0002J\f\u0010\f\u001a\u00020\r*\u00020\nH\u0014¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/FirDeclarationRendererWithAttributes;", "Lorg/jetbrains/kotlin/fir/renderer/FirDeclarationRenderer;", "()V", "attributeTypesToIds", "", "Lkotlin/Pair;", "", "", "getAttributesWithValues", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "renderAsDeclarationAttributeValue", "renderDeclarationAttributes", "", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirDeclarationRendererWithAttributes extends FirDeclarationRenderer {
    /* JADX WARN: Multi-variable type inference failed */
    public FirDeclarationRendererWithAttributes() {
        super(null, 1, 0 == true ? 1 : 0);
    }

    @Override // org.jetbrains.kotlin.fir.renderer.FirDeclarationRenderer
    protected void renderDeclarationAttributes(FirDeclaration firDeclaration) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        if (firDeclaration.getAttributes().isNotEmpty()) {
            List<Pair<String, Object>> attributesWithValues = getAttributesWithValues(firDeclaration);
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = attributesWithValues.iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                String str = (String) pair.component1();
                Object objComponent2 = pair.component2();
                Pair pair2 = objComponent2 != null ? TuplesKt.to(str, renderAsDeclarationAttributeValue(objComponent2)) : null;
                if (pair2 != null) {
                    arrayList.add(pair2);
                }
            }
            ArrayList arrayList2 = arrayList;
            if (arrayList2.isEmpty()) {
                return;
            }
            getPrinter().print("[" + CollectionsKt.joinToString$default(arrayList2, null, null, null, 0, null, new Function1<Pair<? extends String, ? extends String>, CharSequence>() { // from class: org.jetbrains.kotlin.fir.renderer.FirDeclarationRendererWithAttributes$renderDeclarationAttributes$attributes$3
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final CharSequence invoke2(Pair<String, String> pair3) {
                    Intrinsics.checkNotNullParameter(pair3, "<name for destructuring parameter 0>");
                    return pair3.component1() + '=' + pair3.component2();
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ CharSequence invoke(Pair<? extends String, ? extends String> pair3) {
                    return invoke2((Pair<String, String>) pair3);
                }
            }, 31, null) + "] ");
        }
    }

    private final List<Pair<String, Object>> getAttributesWithValues(FirDeclaration firDeclaration) {
        List<Pair> listSortedWith = CollectionsKt.sortedWith(attributeTypesToIds(), new Comparator() { // from class: org.jetbrains.kotlin.fir.renderer.FirDeclarationRendererWithAttributes$getAttributesWithValues$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues((String) ((Pair) t).getFirst(), (String) ((Pair) t2).getFirst());
            }
        });
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSortedWith, 10));
        for (Pair pair : listSortedWith) {
            arrayList.add(TuplesKt.to((String) pair.component1(), firDeclaration.getAttributes().get(((Number) pair.component2()).intValue())));
        }
        return arrayList;
    }

    protected List<Pair<String, Integer>> attributeTypesToIds() {
        Set<Map.Entry<String, Integer>> setEntrySet = FirDeclarationDataRegistry.INSTANCE.allValuesThreadUnsafeForRendering().entrySet();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(setEntrySet, 10));
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            arrayList.add(TuplesKt.to(StringsKt.substringAfterLast$default((String) entry.getKey(), ".", (String) null, 2, (Object) null), entry.getValue()));
        }
        return arrayList;
    }

    private final String renderAsDeclarationAttributeValue(Object obj) {
        if (obj instanceof FirCallableSymbol) {
            return ((FirCallableSymbol) obj).getCallableId().toString();
        }
        if (obj instanceof FirClassLikeSymbol) {
            String strAsString = ((FirClassLikeSymbol) obj).getClassId().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return strAsString;
        }
        if (obj instanceof FirProperty) {
            return ((FirProperty) obj).getSymbol().getCallableId().toString();
        }
        return obj instanceof Lazy ? String.valueOf(((Lazy) obj).getValue()) : obj.toString();
    }
}
