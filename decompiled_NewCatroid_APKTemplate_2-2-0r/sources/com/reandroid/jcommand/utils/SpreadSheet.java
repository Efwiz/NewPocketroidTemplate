package com.reandroid.jcommand.utils;

import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes11.dex */
public class SpreadSheet implements Iterable<Row> {
    public static final int INDENT_BOTTOM = 2;
    public static final int INDENT_CENTER = 1;
    public static final int INDENT_LEFT = 0;
    public static final int INDENT_MIDDLE = 1;
    public static final int INDENT_RIGHT = 2;
    public static final int INDENT_TOP = 0;
    private final List<Row> rowList = new ArrayList();

    public SpreadSheet format() {
        SpreadSheet spreadSheet = new SpreadSheet();
        Iterator<Row> it = iterator();
        while (it.hasNext()) {
            spreadSheet.addAll(it.next().getSplitLines());
        }
        return spreadSheet;
    }

    public SpreadSheet add(Row row) {
        this.rowList.add(row);
        return this;
    }

    public SpreadSheet addAll(Collection<? extends Row> collection) {
        this.rowList.addAll(collection);
        return this;
    }

    public Row get(int i) {
        if (i < 0 || i >= this.rowList.size()) {
            return null;
        }
        return this.rowList.get(i);
    }

    public int size() {
        return this.rowList.size();
    }

    public void clear() {
        this.rowList.clear();
    }

    public int getPrintWidth() {
        Iterator<Row> it = iterator();
        int i = 0;
        while (it.hasNext()) {
            int printWidth = it.next().getPrintWidth();
            if (printWidth > i) {
                i = printWidth;
            }
        }
        return i;
    }

    public int getMergedCellsWidth() {
        Iterator<Row> it = iterator();
        int i = 0;
        while (it.hasNext()) {
            int mergedCellsWidth = it.next().getMergedCellsWidth();
            if (mergedCellsWidth > i) {
                i = mergedCellsWidth;
            }
        }
        return i;
    }

    public void setIndent(int i, int i2) {
        Iterator<Row> it = iterator();
        while (it.hasNext()) {
            it.next().setIndent(i, i2);
        }
    }

    public int getColumnCount() {
        Iterator<Row> it = iterator();
        int i = 0;
        while (it.hasNext()) {
            int columnCount = it.next().getColumnCount();
            if (columnCount > i) {
                i = columnCount;
            }
        }
        return i;
    }

    public void setColumnCount(int i) {
        Iterator<Row> it = iterator();
        while (it.hasNext()) {
            it.next().ensureSize(i);
        }
    }

    public void fixColumnCount() {
        setColumnCount(getColumnCount());
    }

    public void setBorderLeft(int i, String str) {
        Iterator<Row> it = iterator();
        while (it.hasNext()) {
            it.next().setBorderLeft(i, str);
        }
    }

    public void setBorderRight(int i, String str) {
        Iterator<Row> it = iterator();
        while (it.hasNext()) {
            it.next().setBorderRight(i, str);
        }
    }

    public int itemWidth(int i) {
        Iterator<Row> it = iterator();
        int i2 = 0;
        while (it.hasNext()) {
            int iItemWidth = it.next().itemWidth(i);
            if (iItemWidth > i2) {
                i2 = iItemWidth;
            }
        }
        return i2;
    }

    public int getColumnPrintWidth(int i) {
        Iterator<Row> it = iterator();
        int i2 = 0;
        while (it.hasNext()) {
            int columnPrintWidth = it.next().getColumnPrintWidth(i);
            if (columnPrintWidth > i2) {
                i2 = columnPrintWidth;
            }
        }
        return i2;
    }

    public void setColumnWidth(int i, int i2) {
        Iterator<Row> it = iterator();
        while (it.hasNext()) {
            it.next().setColumnWidth(i, i2);
        }
    }

    public int fitColumnWidth(int i) {
        int iItemWidth = itemWidth(i);
        setColumnWidth(i, iItemWidth);
        return iItemWidth;
    }

    public void fitColumnWidth() {
        int columnCount = getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            fitColumnWidth(i);
        }
    }

    @Override // java.lang.Iterable
    public Iterator<Row> iterator() {
        return this.rowList.iterator();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int size = size();
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                sb.append('\n');
            }
            sb.append(get(i));
        }
        return sb.toString();
    }

    public static class Row implements Iterable<Cell> {
        private final List<Cell> cellList = new ArrayList();
        private String mTab = "";

        public void add(Cell cell) {
            this.cellList.add(cell);
        }

        public void add(int i, Cell cell) {
            this.cellList.add(i, cell);
        }

        public String getTab() {
            return this.mTab;
        }

        public void setTab(String str) {
            if (str == null) {
                str = "";
            }
            this.mTab = str;
        }

        public MergedCells merge(int i, int i2) {
            ArrayList arrayList = new ArrayList(i2);
            for (int i3 = 0; i3 < i2; i3++) {
                arrayList.add(remove(i));
            }
            MergedCells mergedCells = new MergedCells(arrayList);
            add(i, mergedCells);
            return mergedCells;
        }

        public SeparatorCells asSeparator() {
            Cell cell = get(0);
            if (cell instanceof SeparatorCells) {
                return (SeparatorCells) cell;
            }
            int size = size();
            ArrayList arrayList = new ArrayList(size);
            for (int i = 0; i < size; i++) {
                arrayList.add(remove(0));
            }
            SeparatorCells separatorCells = new SeparatorCells(arrayList);
            add(separatorCells);
            return separatorCells;
        }

        public Cell get(int i) {
            if (i < 0 || i >= this.cellList.size()) {
                return null;
            }
            return this.cellList.get(i);
        }

        public Cell getLast() {
            return get(size() - 1);
        }

        private Cell getCell(int i) {
            Cell cell;
            int size = this.cellList.size();
            for (int i2 = 0; i2 < size; i2++) {
                Cell cell2 = this.cellList.get(i2);
                if (i2 == i) {
                    return cell2 instanceof MergedCells ? ((MergedCells) cell2).get(0) : cell2;
                }
                if ((cell2 instanceof MergedCells) && (cell = ((MergedCells) cell2).get(i + i2)) != null) {
                    return cell;
                }
            }
            return null;
        }

        public Cell remove(int i) {
            if (i < 0 || i >= this.cellList.size()) {
                return null;
            }
            return this.cellList.remove(i);
        }

        public int size() {
            return this.cellList.size();
        }

        public int getColumnCount() {
            Iterator<Cell> it = iterator();
            int cellCount = 0;
            while (it.hasNext()) {
                cellCount += it.next().getCellCount();
            }
            return cellCount;
        }

        public int getPrintWidth() {
            int length = getTab().length();
            Iterator<Cell> it = iterator();
            while (it.hasNext()) {
                length += it.next().getPrintWidth();
            }
            return length;
        }

        public int getMergedCellsWidth() {
            Cell first;
            for (Cell cell : this) {
                if ((cell instanceof MergedCells) && (first = ((MergedCells) cell).getFirst()) != null) {
                    return first.itemWidth();
                }
            }
            return 0;
        }

        public int getColumnPrintWidth(int i) {
            Cell cell = getCell(i);
            if (cell != null) {
                return cell.getPrintWidth();
            }
            return 0;
        }

        public void ensureSize(int i) {
            int columnCount = i - getColumnCount();
            if (columnCount <= 0) {
                return;
            }
            Cell last = getLast();
            if (last instanceof MergedCells) {
                ((MergedCells) last).addEmptyCells(columnCount);
                return;
            }
            for (int i2 = 0; i2 < columnCount; i2++) {
                add(new Cell(""));
            }
        }

        public int itemWidth(int i) {
            Cell cell = get(i);
            if (cell == null) {
                return 0;
            }
            return cell.itemWidth();
        }

        public void setIndent(int i, int i2) {
            Cell cell = get(i);
            if (cell != null) {
                cell.setHorizontalIndent(i2);
            }
        }

        public void setColumnWidth(int i, int i2) {
            Cell cell = getCell(i);
            if (cell != null) {
                cell.setColumnWidth(i2);
            }
        }

        public void setBorderLeft(int i, String str) {
            Cell cell = get(i);
            if (cell != null) {
                cell.setBorderLeft(str);
            }
        }

        public void setBorderRight(int i, String str) {
            Cell cell = get(i);
            if (cell != null) {
                cell.setBorderRight(str);
            }
        }

        public List<Row> getSplitLines() {
            ArrayList arrayList = new ArrayList();
            int splitLinesSize = getSplitLinesSize();
            setSplitLinesSize(splitLinesSize);
            int size = size();
            for (int i = 0; i < splitLinesSize; i++) {
                Row row = new Row();
                for (int i2 = 0; i2 < size; i2++) {
                    row.add(get(i2).getSplitLine(i));
                }
                arrayList.add(row);
            }
            return arrayList;
        }

        private void setSplitLinesSize(int i) {
            Iterator<Cell> it = iterator();
            while (it.hasNext()) {
                it.next().setSplitLinesSize(i);
            }
        }

        private int getSplitLinesSize() {
            Iterator<Cell> it = iterator();
            int i = 0;
            while (it.hasNext()) {
                int splitLinesSize = it.next().getSplitLinesSize();
                if (splitLinesSize > i) {
                    i = splitLinesSize;
                }
            }
            return i;
        }

        @Override // java.lang.Iterable
        public Iterator<Cell> iterator() {
            return this.cellList.iterator();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(getTab());
            int size = size();
            for (int i = 0; i < size; i++) {
                sb.append(get(i));
            }
            return sb.toString();
        }
    }

    public static class Cell {
        private String borderLeft;
        private String borderRight;
        private int columnWidth;
        private int horizontalIndent;
        private Object item;
        private List<Cell> splitLines;
        private int verticalIndent;

        private int widthOf(char c) {
            return (c == '\n' || c == '\r') ? 0 : 1;
        }

        public int getCellCount() {
            return 1;
        }

        public Cell(Object obj) {
            this.item = obj;
            this.borderLeft = "";
            this.borderRight = "  ";
            this.horizontalIndent = 0;
            this.verticalIndent = 0;
        }

        public Cell() {
            this(null);
        }

        public Object getItem() {
            return this.item;
        }

        public void setItem(Object obj) {
            this.item = obj;
        }

        public Cell copyOf(Object obj) {
            Cell cell = new Cell(obj);
            cell.setHorizontalIndent(getHorizontalIndent());
            cell.setVerticalIndent(getVerticalIndent());
            cell.setColumnWidth(getColumnWidth());
            cell.setBorderLeft(getBorderLeft());
            cell.setBorderRight(getBorderRight());
            return cell;
        }

        public String getBorderLeft() {
            return this.borderLeft;
        }

        public void setBorderLeft(String str) {
            this.borderLeft = str;
        }

        public String getBorderRight() {
            return this.borderRight;
        }

        public void setBorderRight(String str) {
            this.borderRight = str;
        }

        public String getItemString() {
            Object item = getItem();
            return item == null ? "" : item.toString();
        }

        public int getPrintWidth() {
            return getBorderLeft().length() + getColumnWidth() + getBorderRight().length();
        }

        public int itemWidth() {
            return widthOf(getItemString());
        }

        int widthOf(String str) {
            int length = str.length();
            int iWidthOf = 0;
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                iWidthOf += widthOf(str.charAt(i2));
                if (str.charAt(i2) == '\n') {
                    if (iWidthOf > i) {
                        i = iWidthOf;
                    }
                    iWidthOf = 0;
                }
            }
            return iWidthOf > i ? iWidthOf : i;
        }

        public int getHorizontalIndent() {
            return this.horizontalIndent;
        }

        public void setHorizontalIndent(int i) {
            if (i != this.horizontalIndent) {
                if (i < 0 || i > 2) {
                    throw new IllegalArgumentException("Invalid horizontal indent: '" + i + "', should be bn 0~2");
                }
                this.horizontalIndent = i;
                this.splitLines = null;
            }
        }

        public int getVerticalIndent() {
            return this.verticalIndent;
        }

        public void setVerticalIndent(int i) {
            if (i != this.verticalIndent) {
                if (i < 0 || i > 2) {
                    throw new IllegalArgumentException("Invalid vertical indent: '" + i + "', should be bn 0~2");
                }
                this.verticalIndent = i;
                this.splitLines = null;
            }
        }

        public int getColumnWidth() {
            int i = this.columnWidth;
            return i == 0 ? itemWidth() : i;
        }

        public void setColumnWidth(int i) {
            if (i != this.columnWidth) {
                this.columnWidth = i;
                this.splitLines = null;
            }
        }

        public Cell getSplitLine(int i) {
            return getSplitLines().get(i);
        }

        public int getSplitLinesSize() {
            return getSplitLines().size();
        }

        public void setSplitLinesSize(int i) {
            int i2;
            List<Cell> splitLines = getSplitLines();
            String strFillSpace = SpreadSheet.fillSpace(getColumnWidth());
            int size = i - splitLines.size();
            int verticalIndent = getVerticalIndent();
            if (verticalIndent == 2) {
                i2 = 0;
            } else if (verticalIndent == 1) {
                int i3 = size / 2;
                i2 = size - i3;
                size = i3;
            } else {
                i2 = size;
                size = 0;
            }
            for (int i4 = 0; i4 < size; i4++) {
                splitLines.add(0, copyOf(strFillSpace));
            }
            for (int i5 = 0; i5 < i2; i5++) {
                splitLines.add(copyOf(strFillSpace));
            }
        }

        public List<Cell> getSplitLines() {
            List<Cell> list = this.splitLines;
            if (list != null) {
                return list;
            }
            List<Cell> listSplitLines = splitLines();
            this.splitLines = listSplitLines;
            return listSplitLines;
        }

        private List<Cell> splitLines() {
            int columnWidth = getColumnWidth();
            String[] strArrSplitNewLineOrWidth = SpreadSheet.splitNewLineOrWidth(columnWidth, getItemString());
            ArrayList arrayList = new ArrayList(strArrSplitNewLineOrWidth.length);
            for (String str : strArrSplitNewLineOrWidth) {
                arrayList.add(copyOf(indentHorizontally(columnWidth, str)));
            }
            return arrayList;
        }

        String indentHorizontally(int i, String str) {
            int length = i - str.length();
            if (length == 0) {
                return str;
            }
            int horizontalIndent = getHorizontalIndent();
            int i2 = 0;
            if (horizontalIndent != 2) {
                if (horizontalIndent == 1) {
                    int i3 = length / 2;
                    i2 = length - i3;
                    length = i3;
                } else {
                    i2 = length;
                    length = 0;
                }
            }
            StringBuilder sb = new StringBuilder();
            SpreadSheet.fillSpace(sb, length);
            sb.append(str);
            SpreadSheet.fillSpace(sb, i2);
            return sb.toString();
        }

        public String toString() {
            return getBorderLeft() + getItemString() + getBorderRight();
        }
    }

    public static class MergedCells extends Cell implements Iterable<Cell> {
        private final List<Cell> childCells;

        @Override // com.reandroid.jcommand.utils.SpreadSheet.Cell
        public int itemWidth() {
            return 0;
        }

        public MergedCells(List<Cell> list) {
            this.childCells = list;
        }

        @Override // com.reandroid.jcommand.utils.SpreadSheet.Cell
        String indentHorizontally(int i, String str) {
            return super.indentHorizontally(i, str);
        }

        @Override // com.reandroid.jcommand.utils.SpreadSheet.Cell
        public MergedCells copyOf(Object obj) {
            ArrayList arrayList = new ArrayList();
            Cell first = getFirst();
            if (first != null) {
                arrayList.add(first.copyOf(obj));
                int size = size();
                for (int i = 1; i < size; i++) {
                    arrayList.add(get(i).copyOf(""));
                }
            }
            MergedCells mergedCellsCreateNew = createNew(arrayList);
            mergedCellsCreateNew.setHorizontalIndent(getHorizontalIndent());
            mergedCellsCreateNew.setVerticalIndent(getVerticalIndent());
            mergedCellsCreateNew.setBorderLeft(getBorderLeft());
            mergedCellsCreateNew.setBorderRight(getBorderRight());
            return mergedCellsCreateNew;
        }

        MergedCells createNew(List<Cell> list) {
            return new MergedCells(list);
        }

        @Override // com.reandroid.jcommand.utils.SpreadSheet.Cell
        public int getCellCount() {
            return size();
        }

        public Cell get(int i) {
            if (i < 0 || i >= this.childCells.size()) {
                return null;
            }
            return this.childCells.get(i);
        }

        public void add(Cell cell) {
            this.childCells.add(cell);
        }

        public int size() {
            return this.childCells.size();
        }

        public Cell getFirst() {
            return get(0);
        }

        @Override // com.reandroid.jcommand.utils.SpreadSheet.Cell
        public Object getItem() {
            Cell first = getFirst();
            if (first != null) {
                return first.getItem();
            }
            return null;
        }

        @Override // com.reandroid.jcommand.utils.SpreadSheet.Cell
        public void setItem(Object obj) {
            Cell first = getFirst();
            if (first != null) {
                first.setItem(obj);
            }
        }

        void addEmptyCells(int i) {
            for (int i2 = 0; i2 < i; i2++) {
                add(new Cell(""));
            }
        }

        @Override // com.reandroid.jcommand.utils.SpreadSheet.Cell
        public int getColumnWidth() {
            Iterator<Cell> it = iterator();
            int printWidth = 0;
            while (it.hasNext()) {
                printWidth += it.next().getPrintWidth();
            }
            int length = (printWidth - getBorderLeft().length()) - getBorderRight().length();
            if (length < 0) {
                return 0;
            }
            return length;
        }

        @Override // java.lang.Iterable
        public Iterator<Cell> iterator() {
            return this.childCells.iterator();
        }
    }

    public static class SeparatorCells extends MergedCells {
        private char mChar;

        @Override // com.reandroid.jcommand.utils.SpreadSheet.MergedCells
        /* bridge */ /* synthetic */ MergedCells createNew(List list) {
            return createNew((List<Cell>) list);
        }

        public SeparatorCells(List<Cell> list) {
            super(list);
            this.mChar = '_';
        }

        public char getChar() {
            return this.mChar;
        }

        public void setChar(char c) {
            this.mChar = c;
        }

        @Override // com.reandroid.jcommand.utils.SpreadSheet.MergedCells, com.reandroid.jcommand.utils.SpreadSheet.Cell
        public MergedCells copyOf(Object obj) {
            SeparatorCells separatorCells = (SeparatorCells) super.copyOf(obj);
            separatorCells.setChar(getChar());
            return separatorCells;
        }

        @Override // com.reandroid.jcommand.utils.SpreadSheet.MergedCells
        SeparatorCells createNew(List<Cell> list) {
            return new SeparatorCells(list);
        }

        @Override // com.reandroid.jcommand.utils.SpreadSheet.Cell
        public String toString() {
            return getBorderLeft() + SpreadSheet.fill(getChar(), getColumnWidth()) + getBorderRight();
        }
    }

    public static String[] splitNewLineOrWidth(int i, String str) {
        int length = str.length();
        int iPlaceNewline = i == length ? -length : 0;
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            char cCharAt = str.charAt(i3);
            sb.append(cCharAt);
            iPlaceNewline++;
            if (cCharAt == '\n' || iPlaceNewline == i) {
                i2++;
                iPlaceNewline = cCharAt != '\n' ? placeNewline(sb, i) : 0;
            }
        }
        String string = sb.toString();
        int length2 = string.length();
        int i4 = i2 + 1;
        String[] strArr = new String[i4];
        StringBuilder sb2 = new StringBuilder();
        int i5 = 0;
        for (int i6 = 0; i6 < length2; i6++) {
            char cCharAt2 = string.charAt(i6);
            if (cCharAt2 == '\n') {
                strArr[i5] = sb2.toString();
                sb2 = new StringBuilder();
                i5++;
            } else {
                sb2.append(cCharAt2);
            }
        }
        if (i5 < i4) {
            strArr[i5] = sb2.toString();
        }
        return strArr;
    }

    private static int placeNewline(StringBuilder sb, int i) {
        int length = sb.length();
        int i2 = i / 2;
        if (i2 >= length) {
            return 0;
        }
        int i3 = length - i2;
        for (int i4 = length - 1; i4 > i3; i4--) {
            char cCharAt = sb.charAt(i4);
            if (cCharAt == '\n') {
                return 0;
            }
            if (cCharAt == ' ' || cCharAt == '\t') {
                sb.deleteCharAt(i4);
                sb.insert(i4, '\n');
                return length - i4;
            }
        }
        sb.append('\n');
        return 0;
    }

    public static String fillSpace(int i) {
        return fill(StringUtil.SPACE, i);
    }

    public static void fillSpace(StringBuilder sb, int i) {
        fill(sb, StringUtil.SPACE, i);
    }

    public static String fill(char c, int i) {
        StringBuilder sb = new StringBuilder(i);
        fill(sb, c, i);
        return sb.toString();
    }

    public static void fill(StringBuilder sb, char c, int i) {
        if (c != 0) {
            for (int i2 = 0; i2 < i; i2++) {
                sb.append(c);
            }
        }
    }
}
