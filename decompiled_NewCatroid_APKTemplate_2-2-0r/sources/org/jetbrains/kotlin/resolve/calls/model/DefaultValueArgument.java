package org.jetbrains.kotlin.resolve.calls.model;

import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.psi.ValueArgument;

/* JADX INFO: loaded from: classes6.dex */
public class DefaultValueArgument implements ResolvedValueArgument {
    public static final DefaultValueArgument DEFAULT = new DefaultValueArgument();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/resolve/calls/model/DefaultValueArgument", "getArguments"));
    }

    private DefaultValueArgument() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument
    public List<ValueArgument> getArguments() {
        List<ValueArgument> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(0);
        }
        return listEmptyList;
    }

    public String toString() {
        return "|DEFAULT|";
    }
}
