package digitrecognizer;

import java.util.Arrays;

public class Record {
    public Integer Number;
    public Integer[] Pixels;


    @Override
    public String toString() {
        return "Record{" +
                "Number=" + Number +
                ", Pixels=" + Arrays.toString(Pixels) +
                '}';
    }
}
