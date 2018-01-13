package by.bsuir.search.document;

public class FileContainer {
    private String name;
    private String category;
    private String content;

    public FileContainer(String name, String category, String content) {
        this.name = name;
        this.category = category;
        this.content = content;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
