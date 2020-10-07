package practica1;

public interface LinearList<T> {
        boolean isEmpty();
        int size();
        T get(int index);
        int indexOf(T element);
        T remove(int index);
        void add(int ndex, T theElement);
        String toString();

}
