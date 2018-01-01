package pl.coderslab.model.standards;

public abstract class DataType<T> {
    public abstract int getId();
    protected abstract T setId(int id);
}
