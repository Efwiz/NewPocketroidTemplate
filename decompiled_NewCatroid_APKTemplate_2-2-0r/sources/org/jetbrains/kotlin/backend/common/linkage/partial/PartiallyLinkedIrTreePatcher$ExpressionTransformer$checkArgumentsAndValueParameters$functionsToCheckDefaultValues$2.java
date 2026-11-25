package org.jetbrains.kotlin.backend.common.linkage.partial;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrMemberWithContainerSource;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: PartiallyLinkedIrTreePatcher.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\n¢\u0006\u0002\b\u0003"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class PartiallyLinkedIrTreePatcher$ExpressionTransformer$checkArgumentsAndValueParameters$functionsToCheckDefaultValues$2 extends Lambda implements Function0<List<? extends IrFunction>> {
    final /* synthetic */ IrFunction $function;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PartiallyLinkedIrTreePatcher$ExpressionTransformer$checkArgumentsAndValueParameters$functionsToCheckDefaultValues$2(IrFunction irFunction) {
        super(0);
        this.$function = irFunction;
    }

    @Override // kotlin.jvm.functions.Function0
    public final List<? extends IrFunction> invoke() {
        IrMemberWithContainerSource irMemberWithContainerSource = this.$function;
        if (!(irMemberWithContainerSource instanceof IrSimpleFunction)) {
            return CollectionsKt.listOf(irMemberWithContainerSource);
        }
        List listAllOverridden = IrUtilsKt.allOverridden((IrOverridableDeclaration) irMemberWithContainerSource, true);
        ArrayList arrayList = new ArrayList();
        for (Object obj : listAllOverridden) {
            IrSimpleFunction irSimpleFunction = (IrSimpleFunction) obj;
            if (!irSimpleFunction.getIsFakeOverride() && !Intrinsics.areEqual(irSimpleFunction.getOrigin(), IrDeclarationOrigin.DELEGATED_MEMBER.INSTANCE)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }
}
