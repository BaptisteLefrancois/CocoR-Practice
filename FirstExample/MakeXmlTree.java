
//Baptiste 
//Matthew 
//Thomas

import java.io.FileNotFoundException;

public class MakeXmlTree {
    public static void main(String[] args) {
        String inFileName = args[0];
        String outFileName = args[1];
        Scanner scanner = new Scanner(inFileName);
        Parser parser = new Parser(scanner);
        try {
        parser.xml = new XmlGenerator(outFileName);
        parser.Parse();
        System.out.println(parser.errors.count + " errors detected");
        } catch (FileNotFoundException e) {
        System.out.println("-- cannot create file " + outFileName);
        }
    }
}
