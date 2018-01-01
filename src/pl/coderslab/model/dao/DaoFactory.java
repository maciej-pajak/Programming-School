package pl.coderslab.model.dao;

import java.util.function.Supplier;

public class DaoFactory<T> {
    private Supplier<T> supplier;
    
    public DaoFactory(Supplier<T> supplier) {
        this.supplier = supplier;
    }
    
    T create() {
        return supplier.get();
    }
}
