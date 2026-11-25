package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: SuperCalls.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "typeWithConcreteMember", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "invoke", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)Ljava/lang/Boolean;"}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class SuperCallsKt$resolveSupertypesByMembers$3 extends Lambda implements Function1<ConeKotlinType, Boolean> {
    final /* synthetic */ BodyResolveComponents $this_resolveSupertypesByMembers;
    final /* synthetic */ SmartList<ConeKotlinType> $typesWithNonConcreteMembers;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SuperCallsKt$resolveSupertypesByMembers$3(SmartList<ConeKotlinType> smartList, BodyResolveComponents bodyResolveComponents) {
        super(1);
        this.$typesWithNonConcreteMembers = smartList;
        this.$this_resolveSupertypesByMembers = bodyResolveComponents;
    }

    @Override // kotlin.jvm.functions.Function1
    public final Boolean invoke(ConeKotlinType coneKotlinType) {
        SmartList<ConeKotlinType> smartList = this.$typesWithNonConcreteMembers;
        BodyResolveComponents bodyResolveComponents = this.$this_resolveSupertypesByMembers;
        boolean z = false;
        if (!(smartList instanceof Collection) || !smartList.isEmpty()) {
            for (ConeKotlinType coneKotlinType2 : smartList) {
                AbstractTypeChecker abstractTypeChecker = AbstractTypeChecker.INSTANCE;
                ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(bodyResolveComponents.getSession());
                Intrinsics.checkNotNull(coneKotlinType2);
                Intrinsics.checkNotNull(coneKotlinType);
                if (AbstractTypeChecker.isSubtypeOf$default(abstractTypeChecker, (TypeCheckerProviderContext) typeContext, (KotlinTypeMarker) coneKotlinType2, (KotlinTypeMarker) coneKotlinType, false, 8, (Object) null)) {
                    z = true;
                    break;
                }
            }
        }
        return Boolean.valueOf(z);
    }
}
