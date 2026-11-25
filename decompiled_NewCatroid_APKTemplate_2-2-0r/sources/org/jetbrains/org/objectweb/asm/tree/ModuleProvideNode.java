package org.jetbrains.org.objectweb.asm.tree;

import java.util.List;
import org.jetbrains.org.objectweb.asm.ModuleVisitor;

/* JADX INFO: loaded from: classes6.dex */
public class ModuleProvideNode {
    public List<String> providers;
    public String service;

    public ModuleProvideNode(String str, List<String> list) {
        this.service = str;
        this.providers = list;
    }

    public void accept(ModuleVisitor moduleVisitor) {
        moduleVisitor.visitProvide(this.service, (String[]) this.providers.toArray(new String[0]));
    }
}
