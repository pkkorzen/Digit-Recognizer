package digitrecognizer;

import java.util.Arrays;

public class Record {
    private Integer Number;
    private Integer[] Pixels;

    public Integer getNumber() {
        return Number;
    }

    public Integer[] getPixels() {
        return Pixels;
    }

    public void setNumber(Integer number) {
        Number = number;
    }

    public void setPixels(Integer[] pixels) {
        Pixels = pixels;
    }

    @Override
    public String toString() {
        return "Record{" +
                "Number=" + Number +
                ", Pixels=" + Arrays.toString(Pixels) +
                '}';
    }
}
