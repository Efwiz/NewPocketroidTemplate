package org.jetbrains.kotlin.contracts.model.functors;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.method.ParameterDescription;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.contracts.model.AbstractFunctor;
import org.jetbrains.kotlin.contracts.model.Computation;
import org.jetbrains.kotlin.contracts.model.ConditionalEffect;
import org.jetbrains.kotlin.contracts.model.ESEffect;
import org.jetbrains.kotlin.contracts.model.ESTypeSubstitution;
import org.jetbrains.kotlin.contracts.model.ESValue;
import org.jetbrains.kotlin.contracts.model.structure.ESConstants;
import org.jetbrains.kotlin.contracts.model.structure.ESIs;
import org.jetbrains.kotlin.contracts.model.structure.ESKotlinType;
import org.jetbrains.kotlin.contracts.model.structure.ESReturns;
import org.jetbrains.kotlin.contracts.model.structure.ESType;
import org.jetbrains.kotlin.contracts.model.visitors.Reducer;

/* JADX INFO: compiled from: IsFunctor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J,\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u000b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0014J\u001c\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u001e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u000b2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u000f\u001a\u00020\u0010H\u0002R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/functors/IsFunctor;", "Lorg/jetbrains/kotlin/contracts/model/AbstractFunctor;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/contracts/model/structure/ESType;", "isNegated", "", "(Lorg/jetbrains/kotlin/contracts/model/structure/ESType;Z)V", "()Z", "getType", "()Lorg/jetbrains/kotlin/contracts/model/structure/ESType;", "doInvocation", "", "Lorg/jetbrains/kotlin/contracts/model/ESEffect;", "arguments", "Lorg/jetbrains/kotlin/contracts/model/Computation;", "typeSubstitution", "Lorg/jetbrains/kotlin/contracts/model/ESTypeSubstitution;", "reducer", "Lorg/jetbrains/kotlin/contracts/model/visitors/Reducer;", "invokeWithArguments", ParameterDescription.NAME_PREFIX, "invokeWithValue", "Lorg/jetbrains/kotlin/contracts/model/ConditionalEffect;", "value", "Lorg/jetbrains/kotlin/contracts/model/ESValue;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IsFunctor extends AbstractFunctor {
    private final boolean isNegated;
    private final ESType type;

    public IsFunctor(ESType type, boolean z) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.type = type;
        this.isNegated = z;
    }

    public final ESType getType() {
        return this.type;
    }

    /* JADX INFO: renamed from: isNegated, reason: from getter */
    public final boolean getIsNegated() {
        return this.isNegated;
    }

    @Override // org.jetbrains.kotlin.contracts.model.AbstractFunctor
    protected List<ESEffect> doInvocation(List<? extends Computation> arguments, ESTypeSubstitution typeSubstitution, Reducer reducer) {
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(typeSubstitution, "typeSubstitution");
        Intrinsics.checkNotNullParameter(reducer, "reducer");
        arguments.size();
        return invokeWithArguments(arguments.get(0), typeSubstitution);
    }

    public final List<ESEffect> invokeWithArguments(Computation arg, ESTypeSubstitution typeSubstitution) {
        Intrinsics.checkNotNullParameter(arg, "arg");
        Intrinsics.checkNotNullParameter(typeSubstitution, "typeSubstitution");
        if (arg instanceof ESValue) {
            return invokeWithValue((ESValue) arg, typeSubstitution);
        }
        return CollectionsKt.emptyList();
    }

    private final List<ConditionalEffect> invokeWithValue(ESValue value, ESTypeSubstitution typeSubstitution) {
        ESKotlinType eSKotlinType = new ESKotlinType(typeSubstitution.getSubstitutor().safeSubstitute(this.type.toKotlinType(typeSubstitution.getBuiltIns()).unwrap()));
        return CollectionsKt.listOf((Object[]) new ConditionalEffect[]{new ConditionalEffect(new ESIs(value, new IsFunctor(eSKotlinType, this.isNegated)), new ESReturns(ESConstants.INSTANCE.getTrueValue())), new ConditionalEffect(new ESIs(value, new IsFunctor(eSKotlinType, !this.isNegated)), new ESReturns(ESConstants.INSTANCE.getFalseValue()))});
    }
}
