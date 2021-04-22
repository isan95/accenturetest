package io.github.isan95.accenturetest.payload.response;

public class JwtResponse {
	
	private String token;
	private String type = "Bearer";
	private String nDoc;
	private String address;
	
	public JwtResponse(String accessToken, String nDoc, String address) {
		this.token = accessToken;
		this.nDoc = nDoc;
		this.address = address;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}


	public String getNDoc() {
		return nDoc;
	}

	public void setNDoc(String nDoc) {
		this.nDoc = nDoc;
	}
	
	public String getAddress() {
		
		return this.address;
	}
	
	public void setAddress(String address) {
		
		this.address = address;
	}

}
