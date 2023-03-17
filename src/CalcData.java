public class CalcData<T> {
    private T value;

    public CalcData(T value) {
        this.value = (T)value;
    }
    <T> T get() {
        return (T)value;
    }
    String valueOf() {
        return value.toString();
    }
}
