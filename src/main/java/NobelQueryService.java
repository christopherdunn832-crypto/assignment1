import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.search.Document;
import redis.clients.jedis.search.SearchResult;

import java.util.Set;

public class NobelQueryService {

    private final JedisPooled jedis;
    private final ObjectMapper mapper;

    public NobelQueryService() {

        jedis = new JedisPooled(
                "redis://default:f2cfmGfeaXG2b4csr3aJBxD2aY9noKuD@wish-team-megaradiant-67697.db.redis.io:19686"
        );

        mapper = new ObjectMapper();
    }

    /**
     * Query 1
     * Given a category and year range,
     * return total number of laureates.
     */
    public int countLaureatesByCategory(String category,
                                        int startYear,
                                        int endYear) {

        int totalLaureates = 0;

        try {

            SearchResult result =
                    jedis.ftSearch(
                            "prizeIdx",
                            "@category:{" + category + "}"
                    );

            for (Document doc : result.getDocuments()) {

                String json = doc.getString("$");

                JsonNode root =
                        mapper.readTree(json);

                int year =
                        Integer.parseInt(
                                root.get("year").asText());

                if (year >= startYear &&
                        year <= endYear) {

                    JsonNode laureates =
                            root.get("laureates");

                    totalLaureates +=
                            laureates.size();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalLaureates;
    }

    /**
     * Query 2
     * Given a keyword,
     * return number of laureates whose
     * motivation contains the keyword.
     */
    public int countKeyword(String keyword) {

        int count = 0;

        try {

            Set<String> keys =
                    jedis.keys("prize:*");

            for (String key : keys) {

                Object jsonObject = jedis.jsonGet(key);

                JsonNode root =
                        mapper.valueToTree(jsonObject);

                JsonNode laureates =
                        root.get("laureates");

                if (laureates == null) {
                    continue;
                }

                for (JsonNode laureate : laureates) {

                    String motivation =
                            laureate
                                    .get("motivation")
                                    .asText()
                                    .toLowerCase();

                    if (motivation.contains(
                            keyword.toLowerCase())) {

                        count++;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    /**
     * Query 3
     * Given first and last name,
     * return year, category and motivation.
     */
    public LaureateRecord findLaureateRecord(
            String firstName,
            String lastName) {

        try {

            Set<String> keys = jedis.keys("prize:*");

            for (String key : keys) {

                Object jsonObject = jedis.jsonGet(key);

                JsonNode root =
                        mapper.valueToTree(jsonObject);

                JsonNode laureates =
                        root.get("laureates");

                if (laureates == null) {
                    continue;
                }

                for (JsonNode laureate : laureates) {

                    JsonNode firstNode =
                            laureate.get("firstname");

                    JsonNode lastNode =
                            laureate.get("surname");

                    if (firstNode == null ||
                            lastNode == null) {
                        continue;
                    }

                    String first =
                            firstNode.asText();

                    String last =
                            lastNode.asText();

                    if (first.equalsIgnoreCase(firstName)
                            && last.equalsIgnoreCase(lastName)) {

                        String motivation = "";

                        JsonNode motivationNode =
                                laureate.get("motivation");

                        if (motivationNode != null) {
                            motivation =
                                    motivationNode.asText();
                        }

                        return new LaureateRecord(
                                root.get("year").asText(),
                                root.get("category").asText(),
                                motivation
                        );
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new LaureateRecord(
                "Not Found",
                "Not Found",
                "Not Found"
        );
    }
}