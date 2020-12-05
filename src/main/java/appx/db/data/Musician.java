package appx.db.data;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Musician {
	private String name;
	private String born;
	private String language;
	private String city;
}
