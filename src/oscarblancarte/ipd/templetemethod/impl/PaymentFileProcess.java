package oscarblancarte.ipd.templetemethod.impl;

import java.io.File;
import java.io.FileOutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PaymentFileProcess extends AbstractFileProcessTemplete {

    private String log = "";

    public PaymentFileProcess(File file, String logPath, String movePath) {
        super(file, logPath, movePath);
    }
    
    @Override
    protected void validateName() throws Exception {
        String fileName = file.getName();
        if (!fileName.endsWith(".xml")) {
            throw new Exception("Invalid file name" + ", must end with .xml");
        }
        if (fileName.length() != 12) {
            throw new Exception("Invalid document format");
        }
    }

    @Override
    protected void processFile() throws Exception {
        try{
            File Archivo = new File(file.getPath());
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(Archivo);
            document.getDocumentElement().normalize();
            NodeList nList = document.getElementsByTagName("payment");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;                    
                    String Nombre = eElement.getElementsByTagName("bank").item(0).getTextContent();
                    String Tarjeta = eElement.getElementsByTagName("cardType").item(0).getTextContent();
                    String Monto = eElement.getElementsByTagName("amount").item(0).getTextContent();
                    String Cliente = eElement.getElementsByTagName("client").item(0).getTextContent();
                    String Estado = eElement.getElementsByTagName("status").item(0).getTextContent();
                    if(Nombre.equals("")){
                        log += "";
                    }
                    else{
                        log += Nombre.trim().toLowerCase();
                    }                    
                    if(Tarjeta.equals("Credito")){
                        log += "00100";
                    }
                    else{
                        log += "00200";
                    }                    
                    log += Monto.replace(".", "");
                    log += Cliente.trim().toLowerCase();
                    if(Estado.equals("S")){
                        log += "00100";
                    }
                    else{
                        log += "00200";
                    }
                }
            }
        }
        finally{
            try {
                
            } catch (Exception e) {
                
            }
        }
    }

    @Override
    protected void createLog() throws Exception {
        FileOutputStream out = null;
        try {
            File outFile = new File(logPath + "/" + file.getName());
            if (!outFile.exists()) {
                outFile.createNewFile();
            }
            out = new FileOutputStream(outFile, false);
            out.write(log.getBytes());
            out.flush();
        } finally {
            out.close();
        }
    }    
}
