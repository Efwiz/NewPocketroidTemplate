package org.jetbrains.kotlin.types.model;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.TypeCheckerState;

/* JADX INFO: compiled from: TypeSystemContext.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003J\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007H&J\u001c\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00072\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0007H&J \u0010\f\u001a\u00020\r*\u00020\u000b2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\r0\u000fH\u0016J\f\u0010\u0010\u001a\u00020\r*\u00020\u000bH&J\f\u0010\u0011\u001a\u00020\r*\u00020\u0005H&J\u001a\u0010\u0012\u001a\u00020\u000b*\u00020\u000b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\t0\u0007H&J\f\u0010\u0014\u001a\u00020\u0005*\u00020\u0015H&J\f\u0010\u0016\u001a\u00020\u0017*\u00020\u000bH\u0016J\f\u0010\u0016\u001a\u00020\u0017*\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0018À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/types/model/TypeSystemCommonSuperTypesContext;", "Lorg/jetbrains/kotlin/types/model/TypeSystemContext;", "Lorg/jetbrains/kotlin/types/model/TypeSystemTypeFactoryContext;", "Lorg/jetbrains/kotlin/types/model/TypeCheckerProviderContext;", "findCommonIntegerLiteralTypesSuperType", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "explicitSupertypes", "", "unionTypeAttributes", "Lorg/jetbrains/kotlin/types/model/AnnotationMarker;", SpecTypePair.NAME_types, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "anySuperTypeConstructor", "", "predicate", "Lkotlin/Function1;", "canHaveUndefinedNullability", "isExtensionFunction", "replaceCustomAttributes", "newAttributes", "toErrorType", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "typeDepth", "", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface TypeSystemCommonSuperTypesContext extends TypeCheckerProviderContext, TypeSystemContext, TypeSystemTypeFactoryContext {
    boolean canHaveUndefinedNullability(KotlinTypeMarker kotlinTypeMarker);

    SimpleTypeMarker findCommonIntegerLiteralTypesSuperType(List<? extends SimpleTypeMarker> explicitSupertypes);

    boolean isExtensionFunction(SimpleTypeMarker simpleTypeMarker);

    KotlinTypeMarker replaceCustomAttributes(KotlinTypeMarker kotlinTypeMarker, List<? extends AnnotationMarker> list);

    SimpleTypeMarker toErrorType(TypeConstructorMarker typeConstructorMarker);

    int typeDepth(SimpleTypeMarker simpleTypeMarker);

    List<AnnotationMarker> unionTypeAttributes(List<? extends KotlinTypeMarker> types);

    default boolean anySuperTypeConstructor(KotlinTypeMarker kotlinTypeMarker, Function1<? super SimpleTypeMarker, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        TypeCheckerState typeCheckerStateNewTypeCheckerState = newTypeCheckerState(false, true);
        SimpleTypeMarker simpleTypeMarkerLowerBoundIfFlexible = lowerBoundIfFlexible(kotlinTypeMarker);
        if (!predicate.invoke(simpleTypeMarkerLowerBoundIfFlexible).booleanValue()) {
            typeCheckerStateNewTypeCheckerState.initialize();
            ArrayDeque<SimpleTypeMarker> supertypesDeque = typeCheckerStateNewTypeCheckerState.getSupertypesDeque();
            Intrinsics.checkNotNull(supertypesDeque);
            Set<SimpleTypeMarker> supertypesSet = typeCheckerStateNewTypeCheckerState.getSupertypesSet();
            Intrinsics.checkNotNull(supertypesSet);
            supertypesDeque.push(simpleTypeMarkerLowerBoundIfFlexible);
            while (!supertypesDeque.isEmpty()) {
                if (supertypesSet.size() > 1000) {
                    throw new IllegalStateException(("Too many supertypes for type: " + simpleTypeMarkerLowerBoundIfFlexible + ". Supertypes = " + CollectionsKt.joinToString$default(supertypesSet, null, null, null, 0, null, null, 63, null)).toString());
                }
                SimpleTypeMarker simpleTypeMarkerPop = supertypesDeque.pop();
                Intrinsics.checkNotNull(simpleTypeMarkerPop);
                if (supertypesSet.add(simpleTypeMarkerPop)) {
                    TypeCheckerState.SupertypesPolicy.LowerIfFlexible lowerIfFlexible = TypeCheckerState.SupertypesPolicy.LowerIfFlexible.INSTANCE;
                    if (Intrinsics.areEqual(lowerIfFlexible, TypeCheckerState.SupertypesPolicy.None.INSTANCE)) {
                        lowerIfFlexible = null;
                    }
                    if (lowerIfFlexible == null) {
                        continue;
                    } else {
                        TypeSystemContext typeSystemContext = typeCheckerStateNewTypeCheckerState.getTypeSystemContext();
                        Iterator<KotlinTypeMarker> it = typeSystemContext.supertypes(typeSystemContext.typeConstructor(simpleTypeMarkerPop)).iterator();
                        while (it.hasNext()) {
                            SimpleTypeMarker simpleTypeMarkerMo7474transformType = lowerIfFlexible.mo7474transformType(typeCheckerStateNewTypeCheckerState, it.next());
                            if (predicate.invoke(simpleTypeMarkerMo7474transformType).booleanValue()) {
                                typeCheckerStateNewTypeCheckerState.clear();
                            } else {
                                supertypesDeque.add(simpleTypeMarkerMo7474transformType);
                            }
                        }
                    }
                }
            }
            typeCheckerStateNewTypeCheckerState.clear();
            return false;
        }
        return true;
    }

    default int typeDepth(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (kotlinTypeMarker instanceof SimpleTypeMarker) {
            return typeDepth((SimpleTypeMarker) kotlinTypeMarker);
        }
        if (!(kotlinTypeMarker instanceof FlexibleTypeMarker)) {
            throw new IllegalStateException(("Type should be simple or flexible: " + kotlinTypeMarker).toString());
        }
        FlexibleTypeMarker flexibleTypeMarker = (FlexibleTypeMarker) kotlinTypeMarker;
        return Math.max(typeDepth(lowerBound(flexibleTypeMarker)), typeDepth(upperBound(flexibleTypeMarker)));
    }
}
