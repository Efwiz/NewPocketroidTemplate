package org.eclipse.jdt.internal.compiler.impl;

import org.eclipse.jdt.internal.compiler.env.AccessRestriction;
import org.eclipse.jdt.internal.compiler.env.IBinaryType;
import org.eclipse.jdt.internal.compiler.env.ICompilationUnit;
import org.eclipse.jdt.internal.compiler.env.ISourceType;
import org.eclipse.jdt.internal.compiler.lookup.PackageBinding;

/* JADX INFO: loaded from: classes14.dex */
public interface ITypeRequestor {
    void accept(IBinaryType iBinaryType, PackageBinding packageBinding, AccessRestriction accessRestriction);

    void accept(ICompilationUnit iCompilationUnit, AccessRestriction accessRestriction);

    void accept(ISourceType[] iSourceTypeArr, PackageBinding packageBinding, AccessRestriction accessRestriction);
}
