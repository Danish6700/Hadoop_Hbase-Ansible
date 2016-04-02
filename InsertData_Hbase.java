import java.io.IOException;
//import java.io.File;
import org.apache.hadoop.conf.Configuration;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.commons.io.FileUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class InsertData{

   public static void main(String[] args) throws IOException {

      // Instantiating Configuration class
      Configuration config = HBaseConfiguration.create();

      // Instantiating HTable class
      HTable hTable = new HTable(config, "testable");
        JSONParser parser = new JSONParser();
        try {
		File dir = new File("dir");
		String extension = “json”;
		List<File> files = (List<File>) FileUtils.listFiles(dir, extension, true);
		for (File file : files) {
			System.out.println("file: " + file.getCanonicalPath());
		}

                Object obj = parser.parse(new FileReader("/usr/local/danish/jsondata.json"));
                JSONArray jsonarr = (JSONArray) obj;
				//List<Put> list;
				List<Put> list = new ArrayList<Put>();
                for(int i=0;i<jsonarr.size();)
                {
		for(;i<1000;i++)
		{
                JSONObject jobj =(JSONObject) jsonarr.get(i);
                String name = (String) jobj.get("locality-name");
                //System.out.println(name);
                String age = (String) jobj.get("region-id");
                //System.out.println(age);
                //String data=name+age;
				//String data=name+age;
                Put p = new Put(Bytes.toBytes(i));
                p.add(Bytes.toBytes("d"),Bytes.toBytes("data"),Bytes.toBytes(jobj.toString()));
				list.add(p);
                //hTable.put(p);
                }
				hTable.put(list);
		}
                System.out.println(jsonarr.size());
                }
                catch (FileNotFoundException e) {
                        e.printStackTrace();
                } catch (IOException e) {
                        e.printStackTrace();
                } catch (ParseException e) {
                        e.printStackTrace();
                }
/*
      // Instantiating Put class
      // accepts a row name.
      Put p = new Put(Bytes.toBytes("1")); 

      // adding values using add() method
      // accepts column family name, qualifier/row name ,value
      p.add(Bytes.toBytes("d"),Bytes.toBytes("data"),Bytes.toBytes("[{id:abc,location:delhi}]"));

      /*p.add(Bytes.toBytes("personal"),
      Bytes.toBytes("city"),Bytes.toBytes("hyderabad"));

      p.add(Bytes.toBytes("professional"),Bytes.toBytes("designation"),
      Bytes.toBytes("manager"));

      p.add(Bytes.toBytes("professional"),Bytes.toBytes("salary"),
      Bytes.toBytes("50000"));
      */
      // Saving the put Instance to the HTable.
  //    hTable.put(p);
      System.out.println("data inserted");

      // closing HTable
      hTable.close();
   }
}

