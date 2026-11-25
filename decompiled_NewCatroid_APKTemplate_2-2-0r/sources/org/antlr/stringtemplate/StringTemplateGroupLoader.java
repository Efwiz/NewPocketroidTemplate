package org.antlr.stringtemplate;

/* JADX INFO: loaded from: classes12.dex */
public interface StringTemplateGroupLoader {
    StringTemplateGroup loadGroup(String str);

    StringTemplateGroup loadGroup(String str, Class cls, StringTemplateGroup stringTemplateGroup);

    StringTemplateGroup loadGroup(String str, StringTemplateGroup stringTemplateGroup);

    StringTemplateGroupInterface loadInterface(String str);
}
