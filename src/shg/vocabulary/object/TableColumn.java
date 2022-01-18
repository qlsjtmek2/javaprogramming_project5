package shg.vocabulary.object;

import javax.swing.table.DefaultTableModel;

public class TableColumn {
	private final int preferredWidth;
	private ColumnValue value;
	public String name;
	
	private TableColumn(TableColumnBuilder builder) {
		super();
		this.name = builder.name;
		this.preferredWidth = builder.preferredWidth;
		this.value = builder.value;
	}
	
	public int getPreferredWidth() {
		return preferredWidth;
	}
	
	public String value(Word word, DefaultTableModel model) {
		return value.value(word, model);
	}
	
	public static class TableColumnBuilder {
		private int preferredWidth;
		private ColumnValue value;
		private String name;
		
		public TableColumnBuilder() {}

		public TableColumnBuilder setPreferredWidth(int preferredWidth) {
			this.preferredWidth = preferredWidth;
			return this;
		}

		public TableColumnBuilder setValue(ColumnValue value) {
			this.value = value;
			return this;
		}

		public TableColumnBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public TableColumn build() {
			return new TableColumn(this);
		}
	}
}