package org.jetbrains.kotlin.util;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;

/* JADX INFO: compiled from: modifierChecks.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0090\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/util/InfixChecks;", "Lorg/jetbrains/kotlin/util/AbstractModifierChecks;", "()V", "checks", "", "Lorg/jetbrains/kotlin/util/Checks;", "getChecks$descriptors", "()Ljava/util/List;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InfixChecks extends AbstractModifierChecks {
    public static final InfixChecks INSTANCE = new InfixChecks();
    private static final List<Checks> checks = CollectionsKt.listOf(new Checks(new Check[]{MemberKindCheck.MemberOrExtension.INSTANCE, ValueParameterCountCheck.SingleValueParameter.INSTANCE, NoDefaultAndVarargsCheck.INSTANCE}, null, 2, null));

    private InfixChecks() {
    }

    @Override // org.jetbrains.kotlin.util.AbstractModifierChecks
    public List<Checks> getChecks$descriptors() {
        return checks;
    }
}
