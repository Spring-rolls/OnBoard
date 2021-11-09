package OnBoard.example.OnBoard.Model;

import java.util.Arrays;

public class Splash {
    private Object[] results;


    public Splash( Object[] results) {
        this.results = results;
    }

    public Object[] getResults() {
        return results;
    }

    public void setResults(Object[] results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return Arrays.toString(results);
    }
}
