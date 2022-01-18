package shg.vocabulary.object;

import javax.swing.table.DefaultTableModel;

@FunctionalInterface
public interface ColumnValue {
    public String value(Word word, DefaultTableModel model);
}