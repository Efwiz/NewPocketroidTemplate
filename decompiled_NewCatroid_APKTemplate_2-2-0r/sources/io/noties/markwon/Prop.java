package io.noties.markwon;

/* JADX INFO: loaded from: classes11.dex */
public class Prop<T> {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f289name;

    public static <T> Prop<T> of(Class<T> cls, String str) {
        return new Prop<>(str);
    }

    public static <T> Prop<T> of(String str) {
        return new Prop<>(str);
    }

    Prop(String str) {
        this.f289name = str;
    }

    public String name() {
        return this.f289name;
    }

    public T get(RenderProps renderProps) {
        return (T) renderProps.get(this);
    }

    public T get(RenderProps renderProps, T t) {
        return (T) renderProps.get(this, t);
    }

    public T require(RenderProps renderProps) {
        T t = get(renderProps);
        if (t != null) {
            return t;
        }
        throw new NullPointerException(this.f289name);
    }

    public void set(RenderProps renderProps, T t) {
        renderProps.set(this, t);
    }

    public void clear(RenderProps renderProps) {
        renderProps.clear(this);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.f289name.equals(((Prop) obj).f289name);
    }

    public int hashCode() {
        return this.f289name.hashCode();
    }

    public String toString() {
        return "Prop{name='" + this.f289name + "'}";
    }
}
