package email;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmailDTO {
	private String senderName;
	private String senderMail;
	private String email;
	private String userid;
	private String subject;
	private String message;
	

}
