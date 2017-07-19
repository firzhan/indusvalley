package com.indusvalley.oauth.model.json.callback;

public class FBGraphAPIResponse {

	private String id;
	private String birthday;
	private String email;
	private String first_name;
	private String gender;
	private String name;
	private String timezone;
	private FBGraphAPILocation location;

	public FBGraphAPIResponse() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public FBGraphAPILocation getLocation() {
		return location;
	}

	public void setLocation(FBGraphAPILocation location) {
		this.location = location;
	}
}
