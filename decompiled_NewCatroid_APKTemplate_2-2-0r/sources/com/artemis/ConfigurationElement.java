package com.artemis;

/* JADX INFO: loaded from: classes8.dex */
class ConfigurationElement<T> implements Comparable<ConfigurationElement<T>> {
    public T item;
    public final Class<?> itemType;
    public final int priority;

    public ConfigurationElement(T t, int i) {
        this.item = t;
        this.itemType = t.getClass();
        this.priority = i;
    }

    @Override // java.lang.Comparable
    public int compareTo(ConfigurationElement<T> configurationElement) {
        return Integer.compare(configurationElement.priority, this.priority);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.item.equals(((ConfigurationElement) obj).item);
    }

    public int hashCode() {
        return this.item.hashCode();
    }

    public static <T> ConfigurationElement<T> of(T t) {
        return of(t, 0);
    }

    public static <T> ConfigurationElement<T> of(T t, int i) {
        return new ConfigurationElement<>(t, i);
    }
}
