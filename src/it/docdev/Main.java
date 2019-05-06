package it.docdev;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {

    public static void main(String[] args) {
        //parola da cercare
        Query request = new Query("dizionario");

        try {
            URL url = new URL("https://www.sillabario.net/index.lm?key=" + request.getRequest());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.246");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            //System.out.println(content);
            Document doc = Jsoup.parse(content.toString());
            request.setResponse(doc.select("strong").text().split(" ")[2]);

            System.out.println(request);

        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

    static class Query {
        private int id;
        private String request;
        private String response;

        Query(String request) {
            this.request = request;
            this.response = response;
        }

        String getRequest() {
            return request;
        }

        void setRequest(String request) {
            this.request = request;
        }

        String getResponse() {
            return response;
        }

        void setResponse(String response) {
            this.response = response;
            this.id = (this.request + this.response).hashCode();
        }

        @Override
        public String toString() {
            return "QueryId : " + this.id + "\n" +
                    "Searched Word : " + this.request + "\n" +
                    "Syllabed Word : " + this.response + "\n";

        }
    }
}
