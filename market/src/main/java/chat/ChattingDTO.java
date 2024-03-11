package chat;

import java.sql.Timestamp;

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
public class ChattingDTO {
	private String fromid;
	private String toid;
	private String chatcomment;
	private String time;
	private String max_time;
	private int idx;
}
