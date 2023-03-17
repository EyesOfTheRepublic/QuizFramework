public class Seed<T> {
    private T value;

    public Seed(T value) {
        this.value = (T)value;
    }
    <T> T get() {
        return (T)value;
    }
    String valueOf() {
        return value.toString();
    }
}
