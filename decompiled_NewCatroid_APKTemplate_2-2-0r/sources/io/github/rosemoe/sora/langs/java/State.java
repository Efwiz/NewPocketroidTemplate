package io.github.rosemoe.sora.langs.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes11.dex */
public class State {
    public int state = 0;
    public boolean hasBraces = false;
    public List<String> identifiers = null;

    public void addIdentifier(CharSequence charSequence) {
        if (this.identifiers == null) {
            this.identifiers = new ArrayList();
        }
        if (charSequence instanceof String) {
            this.identifiers.add((String) charSequence);
        } else {
            this.identifiers.add(charSequence.toString());
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        State state = (State) obj;
        return this.state == state.state && this.hasBraces == state.hasBraces;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.state), Boolean.valueOf(this.hasBraces));
    }
}
