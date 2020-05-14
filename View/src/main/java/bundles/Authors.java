package bundles;

import java.util.ListResourceBundle;

public class Authors extends ListResourceBundle {

    public Object[][] getContents() {
        return contents;
    }

    private Object[][] contents = {
        {"Autor1", "Jakub Wachala"},
        {"Autor2", "Radoslaw Grela"}};
}
