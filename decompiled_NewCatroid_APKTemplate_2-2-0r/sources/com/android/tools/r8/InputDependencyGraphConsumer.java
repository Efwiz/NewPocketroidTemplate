package com.android.tools.r8;

import com.android.tools.r8.origin.Origin;
import java.nio.file.Path;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface InputDependencyGraphConsumer {
    void accept(Origin origin, Path path);

    default void acceptProguardApplyMapping(Origin origin, Path path) {
        accept(origin, path);
    }

    default void acceptProguardClassObfuscationDictionary(Origin origin, Path path) {
        accept(origin, path);
    }

    default void acceptProguardInJars(Origin origin, Path path) {
        accept(origin, path);
    }

    default void acceptProguardInclude(Origin origin, Path path) {
        accept(origin, path);
    }

    default void acceptProguardLibraryJars(Origin origin, Path path) {
        accept(origin, path);
    }

    default void acceptProguardObfuscationDictionary(Origin origin, Path path) {
        accept(origin, path);
    }

    default void acceptProguardPackageObfuscationDictionary(Origin origin, Path path) {
        accept(origin, path);
    }

    void finished();
}
