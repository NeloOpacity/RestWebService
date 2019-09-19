package com.example.boxwebservice;

import com.example.boxwebservice.model.Box;
import com.example.boxwebservice.model.Item;
import com.example.boxwebservice.utils.BoxXmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String filename = args[0];
        File file = new File(filename);
        if (file.exists()) {
            try (FileInputStream fs = new FileInputStream(file)) {

                jdbcTemplate.execute("drop table if exists BOX");
                jdbcTemplate.execute("drop table if exists ITEM");
                jdbcTemplate.execute("CREATE TABLE BOX\n" +
                        "(ID INTEGER PRIMARY KEY,\n" +
                        "CONTAINED_IN INTEGER\n" +
                        ")");
                jdbcTemplate.execute("CREATE TABLE ITEM\n" +
                        "(ID INTEGER PRIMARY KEY,\n" +
                        "CONTAINED_IN INTEGER REFERENCES BOX(ID),\n" +
                        "COLOR VARCHAR(100)\n" +
                        ");");

                DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = documentFactory.newDocumentBuilder();
                Document xmlDoc = builder.parse(fs);

                XPath xPath = XPathFactory.newInstance().newXPath();
                NodeList boxNodeList = (NodeList) xPath.evaluate(".//Box", xmlDoc, XPathConstants.NODESET);
                List<Box> boxList = BoxXmlUtils.getBoxes(boxNodeList);
                for (Box box : boxList) {
                    jdbcTemplate.execute(String.format("insert into BOX values(%d,%d)", box.getId(),
                            box.getContainedIn()));
                }

                NodeList itemNodeList = (NodeList) xPath.evaluate(".//Item", xmlDoc, XPathConstants.NODESET);
                List<Item> itemList = BoxXmlUtils.getItems(itemNodeList);
                for (Item item : itemList) {
                    jdbcTemplate.execute(String.format("insert into ITEM values(%d,%d,%s)", item.getId(),
                            item.getContainedIn(),
                            item.getColor() == null ? "null" : "'" + item.getColor() + "'"));
                }
            } catch (Exception ex) {
                System.out.println(ex);
                System.exit(-1);
            }
        } else {System.exit(-1);}
    }
}
