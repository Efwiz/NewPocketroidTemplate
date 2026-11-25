package org.luaj.vm2.ast;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public class FuncName extends SyntaxElement {
    public List dots;
    public String method;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public final Name f1047name;

    public FuncName(String str) {
        this.f1047name = new Name(str);
    }

    public void adddot(String str) {
        if (this.dots == null) {
            this.dots = new ArrayList();
        }
        this.dots.add(str);
    }
}
