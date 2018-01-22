package by.bsuir.search.ui;

import by.bsuir.search.document.Document;
import by.bsuir.search.document.FileContainer;
import by.bsuir.search.termsManager.TermsManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Interface {
    public static void listen () {
        List<FileContainer> files = Document.readsFileFromDirectory("docs/");
        List<FileContainer> learnFiles = Document.readsFileFromDirectory("learn_docs/");

        TermsManager tm = new TermsManager(files);

        BufferedReader br = null;

        try {

            br = new BufferedReader(new InputStreamReader(System.in));
            String category = "";
            while (true) {

                System.out.print("Enter something : ");
                String input = br.readLine();
                if (input.contains("select ")) {
                    if (!input.split(" ")[1].equals("*")) {

                        category = input.split(" ")[1];
                    } else {
                        category = "";
                    }
                    continue;
                }

                if ("q".equals(input)) {
                    System.out.println("Exit!");
                    System.exit(0);
                }

                System.out.println("Search : " + input);
                System.out.println("-----------\n");
                List<Double> result = tm.search(input, category, learnFiles);
                for (int i = 0; i < files.size(); i++) {
                    FileContainer file = files.get(i);
                    System.out.println(file.getName() + ": " + result.get(i));
//                    System.out.println("*********************");
//                    System.out.println(file.getContent().replaceAll("(?i)" + input,
//                            "<strong>" + input + "</strong>"));
//                    System.out.println("");
//                    System.out.println("&&&&&&&&&&&&&&&&&&&&&");

//                    System.out.println("*********************");
//                    String[] l = file.getContent().toLowerCase().split("\n");
//                    for (int j = 0; j < l.length; j++) {
//                        String s = l[j];
//                        int index = s.indexOf(input);
//                        int flag = 0;
//                        while (index != -1) {
//                            flag = 1;
//                            System.out.print("L" + (j+1) + "C" + (index + 1) + ", ");
//                            index = s.indexOf(input, index + 1);
//                        }
//                        if (flag == 1) {
//                            System.out.println();
//                        }
//                    }
//                    System.out.println();
//                    System.out.println("&&&&&&&&&&&&&&&&&&&&&");
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
