package com.badlogic.gdx.input;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/input/TextInputWrapper.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface TextInputWrapper {
    int getSelectionEnd();

    int getSelectionStart();

    String getText();

    void setPosition(int i);

    void setText(String str);

    boolean shouldClose();
}
