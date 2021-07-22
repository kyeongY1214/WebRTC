package com.d203.backend.api.request;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel("UserRequest")
public class UserReq {
	@ApiModelProperty(name="유저 email", example="ssafy_web")
	String email;
	@ApiModelProperty(name="유저 Password", example="your_password")
	String password;
	@ApiModelProperty(name="유저 language", example="your_language")
	Long lang;
	@ApiModelProperty(name="유저 nickname", example="your_nickname")
	String nickname;
	
	
}