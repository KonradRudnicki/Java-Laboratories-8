import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Document implements IWithName {
    private static final int MODVALUE = 100000000;
    public String name;
    public BST<Link> link;

    public Document(String name) {
        this.name = name.toLowerCase();
        link = new BST<>();
    }

    public Document(String name, Scanner scan) {
        this.name = name.toLowerCase();
        link = new BST<>();
        load(scan);
    }

    public void load(Scanner scan) {
        while (scan.hasNext()) {
            String line = scan.nextLine();
            if (line.equals("eod")) return;

            String[] oneLine = line.split(" ");
            String regex = "link=(.+)";
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher;

            for (String string : oneLine) {
                matcher = pattern.matcher(string);

                if (matcher.matches())
                    if (isCorrectLink(matcher.group(1))) {
                        link.add(new Link(matcher.group(1).toLowerCase()));
                    } else if (checkWithWeight(matcher.group(1))) {
                        link.add(createLink(matcher.group(1).toLowerCase()));
                    }
            }
        }
    }

    public static boolean checkWithWeight(String id) {
        String regex = "[a-zA-Z][a-zA-Z_0-9]*(.*)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(id);

        if (matcher.matches()) {
            String weightReg = "(\\({1}([1-9]{1}[0-9]*)\\){1})";
            Pattern weightPat = Pattern.compile(weightReg);
            Matcher weightMatch = weightPat.matcher(matcher.group(1));

            return weightMatch.matches();
        }

        return false;
    }

    public static boolean isCorrectLink(String id) {
        String regex = "[a-zA-Z][a-zA-Z_0-9]*";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(id);

        return matcher.matches();
    }

    public static boolean isCorrectId(String id) {
        String regex = "[a-z]*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(id);

        return matcher.matches();
    }

    // accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
    static Link createLink(String link) {
        String regex = "([a-zA-Z][a-zA-Z_0-9]*)\\((.*)\\)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(link);

        if (matcher.matches()) {
            return new Link(matcher.group(1), Integer.parseInt(matcher.group(2)));
        }

        regex = "([a-zA-Z][a-zA-Z_0-9]*)";
        pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(link);

        if (matcher.matches()) {
            return new Link(matcher.group(1));
        }

        return null;
    }

    @Override
    public String toString() {
        String retStr = "Document: " + name + "\n";
        retStr += link.toStringInOrder();

        return retStr;
    }

    public String toStringPreOrder() {
        String retStr = "Document: " + name + "\n";
        retStr += link.toStringPreOrder();

        return retStr;
    }

    public String toStringPostOrder() {
        String retStr = "Document: " + name + "\n";
        retStr += link.toStringPostOrder();

        return retStr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        Document document = (Document) o;

        return Objects.equals(name, document.name);
    }

    @Override
    public int hashCode() {
        int[] sequence = {7, 11, 13, 17, 19};
        char[] characters = this.name.toCharArray();
        int hashCode = characters[0];

        for (int i = 1; i < characters.length; i++) {
            hashCode = (hashCode * sequence[(i - 1) % 5] + characters[i]) % MODVALUE;
        }

        return hashCode;
    }

    @Override
    public String getName() {
        return name;
    }
}