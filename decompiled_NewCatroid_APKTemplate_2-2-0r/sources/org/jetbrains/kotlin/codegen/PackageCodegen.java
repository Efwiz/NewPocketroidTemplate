package org.jetbrains.kotlin.codegen;

import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;

/* JADX INFO: loaded from: classes3.dex */
public interface PackageCodegen {
    void generate();

    PackageFragmentDescriptor getPackageFragment();
}
