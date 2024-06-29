package hospital.staff;

public class Nurses {
	
	private int nurseId;
	private String nurseName;
	private String nurseSpecialty;
	private String nurseContact;
	public int getNurseId() {
		return nurseId;
	}
	public void setNurseId(int nurseId) {
		this.nurseId = nurseId;
	}
	public String getNurseName() {
		return nurseName;
	}
	public void setNurseName(String nurseName) {
		this.nurseName = nurseName;
	}
	public String getNurseSpecialty() {
		return nurseSpecialty;
	}
	public void setNurseSpecialty(String nurseSpecialty) {
		this.nurseSpecialty = nurseSpecialty;
	}
	public String getNurseContact() {
		return nurseContact;
	}
	public void setNurseContact(String nurseContact) {
		this.nurseContact = nurseContact;
	}
	public Nurses(int nurseId, String nurseName, String nurseSpecialty, String nurseContact) {
		super();
		this.nurseId = nurseId;
		this.nurseName = nurseName;
		this.nurseSpecialty = nurseSpecialty;
		this.nurseContact = nurseContact;
	}
	@Override
	public String toString() {
		return "Nurses [nurseId=" + nurseId + ", nurseName=" + nurseName + ", nurseSpecialty=" + nurseSpecialty
				+ ", nurseContact=" + nurseContact + "]";
	}

	
	   	    

}
