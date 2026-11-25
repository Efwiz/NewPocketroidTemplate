package org.jetbrains.kotlin.js.patterns.typePredicates;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: typePredicates.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0015\b\u0002\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006¢\u0006\u0002\u0010\u0007J\u000e\u0010\b\u001a\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u0003J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/js/patterns/typePredicates/TypePredicateImpl;", "Lorg/jetbrains/kotlin/js/patterns/typePredicates/TypePredicate;", "name", "", "(Ljava/lang/String;)V", "nameParts", "", "(Ljava/util/List;)V", "inner", "test", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class TypePredicateImpl implements TypePredicate {
    private final List<String> nameParts;

    private TypePredicateImpl(List<String> list) {
        this.nameParts = list;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TypePredicateImpl(String name2) {
        this((List<String>) CollectionsKt.listOf(name2));
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    @Override // java.util.function.Predicate
    public boolean test(KotlinType type) {
        Name name2;
        Intrinsics.checkNotNullParameter(type, "type");
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = type.getConstructor().mo7434getDeclarationDescriptor();
        if (classifierDescriptorMo7434getDeclarationDescriptor == null) {
            return false;
        }
        ClassifierDescriptor containingDeclaration = classifierDescriptorMo7434getDeclarationDescriptor;
        for (int lastIndex = CollectionsKt.getLastIndex(this.nameParts); -1 < lastIndex; lastIndex--) {
            if (!Intrinsics.areEqual(this.nameParts.get(lastIndex), (containingDeclaration == null || (name2 = containingDeclaration.getName()) == null) ? null : name2.asString())) {
                return false;
            }
            containingDeclaration = containingDeclaration.getContainingDeclaration();
        }
        return true;
    }

    public final TypePredicateImpl inner(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return new TypePredicateImpl((List<String>) CollectionsKt.plus((Collection) this.nameParts, (Iterable) CollectionsKt.listOf(name2)));
    }
}
