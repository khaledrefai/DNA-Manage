import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICaseSampleType } from 'app/shared/model/case-sample-type.model';
import { getEntities as getCaseSampleTypes } from 'app/entities/case-sample-type/case-sample-type.reducer';
import { ISampleStatus } from 'app/shared/model/sample-status.model';
import { getEntities as getSampleStatuses } from 'app/entities/sample-status/sample-status.reducer';
import { getEntity, updateEntity, createEntity, reset } from './case-sample.reducer';
import { ICaseSample } from 'app/shared/model/case-sample.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CaseSampleUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const caseSampleTypes = useAppSelector(state => state.caseSampleType.entities);
  const sampleStatuses = useAppSelector(state => state.sampleStatus.entities);
  const caseSampleEntity = useAppSelector(state => state.caseSample.entity);
  const loading = useAppSelector(state => state.caseSample.loading);
  const updating = useAppSelector(state => state.caseSample.updating);
  const updateSuccess = useAppSelector(state => state.caseSample.updateSuccess);
  const handleClose = () => {
    props.history.push('/case-sample' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCaseSampleTypes({}));
    dispatch(getSampleStatuses({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...caseSampleEntity,
      ...values,
      caseSampleType: caseSampleTypes.find(it => it.id.toString() === values.caseSampleType.toString()),
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
          ...caseSampleEntity,
          caseSampleType: caseSampleEntity?.caseSampleType?.id,
          sampleStatus: caseSampleEntity?.sampleStatus?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dnaManagementApplicationApp.caseSample.home.createOrEditLabel" data-cy="CaseSampleCreateUpdateHeading">
            <Translate contentKey="dnaManagementApplicationApp.caseSample.home.createOrEditLabel">Create or edit a CaseSample</Translate>
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
                  id="case-sample-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('dnaManagementApplicationApp.caseSample.sampleId')}
                id="case-sample-sampleId"
                name="sampleId"
                data-cy="sampleId"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.caseSample.fullNameAr')}
                id="case-sample-fullNameAr"
                name="fullNameAr"
                data-cy="fullNameAr"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.caseSample.fullNameEn')}
                id="case-sample-fullNameEn"
                name="fullNameEn"
                data-cy="fullNameEn"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.caseSample.natAr')}
                id="case-sample-natAr"
                name="natAr"
                data-cy="natAr"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.caseSample.natEn')}
                id="case-sample-natEn"
                name="natEn"
                data-cy="natEn"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.caseSample.uid')}
                id="case-sample-uid"
                name="uid"
                data-cy="uid"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.caseSample.emiratesId')}
                id="case-sample-emiratesId"
                name="emiratesId"
                data-cy="emiratesId"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.caseSample.exhibit')}
                id="case-sample-exhibit"
                name="exhibit"
                data-cy="exhibit"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.caseSample.gender')}
                id="case-sample-gender"
                name="gender"
                data-cy="gender"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.caseSample.dateOfBirth')}
                id="case-sample-dateOfBirth"
                name="dateOfBirth"
                data-cy="dateOfBirth"
                type="date"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.caseSample.dueDate')}
                id="case-sample-dueDate"
                name="dueDate"
                data-cy="dueDate"
                type="date"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.caseSample.recievedDate')}
                id="case-sample-recievedDate"
                name="recievedDate"
                data-cy="recievedDate"
                type="date"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.caseSample.sampleNotes')}
                id="case-sample-sampleNotes"
                name="sampleNotes"
                data-cy="sampleNotes"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.caseSample.addBy')}
                id="case-sample-addBy"
                name="addBy"
                data-cy="addBy"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.caseSample.addDate')}
                id="case-sample-addDate"
                name="addDate"
                data-cy="addDate"
                type="date"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.caseSample.updateBy')}
                id="case-sample-updateBy"
                name="updateBy"
                data-cy="updateBy"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.caseSample.updateDate')}
                id="case-sample-updateDate"
                name="updateDate"
                data-cy="updateDate"
                type="date"
              />
              <ValidatedBlobField
                label={translate('dnaManagementApplicationApp.caseSample.attachment')}
                id="case-sample-attachment"
                name="attachment"
                data-cy="attachment"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.caseSample.caseNumber')}
                id="case-sample-caseNumber"
                name="caseNumber"
                data-cy="caseNumber"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.caseSample.barcodeNumber')}
                id="case-sample-barcodeNumber"
                name="barcodeNumber"
                data-cy="barcodeNumber"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.caseSample.caseType')}
                id="case-sample-caseType"
                name="caseType"
                data-cy="caseType"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.caseSample.policeStation')}
                id="case-sample-policeStation"
                name="policeStation"
                data-cy="policeStation"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.caseSample.reportNumber')}
                id="case-sample-reportNumber"
                name="reportNumber"
                data-cy="reportNumber"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.caseSample.testEndDate')}
                id="case-sample-testEndDate"
                name="testEndDate"
                data-cy="testEndDate"
                type="date"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.caseSample.caseNote')}
                id="case-sample-caseNote"
                name="caseNote"
                data-cy="caseNote"
                type="text"
              />
              <ValidatedField
                id="case-sample-caseSampleType"
                name="caseSampleType"
                data-cy="caseSampleType"
                label={translate('dnaManagementApplicationApp.caseSample.caseSampleType')}
                type="select"
              >
                <option value="" key="0" />
                {caseSampleTypes
                  ? caseSampleTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="case-sample-sampleStatus"
                name="sampleStatus"
                data-cy="sampleStatus"
                label={translate('dnaManagementApplicationApp.caseSample.sampleStatus')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/case-sample" replace color="info">
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

export default CaseSampleUpdate;
