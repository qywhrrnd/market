package market;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
	private int write_code;
	private String userid;
	private String subject;
	private String contents;
	private int price;
	private String filename;
	private int love;
	private int see;
	private int status_code;
	private String status_type;

	
	
}