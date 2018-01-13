package io.happium.appium_client_service.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import org.springframework.boot.json.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class CapabilityParser implements JsonParser {

    @Override
    public Map<String, Object> parseMap(String json) {
        return null;
    }

    /**
     * Overridden method to parse a JSON String
     *
     * <p>
     *     This implementation of parseList() first creates
     *     a new ObjectMapper, which is required for JSON
     *     parsing in Spring (uses fasterxml - Spring abstractions).
     *     It then reads through all elements in the source JSON
     *     string and returns the list.
     * <p>
     *     If no elements are found, this will still return
     *     successfully, but the list will be empty.
     * <p>
     *     Note that this method is ONLY capable of returning basic
     *     java.lang.Objects - you must further process the objects
     *     in the caller
     *
     * @param json          String to parse
     * @return              List of objects (nested JsonNodes, usually)
     */
    @Override
    public List<Object> parseList(String json) {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonStringAsNode;

        List<Object> listOfObjects = new ArrayList<>();

        try {
            jsonStringAsNode = mapper.readTree( json );                 // Convert String to JsonNode
            Iterator<JsonNode> elements = jsonStringAsNode.elements();  // Get iterator for elements

            while ( elements.hasNext() ) {  // Iterate all elements

                JsonNode element = elements.next();
                listOfObjects.add( element );

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listOfObjects;
    }

}
