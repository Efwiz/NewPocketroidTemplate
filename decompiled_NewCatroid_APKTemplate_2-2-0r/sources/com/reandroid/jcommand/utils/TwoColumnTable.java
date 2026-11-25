package com.reandroid.jcommand.utils;

/* JADX INFO: loaded from: classes11.dex */
public class TwoColumnTable {
    private boolean drawBorder;
    private final SpreadSheet mSpreadSheet = new SpreadSheet();
    private String tab2 = "  ";
    private String mColumnSeparator = "   ";
    private int maxWidth = 80;

    public void setMaxWidth(int i) {
        this.maxWidth = i;
    }

    public void setTab2(String str) {
        this.tab2 = str;
    }

    public void setColumnSeparator(String str) {
        this.mColumnSeparator = str;
    }

    public void setDrawBorder(boolean z) {
        this.drawBorder = z;
    }

    public SpreadSheet getSpreadSheet() {
        return this.mSpreadSheet;
    }

    public void clear() {
        getSpreadSheet().clear();
    }

    public String build() {
        return buildTable().toString();
    }

    public SpreadSheet buildTable() {
        SpreadSheet spreadSheet = getSpreadSheet();
        spreadSheet.fixColumnCount();
        spreadSheet.fitColumnWidth(0);
        int columnPrintWidth = spreadSheet.getColumnPrintWidth(0);
        int i = this.maxWidth - columnPrintWidth;
        if (i <= 0) {
            i = 1;
        }
        int mergedCellsWidth = spreadSheet.getMergedCellsWidth() - columnPrintWidth;
        int iFitColumnWidth = spreadSheet.fitColumnWidth(1);
        int columnPrintWidth2 = i - (spreadSheet.getColumnPrintWidth(1) - iFitColumnWidth);
        if (mergedCellsWidth <= iFitColumnWidth) {
            mergedCellsWidth = iFitColumnWidth;
        }
        if (mergedCellsWidth <= columnPrintWidth2) {
            columnPrintWidth2 = mergedCellsWidth;
        }
        spreadSheet.setColumnWidth(1, columnPrintWidth2);
        return spreadSheet.format();
    }

    public SpreadSheet.Row addRow(Object obj, Object obj2) {
        SpreadSheet.Row row = new SpreadSheet.Row();
        SpreadSheet.Cell cell = new SpreadSheet.Cell(obj);
        SpreadSheet.Cell cell2 = new SpreadSheet.Cell(obj2);
        row.add(cell);
        row.add(cell2);
        String str = this.tab2;
        if (this.drawBorder) {
            str = "|" + str;
        }
        cell.setBorderLeft(str);
        cell.setBorderRight("");
        cell2.setBorderLeft(this.mColumnSeparator);
        if (this.drawBorder) {
            cell2.setBorderRight(" |");
        } else {
            cell2.setBorderRight("");
        }
        this.mSpreadSheet.add(row);
        return row;
    }

    public boolean hasSeparatorRow() {
        SpreadSheet spreadSheet = this.mSpreadSheet;
        SpreadSheet.Row row = spreadSheet.get(spreadSheet.size() - 1);
        if (row != null) {
            return row.get(0) instanceof SpreadSheet.SeparatorCells;
        }
        return false;
    }

    public SpreadSheet.MergedCells addMergedRow(Object obj) {
        SpreadSheet.MergedCells mergedCellsMerge = addRow(obj, "").merge(0, 2);
        mergedCellsMerge.setHorizontalIndent(0);
        mergedCellsMerge.setVerticalIndent(0);
        if (this.drawBorder) {
            mergedCellsMerge.setBorderLeft("|");
            mergedCellsMerge.setBorderRight(" |");
            mergedCellsMerge.setHorizontalIndent(1);
        }
        return mergedCellsMerge;
    }

    public SpreadSheet.MergedCells addMergedRowTabbed(Object obj) {
        SpreadSheet.MergedCells mergedCellsAddMergedRow = addMergedRow(obj);
        String str = this.tab2;
        if (this.drawBorder) {
            str = "|" + str;
        }
        mergedCellsAddMergedRow.setHorizontalIndent(0);
        mergedCellsAddMergedRow.setBorderLeft(str);
        return mergedCellsAddMergedRow;
    }

    public void addSeparator() {
        if (!this.drawBorder || hasSeparatorRow()) {
            return;
        }
        boolean z = this.mSpreadSheet.size() == 0;
        SpreadSheet.SeparatorCells separatorCellsAsSeparator = addRow("", "").asSeparator();
        String str = " ";
        separatorCellsAsSeparator.setBorderLeft(z ? " " : "|");
        if (!z) {
            str = "_|";
        }
        separatorCellsAsSeparator.setBorderRight(str);
        separatorCellsAsSeparator.setHorizontalIndent(0);
        separatorCellsAsSeparator.setVerticalIndent(0);
    }

    public void addLine() {
        if (hasSeparatorRow()) {
            return;
        }
        boolean z = this.drawBorder && this.mSpreadSheet.size() != 0;
        SpreadSheet.SeparatorCells separatorCellsAsSeparator = addRow("", "").asSeparator();
        String str = " ";
        separatorCellsAsSeparator.setBorderLeft(z ? "|" : " ");
        if (z) {
            str = "_|";
        }
        separatorCellsAsSeparator.setBorderRight(str);
        separatorCellsAsSeparator.setHorizontalIndent(0);
        separatorCellsAsSeparator.setVerticalIndent(0);
    }
}
