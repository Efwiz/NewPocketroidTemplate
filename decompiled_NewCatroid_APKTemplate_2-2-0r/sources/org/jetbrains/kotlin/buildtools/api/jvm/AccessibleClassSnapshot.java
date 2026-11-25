package org.jetbrains.kotlin.buildtools.api.jvm;

import kotlin.Metadata;
import org.jetbrains.kotlin.buildtools.api.ExperimentalBuildToolsApi;

/* JADX INFO: compiled from: ClasspathEntrySnapshot.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0006À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/buildtools/api/jvm/AccessibleClassSnapshot;", "Lorg/jetbrains/kotlin/buildtools/api/jvm/ClassSnapshot;", "classAbiHash", "", "getClassAbiHash", "()J", "kotlin-build-tools-api"}, k = 1, mv = {1, 5, 1}, xi = 48)
@ExperimentalBuildToolsApi
public interface AccessibleClassSnapshot extends ClassSnapshot {
    long getClassAbiHash();
}
