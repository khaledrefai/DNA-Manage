package ae.gov.dubaipolice.dna.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A InhouseSample.
 */
@Entity
@Table(name = "inhouse_sample")
public class InhouseSample implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "sample_id")
    private Long sampleId;

    @Column(name = "emp_grp_no")
    private Long empGrpNo;

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

    @Column(name = "batch_date")
    private LocalDate batchDate;

    @Column(name = "collection_date")
    private LocalDate collectionDate;

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

    @ManyToOne
    private DnaProfileType dnaProfileType;

    @ManyToOne
    private ProjectType projectType;

    @ManyToOne
    private SampleType sampleType;

    @ManyToOne
    private WorkPlace workPlace;

    @ManyToOne
    private SampleStatus sampleStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public InhouseSample id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSampleId() {
        return this.sampleId;
    }

    public InhouseSample sampleId(Long sampleId) {
        this.setSampleId(sampleId);
        return this;
    }

    public void setSampleId(Long sampleId) {
        this.sampleId = sampleId;
    }

    public Long getEmpGrpNo() {
        return this.empGrpNo;
    }

    public InhouseSample empGrpNo(Long empGrpNo) {
        this.setEmpGrpNo(empGrpNo);
        return this;
    }

    public void setEmpGrpNo(Long empGrpNo) {
        this.empGrpNo = empGrpNo;
    }

    public String getFullNameAr() {
        return this.fullNameAr;
    }

    public InhouseSample fullNameAr(String fullNameAr) {
        this.setFullNameAr(fullNameAr);
        return this;
    }

    public void setFullNameAr(String fullNameAr) {
        this.fullNameAr = fullNameAr;
    }

    public String getFullNameEn() {
        return this.fullNameEn;
    }

    public InhouseSample fullNameEn(String fullNameEn) {
        this.setFullNameEn(fullNameEn);
        return this;
    }

    public void setFullNameEn(String fullNameEn) {
        this.fullNameEn = fullNameEn;
    }

    public String getNatAr() {
        return this.natAr;
    }

    public InhouseSample natAr(String natAr) {
        this.setNatAr(natAr);
        return this;
    }

    public void setNatAr(String natAr) {
        this.natAr = natAr;
    }

    public String getNatEn() {
        return this.natEn;
    }

    public InhouseSample natEn(String natEn) {
        this.setNatEn(natEn);
        return this;
    }

    public void setNatEn(String natEn) {
        this.natEn = natEn;
    }

    public String getUid() {
        return this.uid;
    }

    public InhouseSample uid(String uid) {
        this.setUid(uid);
        return this;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmiratesId() {
        return this.emiratesId;
    }

    public InhouseSample emiratesId(String emiratesId) {
        this.setEmiratesId(emiratesId);
        return this;
    }

    public void setEmiratesId(String emiratesId) {
        this.emiratesId = emiratesId;
    }

    public String getExhibit() {
        return this.exhibit;
    }

    public InhouseSample exhibit(String exhibit) {
        this.setExhibit(exhibit);
        return this;
    }

    public void setExhibit(String exhibit) {
        this.exhibit = exhibit;
    }

    public String getGender() {
        return this.gender;
    }

    public InhouseSample gender(String gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public InhouseSample dateOfBirth(LocalDate dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getBatchDate() {
        return this.batchDate;
    }

    public InhouseSample batchDate(LocalDate batchDate) {
        this.setBatchDate(batchDate);
        return this;
    }

    public void setBatchDate(LocalDate batchDate) {
        this.batchDate = batchDate;
    }

    public LocalDate getCollectionDate() {
        return this.collectionDate;
    }

    public InhouseSample collectionDate(LocalDate collectionDate) {
        this.setCollectionDate(collectionDate);
        return this;
    }

    public void setCollectionDate(LocalDate collectionDate) {
        this.collectionDate = collectionDate;
    }

    public String getSampleNotes() {
        return this.sampleNotes;
    }

    public InhouseSample sampleNotes(String sampleNotes) {
        this.setSampleNotes(sampleNotes);
        return this;
    }

    public void setSampleNotes(String sampleNotes) {
        this.sampleNotes = sampleNotes;
    }

    public Long getAddBy() {
        return this.addBy;
    }

    public InhouseSample addBy(Long addBy) {
        this.setAddBy(addBy);
        return this;
    }

    public void setAddBy(Long addBy) {
        this.addBy = addBy;
    }

    public LocalDate getAddDate() {
        return this.addDate;
    }

    public InhouseSample addDate(LocalDate addDate) {
        this.setAddDate(addDate);
        return this;
    }

    public void setAddDate(LocalDate addDate) {
        this.addDate = addDate;
    }

    public Long getUpdateBy() {
        return this.updateBy;
    }

    public InhouseSample updateBy(Long updateBy) {
        this.setUpdateBy(updateBy);
        return this;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public LocalDate getUpdateDate() {
        return this.updateDate;
    }

    public InhouseSample updateDate(LocalDate updateDate) {
        this.setUpdateDate(updateDate);
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public byte[] getAttachment() {
        return this.attachment;
    }

    public InhouseSample attachment(byte[] attachment) {
        this.setAttachment(attachment);
        return this;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentContentType() {
        return this.attachmentContentType;
    }

    public InhouseSample attachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
        return this;
    }

    public void setAttachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
    }

    public DnaProfileType getDnaProfileType() {
        return this.dnaProfileType;
    }

    public void setDnaProfileType(DnaProfileType dnaProfileType) {
        this.dnaProfileType = dnaProfileType;
    }

    public InhouseSample dnaProfileType(DnaProfileType dnaProfileType) {
        this.setDnaProfileType(dnaProfileType);
        return this;
    }

    public ProjectType getProjectType() {
        return this.projectType;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }

    public InhouseSample projectType(ProjectType projectType) {
        this.setProjectType(projectType);
        return this;
    }

    public SampleType getSampleType() {
        return this.sampleType;
    }

    public void setSampleType(SampleType sampleType) {
        this.sampleType = sampleType;
    }

    public InhouseSample sampleType(SampleType sampleType) {
        this.setSampleType(sampleType);
        return this;
    }

    public WorkPlace getWorkPlace() {
        return this.workPlace;
    }

    public void setWorkPlace(WorkPlace workPlace) {
        this.workPlace = workPlace;
    }

    public InhouseSample workPlace(WorkPlace workPlace) {
        this.setWorkPlace(workPlace);
        return this;
    }

    public SampleStatus getSampleStatus() {
        return this.sampleStatus;
    }

    public void setSampleStatus(SampleStatus sampleStatus) {
        this.sampleStatus = sampleStatus;
    }

    public InhouseSample sampleStatus(SampleStatus sampleStatus) {
        this.setSampleStatus(sampleStatus);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InhouseSample)) {
            return false;
        }
        return id != null && id.equals(((InhouseSample) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InhouseSample{" +
            "id=" + getId() +
            ", sampleId=" + getSampleId() +
            ", empGrpNo=" + getEmpGrpNo() +
            ", fullNameAr='" + getFullNameAr() + "'" +
            ", fullNameEn='" + getFullNameEn() + "'" +
            ", natAr='" + getNatAr() + "'" +
            ", natEn='" + getNatEn() + "'" +
            ", uid='" + getUid() + "'" +
            ", emiratesId='" + getEmiratesId() + "'" +
            ", exhibit='" + getExhibit() + "'" +
            ", gender='" + getGender() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", batchDate='" + getBatchDate() + "'" +
            ", collectionDate='" + getCollectionDate() + "'" +
            ", sampleNotes='" + getSampleNotes() + "'" +
            ", addBy=" + getAddBy() +
            ", addDate='" + getAddDate() + "'" +
            ", updateBy=" + getUpdateBy() +
            ", updateDate='" + getUpdateDate() + "'" +
            ", attachment='" + getAttachment() + "'" +
            ", attachmentContentType='" + getAttachmentContentType() + "'" +
            "}";
    }
}
