package org.jetbrains.kotlin.js.naming;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;

/* JADX INFO: compiled from: SuggestedName.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B+\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b¢\u0006\u0002\u0010\nR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\t\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/js/naming/SuggestedName;", "", "names", "", "", "stable", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "scope", "(Ljava/util/List;ZLorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getNames", "()Ljava/util/List;", "getScope", "getStable", "()Z", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SuggestedName {
    private final DeclarationDescriptor descriptor;
    private final List<String> names;
    private final DeclarationDescriptor scope;
    private final boolean stable;

    public SuggestedName(List<String> names, boolean z, DeclarationDescriptor descriptor, DeclarationDescriptor scope) {
        Intrinsics.checkNotNullParameter(names, "names");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(scope, "scope");
        this.names = names;
        this.stable = z;
        this.descriptor = descriptor;
        this.scope = scope;
    }

    public final DeclarationDescriptor getDescriptor() {
        return this.descriptor;
    }

    public final List<String> getNames() {
        return this.names;
    }

    public final DeclarationDescriptor getScope() {
        return this.scope;
    }

    public final boolean getStable() {
        return this.stable;
    }
}
