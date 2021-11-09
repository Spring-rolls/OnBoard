package OnBoard.example.OnBoard.Model;

import java.util.Arrays;

public class Splash {
    public O[] results;
    public class O{
        public urls urls;

        public O(O.urls urls) {
            this.urls = urls;
        }

        public class urls{
            public String raw;

            public urls(String raw) {
                this.raw = raw;
            }

            @Override
            public String toString() {
                return "urls{" +
                        "raw='" + raw + '\'' +
                        '}';
            }
        };
    };


    public Splash( O[] results) {
        this.results = results;
    }

    public O[] getResults() {
        return results;
    }

    public void setResults(O[] results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return Arrays.toString(results);
    }
}
