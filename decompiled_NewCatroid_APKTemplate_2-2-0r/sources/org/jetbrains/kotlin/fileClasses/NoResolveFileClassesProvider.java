package org.jetbrains.kotlin.fileClasses;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;

/* JADX INFO: compiled from: Deprecated.kt */
/* JADX INFO: loaded from: classes4.dex */
@Deprecated(level = DeprecationLevel.ERROR, message = "Use JvmFileClassUtil instead.")
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fileClasses/NoResolveFileClassesProvider;", "Lorg/jetbrains/kotlin/fileClasses/JvmFileClassesProvider;", "()V", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NoResolveFileClassesProvider extends JvmFileClassesProvider {
    public static final NoResolveFileClassesProvider INSTANCE = new NoResolveFileClassesProvider();

    private NoResolveFileClassesProvider() {
    }
}
