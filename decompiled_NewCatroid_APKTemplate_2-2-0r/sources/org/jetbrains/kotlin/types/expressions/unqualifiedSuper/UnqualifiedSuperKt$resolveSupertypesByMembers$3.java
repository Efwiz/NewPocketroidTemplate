package org.jetbrains.kotlin.types.expressions.unqualifiedSuper;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;

/* JADX INFO: compiled from: unqualifiedSuper.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "typeWithConcreteMember", "Lorg/jetbrains/kotlin/types/KotlinType;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "invoke", "(Lorg/jetbrains/kotlin/types/KotlinType;)Ljava/lang/Boolean;"}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class UnqualifiedSuperKt$resolveSupertypesByMembers$3 extends Lambda implements Function1<KotlinType, Boolean> {
    final /* synthetic */ SmartList<KotlinType> $typesWithNonConcreteMembers;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UnqualifiedSuperKt$resolveSupertypesByMembers$3(SmartList<KotlinType> smartList) {
        super(1);
        this.$typesWithNonConcreteMembers = smartList;
    }

    @Override // kotlin.jvm.functions.Function1
    public final Boolean invoke(KotlinType kotlinType) {
        SmartList<KotlinType> smartList = this.$typesWithNonConcreteMembers;
        boolean z = false;
        if (!(smartList instanceof Collection) || !smartList.isEmpty()) {
            Iterator<KotlinType> it = smartList.iterator();
            while (it.hasNext()) {
                if (KotlinTypeChecker.DEFAULT.isSubtypeOf(it.next(), kotlinType)) {
                    z = true;
                    break;
                }
            }
        }
        return Boolean.valueOf(z);
    }
}
