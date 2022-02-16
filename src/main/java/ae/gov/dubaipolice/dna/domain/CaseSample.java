package ae.gov.dubaipolice.dna.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A CaseSample.
 */
@Entity
@Table(name = "case_sample")
public class CaseSample implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "sample_id")
    private Long sampleId;

    @Column(name = "full_name_ar")
    private String fullNameAr;

    @Column(name = "full_name_en")
    private String fullNameEn;

    @Column(name = "nat_ar")
    private String natAr;

    @Column(name = "nat_en")
    private String natEn;

    @Column(name = "jhi_uid")
    private String uid;

    @Column(name = "emirates_id")
    private String emiratesId;

    @Column(name = "exhibit")
    private String exhibit;

    @Column(name = "gender")
    private String gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "recieved_date")
    private LocalDate recievedDate;

    @Column(name = "sample_notes")
    private String sampleNotes;

    @Column(name = "add_by")
    private Long addBy;

    @Column(name = "add_date")
    private LocalDate addDate;

    @Column(name = "update_by")
    private Long updateBy;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Lob
    @Column(name = "attachment")
    private byte[] attachment;

    @Column(name = "attachment_content_type")
    private String attachmentContentType;

    @Column(name = "case_number")
    private String caseNumber;

    @Column(name = "barcode_number")
    private String barcodeNumber;

    @Column(name = "case_type")
    private String caseType;

    @Column(name = "police_station")
    private String policeStation;

    @Column(name = "report_number")
    private String reportNumber;

    @Column(name = "test_end_date")
    private LocalDate testEndDate;

    @Column(name = "case_note")
    private String caseNote;

    @ManyToOne
    private CaseSampleType caseSampleType;

    @ManyToOne
    private SampleStatus sampleStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CaseSample id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSampleId() {
        return this.sampleId;
    }

    public CaseSample sampleId(Long sampleId) {
        this.setSampleId(sampleId);
        return this;
    }

    public void setSampleId(Long sampleId) {
        this.sampleId = sampleId;
    }

    public String getFullNameAr() {
        return this.fullNameAr;
    }

    public CaseSample fullNameAr(String fullNameAr) {
        this.setFullNameAr(fullNameAr);
        return this;
    }

    public void setFullNameAr(String fullNameAr) {
        this.fullNameAr = fullNameAr;
    }

    public String getFullNameEn() {
        return this.fullNameEn;
    }

    public CaseSample fullNameEn(String fullNameEn) {
        this.setFullNameEn(fullNameEn);
        return this;
    }

    public void setFullNameEn(String fullNameEn) {
        this.fullNameEn = fullNameEn;
    }

    public String getNatAr() {
        return this.natAr;
    }

    public CaseSample natAr(String natAr) {
        this.setNatAr(natAr);
        return this;
    }

    public void setNatAr(String natAr) {
        this.natAr = natAr;
    }

    public String getNatEn() {
        return this.natEn;
    }

    public CaseSample natEn(String natEn) {
        this.setNatEn(natEn);
        return this;
    }

    public void setNatEn(String natEn) {
        this.natEn = natEn;
    }

    public String getUid() {
        return this.uid;
    }

    public CaseSample uid(String uid) {
        this.setUid(uid);
        return this;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmiratesId() {
        return this.emiratesId;
    }

    public CaseSample emiratesId(String emiratesId) {
        this.setEmiratesId(emiratesId);
        return this;
    }

    public void setEmiratesId(String emiratesId) {
        this.emiratesId = emiratesId;
    }

    public String getExhibit() {
        return this.exhibit;
    }

    public CaseSample exhibit(String exhibit) {
        this.setExhibit(exhibit);
        return this;
    }

    public void setExhibit(String exhibit) {
        this.exhibit = exhibit;
    }

    public String getGender() {
        return this.gender;
    }

    public CaseSample gender(String gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public CaseSample dateOfBirth(LocalDate dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    public CaseSample dueDate(LocalDate dueDate) {
        this.setDueDate(dueDate);
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getRecievedDate() {
        return this.recievedDate;
    }

    public CaseSample recievedDate(LocalDate recievedDate) {
        this.setRecievedDate(recievedDate);
        return this;
    }

    public void setRecievedDate(LocalDate recievedDate) {
        this.recievedDate = recievedDate;
    }

    public String getSampleNotes() {
        return this.sampleNotes;
    }

    public CaseSample sampleNotes(String sampleNotes) {
        this.setSampleNotes(sampleNotes);
        return this;
    }

    public void setSampleNotes(String sampleNotes) {
        this.sampleNotes = sampleNotes;
    }

    public Long getAddBy() {
        return this.addBy;
    }

    public CaseSample addBy(Long addBy) {
        this.setAddBy(addBy);
        return this;
    }

    public void setAddBy(Long addBy) {
        this.addBy = addBy;
    }

    public LocalDate getAddDate() {
        return this.addDate;
    }

    public CaseSample addDate(LocalDate addDate) {
        this.setAddDate(addDate);
        return this;
    }

    public void setAddDate(LocalDate addDate) {
        this.addDate = addDate;
    }

    public Long getUpdateBy() {
        return this.updateBy;
    }

    public CaseSample updateBy(Long updateBy) {
        this.setUpdateBy(updateBy);
        return this;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public LocalDate getUpdateDate() {
        return this.updateDate;
    }

    public CaseSample updateDate(LocalDate updateDate) {
        this.setUpdateDate(updateDate);
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public byte[] getAttachment() {
        return this.attachment;
    }

    public CaseSample attachment(byte[] attachment) {
        this.setAttachment(attachment);
        return this;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentContentType() {
        return this.attachmentContentType;
    }

    public CaseSample attachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
        return this;
    }

    public void setAttachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
    }

    public String getCaseNumber() {
        return this.caseNumber;
    }

    public CaseSample caseNumber(String caseNumber) {
        this.setCaseNumber(caseNumber);
        return this;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getBarcodeNumber() {
        return this.barcodeNumber;
    }

    public CaseSample barcodeNumber(String barcodeNumber) {
        this.setBarcodeNumber(barcodeNumber);
        return this;
    }

    public void setBarcodeNumber(String barcodeNumber) {
        this.barcodeNumber = barcodeNumber;
    }

    public String getCaseType() {
        return this.caseType;
    }

    public CaseSample caseType(String caseType) {
        this.setCaseType(caseType);
        return this;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getPoliceStation() {
        return this.policeStation;
    }

    public CaseSample policeStation(String policeStation) {
        this.setPoliceStation(policeStation);
        return this;
    }

    public void setPoliceStation(String policeStation) {
        this.policeStation = policeStation;
    }

    public String getReportNumber() {
        return this.reportNumber;
    }

    public CaseSample reportNumber(String reportNumber) {
        this.setReportNumber(reportNumber);
        return this;
    }

    public void setReportNumber(String reportNumber) {
        this.reportNumber = reportNumber;
    }

    public LocalDate getTestEndDate() {
        return this.testEndDate;
    }

    public CaseSample testEndDate(LocalDate testEndDate) {
        this.setTestEndDate(testEndDate);
        return this;
    }

    public void setTestEndDate(LocalDate testEndDate) {
        this.testEndDate = testEndDate;
    }

    public String getCaseNote() {
        return this.caseNote;
    }

    public CaseSample caseNote(String caseNote) {
        this.setCaseNote(caseNote);
        return this;
    }

    public void setCaseNote(String caseNote) {
        this.caseNote = caseNote;
    }

    public CaseSampleType getCaseSampleType() {
        return this.caseSampleType;
    }

    public void setCaseSampleType(CaseSampleType caseSampleType) {
        this.caseSampleType = caseSampleType;
    }

    public CaseSample caseSampleType(CaseSampleType caseSampleType) {
        this.setCaseSampleType(caseSampleType);
        return this;
    }

    public SampleStatus getSampleStatus() {
        return this.sampleStatus;
    }

    public void setSampleStatus(SampleStatus sampleStatus) {
        this.sampleStatus = sampleStatus;
    }

    public CaseSample sampleStatus(SampleStatus sampleStatus) {
        this.setSampleStatus(sampleStatus);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CaseSample)) {
            return false;
        }
        return id != null && id.equals(((CaseSample) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CaseSample{" +
            "id=" + getId() +
            ", sampleId=" + getSampleId() +
            ", fullNameAr='" + getFullNameAr() + "'" +
            ", fullNameEn='" + getFullNameEn() + "'" +
            ", natAr='" + getNatAr() + "'" +
            ", natEn='" + getNatEn() + "'" +
            ", uid='" + getUid() + "'" +
            ", emiratesId='" + getEmiratesId() + "'" +
            ", exhibit='" + getExhibit() + "'" +
            ", gender='" + getGender() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", recievedDate='" + getRecievedDate() + "'" +
            ", sampleNotes='" + getSampleNotes() + "'" +
            ", addBy=" + getAddBy() +
            ", addDate='" + getAddDate() + "'" +
            ", updateBy=" + getUpdateBy() +
            ", updateDate='" + getUpdateDate() + "'" +
            ", attachment='" + getAttachment() + "'" +
            ", attachmentContentType='" + getAttachmentContentType() + "'" +
            ", caseNumber='" + getCaseNumber() + "'" +
            ", barcodeNumber='" + getBarcodeNumber() + "'" +
            ", caseType='" + getCaseType() + "'" +
            ", policeStation='" + getPoliceStation() + "'" +
            ", reportNumber='" + getReportNumber() + "'" +
            ", testEndDate='" + getTestEndDate() + "'" +
            ", caseNote='" + getCaseNote() + "'" +
            "}";
    }
}
