package org.jetbrains.kotlin.com.intellij.psi.util;

import java.util.function.Predicate;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;
import org.jetbrains.kotlin.com.intellij.util.messages.Topic;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiModificationTracker extends ModificationTracker {

    @Deprecated
    public static final Key JAVA_STRUCTURE_MODIFICATION_COUNT;
    public static final Key MODIFICATION_COUNT;

    @Deprecated
    public static final Key OUT_OF_CODE_BLOCK_MODIFICATION_COUNT;
    public static final Topic<Listener> TOPIC;

    @FunctionalInterface
    public interface Listener {
        void modificationCountChanged();
    }

    ModificationTracker forLanguage(Language language);

    ModificationTracker forLanguages(Predicate<? super Language> predicate);

    @Deprecated
    ModificationTracker getJavaStructureModificationTracker();

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker
    long getModificationCount();

    public static final class SERVICE {
        private SERVICE() {
        }

        public static PsiModificationTracker getInstance(Project project) {
            return (PsiModificationTracker) project.getService(PsiModificationTracker.class);
        }
    }

    static {
        Key keyCreate = Key.create("MODIFICATION_COUNT");
        MODIFICATION_COUNT = keyCreate;
        OUT_OF_CODE_BLOCK_MODIFICATION_COUNT = keyCreate;
        JAVA_STRUCTURE_MODIFICATION_COUNT = keyCreate;
        TOPIC = new Topic<>(Listener.class, Topic.BroadcastDirection.TO_PARENT);
    }
}
