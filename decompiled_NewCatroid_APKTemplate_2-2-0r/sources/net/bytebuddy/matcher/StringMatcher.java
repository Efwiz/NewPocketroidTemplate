package net.bytebuddy.matcher;

import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.utility.nullability.MaybeNull;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: loaded from: classes12.dex */
@HashCodeAndEqualsPlugin.Enhance
public class StringMatcher extends ElementMatcher.Junction.ForNonNullValues<String> {
    private final Mode mode;
    private final String value;

    @Override // net.bytebuddy.matcher.ElementMatcher.Junction.ForNonNullValues
    public boolean equals(@MaybeNull Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        StringMatcher stringMatcher = (StringMatcher) obj;
        return this.mode.equals(stringMatcher.mode) && this.value.equals(stringMatcher.value);
    }

    @Override // net.bytebuddy.matcher.ElementMatcher.Junction.ForNonNullValues
    public int hashCode() {
        return (((super.hashCode() * 31) + this.value.hashCode()) * 31) + this.mode.hashCode();
    }

    public StringMatcher(String str, Mode mode) {
        this.value = str;
        this.mode = mode;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.bytebuddy.matcher.ElementMatcher.Junction.ForNonNullValues
    public boolean doMatch(String str) {
        return this.mode.matches(this.value, str);
    }

    public String toString() {
        return this.mode.getDescription() + '(' + this.value + ')';
    }

    public enum Mode {
        EQUALS_FULLY(Namer.EQUALS_METHOD_NAME) { // from class: net.bytebuddy.matcher.StringMatcher.Mode.1
            @Override // net.bytebuddy.matcher.StringMatcher.Mode
            protected boolean matches(String str, String str2) {
                return str2.equals(str);
            }
        },
        EQUALS_FULLY_IGNORE_CASE("equalsIgnoreCase") { // from class: net.bytebuddy.matcher.StringMatcher.Mode.2
            @Override // net.bytebuddy.matcher.StringMatcher.Mode
            protected boolean matches(String str, String str2) {
                return str2.equalsIgnoreCase(str);
            }
        },
        STARTS_WITH("startsWith") { // from class: net.bytebuddy.matcher.StringMatcher.Mode.3
            @Override // net.bytebuddy.matcher.StringMatcher.Mode
            protected boolean matches(String str, String str2) {
                return str2.startsWith(str);
            }
        },
        STARTS_WITH_IGNORE_CASE("startsWithIgnoreCase") { // from class: net.bytebuddy.matcher.StringMatcher.Mode.4
            @Override // net.bytebuddy.matcher.StringMatcher.Mode
            protected boolean matches(String str, String str2) {
                return str2.toLowerCase().startsWith(str.toLowerCase());
            }
        },
        ENDS_WITH("endsWith") { // from class: net.bytebuddy.matcher.StringMatcher.Mode.5
            @Override // net.bytebuddy.matcher.StringMatcher.Mode
            protected boolean matches(String str, String str2) {
                return str2.endsWith(str);
            }
        },
        ENDS_WITH_IGNORE_CASE("endsWithIgnoreCase") { // from class: net.bytebuddy.matcher.StringMatcher.Mode.6
            @Override // net.bytebuddy.matcher.StringMatcher.Mode
            protected boolean matches(String str, String str2) {
                return str2.toLowerCase().endsWith(str.toLowerCase());
            }
        },
        CONTAINS("contains") { // from class: net.bytebuddy.matcher.StringMatcher.Mode.7
            @Override // net.bytebuddy.matcher.StringMatcher.Mode
            protected boolean matches(String str, String str2) {
                return str2.contains(str);
            }
        },
        CONTAINS_IGNORE_CASE("containsIgnoreCase") { // from class: net.bytebuddy.matcher.StringMatcher.Mode.8
            @Override // net.bytebuddy.matcher.StringMatcher.Mode
            protected boolean matches(String str, String str2) {
                return str2.toLowerCase().contains(str.toLowerCase());
            }
        },
        MATCHES("matches") { // from class: net.bytebuddy.matcher.StringMatcher.Mode.9
            @Override // net.bytebuddy.matcher.StringMatcher.Mode
            protected boolean matches(String str, String str2) {
                return str2.matches(str);
            }
        };

        private final String description;

        protected abstract boolean matches(String str, String str2);

        Mode(String str) {
            this.description = str;
        }

        protected String getDescription() {
            return this.description;
        }
    }
}
