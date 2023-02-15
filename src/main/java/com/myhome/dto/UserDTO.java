package com.myhome.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
	
	private long id;
	private String username;
	private String password;
	private boolean enabled;

	private String addr1;
	private String addr2;
	private String addr3;
}
