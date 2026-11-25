package org.jetbrains.kotlin.codegen.context;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.AccessorForCallableDescriptor;
import org.jetbrains.kotlin.codegen.OwnerKind;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;

/* JADX INFO: compiled from: DefaultImplsClassContext.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001BI\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\t\u0012\u0014\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r\u0018\u00010\u000b\u0012\u0006\u0010\u000e\u001a\u00020\u0001¢\u0006\u0002\u0010\u000fJ\u0012\u0010\u0012\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00140\u0013H\u0016J\u000e\u0010\u0015\u001a\b\u0012\u0002\b\u0003\u0018\u00010\tH\u0016R\u0011\u0010\u000e\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/codegen/context/DefaultImplsClassContext;", "Lorg/jetbrains/kotlin/codegen/context/ClassContext;", "typeMapper", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;", "contextDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "contextKind", "Lorg/jetbrains/kotlin/codegen/OwnerKind;", "parentContext", "Lorg/jetbrains/kotlin/codegen/context/CodegenContext;", "localLookup", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "", "interfaceContext", "(Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/codegen/OwnerKind;Lorg/jetbrains/kotlin/codegen/context/CodegenContext;Lkotlin/jvm/functions/Function1;Lorg/jetbrains/kotlin/codegen/context/ClassContext;)V", "getInterfaceContext", "()Lorg/jetbrains/kotlin/codegen/context/ClassContext;", "getAccessors", "", "Lorg/jetbrains/kotlin/codegen/AccessorForCallableDescriptor;", "getCompanionObjectContext", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DefaultImplsClassContext extends ClassContext {
    private final ClassContext interfaceContext;

    public final ClassContext getInterfaceContext() {
        return this.interfaceContext;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DefaultImplsClassContext(KotlinTypeMapper typeMapper, ClassDescriptor contextDescriptor, OwnerKind contextKind, CodegenContext<?> codegenContext, final Function1<? super DeclarationDescriptor, Boolean> function1, ClassContext interfaceContext) {
        super(typeMapper, contextDescriptor, contextKind, codegenContext, function1 != null ? new LocalLookup() { // from class: org.jetbrains.kotlin.codegen.context.DefaultImplsClassContext$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.codegen.context.LocalLookup
            public final boolean isLocal(DeclarationDescriptor declarationDescriptor) {
                return DefaultImplsClassContext._init_$lambda$0(function1, declarationDescriptor);
            }
        } : null);
        Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
        Intrinsics.checkNotNullParameter(contextDescriptor, "contextDescriptor");
        Intrinsics.checkNotNullParameter(contextKind, "contextKind");
        Intrinsics.checkNotNullParameter(interfaceContext, "interfaceContext");
        this.interfaceContext = interfaceContext;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean _init_$lambda$0(Function1 function1, DeclarationDescriptor declarationDescriptor) {
        return ((Boolean) function1.invoke(declarationDescriptor)).booleanValue();
    }

    @Override // org.jetbrains.kotlin.codegen.context.ClassContext
    public CodegenContext<?> getCompanionObjectContext() {
        return this.interfaceContext.getCompanionObjectContext();
    }

    @Override // org.jetbrains.kotlin.codegen.context.CodegenContext
    public Collection<AccessorForCallableDescriptor<?>> getAccessors() {
        Collection<? extends AccessorForCallableDescriptor<?>> accessors = super.getAccessors();
        Intrinsics.checkNotNullExpressionValue(accessors, "getAccessors(...)");
        Collection<? extends AccessorForCallableDescriptor<?>> collection = accessors;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            AccessorForCallableDescriptor accessorForCallableDescriptor = (AccessorForCallableDescriptor) it.next();
            arrayList.add(new Pair(accessorForCallableDescriptor.getCalleeDescriptor(), accessorForCallableDescriptor.getSuperCallTarget()));
        }
        ArrayList arrayList2 = arrayList;
        Collection<? extends AccessorForCallableDescriptor<?>> accessors2 = this.interfaceContext.getAccessors();
        Intrinsics.checkNotNullExpressionValue(accessors2, "getAccessors(...)");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : accessors2) {
            AccessorForCallableDescriptor accessorForCallableDescriptor2 = (AccessorForCallableDescriptor) obj;
            linkedHashMap.put(new Pair(accessorForCallableDescriptor2.getCalleeDescriptor(), accessorForCallableDescriptor2.getSuperCallTarget()), obj);
        }
        LinkedHashMap linkedHashMap2 = linkedHashMap;
        Set setKeySet = linkedHashMap2.keySet();
        Intrinsics.checkNotNullExpressionValue(setKeySet, "<get-keys>(...)");
        CollectionsKt.removeAll((Collection) setKeySet, (Iterable) arrayList2);
        Collection collectionValues = linkedHashMap2.values();
        Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
        return CollectionsKt.plus((Collection) accessors, (Iterable) collectionValues);
    }
}
