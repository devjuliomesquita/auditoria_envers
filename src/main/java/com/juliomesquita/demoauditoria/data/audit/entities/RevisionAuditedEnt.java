package com.juliomesquita.demoauditoria.data.audit.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.juliomesquita.demoauditoria.infra.configs.audit.listener.RevisionAuditedListener;
import jakarta.persistence.*;
import org.hibernate.envers.ModifiedEntityNames;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "revision_audited", schema = "core_audit")
@RevisionEntity(value = RevisionAuditedListener.class)
public class RevisionAuditedEnt {
    @Id
    @RevisionNumber
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    @SequenceGenerator(name = "id", sequenceName = "revision_audited_seq", schema = "core_audit", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @RevisionTimestamp
    @Column(name = "revision_data", nullable = false)
    private Date revisionData;

    @JsonIgnore
    @Column(name = "action_done_by")
    private Long actionDoneBy;

    @JsonIgnore
    @Column(name = "name_action_done_by", nullable = false)
    private String nameActionDoneBy;

    @JsonIgnore
    @Column(name = "action_done_by_ip")
    private String actionDoneByIp;

    @JsonIgnore
    @Column(name = "method_names_tracking", nullable = false)
    private String methodNamesTracking;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "modified_entity_names", schema = "core_audit", joinColumns = @JoinColumn(name = "revision_id"))
    @Column(name = "entity_name")
    @ModifiedEntityNames
    private Set<String> modifiedEntityNames;

//    Pojo

    protected RevisionAuditedEnt() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getRevisionData() {
        return revisionData;
    }

    public void setRevisionData(Date revisionData) {
        this.revisionData = revisionData;
    }

    public Long getActionDoneBy() {
        return actionDoneBy;
    }

    public void setActionDoneBy(Long actionDoneBy) {
        this.actionDoneBy = actionDoneBy;
    }

    public String getNameActionDoneBy() {
        return nameActionDoneBy;
    }

    public void setNameActionDoneBy(String nameActionDoneBy) {
        this.nameActionDoneBy = nameActionDoneBy;
    }

    public String getActionDoneByIp() {
        return actionDoneByIp;
    }

    public void setActionDoneByIp(String actionDoneByIp) {
        this.actionDoneByIp = actionDoneByIp;
    }

    public Set<String> getModifiedEntityNames() {
        return modifiedEntityNames;
    }

    public void setModifiedEntityNames(Set<String> modifiedEntityNames) {
        this.modifiedEntityNames = modifiedEntityNames;
    }

    public String getMethodNamesTracking() {
        return methodNamesTracking;
    }

    public void setMethodNamesTracking(String methodNamesTracking) {
        this.methodNamesTracking = methodNamesTracking;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RevisionAuditedEnt that = (RevisionAuditedEnt) o;
        return Objects.equals(id, that.id) && Objects.equals(revisionData, that.revisionData)
            && Objects.equals(actionDoneBy, that.actionDoneBy) && Objects.equals(nameActionDoneBy, that.nameActionDoneBy)
            && Objects.equals(actionDoneByIp, that.actionDoneByIp) && Objects.equals(methodNamesTracking, that.methodNamesTracking)
            && Objects.equals(modifiedEntityNames, that.modifiedEntityNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, revisionData, actionDoneBy, nameActionDoneBy, actionDoneByIp, methodNamesTracking, modifiedEntityNames);
    }
}