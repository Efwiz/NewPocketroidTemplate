package org.jetbrains.kotlin.fir.resolve.substitution;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: Substitutors.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0001¢\u0006\u0002\u0010\u0004J\t\u0010\u0005\u001a\u00020\u0001HÂ\u0003J\t\u0010\u0006\u001a\u00020\u0001HÂ\u0003J\u001d\u0010\u0007\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00012\b\b\u0002\u0010\u0003\u001a\u00020\u0001HÆ\u0001J\u0013\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bHÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/substitution/ChainedSubstitutor;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "first", "second", "(Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;)V", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "substituteOrNull", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", ModuleXmlParser.TYPE, "toString", "", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ChainedSubstitutor extends ConeSubstitutor {
    private final ConeSubstitutor first;
    private final ConeSubstitutor second;

    public static /* synthetic */ ChainedSubstitutor copy$default(ChainedSubstitutor chainedSubstitutor, ConeSubstitutor coneSubstitutor, ConeSubstitutor coneSubstitutor2, int i, Object obj) {
        if ((i & 1) != 0) {
            coneSubstitutor = chainedSubstitutor.first;
        }
        if ((i & 2) != 0) {
            coneSubstitutor2 = chainedSubstitutor.second;
        }
        return chainedSubstitutor.copy(coneSubstitutor, coneSubstitutor2);
    }

    public final ChainedSubstitutor copy(ConeSubstitutor first, ConeSubstitutor second) {
        Intrinsics.checkNotNullParameter(first, "first");
        Intrinsics.checkNotNullParameter(second, "second");
        return new ChainedSubstitutor(first, second);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ChainedSubstitutor)) {
            return false;
        }
        ChainedSubstitutor chainedSubstitutor = (ChainedSubstitutor) other;
        return Intrinsics.areEqual(this.first, chainedSubstitutor.first) && Intrinsics.areEqual(this.second, chainedSubstitutor.second);
    }

    public int hashCode() {
        return (this.first.hashCode() * 31) + this.second.hashCode();
    }

    public ChainedSubstitutor(ConeSubstitutor first, ConeSubstitutor second) {
        Intrinsics.checkNotNullParameter(first, "first");
        Intrinsics.checkNotNullParameter(second, "second");
        this.first = first;
        this.second = second;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor
    public ConeKotlinType substituteOrNull(ConeKotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        ConeKotlinType coneKotlinTypeSubstituteOrNull = this.first.substituteOrNull(type);
        if (coneKotlinTypeSubstituteOrNull == null) {
            return this.second.substituteOrNull(type);
        }
        return this.second.substituteOrSelf(coneKotlinTypeSubstituteOrNull);
    }

    public String toString() {
        return this.first + " then " + this.second;
    }
}
