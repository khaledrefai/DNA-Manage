import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IDnaProfileType } from 'app/shared/model/dna-profile-type.model';
import { getEntities as getDnaProfileTypes } from 'app/entities/dna-profile-type/dna-profile-type.reducer';
import { IProjectType } from 'app/shared/model/project-type.model';
import { getEntities as getProjectTypes } from 'app/entities/project-type/project-type.reducer';
import { ISampleType } from 'app/shared/model/sample-type.model';
import { getEntities as getSampleTypes } from 'app/entities/sample-type/sample-type.reducer';
import { IWorkPlace } from 'app/shared/model/work-place.model';
import { getEntities as getWorkPlaces } from 'app/entities/work-place/work-place.reducer';
import { ISampleStatus } from 'app/shared/model/sample-status.model';
import { getEntities as getSampleStatuses } from 'app/entities/sample-status/sample-status.reducer';
import { getEntity, updateEntity, createEntity, reset } from './inhouse-sample.reducer';
import { IInhouseSample } from 'app/shared/model/inhouse-sample.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const InhouseSampleUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const dnaProfileTypes = useAppSelector(state => state.dnaProfileType.entities);
  const projectTypes = useAppSelector(state => state.projectType.entities);
  const sampleTypes = useAppSelector(state => state.sampleType.entities);
  const workPlaces = useAppSelector(state => state.workPlace.entities);
  const sampleStatuses = useAppSelector(state => state.sampleStatus.entities);
  const inhouseSampleEntity = useAppSelector(state => state.inhouseSample.entity);
  const loading = useAppSelector(state => state.inhouseSample.loading);
  const updating = useAppSelector(state => state.inhouseSample.updating);
  const updateSuccess = useAppSelector(state => state.inhouseSample.updateSuccess);
  const handleClose = () => {
    props.history.push('/inhouse-sample' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getDnaProfileTypes({}));
    dispatch(getProjectTypes({}));
    dispatch(getSampleTypes({}));
    dispatch(getWorkPlaces({}));
    dispatch(getSampleStatuses({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...inhouseSampleEntity,
      ...values,
      dnaProfileType: dnaProfileTypes.find(it => it.id.toString() === values.dnaProfileType.toString()),
      projectType: projectTypes.find(it => it.id.toString() === values.projectType.toString()),
      sampleType: sampleTypes.find(it => it.id.toString() === values.sampleType.toString()),
      workPlace: workPlaces.find(it => it.id.toString() === values.workPlace.toString()),
      sampleStatus: sampleStatuses.find(it => it.id.toString() === values.sampleStatus.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...inhouseSampleEntity,
          dnaProfileType: inhouseSampleEntity?.dnaProfileType?.id,
          projectType: inhouseSampleEntity?.projectType?.id,
          sampleType: inhouseSampleEntity?.sampleType?.id,
          workPlace: inhouseSampleEntity?.workPlace?.id,
          sampleStatus: inhouseSampleEntity?.sampleStatus?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dnaManagementApplicationApp.inhouseSample.home.createOrEditLabel" data-cy="InhouseSampleCreateUpdateHeading">
            <Translate contentKey="dnaManagementApplicationApp.inhouseSample.home.createOrEditLabel">
              Create or edit a InhouseSample
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="inhouse-sample-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('dnaManagementApplicationApp.inhouseSample.sampleId')}
                id="inhouse-sample-sampleId"
                name="sampleId"
                data-cy="sampleId"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.inhouseSample.empGrpNo')}
                id="inhouse-sample-empGrpNo"
                name="empGrpNo"
                data-cy="empGrpNo"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.inhouseSample.fullNameAr')}
                id="inhouse-sample-fullNameAr"
                name="fullNameAr"
                data-cy="fullNameAr"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.inhouseSample.fullNameEn')}
                id="inhouse-sample-fullNameEn"
                name="fullNameEn"
                data-cy="fullNameEn"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.inhouseSample.natAr')}
                id="inhouse-sample-natAr"
                name="natAr"
                data-cy="natAr"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.inhouseSample.natEn')}
                id="inhouse-sample-natEn"
                name="natEn"
                data-cy="natEn"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.inhouseSample.uid')}
                id="inhouse-sample-uid"
                name="uid"
                data-cy="uid"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.inhouseSample.emiratesId')}
                id="inhouse-sample-emiratesId"
                name="emiratesId"
                data-cy="emiratesId"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.inhouseSample.exhibit')}
                id="inhouse-sample-exhibit"
                name="exhibit"
                data-cy="exhibit"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.inhouseSample.gender')}
                id="inhouse-sample-gender"
                name="gender"
                data-cy="gender"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.inhouseSample.dateOfBirth')}
                id="inhouse-sample-dateOfBirth"
                name="dateOfBirth"
                data-cy="dateOfBirth"
                type="date"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.inhouseSample.batchDate')}
                id="inhouse-sample-batchDate"
                name="batchDate"
                data-cy="batchDate"
                type="date"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.inhouseSample.collectionDate')}
                id="inhouse-sample-collectionDate"
                name="collectionDate"
                data-cy="collectionDate"
                type="date"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.inhouseSample.sampleNotes')}
                id="inhouse-sample-sampleNotes"
                name="sampleNotes"
                data-cy="sampleNotes"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.inhouseSample.addBy')}
                id="inhouse-sample-addBy"
                name="addBy"
                data-cy="addBy"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.inhouseSample.addDate')}
                id="inhouse-sample-addDate"
                name="addDate"
                data-cy="addDate"
                type="date"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.inhouseSample.updateBy')}
                id="inhouse-sample-updateBy"
                name="updateBy"
                data-cy="updateBy"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.inhouseSample.updateDate')}
                id="inhouse-sample-updateDate"
                name="updateDate"
                data-cy="updateDate"
                type="date"
              />
              <ValidatedBlobField
                label={translate('dnaManagementApplicationApp.inhouseSample.attachment')}
                id="inhouse-sample-attachment"
                name="attachment"
                data-cy="attachment"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedField
                id="inhouse-sample-dnaProfileType"
                name="dnaProfileType"
                data-cy="dnaProfileType"
                label={translate('dnaManagementApplicationApp.inhouseSample.dnaProfileType')}
                type="select"
              >
                <option value="" key="0" />
                {dnaProfileTypes
                  ? dnaProfileTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="inhouse-sample-projectType"
                name="projectType"
                data-cy="projectType"
                label={translate('dnaManagementApplicationApp.inhouseSample.projectType')}
                type="select"
              >
                <option value="" key="0" />
                {projectTypes
                  ? projectTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="inhouse-sample-sampleType"
                name="sampleType"
                data-cy="sampleType"
                label={translate('dnaManagementApplicationApp.inhouseSample.sampleType')}
                type="select"
              >
                <option value="" key="0" />
                {sampleTypes
                  ? sampleTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="inhouse-sample-workPlace"
                name="workPlace"
                data-cy="workPlace"
                label={translate('dnaManagementApplicationApp.inhouseSample.workPlace')}
                type="select"
              >
                <option value="" key="0" />
                {workPlaces
                  ? workPlaces.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="inhouse-sample-sampleStatus"
                name="sampleStatus"
                data-cy="sampleStatus"
                label={translate('dnaManagementApplicationApp.inhouseSample.sampleStatus')}
                type="select"
              >
                <option value="" key="0" />
                {sampleStatuses
                  ? sampleStatuses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/inhouse-sample" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default InhouseSampleUpdate;
