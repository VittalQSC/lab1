package by.bsuir.search.ui;

import by.bsuir.search.document.Document;
import by.bsuir.search.document.FileContainer;
import by.bsuir.search.termsManager.TermsManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Interface {
    public static void listen () {
        List<FileContainer> files = Document.readsFileFromDirectory("docs/");

        TermsManager tm = new TermsManager(files);

        BufferedReader br = null;

        try {

            br = new BufferedReader(new InputStreamReader(System.in));
            String category = "";
            while (true) {

                System.out.print("Enter something : ");
                String input = br.readLine();
                if (input.contains("select ")) {
                    category = input.split(" ")[1];
                    continue;
                }

                if ("q".equals(input)) {
                    System.out.println("Exit!");
                    System.exit(0);
                }

                System.out.println("Search : " + input);
                System.out.println("-----------\n");
                List<Double> result = tm.search(input, category);
                for (int i = 0; i < files.size(); i++) {
                    FileContainer file = files.get(i);
                    System.out.println(file.getName() + ": " + result.get(i));
                }
                System.out.println("-----------\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
