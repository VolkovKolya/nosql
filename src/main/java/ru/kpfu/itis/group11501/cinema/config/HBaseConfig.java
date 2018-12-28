package ru.kpfu.itis.group11501.cinema.config;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Amir Kadyrov
 * Date: 24.12.2018
 */
public class HBaseConfig {
    public static void main(String[] args) throws IOException {
        Configuration hConf = HBaseConfiguration.create();
        hConf.set("hbase.zookeeper.quorum", "192.168.43.244");
        hConf.setInt("hbase.zookeeper.property.clientPort", 2181);

        try {
            HBaseAdmin.available(hConf);
            System.out.println("Damir");
        } catch (Exception e){
            e.printStackTrace();
        }
        //System.exit(0);
        Connection connection = ConnectionFactory.createConnection(hConf);
        Admin admin = connection.getAdmin();


        TableName tableName = TableName.valueOf("films");
        String country = "country";
        String movies = "movies";
        String video = "video";
        String stats = "statistics";

        /*HTableDescriptor desc = new HTableDescriptor(tableName);
        desc.addFamily(new HColumnDescriptor(country));
        desc.addFamily(new HColumnDescriptor(movies));
        desc.addFamily(new HColumnDescriptor(video));
        desc.addFamily(new HColumnDescriptor(stats));
        admin.createTable(desc);*/

        Table table = connection.getTable(tableName);

        // Adding data to DB
        byte[] row = Bytes.toBytes("country1");
        Put p = new Put(row);
        p.addColumn(country.getBytes(), "name".getBytes(), Bytes.toBytes("Russia"));
        p.addColumn(country.getBytes(), "ip_start".getBytes(), Bytes.toBytes("172.168.0.1"));
        p.addColumn(country.getBytes(), "ip_end".getBytes(), Bytes.toBytes("172.168.255.254"));
        table.put(p);

        List<String> keys = new ArrayList<>();

        for (int i = 0; i < 5; i++){
            Random r = new Random();
            String key = "movie" + i + "_" + "199" + r.nextInt(9);
            keys.add(key);
            byte[] new_row = Bytes.toBytes(key);
            Put put = new Put(new_row);
            put.addColumn(movies.getBytes(), "desc".getBytes(), ("Movie " + i + " " + r.nextInt(50)).getBytes());
            put.addColumn(video.getBytes(), "length".getBytes(), ByteBuffer.allocate(4).putInt(r.nextInt(1000)).array());
            put.addColumn(video.getBytes(), "size".getBytes(), ByteBuffer.allocate(4).putInt(r.nextInt(300)).array());
            put.addColumn(video.getBytes(), "link".getBytes(), ("http://danilbestboy.com/movie/" + key).getBytes());
            table.put(p);
        }

        long start = System.nanoTime();

        for (int i = 0; i < 1_000_000; i++){
            Date date = new Date();
            Random r = new Random();
            String key = keys.get(r.nextInt(4));
            Put put = new Put(Bytes.toBytes(key));
            put.addColumn(stats.getBytes(), "timestamp".getBytes(), ByteBuffer.allocate(8).putLong(date.getTime()).array());
            put.addColumn(stats.getBytes(), "percent".getBytes(), ByteBuffer.allocate(4).putInt(r.nextInt(100)).array());
            put.addColumn(stats.getBytes(), "uID".getBytes(), ("" + i + 1000).getBytes());
            table.put(p);
        }

        double second = ((double)(System.nanoTime() - start))/1_000_000;
        System.out.println("Elapsed: "+ 100000/second + " ops/s");

        //Retrieving data from DB
        /*Get g = new Get(row);
        Result r = table.get(g);
        byte[] value = r.getValue(country.getBytes(), "name".getBytes());*/
    }
}
