package DataLoader.java;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.json.Path;


import java.io.File;

public class DataLoader {

    public static void main(String[] args) throws Exception {


        JedisPooled jedis = new JedisPooled(
                "redis://default:f2cfmGfeaXG2b4csr3aJBxD2aY9noKuD@wish-team-megaradiant-67697.db.redis.io:19686"
        );

        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = mapper.readTree(
                new File("prize.json"));

        JsonNode prizes = root.get("prizes");

        for (JsonNode prize : prizes) {

            int year = Integer.parseInt(
                    prize.get("year").asText());

            if (year >= 2013 && year <= 2023) {

                String category =
                        prize.get("category").asText();

                String key =
                        "prize:" + year + ":" + category;


                Object jsonObject = mapper.readValue(
                        prize.toString(),
                        Object.class);

                jedis.jsonSet(
                        key,
                        Path.ROOT_PATH,
                        jsonObject);

                System.out.println(
                        "Inserted " + key);
            }
        }

        System.out.println("Finished!");
    }
}