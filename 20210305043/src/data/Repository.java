package data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

// Her türlü T tipi için basit generic depo sınıfı
public class Repository<T> {

    private final List<T> items = new ArrayList<>();

    public void add(T item) {
        items.add(item);
    }

    public List<T> getAll() {
        return items;
    }

    // Lambda ile arama yapmamızı sağlar
    public Optional<T> find(Predicate<T> predicate) {
        return items.stream().filter(predicate).findFirst();
    }
}
