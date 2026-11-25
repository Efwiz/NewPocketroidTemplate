package org.jetbrains.kotlin.analysis.providers.topics;

import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.util.messages.Topic;

/* JADX INFO: compiled from: KotlinTopics.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0007R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0007R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0007R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0007¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/topics/KotlinTopics;", "", "()V", "GLOBAL_MODULE_STATE_MODIFICATION", "Lorg/jetbrains/kotlin/com/intellij/util/messages/Topic;", "Lorg/jetbrains/kotlin/analysis/providers/topics/KotlinGlobalModuleStateModificationListener;", "getGLOBAL_MODULE_STATE_MODIFICATION", "()Lcom/intellij/util/messages/Topic;", "GLOBAL_SOURCE_MODULE_STATE_MODIFICATION", "Lorg/jetbrains/kotlin/analysis/providers/topics/KotlinGlobalSourceModuleStateModificationListener;", "getGLOBAL_SOURCE_MODULE_STATE_MODIFICATION", "GLOBAL_SOURCE_OUT_OF_BLOCK_MODIFICATION", "Lorg/jetbrains/kotlin/analysis/providers/topics/KotlinGlobalSourceOutOfBlockModificationListener;", "getGLOBAL_SOURCE_OUT_OF_BLOCK_MODIFICATION", "MODULE_OUT_OF_BLOCK_MODIFICATION", "Lorg/jetbrains/kotlin/analysis/providers/topics/KotlinModuleOutOfBlockModificationListener;", "getMODULE_OUT_OF_BLOCK_MODIFICATION", "MODULE_STATE_MODIFICATION", "Lorg/jetbrains/kotlin/analysis/providers/topics/KotlinModuleStateModificationListener;", "getMODULE_STATE_MODIFICATION", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinTopics {
    public static final KotlinTopics INSTANCE = new KotlinTopics();
    private static final Topic<KotlinModuleStateModificationListener> MODULE_STATE_MODIFICATION = new Topic<>(KotlinModuleStateModificationListener.class, Topic.BroadcastDirection.TO_CHILDREN, true);
    private static final Topic<KotlinModuleOutOfBlockModificationListener> MODULE_OUT_OF_BLOCK_MODIFICATION = new Topic<>(KotlinModuleOutOfBlockModificationListener.class, Topic.BroadcastDirection.TO_CHILDREN, true);
    private static final Topic<KotlinGlobalModuleStateModificationListener> GLOBAL_MODULE_STATE_MODIFICATION = new Topic<>(KotlinGlobalModuleStateModificationListener.class, Topic.BroadcastDirection.TO_CHILDREN, true);
    private static final Topic<KotlinGlobalSourceModuleStateModificationListener> GLOBAL_SOURCE_MODULE_STATE_MODIFICATION = new Topic<>(KotlinGlobalSourceModuleStateModificationListener.class, Topic.BroadcastDirection.TO_CHILDREN, true);
    private static final Topic<KotlinGlobalSourceOutOfBlockModificationListener> GLOBAL_SOURCE_OUT_OF_BLOCK_MODIFICATION = new Topic<>(KotlinGlobalSourceOutOfBlockModificationListener.class, Topic.BroadcastDirection.TO_CHILDREN, true);

    private KotlinTopics() {
    }

    public final Topic<KotlinModuleStateModificationListener> getMODULE_STATE_MODIFICATION() {
        return MODULE_STATE_MODIFICATION;
    }

    public final Topic<KotlinModuleOutOfBlockModificationListener> getMODULE_OUT_OF_BLOCK_MODIFICATION() {
        return MODULE_OUT_OF_BLOCK_MODIFICATION;
    }

    public final Topic<KotlinGlobalModuleStateModificationListener> getGLOBAL_MODULE_STATE_MODIFICATION() {
        return GLOBAL_MODULE_STATE_MODIFICATION;
    }

    public final Topic<KotlinGlobalSourceModuleStateModificationListener> getGLOBAL_SOURCE_MODULE_STATE_MODIFICATION() {
        return GLOBAL_SOURCE_MODULE_STATE_MODIFICATION;
    }

    public final Topic<KotlinGlobalSourceOutOfBlockModificationListener> getGLOBAL_SOURCE_OUT_OF_BLOCK_MODIFICATION() {
        return GLOBAL_SOURCE_OUT_OF_BLOCK_MODIFICATION;
    }
}
