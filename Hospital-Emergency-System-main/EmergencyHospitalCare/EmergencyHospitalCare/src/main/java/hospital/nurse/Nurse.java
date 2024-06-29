package hospital.nurse;


	public class Nurse {
		  private int nurseId;
		  private String nurseName;
		  private String nurseSpecialty;
		  private String nurseContact;
		  private String password;

		  // Constructor that takes id and name
		  public Nurse(int nurseId, String nurseName) {
		    this.nurseId = nurseId;
		    this.nurseName = nurseName;
		  }

		public Nurse(int nurseId, String nurseName, String nurseSpecialty, String nurseContact, String password) {
			super();
			this.nurseId = nurseId;
			this.nurseName = nurseName;
			this.nurseSpecialty = nurseSpecialty;
			this.nurseContact = nurseContact;
			this.password = password;
		}

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

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		 
		}
	

	
	



