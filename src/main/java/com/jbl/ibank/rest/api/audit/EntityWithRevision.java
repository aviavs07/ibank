package com.jbl.ibank.rest.api.audit;

import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class EntityWithRevision<T> {

    private AuditRevisionEntity revision;
    private T entity;
    private String type;

    public EntityWithRevision(AuditRevisionEntity revision, T entity, String type) {
        this.revision = revision;
        this.entity = entity;
        this.type = type;
    }
}
