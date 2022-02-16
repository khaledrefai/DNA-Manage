import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './inbody-data.reducer';
import { IInbodyData } from 'app/shared/model/inbody-data.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const InbodyDataUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const inbodyDataEntity = useAppSelector(state => state.inbodyData.entity);
  const loading = useAppSelector(state => state.inbodyData.loading);
  const updating = useAppSelector(state => state.inbodyData.updating);
  const updateSuccess = useAppSelector(state => state.inbodyData.updateSuccess);
  const handleClose = () => {
    props.history.push('/inbody-data' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...inbodyDataEntity,
      ...values,
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
          ...inbodyDataEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dnaManagementApplicationApp.inbodyData.home.createOrEditLabel" data-cy="InbodyDataCreateUpdateHeading">
            <Translate contentKey="dnaManagementApplicationApp.inbodyData.home.createOrEditLabel">Create or edit a InbodyData</Translate>
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
                  id="inbody-data-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('dnaManagementApplicationApp.inbodyData.userID')}
                id="inbody-data-userID"
                name="userID"
                data-cy="userID"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.inbodyData.columnName')}
                id="inbody-data-columnName"
                name="columnName"
                data-cy="columnName"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.inbodyData.columnValue')}
                id="inbody-data-columnValue"
                name="columnValue"
                data-cy="columnValue"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/inbody-data" replace color="info">
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

export default InbodyDataUpdate;
