package org.jetbrains.kotlin.cfg.pseudocode;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: TypePredicate.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\u0010\u0004J\u000f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003HÆ\u0003J\u0019\u0010\b\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0016\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u0011H\u0096\u0002¢\u0006\u0002\u0010\u0012J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/ForAllTypes;", "Lorg/jetbrains/kotlin/cfg/pseudocode/TypePredicate;", "typeSets", "", "(Ljava/util/List;)V", "getTypeSets", "()Ljava/util/List;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "invoke", "typeToCheck", "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/types/KotlinType;)Ljava/lang/Boolean;", "toString", "", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ForAllTypes implements TypePredicate {
    private final List<TypePredicate> typeSets;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ForAllTypes copy$default(ForAllTypes forAllTypes, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            list = forAllTypes.typeSets;
        }
        return forAllTypes.copy(list);
    }

    public final List<TypePredicate> component1() {
        return this.typeSets;
    }

    public final ForAllTypes copy(List<? extends TypePredicate> typeSets) {
        Intrinsics.checkNotNullParameter(typeSets, "typeSets");
        return new ForAllTypes(typeSets);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof ForAllTypes) && Intrinsics.areEqual(this.typeSets, ((ForAllTypes) other).typeSets);
    }

    public int hashCode() {
        return this.typeSets.hashCode();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ForAllTypes(List<? extends TypePredicate> typeSets) {
        Intrinsics.checkNotNullParameter(typeSets, "typeSets");
        this.typeSets = typeSets;
    }

    public final List<TypePredicate> getTypeSets() {
        return this.typeSets;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // kotlin.jvm.functions.Function1
    public Boolean invoke(KotlinType typeToCheck) {
        Intrinsics.checkNotNullParameter(typeToCheck, "typeToCheck");
        List<TypePredicate> list = this.typeSets;
        boolean z = true;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                if (!((TypePredicate) it.next()).invoke(typeToCheck).booleanValue()) {
                    z = false;
                    break;
                }
            }
        }
        return Boolean.valueOf(z);
    }

    public String toString() {
        return "AND{" + CollectionsKt.joinToString$default(this.typeSets, ", ", null, null, 0, null, null, 62, null) + '}';
    }
}
