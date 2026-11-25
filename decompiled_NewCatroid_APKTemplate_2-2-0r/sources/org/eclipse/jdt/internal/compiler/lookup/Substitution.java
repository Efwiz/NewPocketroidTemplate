package org.eclipse.jdt.internal.compiler.lookup;

/* JADX INFO: loaded from: classes14.dex */
public interface Substitution {
    LookupEnvironment environment();

    boolean isRawSubstitution();

    TypeBinding substitute(TypeVariableBinding typeVariableBinding);
}
