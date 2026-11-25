package io.github.rosemoe.sora.lang.completion.snippet;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;

/* JADX INFO: loaded from: classes11.dex */
public class CodeSnippet implements Cloneable {
    private final List<SnippetItem> items;
    private final List<PlaceholderDefinition> placeholders;

    public CodeSnippet(List<SnippetItem> list, List<PlaceholderDefinition> list2) {
        this.items = list;
        this.placeholders = list2;
    }

    public boolean checkContent() {
        int endIndex = 0;
        for (SnippetItem snippetItem : this.items) {
            if (snippetItem.getStartIndex() != endIndex) {
                return false;
            }
            if ((snippetItem instanceof PlaceholderItem) && !this.placeholders.contains(((PlaceholderItem) snippetItem).getDefinition())) {
                return false;
            }
            endIndex = snippetItem.getEndIndex();
        }
        TreeSet treeSet = new TreeSet();
        for (PlaceholderDefinition placeholderDefinition : this.placeholders) {
            if (treeSet.contains(Integer.valueOf(placeholderDefinition.getId()))) {
                return false;
            }
            treeSet.add(Integer.valueOf(placeholderDefinition.getId()));
        }
        return true;
    }

    public List<SnippetItem> getItems() {
        return this.items;
    }

    public List<PlaceholderDefinition> getPlaceholderDefinitions() {
        return this.placeholders;
    }

    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public CodeSnippet m2800clone() {
        ArrayList arrayList = new ArrayList(this.placeholders.size());
        HashMap map = new HashMap();
        for (PlaceholderDefinition placeholderDefinition : this.placeholders) {
            PlaceholderDefinition placeholderDefinition2 = new PlaceholderDefinition(placeholderDefinition.getId(), placeholderDefinition.getChoices(), placeholderDefinition.getElements(), placeholderDefinition.getTransform());
            arrayList.add(placeholderDefinition2);
            map.put(placeholderDefinition, placeholderDefinition2);
        }
        ArrayList arrayList2 = new ArrayList(this.items.size());
        Iterator<SnippetItem> it = this.items.iterator();
        while (it.hasNext()) {
            SnippetItem snippetItemMo2801clone = it.next().mo2801clone();
            arrayList2.add(snippetItemMo2801clone);
            if (snippetItemMo2801clone instanceof PlaceholderItem) {
                PlaceholderItem placeholderItem = (PlaceholderItem) snippetItemMo2801clone;
                if (map.get(placeholderItem.getDefinition()) != null) {
                    placeholderItem.setDefinition((PlaceholderDefinition) map.get(placeholderItem.getDefinition()));
                }
            }
        }
        return new CodeSnippet(arrayList2, arrayList);
    }

    public static class Builder {
        private final List<PlaceholderDefinition> definitions;
        private int index;
        private List<SnippetItem> items;

        public Builder() {
            this(new ArrayList());
        }

        public Builder(List<PlaceholderDefinition> list) {
            this.items = new ArrayList();
            this.definitions = list;
        }

        public Builder addPlainText(String str) {
            if (!this.items.isEmpty()) {
                List<SnippetItem> list = this.items;
                if (list.get(list.size() - 1) instanceof PlainTextItem) {
                    List<SnippetItem> list2 = this.items;
                    PlainTextItem plainTextItem = (PlainTextItem) list2.get(list2.size() - 1);
                    plainTextItem.setText(plainTextItem.getText() + str);
                    plainTextItem.setIndex(plainTextItem.getStartIndex(), plainTextItem.getEndIndex() + str.length());
                    this.index += str.length();
                    return this;
                }
            }
            this.items.add(new PlainTextItem(str, this.index));
            this.index += str.length();
            return this;
        }

        public Builder addInterpolatedShell(String str) {
            this.items.add(new InterpolatedShellItem(str, this.index));
            return this;
        }

        public Builder addPlaceholder(int i) {
            return addPlaceholder(i, (String) null);
        }

        public Builder addPlaceholder(int i, List<String> list) {
            if (list.isEmpty()) {
                return addPlaceholder(i);
            }
            if (list.size() == 1) {
                return addPlaceholder(i, list.get(0));
            }
            addPlaceholder(i, list.get(0));
            for (PlaceholderDefinition placeholderDefinition : this.definitions) {
                if (placeholderDefinition.getId() == i) {
                    ((PlaceholderDefinition) Objects.requireNonNull(placeholderDefinition)).setChoices(list);
                    return this;
                }
            }
            placeholderDefinition = null;
            ((PlaceholderDefinition) Objects.requireNonNull(placeholderDefinition)).setChoices(list);
            return this;
        }

        public Builder addPlaceholder(int i, Transform transform) {
            if (transform == null) {
                return addPlaceholder(i);
            }
            addPlaceholder(i);
            for (PlaceholderDefinition placeholderDefinition : this.definitions) {
                if (placeholderDefinition.getId() == i) {
                    ((PlaceholderDefinition) Objects.requireNonNull(placeholderDefinition)).setTransform(transform);
                    return this;
                }
            }
            placeholderDefinition = null;
            ((PlaceholderDefinition) Objects.requireNonNull(placeholderDefinition)).setTransform(transform);
            return this;
        }

        public Builder addPlaceholder(int i, String str) {
            ArrayList arrayList = new ArrayList();
            if (!TextUtils.isEmpty(str)) {
                arrayList.add(new PlainPlaceholderElement(str));
            }
            return addComplexPlaceholder(i, arrayList);
        }

        public Builder addComplexPlaceholder(int i, List<PlaceHolderElement> list) {
            PlaceholderDefinition placeholderDefinition;
            Iterator<PlaceholderDefinition> it = this.definitions.iterator();
            do {
                if (!it.hasNext()) {
                    placeholderDefinition = null;
                    break;
                }
                placeholderDefinition = it.next();
            } while (placeholderDefinition.getId() != i);
            if (placeholderDefinition == null) {
                placeholderDefinition = new PlaceholderDefinition(i);
                this.definitions.add(placeholderDefinition);
            }
            placeholderDefinition.getElements().addAll(list);
            this.items.add(new PlaceholderItem(placeholderDefinition, this.index));
            return this;
        }

        public Builder addVariable(String str, String str2) {
            this.items.add(new VariableItem(this.index, str, str2));
            return this;
        }

        public Builder addVariable(String str, Transform transform) {
            this.items.add(new VariableItem(this.index, str, null, transform));
            return this;
        }

        public Builder addVariable(VariableItem variableItem) {
            variableItem.setIndex(this.index);
            this.items.add(variableItem);
            return this;
        }

        public CodeSnippet build() {
            return new CodeSnippet(this.items, this.definitions);
        }
    }
}
