package org.jetbrains.kotlin.com.intellij.openapi.roots;

import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.util.messages.Topic;

/* JADX INFO: loaded from: classes3.dex */
public abstract class LanguageLevelProjectExtension {
    public static final Topic<LanguageLevelChangeListener> LANGUAGE_LEVEL_CHANGED_TOPIC = Topic.create("Java language level", LanguageLevelChangeListener.class);

    public interface LanguageLevelChangeListener {
    }

    public abstract LanguageLevel getLanguageLevel();

    public abstract void setLanguageLevel(LanguageLevel languageLevel);

    public static LanguageLevelProjectExtension getInstance(Project project) {
        return (LanguageLevelProjectExtension) project.getService(LanguageLevelProjectExtension.class);
    }
}
