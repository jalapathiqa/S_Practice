package restClient_Tests;

//pojo -- plain old java object
public class Users {

	String name;
	String id;
	String job;
	String createdAt;

	public Users() {

	}

	public Users(String name, String job) {

		this.name = name;
		// this.id = id;
		this.job = job;
		// this.createdAt = createdAt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

}
