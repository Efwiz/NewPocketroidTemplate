package org.jetbrains.kotlin.analyzer;

import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;

/* JADX INFO: compiled from: TrackableModuleInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0004À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/TrackableModuleInfo;", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "createModificationTracker", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/ModificationTracker;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface TrackableModuleInfo extends ModuleInfo {
    ModificationTracker createModificationTracker();
}
