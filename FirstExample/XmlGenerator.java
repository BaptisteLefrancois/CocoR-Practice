
//Baptiste 
//Matthew
//Thomas 

import java.io.*;
public class XmlGenerator {
    PrintStream s;
    
    
    public XmlGenerator(String fileName)throws FileNotFoundException{
        s = new PrintStream(fileName);
    }
    
    public void printHeader()
    {
        s.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    }
    
    public void printOpenNodeTag(String nameAtr){       
        s.println("<Node name=\'"+nameAtr+"\'>");
    }
    
    public void printEmptyNodeTag(){
        s.println("<Node/>");
    }
    
    public void printCloseNodeTag(){
        s.println("</Node>");
    }
             
}
