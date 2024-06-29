package hospital.staff;

public class Doctor {
	
	private int doctorId;
    private String doctorName;
    private String doctorSpecialty ;
    private String doctorContact;
    private String password;
    private String username;
    
    
    
    
    
	public Doctor(int doctorId, String doctorName, String doctorSpecialty, String doctorContact, String password,
			String username) {
		super();
		this.doctorId = doctorId;
		this.doctorName = doctorName;
		this.doctorSpecialty = doctorSpecialty;
		this.doctorContact = doctorContact;
		this.password = password;
		this.username = username;
	}
	public int getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getDoctorSpecialty() {
		return doctorSpecialty;
	}
	public void setDoctorSpecialty(String doctorSpecialty) {
		this.doctorSpecialty = doctorSpecialty;
	}
	public String getDoctorContact() {
		return doctorContact;
	}
	public void setDoctorContact(String doctorContact) {
		this.doctorContact = doctorContact;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return "Doctor [doctorId=" + doctorId + ", doctorName=" + doctorName + ", doctorSpecialty=" + doctorSpecialty
				+ ", doctorContact=" + doctorContact + ", password=" + password + ", username=" + username + "]";
	}
    
    
    
	
	

}
