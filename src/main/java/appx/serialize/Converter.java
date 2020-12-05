package appx.serialize;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import appx.db.data.Musician;
import appx.db.data.Musician.MusicianBuilder;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.google.common.collect.ImmutableList;



public class Converter {

    public static Musician convert(final QueryResult ddbResponse, final String excluded) {
    	List<Map<String, AttributeValue>> items = !ddbResponse.getItems().isEmpty() ? ddbResponse.getItems()
                                                                               : ImmutableList.of();
        final MusicianBuilder resultBuilder = Musician.builder();
    	if(!items.isEmpty()) {
    		Map<String, AttributeValue> attList = items.get(0);

    		attList.forEach((key,value)->{
    			if(!key.equals(excluded)) {
    				switch (key) {
					case "Name":
						resultBuilder.name(value.getS());
						break;
					case "Born":
						resultBuilder.born(value.getS());
						break;
					case "Language":
						resultBuilder.language(value.getS());
						break;
					case "City":
						resultBuilder.city(value.getS());
						break;
					default:
						break;
					}
    			}
    			  
    		});
    	}
    	
        return resultBuilder.build();
    }
}
