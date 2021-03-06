package pl.coderslab.model.standards;

public interface DaoInterface<T> {
    
    public T[] loadAll();
    public T[] loadSortedWithLimit(ColumnsEnumInterface sortColumnName, SortType sortType, int limit, int offset);
    public T loadById(int id);
    public void saveToDb(T object);
    public void delete(T object);
    public int getCount();
    
    public enum SortType {
        ASC("ASC"), DESC("DESC");
        
        private String type;
        
        private SortType(String type) {
            this.type = type;
        }
        public String getName() {
            return type;
        }
    }
   
}
